/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.zimlet.ZimletUtil;

public class ZimletConfig extends ZmailTag {

    private String mZimlet;
    private String mAction;
    private String mVar;
    private String mName;
    private String mScope;

    public void setZimlet(String val) {
        mZimlet = val;
    }

    public String getZimlet() {
        return mZimlet;
    }

    public void setAction(String val) {
        mAction = val;
    }

    public String getAction() {
        return mAction;
    }

    public void setVar(String val) {
        mVar = val;
    }

    public String getVar() {
        return mVar;
    }

    public void setName(String val) {
        mName = val;
    }

    public String getName() {
        return mName;
    }

    public void setScope(String val) {
        mScope = val;
    }

    public String getScope() {
        return mScope;
    }

    public String doListConfig(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        if (mVar == null) {
            throw ZmailTagException.MISSING_ATTR("var");
        }
        Map<String,Map> m = new HashMap<String,Map>();
    	HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
    	req.setAttribute(mVar, m);

    	org.zmail.cs.zimlet.ZimletConfig config = ZimletUtil.getZimletConfig(mZimlet);

    	Map gc, sc, lc;
    	
        if (config == null) {
        	gc = new HashMap();
        	sc = new HashMap();
        	lc = new HashMap();
        } else {
        	gc = config.getGlobalConfig();
        	sc = config.getSiteConfig();
        	lc = config.getSiteConfig();
        }
    	m.put("global", gc);
    	m.put("site", sc);
    	m.put("local", lc);
    	return "";
    }
    
    public String doGetConfig(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        if (mName == null) {
            throw ZmailTagException.MISSING_ATTR("name");
        }
        org.zmail.cs.zimlet.ZimletConfig config = ZimletUtil.getZimletConfig(mZimlet);
        String val;

        if (config == null) {
        	return "zimlet " + mName + "not found";
        }
        // if scope is not defined, search both global and site config.
       	val = config.getSiteConf(mName);
        if (mScope == null && val == null ||
        	mScope != null && mScope.equals("global")) {
        	val = config.getGlobalConf(mName);
        }
        if (val == null) val = "";
        return val;
    }
    
    public String getContentStart(Account acct, OperationContext octxt) throws ZmailTagException, ServiceException {
        if (mZimlet == null) {
            throw ZmailTagException.MISSING_ATTR("zimlet");
        }
        if (mAction != null && mAction.equals("list")) {
        	return doListConfig(acct, octxt);
        }
        return doGetConfig(acct, octxt);
    }
}
