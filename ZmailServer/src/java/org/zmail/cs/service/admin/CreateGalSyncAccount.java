/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.mailbox.ACL;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.CreateGalSyncAccountRequest;
import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.soap.admin.type.GalMode;
import org.zmail.soap.type.AccountBy;
import org.zmail.soap.type.AccountSelector;

public class CreateGalSyncAccount extends AdminDocumentHandler {

    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        CreateGalSyncAccountRequest cgaRequest = JaxbUtil.elementToJaxb(request);

        String name = cgaRequest.getName();
        String domainStr = cgaRequest.getDomain();
        GalMode type = cgaRequest.getType();

        AccountSelector acctSelector = cgaRequest.getAccount();
        AccountBy acctBy = acctSelector.getBy();
        String acctValue = acctSelector.getKey();

        String password = cgaRequest.getPassword();
        String folder = cgaRequest.getFolder();

        String mailHost = cgaRequest.getMailHost();

        Domain domain = prov.getDomainByName(domainStr);

        if (domain == null)
            throw AccountServiceException.NO_SUCH_DOMAIN(domainStr);

        Account account = null;
        try {
            account = prov.get(acctBy.toKeyDomainBy(), acctValue, zsc.getAuthToken());
        } catch (ServiceException se) {
            ZmailLog.gal.warn("error checking GalSyncAccount", se);
        }

        // create the system account if not already exists.
        if (account == null) {
            if (acctBy != AccountBy.name)
                throw AccountServiceException.NO_SUCH_ACCOUNT(acctValue);
            // there should be one gal sync account per domain per mailhost
            for (String acctId : domain.getGalAccountId()) {
                Account acct = prov.getAccountById(acctId);
                if ((acct != null) && (acct.getMailHost().equals(mailHost)))
                    throw AccountServiceException.ACCOUNT_EXISTS(acct.getName());
            }
            // XXX revisit
            checkDomainRightByEmail(zsc, acctValue, Admin.R_createAccount);
            Map<String,Object> accountAttrs = new HashMap<String,Object>();
            StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zmailIsSystemResource, LdapConstants.LDAP_TRUE);
            StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zmailIsSystemAccount, LdapConstants.LDAP_TRUE);
            StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zmailHideInGal, LdapConstants.LDAP_TRUE);
            StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zmailContactMaxNumEntries, "0");
            StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zmailMailHost, mailHost);
            checkSetAttrsOnCreate(zsc, TargetType.account, acctValue, accountAttrs);
            account = prov.createAccount(acctValue, password, accountAttrs);
        }

        if (!Provisioning.onLocalServer(account)) {
            String host = account.getMailHost();
            Server server = prov.getServerByName(host);
            return proxyRequest(request, context, server);
        }
        addDataSource(request, zsc, account, domain, folder, name, type);

        Element response = zsc.createElement(AdminConstants.CREATE_GAL_SYNC_ACCOUNT_RESPONSE);
        ToXML.encodeAccount(response, account, false, emptySet, null);
        return response;
    }

    static void addDataSource(Element request, ZmailSoapContext zsc, Account account,
            Domain domain, String folder, String name, GalMode type)  throws ServiceException {
        String acctName = account.getName();
        String acctId = account.getId();
        HashSet<String> galAcctIds = new HashSet<String>();
        galAcctIds.addAll(Arrays.asList(domain.getGalAccountId()));
        if (!galAcctIds.contains(acctId)) {
            galAcctIds.add(acctId);
            domain.setGalAccountId(galAcctIds.toArray(new String[0]));
        }

        // create folder if not already exists.
        if (folder == null) {
            folder = "/Contacts";
        } else if (folder.length() > 0 && folder.charAt(0) != '/') {
            folder = "/" + folder;
        }
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
        Folder contactFolder = null;
        try {
            contactFolder = mbox.getFolderByPath(null, folder);
        } catch (MailServiceException.NoSuchItemException e) {
            contactFolder = mbox.createFolder(null, folder, new Folder.FolderOptions().setDefaultView(MailItem.Type.CONTACT));
        }

        int folderId = contactFolder.getId();

        // check if there is another datasource already that maps to the same contact folder.
        for (DataSource ds : account.getAllDataSources()) {
            if (ds.getFolderId() == folderId) {
                throw MailServiceException.ALREADY_EXISTS("data source " + ds.getName() + " already contains folder " + folder);
            }
        }


        mbox.grantAccess(null, folderId, domain.getId(), ACL.GRANTEE_DOMAIN, ACL.stringToRights("r"), null);

        // create datasource
        Map<String,Object> attrs = AdminService.getAttrs(request, true);
        try {
            attrs.put(Provisioning.A_zmailGalType, type.name());
            attrs.put(Provisioning.A_zmailDataSourceFolderId, "" + folderId);
            if (!attrs.containsKey(Provisioning.A_zmailDataSourceEnabled)) {
                attrs.put(Provisioning.A_zmailDataSourceEnabled, LdapConstants.LDAP_TRUE);
            }
            if (!attrs.containsKey(Provisioning.A_zmailGalStatus)) {
                attrs.put(Provisioning.A_zmailGalStatus, "enabled");
            }
            Provisioning.getInstance().createDataSource(account, DataSourceType.gal, name, attrs);
        } catch (ServiceException e) {
            ZmailLog.gal.error("error creating datasource for GalSyncAccount", e);
            throw e;
        }

        ZmailLog.security.info(ZmailLog.encodeAttrs(new String[] {"cmd", "CreateGalSyncAccount", "name", acctName} ));
    }

    private static final Set<String> emptySet = Collections.emptySet();

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        // XXX revisit
        relatedRights.add(Admin.R_createAccount);
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY,
                Admin.R_modifyAccount.getName(), "account"));
    }
}
