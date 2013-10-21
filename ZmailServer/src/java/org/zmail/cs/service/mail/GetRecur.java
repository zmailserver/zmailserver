/* ***** BEGIN LICENSE BLOCK *****
/* Zimbra Collaboration Suite Server
/* Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
/* 
/* The contents of this file are subject to the Zimbra Public License
/* Version 1.3 ("License"); you may not use this file except in
/* compliance with the License.  You may obtain a copy of the License at
/* http://www.zimbra.com/license.
/* 
/* Software distributed under the License is distributed on an "AS IS"
/* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public final class GetRecur extends MailDocumentHandler {

    private static final String[] TARGET_ITEM_PATH = new String[] { MailConstants.A_ID };
    private static final String[] RESPONSE_ITEM_PATH = new String[] { };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_ITEM_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return false;
    }

    @Override
    protected String[] getResponseItemPath()  {
        return RESPONSE_ITEM_PATH;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        ItemId iid = new ItemId(request.getAttribute(MailConstants.A_ID), zsc);
        Element response = getResponseElement(zsc);

        CalendarItem calItem = mbox.getCalendarItemById(octxt, iid.getId());
        ToXML.encodeCalendarItemRecur(response, calItem);
        return response;
    }
}
