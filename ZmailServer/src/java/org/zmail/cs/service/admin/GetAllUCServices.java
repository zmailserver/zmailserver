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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.UCService;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author pshao
 */
public class GetAllUCServices extends AdminDocumentHandler {

    public static final String BY_NAME = "name";
    public static final String BY_ID = "id";
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        List<UCService> ucServices = prov.getAllUCServices();
        
        AdminAccessControl aac = AdminAccessControl.getAdminAccessControl(zsc);
        
        Element response = zsc.createElement(AdminConstants.GET_ALL_UC_SERVICES_RESPONSE);
        for (UCService ucSservice : ucServices) {
            if (aac.hasRightsToList(ucSservice, Admin.R_listUCService, null)) {
                GetUCService.encodeUCService(response, ucSservice, null, aac.getAttrRightChecker(ucSservice));
            }
        }

        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_listUCService);
        relatedRights.add(Admin.R_getUCService);
        
        notes.add(AdminRightCheckPoint.Notes.LIST_ENTRY);
    }
}
