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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

public class GetAllDistributionLists extends AdminDocumentHandler {

    public static final String BY_NAME = "name";
    public static final String BY_ID = "id";
    
    /**
     * must be careful and only allow on dls domain admin has access to
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
	    
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        Element response = null;
        
        Element d = request.getOptionalElement(AdminConstants.E_DOMAIN);
        Domain domain = null;
        
        if (d != null) {
            String key = d.getAttribute(AdminConstants.A_BY);
            String value = d.getText();
        
            if (key.equals(BY_NAME)) {
                domain = prov.get(Key.DomainBy.name, value);
            } else if (key.equals(BY_ID)) {
                domain = prov.get(Key.DomainBy.id, value);
            } else {
                throw ServiceException.INVALID_REQUEST("unknown value for by: "+key, null);
            }
            if (domain == null)
                throw AccountServiceException.NO_SUCH_DOMAIN(value);            
        }
        
        if (isDomainAdminOnly(zsc)) {
            if (domain != null) { 
                checkDomainRight(zsc, domain, AdminRight.PR_ALWAYS_ALLOW); 
            }
            domain = getAuthTokenAccountDomain(zsc);
        }

        AdminAccessControl aac = AdminAccessControl.getAdminAccessControl(zsc);
        
        if (domain != null) {
            response = zsc.createElement(AdminConstants.GET_ALL_DISTRIBUTION_LISTS_RESPONSE);
            doDomain(zsc, response, domain, aac);
        } else {
            response = zsc.createElement(AdminConstants.GET_ALL_DISTRIBUTION_LISTS_RESPONSE);
            List domains = prov.getAllDomains();
            for (Iterator dit=domains.iterator(); dit.hasNext(); ) {
                Domain dm = (Domain) dit.next();
                doDomain(zsc, response, dm, aac);                
            }
        }
        return response;        
    }
    
    private void doDomain(ZmailSoapContext zsc, Element e, Domain d, AdminAccessControl aac) throws ServiceException {
        List dls = Provisioning.getInstance().getAllGroups(d);
        for (Iterator it = dls.iterator(); it.hasNext(); ) {
            Group dl = (Group) it.next();
            boolean hasRightToList = true;
            if (dl.isDynamic()) {
                // TODO: fix me
                hasRightToList = true;
            } else {
                hasRightToList = aac.hasRightsToList(dl, Admin.R_listDistributionList, null);
            }
            
            if (hasRightToList) {
                GetDistributionList.encodeDistributionList(e, dl, true, false, null, aac.getAttrRightChecker(dl));
            }
        }        
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_listDistributionList);
        relatedRights.add(Admin.R_getDistributionList);
        
        notes.add(AdminRightCheckPoint.Notes.LIST_ENTRY);
    }
}
