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

import java.util.Map;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.ScheduledTask;
import org.zmail.cs.mailbox.ScheduledTaskManager;
import org.zmail.cs.service.account.AccountDocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class HelloWorld extends AccountDocumentHandler {
/**
 * Process the SOAP request (XML of the request in in the request argument). Return the response element.
 */
	@Override
	public Element handle(Element request, Map<String, Object> context)
			throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
		Element response = zsc.createElement(ZmailHelloWorldService.HELLO_WORLD_RESPONSE);
		response.addElement(ZmailHelloWorldExtension.E_helloWorld);
		response.addAttribute(ZmailHelloWorldExtension.E_helloWorld, "hellow");
		return response;
	}

}
