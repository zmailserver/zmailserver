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
/**
 * 
 */
package org.zmail.qa.selenium.framework.items;

import java.util.Locale;

import org.apache.log4j.*;

import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;


/**
 * @author Matt Rhoades
 *
 */
public class ZimletItem  {
	protected static Logger logger = LogManager.getLogger(IItem.class);

	protected boolean gPreferencesEnabled = false;
	protected String gPreferencesName = null;
	protected String gPreferencesDescription = null;
	
	protected String gFolderTreeName = null;
	protected String gFolderTreeImage = null;
	protected String gFolderTreeLocator = null;
	
	/**
	 * Create a new ZimletItem object
	 */
	public ZimletItem() {
	}

	


	/**
	 * Return the 'display' string for the specified localization key.
	 * <p>
	 * The harness uses the zimlet name to look-up the translation properties file
	 * @param key
	 * @return
	 */
	public String getTranslation(String key) {
		return (null);
	}
	
	public void setPreferencesEnabled(boolean enabled) {
		gPreferencesEnabled = enabled;
	}
	
	public boolean getPreferencesEnabled() {
		return (gPreferencesEnabled);
	}
	
	public void setPreferencesName(String name) {
		gPreferencesName = name;
	}
	
	public String getPreferencesName() {
		return (gPreferencesName);
	}
	
	public void setPreferencesDescription(String description) {
		gPreferencesDescription = description;
	}
	
	public String getPreferencesDescription() {
		return (gPreferencesDescription);
	}
	
	public void setFolderTreeName(String name) {
		gFolderTreeName = name;
	}
	
	public String getFolderTreeName() {
		return (gFolderTreeName);
	}

	public void setFolderTreeImage(String image) {
		gFolderTreeImage = image;
	}
	
	public String getFolderTreeImage() {
		return (gFolderTreeImage);
	}

	public void setFolderTreeLocator(String locator) {
		gFolderTreeLocator = locator;
	}

	public String getFolderTreeLocator() {
		return (gFolderTreeLocator);
	}
	
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(ZimletItem.class.getSimpleName()).append('\n');
		sb.append("Name: ").append(gPreferencesName).append('\n');
		sb.append("Description: ").append(gPreferencesDescription).append('\n');
		sb.append("Enabled: ").append(gPreferencesEnabled).append('\n');
		return (sb.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gFolderTreeName == null) ? 0 : gFolderTreeName.hashCode());
		result = prime
				* result
				+ ((gPreferencesName == null) ? 0 : gPreferencesName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
//		if (getClass() != obj.getClass())
//			return false;
		if ( !(obj instanceof ZimletItem) )
			return false;
		
		ZimletItem other = (ZimletItem) obj;
		
		String thisFolderTreeName = this.getFolderTreeName();
		String thisPreferencesName = this.getPreferencesName();
		String otherFolderTreeName = other.getFolderTreeName();
		String otherPreferencesName = other.getPreferencesName();
		
		// If folder name are equal OR If preference name are equal, then objects are equal
		
		if ((otherFolderTreeName == null) && (otherPreferencesName == null) )
			return false; // Nothing to compare

		if ((thisFolderTreeName == null) && (thisPreferencesName == null) )
			return false; // Nothing to compare
			
		if (thisFolderTreeName != null)
			return (thisFolderTreeName.equals(otherFolderTreeName));
			
		if (thisPreferencesName != null)
			return (thisPreferencesName.equals(otherPreferencesName));
			
		// Log a warning with a stack trace
		logger.warn("How did we get here?", new Throwable());
		return (false);
	
	}

	public static class CoreZimletItem extends ZimletItem {

		public enum CoreZimletName {
			org_zmail_attachcontacts,
			org_zmail_attachmail,
			org_zmail_date,
			org_zmail_dnd,
			org_zmail_email,
			org_zmail_linkedin,
			org_zmail_phone,
			org_zmail_social,
			org_zmail_srchhighlighter,
			org_zmail_url,
			org_zmail_webex
		}
		
		
		/**
		 * Return the core ZimletItem
		 * @param name
		 * @param app
		 * @return
		 */
		public static CoreZimletItem getCoreZimlet(CoreZimletName name, AbsApplication app) {
			CoreZimletItem zimlet = new CoreZimletItem(name);
			zimlet.setLocale(app);
			return (zimlet);
		}

		/**
		 * The zimlet name, such as org_zmail_email
		 */
		protected CoreZimletName zName = null;
		
		/**
		 * An object containing the localized strings
		 */
		protected I18N L10N = new I18N();
		

		protected CoreZimletItem(CoreZimletName name) {
			logger.info("new " + CoreZimletItem.class.getCanonicalName());
			
			zName = name;
			L10N.zAddBundlename(zName.toString());
			setLocale(Locale.ENGLISH); // Default
		}

		protected void setLocale(Locale locale) {
			logger.info("Zimlet: setLocale("+ locale +")");
			L10N.setLocale(locale);
		}
		
		protected void setLocale(ZmailAccount account) {
			logger.info("Zimlet: setLocale("+ account.EmailAddress +")");
			setLocale(account.getLocalePreference());
		}
		
		protected void setLocale(AbsApplication app) {
			logger.info("Zimlet: setLocale("+ app.myApplicationName() +")");
			setLocale(app.zGetActiveAccount());
		}
		
		public String getPreferencesName() {
			return (L10N.zGetString("label"));
		}

		public String getPreferencesDescription() {
			return (L10N.zGetString("description"));
		}
		
		public String getFolderTreeName() {
			String key = "label";
			
			if ( zName.equals(CoreZimletName.org_zmail_webex) )
				key = "WebExZimlet_label"; // Special case for the webex zimlet
			
			return (L10N.zGetString(key));
		}

		public String prettyPrint() {
			StringBuilder sb = new StringBuilder();
			sb.append(ZimletItem.class.getSimpleName()).append('\n');
			sb.append("ID: ").append(zName).append('\n');
			sb.append("Name: ").append(getPreferencesName()).append('\n');
			sb.append("Description: ").append("unknown").append('\n');
			return (sb.toString());
		}




	}

    
    
}
