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
package org.zmail.cs.offline.util.yc;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import org.zmail.cs.offline.util.Xml;
import org.zmail.cs.offline.util.yc.oauth.OAuthGetRequestTokenRequest;
import org.zmail.cs.offline.util.yc.oauth.OAuthGetRequestTokenResponse;
import org.zmail.cs.offline.util.yc.oauth.OAuthGetTokenRequest;
import org.zmail.cs.offline.util.yc.oauth.OAuthGetTokenResponse;
import org.zmail.cs.offline.util.yc.oauth.OAuthHelper;
import org.zmail.cs.offline.util.yc.oauth.OAuthPutContactRequest;
import org.zmail.cs.offline.util.yc.oauth.OAuthRequest;
import org.zmail.cs.offline.util.yc.oauth.OAuthResponse;
import org.zmail.cs.offline.util.yc.oauth.OAuthToken;

public class DeleteContactTest {

    static OAuthToken token;
    static OAuthRequest req;
    static String resp;
    static OAuthResponse response;
    static String id;
    static int rev;

    @BeforeClass
    public static void init() throws Exception {
        req = new OAuthGetRequestTokenRequest(new OAuthToken());
        resp = req.send();
        OAuthResponse response = new OAuthGetRequestTokenResponse(resp);
        System.out.println("paste it into browser and input the highlighted codes below: "
                + response.getToken().getNextUrl());

        System.out.print("Verifier: ");
        Scanner scan = new Scanner(System.in);
        String verifier = scan.nextLine();
        req = new OAuthGetTokenRequest(response.getToken(), verifier);
        resp = req.send();
        response = new OAuthGetTokenResponse(resp);
        token = response.getToken();
    }

    @Test
    public void testAddContact() {
        try {
            InputStream stream = this.getClass().getClassLoader()
                    .getResourceAsStream("yahoo_contacts_client_add_dummy.xml");
            String content = OAuthHelper.getStreamContents(stream);

            req = new OAuthPutContactRequest(token, content);
            resp = req.send();
            System.out.println("resp:" + resp);
            DocumentBuilder builder = Xml.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(resp)));
            Element syncResult = doc.getDocumentElement();
            rev = Xml.getIntAttribute(syncResult, "yahoo:rev");
            System.out.println("rev: " + rev);
            Element result = Xml.getChildren(syncResult).get(0);
            Element contacts = Xml.getChildren(result).get(0);
            boolean success = false;
            for (Element child : Xml.getChildren(contacts)) {
                if ("response".equals(child.getNodeName())) {
                    Assert.assertEquals("success", child.getTextContent());
                    success = true;
                }
                if ("id".equals(child.getNodeName())) {
                    id = child.getTextContent();
                    System.out.println("get new contact id: " + id);
                }
            }
            if (!success) {
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    //
    // @Test
    // public void testUpdateContact() {
    // try {
    // InputStream stream =
    // this.getClass().getClassLoader().getResourceAsStream("yahoo_contacts_client_update_dummy.xml");
    // String content = OAuthHelper.getStreamContents(stream);
    // content = String.format(content, id);
    // System.out.println("update put body: " + content);
    // req = new OAuthPutContactRequest(token, content);
    // resp = req.send();
    // System.out.println("resp:" + resp);
    // } catch (Exception e) {
    // e.printStackTrace();
    // Assert.fail();
    // }
    // }

    @Test
    public void testRemoveContact() {
        try {
            InputStream stream = this.getClass().getClassLoader()
                    .getResourceAsStream("yahoo_contacts_client_remove_dummy.xml");
            String content = OAuthHelper.getStreamContents(stream);
            content = String.format(content, id);
            System.out.println("remove put body: " + content);
            req = new OAuthPutContactRequest(token, content);
            resp = req.send();
            System.out.println("resp:" + resp);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
