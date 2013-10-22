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



/**
 * The <code>Action</code> class defines constants that represent
 * general actions in the client apps.
 * <p>
 * This class defines constants for
 * <ol>
 * <li>Mouse actions, such as click or ctrl-click</li>
 * <li>Zmail object actions, such as check a checkbox or flag a mail</li>
 * </ol>
 * <p>
 * Action constant names start with "A_" and take the general format
 * <code>A_PAGE_ACTION</code>,
 * where "Page" is the application name such as MAIL, ADDRESSBOOK and
 * "Action" is the general description of the action.  For non-page
 * specific Actions, "Page" is not specified.
 * <p>
 * The action constants can be used in page methods, for example:
 * <pre>
 * {@code
 * String subject = "foo123";
 * app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
 * }
 * </pre>
 * <p>
 * 
 * @author Matt Rhoades
 *
 */
public class Action {

	// General actions
	public static final Action A_LEFTCLICK = new Action("A_LEFTCLICK");
	public static final Action A_SHIFTSELECT = new Action("A_SHIFTSELECT");
	public static final Action A_CTRLSELECT = new Action("A_CTRLSELECT");
	public static final Action A_RIGHTCLICK = new Action("A_RIGHTCLICK");
	public static final Action A_DOUBLECLICK = new Action("A_DOUBLECLICK");
	public static final Action A_CHECKBOX = new Action("A_CHECKBOX");
	public static final Action A_UNCHECKBOX = new Action("A_UNCHECKBOX");

	// Mail page actions
	public static final Action A_MAIL_CHECKBOX = new Action("A_MAIL_CHECKBOX");
	public static final Action A_MAIL_UNCHECKBOX = new Action("A_MAIL_UNCHECKBOX");
	public static final Action A_MAIL_FLAG = new Action("A_MAIL_FLAG");
	public static final Action A_MAIL_UNFLAG = new Action("A_MAIL_UNFLAG");
	public static final Action A_MAIL_EXPANDCONVERSATION = new Action("A_MAIL_EXPANDCONVERSATION");
	public static final Action A_MAIL_COLLAPSECONVERSATION = new Action("A_MAIL_COLLAPSECONVERSATION");
	
	// Tree actions
	public static final Action A_TREE_EXPAND = new Action("A_TREE_EXPAND");
	public static final Action A_TREE_COLLAPSE = new Action("A_TREE_COLLAPSE");
	public static final Action A_TREE_CHECKBOX = new Action("A_TREE_CHECKBOX");

	// Briefcase page actions
	public static final Action A_BRIEFCASE_CHECKBOX = new Action("A_BRIEFCASE_CHECKBOX");
	public static final Action A_BRIEFCASE_HEADER_CHECKBOX = new Action("A_BRIEFCASE_HEADER_CHECKBOX");

	private final String ID;
	
	protected Action(String id) {
		this.ID = id;
	}

	@Override
	public String toString() {
		return ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

}
