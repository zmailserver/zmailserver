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
 * Created on May 26, 2004
 */
package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.mailbox.Color;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.Tag;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class CreateTag extends MailDocumentHandler  {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element t = request.getElement(MailConstants.E_TAG);
        String name = t.getAttribute(MailConstants.A_NAME);
        String rgb = t.getAttribute(MailConstants.A_RGB, null);

        Tag tag;
        if (rgb != null) {
            Color color = new Color(rgb);
            tag = mbox.createTag(octxt, name, color);
        }
        else {
            byte color = (byte) t.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
            tag = mbox.createTag(octxt, name, color);
        }
        
        Element response = zsc.createElement(MailConstants.CREATE_TAG_RESPONSE);
        if (tag != null)
        	ToXML.encodeTag(response, ifmt, octxt, tag);
        return response;
	}
}
