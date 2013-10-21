/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.index;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.mailbox.MailItem;

/**
 * UngroupedQueryResults which do NOT group (ie return parts or messages in whatever mix)
 *
 * @since Nov 3, 2004
 */
final class UngroupedQueryResults extends ZmailQueryResultsImpl {
    private final ZmailQueryResults results;

    UngroupedQueryResults(ZmailQueryResults results, Set<MailItem.Type> types, SortBy sort, SearchParams.Fetch fetch) {
        super(types, sort, fetch);
        this.results = results;
    }

    @Override
    public long getCursorOffset() {
        return results.getCursorOffset();
    }

    @Override
    public void resetIterator() throws ServiceException {
        results.resetIterator();
    }

    @Override
    public ZmailHit getNext() throws ServiceException {
        return results.getNext();
    }

    @Override
    public ZmailHit peekNext() throws ServiceException {
        return results.peekNext();
    }

    @Override
    public void close() throws IOException {
        results.close();
    }

    @Override
    public ZmailHit skipToHit(int hitNo) throws ServiceException {
        return results.skipToHit(hitNo);
    }

    @Override
    public List<QueryInfo> getResultInfo() {
        return results.getResultInfo();
    }

    @Override
    public boolean isPreSorted() {
        return results.isPreSorted();
    }
}
