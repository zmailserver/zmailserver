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
package org.zmail.cs.service.mail;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.zmail.common.soap.SoapProtocol;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.service.MockHttpServletRequest;
import org.zmail.soap.DocumentService;
import org.zmail.soap.MockSoapEngine;
import org.zmail.soap.SoapEngine;
import org.zmail.soap.SoapServlet;
import org.zmail.soap.ZmailSoapContext;

public class ServiceTestUtil {

    public static Map<String, Object> getRequestContext(Account acct) throws Exception {
        return getRequestContext(acct, acct);
    }

    public static Map<String, Object> getExternalRequestContext(String externalEmail, Account targetAcct) throws Exception {
        return getRequestContext(new GuestAccount(externalEmail, "password"), targetAcct);
    }

    public static Map<String, Object> getRequestContext(Account authAcct, Account targetAcct) throws Exception {
        return getRequestContext(authAcct, targetAcct, new MailService());
    }

    public static Map<String, Object> getRequestContext(Account authAcct, Account targetAcct, DocumentService service) throws Exception {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(SoapEngine.ZIMBRA_CONTEXT, new ZmailSoapContext(AuthProvider.getAuthToken(authAcct), targetAcct.getId(), SoapProtocol.Soap12, SoapProtocol.Soap12));
        context.put(SoapServlet.SERVLET_REQUEST, new MockHttpServletRequest("test".getBytes("UTF-8"), new URL("http://localhost:7070/service/FooRequest"), ""));
        context.put(SoapEngine.ZIMBRA_ENGINE, new MockSoapEngine(service));
        return context;
    }

}
