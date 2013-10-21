/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.store.file;

import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.store.MailboxBlob;

public class VolumeMailboxBlob extends MailboxBlob {
    private final VolumeBlob blob;

    protected VolumeMailboxBlob(Mailbox mbox, int itemId, int revision, String locator, VolumeBlob blob) {
        super(mbox, itemId, revision, locator);
        this.blob = blob;
    }

    @Override
    public MailboxBlob setSize(long size) {
        super.setSize(size);
        if (blob != null) {
            blob.setRawSize(size);
        }
        return this;
    }

    @Override
    public VolumeBlob getLocalBlob() {
        return blob;
    }
}
