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

import junit.framework.TestCase;

import org.zmail.client.ZFolder;
import org.zmail.client.ZImapDataSource;
import org.zmail.client.ZMailbox;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.ldap.LdapConstants;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.ScheduledTask;
import org.zmail.qa.unittest.TestUtil;
import org.zmail.soap.type.DataSource.ConnectionType;

public class TestDataSourceServer extends TestCase {

    private static final String USER_NAME = "user1";
    private static final String TEST_USER_NAME = "testdatasource";
    private static final String NAME_PREFIX = TestDataSourceServer.class.getSimpleName();
    
    private String mOriginalAccountPollingInterval;
    private String mOriginalAccountPop3PollingInterval;
    private String mOriginalAccountImapPollingInterval;
    
    private String mOriginalCosPollingInterval;
    private String mOriginalCosPop3PollingInterval;
    private String mOriginalCosImapPollingInterval;
    
    public void setUp()
    throws Exception {
        cleanUp();

        // Remember original polling intervals.
        Account account = TestUtil.getAccount(USER_NAME);
        Cos cos = account.getCOS();
        mOriginalAccountPollingInterval = account.getAttr(Provisioning.A_zmailDataSourcePollingInterval, false);
        if (mOriginalAccountPollingInterval == null) {
            mOriginalAccountPollingInterval = "";
        }
        mOriginalAccountPop3PollingInterval = account.getAttr(Provisioning.A_zmailDataSourcePop3PollingInterval, false);
        if (mOriginalAccountPop3PollingInterval == null) {
            mOriginalAccountPop3PollingInterval = "";
        }
        mOriginalAccountImapPollingInterval = account.getAttr(Provisioning.A_zmailDataSourceImapPollingInterval, false);
        if (mOriginalAccountImapPollingInterval == null) {
            mOriginalAccountImapPollingInterval = "";
        }
        
        mOriginalCosPollingInterval = cos.getAttr(Provisioning.A_zmailDataSourcePollingInterval, "");
        mOriginalCosPop3PollingInterval = cos.getAttr(Provisioning.A_zmailDataSourcePop3PollingInterval, "");
        mOriginalCosImapPollingInterval = cos.getAttr(Provisioning.A_zmailDataSourceImapPollingInterval, "");
    }
    
    public void testScheduling()
    throws Exception {
        // Create data source.
        ZMailbox zmbox = TestUtil.getZMailbox(USER_NAME);
        ZFolder folder = TestUtil.createFolder(zmbox, "/" + NAME_PREFIX + "-testScheduling");
        Provisioning prov = Provisioning.getInstance();
        Server server = prov.getLocalServer();
        int port = server.getImapBindPort();
        ZImapDataSource zds = new ZImapDataSource(NAME_PREFIX + " testScheduling", true, "localhost", port,
            "user2", "test123", folder.getId(), ConnectionType.cleartext);
        String dsId = zmbox.createDataSource(zds);
        
        // Test scheduling based on polling interval. 
        Mailbox mbox = TestUtil.getMailbox(USER_NAME);
        String attrName = Provisioning.A_zmailDataSourcePollingInterval;
        String imapAttrName = Provisioning.A_zmailDataSourceImapPollingInterval;
        TestUtil.setDataSourceAttr(USER_NAME, zds.getName(), attrName, "0");
        checkSchedule(mbox, dsId, null);
        
        TestUtil.setDataSourceAttr(USER_NAME, zds.getName(), attrName, "10m");
        checkSchedule(mbox, dsId, 600000);

        TestUtil.setAccountAttr(USER_NAME, imapAttrName, "");
        TestUtil.setDataSourceAttr(USER_NAME, zds.getName(), attrName, "");
        checkSchedule(mbox, dsId, null);
        
        TestUtil.setAccountAttr(USER_NAME, imapAttrName, "5m");
        checkSchedule(mbox, dsId, 300000);
        
        TestUtil.setDataSourceAttr(USER_NAME, zds.getName(), attrName, "0");
        checkSchedule(mbox, dsId, null);
        
        // Bug 44502: test changing polling interval from 0 to unset when
        // interval is set on the account.
        TestUtil.setDataSourceAttr(USER_NAME, zds.getName(), attrName, "");
        checkSchedule(mbox, dsId, 300000);
        
        TestUtil.setDataSourceAttr(USER_NAME, zds.getName(), Provisioning.A_zmailDataSourceEnabled, LdapConstants.LDAP_FALSE);
        checkSchedule(mbox, dsId, null);
    }
    
    private void checkSchedule(Mailbox mbox, String dataSourceId, Integer intervalMillis)
    throws Exception {
        ScheduledTask task = DataSourceManager.getTask(mbox, dataSourceId);
        if (intervalMillis == null) {
            assertNull(task);
        } else {
            assertEquals(intervalMillis.longValue(), task.getIntervalMillis());
        }
    }
    
    public void tearDown()
    throws Exception {
        // Reset original polling intervals.
        Account account = TestUtil.getAccount(USER_NAME);
        Cos cos = account.getCOS();
        
        account.setDataSourcePollingInterval(mOriginalAccountPollingInterval);
        account.setDataSourcePop3PollingInterval(mOriginalAccountPop3PollingInterval);
        account.setDataSourceImapPollingInterval(mOriginalAccountImapPollingInterval);
        
        cos.setDataSourcePollingInterval(mOriginalCosPollingInterval);
        cos.setDataSourcePop3PollingInterval(mOriginalCosPop3PollingInterval);
        cos.setDataSourceImapPollingInterval(mOriginalCosImapPollingInterval);
        
        cleanUp();
    }
    
    public void cleanUp()
    throws Exception {
        TestUtil.deleteAccount(TEST_USER_NAME);
        TestUtil.deleteTestData(USER_NAME, NAME_PREFIX);
    }
}
