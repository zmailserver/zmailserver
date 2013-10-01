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
package com.zimbra.qa.selenium.framework.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.util.HarnessException;


/**
 * A <code>AbsTree</code> object represents a "tree panel", 
 * such as a Folder tree, Addressbook tree, Calendar tree, etc.
 * <p>
 * 
 * @author Matt Rhoades
 *
 */
public abstract class AbsTree extends AbsPage {
	protected static Logger logger = LogManager.getLogger(AbsTree.class);

	public static final String zNewTagIcon = "css=td[class='overviewHeader-Text FakeAnchor']>div[class^=ImgNewTag]";


	/**
	 * Create this page object that exists in the specified application
	 * @param application
	 */
	public AbsTree(AbsApplication application) {
		super(application);
		
		logger.info("new AbsTree");
	}
	
	/**
	 * Click on a button
	 * @param button
	 * @return
	 * @throws HarnessException
	 */
	public abstract AbsPage zPressButton(Button button) throws HarnessException;

	/**
	 * Apply the specified action on the specified item
	 * @param action
	 * @param addressbook
	 * @return
	 * @throws HarnessException
	 */
	public abstract AbsPage zTreeItem(Action action, IItem item) throws HarnessException;

	/**
	 * Apply the specified action with option on the specified item
	 * <p>
	 * For example, use this method to take an action using the context method.  The
	 * Action is Action.A_LEFTCLICK and the Button would be the context menu item, such
	 * as Button.B_DELETE
	 * <p>
	 * @param action
	 * @param addressbook
	 * @return
	 * @throws HarnessException
	 */
	public abstract AbsPage zTreeItem(Action action, Button option, IItem item) throws HarnessException;


	/**
	 * Return the unique name for this page class
	 * @return
	 */
	public abstract String myPageName();
	
	
}
