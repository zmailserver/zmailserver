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
import org.zmail.common.soap.SoapFaultException;
import org.zmail.cs.mailbox.ACL;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.acl.FolderACL;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public class GetEffectiveFolderPerms extends MailDocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException, SoapFaultException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        OperationContext octxt = getOperationContext(zsc, context);
        
        Element eFolder = request.getElement(MailConstants.E_FOLDER);
        String fid = eFolder.getAttribute(MailConstants.A_FOLDER);
        
        ItemId iid = new ItemId(fid, zsc);
        int folderId = iid.getId();
        
        Mailbox mbox;
        String ownerAcctId = iid.getAccountId();
        if (ownerAcctId == null)
            mbox = getRequestedMailbox(zsc);
        else
            mbox = MailboxManager.getInstance().getMailboxByAccountId(ownerAcctId);
        
        Folder folder = mbox.getFolderById(null, folderId);
        
        // return the effective permissions - authed user dependent
        Short perms = FolderACL.getEffectivePermissionsLocal(octxt, mbox, folder);
        
        Element response = zsc.createElement(MailConstants.GET_EFFECTIVE_FOLDER_PERMS_RESPONSE);
        encodePerms(response, perms);

        return response;
    }
    
    private void encodePerms(Element response, Short perms) {
        String permsStr = ACL.rightsToString(perms);
        Element eFolder = response.addElement(MailConstants.E_FOLDER);
        eFolder.addAttribute(MailConstants.A_RIGHTS, permsStr);
    }
}
