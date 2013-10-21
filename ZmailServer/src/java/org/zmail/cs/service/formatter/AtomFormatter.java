/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.formatter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.Constants;
import org.zmail.common.util.DateUtil;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.CalendarItem.Instance;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mailbox.calendar.InviteInfo;
import org.zmail.cs.service.UserServletContext;
import org.zmail.cs.service.formatter.FormatterFactory.FormatType;

public class AtomFormatter extends Formatter {
    @Override
    public void formatCallback(UserServletContext context) throws IOException, ServiceException {
        Iterator<? extends MailItem> iterator = null;
        StringBuffer sb = new StringBuffer();
        Element.XMLElement feed = new Element.XMLElement("feed");
        int offset = context.getOffset();
        int limit = context.getLimit();
        try {
            iterator = getMailItems(context, context.getStartTime(), context.getEndTime(), limit-offset);

            context.resp.setCharacterEncoding("UTF-8");
            context.resp.setContentType("application/atom+xml");

            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");

            feed.addAttribute("xmlns", "http://www.w3.org/2005/Atom");

            feed.addElement("title").setText("Zmail " + context.itemPath);
            feed.addElement("generator").setText("Zmail Atom Feed Servlet");
            feed.addElement("id").setText(context.req.getRequestURL().toString());
            feed.addElement("updated").setText(DateUtil.toISO8601(new Date(context.targetMailbox.getLastChangeDate())));

            int curHit = 0;

            while (iterator.hasNext()) {
                MailItem itItem = iterator.next();
                curHit++;
                if (curHit > limit)
                    break;
                if (curHit >= offset) {
                    if (itItem instanceof CalendarItem) {
                        // Don't return private appointments/tasks if the requester is not the mailbox owner.
                        CalendarItem calItem = (CalendarItem) itItem;
                        if (calItem.isPublic() || calItem.allowPrivateAccess(
                                context.getAuthAccount(), context.isUsingAdminPrivileges())) {
                            addCalendarItem(calItem, feed, context);
                        }
                    } else if (itItem instanceof Message) {
                        addMessage((Message) itItem, feed);
                    }
                }
            }
        } finally {
            if (iterator instanceof QueryResultIterator)
                ((QueryResultIterator) iterator).finished();
        }
        sb.append(feed.toString());
        context.resp.getOutputStream().write(sb.toString().getBytes("UTF-8"));
    }

    @Override
    public long getDefaultStartTime() {
        return System.currentTimeMillis() - (7*Constants.MILLIS_PER_DAY);
    }

    // eventually get this from query param ?end=long|YYYYMMMDDHHMMSS
    @Override
    public long getDefaultEndTime() {
        return System.currentTimeMillis() + (7*Constants.MILLIS_PER_DAY);
    }

    private void addCalendarItem(CalendarItem calItem, Element feed, UserServletContext context) throws ServiceException {
        Collection<Instance> instances = calItem.expandInstances(context.getStartTime(), context.getEndTime(), false);
        for (Iterator<Instance> instIt = instances.iterator(); instIt.hasNext(); ) {
            CalendarItem.Instance inst = instIt.next();
            InviteInfo invId = inst.getInviteInfo();
            Invite inv = calItem.getInvite(invId.getMsgId(), invId.getComponentId());
            Element entry = feed.addElement("entry");
            entry.addElement("title").setText(inv.getName());
            entry.addElement("updated").setText(DateUtil.toISO8601(new Date(inst.getStart())));
            entry.addElement("summary").setText(inv.getFragment());
            // TODO: only personal part in name
            if (inv.hasOrganizer()) {
                Element author = entry.addElement("author");
                author.addElement("name").setText(inv.getOrganizer().getCn());
                author.addElement("email").setText(inv.getOrganizer().getAddress());
            }
        }

    }

    private void addMessage(Message m, Element feed) {
        Element entry = feed.addElement("entry");
        entry.addElement("title").setText(m.getSubject());
        entry.addElement("summary").setText(m.getFragment());
        Element author = entry.addElement("author");
        // TODO: only personal part in name
        author.addElement("name").setText(m.getSender());
        author.addElement("email").setText(m.getSender());
        entry.addElement("modified").setText(DateUtil.toISO8601(new Date(m.getDate())));
    }

    @Override
    public FormatType getType() {
        return FormatType.ATOM;
    }

}
