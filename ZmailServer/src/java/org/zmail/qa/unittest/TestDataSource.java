/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.zimbra.client.ZCalDataSource;
import com.zimbra.client.ZDataSource;
import com.zimbra.client.ZFolder;
import com.zimbra.client.ZGrant.GranteeType;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMessage;
import com.zimbra.client.ZRssDataSource;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.Element.XMLElement;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.util.HttpUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Cos;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.ldap.LdapConstants;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.soap.type.DataSource.ConnectionType;

public class TestDataSource extends TestCase {

    private static final String USER_NAME = "user1";
    private static final String DS_NAME = "TestDataSource";
    private static final String TEST_USER_NAME = "testdatasource";
    private static final String NAME_PREFIX = TestDataSource.class.getSimpleName();

    private String mOriginalAccountPollingInterval;
    private String mOriginalAccountPop3PollingInterval;
    private String mOriginalAccountImapPollingInterval;

    private String mOriginalCosPollingInterval;
    private String mOriginalCosPop3PollingInterval;
    private String mOriginalCosImapPollingInterval;

    @Override
    public void setUp()
    throws Exception {
        cleanUp();

        // Remember original polling intervals.
        Account account = TestUtil.getAccount(USER_NAME);
        Cos cos = account.getCOS();
        mOriginalAccountPollingInterval = account.getAttr(Provisioning.A_zimbraDataSourcePollingInterval, false);
        if (mOriginalAccountPollingInterval == null) {
            mOriginalAccountPollingInterval = "";
        }
        mOriginalAccountPop3PollingInterval = account.getAttr(Provisioning.A_zimbraDataSourcePop3PollingInterval, false);
        if (mOriginalAccountPop3PollingInterval == null) {
            mOriginalAccountPop3PollingInterval = "";
        }
        mOriginalAccountImapPollingInterval = account.getAttr(Provisioning.A_zimbraDataSourceImapPollingInterval, false);
        if (mOriginalAccountImapPollingInterval == null) {
            mOriginalAccountImapPollingInterval = "";
        }

        mOriginalCosPollingInterval = cos.getAttr(Provisioning.A_zimbraDataSourcePollingInterval, "");
        mOriginalCosPop3PollingInterval = cos.getAttr(Provisioning.A_zimbraDataSourcePop3PollingInterval, "");
        mOriginalCosImapPollingInterval = cos.getAttr(Provisioning.A_zimbraDataSourceImapPollingInterval, "");
    }

    public void testPollingInterval()
    throws Exception {
        // Create data source
        Provisioning prov = Provisioning.getInstance();
        Account account = TestUtil.getAccount(USER_NAME);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraDataSourceEnabled, LdapConstants.LDAP_FALSE);
        attrs.put(Provisioning.A_zimbraDataSourceHost, "testhost");
        attrs.put(Provisioning.A_zimbraDataSourcePort, "0");
        attrs.put(Provisioning.A_zimbraDataSourceUsername, "testuser");
        attrs.put(Provisioning.A_zimbraDataSourcePassword, "testpass");
        attrs.put(Provisioning.A_zimbraDataSourceFolderId, "1");
        attrs.put(Provisioning.A_zimbraDataSourceConnectionType, ConnectionType.cleartext.toString());
        DataSource ds = prov.createDataSource(account, DataSourceType.pop3, NAME_PREFIX + " testPollingInterval", attrs);

        // Valid polling interval
        assertNotNull("Min not defined", account.getAttr(Provisioning.A_zimbraDataSourceMinPollingInterval));
        long min = account.getTimeInterval(Provisioning.A_zimbraDataSourceMinPollingInterval, 0) / 1000;
        attrs.clear();
        attrs.put(Provisioning.A_zimbraDataSourcePollingInterval, Long.toString(min));
        prov.modifyDataSource(account, ds.getId(), attrs);

