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

/*
 * Created on Jun 17, 2004
 */
package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.DistributionList;
import com.zimbra.cs.account.Group;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.DistributionListBy;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.soap.ZimbraSoapContext;

public class RenameDistributionList extends AdminDocumentHandler {

    /**
     * must be careful and only allow access to domain if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZimbraSoapContext zsc = getZimbraSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String id = request.getAttribute(AdminConstants.E_ID);
        String newName = request.getAttribute(AdminConstants.E_NEW_NAME);

	    Group group = prov.getGroup(Key.DistributionListBy.id, id);
        if (group == null) {
            throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(id);
        }
        
        // check if the admin can rename the DL
        if (group.isDynamic()) {
            // TODO: fixme
        } else {
            checkDistributionListRight(zsc, (DistributionList) group, Admin.R_renameDistributionList);
        }
        
        // check if the admin can "create DL" in the domain (can be same or diff)
        checkDomainRightByEmail(zsc, newName, Admin.R_createDistributionList);
        
        String oldName = group.getName();

        prov.renameGroup(id, newName);

        ZimbraLog.security.info(ZimbraLog.encodeAttrs(
                new String[] {"cmd", "RenameDistributionList", "name", oldName, "newName", newName})); 
        
        // get again with new name...

        group = prov.getGroup(Key.DistributionListBy.id, id);
        if (group == null)
            throw ServiceException.FAILURE("unable to get distribution list after rename: " + id, null);
	    Element response = zsc.createElement(AdminConstants.RENAME_DISTRIBUTION_LIST_RESPONSE);
	    GetDistributionList.encodeDistributionList(response, group);
	    return response;

    }
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_renameDistributionList);
        relatedRights.add(Admin.R_createDistributionList);
    }
}
