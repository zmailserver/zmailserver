/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.account.soap;

import org.zmail.common.account.SignatureUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.common.zclient.ZClientException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Signature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SoapSignature extends Signature implements SoapEntry {
    
    SoapSignature(Account acct, String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(acct, name, id, attrs, prov);
    }

    SoapSignature(Account acct, Element e, Provisioning prov) throws ServiceException {
        super(acct, e.getAttribute(AccountConstants.A_NAME), e.getAttribute(AccountConstants.A_ID), fromXML(e), prov);
    }
    
    public void modifyAttrs(SoapProvisioning prov, Map<String, ? extends Object> attrs, boolean checkImmutable) throws ServiceException {
    }

    public void reload(SoapProvisioning prov) throws ServiceException {
    }
    
    public static void toXML(Element signature, Map<String, Object> attrs) throws ServiceException {
        for (Map.Entry entry : attrs.entrySet()) {
            String attr = (String)entry.getKey();
            String value = (String)entry.getValue();
            
            if (attr.equals(Provisioning.A_zmailSignatureId) && !StringUtil.isNullOrEmpty(value))
                signature.addAttribute(AccountConstants.A_ID, value);
            else if (attr.equals(Provisioning.A_zmailSignatureName) && !StringUtil.isNullOrEmpty(value))
                signature.addAttribute(AccountConstants.A_NAME, value);
            else if (attr.equals(Provisioning.A_zmailPrefMailSignatureContactId) && !StringUtil.isNullOrEmpty(value))
                signature.addElement(AccountConstants.E_CONTACT_ID).setText(value);
            else {
                String mimeType = SignatureUtil.attrNameToMimeType(attr);
                if (mimeType == null)
                    throw ZClientException.CLIENT_ERROR("unable to determine mime type from attr " + attr, null);
                
                signature.addElement(AccountConstants.E_CONTENT).addAttribute(AccountConstants.A_TYPE, mimeType).addText(value);
            }
        }
    }
    
    private static Map<String, Object> fromXML(Element signature) throws ServiceException {
        List<Element> contents = signature.listElements(AccountConstants.E_CONTENT);
        Map<String,Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailSignatureId, signature.getAttribute(AccountConstants.A_ID));
        attrs.put(Provisioning.A_zmailSignatureName, signature.getAttribute(AccountConstants.A_NAME));
        
        Element eContactId = signature.getOptionalElement(AccountConstants.E_CONTACT_ID);
        if (eContactId != null)
            attrs.put(Provisioning.A_zmailPrefMailSignatureContactId, eContactId.getText());
        
        for (Element eContent : contents) {
            String type = eContent.getAttribute(AccountConstants.A_TYPE);
            String attr = SignatureUtil.mimeTypeToAttrName(type);
            if (attr != null) {
                attrs.put(attr, eContent.getText());
            }
        }

        return attrs;
    }
    
    public Account getAccount() throws ServiceException {
        throw ServiceException.INVALID_REQUEST("unsupported, use getAccount(Provisioning)", null);
    }
}
