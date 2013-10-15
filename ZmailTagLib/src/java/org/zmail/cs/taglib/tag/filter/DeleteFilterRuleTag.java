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
package org.zmail.cs.taglib.tag.filter;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.taglib.tag.ZmailSimpleTag;
import org.zmail.cs.taglib.bean.ZTagLibException;
import org.zmail.client.ZFilterRule;
import org.zmail.client.ZFilterRules;
import org.zmail.client.ZMailbox;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteFilterRuleTag extends ZmailSimpleTag {

    private String mName;

    public void setName(String name) { mName = name; }

    public void doTag() throws JspException, IOException {
        try {
            ZMailbox mbox = getMailbox();
            ZFilterRules rules = mbox.getIncomingFilterRules(true);
            List<ZFilterRule> newRules = new ArrayList<ZFilterRule>();
            boolean found = false;
            for (ZFilterRule rule: rules.getRules()) {
                String ruleName = StringUtil.escapeHtml(rule.getName());
                if (ruleName.equalsIgnoreCase(mName)) {
                    found = true;
                } else {
                    newRules.add(rule);
                }
            }
            if (!found)
                throw ZTagLibException.NO_SUCH_FILTER_EXISTS("filter with name "+mName+" doesn't exist", null);
            mbox.saveIncomingFilterRules(new ZFilterRules(newRules));
        } catch (ServiceException e) {
            throw new JspTagException(e);
        }
    }
}
