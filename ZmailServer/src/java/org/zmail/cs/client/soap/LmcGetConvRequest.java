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

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

import org.zmail.common.soap.DomUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.client.*;


public class LmcGetConvRequest extends LmcSoapRequest {

    private String mConvID;
    private String mMsgsToGet[];
    
    // There is a single conversation to get.  Must be present.
    public void setConvToGet(String f) { mConvID = f; }

    // Set the ID's of the msgs within the conversation to get.  Optional.
    public void setMsgsToGet(String m[]) { mMsgsToGet = m; }
    
    public String getConvToGet() { return mConvID; }

    public String[] getMsgsToGet() { return mMsgsToGet; }


    protected Element getRequestXML() {
        Element request = DocumentHelper.createElement(MailConstants.GET_CONV_REQUEST);

        // set the ID of the conversation to get
        Element convElement = DomUtil.add(request, MailConstants.E_CONV, "");
        DomUtil.addAttr(convElement, MailConstants.A_ID, mConvID);

        // add message elements within the conversation element if desired
        if (mMsgsToGet != null) {
            for (int i = 0; i < mMsgsToGet.length; i++) {
                Element m = DomUtil.add(convElement, MailConstants.E_MSG, "");
                DomUtil.addAttr(m, MailConstants.A_ID, mMsgsToGet[i]);
            }
        }

        return request;
    }

    protected LmcSoapResponse parseResponseXML(Element responseXML) 
        throws ServiceException, LmcSoapClientException
    {
        // the response will always be exactly one conversation
        LmcConversation c = parseConversation(DomUtil.get(responseXML, MailConstants.E_CONV));
        LmcGetConvResponse response = new LmcGetConvResponse();
        response.setConv(c);
        return response;
    }

}
