/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.db;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.mailbox.DeliveryOptions;
import org.zmail.cs.mailbox.Document;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mime.ParsedDocument;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.store.MailboxBlob.MailboxBlobInfo;
import org.zmail.cs.store.StoreManager;
import org.zmail.cs.store.file.BlobReference;
import org.zmail.cs.store.file.FileBlobStore;
import org.zmail.cs.util.SpoolingCache;
import org.zmail.cs.volume.Volume;
import org.zmail.cs.volume.VolumeManager;

public class DbVolumeBlobsTest {

    private DbConnection conn;
    private StoreManager originalStoreManager;
    private Volume originalVolume;

    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();
        prov.createAccount("test@zmail.com", "secret", new HashMap<String, Object>());
        System.setProperty("zmail.native.required", "false");

        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailId, UUID.randomUUID().toString());

        prov.createAccount("test2@zmail.com", "secret", attrs);
        //need MVCC since the VolumeManager code creates connections internally
        HSQLDB db = (HSQLDB) Db.getInstance();
        db.useMVCC(null);
    }

    @Before
    public void setUp() throws Exception {
        MailboxTestUtil.clearData();
        conn = DbPool.getConnection();
        originalStoreManager = StoreManager.getInstance();
        originalVolume = VolumeManager.getInstance().getCurrentMessageVolume();
        StoreManager.setInstance(new FileBlobStore());
        StoreManager.getInstance().startup();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();
        if (VolumeManager.getInstance().getCurrentMessageVolume() != originalVolume) {
            ZmailLog.store.info("setting back to original volume");
            VolumeManager.getInstance().setCurrentVolume(Volume.TYPE_MESSAGE, originalVolume.getId());
        }
        StoreManager.getInstance().shutdown();
        StoreManager.setInstance(originalStoreManager);
    }

    private String getPath(BlobReference ref) throws ServiceException {
        return FileBlobStore.getBlobPath(ref.getMailboxId(), ref.getItemId(), ref.getRevision(), ref.getVolumeId());
    }

    @Test
    public void writeBlobInfo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Message msg = mbox.addMessage(null, new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false), opt, null);

        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        DbVolumeBlobs.addBlobReference(conn, mbox, vol, msg);

        String digest = msg.getBlob().getDigest();
        String path = msg.getBlob().getLocalBlob().getFile().getPath();
        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);
        Assert.assertEquals(1, blobs.size());
        BlobReference ref = blobs.get(0);

        Assert.assertEquals(path, getPath(ref));
    }
    
    @Test
    public void testDuplicateRow() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Message msg = mbox.addMessage(null, new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false), opt, null);

        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();
        MailboxBlobInfo blobInfo = new MailboxBlobInfo(null, mbox.getId(), msg.getId(), msg.getSavedSequence(), String.valueOf(vol.getId()), null);
        DbVolumeBlobs.addBlobReference(conn, blobInfo);
        try {
            DbVolumeBlobs.addBlobReference(conn, blobInfo);
            Assert.fail("expected exception");
        } catch (ServiceException e) {
           // expected
        }
    }
    
    @Test
    public void testIncrementalBlobs() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        int ts1 = (int) (System.currentTimeMillis()/1000);
        Message msg1 = mbox.addMessage(null, new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false), opt, null);
        Thread.sleep(1000);
        int ts2 = (int) (System.currentTimeMillis()/1000);
        Message msg2 = mbox.addMessage(null, new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false), opt, null);
        Thread.sleep(1000);
        int ts3 = (int) (System.currentTimeMillis()/1000);
        Iterable<MailboxBlobInfo> allBlobs = null;
        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), ts1, ts2);
        Assert.assertEquals(msg1.getId(), allBlobs.iterator().next().itemId);
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), ts2, ts3);
        Assert.assertEquals(msg2.getId(), allBlobs.iterator().next().itemId);
    }

    @Test
    public void writeAllBlobRefs() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Map<String, String> digestToPath = new HashMap<String, String>();
        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        for (int i = 0; i < 10; i++) {
            Message msg = mbox.addMessage(null, new ParsedMessage(("From: from" + i + "@zmail.com\r\nTo: to1@zmail.com").getBytes(), false), opt, null);
            digestToPath.put(msg.getDigest(), msg.getBlob().getLocalBlob().getFile().getPath());
        }
        Iterable<MailboxBlobInfo> allBlobs = null;
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), -1, -1);
        for (MailboxBlobInfo info : allBlobs) {
            DbVolumeBlobs.addBlobReference(conn, info);
        }

        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(digestToPath.size(), blobs.size());
        for (BlobReference blob : blobs) {
            String path = digestToPath.remove(blob.getDigest());
            Assert.assertNotNull(path);
            Assert.assertEquals(path, getPath(blob));
        }

        Assert.assertTrue(digestToPath.isEmpty());
    }
    
    @Test
    public void testUniqueBlobDigests() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        for (int i = 0; i < 5; i++) {
            mbox.addMessage(null, new ParsedMessage(("From: from" + i + "@zmail.com\r\nTo: to1@zmail.com").getBytes(), false), opt, null);
            mbox.addMessage(null, new ParsedMessage(("From: from" + i + "@zmail.com\r\nTo: to1@zmail.com").getBytes(), false), opt, null);
        }
        Iterable<MailboxBlobInfo> allBlobs = null;
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), -1, -1);
        for (MailboxBlobInfo info : allBlobs) {
            DbVolumeBlobs.addBlobReference(conn, info);
        }
        SpoolingCache<String> digests = DbVolumeBlobs.getUniqueDigests(conn, vol);
        Assert.assertEquals(5, digests.size());
    }

    @Test
    public void dumpsterBlobs() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Map<String, String> digestToPath = new HashMap<String, String>();
        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        for (int i = 0; i < 10; i++) {
            Message msg = mbox.addMessage(null, new ParsedMessage(("From: from" + i + "@zmail.com\r\nTo: to1@zmail.com").getBytes(), false), opt, null);
            digestToPath.put(msg.getDigest(), msg.getBlob().getLocalBlob().getFile().getPath());
            mbox.delete(null, msg.getId(), msg.getType());
        }

        mbox.emptyFolder(null, Mailbox.ID_FOLDER_TRASH, false);

        Iterable<MailboxBlobInfo> allBlobs = null;
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), -1, -1);
        for (MailboxBlobInfo info : allBlobs) {
            DbVolumeBlobs.addBlobReference(conn, info);
        }

        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(digestToPath.size(), blobs.size());
        for (BlobReference blob : blobs) {
            String path = digestToPath.remove(blob.getDigest());
            Assert.assertNotNull(path);
            Assert.assertEquals(path, getPath(blob));
        }

        Assert.assertTrue(digestToPath.isEmpty());
    }

    @Test
    public void revisionBlobs() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        Map<String, String> digestToPath = new HashMap<String, String>();
        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        InputStream in = new ByteArrayInputStream("testcontent".getBytes());
        ParsedDocument pd = new ParsedDocument(in, "docname", "text/plain", System.currentTimeMillis(), null, null);
        Document doc = mbox.createDocument(null, Mailbox.ID_FOLDER_BRIEFCASE, pd, MailItem.Type.DOCUMENT, 0);
        digestToPath.put(doc.getDigest(), doc.getBlob().getLocalBlob().getFile().getPath());

        int baseId = doc.getId();
        for (int i = 0; i < 10; i++) {
            in = new ByteArrayInputStream(("testcontent-new-"+i).getBytes());
            pd = new ParsedDocument(in, "docname", "text/plain", System.currentTimeMillis(), null, null);
            doc = mbox.addDocumentRevision(null, baseId, pd);
            digestToPath.put(doc.getDigest(), doc.getBlob().getLocalBlob().getFile().getPath());
        }

        Iterable<MailboxBlobInfo> allBlobs = null;
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), -1, -1);
        for (MailboxBlobInfo info : allBlobs) {
            DbVolumeBlobs.addBlobReference(conn, info);
        }

        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(digestToPath.size(), blobs.size());
        for (BlobReference blob : blobs) {
            String path = digestToPath.remove(blob.getDigest());
            Assert.assertNotNull(path);
            Assert.assertEquals(path, getPath(blob));
        }

        Assert.assertTrue(digestToPath.isEmpty());
    }

    @Test
    public void revisionDumpsterBlobs() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        Map<String, String> digestToPath = new HashMap<String, String>();
        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        InputStream in = new ByteArrayInputStream("testcontent".getBytes());
        ParsedDocument pd = new ParsedDocument(in, "docname", "text/plain", System.currentTimeMillis(), null, null);
        Document doc = mbox.createDocument(null, Mailbox.ID_FOLDER_BRIEFCASE, pd, MailItem.Type.DOCUMENT, 0);
        digestToPath.put(doc.getDigest(), doc.getBlob().getLocalBlob().getFile().getPath());

        int baseId = doc.getId();
        for (int i = 0; i < 10; i++) {
            in = new ByteArrayInputStream(("testcontent-new-"+i).getBytes());
            pd = new ParsedDocument(in, "docname", "text/plain", System.currentTimeMillis(), null, null);
            doc = mbox.addDocumentRevision(null, baseId, pd);
            digestToPath.put(doc.getDigest(), doc.getBlob().getLocalBlob().getFile().getPath());
        }

        mbox.delete(null, baseId, MailItem.Type.DOCUMENT);
        mbox.emptyFolder(null, Mailbox.ID_FOLDER_TRASH, false);

        Iterable<MailboxBlobInfo> allBlobs = null;
        allBlobs = DbMailItem.getAllBlobs(conn, mbox.getSchemaGroupId(), vol.getId(), -1, -1);
        for (MailboxBlobInfo info : allBlobs) {
            DbVolumeBlobs.addBlobReference(conn, info);
        }

        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(digestToPath.size(), blobs.size());
        for (BlobReference blob : blobs) {
            String path = digestToPath.remove(blob.getDigest());
            Assert.assertNotNull(path);
            Assert.assertEquals(path, getPath(blob));
        }

        Assert.assertTrue(digestToPath.isEmpty());
    }

    @Test
    public void deleteBlobRef() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Message msg = mbox.addMessage(null, new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false), opt, null);

        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        DbVolumeBlobs.addBlobReference(conn, mbox, vol, msg);

        String digest = msg.getBlob().getDigest();
        String path = msg.getBlob().getLocalBlob().getFile().getPath();
        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);
        Assert.assertEquals(1, blobs.size());
        Assert.assertEquals(path, getPath(blobs.get(0)));

        DbVolumeBlobs.deleteBlobRef(conn, blobs.get(0).getId());

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);
        Assert.assertEquals(0, blobs.size());
    }
    
    @Test
    public void deleteAllBlobRef() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        Message msg = mbox.addMessage(null, new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false), opt, null);

        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        DbVolumeBlobs.addBlobReference(conn, mbox, vol, msg);

        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(1, blobs.size());

        DbVolumeBlobs.deleteAllBlobRef(conn);

        blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(0, blobs.size());
    }

    @Test
    public void blobsByMbox() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        ParsedMessage pm = new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false);
        Message msg = mbox.addMessage(null, pm, opt, null);

        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        DbVolumeBlobs.addBlobReference(conn, mbox, vol, msg);



        String digest = msg.getBlob().getDigest();
        String path = msg.getBlob().getLocalBlob().getFile().getPath();
        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);
        Assert.assertEquals(1, blobs.size());
        Assert.assertEquals(path, getPath(blobs.get(0)));


        Account acct2 = Provisioning.getInstance().getAccount("test2@zmail.com");
        Mailbox mbox2 = MailboxManager.getInstance().getMailboxByAccount(acct2);
        Message msg2 = mbox2.addMessage(null, pm, opt, null);

        DbVolumeBlobs.addBlobReference(conn, mbox2, vol, msg2);

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);

        Set<String> paths = new HashSet<String>();
        paths.add(path);
        paths.add(msg2.getBlob().getLocalBlob().getFile().getPath());

        Assert.assertEquals(2, blobs.size());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
        }

        Assert.assertTrue(paths.isEmpty());

        DbVolumeBlobs.deleteBlobRef(conn, mbox);

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);
        Assert.assertEquals(1, blobs.size());
        BlobReference ref = blobs.get(0);
        path = msg2.getBlob().getLocalBlob().getFile().getPath();

        Assert.assertEquals(path, getPath(ref));
        Assert.assertEquals(mbox2.getId(), ref.getMailboxId());

    }

    @Test
    public void blobsByVolume() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        DeliveryOptions opt = new DeliveryOptions();
        opt.setFolderId(Mailbox.ID_FOLDER_INBOX);
        ParsedMessage pm = new ParsedMessage("From: from1@zmail.com\r\nTo: to1@zmail.com".getBytes(), false);
        Message msg = mbox.addMessage(null, pm, opt, null);

        Volume vol = VolumeManager.getInstance().getCurrentMessageVolume();

        DbVolumeBlobs.addBlobReference(conn, mbox, vol, msg);

        String volPath = vol.getRootPath().replace("store", "store2");
        File volFile = new File(volPath);
        volFile.mkdirs();

        Volume vol2 = Volume.builder().setPath(volFile.getAbsolutePath(), true)
            .setType(Volume.TYPE_MESSAGE).setName("volume2").build();

        vol2 = VolumeManager.getInstance().create(vol2);

        VolumeManager.getInstance().setCurrentVolume(Volume.TYPE_MESSAGE, vol2.getId());

        Message msg2 = mbox.addMessage(null, pm, opt, null);
        DbVolumeBlobs.addBlobReference(conn, mbox, vol2, msg2);

        String digest = msg.getBlob().getDigest();

        //add same msg to two different volumes

        List<BlobReference> blobs = DbVolumeBlobs.getBlobReferences(conn, vol);

        Assert.assertEquals(1, blobs.size());

        Set<String> paths = new HashSet<String>();
        paths.add(msg.getBlob().getLocalBlob().getFile().getPath());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
            Assert.assertEquals(vol.getId(), ref.getVolumeId());
        }

        blobs = DbVolumeBlobs.getBlobReferences(conn, vol2);

        Assert.assertEquals(1, blobs.size());

        paths = new HashSet<String>();
        paths.add(msg2.getBlob().getLocalBlob().getFile().getPath());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
            Assert.assertEquals(vol2.getId(), ref.getVolumeId());
        }

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol);

        paths = new HashSet<String>();
        paths.add(msg.getBlob().getLocalBlob().getFile().getPath());

        Assert.assertEquals(1, blobs.size());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
        }

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol2);

        paths = new HashSet<String>();
        paths.add(msg2.getBlob().getLocalBlob().getFile().getPath());

        Assert.assertEquals(1, blobs.size());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
        }

        //delete from vol1
        DbVolumeBlobs.deleteBlobRef(conn, vol);

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol2);

        paths = new HashSet<String>();
        paths.add(msg2.getBlob().getLocalBlob().getFile().getPath());

        Assert.assertEquals(1, blobs.size());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
        }

        blobs = DbVolumeBlobs.getBlobReferences(conn, vol);
        Assert.assertEquals(0, blobs.size());

        blobs = DbVolumeBlobs.getBlobReferences(conn, vol2);
        Assert.assertEquals(1, blobs.size());

        paths = new HashSet<String>();
        paths.add(msg2.getBlob().getLocalBlob().getFile().getPath());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
        }

        blobs = DbVolumeBlobs.getBlobReferences(conn, digest, vol2);

        paths = new HashSet<String>();
        paths.add(msg2.getBlob().getLocalBlob().getFile().getPath());

        Assert.assertEquals(1, blobs.size());
        for (BlobReference ref : blobs) {
            Assert.assertTrue(paths.remove(getPath(ref)));
        }
    }

}
