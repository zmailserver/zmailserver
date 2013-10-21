/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
import org.zmail.cs.fb.FreeBusyProvider;
import org.zmail.cs.fb.FreeBusyProvider.FreeBusySyncQueue;
import org.zmail.soap.ZmailSoapContext;

public class GetFreeBusyQueueInfo extends AdminDocumentHandler {
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        // allow only system admin for now
        checkRight(zsc, context, null, AdminRight.PR_SYSTEM_ADMIN_ONLY);
        
        String name = null;
        Element provider = request.getOptionalElement(AdminConstants.E_PROVIDER);
        if (provider != null)
        	name = provider.getAttribute(AdminConstants.A_NAME);
        
        Element response = zsc.createElement(AdminConstants.GET_FREE_BUSY_QUEUE_INFO_RESPONSE);
        if (name != null) {
        	FreeBusyProvider prov = FreeBusyProvider.getProvider(name);
        	if (prov == null)
        		throw ServiceException.INVALID_REQUEST("provider not found: "+name, null);
        	handleProvider(response, prov);
        } else {
        	for (FreeBusyProvider prov : FreeBusyProvider.getProviders()) {
        		handleProvider(response, prov);
        	}
        }
	    return response;
	}
	private void handleProvider(Element response, FreeBusyProvider prov) {
        Element provider = response.addElement(AdminConstants.E_PROVIDER);
        provider.addAttribute(AdminConstants.A_NAME, prov.getName());
        FreeBusySyncQueue queue = prov.getSyncQueue();
        if (queue == null)
        	return;
        synchronized (queue) {
        	for (String id : queue)
        		provider.addElement(AdminConstants.E_ACCOUNT).addAttribute(AdminConstants.A_ID, id);
        }
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
    }
}
