/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.service.account;

import java.util.Map;

import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.IdentityBy;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.Identity;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.soap.DocumentHandler;
import com.zimbra.common.soap.SoapFaultException;
import com.zimbra.soap.ZimbraSoapContext;

public class DeleteIdentity extends DocumentHandler {
	
    public Element handle(Element request, Map<String, Object> context) throws ServiceException, SoapFaultException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Account account = getRequestedAccount(zsc);
        
        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");
        
        Provisioning prov = Provisioning.getInstance();

        Element eIdentity = request.getElement(AccountConstants.E_IDENTITY);

        // identity can be specified by name or by ID
        Identity ident = null;
        String idStr = eIdentity.getAttribute(AccountConstants.A_ID, null);
        if (idStr != null) {
            ident = prov.get(account, Key.IdentityBy.id, idStr);
        } else {
            idStr = eIdentity.getAttribute(AccountConstants.A_NAME);
            ident = prov.get(account, Key.IdentityBy.name, idStr);
        }
        
        if (ident != null)
            Provisioning.getInstance().deleteIdentity(account, ident.getName());
        else
            throw AccountServiceException.NO_SUCH_IDENTITY(idStr);

        Element response = zsc.createElement(AccountConstants.DELETE_IDENTITY_RESPONSE);
        return response;
    }
}