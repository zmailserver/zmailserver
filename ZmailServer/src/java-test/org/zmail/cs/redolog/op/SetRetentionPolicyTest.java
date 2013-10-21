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

package org.zmail.cs.redolog.op;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;
import org.zmail.cs.mailbox.Tag;
import org.zmail.soap.mail.type.Policy;
import org.zmail.soap.mail.type.RetentionPolicy;

public class SetRetentionPolicyTest {

    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();
        prov.createAccount("test@zmail.com", "secret", new HashMap<String, Object>());
    }

    @Before
    public void setUp() throws Exception {
        MailboxTestUtil.clearData();
    }

    /**
     * Verifies serializing, deserializing, and replaying for folder.
     */
    @Test
    public void redoFolder() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        // Create folder.
        Folder folder = mbox.createFolder(null, "/redo", new Folder.FolderOptions().setDefaultView(MailItem.Type.MESSAGE));
        assertEquals(0, folder.getRetentionPolicy().getKeepPolicy().size());
        assertEquals(0, folder.getRetentionPolicy().getPurgePolicy().size());

        // Create RedoableOp.
        RetentionPolicy rp = new RetentionPolicy(
            Arrays.asList(Policy.newSystemPolicy("123")),
            Arrays.asList(Policy.newUserPolicy("45m")));
        SetRetentionPolicy redoPlayer = new SetRetentionPolicy(mbox.getId(), MailItem.Type.FOLDER, folder.getId(), rp);

        // Serialize, deserialize, and redo.
        byte[] data = redoPlayer.testSerialize();
        redoPlayer = new SetRetentionPolicy();
        redoPlayer.setMailboxId(mbox.getId());
        redoPlayer.testDeserialize(data);
        redoPlayer.redo();
        folder = mbox.getFolderById(null, folder.getId());
        assertEquals(1, folder.getRetentionPolicy().getKeepPolicy().size());
        assertEquals(1, folder.getRetentionPolicy().getPurgePolicy().size());
        assertEquals("45m", folder.getRetentionPolicy().getPurgePolicy().get(0).getLifetime());
        assertEquals("123", folder.getRetentionPolicy().getKeepPolicy().get(0).getId());
    }

    /**
     * Verifies serializing, deserializing, and replaying for tag.
     */
    @Test
    public void redoTag() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);

        // Create folder.
        Tag tag = mbox.createTag(null, "tag", (byte) 0);
        assertEquals(0, tag.getRetentionPolicy().getKeepPolicy().size());
        assertEquals(0, tag.getRetentionPolicy().getPurgePolicy().size());

        // Create RedoableOp.
        RetentionPolicy rp = new RetentionPolicy(
            Arrays.asList(Policy.newSystemPolicy("123")),
            Arrays.asList(Policy.newUserPolicy("45m")));
        SetRetentionPolicy redoPlayer = new SetRetentionPolicy(mbox.getId(), MailItem.Type.TAG, tag.getId(), rp);

        // Serialize, deserialize, and redo.
        byte[] data = redoPlayer.testSerialize();
        redoPlayer = new SetRetentionPolicy();
        redoPlayer.setMailboxId(mbox.getId());
        redoPlayer.testDeserialize(data);
        redoPlayer.redo();

        tag = mbox.getTagById(null, tag.getId());
        assertEquals(1, tag.getRetentionPolicy().getKeepPolicy().size());
        assertEquals(1, tag.getRetentionPolicy().getPurgePolicy().size());
        assertEquals("45m", tag.getRetentionPolicy().getPurgePolicy().get(0).getLifetime());
        assertEquals("123", tag.getRetentionPolicy().getKeepPolicy().get(0).getId());
    }
}
