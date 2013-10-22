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

package com.zimbra.soap.admin.type;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.base.ByMinuteRuleInterface;

@XmlAccessorType(XmlAccessType.NONE)
public class ByMinuteRule implements ByMinuteRuleInterface {

    /**
     * @zm-api-field-tag minute-list
     * @zm-api-field-description Comma separated list of minutes where minute is a number between 0 and 59
     */
    @XmlAttribute(name=MailConstants.A_CAL_RULE_BYMINUTE_MINLIST, required=true)
    private final String list;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private ByMinuteRule() {
        this((String) null);
    }

    public ByMinuteRule(String list) {
        this.list = list;
    }

    @Override
    public ByMinuteRuleInterface create(String list) {
        return new ByMinuteRule(list);
    }

    @Override
    public String getList() { return list; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("list", list)
            .toString();
    }
}
