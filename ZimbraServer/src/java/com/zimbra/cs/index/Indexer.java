/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.zimbra.cs.mailbox.Folder;
import com.zimbra.cs.mailbox.MailItem;

/**
 * Abstraction of index write operations.
 *
 * @see IndexStore#openIndexer()
 * @author ysasaki
 * @author smukhopadhyay
 */
public interface Indexer extends Closeable {

    /**
     * Adds index documents.
     */
    void addDocument(Folder folder, MailItem item, List<IndexDocument> docs) throws IOException;

    /**
     * Deletes index documents.
     *
     * @param ids list of item IDs to delete
     */
    void deleteDocument(List<Integer> ids) throws IOException;

    /**
     * Requests an "optimize" operation on the index, priming the index for the fastest available search.
     */
    void optimize();

    /**
     * Compacts the index by expunging all the deletes.
     */
    void compact();

    /**
     * Returns total number of docs in this index, including docs not yet flushed (still in the RAM buffer), not counting deletions.
     * @return total number of docs
     */
    int maxDocs();
}
