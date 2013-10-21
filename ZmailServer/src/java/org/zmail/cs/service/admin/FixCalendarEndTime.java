/* ***** BEGIN LICENSE BLOCK *****
/* Zimbra Collaboration Suite Server
/* Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
/* 
/* The contents of this file are subject to the Zimbra Public License
/* Version 1.3 ("License"); you may not use this file except in
/* compliance with the License.  You may obtain a copy of the License at
/* http://www.zimbra.com/license.
/* 
/* Software distributed under the License is distributed on an "AS IS"
/* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.cs.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.SearchAccountsOptions;
import org.zmail.cs.account.Server;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.SearchDirectoryOptions.SortOpt;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.soap.ZmailSoapContext;

public class FixCalendarEndTime extends AdminDocumentHandler {

    public static final String ALL = "all";

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        // what to check for this SOAP?
        // allow only system admin for now
        checkRight(zsc, context, null, AdminRight.PR_SYSTEM_ADMIN_ONLY);
        
        boolean sync = request.getAttributeBool(AdminConstants.A_TZFIXUP_SYNC, false);
        List<Element> acctElems = request.listElements(AdminConstants.E_ACCOUNT);
        List<String> acctNames = parseAccountNames(acctElems);
        if (acctNames.isEmpty())
            throw ServiceException.INVALID_REQUEST("Accounts must be specified", null);
        if (sync) {
            fixAccounts(acctNames);
        } else {
            CalendarEndTimeFixupThread thread =
                new CalendarEndTimeFixupThread(acctNames);
            thread.start();
        }

        Element response = zsc.createElement(AdminConstants.FIX_CALENDAR_END_TIME_RESPONSE);
        return response;
    }

    protected List<String> parseAccountNames(List<Element> acctElems) throws ServiceException {
        List<String> a = new ArrayList<String>(acctElems.size());
        for (Element elem : acctElems) {
            String name = elem.getAttribute(AdminConstants.A_NAME);
            if (ALL.equals(name)) {
                List<String> all = new ArrayList<String>(1);
                all.add(ALL);
                return all;
            } else {
                String[] parts = name.split("@");
                if (parts.length != 2)
                    throw ServiceException.INVALID_REQUEST("invalid account email address: " + name, null);
            }
            a.add(name);
        }
        return a;
    }

    private static List<NamedEntry> getAccountsOnServer() throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        Server server = prov.getLocalServer();
        String serverName = server.getAttr(Provisioning.A_zmailServiceHostname);
        
        SearchAccountsOptions searchOpts = 
            new SearchAccountsOptions(new String[] { Provisioning.A_zmailId });
        searchOpts.setSortOpt(SortOpt.SORT_DESCENDING);
        
        List<NamedEntry> accts = prov.searchAccountsOnServer(server, searchOpts);

        ZmailLog.calendar.info("Found " + accts.size() + " accounts on server " + serverName);
        return accts;
    }

    private static void fixAccounts(List<String> acctNames)
    throws ServiceException {
        int numAccts = acctNames.size();
        boolean all = (numAccts == 1 && ALL.equals(acctNames.get(0)));
        int numFixedAccts = 0;
        int numFixedAppts = 0;
        List<NamedEntry> accts;
        if (all) {
            accts = getAccountsOnServer();
        } else {
            accts = new ArrayList<NamedEntry>(acctNames.size());
            for (String name : acctNames) {
                try {
                    accts.add(Provisioning.getInstance().get(AccountBy.name, name));
                } catch (ServiceException e) {
                    ZmailLog.calendar.error(
                            "Error looking up account " + name + ": " + e.getMessage(), e);
                }
            }
        }
        numAccts = accts.size();
        int every = 10;
        for (NamedEntry entry : accts) {
            if (!(entry instanceof Account))
                continue;
            Account acct = (Account) entry;
            Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);
            try {
                numFixedAppts += mbox.fixAllCalendarItemEndTime(null);
            } catch (ServiceException e) {
                ZmailLog.calendar.error(
                        "Error fixing calendar item end times in mailbox " + mbox.getId() +
                        ": " + e.getMessage(), e);
            }
            numFixedAccts++;
            if (numFixedAccts % every == 0) {
                ZmailLog.calendar.info(
                        "Progress: fixed calendar item end times in " + numFixedAccts + "/" +
                        numAccts + " accounts");
            }
        }
        ZmailLog.calendar.info(
                "Fixed end times in total " + numFixedAppts + " calendar items in " + numFixedAccts + " accounts");
    }

    private static class CalendarEndTimeFixupThread extends Thread {
        private List<String> mAcctNames;

        public CalendarEndTimeFixupThread(List<String> acctNames) {
            setName("CalendarEndTimeFixupThread");
            mAcctNames = acctNames;
        }

        public void run() {
            try {
                fixAccounts(mAcctNames);
            } catch (ServiceException e) {
                ZmailLog.calendar.error(
                        "Error while fixing up calendar end times: " + e.getMessage(), e);
            }
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
    }
}
