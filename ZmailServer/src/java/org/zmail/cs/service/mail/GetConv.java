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

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.index.SearchParams;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.index.SearchParams.ExpandResults;
import org.zmail.cs.mailbox.Conversation;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @since May 26, 2004
 * @author schemers
 */
public class GetConv extends MailDocumentHandler  {

    private static final String[] TARGET_CONV_PATH = new String[] { MailConstants.E_CONV, MailConstants.A_ID };

    @Override
    protected String[] getProxiedIdPath(Element request)  { return TARGET_CONV_PATH; }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element econv = request.getElement(MailConstants.E_CONV);
        ItemId iid = new ItemId(econv.getAttribute(MailConstants.A_ID), zsc);

        SearchParams params = new SearchParams();
        params.setInlineRule(ExpandResults.valueOf(econv.getAttribute(MailConstants.A_FETCH, null), zsc));
        if (params.getInlineRule() != ExpandResults.NONE) {
            params.setWantHtml(econv.getAttributeBool(MailConstants.A_WANT_HTML, false));
            params.setMaxInlinedLength((int) econv.getAttributeLong(MailConstants.A_MAX_INLINED_LENGTH, -1));
            for (Element eHdr : econv.listElements(MailConstants.A_HEADER)) {
                params.addInlinedHeader(eHdr.getAttribute(MailConstants.A_ATTRIBUTE_NAME));
            }
        }

        Conversation conv = mbox.getConversationById(octxt, iid.getId());

        if (conv == null) {
            throw MailServiceException.NO_SUCH_CONV(iid.getId());
        }
        List<Message> msgs = mbox.getMessagesByConversation(octxt, conv.getId(), SortBy.DATE_ASC, -1);
        if (msgs.isEmpty() && zsc.isDelegatedRequest()) {
            throw ServiceException.PERM_DENIED("you do not have sufficient permissions");
        }
        Element response = zsc.createElement(MailConstants.GET_CONV_RESPONSE);
        ToXML.encodeConversation(response, ifmt, octxt, conv, msgs, params);
        return response;
    }
}
