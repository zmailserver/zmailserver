/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.taglib.tag;

import org.zmail.cs.account.soap.SoapProvisioning;
import org.zmail.cs.account.Entry;
import org.zmail.common.account.Key;
import org.zmail.common.localconfig.LC;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public class GetFavIconTag extends ZmailSimpleTag {

	//
	// Data
	//

	private String var;
	private HttpServletRequest request;

	//
	// Public methods
	//

	// properties

	public void setVar(String var) {
		this.var = var;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	// simple tag methods

	public void doTag() throws JspException, IOException {
		try {
			// get provisioning
			String soapUri =
				LC.zmail_admin_service_scheme.value() +
				LC.zmail_zmprov_default_soap_server.value() +
				':' +
				LC.zmail_admin_service_port.intValue() +
				AdminConstants.ADMIN_SERVICE_URI
			;

			SoapProvisioning provisioning = new SoapProvisioning();
			provisioning.soapSetURI(soapUri);

			// get serverName
			String serverName = this.request.getParameter("customerDomain");
			// TODO: Is this possible in this context? Does it matter?
//			if(serverName==null || serverName.trim().length() == 0) {
//				serverName = getServletConfig().getInitParameter(P_SERVER_NAME);
//			}
			if (serverName == null) {
				serverName = HttpUtil.getVirtualHost(this.request);
			}

			// get info
			Entry info = provisioning.getDomainInfo(Key.DomainBy.virtualHostname, serverName);
			if (info == null) {
				info = provisioning.getConfig();
			}
			if (info != null) {
			    String favicon = info.getAttr("zmailSkinFavicon");
				getJspContext().setAttribute(this.var, favicon, PageContext.REQUEST_SCOPE);
			}
			else {
				if (ZmailLog.webclient.isDebugEnabled()) {
					ZmailLog.webclient.debug("unable to get domain or config info");
				}
			}
		}
		catch (Exception e) {
			if (ZmailLog.webclient.isDebugEnabled()) {
				ZmailLog.webclient.debug("error getting favicon: "+e.getMessage());
			}
		}
	}

} // class GetFavIconTag
