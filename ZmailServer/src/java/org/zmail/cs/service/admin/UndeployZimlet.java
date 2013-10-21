/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.zimlet.ZimletUtil;
import org.zmail.soap.ZmailSoapContext;

public class UndeployZimlet extends AdminDocumentHandler {

	private static class UndeployThread implements Runnable {
		String name;
		ZAuthToken auth;
		public UndeployThread(String na, ZAuthToken au) {
			name = na;
			auth = au;
		}
		public void run() {
			try {
				ZimletUtil.uninstallFromOtherServers(name, auth);
			} catch (Exception e) {
				ZmailLog.zimlet.info("undeploy", e);
			}
		}
	}
	
	@Override
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
	    
	    ZmailSoapContext zsc = getZmailSoapContext(context);
		
		for (Server server : Provisioning.getInstance().getAllServers())
            checkRight(zsc, context, server, Admin.R_deployZimlet);
		
	    String name = request.getAttribute(AdminConstants.A_NAME);
		String action = request.getAttribute(AdminConstants.A_ACTION, null);
		ZAuthToken auth = null;
		
		if (action == null)
			auth = zsc.getRawAuthToken();
	    Element response = zsc.createElement(AdminConstants.UNDEPLOY_ZIMLET_RESPONSE);
	    ZimletUtil.uninstallZimlet(name);
	    new Thread(new UndeployThread(name, auth)).start();
		return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deployZimlet);
        notes.add("Need the " + Admin.R_deployZimlet.getName() + " right on all servers.");
    }

}
