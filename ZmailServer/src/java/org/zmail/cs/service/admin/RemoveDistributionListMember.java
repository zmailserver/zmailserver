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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.DynamicGroup;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.account.Key.DistributionListBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class RemoveDistributionListMember extends DistributionListDocumentHandler {

    /**
     * must be careful and only allow access to domain if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }
    
    @Override
    protected Group getGroup(Element request) throws ServiceException {
        String id = request.getAttribute(AdminConstants.E_ID);
        return Provisioning.getInstance().getGroup(DistributionListBy.id, id);
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        List<String> memberList = new LinkedList<String>();
        for (Element elem : request.listElements(AdminConstants.E_DLM)) {
        	memberList.add(elem.getTextTrim());
        }

        Group group = getGroupFromContext(context);
        if (group == null) {
            String id = request.getAttribute(AdminConstants.E_ID);
            throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(id);
        }
        
        if (group.isDynamic()) {
            checkDynamicGroupRight(zsc, (DynamicGroup) group, Admin.R_removeGroupMember);
        } else {
            checkDistributionListRight(zsc, (DistributionList) group, Admin.R_removeDistributionListMember);
        }
        
        String[] members = (String[]) memberList.toArray(new String[0]); 
        prov.removeGroupMembers(group, members);
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "RemoveDistributionListMember", "name", group.getName(), 
                "member", Arrays.deepToString(members)})); 

        Element response = zsc.createElement(AdminConstants.REMOVE_DISTRIBUTION_LIST_MEMBER_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_removeDistributionListMember);
        relatedRights.add(Admin.R_removeGroupMember);
    }
}
