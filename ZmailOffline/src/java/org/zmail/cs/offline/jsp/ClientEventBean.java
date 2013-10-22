/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.offline.jsp;

import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.cs.offline.common.OfflineConstants;

public class ClientEventBean extends PageBean {
	
	protected void onLogin() {
        String uri = getBaseUri() + "/service/soap/";
		try {
			SoapHttpTransport transport = new SoapHttpTransport(uri);
			transport.setTimeout(5000);
			transport.setRetryCount(1);
			transport.setRequestProtocol(SoapProtocol.Soap12);
			transport.setResponseProtocol(SoapProtocol.Soap12);

			Element request = new Element.XMLElement(OfflineConstants.CLIENT_EVENT_NOTIFY_REQUEST);
			request.addAttribute(OfflineConstants.A_Event, OfflineConstants.EVENT_UI_LOAD_BEGIN);
			transport.invokeWithoutSession(request.detach());
		} catch (Exception x) {
			System.out.println("failed sending ui_load_event");
			x.printStackTrace(System.out);
		}
	}
	
	public static void onLogin(ClientEventBean bean) {
		bean.onLogin();
	}
}
