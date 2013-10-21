/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.util.BuildInfo;
import org.zmail.soap.ZmailSoapContext;


public class GetVersionInfo extends AccountDocumentHandler {

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        if (!Provisioning.getInstance().getLocalServer().getBooleanAttr(Provisioning.A_zmailSoapExposeVersion, false)) {
            throw ServiceException.PERM_DENIED("Version info is not available.");
        }
        ZmailSoapContext lc = getZmailSoapContext(context);

        Element response = lc.createElement(AccountConstants.GET_VERSION_INFO_RESPONSE);
        Element infoEl = response.addElement(AccountConstants.E_VERSION_INFO_INFO);
        
        String fullVersionInfo = BuildInfo.VERSION;
        if (!StringUtil.isNullOrEmpty(BuildInfo.TYPE))
            fullVersionInfo = fullVersionInfo + "." + BuildInfo.TYPE;
            
        infoEl.addAttribute(AccountConstants.A_VERSION_INFO_VERSION, fullVersionInfo);
        infoEl.addAttribute(AccountConstants.A_VERSION_INFO_RELEASE, BuildInfo.RELEASE);
        infoEl.addAttribute(AccountConstants.A_VERSION_INFO_DATE, BuildInfo.DATE);
        infoEl.addAttribute(AccountConstants.A_VERSION_INFO_HOST, BuildInfo.HOST);
        return response;
    }

    public boolean needsAdminAuth(Map<String, Object> context) {
        return false;
    }

    public boolean needsAuth(Map<String, Object> context) {
        return false;
    }
}
