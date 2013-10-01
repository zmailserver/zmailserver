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
package com.zimbra.cs.filter;

import java.util.List;

import org.apache.jsieve.parser.generated.Node;

import com.zimbra.common.localconfig.LC;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.Element.ElementFactory;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.mailbox.Mailbox;

class RuleRewriterFactory {
	
	private static RuleRewriterFactory instance = null;	
	
    synchronized static RuleRewriterFactory getInstance() {
		if (instance == null) {
	        String className = LC.zimbra_class_rulerewriterfactory.value();
	        if (className != null && !className.equals("")) {
	            try {
	                instance = (RuleRewriterFactory) Class.forName(className).newInstance();
	            } catch (Exception e) {
	                ZimbraLog.filter.error("could not instantiate RuleRewriterFactory interface of class '" + className + "'; defaulting to RuleRewriterFactory", e);
	            }
	        }
	        if (instance == null)
	            instance = new RuleRewriterFactory();
		}
        return instance;
    }
    
    RuleRewriter createRuleRewriter() {
    	return new RuleRewriter();
    }
    
    RuleRewriter createRuleRewriter(ElementFactory factory, Node node, List<String> ruleNames) {
    	RuleRewriter rrw = createRuleRewriter();
    	rrw.initialize(factory, node, ruleNames);
    	return rrw;
    }
    
    RuleRewriter createRuleRewriter(Element eltRules, Mailbox mbox) {
     	RuleRewriter rrw = createRuleRewriter();
    	rrw.initialize(eltRules, mbox);
    	return rrw;
    }
}
