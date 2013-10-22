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

import com.zimbra.cs.taglib.tag.ZimbraSimpleTag;
import com.zimbra.client.ZFilterAction;
import com.zimbra.client.ZFilterCondition;
import com.zimbra.client.ZFilterRule;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterRuleTag extends ZimbraSimpleTag {

    protected boolean mActive;
    protected boolean mAll;
    protected String mName;
    protected String mVar;
    protected List<ZFilterCondition> mConditions = new ArrayList<ZFilterCondition>();
    protected List<ZFilterAction> mActions = new ArrayList<ZFilterAction>();

    public void setActive(boolean active) { mActive = active; }

    public void setAllconditions(boolean all) { mAll = all; }

    public void setName(String name) { mName = name; }

    public void setVar(String var) {  mVar = var; }

    public void addCondition(ZFilterCondition condition) throws JspTagException {
        mConditions.add(condition);
    }

    public void addAction(ZFilterAction action) throws JspTagException {
        mActions.add(action);
    }

    public void doTag() throws JspException, IOException {
        getJspBody().invoke(null);
        JspContext jctxt = getJspContext();
        ZFilterRule rule = new ZFilterRule(mName, mActive, mAll, mConditions, mActions);
        jctxt.setAttribute(mVar, rule,  PageContext.PAGE_SCOPE);
    }
}
