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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.AdminConstants;
import org.zmail.soap.admin.type.DomainSelector;
import org.zmail.soap.admin.type.SMIMEConfigModifications;

/**
 * @zm-api-command-network-edition
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Modify a configuration for SMIME public key lookup via external LDAP on a domain or
 * globalconfig.
 * <br />
 * Notes: if <b>&lt;domain></b> is present, modify the config on the domain, otherwise modify the config on
 * globalconfig.
 * <br />
 * <br />
 * Attributes:
 * <table>
 * <tr> <td> <b> zmailSMIMELdapURL * </b> </td>
 *      <td> required </td>
 *      <td> LDAL URL - Multiple space-separated URLs can be specified for fallback purpose </td>
 * </tr>
 * <tr> <td> <b> zmailSMIMELdapStartTlsEnabled * </b> </td>
 *      <td> optional - TRUE/FALSE [default] </td>
 *      <td> Whether startTLS is enabled for the LDAP connection </td>
 * </tr>
 * <tr> <td> <b> zmailSMIMELdapBindDn </b> </td>
 *      <td> optional - default value is empty </td>
 *      <td> Bind DN.  ZCS will do anonymous bind if this attribute is not set </td>
 * </tr>
 * <tr> <td> <b> zmailSMIMELdapBindPassword </b> </td>
 *      <td> optional - default value is empty </td>
 *      <td> Bind password. Ignored if zmailSMIMELdapBindDn is not set </td>
 * </tr>
 * <tr> <td> <b> zmailSMIMELdapSearchBase * </b> </td>
 *      <td> optional - default is LDAP DIT root </td>
 *      <td> LDAP search base DN </td>
 * </tr>
 * <tr> <td> <b> zmailSMIMELdapFilter </b> </td>
 *      <td> required </td>
 *      <td> LDAP search filter template
 *           <br />
 *           Can contain the following conversion variables:
 *           <br />
 *           %n - search key with @ (or without, if no @ was specified)
 *           <br />
 *           %u - with @ removed
 *           <br />
 *           <br />
 *           e.g. (mail=%n)
 *      </td>
 * </tr>
 * <tr> <td> <b>zmailSMIMELdapAttribute</b> </td>
 *      <td> required </td>
 *      <td> LDAP attributes for SMIME certificates </td>
 * </tr>
 * </table>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_MODIFY_SMIME_CONFIG_REQUEST)
public class ModifySMIMEConfigRequest {

    /**
     * @zm-api-field-description SMIME Config Modifications
     */
    @XmlElement(name=AdminConstants.E_CONFIG, required=true)
    private final SMIMEConfigModifications config;

    /**
     * @zm-api-field-description Domain selector
     */
    @XmlElement(name=AdminConstants.E_DOMAIN, required=false)
    private final DomainSelector domain;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private ModifySMIMEConfigRequest() {
        this((SMIMEConfigModifications) null, (DomainSelector) null);
    }

    public ModifySMIMEConfigRequest(SMIMEConfigModifications config,
                    DomainSelector domain) {
        this.config = config;
        this.domain = domain;
    }

    public SMIMEConfigModifications getConfig() { return config; }
    public DomainSelector getDomain() { return domain; }
}
