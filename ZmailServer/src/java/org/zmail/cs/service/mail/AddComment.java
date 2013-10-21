/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.Comment;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public class AddComment extends MailDocumentHandler {

    private static final String[] PARENT_ID_PATH = new String[] { MailConstants.E_COMMENT, MailConstants.A_PARENT_ID };
    
    @Override
    protected String[] getProxiedIdPath(Element request)     { return PARENT_ID_PATH; }
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        String creator = zsc.getAuthtokenAccountId();

        Element c = request.getElement(MailConstants.E_COMMENT);
        String itemId = c.getAttribute(MailConstants.A_PARENT_ID);
        String text = c.getAttribute(MailConstants.A_TEXT);

        ItemId iid = new ItemId(itemId, zsc);
        Comment comment = mbox.createComment(octxt, iid.getId(), text, creator);

        Element response = zsc.createElement(MailConstants.ADD_COMMENT_RESPONSE);
        response.addElement(MailConstants.E_COMMENT).addAttribute(MailConstants.A_ID, comment.getId());
        return response;
    }
}
