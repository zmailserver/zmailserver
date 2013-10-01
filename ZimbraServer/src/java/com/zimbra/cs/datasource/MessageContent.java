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
package com.zimbra.cs.datasource;

import java.io.IOException;
import java.io.InputStream;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.BufferStream;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.mailbox.DeliveryContext;
import com.zimbra.cs.mime.ParsedMessage;
import com.zimbra.cs.store.Blob;
import com.zimbra.cs.store.StoreManager;

public class MessageContent {
    private Blob blob;
    private byte[] data;

    public static MessageContent read(InputStream is, int size) throws IOException, ServiceException {
        MessageContent mb = new MessageContent();
        mb.readContent(is, size);
        return mb;
    }

    private MessageContent() {}
    
    private void readContent(InputStream is, int sizeHint) throws IOException, ServiceException {
        if (sizeHint < StoreManager.getDiskStreamingThreshold()) {
            BufferStream bs = new BufferStream(sizeHint);
            
            long realSize = bs.readFrom(is);
            if (realSize != sizeHint) {
                // ZimbraLog.datasource.debug("Content size mismatch: expected %d but got %d bytes", sizeHint, realSize);
            }
            data = bs.toByteArray();
            bs.close();
        } else {
            blob = StoreManager.getInstance().storeIncoming(is);
        }
    }

    public ParsedMessage getParsedMessage(Long receivedDate, boolean indexAttachments)
        throws IOException, ServiceException {
        if (data != null) {
            return data.length == 0 ? null : new ParsedMessage(data, receivedDate, indexAttachments);
        } else {
            return new ParsedMessage(blob, receivedDate, indexAttachments);
        }
    }
    
    public int getSize() {
        if (data != null) {
            return data.length;
        }
        try {
            return (int) blob.getRawSize();
        } catch (IOException e) {
            ZimbraLog.datasource.error("Unable to determine size of %s.", blob.getPath(), e);
        }
        return 0;
    }

    public DeliveryContext getDeliveryContext() {
        DeliveryContext dc = new DeliveryContext();
        if (blob != null) {
            dc.setIncomingBlob(blob);
        }
        return dc;
    }
    
    public void cleanup() throws IOException {
        if (blob != null) {
            StoreManager.getInstance().delete(blob);
            blob = null;
        }
    }

    @Override
    public void finalize() throws Throwable {
        try {
            cleanup();
        } finally {
            super.finalize();
        }
    }
}
