/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.HashSet;
import java.util.Map;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Document;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.service.mail.ToXML;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class ListDocumentRevisions extends DocDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element doc = request.getElement(MailConstants.E_DOC);
        String id = doc.getAttribute(MailConstants.A_ID);
        int version = (int) doc.getAttributeLong(MailConstants.A_VERSION, -1);
        int count = (int) doc.getAttributeLong(MailConstants.A_COUNT, 1);

        Element response = zsc.createElement(MailConstants.LIST_DOCUMENT_REVISIONS_RESPONSE);

        Document item;

        ItemId iid = new ItemId(id, zsc);
        item = mbox.getDocumentById(octxt, iid.getId());

        if (version < 0) {
            version = item.getVersion();
        }
        MailItem.Type type = item.getType();
        HashSet<Account> accounts = new HashSet<Account>();
        Provisioning prov = Provisioning.getInstance();
        while (version > 0 && count > 0) {
            item = (Document) mbox.getItemRevision(octxt, iid.getId(), type, version);
            if (item != null) {
                ToXML.encodeDocument(response, ifmt, octxt, item);
                Account a = prov.getAccountByName(item.getCreator());
                if (a != null)
                    accounts.add(a);
            }
            version--; count--;
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
