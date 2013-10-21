/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.DistributionListBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class GetDistributionListMembership extends AdminDocumentHandler {

    /**
     * must be careful and only return accounts a domain admin can see
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext lc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        int limit = (int) request.getAttributeLong(AdminConstants.A_LIMIT, 0);
        if (limit < 0) {
            throw ServiceException.INVALID_REQUEST("limit" + limit + " is negative", null);
        }
        int offset = (int) request.getAttributeLong(AdminConstants.A_OFFSET, 0);
        if (offset < 0) {
            throw ServiceException.INVALID_REQUEST("offset" + offset + " is negative", null);
        }
//        boolean sortAscending = request.getAttributeBool(AdminService.A_SORT_ASCENDING, true);        

        Element d = request.getElement(AdminConstants.E_DL);
        String key = d.getAttribute(AdminConstants.A_BY);
        String value = d.getText();
        
        DistributionList distributionList = prov.get(Key.DistributionListBy.fromString(key), value);        
        
        if (distributionList == null)
            throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(value);
        
        checkDistributionListRight(lc, distributionList, Admin.R_getDistributionListMembership);

        HashMap<String,String> via = new HashMap<String, String>();
        List<DistributionList> lists = prov.getDistributionLists(distributionList, false, via);
        
        Element response = lc.createElement(AdminConstants.GET_DISTRIBUTION_LIST_MEMBERSHIP_RESPONSE);
        for (DistributionList dl: lists) {
            Element dlEl = response.addElement(AdminConstants.E_DL);
            dlEl.addAttribute(AdminConstants.A_NAME, dl.getName());
            dlEl.addAttribute(AdminConstants.A_ID,dl.getId());
            String viaDl = via.get(dl.getName());
            if (viaDl != null) dlEl.addAttribute(AdminConstants.A_VIA, viaDl);
        }

        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getDistributionListMembership);
    }
}
