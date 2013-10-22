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

package com.zimbra.cs.taglib.tag.i18n;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ParamTag extends BodyTagSupport {

	//
	// Data
	//

	protected Object value;

	//
	// Public methods
	//

	public void setValue(Object value) {
		this.value = value;
	}

	//
	// TagSupport methods
	//

	public int doEndTag() throws JspException {
		MessageTag messageTag = (MessageTag)findAncestorWithClass(this, MessageTag.class);
		if (messageTag != null) {
			Object value = this.value;
			if (value == null) {
				BodyContent bodyContent = getBodyContent();
				value = I18nUtil.evaluate(pageContext, bodyContent.getString(), Object.class);
			}
			messageTag.addParam(value);
		}
		// clear state
		this.value= null;
		// process page
		return EVAL_PAGE;
	}

} // class ParamTag