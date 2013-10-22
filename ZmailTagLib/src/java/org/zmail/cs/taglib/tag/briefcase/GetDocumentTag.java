/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.taglib.tag.briefcase;

import com.zimbra.cs.taglib.tag.ZimbraSimpleTag;
import com.zimbra.cs.taglib.bean.ZMessageComposeBean;
import com.zimbra.cs.taglib.bean.ZMessageBean;
import com.zimbra.cs.taglib.bean.ZMailboxBean;
import com.zimbra.cs.taglib.bean.ZDocumentBean;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZDocument;
import com.zimbra.common.service.ServiceException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspTagException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.FilePart;

public class GetDocumentTag extends ZimbraSimpleTag {

    private String mVar;
    private String mId;
    private ZMailboxBean mMailbox;

    public void setId(String id) { this.mId = id; }
    public void setVar(String var) { this.mVar = var; }
    public void setBox(ZMailboxBean mailbox) { this.mMailbox = mailbox; }    

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        PageContext pc = (PageContext) jctxt;
        try {
            ZMailbox mbox = mMailbox != null ? mMailbox.getMailbox() :  getMailbox();
			ZDocument doc = mbox.getDocument(this.mId);
            jctxt.setAttribute(mVar, new ZDocumentBean(doc), PageContext.PAGE_SCOPE);
        } catch (ServiceException e) {
            throw new JspTagException(e.getMessage(), e);
        }

    }

}
