/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.junit.*;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZDataSource;
import org.zmail.client.ZFolder;
import org.zmail.client.ZImapDataSource;
import org.zmail.cs.mailclient.imap.ImapConnection;
import org.zmail.cs.mailclient.imap.ImapConfig;
import org.zmail.cs.mailclient.imap.ListData;
import org.zmail.cs.mailclient.imap.MailboxInfo;
import org.zmail.cs.mailclient.MailConfig;
import org.zmail.cs.account.DataSource;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.soap.type.DataSource.ConnectionType;

import java.io.IOException;
import java.util.List;

public class TestImapSync {
    private static final String LOCAL_USER = "TestImapSync";
    private static final String TEST_FOLDER_1 = "TestOne";
    private static final Log LOG = ZmailLog.test;

    private final ImapConfig config;
    private final String pass;
    private final ImapFolder imapFolder1 = new ImapFolder(TEST_FOLDER_1);
    private ZMailbox localMailbox;
    private ImapConnection imapConnection;
    private ZDataSource dataSource;

    public TestImapSync(ImapConfig config, String pass) {
        this.config = config;
        this.pass = pass;
        config.getLogger().setLevel(Log.Level.trace);
    }

    public TestImapSync() {
        config = new ImapConfig();
        config.setHost("localhost");
        config.setPort(7143);
        config.setAuthenticationId("user1");
        pass = "test123";
    }

    @Test
    public void testSync() throws Exception {
        LOG.info("Testing adding message to local mailbox");
        ZFolder folder = TestUtil.createFolder(localMailbox, TEST_FOLDER_1);
        TestUtil.addMessage(localMailbox, "msg1", folder.getId(), "u");
        Assert.assertFalse(imapFolder1.exists());
        syncFolders();
        Assert.assertTrue(imapFolder1.exists());
        MailboxInfo mb = imapFolder1.select();
        Assert.assertEquals(1, mb.getExists());
        Assert.assertEquals(1, mb.getUnseen());
    }

    @Before
    public void setUp() throws Exception {
        if (TestUtil.accountExists(LOCAL_USER)) {
            TestUtil.deleteAccount(LOCAL_USER);
        }
        TestUtil.createAccount(LOCAL_USER);
        localMailbox = TestUtil.getZMailbox(LOCAL_USER);
        dataSource = createDataSource();
        connect();
        deleteImapFolders();
    }

    @After
    public void tearDown() throws Exception {
        if (TestUtil.accountExists(LOCAL_USER)) {
            TestUtil.deleteAccount(LOCAL_USER);
        }
        if (imapConnection != null) {
            deleteImapFolders();
            imapConnection.logout();
            imapConnection = null;
        }
        localMailbox = null;
        dataSource = null;
    }

    private void syncFolders() throws Exception {
        TestUtil.importDataSource(dataSource, localMailbox, null);
    }

    private ZDataSource createDataSource() throws Exception {
        ConnectionType ctype =
            config.getSecurity() == MailConfig.Security.SSL ?
                ConnectionType.ssl : ConnectionType.cleartext;
        String id = localMailbox.createDataSource(
            new ZImapDataSource(
                "TestImapSync", true, config.getHost(), config.getPort(),
                config.getAuthenticationId(), pass, "1", ctype));
        for (ZDataSource ds : localMailbox.getAllDataSources()) {
            if (ds.getId().equals(id)) {
                return ds;
            }
        }
        Assert.fail("Could not find data source");
        return null;
    }

    private void connect() throws IOException {
        imapConnection = new ImapConnection(config);
        imapConnection.connect();
        imapConnection.login(pass);
    }

    private void deleteImapFolders() throws IOException {
        if (imapConnection.isAuthenticated()) {
            if (imapFolder1.exists()) {
                imapFolder1.delete();
            }
            if (imapFolder1.exists()) {
                imapFolder1.delete();
            }
        }
    }

    private class ImapFolder {
        private String name;

        ImapFolder(String name) {
            this.name = name;
        }

        String name() { return name; }

        boolean exists() throws IOException {
            return getListData() != null;
        }

        ListData getListData() throws IOException {
            List<ListData> lds = imapConnection.list("", name);
            return lds.isEmpty() ? null : lds.get(0);
        }

        MailboxInfo select() throws IOException {
            return imapConnection.select(name());
        }

        void delete() throws IOException {
            imapConnection.delete(name());
        }
    }
}
