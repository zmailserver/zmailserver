/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
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
package org.zmail.cs.dav.service.method;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.dav.DavContext;
import org.zmail.cs.dav.DavException;
import org.zmail.cs.dav.DavProtocol;
import org.zmail.cs.dav.LockMgr;
import org.zmail.cs.dav.service.DavMethod;

public class Unlock extends DavMethod {
	public static final String UNLOCK  = "UNLOCK";
	public String getName() {
		return UNLOCK;
	}
	public void handle(DavContext ctxt) throws DavException, IOException, ServiceException {
		String token = ctxt.getRequest().getHeader(DavProtocol.HEADER_LOCK_TOKEN);
		if (token != null) {
            LockMgr.getInstance().deleteLock(ctxt, ctxt.getUri(), LockMgr.Lock.parseLockTokenHeader(token));
		}
		ctxt.getResponse().setStatus(HttpServletResponse.SC_NO_CONTENT);
		ctxt.responseSent();
	}
}
