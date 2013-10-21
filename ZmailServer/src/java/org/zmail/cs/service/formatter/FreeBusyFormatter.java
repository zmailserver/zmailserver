/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.formatter;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import javax.servlet.ServletException;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.service.UserServletContext;
import org.zmail.cs.service.UserServletException;
import org.zmail.cs.service.formatter.FormatterFactory.FormatType;

public class FreeBusyFormatter extends Formatter {

    private static final String ATTR_FREEBUSY = "zmail_freebusy";

    @Override
    public FormatType getType() {
        return FormatType.FREE_BUSY;
    }

    @Override
    public boolean requiresAuth() {
        return true;
    }

    @Override
    public Set<MailItem.Type> getDefaultSearchTypes() {
        return EnumSet.of(MailItem.Type.APPOINTMENT);
    }

    @Override
    public void formatCallback(UserServletContext context)
            throws IOException, ServiceException, UserServletException, ServletException {
        context.req.setAttribute(ATTR_FREEBUSY, "true");
        HtmlFormatter.dispatchJspRest(context.getServlet(), context);
    }
}
