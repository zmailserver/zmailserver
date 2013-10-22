/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2009, 2010, 2012, 2013 VMware, Inc.
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

package org.zmail.webClient.servlet;

import org.zmail.common.util.ZmailLog;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Props2JsServlet extends org.zmail.kabuki.servlets.Props2JsServlet {

	//
	// Constants
	//

	protected static final String P_SKIN = "skin";
	protected static final String A_SKIN = P_SKIN;

	//
	// Protected methods
	//

	protected String getSkin(HttpServletRequest req) {
		String skin = (String)req.getAttribute(A_SKIN);
		if (skin == null) {
			skin = req.getParameter(P_SKIN);
		}
        if (skin != null) {
            skin = skin.replaceAll("[^A-Za-z0-9]", "");
        }
		return skin;
	}

	//
	// org.zmail.kabuki.servlets.Props2JsServlet methods
	//

	protected String getRequestURI(HttpServletRequest req) {
		return this.getSkin(req) + super.getRequestURI(req);
	}

	protected List<String> getBasenamePatternsList(HttpServletRequest req) {
		List<String> list = super.getBasenamePatternsList(req);
		String skin = this.getSkin(req);
		String patterns = "skins/"+skin+"/messages/${name},skins/"+skin+"/keys/${name}";
		list.add(patterns);
		return list;
	};

	//
	// org.zmail.kabuki.servlets.Props2JsServlet methods
	//

	protected boolean isWarnEnabled() {
		return ZmailLog.webclient.isWarnEnabled();
	}
	protected boolean isErrorEnabled() {
		return ZmailLog.webclient.isErrorEnabled();
	}
	protected boolean isDebugEnabled() {
		return ZmailLog.webclient.isDebugEnabled();
	}

	protected void warn(String message) {
		ZmailLog.webclient.warn(message);
	}
	protected void error(String message) {
		ZmailLog.webclient.error(message);
	}
	protected void debug(String message) {
		ZmailLog.webclient.debug(message);
	}

} // class Props2JsServlet