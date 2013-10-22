/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013 VMware, Inc.
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

package com.zimbra.cs.mailbox;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.zimbra.common.calendar.ZCalendar.ZVCalendar;
import com.zimbra.common.localconfig.LC;
import com.zimbra.common.mime.ContentDisposition;
import com.zimbra.common.mime.MimeConstants;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.common.zmime.ZMimeBodyPart;
import com.zimbra.common.zmime.ZMimeMultipart;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.MockProvisioning;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.db.DbPool;
import com.zimbra.cs.db.HSQLDB;
import com.zimbra.cs.mailbox.calendar.Invite;
import com.zimbra.cs.mime.Mime;
import com.zimbra.cs.mime.ParsedMessage;
import com.zimbra.cs.store.MockStoreManager;
import com.zimbra.cs.store.StoreManager;
import com.zimbra.cs.store.http.HttpStoreManagerTest.MockHttpStoreManager;
import com.zimbra.cs.store.http.MockHttpStore;
import com.zimbra.cs.util.JMSession;
import com.zimbra.soap.DocumentHandler;

public final class MailboxTestUtil {

    private MailboxTestUtil() {
    }

    /**
     * Initializes the provisioning.
     */
    public static void initProvisioning() throws Exception {
        initProvisioning("");
    }

    /**
     * Initializes the provisioning.
     *
     * @param zimbraServerDir the directory that contains the ZimbraServer project
     * @throws Exception
     */
    public static void initProvisioning(String zimbraServerDir) throws Exception {
        zimbraServerDir = Strings.nullToEmpty(zimbraServerDir);
        System.setProperty("log4j.configuration", "log4j-test.properties");
        // Don't load from /opt/zimbra/conf
        System.setProperty("zimbra.config", zimbraServerDir + "src/java-test/localconfig-test.xml");
        LC.reload();
        LC.zimbra_attrs_directory.setDefault(zimbraServerDir + "conf/attrs");
        LC.zimbra_rights_directory.setDefault(zimbraServerDir + "conf/rights");
        LC.timezone_file.setDefault(zimbraServerDir + "conf/timezones.ics");

        // default MIME handlers are now set up in MockProvisioning constructor
        Provisioning.setInstance(new MockProvisioning());
    }

    /**
     * Initializes the provisioning, database, index and store manager.
     */
    public static void initServer() throws Exception {
        initServer(MockStoreManager.class);
    }

    /**
     * Initializes the provisioning, database, index and store manager.
     * @param zimbraServerDir the directory that contains the ZimbraServer project
     * @throws Exception
     */
    public static void initServer(String zimbraServerDir) throws Exception {
        initServer(MockStoreManager.class, zimbraServerDir);
    }

    public static void initServer(Class<? extends StoreManager> storeManagerClass) throws Exception {
        initServer(storeManagerClass, "");
    }

    public static void initServer(Class<? extends StoreManager> storeManagerClass, String zimbraServerDir, boolean OctopusInstance) throws Exception {
        initProvisioning(zimbraServerDir);

        LC.zimbra_class_database.setDefault(HSQLDB.class.getName());
        DbPool.startup();
        HSQLDB.createDatabase(zimbraServerDir, OctopusInstance);

        MailboxManager.setInstance(null);
        MailboxIndex.setIndexStoreFactory("lucene");

        LC.zimbra_class_store.setDefault(storeManagerClass.getName());
        StoreManager.getInstance().startup();
    }

    public static void initServer(Class<? extends StoreManager> storeManagerClass, String zimbraServerDir) throws Exception {
        initProvisioning(zimbraServerDir);

        LC.zimbra_class_database.setDefault(HSQLDB.class.getName());
        DbPool.startup();
        HSQLDB.createDatabase(zimbraServerDir, false);

        MailboxManager.setInstance(null);
        MailboxIndex.setIndexStoreFactory("lucene");

        LC.zimbra_class_store.setDefault(storeManagerClass.getName());
        StoreManager.getInstance().startup();
    }

    /**
     * Clears the database and index.
     */
    public static void clearData() throws Exception {
        clearData("");
    }

    /**
     * Clears the database and index.
     * @param zimbraServerDir the directory that contains the ZimbraServer project
     */
    public static void clearData(String zimbraServerDir) throws Exception {
        HSQLDB.clearDatabase(zimbraServerDir);
        MailboxManager.getInstance().clearCache();
        MailboxIndex.shutdown();
        File index = new File("build/test/index");
        if (index.isDirectory()) {
            deleteDirContents(index);
        }
        StoreManager sm = StoreManager.getInstance();
        if (sm instanceof MockStoreManager) {
            ((MockStoreManager) sm).purge();
        } else if (sm instanceof MockHttpStoreManager) {
            MockHttpStore.purge();
        }
        DocumentHandler.resetLocalHost();
    }

