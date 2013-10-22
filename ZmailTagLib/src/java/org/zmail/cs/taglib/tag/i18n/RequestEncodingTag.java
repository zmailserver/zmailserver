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

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class RequestEncodingTag extends SimpleTagSupport  {

	//
	// Data
	//

	protected String value;

	//
	// Public methods
	//

	public void setValue(String value) {
		this.value = value;
	}

	//
	// SimpleTag methods
	//

	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext)getJspContext();

		String encoding = this.value;
		if (encoding == null) encoding = pageContext.getResponse().getCharacterEncoding();
		pageContext.getRequest().setCharacterEncoding(encoding);

		// clear state
		this.value = null;
	}

} // class RequestEncodingTag