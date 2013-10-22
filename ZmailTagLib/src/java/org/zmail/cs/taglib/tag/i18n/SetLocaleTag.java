/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

package org.zmail.cs.taglib.tag.i18n;

import org.zmail.cs.taglib.tag.i18n.I18nUtil;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class SetLocaleTag extends SimpleTagSupport  {

	//
	// Data
	//

	protected String value;
	protected String variant;
	protected int scope = I18nUtil.DEFAULT_SCOPE_VALUE;

	//
	// Public methods
	//

	public void setValue(String value) {
		this.value = value;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public void setScope(String scope) {
		this.scope = I18nUtil.getScope(scope);
	}

	//
	// SimpleTag methods
	//

	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext)getJspContext();
		Locale locale = I18nUtil.getLocale(this.value);
		pageContext.setAttribute(I18nUtil.DEFAULT_LOCALE_VAR, locale, this.scope);
		pageContext.getResponse().setLocale(locale);
	}

} // class SetLocaleTag