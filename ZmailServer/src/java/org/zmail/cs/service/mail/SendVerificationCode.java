/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.service.mail;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Hex;

import org.zmail.common.mime.MimeConstants;
import org.zmail.common.mime.shim.JavaMailInternetAddress;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.L10nUtil;
import org.zmail.common.util.LruMap;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.MailSender;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.util.JMSession;
import org.zmail.soap.ZmailSoapContext;

/**
 * Handler for sending a verification code to a device.
 */
public class SendVerificationCode extends MailDocumentHandler {

    static Map<String, String> emailToCodeMap = Collections.synchronizedMap(new LruMap<String, String>(1000));

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        // email address corresponding to device
        String emailAddr = request.getAttribute(MailConstants.A_ADDRESS);
        String code = generateVerificationCode();
        try {
            sendVerificationCode(emailAddr, code, getRequestedMailbox(zsc));
        } catch (MessagingException e) {
            throw ServiceException.FAILURE("Error in sending verification code to device", e);
        }
        emailToCodeMap.put(emailAddr, code);
        return zsc.createElement(MailConstants.SEND_VERIFICATION_CODE_RESPONSE);
    }

    static void sendVerificationCode(String emailAddr, String code, Mailbox mbox) throws MessagingException, ServiceException {
        MimeMessage mm = new Mime.FixedMimeMessage(JMSession.getSmtpSession(mbox.getAccount()));
        mm.setRecipient(javax.mail.Message.RecipientType.TO, new JavaMailInternetAddress(emailAddr));
        mm.setText(L10nUtil.getMessage(L10nUtil.MsgKey.deviceSendVerificationCodeText,
                                       mbox.getAccount().getLocale(), code),
                   MimeConstants.P_CHARSET_UTF8);
        mm.saveChanges();
        MailSender mailSender = mbox.getMailSender();
        mailSender.setSaveToSent(false);
        mailSender.sendMimeMessage(null, mbox, mm);
    }

    static String generateVerificationCode() {
        ZmailLog.misc.debug("Generating verification code");
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[3];
        random.nextBytes(bytes);
        return new String(Hex.encodeHex(bytes));
    }
}
