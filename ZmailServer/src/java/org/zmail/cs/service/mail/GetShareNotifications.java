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
package org.zmail.cs.service.mail;

import com.google.common.io.Closeables;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.share.ShareNotification;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.index.MessageHit;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.index.ZmailHit;
import org.zmail.cs.index.ZmailQueryResults;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailItem.Type;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.MPartInfo;
import org.zmail.cs.mime.Mime;
import org.zmail.soap.ZmailSoapContext;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetShareNotifications extends MailDocumentHandler {

    private static final Log sLog = LogFactory.getLog(GetShareNotifications.class);
    private final String query = "type:" + MimeConstants.CT_XML_ZIMBRA_SHARE + " AND in:inbox";
    private final Set<MailItem.Type> SEARCH_TYPES = EnumSet.of(MailItem.Type.MESSAGE);

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        Element response = zsc.createElement(MailConstants.GET_SHARE_NOTIFICATIONS_RESPONSE);
        HashSet<String> shares = new HashSet<String>();

        ZmailQueryResults zqr = null;
        try {
            zqr = mbox.index.search(octxt, query, SEARCH_TYPES, SortBy.DATE_DESC, 10);
            while (zqr.hasNext()) {
                ZmailHit hit = zqr.getNext();
                if (hit instanceof MessageHit) {
                    Message message = ((MessageHit)hit).getMessage();
                    try {
                        for (MPartInfo part : Mime.getParts(message.getMimeMessage())) {
                            String ctype = StringUtil.stripControlCharacters(part.getContentType());
                            if (MimeConstants.CT_XML_ZIMBRA_SHARE.equals(ctype)) {
                                ShareNotification sn = ShareNotification.fromMimePart(part.getMimePart());
                                String shareItemId = sn.getGrantorId() + ":" + sn.getItemId();
                                if (shares.contains(shareItemId)) {
                                    // this notification is stale as there is
                                    // a new one for the same share.  delete
                                    // this notification and skip to the next one.
                                    sLog.info("deleting stale notification %s", message.getId());
                                    mbox.delete(octxt, message.getId(), Type.MESSAGE);
                                    continue;
                                }
                                shares.add(shareItemId);

                                Element share = response.addElement(sn.isRevoke() || sn.isExpire() ? MailConstants.E_REVOKE : MailConstants.E_SHARE);
                                if (sn.isExpire()) {
                                    share.addAttribute(MailConstants.A_EXPIRE, true);
                                }
                                Element g = share.addUniqueElement(MailConstants.E_GRANTOR);
                                g.addAttribute(MailConstants.A_ID, sn.getGrantorId());
                                g.addAttribute(MailConstants.A_EMAIL, sn.getGrantorEmail());
                                g.addAttribute(MailConstants.A_NAME, sn.getGrantorName());
                                Element l = share.addUniqueElement(MailConstants.E_MOUNT);
                                l.addAttribute(MailConstants.A_ID, sn.getItemId());
                                l.addAttribute(MailConstants.A_NAME, sn.getItemName());
                                l.addAttribute(MailConstants.A_DEFAULT_VIEW, sn.getView());
                                l.addAttribute(MailConstants.A_RIGHTS, sn.getPermissions());
                                String status = (message.isUnread() ? "new" : "seen");
                                share.addAttribute(MailConstants.A_STATUS, status);
                                share.addAttribute(MailConstants.A_ID, "" + message.getId());
                                share.addAttribute(MailConstants.A_DATE, message.getDate());
                                if (sn.isRevoke() || sn.isExpire()) {
                                    // purge revoke/expire notification upon receipt
                                    mbox.delete(octxt, message.getId(), Type.MESSAGE);
                                }
                            }
                        }
                    } catch (IOException e) {
                        ZmailLog.misc.warn("can't parse share notification", e);
                    } catch (MessagingException e) {
                        ZmailLog.misc.warn("can't parse share notification", e);
                    }
                }
            }
        } finally {
            Closeables.closeQuietly(zqr);
        }

        return response;
    }
}
