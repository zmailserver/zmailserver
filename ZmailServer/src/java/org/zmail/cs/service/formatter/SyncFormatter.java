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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.HttpUtil;
import org.zmail.common.util.Pair;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.DeliveryOptions;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.calendar.CalendarMailSender;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mailbox.util.TagUtil;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.UserServletContext;
import org.zmail.cs.service.UserServletException;
import org.zmail.cs.service.formatter.FormatterFactory.FormatType;

public class SyncFormatter extends Formatter {

    public static final String QP_NOHDR = "nohdr";

    @Override
    public FormatType getType() {
        return FormatType.SYNC;
    }

    @Override
    public Set<MailItem.Type> getDefaultSearchTypes() {
        // TODO: all?
        return EnumSet.of(MailItem.Type.MESSAGE);
    }

    /**
     * add to content as well as http headers for now (unless told not to)...
     */
    private static List<Pair<String, String>> getXZmailHeaders(MailItem item) {
        List<Pair<String, String>> hdrs = new ArrayList<Pair<String, String>>();
        hdrs.add(new Pair<String, String>("X-Zmail-ItemId", item.getId() + ""));
        hdrs.add(new Pair<String, String>("X-Zmail-FolderId", item.getFolderId() + ""));
        hdrs.add(new Pair<String, String>("X-Zmail-Tags", TagUtil.getTagIdString(item)));
        hdrs.add(new Pair<String, String>("X-Zmail-Tag-Names", TagUtil.encodeTags(item.getTags())));
        hdrs.add(new Pair<String, String>("X-Zmail-Flags", item.getFlagString()));
        hdrs.add(new Pair<String, String>("X-Zmail-Received", item.getDate() + ""));
        hdrs.add(new Pair<String, String>("X-Zmail-Modified", item.getChangeDate() + ""));
        hdrs.add(new Pair<String, String>("X-Zmail-Change", item.getModifiedSequence() + ""));
        hdrs.add(new Pair<String, String>("X-Zmail-Revision", item.getSavedSequence() + ""));
        if (item instanceof Message)
            hdrs.add(new Pair<String, String>("X-Zmail-Conv", ((Message) item).getConversationId() + ""));
        return hdrs;
    }

    private static byte[] getXZmailHeadersBytes(List<Pair<String, String>> hdrs) {
        StringBuilder sb = new StringBuilder();
        for (Pair<String, String> pair : hdrs)
            sb.append(pair.getFirst()).append(": ").append(pair.getSecond()).append("\r\n");
        return sb.toString().getBytes();
    }

    public static byte[] getXZmailHeadersBytes(MailItem item) {
        return getXZmailHeadersBytes(getXZmailHeaders(item));
    }

    private static void addXZmailHeaders(UserServletContext context, MailItem item, long size) throws IOException {
        List<Pair<String, String>> hdrs = getXZmailHeaders(item);
        for (Pair<String, String> pair : hdrs)
            context.resp.addHeader(pair.getFirst(), pair.getSecond());

        // inline X-Zmail headers with response body if nohdr parameter is not present
        // also explicitly set the Content-Length header, as it's only done implicitly for short payloads
        if (context.params.get(QP_NOHDR) == null) {
            byte[] inline = getXZmailHeadersBytes(hdrs);
            if (size > 0)
                context.resp.setContentLength(inline.length + (int) size);
            context.resp.getOutputStream().write(inline);
        } else if (size > 0) {
            context.resp.setContentLength((int) size);
        }
    }

    @Override
    public void formatCallback(UserServletContext context) throws IOException, ServiceException, UserServletException {
        try {
            if (context.hasPart()) {
                handleMessagePart(context, context.target);
            } else if (context.target instanceof Message) {
                handleMessage(context, (Message) context.target);
            } else if (context.target instanceof CalendarItem) {
                // Don't return private appointments/tasks if the requester is not the mailbox owner.
                CalendarItem calItem = (CalendarItem) context.target;
                if (calItem.isPublic() || calItem.allowPrivateAccess(
                        context.getAuthAccount(), context.isUsingAdminPrivileges())) {
                    handleCalendarItem(context, calItem);
                } else {
                    context.resp.sendError(HttpServletResponse.SC_FORBIDDEN, "permission denied");
                }
            }
        } catch (MessagingException me) {
            throw ServiceException.FAILURE(me.getMessage(), me);
        }
    }

