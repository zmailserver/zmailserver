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
package org.zmail.qa.selenium.framework.ui;

import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.qa.selenium.framework.util.HarnessException;


/**
 * A class that contains Zmail translations
 * @author Matt Rhoades
 *
 */
public class I18N {
	private static Logger logger = LogManager.getLogger(I18N.class);
	
	/*
	 * See http://wiki.zmail.com/wiki/Translations
	 */
	
    // I18nMsg.properties:	Internationalization messages such as names of months, date and time formats, etc. 
	// AjxMsg.properties:	Messages used by the ajax toolkit. 
	// ZMsg.properties:		Common messages such as server error messages, etc. 
	// ZaMsg.properties:	Messages used by the Zmail Admin web client. 
	// ZmMsg.properties:	Messages used by the Zmail End User web client. 
	// ZhMsg.properties:	Messages used by the Zmail End User basic web client. 
	// ZsMsg.properties:	Messages used by the Zmail Server when automatically replying to appointment requests for locations and resources. (The language used for the outgoing messages is based on the server's default locale, not the client.) 

	public enum Catalog {
		I18nMsg, AjxMsg, ZMsg, ZaMsg, ZhMsg, ZmMsg, ZsMsg
	}
	
    public static final String CONTEXT_MENU_ITEM_NEW_FOLDER = "New Folder"; // TODO: ZmMsg: key: newFolder
    public static final String CONTEXT_MENU_ITEM_MARK_ALL_AS_READ = "Mark All as Read";
    public static final String CONTEXT_MENU_ITEM_DELETE = "Delete"; // TODO: ZMsg: key: del (?)
    public static final String CONTEXT_MENU_ITEM_RENAME_FOLDER = "Rename Folder";
    public static final String CONTEXT_MENU_ITEM_MOVE = "Move";
    public static final String CONTEXT_MENU_ITEM_SHARE_FOLDER = "Share Folder";
    public static final String CONTEXT_MENU_ITEM_EDIT_PROPERTIES = "Edit Properties";
    public static final String CONTEXT_MENU_ITEM_EXPAND_ALL = "Expand All";
    public static final String CONTEXT_MENU_ITEM_EMPTY_FOLDER = "Empty Folder";
    public static final String CONTEXT_MENU_ITEM_TURN_SYNC_OFF = "Turn sync off";
    public static final String CONTEXT_MENU_ITEM_SEND_RECEIVE = "Send/Receive";
    public static final String FORMAT_AS_PLAIN_TEXT= "Format As Plain Text";
    public static final String FORMAT_AS_HTML_TEXT= "Format As HTML";
    public static final String NEW_SIGNATURE= "New Signature";
    public static final String DELETE= "Delete";
    

	/**
	 * My current Locale
	 */
	protected Locale currentLocale = Locale.getDefault();
	
	/**
	 * A mapping of msg filename to resource bundles
	 */
	protected Map<String, ResourceBundle> bundles = new HashMap<String, ResourceBundle>();
	
	/**
	 * 
	 * @param locale
	 */
	public I18N() {
	}
	
	public Locale getLocale() {
		return (currentLocale);
	}
	
	public void setLocale(Locale locale) {
		
		currentLocale = locale;

		// Clear the bundles, so that the mapping is rebuilt the next time a key/value is requested
		for (Map.Entry<String, ResourceBundle> entry : bundles.entrySet()) {
			logger.info("bundle: set value to null for "+ entry.getKey());
			entry.setValue(null);
		}

	}
	
	public void zAddBundlename(Catalog catalog) {
		zAddBundlename(catalog.toString());
	}
	
	public void zAddBundlename(String catalog) {
		if ( !bundles.containsKey(catalog) ) {
			
			// Remember the new bundlename
			logger.info("bundle: intialize value to null for "+ catalog);
			bundles.put(catalog, null);

		}
	}

	/**
	 * Get the translation for the given key using the given bundle id
	 * @param bundlename
	 * @param key
	 * @return
	 * @throws HarnessException 
	 */
	public String zGetStringFromBundle(String catalog, String key) {
		
		if ( catalog == null )
			throw new NullPointerException("catalog was null");
		if ( key == null )
			throw new NullPointerException("key was null");

		// If any bundles are null, load them now
		checkBundles();
		
		// Get the specified bundle
		ResourceBundle bundle = bundles.get(catalog);
		if ( bundle == null )
			throw new NullPointerException("bundle was null");
		
		// Check if the bundle has the key
		if ( !bundle.containsKey(key) ) {
			return (null);
		}
		
		// Get the value from the bundle
		String value = bundle.getString(key);
		
		logger.info(String.format("Localization: %s (key, value) = (%s, %s)", catalog, key, value));
		
		return (value);
		
	}
	
	/**
	 * Get the first matching translation for the given key
	 * @param key
	 * @return
	 * @throws HarnessException
	 */
	public String zGetString(String key) {
				
		// If any bundles are null, load them now
		checkBundles();
		
		List<String> values = new ArrayList<String>();
		
		for (String catalog : bundles.keySet()) {
			String value = zGetStringFromBundle(catalog, key);
			if ( value != null )
				values.add(value);
		}
		
		if ( values.size() == 0) {
			logger.error("No match for key: "+ key);
			return (null);
		}
		
		if ( values.size() > 1) {
			logger.warn("Multiple bundles matched key: "+ key);
		}
		
		logger.info(String.format("Localization: (key, value) = (%s, %s)", key, values.get(0)));
		return (values.get(0));
		
	}
	
	
	

    /**
     * Rebuild bundles to include all bundles from bundlename, using the currentLocale
     */
    protected void checkBundles() {
    	
		// Rebuild any bundle that is null
    	for (Map.Entry<String, ResourceBundle> entry : bundles.entrySet()) {
    		if ( entry.getValue() == null ) {
    			entry.setValue(ResourceBundle.getBundle(entry.getKey(), currentLocale));
    		}
    	}

    }
    
    /**
     * http://www.java2s.com/Code/Java/Network-Protocol/GetLocaleFromString.htm
     * Convert a string based locale into a Locale Object.
     * Assumes the string has form "{language}_{country}_{variant}".
     * Examples: "en", "de_DE", "_GB", "en_US_WIN", "de__POSIX", "fr_MAC"
     *  
     * @param localeString The String
     * @return the Locale
     */
    public static Locale getLocaleFromString(String localeString)
    {
        if (localeString == null)
        {
            return null;
        }
        localeString = localeString.trim();
        if (localeString.toLowerCase().equals("default"))
        {
            return Locale.getDefault();
        }

        // Extract language
        int languageIndex = localeString.indexOf('_');
        String language = null;
        if (languageIndex == -1)
        {
            // No further "_" so is "{language}" only
            return new Locale(localeString, "");
        }
        else
        {
            language = localeString.substring(0, languageIndex);
        }

        // Extract country
        int countryIndex = localeString.indexOf('_', languageIndex + 1);
        String country = null;
        if (countryIndex == -1)
        {
            // No further "_" so is "{language}_{country}"
            country = localeString.substring(languageIndex+1);
            return new Locale(language, country);
        }
        else
        {
            // Assume all remaining is the variant so is "{language}_{country}_{variant}"
            country = localeString.substring(languageIndex+1, countryIndex);
            String variant = localeString.substring(countryIndex+1);
            return new Locale(language, country, variant);
        }
    }


}
