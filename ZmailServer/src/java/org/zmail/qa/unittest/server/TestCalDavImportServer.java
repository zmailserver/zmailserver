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
package org.zmail.qa.unittest.server;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.zmail.common.account.Key;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.datasource.CalDavDataImport;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.soap.admin.type.DataSourceType;

public class TestCalDavImportServer extends TestCase {
    private static final String NAME_PREFIX = TestCalDavImportServer.class.getSimpleName();
    private static final String USER_NAME = NAME_PREFIX + "user1";
    private static final String DATA_SOURCE_NAME = NAME_PREFIX;
    private static final String TEMP_USER_NAME = NAME_PREFIX + "Temp";
    private Folder rootFolder = null;
    private Account account = null;
    private Mailbox mbox = null;

    @Override
    public void setUp() throws Exception {
        cleanUp();
        Provisioning prov = Provisioning.getInstance();
        // create user1 account
        Account user1Account = prov.createAccount(TestUtil.getAddress(USER_NAME), "test123", null);
        // load the mailbox
        Mailbox user1Mbox = MailboxManager.getInstance().getMailboxByAccount(user1Account);
        // Create temp account and mailbox
        account = prov.createAccount(TestUtil.getAddress(TEMP_USER_NAME), "test123", null);
        mbox = MailboxManager.getInstance().getMailboxByAccount(account);
        rootFolder = mbox.createFolder(null, USER_NAME, Mailbox.ID_FOLDER_ROOT, new Folder.FolderOptions().setDefaultView(MailItem.Type.APPOINTMENT));
        createDataSource();
    }

    public void testRootFolderSyncToken() throws Exception {
        Assert.assertTrue(rootFolder.getLastSyncDate() == 0);
        // sync data source
        CalDavDataImport davDataImport = new CalDavDataImport(getDataSource());
        davDataImport.importData(null, true);
        // make sure sync token is updated on root folder
        Assert.assertTrue(rootFolder.getLastSyncDate() > 0);
    }

    @Override
    public void tearDown() throws Exception {
        cleanUp();
    }

    private void createDataSource() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        int port = Integer.parseInt(TestUtil.getServerAttr(Provisioning.A_zmailMailSSLPort));
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailDataSourceEnabled, ProvisioningConstants.TRUE);
        attrs.put(Provisioning.A_zmailDataSourceHost, TestUtil.getServerAttr(Provisioning.A_zmailServiceHostname));
        attrs.put(Provisioning.A_zmailDataSourcePort, Integer.toString(port));
        attrs.put(Provisioning.A_zmailDataSourceUsername, USER_NAME);
        attrs.put(Provisioning.A_zmailDataSourcePassword, "test123");
        attrs.put(Provisioning.A_zmailDataSourceFolderId, Integer.toString(rootFolder.getId()));
        attrs.put(Provisioning.A_zmailDataSourceConnectionType, "ssl");
        attrs.put(Provisioning.A_zmailDataSourceAttribute, "p:/principals/users/_USERNAME_");
        prov.createDataSource(account, DataSourceType.caldav, DATA_SOURCE_NAME, attrs);
    }

    private DataSource getDataSource() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        return prov.get(account, Key.DataSourceBy.name, DATA_SOURCE_NAME);
    }

    private void cleanUp() throws Exception {
        // Delete data source
        Account account = TestUtil.getAccount(TEMP_USER_NAME);
        if (account != null) {
            Provisioning prov = Provisioning.getInstance();
            DataSource ds = prov.get(account, Key.DataSourceBy.name, DATA_SOURCE_NAME);
            if (ds != null) {
                prov.deleteDataSource(account, ds.getId());
            }
            TestUtil.deleteTestData(TEMP_USER_NAME, NAME_PREFIX);
            TestUtil.deleteAccount(TEMP_USER_NAME);
        }
        account = TestUtil.getAccount(USER_NAME);
        if (account != null) {
            TestUtil.deleteTestData(USER_NAME, NAME_PREFIX);
            TestUtil.deleteAccount(USER_NAME);
        }
    }
}
