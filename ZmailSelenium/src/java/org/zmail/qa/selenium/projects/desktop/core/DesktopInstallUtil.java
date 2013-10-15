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
package org.zmail.qa.selenium.projects.desktop.core;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import org.zmail.qa.selenium.framework.util.BuildUtility;
import org.zmail.qa.selenium.framework.util.CommandLine;
import org.zmail.qa.selenium.framework.util.GeneralUtility;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.OperatingSystem;
import org.zmail.qa.selenium.framework.util.SleepUtil;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailDesktopProperties;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.framework.util.BuildUtility.ARCH;
import org.zmail.qa.selenium.framework.util.BuildUtility.BRANCH;
import org.zmail.qa.selenium.framework.util.BuildUtility.PRODUCT_NAME;
import org.zmail.qa.selenium.framework.util.GeneralUtility.WAIT_FOR_OPERAND;
import org.zmail.qa.selenium.framework.util.OperatingSystem.OsArch;
import org.zmail.qa.selenium.framework.util.OperatingSystem.OsType;

class ZDProcess{
   public int desktopClientPid = 0;
   public int desktopServerPid = 0;

   ZDProcess(int clientPid, int serverPid) {
      desktopClientPid = clientPid;
      desktopServerPid = serverPid;
   }
}

/**
 * This class contains methods that can be used for Zmail Desktop Installation and Uninstallation
 * @author Jeffry Hidayat
 *
 */
public class DesktopInstallUtil {
   protected static Logger logger = LogManager.getLogger(DesktopInstallUtil.class);
   private static final String _commonRegistryPath_x64 = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
   private static final String _commonRegistryPath_x86 = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall";
   private static String _desktopRegistryPath = null;
   private static final String _zmailDesktopDisplayName = "Zmail Desktop";
   private static ZDProcess zdProcess = null;
   //private static final String _buildUrl = "http://zre-matrix.eng.vmware.com/links/WINDOWS/HELIX/20110114070101_ZDESKTOP/ZmailBuild/i386/";

   /**
    * Reads Windows registry value based on the specified location and key
    * @param location Registry Path
    * @param key Registry Key to be queried for value
    * @return (String) Raw output of the query
    */
   private static final String _readZmailDesktopRegistry(String location, String key){
      try {
         String registryQuery = "reg query " + location;
         // Run reg query, then read output with StreamReader (internal class)
         String[] lines = CommandLine.cmdExecWithOutput(registryQuery).split("\n");

         logger.debug("registryQuery is: " + registryQuery);
         logger.debug("Lines are: ");

         String output = null;
         String zmailDesktopRegistryPath = null;
         if (_desktopRegistryPath == null) {
            for (int i = 0; i < lines.length; i++) {
               if (lines[i].contains("\"")) {
                  lines[i] = lines[i].replaceAll("\"", "");
               }
               if (lines[i].trim().equals("")) {
                  logger.debug("//Skip it");
               } else {
                  String displayName = CommandLine.cmdExecWithOutput("reg query " +
                         lines[i] + " /v DisplayName");
                  logger.debug("Display Name is: " + displayName);
                  if (displayName.contains(_zmailDesktopDisplayName)) {
                     zmailDesktopRegistryPath = _desktopRegistryPath = lines[i];
                     break;
                  }
               }
            }
         } else {
            zmailDesktopRegistryPath = _desktopRegistryPath;
         }

         if (zmailDesktopRegistryPath == null) {
            return null;
         } else {
            output = CommandLine.cmdExecWithOutput("reg query " +
                  zmailDesktopRegistryPath + " /v " + key);
            return output;
         }

      } catch (Exception e) {
         return null;
      }
   }

   /**
    * Gets the registry value from readRegistry method and parse it to only the value
    * @param location Registry Path
    * @param key Registry Key to be queried for value
    * @return (String) Value of the specified key
    */
   private static final String _getRegistryValue(String location, String key) {
      String output = _readZmailDesktopRegistry(location, key).trim();
      logger.info("Registry output is: " + output);
      // Now process it
      // Output has the following format:
      // \n<Version information>\n\n<key>    *_SZ    <value>
      if (output == null) {
         return "";
      } else {
         if (output.equals("")) {
            return output;
         } else {
            // Parse out the value
            String[] parsed = output.split("_SZ");
            return parsed[parsed.length-1].trim();
         }
      }
   }

