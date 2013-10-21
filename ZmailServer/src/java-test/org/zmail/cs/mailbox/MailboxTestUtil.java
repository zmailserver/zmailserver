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

package org.zmail.cs.mailbox;

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
import org.zmail.common.calendar.ZCalendar.ZVCalendar;
import org.zmail.common.localconfig.LC;
import org.zmail.common.mime.ContentDisposition;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.zmime.ZMimeBodyPart;
import org.zmail.common.zmime.ZMimeMultipart;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.db.DbPool;
import org.zmail.cs.db.HSQLDB;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.store.MockStoreManager;
import org.zmail.cs.store.StoreManager;
import org.zmail.cs.store.http.HttpStoreManagerTest.MockHttpStoreManager;
import org.zmail.cs.store.http.MockHttpStore;
import org.zmail.cs.util.JMSession;
import org.zmail.soap.DocumentHandler;

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
     * @param zmailServerDir the directory that contains the ZmailServer project
     * @throws Exception
     */
    public static void initProvisioning(String zmailServerDir) throws Exception {
        zmailServerDir = Strings.nullToEmpty(zmailServerDir);
        System.setProperty("log4j.configuration", "log4j-test.properties");
        // Don't load from /opt/zmail/conf
        System.setProperty("zmail.config", zmailServerDir + "src/java-test/localconfig-test.xml");
        LC.reload();
        LC.zmail_attrs_directory.setDefault(zmailServerDir + "conf/attrs");
        LC.zmail_rights_directory.setDefault(zmailServerDir + "conf/rights");
        LC.timezone_file.setDefault(zmailServerDir + "conf/timezones.ics");

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
     * @param zmailServerDir the directory that contains the ZmailServer project
     * @throws Exception
     */
    public static void initServer(String zmailServerDir) throws Exception {
        initServer(MockStoreManager.class, zmailServerDir);
    }

    public static void initServer(Class<? extends StoreManager> storeManagerClass) throws Exception {
        initServer(storeManagerClass, "");
    }

    public static void initServer(Class<? extends StoreManager> storeManagerClass, String zmailServerDir, boolean OctopusInstance) throws Exception {
        initProvisioning(zmailServerDir);

        LC.zmail_class_database.setDefault(HSQLDB.class.getName());
        DbPool.startup();
        HSQLDB.createDatabase(zmailServerDir, OctopusInstance);

        MailboxManager.setInstance(null);
        MailboxIndex.setIndexStoreFactory("lucene");

        LC.zmail_class_store.setDefault(storeManagerClass.getName());
        StoreManager.getInstance().startup();
    }

    public static void initServer(Class<? extends StoreManager> storeManagerClass, String zmailServerDir) throws Exception {
        initProvisioning(zmailServerDir);

        LC.zmail_class_database.setDefault(HSQLDB.class.getName());
        DbPool.startup();
        HSQLDB.createDatabase(zmailServerDir, false);

        MailboxManager.setInstance(null);
        MailboxIndex.setIndexStoreFactory("lucene");

        LC.zmail_class_store.setDefault(storeManagerClass.getName());
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
     * @param zmailServerDir the directory that contains the ZmailServer project
     */
    public static void clearData(String zmailServerDir) throws Exception {
        HSQLDB.clearDatabase(zmailServerDir);
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
            ZmailLog.test.info("delete dir failed due to IOException (probably files still in use). Waiting a moment and trying again");
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
