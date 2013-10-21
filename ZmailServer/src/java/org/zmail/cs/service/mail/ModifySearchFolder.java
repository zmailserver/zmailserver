/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.SearchFolder;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class ModifySearchFolder extends MailDocumentHandler  {

    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_SEARCH, MailConstants.A_ID };
    protected String[] getProxiedIdPath(Element request)     { return TARGET_FOLDER_PATH; }
    protected boolean checkMountpointProxy(Element request)  { return false; }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
		Mailbox mbox = getRequestedMailbox(zsc);
		OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);
		
        Element t = request.getElement(MailConstants.E_SEARCH);
        ItemId iid = new ItemId(t.getAttribute(MailConstants.A_ID), zsc);
        String query = t.getAttribute(MailConstants.A_QUERY);
        String types = t.getAttribute(MailConstants.A_SEARCH_TYPES, null);
        String sort = t.getAttribute(MailConstants.A_SORTBY, null);
        
        mbox.modifySearchFolder(octxt, iid.getId(), query, types, sort);
        SearchFolder search = mbox.getSearchFolderById(octxt, iid.getId());
        
        Element response = zsc.createElement(MailConstants.MODIFY_SEARCH_FOLDER_RESPONSE);
    	ToXML.encodeSearchFolder(response, ifmt, search);
        return response;
	}
}
