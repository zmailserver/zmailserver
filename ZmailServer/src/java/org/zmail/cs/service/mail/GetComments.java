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

import java.util.HashSet;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.Comment;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

public class GetComments extends MailDocumentHandler {

    private static final String[] PARENT_ID_PATH = new String[] { MailConstants.E_COMMENT, MailConstants.A_PARENT_ID };
    
    @Override
    protected String[] getProxiedIdPath(Element request)     { return PARENT_ID_PATH; }
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element c = request.getElement(MailConstants.E_COMMENT);
        String itemId = c.getAttribute(MailConstants.A_PARENT_ID);

        ItemId iid = new ItemId(itemId, zsc);

        Element response = zsc.createElement(MailConstants.GET_COMMENTS_RESPONSE);
        HashSet<Account> accounts = new HashSet<Account>();
        for (Comment comment : mbox.getComments(octxt, iid.getId(), 0, -1)) {
            accounts.add(comment.getCreatorAccount());
            ToXML.encodeComment(response, ifmt, octxt, comment);
        }
        
        for (Account a : accounts) {
            Element user = response.addElement(MailConstants.A_USER);
            user.addAttribute(MailConstants.A_ID, a.getId());
            user.addAttribute(MailConstants.A_EMAIL, a.getName());
            user.addAttribute(MailConstants.A_NAME, a.getDisplayName());
        }
        return response;
    }
}
