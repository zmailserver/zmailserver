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
package org.zmail.qa.selenium.projects.ajax.ui;

import org.zmail.qa.selenium.framework.ui.*;

/**
 * A <code>DialogError</code> object represents a "Error" dialog, such as "Permission 
 * denied", etc.
 * <p>
 * During construction, the div ID attribute must be specified, such as "Zmail".
 * <p>
 * @author Matt Rhoades
 *
 */
public class DialogError extends DialogWarning {

	public static class DialogErrorID extends DialogWarning.DialogWarningID {

		/**
		 * General "Zmail" server error (such as PERM_DENIED, etc.)
		 * See: https://bugzilla.zmail.com/show_bug.cgi?id=57207
		 */
		public static final DialogErrorID Zmail = new DialogErrorID("ErrorDialog");
		public static final DialogErrorID InvalidFolderName = new DialogErrorID("ZmMsgDialog");;

		/**
		 * http://wiki.zmail.com/wiki/File:ZmailSeleniumScreenshotPopups1.jpeg
		 */
		public static DialogErrorID ZmMsgDialog = new DialogErrorID("ZmMsgDialog");

		protected DialogErrorID(String id) {
			super(id);
		}
		
	}

	public DialogError(DialogErrorID dialogId, AbsApplication application, AbsTab tab) {
		super(dialogId, application, tab);
	}

}
