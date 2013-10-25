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
package com.zimbra.cs.datasource;

import java.util.List;
import com.zimbra.common.localconfig.LC;
import com.zimbra.common.net.SocketFactories;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Log;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.filter.RuleManager;
import com.zimbra.cs.mailbox.DeliveryContext;
import com.zimbra.cs.mailbox.Flag;
import com.zimbra.cs.mailbox.Message;
import com.zimbra.cs.mailclient.MailConfig;
import com.zimbra.cs.mailclient.MailConfig.Security;
import com.zimbra.cs.mime.ParsedMessage;
import com.zimbra.soap.type.DataSource.ConnectionType;
import com.zimbra.cs.mailclient.activesync.*;
import com.zimbra.cs.mailclient.activesync.ActiveSync.Callable2;
import com.zimbra.cs.mailclient.activesync.ASTypes.*;

public class ActiveSyncSync extends MailItemImport {
    private final ActiveSyncConfig config;
    private final ActiveSync as;
    private final boolean indexAttachments;

    private static final Log LOG = ZimbraLog.datasource;

    // Zimbra UID format is: item_id "." blob_digest
    //private static final Pattern PATTERN_ZIMBRA_UID = Pattern.compile("(\\d+)\\.([^\\.]+)");

    private AS_FolderSyncB foldersyncb; // Result of the last foldersync
    private AS_Sync syncb; // Result of last sync

    public ActiveSyncSync(DataSource ds) throws ServiceException {
        super(ds);
        config = newActiveSyncConfig(ds);
        as = new ActiveSync(config.getHost(), //serverName,
                            config.getRealm(), //domain,
                            config.getAuthenticationId(), //username,
                            ds.getDecryptedPassword(), //password,
                            config.getSecurity() == Security.SSL, //useSSL,
                            config.getAcceptAllCerts(), //acceptAllCerts,
                            config.getPolicyKey(), //policyKey,
                            config.getActiveSyncVersion(), //activeSyncVersion,
                            config.getDeviceType(), //deviceType,
                            config.getDeviceId() //deviceId
                            );

        indexAttachments = mbox.attachmentsIndexingEnabled();
    }

    private ActiveSyncConfig newActiveSyncConfig(DataSource ds) {
        ActiveSyncConfig config = new ActiveSyncConfig();
        config.setHost(ds.getHost());
        config.setPort(ds.getPort());
        config.setAuthenticationId(ds.getUsername());
        config.setSecurity(getSecurity(ds.getConnectionType()));
        if (ds.isDebugTraceEnabled()) {
            config.setLogger(SyncUtil.getTraceLogger(ZimbraLog.pop_client, ds.getId()));
        }
        config.setSocketFactory(SocketFactories.defaultSocketFactory());
        config.setSSLSocketFactory(SocketFactories.defaultSSLSocketFactory());
        config.setConnectTimeout(ds.getConnectTimeout(LC.javamail_activesync_timeout.intValue()));
        config.setReadTimeout(ds.getReadTimeout(LC.javamail_activesync_timeout.intValue()));
        return config;
    }


    private MailConfig.Security getSecurity(ConnectionType type) {
        if (type == null) {
            type = ConnectionType.cleartext;
        }
        switch (type) {
        case cleartext:
            return !dataSource.isOffline() && LC.javamail_activesync_enable_starttls.booleanValue() ?
                MailConfig.Security.TLS_IF_AVAILABLE : MailConfig.Security.NONE;
        case ssl:
            return MailConfig.Security.SSL;
        case tls:
            return MailConfig.Security.TLS;
        case tls_if_available:
            return MailConfig.Security.TLS_IF_AVAILABLE;
        default:
            return MailConfig.Security.NONE;
        }
    }

    @Override
    public void validateDataSource() throws ServiceException {
        // TODO: check other mandatory fields
        DataSource ds = getDataSource();
        if (ds.getHost() == null) {
            throw ServiceException.FAILURE(ds + ": host not set", null);
        }
        if (ds.getConnectionType() == null) {
            throw ServiceException.FAILURE(ds + ": connectionType not set", null);
        }
        if (ds.getUsername() == null) {
            throw ServiceException.FAILURE(ds + ": username not set", null);
        }
    }

