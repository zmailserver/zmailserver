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

package com.zimbra.cs.taglib.tag.calendar;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.taglib.bean.ZMessageBean;
import com.zimbra.cs.taglib.bean.ZMessageComposeBean;
import com.zimbra.cs.taglib.tag.ZimbraSimpleTag;
import com.zimbra.client.ZDateTime;
import com.zimbra.client.ZInvite;
import com.zimbra.client.ZInvite.ZComponent;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMailbox.ZOutgoingMessage;
import com.zimbra.client.ZMailbox.ZOutgoingMessage.MessagePart;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public class CancelAppointmentTag extends ZimbraSimpleTag {

    private ZMessageComposeBean mCompose;
    private ZMessageBean mMessage;
    private long mInstance;

    public void setCompose(ZMessageComposeBean compose) { mCompose = compose; }
    public void setMessage(ZMessageBean message) { mMessage = message; }
    public void setInstance(long instance) { mInstance = instance; }

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        PageContext pc = (PageContext) jctxt;

        try {
            ZMailbox mbox = getMailbox();

            if (mCompose != null) {
                cancelAppt(mbox, pc, mCompose, mMessage);
            } else {
                cancelAppt(mbox, mMessage, mInstance);
            }

        } catch (ServiceException e) {
            throw new JspTagException(e.getMessage(), e);
        }
    }

    private void cancelAppt(ZMailbox mbox, ZMessageBean message, long instance) throws ServiceException {
        ZInvite inv = message.getInvite();
        ZComponent comp = inv.getComponent();

        ZDateTime exceptionId = instance == 0 ?  null :
                new ZDateTime(instance, comp.isAllDay(), mbox.getPrefs().getTimeZone());

        ZOutgoingMessage m = new ZOutgoingMessage();
        m.setMessagePart(new MessagePart("text/plain", ""));
        mbox.cancelAppointment(message.getId(), "0", null, exceptionId , null, m);
    }

    private void cancelAppt(ZMailbox mbox, PageContext pc, ZMessageComposeBean compose, ZMessageBean message) throws ServiceException {
        ZInvite inv = compose.toInvite(mbox, message);

        boolean hasAttendeees = inv.getComponent().getAttendees().size() > 0;

        ZInvite previousInv = message != null ? message.getInvite() : null;
        ZComponent prevComp = previousInv != null ? previousInv.getComponent() : null;

        if (hasAttendeees) {
            String key = (compose.getUseInstance()) ? "apptInstanceCancelled" : "apptCancelled";
            compose.setInviteBlurb(mbox, pc, inv, previousInv, key);
        }

        ZDateTime exceptionId = prevComp != null && prevComp.isException() ? prevComp.getStart() : null;

        ZOutgoingMessage m = hasAttendeees ? compose.toOutgoingMessage(mbox) : null;

        if (compose.getUseInstance()) {
            if (compose.getExceptionInviteId() != null && compose.getExceptionInviteId().length() > 0) {
                mbox.cancelAppointment(compose.getExceptionInviteId(), /*TODO:pass thru */ "0", null, exceptionId , null, m);
            } else {
                exceptionId = new ZDateTime(compose.getInstanceStartTime(), compose.getAllDay(), mbox.getPrefs().getTimeZone());
                mbox.cancelAppointment(compose.getInviteId(), /*TODO:pass thru */ "0", null, exceptionId , null, m);
            }
        } else {
            mbox.cancelAppointment(compose.getInviteId(), /*TODO:pass thru */ "0", null, exceptionId , null, m);
        }
    }
}
