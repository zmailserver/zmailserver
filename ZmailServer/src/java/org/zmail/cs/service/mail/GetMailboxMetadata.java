/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Metadata;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.soap.ZmailSoapContext;

public class GetMailboxMetadata extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        Element meta = request.getElement(MailConstants.E_METADATA);
        String section = meta.getAttribute(MailConstants.A_SECTION);
        Metadata metadata = mbox.getConfig(octxt, section);

        Element response = zsc.createElement(MailConstants.GET_MAILBOX_METADATA_RESPONSE);
        meta = response.addElement(MailConstants.E_METADATA);
        meta.addAttribute(MailConstants.A_SECTION, section);

        if (metadata != null) {
            for (Map.Entry<String, ?> entry : metadata.asMap().entrySet()) {
                meta.addKeyValuePair(entry.getKey(), entry.getValue().toString());
            }
        }

        return response;
    }
}
