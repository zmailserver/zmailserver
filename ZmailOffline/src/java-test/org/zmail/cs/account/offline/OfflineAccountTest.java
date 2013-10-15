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
package org.zmail.cs.account.offline;

import org.junit.Assert;
import org.junit.Test;

public class OfflineAccountTest {

    private static class MockOfflineAccount extends OfflineAccount {

        private String mail = "";
        private boolean isGal = false;
        private String id = "";

        public MockOfflineAccount(boolean isGal, String mail, String id) {
            super(mail, "", null, null, null, null);
            this.isGal = isGal;
            this.mail = mail;
            this.id = id;
        }

        @Override
        public String getMail() {
            return this.mail;
        }

        @Override
        public boolean isGalAccount() {
            return this.isGal;
        }

        @Override
        public String getId() {
            return this.id;
        }
    }

    private String zmailId = "3ed11feb-4d65-47c3-9fb3-45da9b80fcfa";
    private OfflineAccount galAccount = new MockOfflineAccount(true, "offline_gal@zmail.com__OFFLINE_GAL__", zmailId);
    private OfflineAccount nonGalAccount = new MockOfflineAccount(false, "dogfood-test@zmail.com", zmailId);

    @Test
    public void testGetDomain() {
        Assert.assertEquals("zmail.com", galAccount.getDomain());
        Assert.assertEquals("zmail.com", nonGalAccount.getDomain());
        Assert.assertEquals(galAccount, nonGalAccount);
    }
}