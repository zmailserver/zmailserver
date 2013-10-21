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

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.DynamicGroup;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class ModifyDistributionList extends DistributionListDocumentHandler {

    /**
     * must be careful and only allow access to domain if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    @Override
    protected Group getGroup(Element request) throws ServiceException {
        String id = request.getAttribute(AdminConstants.E_ID);
        return Provisioning.getInstance().getGroup(Key.DistributionListBy.id, id);
    }
    
    public Element handle(Element request, Map<String, Object> context) 
    throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        Map<String, Object> attrs = AdminService.getAttrs(request);
	    
        Group group = getGroupFromContext(context);
        if (group == null) {
            String id = request.getAttribute(AdminConstants.E_ID);
            throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(id);
        }
        
        if (group.isDynamic()) {
            checkDynamicGroupRight(zsc, (DynamicGroup) group, attrs);
        } else {
            checkDistributionListRight(zsc, (DistributionList) group, attrs);
        }

        // pass in true to checkImmutable
        prov.modifyAttrs(group, attrs, true);
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                  new String[] {"cmd", "ModifyDistributionList","name", group.getName()}, attrs));	    

        Element response = zsc.createElement(AdminConstants.MODIFY_DISTRIBUTION_LIST_RESPONSE);
        GetDistributionList.encodeDistributionList(response, group);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY, 
                Admin.R_modifyDistributionList.getName(), "distribution list"));
    }
}
