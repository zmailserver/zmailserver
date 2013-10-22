/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.cs.taglib.tag.calendar;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.taglib.bean.ZMailboxBean;
import org.zmail.cs.taglib.tag.ZmailSimpleTag;
import org.zmail.client.ZMailbox;


import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspTagException;
import java.io.IOException;


public class GetValidFolderIdsTag extends ZmailSimpleTag {
    private String mVar;
    private String mVarException;
    private String mFolderId;
    private ZMailboxBean mMailbox;

    public void setVar(String var) { this.mVar = var; }
    public void setVarexception(String varException) { this.mVarException = varException; }
    public void setFolderid(String folderId) { this.mFolderId = folderId; }
    public void setBox(ZMailboxBean mailbox) { this.mMailbox = mailbox; }
    
    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        try {
            ZMailbox mbox = mMailbox != null ? mMailbox.getMailbox() :  getMailbox();
            String result = mbox.getValidFolderIds(mFolderId);
            jctxt.setAttribute(mVar, result,  PageContext.PAGE_SCOPE);
        } catch (ServiceException e){
            if (mVarException != null) {
                jctxt.setAttribute(mVarException, e,  PageContext.PAGE_SCOPE);
                jctxt.setAttribute(mVar, "",  PageContext.PAGE_SCOPE);
            } else {
                throw new JspTagException(e);
            }
        }

    }
}