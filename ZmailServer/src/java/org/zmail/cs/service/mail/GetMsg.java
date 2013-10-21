/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mailbox.calendar.RecurId;
import org.zmail.cs.redolog.RedoLogProvider;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 * @since May 26, 2004
 */
public class GetMsg extends MailDocumentHandler {

    private static final String[] TARGET_MSG_PATH = new String[] { MailConstants.E_MSG, MailConstants.A_ID };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_MSG_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return false;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element eMsg = request.getElement(MailConstants.E_MSG);
        ItemId iid = new ItemId(eMsg.getAttribute(MailConstants.A_ID), zsc);
        String part = eMsg.getAttribute(MailConstants.A_PART, null);

        boolean raw = eMsg.getAttributeBool(MailConstants.A_RAW, false);
        boolean read = eMsg.getAttributeBool(MailConstants.A_MARK_READ, false);
        int maxSize = (int) eMsg.getAttributeLong(MailConstants.A_MAX_INLINED_LENGTH, 0);

        boolean wantHTML = eMsg.getAttributeBool(MailConstants.A_WANT_HTML, false);
        boolean neuter = eMsg.getAttributeBool(MailConstants.A_NEUTER, true);

        Set<String> headers = null;
        for (Element eHdr : eMsg.listElements(MailConstants.A_HEADER)) {
            if (headers == null)  headers = new HashSet<String>();
            headers.add(eHdr.getAttribute(MailConstants.A_ATTRIBUTE_NAME));
        }

        boolean needGroupInfo = eMsg.getAttributeBool(MailConstants.A_NEED_EXP, false);

        Element response = zsc.createElement(MailConstants.GET_MSG_RESPONSE);
        if (iid.hasSubpart()) {
            // calendar item
            CalendarItem calItem = getCalendarItem(octxt, mbox, iid);
            if (raw) {
                throw ServiceException.INVALID_REQUEST("Cannot request RAW formatted subpart message", null);
            } else {
                String recurIdZ = eMsg.getAttribute(MailConstants.A_CAL_RECURRENCE_ID_Z, null);
                if (recurIdZ == null) {
                    // If not specified, try to get it from the Invite.
                    int invId = iid.getSubpartId();
                    Invite[] invs = calItem.getInvites(invId);
                    if (invs.length > 0) {
                        RecurId rid = invs[0].getRecurId();
                        if (rid != null)
                            recurIdZ = rid.getDtZ();
                    }
                }
                ToXML.encodeInviteAsMP(response, ifmt, octxt, calItem, recurIdZ, iid,
                        part, maxSize, wantHTML, neuter, headers, false, needGroupInfo);
            }
        } else {
            Message msg = getMsg(octxt, mbox, iid, read);
            if (raw) {
                ToXML.encodeMessageAsMIME(response, ifmt, octxt, msg, part, false);
            } else {
                ToXML.encodeMessageAsMP(response, ifmt, octxt, msg, part, maxSize,
                        wantHTML, neuter, headers, false, needGroupInfo, LC.mime_encode_missing_blob.booleanValue());
            }
        }

        return response;
    }

    @Override
    public boolean isReadOnly() {
        return RedoLogProvider.getInstance().isSlave();
    }

    public static CalendarItem getCalendarItem(OperationContext octxt, Mailbox mbox, ItemId iid) throws ServiceException {
        assert(iid.hasSubpart());
        return mbox.getCalendarItemById(octxt, iid.getId());
    }

    public static Message getMsg(OperationContext octxt, Mailbox mbox, ItemId iid, boolean read) throws ServiceException {
        assert(!iid.hasSubpart());
        Message msg = mbox.getMessageById(octxt, iid.getId());
        if (read && msg.isUnread() && !RedoLogProvider.getInstance().isSlave()) {
            try {
                mbox.alterTag(octxt, msg.getId(), MailItem.Type.MESSAGE, Flag.FlagInfo.UNREAD, false, null);
            } catch (ServiceException e) {
                if (e.getCode().equals(ServiceException.PERM_DENIED)) {
                    ZmailLog.mailbox.info("no permissions to mark message as read (ignored): %d", msg.getId());
                } else {
                    ZmailLog.mailbox.warn("problem marking message as read (ignored): %d", msg.getId(), e);
                }
            }
        }
        return msg;
    }

    public static List<Message> getMsgs(OperationContext octxt, Mailbox mbox, List<Integer> iids, boolean read) throws ServiceException {
        int i = 0;
        int[] msgIdArray = new int[iids.size()];
        for (int id : iids) {
            msgIdArray[i++] = id;
        }

        List<Message> toRet = new ArrayList<Message>(msgIdArray.length);
        for (MailItem item : mbox.getItemById(octxt, msgIdArray, MailItem.Type.MESSAGE)) {
            toRet.add((Message) item);
            if (read && item.isUnread() && !RedoLogProvider.getInstance().isSlave()) {
                mbox.alterTag(octxt, item.getId(), MailItem.Type.MESSAGE, Flag.FlagInfo.UNREAD, false, null);
            }
        }
        return toRet;
    }
}
