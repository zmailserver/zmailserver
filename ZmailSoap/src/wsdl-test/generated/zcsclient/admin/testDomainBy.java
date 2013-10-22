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

package generated.zcsclient.admin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for domainBy.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="domainBy">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="id"/>
 *     &lt;enumeration value="name"/>
 *     &lt;enumeration value="virtualHostname"/>
 *     &lt;enumeration value="krb5Realm"/>
 *     &lt;enumeration value="foreignName"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "domainBy")
@XmlEnum
public enum testDomainBy {

    @XmlEnumValue("id")
    ID("id"),
    @XmlEnumValue("name")
    NAME("name"),
    @XmlEnumValue("virtualHostname")
    VIRTUAL_HOSTNAME("virtualHostname"),
    @XmlEnumValue("krb5Realm")
    KRB_5_REALM("krb5Realm"),
    @XmlEnumValue("foreignName")
    FOREIGN_NAME("foreignName");
    private final String value;

    testDomainBy(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static testDomainBy fromValue(String v) {
        for (testDomainBy c: testDomainBy.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
