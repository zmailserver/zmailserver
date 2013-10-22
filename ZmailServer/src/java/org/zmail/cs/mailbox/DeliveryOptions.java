/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.Collection;

import com.zimbra.cs.mailbox.MailItem.CustomMetadata;

/**
 * Specifies options for new messages created with {@link Mailbox#addMessage}.
 */
public class DeliveryOptions {

    private int mFolderId = -1;
    private boolean mNoICal = false;
    private int mFlags = 0;
    private String[] mTags = null;
    private int mConversationId = Mailbox.ID_AUTO_INCREMENT;
    private String mRecipientEmail = ":API:";
    private Message.DraftInfo mDraftInfo = null;
    private CustomMetadata mCustomMetadata = null;
    
    public int getFolderId() { return mFolderId; }
    public boolean getNoICal() { return mNoICal; }
    public int getFlags() { return mFlags; }
    public String[] getTags() { return mTags; }
    public int getConversationId() { return mConversationId; }
    public String getRecipientEmail() { return mRecipientEmail; }
    public Message.DraftInfo getDraftInfo() { return mDraftInfo; }
    public CustomMetadata getCustomMetadata() { return mCustomMetadata; }

    public DeliveryOptions setFolderId(int folderId) {
        mFolderId = folderId;
        return this;
    }

    public DeliveryOptions setFolderId(Folder folder) {
        mFolderId = folder.getId();
        return this;
    }
    
    public DeliveryOptions setNoICal(boolean noICal) {
        mNoICal = noICal;
        return this;
    }
    
    public DeliveryOptions setFlags(int flags) {
        mFlags = flags;
        return this;
    }

    public DeliveryOptions setTags(Collection<String> tags) {
        mTags = tags == null ? null : tags.toArray(new String[tags.size()]);
        return this;
    }

    public DeliveryOptions setTags(String[] tags) {
        mTags = tags;
        return this;
    }
    
    public DeliveryOptions setConversationId(int conversationId) {
        mConversationId = conversationId;
        return this;
    }
    
    public DeliveryOptions setRecipientEmail(String recipientEmail) {
        mRecipientEmail = recipientEmail;
        return this;
    }
    
    public DeliveryOptions setDraftInfo(Message.DraftInfo draftInfo) {
        mDraftInfo = draftInfo;
        return this;
    }
    
    public DeliveryOptions setCustomMetadata(CustomMetadata customMetadata) {
        mCustomMetadata = customMetadata;
        return this;
    }
}
