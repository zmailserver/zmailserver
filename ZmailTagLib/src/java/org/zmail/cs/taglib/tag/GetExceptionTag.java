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
package org.zmail.cs.taglib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;

import org.zmail.cs.taglib.bean.ZExceptionBean;
import org.zmail.cs.taglib.bean.ZTagLibException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.zclient.ZClientException;
import org.zmail.common.service.ServiceException;
import org.eclipse.jetty.io.RuntimeIOException;

public class GetExceptionTag extends ZmailSimpleTag {
    
    private String mVar;
    private Exception mException;
    
    public void setVar(String var) { this.mVar = var; }
    
    public void setException(Exception e) { this.mException = e; }
    
    public void doTag() throws JspException, IOException {
        ZExceptionBean eb = new ZExceptionBean(mException);
        Exception e = eb.getException();
        if (e != null) {
            if (
                    (!(e instanceof ServiceException)) ||
                            ((e instanceof ZTagLibException) && (!(e.getCause() instanceof SkipPageException || e.getCause() instanceof IllegalStateException || e.getCause() instanceof RuntimeIOException))) || (e instanceof ZClientException))
                ZmailLog.webclient.warn("local exception", e);
        }
        getJspContext().setAttribute(mVar, eb,  PageContext.PAGE_SCOPE);
    }
}
