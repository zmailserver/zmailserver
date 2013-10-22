/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.soap.mail;

import com.sun.xml.ws.developer.WSBindingProvider;
import com.zimbra.soap.Utility;
import generated.zcsclient.mail.testExportContactsRequest;
import generated.zcsclient.mail.testExportContactsResponse;
import generated.zcsclient.ws.service.ZcsPortType;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WSDLExportContactsTest {

    private static ZcsPortType mailSvcEIF;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @BeforeClass
    public static void init() throws Exception {
        mailSvcEIF = Utility.getZcsSvcEIF();
    }

    /**
     * Current assumption : user1 exists with password test123
     */
    @Test
    public void defaultZimbraFmt() throws Exception {
       testExportContactsRequest req = new testExportContactsRequest();
       req.setCt("csv");
       Utility.addSoapAcctAuthHeader((WSBindingProvider)mailSvcEIF);
       testExportContactsResponse resp = mailSvcEIF.exportContactsRequest(req);
       Assert.assertNotNull("ExportContactsResponse object", resp);
       String content = resp.getContent();
       Assert.assertNotNull("<content> contents", content);
       String firstPart = content.substring(0, 17);
       Assert.assertEquals("First part of <content> ", "\"assistantPhone\",", firstPart);
    }
    
    @Test
    public void winLiveFrenchSemicolon() throws Exception {
       testExportContactsRequest req = new testExportContactsRequest();
       req.setCt("csv");
       req.setCsvfmt("windows-live-mail-csv");
       req.setCsvlocale("fr");
       req.setCsvsep(";");
       Utility.addSoapAcctAuthHeader((WSBindingProvider)mailSvcEIF);
       testExportContactsResponse resp = mailSvcEIF.exportContactsRequest(req);
       Assert.assertNotNull("ExportContactsResponse object", resp);
       String content = resp.getContent();
       Assert.assertNotNull("<content> contents", content);
       String firstPart = content.substring(0, 15);
       Assert.assertEquals("First part of <content> ", "\"Pr�nom\";\"Nom\";", firstPart);
    }
}
