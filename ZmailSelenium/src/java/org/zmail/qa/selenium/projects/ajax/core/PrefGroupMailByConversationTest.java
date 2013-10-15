/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.qa.selenium.projects.ajax.core;

import java.util.HashMap;

/**
 * A base class that sets
 *  1) the starting page to be the mail app,
 *  2) sets zmailPrefGroupMailBy=message, and
 *  3) sets zmailPrefMessageViewHtmlPreferred=TRUE
 *  
 * @author Matt Rhoades
 *
 */
public class PrefGroupMailByConversationTest extends AjaxCommonTest {

	public PrefGroupMailByConversationTest() {
		
		super.startingPage = app.zPageMail;

		super.startingAccountPreferences = new HashMap<String, String>() {
			private static final long serialVersionUID = -8033517997031535379L;
		{
			put("zmailPrefGroupMailBy", "conversation");
			put("zmailPrefMessageViewHtmlPreferred", "TRUE");
		}};

	}
	
}
