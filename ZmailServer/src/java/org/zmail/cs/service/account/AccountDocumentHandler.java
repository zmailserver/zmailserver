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
package org.zmail.cs.service.account;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.MailMode;
import org.zmail.cs.account.Server;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.SoapServlet;


public abstract class AccountDocumentHandler extends DocumentHandler {

    @Override
    protected Element proxyIfNecessary(Element request, Map<String, Object> context) throws ServiceException {
        try {
            // by default, try to execute on the appropriate host
            return super.proxyIfNecessary(request, context);
        } catch (ServiceException e) {
            // if something went wrong proxying the request, just execute it locally
            if (ServiceException.PROXY_ERROR.equals(e.getCode()))
                return null;
            // but if it's a real error, it's a real error
            throw e;
        }
    }
    
    /*
     * bug 27389
     */
    protected boolean checkPasswordSecurity(Map<String, Object> context) throws ServiceException {
        HttpServletRequest req = (HttpServletRequest)context.get(SoapServlet.SERVLET_REQUEST);
        boolean isHttps = req.getScheme().equals("https");
        if (isHttps)
            return true;
        
        // clear text
        Server server = Provisioning.getInstance().getLocalServer();
        String modeString = server.getAttr(Provisioning.A_zmailMailMode, null);
        if (modeString == null) {
            // not likely, but just log and let it through
            ZmailLog.soap.warn("missing " + Provisioning.A_zmailMailMode + 
                                " for checking password security, allowing the request");
            return true;
        }
            
        MailMode mailMode = Provisioning.MailMode.fromString(modeString);
        if (mailMode == MailMode.mixed && 
            !server.getBooleanAttr(Provisioning.A_zmailMailClearTextPasswordEnabled, true)) 
            return false;
        else
            return true;
    }
    
    protected Set<String> getReqAttrs(Element request, AttributeClass klass) throws ServiceException {
        String attrsStr = request.getAttribute(AccountConstants.A_ATTRS, null);
        if (attrsStr == null) {
            return null;
        }
        
        String[] attrs = attrsStr.split(",");

        Set<String> attrsOnEntry = AttributeManager.getInstance().getAllAttrsInClass(klass);
        Set<String> validAttrs = new HashSet<String>();

        for (String attr : attrs) {
            if (attrsOnEntry.contains(attr)) {
                validAttrs.add(attr);
            } else {
                throw ServiceException.INVALID_REQUEST("requested attribute " + attr + 
                        " is not on " + klass.name(), null);
            }
        }

        // check and throw if validAttrs is empty?
        // probably not, to be compatible with SearchDirectory

        return validAttrs;
    }
}
