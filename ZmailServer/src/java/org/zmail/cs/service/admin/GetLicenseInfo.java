/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.GetLicenseInfoResponse;
import org.zmail.soap.admin.type.LicenseExpirationInfo;

import org.zmail.common.localconfig.LC;
import org.zmail.cs.account.accesscontrol.AdminRight;

public class GetLicenseInfo extends AdminDocumentHandler {

    static final String TRIAL_EXPIRATION_DATE_KEY = "trial_expiration_date";

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext lc = getZmailSoapContext(context);

        String expirationDate = LC.get(TRIAL_EXPIRATION_DATE_KEY);
        LicenseExpirationInfo expirationInfo =
            new LicenseExpirationInfo(expirationDate);
        GetLicenseInfoResponse resp =
            new GetLicenseInfoResponse(expirationInfo);
        return lc.jaxbToElement(resp);
    }

    public boolean needsAdminAuth(Map<String, Object> context) {
        return false;
    }

    public boolean needsAuth(Map<String, Object> context) {
        return false;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }
}
