/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.mailbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.io.Closeables;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineGal;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.db.DbDataSource;
import org.zmail.cs.db.DbDataSource.DataSourceItem;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.index.ZmailHit;
import org.zmail.cs.index.ZmailQueryResults;
import org.zmail.cs.mime.ParsedContact;
import org.zmail.cs.offline.OfflineLC;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.soap.admin.type.DataSourceType;

/**
 * Utility class for common gal sync operations
 * 
 */
public final class GalSyncUtil {

    private GalSyncUtil() {
    }

    /**
     * Find contact id from data source database
     * 
     * @param id
     * @param dsource
     * @return contact id, or -1 if not found
     * @throws ServiceException
     */
    public static int findContact(String id, DataSource dsource) throws ServiceException {
        DataSourceItem dsItem = DbDataSource.getReverseMapping(dsource, id);
        if (dsItem.itemId > 0)
            return dsItem.itemId;
        return -1;
    }

    /**
     * Create a DataSource instance for gal account
     * 
     * @param galAccount
     * @return DataSource for the account
     * @throws ServiceException
     */
    public static DataSource createDataSourceForAccount(Account galAccount) throws ServiceException {
        OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
        String dsId = galAccount.getAttr(OfflineConstants.A_offlineGalAccountDataSourceId, false);
        if (dsId == null) {
            dsId = UUID.randomUUID().toString();
            prov.setAccountAttribute(galAccount, OfflineConstants.A_offlineGalAccountDataSourceId, dsId);
        }
        return new DataSource(galAccount, DataSourceType.gal, galAccount.getName(), dsId,
                new HashMap<String, Object>(), prov);
    }

    /**
     * Retrieve a contact specified by email address from OfflineGal
     * 
     * @param requestedAcct
     * @param addr
     * @return Contact for the address or null if it does not exist
     * @throws ServiceException
     */
    public static Contact getGalDlistContact(OfflineAccount requestedAcct, String addr) throws ServiceException {
        Contact con = null;
        if (requestedAcct.isZcsAccount() && requestedAcct.isFeatureGalEnabled()
                && requestedAcct.isFeatureGalSyncEnabled()) {
            ZmailQueryResults dlResult = (new OfflineGal((OfflineAccount) requestedAcct)).search(addr, "group",
                    SortBy.NONE, 0, 0, null);
            if (dlResult != null) {
                try {
                    if (dlResult.hasNext()) {
                        ZmailHit hit = dlResult.getNext();
                        con = (Contact) hit.getMailItem();
                        while (OfflineLog.offline.isDebugEnabled() && dlResult.hasNext()) {
                            Contact dupe = (Contact) dlResult.getNext().getMailItem();
                            OfflineLog.offline.debug("Ignoring duplicate group %s", dupe);
                        }
                    }
                } finally {
                    Closeables.closeQuietly(dlResult);
                }
            }
        }
        return con;
    }

    /**
     * Retrieve a list of groups email addresses
     * 
     * @param requestedAcct
     * @param addrs - set of email addresses to select from
     * @return - subset of addrs which are distribution lists
     * @throws ServiceException
     */
    public static List<String> getGroupNames(Account requestedAcct, Set<String> addrs) throws ServiceException {
        ZmailQueryResults dlResult = (new OfflineGal((OfflineAccount) requestedAcct)).search(addrs, "group",
                SortBy.NONE, 0, 0, null);
        List<String> groups = new ArrayList<String>();
        if (dlResult != null) {
            try {
                while (dlResult.hasNext()) {
                    ZmailHit hit = dlResult.getNext();
                    Contact contact = (Contact) hit.getMailItem();
                    if (contact.getEmailAddresses().size() > 0) {
                        groups.addAll(contact.getEmailAddresses());
                    } else {
                        groups.add(contact.getFileAsString());
                    }
                }
            } finally {
                Closeables.closeQuietly(dlResult);
            }
        }
        return groups;
    }

    public static String getContactLogStr(ParsedContact contact) {
        StringBuilder logBuf = new StringBuilder();
        logBuf.append(" name=\"").append(contact.getFields().get(ContactConstants.A_fullName)).append("\"")
                .append(" type=\"").append(contact.getFields().get(ContactConstants.A_type)).append("\"");
        return logBuf.toString();
    }

