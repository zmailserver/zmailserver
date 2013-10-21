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
package org.zmail.cs.service.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.ServerBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.rmgmt.RemoteCommands;
import org.zmail.cs.rmgmt.RemoteManager;
import org.zmail.cs.rmgmt.RemoteResult;
import org.zmail.cs.rmgmt.RemoteResultParser;
import org.zmail.soap.ZmailSoapContext;

public class GetMailQueueInfo extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();
	    
	    Element serverElem = request.getElement(AdminConstants.E_SERVER);
	    String serverName = serverElem.getAttribute(AdminConstants.A_NAME);
	    
	    Server server = prov.get(Key.ServerBy.name, serverName);
	    if (server == null) {
	    	throw ServiceException.INVALID_REQUEST("server with name " + serverName + " could not be found", null);
	    }
	    
	    checkRight(zsc, context, server, Admin.R_manageMailQueue);
	    
        RemoteManager rmgr = RemoteManager.getRemoteManager(server);
        RemoteResult rr = rmgr.execute(RemoteCommands.ZMQSTAT_ALL);
        Map<String,String> queueInfo;
        try { 
            queueInfo = RemoteResultParser.parseSingleMap(rr);
        } catch (IOException ioe) {
            throw ServiceException.FAILURE("exception occurred handling command", ioe);
        }
        if (queueInfo == null) {
            throw ServiceException.FAILURE("server " + serverName + " returned no result", null);
        }

        Element response = zsc.createElement(AdminConstants.GET_MAIL_QUEUE_INFO_RESPONSE);
        serverElem = response.addElement(AdminConstants.E_SERVER);
        serverElem.addAttribute(AdminConstants.A_NAME, serverName);
        for (String k : queueInfo.keySet()) {
            Element queue = serverElem.addElement(AdminConstants.E_QUEUE);
            queue.addAttribute(AdminConstants.A_NAME, k);
            queue.addAttribute(AdminConstants.A_N, queueInfo.get(k));
        }
        return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageMailQueue);
    }
}
