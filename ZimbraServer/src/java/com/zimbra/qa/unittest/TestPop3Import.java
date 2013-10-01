/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.qa.unittest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MailDateFormat;

import junit.framework.TestCase;

import com.zimbra.client.ZDataSource;
import com.zimbra.client.ZFilterAction;
import com.zimbra.client.ZFilterAction.ZFileIntoAction;
import com.zimbra.client.ZFilterCondition;
import com.zimbra.client.ZFilterCondition.HeaderOp;
import com.zimbra.client.ZFilterCondition.ZHeaderCondition;
import com.zimbra.client.ZFilterRule;
import com.zimbra.client.ZFilterRules;
import com.zimbra.client.ZFolder;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMessage;
import com.zimbra.client.ZPop3DataSource;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.ProvisioningConstants;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.db.DbPop3Message;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.soap.admin.type.DataSourceType;

public class TestPop3Import extends TestCase {
    private static final String USER_NAME = "user1";
    private static final String USER2_NAME = "user2";
    private static final String NAME_PREFIX = TestPop3Import.class.getSimpleName();
    private static final String DATA_SOURCE_NAME = NAME_PREFIX;
    private static final String TEMP_USER_NAME = NAME_PREFIX + "Temp";
    
    private ZFilterRules mOriginalRules;
    private boolean mIsServerSideTest;

    @Override
    public void setUp() throws Exception {
        mIsServerSideTest = false;
        cleanUp();
        createDataSource();
        mOriginalRules = TestUtil.getZMailbox(USER_NAME).getIncomingFilterRules();
    }

    /**
     * Tests import of a message with a date in the future (bug 17031).
     */
    public void testBogusDate() throws Exception {
        // Create remote account
        Provisioning.getInstance().createAccount(TestUtil.getAddress(TEMP_USER_NAME), "test123", null);
        
        // Add message with bogus date to remote mailbox
        MailDateFormat format = new MailDateFormat();
        Date date = format.parse("Thu, 31  Aug 2039 10:29:46 +0800");
        String message = TestUtil.getTestMessage(NAME_PREFIX + " testBogusDate", null, null, date);
        ZMailbox remoteMbox = TestUtil.getZMailbox(TEMP_USER_NAME);
        String folderId = Integer.toString(Mailbox.ID_FOLDER_INBOX);
        remoteMbox.addMessage(folderId, null, null, 0, message, true);
        
        // Update the data source, import data
        ZMailbox localMbox = TestUtil.getZMailbox(USER_NAME);
        ZPop3DataSource ds = getZDataSource();
        ds.setUsername(TEMP_USER_NAME);
        ds.setEnabled(true);
        localMbox.modifyDataSource(ds);
        
        // Import data and make sure the message was imported
        List<ZMessage> messages = TestUtil.search(localMbox, "in:inbox " + NAME_PREFIX);
        assertEquals("Found unexpected message in local inbox", 0, messages.size());
        TestUtil.importDataSource(ds, localMbox, remoteMbox);
        messages = TestUtil.search(localMbox, "in:inbox " + NAME_PREFIX);
        assertEquals("Imported message not found", 1, messages.size());
    }
    
    /**
     * Tests {@link ZMailbox#testDataSource}.
     */
    public void testTestDataSource() throws Exception {
        ZMailbox localMbox = TestUtil.getZMailbox(USER_NAME);
        ZPop3DataSource ds = getZDataSource();
        ds.setUsername(USER2_NAME);
        localMbox.modifyDataSource(ds);
        assertNull(localMbox.testDataSource(ds));
    }
    