   public static ZDProcess getZDProcess()
   throws HarnessException, IOException, InterruptedException {

      if (zdProcess == null) {         
         int clientProcess = 0;
         int serverProcess = 0;
         
         switch (OperatingSystem.getOSType()) {
         case LINUX:
            String username = ZmailDesktopProperties.getInstance().getUserName();
            logger.info("user name is: " + username);
            String [] output = CommandLine.cmdExecWithOutput("ps -ef").split("\n");
            logger.info("output's length is: " + output.length);

            for (int i = 0; i < output.length; i++) {
               logger.info("Process " + i + ": " + output[i]);
               if (output[i].contains("/linux/prism") && !output[i].contains("sh ")) {
                  String clientProcessString = output[i].replace(username, "").trim().split(" ")[0];
                  logger.info("Client Process String: " + clientProcessString);
                  clientProcess = Integer.parseInt(clientProcessString);
               } else if (output[i].contains("/linux/jre")) {
                  String serverProcessString = output[i].replace(username, "").trim().split(" ")[0];
                  logger.info("Server Process String: " + serverProcessString);
                  serverProcess = Integer.parseInt(serverProcessString);
               }
            }

            if (serverProcess == 0 && clientProcess != 0) {
               // retry
               int retry = 0;
               int maxRetry = 60;
               while (serverProcess == 0 && retry < maxRetry) {
                  SleepUtil.sleep(1000);
                  maxRetry ++;

                  output = CommandLine.cmdExecWithOutput("ps -ef").split("\n");
                  logger.info("output's length is: " + output.length);

                  for (int i = 0; i < output.length; i++) {
                     logger.info("Process " + i + ": " + output[i]);
                     if (output[i].contains("/linux/jre")) {
                        String serverProcessString = output[i].replace(username, "").trim().split(" ")[0];
                        logger.info("Server Process String: " + serverProcessString);
                        serverProcess = Integer.parseInt(serverProcessString);
                     }
                  }
               }
            }

            break;
         case MAC:
            username = ZmailDesktopProperties.getInstance().getUserName();
            logger.info("user name is: " + username);
            output = CommandLine.cmdExecWithOutput("ps -ef").split("\n");
            logger.info("output's length is: " + output.length);

            for (int i = 0; i < output.length; i++) {
               logger.info("Process " + i + ": " + output[i]);
               if (output[i].contains("/MacOS/prism") && !output[i].contains("sh ")) {
                  int j;
                  for (j = 1; j < 20; j++) {
                     if (!output[i].trim().split(" ")[j].equals("")){
                        break;
                     }
                  }
                  String clientProcessString = output[i].trim().split(" ")[j];
                  logger.info("Client Process String: " + clientProcessString);
                  clientProcess = Integer.parseInt(clientProcessString);
               } else if (output[i].contains("Desktop/bin/zdesktop start -w")) {
                  int j;
                  for (j = 1; j < 20; j++) {
                     if (!output[i].trim().split(" ")[j].equals("")){
                        break;
                     }
                  }
                  String serverProcessString = output[i].trim().split(" ")[j];
                  logger.info("Server Process String: " + serverProcessString);
                  serverProcess = Integer.parseInt(serverProcessString);
               }
            }

            if (serverProcess == 0 && clientProcess != 0) {
               // retry
               int retry = 0;
               int maxRetry = 60;
               while (serverProcess == 0 && retry < maxRetry) {
                  SleepUtil.sleep(1000);
                  maxRetry ++;

                  output = CommandLine.cmdExecWithOutput("ps -ef").split("\n");
                  logger.info("output's length is: " + output.length);

                  for (int i = 0; i < output.length; i++) {
                     logger.info("Process " + i + ": " + output[i]);
                     if (output[i].contains("/linux/jre")) {
                        String serverProcessString = output[i].replace(username, "").trim().split(" ")[0];
                        logger.info("Server Process String: " + serverProcessString);
                        serverProcess = Integer.parseInt(serverProcessString);
                     }
                  }
               }
            }

            break;
         default:
            throw new HarnessException("Implement me!");
         }
         
         if (clientProcess == 0 && serverProcess == 0) {
            return null;
         } else {
            zdProcess = new ZDProcess(clientProcess, serverProcess);
            return zdProcess;
         }

      } else {
         return zdProcess;
      }
   }

