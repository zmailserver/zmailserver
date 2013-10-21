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
import javax.xml.bind.annotation.XmlType;

import org.zmail.common.soap.AdminConstants;
import org.zmail.soap.admin.type.AdminAttrsImpl;
import org.zmail.soap.admin.type.LimitedQuery;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Check Global Addressbook Configuration
 * <br />
 * <pre>
 * &lt;CheckGalConfigRequest>
 *     &lt;a n='zmailGalMode'>ldap&lt;/a>
 *
 *     &lt;a n='zmailGalLdapURL'>...&lt;/a>
 *     &lt;a n='zmailGalLdapSearchBase'>...&lt;/a>
 *     &lt;a n='zmailGalLdapFilter'>...&lt;/a>
 *     &lt;a n='zmailGalLdapAuthMech'>...&lt;/a>
 *     &lt;a n='zmailGalLdapBindDn'>...&lt;/a>*
 *     &lt;a n='zmailGalLdapBindPassword'>...&lt;/a>*
 *     &lt;a n='zmailGalLdapKerberos5Principal'>...&lt;/a>*
 *     &lt;a n='zmailGalLdapKerberos5Keytab'>...&lt;/a>*
 *
 *     &lt;a n='zmailGalSyncLdapURL'>...&lt;/a>
 *     &lt;a n='zmailGalSyncLdapSearchBase'>...&lt;/a>
 *     &lt;a n='zmailGalSyncLdapFilter'>...&lt;/a>
 *     &lt;a n='zmailGalSyncLdapAuthMech'>...&lt;/a>
 *     &lt;a n='zmailGalSyncLdapBindDn'>...&lt;/a>*
 *     &lt;a n='zmailGalSyncLdapBindPassword'>...&lt;/a>*
 *     &lt;a n='zmailGalSyncLdapKerberos5Principal'>...&lt;/a>*
 *     &lt;a n='zmailGalSyncLdapKerberos5Keytab'>...&lt;/a>*
 *
 *     &lt;a n='zmailGalAutoCompleteLdapFilter'>...&lt;/a>
 *
 *     &lt;a n='zmailGalTokenizeAutoCompleteKey'>...&lt;/a>
 *     &lt;a n='zmailGalTokenizeSearchKey'>...&lt;/a>
 *
 *     &lt;query limit="...">...&lt;/query>*
 *     &lt;action>{GAL-action}&lt;/action>*
 * &lt;/CheckGalConfigRequest>
 * </pre>
 * Notes:
 * <ul>
 * <li> zmailGalMode must be set to ldap, even if you eventually want to set it to "both".
 * <li> &lt;action> is optional.  GAL-action can be autocomplete|search|sync.  Default is search.
 * <li> &lt;query> is ignored if &lt;action> is "sync".
 * <li> AuthMech can be none|simple|kerberos5.
 *      <ul>
 *      <li> Default is simple if both BindDn/BindPassword are provided.
 *      <li> Default is none if either BindDn or BindPassword are NOT provided.
 *      </ul>
 * <li> BindDn/BindPassword are required if AuthMech is "simple".
 * <li> Kerberos5Principal/Kerberos5Keytab are required only if AuthMech is "kerberos5".
 * <li> zmailGalSyncLdapXXX attributes are for GAL sync.  They are ignored if &lt;action> is not sync.
 *      <br />
 *      For GAL sync, if a zmailGalSyncLdapXXX attribute is not set, server will fallback to the corresponding
 *      zmailGalLdapXXX attribute.
 * </ul>
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_CHECK_GAL_CONFIG_REQUEST)
@XmlType(propOrder = {"query", "action"})
public class CheckGalConfigRequest extends AdminAttrsImpl {

    /**
     * @zm-api-field-description Query
     */
    @XmlElement(name=AdminConstants.E_QUERY)

    /**
     * @zm-api-field-tag GAL-action
     * @zm-api-field-description GAL action
     */
    private LimitedQuery query;
    @XmlElement(name=AdminConstants.E_ACTION)
    private String action;

    public CheckGalConfigRequest() {
        this((LimitedQuery)null, (String)null);
    }

    public CheckGalConfigRequest(LimitedQuery query, String action) {
        this.query = query;
        this.action = action;
    }

    public void setQuery(LimitedQuery query) { this.query = query; }

    public LimitedQuery getQuery() { return query; }
    public void setAction(String action) { this.action = action; }

    public String getAction() { return action; }
}
