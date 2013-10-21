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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;

import org.dom4j.DocumentHelper;

import org.zmail.common.soap.DomUtil;
import org.zmail.cs.client.*;

public class LmcCreateTagRequest extends LmcSoapRequest {

    private String mName;
    private String mColor;
    

    public void setName(String n) { mName = n; }
    public void setColor(String c) { mColor = c; }

    public String getName() { return mName; }
    public String getColor() { return mColor; }


    protected Element getRequestXML() {
        Element request = DocumentHelper.createElement(MailConstants.CREATE_TAG_REQUEST);
        Element t = DomUtil.add(request, MailConstants.E_TAG, "");
        DomUtil.addAttr(t, MailConstants.A_NAME, mName);
        DomUtil.addAttr(t, MailConstants.A_COLOR, mColor);
        return request;
    }

    protected LmcSoapResponse parseResponseXML(Element responseXML) 
        throws ServiceException
    {
        Element tagElem = DomUtil.get(responseXML, MailConstants.E_TAG);
        LmcTag f = parseTag(tagElem);
        LmcCreateTagResponse response = new LmcCreateTagResponse();
        response.setTag(f);
        return response;
    }

}