        // Invalid polling interval
        attrs.clear();
        attrs.put(Provisioning.A_zimbraDataSourcePollingInterval, Long.toString(min - 1));
        try {
            prov.modifyDataSource(account, ds.getId(), attrs);
            fail("modifyDataSource() was not supposed to succeed");
        } catch (ServiceException e) {
            assertTrue("Unexpected message: " + e.getMessage(),
                e.getMessage().contains("shorter than the allowed minimum"));
        }
    }

    /**
     * Tests the <tt>lastError</tt> element and <tt>failingSince</tt> attribute
     * for <tt>GetInfoRequest</tt> and <tt>GetDataSourcesRequest</tt>.
     */
    public void testErrorStatus()
    throws Exception {
        Account account = TestUtil.createAccount(TEST_USER_NAME);

        // Create data source.
        Provisioning prov = Provisioning.getInstance();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraDataSourceEnabled, LdapConstants.LDAP_TRUE);
        attrs.put(Provisioning.A_zimbraDataSourceHost, "localhost");
        attrs.put(Provisioning.A_zimbraDataSourcePort, TestUtil.getServerAttr(Provisioning.A_zimbraPop3BindPort));
        attrs.put(Provisioning.A_zimbraDataSourceUsername, "user2");
        attrs.put(Provisioning.A_zimbraDataSourcePassword, TestUtil.DEFAULT_PASSWORD);
        attrs.put(Provisioning.A_zimbraDataSourceFolderId, Integer.toString(Mailbox.ID_FOLDER_INBOX));
        attrs.put(Provisioning.A_zimbraDataSourceConnectionType, ConnectionType.cleartext.toString());
        attrs.put(Provisioning.A_zimbraDataSourceLeaveOnServer, LdapConstants.LDAP_TRUE);
        DataSource ds = prov.createDataSource(account, DataSourceType.pop3, DS_NAME, attrs);

        // Make sure error status is not set.
        ZMailbox mbox = TestUtil.getZMailbox(TEST_USER_NAME);
        confirmErrorStatus(mbox, null);

        // Invoke data source sync and make sure error status is not set.
        ZDataSource zds = TestUtil.getDataSource(mbox, DS_NAME);
        TestUtil.importDataSource(zds, mbox, null, true);
        confirmErrorStatus(mbox, null);

        // Change to an invalid password, make sure error status is set.
        attrs.clear();
        attrs.put(Provisioning.A_zimbraDataSourcePassword, "bogus");
        prov.modifyDataSource(account, ds.getId(), attrs);
        zds = TestUtil.getDataSource(mbox, DS_NAME);
        long startTimestamp = System.currentTimeMillis() - 1000; // timestamp is returned in seconds, not millis
        TestUtil.importDataSource(zds, mbox, null, false);
        confirmErrorStatus(mbox, startTimestamp);

        // Fix password, make sure that error status is reset (bug 39050).
        attrs.put(Provisioning.A_zimbraDataSourcePassword, TestUtil.DEFAULT_PASSWORD);
        prov.modifyDataSource(account, ds.getId(), attrs);
        confirmErrorStatus(mbox, null);

        // Do another sync, make sure error password is not set.
        zds = TestUtil.getDataSource(mbox, DS_NAME);
        startTimestamp = System.currentTimeMillis();
        TestUtil.importDataSource(zds, mbox, null, true);
        confirmErrorStatus(mbox, null);
    }

    private void confirmErrorStatus(ZMailbox mbox, Long laterThanTimestamp)
    throws Exception {
        // Check GetInfoRequest.
        Element request = new XMLElement(AccountConstants.GET_INFO_REQUEST);
        Element response = mbox.invoke(request);
        Element eDS = response.getElement(AccountConstants.E_DATA_SOURCES);
        Element ePop3 = null;
        for (Element e : eDS.listElements(MailConstants.E_DS_POP3)) {
            if (e.getAttribute(MailConstants.A_NAME).equals(DS_NAME)) {
                ePop3 = e;
            }
        }
        assertNotNull("Could not find data source in response: " + response.prettyPrint(), ePop3);
        confirmErrorStatus(ePop3, laterThanTimestamp);

        // Check GetDataSources.
        ePop3 = null;
        request = new XMLElement(MailConstants.GET_DATA_SOURCES_REQUEST);
        response = mbox.invoke(request);
        for (Element e : response.listElements(MailConstants.E_DS_POP3)) {
            if (e.getAttribute(MailConstants.A_NAME).equals(DS_NAME)) {
                ePop3 = e;
            }
        }
        assertNotNull("Could not find data source in response: " + response.prettyPrint(), ePop3);
        confirmErrorStatus(ePop3, laterThanTimestamp);
    }

    private void confirmErrorStatus(Element ePop3, Long timestampBeforeSync)
    throws Exception {
        if (timestampBeforeSync != null) {
            assertTrue(ePop3.getElement(MailConstants.E_DS_LAST_ERROR).getText().length() > 0);
            long failingSince = ePop3.getAttributeLong(MailConstants.A_DS_FAILING_SINCE) * 1000;
            long now = System.currentTimeMillis();

            assertTrue(failingSince + " is earlier than " + timestampBeforeSync, failingSince >= timestampBeforeSync);
            assertTrue(failingSince + " is later than " + now, failingSince < now);
        } else {
            assertNull("Last error was not reset", ePop3.getOptionalElement(MailConstants.E_DS_LAST_ERROR));
            assertNull("Error timestamp was not reset", ePop3.getAttribute(MailConstants.A_DS_FAILING_SINCE, null));
        }
    }

    /**
     * Tests {@link DataSource#isScheduled()}.
     */
    public void testIsScheduled()
    throws Exception {
        // Create data source
        Provisioning prov = Provisioning.getInstance();
        Account account = TestUtil.getAccount(USER_NAME);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraDataSourceEnabled, LdapConstants.LDAP_FALSE);
        attrs.put(Provisioning.A_zimbraDataSourceHost, "testhost");
        attrs.put(Provisioning.A_zimbraDataSourcePort, "0");
        attrs.put(Provisioning.A_zimbraDataSourceUsername, "testuser");
        attrs.put(Provisioning.A_zimbraDataSourcePassword, "testpass");
        attrs.put(Provisioning.A_zimbraDataSourceFolderId, "1");
        attrs.put(Provisioning.A_zimbraDataSourceConnectionType, ConnectionType.cleartext.toString());
        String name = NAME_PREFIX + " testNegativePollingInterval";
        DataSource ds = prov.createDataSource(account, DataSourceType.pop3, name, attrs);

        // Test polling interval not set.
        ds = account.getDataSourceByName(name);
        assertFalse(ds.isScheduled());

        // Test polling interval = 0.
        attrs.clear();
        attrs.put(Provisioning.A_zimbraDataSourcePollingInterval, "0");
        prov.modifyDataSource(account, ds.getId(), attrs);
        ds = account.getDataSourceByName(name);
        assertFalse(ds.isScheduled());

        // Test polling interval > 0.
        attrs.clear();
        attrs.put(Provisioning.A_zimbraDataSourcePollingInterval, "365d");
        prov.modifyDataSource(account, ds.getId(), attrs);
        ds = account.getDataSourceByName(name);
        assertTrue(ds.isScheduled());
    }

    public void testMigratePollingInterval()
    throws Exception {
        Account account = TestUtil.getAccount(USER_NAME);
        Cos cos = account.getCOS();

        // Create data source
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        ZFolder folder = TestUtil.createFolder(mbox, NAME_PREFIX + " testMigratePollingInterval");

        Provisioning prov = Provisioning.getInstance();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraDataSourceEnabled, LdapConstants.LDAP_FALSE);
        attrs.put(Provisioning.A_zimbraDataSourceHost, "localhost");
        int port = Integer.parseInt(TestUtil.getServerAttr(Provisioning.A_zimbraPop3BindPort));
        attrs.put(Provisioning.A_zimbraDataSourcePort, Integer.toString(port));
        attrs.put(Provisioning.A_zimbraDataSourceUsername, "user2");
        attrs.put(Provisioning.A_zimbraDataSourcePassword, "test123");
        attrs.put(Provisioning.A_zimbraDataSourceFolderId, folder.getId());
        attrs.put(Provisioning.A_zimbraDataSourceConnectionType, ConnectionType.cleartext.toString());
        String dsName = NAME_PREFIX + " testMigratePollingInterval";
        DataSource ds = prov.createDataSource(account, DataSourceType.pop3, dsName, attrs);

        // Set old polling intervals and unset new ones.
        account.unsetDataSourcePop3PollingInterval();
        account.unsetDataSourceImapPollingInterval();
        cos.unsetDataSourcePop3PollingInterval();
        cos.unsetDataSourceImapPollingInterval();
        account.setDataSourcePollingInterval("1h");
        cos.setDataSourcePollingInterval("2h");

        // Trigger the migration.
        ds.getPollingInterval();

        // Refresh and verify migrated values.
        account = TestUtil.getAccount(USER_NAME);
        cos = account.getCOS();
        ds = account.getDataSourceByName(dsName);

        assertEquals("1h", account.getAttr(Provisioning.A_zimbraDataSourcePop3PollingInterval));
        assertEquals("1h", account.getAttr(Provisioning.A_zimbraDataSourceImapPollingInterval));
        assertEquals("2h", cos.getAttr(Provisioning.A_zimbraDataSourcePop3PollingInterval));
        assertEquals("2h", cos.getAttr(Provisioning.A_zimbraDataSourceImapPollingInterval));
    }

    /**
     * Creates a folder that syncs to another folder via RSS, and verifies that an
     * RSS data source was implicitly created.
     */
    public void testRss()
    throws Exception {
        // Create source folder, make it publically readable, and add a message to it.
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        String parentId = Integer.toString(Mailbox.ID_FOLDER_USER_ROOT);
        ZFolder sourceFolder = TestUtil.createFolder(mbox, "/" + NAME_PREFIX + " testRss source");
        mbox.modifyFolderGrant(sourceFolder.getId(), GranteeType.pub, null, "r", null);
        String subject = NAME_PREFIX + " testRss";
        TestUtil.addMessage(mbox, subject, sourceFolder.getId());

        // Create destination folder that syncs to the source folder via RSS.
        String urlString = String.format("https://%s:%s/home/%s%s.rss", TestUtil.getServerAttr(Provisioning.A_zimbraServiceHostname),
                                            TestUtil.getServerAttr(Provisioning.A_zimbraMailSSLPort), USER_NAME, sourceFolder.getPath());
        urlString = HttpUtil.encodePath(urlString);
        ZFolder rssFolder = mbox.createFolder(parentId, NAME_PREFIX + " testRss destination", null, null, null, urlString);

        // Get the data source that was implicitly created.
        ZRssDataSource ds = (ZRssDataSource) getDataSource(mbox, rssFolder.getId());
        assertNotNull(ds);
        assertNull(mbox.testDataSource(ds));

        // Import data and validate the synced message.
        List<ZDataSource> list = new ArrayList<ZDataSource>();
        list.add(ds);
        mbox.importData(list);
        waitForData(mbox, rssFolder);
        ZMessage syncedMsg = TestUtil.getMessage(mbox, "in:\"" + rssFolder.getPath() + "\"");
        assertEquals(subject, syncedMsg.getSubject());

        // Delete folder, import data, and make sure that the data source was deleted.
        // Data source import runs asynchronously, so poll until the data source is gone.
        mbox.deleteFolder(rssFolder.getId());
        //JBF - do not do the import; it will fail if DS is already deleted
        //mbox.importData(list);

        // XXX bburtin: disabled check to avoid false positives (bug 54816).  Some sort
        // of race condition is causing this check to fail intermittently.  I was unable
        // to consistently repro.

        /*
        for (int i = 1; i <= 10; i++) {
            ds = (ZRssDataSource) getDataSource(mbox, rssFolder.getId());
            if (ds == null) {
                break;
            }
            Thread.sleep(500);
        }
        assertNull(ds);
        */
    }

    // XXX bburtin: disabled test due to bug 37222 (unable to parse Google calendar).
    public void disabledTestCal()
    throws Exception {
        // Create folder.
        ZMailbox mbox = TestUtil.getZMailbox(USER_NAME);
        String parentId = Integer.toString(Mailbox.ID_FOLDER_USER_ROOT);
        String urlString = "http://www.google.com/calendar/ical/k2kh7ncij3s05dog63g0o0n254%40group.calendar.google.com/public/basic.ics";
        ZFolder folder;
        try {
            folder = mbox.createFolder(parentId, NAME_PREFIX + " testCal", ZFolder.View.appointment, null, null, urlString);
        } catch (ServiceException e) {
            assertEquals(ServiceException.RESOURCE_UNREACHABLE, e.getCode());
            ZimbraLog.test.warn("Unable to test calendar data source for %s: %s", urlString, e.toString());
            return;
        }

        // Get the data source that was implicitly created.
        ZCalDataSource ds = (ZCalDataSource) getDataSource(mbox, folder.getId());
        assertNotNull(ds);

        // Test data source.  If the test fails, skip validation so we don't
        // get false positives when the feed is down or the test
        // is running on a box that's not connected to the internet.
        String error = mbox.testDataSource(ds);
        if (error != null) {
            ZimbraLog.test.warn("Unable to test iCal data source for %s: %s.", urlString, error);
            return;
        }

        // Import data and confirm that the folder is not empty.
        List<ZDataSource> list = new ArrayList<ZDataSource>();
        list.add(ds);
        mbox.importData(list);
        waitForData(mbox, folder);

        // Delete folder, import data, and make sure that the data source was deleted.
        mbox.deleteFolder(folder.getId());
        mbox.importData(list);
        ds = (ZCalDataSource) getDataSource(mbox, folder.getId());
        assertNull(ds);
    }

    private void waitForData(ZMailbox mbox, ZFolder folder)
    throws Exception {
        for (int i = 1; i <= 10; i++) {
            mbox.noOp();
            if (folder.getSize() > 0) {
                return;
            }
            Thread.sleep(500);
        }
        fail("No items found in folder " + folder.getPath());
    }

    private ZDataSource getDataSource(ZMailbox mbox, String folderId)
    throws ServiceException {
        for (ZDataSource i : mbox.getAllDataSources()) {
            if (i instanceof ZRssDataSource && ((ZRssDataSource) i).getFolderId().equals(folderId)) {
                return i;
            }
        }
        return null;
    }


    @Override
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

    public static void main(String[] args)
    throws Exception {
        TestUtil.cliSetup();
        TestUtil.runTest(TestDataSource.class);
    }
}