    @Override
    public synchronized void test() throws ServiceException {
        validateDataSource();
        try {
            foldersyncb = as.foldersync();
        } catch (Exception e) {
            throw ServiceException.FAILURE("Unable to FolderSync with ACTIVESYNC server: " + dataSource, e);
        }
    }

    private void handleMessage(String code, javax.mail.Message message) {
        String ServerId;
        try {
            String[] ServerIds = message.getHeader("ZM-ActiveSync-ServerId");
            if (ServerIds.length == 1) {
                ServerId = ServerIds[0];
                switch (code) {
                case "add":
                    LOG.info("Adding ActiveSync message %s",ServerId);
                    ParsedMessage pm = new ParsedMessage((javax.mail.internet.MimeMessage) message,indexAttachments);
                    DeliveryContext dc = new DeliveryContext();
                    Message msg = null;
                    int size = message.getSize();
                    if (isOffline()) {
                        msg = addMessage(null, pm, size, dataSource.getFolderId(), Flag.BITMASK_UNREAD, dc);
                    } else {
                        Integer localId =
                            getFirstLocalId(RuleManager.applyRulesToIncomingMessage(null, mbox, pm, size,
                                                                                    dataSource.getEmailAddress(), dc,
                                                                                    dataSource.getFolderId(), true, false));
                        if (localId != null) {
                            msg = mbox.getMessageById(null, localId);
                        }
                    }
                    String uid = ServerId; // This needs verified.
                    if (msg != null && uid != null) {
                        ActiveSyncMessage msgTracker = new ActiveSyncMessage(dataSource, msg.getId(), uid);
                        msgTracker.add();
                    }
                    break;
                default:
                    break;
                }

            } else {
                ServerId = "bad_ServerId";
            }
        } catch (Exception e) {
            ServerId = "unknown_ServerId";
        }
    }

    @Override
    public synchronized void importData(List<Integer> folderIds, boolean fullSync) throws ServiceException {
        validateDataSource();
        try {
            if (foldersyncb == null) foldersyncb = as.foldersync();
            String inbox = as.getServerId("Inbox");
            if (inbox == null) throw new Exception("Can't determine ACTIVESYNC Inbox ServerId");
            syncb = as.sync_getchanges(inbox,AS_MIMESupport.never,AS_MIMETruncation.never_truncate,AS_Class.Email);
            as.convertToMimeMessages(syncb,
                                     new Callable2<String,javax.mail.Message>() {
                                         @Override
                                         public void call(String code, javax.mail.Message message) { handleMessage(code, message); }
                                     });
        } catch (Exception e) {
            throw ServiceException.FAILURE(
                "Synchronization of ACTIVESYNC folder failed", e);
        }

        /*
        try {
            if (connection.getMessageCount() > 0) {
                if (dataSource.leaveOnServer()) {
                    fetchAndRetainMessages();
                } else {
                    fetchAndDeleteMessages();
                }
            }
            connection.quit();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw ServiceException.FAILURE(
                "Synchronization of ACTIVESYNC folder failed", e);
        } finally {
            connection.close();
        }
        */
    }

