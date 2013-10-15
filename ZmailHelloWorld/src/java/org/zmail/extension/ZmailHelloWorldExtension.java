/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.extension;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.extension.ExtensionException;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.soap.SoapServlet;
/**
 * The main entry point for extensions
 * @author gsolovyev
 *
 */
public class ZmailHelloWorldExtension implements ZmailExtension {
	public static String ZAS_EXTENSION_NAME = "org_zmail_appointment_summary";
	public static final String APPOINTMENT_SUMMARY_TASK_NAME = "SendAppointmentSummary";
	public static final String E_helloWorld = "HelloWorld";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return ZAS_EXTENSION_NAME;
	}

	@Override
	public void init() throws ExtensionException, ServiceException {
		SoapServlet.addService("SoapServlet", new ZmailHelloWorldService());
	}

}
