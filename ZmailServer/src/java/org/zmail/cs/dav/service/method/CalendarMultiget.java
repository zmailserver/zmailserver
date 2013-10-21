/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import org.dom4j.Element;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.dav.DavContext;
import org.zmail.cs.dav.DavContext.RequestProp;
import org.zmail.cs.dav.DavElements;
import org.zmail.cs.dav.DavException;
import org.zmail.cs.dav.resource.CalendarCollection;
import org.zmail.cs.dav.resource.DavResource;
import org.zmail.cs.dav.service.DavResponse;

/*
 * draft-dusseault-caldav section 9.10
 * 
 *     <!ELEMENT calendar-multiget ((DAV:allprop |
 *                                   DAV:propname |
 *                                   DAV:prop)?, DAV:href+)>
 *                                
 */
public class CalendarMultiget extends Report {
	public void handle(DavContext ctxt) throws ServiceException, DavException {
		Element query = ctxt.getRequestMessage().getRootElement();
		if (!query.getQName().equals(DavElements.E_CALENDAR_MULTIGET))
			throw new DavException("msg "+query.getName()+" is not calendar-multiget", HttpServletResponse.SC_BAD_REQUEST, null);

		DavResponse resp = ctxt.getDavResponse();
		ArrayList<String> hrefs = new ArrayList<String>();
		for (Object obj : query.elements(DavElements.E_HREF))
			if (obj instanceof Element)
				hrefs.add(((Element)obj).getText());
		long ts = System.currentTimeMillis();
		DavResource reqResource = ctxt.getRequestedResource();
		if (!(reqResource instanceof CalendarCollection))
			throw new DavException("requested resource is not a calendar collection", HttpServletResponse.SC_BAD_REQUEST, null);
		CalendarCollection calResource = (CalendarCollection) reqResource;
		long now = System.currentTimeMillis();
		ZmailLog.dav.debug("GetRequestedResource: "+(now - ts)+"ms");
		RequestProp reqProp = ctxt.getRequestProp();
		for (DavResource rs : calResource.getAppointmentsByUids(ctxt, hrefs))
			resp.addResource(ctxt, rs, reqProp, false);
		ts = now;
		now = System.currentTimeMillis();
		ZmailLog.dav.debug("multiget: "+(now - ts)+"ms");
	}
}
