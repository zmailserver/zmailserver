/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.mail;

import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.filter.RuleManager;
import org.dom4j.QName;

/**
 */
public class ApplyOutgoingFilterRules extends ApplyFilterRules {

    @Override
    protected String getRules(Account account) {
        return RuleManager.getOutgoingRules(account);
    }

    @Override
    protected QName getResponseElementName() {
        return MailConstants.APPLY_OUTGOING_FILTER_RULES_RESPONSE;
    }
}
