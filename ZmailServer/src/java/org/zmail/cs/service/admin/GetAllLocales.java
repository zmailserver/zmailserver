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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.L10nUtil;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.soap.ZmailSoapContext;

public class GetAllLocales extends AdminDocumentHandler {
    
    public boolean domainAuthSufficient(Map context) {
        return true;
    }
    
    public Element handle(Element request, Map<String, Object> context) {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Locale locales[] = L10nUtil.getAllLocalesSorted();
        Element response = zsc.createElement(AdminConstants.GET_ALL_LOCALES_RESPONSE);
        for (Locale locale : locales)
            org.zmail.cs.service.account.ToXML.encodeLocale(response, locale, Locale.US);
        return response;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("Allow all admins");
    }
}