   public static boolean isDesktopAppRunning()
   throws IOException, InterruptedException, HarnessException {
      boolean isRunning = false;
      switch (OperatingSystem.getOSType()) {
      case WINDOWS: case WINDOWS_XP:
         isRunning = (GeneralUtility.findWindowsRunningTask("zdesktop.exe") ||
               GeneralUtility.findWindowsRunningTask("zdclient.exe"));
         break;
      case LINUX: case MAC:
         isRunning = (getZDProcess() == null ? false : true);
         break;
      default:
         throw new HarnessException("Implement me!");
      }

      logger.info("isDesktopAppRunning - " + isRunning);
      return isRunning;
   }

   public static void killDesktopProcess() throws HarnessException {
      try {
         if (isDesktopAppRunning()) {
            
            switch (OperatingSystem.getOSType()) {
            case WINDOWS: case WINDOWS_XP:
               CommandLine.CmdExec("TASKKILL /F /IM zdclient.exe");
               CommandLine.CmdExec("TASKKILL /F /IM zdesktop.exe");
               break;
            case LINUX: case MAC:
               if (zdProcess.desktopClientPid != 0) {
                  CommandLine.CmdExec("kill -9 " + zdProcess.desktopClientPid);
               }
               CommandLine.CmdExec("kill -9 " + zdProcess.desktopServerPid);
               _resetZdProcess();
               break;
            default:
               throw new HarnessException("Implement me!");
            }
         } else {
            logger.info("None of the desktop process is running");
         }
         
      } catch (IOException ioe) {
         throw new HarnessException("Getting IO Exception", ioe);
      } catch (InterruptedException ie) {
         throw new HarnessException("Getting Interrupted Exception", ie);
      } finally {
         ZmailAccount.AccountZDC().resetClientAuthentication();
      }
   }

   private static void _resetZdProcess() {
      zdProcess = null;
   }

   /**
    * Uninstalls Zmail Desktop Application, if it was uninstalled already, then
    * just exits the method, otherwise it will execute and wait dynamically until
    * the uninstallation is done (Waiting for the registry query to return nothing)
    * @throws InterruptedException 
    * @throws IOException 
    * @throws HarnessException 
    */
   public static void uninstallDesktopApp() throws IOException, InterruptedException, HarnessException {
      OsType osType = OperatingSystem.getOSType();
      OsArch osArch = OperatingSystem.getOsArch();

      if (isDesktopAppInstalled()) {
         killDesktopProcess();

         switch (osType) {
         case WINDOWS: case WINDOWS_XP:

            String uninstallCommand = null;
            switch (osArch) {
            case X64:
               uninstallCommand = DesktopInstallUtil._getRegistryValue(
                     DesktopInstallUtil._commonRegistryPath_x64, "UninstallString") + " /quiet";
               break;
            case X86:
               uninstallCommand = DesktopInstallUtil._getRegistryValue(
                     DesktopInstallUtil._commonRegistryPath_x86, "UninstallString") + " /quiet";
               break;
            }

            //TODO: Do for Linux and Mac, right now it's only for Windows
            logger.info("uninstallCommand is: " + uninstallCommand);
      
            if ( (uninstallCommand == null) || (uninstallCommand.trim().equals(""))) {
               logger.info("Zmail Desktop App doesn't exist, thus exiting the method");
               return;
            } else {
               logger.debug("Executing command...");
               CommandLine.CmdExec(uninstallCommand);
               logger.debug("uninstallCommand execution is successful");
               GeneralUtility.waitFor("org.zmail.qa.selenium.projects.desktop.core.DesktopInstallUtil", null, true, "isDesktopAppInstalled", null, WAIT_FOR_OPERAND.NEQ, true,
                     300 * 1000, 5 * 1000);
               logger.debug("Successfully waiting for");
               logger.info("Zmail Desktop Uninstallation is complete!");
            }
            break;
         case LINUX:
            File mainDir = new File("/opt/zmail/zdesktop");
            GeneralUtility.deleteDirectory(mainDir);
            break;
         case MAC:
            mainDir = new File("/Applications/Zmail Desktop/");
            GeneralUtility.deleteDirectory(mainDir);
            break;
         }
      } else {
         logger.info("Desktop App doesn't exist, so quitting the uninstallation...");
      }

      logger.info("Removing existing config file");

      ZmailDesktopProperties.getInstance();
      if (ZmailDesktopProperties.getUserFolder() != null) {
         File localProfile = new File(ZmailDesktopProperties.getUserFolder());
         logger.debug("Deleting Local profile... : " + localProfile.getCanonicalPath());
         if (GeneralUtility.deleteDirectory(localProfile)) {
            logger.debug("Local Profile folders cannot be deleted.");
         } else {
            logger.debug("Local Profile folders are successfully deleted.");
         }
      }

      ZmailDesktopProperties.reset();
      _desktopRegistryPath = null;
   }

