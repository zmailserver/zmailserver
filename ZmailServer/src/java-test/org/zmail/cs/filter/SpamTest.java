/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.filter;

import javax.mail.internet.MimeMessage;
import javax.mail.util.SharedByteArrayInputStream;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.service.util.SpamHandler;
import org.zmail.cs.util.JMSession;

/**
 * Unit tests for spam/whitelist filtering
 */
public class SpamTest {

    @BeforeClass
    public static void init() throws ServiceException {
        MockProvisioning prov = new MockProvisioning();
        Provisioning.setInstance(prov);
        Config config = prov.getConfig();
        config.setSpamWhitelistHeader("X-Whitelist-Flag");
        config.setSpamWhitelistHeaderValue("YES");
    }

    /**
     * Tests whitelisting takes precedence over marking spam.
     */
    @Test
    public void whitelist() throws Exception {
        String raw = "From: sender@zmail.com\n" +
                "To: recipient@zmail.com\n" +
                "X-Spam-Flag: YES\n" +
                "Subject: test\n" +
                "\n" +
                "Hello World.";
        MimeMessage msg = new Mime.FixedMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(raw.getBytes()));
        Assert.assertTrue(SpamHandler.isSpam(msg));

        // add a whitelist header to the previous message
        raw = "From: sender@zmail.com\n" +
                "To: recipient@zmail.com\n" +
                "X-Whitelist-Flag: YES\n" +
                "X-Spam-Flag: YES\n" +
                "Subject: test\n" +
                "\n" +
                "Hello World.";
        msg = new Mime.FixedMimeMessage(JMSession.getSession(), new SharedByteArrayInputStream(raw.getBytes()));
        Assert.assertFalse(SpamHandler.isSpam(msg));
    }
}
