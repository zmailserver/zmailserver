/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zmail.common.localconfig.DebugConfig;
import org.zmail.common.service.ServiceException;

/**
 * QueryResults wrapper that implements Re-Sorting. It does this by caching **ALL** hits and then sorting them. It is
 * used for the Task sorts as well as specially localized language sorts
 */
public final class ReSortingQueryResults implements ZmailQueryResults {
    private static final int MAX_BUFFERED_HITS = 10000;

    private final ZmailQueryResults results;
    private final SortBy sort;
    private List<ZmailHit> mHitBuffer = null;
    private int iterOffset = 0;
    private final SearchParams params;

    public ReSortingQueryResults(ZmailQueryResults results, SortBy sort, SearchParams params) {
        this.results = results;
        this.sort = sort;
        this.params = params;
    }

    @Override
    public long getCursorOffset() {
        return -1;
    }

    @Override
    public void close() throws IOException {
        results.close();
    }

    @Override
    public ZmailHit getNext() throws ServiceException {
        if (hasNext()) {
            ZmailHit toRet = peekNext();
            iterOffset++;
            return toRet;
        } else {
            return null;
        }
    }

    @Override
    public List<QueryInfo> getResultInfo() {
        return results.getResultInfo();
    }

    @Override
    public SortBy getSortBy() {
        return sort;
    }

    @Override
    public boolean hasNext() throws ServiceException {
        return (iterOffset < getHitBuffer().size());
    }

    @Override
    public ZmailHit peekNext() throws ServiceException {
        List<ZmailHit> buffer = getHitBuffer();
        if (hasNext()) {
            return buffer.get(iterOffset);
        } else {
            return null;
        }
    }

    @Override
    public void resetIterator() throws ServiceException {
        iterOffset = 0;
    }

    @Override
    public ZmailHit skipToHit(int hitNo) throws ServiceException {
        List<ZmailHit> buffer = getHitBuffer();
        if (hitNo >= buffer.size()) {
            iterOffset = buffer.size();
        } else {
            iterOffset = hitNo;
        }
        return getNext();
    }

    @Override
    public boolean isPreSorted() {
        return results.isPreSorted();
    }

    private List<ZmailHit> getHitBuffer() throws ServiceException {
        if (mHitBuffer == null) {
            bufferAllHits();
        }
        return mHitBuffer;
    }

    private boolean isTaskSort() {
        switch (sort) {
            case TASK_DUE_ASC:
            case TASK_DUE_DESC:
            case TASK_STATUS_ASC:
            case TASK_STATUS_DESC:
            case TASK_PERCENT_COMPLETE_ASC:
            case TASK_PERCENT_COMPLETE_DESC:
                return true;
            default:
                return false;
        }
    }

    private void bufferAllHits() throws ServiceException {
        assert(mHitBuffer == null);
        mHitBuffer = new ArrayList<ZmailHit>();

        // get the proper comparator
        Comparator<ZmailHit> comp;
        switch (sort) {
            default:
            case TASK_DUE_ASC:
                comp = new Comparator<ZmailHit>() {
                    @Override
                    public int compare(ZmailHit lhs, ZmailHit rhs) {
                        return TaskHit.compareByDueDate(true, lhs, rhs);
                    }
                };
                break;
            case TASK_DUE_DESC:
                comp = new Comparator<ZmailHit>() {
                    @Override
                    public int compare(ZmailHit lhs, ZmailHit rhs) {
                        return TaskHit.compareByDueDate(false, lhs, rhs);
                    }
                };
                break;
            case TASK_STATUS_ASC:
                comp = new Comparator<ZmailHit>() {
                    @Override
                    public int compare(ZmailHit lhs, ZmailHit rhs) {
                        return TaskHit.compareByStatus(true, lhs, rhs);
                    }
                };
                break;
            case TASK_STATUS_DESC:
                comp = new Comparator<ZmailHit>() {
                    @Override
                    public int compare(ZmailHit lhs, ZmailHit rhs) {
                        return TaskHit.compareByStatus(false, lhs, rhs);
                    }
                };
                break;
            case TASK_PERCENT_COMPLETE_ASC:
                comp = new Comparator<ZmailHit>() {
                    @Override
                    public int compare(ZmailHit lhs, ZmailHit rhs) {
                        return TaskHit.compareByCompletionPercent(true, lhs, rhs);
                    }
                };
                break;
            case TASK_PERCENT_COMPLETE_DESC:
                comp = new Comparator<ZmailHit>() {
                    @Override
                    public int compare(ZmailHit lhs, ZmailHit rhs) {
                        return TaskHit.compareByCompletionPercent(false, lhs, rhs);
                    }
                };
                break;
            case NAME_LOCALIZED_ASC:
            case NAME_LOCALIZED_DESC:
                comp = sort.getHitComparator(params.getLocale());
                break;
        }

        int maxIfPresorted = MAX_BUFFERED_HITS;
        if (params != null && params.getCursor() == null) {
            maxIfPresorted = params.getLimit();
            if (maxIfPresorted > 0) {
                // 1 is added so that the 'more' setting will be correct.
                maxIfPresorted = maxIfPresorted + 1 + params.getOffset();
            }
        }
        ZmailHit cur;
        while ((cur = results.getNext()) != null) {

            if (isTaskSort()) {
                if (!(cur instanceof TaskHit) && !(cur instanceof ProxiedHit)) {
                    throw ServiceException.FAILURE("Invalid hit type, can only task-sort Tasks", null);
                }
            }

            boolean skipHit = false;

            boolean handleCursorFilteringForFirstHit = true;
            if (DebugConfig.enableContactLocalizedSort) {
                switch (sort) {
                    case NAME_LOCALIZED_ASC:
                    case NAME_LOCALIZED_DESC:
                        handleCursorFilteringForFirstHit = false;
                        break;
                }
            }

            // handle cursor filtering
            if (params != null && params.getCursor() != null) {
                ZmailHit firstHit = null;
                if (params.getCursor().getSortValue() != null) {
                    firstHit = new ResultsPager.CursorHit(results, params.getCursor().getSortValue(),
                            params.getCursor().getItemId().getId());
                }
                ZmailHit endHit = null;
                if (params.getCursor().getEndSortValue() != null) {
                    endHit = new ResultsPager.CursorHit(results, params.getCursor().getEndSortValue(), 0);
                }
                // fail if cur < first OR cur >= end
                if (handleCursorFilteringForFirstHit) {
                    if (firstHit != null && comp.compare(cur, firstHit) < 0) {
                        skipHit = true;
                    }
                }
                if (endHit != null && comp.compare(cur, endHit) >= 0) {
                    skipHit = true;
                }
            }

            if (!skipHit) {
                mHitBuffer.add(cur);
            }
            if (mHitBuffer.size() >= MAX_BUFFERED_HITS) {
                break;
            }
            // If it turns out that the results were sorted remotely, we can bail out early.
            if (results.isPreSorted() && mHitBuffer.size() >= maxIfPresorted) {
                break;
            }
        }

        if (!results.isPreSorted()) {
            Collections.sort(mHitBuffer, comp);
        }
    }

}