    public static void fillContactAttrMap(Map<String, String> map) throws ServiceException {
        String fullName = map.get(ContactConstants.A_fullName);
        if (fullName == null) {
            String fname = map.get(ContactConstants.A_firstName);
            String lname = map.get(ContactConstants.A_lastName);
            fullName = fname == null ? "" : fname;
            if (lname != null)
                fullName = fullName + (fullName.length() > 0 ? " " : "") + lname;
            if (fullName.length() > 0)
                map.put(ContactConstants.A_fullName, fullName);
        }
        String type = map.get(ContactConstants.A_type);
        if (type == null) {
            type = map.get(OfflineGal.A_zmailCalResType) == null ? OfflineGal.CTYPE_ACCOUNT
                    : OfflineGal.CTYPE_RESOURCE;
            map.put(ContactConstants.A_type, type);
        }
    }

    private static LinkedHashMap<String, ParsedContact> getParsedContacts(List<Element> contacts, List<String> retryIds)
            throws ServiceException {
        LinkedHashMap<String, ParsedContact> parsed = new LinkedHashMap<String, ParsedContact>();
        for (Element elt : contacts) {
            String id = elt.getAttribute(AccountConstants.A_ID);
            Map<String, String> fields = new HashMap<String, String>();
            fields.put(OfflineConstants.GAL_LDAP_DN, id);
            for (Element eField : elt.listElements()) {
                String name = eField.getAttribute(Element.XMLElement.A_ATTR_NAME);
                if (!name.equals("objectClass"))
                    fields.put(name, eField.getText());
            }
            try {
                fillContactAttrMap(fields);
                parsed.put(id, new ParsedContact(fields));
            } catch (ServiceException e) {
                retryIds.add(id);
                // TODO LC ?
                if (retryIds.size() > 100) {
                    retryIds.clear();
                    OfflineLog.offline.info("Offline GAL sync retry aborted, too many failed items");
                    throw e;
                }
            }
        }
        return parsed;
    }

    static void createContact(Mailbox mbox, OperationContext ctxt, int syncFolder, DataSource ds,
            ParsedContact contact, String id, String logstr) throws ServiceException {
        Contact c = mbox.createContact(ctxt, contact, syncFolder, null);
        DbDataSource.addMapping(ds, new DataSourceItem(0, c.getId(), id, null), true);
        OfflineLog.offline.debug("Offline GAL contact created: %s id: %s remoteId: %s", logstr, c.getId(), id);
    }

    private static void saveParsedContact(Mailbox mbox, OperationContext ctxt, int syncFolder, String id,
            ParsedContact contact, String logstr, boolean isFullSync, DataSource ds) throws ServiceException {
        if (isFullSync) {
            createContact(mbox, ctxt, syncFolder, ds, contact, id, logstr);
        } else {
            int itemId = GalSyncUtil.findContact(id, ds);
            if (itemId > 0) {
                try {
                    mbox.modifyContact(ctxt, itemId, contact);
                    OfflineLog.offline
                            .debug("Offline GAL contact modified: %s id: %d remoteId: %s", logstr, itemId, id);
                } catch (MailServiceException.NoSuchItemException e) {
                    OfflineLog.offline.warn("Offline GAL modify error - no such contact: " + logstr + " itemId="
                            + Integer.toString(itemId));
                }
            } else {
                createContact(mbox, ctxt, syncFolder, ds, contact, id, logstr);
            }
        }
    }

    static ZcsMailbox getGalEnabledZcsMailbox(String domain) throws ServiceException {
        Set<String> existingAccounts = OfflineProvisioning.getOfflineInstance().getAllAccountsByDomain(domain);
        for (String accountId : existingAccounts) {
            //in case local dev domain is @host.local
            if (StringUtil.equal(OfflineConstants.LOCAL_ACCOUNT_ID, accountId)) {
                continue;
            }
            Account account = OfflineProvisioning.getOfflineInstance().getAccountById(accountId);
            // account might have been deleted here
            if (account != null && account.isFeatureGalEnabled() && account.isFeatureGalSyncEnabled()) {
                return (ZcsMailbox) MailboxManager.getInstance().getMailboxByAccountId(accountId);
            }
        }
        return null;
    }

