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
//helper class for retrieving properties
package org.zmail.qa.selenium.framework.util;

import java.io.File;
import java.net.InetAddress;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ZmailSeleniumProperties {
	private static final Logger logger = LogManager.getLogger(ZmailSeleniumProperties.class);
	
	// Use these strings as arguments for some standard properties, e.g. ZmailSeleniumProperties.getStringProperty(PropZmailServer, "default");
	public static final String PropZmailVersion = "zmailserverversion"; 
	private static InetAddress localMachine;
	private static ZmailSeleniumProperties instance = null;
	private File BaseDirectory = null;
	private File PropertiesConfigurationFilename = null;	
	private PropertiesConfiguration configProp;

	public static void setStringProperty(String key,String value) {
		ZmailSeleniumProperties.getInstance().getConfigProp().setProperty(key, value);
	}
	
	public static String getStringProperty(String key, String defaultValue) {
		return (ZmailSeleniumProperties.getInstance().getConfigProp()
				.getString(key, defaultValue));
	}

	public static String getStringProperty(String key) {
		return (getStringProperty(key, null));
	}
	
	public static int getIntProperty(String key) {
		return (getIntProperty(key, 0));
	}

	public static int getIntProperty(String key, int defaultValue) {
		String value = ZmailSeleniumProperties.getInstance().getConfigProp().getString(key, null);
		if ( value == null )
			return (defaultValue);
		return (Integer.parseInt(value));
	}

	private static int counter = 0;
	public static String getUniqueString() {
		return ("" + System.currentTimeMillis() + (++counter));
	}

	public static ResourceBundle getResourceBundleProperty(String key) {
		return ((ResourceBundle) ZmailSeleniumProperties.getInstance()
				.getConfigProp().getProperty(key));
	}

	public static PropertiesConfiguration getConfigProperties() {
		return ZmailSeleniumProperties.getInstance().getConfigProp();
	}
	
	public static PropertiesConfiguration setConfigProperties(String filename) {
		logger.info("setConfigProperties using: "+ filename);
		ZmailSeleniumProperties.getInstance().PropertiesConfigurationFilename = new File(filename);
		ZmailSeleniumProperties.getInstance().init();
		return (ZmailSeleniumProperties.getInstance().getConfigProp());
	}

	public static String getBaseDirectory() {
		if (ZmailSeleniumProperties.getInstance().BaseDirectory == null)
			return (".");
		return (ZmailSeleniumProperties.getInstance().BaseDirectory.getAbsolutePath());
	}
	
	public static File setBaseDirectory(String directory) {
		logger.info("setWorkingDirectory using: "+ directory);
		ZmailSeleniumProperties.getInstance().BaseDirectory = new File(directory);
		return (ZmailSeleniumProperties.getInstance().BaseDirectory);
	}
	
	private PropertiesConfiguration getConfigProp() {
		return configProp;
	}

	private static ZmailSeleniumProperties getInstance() {
		if ( instance == null ) {
			synchronized(ZmailSeleniumProperties.class) {
				if ( instance == null ) {
					instance = new ZmailSeleniumProperties();
					instance.init();
				}
			}
		}
		return instance;
	}

	private ZmailSeleniumProperties() {
		logger.debug("new ZmailSeleniumProperties");
	}

	private void init() {

		// Load the config.properties values
		if ( PropertiesConfigurationFilename == null ) {
			logger.info("config.properties is default");
			configProp = createDefaultProperties();
		} else {
			try {
				logger.info("config.properties is "+ PropertiesConfigurationFilename.getAbsolutePath());
				configProp = new PropertiesConfiguration();
				configProp.load(PropertiesConfigurationFilename);
			} catch (ConfigurationException e) {
				logger.error("Unable to open config file: " + PropertiesConfigurationFilename.getAbsolutePath(), e);
				logger.info("config.properties is default");
				configProp = createDefaultProperties();
			}
		}


		// Load the locale information
		String locale = configProp.getString("locale");

		configProp.setProperty("zmMsg", ResourceBundle.getBundle("ZmMsg", new Locale(locale)));

		configProp.setProperty("zhMsg", ResourceBundle.getBundle("ZhMsg", new Locale(locale)));

		configProp.setProperty("ajxMsg", ResourceBundle.getBundle("AjxMsg", new Locale(locale)));

		configProp.setProperty("i18Msg", ResourceBundle.getBundle("I18nMsg", new Locale(locale)));

		configProp.setProperty("zsMsg", ResourceBundle.getBundle("ZsMsg", new Locale(locale)));

	}

	private PropertiesConfiguration createDefaultProperties() {
		PropertiesConfiguration defaultProp = new PropertiesConfiguration();

		defaultProp.setProperty("browser", "FF3");

		defaultProp.setProperty("runMode", "DEBUG");

		defaultProp.setProperty("product", "zcs");

		defaultProp.setProperty("locale", "en_US");

		defaultProp.setProperty("intl", "us");

		defaultProp.setProperty("testdomain", "testdomain.com");

		defaultProp.setProperty("multiWindow", "true");

		defaultProp.setProperty("objectDataFile",
				"projects/zcs/data/objectdata.xml");

		defaultProp.setProperty("testDataFile",
				"projects/zcs/data/testdata.xml");

		defaultProp.setProperty("serverMachineName", "localhost");

		defaultProp.setProperty("serverport", "4444");

		defaultProp.setProperty("mode", "http");

		defaultProp.setProperty("server", "qa60.lab.zmail.com");

		defaultProp.setProperty("ZmailLogRoot", "test-output");

		defaultProp.setProperty("adminName", "admin");

		defaultProp.setProperty("adminPwd", "test123");
		
		defaultProp.setProperty("very_small_wait", "1000");

		defaultProp.setProperty("small_wait", "1000");

		defaultProp.setProperty("medium_wait", "2000");

		defaultProp.setProperty("long_wait", "4000");

		defaultProp.setProperty("very_long_wait", "10000");

		String locale = defaultProp.getString("locale");

		defaultProp.setProperty("zmMsg", ResourceBundle.getBundle("ZmMsg", new Locale(locale)));

		defaultProp.setProperty("zhMsg", ResourceBundle.getBundle("ZhMsg", new Locale(locale)));

		defaultProp.setProperty("ajxMsg", ResourceBundle.getBundle("AjxMsg", new Locale(locale)));

		defaultProp.setProperty("i18Msg", ResourceBundle.getBundle("I18nMsg", new Locale(locale)));

		defaultProp.setProperty("zsMsg", ResourceBundle.getBundle("ZsMsg", new Locale(locale)));

		return defaultProp;
	}

	/**
	 * isWebDriver() 
	 * method to check whether WebDriver mode is enabled
	 * in the configuration settings
	 */
	public static boolean isWebDriver() {
		if (ZmailSeleniumProperties.getStringProperty("seleniumDriver") != null && ZmailSeleniumProperties.getStringProperty("seleniumDriver").contentEquals("WebDriver"))
			return true;
		else
			return false;
	}
	
	/**
	 * isWebDriverBackedSelenium() 
	 * method to check whether WebDriverBackedSelenium mode is enabled
	 * in the configuration settings
	 */
	public static boolean isWebDriverBackedSelenium() {
		if (ZmailSeleniumProperties.getStringProperty("seleniumDriver") != null && ZmailSeleniumProperties.getStringProperty("seleniumDriver").contentEquals("WebDriverBackedSelenium"))
			return true;
		else
			return false;
	}

	/**
	 * App type
	 */
	public enum AppType {
		AJAX, HTML, MOBILE, DESKTOP, ADMIN, APPLIANCE, OCTOPUS
	}
	
	private static AppType appType = AppType.AJAX;
	public static void setAppType(AppType type) {
		appType = type;
	}
	public static AppType getAppType() {
		return (appType);
	}
	
	public static String getLocalHost() {
		try {
			localMachine = InetAddress.getLocalHost();
			return localMachine.getHostName();
		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			return "127.0.0.1";
		}
	}
	
	private static final String CalculatedBrowser = "CalculatedBrowser";
	public static String getCalculatedBrowser() {
		
		String browser = getStringProperty(CalculatedBrowser);
		
		if ( browser != null ) {
			// Calculated browser already determined, just return it
			return (browser);
		}
				
		browser = ZmailSeleniumProperties.getStringProperty(
				ZmailSeleniumProperties.getLocalHost() + ".browser",
				ZmailSeleniumProperties.getStringProperty("browser"));
		
		if (browser.charAt(0) == '*') {
			browser = browser.substring(1);
			if ((browser.indexOf(" ")) > 0) {
				String str = browser.split(" ")[0];
				int i;
				if ((i = browser.lastIndexOf("\\")) > 0) {
					str += "_" + browser.substring(i+1);
				}
				browser = str;
			}
		}
		
		// Save the browser value (for logging)
		ZmailSeleniumProperties.setStringProperty(CalculatedBrowser, browser);

		return (browser);
	}
	
	/**
	 * Get Logout URL for selenium to sign out from the application
	 * @return Logout URL String
	 * 
	 */
	public static String getLogoutURL() {
		
		ZmailURI uri = new ZmailURI(ZmailURI.getBaseURI());
		uri.addQuery("loginOp", "logout");
		return (uri.toString());

	}
	
	/**
	 * Get Base URL for selenium to open to access the application
	 * under test
	 * @return Base URL
	 * @throws HarnessException 
	 */
	public static String getBaseURL() {
		
		return (ZmailURI.getBaseURI().toString());
		
	}
	
	public static String zmailGetVersionString() throws HarnessException {		
		ZmailAdminAccount.GlobalAdmin().soapSend("<GetVersionInfoRequest xmlns='urn:zmailAdmin'/>");
		String version = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:info", "version");
		if ( version == null )
			throw new HarnessException("Unable to determine version from GetVersionInfoResponse "+ ZmailAdminAccount.GlobalAdmin().soapLastResponse());
		
		// The version string looks like 6.0.7_GA_2470.UBUNTU8.NETWORK
		return (version);
	}


	// for unit test need to change access to public
	public static void main(String[] args) {

		System
				.setProperty("log4j.configuration",
						"file:///C:/log4j.properties");
		System.out.println(System.getProperty("log4j.configuration"));

		System.out.println(System.getProperty("user.dir"));

		String br = (String) ZmailSeleniumProperties.getInstance()
				.getConfigProp().getProperty("browser");
		System.out.println(br);
		logger.debug(br);

		ResourceBundle zmMsg = (ResourceBundle) ZmailSeleniumProperties
				.getInstance().getConfigProp().getProperty("zmMsg");
		System.out.println(zmMsg.getLocale());
		logger.debug(zmMsg.getLocale());				
	}

}