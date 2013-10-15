/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.taglib;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OperationContext;

public class Contact extends ZmailTag {
    private static final long serialVersionUID = 4310265594871660074L;

    private String mContactId;
    private String mField;

    public void setId(String val) {
        mContactId = val;
    }

    public String getId() {
        return mContactId;
    }

    public void setField(String val) {
        mField = val;
    }

    public String getField() {
        return mField;
    }

    public String getContentStart(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        if (mContactId == null) {
            throw ZmailTagException.MISSING_ATTR("id");
        }
        if (mField == null) {
            throw ZmailTagException.MISSING_ATTR("field");
        }
        int cid = Integer.parseInt(mContactId);
        String id = acct.getId();
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(id);
        org.zmail.cs.mailbox.Contact con = mbox.getContactById(octxt, cid);
        Map fields = con.getFields();
        String val = (String)fields.get(mField);
        if (val == null) {
        	return "";
        }
        return val;
    }
}
