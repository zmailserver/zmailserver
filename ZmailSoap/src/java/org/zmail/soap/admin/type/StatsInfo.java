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

package org.zmail.soap.admin.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.zmail.common.soap.AdminConstants;

@XmlAccessorType(XmlAccessType.NONE)
public class StatsInfo {

    /**
     * @zm-api-field-tag stat-name
     * @zm-api-field-description Stat name
     */
    @XmlAttribute(name=AdminConstants.A_NAME /* name */, required=true)
    private final String name;

    /**
     * @zm-api-field-description Stats values
     */
    @XmlElement(name=AdminConstants.E_VALUES /* values */, required=false)
    private final StatsValues values;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private StatsInfo() {
        this((String) null, (StatsValues) null);
    }

    public StatsInfo(String name, StatsValues values) {
        this.name = name;
        this.values = values;
    }

    public String getName() { return name; }
    public StatsValues getValues() { return values; }
}
