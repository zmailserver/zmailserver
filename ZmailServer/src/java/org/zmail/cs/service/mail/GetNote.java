/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Note;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

public class GetNote extends MailDocumentHandler {

    private static final String[] TARGET_NOTE_PATH = new String[] { MailConstants.E_NOTE, MailConstants.A_ID };
    protected String[] getProxiedIdPath(Element request)     { return TARGET_NOTE_PATH; }
    protected boolean checkMountpointProxy(Element request)  { return false; }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
		Mailbox mbox = getRequestedMailbox(zsc);
		OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);
		
		Element enote = request.getElement(MailConstants.E_NOTE);
        ItemId iid = new ItemId(enote.getAttribute(MailConstants.A_ID), zsc);

		Note note = mbox.getNoteById(octxt, iid.getId());

		if (note == null)
			throw MailServiceException.NO_SUCH_NOTE(iid.getId());
		
		Element response = zsc.createElement(MailConstants.GET_NOTE_RESPONSE);
		ToXML.encodeNote(response, ifmt, octxt, note);
		return response;
	}
}
