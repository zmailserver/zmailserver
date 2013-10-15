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
package org.zmail.qa.selenium.framework.util.staf;

import org.zmail.qa.selenium.framework.util.HarnessException;

/**
 * @deprecated As of version 7.0
 * @author zmail
 *
 */
public class Stafzmmailbox extends StafServicePROCESS {
		
	public Stafzmmailbox() {
		super();
		
		logger.info("new "+ Stafzmmailbox.class.getCanonicalName());
		StafService = "PROCESS";
		
	}
	
	public boolean execute(String command) throws HarnessException {
		setCommand(command);
		return (super.execute());
	}
	
	protected String setCommand(String command) {
		
		// Make sure the full path is specified
		if ( command.trim().startsWith("zmmailbox") ) {
			command = "/opt/zmail/bin/" + command;
		}
		// Running a command as 'zmail' user.
		// We must convert the command to a special format
		// START SHELL COMMAND "su - zmail -c \'<cmd>\'" RETURNSTDOUT RETURNSTDERR WAIT 30000</params>

		StafParms = String.format("START SHELL COMMAND \"su - zmail -c '%s'\" RETURNSTDOUT RETURNSTDERR WAIT %d", command, this.getTimeout());
		return (getStafCommand());
	}
	
}
