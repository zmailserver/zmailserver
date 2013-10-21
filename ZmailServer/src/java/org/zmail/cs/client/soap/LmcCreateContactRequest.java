/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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


package org.zmail.cs.client.soap;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapParseException;
import org.zmail.common.soap.DomUtil;
import org.zmail.cs.client.*;

public class LmcCreateContactRequest extends LmcSoapRequest {

	private LmcContact mContact;
    
    /**
     * This method only sends the parameters from contact that the SOAP
     * protocol will accept.  That means folder ID, tags, and attributes.
     * Flags are currently ignored.
     * @param c - contact to create
     */
    public void setContact(LmcContact c) { mContact = c; }
    
    public LmcContact getContact() { return mContact; }
    
	protected Element getRequestXML() throws LmcSoapClientException {
		Element request = DocumentHelper.createElement(MailConstants.CREATE_CONTACT_REQUEST);
        Element newCN = DomUtil.add(request, MailConstants.E_CONTACT, "");
        LmcSoapRequest.addAttrNotNull(newCN, MailConstants.A_FOLDER, mContact.getFolder());
        LmcSoapRequest.addAttrNotNull(newCN, MailConstants.A_TAGS, mContact.getTags());
        
        // emit contact attributes if any
        LmcContactAttr attrs[] = mContact.getAttrs();
		for (int i = 0; attrs != null && i < attrs.length; i++)
			addContactAttr(newCN, attrs[i]);
		
        return request;
    }

	protected LmcSoapResponse parseResponseXML(Element responseXML)
			throws SoapParseException, ServiceException, LmcSoapClientException {
		
        LmcCreateContactResponse response = new LmcCreateContactResponse();
        LmcContact c = parseContact(DomUtil.get(responseXML, MailConstants.E_CONTACT));
        response.setContact(c);
        return response;
	}

}
