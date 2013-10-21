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
package org.zmail.cs.mailbox;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MailDateFormat;

import org.zmail.common.util.Constants;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.EmailUtil;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.lmtpserver.LmtpCallback;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.util.AccountUtil;

/**
 * LMTP callback that sends the user a warning if he is getting
 * close to exceeding his mailbox quota.
 * 
 * @author bburtin
 */
public class QuotaWarning implements LmtpCallback {

    private static final QuotaWarning sInstance = new QuotaWarning();
    
    private QuotaWarning() {
    }
    
    public static QuotaWarning getInstance() {
        return sInstance;
    }
    
    @Override
    public void afterDelivery(Account account, Mailbox mbox, String envelopeSender,
                              String recipientEmail, Message newMessage){
        try {
            int warnPercent = account.getIntAttr(Provisioning.A_zmailQuotaWarnPercent, 90);
            long quota = AccountUtil.getEffectiveQuota(account);
            long warnInterval = account.getTimeInterval(Provisioning.A_zmailQuotaWarnInterval, Constants.MILLIS_PER_DAY);
            String template = account.getAttr(Provisioning.A_zmailQuotaWarnMessage, null);
            
            Date lastWarnTime = account.getGeneralizedTimeAttr(Provisioning.A_zmailQuotaLastWarnTime, null);
            Date now = new Date();
            
            ZmailLog.lmtp.debug("Checking quota warning: mbox size=%d, quota=%d, lastWarnTime=%s, warnInterval=%d, warnPercent=%d",
                mbox.getSize(), quota, lastWarnTime, warnInterval, warnPercent);
            
            // Bail out if there's no quota, we haven't hit the warning threshold, or a warning
            // has already been sent.
            if (quota == 0 || warnPercent == 0) {
                return;
            }
            if (mbox.getSize() * 100 / quota < warnPercent) {
                return;
            }
            if (lastWarnTime != null && now.getTime() - lastWarnTime.getTime() < warnInterval) {
                return;
            }
            if (template == null) {
                ZmailLog.lmtp.warn("%s not specified.  Unable to send quota warning message.",
                    Provisioning.A_zmailQuotaWarnMessage);
            }

            // Send the quota warning
            ZmailLog.lmtp.info("Sending quota warning: mbox size=%d, quota=%d, lastWarnTime=%s, warnInterval=%dms, warnPercent=%d",
                mbox.getSize(), quota, lastWarnTime, warnInterval, warnPercent);
            String address = account.getAttr(Provisioning.A_zmailMailDeliveryAddress);
            String domain = EmailUtil.getLocalPartAndDomain(address)[1];

            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("RECIPIENT_NAME", account.getAttr(Provisioning.A_displayName));
            vars.put("RECIPIENT_DOMAIN", domain);
            vars.put("RECIPIENT_ADDRESS", address);
            vars.put("DATE", new MailDateFormat().format(new Date()));
            vars.put("MBOX_SIZE_MB", String.format("%.2f", mbox.getSize() / 1024.0 / 1024.0));
            vars.put("QUOTA_MB", String.format("%.2f", quota / 1024.0 / 1024.0));
            vars.put("WARN_PERCENT", warnPercent);
            vars.put("NEWLINE", "\r\n");

            String msgBody = StringUtil.fillTemplate(template, vars);
            ParsedMessage pm = new ParsedMessage(msgBody.getBytes(), now.getTime(), false);
            DeliveryOptions dopt = new DeliveryOptions().setFolderId(Mailbox.ID_FOLDER_INBOX).setFlags(Flag.BITMASK_UNREAD | Flag.BITMASK_HIGH_PRIORITY);
            mbox.addMessage(null, pm, dopt, null);
            
            // Update last sent date
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put(Provisioning.A_zmailQuotaLastWarnTime, DateUtil.toGeneralizedTime(now));
            Provisioning.getInstance().modifyAttrs(account, attrs);
        } catch (Exception e) {
            ZmailLog.lmtp.warn("Unable to send quota warning message", e);
        }
    }

    @Override
    public void forwardWithoutDelivery(Account account, Mailbox mbox, String envelopeSender, String recipientEmail, ParsedMessage pm) {
        // no delivery, so no need to check anything
    }
}
