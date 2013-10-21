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
package org.zmail.cs.util;

import java.util.HashMap;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.mail.smtp.SMTPMessage;
import org.zmail.common.account.ZAttrProvisioning.ShareNotificationMtaConnectionType;
import org.zmail.common.mime.shim.JavaMailInternetAddress;
import org.zmail.common.util.Log.Level;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.mailclient.smtp.SmtpTransport;
import org.zmail.cs.mailclient.smtp.SmtpsTransport;

/**
 * Unit test for {@link JMSession}.
 *
 * @author ysasaki
 */
public class JMSessionTest {

    @BeforeClass
    public static void init() throws Exception {
        MockProvisioning prov = new MockProvisioning();
        prov.getLocalServer().setSmtpPort(25);
        Provisioning.setInstance(prov);
    }

    @Test
    public void getTransport() throws Exception {
        Assert.assertSame(SmtpTransport.class,
                JMSession.getSession().getTransport("smtp").getClass());
        Assert.assertSame(SmtpsTransport.class,
                JMSession.getSession().getTransport("smtps").getClass());

        Assert.assertSame(SmtpTransport.class,
                JMSession.getSmtpSession().getTransport("smtp").getClass());
        Assert.assertSame(SmtpsTransport.class,
                JMSession.getSmtpSession().getTransport("smtps").getClass());
    }

    //@Test
    public void testRelayMta() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        Server server = prov.getLocalServer();
        server.setShareNotificationMtaHostname("mta02.zmail.com");
        server.setShareNotificationMtaPort(25);
        server.setShareNotificationMtaAuthRequired(true);
        server.setShareNotificationMtaConnectionType(ShareNotificationMtaConnectionType.STARTTLS);
        server.setShareNotificationMtaAuthAccount("test-jylee");
        server.setShareNotificationMtaAuthPassword("test123");

        SMTPMessage out = new SMTPMessage(JMSession.getRelaySession());
        InternetAddress address = new JavaMailInternetAddress("test-jylee@zmail.com");
        out.setFrom(address);

        address = new JavaMailInternetAddress("test-jylee@zmail.com");
        out.setRecipient(javax.mail.Message.RecipientType.TO, address);

        out.setSubject("test mail");
        out.setText("hello world");

        out.saveChanges();
        ZmailLog.smtp.setLevel(Level.trace);
        Transport.send(out);
    }

    @Test
    public void messageID() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        Domain domain = prov.createDomain("example.com", new HashMap<String, Object>());
        Account account = prov.createAccount("user1@example.com", "test123", new HashMap<String, Object>());

        MimeMessage mm = new MimeMessage(JMSession.getSmtpSession(account));
        mm.saveChanges();
        Assert.assertEquals("message ID contains account domain", domain.getName() + '>', mm.getMessageID().split("@")[1]);
    }
}
