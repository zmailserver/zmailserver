/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.taglib.tag.i18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.cs.taglib.ZJspSession;
import org.zmail.cs.taglib.tag.ZmailSimpleTag;


public class GetValidLocaleTag extends ZmailSimpleTag {


	private String mVar;
    private String mLocale;
	
    public void setVar(String var) { this.mVar = var; }
	public void setLocale(String locale) { this.mLocale = locale; }


    // simple tag methods

    public void doTag() throws JspException, IOException {
        JspContext ctxt = getJspContext();
        if (this.mLocale == null) {
            ctxt.setAttribute(mVar, false,  PageContext.REQUEST_SCOPE);
            return;
        }
        ZAuthToken authToken = ZJspSession.getAuthToken((PageContext)ctxt);
        String soapUri = ZJspSession.getSoapURL((PageContext)ctxt);
        SoapHttpTransport transport = null;
        try {
        	transport = new SoapHttpTransport(soapUri);
     		transport.setAuthToken(authToken);
        	XMLElement req = new XMLElement(AccountConstants.GET_AVAILABLE_LOCALES_REQUEST);
            Element resp = transport.invokeWithoutSession(req);
            List<String> locales = new ArrayList<String>();
            for (Element locale : resp.listElements(AccountConstants.E_LOCALE)) {
                String id = locale.getAttribute(AccountConstants.A_ID, null);
                if (id != null)
                	locales.add(id);
            }
            Collections.sort(locales);
            boolean isValid = false;
            for(String s : locales) {
                if (this.mLocale.toLowerCase().startsWith(s.toLowerCase())) {
                    isValid = true;
                    break;
                }
            }
            ctxt.setAttribute(mVar, isValid,  PageContext.REQUEST_SCOPE);
        }
        catch(ServiceException e) {
            throw new JspTagException(e.getMessage(), e);   
        } finally {
            if (transport != null)
                transport.shutdown();
        }
    }

}

