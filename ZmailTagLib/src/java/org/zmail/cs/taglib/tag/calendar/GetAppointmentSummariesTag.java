/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.taglib.tag.calendar;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.taglib.bean.ZApptSummariesBean;
import org.zmail.cs.taglib.bean.ZMailboxBean;
import org.zmail.cs.taglib.tag.ZmailSimpleTag;
import org.zmail.client.ZAppointmentHit;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZMailbox.ZApptSummaryResult;
import org.zmail.client.ZSearchParams;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspTagException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class GetAppointmentSummariesTag extends ZmailSimpleTag {

    private String mVar;
    private String mVarException;
    private String mQuery;
    private long mStart;
    private long mEnd;
    private String mFolderId;
    private TimeZone mTimeZone = TimeZone.getDefault();
    private ZMailboxBean mMailbox;

    public void setVar(String var) { this.mVar = var; }
    public void setVarexception(String varException) { this.mVarException = varException; }
    public void setQuery(String query) { this.mQuery = query; }

    public void setStart(long start) { this.mStart = start; }
    public void setEnd(long end) { this.mEnd = end; }
    public void setFolderid(String folderId) { this.mFolderId = folderId; }
    public void setTimezone(TimeZone timeZone) { this.mTimeZone = timeZone; }
    public void setBox(ZMailboxBean mailbox) { this.mMailbox = mailbox; }

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        try {
            ZMailbox mbox = mMailbox != null ? mMailbox.getMailbox() :  getMailbox();

            List<ZAppointmentHit> appts;
            if (mFolderId == null || mFolderId.length() == 0) {
                // if non are checked, return no appointments (to match behavior of ajax client
                appts = new ArrayList<ZAppointmentHit>();
            } else if (mFolderId.indexOf(',') == -1) {
                List<ZApptSummaryResult> result = mbox.getApptSummaries(mQuery, mStart, mEnd, new String[] {mFolderId}, mTimeZone, ZSearchParams.TYPE_APPOINTMENT);
                //appts = mbox.getApptSummaries(mStart, mEnd, mFolderId);
                if (result.size() != 1) {
                    appts = new ArrayList<ZAppointmentHit>();
                    for (ZApptSummaryResult r : result) {
                        appts.addAll(r.getAppointments());
                    }
                } else {
                    ZApptSummaryResult asr = result.get(0);
                    appts = asr.getAppointments();
                }
            } else {
                appts = new ArrayList<ZAppointmentHit>();
                List<ZApptSummaryResult> result = mbox.getApptSummaries(mQuery, mStart, mEnd, mFolderId.split(","), mTimeZone, ZSearchParams.TYPE_APPOINTMENT);
                for (ZApptSummaryResult sum : result) {
                    appts.addAll(sum.getAppointments());
                }
            }
            jctxt.setAttribute(mVar, new ZApptSummariesBean(appts),  PageContext.PAGE_SCOPE);

        } catch (ServiceException e) {
            if (mVarException != null) {
                jctxt.setAttribute(mVarException, e,  PageContext.PAGE_SCOPE);
                jctxt.setAttribute(mVar, new ZApptSummariesBean(new ArrayList<ZAppointmentHit>()),  PageContext.PAGE_SCOPE);
            } else {
                throw new JspTagException(e);
            }
        }
    }
}
