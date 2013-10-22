/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.taglib.tag.contact;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.taglib.bean.ZContactBean;
import org.zmail.cs.taglib.tag.ZmailSimpleTag;
import org.zmail.client.ZMailbox;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public class GetContactTag extends ZmailSimpleTag {
    
    private String mVar;
    private String mId;
    private boolean mSync;
    
    public void setVar(String var) { this.mVar = var; }
    public void setId(String id) { this.mId = id; }    
    public void setSync(boolean sync) { this.mSync = sync; }

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        try {
            ZMailbox mbox = getMailbox();
            jctxt.setAttribute(mVar, new ZContactBean(mbox.getContact(mId)),  PageContext.PAGE_SCOPE);
        } catch (ServiceException e) {
            throw new JspTagException(e);
        }
    }
}
