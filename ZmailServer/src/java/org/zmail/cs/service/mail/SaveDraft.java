/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.zmail.common.mailbox.Color;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ArrayUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.AutoSendDraftTask;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.util.TagUtil;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.cs.util.AccountUtil;
import org.zmail.soap.ZmailSoapContext;

/**
 * @since Jun 11, 2005
 * @author dkarp
 */
public class SaveDraft extends MailDocumentHandler {

    private static final String[] TARGET_DRAFT_PATH = new String[] { MailConstants.E_MSG, MailConstants.A_ID };
    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_MSG, MailConstants.A_FOLDER };
    private static final String[] RESPONSE_ITEM_PATH = new String[] { };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return getXPath(request, TARGET_DRAFT_PATH) != null ? TARGET_DRAFT_PATH : TARGET_FOLDER_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return getXPath(request, TARGET_DRAFT_PATH) == null;
    }

    @Override
    protected String[] getResponseItemPath()  { return RESPONSE_ITEM_PATH; }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element msgElem = request.getElement(MailConstants.E_MSG);

        int id = (int) msgElem.getAttributeLong(MailConstants.A_ID, Mailbox.ID_AUTO_INCREMENT);
        String originalId = msgElem.getAttribute(MailConstants.A_ORIG_ID, null);
        ItemId iidOrigid = originalId == null ? null : new ItemId(originalId, zsc);
        String replyType = msgElem.getAttribute(MailConstants.A_REPLY_TYPE, null);
        String identity = msgElem.getAttribute(MailConstants.A_IDENTITY_ID, null);
        String account = msgElem.getAttribute(MailConstants.A_FOR_ACCOUNT, null);

        // allow the caller to update the draft's metadata at the same time as they save the draft
        String folderId = msgElem.getAttribute(MailConstants.A_FOLDER, null);
        ItemId iidFolder = new ItemId(folderId == null ? "-1" : folderId, zsc);
        if (!iidFolder.belongsTo(mbox)) {
            throw ServiceException.INVALID_REQUEST("cannot move item between mailboxes", null);
        } else if (folderId != null && iidFolder.getId() <= 0) {
            throw MailServiceException.NO_SUCH_FOLDER(iidFolder.getId());
        }
        String flags = msgElem.getAttribute(MailConstants.A_FLAGS, null);
        String[] tags = TagUtil.parseTags(msgElem, mbox, octxt);
        Color color = ItemAction.getColor(msgElem);

        // check to see whether the entire message has been uploaded under separate cover
        String attachment = msgElem.getAttribute(MailConstants.A_ATTACHMENT_ID, null);
        long autoSendTime = new Long(msgElem.getAttribute(MailConstants.A_AUTO_SEND_TIME, "0"));

        ParseMimeMessage.MimeMessageData mimeData = new ParseMimeMessage.MimeMessageData();
        Message msg;
        try {
            MimeMessage mm;
            if (attachment != null) {
                mm = SendMsg.parseUploadedMessage(zsc, attachment, mimeData);
            } else {
                mm = ParseMimeMessage.parseMimeMsgSoap(zsc, octxt, mbox, msgElem, null, mimeData);
            }

            long date = System.currentTimeMillis();
            try {
                Date d = new Date();
                mm.setSentDate(d);
                date = d.getTime();
            } catch (Exception ignored) { }

            try {
                mm.saveChanges();
            } catch (MessagingException me) {
                throw ServiceException.FAILURE("completing MIME message object", me);
            }

            ParsedMessage pm = new ParsedMessage(mm, date, mbox.attachmentsIndexingEnabled());

            if (autoSendTime != 0) {
                AccountUtil.checkQuotaWhenSendMail(mbox);
            }

            String origid = iidOrigid == null ? null : iidOrigid.toString(account == null ? mbox.getAccountId() : account);

            msg = mbox.saveDraft(octxt, pm, id, origid, replyType, identity, account, autoSendTime);
        } catch (IOException e) {
            throw ServiceException.FAILURE("IOException while saving draft", e);
        } finally {
            // purge the messages fetched from other servers.
            if (mimeData.fetches != null) {
                FileUploadServlet.deleteUploads(mimeData.fetches);
            }
        }

        // we can now purge the uploaded attachments
        if (mimeData.uploads != null) {
            FileUploadServlet.deleteUploads(mimeData.uploads);
        }

        // try to set the metadata on the new/revised draft
        if (folderId != null || flags != null || !ArrayUtil.isEmpty(tags) || color != null) {
            try {
                // best not to fail if there's an error here...
                ItemActionHelper.UPDATE(octxt, mbox, zsc.getResponseProtocol(), Arrays.asList(msg.getId()),
                        MailItem.Type.MESSAGE, null, null, iidFolder, flags, tags, color);
                // and make sure the Message object reflects post-update reality
                msg = mbox.getMessageById(octxt, msg.getId());
            } catch (ServiceException e) {
                ZmailLog.soap.warn("error setting metadata for draft " + msg.getId() + "; skipping that operation", e);
            }
        }

        if (schedulesAutoSendTask()) {
            if (id != Mailbox.ID_AUTO_INCREMENT) {
                // Cancel any existing auto-send task for this draft
                AutoSendDraftTask.cancelTask(id, mbox.getId());
            }
            if (autoSendTime != 0) {
                // schedule a new auto-send-draft task
                AutoSendDraftTask.scheduleTask(msg.getId(), mbox.getId(), autoSendTime);
            }
        }

        return generateResponse(zsc, ifmt, octxt, mbox, msg);
    }

    protected boolean schedulesAutoSendTask() {
        return true;
    }

    protected Element generateResponse(ZmailSoapContext zsc, ItemIdFormatter ifmt, OperationContext octxt, Mailbox mbox, Message msg) {
        Element response = zsc.createElement(MailConstants.SAVE_DRAFT_RESPONSE);
        try {
            ToXML.encodeMessageAsMP(response, ifmt, octxt, msg, null, -1, true, true, null, true, false, false);
        } catch (NoSuchItemException nsie) {
            ZmailLog.soap.info("draft was deleted while serializing response; omitting <m> from response");
        } catch (ServiceException e) {
            ZmailLog.soap.warn("problem serializing draft structure to response", e);
        }
        return response;
    }
}
