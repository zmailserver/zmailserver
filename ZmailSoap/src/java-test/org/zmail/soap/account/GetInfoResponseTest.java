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
package org.zmail.soap.account;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.soap.Element;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.account.message.GetInfoResponse;
import org.zmail.soap.account.type.Identity;

/**
 * Unit test for {@link GetInfoResponse}.
 *
 * @author ysasaki
 */
public class GetInfoResponseTest {

    private static Unmarshaller unmarshaller;

    @BeforeClass
    public static void init() throws Exception {
        JAXBContext jaxb = JAXBContext.newInstance(GetInfoResponse.class);
        unmarshaller = jaxb.createUnmarshaller();
    }

    private void checkAsserts(GetInfoResponse result) {
        List<Identity> identities =  result.getIdentities();
        Assert.assertEquals(1, identities.size());
        Assert.assertEquals("Identity{a=[" +
                "Attr{name=zmailPrefIdentityId, value=91e6d036-5d5e-4788-9bc2-5b65e8c2480c}, " +
                "Attr{name=zmailPrefSaveToSent, value=TRUE}, " +
                "Attr{name=zmailPrefForwardReplyPrefixChar, value=>}, " +
                "Attr{name=zmailPrefSentMailFolder, value=sent}, " +
                "Attr{name=zmailPrefFromDisplay, value=Demo User One}, " +
                "Attr{name=zmailPrefForwardIncludeOriginalText, value=includeBody}, " +
                "Attr{name=zmailPrefForwardReplyFormat, value=same}, " +
                "Attr{name=zmailPrefMailSignatureStyle, value=outlook}, " +
                "Attr{name=zmailPrefIdentityName, value=DEFAULT}, " +
                "Attr{name=zmailCreateTimestamp, value=20120528071949Z}, " +
                "Attr{name=zmailPrefReplyIncludeOriginalText, value=includeBody}, " +
                "Attr{name=zmailPrefFromAddress, value=user1@tarka.local}, " +
                "Attr{name=zmailPrefDefaultSignatureId, value=28fa4fec-a5fb-4dc8-acf9-df930bb13546}], " +
                "name=DEFAULT, id=91e6d036-5d5e-4788-9bc2-5b65e8c2480c}",
                identities.get(0).toString());
        Collection<String> sigHtml = result.getPrefsMultimap().get("zmailPrefMailSignatureHTML");
        Assert.assertNotNull(sigHtml);
        // Full comparison failing on Jenkins system due to environmental charset issues
        String sig = sigHtml.iterator().next();
        // Assert.assertEquals("\u003Cstrong\u003Ef—— utf8\u003C/strong\u003E signature test" , sig);
        Assert.assertTrue("Signature", sig.endsWith("signature test"));
        Assert.assertTrue("Number of license attributes", 2 <= result.getLicense().getAttrs().size());
    }
    
    @Test
    public void unmarshall() throws Exception {
        checkAsserts((GetInfoResponse) unmarshaller.unmarshal(
                        getClass().getResourceAsStream("GetInfoResponse.xml")));
    }
    
    @Test
    public void jaxbUtilUnmarshall() throws Exception {
        //same as unmarshall but use JaxbUtil; this provokes/tests issues with utf8 conversion
        checkAsserts((GetInfoResponse) JaxbUtil.elementToJaxb(
                        Element.parseXML(getClass().getResourceAsStream("GetInfoResponse.xml"))));
    }

}
