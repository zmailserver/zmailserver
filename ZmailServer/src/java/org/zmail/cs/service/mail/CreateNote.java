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

/*
 * Created on Sep 8, 2004
 */
package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Note;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.Note.Rectangle;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author dkarp
 */
public class CreateNote extends MailDocumentHandler {

    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_NOTE, MailConstants.A_FOLDER };
    private static final String[] RESPONSE_ITEM_PATH = new String[] { };
    protected String[] getProxiedIdPath(Element request)     { return TARGET_FOLDER_PATH; }
    protected boolean checkMountpointProxy(Element request)  { return true; }
    protected String[] getResponseItemPath()  { return RESPONSE_ITEM_PATH; }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element t = request.getElement(MailConstants.E_NOTE);
        ItemId iidFolder = new ItemId(t.getAttribute(MailConstants.A_FOLDER), zsc);
        String content = t.getAttribute(MailConstants.E_CONTENT);
        byte color = (byte) t.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        String strBounds = t.getAttribute(MailConstants.A_BOUNDS, null);
        Rectangle bounds = new Rectangle(strBounds);

        Note note = mbox.createNote(octxt, content, bounds, color, iidFolder.getId());

        Element response = zsc.createElement(MailConstants.CREATE_NOTE_RESPONSE);
        if (note != null)
        	ToXML.encodeNote(response, ifmt, octxt, note);
        return response;
	}
}
