/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
/*
 * Created on Sep 23, 2005
 */
package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.mailbox.Color;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Mountpoint;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

public class CreateMountpoint extends MailDocumentHandler {

    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_MOUNT, MailConstants.A_FOLDER };
    private static final String[] RESPONSE_ITEM_PATH = new String[] { };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_FOLDER_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return true;
    }

    @Override
    protected String[] getResponseItemPath() {
        return RESPONSE_ITEM_PATH;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        Element t = request.getElement(MailConstants.E_MOUNT);
        String name = t.getAttribute(MailConstants.A_NAME);
        String view = t.getAttribute(MailConstants.A_DEFAULT_VIEW, null);
        String flags = t.getAttribute(MailConstants.A_FLAGS, null);
        byte color       = (byte) t.getAttributeLong(MailConstants.A_COLOR, MailItem.DEFAULT_COLOR);
        String rgb       = t.getAttribute(MailConstants.A_RGB, null);
        ItemId iidParent = new ItemId(t.getAttribute(MailConstants.A_FOLDER), zsc);
        boolean fetchIfExists = t.getAttributeBool(MailConstants.A_FETCH_IF_EXISTS, false);
        boolean reminderEnabled = t.getAttributeBool(MailConstants.A_REMINDER, false);

        Account target = null;
        String ownerId = t.getAttribute(MailConstants.A_ZIMBRA_ID, null);
        if (ownerId == null) {
            String ownerName = t.getAttribute(MailConstants.A_OWNER_NAME);
            target = Provisioning.getInstance().get(AccountBy.name, ownerName, zsc.getAuthToken());

            // prevent directory harvest attack, mask no such account as permission denied
            if (target == null)
                throw ServiceException.PERM_DENIED("you do not have sufficient permissions");

            ownerId = target.getId();
            if (ownerId.equalsIgnoreCase(zsc.getRequestedAccountId()))
                throw ServiceException.INVALID_REQUEST("cannot mount your own folder", null);
        }

        Element remote = fetchRemoteFolder(zsc, context, ownerId, (int) t.getAttributeLong(MailConstants.A_REMOTE_ID, -1), t.getAttribute(MailConstants.A_PATH, null));
        int remoteId = new ItemId(remote.getAttribute(MailConstants.A_ID), zsc).getId();
        String remoteUuid = remote.getAttribute(MailConstants.A_UUID, null);
        if (view == null)
            view = remote.getAttribute(MailConstants.A_DEFAULT_VIEW, null);

        Mountpoint mpt;
        try {
            Color itemColor = rgb != null ? new Color(rgb) : new Color(color);
            mpt = mbox.createMountpoint(octxt, iidParent.getId(), name, ownerId, remoteId, remoteUuid, MailItem.Type.of(view),
                    Flag.toBitmask(flags), itemColor, reminderEnabled);
        } catch (ServiceException se) {
            if (se.getCode() == MailServiceException.ALREADY_EXISTS && fetchIfExists) {
                Folder folder = mbox.getFolderByName(octxt, iidParent.getId(), name);
                if (folder instanceof Mountpoint)
                    mpt = (Mountpoint) folder;
                else
                    throw se;
            } else {
                throw se;
            }
        }

        Element response = zsc.createElement(MailConstants.CREATE_MOUNTPOINT_RESPONSE);
        if (mpt != null) {
            Element eMount = ToXML.encodeMountpoint(response, ifmt, octxt, mpt);
            // transfer folder counts and subfolders to the serialized mountpoint from the serialized target folder
            ToXML.transferMountpointContents(eMount, remote);
        }
        return response;
    }

    private Element fetchRemoteFolder(ZmailSoapContext zsc, Map<String, Object> context, String ownerId, int remoteId, String remotePath)
    throws ServiceException {
        Element request = zsc.createRequestElement(MailConstants.GET_FOLDER_REQUEST);
        if (remoteId > 0)
            request.addElement(MailConstants.E_FOLDER).addAttribute(MailConstants.A_FOLDER, remoteId);
        else if (remotePath != null)
            request.addElement(MailConstants.E_FOLDER).addAttribute(MailConstants.A_PATH, remotePath);
        else
            throw ServiceException.INVALID_REQUEST("must specify one of rid/path", null);

        Element response = proxyRequest(request, context, ownerId);
        Element remote = response.getOptionalElement(MailConstants.E_FOLDER);
        if (remote == null)
            throw ServiceException.INVALID_REQUEST("cannot mount a search or mountpoint", null);
        return remote;
    }
}
