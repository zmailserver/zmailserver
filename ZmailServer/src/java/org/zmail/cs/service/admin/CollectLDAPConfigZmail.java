/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.ServerBy;
import org.zmail.common.localconfig.LC;
import org.zmail.common.mime.ContentDisposition;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.rmgmt.RemoteCommands;
import org.zmail.cs.rmgmt.RemoteManager;
import org.zmail.cs.rmgmt.RemoteResult;
import org.zmail.cs.servlet.ZmailServlet;

public class CollectLDAPConfigZmail extends ZmailServlet {
	private static final String DOWNLOAD_CONTENT_TYPE = "application/x-compressed";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			//check the auth token
			AuthToken authToken = getAdminAuthTokenFromCookie(req, resp);
			if (authToken == null) 
			   return;		
			//find the LDAP master
			Provisioning prov = Provisioning.getInstance();
			String ldapHost = LC.ldap_host.value();
			if(ldapHost == null) {
				throw ServiceException.INVALID_REQUEST("Cannot find value for ldap_host in local config", null);
			}
			Server server = prov.get(Key.ServerBy.name, ldapHost);
			if (server == null) {
				throw ServiceException.INVALID_REQUEST("Cannot find server record for LDAP master host: " + ldapHost, null);
			}		
			//call RemoteManager
			RemoteManager rmgr = RemoteManager.getRemoteManager(server);
			RemoteResult rr = rmgr.execute(RemoteCommands.COLLECT_LDAP_ZIMBRA);
			//stream the data
			resp.setContentType(DOWNLOAD_CONTENT_TYPE);
            ContentDisposition cd = new ContentDisposition(Part.INLINE).setParameter("filename", ldapHost+".ldif.gz");
            resp.addHeader("Content-Disposition", cd.toString());			
			ByteUtil.copy(new ByteArrayInputStream(rr.getMStdout()), true, resp.getOutputStream(), false);
		} catch (ServiceException e) {
			returnError(resp, e);
        	return;
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