   /**
    * Installs Zmail Desktop Application, if the app has been installed (based on the readRegistry),
    * then nothing to proceed
    * @param msiLocation Location of the installation file
    * @throws HarnessException
    * @throws IOException
    * @throws InterruptedException
    */
   public static void installDesktopApp(String installFileBinaryLocation) throws HarnessException, IOException, InterruptedException {

      if (DesktopInstallUtil.isDesktopAppInstalled()) {
         logger.info("Zmail Desktop App has already been installed.");
      } else {
         //TODO: Do for Linux and Mac, right now it's only for Windows
         logger.info("installFileBinaryLocation is: " + installFileBinaryLocation);
         File file = new File(installFileBinaryLocation);
         OsType osType = OperatingSystem.getOSType();
         String installCommand = null;

         if (!file.exists()) {
            throw new HarnessException ("MSI file doesn't exist at given path" + installFileBinaryLocation);
         } else {
            switch (osType) {
            case WINDOWS: case WINDOWS_XP:
               installCommand = "MsiExec.exe /i " + installFileBinaryLocation + " /qn";
               logger.debug("installCommand is: " + installCommand);
               CommandLine.CmdExec(installCommand);
               logger.debug("installCommand execution is successful");
               GeneralUtility.waitFor("org.zmail.qa.selenium.projects.desktop.core.DesktopInstallUtil", null, true, "isDesktopAppInstalled", null, WAIT_FOR_OPERAND.EQ, true,
                     300 * 1000, 5 * 1000);
               logger.info("Zmail Desktop Installation is complete!");
               break;

            case LINUX:

               file = new File(installFileBinaryLocation);

               logger.info("Giving full permission to installFileBinaryLocation");
               CommandLine.cmdExecWithOutput("chmod 777 " + installFileBinaryLocation);
               logger.info("PWD: " + CommandLine.cmdExecWithOutput("pwd"));

               File currentDir = new File(CommandLine.cmdExecWithOutput("pwd").trim());
               logger.info("currentDir is implemented: " + currentDir.exists());
               String destPath = currentDir.getAbsolutePath().endsWith(Character.toString(File.separatorChar)) ?
                     currentDir.getAbsolutePath() + "zdesktop" :
                        currentDir.getAbsolutePath() + "/zdesktop";
               File destinationDir = new File(destPath);

               logger.info("destinationDir is: " + destinationDir.getAbsolutePath());
               logger.info("Now cleaning the destination dir...");
               if (destinationDir.exists()) {
                  logger.info("destinationDir exists");
                  GeneralUtility.deleteDirectory(destinationDir);
               } else {
                  logger.info("destinationDir doesn't exist");
               }

               logger.info("Creating the directory...");
               GeneralUtility.createDirectory(destinationDir);

               logger.info("Untar-ing the tar file...");
               GeneralUtility.untarBaseUpgradeFile(file, destinationDir);
               logger.info("Untar-ing the tar file is successful.");

               installFileBinaryLocation = installFileBinaryLocation.replace(".tgz", "");
               String[] path = installFileBinaryLocation.split("/");
               logger.info("Giving full permissions to some files...");
               CommandLine.CmdExec("chmod -R 777 " + "zdesktop/" +
                     path[path.length - 1]);

               String[] params = new String[] {"\n", "A\n", "\n", "N\n", "\n"};
               // TODO: /test.txt is only a blank file
               installCommand = "zdesktop/" + path[path.length - 1] + "/install.pl";

               logger.info("installCommand: " + installCommand);
               logger.info("executing installCommand");
               CommandLine.CmdExec(installCommand, params);

               CommandLine.CmdExec("chmod -R 777 " + "/opt/zmail/zdesktop");
               //CommandLine.CmdExec("su - zmail -c ", new String[] {"zmail"});
               String[] params2 = new String[] {"\n", "\n", "\\x03"};
               CommandLine.CmdExec("su - " + ZmailDesktopProperties.getInstance().getUserName() +
                     " -c \"/opt/zmail/zdesktop/linux/user-install.pl\"", params2);
               zdProcess = getZDProcess();

               GeneralUtility.waitFor("org.zmail.qa.selenium.projects.desktop.core.DesktopInstallUtil", null,
                     true, "isDesktopAppRunning", null, WAIT_FOR_OPERAND.EQ, true, 60000, 1000);
               // The reason for killing the current process because desktop app is started by
               // the user-install.pl script and this will lock the thread
               killDesktopProcess();
               break;
            case MAC:
               logger.info(CommandLine.cmdExecWithOutput(
                     "hdiutil mount " + installFileBinaryLocation));
               String [] command = {"cp", "-R", "/Volumes/Zmail Desktop Installer", "/Applications"};
               logger.info(CommandLine.cmdExecWithOutput(command, null));

               command = new String[] {"installer", "-package", "/Applications/Zmail Desktop Installer/Zmail Desktop.mpkg", "-target", "/Volumes/Server HD"};
               logger.info(CommandLine.cmdExecWithOutput(command, null));

               command = new String[] {"hdiutil", "unmount", "/Volumes/Zmail Desktop Installer/"};
               logger.info(CommandLine.cmdExecWithOutput(command, null));

               GeneralUtility.waitFor("org.zmail.qa.selenium.projects.desktop.core.DesktopInstallUtil", null,
                     true, "isDesktopAppRunning", null, WAIT_FOR_OPERAND.EQ, true, 60000, 1000);
               GeneralUtility.waitFor(null, ZmailAccount.AccountZDC(), false,
                     "authenticateToMailClientHost", null, WAIT_FOR_OPERAND.NEQ, null, 60000, 3000);
               // The reason for killing the current process because desktop app is started by
               // the installer script and this will block selenium test browser window
               killDesktopProcess();
               break;
            }
         }
      }
   }

