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
package com.zimbra.cs.service.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.zimbra.client.ZFolder;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMountpoint;
import com.zimbra.common.account.Key;
import com.zimbra.common.auth.ZAuthToken;
import com.zimbra.common.mailbox.Color;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.soap.SoapProtocol;
import com.zimbra.common.util.ArrayUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AuthToken;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.mailbox.Flag;
import com.zimbra.cs.mailbox.Folder;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.MailItem.TargetConstraint;
import com.zimbra.cs.mailbox.MailServiceException;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.Mountpoint;
import com.zimbra.cs.mailbox.OperationContext;
import com.zimbra.cs.mailbox.util.TagUtil;
import com.zimbra.cs.service.util.ItemId;
import com.zimbra.cs.session.Session;
import com.zimbra.cs.session.SoapSession;
import com.zimbra.cs.util.AccountUtil;
import com.zimbra.soap.SoapEngine;
import com.zimbra.soap.ZimbraSoapContext;

/**
 * @since May 29, 2005
 */
public class ItemAction extends MailDocumentHandler {

    protected static final String[] OPERATION_PATH = new String[] { MailConstants.E_ACTION, MailConstants.A_OPERATION };
    protected static final String[] TARGET_ITEM_PATH = new String[] { MailConstants.E_ACTION, MailConstants.A_ID };

