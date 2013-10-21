/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.soap.ZmailSoapContext;

import java.util.Map;

/**
 * Unsets the zmailCalendarReminderDeviceEmail account attr
 */
public class InvalidateReminderDevice extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        String emailAddr = request.getAttribute(MailConstants.A_ADDRESS);
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);
        String configuredAddr = account.getCalendarReminderDeviceEmail();
        if (emailAddr.equals(configuredAddr))
            account.unsetCalendarReminderDeviceEmail();
        else
            throw ServiceException.INVALID_REQUEST("Email address'" +
                    emailAddr + "' is not same as the " +
                    Provisioning.A_zmailCalendarReminderDeviceEmail +
                    " attr value '" + configuredAddr + "'", null);
        return zsc.createElement(
                MailConstants.INVALIDATE_REMINDER_DEVICE_RESPONSE);
    }
}
