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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.SignatureUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Signature;
import org.zmail.cs.account.Provisioning;
import org.zmail.soap.DocumentHandler;

import org.zmail.soap.ZmailSoapContext;

public class CreateSignature extends DocumentHandler {
    public Element handle(Element request, Map<String, Object> context) throws ServiceException, SoapFaultException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);
        
        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");
        
        Element eReqSignature = request.getElement(AccountConstants.E_SIGNATURE);
        String name = eReqSignature.getAttribute(AccountConstants.A_NAME);
        String id = eReqSignature.getAttribute(AccountConstants.A_ID, null);
        
        List<Element> contents = eReqSignature.listElements(AccountConstants.E_CONTENT);
        Map<String,Object> attrs = new HashMap<String, Object>();
        for (Element eContent : contents) {
            String type = eContent.getAttribute(AccountConstants.A_TYPE);
            String attr = SignatureUtil.mimeTypeToAttrName(type);
            if (attr == null)
                throw ServiceException.INVALID_REQUEST("invalid type "+type, null);
            if (attrs.get(attr) != null)
                throw ServiceException.INVALID_REQUEST("only one "+type+" content is allowed", null);
            
            String content = eContent.getText();
            if (!StringUtil.isNullOrEmpty(content))
                attrs.put(attr, content);
        }
        
        if (id != null)
            attrs.put(Provisioning.A_zmailSignatureId, id);
        
        Element eContactId = eReqSignature.getOptionalElement(AccountConstants.E_CONTACT_ID);
        if (eContactId != null)
            attrs.put(Provisioning.A_zmailPrefMailSignatureContactId, eContactId.getText());
        
        Signature signature = Provisioning.getInstance().createSignature(account, name, attrs);
        
        Element response = zsc.createElement(AccountConstants.CREATE_SIGNATURE_RESPONSE);
        Element eRespSignature = response.addElement(AccountConstants.E_SIGNATURE);
        eRespSignature.addAttribute(AccountConstants.A_ID, signature.getId());
        eRespSignature.addAttribute(AccountConstants.A_NAME, signature.getName());
        return response;
    }
}

