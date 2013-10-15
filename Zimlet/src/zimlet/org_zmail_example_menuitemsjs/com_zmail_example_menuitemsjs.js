/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */

/**
 * Defines the Zimlet handler class.
 *   
 */
function org_zmail_example_menuitemsjs_HandlerObject() {
}

/**
 * Makes the Zimlet class a subclass of ZmZimletBase.
 *
 */
org_zmail_example_menuitemsjs_HandlerObject.prototype = new ZmZimletBase();
org_zmail_example_menuitemsjs_HandlerObject.prototype.constructor = org_zmail_example_menuitemsjs_HandlerObject;

/**
 * This method gets called by the Zimlet framework when the zimlet loads.
 *  
 */
org_zmail_example_menuitemsjs_HandlerObject.prototype.init =
function() {
	// do something
};

/**
 * This method gets called by the Zimlet framework when a context menu item is selected.
 * 
 * @param	itemId		the Id of selected menu item
 */
org_zmail_example_menuitemsjs_HandlerObject.prototype.menuItemSelected =
function(itemId) {
	switch (itemId) {
		case "SOME_MENU_ITEM_ID1":
			window.open ("http://www.yahoo.com",
					"mywindow","menubar=1,resizable=1,width=800,height=600"); 
			break;
		case "SOME_MENU_ITEM_ID2":
			window.open ("http://sports.yahoo.com",
					"mywindow","menubar=1,resizable=1,width=800,height=600"); 
			break;
		default:
			// do nothing
			break;
	}

};
