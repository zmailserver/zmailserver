/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */

package generated.zcsclient.zm;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for granteeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="granteeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="usr"/>
 *     &lt;enumeration value="grp"/>
 *     &lt;enumeration value="egp"/>
 *     &lt;enumeration value="all"/>
 *     &lt;enumeration value="dom"/>
 *     &lt;enumeration value="gst"/>
 *     &lt;enumeration value="key"/>
 *     &lt;enumeration value="pub"/>
 *     &lt;enumeration value="email"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "granteeType")
@XmlEnum
public enum testGranteeType {

    @XmlEnumValue("usr")
    USR("usr"),
    @XmlEnumValue("grp")
    GRP("grp"),
    @XmlEnumValue("egp")
    EGP("egp"),
    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("dom")
    DOM("dom"),
    @XmlEnumValue("gst")
    GST("gst"),
    @XmlEnumValue("key")
    KEY("key"),
    @XmlEnumValue("pub")
    PUB("pub"),
    @XmlEnumValue("email")
    EMAIL("email");
    private final String value;

    testGranteeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static testGranteeType fromValue(String v) {
        for (testGranteeType c: testGranteeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
