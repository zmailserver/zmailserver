/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

package generated.zcsclient.zm;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for loggingLevel.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="loggingLevel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="error"/>
 *     &lt;enumeration value="warn"/>
 *     &lt;enumeration value="info"/>
 *     &lt;enumeration value="debug"/>
 *     &lt;enumeration value="trace"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "loggingLevel")
@XmlEnum
public enum testLoggingLevel {

    @XmlEnumValue("error")
    ERROR("error"),
    @XmlEnumValue("warn")
    WARN("warn"),
    @XmlEnumValue("info")
    INFO("info"),
    @XmlEnumValue("debug")
    DEBUG("debug"),
    @XmlEnumValue("trace")
    TRACE("trace");
    private final String value;

    testLoggingLevel(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static testLoggingLevel fromValue(String v) {
        for (testLoggingLevel c: testLoggingLevel.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
