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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.ZAttrProvisioning.MailThreadingAlgorithm;
import com.zimbra.common.localconfig.LC;
import com.zimbra.common.mime.InternetAddress;
import com.zimbra.common.util.ArrayUtil;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.MockProvisioning;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.index.BrowseTerm;
import com.zimbra.cs.mailbox.util.TypedIdList;
import com.zimbra.cs.mime.ParsedMessage;
import com.zimbra.cs.session.PendingModifications;
import com.zimbra.cs.session.PendingModifications.ModificationKey;
import com.zimbra.cs.store.MockStoreManager;
import com.zimbra.cs.store.StoreManager;

/**
 * Unit test for {@link Mailbox}.
 *
 * @author ysasaki
 */
public final class MailboxTest {

    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();
        prov.createAccount("test@zimbra.com", "secret", new HashMap<String, Object>());
    }

    @Before
    public void setUp() throws Exception {
        MailboxTestUtil.clearData();
    }

    public static final DeliveryOptions STANDARD_DELIVERY_OPTIONS = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX);

    @Test
    public void browse() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX);
        mbox.addMessage(null, new ParsedMessage("From: test1-1@sub1.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test1-2@sub1.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test1-3@sub1.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test1-4@sub1.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test2-1@sub2.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test2-2@sub2.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test2-3@sub2.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test3-1@sub3.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test3-2@sub3.zimbra.com".getBytes(), false), dopt, null);
        mbox.addMessage(null, new ParsedMessage("From: test4-1@sub4.zimbra.com".getBytes(), false), dopt, null);
        mbox.index.indexDeferredItems();

        List<BrowseTerm> terms = mbox.browse(null, Mailbox.BrowseBy.domains, null, 100);
        Assert.assertEquals(4, terms.size());
        Assert.assertEquals("sub1.zimbra.com", terms.get(0).getText());
        Assert.assertEquals("sub2.zimbra.com", terms.get(1).getText());
        Assert.assertEquals("sub3.zimbra.com", terms.get(2).getText());
        Assert.assertEquals("sub4.zimbra.com", terms.get(3).getText());
        Assert.assertEquals(8, terms.get(0).getFreq());
        Assert.assertEquals(6, terms.get(1).getFreq());
        Assert.assertEquals(4, terms.get(2).getFreq());
        Assert.assertEquals(2, terms.get(3).getFreq());
    }

    @Test
    public void threadDraft() throws Exception {
        Account acct = Provisioning.getInstance().getAccount("test@zimbra.com");
        acct.setMailThreadingAlgorithm(MailThreadingAlgorithm.subject);

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);

        // setup: add the root message
        ParsedMessage pm = MailboxTestUtil.generateMessage("test subject");
        int rootId = mbox.addMessage(null, pm, STANDARD_DELIVERY_OPTIONS, null).getId();

        // first draft explicitly references the parent by item ID (how ZWC does it)
        pm = MailboxTestUtil.generateMessage("Re: test subject");
        Message draft = mbox.saveDraft(null, pm, Mailbox.ID_AUTO_INCREMENT, rootId + "", MailSender.MSGTYPE_REPLY, null, null, 0);
        Message parent = mbox.getMessageById(null, rootId);
        Assert.assertEquals("threaded explicitly", parent.getConversationId(), draft.getConversationId());

        // second draft implicitly references the parent by default threading rules
        pm = MailboxTestUtil.generateMessage("Re: test subject");
        draft = mbox.saveDraft(null, pm, Mailbox.ID_AUTO_INCREMENT);
        parent = mbox.getMessageById(null, rootId);
        Assert.assertEquals("threaded implicitly [saveDraft]", parent.getConversationId(), draft.getConversationId());

        // threading is set up at first save time, so modifying the second draft should *not* affect threading
        pm = MailboxTestUtil.generateMessage("Re: changed the subject");
        draft = mbox.saveDraft(null, pm, draft.getId());
        parent = mbox.getMessageById(null, rootId);
        Assert.assertEquals("threaded implicitly [resaved]", parent.getConversationId(), draft.getConversationId());

        // third draft is like second draft, but goes via Mailbox.addMessage (how IMAP does it)
        pm = MailboxTestUtil.generateMessage("Re: test subject");
        DeliveryOptions dopt = new DeliveryOptions().setFlags(Flag.BITMASK_DRAFT).setFolderId(Mailbox.ID_FOLDER_DRAFTS);
        draft = mbox.addMessage(null, pm, dopt, null);
        parent = mbox.getMessageById(null, rootId);
        Assert.assertEquals("threaded implicitly [addMessage]", parent.getConversationId(), draft.getConversationId());

        // fourth draft explicitly references the parent by item ID, even though it wouldn't get threaded using the default threader
        pm = MailboxTestUtil.generateMessage("changed the subject");
        draft = mbox.saveDraft(null, pm, Mailbox.ID_AUTO_INCREMENT, rootId + "", MailSender.MSGTYPE_REPLY, null, null, 0);
        parent = mbox.getMessageById(null, rootId);
        Assert.assertEquals("threaded explicitly (changed subject)", parent.getConversationId(), draft.getConversationId());

        // fifth draft is not related to the parent and should not be threaded
        pm = MailboxTestUtil.generateMessage("Re: unrelated subject");
        draft = mbox.saveDraft(null, pm, Mailbox.ID_AUTO_INCREMENT);
        Assert.assertEquals("unrelated", -draft.getId(), draft.getConversationId());
    }

    @Test
    public void trimTombstones() throws Exception {
        Account acct = Provisioning.getInstance().getAccount("test@zimbra.com");
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);

        // add a message
        int changeId1 = mbox.getLastChangeID();
        int msgId = mbox.addMessage(null, MailboxTestUtil.generateMessage("foo"), STANDARD_DELIVERY_OPTIONS, null).getId();

        // turn on sync tracking -- tombstone table should be empty
        mbox.beginTrackingSync();
        int changeId2 = mbox.getLastChangeID();
        Assert.assertTrue("no changes", mbox.getTombstones(changeId2).isEmpty());

        // verify that we can't use a sync token from *before* sync tracking was enabled
        try {
            mbox.getTombstones(changeId1);
            Assert.fail("too-early sync token");
        } catch (MailServiceException e) {
            Assert.assertEquals("too-early sync token", e.getCode(), MailServiceException.MUST_RESYNC);
        }

        // delete the message and check that it generated a tombstone
        mbox.delete(null, msgId, MailItem.Type.MESSAGE);
        int changeId3 = mbox.getLastChangeID();
        Assert.assertTrue("deleted item in tombstones", mbox.getTombstones(changeId2).contains(msgId));
        Assert.assertTrue("no changes since delete", mbox.getTombstones(changeId3).isEmpty());

        // purge the account with the default tombstone purge lifetime (3 months)
        mbox.purgeMessages(null);
        Assert.assertTrue("deleted item still in tombstones", mbox.getTombstones(changeId2).contains(msgId));

        // purge the account and all its tombstones
        LC.tombstone_max_age_ms.setDefault(0);
        mbox.purgeMessages(null);
        try {
            mbox.getTombstones(changeId2);
            Assert.fail("sync token predates purged tombstone");
        } catch (MailServiceException e) {
            Assert.assertEquals("sync token predates purged tombstone", e.getCode(), MailServiceException.MUST_RESYNC);
        }
        Assert.assertTrue("sync token matches last purged tombstone", mbox.getTombstones(changeId3).isEmpty());
    }

    static class MockListener extends MailboxListener {
        PendingModifications pms;

        @Override public void notify(ChangeNotification notification) {
            this.pms = notification.mods;
        }
    }

    @Test
    public void notifications() throws Exception {
        Account acct = Provisioning.getInstance().getAccount("test@zimbra.com");
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);

        MockListener ml = new MockListener();
        MailboxListener.register(ml);

        try {
            Folder f = mbox.createFolder(null, "foo", new Folder.FolderOptions().setDefaultView(MailItem.Type.MESSAGE));
            Folder fParent = (Folder) f.getParent();

            ModificationKey fkey = new ModificationKey(f);
            ModificationKey fParentKey = new ModificationKey(fParent);

            Assert.assertNull("no deletes after create", ml.pms.deleted);

            Assert.assertNotNull("creates aren't null", ml.pms.created);
            Assert.assertEquals("one created folder", 1, ml.pms.created.size());
            Assert.assertNotNull("created folder has entry", ml.pms.created.get(fkey));
            Assert.assertEquals("created folder matches created entry", f.getId(), ml.pms.created.get(fkey).getId());

            Assert.assertNotNull("modifications aren't null", ml.pms.modified);
            Assert.assertEquals("one modified folder", 1, ml.pms.modified.size());
            PendingModifications.Change pModification = ml.pms.modified.get(fParentKey);
            Assert.assertNotNull("parent folder modified", pModification);
            Assert.assertEquals("parent folder matches modified entry",
                                fParent.getId(), ((Folder) pModification.what).getId());
            Assert.assertNotNull("preModifyObj is not null", pModification.preModifyObj);
            Assert.assertEquals("preModifyObj is a snapshot of parent folder",
                                fParent.getId(), ((Folder) pModification.preModifyObj).getId());

            DeliveryOptions dopt = new DeliveryOptions().setFolderId(f.getId());
            Message m = mbox.addMessage(null, MailboxTestUtil.generateMessage("test subject"), dopt, null);
            ModificationKey mkey = new ModificationKey(m);

            mbox.delete(null, f.getId(), MailItem.Type.FOLDER);

            Assert.assertNull("no creates after delete", ml.pms.created);

            Assert.assertNotNull("deletes aren't null", ml.pms.deleted);
            Assert.assertEquals("one deleted folder, one deleted message, one deleted vconv", 3, ml.pms.deleted.size());
            PendingModifications.Change fDeletion = ml.pms.deleted.get(fkey);
            Assert.assertNotNull("deleted folder has entry", fDeletion);
            Assert.assertTrue("deleted folder matches deleted entry",
                              f.getType() == fDeletion.what && f.getId() == ((Folder) fDeletion.preModifyObj).getId());
            PendingModifications.Change mDeletion = ml.pms.deleted.get(mkey);
            Assert.assertNotNull("deleted message has entry", mDeletion);
            // Note that preModifyObj may be null for the deleted message, so just check for the type
            Assert.assertTrue("deleted message matches deleted entry", m.getType() == mDeletion.what);

            Assert.assertNotNull("modifications aren't null", ml.pms.modified);
            Assert.assertEquals("parent folder modified, mailbox size modified", 2, ml.pms.modified.size());
            pModification = ml.pms.modified.get(fParentKey);
            Assert.assertNotNull("parent folder modified", pModification);
            Assert.assertEquals("parent folder matches modified entry",
                                fParent.getId(), ((Folder) pModification.what).getId());
            Assert.assertNotNull("preModifyObj is not null", pModification.preModifyObj);
            Assert.assertEquals("preModifyObj is a snapshot of parent folder",
                                fParent.getId(), ((Folder) pModification.preModifyObj).getId());
        } finally {
            MailboxListener.unregister(ml);
        }
    }

    @Test
    public void dumpster() throws Exception {
        Account acct = Provisioning.getInstance().getAccount("test@zimbra.com");
        acct.setDumpsterEnabled(true);

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);

        int msgId = mbox.addMessage(null, MailboxTestUtil.generateMessage("test"), STANDARD_DELIVERY_OPTIONS, null).getId();

        mbox.index.indexDeferredItems();

        mbox.delete(null, msgId, MailItem.Type.MESSAGE);
        mbox.recover(null, new int[] { msgId }, MailItem.Type.MESSAGE, Mailbox.ID_FOLDER_INBOX);
    }

    @Test
    public void deleteMailbox() throws Exception {
        MockStoreManager sm = (MockStoreManager) StoreManager.getInstance();

        // first test normal mailbox delete
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Assert.assertEquals("start with no blobs in the store", 0, sm.size());

        MailItem item = mbox.addMessage(null, MailboxTestUtil.generateMessage("test"), STANDARD_DELIVERY_OPTIONS, null);
        Assert.assertEquals("1 blob in the store", 1, sm.size());
        // Index the mailbox so that mime message gets cached
        mbox.index.indexDeferredItems();
        // make sure digest is in message cache.
        Assert.assertTrue(MessageCache.contains(item.getDigest()));

        mbox.deleteMailbox();
        Assert.assertEquals("end with no blobs in the store", 0, sm.size());
        // make sure digest is removed from message cache.
        Assert.assertFalse(MessageCache.contains(item.getDigest()));


        // then test mailbox delete without store delete
        mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Assert.assertEquals("start with no blobs in the store", 0, sm.size());

        item = mbox.addMessage(null, MailboxTestUtil.generateMessage("test"), STANDARD_DELIVERY_OPTIONS, null);
        Assert.assertEquals("1 blob in the store", 1, sm.size());

        // Index the mailbox so that mime message gets cached
        mbox.index.indexDeferredItems();
        // make sure digest is in message cache.
        Assert.assertTrue(MessageCache.contains(item.getDigest()));

        mbox.deleteMailbox(Mailbox.DeleteBlobs.NEVER);
        Assert.assertEquals("end with 1 blob in the store", 1, sm.size());
        // make sure digest is still present in message cache.
        Assert.assertTrue(MessageCache.contains(item.getDigest()));
        sm.purge();


        // then do it contingent on whether the store is centralized or local
        mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Assert.assertEquals("start with no blobs in the store", 0, sm.size());

        mbox.addMessage(null, MailboxTestUtil.generateMessage("test"), STANDARD_DELIVERY_OPTIONS, null).getId();
        Assert.assertEquals("1 blob in the store", 1, sm.size());

        mbox.deleteMailbox(Mailbox.DeleteBlobs.UNLESS_CENTRALIZED);
        int expected = StoreManager.getInstance().supports(StoreManager.StoreFeature.CENTRALIZED) ? 1 : 0;
        Assert.assertEquals("end with " + expected + " blob(s) in the store", expected, sm.size());
        sm.purge();
    }

    @Test
    public void muted() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        // root message
        DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX).setFlags(Flag.BITMASK_UNREAD);
        Message msg = mbox.addMessage(null, MailboxTestUtil.generateMessage("test subject"), dopt, null);
        Assert.assertTrue("root unread", msg.isUnread());
        Assert.assertFalse("root not muted", msg.isTagged(Flag.FlagInfo.MUTED));
        Assert.assertTrue("root in virtual conv", msg.getConversationId() < 0);

        // mark root muted
        mbox.alterTag(null, msg.getId(), MailItem.Type.MESSAGE, Flag.FlagInfo.MUTED, true, null);
        msg = mbox.getMessageById(null, msg.getId());
        Assert.assertTrue("root unread", msg.isUnread());
        Assert.assertTrue("root muted", msg.isTagged(Flag.FlagInfo.MUTED));
        Assert.assertTrue("root in virtual conv", msg.getConversationId() < 0);
        Assert.assertTrue("virtual conv muted", mbox.getConversationById(null, msg.getConversationId()).isTagged(Flag.FlagInfo.MUTED));

        // add a reply to the muted virtual conversation
        dopt.setConversationId(msg.getConversationId());
        Message msg2 = mbox.addMessage(null, MailboxTestUtil.generateMessage("Re: test subject"), dopt, null);
        Assert.assertFalse("reply read", msg2.isUnread());
        Assert.assertTrue("reply muted", msg2.isTagged(Flag.FlagInfo.MUTED));
        Assert.assertFalse("reply in real conv", msg2.getConversationId() < 0);
        Assert.assertTrue("real conversation muted", mbox.getConversationById(null, msg2.getConversationId()).isTagged(Flag.FlagInfo.MUTED));

        // add another reply to the now-real still-muted conversation
        dopt.setConversationId(msg2.getConversationId());
        Message msg3 = mbox.addMessage(null, MailboxTestUtil.generateMessage("Re: test subject"), dopt, null);
        Assert.assertFalse("second reply read", msg3.isUnread());
        Assert.assertTrue("second reply muted", msg3.isTagged(Flag.FlagInfo.MUTED));
        Assert.assertFalse("second reply in real conv", msg3.getConversationId() < 0);
        Assert.assertTrue("real conversation muted", mbox.getConversationById(null, msg3.getConversationId()).isTagged(Flag.FlagInfo.MUTED));

        // unmute conversation
        mbox.alterTag(null, msg3.getConversationId(), MailItem.Type.CONVERSATION, Flag.FlagInfo.MUTED, false, null);
        msg3 = mbox.getMessageById(null, msg3.getId());
        Assert.assertFalse("second reply not muted", msg3.isTagged(Flag.FlagInfo.MUTED));
        Assert.assertFalse("real conversation not muted", mbox.getConversationById(null, msg3.getConversationId()).isTagged(Flag.FlagInfo.MUTED));

        // add a last reply to the now-unmuted conversation
        Message msg4 = mbox.addMessage(null, MailboxTestUtil.generateMessage("Re: test subject"), dopt, null);
        Assert.assertTrue("third reply unread", msg4.isUnread());
        Assert.assertFalse("third reply not muted", msg4.isTagged(Flag.FlagInfo.MUTED));
        Assert.assertFalse("third reply in real conv", msg4.getConversationId() < 0);
        Assert.assertFalse("real conversation not muted", mbox.getConversationById(null, msg4.getConversationId()).isTagged(Flag.FlagInfo.MUTED));
    }

    @Test
    public void tombstones() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        mbox.beginTrackingSync();
        int token = mbox.getLastChangeID();

        Document doc1 = DocumentTest.createDocument(mbox, "doc1", "abcdefg", false);
        Document doc2 = DocumentTest.createDocument(mbox, "doc2", "tuvwxyz", false);

        Set<Integer> ids = Sets.newHashSet(doc1.getId(), doc2.getId());
        Set<String> uuids = Sets.newHashSet(doc1.getUuid(), doc2.getUuid());
        Assert.assertEquals("2 different UUIDs", 2, uuids.size());

        mbox.delete(null, ArrayUtil.toIntArray(ids), MailItem.Type.DOCUMENT, null);

        TypedIdList tombstones = mbox.getTombstones(token);
        Assert.assertEquals("2 tombstones", 2, tombstones.size());
        for (Map.Entry<MailItem.Type, List<TypedIdList.ItemInfo>> row : tombstones) {
            Assert.assertEquals("all tombstones are for Documents", MailItem.Type.DOCUMENT, row.getKey());
            for (TypedIdList.ItemInfo iinfo : row.getValue()) {
                Assert.assertTrue(iinfo + ": id contained in set", ids.remove(iinfo.getId()));
                Assert.assertTrue(iinfo + ": uuid contained in set", uuids.remove(iinfo.getUuid()));
            }
        }


        token = mbox.getLastChangeID();

        Message msg = mbox.addMessage(null, MailboxTestUtil.generateMessage("test subject"), STANDARD_DELIVERY_OPTIONS, null);
        Document doc3 = DocumentTest.createDocument(mbox, "doc3", "lmnop", false);
        Folder folder = mbox.createFolder(null, "test", new Folder.FolderOptions());

        ids = Sets.newHashSet(doc3.getId(), msg.getId(), folder.getId());
        uuids = Sets.newHashSet(doc3.getUuid(), msg.getUuid(), folder.getUuid());
        Assert.assertEquals("3 different UUIDs", 3, uuids.size());

        mbox.move(null, new int[] { doc3.getId(), msg.getId() }, MailItem.Type.UNKNOWN, folder.getId(), null);
        mbox.delete(null, folder.getId(), MailItem.Type.FOLDER, null);

        tombstones = mbox.getTombstones(token);
        Assert.assertEquals("3 tombstones", 3, tombstones.size());
        for (Map.Entry<MailItem.Type, List<TypedIdList.ItemInfo>> row : tombstones) {
            Assert.assertTrue("expected tombstone types", EnumSet.of(MailItem.Type.FOLDER, MailItem.Type.MESSAGE, MailItem.Type.DOCUMENT).contains(row.getKey()));
            Assert.assertEquals("1 tombstone per type", 1, row.getValue().size());
            for (TypedIdList.ItemInfo iinfo : row.getValue()) {
                Assert.assertTrue(iinfo + ": id contained in set", ids.remove(iinfo.getId()));
                Assert.assertTrue(iinfo + ": uuid contained in set", uuids.remove(iinfo.getUuid()));
            }
        }
    }

    @Test
    public void createAutoContactTestWhenMaxEntriesLimitIsReached() throws Exception {

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Collection<InternetAddress> addrs = new ArrayList<InternetAddress>();
        addrs.add(new InternetAddress("user2@email.com"));
        addrs.add(new InternetAddress("user3@email.com"));
        addrs.add(new InternetAddress("user4@email.com"));

        Provisioning prov = Provisioning.getInstance();
        Account acct1 = Provisioning.getInstance().get(Key.AccountBy.id, MockProvisioning.DEFAULT_ACCOUNT_ID);
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraContactMaxNumEntries, Integer.toString(2));
        prov.modifyAttrs(acct1, attrs);
        List<Contact> contactList = mbox.createAutoContact(null, addrs);
        assertEquals(2, contactList.size());


        attrs.put(Provisioning.A_zimbraContactMaxNumEntries, Integer.toString(10));
        prov.modifyAttrs(acct1, attrs);
        addrs = new ArrayList<InternetAddress>();
        addrs.add(new InternetAddress("user2@email.com"));
        addrs.add(new InternetAddress("user3@email.com"));
        addrs.add(new InternetAddress("user4@email.com"));
        contactList = mbox.createAutoContact(null, addrs);
        assertEquals(3, contactList.size());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        MailboxTestUtil.clearData();

    }
}
