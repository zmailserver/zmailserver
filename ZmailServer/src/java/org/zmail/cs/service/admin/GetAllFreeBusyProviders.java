/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.fb.ExchangeEWSFreeBusyProvider;
import org.zmail.cs.fb.FreeBusyProvider;
import org.zmail.soap.ZmailSoapContext;

public class GetAllFreeBusyProviders extends AdminDocumentHandler {
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        // allow only system admin for now
        checkRight(zsc, context, null, Admin.R_getAllFreeBusyProviders);
        
        Element response = zsc.createElement(AdminConstants.GET_ALL_FREE_BUSY_PROVIDERS_RESPONSE);
        
        for (FreeBusyProvider prov : FreeBusyProvider.getProviders()) {
            if (!(prov instanceof ExchangeEWSFreeBusyProvider )) {
                Element provElem = response.addElement(AdminConstants.E_PROVIDER);
                provElem.addAttribute(AdminConstants.A_NAME, prov.getName());
                provElem.addAttribute(AdminConstants.A_PROPAGATE, prov.registerForMailboxChanges());
                provElem.addAttribute(AdminConstants.A_START, prov.cachedFreeBusyStartTime());
                provElem.addAttribute(AdminConstants.A_END, prov.cachedFreeBusyEndTime());
                provElem.addAttribute(AdminConstants.A_QUEUE, prov.getQueueFilename());
                provElem.addAttribute(AdminConstants.A_PREFIX, prov.foreignPrincipalPrefix());
            }
        }
	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getAllFreeBusyProviders);
    }
}