   /**
    * Determines whether the Zmail Desktop client is installed
    * @return true, if it is installed, otherwise, false
    */
   public static boolean isDesktopAppInstalled() {
      OsType osType = OperatingSystem.getOSType();
      OsArch osArch = OperatingSystem.getOsArch();
      boolean isDesktopInstalled = false;
      switch (osType) {
      case WINDOWS: case WINDOWS_XP:
         String registry = null;
         switch (osArch) {
         case X64:
            registry = DesktopInstallUtil._readZmailDesktopRegistry(DesktopInstallUtil._commonRegistryPath_x64, "UninstallString");
            break;
         case X86:
            registry = DesktopInstallUtil._readZmailDesktopRegistry(DesktopInstallUtil._commonRegistryPath_x86, "UninstallString");
            break;
         } 

         if (registry != null) {
            if (!registry.trim().equals("") &&
                  !registry.trim().toLowerCase().contains("error")) {
               isDesktopInstalled = true;
               logger.debug("readRegistry: " + registry);
            }
         }
         break;
      case LINUX:
         File installDir = new File("/opt/zmail/zdesktop");
         if (installDir.exists()) {
            isDesktopInstalled = true;
         }
         break;
      case MAC:
         installDir = new File("/Applications/Zmail Desktop/Zmail Desktop.app/Contents/MacOS/zdrun");
         if (installDir.exists()) {
            isDesktopInstalled = true;
         }
         break;
      }

      logger.debug("isDesktopInstalled = " + isDesktopInstalled);
      return isDesktopInstalled;   
   }

   /**
    * This method will forcefully install the latest build. If there is a current installed build,
    * it will uninstall and install the latest.
    * @param prodName Product Name
    * @param branch Branch Name
    * @param arch Arch Name
    * @param downloadLocation Location to download the install file, ie. C:\\download-zmail-qa-test\\ for windows
    * @throws HarnessException 
    * @throws InterruptedException 
    * @throws IOException 
    * @throws SAXException 
    */
   public static void forceInstallLatestBuild(PRODUCT_NAME prodName, BRANCH branch, ARCH arch,
         String downloadLocation)
         throws IOException, InterruptedException, HarnessException, SAXException {
      logger.info("Forcefully install latest build");
      boolean isDesktopAppInstalled = isDesktopAppInstalled();
      logger.info("isDesktopAppInstalled: " + isDesktopAppInstalled);
      if (isDesktopAppInstalled) {
         logger.info("Uninstalling the app");
         uninstallDesktopApp();
      } else {
    	  logger.info("nothing to do here");
      }

      logger.info("Downloading the build");
      String buildUrl = ZmailSeleniumProperties.getStringProperty("desktop.buildUrl", "");
      String fullDownloadPath = null;
      if (buildUrl.equals("")) {
         fullDownloadPath = BuildUtility.downloadLatestBuild(downloadLocation, prodName, branch, arch);         
      } else {
         fullDownloadPath = BuildUtility.downloadBuild(downloadLocation, buildUrl);
      }

      installDesktopApp(fullDownloadPath);
   }
}
