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
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.soap.ZmailSoapContext;

public class CreateDistributionList extends AdminDocumentHandler {

    /**
     * must be careful and only allow access to domain if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) 
    throws ServiceException {
	    
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
	    
        String name = request.getAttribute(AdminConstants.E_NAME).toLowerCase();
        Map<String, Object> attrs = AdminService.getAttrs(request, true);
        
        boolean dynamic = request.getAttributeBool(AdminConstants.A_DYNAMIC, false);

        if (dynamic) {
            checkDomainRightByEmail(zsc, name, Admin.R_createGroup);
            checkSetAttrsOnCreate(zsc, TargetType.group, name, attrs);
        } else {
            checkDomainRightByEmail(zsc, name, Admin.R_createDistributionList);
            checkSetAttrsOnCreate(zsc, TargetType.dl, name, attrs);
        }
        
        Group group = prov.createGroup(name, attrs, dynamic);
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                 new String[] {"cmd", "CreateDistributionList","name", name}, attrs));         

        Element response = zsc.createElement(AdminConstants.CREATE_DISTRIBUTION_LIST_RESPONSE);
        
        GetDistributionList.encodeDistributionList(response, group);

        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_createDistributionList);
        relatedRights.add(Admin.R_createGroup);
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY, 
                Admin.R_modifyDistributionList.getName(), "distribution list"));
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY, 
                Admin.R_modifyGroup.getName(), "group"));
    }
}
