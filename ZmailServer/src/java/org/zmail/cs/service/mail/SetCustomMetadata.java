/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.MailItem.CustomMetadata;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

public class SetCustomMetadata extends MailDocumentHandler {
    private static final String[] TARGET_ITEM_PATH = new String[] { MailConstants.A_ID };
    @Override protected String[] getProxiedIdPath(Element request)     { return TARGET_ITEM_PATH; }
    @Override protected boolean checkMountpointProxy(Element request)  { return false; }

    @Override public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element meta = request.getElement(MailConstants.E_METADATA);
        String section = meta.getAttribute(MailConstants.A_SECTION);
        section = section.trim();
        if (section.length() == 0 || section.length() > 36)
            throw ServiceException.INVALID_REQUEST("invalid length for custom metadata section name", null);

        CustomMetadata custom = new CustomMetadata(section);
        for (Element.KeyValuePair kvp : meta.listKeyValuePairs())
            custom.put(kvp.getKey(), kvp.getValue());

        ItemId iid = new ItemId(request.getAttribute(MailConstants.A_ID), zsc);
        mbox.setCustomData(octxt, iid.getId(), MailItem.Type.UNKNOWN, custom);

        Element response = zsc.createElement(MailConstants.SET_METADATA_RESPONSE);
        response.addAttribute(MailConstants.A_ID, ifmt.formatItemId(iid));
        return response;
    }
}