    public static void fetchContacts(ZcsMailbox randMailbox, Mailbox galMbox, OperationContext ctxt, int syncFolder,
            String reqIds, boolean isFullSync, DataSource ds, List<String> retryContactIds, String token,
            String galAcctId) throws ServiceException, IOException {
        XMLElement req = new XMLElement(MailConstants.GET_CONTACTS_REQUEST);
        req.addElement(AdminConstants.E_CN).addAttribute(AccountConstants.A_ID, reqIds);
        Element response = null;
        try {
            response = randMailbox.sendRequest(req, true, true, OfflineLC.zdesktop_gal_sync_request_timeout.intValue(), SoapProtocol.Soap12);
        } catch (ServiceException e) {
            //resolve mail.NO_SUCH_CONTACT, recursively exclude the bad id and try the good ids.
            if (MailServiceException.NO_SUCH_CONTACT.equals(e.getCode())) {
                OfflineLog.offline.warn(
                        "GAL sync got NO_SUCH_CONTACT error, trying to isolate it, code: %s", e.getCode(), e);
                String err = e.toString();
                String key = "no such contact: ";
                String badIdStr = err.substring(err.indexOf(key)+key.length(), err.indexOf("ExceptionId"));
                String badId = galAcctId + ":" + badIdStr;
                retryContactIds.add(badId);
                for (String ids : Splitter.on(badId).split(reqIds)) {
                    String newIds = retrofitIds(ids);
                    if (!Strings.isNullOrEmpty(newIds)) {
                        fetchContacts(randMailbox, galMbox, ctxt, syncFolder, newIds, isFullSync, ds, retryContactIds, token, galAcctId);
                    }
                }
            } else {
                throw e;
            }
        }
        if (response == null) {
            return;
        }
        List<Element> contacts = response.listElements(MailConstants.E_CONTACT);
        LinkedHashMap<String, ParsedContact> parsedContacts = getParsedContacts(contacts, retryContactIds);

        if (!parsedContacts.isEmpty()) {
            boolean success = false;
            try {
                galMbox.beginTransaction("GALSync", null);
                if (isFullSync) {
                    for (Entry<String, ParsedContact> entry : parsedContacts.entrySet()) {
                        saveParsedContact(galMbox, ctxt, syncFolder, entry.getKey(), entry.getValue(),
                                getContactLogStr(entry.getValue()), isFullSync, ds);
                    }
                    GalSyncCheckpointUtil.checkpoint(galMbox, token, galAcctId, reqIds);
                } else {
                    SortedMap<Integer, String> sorted = new TreeMap<Integer, String>();
                    int count = Integer.MAX_VALUE;
                    for (Entry<String, ParsedContact> entry : parsedContacts.entrySet()) {
                        int itemId = GalSyncUtil.findContact(entry.getKey(), ds);
                        if (itemId > 0) {
                            sorted.put(itemId, entry.getKey()); // exists, sort by local item id
                        } else {
                            sorted.put(count--, entry.getKey()); // doesn't exist; add at end; new items have highest id
                        }
                    }
                    for (String id : sorted.values()) {
                        ParsedContact pc = parsedContacts.get(id);
                        saveParsedContact(galMbox, ctxt, syncFolder, id, pc, getContactLogStr(pc), false, ds);
                    }
                }
                success = true;
            } finally {
                galMbox.endTransaction(success);
            }
        }
    }

    private static String retrofitIds(String ids) {
        if (ids != null) {
            ids = ids.trim();
            if (ids.startsWith(",")) {
                ids = ids.substring(1);
            }
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length()-1);
            }
            ids = ids.trim();
        }
        return ids;
    }

    public static void fetchContacts(Mailbox galMbox, OperationContext ctxt, int syncFolder, String reqIds,
            boolean isFullSync, DataSource ds, List<String> retryContactIds, String token, String galAcctId)
            throws ServiceException, IOException {
        String domain = ((OfflineAccount) galMbox.getAccount()).getDomain();
        ZcsMailbox mbox = getGalEnabledZcsMailbox(domain);
        fetchContacts(mbox, galMbox, ctxt, syncFolder, reqIds, isFullSync, ds, retryContactIds, token, galAcctId);
    }

    public static void removeConfig(Mailbox galMbox) throws ServiceException {
        GalSyncCheckpointUtil.removeCheckpoint(galMbox);
        GalSyncRetry.remove(galMbox);
    }
}
