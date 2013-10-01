/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.mail.message;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.mail.type.FilterRule;
import com.zimbra.soap.json.jackson.annotate.ZimbraJsonArrayForWrapper;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_GET_OUTGOING_FILTER_RULES_RESPONSE)
public final class GetOutgoingFilterRulesResponse {

    /**
     * @zm-api-field-description Filter rules
     */
    @ZimbraJsonArrayForWrapper
    @XmlElementWrapper(name=MailConstants.E_FILTER_RULES /* filterRules */, required=true)
    @XmlElement(name=MailConstants.E_FILTER_RULE /* filterRule */, required=false)
    private final List<FilterRule> rules = Lists.newArrayList();

    public GetOutgoingFilterRulesResponse() {
    }

    public void setFilterRules(Collection<FilterRule> list) {
        rules.clear();
        if (list != null) {
            rules.addAll(list);
        }
    }

    public GetOutgoingFilterRulesResponse addFilterRule(FilterRule rule) {
        rules.add(rule);
        return this;
    }

    public GetOutgoingFilterRulesResponse addFilterRule(Collection<FilterRule> list) {
        rules.addAll(list);
        return this;
    }

    public List<FilterRule> getFilterRules() {
        return Collections.unmodifiableList(rules);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("rules", rules).toString();
    }
}
