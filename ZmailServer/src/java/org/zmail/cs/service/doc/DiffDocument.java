/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.doc;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.zmail.cs.mailbox.Document;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.doc.Diff;
import org.zmail.cs.doc.Diff.Chunk;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.soap.ZmailSoapContext;

public class DiffDocument extends DocDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        Element doc = request.getElement(MailConstants.E_DOC);
        String idStr = doc.getAttribute(MailConstants.A_ID, null);
        int v1 = (int) doc.getAttributeLong(MailConstants.A_V1, -1);
        int v2 = (int) doc.getAttributeLong(MailConstants.A_V2, -1);

        ItemId id = new ItemId(idStr, zsc);
        Document r1 = (Document) mbox.getItemRevision(octxt, id.getId(), MailItem.Type.UNKNOWN, v1);
        Document r2 = (Document) mbox.getItemRevision(octxt, id.getId(), MailItem.Type.UNKNOWN, v2);

        Element response = zsc.createElement(MailConstants.DIFF_DOCUMENT_RESPONSE);

        try {
            Collection<Chunk> diffResult = Diff.getResult(r1.getContentStream(), r2.getContentStream());
            for (Chunk c : diffResult) {
                Element chunk = response.addElement(MailConstants.E_CHUNK);
                chunk.addAttribute(MailConstants.A_DISP, c.disposition.toString());
                chunk.setText(StringUtil.join("\n", c.content));
            }
        } catch (IOException e) {
            throw ServiceException.FAILURE("can't diff documents", e);
        }

        return response;
    }
}
