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
import org.zmail.cs.taglib.ZJspSession;
import org.zmail.client.ZChangePasswordResult;
import org.zmail.client.ZMailbox;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public class ChangePasswordTag extends ZmailSimpleTag {

    private String mUsername;
    private String mPassword;
    private String mNewPassword;
    private String mUrl = null;
    private boolean mSecure = ZJspSession.isProtocolModeHttps();
    private boolean mRememberMe;

    public void setUsername(String username) { this.mUsername = username; }

    public void setPassword(String password) { this.mPassword = password; }

    public void setNewpassword(String password) { this.mNewPassword = password; }

    public void setUrl(String url) { this.mUrl = url; }

    public void setSecure(boolean secure) { this.mSecure = secure; }

    public void setRememberme(boolean rememberMe) { this.mRememberMe = rememberMe; }

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();
        try {
            PageContext pageContext = (PageContext) jctxt;
            ZMailbox.Options options = new ZMailbox.Options();
            options.setAccount(mUsername);
            options.setPassword(mPassword);
            options.setNewPassword(mNewPassword);
            options.setUri(mUrl == null ? ZJspSession.getSoapURL(pageContext): mUrl);
            ZChangePasswordResult cpr = ZMailbox.changePassword(options);

            LoginTag.setCookie((HttpServletResponse)pageContext.getResponse(),
                    cpr.getAuthToken(),
                    mSecure,
                    mRememberMe,
                    cpr.getExpires());
 
        } catch (ServiceException e) {

            throw new JspTagException(e.getMessage(), e);
        }
    }
}