    private void handleCalendarItem(UserServletContext context, CalendarItem calItem) throws IOException, ServiceException, MessagingException {
        context.resp.setContentType(MimeConstants.CT_TEXT_PLAIN);
        if (context.itemId.hasSubpart()) {
            Pair<MimeMessage,Integer> calItemMsgData = calItem.getSubpartMessageData(context.itemId.getSubpartId());
            if (calItemMsgData != null) {
                addXZmailHeaders(context, calItem, -1);
                calItemMsgData.getFirst().writeTo(context.resp.getOutputStream());
            } else {
                // Backward compatibility for pre-5.0.16 ZCO/ZCB: Build a MIME message on the fly.
                // Let's first make sure the requested invite id is valid.
                int invId = context.itemId.getSubpartId();
                Invite[] invs = calItem.getInvites(invId);
                if (invs != null && invs.length > 0) {
                    Invite invite = invs[0];
                    MimeMessage mm = CalendarMailSender.createCalendarMessage(invite);
                    if (mm != null) {
                        // Go through ByteArrayInput/OutputStream to calculate the exact size in bytes.
                        int sizeHint = mm.getSize();
                        if (sizeHint < 0) sizeHint = 0;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream(sizeHint);
                        mm.writeTo(baos);
                        byte[] bytes = baos.toByteArray();
                        addXZmailHeaders(context, calItem, bytes.length);
                        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                        ByteUtil.copy(bais, true, context.resp.getOutputStream(), false);
                    }
                }
            }
        } else {
            addXZmailHeaders(context, calItem, -1);
            InputStream is = calItem.getRawMessage();
            if (is != null)
                ByteUtil.copy(is, true, context.resp.getOutputStream(), false);
        }
    }

    private void handleMessage(UserServletContext context, Message msg) throws IOException, ServiceException {
        context.resp.setContentType(MimeConstants.CT_TEXT_PLAIN);
        InputStream is = msg.getContentStream();
        long size = msg.getSize();

        if (!context.shouldReturnBody()) {
            byte[] headers = HeadersOnlyInputStream.getHeaders(is);
            is = new ByteArrayInputStream(headers);
            size = headers.length;
        }
        addXZmailHeaders(context, msg, size);
        ByteUtil.copy(is, true, context.resp.getOutputStream(), false);
    }

    private void handleMessagePart(UserServletContext context, MailItem item) throws IOException, ServiceException, MessagingException, UserServletException {
        if (!(item instanceof Message))
            throw UserServletException.notImplemented("can only handle messages");
        Message message = (Message) item;

        MimePart mp = getMimePart(message, context.getPart());
        if (mp != null) {
            String contentType = mp.getContentType();
            if (contentType == null)
                contentType = MimeConstants.CT_APPLICATION_OCTET_STREAM;
            sendbackOriginalDoc(mp, contentType, context.req, context.resp);
            return;
        }
        context.resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "part not found");
    }

    public static MimePart getMimePart(CalendarItem calItem, String part) throws IOException, MessagingException, ServiceException {
        return Mime.getMimePart(calItem.getMimeMessage(), part);
    }

    public static MimePart getMimePart(Message msg, String part) throws IOException, MessagingException, ServiceException {
        return Mime.getMimePart(msg.getMimeMessage(), part);
    }

    public static void sendbackOriginalDoc(MimePart mp, String contentType, HttpServletRequest req, HttpServletResponse resp) throws IOException, MessagingException {
        String filename = Mime.getFilename(mp);
        if (filename == null)
            filename = "unknown";
        String cd = HttpUtil.createContentDisposition(req, Part.INLINE, filename);
        resp.addHeader("Content-Disposition", cd);
        String desc = mp.getDescription();
        if (desc != null)
            resp.addHeader("Content-Description", desc);
        sendbackOriginalDoc(mp.getInputStream(), contentType, resp);
    }

    public static void sendbackOriginalDoc(InputStream is, String contentType, HttpServletResponse resp) throws IOException {
        resp.setContentType(contentType);
        ByteUtil.copy(is, true, resp.getOutputStream(), false);
    }

    @Override
    public boolean supportsSave() {
        return true;
    }

    // FIXME: need to support tags, flags, date, etc...
    @Override
    public void saveCallback(UserServletContext context, String contentType, Folder folder, String filename)
            throws IOException, ServiceException, UserServletException {
        byte[] body = context.getPostBody();
        try {
            Mailbox mbox = folder.getMailbox();
            ParsedMessage pm = new ParsedMessage(body, mbox.attachmentsIndexingEnabled());
            DeliveryOptions dopt = new DeliveryOptions().setFolderId(folder).setNoICal(true);
            mbox.addMessage(context.opContext, pm, dopt, null);
        } catch (ServiceException e) {
            throw new UserServletException(HttpServletResponse.SC_BAD_REQUEST, "error parsing message");
        }
    }
}

