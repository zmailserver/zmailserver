/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.offline;

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.util.ZmailApplication;
import org.zmail.soap.DocumentHandler;


public class OfflineGetExtensions extends DocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        List<String> extensionNames = ZmailApplication.getInstance().getExtensionNames();
        Element response = getZmailSoapContext(context).createElement(OfflineConstants.GET_EXTENSIONS_RESPONSE);
        if (extensionNames != null) {
            for (String ext : extensionNames)
                response.addElement(OfflineConstants.EXTENSION).addAttribute(OfflineConstants.EXTENSION_NAME, ext);
        }
        return response;
    }

    @Override
    public boolean needsAuth(Map<String, Object> context) {
        return false;
    }

    @Override
    public boolean needsAdminAuth(Map<String, Object> context) {
        return false;
    }
}
