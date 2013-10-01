/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.service.offline;

import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.offline.OfflineLog;
import com.zimbra.cs.offline.OfflineSyncManager;
import com.zimbra.cs.offline.common.OfflineConstants;
import com.zimbra.cs.offline.util.HeapDumpScanner;
import com.zimbra.soap.DocumentHandler;
import com.zimbra.soap.ZimbraSoapContext;

/**
 * <DialogActionRequest id="accountId" type="dialog-type" action="yes|no" />
 */
public class OfflineDialogAction extends DocumentHandler {
    public static final String DIALOG_TYPE_RESYNC = "resync";
    public static final String DIALOG_RESYNC_MSG = "Client hasn't been synced for too long. Needs to reset mailbox and resync.";
    public static final String DIALOG_TYPE_FOLDER_MOVE_START = "foldermove_start";
    public static final String DIALOG_TYPE_FOLDER_MOVE_START_MSG = "Folder move started";
    public static final String DIALOG_TYPE_FOLDER_MOVE_COMPLETE = "foldermove_complete";
    public static final String DIALOG_TYPE_FOLDER_MOVE_COMPLETE_MSG = "Folder move completed";
    public static final String DIALOG_TYPE_FOLDER_MOVE_FAIL = "foldermove_failed";
    public static final String DIALOG_TYPE_FOLDER_MOVE_FAIL_MSG = "Folder move failed";
    public static final String DIALOG_TYPE_HEAP_DUMP_UPLOAD_CONSENT = "heapdump_upload";
    public static final String DIALOG_HEAP_DUMP_UPLOAD_CONSENT_MSG = "Do we have your consent to upload heap dump of ZD's previous crash ?";

    private static enum DialogType {
        resync, heapdump_upload
    }

    private static enum DialogAction {
        yes, no
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        String accountId = request.getAttribute(AdminConstants.E_ID);
        DialogType type = null;
        DialogAction action = null;
        try {
            type = DialogType.valueOf(request.getAttribute(AdminConstants.A_TYPE));
            action = DialogAction.valueOf(request.getAttribute(AdminConstants.A_ACTION));
        } catch (IllegalArgumentException e) {
            OfflineLog.offline.warn("dialog [type,action] cannot be recognized. [%s , %s]",
                    request.getAttribute(AdminConstants.A_TYPE), request.getAttribute(AdminConstants.A_ACTION));
            throw ServiceException.INVALID_REQUEST("dialogue type/action cannot be recognized", e);
        }

        handleDialog(accountId, type, action);
        OfflineSyncManager.getInstance().unregisterDialog(accountId, type.name());

        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Element resp = zsc.createElement(OfflineConstants.DIALOG_ACTION_RESPONSE);
        return resp;
    }

    protected void handleDialog(String accountId, DialogType type, DialogAction action) throws ServiceException {
        switch (type) {
        case resync:
            handleResync(accountId, action);
            break;
        case heapdump_upload:
            handleHeapdumpUpload(accountId, action);
            break;
        }
    }

    private void handleResync(String accountId, DialogAction action) throws ServiceException {
        switch (action) {
        case yes:
            OfflineLog.offline.debug("about to resync mailbox %s", accountId);
            Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(accountId);
            mbox.deleteMailbox();
            break;
        case no:
            OfflineLog.offline.debug("user refused to resync mailbox %s", accountId);
            break;
        }
    }

    private void handleHeapdumpUpload(String accountId, DialogAction action) {
        switch (action) {
        case yes:
            OfflineLog.offline.info("user chose to upload heap dump.");
            HeapDumpScanner.getInstance().upload();
            break;
        case no:
            OfflineLog.offline.info("user chose NOT to upload heap dump.");
            break;
        }
    }
}
