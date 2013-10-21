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

import java.util.HashMap;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.soap.ZmailSoapContext;

public class ModifyWhiteBlackList extends AccountDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");

        HashMap<String, Object> addrs = new HashMap<String, Object>();
        
        doList(request.getOptionalElement(AccountConstants.E_WHITE_LIST),
               Provisioning.A_amavisWhitelistSender,
               addrs);
        
        doList(request.getOptionalElement(AccountConstants.E_BLACK_LIST),
                Provisioning.A_amavisBlacklistSender,
                addrs);

        // call modifyAttrs and pass true to checkImmutable
        Provisioning.getInstance().modifyAttrs(account, addrs, true, zsc.getAuthToken());

        Element response = zsc.createElement(AccountConstants.MODIFY_WHITE_BLACK_LIST_RESPONSE);
        return response;
    }
    
    private void doList(Element eList, String attrName, HashMap<String, Object> addrs) {
        if (eList == null)
            return;
        
        // empty list, means delete all
        if (eList.getOptionalElement(AccountConstants.E_ADDR) == null) {
            StringUtil.addToMultiMap(addrs, attrName, "");
            return;
        }
        
        for (Element eAddr : eList.listElements(AccountConstants.E_ADDR)) {
            String attr = eAddr.getAttribute(AccountConstants.A_OP, "") + attrName;
            String value = eAddr.getText();
            StringUtil.addToMultiMap(addrs, attr, value);
        }
    }

}