    public static final String OP_TAG = "tag";
    public static final String OP_FLAG = "flag";
    public static final String OP_PRIORITY = "priority";
    public static final String OP_READ = "read";
    public static final String OP_COLOR = "color";
    public static final String OP_HARD_DELETE = "delete";
    public static final String OP_RECOVER = "recover";  // recover by copying then deleting from dumpster
    public static final String OP_DUMPSTER_DELETE = "dumpsterdelete";  // delete from dumpster
    public static final String OP_MOVE = "move";
    public static final String OP_COPY = "copy";
    public static final String OP_SPAM = "spam";
    public static final String OP_TRASH = "trash";
    public static final String OP_RENAME = "rename";
    public static final String OP_UPDATE = "update";
    public static final String OP_LOCK = "lock";
    public static final String OP_UNLOCK = "unlock";
    public static final String OP_INHERIT = "inherit";
    public static final String OP_MUTE = "mute";

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);

        Element action = request.getElement(MailConstants.E_ACTION);
        String operation = action.getAttribute(MailConstants.A_OPERATION).toLowerCase();

        String successes = handleCommon(context, request, operation, MailItem.Type.UNKNOWN);

        Element response = zsc.createElement(MailConstants.ITEM_ACTION_RESPONSE);
        Element act = response.addUniqueElement(MailConstants.E_ACTION);
        act.addAttribute(MailConstants.A_ID, successes);
        act.addAttribute(MailConstants.A_OPERATION, operation);
        return response;
    }

    protected String getOperation(String opAttr) {
        // strip off a leading '!', if present
        return (opAttr.startsWith("!") ? opAttr.substring(1) : opAttr).toLowerCase();
    }

    protected String handleCommon(Map<String, Object> context, Element request, String opAttr, MailItem.Type type)
    throws ServiceException {
        Element action = request.getElement(MailConstants.E_ACTION);
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        SoapProtocol responseProto = zsc.getResponseProtocol();

        // determine the requested operation
        boolean flagValue = !opAttr.startsWith("!");
        String opStr = getOperation(opAttr);

        // figure out which items are local and which ones are remote, and proxy accordingly
        List<Integer> local = new ArrayList<Integer>();
        Map<String, StringBuilder> remote = new HashMap<String, StringBuilder>();
        partitionItems(zsc, action.getAttribute(MailConstants.A_ID), local, remote);
        if (remote.isEmpty() && local.isEmpty()) {
            return "";
        }

        // for moves/copies, make sure that we're going to receive notifications from the target folder
        Account remoteNotify = forceRemoteSession(zsc, context, octxt, opStr, action);

        // handle referenced items living on other servers
        StringBuilder successes = proxyRemoteItems(action, remote, request, context);

        // handle referenced items living on this server
        if (!local.isEmpty()) {
            String constraint = action.getAttribute(MailConstants.A_TARGET_CONSTRAINT, null);
            TargetConstraint tcon = TargetConstraint.parseConstraint(mbox, constraint);

            String localResults;

            // set additional parameters (depends on op type)
            if (opStr.equals(OP_TAG)) {
                String tagName = action.getAttribute(MailConstants.A_TAG_NAMES, null);
                if (tagName == null) {
                    if (action.getAttribute(MailConstants.A_TAG) == null) {
                        throw ServiceException.INVALID_REQUEST("missing required attribute: " + MailConstants.A_TAG_NAMES, null);
                    }
                    tagName = TagUtil.tagIdToName(mbox, octxt, (int) action.getAttributeLong(MailConstants.A_TAG));
                }
                localResults = ItemActionHelper.TAG(octxt, mbox, responseProto, local, type, tagName, flagValue, tcon).getResult();
            } else if (opStr.equals(OP_FLAG)) {
                localResults = ItemActionHelper.FLAG(octxt, mbox, responseProto, local, type, flagValue, tcon).getResult();
            } else if (opStr.equals(OP_PRIORITY)) {
                localResults = ItemActionHelper.PRIORITY(octxt, mbox, responseProto, local, type, flagValue, tcon).getResult();
            } else if (opStr.equals(OP_READ)) {
                localResults = ItemActionHelper.READ(octxt, mbox, responseProto, local, type, flagValue, tcon).getResult();
            } else if (opStr.equals(OP_COLOR)) {
                Color color = getColor(action);
                localResults = ItemActionHelper.COLOR(octxt, mbox, responseProto, local, type, tcon, color).getResult();
            } else if (opStr.equals(OP_HARD_DELETE)) {
                localResults = ItemActionHelper.HARD_DELETE(octxt, mbox, responseProto, local, type, tcon).getResult();
            } else if (opStr.equals(OP_RECOVER)) {
                ItemId iidFolder = new ItemId(action.getAttribute(MailConstants.A_FOLDER), zsc);
                localResults = ItemActionHelper.RECOVER(octxt, mbox, responseProto, local, type, tcon, iidFolder).getResult();
            } else if (opStr.equals(OP_DUMPSTER_DELETE)) {
                localResults = ItemActionHelper.DUMPSTER_DELETE(octxt, mbox, responseProto, local, type, tcon).getResult();
            } else if (opStr.equals(OP_TRASH)) {
                ItemId iidTrash = new ItemId(mbox, Mailbox.ID_FOLDER_TRASH);
                localResults = ItemActionHelper.MOVE(octxt, mbox, responseProto, local, type, tcon, iidTrash).getResult();
            } else if (opStr.equals(OP_MOVE)) {
                ItemId iidFolder = new ItemId(action.getAttribute(MailConstants.A_FOLDER), zsc);
                localResults = ItemActionHelper.MOVE(octxt, mbox, responseProto, local, type, tcon, iidFolder).getResult();
            } else if (opStr.equals(OP_COPY)) {
                ItemId iidFolder = new ItemId(action.getAttribute(MailConstants.A_FOLDER), zsc);
                localResults = ItemActionHelper.COPY(octxt, mbox, responseProto, local, type, tcon, iidFolder).getResult();
            } else if (opStr.equals(OP_SPAM)) {
                String defaultFolder = (flagValue ? Mailbox.ID_FOLDER_SPAM : Mailbox.ID_FOLDER_INBOX) + "";
                ItemId iidFolder = new ItemId(action.getAttribute(MailConstants.A_FOLDER, defaultFolder), zsc);
                localResults = ItemActionHelper.SPAM(octxt, mbox, responseProto, local, type, flagValue, tcon, iidFolder).getResult();
            } else if (opStr.equals(OP_RENAME)) {
                String name = action.getAttribute(MailConstants.A_NAME);
                ItemId iidFolder = new ItemId(action.getAttribute(MailConstants.A_FOLDER, "-1"), zsc);
                localResults = ItemActionHelper.RENAME(octxt, mbox, responseProto, local, type, tcon, name, iidFolder).getResult();
            } else if (opStr.equals(OP_UPDATE)) {
                String folderId = action.getAttribute(MailConstants.A_FOLDER, null);
                ItemId iidFolder = new ItemId(folderId == null ? "-1" : folderId, zsc);
                if (!iidFolder.belongsTo(mbox)) {
                    throw ServiceException.INVALID_REQUEST("cannot move item between mailboxes", null);
                } else if (folderId != null && iidFolder.getId() <= 0) {
                    throw MailServiceException.NO_SUCH_FOLDER(iidFolder.getId());
                }
                String name = action.getAttribute(MailConstants.A_NAME, null);
                String flags = action.getAttribute(MailConstants.A_FLAGS, null);
                String[] tags = TagUtil.parseTags(action, mbox, octxt);
                Color color = getColor(action);
                localResults = ItemActionHelper.UPDATE(octxt, mbox, responseProto, local, type, tcon, name, iidFolder, flags, tags, color).getResult();
            } else if (opStr.equals(OP_LOCK)) {
                localResults = ItemActionHelper.LOCK(octxt, mbox, responseProto, local, type, tcon).getResult();
            } else if (opStr.equals(OP_UNLOCK)) {
                localResults = ItemActionHelper.UNLOCK(octxt, mbox, responseProto, local, type, tcon).getResult();
            } else if (opStr.equals(OP_INHERIT)) {
                mbox.alterTag(octxt, ArrayUtil.toIntArray(local), type, Flag.FlagInfo.NO_INHERIT, false, tcon);
                localResults = Joiner.on(",").join(local);
            } else if (opStr.equals(OP_MUTE) && type == MailItem.Type.CONVERSATION) {
                // note that "mute" ignores the tcon value
                localResults = ItemActionHelper.TAG(octxt, mbox, responseProto, local, type, Flag.FlagInfo.MUTED.toString(), flagValue, null).getResult();
                if (flagValue) {
                    // when marking muted, items are also marked read
                    ItemActionHelper.READ(octxt, mbox, responseProto, local, type, flagValue, null).getResult();
                }
            } else {
                throw ServiceException.INVALID_REQUEST("unknown operation: " + opStr, null);
            }
            successes.append(successes.length() > 0 ? "," : "").append(localResults);
        }

        // for moves/copies, make sure that we received notifications from the target folder
        if (remoteNotify != null) {
            proxyRequest(zsc.createElement(MailConstants.NO_OP_REQUEST), context, remoteNotify.getId());
        }

        return successes.toString();
    }

    public static Color getColor(Element action) throws ServiceException {
        String rgb = action.getAttribute(MailConstants.A_RGB, null);
        byte c = (byte) action.getAttributeLong(MailConstants.A_COLOR, -1);
        if (rgb == null && c < 0) {
            return new Color(-1);  // it will default to ORANGE
        } else if (rgb == null) {
            return new Color(c);
        } else {
            return new Color(rgb);
        }
    }

    private Account forceRemoteSession(ZimbraSoapContext zsc, Map<String, Object> context, OperationContext octxt, String op, Element action)
    throws ServiceException {
        // only proxying notification from the user's home-server master session
        if (!zsc.isNotificationEnabled()) {
            return null;
        }

        Session session = (Session) context.get(SoapEngine.ZIMBRA_SESSION);
        if (session instanceof SoapSession.DelegateSession) {
            session = ((SoapSession.DelegateSession) session).getParentSession();
        }
        if (!(session instanceof SoapSession) || session.getMailbox() == null) {
            return null;
        }
        SoapSession ss = (SoapSession) session;

        // only have to worry about operations where things can get created in other mailboxes (regular notification works for all other cases)
        if (!op.equals(OP_MOVE) && !op.equals(OP_COPY) && !op.equals(OP_SPAM) && !op.equals(OP_RENAME) && !op.equals(OP_UPDATE)) {
            return null;
        }
        String folderStr = action.getAttribute(MailConstants.A_FOLDER, null);
        if (folderStr == null) {
            return null;
        }

        // recursively dereference mountpoints to find ultimate target folder
        ItemId iidFolder = new ItemId(folderStr, zsc), iidRequested = iidFolder;
        Account owner = null;
        int hopCount = 0;
        ZAuthToken zat = null;
        while (hopCount < ZimbraSoapContext.MAX_HOP_COUNT) {
            owner = Provisioning.getInstance().getAccountById(iidFolder.getAccountId());
            if (Provisioning.onLocalServer(owner)) {
                try {
                    Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(owner);
                    Folder folder = mbox.getFolderById(octxt, iidFolder.getId());
                    if (!(folder instanceof Mountpoint))
                        break;
                    iidFolder = ((Mountpoint) folder).getTarget();
                } catch (ServiceException e) {
                    // could be a PERM_DENIED, could be something else -- this is not the right place to fail, however
                    break;
                }
            } else {
                if (zat == null) {
                    AuthToken at = zsc.getAuthToken();
                    String pxyAuthToken = at.getProxyAuthToken();
                    zat = pxyAuthToken == null ? at.toZAuthToken() : new ZAuthToken(pxyAuthToken);
                }
                ZMailbox.Options zoptions = new ZMailbox.Options(zat, AccountUtil.getSoapUri(owner));
                zoptions.setNoSession(true);
                zoptions.setTargetAccount(owner.getId());
                zoptions.setTargetAccountBy(Key.AccountBy.id);
                ZMailbox zmbx = ZMailbox.getMailbox(zoptions);
                ZFolder zfolder = zmbx.getFolderById(iidFolder.toString(zsc.getAuthtokenAccountId()));
                if (!(zfolder instanceof ZMountpoint))
                    break;
                iidFolder = new ItemId(((ZMountpoint) zfolder).getCanonicalRemoteId(), zsc.getAuthtokenAccountId());
            }
            hopCount++;
        }
        if (hopCount >= ZimbraSoapContext.MAX_HOP_COUNT) {
            throw MailServiceException.TOO_MANY_HOPS(iidRequested);
        }

        // avoid dereferencing the mountpoint again later on
        action.addAttribute(MailConstants.A_FOLDER, iidFolder.toString());

        // fault in a session to listen in on the target folder's mailbox
        if (iidFolder.belongsTo(session.getAuthenticatedAccountId())) {
            return null;
        } else if (iidFolder.isLocal()) {
            ss.getDelegateSession(iidFolder.getAccountId());
            return null;
        } else {
            try {
                proxyRequest(zsc.createElement(MailConstants.NO_OP_REQUEST), context, owner.getId());
                return owner;
            } catch (ServiceException e) {
                return null;
            }
        }
    }

    static void partitionItems(ZimbraSoapContext zsc, String ids, List<Integer> local, Map<String, StringBuilder> remote)
    throws ServiceException {
        Account acct = getRequestedAccount(zsc);
        for (String target : ids.split(",")) {
            ItemId iid = new ItemId(target, zsc);
            if (iid.belongsTo(acct)) {
                local.add(iid.getId());
            } else {
                StringBuilder sb = remote.get(iid.getAccountId());
                if (sb == null) {
                    remote.put(iid.getAccountId(), new StringBuilder(iid.toString()));
                } else {
                    sb.append(',').append(iid.toString());
                }
            }
        }
    }

    protected StringBuilder proxyRemoteItems(Element action, Map<String, StringBuilder> remote, Element request, Map<String, Object> context)
    throws ServiceException {
        String folderStr = action.getAttribute(MailConstants.A_FOLDER, null);
        if (folderStr != null) {
            // fully qualify the folder ID (if any) in order for proxying to work
            ItemId iidFolder = new ItemId(folderStr, getZimbraSoapContext(context));
            action.addAttribute(MailConstants.A_FOLDER, iidFolder.toString());
        }

        StringBuilder successes = new StringBuilder();
        for (Map.Entry<String, StringBuilder> entry : remote.entrySet()) {
            // update the <action> element to reference the subset of target items belonging to this user...
            String itemIds = entry.getValue().toString();
            action.addAttribute(MailConstants.A_ID, itemIds);
            // ... proxy to the target items' owner's server...
            String accountId = entry.getKey();
            Element response = proxyRequest(request, context, accountId);
            // ... and try to extract the list of items affected by the operation
            try {
                String completed = response.getElement(MailConstants.E_ACTION).getAttribute(MailConstants.A_ID);
                successes.append(completed.length() > 0 && successes.length() > 0 ? "," : "").append(completed);
            } catch (ServiceException e) {
                ZimbraLog.misc.warn("could not extract ItemAction successes from proxied response", e);
            }
        }

        return successes;
    }

    /**
     * Validates the grant expiry time against the maximum allowed expiry duration and returns the effective expiry
     * time for the grant.
     *
     * @param grantExpiry Grant expiry XML attribute value
     * @param maxLifetime Maximum allowed grant expiry duration
     * @return Effective expiry time for the grant. Return value of 0 indicates that grant never expires.
     * @throws ServiceException If the grant expiry time is not valid according to the expiration policy.
     */
    protected static long validateGrantExpiry(String grantExpiry, long maxLifetime) throws ServiceException {
        long now = System.currentTimeMillis();
        long ret = grantExpiry == null ? maxLifetime == 0 ? 0 : now + maxLifetime : Long.valueOf(grantExpiry);
        if (grantExpiry != null && maxLifetime != 0 && (ret == 0 || ret > now + maxLifetime)) {
            throw ServiceException.PERM_DENIED("share expiration policy conflict");
        }
        return ret;
    }
}
