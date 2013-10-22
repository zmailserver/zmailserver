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
package com.zimbra.qa.selenium.framework.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;


/**
 * @deprecated As of version 7.0
 * @author zimbra
 *
 */
public class SelNGBase {
	private static Logger logger = LogManager.getLogger(SelNGBase.class);
	

	
	public static int maxRetryCount = 0;
	public static String someting = " ";
	public static String suiteName = "";
	
    public static Map<String, ArrayList<Integer>> FILENAME_TO_COVERAGE = new HashMap<String, ArrayList<Integer>>();
    public static Map<String, JSONArray> FILENAME_TO_SOURCE = new HashMap<String, JSONArray>();


	/**
	 * indicates that the actual object name must start with required obj name
	 */
	public static boolean labelStartsWith = false;

	/**
	 * @fieldLabelIsAnObject if true, searches for edit/textArea's associated
	 *                       label on menu/button ex: [menuLabel][editfield]
	 *                       where menuLabel is the label for editField
	 */
	public static boolean fieldLabelIsAnObject = false;

	/**
	 * actOnLabel: //set this to true if you want to click on the exact
	 * label-element currently used by listItemCore function
	 */
	public static boolean actOnLabel = false;

	/**
	 * dontMatchHeader: //set this to true if you want to ignore Folder header.
	 * Comes in handy when you have a header('Calendars') and a
	 * folder('Calendar') and you want to act on 'Calendar'-folder.
	 */
	public static boolean ignoreFolderHdr = false;

	
	public SelNGBase() {
		logger.debug("New SelNGBase");
	}
	
	public static void resetSession() {
		// reset all the selngbase settings, since they might have been set to
		// true by the failing test
		SelNGBase.labelStartsWith = false;
		SelNGBase.fieldLabelIsAnObject = false;
		SelNGBase.actOnLabel = false;
		if (ClientSessionFactory.session().selenium() != null){
			ClientSessionFactory.session().selenium().stop();
		}
	}



	public static void openApplication() {

		ClientSession session = ClientSessionFactory.session();
		ZimbraSelenium selenium = session.selenium();
		selenium.start();
		selenium.windowMaximize();
		selenium.windowFocus();
		selenium.setupZVariables();
		selenium.allowNativeXpath("true");
		//selenium.useXpathLibrary("javascript-xpath");
		selenium.open(ZimbraSeleniumProperties.getBaseURL());

	}


	public static void customLogin(String parameter) {


		// TODO: is this needed?  It is not specified in openApplication()
		String browser = SeleniumService.getInstance().getSeleniumBrowser();
		if (!browser.startsWith("*")){
			browser = "*" + browser;
		}

		ClientSession session = ClientSessionFactory.session();
		ZimbraSelenium selenium = session.selenium();
		
		selenium.start();
		selenium.windowMaximize();
		selenium.windowFocus();
		selenium.allowNativeXpath("true");
		selenium.open(ZimbraSeleniumProperties.getStringProperty("server.scheme") + "://"	+ ZimbraSeleniumProperties.getStringProperty("server.host") + "/" + parameter);
		
	}


	// can be used as @aftermethod
	public void deleteCookie(String name, String path) {
		ClientSessionFactory.session().selenium().deleteCookie(name, path);
	}

	public static void stopClient() {
		ClientSessionFactory.session().selenium().close();
	}



	
	public static ThreadLocal<Boolean> isExecutionARetry = new ThreadLocal<Boolean>() {
		protected synchronized Boolean initialValue() {
			// return Boolean value per thread
			boolean retry = false;
			return retry;
		}
	};

	public static ThreadLocal<Boolean> needReset = new ThreadLocal<Boolean>() {
		protected synchronized Boolean initialValue() {
			// return Boolean value per thread
			boolean reset = false;
			return reset;
		}
	};
	
}
