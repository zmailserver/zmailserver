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
import org.zmail.cs.session.Session;
import org.zmail.soap.SoapServlet;
import org.zmail.soap.ZmailSoapContext;

/**
 * End the current session immediately cleaning up all resources used by the session
 * including the notification buffer and logging the session out from IM if applicable
 */
public class EndSession extends AccountDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        if (zsc.hasSession()) {
            Session s = getSession(zsc);
            endSession(s);
        }
        if (getAuthenticatedAccount(zsc).isForceClearCookies()) {
            context.put(SoapServlet.INVALIDATE_COOKIES, true);
        }
        Element response = zsc.createElement(AccountConstants.END_SESSION_RESPONSE);
        return response;
    }
}
