/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.taglib.tag;

import com.zimbra.common.service.ServiceException;
import com.zimbra.client.ZEmailAddress;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMailbox.ZOutgoingMessage;
import com.zimbra.client.ZMailbox.ZOutgoingMessage.AttachedMessagePart;
import com.zimbra.client.ZMailbox.ZOutgoingMessage.MessagePart;
import com.zimbra.client.ZMessage;
import com.zimbra.cs.taglib.bean.ZMessageComposeBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveDraftTag extends ZimbraSimpleTag {

    private String mVar;
    private String mTo;
    private String mReplyTo;
    private String mCc;
    private String mBcc;
    private String mFrom;
    private String mSubject;
    private String mContentType = "text/plain";
    private String mContent;
    private String mReplyType;
    private String mInReplyTo;
    private String mMessageId;    
    private String mMessages;
    private String mAttachments;
	private String mAttachmentUploadId;
    private String mDraftId;
    private String mFolderId;
    private ZMessageComposeBean mCompose;

    public void setCompose(ZMessageComposeBean compose) { mCompose = compose; }

    public void setVar(String var) { this.mVar = var; }
    
    public void setTo(String to) { mTo = to; }

    public void setReplyto(String replyTo) { mReplyTo = replyTo; }
    
    public void setReplytype(String replyType) { mReplyType = replyType; }

    public void setContent(String content) { mContent = content; }

    public void setContenttype(String contentType) { mContentType = contentType; }

    public void setSubject(String subject) { mSubject = subject; }

    public void setMessageid(String id) { mMessageId = id; }

    public void setInreplyto(String inReplyto) { mInReplyTo = inReplyto; }

    public void setFrom(String from) { mFrom = from; }

    public void setBcc(String bcc) { mBcc = bcc; }

    public void setCc(String cc) { mCc = cc; }

    public void setMessages(String messages) { mMessages = messages; }

    public void setAttachments(String attachments) { mAttachments = attachments; }
    
    public void setAttachmentuploadid(String id) { mAttachmentUploadId = id; }

    public void setDraftid(String draftId) { mDraftId = draftId; }
        
    public void setFolderid(String folderId) { mFolderId = folderId; }
    
    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        try {
            ZMailbox mbox = getMailbox();

            ZOutgoingMessage m = mCompose != null ? mCompose.toOutgoingMessage(mbox) :  getOutgoingMessage();

            String folderId = (mFolderId != null && mFolderId.length() > 0) ? mFolderId : null;
            String draftId = (mDraftId != null && mDraftId.length() > 0) ? mDraftId : null;

            ZMessage response = mbox.saveDraft(m, draftId, folderId);
            jctxt.setAttribute(mVar, response, PageContext.PAGE_SCOPE);

        } catch (ServiceException e) {
            throw new JspTagException(e.getMessage(), e);
        }
    }

    private ZOutgoingMessage getOutgoingMessage() throws ServiceException {

            List<ZEmailAddress> addrs = new ArrayList<ZEmailAddress>();

            if (mTo != null && mTo.length() > 0)
                addrs.addAll(ZEmailAddress.parseAddresses(mTo, ZEmailAddress.EMAIL_TYPE_TO));

            if (mReplyTo != null && mReplyTo.length() > 0)
                addrs.addAll(ZEmailAddress.parseAddresses(mReplyTo, ZEmailAddress.EMAIL_TYPE_REPLY_TO));

            if (mCc != null && mCc.length() > 0)
                addrs.addAll(ZEmailAddress.parseAddresses(mCc, ZEmailAddress.EMAIL_TYPE_CC));

            if (mFrom != null && mFrom.length() > 0)
                addrs.addAll(ZEmailAddress.parseAddresses(mFrom, ZEmailAddress.EMAIL_TYPE_FROM));

            if (mBcc != null && mBcc.length() > 0)
                addrs.addAll(ZEmailAddress.parseAddresses(mBcc, ZEmailAddress.EMAIL_TYPE_BCC));

            List<String> messages;

            if (mMessages != null && mMessages.length() > 0) {
                messages = new ArrayList<String>();
                for (String m : mMessages.split(",")) {
                    messages.add(m);
                }
            } else {
                messages = null;
            }

            List<AttachedMessagePart> attachments;
            if (mAttachments != null && mAttachments.length() > 0) {
                attachments = new ArrayList<AttachedMessagePart>();
                for (String partName : mAttachments.split(",")) {
                    attachments.add(new AttachedMessagePart(mMessageId, partName, null));
                }
            } else {
                attachments = null;
            }

            ZOutgoingMessage m = new ZOutgoingMessage();

            m.setAddresses(addrs);

            m.setSubject(mSubject);

            if (mInReplyTo != null && mInReplyTo.length() > 0)
                m.setInReplyTo(mInReplyTo);

            m.setMessagePart(new MessagePart(mContentType, mContent));

            m.setMessageIdsToAttach(messages);

            m.setMessagePartsToAttach(attachments);

            m.setAttachmentUploadId(mAttachmentUploadId);

            if (mMessageId != null && mMessageId.length() > 0)
                m.setOriginalMessageId(mMessageId);

            if (mReplyType != null && mReplyType.length() > 0)
                m.setReplyType(mReplyType);
        return m;
    }

}
