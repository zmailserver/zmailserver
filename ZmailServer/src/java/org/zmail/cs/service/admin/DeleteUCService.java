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

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.UCService;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.account.Key.UCServiceBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author pshao
 */
public class DeleteUCService extends AdminDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getElement(AdminConstants.E_ID).getText();

        UCService ucService = prov.get(UCServiceBy.id, id);
        if (ucService == null) {
            throw AccountServiceException.NO_SUCH_UC_SERVICE(id);
        }
        
        checkRight(zsc, context, ucService, Admin.R_deleteUCService);
        
        prov.deleteUCService(ucService.getId());
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "DeleteUCService","name", ucService.getName(), "id", ucService.getId()}));

        Element response = zsc.createElement(AdminConstants.DELETE_UC_SERVICE_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteUCService);
    }
}