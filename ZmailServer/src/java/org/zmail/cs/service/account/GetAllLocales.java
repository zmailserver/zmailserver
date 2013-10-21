/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import java.util.Locale;
import java.util.Map;

import org.zmail.common.util.L10nUtil;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.common.soap.AccountConstants;

public class GetAllLocales extends AccountDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Locale locales[] = L10nUtil.getAllLocalesSorted();
        Element response = zsc.createElement(AccountConstants.GET_ALL_LOCALES_RESPONSE);
        for (Locale locale : locales)
            ToXML.encodeLocale(response, locale, Locale.US);
        return response;
    }
}
