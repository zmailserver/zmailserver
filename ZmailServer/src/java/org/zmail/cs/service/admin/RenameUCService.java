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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.UCServiceBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.UCService;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

public class RenameUCService extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext lc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getElement(AdminConstants.E_ID).getText();
        String newName = request.getElement(AdminConstants.E_NEW_NAME).getText();

        UCService ucService = prov.get(UCServiceBy.id, id);
        if (ucService == null) {
            throw AccountServiceException.NO_SUCH_UC_SERVICE(id);
        }

        // check if the admin can rename the uc service
        checkRight(lc, context, ucService, Admin.R_renameUCService);

        String oldName = ucService.getName();

        prov.renameUCService(id, newName);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] { "cmd", "RenameUCService", "name", oldName, "newName", newName }));

        // get again with new name...

        ucService = prov.get(Key.UCServiceBy.id, id);
        if (ucService == null) {
            throw ServiceException.FAILURE("unabled to get renamed uc service: " + id, null);
        }
        Element response = lc.createElement(AdminConstants.RENAME_UC_SERVICE_RESPONSE);
        GetUCService.encodeUCService(response, ucService, null, null);
        return response;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_renameUCService);
    }

}
