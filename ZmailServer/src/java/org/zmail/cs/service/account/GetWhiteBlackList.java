/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.soap.ZmailSoapContext;

public class GetWhiteBlackList extends AccountDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
         ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canAccessAccount(zsc, account))
            throw ServiceException.PERM_DENIED("can not access account");

        String[] senders = account.getMultiAttr(Provisioning.A_amavisWhitelistSender);
        
        Element response = zsc.createElement(AccountConstants.GET_WHITE_BLACK_LIST_RESPONSE);
        
        // white list
        senders = account.getMultiAttr(Provisioning.A_amavisWhitelistSender);
        doList(response, AccountConstants.E_WHITE_LIST, senders);
        
        // black list
        senders = account.getMultiAttr(Provisioning.A_amavisBlacklistSender);
        doList(response, AccountConstants.E_BLACK_LIST, senders);
        
        return response;
    }

    private void doList(Element response, String list, String[] senders) {
        Element eList = response.addElement(list);
        
        for (String sender : senders)
            eList.addElement(AccountConstants.E_ADDR).setText(sender);
    }
}
