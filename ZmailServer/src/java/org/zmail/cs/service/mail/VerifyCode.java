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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.soap.ZmailSoapContext;

import java.util.Map;

/**
 * Handler for verifying the device code.
 */
public class VerifyCode extends MailDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        String emailAddr = request.getAttribute(MailConstants.A_ADDRESS);
        boolean success = false;
        if (SendVerificationCode.emailToCodeMap.containsKey(emailAddr)) {
            String code = request.getAttribute(MailConstants.A_VERIFICATION_CODE);
            if (SendVerificationCode.emailToCodeMap.get(emailAddr).equals(code)) {
                Account account = getRequestedAccount(zsc);
                account.setCalendarReminderDeviceEmail(emailAddr);
                success = true;
                SendVerificationCode.emailToCodeMap.remove(emailAddr);
            } else {
                ZmailLog.misc.debug("Invalid verification code");
            }
        } else {
            ZmailLog.misc.debug("Verification code for %s has either not been generated or has expired", emailAddr);
        }
        return zsc.createElement(MailConstants.VERIFY_CODE_RESPONSE).addAttribute(MailConstants.A_VERIFICATION_SUCCESS, success ? "1" : "0");
    }
}
