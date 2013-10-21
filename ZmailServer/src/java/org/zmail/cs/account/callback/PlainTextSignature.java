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

package org.zmail.cs.account.callback;

import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.SignatureUtil;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Identity;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Signature;

/**
 * Callback to ensure a signature being used for auto-accept/decline has non-empty text/plain content.
 */
public class PlainTextSignature extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object value, 
            Map attrsToModify, Entry entry)
    throws ServiceException {
        SingleValueMod mod = singleValueMod(attrName, value);
        if (mod.unsetting())
            return;

        Account account;
        if (entry instanceof Account) {
            account = (Account) entry;
        } else if (entry instanceof Identity) {
            account = ((Identity) entry).getAccount();
        } else if (entry instanceof DataSource) {
            account = ((DataSource) entry).getAccount();
        } else {
            return;
        }
        
        Signature sig = Provisioning.getInstance().get(account, Key.SignatureBy.id, mod.value());
        if (sig == null) {
            throw ServiceException.INVALID_REQUEST("No such signature " + mod.value() + 
                    " for account " + account.getName(), null);
        }
        String sigAttr = SignatureUtil.mimeTypeToAttrName(MimeConstants.CT_TEXT_PLAIN);
        String plainSig = sig.getAttr(sigAttr, null);
        if (StringUtil.isNullOrEmpty(plainSig)) {
            throw ServiceException.INVALID_REQUEST("Signature " + mod.value() + 
                    " must have plain text content", null);
        }
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}
