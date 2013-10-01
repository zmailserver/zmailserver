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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleniumException;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;


/**
 * @deprecated As of version 7.0
 * @author zimbra
 *
 */
public class ZimbraSelenium extends DefaultSelenium {
	private static Logger logger = LogManager.getLogger(ZimbraSelenium.class);

	public String doubleQuote = "\"";

	public ZimbraSelenium(String serverHost, int serverPort,
			String browserStartCommand, String browserURL) {
		super(serverHost, serverPort, browserStartCommand, browserURL);
	}
	
	public void open(String url) {
	    logger.info("selenium.open(" + url + ")");
	    super.open(url);
	}
	
	public void openWindow(String url, String id) {
	    logger.info("selenium.open(" + url + ", "+ id +")");
	    super.openWindow(url, id);
	}
	
	public void close() {
	    logger.info("selenium.close()");
	    super.close();
	}
	
	public boolean isElementPresent(String locator){		
	    boolean result=super.isElementPresent(locator);
	    logger.info("selenium.isElementPresent(" + locator + ") = " + result);	   	    
	    return result;
	}
	
	public void click(String locator){
		logger.info("selenium.click(" + locator + ")" + "\n");
		super.click(locator);
	}
	
	public void clickAt(String locator, String coord){
		logger.info("selenium.clickAt(" + locator + "," + coord + ")" + "\n");
		super.clickAt(locator, coord);
	}
	
	public String call(String coreFunction, String locator, String action, Boolean retryOnFalse)throws HarnessException  {
		return this.call(coreFunction, locator, action, retryOnFalse, "", "");
	}
	public String call(String coreFunction, String locator, String action, Boolean retryOnFalse,
			String panel, String param1) throws HarnessException  {
		return this.call(coreFunction, locator, action, retryOnFalse, panel, param1, "", "");
	}
	public String call(String coreFunction, String locator, String action, Boolean retryOnFalse,
			String panel, String param1, String param2)throws HarnessException  {
		return this.call(coreFunction, locator, action, retryOnFalse, panel, param1, param2, "");
	}	
	public String call(String coreFunction, String locator, String action, Boolean retryOnFalse,
			String panel, String param1, String param2, String param3)
			throws HarnessException {

		// indicates that the actual object name must start with required obj
		// name(js function should take care of resolving
		if (SelNGBase.labelStartsWith)
			locator = locator + "::labelStartsWith";
		// if we have a field whose label is an object(say a button/menu), then
		// set this to true
		// ex: [menuLabel][editfield] where menuLabel is the label for editField
		if (SelNGBase.fieldLabelIsAnObject)
			locator = locator + "::fieldLabelIsAnObject";
			
		if (SelNGBase.ignoreFolderHdr)
			locator = locator + "::ignoreFolderHdr";
		
		if (SelNGBase.actOnLabel)// set this to true if you want to click on the
			// exact label-element
			locator = locator + "::actOnLabel";
		
		String jsFunc = "this." 
			+ coreFunction + "("  + doubleQuote(locator) + ","
			+ doubleQuote(action) + "," + doubleQuote(panel) + ","
			+ doubleQuote(param1) + "," + doubleQuote(param2) + ","
			+ doubleQuote(param3) + ")";
	
		String retval = this.getEval(jsFunc);
		
		if (retryOnFalse){
			Integer second = 0;
			while(second < 10 && retval.equals("false")){
				SleepUtil.sleep(1000);
				retval = this.getEval(jsFunc);
				second++;
			}
		}
		logger.info("js >> " + jsFunc);
		logger.info("js >> " + retval + "\n");
		
		
		if (retval.equals("false")) {

			throw new HarnessException("js function: " + jsFunc + " returns false");

		}


		return retval;
	}

	public void setupZVariables()
			throws SeleniumException {
		commandProcessor.doCommand("setupZVariables", null);
		
	}
	
	private String doubleQuote(String str) {
		return doubleQuote + str + doubleQuote;
	}


}
