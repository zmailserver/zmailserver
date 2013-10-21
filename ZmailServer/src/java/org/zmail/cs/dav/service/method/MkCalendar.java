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

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import org.zmail.cs.dav.DavContext;
import org.zmail.cs.dav.DavElements;
import org.zmail.cs.dav.DavException;
import org.zmail.cs.dav.DavProtocol;
import org.zmail.cs.dav.resource.CalendarCollection;
import org.zmail.cs.dav.resource.Collection;
import org.zmail.cs.dav.resource.UrlNamespace;
import org.zmail.cs.dav.service.DavMethod;
import org.zmail.cs.mailbox.MailItem;

public class MkCalendar extends DavMethod {
    public static final String MKCALENDAR = "MKCALENDAR";

    @Override
    public String getName() {
        return MKCALENDAR;
    }

    // valid return codes:
    // 201 Created, 207 Multi-Status (403, 409, 423, 424, 507),
    // 403 Forbidden, 409 Conflict, 415 Unsupported Media Type,
    // 507 Insufficient Storage
    @Override
    public void handle(DavContext ctxt) throws DavException, IOException {
        String user = ctxt.getUser();
        String name = ctxt.getItem();

        if (user == null || name == null)
            throw new DavException("invalid uri", HttpServletResponse.SC_FORBIDDEN, null);
        Element top = null;
        if (ctxt.hasRequestMessage()) {
            Document doc = ctxt.getRequestMessage();
            top = doc.getRootElement();
            if (!top.getName().equals(DavElements.P_MKCALENDAR))
                throw new DavException("msg "+top.getName()+" not allowed in MKCALENDAR", HttpServletResponse.SC_BAD_REQUEST, null);
        }

        Collection col = UrlNamespace.getCollectionAtUrl(ctxt, ctxt.getPath());
        if (col instanceof CalendarCollection)
            throw new DavException("can't create calendar under another calendar", HttpServletResponse.SC_FORBIDDEN, null);

        Collection newone = col.mkCol(ctxt, name, MailItem.Type.APPOINTMENT);
        boolean success = false;
        try {
            PropPatch.handlePropertyUpdate(ctxt, top, newone, true);
            success = true;
        } finally {
            if (!success)
                newone.delete(ctxt);
        }
        ctxt.setStatus(HttpServletResponse.SC_CREATED);
        ctxt.getResponse().addHeader(DavProtocol.HEADER_CACHE_CONTROL, DavProtocol.NO_CACHE);
    }

    @Override
    public void checkPrecondition(DavContext ctxt) throws DavException {
        // DAV:resource-must-be-null
        // CALDAV:calendar-collection-location-ok
        // CALDAV:valid-calendar-data
        // DAV:need-privilege
    }

    @Override
    public void checkPostcondition(DavContext ctxt) throws DavException {
        // DAV:initialize-calendar-collection
    }
}
