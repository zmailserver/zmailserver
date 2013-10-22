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
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;

public class Stafzmtlsctl extends StafServicePROCESS {

   public enum SERVER_ACCESS {
      HTTP,
      HTTPS,
      BOTH
   }

   public void setServerAccess(SERVER_ACCESS serverAccess)
   throws HarnessException {
      String setting = null;

      StafServicePROCESS stafServicePROCESS = new StafServicePROCESS();
      stafServicePROCESS.execute("zmprov gs `zmhostname` ZmailMailMode");
      String serverName = ZmailSeleniumProperties.getStringProperty("server.host", "localhost");
      String mode = stafServicePROCESS.getStafResponse().split(serverName)[1].split("}")[0].split("zmailMailMode:")[1].trim();

      logger.info("Current server access mode: " + mode);

      switch (serverAccess) {
      case HTTP:
         setting = "http";
         break;
      case HTTPS:
         setting = "https";
         break;
      case BOTH:
         setting = "both";
         break;
      }

      logger.info("Expected server access: " + setting);
      
      if (!mode.equals(setting)) {
         logger.debug("Set the server access mode to " + setting);
         execute("zmtlsctl " + setting);
         //execute("zmconfigdctl reload");
         SleepUtil.sleep(60000);
         stafServicePROCESS.execute("zmmailboxdctl restart");
         
         // Hardcoded 20 seconds sleep is required here, if this still doesn't work, then
         // we have to do the most robust way, wait for HTTP GET to return status 200 
         SleepUtil.sleep(20000);         
      } else {
         logger.info("Current and expected server access modes are the same already");
      }
   }

   public boolean execute(String command) throws HarnessException {
      setCommand(command);
      boolean output = super.execute();

      Stafpostqueue stafpostqueue = new Stafpostqueue();
      stafpostqueue.waitForPostqueue();

      return output;
   }

   protected String setCommand(String command) {

      // Make sure the full path is specified
      if ( command.trim().startsWith("zmtlsctl") ) {
         command = "/opt/zmail/bin/" + command;
      }
      // Running a command as 'zmail' user.
      // We must convert the command to a special format
      // START SHELL COMMAND "su - zmail -c \'<cmd>\'" RETURNSTDOUT RETURNSTDERR WAIT 30000</params>

      StafParms = String.format("START SHELL COMMAND \"su - zmail -c '%s'\" RETURNSTDOUT RETURNSTDERR WAIT %d", command, this.getTimeout());
      return (getStafCommand());
   }
}
