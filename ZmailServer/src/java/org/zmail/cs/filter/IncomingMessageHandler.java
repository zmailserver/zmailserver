/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.filter;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.filter.jsieve.ActionFlag;
import org.zmail.cs.mailbox.DeliveryContext;
import org.zmail.cs.mailbox.DeliveryOptions;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.SpamHandler;
import org.zmail.cs.service.util.SpamHandler.SpamReport;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Mail filtering implementation for messages that arrive via LMTP or from
 * an external account.
 */
public final class IncomingMessageHandler implements FilterHandler {

    private OperationContext octxt;
    private DeliveryContext dctxt;
    private ParsedMessage parsedMessage;
    private Mailbox mailbox;
    private int defaultFolderId;
    private String recipientAddress;
    private int size;
    private boolean noICal;

    public IncomingMessageHandler(OperationContext octxt, DeliveryContext dctxt, Mailbox mbox,
                                  String recipientAddress, ParsedMessage pm, int size,
                                  int defaultFolderId, boolean noICal) {
        this.octxt = octxt;
        this.dctxt = dctxt;
        this.mailbox = mbox;
        this.recipientAddress = recipientAddress;
        this.parsedMessage = pm;
        this.size = size;
        this.defaultFolderId = defaultFolderId;
        this.noICal = noICal;
    }

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public MimeMessage getMimeMessage() {
        return parsedMessage.getMimeMessage();
    }

    @Override
    public ParsedMessage getParsedMessage() {
        return parsedMessage;
    }

    @Override
    public String getDefaultFolderPath() throws ServiceException {
        return mailbox.getFolderById(octxt, defaultFolderId).getPath();
    }

    @Override
    public Message explicitKeep(Collection<ActionFlag> flagActions, String[] tags)
    throws ServiceException {
        return addMessage(defaultFolderId, flagActions, tags);
    }

    @Override
    public ItemId fileInto(String folderPath, Collection<ActionFlag> flagActions, String[] tags)
            throws ServiceException {
        ItemId id = FilterUtil.addMessage(dctxt, mailbox, parsedMessage, recipientAddress, folderPath,
                                          false, FilterUtil.getFlagBitmask(flagActions, Flag.BITMASK_UNREAD),
                                          tags, Mailbox.ID_AUTO_INCREMENT, octxt);

        // Do spam training if the user explicitly filed the message into
        // the spam folder (bug 37164).
        try {
            Folder folder = mailbox.getFolderByPath(octxt, folderPath);
            if (folder.getId() == Mailbox.ID_FOLDER_SPAM && id.isLocal()) {
                SpamReport report = new SpamReport(true, "filter", folderPath);
                SpamHandler.getInstance().handle(octxt, mailbox, id.getId(), MailItem.Type.MESSAGE, report);
            }
        } catch (NoSuchItemException e) {
            ZmailLog.filter.debug("Unable to do spam training for message %s because folder path %s does not exist.",
                id, folderPath);
        } catch (ServiceException e) {
            ZmailLog.filter.warn("Unable to do spam training for message %s.", id, e);
        }

        return id;
    }

    @Override
    public Message implicitKeep(Collection<ActionFlag> flagActions, String[] tags) throws ServiceException {
        int folderId = mailbox.getAccount().isFeatureAntispamEnabled() && SpamHandler.isSpam(getMimeMessage()) ?
                Mailbox.ID_FOLDER_SPAM : defaultFolderId;
        return addMessage(folderId, flagActions, tags);
    }

    private Message addMessage(int folderId, Collection<ActionFlag> flagActions, String[] tags)
            throws ServiceException {
        try {
            DeliveryOptions dopt = new DeliveryOptions().setFolderId(folderId).setNoICal(noICal).setRecipientEmail(recipientAddress);
            dopt.setFlags(FilterUtil.getFlagBitmask(flagActions, Flag.BITMASK_UNREAD)).setTags(tags);
            return mailbox.addMessage(octxt, parsedMessage, dopt, dctxt);
        } catch (IOException e) {
            throw ServiceException.FAILURE("Unable to add incoming message", e);
        }
    }

    @Override
    public void redirect(String destinationAddress)
    throws ServiceException {
        FilterUtil.redirect(octxt, mailbox, parsedMessage.getOriginalMessage(), destinationAddress);
    }

    @Override
    public void reply(String bodyTemplate) throws ServiceException, MessagingException {
        FilterUtil.reply(octxt, mailbox, parsedMessage, bodyTemplate);
    }

    @Override
    public void notify(
            String emailAddr, String subjectTemplate, String bodyTemplate, int maxBodyBytes, List<String> origHeaders)
            throws ServiceException, MessagingException {
        FilterUtil.notify(
                octxt, mailbox, parsedMessage, emailAddr, subjectTemplate, bodyTemplate, maxBodyBytes, origHeaders);
    }

    @Override
    public int getMessageSize() {
        return size;
    }

    @Override
    public void discard() {
    }

    @Override
    public void beforeFiltering() {
    }

    @Override
    public void afterFiltering() {
    }
}
