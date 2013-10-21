/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.account;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Identity;
import org.zmail.cs.account.Provisioning;
import org.zmail.soap.DocumentHandler;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class CreateIdentity extends DocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account)) {
            throw ServiceException.PERM_DENIED("can not modify options");
        }

        Element identityEl = request.getElement(AccountConstants.E_IDENTITY);
        String name = identityEl.getAttribute(AccountConstants.A_NAME);
        Map<String, Object> attrs = AccountService.getAttrs(
                identityEl, true, AccountConstants.A_NAME);
        Identity identity = Provisioning.getInstance().createIdentity(account, name, attrs);

        Element response = zsc.createElement(AccountConstants.CREATE_IDENTITY_RESPONSE);
        ToXML.encodeIdentity(response, identity);
        return response;
    }
}