    private static void deleteDirContents(File dir) throws IOException {
        deleteDirContents(dir, 0);
    }

    private static void deleteDirContents(File dir, int recurCount) throws IOException {
        try {
            Files.deleteDirectoryContents(dir);
        } catch (IOException ioe) {
            if (recurCount > 10) {
                throw new IOException("Gave up after multiple IOExceptions", ioe);
            }
            ZimbraLog.test.info("delete dir failed due to IOException (probably files still in use). Waiting a moment and trying again");
            //wait a moment and try again; this can bomb if files still being written by some thread
            try {
                Thread.sleep(2500);
            } catch (InterruptedException ie) {

            }
            deleteDirContents(dir, recurCount+1);
        }

    }

    public static void setFlag(Mailbox mbox, int itemId, Flag.FlagInfo flag) throws ServiceException {
        MailItem item = mbox.getItemById(null, itemId, MailItem.Type.UNKNOWN);
        int flags = item.getFlagBitmask() | flag.toBitmask();
        mbox.setTags(null, itemId, item.getType(), flags, null, null);
    }

    public static void unsetFlag(Mailbox mbox, int itemId, Flag.FlagInfo flag) throws ServiceException {
        MailItem item = mbox.getItemById(null, itemId, MailItem.Type.UNKNOWN);
        int flags = item.getFlagBitmask() & ~flag.toBitmask();
        mbox.setTags(null, itemId, item.getType(), flags, null, null);
    }

    public static void index(Mailbox mbox) throws ServiceException {
        mbox.index.indexDeferredItems();
    }

    public static ParsedMessage generateMessage(String subject) throws Exception {
        MimeMessage mm = new Mime.FixedMimeMessage(JMSession.getSession());
        mm.setHeader("From", "Bob Evans <bob@example.com>");
        mm.setHeader("To", "Jimmy Dean <jdean@example.com>");
        mm.setHeader("Subject", subject);
        mm.setText("nothing to see here");
        return new ParsedMessage(mm, false);
    }

    public static ParsedMessage generateHighPriorityMessage(String subject) throws Exception {
        MimeMessage mm = new Mime.FixedMimeMessage(JMSession.getSession());
        mm.setHeader("From", "Hi Bob <bob@example.com>");
        mm.setHeader("To", "Jimmy Dean <jdean@example.com>");
        mm.setHeader("Subject", subject);
        mm.addHeader("Importance", "high");
        mm.setText("nothing to see here");
        return new ParsedMessage(mm, false);
    }

    public static ParsedMessage generateLowPriorityMessage(String subject) throws Exception {
        MimeMessage mm = new Mime.FixedMimeMessage(JMSession.getSession());
        mm.setHeader("From", "Lo Bob <bob@example.com>");
        mm.setHeader("To", "Jimmy Dean <jdean@example.com>");
        mm.setHeader("Subject", subject);
        mm.addHeader("Importance", "low");
        mm.setText("nothing to see here");
        return new ParsedMessage(mm, false);
    }

    public static ParsedMessage generateMessageWithAttachment(String subject) throws Exception {
        MimeMessage mm = new Mime.FixedMimeMessage(JMSession.getSession());
        mm.setHeader("From", "Vera Oliphant <oli@example.com>");
        mm.setHeader("To", "Jimmy Dean <jdean@example.com>");
        mm.setHeader("Subject", subject);
        mm.setText("Good as gold");
        MimeMultipart multi = new ZMimeMultipart("mixed");
        ContentDisposition cdisp = new ContentDisposition(Part.ATTACHMENT);
        cdisp.setParameter("filename", "fun.txt");

        ZMimeBodyPart bp = new ZMimeBodyPart();
        // MimeBodyPart.setDataHandler() invalidates Content-Type and CTE if there is any, so make sure
        // it gets called before setting Content-Type and CTE headers.
        try {
            bp.setDataHandler(new DataHandler(new ByteArrayDataSource("Feeling attached.", "text/plain")));
        } catch (IOException e) {
            throw new MessagingException("could not generate mime part content", e);
        }
        bp.addHeader("Content-Disposition", cdisp.toString());
        bp.addHeader("Content-Type", "text/plain");
        bp.addHeader("Content-Transfer-Encoding", MimeConstants.ET_8BIT);
        multi.addBodyPart(bp);

        mm.setContent(multi);
        mm.saveChanges();

        return new ParsedMessage(mm, false);
    }

    public static Invite generateInvite(Account account, String fragment,
                ZVCalendar cals) throws Exception {

        List<Invite> invites = Invite.createFromCalendar(account, fragment, cals,
            true);

        return invites.get(0);
    }
}
