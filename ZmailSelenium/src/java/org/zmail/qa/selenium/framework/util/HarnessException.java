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
package org.zmail.qa.selenium.framework.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.qa.selenium.framework.core.ClientSessionFactory;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties.AppType;

public class HarnessException extends Exception {
	Logger logger = LogManager.getLogger(HarnessException.class);

	private static final long serialVersionUID = 4657095353247341818L;

	protected void resetAccounts() {
		logger.error("Reset AccountZWC due to exception");
		ZmailAccount.ResetAccountZWC();
		ZmailAccount.ResetAccountHTML();
		ZmailAccount.ResetAccountZMC();
		ZmailAccount.ResetAccountZDC();
		ZmailAdminAccount.ResetAccountAdminConsoleAdmin();
		if (ZmailSeleniumProperties.getAppType() == AppType.ADMIN) {
			// WORKAROUND for all the dialogs that need to be dismissed
			// Reload the app
			ClientSessionFactory.session().selenium().refresh();
			SleepUtil.sleep(10000);
		}
	}
	
	public HarnessException(String message) {
		super(message);
		logger.error(message, this);
		resetAccounts();
	}

	public HarnessException(Throwable cause) {
		super(cause);
		logger.error(cause.getMessage(), cause);
		resetAccounts();
	}

	public HarnessException(String message, Throwable cause) {
		super(message, cause);
		logger.error(message, cause);
		resetAccounts();
	}

}
