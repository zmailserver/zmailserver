/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.SignatureBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Signature;
import org.zmail.soap.DocumentHandler;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.soap.ZmailSoapContext;

public class DeleteSignature extends DocumentHandler {
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException, SoapFaultException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);
        
        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");
        
        Provisioning prov = Provisioning.getInstance();

        Element eSignature = request.getElement(AccountConstants.E_SIGNATURE);

        // signature can be specified by name or by ID
        Signature signature = null;
        String sigStr = eSignature.getAttribute(AccountConstants.A_ID, null);
        if (sigStr != null) {
            signature = prov.get(account, Key.SignatureBy.id, sigStr);
        } else {
            sigStr = eSignature.getAttribute(AccountConstants.A_NAME);
            signature = prov.get(account, Key.SignatureBy.name, sigStr);
        }
        
        if (signature != null)
            Provisioning.getInstance().deleteSignature(account, signature.getId());
        else
            throw AccountServiceException.NO_SUCH_SIGNATURE(sigStr);

        Element response = zsc.createElement(AccountConstants.DELETE_SIGNATURE_RESPONSE);
        return response;
    }
}