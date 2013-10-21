/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.qa.unittest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.zmail.common.localconfig.LC;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.client.LmcSession;
import org.zmail.cs.client.soap.LmcDeleteAccountRequest;
import org.zmail.cs.db.DbMailbox;
import org.zmail.cs.db.DbResults;
import org.zmail.cs.db.DbUtil;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.volume.VolumeManager;

/**
 * @author bburtin
 */
public final class TestAccount extends TestCase {

    private static String USER_NAME = "TestAccount";
    private static String PASSWORD = "test123";

    @Override
    public void setUp() throws Exception {
        cleanUp();

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put("zmailMailHost", LC.zmail_server_hostname.value());
        attrs.put("cn", "TestAccount");
        attrs.put("displayName", "TestAccount unit test user");
        Provisioning.getInstance().createAccount(TestUtil.getAddress(USER_NAME), PASSWORD, attrs);
    }

    @Override
    public void tearDown() throws Exception {
        cleanUp();
    }

    public void testDeleteAccount() throws Exception {
        ZmailLog.test.debug("testDeleteAccount()");

        // Get the account and mailbox
        Account account = TestUtil.getAccount(USER_NAME);
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
        String dbName = DbMailbox.getDatabaseName(mbox);
        ZmailLog.test.debug("Account=" + account.getId() + ", mbox=" + mbox.getId());

        // Confirm that the mailbox database exists
        DbResults results = DbUtil.executeQuery(
            "SELECT COUNT(*) FROM mailbox WHERE id = " + mbox.getId());
        assertEquals("Could not find row in mailbox table", 1, results.getInt(1));

        results = DbUtil.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
        assertEquals("Could not find mailbox database", 1, results.size());

        // Add a message to the account and confirm that the message directory exists
        TestUtil.addMessage(mbox, "TestAccount testDeleteAccount");
        String storePath = VolumeManager.getInstance().getCurrentMessageVolume().getMessageRootDir(mbox.getId());
        File storeDir = new File(storePath);
        if (TestUtil.checkLocalBlobs()) {
            assertTrue(storePath + " does not exist", storeDir.exists());
            assertTrue(storePath + " is not a directory", storeDir.isDirectory());
        }
        // Delete the account
        LmcSession session = TestUtil.getAdminSoapSession();
        LmcDeleteAccountRequest req = new LmcDeleteAccountRequest(account.getId());
        req.setSession(session);
        req.invoke(TestUtil.getAdminSoapUrl());

        // Confirm that the mailbox was deleted
        results = DbUtil.executeQuery(
            "SELECT COUNT(*) FROM mailbox WHERE id = " + mbox.getId());
        assertEquals("Unexpected row in mailbox table", 0, results.getInt(1));

        if (TestUtil.checkLocalBlobs()) {
            // Confirm that the message directory was deleted
            assertFalse(storePath + " exists", storeDir.exists());
        }
    }

    private void cleanUp() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        Account account = prov.get(AccountBy.name, TestUtil.getAddress(USER_NAME));
        if (account != null) {
            prov.deleteAccount(account.getId());
        }
    }
}
