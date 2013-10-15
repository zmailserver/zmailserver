/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.offline;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.continuation.ContinuationSupport;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.offline.OfflineSyncManager;
import org.zmail.cs.service.mail.NoOp;
import org.zmail.soap.SoapServlet;

public class OfflineNoOp extends NoOp {

    private static final String TIME_KEY = "ZDNoOpStartTime";

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        HttpServletRequest servletRequest = (HttpServletRequest) context.get(SoapServlet.SERVLET_REQUEST);
        boolean isResumed = !ContinuationSupport.getContinuation(servletRequest).isInitial();
        if (!isResumed) {
			OfflineSyncManager.getInstance().clientPing();
        }
        boolean wait = request.getAttributeBool(MailConstants.A_WAIT, false);
        long start = System.currentTimeMillis();
        if (!isResumed) {
            servletRequest.setAttribute(TIME_KEY, start);
        }
		Element response = super.handle(request, context);
		response.addAttribute(MailConstants.A_WAIT, wait);
		response.addAttribute(MailConstants.A_TIME, System.currentTimeMillis()-(Long) servletRequest.getAttribute(TIME_KEY));
		return response;
	}
}
