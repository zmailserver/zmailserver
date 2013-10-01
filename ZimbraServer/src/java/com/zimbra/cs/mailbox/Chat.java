/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.mime.ParsedMessage;
import com.zimbra.cs.store.StagedBlob;

public class Chat extends Message {

    /**
     * this one will call back into decodeMetadata() to do our initialization
     *
     * @param mbox
     * @param ud
     * @throws ServiceException
     */
    Chat(Mailbox mbox, UnderlyingData ud) throws ServiceException {
        super(mbox, ud);
        if (mData.type != Type.CHAT.toByte()) {
            throw new IllegalArgumentException();
        }
        if (mData.parentId < 0) {
            mData.parentId = -mId;
        }
    }

    static class ChatCreateFactory extends MessageCreateFactory {
        @Override
        Message create(Mailbox mbox, UnderlyingData data) throws ServiceException {
            return new Chat(mbox, data);
        }

        @Override
        Type getType() {
            return Type.CHAT;
        }
    }

    static Chat create(int id, Folder folder, ParsedMessage pm, StagedBlob staged, boolean unread, int flags, Tag.NormalizedTags ntags)
    throws ServiceException {
        return (Chat) Message.createInternal(id, folder, null, pm, staged, unread, flags, ntags, null, true, null, null, new ChatCreateFactory());
    }

    @Override boolean isMutable() { return true; }
}
