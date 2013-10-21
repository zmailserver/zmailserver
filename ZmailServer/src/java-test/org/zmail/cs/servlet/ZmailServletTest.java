/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.servlet;

import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

import org.zmail.cs.account.Provisioning;
import org.zmail.cs.service.MockHttpServletRequest;
import org.zmail.cs.service.MockHttpServletResponse;
import org.zmail.cs.servlet.ZmailServlet;

public class ZmailServletTest {
    
    private static String uri = "/Briefcase/上的发生的发";

    @Ignore("until bug 60345 is fixed")
    @Test
    public void proxyTest() throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest("test".getBytes("UTF-8"), new URL("http://localhost:7070/user1"+uri), "");
        MockHttpServletResponse resp = new MockHttpServletResponse();
        ZmailServlet.proxyServletRequest(req, resp, Provisioning.getInstance().getLocalServer(), uri, null);
    }
}
