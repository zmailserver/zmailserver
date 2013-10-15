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
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.mailbox.Color;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.mailbox.ChangeTrackingMailbox.TracelessContext;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OfflineServiceException;
import org.zmail.cs.mailbox.ZcsMailbox;
import org.zmail.cs.redolog.op.CreateMountpoint;
import org.zmail.soap.ZmailSoapContext;

public class OfflineCreateMountpoint extends OfflineServiceProxy {

    public OfflineCreateMountpoint() {
        super("create mountpoint", false, false);
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext ctxt = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(ctxt);
        if (!(mbox instanceof ZcsMailbox)) {
            throw OfflineServiceException.MISCONFIGURED("incorrect mailbox class: " + mbox.getClass().getSimpleName());
        }
        Element t = request.getElement(MailConstants.E_MOUNT);
        t.addAttribute(MailConstants.A_FETCH_IF_EXISTS, true);
        Element response = super.handle(request, context);

        Element eMount = response.getElement(MailConstants.E_MOUNT);
        int parentId = (int) eMount.getAttributeLong(MailConstants.A_FOLDER);
        int id = (int) eMount.getAttributeLong(MailConstants.A_ID);
        String uuid = eMount.getAttribute(MailConstants.A_UUID);
        String name = (id == Mailbox.ID_FOLDER_ROOT) ? "ROOT" : MailItem.normalizeItemName(eMount.getAttribute(MailConstants.A_NAME));
        int flags = Flag.toBitmask(eMount.getAttribute(MailConstants.A_FLAGS, null));
        byte color = (byte) eMount.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        MailItem.Type view = MailItem.Type.of(eMount.getAttribute(MailConstants.A_DEFAULT_VIEW, null));
        String ownerId = eMount.getAttribute(MailConstants.A_ZIMBRA_ID);
        String ownerName = eMount.getAttribute(MailConstants.A_OWNER_NAME);
        int remoteId = (int) eMount.getAttributeLong(MailConstants.A_REMOTE_ID);
        int mod_content = (int) eMount.getAttributeLong(MailConstants.A_REVISION, -1);
        boolean reminderEnabled = eMount.getAttributeBool(MailConstants.A_REMINDER, false);

        OfflineProvisioning.getOfflineInstance().createMountpointAccount(ownerName, ownerId, ((ZcsMailbox)mbox).getOfflineAccount());
        CreateMountpoint redo = new CreateMountpoint(mbox.getId(), parentId, name, ownerId, remoteId, null, view, flags,
                new Color(color), reminderEnabled);
        redo.setIdAndUuid(id, uuid);
        redo.setChangeId(mod_content);
        try {
            mbox.createMountpoint(new TracelessContext(redo), parentId, name, ownerId, remoteId, null, view, flags, color, reminderEnabled);
        } catch (ServiceException e) {
            if (e.getCode() != MailServiceException.ALREADY_EXISTS)
                throw e;
        }

        return response;
    }
}
