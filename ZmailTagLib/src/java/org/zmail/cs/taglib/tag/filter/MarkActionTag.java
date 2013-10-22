/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.taglib.tag.filter;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.taglib.tag.ZimbraSimpleTag;
import com.zimbra.client.ZFilterAction.MarkOp;
import com.zimbra.client.ZFilterAction.ZMarkAction;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public final class MarkActionTag extends ZimbraSimpleTag {

    private MarkOp op;

    public void setOp(String op) throws ServiceException {
        this.op = MarkOp.fromString(op);
    }

    @Override
    public void doTag() throws JspException {
        FilterRuleTag rule = (FilterRuleTag) findAncestorWithClass(this, FilterRuleTag.class);
        if (rule == null) {
            throw new JspTagException("The markAction tag must be used within a filterRule tag");
        }
        rule.addAction(new ZMarkAction(op));
    }

}
