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
package org.zmail.qa.selenium.framework.core;

import java.net.URL;
import static org.openqa.selenium.firefox.FirefoxDriver.PROFILE;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;


/**
 * A <code>ClientSession</code> object contains all session information for the test methods.
 * <p>
 * The Zmail Selenium harness is designed to  
 * execute test cases concurrently at the class level.
 * 
 * The {@link ClientSession} objects maintain all session information on 
 * a per thread basis, such as the current DefaultSelenium object.  Each 
 * TestNG thread uses a single {@link ClientSession} Object.
 * <p>
 * Use the {@link ClientSessionFactory} to retrieve the current {@link ClientSession}.
 * <p>
 * 
 * @author Matt Rhoades
 *
 */
public class ClientSession {
	public static final String IE9="MSIE 9";
	public static final String IE8="MSIE 8";
	
	private static Logger logger = LogManager.getLogger(ClientSession.class);
	
	private String name;	// A unique string identifying this session
	
	private ZmailSelenium selenium = null;
	private WebDriver webDriver = null;
	private WebDriverBackedSelenium webDriverBackedSelenium = null;
	
	private String applicationURL = ZmailSeleniumProperties.getStringProperty("server.scheme", "http") 
	+ "://" + ZmailSeleniumProperties.getStringProperty("server.host", "localhost"); 
	private ZmailAccount currentAccount = null;

	protected ClientSession() {
		logger.info("New ClientSession");
		
		name = "ClientSession-" + Thread.currentThread().getName();
		
	}
	
	/**
	 * Get the current ZmailSelenium (DefaultSelenium) object
	 * <p>
	 * @return
	 */
	public ZmailSelenium selenium() {
		if ( selenium == null ) {
			selenium = new ZmailSelenium(
							SeleniumService.getInstance().getSeleniumServer(), 
							SeleniumService.getInstance().getSeleniumPort(),
							SeleniumService.getInstance().getSeleniumBrowser(), 
							applicationURL);
		}
		return (selenium);
	}
	
	/**
	 * Get the current WebDriverBackedSelenium object
	 * <p>
	 * 
	 * @return
	 */
	public WebDriverBackedSelenium webDriverBackedSelenium() {
		if (webDriverBackedSelenium == null) {
			if(ZmailSeleniumProperties.getStringProperty("browser").contains("googlechrome")){
				webDriverBackedSelenium = new WebDriverBackedSelenium(new ChromeDriver(), applicationURL);
			}else{
				FirefoxProfile profile = new FirefoxProfile();
				profile.setEnableNativeEvents(false);
				webDriverBackedSelenium = new WebDriverBackedSelenium(new FirefoxDriver(profile), applicationURL);
			}
		}
		return webDriverBackedSelenium;
	}
	
	/**
	 * Get the current WebDriver object
	 * <p>
	 * 
	 * @return
	 */
	public WebDriver webDriver() {
		if (webDriver == null) {			
			if(ZmailSeleniumProperties.getStringProperty("browser").contains("iexplore")){	
				DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
				//desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
				webDriver = new InternetExplorerDriver(desiredCapabilities);	
			}
			else if(ZmailSeleniumProperties.getStringProperty("browser").contains("googlechrome")){
				//DesiredCapabilities caps = DesiredCapabilities.chrome();
				//caps.setJavascriptEnabled(true);
				//caps.setCapability("chrome.binary", "path/to/chrome.exe");
				//System.setProperty("webdriver.chrome.driver","/path/to/chromedriver.exe");
				//ChromeDriver driver = new ChromeDriver(caps);
				
				ChromeOptions options = new ChromeOptions();
				String chromedriverPath = null;
				if((chromedriverPath = ZmailSeleniumProperties.getStringProperty("chromedriver.path"))!=null){
					System.setProperty("webdriver.chrome.driver",chromedriverPath);
				}
				webDriver = new ChromeDriver(options);
				//webDriver = new ChromeDriver();
			} else if (ZmailSeleniumProperties.getStringProperty("browser").contains("firefox")){
				FirefoxProfile profile = new FirefoxProfile();
				//Proxy proxy = new Proxy();
				//proxy.setHttpProxy("proxy.vmware.com:3128");
				//profile.setProxyPreferences(proxy);
				//profile.addExtension(....);
				profile.setEnableNativeEvents(false);
				webDriver = new FirefoxDriver(profile);
				//webDriver = new FirefoxDriver();					
			} else if(ZmailSeleniumProperties.getStringProperty("browser").contains("remoteff")){
				try {
					DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
					desiredCapabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
					FirefoxProfile fp = new FirefoxProfile();
					fp.setEnableNativeEvents(false);
					desiredCapabilities.setCapability(PROFILE,fp);
					desiredCapabilities.setJavascriptEnabled(true);
					webDriver = new RemoteWebDriver(new URL(String.format("http://localhost:%d/wd/hub", 4444)), desiredCapabilities);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}else if(ZmailSeleniumProperties.getStringProperty("browser").contains("remotechrome")){
				try {
					DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
					desiredCapabilities.setJavascriptEnabled(true);
					webDriver = new RemoteWebDriver(new URL(String.format("http://localhost:%d/wd/hub", 4444)), desiredCapabilities);
				} catch (Exception ex) {
						logger.error(ex);					
				}					
			}else if(ZmailSeleniumProperties.getStringProperty("browser").contains("remoteie")){
				try {
					DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
					desiredCapabilities.setJavascriptEnabled(true);
					webDriver = new RemoteWebDriver(new URL(String.format("http://localhost:%d/wd/hub", 4444)), desiredCapabilities);
				} catch (Exception ex) {
					logger.error(ex);					
				}					
			}else{
				// default FirefoxDriver
				FirefoxProfile profile = new FirefoxProfile();
				profile.setEnableNativeEvents(false);
				profile.setPreference("javascript.enabled", true);
				webDriver = new FirefoxDriver(profile);
			}
		}	
		
		return webDriver;
	}	
	
	/**
	 * Get the current Browser Name
	 * <p>
	 * @return
	 */
	@Deprecated()
	public String currentBrowserName() {
		return (ClientSessionFactory.session().selenium().getEval("navigator.userAgent;"));
	}

	/**
	 * Get the currently logged in user name
	 * <p>
	 * @return
	 */
	public String currentUserName() {
		if ( currentAccount == null ) {
			return ("");
		}
		return (currentAccount.EmailAddress);
	}
	
	/**
	 * NOT FOR TEST CASE USE.  Set the currently logged in user name.
	 * <p>
	 * This method should only be used by the AppPage LoginPage object.
	 * <p>
	 * TODO: once projects.zcs.* and projects.html.* are converted to this
	 * mechanism, need to make this method "protected" rather than "public"
	 * <p>
	 * @param account
	 * @return
	 */
	public String setCurrentUser(ZmailAccount account) {
		currentAccount = account;
		return (currentUserName());
	}
	
	/**
	 * A unique string ID for this ClientSession object
	 */
	public String toString() {
		logger.debug("ClientSession.toString()="+ name);
		return (name);
	}

	
		

}
