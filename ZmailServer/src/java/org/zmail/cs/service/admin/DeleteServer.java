/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.List;
import java.util.Map;

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.ServerBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class DeleteServer extends AdminDocumentHandler {

    private static final String[] TARGET_SERVER_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedServerPath()  { return TARGET_SERVER_PATH; }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String id = request.getAttribute(AdminConstants.E_ID);
	    
	    Server server = prov.get(Key.ServerBy.id, id);
        if (server == null)
            throw AccountServiceException.NO_SUCH_SERVER(id);
        
        checkRight(zsc, context, server, Admin.R_deleteServer);
        
        prov.deleteServer(server.getId());
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "DeleteServer","name", server.getName(), "id", server.getId()}));

	    Element response = zsc.createElement(AdminConstants.DELETE_SERVER_RESPONSE);
	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteServer);
    }
}