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

import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.ContactAutoComplete.AutoCompleteResult;
import org.zmail.cs.mailbox.ContactAutoComplete.ContactEntry;
import org.zmail.soap.ZmailSoapContext;

public class OfflineGalContactAutoComplete extends ContactAutoComplete {

    public OfflineGalContactAutoComplete(Account acct, OperationContext octxt) {
        super(acct, octxt);
    }

    public OfflineGalContactAutoComplete(Account acct, ZmailSoapContext zsc,
                    OperationContext octxt) {
        super(acct, zsc, octxt);
    }

    @Override
    protected void addEntry(ContactEntry entry, AutoCompleteResult result) {
        if (entry.isGroup() && result.entries.contains(entry)) {
            //duplicate non-group added; addEntry rejects duplicates so we need to manually set the flag
            //this occurs because GAL search in ZD uses mailbox search; there can be multiple entries for one addr 
            //for example VMware GAL has server-team@zmail.com as type=account and Zmail GAL has server-team@zmail.com as type=group
            for (ContactEntry exist : result.entries) {
                if (entry.getKey().equals(exist.getKey()) && !exist.isGroup()) {
                    exist.setIsGalGroup(true);
                }
            }
        } else {
            result.addEntry(entry);
        }
    }
}
