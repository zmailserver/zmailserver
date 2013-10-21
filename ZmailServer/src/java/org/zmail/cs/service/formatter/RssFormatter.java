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
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.Constants;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Document;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.CalendarItem.Instance;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mailbox.calendar.InviteInfo;
import org.zmail.cs.service.UserServletContext;
import org.zmail.cs.service.formatter.FormatterFactory.FormatType;

public class RssFormatter extends Formatter {

    protected SimpleDateFormat mDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

    @Override
    public void formatCallback(UserServletContext context) throws IOException, ServiceException {
        //ZmailLog.mailbox.info("start = "+new Date(context.getStartTime()));
        //ZmailLog.mailbox.info("end = "+new Date(context.getEndTime()));
        Iterator<? extends MailItem> iterator = null;
        StringBuffer sb = new StringBuffer();
        Element.XMLElement rss = new Element.XMLElement("rss");
        int offset = context.getOffset();
        int limit = context.getLimit();

        try {
            iterator = getMailItems(context, context.getStartTime(), context.getEndTime(), limit-offset);

            context.resp.setCharacterEncoding("UTF-8");
            context.resp.setContentType("application/rss+xml");

            sb.append("<?xml version=\"1.0\"?>\n");

            rss.addAttribute("version", "2.0");
            Element channel = rss.addElement("channel");
            channel.addElement("title").setText("Zmail " + context.itemPath);
            channel.addElement("link").setText("http://www.zmail.com");
            channel.addElement("description").setText("Zmail item " + context.itemPath + " in RSS format.");
            channel.addElement("generator").setText("Zmail RSS Feed Servlet");
            //channel.addElement("description").setText(query);

//            MailDateFormat mdf = new MailDateFormat();

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
                            addCalendarItem(calItem, channel, context);
                        }
                    } else if (itItem instanceof Message) {
                        addMessage((Message) itItem, channel);
                    } else if (itItem instanceof Document) {
                        addDocument((Document) itItem, channel, context);
                    }
                }
            }
        } finally {
            if (iterator instanceof QueryResultIterator)
                ((QueryResultIterator) iterator).finished();
        }
        sb.append(rss.toString());
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

    private void addCalendarItem(CalendarItem calItem, Element channel, UserServletContext context) throws ServiceException {
        Collection<Instance> instances = calItem.expandInstances(context.getStartTime(), context.getEndTime(), false);
        for (Iterator<Instance> instIt = instances.iterator(); instIt.hasNext(); ) {
            CalendarItem.Instance inst = instIt.next();
            InviteInfo invId = inst.getInviteInfo();
            Invite inv = calItem.getInvite(invId.getMsgId(), invId.getComponentId());
            Element rssItem = channel.addElement("item");
            rssItem.addElement("title").setText(inv.getName());
            rssItem.addElement("pubDate").setText(mDateFormat.format(new Date(inst.getStart())));
            /*
            StringBuffer desc = new StringBuffer();
            sb.append("Start: ").append(sdf.format(new Date(inst.getStart()))).append("\n");
            sb.append("End: ").append(sdf.format(new Date(inst.getEnd()))).append("\n");
            sb.append("Location: ").append(inv.getLocation()).append("\n");
            sb.append("Notes: ").append(inv.getFragment()).append("\n");
            item.addElement("description").setText(sb.toString());
            */
            rssItem.addElement("description").setText(inv.getFragment());
            if (inv.hasOrganizer())
                rssItem.addElement("author").setText(inv.getOrganizer().getAddress());
        }

    }

    private void addMessage(Message m, Element channel) {
        Element item = channel.addElement("item");
        item.addElement("title").setText(m.getSubject());
        item.addElement("description").setText(m.getFragment());
        item.addElement("author").setText(m.getSender());
        item.addElement("pubDate").setText(mDateFormat.format(new Date(m.getDate())));
        /* TODO: guid, links, etc */
        // Element guid = item.addElement("guid");
        // guid.setText(acct.getId()+"/"+m.getId());
        // guid.addAttribute("isPermaLink", "false");
    }

    private void addDocument(Document doc, Element channel, UserServletContext context) {
        Element item = channel.addElement("item");
        item.addElement("title").setText(doc.getName() + " ver " + doc.getVersion());
        item.addElement("description").setText(doc.getFragment());
        item.addElement("author").setText(doc.getCreator());
        item.addElement("pubDate").setText(mDateFormat.format(new Date(doc.getDate())));
        item.addElement("link").setText(context.req.getRequestURL().append("?id=" + doc.getId()).toString());
    }

    @Override
    public FormatType getType() {
        return FormatType.RSS;
    }
}