    /*
    private void fetchAndDeleteMessages() throws Exception {
        Integer sizes[] = connection.getMessageSizes();

        LOG.info("Found %d new message(s) on remote server", sizes.length);
        IOExceptionHandler.getInstance().resetSyncCounter(mbox);
        for (int msgno = sizes.length; msgno > 0; --msgno) {
            LOG.debug("Fetching message number %d", msgno);
            IOExceptionHandler.getInstance().trackSyncItem(mbox, msgno);
            try {
                fetchAndAddMessage(msgno, sizes[msgno - 1], null, true);
            } catch (Exception e) {
                if (IOExceptionHandler.getInstance().isRecoverable(mbox, msgno, "pop sync fail", e)) {
                    //don't delete msg; if we end up aborting we want it still to exist
                    //skip it; will be retried on every subsequent sync
                    continue;
                }
                throw e;
            }
            checkIsEnabled();
            connection.deleteMessage(msgno);
        }
        IOExceptionHandler.getInstance().checkpointIOExceptionRate(mbox);
    }

    private void fetchAndRetainMessages()
        throws Exception {
        String[] uids = connection.getMessageUids();
        Set<String> existingUids = ActiveSyncMessage.getMatchingUids(dataSource, uids);
        int count = uids.length - existingUids.size();

        LOG.info("Found %d new message(s) on remote server", count);
        if (count == 0) {
            return; // No new messages
        }
        IOExceptionHandler.getInstance().resetSyncCounter(mbox);
        boolean checkForSelfPopping = true;
        for (int msgno = uids.length; msgno > 0; --msgno) {
            String uid = uids[msgno - 1];

            if (!existingUids.contains(uid)) {
                if (checkForSelfPopping) {
                    //  Only check new messages else could match one we previously synced.
                    if (poppingSelf(uid)) {
                        throw ServiceException.INVALID_REQUEST(
                            "User attempted to import messages from his own mailbox", null);
                    }
                    checkForSelfPopping = false; // Only need to check one message
                }
                LOG.debug("Fetching message with uid %s", uid);
                IOExceptionHandler.getInstance().trackSyncItem(mbox, msgno);
                // Don't allow filtering to a mountpoint when retaining the
                // message.  We don't have a local id, so we can't keep track
                // of it in the data_source_item table.
                try {
                    fetchAndAddMessage(msgno, connection.getMessageSize(msgno), uid, false);
                } catch (Exception e) {
                    if (IOExceptionHandler.getInstance().isRecoverable(mbox, msgno, "pop sync fail", e)) {
                        //skip it; will be retried on every subsequent sync
                        continue;
                    }
                    throw e;
                }
            }
        }
        IOExceptionHandler.getInstance().checkpointIOExceptionRate(mbox);
    }

    private void fetchAndAddMessage(int msgno, int size, String uid, boolean allowFilterToMountpoint)
        throws ServiceException, IOException {
        ContentInputStream cis = null;
        MessageContent mc = null;
        checkIsEnabled();
        try {
            cis = connection.getMessage(msgno);
            mc = MessageContent.read(cis, size);
            ParsedMessage pm = mc.getParsedMessage(null, indexAttachments);
            if (pm == null) {
                LOG.warn("Empty message body for UID %d. Must be ignored.", uid);
                return;
            }
            Message msg = null;
        // bug 47796: Set received date to sent date if available otherwise use current time
            try {
                Date sentDate = pm.getMimeMessage().getSentDate();
                if (sentDate == null) {
                    LOG.warn("null sent date; probably due to parse error. Date header value: [%s]",pm.getMimeMessage().getHeader("Date", null));
                }
                pm.setReceivedDate(sentDate != null ? sentDate.getTime() : System.currentTimeMillis());
            } catch (MessagingException e) {
                LOG.warn("unable to get sent date from parsed message due to exception, must use current time", e);
                pm.setReceivedDate(System.currentTimeMillis());
            }
            DeliveryContext dc = mc.getDeliveryContext();
            if (isOffline()) {
                msg = addMessage(null, pm, size, dataSource.getFolderId(), Flag.BITMASK_UNREAD, dc);
            } else {
                Integer localId = getFirstLocalId(
                    RuleManager.applyRulesToIncomingMessage(
                        null, mbox, pm, size, dataSource.getEmailAddress(), dc, dataSource.getFolderId(), true,
                        allowFilterToMountpoint));
                if (localId != null) {
                    msg = mbox.getMessageById(null, localId);
                }
            }
            if (msg != null && uid != null) {
                ActiveSyncMessage msgTracker = new ActiveSyncMessage(dataSource, msg.getId(), uid);
                msgTracker.add();
            }
        } catch (CommandFailedException e) {
            LOG.warn("Error fetching message number %d: %s", msgno, e.getMessage());
        } finally {
            if (cis != null) {
                try {
                    cis.close();
                } catch (ParseException pe) {
                    LOG.error("ParseException while closing ContentInputStream. Assuming cis is effectively closed", pe);
                }
            }
            if (mc != null) {
                mc.cleanup();
            }
        }
    }

    private boolean poppingSelf(String uid)
        throws ServiceException {
        Matcher matcher = PATTERN_ZIMBRA_UID.matcher(uid);
        if (!matcher.matches()) {
            return false; // Not a Zimbra UID
        }
        // See if this UID comes from the specified mailbox. Popping from
        // another Zimbra mailbox is ok.
        int itemId;
        try {
            itemId = Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            return false;
        }
        String digest = matcher.group(2);
        Message msg;
        try {
            msg = mbox.getMessageById(null, itemId);
        } catch (MailServiceException.NoSuchItemException e) {
            return false;
        }
        return digest.equals(msg.getDigest());
    }
    */

}