    /**
     * Confirms that messages pulled from a POP3 account are affected by
     * mail filtering (bug 13821).
     */
    public void testFiltering()
    throws Exception {
        String folderPath = "/" + NAME_PREFIX + "-testFiltering";
        String filteredPath = "/" + NAME_PREFIX + "-testFiltering-filtered";
        
        // Create remote account
        Provisioning.getInstance().createAccount(TestUtil.getAddress(TEMP_USER_NAME), "test123", null);
        
        // Add message to remote mailbox
        ZMailbox remoteMbox = TestUtil.getZMailbox(TEMP_USER_NAME);
        TestUtil.addMessage(remoteMbox, NAME_PREFIX + " testFiltering");

        // Create local folders
        ZMailbox localMbox = TestUtil.getZMailbox(USER_NAME);
        localMbox.getFolderByPath("/Inbox");
        ZFolder dsFolder = TestUtil.createFolder(localMbox, folderPath);
        TestUtil.createFolder(localMbox, filteredPath);
        
        // Create filter rule that files to the local folder
        List<ZFilterRule> rules = new ArrayList<ZFilterRule>();
        List<ZFilterCondition> conditions = new ArrayList<ZFilterCondition>();
        List<ZFilterAction> actions = new ArrayList<ZFilterAction>();
        conditions.add(new ZHeaderCondition("subject", HeaderOp.CONTAINS, "testFiltering"));
        actions.add(new ZFileIntoAction(filteredPath));
        rules.add(new ZFilterRule("testFiltering", true, false, conditions, actions));
        localMbox.saveIncomingFilterRules(new ZFilterRules(rules));
        
        // Set up data source and run import
        ZPop3DataSource ds = getZDataSource();
        ds.setUsername(TEMP_USER_NAME);
        ds.setFolderId(dsFolder.getId());
        ds.setEnabled(true);
        localMbox.modifyDataSource(ds);
        
        // Import data and make sure the message was filed to the folder
        TestUtil.importDataSource(ds, localMbox, remoteMbox);
        List<ZMessage> messages = TestUtil.search(localMbox, "in:" + folderPath);
        assertEquals("Found unexpected messages in " + folderPath, 0, messages.size());
        messages = TestUtil.search(localMbox, "in:" + filteredPath);
        assertEquals("Message not found in " + filteredPath, 1, messages.size());
    }
    
    private ZPop3DataSource getZDataSource() throws Exception {
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        List<ZDataSource> dataSources = mbox.getAllDataSources();
        for (ZDataSource ds : dataSources) {
            if (ds.getName().equals(DATA_SOURCE_NAME)) {
                return (ZPop3DataSource) ds;
            }
        }
        fail("Could not find data source " + DATA_SOURCE_NAME);
        return null;
    }
    
    @Override
    public void tearDown() throws Exception {
        cleanUp();
        TestUtil.getZMailbox(USER_NAME).saveIncomingFilterRules(mOriginalRules);
    }
    
    private void createDataSource() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        Account account = TestUtil.getAccount(USER_NAME);
        int port = Integer.parseInt(TestUtil.getServerAttr(Provisioning.A_zimbraPop3BindPort));
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraDataSourceEnabled, ProvisioningConstants.FALSE);
        attrs.put(Provisioning.A_zimbraDataSourceHost, "localhost");
        attrs.put(Provisioning.A_zimbraDataSourcePort, Integer.toString(port));
        attrs.put(Provisioning.A_zimbraDataSourceUsername, "user1");
        attrs.put(Provisioning.A_zimbraDataSourcePassword, "test123");
        attrs.put(Provisioning.A_zimbraDataSourceFolderId, Integer.toString(Mailbox.ID_FOLDER_INBOX));
        attrs.put(Provisioning.A_zimbraDataSourceConnectionType, "cleartext");
        attrs.put(Provisioning.A_zimbraDataSourceLeaveOnServer, ProvisioningConstants.FALSE);
        prov.createDataSource(account, DataSourceType.pop3, DATA_SOURCE_NAME, attrs);
    }
    
    private DataSource getDataSource() throws Exception {
        Provisioning prov = Provisioning.getInstance();
        Account account = TestUtil.getAccount(USER_NAME);
        return prov.get(account, Key.DataSourceBy.name, DATA_SOURCE_NAME);
    }
    
    private void cleanUp() throws Exception {
        // Delete data source
        Provisioning prov = Provisioning.getInstance();
        DataSource ds = getDataSource();
        if (ds != null) {
            Account account = TestUtil.getAccount(USER_NAME);
            if (mIsServerSideTest) {
                Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
                DbPop3Message.deleteUids(mbox, ds.getId());
            }
            prov.deleteDataSource(account, ds.getId());
        }
        TestUtil.deleteTestData(USER_NAME, NAME_PREFIX);
        TestUtil.deleteAccount(TEMP_USER_NAME);
    }

    public static void main(String[] args)
    throws Exception {
        TestUtil.cliSetup();
        TestUtil.runTest(TestPop3Import.class);
    }
}
