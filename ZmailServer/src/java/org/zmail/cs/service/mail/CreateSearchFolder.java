/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.mailbox.Color;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.SearchFolder;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @since May 26, 2004
 */
public class CreateSearchFolder extends MailDocumentHandler  {

    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_SEARCH, MailConstants.A_FOLDER };
    private static final String[] RESPONSE_ITEM_PATH = new String[] { };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_FOLDER_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return true;
    }

    @Override
    protected String[] getResponseItemPath() {
        return RESPONSE_ITEM_PATH;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element t = request.getElement(MailConstants.E_SEARCH);
        String name      = t.getAttribute(MailConstants.A_NAME);
        String query     = t.getAttribute(MailConstants.A_QUERY);
        String types     = t.getAttribute(MailConstants.A_SEARCH_TYPES, null);
        String sort      = t.getAttribute(MailConstants.A_SORTBY, null);
        String flags     = t.getAttribute(MailConstants.A_FLAGS, null);
        byte color       = (byte) t.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        String rgb       = t.getAttribute(MailConstants.A_RGB, null);

        Color itemColor = rgb != null ? new Color(rgb) : new Color(color);
        ItemId iidParent = new ItemId(t.getAttribute(MailConstants.A_FOLDER), zsc);

        SearchFolder search = mbox.createSearchFolder(octxt, iidParent.getId(),
            name, query, types, sort, Flag.toBitmask(flags), itemColor);

        Element response = zsc.createElement(MailConstants.CREATE_SEARCH_FOLDER_RESPONSE);
        if (search != null)
            ToXML.encodeSearchFolder(response, ifmt, search);
        return response;
    }
}
