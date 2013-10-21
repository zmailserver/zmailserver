/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public class PurgeRevision extends MailDocumentHandler {

    private static final String[] TARGET_PATH = new String[] { MailConstants.E_REVISION, MailConstants.A_ID };
    protected String[] getProxiedIdPath(Element request)     { return TARGET_PATH; }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element revisionElem = request.getElement(MailConstants.E_REVISION);
        ItemId iid = new ItemId(revisionElem.getAttribute(MailConstants.A_ID), zsc);
        int rev = (int)revisionElem.getAttributeLong(MailConstants.A_VERSION);
        boolean includeOlderRevisions = revisionElem.getAttributeBool(MailConstants.A_INCLUDE_OLDER_REVISIONS, false);
        Mailbox mbox = getRequestedMailbox(zsc);
        mbox.purgeRevision(getOperationContext(zsc, context), iid.getId(), rev, includeOlderRevisions);
        
        return zsc.createElement(MailConstants.PURGE_REVISION_RESPONSE);
    }
}
