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
package com.zimbra.common.zmime;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.util.SharedByteArrayInputStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zimbra.common.util.ByteUtil;
import com.zimbra.common.zmime.ZMimeUtility.ByteBuilder;
import com.zimbra.common.zmime.ZTransferEncoding.Base64EncoderStream;

public class ZMimeMultipartTest {
    @BeforeClass
    public static void init() {
        System.setProperty("mail.mime.ignoremultipartencoding", "false");
    }

    @Test
    public void encoded() throws Exception {
        final String boundary = "dfghjkl";
        final String preamble = "when in the course of human events...";
        final String plain = "The Rain in Spain.";
        final String html = "The <u>Rain</u> in <em>Spain</em>.";

        ByteBuilder bbheader = new ByteBuilder();
        bbheader.append("From: test@example.com\r\n");
        bbheader.append("To: rcpt@example.com\r\n");
        bbheader.append("Subject: message subject\r\n");
        bbheader.append("Message-ID: <11e1-b0c4-0800200c9a66@example.com>\r\n");
        bbheader.append("Content-Transfer-Encoding: base64\r\n");
        bbheader.append("Content-Type: multipart/alternative; boundary=").append(boundary).append("\r\n");
        bbheader.append("\r\n");

        ByteBuilder bbmulti = new ByteBuilder();
        bbmulti.append(preamble).append("\r\n");
        bbmulti.append("--").append(boundary).append("\r\n");
        bbmulti.append("Content-Type: text/plain\r\n");
        bbmulti.append("\r\n");
        bbmulti.append(plain).append("\r\n");
        bbmulti.append("--").append(boundary).append("\r\n");
        bbmulti.append("Content-Type: text/html\r\n");
        bbmulti.append("\r\n");
        bbmulti.append(html).append("\r\n");
        bbmulti.append("--").append(boundary).append("--\r\n");

        // message with CTE header and base64-encoded body
        ByteBuilder bb = new ByteBuilder();
        bb.append(bbheader);
        bb.append(ByteUtil.getContent(new Base64EncoderStream(new ByteArrayInputStream(bbmulti.toByteArray())), -1));

        Session s = Session.getDefaultInstance(new Properties());
        ZMimeMessage mm = new ZMimeMessage(s, new SharedByteArrayInputStream(bb.toByteArray()));
        Object o = mm.getContent();
        Assert.assertTrue("content is ZMimeMultipart", o instanceof ZMimeMultipart);
        ZMimeMultipart multi = (ZMimeMultipart) o;
        Assert.assertEquals("preamble matches", preamble, multi.getPreamble());
        Assert.assertEquals("2 subparts", 2, multi.getCount());
        Assert.assertEquals("part 1 content match", plain, multi.getBodyPart(0).getContent());
        Assert.assertEquals("part 2 content match", html, multi.getBodyPart(1).getContent());

        // message with CTE header and nonencoded body
        bb = new ByteBuilder();
        bb.append(bbheader);
        bb.append(bbmulti);

        mm = new ZMimeMessage(s, new SharedByteArrayInputStream(bb.toByteArray()));
        o = mm.getContent();
        Assert.assertTrue("content is ZMimeMultipart", o instanceof ZMimeMultipart);
        multi = (ZMimeMultipart) o;
        Assert.assertEquals("preamble matches", preamble, multi.getPreamble());
        Assert.assertEquals("2 subparts", 2, multi.getCount());
        Assert.assertEquals("part 1 content match", plain, multi.getBodyPart(0).getContent());
        Assert.assertEquals("part 2 content match", html, multi.getBodyPart(1).getContent());
    }
}
