/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.store.external;

import java.io.IOException;
import java.io.InputStream;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.mailbox.Mailbox;

/**
 * Interface for the simplest blob store integration possible
 * The implementor must provide functionality for reading, writing, and deleting blobs
 */
public interface ExternalBlobIO {
    /**
     * Write data to blob store
     * @param in: InputStream containing data to be written
     * @param actualSize: size of data in stream, or -1 if size is unknown. To be used by implementor for optimization where possible
     * @param mbox: Mailbox which contains the blob. Can optionally be used by store for partitioning
     * @return locator string for the stored blob, unique identifier created by storage protocol
     * @throws IOException
     * @throws ServiceException
     */
    String writeStreamToStore(InputStream in, long actualSize, Mailbox mbox) throws IOException, ServiceException;

    /**
     * Create an input stream for reading data from blob store
     * @param locator: identifier string for the blob as returned from write operation
     * @param mbox: Mailbox which contains the blob. Can optionally be used by store for partitioning
     * @return InputStream containing the data
     * @throws IOException
     */
    InputStream readStreamFromStore(String locator, Mailbox mbox) throws IOException;

    /**
     * Delete a blob from the store
     * @param locator: identifier string for the blob
     * @param mbox: Mailbox which contains the blob. Can optionally be used by store for partitioning
     * @return true on success false on failure
     * @throws IOException
     */
    boolean deleteFromStore(String locator, Mailbox mbox) throws IOException;
}
