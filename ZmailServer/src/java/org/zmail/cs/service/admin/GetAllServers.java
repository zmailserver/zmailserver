/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class GetAllServers extends AdminDocumentHandler {

    public static final String BY_NAME = "name";
    public static final String BY_ID = "id";
    
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String service = request.getAttribute(AdminConstants.A_SERVICE, null);
        boolean applyConfig = request.getAttributeBool(AdminConstants.A_APPLY_CONFIG, true);
        List servers = prov.getAllServers(service);
        
        AdminAccessControl aac = AdminAccessControl.getAdminAccessControl(zsc);
        
        Element response = zsc.createElement(AdminConstants.GET_ALL_SERVERS_RESPONSE);
        for (Iterator it = servers.iterator(); it.hasNext(); ) {
            Server server = (Server) it.next();
            if (aac.hasRightsToList(server, Admin.R_listServer, null))
                GetServer.encodeServer(response, server, applyConfig, null, aac.getAttrRightChecker(server));
        }

	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_listServer);
        relatedRights.add(Admin.R_getServer);
        
        notes.add(AdminRightCheckPoint.Notes.LIST_ENTRY);
    }
}
