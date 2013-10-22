/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
import com.zimbra.cs.account.offline.OfflineProvisioning;
import com.zimbra.cs.service.mail.CreateAppointmentException;


public class OfflineCreateAppointmentException extends CreateAppointmentException {
    @Override
    protected String getProxyAuthToken(String requestedAccountId, Map<String, Object> context) throws ServiceException {
        OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
        return prov.getCalendarProxyAuthToken(requestedAccountId, context);
    }
}
