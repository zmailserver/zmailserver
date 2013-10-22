/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.redolog.op;

import java.io.IOException;

import com.zimbra.cs.mailbox.MailboxOperation;
import com.zimbra.cs.redolog.RedoLogInput;
import com.zimbra.cs.redolog.RedoLogOutput;

/**
 * This class is deprecated and obsolete.
 */
public class IndexDeferredItems extends RedoableOp {
    
    private int[] mItemIds = null;
    private byte[] mItemTypes = null;

    public IndexDeferredItems() {
        super(MailboxOperation.IndexDeferredItems);
    }
    
    public void setIds(int[] itemIds, byte[] itemTypes) {
        mItemIds = itemIds;
        mItemTypes = itemTypes;
        if (mItemIds.length != mItemTypes.length)
            throw new IllegalArgumentException("ItemIds and ItemTypes arrays must be same size");
    }
    
    @Override
    protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mItemIds.length);
        for (int i = 0; i < mItemIds.length; i++)
            out.writeInt(mItemIds[i]);
        for (int i = 0; i < mItemIds.length; i++)
            out.writeByte(mItemTypes[i]);
    }
    
    @Override
    protected void deserializeData(RedoLogInput in) throws IOException {
        int count = in.readInt();
        mItemIds = new int[count];
        mItemTypes = new byte[count];
        for (int i = 0; i < count; i++)
            mItemIds[i] = in.readInt();
        for (int i = 0; i < count; i++)
            mItemTypes[i] = in.readByte();
    }
    
    public boolean deferCrashRecovery() {
        return true;
    }
    
    public int[] getItemIds() { return mItemIds; }
    public byte[] getItemTypes() { return mItemTypes; }

    @Override
    protected String getPrintableData() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : mItemIds) {
            sb.append(i).append(',');
        }
        return sb.toString();
    }

    @Override
    public void redo() throws Exception {
        // do nothing.
    }

}
