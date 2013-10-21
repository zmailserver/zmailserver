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

package org.zmail.soap.admin.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.AdminConstants;
import org.zmail.soap.admin.type.AdminAttrsImpl;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Check Auth Config
 * <br />
 * For example:
 * <br />
 * <pre>
 * &lt;CheckAuthConfigRequest>
 *     &lt;a n='zmailAuthMech'>ldap&lt&lt;/a>
 *     &lt;a n='zmailAuthLdapURL'>...&lt;/a>
 *     &lt;a n='zmailAuthLdapBindDn'>...&lt;/a>
 *     &lt;a n='zmailAuthLdapSearchFilter'>...&lt;/a>
 *     &lt;a n='zmailAuthLdapSearchBase'>...&lt;/a>
 *     &lt;a n='zmailAuthLdapSearchBindDn'>...&lt;/a>
 *     &lt;a n='zmailAuthLdapSearchBindPassword'>...&lt;/a>
 *     &lt;name>...&lt;/name>
 *     &lt;password>...&lt;/password>
 * &lt;/CheckAuthConfigRequest>
 *
 * &lt;CheckAuthConfigResponse>
 *     &lt;code>...&lt;/code>
 *     &lt;message>...&lt;/message>*
 *     &lt;bindDn>{dn-computed-from-supplied-binddn-and-name}&lt;/bindDn>
 * &lt;/CheckAuthConfigResponse>
 * </pre>
 * notes:
 * <ul>
 * <li> zmailAuthMech must be set to ldap/ad. There is no reason to check zmail.
 * <li> zmailAuthLdapURL must be set
 * <li> either zmailAuthLdapBindDn or zmailAuthLdapSearchFilter must be set
 * </ul>
 * The following are optional, and only looked at if zmailAuthLdapSearchFilter is set:
 * <ul>
 * <li> zmailAuthLdapSearchBase is optional and defaults to ""
 * <li> zmailAuthLdapSearchBind{Dn,Password} are both optional
 * </ul>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_CHECK_AUTH_CONFIG_REQUEST)
public class CheckAuthConfigRequest extends AdminAttrsImpl {

    /**
     * @zm-api-field-tag name
     * @zm-api-field-description Name
     */
    @XmlAttribute(name=AdminConstants.E_NAME, required=true)
    private String name;

    /**
     * @zm-api-field-tag password
     * @zm-api-field-description Password
     */
    @XmlAttribute(name=AdminConstants.E_PASSWORD, required=true)
    private String password;

    public CheckAuthConfigRequest() {
        this(null, null);
    }

    public CheckAuthConfigRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
