/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.taglib;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.servlet.ZmailServlet;

public class ZmailTag extends BodyTagSupport {

    /**
     * Override getContentStart and getContentEnd
     */
    public String getContentStart(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        return "";
    }

    public String getContentEnd(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        return "";
    }

    private AuthToken getAuthToken() throws ZmailTagException, ServiceException {
        HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
        
        AuthToken token = null;
        try {
            token = AuthProvider.getAuthToken(req, false);
            if (token == null)
                throw ZmailTagException.AUTH_FAILURE("no auth cookie");
        } catch (AuthTokenException ate) {
            throw ZmailTagException.AUTH_FAILURE("cannot parse authtoken");
        }

        if (token.isExpired()) {
            throw ZmailTagException.AUTH_FAILURE("authtoken expired");
        }
        
        return token;
    }
    
    private Account getRequestAccount(AuthToken token) throws ZmailTagException, ServiceException {
    	Provisioning prov = Provisioning.getInstance();
        Account acct = prov.get(Key.AccountBy.id, token.getAccountId(), token);
        if (acct == null) {
        	throw ZmailTagException.AUTH_FAILURE("account not found "+token.getAccountId());
        }
        return acct;
    }

    public int doStartTag() throws JspTagException {
        try {
            AuthToken authToken = getAuthToken();
            Account acct = getRequestAccount(authToken);
            OperationContext octxt = new OperationContext(acct);

            String content = getContentStart(acct, octxt);
            if (content.length() > 0) {
                JspWriter out = pageContext.getOut();
                out.print(content);
            }
        } catch (IOException ioe) {
        	throw ZmailTagException.IO_ERROR(ioe);
        } catch (ServiceException se){
        	throw ZmailTagException.SERVICE_ERROR(se);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException {
        try {
            AuthToken authToken = getAuthToken();
            Account acct = getRequestAccount(authToken);
            OperationContext octxt = new OperationContext(acct);

            String content = getContentEnd(acct, octxt);
            JspWriter out = pageContext.getOut();
            out.print(content);
        } catch (IOException ioe) {
        	throw ZmailTagException.IO_ERROR(ioe);
        } catch (ServiceException se){
        	throw ZmailTagException.SERVICE_ERROR(se);
        }
        return EVAL_PAGE;
    }
}
