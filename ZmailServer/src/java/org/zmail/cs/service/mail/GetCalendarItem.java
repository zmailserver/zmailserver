/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.cs.session.PendingModifications.Change;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author tim
 */
public class GetCalendarItem extends CalendarRequest {
    private static final Log LOG = LogFactory.getLog(GetCalendarItem.class);

    private static final String[] TARGET_ITEM_PATH = new String[] { MailConstants.A_ID };
    private static final String[] RESPONSE_ITEM_PATH = new String[0];

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_ITEM_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return false;
    }

    @Override
    protected String[] getResponseItemPath() {
        return RESPONSE_ITEM_PATH;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        boolean sync = request.getAttributeBool(MailConstants.A_SYNC, false);
        boolean includeContent = request.getAttributeBool(MailConstants.A_CAL_INCLUDE_CONTENT, false);
        ItemId iid = null;
        String uid = request.getAttribute(MailConstants.A_UID, null);
        String id = request.getAttribute(MailConstants.A_ID, null);
        if (uid != null) {
            if (id != null) {
                throw ServiceException.INVALID_REQUEST("either id or uid should be specified, but not both", null);
            }
            LOG.info("<GetCalendarItem uid=" + uid + "> " + zsc);
        } else {
            iid = new ItemId(id, zsc);
            LOG.info("<GetCalendarItem id=" + iid.getId() + "> " + zsc);
        }

        // want to return modified date only on sync-related requests
        int fields = ToXML.NOTIFY_FIELDS;
        if (sync) {
            fields |= Change.CONFLICT;
        }
        Element response = getResponseElement(zsc);
        mbox.lock.lock();
        try {
            CalendarItem calItem;
            if (uid != null) {
                calItem = mbox.getCalendarItemByUid(octxt, uid);
                if (calItem == null) {
                    throw MailServiceException.NO_SUCH_CALITEM(uid);
                }
            } else {
                calItem = mbox.getCalendarItemById(octxt, iid.getId());
                if (calItem == null) {
                    throw MailServiceException.NO_SUCH_CALITEM(iid.getId());
                }
            }
            ToXML.encodeCalendarItemSummary(response, ifmt, octxt, calItem, fields, true, includeContent);
        } finally {
            mbox.lock.release();
        }

        return response;
    }
}
