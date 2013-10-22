/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.dav.resource;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import com.zimbra.common.account.Key;
import com.zimbra.common.auth.ZAuthToken;
import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.dav.DavContext;
import com.zimbra.cs.dav.DavElements;
import com.zimbra.cs.dav.DavException;
import com.zimbra.cs.dav.property.Acl;
import com.zimbra.cs.dav.property.ResourceProperty;
import com.zimbra.cs.mailbox.ACL;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.MailServiceException;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.Mountpoint;
import com.zimbra.cs.mailbox.calendar.cache.CtagInfo;
import com.zimbra.cs.service.AuthProvider;
import com.zimbra.cs.service.util.ItemId;
import com.zimbra.cs.util.AccountUtil;
import com.zimbra.client.ZFolder;
import com.zimbra.client.ZMailbox;

public class RemoteCollection extends Collection {

    protected String mRemoteOwnerId;
    protected int mRemoteId;
    protected String mCtag;
    protected short mRights = 0;

    public RemoteCollection(DavContext ctxt, Mountpoint mp) throws DavException, ServiceException {
        super(ctxt, mp);
        mRemoteOwnerId = mp.getOwnerId();
        mRemoteId = mp.getRemoteId();
        addResourceType(DavElements.E_MOUNTPOINT);
        getMountpointTarget(ctxt);
        mMailboxId = 0;
        Account target = Provisioning.getInstance().get(Key.AccountBy.id, mRemoteOwnerId);
        if (target != null && Provisioning.onLocalServer(target))
            mMailboxId = MailboxManager.getInstance().getMailboxByAccount(target).getId();
    }

    public RemoteCollection(DavContext ctxt, String path, Account user) throws DavException, ServiceException {
        super(path, user.getName());
        ZAuthToken zat = AuthProvider.getAuthToken(ctxt.getAuthAccount()).toZAuthToken();
        ZMailbox mbox = getRemoteMailbox(zat, user.getId());
        ZFolder folder = mbox.getFolderByPath(path);
        if (folder == null)
            throw MailServiceException.NO_SUCH_FOLDER(path);
        mOwnerId = user.getId();
        mId = new ItemId(folder.getId(), mOwnerId).getId();
        mPath = path;
        mSubject = folder.getName();
        type = MailItem.Type.FOLDER;
        ZFolder.View zview = folder.getDefaultView();
        if (zview != null)
            view = MailItem.Type.of(zview.name());
    }
    
    @Override
    public void delete(DavContext ctxt) throws DavException {
        throw new DavException("cannot delete this resource", HttpServletResponse.SC_FORBIDDEN, null);
    }

    @Override
    public java.util.Collection<DavResource> getChildren(DavContext ctxt) throws DavException {
        return Collections.emptyList();
    }

    @Override
    public DavResource createItem(DavContext ctxt, String name) throws DavException, IOException {
        throw new DavException("request should be proxied", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    static ZMailbox getRemoteMailbox(ZAuthToken zat, String ownerId) throws ServiceException {
        Account target = Provisioning.getInstance().get(Key.AccountBy.id, ownerId);
        if (target == null)
            return null;
        ZMailbox.Options zoptions = new ZMailbox.Options(zat, AccountUtil.getSoapUri(target));
        zoptions.setNoSession(true);
        zoptions.setTargetAccount(ownerId);
        zoptions.setTargetAccountBy(Key.AccountBy.id);
        return ZMailbox.getMailbox(zoptions);
    }
    protected void getMountpointTarget(DavContext ctxt) throws ServiceException {
        ZAuthToken zat = AuthProvider.getAuthToken(ctxt.getAuthAccount()).toZAuthToken();
        ZMailbox zmbx = getRemoteMailbox(zat, mRemoteOwnerId);
        if (zmbx == null)
            return;
        ZFolder folder = zmbx.getFolder(new ItemId(mRemoteOwnerId, mRemoteId).toString(mOwnerId));
        if (folder == null)
            return;
        mCtag = CtagInfo.makeCtag(folder);
        setProperty(DavElements.E_GETCTAG, mCtag);
        mRights = ACL.stringToRights(folder.getEffectivePerms());
        addProperty(Acl.getCurrentUserPrivilegeSet(mRights));
        addProperty(Acl.getMountpointTargetPrivilegeSet(mRights));
        String targetUrl = UrlNamespace.getResourceUrl(Provisioning.getInstance().get(Key.AccountBy.id, mRemoteOwnerId), folder.getPath() + "/");
        ResourceProperty mp = new ResourceProperty(DavElements.E_MOUNTPOINT_TARGET_URL);
        mp.addChild(DavElements.E_HREF).setText(targetUrl);
        addProperty(mp);
    }
}
