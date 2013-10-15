/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.taglib.tag;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.taglib.bean.ZMessageComposeBean;
import org.zmail.client.ZDateTime;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZMailbox.ReplyVerb;
import org.zmail.client.ZMailbox.ZOutgoingMessage;
import org.zmail.client.ZMailbox.ZSendInviteReplyResult;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public class SendInviteReplyTag extends ZmailSimpleTag {

    private String mVar;
    
    private ZMessageComposeBean mCompose;

    public void setCompose(ZMessageComposeBean compose) { mCompose = compose; }

    public void setVar(String var) { this.mVar = var; }

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        try {
            ZMailbox mbox = getMailbox();

            ZOutgoingMessage m = mCompose.toOutgoingMessage(mbox);

            ZDateTime instance = mCompose.getInviteReplyInst() == 0 ? null :  new ZDateTime(mCompose.getInviteReplyInst(), mCompose.getInviteReplyAllDay(), mbox.getPrefs().getTimeZone());

            String compNum = mCompose.getCompNum();
            if (compNum != null && compNum.length()==0)
                compNum = "0";

            String instCompNum = mCompose.getInstanceCompNum();
            if (instCompNum != null && instCompNum.length()==0)
                instCompNum = null;


            if (instance != null && instCompNum != null)
                compNum = instCompNum;

            ZSendInviteReplyResult response = mbox.sendInviteReply(mCompose.getMessageId(), compNum, ReplyVerb.fromString(mCompose.getInviteReplyVerb()), true, null, instance, m);
            jctxt.setAttribute(mVar, response, PageContext.PAGE_SCOPE);

        } catch (ServiceException e) {
            throw new JspTagException(e.getMessage(), e);
        }
    }
}
