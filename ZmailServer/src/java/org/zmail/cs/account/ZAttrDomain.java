/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

/*
 * Created on Sep 23, 2004
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.zmail.cs.account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zmail.common.account.ZAttr;
import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.StringUtil;

/**
 * AUTO-GENERATED. DO NOT EDIT.
 *
 */
public abstract class ZAttrDomain extends NamedEntry {

    public ZAttrDomain(String name, String id, Map<String, Object> attrs, Map<String, Object> defaults, Provisioning prov) {
        super(name, id, attrs, defaults, prov);

    }

    ///// BEGIN-AUTO-GEN-REPLACE

    /* build: 9.0.0_BETA1_1111 rgadipuuri 20130510-1145 */

    /**
     * RFC2256: descriptive information
     *
     * @return description, or empty array if unset
     */
    @ZAttr(id=-1)
    public String[] getDescription() {
        return getMultiAttr(Provisioning.A_description);
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setDescription(String[] description) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_description, description);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setDescription(String[] description, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_description, description);
        return attrs;
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void addDescription(String description) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_description, description);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> addDescription(String description, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_description, description);
        return attrs;
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void removeDescription(String description) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_description, description);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2256: descriptive information
     *
     * @param description existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> removeDescription(String description, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_description, description);
        return attrs;
    }

    /**
     * RFC2256: descriptive information
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetDescription() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_description, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2256: descriptive information
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetDescription(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_description, "");
        return attrs;
    }

    /**
     * Zmail access control list
     *
     * @return zmailACE, or empty array if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public String[] getACE() {
        return getMultiAttr(Provisioning.A_zmailACE);
    }

    /**
     * Zmail access control list
     *
     * @param zmailACE new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void setACE(String[] zmailACE) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailACE, zmailACE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zmail access control list
     *
     * @param zmailACE new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> setACE(String[] zmailACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailACE, zmailACE);
        return attrs;
    }

    /**
     * Zmail access control list
     *
     * @param zmailACE new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void addACE(String zmailACE) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailACE, zmailACE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zmail access control list
     *
     * @param zmailACE new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> addACE(String zmailACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailACE, zmailACE);
        return attrs;
    }

    /**
     * Zmail access control list
     *
     * @param zmailACE existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void removeACE(String zmailACE) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailACE, zmailACE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zmail access control list
     *
     * @param zmailACE existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> removeACE(String zmailACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailACE, zmailACE);
        return attrs;
    }

    /**
     * Zmail access control list
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void unsetACE() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailACE, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zmail access control list
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> unsetACE(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailACE, "");
        return attrs;
    }

    /**
     * whether to show catchall addresses in admin console
     *
     * @return zmailAdminConsoleCatchAllAddressEnabled, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=746)
    public boolean isAdminConsoleCatchAllAddressEnabled() {
        return getBooleanAttr(Provisioning.A_zmailAdminConsoleCatchAllAddressEnabled, false);
    }

    /**
     * whether to show catchall addresses in admin console
     *
     * @param zmailAdminConsoleCatchAllAddressEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=746)
    public void setAdminConsoleCatchAllAddressEnabled(boolean zmailAdminConsoleCatchAllAddressEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleCatchAllAddressEnabled, zmailAdminConsoleCatchAllAddressEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to show catchall addresses in admin console
     *
     * @param zmailAdminConsoleCatchAllAddressEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=746)
    public Map<String,Object> setAdminConsoleCatchAllAddressEnabled(boolean zmailAdminConsoleCatchAllAddressEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleCatchAllAddressEnabled, zmailAdminConsoleCatchAllAddressEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether to show catchall addresses in admin console
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=746)
    public void unsetAdminConsoleCatchAllAddressEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleCatchAllAddressEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to show catchall addresses in admin console
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=746)
    public Map<String,Object> unsetAdminConsoleCatchAllAddressEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleCatchAllAddressEnabled, "");
        return attrs;
    }

    /**
     * enable MX check feature for domain
     *
     * @return zmailAdminConsoleDNSCheckEnabled, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=743)
    public boolean isAdminConsoleDNSCheckEnabled() {
        return getBooleanAttr(Provisioning.A_zmailAdminConsoleDNSCheckEnabled, false);
    }

    /**
     * enable MX check feature for domain
     *
     * @param zmailAdminConsoleDNSCheckEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=743)
    public void setAdminConsoleDNSCheckEnabled(boolean zmailAdminConsoleDNSCheckEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleDNSCheckEnabled, zmailAdminConsoleDNSCheckEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable MX check feature for domain
     *
     * @param zmailAdminConsoleDNSCheckEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=743)
    public Map<String,Object> setAdminConsoleDNSCheckEnabled(boolean zmailAdminConsoleDNSCheckEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleDNSCheckEnabled, zmailAdminConsoleDNSCheckEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * enable MX check feature for domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=743)
    public void unsetAdminConsoleDNSCheckEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleDNSCheckEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable MX check feature for domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=743)
    public Map<String,Object> unsetAdminConsoleDNSCheckEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleDNSCheckEnabled, "");
        return attrs;
    }

    /**
     * whether configuring external LDAP auth is enabled in admin console
     *
     * @return zmailAdminConsoleLDAPAuthEnabled, or false if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=774)
    public boolean isAdminConsoleLDAPAuthEnabled() {
        return getBooleanAttr(Provisioning.A_zmailAdminConsoleLDAPAuthEnabled, false);
    }

    /**
     * whether configuring external LDAP auth is enabled in admin console
     *
     * @param zmailAdminConsoleLDAPAuthEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=774)
    public void setAdminConsoleLDAPAuthEnabled(boolean zmailAdminConsoleLDAPAuthEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLDAPAuthEnabled, zmailAdminConsoleLDAPAuthEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether configuring external LDAP auth is enabled in admin console
     *
     * @param zmailAdminConsoleLDAPAuthEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=774)
    public Map<String,Object> setAdminConsoleLDAPAuthEnabled(boolean zmailAdminConsoleLDAPAuthEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLDAPAuthEnabled, zmailAdminConsoleLDAPAuthEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether configuring external LDAP auth is enabled in admin console
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=774)
    public void unsetAdminConsoleLDAPAuthEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLDAPAuthEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether configuring external LDAP auth is enabled in admin console
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=774)
    public Map<String,Object> unsetAdminConsoleLDAPAuthEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLDAPAuthEnabled, "");
        return attrs;
    }

    /**
     * admin console login message
     *
     * @return zmailAdminConsoleLoginMessage, or empty array if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public String[] getAdminConsoleLoginMessage() {
        return getMultiAttr(Provisioning.A_zmailAdminConsoleLoginMessage);
    }

    /**
     * admin console login message
     *
     * @param zmailAdminConsoleLoginMessage new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public void setAdminConsoleLoginMessage(String[] zmailAdminConsoleLoginMessage) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginMessage, zmailAdminConsoleLoginMessage);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * admin console login message
     *
     * @param zmailAdminConsoleLoginMessage new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public Map<String,Object> setAdminConsoleLoginMessage(String[] zmailAdminConsoleLoginMessage, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginMessage, zmailAdminConsoleLoginMessage);
        return attrs;
    }

    /**
     * admin console login message
     *
     * @param zmailAdminConsoleLoginMessage new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public void addAdminConsoleLoginMessage(String zmailAdminConsoleLoginMessage) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAdminConsoleLoginMessage, zmailAdminConsoleLoginMessage);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * admin console login message
     *
     * @param zmailAdminConsoleLoginMessage new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public Map<String,Object> addAdminConsoleLoginMessage(String zmailAdminConsoleLoginMessage, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAdminConsoleLoginMessage, zmailAdminConsoleLoginMessage);
        return attrs;
    }

    /**
     * admin console login message
     *
     * @param zmailAdminConsoleLoginMessage existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public void removeAdminConsoleLoginMessage(String zmailAdminConsoleLoginMessage) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAdminConsoleLoginMessage, zmailAdminConsoleLoginMessage);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * admin console login message
     *
     * @param zmailAdminConsoleLoginMessage existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public Map<String,Object> removeAdminConsoleLoginMessage(String zmailAdminConsoleLoginMessage, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAdminConsoleLoginMessage, zmailAdminConsoleLoginMessage);
        return attrs;
    }

    /**
     * admin console login message
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public void unsetAdminConsoleLoginMessage() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginMessage, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * admin console login message
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=772)
    public Map<String,Object> unsetAdminConsoleLoginMessage(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginMessage, "");
        return attrs;
    }

    /**
     * login URL for admin console to send the user to upon explicit logging
     * in
     *
     * @return zmailAdminConsoleLoginURL, or null if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=696)
    public String getAdminConsoleLoginURL() {
        return getAttr(Provisioning.A_zmailAdminConsoleLoginURL, null);
    }

    /**
     * login URL for admin console to send the user to upon explicit logging
     * in
     *
     * @param zmailAdminConsoleLoginURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=696)
    public void setAdminConsoleLoginURL(String zmailAdminConsoleLoginURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginURL, zmailAdminConsoleLoginURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * login URL for admin console to send the user to upon explicit logging
     * in
     *
     * @param zmailAdminConsoleLoginURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=696)
    public Map<String,Object> setAdminConsoleLoginURL(String zmailAdminConsoleLoginURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginURL, zmailAdminConsoleLoginURL);
        return attrs;
    }

    /**
     * login URL for admin console to send the user to upon explicit logging
     * in
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=696)
    public void unsetAdminConsoleLoginURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * login URL for admin console to send the user to upon explicit logging
     * in
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=696)
    public Map<String,Object> unsetAdminConsoleLoginURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLoginURL, "");
        return attrs;
    }

    /**
     * logout URL for admin console to send the user to upon explicit logging
     * out
     *
     * @return zmailAdminConsoleLogoutURL, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=684)
    public String getAdminConsoleLogoutURL() {
        return getAttr(Provisioning.A_zmailAdminConsoleLogoutURL, null);
    }

    /**
     * logout URL for admin console to send the user to upon explicit logging
     * out
     *
     * @param zmailAdminConsoleLogoutURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=684)
    public void setAdminConsoleLogoutURL(String zmailAdminConsoleLogoutURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLogoutURL, zmailAdminConsoleLogoutURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logout URL for admin console to send the user to upon explicit logging
     * out
     *
     * @param zmailAdminConsoleLogoutURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=684)
    public Map<String,Object> setAdminConsoleLogoutURL(String zmailAdminConsoleLogoutURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLogoutURL, zmailAdminConsoleLogoutURL);
        return attrs;
    }

    /**
     * logout URL for admin console to send the user to upon explicit logging
     * out
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=684)
    public void unsetAdminConsoleLogoutURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLogoutURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logout URL for admin console to send the user to upon explicit logging
     * out
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=684)
    public Map<String,Object> unsetAdminConsoleLogoutURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleLogoutURL, "");
        return attrs;
    }

    /**
     * whether to allow skin management in admin console
     *
     * @return zmailAdminConsoleSkinEnabled, or false if unset
     *
     * @since ZCS 5.0.11
     */
    @ZAttr(id=751)
    public boolean isAdminConsoleSkinEnabled() {
        return getBooleanAttr(Provisioning.A_zmailAdminConsoleSkinEnabled, false);
    }

    /**
     * whether to allow skin management in admin console
     *
     * @param zmailAdminConsoleSkinEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.11
     */
    @ZAttr(id=751)
    public void setAdminConsoleSkinEnabled(boolean zmailAdminConsoleSkinEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleSkinEnabled, zmailAdminConsoleSkinEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to allow skin management in admin console
     *
     * @param zmailAdminConsoleSkinEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.11
     */
    @ZAttr(id=751)
    public Map<String,Object> setAdminConsoleSkinEnabled(boolean zmailAdminConsoleSkinEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleSkinEnabled, zmailAdminConsoleSkinEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether to allow skin management in admin console
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.11
     */
    @ZAttr(id=751)
    public void unsetAdminConsoleSkinEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleSkinEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to allow skin management in admin console
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.11
     */
    @ZAttr(id=751)
    public Map<String,Object> unsetAdminConsoleSkinEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleSkinEnabled, "");
        return attrs;
    }

    /**
     * last calculated aggregate quota usage for the domain in bytes
     *
     * @return zmailAggregateQuotaLastUsage, or -1 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1328)
    public long getAggregateQuotaLastUsage() {
        return getLongAttr(Provisioning.A_zmailAggregateQuotaLastUsage, -1L);
    }

    /**
     * last calculated aggregate quota usage for the domain in bytes
     *
     * @param zmailAggregateQuotaLastUsage new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1328)
    public void setAggregateQuotaLastUsage(long zmailAggregateQuotaLastUsage) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAggregateQuotaLastUsage, Long.toString(zmailAggregateQuotaLastUsage));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * last calculated aggregate quota usage for the domain in bytes
     *
     * @param zmailAggregateQuotaLastUsage new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1328)
    public Map<String,Object> setAggregateQuotaLastUsage(long zmailAggregateQuotaLastUsage, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAggregateQuotaLastUsage, Long.toString(zmailAggregateQuotaLastUsage));
        return attrs;
    }

    /**
     * last calculated aggregate quota usage for the domain in bytes
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1328)
    public void unsetAggregateQuotaLastUsage() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAggregateQuotaLastUsage, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * last calculated aggregate quota usage for the domain in bytes
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1328)
    public Map<String,Object> unsetAggregateQuotaLastUsage(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAggregateQuotaLastUsage, "");
        return attrs;
    }

    /**
     * fallback to local auth if external mech fails
     *
     * @return zmailAuthFallbackToLocal, or false if unset
     */
    @ZAttr(id=257)
    public boolean isAuthFallbackToLocal() {
        return getBooleanAttr(Provisioning.A_zmailAuthFallbackToLocal, false);
    }

    /**
     * fallback to local auth if external mech fails
     *
     * @param zmailAuthFallbackToLocal new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=257)
    public void setAuthFallbackToLocal(boolean zmailAuthFallbackToLocal) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthFallbackToLocal, zmailAuthFallbackToLocal ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * fallback to local auth if external mech fails
     *
     * @param zmailAuthFallbackToLocal new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=257)
    public Map<String,Object> setAuthFallbackToLocal(boolean zmailAuthFallbackToLocal, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthFallbackToLocal, zmailAuthFallbackToLocal ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * fallback to local auth if external mech fails
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=257)
    public void unsetAuthFallbackToLocal() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthFallbackToLocal, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * fallback to local auth if external mech fails
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=257)
    public Map<String,Object> unsetAuthFallbackToLocal(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthFallbackToLocal, "");
        return attrs;
    }

    /**
     * kerberos5 realm for kerberos5 auth mech
     *
     * @return zmailAuthKerberos5Realm, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=548)
    public String getAuthKerberos5Realm() {
        return getAttr(Provisioning.A_zmailAuthKerberos5Realm, null);
    }

    /**
     * kerberos5 realm for kerberos5 auth mech
     *
     * @param zmailAuthKerberos5Realm new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=548)
    public void setAuthKerberos5Realm(String zmailAuthKerberos5Realm) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthKerberos5Realm, zmailAuthKerberos5Realm);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 realm for kerberos5 auth mech
     *
     * @param zmailAuthKerberos5Realm new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=548)
    public Map<String,Object> setAuthKerberos5Realm(String zmailAuthKerberos5Realm, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthKerberos5Realm, zmailAuthKerberos5Realm);
        return attrs;
    }

    /**
     * kerberos5 realm for kerberos5 auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=548)
    public void unsetAuthKerberos5Realm() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthKerberos5Realm, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 realm for kerberos5 auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=548)
    public Map<String,Object> unsetAuthKerberos5Realm(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthKerberos5Realm, "");
        return attrs;
    }

    /**
     * LDAP bind dn for ldap auth mech
     *
     * @return zmailAuthLdapBindDn, or null if unset
     */
    @ZAttr(id=44)
    public String getAuthLdapBindDn() {
        return getAttr(Provisioning.A_zmailAuthLdapBindDn, null);
    }

    /**
     * LDAP bind dn for ldap auth mech
     *
     * @param zmailAuthLdapBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=44)
    public void setAuthLdapBindDn(String zmailAuthLdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapBindDn, zmailAuthLdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind dn for ldap auth mech
     *
     * @param zmailAuthLdapBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=44)
    public Map<String,Object> setAuthLdapBindDn(String zmailAuthLdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapBindDn, zmailAuthLdapBindDn);
        return attrs;
    }

    /**
     * LDAP bind dn for ldap auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=44)
    public void unsetAuthLdapBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind dn for ldap auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=44)
    public Map<String,Object> unsetAuthLdapBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapBindDn, "");
        return attrs;
    }

    /**
     * LDAP search base for ldap auth mech
     *
     * @return zmailAuthLdapSearchBase, or null if unset
     */
    @ZAttr(id=252)
    public String getAuthLdapSearchBase() {
        return getAttr(Provisioning.A_zmailAuthLdapSearchBase, null);
    }

    /**
     * LDAP search base for ldap auth mech
     *
     * @param zmailAuthLdapSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=252)
    public void setAuthLdapSearchBase(String zmailAuthLdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBase, zmailAuthLdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for ldap auth mech
     *
     * @param zmailAuthLdapSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=252)
    public Map<String,Object> setAuthLdapSearchBase(String zmailAuthLdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBase, zmailAuthLdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for ldap auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=252)
    public void unsetAuthLdapSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for ldap auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=252)
    public Map<String,Object> unsetAuthLdapSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBase, "");
        return attrs;
    }

    /**
     * LDAP search bind dn for ldap auth mech
     *
     * @return zmailAuthLdapSearchBindDn, or null if unset
     */
    @ZAttr(id=253)
    public String getAuthLdapSearchBindDn() {
        return getAttr(Provisioning.A_zmailAuthLdapSearchBindDn, null);
    }

    /**
     * LDAP search bind dn for ldap auth mech
     *
     * @param zmailAuthLdapSearchBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=253)
    public void setAuthLdapSearchBindDn(String zmailAuthLdapSearchBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindDn, zmailAuthLdapSearchBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search bind dn for ldap auth mech
     *
     * @param zmailAuthLdapSearchBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=253)
    public Map<String,Object> setAuthLdapSearchBindDn(String zmailAuthLdapSearchBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindDn, zmailAuthLdapSearchBindDn);
        return attrs;
    }

    /**
     * LDAP search bind dn for ldap auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=253)
    public void unsetAuthLdapSearchBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search bind dn for ldap auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=253)
    public Map<String,Object> unsetAuthLdapSearchBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindDn, "");
        return attrs;
    }

    /**
     * LDAP search bind password for ldap auth mech
     *
     * @return zmailAuthLdapSearchBindPassword, or null if unset
     */
    @ZAttr(id=254)
    public String getAuthLdapSearchBindPassword() {
        return getAttr(Provisioning.A_zmailAuthLdapSearchBindPassword, null);
    }

    /**
     * LDAP search bind password for ldap auth mech
     *
     * @param zmailAuthLdapSearchBindPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=254)
    public void setAuthLdapSearchBindPassword(String zmailAuthLdapSearchBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindPassword, zmailAuthLdapSearchBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search bind password for ldap auth mech
     *
     * @param zmailAuthLdapSearchBindPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=254)
    public Map<String,Object> setAuthLdapSearchBindPassword(String zmailAuthLdapSearchBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindPassword, zmailAuthLdapSearchBindPassword);
        return attrs;
    }

    /**
     * LDAP search bind password for ldap auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=254)
    public void unsetAuthLdapSearchBindPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search bind password for ldap auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=254)
    public Map<String,Object> unsetAuthLdapSearchBindPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchBindPassword, "");
        return attrs;
    }

    /**
     * LDAP search filter for ldap auth mech
     *
     * @return zmailAuthLdapSearchFilter, or null if unset
     */
    @ZAttr(id=255)
    public String getAuthLdapSearchFilter() {
        return getAttr(Provisioning.A_zmailAuthLdapSearchFilter, null);
    }

    /**
     * LDAP search filter for ldap auth mech
     *
     * @param zmailAuthLdapSearchFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=255)
    public void setAuthLdapSearchFilter(String zmailAuthLdapSearchFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchFilter, zmailAuthLdapSearchFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for ldap auth mech
     *
     * @param zmailAuthLdapSearchFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=255)
    public Map<String,Object> setAuthLdapSearchFilter(String zmailAuthLdapSearchFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchFilter, zmailAuthLdapSearchFilter);
        return attrs;
    }

    /**
     * LDAP search filter for ldap auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=255)
    public void unsetAuthLdapSearchFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for ldap auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=255)
    public Map<String,Object> unsetAuthLdapSearchFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapSearchFilter, "");
        return attrs;
    }

    /**
     * whether to use startTLS for external LDAP auth
     *
     * @return zmailAuthLdapStartTlsEnabled, or false if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=654)
    public boolean isAuthLdapStartTlsEnabled() {
        return getBooleanAttr(Provisioning.A_zmailAuthLdapStartTlsEnabled, false);
    }

    /**
     * whether to use startTLS for external LDAP auth
     *
     * @param zmailAuthLdapStartTlsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=654)
    public void setAuthLdapStartTlsEnabled(boolean zmailAuthLdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapStartTlsEnabled, zmailAuthLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to use startTLS for external LDAP auth
     *
     * @param zmailAuthLdapStartTlsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=654)
    public Map<String,Object> setAuthLdapStartTlsEnabled(boolean zmailAuthLdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapStartTlsEnabled, zmailAuthLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether to use startTLS for external LDAP auth
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=654)
    public void unsetAuthLdapStartTlsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapStartTlsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to use startTLS for external LDAP auth
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=654)
    public Map<String,Object> unsetAuthLdapStartTlsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapStartTlsEnabled, "");
        return attrs;
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @return zmailAuthLdapURL, or empty array if unset
     */
    @ZAttr(id=43)
    public String[] getAuthLdapURL() {
        return getMultiAttr(Provisioning.A_zmailAuthLdapURL);
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param zmailAuthLdapURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=43)
    public void setAuthLdapURL(String[] zmailAuthLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapURL, zmailAuthLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param zmailAuthLdapURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=43)
    public Map<String,Object> setAuthLdapURL(String[] zmailAuthLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapURL, zmailAuthLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param zmailAuthLdapURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=43)
    public void addAuthLdapURL(String zmailAuthLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAuthLdapURL, zmailAuthLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param zmailAuthLdapURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=43)
    public Map<String,Object> addAuthLdapURL(String zmailAuthLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAuthLdapURL, zmailAuthLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param zmailAuthLdapURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=43)
    public void removeAuthLdapURL(String zmailAuthLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAuthLdapURL, zmailAuthLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param zmailAuthLdapURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=43)
    public Map<String,Object> removeAuthLdapURL(String zmailAuthLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAuthLdapURL, zmailAuthLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=43)
    public void unsetAuthLdapURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for ldap auth mech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=43)
    public Map<String,Object> unsetAuthLdapURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthLdapURL, "");
        return attrs;
    }

    /**
     * mechanism to use for verifying password. Valid values are zmail,
     * ldap, ad, kerberos5, custom:{handler-name} [arg1 arg2 ...]
     *
     * @return zmailAuthMech, or null if unset
     */
    @ZAttr(id=42)
    public String getAuthMech() {
        return getAttr(Provisioning.A_zmailAuthMech, null);
    }

    /**
     * mechanism to use for verifying password. Valid values are zmail,
     * ldap, ad, kerberos5, custom:{handler-name} [arg1 arg2 ...]
     *
     * @param zmailAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=42)
    public void setAuthMech(String zmailAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMech, zmailAuthMech);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mechanism to use for verifying password. Valid values are zmail,
     * ldap, ad, kerberos5, custom:{handler-name} [arg1 arg2 ...]
     *
     * @param zmailAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=42)
    public Map<String,Object> setAuthMech(String zmailAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMech, zmailAuthMech);
        return attrs;
    }

    /**
     * mechanism to use for verifying password. Valid values are zmail,
     * ldap, ad, kerberos5, custom:{handler-name} [arg1 arg2 ...]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=42)
    public void unsetAuthMech() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMech, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mechanism to use for verifying password. Valid values are zmail,
     * ldap, ad, kerberos5, custom:{handler-name} [arg1 arg2 ...]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=42)
    public Map<String,Object> unsetAuthMech(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMech, "");
        return attrs;
    }

    /**
     * mechanism to use for verifying password for admin. See zmailAuthMech
     *
     * @return zmailAuthMechAdmin, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1252)
    public String getAuthMechAdmin() {
        return getAttr(Provisioning.A_zmailAuthMechAdmin, null);
    }

    /**
     * mechanism to use for verifying password for admin. See zmailAuthMech
     *
     * @param zmailAuthMechAdmin new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1252)
    public void setAuthMechAdmin(String zmailAuthMechAdmin) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMechAdmin, zmailAuthMechAdmin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mechanism to use for verifying password for admin. See zmailAuthMech
     *
     * @param zmailAuthMechAdmin new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1252)
    public Map<String,Object> setAuthMechAdmin(String zmailAuthMechAdmin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMechAdmin, zmailAuthMechAdmin);
        return attrs;
    }

    /**
     * mechanism to use for verifying password for admin. See zmailAuthMech
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1252)
    public void unsetAuthMechAdmin() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMechAdmin, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mechanism to use for verifying password for admin. See zmailAuthMech
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1252)
    public Map<String,Object> unsetAuthMechAdmin(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAuthMechAdmin, "");
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute name in the external directory that contains localpart of
     * the account name. If not specified, localpart of the account name is
     * the principal user used to authenticated to Zmail.
     *
     * @return zmailAutoProvAccountNameMap, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1230)
    public String getAutoProvAccountNameMap() {
        return getAttr(Provisioning.A_zmailAutoProvAccountNameMap, null);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute name in the external directory that contains localpart of
     * the account name. If not specified, localpart of the account name is
     * the principal user used to authenticated to Zmail.
     *
     * @param zmailAutoProvAccountNameMap new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1230)
    public void setAutoProvAccountNameMap(String zmailAutoProvAccountNameMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAccountNameMap, zmailAutoProvAccountNameMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute name in the external directory that contains localpart of
     * the account name. If not specified, localpart of the account name is
     * the principal user used to authenticated to Zmail.
     *
     * @param zmailAutoProvAccountNameMap new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1230)
    public Map<String,Object> setAutoProvAccountNameMap(String zmailAutoProvAccountNameMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAccountNameMap, zmailAutoProvAccountNameMap);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute name in the external directory that contains localpart of
     * the account name. If not specified, localpart of the account name is
     * the principal user used to authenticated to Zmail.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1230)
    public void unsetAutoProvAccountNameMap() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAccountNameMap, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute name in the external directory that contains localpart of
     * the account name. If not specified, localpart of the account name is
     * the principal user used to authenticated to Zmail.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1230)
    public Map<String,Object> unsetAutoProvAccountNameMap(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAccountNameMap, "");
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @return zmailAutoProvAttrMap, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public String[] getAutoProvAttrMap() {
        return getMultiAttr(Provisioning.A_zmailAutoProvAttrMap);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param zmailAutoProvAttrMap new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public void setAutoProvAttrMap(String[] zmailAutoProvAttrMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAttrMap, zmailAutoProvAttrMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param zmailAutoProvAttrMap new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public Map<String,Object> setAutoProvAttrMap(String[] zmailAutoProvAttrMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAttrMap, zmailAutoProvAttrMap);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param zmailAutoProvAttrMap new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public void addAutoProvAttrMap(String zmailAutoProvAttrMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAutoProvAttrMap, zmailAutoProvAttrMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param zmailAutoProvAttrMap new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public Map<String,Object> addAutoProvAttrMap(String zmailAutoProvAttrMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAutoProvAttrMap, zmailAutoProvAttrMap);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param zmailAutoProvAttrMap existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public void removeAutoProvAttrMap(String zmailAutoProvAttrMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAutoProvAttrMap, zmailAutoProvAttrMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param zmailAutoProvAttrMap existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public Map<String,Object> removeAutoProvAttrMap(String zmailAutoProvAttrMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAutoProvAttrMap, zmailAutoProvAttrMap);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public void unsetAutoProvAttrMap() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAttrMap, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional
     * Attribute map for mapping attribute values from the external entry to
     * Zmail account attributes. Values are in the format of {external
     * attribute}={zmail attribute}. If not set, no attributes from the
     * external directory will be populated in Zmail directory. Invalid
     * mapping configuration will cause the account creation to fail.
     * Examples of bad mapping: - invalid external attribute name. - invalid
     * Zmail attribute name. - external attribute has multiple values but
     * the zmail attribute is single-valued. - syntax violation. e.g. Value
     * on the external attribute is a String but the Zmail attribute is
     * declared an integer.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1231)
    public Map<String,Object> unsetAutoProvAttrMap(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAttrMap, "");
        return attrs;
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @return zmailAutoProvAuthMech, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public String[] getAutoProvAuthMechAsString() {
        return getMultiAttr(Provisioning.A_zmailAutoProvAuthMech);
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @param zmailAutoProvAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public void setAutoProvAuthMech(ZAttrProvisioning.AutoProvAuthMech zmailAutoProvAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAuthMech, zmailAutoProvAuthMech.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @param zmailAutoProvAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public Map<String,Object> setAutoProvAuthMech(ZAttrProvisioning.AutoProvAuthMech zmailAutoProvAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAuthMech, zmailAutoProvAuthMech.toString());
        return attrs;
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @param zmailAutoProvAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public void setAutoProvAuthMechAsString(String[] zmailAutoProvAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAuthMech, zmailAutoProvAuthMech);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @param zmailAutoProvAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public Map<String,Object> setAutoProvAuthMechAsString(String[] zmailAutoProvAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAuthMech, zmailAutoProvAuthMech);
        return attrs;
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public void unsetAutoProvAuthMech() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAuthMech, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: N/A LAZY mode: required MANUAL mode: N/A Auth mechanisms
     * enabled for auto provision in LAZY mode. When a user authenticates via
     * one of the external auth mechanisms enabled in this attribute, and
     * when the user account does not yet exist in Zmail directory, an
     * account entry will be automatically created in Zmail directory.
     *
     * <p>Valid values: [KRB5, LDAP, PREAUTH, SPNEGO]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1222)
    public Map<String,Object> unsetAutoProvAuthMech(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvAuthMech, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Max number of
     * accounts to process in each interval for EAGER auto provision.
     *
     * @return zmailAutoProvBatchSize, or 20 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1234)
    public int getAutoProvBatchSize() {
        return getIntAttr(Provisioning.A_zmailAutoProvBatchSize, 20);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Max number of
     * accounts to process in each interval for EAGER auto provision.
     *
     * @param zmailAutoProvBatchSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1234)
    public void setAutoProvBatchSize(int zmailAutoProvBatchSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvBatchSize, Integer.toString(zmailAutoProvBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Max number of
     * accounts to process in each interval for EAGER auto provision.
     *
     * @param zmailAutoProvBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1234)
    public Map<String,Object> setAutoProvBatchSize(int zmailAutoProvBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvBatchSize, Integer.toString(zmailAutoProvBatchSize));
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Max number of
     * accounts to process in each interval for EAGER auto provision.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1234)
    public void unsetAutoProvBatchSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvBatchSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Max number of
     * accounts to process in each interval for EAGER auto provision.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1234)
    public Map<String,Object> unsetAutoProvBatchSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvBatchSize, "");
        return attrs;
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * <p>Use getAutoProvLastPolledTimestampAsString to access value as a string.
     *
     * @see #getAutoProvLastPolledTimestampAsString()
     *
     * @return zmailAutoProvLastPolledTimestamp as Date, null if unset or unable to parse
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public Date getAutoProvLastPolledTimestamp() {
        return getGeneralizedTimeAttr(Provisioning.A_zmailAutoProvLastPolledTimestamp, null);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @return zmailAutoProvLastPolledTimestamp, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public String getAutoProvLastPolledTimestampAsString() {
        return getAttr(Provisioning.A_zmailAutoProvLastPolledTimestamp, null);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @param zmailAutoProvLastPolledTimestamp new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public void setAutoProvLastPolledTimestamp(Date zmailAutoProvLastPolledTimestamp) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLastPolledTimestamp, zmailAutoProvLastPolledTimestamp==null ? "" : DateUtil.toGeneralizedTime(zmailAutoProvLastPolledTimestamp));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @param zmailAutoProvLastPolledTimestamp new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public Map<String,Object> setAutoProvLastPolledTimestamp(Date zmailAutoProvLastPolledTimestamp, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLastPolledTimestamp, zmailAutoProvLastPolledTimestamp==null ? "" : DateUtil.toGeneralizedTime(zmailAutoProvLastPolledTimestamp));
        return attrs;
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @param zmailAutoProvLastPolledTimestamp new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public void setAutoProvLastPolledTimestampAsString(String zmailAutoProvLastPolledTimestamp) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLastPolledTimestamp, zmailAutoProvLastPolledTimestamp);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @param zmailAutoProvLastPolledTimestamp new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public Map<String,Object> setAutoProvLastPolledTimestampAsString(String zmailAutoProvLastPolledTimestamp, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLastPolledTimestamp, zmailAutoProvLastPolledTimestamp);
        return attrs;
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public void unsetAutoProvLastPolledTimestamp() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLastPolledTimestamp, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A Timestamp when the external domain is last
     * polled for EAGER auto provision. The poll (LDAP search) for the next
     * iteration will fetch external entries with create timestamp later than
     * the timestamp recorded from the previous iteration.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1235)
    public Map<String,Object> unsetAutoProvLastPolledTimestamp(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLastPolledTimestamp, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: required LDAP search bind
     * DN for auto provision.
     *
     * @return zmailAutoProvLdapAdminBindDn, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1225)
    public String getAutoProvLdapAdminBindDn() {
        return getAttr(Provisioning.A_zmailAutoProvLdapAdminBindDn, null);
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: required LDAP search bind
     * DN for auto provision.
     *
     * @param zmailAutoProvLdapAdminBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1225)
    public void setAutoProvLdapAdminBindDn(String zmailAutoProvLdapAdminBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindDn, zmailAutoProvLdapAdminBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: required LDAP search bind
     * DN for auto provision.
     *
     * @param zmailAutoProvLdapAdminBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1225)
    public Map<String,Object> setAutoProvLdapAdminBindDn(String zmailAutoProvLdapAdminBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindDn, zmailAutoProvLdapAdminBindDn);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: required LDAP search bind
     * DN for auto provision.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1225)
    public void unsetAutoProvLdapAdminBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: required LDAP search bind
     * DN for auto provision.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1225)
    public Map<String,Object> unsetAutoProvLdapAdminBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindDn, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * search bind password for auto provision.
     *
     * @return zmailAutoProvLdapAdminBindPassword, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1226)
    public String getAutoProvLdapAdminBindPassword() {
        return getAttr(Provisioning.A_zmailAutoProvLdapAdminBindPassword, null);
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * search bind password for auto provision.
     *
     * @param zmailAutoProvLdapAdminBindPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1226)
    public void setAutoProvLdapAdminBindPassword(String zmailAutoProvLdapAdminBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindPassword, zmailAutoProvLdapAdminBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * search bind password for auto provision.
     *
     * @param zmailAutoProvLdapAdminBindPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1226)
    public Map<String,Object> setAutoProvLdapAdminBindPassword(String zmailAutoProvLdapAdminBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindPassword, zmailAutoProvLdapAdminBindPassword);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * search bind password for auto provision.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1226)
    public void unsetAutoProvLdapAdminBindPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * search bind password for auto provision.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1226)
    public Map<String,Object> unsetAutoProvLdapAdminBindPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapAdminBindPassword, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) LDAP external DN template for account
     * auto provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @return zmailAutoProvLdapBindDn, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1229)
    public String getAutoProvLdapBindDn() {
        return getAttr(Provisioning.A_zmailAutoProvLdapBindDn, null);
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) LDAP external DN template for account
     * auto provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @param zmailAutoProvLdapBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1229)
    public void setAutoProvLdapBindDn(String zmailAutoProvLdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapBindDn, zmailAutoProvLdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) LDAP external DN template for account
     * auto provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @param zmailAutoProvLdapBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1229)
    public Map<String,Object> setAutoProvLdapBindDn(String zmailAutoProvLdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapBindDn, zmailAutoProvLdapBindDn);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) LDAP external DN template for account
     * auto provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1229)
    public void unsetAutoProvLdapBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) MANUAL mode: optional (if not using
     * zmailAutoProvLdapSearchFilter) LDAP external DN template for account
     * auto provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1229)
    public Map<String,Object> unsetAutoProvLdapBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapBindDn, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter), MANUAL mode: required LDAP search
     * base for auto provision, used in conjunction with
     * zmailAutoProvLdapSearchFilter. If not set, LDAP root DSE will be
     * used.
     *
     * @return zmailAutoProvLdapSearchBase, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1227)
    public String getAutoProvLdapSearchBase() {
        return getAttr(Provisioning.A_zmailAutoProvLdapSearchBase, null);
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter), MANUAL mode: required LDAP search
     * base for auto provision, used in conjunction with
     * zmailAutoProvLdapSearchFilter. If not set, LDAP root DSE will be
     * used.
     *
     * @param zmailAutoProvLdapSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1227)
    public void setAutoProvLdapSearchBase(String zmailAutoProvLdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, zmailAutoProvLdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter), MANUAL mode: required LDAP search
     * base for auto provision, used in conjunction with
     * zmailAutoProvLdapSearchFilter. If not set, LDAP root DSE will be
     * used.
     *
     * @param zmailAutoProvLdapSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1227)
    public Map<String,Object> setAutoProvLdapSearchBase(String zmailAutoProvLdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, zmailAutoProvLdapSearchBase);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter), MANUAL mode: required LDAP search
     * base for auto provision, used in conjunction with
     * zmailAutoProvLdapSearchFilter. If not set, LDAP root DSE will be
     * used.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1227)
    public void unsetAutoProvLdapSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required (if using
     * zmailAutoProvLdapSearchFilter), MANUAL mode: required LDAP search
     * base for auto provision, used in conjunction with
     * zmailAutoProvLdapSearchFilter. If not set, LDAP root DSE will be
     * used.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1227)
    public Map<String,Object> unsetAutoProvLdapSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchBase, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapBindDn) MANUAL mode: optional (if not using
     * zmailAutoProvLdapBindDn) LDAP search filter template for account auto
     * provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @return zmailAutoProvLdapSearchFilter, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1228)
    public String getAutoProvLdapSearchFilter() {
        return getAttr(Provisioning.A_zmailAutoProvLdapSearchFilter, null);
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapBindDn) MANUAL mode: optional (if not using
     * zmailAutoProvLdapBindDn) LDAP search filter template for account auto
     * provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @param zmailAutoProvLdapSearchFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1228)
    public void setAutoProvLdapSearchFilter(String zmailAutoProvLdapSearchFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, zmailAutoProvLdapSearchFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapBindDn) MANUAL mode: optional (if not using
     * zmailAutoProvLdapBindDn) LDAP search filter template for account auto
     * provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @param zmailAutoProvLdapSearchFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1228)
    public Map<String,Object> setAutoProvLdapSearchFilter(String zmailAutoProvLdapSearchFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, zmailAutoProvLdapSearchFilter);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapBindDn) MANUAL mode: optional (if not using
     * zmailAutoProvLdapBindDn) LDAP search filter template for account auto
     * provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1228)
    public void unsetAutoProvLdapSearchFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: optional (if not using
     * zmailAutoProvLdapBindDn) MANUAL mode: optional (if not using
     * zmailAutoProvLdapBindDn) LDAP search filter template for account auto
     * provisioning. For LAZY and MANUAL modes, either
     * zmailAutoProvLdapSearchFilter or zmailAutoProvLdapBindDn has to be
     * set. If both are set, zmailAutoProvLdapSearchFilter will take
     * precedence. Supported place holders: %n = username with @ (or without,
     * if no @ was specified) %u = username with @ removed %d = domain as
     * foo.com %D = domain as dc=foo,dc=com
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1228)
    public Map<String,Object> unsetAutoProvLdapSearchFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapSearchFilter, "");
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Default
     * is FALSE. Whether to use startTLS when accessing the external LDAP
     * server for auto provision.
     *
     * @return zmailAutoProvLdapStartTlsEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1224)
    public boolean isAutoProvLdapStartTlsEnabled() {
        return getBooleanAttr(Provisioning.A_zmailAutoProvLdapStartTlsEnabled, false);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Default
     * is FALSE. Whether to use startTLS when accessing the external LDAP
     * server for auto provision.
     *
     * @param zmailAutoProvLdapStartTlsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1224)
    public void setAutoProvLdapStartTlsEnabled(boolean zmailAutoProvLdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapStartTlsEnabled, zmailAutoProvLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Default
     * is FALSE. Whether to use startTLS when accessing the external LDAP
     * server for auto provision.
     *
     * @param zmailAutoProvLdapStartTlsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1224)
    public Map<String,Object> setAutoProvLdapStartTlsEnabled(boolean zmailAutoProvLdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapStartTlsEnabled, zmailAutoProvLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Default
     * is FALSE. Whether to use startTLS when accessing the external LDAP
     * server for auto provision.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1224)
    public void unsetAutoProvLdapStartTlsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapStartTlsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Default
     * is FALSE. Whether to use startTLS when accessing the external LDAP
     * server for auto provision.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1224)
    public Map<String,Object> unsetAutoProvLdapStartTlsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapStartTlsEnabled, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * URL of the external LDAP source for auto provision.
     *
     * @return zmailAutoProvLdapURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1223)
    public String getAutoProvLdapURL() {
        return getAttr(Provisioning.A_zmailAutoProvLdapURL, null);
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * URL of the external LDAP source for auto provision.
     *
     * @param zmailAutoProvLdapURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1223)
    public void setAutoProvLdapURL(String zmailAutoProvLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapURL, zmailAutoProvLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * URL of the external LDAP source for auto provision.
     *
     * @param zmailAutoProvLdapURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1223)
    public Map<String,Object> setAutoProvLdapURL(String zmailAutoProvLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapURL, zmailAutoProvLdapURL);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * URL of the external LDAP source for auto provision.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1223)
    public void unsetAutoProvLdapURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: required MANUAL mode: required LDAP
     * URL of the external LDAP source for auto provision.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1223)
    public Map<String,Object> unsetAutoProvLdapURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLdapURL, "");
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Class
     * name of auto provision listener. The class must implement the
     * org.zmail.cs.account.Account.AutoProvisionListener interface. The
     * singleton listener instance is invoked after each account is auto
     * created in Zmail. Listener can be plugged in as a server extension to
     * handle tasks like updating the account auto provision status in the
     * external LDAP directory. At each eager provision interval, ZCS does an
     * LDAP search based on the value configured in
     * zmailAutoProvLdapSearchFilter. Returned entries from this search are
     * candidates to be auto provisioned in this batch. The
     * zmailAutoProvLdapSearchFilter should include an assertion that will
     * only hit entries in the external directory that have not yet been
     * provisioned in ZCS, otherwise it&#039;s likely the same entries will
     * be repeated pulled in to ZCS. After an account is auto provisioned in
     * ZCS,
     * org.zmail.cs.account.Account.AutoProvisionListener.postCreate(Domain
     * domain, Account acct, String externalDN) will be called by the auto
     * provisioning framework. Customer can implement the
     * AutoProvisionListener interface in a ZCS server extension and get
     * their AutoProvisionListener.postCreate() get called. The
     * implementation of customer&#039;s postCreate method can be, for
     * example, setting an attribute in the external directory on the account
     * just provisioned in ZCS. The attribute can be included as a condition
     * in the zmailAutoProvLdapSearchFilter, so the entry won&#039;t be
     * returned again by the LDAP search in the next interval.
     *
     * @return zmailAutoProvListenerClass, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1233)
    public String getAutoProvListenerClass() {
        return getAttr(Provisioning.A_zmailAutoProvListenerClass, null);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Class
     * name of auto provision listener. The class must implement the
     * org.zmail.cs.account.Account.AutoProvisionListener interface. The
     * singleton listener instance is invoked after each account is auto
     * created in Zmail. Listener can be plugged in as a server extension to
     * handle tasks like updating the account auto provision status in the
     * external LDAP directory. At each eager provision interval, ZCS does an
     * LDAP search based on the value configured in
     * zmailAutoProvLdapSearchFilter. Returned entries from this search are
     * candidates to be auto provisioned in this batch. The
     * zmailAutoProvLdapSearchFilter should include an assertion that will
     * only hit entries in the external directory that have not yet been
     * provisioned in ZCS, otherwise it&#039;s likely the same entries will
     * be repeated pulled in to ZCS. After an account is auto provisioned in
     * ZCS,
     * org.zmail.cs.account.Account.AutoProvisionListener.postCreate(Domain
     * domain, Account acct, String externalDN) will be called by the auto
     * provisioning framework. Customer can implement the
     * AutoProvisionListener interface in a ZCS server extension and get
     * their AutoProvisionListener.postCreate() get called. The
     * implementation of customer&#039;s postCreate method can be, for
     * example, setting an attribute in the external directory on the account
     * just provisioned in ZCS. The attribute can be included as a condition
     * in the zmailAutoProvLdapSearchFilter, so the entry won&#039;t be
     * returned again by the LDAP search in the next interval.
     *
     * @param zmailAutoProvListenerClass new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1233)
    public void setAutoProvListenerClass(String zmailAutoProvListenerClass) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvListenerClass, zmailAutoProvListenerClass);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Class
     * name of auto provision listener. The class must implement the
     * org.zmail.cs.account.Account.AutoProvisionListener interface. The
     * singleton listener instance is invoked after each account is auto
     * created in Zmail. Listener can be plugged in as a server extension to
     * handle tasks like updating the account auto provision status in the
     * external LDAP directory. At each eager provision interval, ZCS does an
     * LDAP search based on the value configured in
     * zmailAutoProvLdapSearchFilter. Returned entries from this search are
     * candidates to be auto provisioned in this batch. The
     * zmailAutoProvLdapSearchFilter should include an assertion that will
     * only hit entries in the external directory that have not yet been
     * provisioned in ZCS, otherwise it&#039;s likely the same entries will
     * be repeated pulled in to ZCS. After an account is auto provisioned in
     * ZCS,
     * org.zmail.cs.account.Account.AutoProvisionListener.postCreate(Domain
     * domain, Account acct, String externalDN) will be called by the auto
     * provisioning framework. Customer can implement the
     * AutoProvisionListener interface in a ZCS server extension and get
     * their AutoProvisionListener.postCreate() get called. The
     * implementation of customer&#039;s postCreate method can be, for
     * example, setting an attribute in the external directory on the account
     * just provisioned in ZCS. The attribute can be included as a condition
     * in the zmailAutoProvLdapSearchFilter, so the entry won&#039;t be
     * returned again by the LDAP search in the next interval.
     *
     * @param zmailAutoProvListenerClass new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1233)
    public Map<String,Object> setAutoProvListenerClass(String zmailAutoProvListenerClass, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvListenerClass, zmailAutoProvListenerClass);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Class
     * name of auto provision listener. The class must implement the
     * org.zmail.cs.account.Account.AutoProvisionListener interface. The
     * singleton listener instance is invoked after each account is auto
     * created in Zmail. Listener can be plugged in as a server extension to
     * handle tasks like updating the account auto provision status in the
     * external LDAP directory. At each eager provision interval, ZCS does an
     * LDAP search based on the value configured in
     * zmailAutoProvLdapSearchFilter. Returned entries from this search are
     * candidates to be auto provisioned in this batch. The
     * zmailAutoProvLdapSearchFilter should include an assertion that will
     * only hit entries in the external directory that have not yet been
     * provisioned in ZCS, otherwise it&#039;s likely the same entries will
     * be repeated pulled in to ZCS. After an account is auto provisioned in
     * ZCS,
     * org.zmail.cs.account.Account.AutoProvisionListener.postCreate(Domain
     * domain, Account acct, String externalDN) will be called by the auto
     * provisioning framework. Customer can implement the
     * AutoProvisionListener interface in a ZCS server extension and get
     * their AutoProvisionListener.postCreate() get called. The
     * implementation of customer&#039;s postCreate method can be, for
     * example, setting an attribute in the external directory on the account
     * just provisioned in ZCS. The attribute can be included as a condition
     * in the zmailAutoProvLdapSearchFilter, so the entry won&#039;t be
     * returned again by the LDAP search in the next interval.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1233)
    public void unsetAutoProvListenerClass() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvListenerClass, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Class
     * name of auto provision listener. The class must implement the
     * org.zmail.cs.account.Account.AutoProvisionListener interface. The
     * singleton listener instance is invoked after each account is auto
     * created in Zmail. Listener can be plugged in as a server extension to
     * handle tasks like updating the account auto provision status in the
     * external LDAP directory. At each eager provision interval, ZCS does an
     * LDAP search based on the value configured in
     * zmailAutoProvLdapSearchFilter. Returned entries from this search are
     * candidates to be auto provisioned in this batch. The
     * zmailAutoProvLdapSearchFilter should include an assertion that will
     * only hit entries in the external directory that have not yet been
     * provisioned in ZCS, otherwise it&#039;s likely the same entries will
     * be repeated pulled in to ZCS. After an account is auto provisioned in
     * ZCS,
     * org.zmail.cs.account.Account.AutoProvisionListener.postCreate(Domain
     * domain, Account acct, String externalDN) will be called by the auto
     * provisioning framework. Customer can implement the
     * AutoProvisionListener interface in a ZCS server extension and get
     * their AutoProvisionListener.postCreate() get called. The
     * implementation of customer&#039;s postCreate method can be, for
     * example, setting an attribute in the external directory on the account
     * just provisioned in ZCS. The attribute can be included as a condition
     * in the zmailAutoProvLdapSearchFilter, so the entry won&#039;t be
     * returned again by the LDAP search in the next interval.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1233)
    public Map<String,Object> unsetAutoProvListenerClass(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvListenerClass, "");
        return attrs;
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A For EAGER auto provision, a domain can be
     * scheduled on multiple server. To avoid conflict, only one server can
     * perform provisioning for a domain at one time. This attribute servers
     * a lock for the test-and-set LDAP operation to synchronize EAGER auto
     * provision attempts between servers.
     *
     * @return zmailAutoProvLock, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1236)
    public String getAutoProvLock() {
        return getAttr(Provisioning.A_zmailAutoProvLock, null);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A For EAGER auto provision, a domain can be
     * scheduled on multiple server. To avoid conflict, only one server can
     * perform provisioning for a domain at one time. This attribute servers
     * a lock for the test-and-set LDAP operation to synchronize EAGER auto
     * provision attempts between servers.
     *
     * @param zmailAutoProvLock new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1236)
    public void setAutoProvLock(String zmailAutoProvLock) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLock, zmailAutoProvLock);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A For EAGER auto provision, a domain can be
     * scheduled on multiple server. To avoid conflict, only one server can
     * perform provisioning for a domain at one time. This attribute servers
     * a lock for the test-and-set LDAP operation to synchronize EAGER auto
     * provision attempts between servers.
     *
     * @param zmailAutoProvLock new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1236)
    public Map<String,Object> setAutoProvLock(String zmailAutoProvLock, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLock, zmailAutoProvLock);
        return attrs;
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A For EAGER auto provision, a domain can be
     * scheduled on multiple server. To avoid conflict, only one server can
     * perform provisioning for a domain at one time. This attribute servers
     * a lock for the test-and-set LDAP operation to synchronize EAGER auto
     * provision attempts between servers.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1236)
    public void unsetAutoProvLock() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLock, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: for Zmail internal use only - do not change it. LAZY
     * mode: N/A MANUAL mode: N/A For EAGER auto provision, a domain can be
     * scheduled on multiple server. To avoid conflict, only one server can
     * perform provisioning for a domain at one time. This attribute servers
     * a lock for the test-and-set LDAP operation to synchronize EAGER auto
     * provision attempts between servers.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1236)
    public Map<String,Object> unsetAutoProvLock(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvLock, "");
        return attrs;
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @return zmailAutoProvMode, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public String[] getAutoProvModeAsString() {
        return getMultiAttr(Provisioning.A_zmailAutoProvMode);
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @param zmailAutoProvMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public void setAutoProvMode(ZAttrProvisioning.AutoProvMode zmailAutoProvMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvMode, zmailAutoProvMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @param zmailAutoProvMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public Map<String,Object> setAutoProvMode(ZAttrProvisioning.AutoProvMode zmailAutoProvMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvMode, zmailAutoProvMode.toString());
        return attrs;
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @param zmailAutoProvMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public void setAutoProvModeAsString(String[] zmailAutoProvMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvMode, zmailAutoProvMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @param zmailAutoProvMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public Map<String,Object> setAutoProvModeAsString(String[] zmailAutoProvMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvMode, zmailAutoProvMode);
        return attrs;
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public void unsetAutoProvMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Auto provision modes enabled. Multiple modes can be enabled on a
     * domain. EAGER: A server maintenance thread automatically polls the
     * configured external auto provision LDAP source at a configured
     * interval for entries due to be auto provisioned in Zmail, and then
     * auto creates the accounts in Zmail directory. LAZY: auto creates the
     * Zmail account when user first login via one of the external auth
     * mechanisms enabled for auto provisioning. Auth mechanisms enabled for
     * auto provisioning are configured in zmailAutoProvAuthMech. MANUAL:
     * admin to search from the configured external auto provision LDAP
     * source and select an entry from the search result to create the
     * corresponding Zmail account for the external entry. In all cases,
     * localpart of the Zmail account is mapped from an attribute on the
     * external entry based on zmailAutoProvAccountNameMap. The Zmail
     * account is populated with attributes mapped from the external entry
     * based on zmailAutoProvAttrMap.
     *
     * <p>Valid values: [MANUAL, LAZY, EAGER]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1221)
    public Map<String,Object> unsetAutoProvMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvMode, "");
        return attrs;
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @return zmailAutoProvNotificationBody, or "Your account has been auto provisioned.  Your email address is ${ACCOUNT_ADDRESS}." if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1357)
    public String getAutoProvNotificationBody() {
        return getAttr(Provisioning.A_zmailAutoProvNotificationBody, "Your account has been auto provisioned.  Your email address is ${ACCOUNT_ADDRESS}.");
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @param zmailAutoProvNotificationBody new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1357)
    public void setAutoProvNotificationBody(String zmailAutoProvNotificationBody) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationBody, zmailAutoProvNotificationBody);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @param zmailAutoProvNotificationBody new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1357)
    public Map<String,Object> setAutoProvNotificationBody(String zmailAutoProvNotificationBody, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationBody, zmailAutoProvNotificationBody);
        return attrs;
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1357)
    public void unsetAutoProvNotificationBody() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationBody, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1357)
    public Map<String,Object> unsetAutoProvNotificationBody(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationBody, "");
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Email
     * address to put in the From header for the notification email to the
     * newly created account. If not set, no notification email will sent to
     * the newly created account.
     *
     * @return zmailAutoProvNotificationFromAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1232)
    public String getAutoProvNotificationFromAddress() {
        return getAttr(Provisioning.A_zmailAutoProvNotificationFromAddress, null);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Email
     * address to put in the From header for the notification email to the
     * newly created account. If not set, no notification email will sent to
     * the newly created account.
     *
     * @param zmailAutoProvNotificationFromAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1232)
    public void setAutoProvNotificationFromAddress(String zmailAutoProvNotificationFromAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationFromAddress, zmailAutoProvNotificationFromAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Email
     * address to put in the From header for the notification email to the
     * newly created account. If not set, no notification email will sent to
     * the newly created account.
     *
     * @param zmailAutoProvNotificationFromAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1232)
    public Map<String,Object> setAutoProvNotificationFromAddress(String zmailAutoProvNotificationFromAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationFromAddress, zmailAutoProvNotificationFromAddress);
        return attrs;
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Email
     * address to put in the From header for the notification email to the
     * newly created account. If not set, no notification email will sent to
     * the newly created account.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1232)
    public void unsetAutoProvNotificationFromAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationFromAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: optional LAZY mode: optional MANUAL mode: optional Email
     * address to put in the From header for the notification email to the
     * newly created account. If not set, no notification email will sent to
     * the newly created account.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1232)
    public Map<String,Object> unsetAutoProvNotificationFromAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationFromAddress, "");
        return attrs;
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @return zmailAutoProvNotificationSubject, or "New account auto provisioned" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1356)
    public String getAutoProvNotificationSubject() {
        return getAttr(Provisioning.A_zmailAutoProvNotificationSubject, "New account auto provisioned");
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @param zmailAutoProvNotificationSubject new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1356)
    public void setAutoProvNotificationSubject(String zmailAutoProvNotificationSubject) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationSubject, zmailAutoProvNotificationSubject);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @param zmailAutoProvNotificationSubject new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1356)
    public Map<String,Object> setAutoProvNotificationSubject(String zmailAutoProvNotificationSubject, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationSubject, zmailAutoProvNotificationSubject);
        return attrs;
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1356)
    public void unsetAutoProvNotificationSubject() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationSubject, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Template used to construct the subject of the notification message
     * sent to the user when the user&#039;s account is auto provisioned.
     * Supported variables: ${ACCOUNT_ADDRESS}, ${ACCOUNT_DISPLAY_NAME}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1356)
    public Map<String,Object> unsetAutoProvNotificationSubject(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvNotificationSubject, "");
        return attrs;
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @return zmailAvailableSkin, or empty array if unset
     */
    @ZAttr(id=364)
    public String[] getAvailableSkin() {
        return getMultiAttr(Provisioning.A_zmailAvailableSkin);
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param zmailAvailableSkin new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=364)
    public void setAvailableSkin(String[] zmailAvailableSkin) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAvailableSkin, zmailAvailableSkin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param zmailAvailableSkin new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=364)
    public Map<String,Object> setAvailableSkin(String[] zmailAvailableSkin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAvailableSkin, zmailAvailableSkin);
        return attrs;
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param zmailAvailableSkin new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=364)
    public void addAvailableSkin(String zmailAvailableSkin) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAvailableSkin, zmailAvailableSkin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param zmailAvailableSkin new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=364)
    public Map<String,Object> addAvailableSkin(String zmailAvailableSkin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAvailableSkin, zmailAvailableSkin);
        return attrs;
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param zmailAvailableSkin existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=364)
    public void removeAvailableSkin(String zmailAvailableSkin) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAvailableSkin, zmailAvailableSkin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param zmailAvailableSkin existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=364)
    public Map<String,Object> removeAvailableSkin(String zmailAvailableSkin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAvailableSkin, zmailAvailableSkin);
        return attrs;
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=364)
    public void unsetAvailableSkin() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAvailableSkin, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Skins available for this account. Fallback order is: 1. the normal
     * account/cos inheritance 2. if not set on account/cos, use the value on
     * the domain of the account
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=364)
    public Map<String,Object> unsetAvailableSkin(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAvailableSkin, "");
        return attrs;
    }

    /**
     * Realm for the basic auth challenge (WWW-Authenticate) header
     *
     * @return zmailBasicAuthRealm, or "Zmail" if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1098)
    public String getBasicAuthRealm() {
        return getAttr(Provisioning.A_zmailBasicAuthRealm, "Zmail");
    }

    /**
     * Realm for the basic auth challenge (WWW-Authenticate) header
     *
     * @param zmailBasicAuthRealm new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1098)
    public void setBasicAuthRealm(String zmailBasicAuthRealm) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBasicAuthRealm, zmailBasicAuthRealm);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Realm for the basic auth challenge (WWW-Authenticate) header
     *
     * @param zmailBasicAuthRealm new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1098)
    public Map<String,Object> setBasicAuthRealm(String zmailBasicAuthRealm, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBasicAuthRealm, zmailBasicAuthRealm);
        return attrs;
    }

    /**
     * Realm for the basic auth challenge (WWW-Authenticate) header
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1098)
    public void unsetBasicAuthRealm() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBasicAuthRealm, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Realm for the basic auth challenge (WWW-Authenticate) header
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1098)
    public Map<String,Object> unsetBasicAuthRealm(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBasicAuthRealm, "");
        return attrs;
    }

    /**
     * list of disabled fields in calendar location web UI
     *
     * @return zmailCalendarLocationDisabledFields, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1218)
    public String getCalendarLocationDisabledFields() {
        return getAttr(Provisioning.A_zmailCalendarLocationDisabledFields, null);
    }

    /**
     * list of disabled fields in calendar location web UI
     *
     * @param zmailCalendarLocationDisabledFields new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1218)
    public void setCalendarLocationDisabledFields(String zmailCalendarLocationDisabledFields) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarLocationDisabledFields, zmailCalendarLocationDisabledFields);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of disabled fields in calendar location web UI
     *
     * @param zmailCalendarLocationDisabledFields new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1218)
    public Map<String,Object> setCalendarLocationDisabledFields(String zmailCalendarLocationDisabledFields, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarLocationDisabledFields, zmailCalendarLocationDisabledFields);
        return attrs;
    }

    /**
     * list of disabled fields in calendar location web UI
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1218)
    public void unsetCalendarLocationDisabledFields() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarLocationDisabledFields, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of disabled fields in calendar location web UI
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1218)
    public Map<String,Object> unsetCalendarLocationDisabledFields(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarLocationDisabledFields, "");
        return attrs;
    }

    /**
     * change password URL
     *
     * @return zmailChangePasswordURL, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=777)
    public String getChangePasswordURL() {
        return getAttr(Provisioning.A_zmailChangePasswordURL, null);
    }

    /**
     * change password URL
     *
     * @param zmailChangePasswordURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=777)
    public void setChangePasswordURL(String zmailChangePasswordURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailChangePasswordURL, zmailChangePasswordURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * change password URL
     *
     * @param zmailChangePasswordURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=777)
    public Map<String,Object> setChangePasswordURL(String zmailChangePasswordURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailChangePasswordURL, zmailChangePasswordURL);
        return attrs;
    }

    /**
     * change password URL
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=777)
    public void unsetChangePasswordURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailChangePasswordURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * change password URL
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=777)
    public Map<String,Object> unsetChangePasswordURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailChangePasswordURL, "");
        return attrs;
    }

    /**
     * time object was created
     *
     * <p>Use getCreateTimestampAsString to access value as a string.
     *
     * @see #getCreateTimestampAsString()
     *
     * @return zmailCreateTimestamp as Date, null if unset or unable to parse
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Date getCreateTimestamp() {
        return getGeneralizedTimeAttr(Provisioning.A_zmailCreateTimestamp, null);
    }

    /**
     * time object was created
     *
     * @return zmailCreateTimestamp, or null if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public String getCreateTimestampAsString() {
        return getAttr(Provisioning.A_zmailCreateTimestamp, null);
    }

    /**
     * time object was created
     *
     * @param zmailCreateTimestamp new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public void setCreateTimestamp(Date zmailCreateTimestamp) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCreateTimestamp, zmailCreateTimestamp==null ? "" : DateUtil.toGeneralizedTime(zmailCreateTimestamp));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time object was created
     *
     * @param zmailCreateTimestamp new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Map<String,Object> setCreateTimestamp(Date zmailCreateTimestamp, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCreateTimestamp, zmailCreateTimestamp==null ? "" : DateUtil.toGeneralizedTime(zmailCreateTimestamp));
        return attrs;
    }

    /**
     * time object was created
     *
     * @param zmailCreateTimestamp new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public void setCreateTimestampAsString(String zmailCreateTimestamp) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCreateTimestamp, zmailCreateTimestamp);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time object was created
     *
     * @param zmailCreateTimestamp new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Map<String,Object> setCreateTimestampAsString(String zmailCreateTimestamp, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCreateTimestamp, zmailCreateTimestamp);
        return attrs;
    }

    /**
     * time object was created
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public void unsetCreateTimestamp() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCreateTimestamp, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time object was created
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Map<String,Object> unsetCreateTimestamp(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCreateTimestamp, "");
        return attrs;
    }

    /**
     * This attribute is used for DNS check by customers that configure their
     * MX to point at spam relays or other non-zmail inbox smtp servers
     *
     * @return zmailDNSCheckHostname, or null if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=744)
    public String getDNSCheckHostname() {
        return getAttr(Provisioning.A_zmailDNSCheckHostname, null);
    }

    /**
     * This attribute is used for DNS check by customers that configure their
     * MX to point at spam relays or other non-zmail inbox smtp servers
     *
     * @param zmailDNSCheckHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=744)
    public void setDNSCheckHostname(String zmailDNSCheckHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDNSCheckHostname, zmailDNSCheckHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * This attribute is used for DNS check by customers that configure their
     * MX to point at spam relays or other non-zmail inbox smtp servers
     *
     * @param zmailDNSCheckHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=744)
    public Map<String,Object> setDNSCheckHostname(String zmailDNSCheckHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDNSCheckHostname, zmailDNSCheckHostname);
        return attrs;
    }

    /**
     * This attribute is used for DNS check by customers that configure their
     * MX to point at spam relays or other non-zmail inbox smtp servers
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=744)
    public void unsetDNSCheckHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDNSCheckHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * This attribute is used for DNS check by customers that configure their
     * MX to point at spam relays or other non-zmail inbox smtp servers
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=744)
    public Map<String,Object> unsetDNSCheckHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDNSCheckHostname, "");
        return attrs;
    }

    /**
     * maximum aggregate quota for the domain in bytes
     *
     * @return zmailDomainAggregateQuota, or 0 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1327)
    public long getDomainAggregateQuota() {
        return getLongAttr(Provisioning.A_zmailDomainAggregateQuota, 0L);
    }

    /**
     * maximum aggregate quota for the domain in bytes
     *
     * @param zmailDomainAggregateQuota new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1327)
    public void setDomainAggregateQuota(long zmailDomainAggregateQuota) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuota, Long.toString(zmailDomainAggregateQuota));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum aggregate quota for the domain in bytes
     *
     * @param zmailDomainAggregateQuota new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1327)
    public Map<String,Object> setDomainAggregateQuota(long zmailDomainAggregateQuota, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuota, Long.toString(zmailDomainAggregateQuota));
        return attrs;
    }

    /**
     * maximum aggregate quota for the domain in bytes
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1327)
    public void unsetDomainAggregateQuota() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuota, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum aggregate quota for the domain in bytes
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1327)
    public Map<String,Object> unsetDomainAggregateQuota(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuota, "");
        return attrs;
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @return zmailDomainAggregateQuotaPolicy, or ZAttrProvisioning.DomainAggregateQuotaPolicy.ALLOWSENDRECEIVE if unset and/or has invalid value
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public ZAttrProvisioning.DomainAggregateQuotaPolicy getDomainAggregateQuotaPolicy() {
        try { String v = getAttr(Provisioning.A_zmailDomainAggregateQuotaPolicy); return v == null ? ZAttrProvisioning.DomainAggregateQuotaPolicy.ALLOWSENDRECEIVE : ZAttrProvisioning.DomainAggregateQuotaPolicy.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.DomainAggregateQuotaPolicy.ALLOWSENDRECEIVE; }
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @return zmailDomainAggregateQuotaPolicy, or "ALLOWSENDRECEIVE" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public String getDomainAggregateQuotaPolicyAsString() {
        return getAttr(Provisioning.A_zmailDomainAggregateQuotaPolicy, "ALLOWSENDRECEIVE");
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @param zmailDomainAggregateQuotaPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public void setDomainAggregateQuotaPolicy(ZAttrProvisioning.DomainAggregateQuotaPolicy zmailDomainAggregateQuotaPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaPolicy, zmailDomainAggregateQuotaPolicy.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @param zmailDomainAggregateQuotaPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public Map<String,Object> setDomainAggregateQuotaPolicy(ZAttrProvisioning.DomainAggregateQuotaPolicy zmailDomainAggregateQuotaPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaPolicy, zmailDomainAggregateQuotaPolicy.toString());
        return attrs;
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @param zmailDomainAggregateQuotaPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public void setDomainAggregateQuotaPolicyAsString(String zmailDomainAggregateQuotaPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaPolicy, zmailDomainAggregateQuotaPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @param zmailDomainAggregateQuotaPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public Map<String,Object> setDomainAggregateQuotaPolicyAsString(String zmailDomainAggregateQuotaPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaPolicy, zmailDomainAggregateQuotaPolicy);
        return attrs;
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public void unsetDomainAggregateQuotaPolicy() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaPolicy, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * policy for a domain whose quota usage is above
     * zmailDomainAggregateQuota
     *
     * <p>Valid values: [BLOCKSENDRECEIVE, BLOCKSEND, ALLOWSENDRECEIVE]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1329)
    public Map<String,Object> unsetDomainAggregateQuotaPolicy(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaPolicy, "");
        return attrs;
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @return zmailDomainAggregateQuotaWarnEmailRecipient, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public String[] getDomainAggregateQuotaWarnEmailRecipient() {
        return getMultiAttr(Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient);
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param zmailDomainAggregateQuotaWarnEmailRecipient new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public void setDomainAggregateQuotaWarnEmailRecipient(String[] zmailDomainAggregateQuotaWarnEmailRecipient) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, zmailDomainAggregateQuotaWarnEmailRecipient);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param zmailDomainAggregateQuotaWarnEmailRecipient new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public Map<String,Object> setDomainAggregateQuotaWarnEmailRecipient(String[] zmailDomainAggregateQuotaWarnEmailRecipient, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, zmailDomainAggregateQuotaWarnEmailRecipient);
        return attrs;
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param zmailDomainAggregateQuotaWarnEmailRecipient new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public void addDomainAggregateQuotaWarnEmailRecipient(String zmailDomainAggregateQuotaWarnEmailRecipient) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, zmailDomainAggregateQuotaWarnEmailRecipient);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param zmailDomainAggregateQuotaWarnEmailRecipient new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public Map<String,Object> addDomainAggregateQuotaWarnEmailRecipient(String zmailDomainAggregateQuotaWarnEmailRecipient, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, zmailDomainAggregateQuotaWarnEmailRecipient);
        return attrs;
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param zmailDomainAggregateQuotaWarnEmailRecipient existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public void removeDomainAggregateQuotaWarnEmailRecipient(String zmailDomainAggregateQuotaWarnEmailRecipient) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, zmailDomainAggregateQuotaWarnEmailRecipient);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param zmailDomainAggregateQuotaWarnEmailRecipient existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public Map<String,Object> removeDomainAggregateQuotaWarnEmailRecipient(String zmailDomainAggregateQuotaWarnEmailRecipient, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, zmailDomainAggregateQuotaWarnEmailRecipient);
        return attrs;
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public void unsetDomainAggregateQuotaWarnEmailRecipient() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * email recipients to be notified when zmailAggregateQuotaLastUsage
     * reaches zmailDomainAggregateQuotaWarnPercent of the
     * zmailDomainAggregateQuota
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1331)
    public Map<String,Object> unsetDomainAggregateQuotaWarnEmailRecipient(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnEmailRecipient, "");
        return attrs;
    }

    /**
     * percentage threshold for domain aggregate quota warnings
     *
     * @return zmailDomainAggregateQuotaWarnPercent, or 80 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1330)
    public int getDomainAggregateQuotaWarnPercent() {
        return getIntAttr(Provisioning.A_zmailDomainAggregateQuotaWarnPercent, 80);
    }

    /**
     * percentage threshold for domain aggregate quota warnings
     *
     * @param zmailDomainAggregateQuotaWarnPercent new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1330)
    public void setDomainAggregateQuotaWarnPercent(int zmailDomainAggregateQuotaWarnPercent) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnPercent, Integer.toString(zmailDomainAggregateQuotaWarnPercent));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * percentage threshold for domain aggregate quota warnings
     *
     * @param zmailDomainAggregateQuotaWarnPercent new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1330)
    public Map<String,Object> setDomainAggregateQuotaWarnPercent(int zmailDomainAggregateQuotaWarnPercent, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnPercent, Integer.toString(zmailDomainAggregateQuotaWarnPercent));
        return attrs;
    }

    /**
     * percentage threshold for domain aggregate quota warnings
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1330)
    public void unsetDomainAggregateQuotaWarnPercent() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnPercent, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * percentage threshold for domain aggregate quota warnings
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1330)
    public Map<String,Object> unsetDomainAggregateQuotaWarnPercent(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAggregateQuotaWarnPercent, "");
        return attrs;
    }

    /**
     * zmailId of domain alias target
     *
     * @return zmailDomainAliasTargetId, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=775)
    public String getDomainAliasTargetId() {
        return getAttr(Provisioning.A_zmailDomainAliasTargetId, null);
    }

    /**
     * zmailId of domain alias target
     *
     * @param zmailDomainAliasTargetId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=775)
    public void setDomainAliasTargetId(String zmailDomainAliasTargetId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAliasTargetId, zmailDomainAliasTargetId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of domain alias target
     *
     * @param zmailDomainAliasTargetId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=775)
    public Map<String,Object> setDomainAliasTargetId(String zmailDomainAliasTargetId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAliasTargetId, zmailDomainAliasTargetId);
        return attrs;
    }

    /**
     * zmailId of domain alias target
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=775)
    public void unsetDomainAliasTargetId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAliasTargetId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of domain alias target
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=775)
    public Map<String,Object> unsetDomainAliasTargetId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainAliasTargetId, "");
        return attrs;
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @return zmailDomainCOSMaxAccounts, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public String[] getDomainCOSMaxAccounts() {
        return getMultiAttr(Provisioning.A_zmailDomainCOSMaxAccounts);
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param zmailDomainCOSMaxAccounts new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public void setDomainCOSMaxAccounts(String[] zmailDomainCOSMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainCOSMaxAccounts, zmailDomainCOSMaxAccounts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param zmailDomainCOSMaxAccounts new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public Map<String,Object> setDomainCOSMaxAccounts(String[] zmailDomainCOSMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainCOSMaxAccounts, zmailDomainCOSMaxAccounts);
        return attrs;
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param zmailDomainCOSMaxAccounts new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public void addDomainCOSMaxAccounts(String zmailDomainCOSMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailDomainCOSMaxAccounts, zmailDomainCOSMaxAccounts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param zmailDomainCOSMaxAccounts new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public Map<String,Object> addDomainCOSMaxAccounts(String zmailDomainCOSMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailDomainCOSMaxAccounts, zmailDomainCOSMaxAccounts);
        return attrs;
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param zmailDomainCOSMaxAccounts existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public void removeDomainCOSMaxAccounts(String zmailDomainCOSMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailDomainCOSMaxAccounts, zmailDomainCOSMaxAccounts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param zmailDomainCOSMaxAccounts existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public Map<String,Object> removeDomainCOSMaxAccounts(String zmailDomainCOSMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailDomainCOSMaxAccounts, zmailDomainCOSMaxAccounts);
        return attrs;
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public void unsetDomainCOSMaxAccounts() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainCOSMaxAccounts, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to be assigned to specified COSes
     * in a domain. Values are in the format of
     * {zmailId-of-a-cos}:{max-accounts}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=714)
    public Map<String,Object> unsetDomainCOSMaxAccounts(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainCOSMaxAccounts, "");
        return attrs;
    }

    /**
     * COS zmailID
     *
     * @return zmailDomainDefaultCOSId, or null if unset
     */
    @ZAttr(id=299)
    public String getDomainDefaultCOSId() {
        return getAttr(Provisioning.A_zmailDomainDefaultCOSId, null);
    }

    /**
     * COS zmailID
     *
     * @param zmailDomainDefaultCOSId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=299)
    public void setDomainDefaultCOSId(String zmailDomainDefaultCOSId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultCOSId, zmailDomainDefaultCOSId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * COS zmailID
     *
     * @param zmailDomainDefaultCOSId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=299)
    public Map<String,Object> setDomainDefaultCOSId(String zmailDomainDefaultCOSId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultCOSId, zmailDomainDefaultCOSId);
        return attrs;
    }

    /**
     * COS zmailID
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=299)
    public void unsetDomainDefaultCOSId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultCOSId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * COS zmailID
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=299)
    public Map<String,Object> unsetDomainDefaultCOSId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultCOSId, "");
        return attrs;
    }

    /**
     * id of the default COS for external user accounts
     *
     * @return zmailDomainDefaultExternalUserCOSId, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1247)
    public String getDomainDefaultExternalUserCOSId() {
        return getAttr(Provisioning.A_zmailDomainDefaultExternalUserCOSId, null);
    }

    /**
     * id of the default COS for external user accounts
     *
     * @param zmailDomainDefaultExternalUserCOSId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1247)
    public void setDomainDefaultExternalUserCOSId(String zmailDomainDefaultExternalUserCOSId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultExternalUserCOSId, zmailDomainDefaultExternalUserCOSId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * id of the default COS for external user accounts
     *
     * @param zmailDomainDefaultExternalUserCOSId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1247)
    public Map<String,Object> setDomainDefaultExternalUserCOSId(String zmailDomainDefaultExternalUserCOSId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultExternalUserCOSId, zmailDomainDefaultExternalUserCOSId);
        return attrs;
    }

    /**
     * id of the default COS for external user accounts
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1247)
    public void unsetDomainDefaultExternalUserCOSId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultExternalUserCOSId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * id of the default COS for external user accounts
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1247)
    public Map<String,Object> unsetDomainDefaultExternalUserCOSId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainDefaultExternalUserCOSId, "");
        return attrs;
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @return zmailDomainFeatureMaxAccounts, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public String[] getDomainFeatureMaxAccounts() {
        return getMultiAttr(Provisioning.A_zmailDomainFeatureMaxAccounts);
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param zmailDomainFeatureMaxAccounts new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public void setDomainFeatureMaxAccounts(String[] zmailDomainFeatureMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainFeatureMaxAccounts, zmailDomainFeatureMaxAccounts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param zmailDomainFeatureMaxAccounts new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public Map<String,Object> setDomainFeatureMaxAccounts(String[] zmailDomainFeatureMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainFeatureMaxAccounts, zmailDomainFeatureMaxAccounts);
        return attrs;
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param zmailDomainFeatureMaxAccounts new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public void addDomainFeatureMaxAccounts(String zmailDomainFeatureMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailDomainFeatureMaxAccounts, zmailDomainFeatureMaxAccounts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param zmailDomainFeatureMaxAccounts new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public Map<String,Object> addDomainFeatureMaxAccounts(String zmailDomainFeatureMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailDomainFeatureMaxAccounts, zmailDomainFeatureMaxAccounts);
        return attrs;
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param zmailDomainFeatureMaxAccounts existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public void removeDomainFeatureMaxAccounts(String zmailDomainFeatureMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailDomainFeatureMaxAccounts, zmailDomainFeatureMaxAccounts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param zmailDomainFeatureMaxAccounts existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public Map<String,Object> removeDomainFeatureMaxAccounts(String zmailDomainFeatureMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailDomainFeatureMaxAccounts, zmailDomainFeatureMaxAccounts);
        return attrs;
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public void unsetDomainFeatureMaxAccounts() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainFeatureMaxAccounts, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed to have specified features in a
     * domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=715)
    public Map<String,Object> unsetDomainFeatureMaxAccounts(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainFeatureMaxAccounts, "");
        return attrs;
    }

    /**
     * enable domain mandatory mail signature
     *
     * @return zmailDomainMandatoryMailSignatureEnabled, or false if unset
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1069)
    public boolean isDomainMandatoryMailSignatureEnabled() {
        return getBooleanAttr(Provisioning.A_zmailDomainMandatoryMailSignatureEnabled, false);
    }

    /**
     * enable domain mandatory mail signature
     *
     * @param zmailDomainMandatoryMailSignatureEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1069)
    public void setDomainMandatoryMailSignatureEnabled(boolean zmailDomainMandatoryMailSignatureEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureEnabled, zmailDomainMandatoryMailSignatureEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable domain mandatory mail signature
     *
     * @param zmailDomainMandatoryMailSignatureEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1069)
    public Map<String,Object> setDomainMandatoryMailSignatureEnabled(boolean zmailDomainMandatoryMailSignatureEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureEnabled, zmailDomainMandatoryMailSignatureEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * enable domain mandatory mail signature
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1069)
    public void unsetDomainMandatoryMailSignatureEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable domain mandatory mail signature
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1069)
    public Map<String,Object> unsetDomainMandatoryMailSignatureEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureEnabled, "");
        return attrs;
    }

    /**
     * domain mandatory mail html signature
     *
     * @return zmailDomainMandatoryMailSignatureHTML, or null if unset
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1071)
    public String getDomainMandatoryMailSignatureHTML() {
        return getAttr(Provisioning.A_zmailDomainMandatoryMailSignatureHTML, null);
    }

    /**
     * domain mandatory mail html signature
     *
     * @param zmailDomainMandatoryMailSignatureHTML new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1071)
    public void setDomainMandatoryMailSignatureHTML(String zmailDomainMandatoryMailSignatureHTML) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureHTML, zmailDomainMandatoryMailSignatureHTML);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain mandatory mail html signature
     *
     * @param zmailDomainMandatoryMailSignatureHTML new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1071)
    public Map<String,Object> setDomainMandatoryMailSignatureHTML(String zmailDomainMandatoryMailSignatureHTML, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureHTML, zmailDomainMandatoryMailSignatureHTML);
        return attrs;
    }

    /**
     * domain mandatory mail html signature
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1071)
    public void unsetDomainMandatoryMailSignatureHTML() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureHTML, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain mandatory mail html signature
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1071)
    public Map<String,Object> unsetDomainMandatoryMailSignatureHTML(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureHTML, "");
        return attrs;
    }

    /**
     * domain mandatory mail plain text signature
     *
     * @return zmailDomainMandatoryMailSignatureText, or null if unset
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1070)
    public String getDomainMandatoryMailSignatureText() {
        return getAttr(Provisioning.A_zmailDomainMandatoryMailSignatureText, null);
    }

    /**
     * domain mandatory mail plain text signature
     *
     * @param zmailDomainMandatoryMailSignatureText new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1070)
    public void setDomainMandatoryMailSignatureText(String zmailDomainMandatoryMailSignatureText) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureText, zmailDomainMandatoryMailSignatureText);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain mandatory mail plain text signature
     *
     * @param zmailDomainMandatoryMailSignatureText new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1070)
    public Map<String,Object> setDomainMandatoryMailSignatureText(String zmailDomainMandatoryMailSignatureText, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureText, zmailDomainMandatoryMailSignatureText);
        return attrs;
    }

    /**
     * domain mandatory mail plain text signature
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1070)
    public void unsetDomainMandatoryMailSignatureText() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureText, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain mandatory mail plain text signature
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.4
     */
    @ZAttr(id=1070)
    public Map<String,Object> unsetDomainMandatoryMailSignatureText(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMandatoryMailSignatureText, "");
        return attrs;
    }

    /**
     * maximum number of accounts allowed in a domain
     *
     * @return zmailDomainMaxAccounts, or -1 if unset
     */
    @ZAttr(id=400)
    public int getDomainMaxAccounts() {
        return getIntAttr(Provisioning.A_zmailDomainMaxAccounts, -1);
    }

    /**
     * maximum number of accounts allowed in a domain
     *
     * @param zmailDomainMaxAccounts new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=400)
    public void setDomainMaxAccounts(int zmailDomainMaxAccounts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMaxAccounts, Integer.toString(zmailDomainMaxAccounts));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed in a domain
     *
     * @param zmailDomainMaxAccounts new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=400)
    public Map<String,Object> setDomainMaxAccounts(int zmailDomainMaxAccounts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMaxAccounts, Integer.toString(zmailDomainMaxAccounts));
        return attrs;
    }

    /**
     * maximum number of accounts allowed in a domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=400)
    public void unsetDomainMaxAccounts() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMaxAccounts, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of accounts allowed in a domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=400)
    public Map<String,Object> unsetDomainMaxAccounts(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainMaxAccounts, "");
        return attrs;
    }

    /**
     * name of the domain
     *
     * @return zmailDomainName, or null if unset
     */
    @ZAttr(id=19)
    public String getDomainName() {
        return getAttr(Provisioning.A_zmailDomainName, null);
    }

    /**
     * name of the domain
     *
     * @param zmailDomainName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=19)
    public void setDomainName(String zmailDomainName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainName, zmailDomainName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name of the domain
     *
     * @param zmailDomainName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=19)
    public Map<String,Object> setDomainName(String zmailDomainName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainName, zmailDomainName);
        return attrs;
    }

    /**
     * name of the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=19)
    public void unsetDomainName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name of the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=19)
    public Map<String,Object> unsetDomainName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainName, "");
        return attrs;
    }

    /**
     * domain rename info/status
     *
     * @return zmailDomainRenameInfo, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=536)
    public String getDomainRenameInfo() {
        return getAttr(Provisioning.A_zmailDomainRenameInfo, null);
    }

    /**
     * domain rename info/status
     *
     * @param zmailDomainRenameInfo new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=536)
    public void setDomainRenameInfo(String zmailDomainRenameInfo) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainRenameInfo, zmailDomainRenameInfo);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain rename info/status
     *
     * @param zmailDomainRenameInfo new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=536)
    public Map<String,Object> setDomainRenameInfo(String zmailDomainRenameInfo, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainRenameInfo, zmailDomainRenameInfo);
        return attrs;
    }

    /**
     * domain rename info/status
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=536)
    public void unsetDomainRenameInfo() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainRenameInfo, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain rename info/status
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=536)
    public Map<String,Object> unsetDomainRenameInfo(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainRenameInfo, "");
        return attrs;
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @return zmailDomainStatus, or null if unset and/or has invalid value
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public ZAttrProvisioning.DomainStatus getDomainStatus() {
        try { String v = getAttr(Provisioning.A_zmailDomainStatus); return v == null ? null : ZAttrProvisioning.DomainStatus.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @return zmailDomainStatus, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public String getDomainStatusAsString() {
        return getAttr(Provisioning.A_zmailDomainStatus, null);
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @param zmailDomainStatus new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public void setDomainStatus(ZAttrProvisioning.DomainStatus zmailDomainStatus) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainStatus, zmailDomainStatus.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @param zmailDomainStatus new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public Map<String,Object> setDomainStatus(ZAttrProvisioning.DomainStatus zmailDomainStatus, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainStatus, zmailDomainStatus.toString());
        return attrs;
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @param zmailDomainStatus new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public void setDomainStatusAsString(String zmailDomainStatus) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainStatus, zmailDomainStatus);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @param zmailDomainStatus new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public Map<String,Object> setDomainStatusAsString(String zmailDomainStatus, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainStatus, zmailDomainStatus);
        return attrs;
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public void unsetDomainStatus() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainStatus, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * domain status. enum values are akin to those of zmailAccountStatus
     * but the status affects all accounts on the domain. See table below for
     * how zmailDomainStatus affects account status. active - see
     * zmailAccountStatus maintenance - see zmailAccountStatus locked - see
     * zmailAccountStatus closed - see zmailAccountStatus suspended -
     * maintenance + no creating/deleting/modifying accounts/DLs under the
     * domain. shutdown - suspended + cannot modify domain attrs + cannot
     * delete the domain Indicating server is doing major and lengthy
     * maintenance work on the domain, e.g. renaming the domain and moving
     * LDAP entries. Modification and deletion of the domain can only be done
     * internally by the server when it is safe to release the domain, they
     * cannot be done in admin console or zmprov. How zmailDomainStatus
     * affects account behavior : -------------------------------------
     * zmailDomainStatus account behavior
     * ------------------------------------- active zmailAccountStatus
     * locked zmailAccountStatus if it is maintenance or pending or closed,
     * else locked maintenance zmailAccountStatus if it is pending or
     * closed, else maintenance suspended zmailAccountStatus if it is
     * pending or closed, else maintenance shutdown zmailAccountStatus if it
     * is pending or closed, else maintenance closed closed
     *
     * <p>Valid values: [active, closed, locked, suspended, maintenance, shutdown]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=535)
    public Map<String,Object> unsetDomainStatus(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainStatus, "");
        return attrs;
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @return zmailDomainType, or null if unset and/or has invalid value
     */
    @ZAttr(id=212)
    public ZAttrProvisioning.DomainType getDomainType() {
        try { String v = getAttr(Provisioning.A_zmailDomainType); return v == null ? null : ZAttrProvisioning.DomainType.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @return zmailDomainType, or null if unset
     */
    @ZAttr(id=212)
    public String getDomainTypeAsString() {
        return getAttr(Provisioning.A_zmailDomainType, null);
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @param zmailDomainType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=212)
    public void setDomainType(ZAttrProvisioning.DomainType zmailDomainType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainType, zmailDomainType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @param zmailDomainType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=212)
    public Map<String,Object> setDomainType(ZAttrProvisioning.DomainType zmailDomainType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainType, zmailDomainType.toString());
        return attrs;
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @param zmailDomainType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=212)
    public void setDomainTypeAsString(String zmailDomainType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainType, zmailDomainType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @param zmailDomainType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=212)
    public Map<String,Object> setDomainTypeAsString(String zmailDomainType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainType, zmailDomainType);
        return attrs;
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=212)
    public void unsetDomainType() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainType, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * should be one of: local, alias
     *
     * <p>Valid values: [alias, local]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=212)
    public Map<String,Object> unsetDomainType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDomainType, "");
        return attrs;
    }

    /**
     * URL for posting error report popped up in WEB client
     *
     * @return zmailErrorReportUrl, or null if unset
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1075)
    public String getErrorReportUrl() {
        return getAttr(Provisioning.A_zmailErrorReportUrl, null);
    }

    /**
     * URL for posting error report popped up in WEB client
     *
     * @param zmailErrorReportUrl new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1075)
    public void setErrorReportUrl(String zmailErrorReportUrl) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailErrorReportUrl, zmailErrorReportUrl);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL for posting error report popped up in WEB client
     *
     * @param zmailErrorReportUrl new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1075)
    public Map<String,Object> setErrorReportUrl(String zmailErrorReportUrl, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailErrorReportUrl, zmailErrorReportUrl);
        return attrs;
    }

    /**
     * URL for posting error report popped up in WEB client
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1075)
    public void unsetErrorReportUrl() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailErrorReportUrl, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL for posting error report popped up in WEB client
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1075)
    public Map<String,Object> unsetErrorReportUrl(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailErrorReportUrl, "");
        return attrs;
    }

    /**
     * the handler class for getting all groups an account belongs to in the
     * external directory
     *
     * @return zmailExternalGroupHandlerClass, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1251)
    public String getExternalGroupHandlerClass() {
        return getAttr(Provisioning.A_zmailExternalGroupHandlerClass, null);
    }

    /**
     * the handler class for getting all groups an account belongs to in the
     * external directory
     *
     * @param zmailExternalGroupHandlerClass new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1251)
    public void setExternalGroupHandlerClass(String zmailExternalGroupHandlerClass) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupHandlerClass, zmailExternalGroupHandlerClass);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the handler class for getting all groups an account belongs to in the
     * external directory
     *
     * @param zmailExternalGroupHandlerClass new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1251)
    public Map<String,Object> setExternalGroupHandlerClass(String zmailExternalGroupHandlerClass, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupHandlerClass, zmailExternalGroupHandlerClass);
        return attrs;
    }

    /**
     * the handler class for getting all groups an account belongs to in the
     * external directory
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1251)
    public void unsetExternalGroupHandlerClass() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupHandlerClass, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the handler class for getting all groups an account belongs to in the
     * external directory
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1251)
    public Map<String,Object> unsetExternalGroupHandlerClass(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupHandlerClass, "");
        return attrs;
    }

    /**
     * LDAP search base for searching external LDAP groups
     *
     * @return zmailExternalGroupLdapSearchBase, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1249)
    public String getExternalGroupLdapSearchBase() {
        return getAttr(Provisioning.A_zmailExternalGroupLdapSearchBase, null);
    }

    /**
     * LDAP search base for searching external LDAP groups
     *
     * @param zmailExternalGroupLdapSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1249)
    public void setExternalGroupLdapSearchBase(String zmailExternalGroupLdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchBase, zmailExternalGroupLdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for searching external LDAP groups
     *
     * @param zmailExternalGroupLdapSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1249)
    public Map<String,Object> setExternalGroupLdapSearchBase(String zmailExternalGroupLdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchBase, zmailExternalGroupLdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for searching external LDAP groups
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1249)
    public void unsetExternalGroupLdapSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for searching external LDAP groups
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1249)
    public Map<String,Object> unsetExternalGroupLdapSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchBase, "");
        return attrs;
    }

    /**
     * LDAP search filter for searching external LDAP groups
     *
     * @return zmailExternalGroupLdapSearchFilter, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1250)
    public String getExternalGroupLdapSearchFilter() {
        return getAttr(Provisioning.A_zmailExternalGroupLdapSearchFilter, null);
    }

    /**
     * LDAP search filter for searching external LDAP groups
     *
     * @param zmailExternalGroupLdapSearchFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1250)
    public void setExternalGroupLdapSearchFilter(String zmailExternalGroupLdapSearchFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchFilter, zmailExternalGroupLdapSearchFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for searching external LDAP groups
     *
     * @param zmailExternalGroupLdapSearchFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1250)
    public Map<String,Object> setExternalGroupLdapSearchFilter(String zmailExternalGroupLdapSearchFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchFilter, zmailExternalGroupLdapSearchFilter);
        return attrs;
    }

    /**
     * LDAP search filter for searching external LDAP groups
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1250)
    public void unsetExternalGroupLdapSearchFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for searching external LDAP groups
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1250)
    public Map<String,Object> unsetExternalGroupLdapSearchFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalGroupLdapSearchFilter, "");
        return attrs;
    }

    /**
     * external imap hostname
     *
     * @return zmailExternalImapHostname, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=786)
    public String getExternalImapHostname() {
        return getAttr(Provisioning.A_zmailExternalImapHostname, null);
    }

    /**
     * external imap hostname
     *
     * @param zmailExternalImapHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=786)
    public void setExternalImapHostname(String zmailExternalImapHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapHostname, zmailExternalImapHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap hostname
     *
     * @param zmailExternalImapHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=786)
    public Map<String,Object> setExternalImapHostname(String zmailExternalImapHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapHostname, zmailExternalImapHostname);
        return attrs;
    }

    /**
     * external imap hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=786)
    public void unsetExternalImapHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=786)
    public Map<String,Object> unsetExternalImapHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapHostname, "");
        return attrs;
    }

    /**
     * external imap port
     *
     * <p>Use getExternalImapPortAsString to access value as a string.
     *
     * @see #getExternalImapPortAsString()
     *
     * @return zmailExternalImapPort, or -1 if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public int getExternalImapPort() {
        return getIntAttr(Provisioning.A_zmailExternalImapPort, -1);
    }

    /**
     * external imap port
     *
     * @return zmailExternalImapPort, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public String getExternalImapPortAsString() {
        return getAttr(Provisioning.A_zmailExternalImapPort, null);
    }

    /**
     * external imap port
     *
     * @param zmailExternalImapPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public void setExternalImapPort(int zmailExternalImapPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapPort, Integer.toString(zmailExternalImapPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap port
     *
     * @param zmailExternalImapPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public Map<String,Object> setExternalImapPort(int zmailExternalImapPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapPort, Integer.toString(zmailExternalImapPort));
        return attrs;
    }

    /**
     * external imap port
     *
     * @param zmailExternalImapPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public void setExternalImapPortAsString(String zmailExternalImapPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapPort, zmailExternalImapPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap port
     *
     * @param zmailExternalImapPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public Map<String,Object> setExternalImapPortAsString(String zmailExternalImapPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapPort, zmailExternalImapPort);
        return attrs;
    }

    /**
     * external imap port
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public void unsetExternalImapPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap port
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=782)
    public Map<String,Object> unsetExternalImapPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapPort, "");
        return attrs;
    }

    /**
     * external imap SSL hostname
     *
     * @return zmailExternalImapSSLHostname, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=787)
    public String getExternalImapSSLHostname() {
        return getAttr(Provisioning.A_zmailExternalImapSSLHostname, null);
    }

    /**
     * external imap SSL hostname
     *
     * @param zmailExternalImapSSLHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=787)
    public void setExternalImapSSLHostname(String zmailExternalImapSSLHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLHostname, zmailExternalImapSSLHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap SSL hostname
     *
     * @param zmailExternalImapSSLHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=787)
    public Map<String,Object> setExternalImapSSLHostname(String zmailExternalImapSSLHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLHostname, zmailExternalImapSSLHostname);
        return attrs;
    }

    /**
     * external imap SSL hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=787)
    public void unsetExternalImapSSLHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap SSL hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=787)
    public Map<String,Object> unsetExternalImapSSLHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLHostname, "");
        return attrs;
    }

    /**
     * external imap SSL port
     *
     * <p>Use getExternalImapSSLPortAsString to access value as a string.
     *
     * @see #getExternalImapSSLPortAsString()
     *
     * @return zmailExternalImapSSLPort, or -1 if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public int getExternalImapSSLPort() {
        return getIntAttr(Provisioning.A_zmailExternalImapSSLPort, -1);
    }

    /**
     * external imap SSL port
     *
     * @return zmailExternalImapSSLPort, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public String getExternalImapSSLPortAsString() {
        return getAttr(Provisioning.A_zmailExternalImapSSLPort, null);
    }

    /**
     * external imap SSL port
     *
     * @param zmailExternalImapSSLPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public void setExternalImapSSLPort(int zmailExternalImapSSLPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLPort, Integer.toString(zmailExternalImapSSLPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap SSL port
     *
     * @param zmailExternalImapSSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public Map<String,Object> setExternalImapSSLPort(int zmailExternalImapSSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLPort, Integer.toString(zmailExternalImapSSLPort));
        return attrs;
    }

    /**
     * external imap SSL port
     *
     * @param zmailExternalImapSSLPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public void setExternalImapSSLPortAsString(String zmailExternalImapSSLPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLPort, zmailExternalImapSSLPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap SSL port
     *
     * @param zmailExternalImapSSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public Map<String,Object> setExternalImapSSLPortAsString(String zmailExternalImapSSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLPort, zmailExternalImapSSLPort);
        return attrs;
    }

    /**
     * external imap SSL port
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public void unsetExternalImapSSLPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external imap SSL port
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=783)
    public Map<String,Object> unsetExternalImapSSLPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalImapSSLPort, "");
        return attrs;
    }

    /**
     * external pop3 hostname
     *
     * @return zmailExternalPop3Hostname, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=784)
    public String getExternalPop3Hostname() {
        return getAttr(Provisioning.A_zmailExternalPop3Hostname, null);
    }

    /**
     * external pop3 hostname
     *
     * @param zmailExternalPop3Hostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=784)
    public void setExternalPop3Hostname(String zmailExternalPop3Hostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Hostname, zmailExternalPop3Hostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 hostname
     *
     * @param zmailExternalPop3Hostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=784)
    public Map<String,Object> setExternalPop3Hostname(String zmailExternalPop3Hostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Hostname, zmailExternalPop3Hostname);
        return attrs;
    }

    /**
     * external pop3 hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=784)
    public void unsetExternalPop3Hostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Hostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=784)
    public Map<String,Object> unsetExternalPop3Hostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Hostname, "");
        return attrs;
    }

    /**
     * external pop3 port
     *
     * <p>Use getExternalPop3PortAsString to access value as a string.
     *
     * @see #getExternalPop3PortAsString()
     *
     * @return zmailExternalPop3Port, or -1 if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public int getExternalPop3Port() {
        return getIntAttr(Provisioning.A_zmailExternalPop3Port, -1);
    }

    /**
     * external pop3 port
     *
     * @return zmailExternalPop3Port, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public String getExternalPop3PortAsString() {
        return getAttr(Provisioning.A_zmailExternalPop3Port, null);
    }

    /**
     * external pop3 port
     *
     * @param zmailExternalPop3Port new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public void setExternalPop3Port(int zmailExternalPop3Port) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Port, Integer.toString(zmailExternalPop3Port));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 port
     *
     * @param zmailExternalPop3Port new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public Map<String,Object> setExternalPop3Port(int zmailExternalPop3Port, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Port, Integer.toString(zmailExternalPop3Port));
        return attrs;
    }

    /**
     * external pop3 port
     *
     * @param zmailExternalPop3Port new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public void setExternalPop3PortAsString(String zmailExternalPop3Port) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Port, zmailExternalPop3Port);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 port
     *
     * @param zmailExternalPop3Port new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public Map<String,Object> setExternalPop3PortAsString(String zmailExternalPop3Port, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Port, zmailExternalPop3Port);
        return attrs;
    }

    /**
     * external pop3 port
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public void unsetExternalPop3Port() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Port, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 port
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=780)
    public Map<String,Object> unsetExternalPop3Port(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3Port, "");
        return attrs;
    }

    /**
     * external pop3 SSL hostname
     *
     * @return zmailExternalPop3SSLHostname, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=785)
    public String getExternalPop3SSLHostname() {
        return getAttr(Provisioning.A_zmailExternalPop3SSLHostname, null);
    }

    /**
     * external pop3 SSL hostname
     *
     * @param zmailExternalPop3SSLHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=785)
    public void setExternalPop3SSLHostname(String zmailExternalPop3SSLHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLHostname, zmailExternalPop3SSLHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 SSL hostname
     *
     * @param zmailExternalPop3SSLHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=785)
    public Map<String,Object> setExternalPop3SSLHostname(String zmailExternalPop3SSLHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLHostname, zmailExternalPop3SSLHostname);
        return attrs;
    }

    /**
     * external pop3 SSL hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=785)
    public void unsetExternalPop3SSLHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 SSL hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=785)
    public Map<String,Object> unsetExternalPop3SSLHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLHostname, "");
        return attrs;
    }

    /**
     * external pop3 SSL port
     *
     * <p>Use getExternalPop3SSLPortAsString to access value as a string.
     *
     * @see #getExternalPop3SSLPortAsString()
     *
     * @return zmailExternalPop3SSLPort, or -1 if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public int getExternalPop3SSLPort() {
        return getIntAttr(Provisioning.A_zmailExternalPop3SSLPort, -1);
    }

    /**
     * external pop3 SSL port
     *
     * @return zmailExternalPop3SSLPort, or null if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public String getExternalPop3SSLPortAsString() {
        return getAttr(Provisioning.A_zmailExternalPop3SSLPort, null);
    }

    /**
     * external pop3 SSL port
     *
     * @param zmailExternalPop3SSLPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public void setExternalPop3SSLPort(int zmailExternalPop3SSLPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLPort, Integer.toString(zmailExternalPop3SSLPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 SSL port
     *
     * @param zmailExternalPop3SSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public Map<String,Object> setExternalPop3SSLPort(int zmailExternalPop3SSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLPort, Integer.toString(zmailExternalPop3SSLPort));
        return attrs;
    }

    /**
     * external pop3 SSL port
     *
     * @param zmailExternalPop3SSLPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public void setExternalPop3SSLPortAsString(String zmailExternalPop3SSLPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLPort, zmailExternalPop3SSLPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 SSL port
     *
     * @param zmailExternalPop3SSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public Map<String,Object> setExternalPop3SSLPortAsString(String zmailExternalPop3SSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLPort, zmailExternalPop3SSLPort);
        return attrs;
    }

    /**
     * external pop3 SSL port
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public void unsetExternalPop3SSLPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external pop3 SSL port
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=781)
    public Map<String,Object> unsetExternalPop3SSLPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalPop3SSLPort, "");
        return attrs;
    }

    /**
     * whether checking against zmailExternalShareWhitelistDomain for
     * external user sharing is enabled
     *
     * @return zmailExternalShareDomainWhitelistEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1264)
    public boolean isExternalShareDomainWhitelistEnabled() {
        return getBooleanAttr(Provisioning.A_zmailExternalShareDomainWhitelistEnabled, false);
    }

    /**
     * whether checking against zmailExternalShareWhitelistDomain for
     * external user sharing is enabled
     *
     * @param zmailExternalShareDomainWhitelistEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1264)
    public void setExternalShareDomainWhitelistEnabled(boolean zmailExternalShareDomainWhitelistEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareDomainWhitelistEnabled, zmailExternalShareDomainWhitelistEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether checking against zmailExternalShareWhitelistDomain for
     * external user sharing is enabled
     *
     * @param zmailExternalShareDomainWhitelistEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1264)
    public Map<String,Object> setExternalShareDomainWhitelistEnabled(boolean zmailExternalShareDomainWhitelistEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareDomainWhitelistEnabled, zmailExternalShareDomainWhitelistEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether checking against zmailExternalShareWhitelistDomain for
     * external user sharing is enabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1264)
    public void unsetExternalShareDomainWhitelistEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareDomainWhitelistEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether checking against zmailExternalShareWhitelistDomain for
     * external user sharing is enabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1264)
    public Map<String,Object> unsetExternalShareDomainWhitelistEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareDomainWhitelistEnabled, "");
        return attrs;
    }

    /**
     * Duration for which the URL sent in the share invitation email to an
     * external user is valid. A value of 0 indicates that the URL never
     * expires. . Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * <p>Use getExternalShareInvitationUrlExpirationAsString to access value as a string.
     *
     * @see #getExternalShareInvitationUrlExpirationAsString()
     *
     * @return zmailExternalShareInvitationUrlExpiration in millseconds, or 0 (0)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1349)
    public long getExternalShareInvitationUrlExpiration() {
        return getTimeInterval(Provisioning.A_zmailExternalShareInvitationUrlExpiration, 0L);
    }

    /**
     * Duration for which the URL sent in the share invitation email to an
     * external user is valid. A value of 0 indicates that the URL never
     * expires. . Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @return zmailExternalShareInvitationUrlExpiration, or "0" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1349)
    public String getExternalShareInvitationUrlExpirationAsString() {
        return getAttr(Provisioning.A_zmailExternalShareInvitationUrlExpiration, "0");
    }

    /**
     * Duration for which the URL sent in the share invitation email to an
     * external user is valid. A value of 0 indicates that the URL never
     * expires. . Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @param zmailExternalShareInvitationUrlExpiration new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1349)
    public void setExternalShareInvitationUrlExpiration(String zmailExternalShareInvitationUrlExpiration) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareInvitationUrlExpiration, zmailExternalShareInvitationUrlExpiration);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Duration for which the URL sent in the share invitation email to an
     * external user is valid. A value of 0 indicates that the URL never
     * expires. . Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @param zmailExternalShareInvitationUrlExpiration new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1349)
    public Map<String,Object> setExternalShareInvitationUrlExpiration(String zmailExternalShareInvitationUrlExpiration, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareInvitationUrlExpiration, zmailExternalShareInvitationUrlExpiration);
        return attrs;
    }

    /**
     * Duration for which the URL sent in the share invitation email to an
     * external user is valid. A value of 0 indicates that the URL never
     * expires. . Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1349)
    public void unsetExternalShareInvitationUrlExpiration() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareInvitationUrlExpiration, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Duration for which the URL sent in the share invitation email to an
     * external user is valid. A value of 0 indicates that the URL never
     * expires. . Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1349)
    public Map<String,Object> unsetExternalShareInvitationUrlExpiration(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareInvitationUrlExpiration, "");
        return attrs;
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @return zmailExternalShareWhitelistDomain, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public String[] getExternalShareWhitelistDomain() {
        return getMultiAttr(Provisioning.A_zmailExternalShareWhitelistDomain);
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param zmailExternalShareWhitelistDomain new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public void setExternalShareWhitelistDomain(String[] zmailExternalShareWhitelistDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareWhitelistDomain, zmailExternalShareWhitelistDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param zmailExternalShareWhitelistDomain new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public Map<String,Object> setExternalShareWhitelistDomain(String[] zmailExternalShareWhitelistDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareWhitelistDomain, zmailExternalShareWhitelistDomain);
        return attrs;
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param zmailExternalShareWhitelistDomain new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public void addExternalShareWhitelistDomain(String zmailExternalShareWhitelistDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailExternalShareWhitelistDomain, zmailExternalShareWhitelistDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param zmailExternalShareWhitelistDomain new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public Map<String,Object> addExternalShareWhitelistDomain(String zmailExternalShareWhitelistDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailExternalShareWhitelistDomain, zmailExternalShareWhitelistDomain);
        return attrs;
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param zmailExternalShareWhitelistDomain existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public void removeExternalShareWhitelistDomain(String zmailExternalShareWhitelistDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailExternalShareWhitelistDomain, zmailExternalShareWhitelistDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param zmailExternalShareWhitelistDomain existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public Map<String,Object> removeExternalShareWhitelistDomain(String zmailExternalShareWhitelistDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailExternalShareWhitelistDomain, zmailExternalShareWhitelistDomain);
        return attrs;
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public void unsetExternalShareWhitelistDomain() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareWhitelistDomain, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of external domains that users can share files and folders with
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1263)
    public Map<String,Object> unsetExternalShareWhitelistDomain(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalShareWhitelistDomain, "");
        return attrs;
    }

    /**
     * switch for turning external user sharing on/off
     *
     * @return zmailExternalSharingEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1261)
    public boolean isExternalSharingEnabled() {
        return getBooleanAttr(Provisioning.A_zmailExternalSharingEnabled, false);
    }

    /**
     * switch for turning external user sharing on/off
     *
     * @param zmailExternalSharingEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1261)
    public void setExternalSharingEnabled(boolean zmailExternalSharingEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalSharingEnabled, zmailExternalSharingEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * switch for turning external user sharing on/off
     *
     * @param zmailExternalSharingEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1261)
    public Map<String,Object> setExternalSharingEnabled(boolean zmailExternalSharingEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalSharingEnabled, zmailExternalSharingEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * switch for turning external user sharing on/off
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1261)
    public void unsetExternalSharingEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalSharingEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * switch for turning external user sharing on/off
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1261)
    public Map<String,Object> unsetExternalSharingEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalSharingEnabled, "");
        return attrs;
    }

    /**
     * whether receiving reminders on the designated device for appointments
     * and tasks is enabled
     *
     * @return zmailFeatureCalendarReminderDeviceEmailEnabled, or false if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1150)
    public boolean isFeatureCalendarReminderDeviceEmailEnabled() {
        return getBooleanAttr(Provisioning.A_zmailFeatureCalendarReminderDeviceEmailEnabled, false);
    }

    /**
     * whether receiving reminders on the designated device for appointments
     * and tasks is enabled
     *
     * @param zmailFeatureCalendarReminderDeviceEmailEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1150)
    public void setFeatureCalendarReminderDeviceEmailEnabled(boolean zmailFeatureCalendarReminderDeviceEmailEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureCalendarReminderDeviceEmailEnabled, zmailFeatureCalendarReminderDeviceEmailEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether receiving reminders on the designated device for appointments
     * and tasks is enabled
     *
     * @param zmailFeatureCalendarReminderDeviceEmailEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1150)
    public Map<String,Object> setFeatureCalendarReminderDeviceEmailEnabled(boolean zmailFeatureCalendarReminderDeviceEmailEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureCalendarReminderDeviceEmailEnabled, zmailFeatureCalendarReminderDeviceEmailEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether receiving reminders on the designated device for appointments
     * and tasks is enabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1150)
    public void unsetFeatureCalendarReminderDeviceEmailEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureCalendarReminderDeviceEmailEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether receiving reminders on the designated device for appointments
     * and tasks is enabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1150)
    public Map<String,Object> unsetFeatureCalendarReminderDeviceEmailEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureCalendarReminderDeviceEmailEnabled, "");
        return attrs;
    }

    /**
     * Whether to display the distribution list folder in address book
     *
     * @return zmailFeatureDistributionListFolderEnabled, or false if unset
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1438)
    public boolean isFeatureDistributionListFolderEnabled() {
        return getBooleanAttr(Provisioning.A_zmailFeatureDistributionListFolderEnabled, false);
    }

    /**
     * Whether to display the distribution list folder in address book
     *
     * @param zmailFeatureDistributionListFolderEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1438)
    public void setFeatureDistributionListFolderEnabled(boolean zmailFeatureDistributionListFolderEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureDistributionListFolderEnabled, zmailFeatureDistributionListFolderEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to display the distribution list folder in address book
     *
     * @param zmailFeatureDistributionListFolderEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1438)
    public Map<String,Object> setFeatureDistributionListFolderEnabled(boolean zmailFeatureDistributionListFolderEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureDistributionListFolderEnabled, zmailFeatureDistributionListFolderEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to display the distribution list folder in address book
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1438)
    public void unsetFeatureDistributionListFolderEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureDistributionListFolderEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to display the distribution list folder in address book
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1438)
    public Map<String,Object> unsetFeatureDistributionListFolderEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFeatureDistributionListFolderEnabled, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for each attachment.
     *
     * @return zmailFileUploadMaxSizePerFile, or 2147483648 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1350)
    public long getFileUploadMaxSizePerFile() {
        return getLongAttr(Provisioning.A_zmailFileUploadMaxSizePerFile, 2147483648L);
    }

    /**
     * Maximum size in bytes for each attachment.
     *
     * @param zmailFileUploadMaxSizePerFile new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1350)
    public void setFileUploadMaxSizePerFile(long zmailFileUploadMaxSizePerFile) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSizePerFile, Long.toString(zmailFileUploadMaxSizePerFile));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for each attachment.
     *
     * @param zmailFileUploadMaxSizePerFile new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1350)
    public Map<String,Object> setFileUploadMaxSizePerFile(long zmailFileUploadMaxSizePerFile, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSizePerFile, Long.toString(zmailFileUploadMaxSizePerFile));
        return attrs;
    }

    /**
     * Maximum size in bytes for each attachment.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1350)
    public void unsetFileUploadMaxSizePerFile() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSizePerFile, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for each attachment.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1350)
    public Map<String,Object> unsetFileUploadMaxSizePerFile(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSizePerFile, "");
        return attrs;
    }

    /**
     * Whether to force clear zmail auth cookies when SOAP session ends
     * (i.e. force logout on browser tab close)
     *
     * @return zmailForceClearCookies, or false if unset
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1437)
    public boolean isForceClearCookies() {
        return getBooleanAttr(Provisioning.A_zmailForceClearCookies, false);
    }

    /**
     * Whether to force clear zmail auth cookies when SOAP session ends
     * (i.e. force logout on browser tab close)
     *
     * @param zmailForceClearCookies new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1437)
    public void setForceClearCookies(boolean zmailForceClearCookies) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForceClearCookies, zmailForceClearCookies ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to force clear zmail auth cookies when SOAP session ends
     * (i.e. force logout on browser tab close)
     *
     * @param zmailForceClearCookies new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1437)
    public Map<String,Object> setForceClearCookies(boolean zmailForceClearCookies, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForceClearCookies, zmailForceClearCookies ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to force clear zmail auth cookies when SOAP session ends
     * (i.e. force logout on browser tab close)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1437)
    public void unsetForceClearCookies() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForceClearCookies, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to force clear zmail auth cookies when SOAP session ends
     * (i.e. force logout on browser tab close)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1437)
    public Map<String,Object> unsetForceClearCookies(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForceClearCookies, "");
        return attrs;
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @return zmailForeignName, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public String[] getForeignName() {
        return getMultiAttr(Provisioning.A_zmailForeignName);
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param zmailForeignName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public void setForeignName(String[] zmailForeignName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignName, zmailForeignName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param zmailForeignName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public Map<String,Object> setForeignName(String[] zmailForeignName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignName, zmailForeignName);
        return attrs;
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param zmailForeignName new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public void addForeignName(String zmailForeignName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailForeignName, zmailForeignName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param zmailForeignName new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public Map<String,Object> addForeignName(String zmailForeignName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailForeignName, zmailForeignName);
        return attrs;
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param zmailForeignName existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public void removeForeignName(String zmailForeignName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailForeignName, zmailForeignName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param zmailForeignName existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public Map<String,Object> removeForeignName(String zmailForeignName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailForeignName, zmailForeignName);
        return attrs;
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public void unsetForeignName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * foreign name for mapping an external name to a zmail domain on domain
     * level, it is in the format of {application}:{foreign name}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1135)
    public Map<String,Object> unsetForeignName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignName, "");
        return attrs;
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @return zmailForeignNameHandler, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public String[] getForeignNameHandler() {
        return getMultiAttr(Provisioning.A_zmailForeignNameHandler);
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param zmailForeignNameHandler new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public void setForeignNameHandler(String[] zmailForeignNameHandler) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignNameHandler, zmailForeignNameHandler);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param zmailForeignNameHandler new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public Map<String,Object> setForeignNameHandler(String[] zmailForeignNameHandler, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignNameHandler, zmailForeignNameHandler);
        return attrs;
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param zmailForeignNameHandler new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public void addForeignNameHandler(String zmailForeignNameHandler) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailForeignNameHandler, zmailForeignNameHandler);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param zmailForeignNameHandler new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public Map<String,Object> addForeignNameHandler(String zmailForeignNameHandler, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailForeignNameHandler, zmailForeignNameHandler);
        return attrs;
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param zmailForeignNameHandler existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public void removeForeignNameHandler(String zmailForeignNameHandler) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailForeignNameHandler, zmailForeignNameHandler);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param zmailForeignNameHandler existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public Map<String,Object> removeForeignNameHandler(String zmailForeignNameHandler, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailForeignNameHandler, zmailForeignNameHandler);
        return attrs;
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public void unsetForeignNameHandler() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignNameHandler, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * handler for foreign name mapping, it is in the format of
     * {application}:{class name}[:{params}]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1136)
    public Map<String,Object> unsetForeignNameHandler(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailForeignNameHandler, "");
        return attrs;
    }

    /**
     * Exchange user password for free/busy lookup and propagation
     *
     * @return zmailFreebusyExchangeAuthPassword, or null if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=609)
    public String getFreebusyExchangeAuthPassword() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeAuthPassword, null);
    }

    /**
     * Exchange user password for free/busy lookup and propagation
     *
     * @param zmailFreebusyExchangeAuthPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=609)
    public void setFreebusyExchangeAuthPassword(String zmailFreebusyExchangeAuthPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthPassword, zmailFreebusyExchangeAuthPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Exchange user password for free/busy lookup and propagation
     *
     * @param zmailFreebusyExchangeAuthPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=609)
    public Map<String,Object> setFreebusyExchangeAuthPassword(String zmailFreebusyExchangeAuthPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthPassword, zmailFreebusyExchangeAuthPassword);
        return attrs;
    }

    /**
     * Exchange user password for free/busy lookup and propagation
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=609)
    public void unsetFreebusyExchangeAuthPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Exchange user password for free/busy lookup and propagation
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=609)
    public Map<String,Object> unsetFreebusyExchangeAuthPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthPassword, "");
        return attrs;
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @return zmailFreebusyExchangeAuthScheme, or null if unset and/or has invalid value
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public ZAttrProvisioning.FreebusyExchangeAuthScheme getFreebusyExchangeAuthScheme() {
        try { String v = getAttr(Provisioning.A_zmailFreebusyExchangeAuthScheme); return v == null ? null : ZAttrProvisioning.FreebusyExchangeAuthScheme.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @return zmailFreebusyExchangeAuthScheme, or null if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public String getFreebusyExchangeAuthSchemeAsString() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeAuthScheme, null);
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @param zmailFreebusyExchangeAuthScheme new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public void setFreebusyExchangeAuthScheme(ZAttrProvisioning.FreebusyExchangeAuthScheme zmailFreebusyExchangeAuthScheme) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthScheme, zmailFreebusyExchangeAuthScheme.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @param zmailFreebusyExchangeAuthScheme new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public Map<String,Object> setFreebusyExchangeAuthScheme(ZAttrProvisioning.FreebusyExchangeAuthScheme zmailFreebusyExchangeAuthScheme, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthScheme, zmailFreebusyExchangeAuthScheme.toString());
        return attrs;
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @param zmailFreebusyExchangeAuthScheme new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public void setFreebusyExchangeAuthSchemeAsString(String zmailFreebusyExchangeAuthScheme) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthScheme, zmailFreebusyExchangeAuthScheme);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @param zmailFreebusyExchangeAuthScheme new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public Map<String,Object> setFreebusyExchangeAuthSchemeAsString(String zmailFreebusyExchangeAuthScheme, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthScheme, zmailFreebusyExchangeAuthScheme);
        return attrs;
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public void unsetFreebusyExchangeAuthScheme() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthScheme, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * auth scheme to use
     *
     * <p>Valid values: [form, basic]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=611)
    public Map<String,Object> unsetFreebusyExchangeAuthScheme(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthScheme, "");
        return attrs;
    }

    /**
     * Exchange username for free/busy lookup and propagation
     *
     * @return zmailFreebusyExchangeAuthUsername, or null if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=608)
    public String getFreebusyExchangeAuthUsername() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeAuthUsername, null);
    }

    /**
     * Exchange username for free/busy lookup and propagation
     *
     * @param zmailFreebusyExchangeAuthUsername new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=608)
    public void setFreebusyExchangeAuthUsername(String zmailFreebusyExchangeAuthUsername) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthUsername, zmailFreebusyExchangeAuthUsername);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Exchange username for free/busy lookup and propagation
     *
     * @param zmailFreebusyExchangeAuthUsername new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=608)
    public Map<String,Object> setFreebusyExchangeAuthUsername(String zmailFreebusyExchangeAuthUsername, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthUsername, zmailFreebusyExchangeAuthUsername);
        return attrs;
    }

    /**
     * Exchange username for free/busy lookup and propagation
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=608)
    public void unsetFreebusyExchangeAuthUsername() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthUsername, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Exchange username for free/busy lookup and propagation
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=608)
    public Map<String,Object> unsetFreebusyExchangeAuthUsername(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeAuthUsername, "");
        return attrs;
    }

    /**
     * The duration of f/b block pushed to Exchange server.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getFreebusyExchangeCachedIntervalAsString to access value as a string.
     *
     * @see #getFreebusyExchangeCachedIntervalAsString()
     *
     * @return zmailFreebusyExchangeCachedInterval in millseconds, or 5184000000 (60d)  if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=621)
    public long getFreebusyExchangeCachedInterval() {
        return getTimeInterval(Provisioning.A_zmailFreebusyExchangeCachedInterval, 5184000000L);
    }

    /**
     * The duration of f/b block pushed to Exchange server.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailFreebusyExchangeCachedInterval, or "60d" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=621)
    public String getFreebusyExchangeCachedIntervalAsString() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeCachedInterval, "60d");
    }

    /**
     * The duration of f/b block pushed to Exchange server.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailFreebusyExchangeCachedInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=621)
    public void setFreebusyExchangeCachedInterval(String zmailFreebusyExchangeCachedInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedInterval, zmailFreebusyExchangeCachedInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The duration of f/b block pushed to Exchange server.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailFreebusyExchangeCachedInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=621)
    public Map<String,Object> setFreebusyExchangeCachedInterval(String zmailFreebusyExchangeCachedInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedInterval, zmailFreebusyExchangeCachedInterval);
        return attrs;
    }

    /**
     * The duration of f/b block pushed to Exchange server.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=621)
    public void unsetFreebusyExchangeCachedInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The duration of f/b block pushed to Exchange server.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=621)
    public Map<String,Object> unsetFreebusyExchangeCachedInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedInterval, "");
        return attrs;
    }

    /**
     * The value of duration is used to indicate the start date (in the past
     * relative to today) of the f/b interval pushed to Exchange server..
     * Must be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getFreebusyExchangeCachedIntervalStartAsString to access value as a string.
     *
     * @see #getFreebusyExchangeCachedIntervalStartAsString()
     *
     * @return zmailFreebusyExchangeCachedIntervalStart in millseconds, or 604800000 (7d)  if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=620)
    public long getFreebusyExchangeCachedIntervalStart() {
        return getTimeInterval(Provisioning.A_zmailFreebusyExchangeCachedIntervalStart, 604800000L);
    }

    /**
     * The value of duration is used to indicate the start date (in the past
     * relative to today) of the f/b interval pushed to Exchange server..
     * Must be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailFreebusyExchangeCachedIntervalStart, or "7d" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=620)
    public String getFreebusyExchangeCachedIntervalStartAsString() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeCachedIntervalStart, "7d");
    }

    /**
     * The value of duration is used to indicate the start date (in the past
     * relative to today) of the f/b interval pushed to Exchange server..
     * Must be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailFreebusyExchangeCachedIntervalStart new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=620)
    public void setFreebusyExchangeCachedIntervalStart(String zmailFreebusyExchangeCachedIntervalStart) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedIntervalStart, zmailFreebusyExchangeCachedIntervalStart);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The value of duration is used to indicate the start date (in the past
     * relative to today) of the f/b interval pushed to Exchange server..
     * Must be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailFreebusyExchangeCachedIntervalStart new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=620)
    public Map<String,Object> setFreebusyExchangeCachedIntervalStart(String zmailFreebusyExchangeCachedIntervalStart, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedIntervalStart, zmailFreebusyExchangeCachedIntervalStart);
        return attrs;
    }

    /**
     * The value of duration is used to indicate the start date (in the past
     * relative to today) of the f/b interval pushed to Exchange server..
     * Must be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=620)
    public void unsetFreebusyExchangeCachedIntervalStart() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedIntervalStart, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The value of duration is used to indicate the start date (in the past
     * relative to today) of the f/b interval pushed to Exchange server..
     * Must be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=620)
    public Map<String,Object> unsetFreebusyExchangeCachedIntervalStart(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeCachedIntervalStart, "");
        return attrs;
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @return zmailFreebusyExchangeServerType, or ZAttrProvisioning.FreebusyExchangeServerType.webdav if unset and/or has invalid value
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public ZAttrProvisioning.FreebusyExchangeServerType getFreebusyExchangeServerType() {
        try { String v = getAttr(Provisioning.A_zmailFreebusyExchangeServerType); return v == null ? ZAttrProvisioning.FreebusyExchangeServerType.webdav : ZAttrProvisioning.FreebusyExchangeServerType.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.FreebusyExchangeServerType.webdav; }
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @return zmailFreebusyExchangeServerType, or "webdav" if unset
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public String getFreebusyExchangeServerTypeAsString() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeServerType, "webdav");
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @param zmailFreebusyExchangeServerType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public void setFreebusyExchangeServerType(ZAttrProvisioning.FreebusyExchangeServerType zmailFreebusyExchangeServerType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeServerType, zmailFreebusyExchangeServerType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @param zmailFreebusyExchangeServerType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public Map<String,Object> setFreebusyExchangeServerType(ZAttrProvisioning.FreebusyExchangeServerType zmailFreebusyExchangeServerType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeServerType, zmailFreebusyExchangeServerType.toString());
        return attrs;
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @param zmailFreebusyExchangeServerType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public void setFreebusyExchangeServerTypeAsString(String zmailFreebusyExchangeServerType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeServerType, zmailFreebusyExchangeServerType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @param zmailFreebusyExchangeServerType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public Map<String,Object> setFreebusyExchangeServerTypeAsString(String zmailFreebusyExchangeServerType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeServerType, zmailFreebusyExchangeServerType);
        return attrs;
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public void unsetFreebusyExchangeServerType() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeServerType, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Can be set to either webdav for Exchange 2007 or older, or ews for
     * 2010 and newer
     *
     * <p>Valid values: [ews, webdav]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.11
     */
    @ZAttr(id=1174)
    public Map<String,Object> unsetFreebusyExchangeServerType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeServerType, "");
        return attrs;
    }

    /**
     * URL to Exchange server for free/busy lookup and propagation
     *
     * @return zmailFreebusyExchangeURL, or null if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=607)
    public String getFreebusyExchangeURL() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeURL, null);
    }

    /**
     * URL to Exchange server for free/busy lookup and propagation
     *
     * @param zmailFreebusyExchangeURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=607)
    public void setFreebusyExchangeURL(String zmailFreebusyExchangeURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeURL, zmailFreebusyExchangeURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL to Exchange server for free/busy lookup and propagation
     *
     * @param zmailFreebusyExchangeURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=607)
    public Map<String,Object> setFreebusyExchangeURL(String zmailFreebusyExchangeURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeURL, zmailFreebusyExchangeURL);
        return attrs;
    }

    /**
     * URL to Exchange server for free/busy lookup and propagation
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=607)
    public void unsetFreebusyExchangeURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL to Exchange server for free/busy lookup and propagation
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=607)
    public Map<String,Object> unsetFreebusyExchangeURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeURL, "");
        return attrs;
    }

    /**
     * O and OU used in legacyExchangeDN attribute
     *
     * @return zmailFreebusyExchangeUserOrg, or null if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=610)
    public String getFreebusyExchangeUserOrg() {
        return getAttr(Provisioning.A_zmailFreebusyExchangeUserOrg, null);
    }

    /**
     * O and OU used in legacyExchangeDN attribute
     *
     * @param zmailFreebusyExchangeUserOrg new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=610)
    public void setFreebusyExchangeUserOrg(String zmailFreebusyExchangeUserOrg) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeUserOrg, zmailFreebusyExchangeUserOrg);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * O and OU used in legacyExchangeDN attribute
     *
     * @param zmailFreebusyExchangeUserOrg new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=610)
    public Map<String,Object> setFreebusyExchangeUserOrg(String zmailFreebusyExchangeUserOrg, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeUserOrg, zmailFreebusyExchangeUserOrg);
        return attrs;
    }

    /**
     * O and OU used in legacyExchangeDN attribute
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=610)
    public void unsetFreebusyExchangeUserOrg() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeUserOrg, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * O and OU used in legacyExchangeDN attribute
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=610)
    public Map<String,Object> unsetFreebusyExchangeUserOrg(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyExchangeUserOrg, "");
        return attrs;
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @return zmailGalAccountId, or empty array if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public String[] getGalAccountId() {
        return getMultiAttr(Provisioning.A_zmailGalAccountId);
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param zmailGalAccountId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public void setGalAccountId(String[] zmailGalAccountId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAccountId, zmailGalAccountId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param zmailGalAccountId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public Map<String,Object> setGalAccountId(String[] zmailGalAccountId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAccountId, zmailGalAccountId);
        return attrs;
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param zmailGalAccountId new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public void addGalAccountId(String zmailGalAccountId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalAccountId, zmailGalAccountId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param zmailGalAccountId new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public Map<String,Object> addGalAccountId(String zmailGalAccountId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalAccountId, zmailGalAccountId);
        return attrs;
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param zmailGalAccountId existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public void removeGalAccountId(String zmailGalAccountId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalAccountId, zmailGalAccountId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param zmailGalAccountId existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public Map<String,Object> removeGalAccountId(String zmailGalAccountId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalAccountId, zmailGalAccountId);
        return attrs;
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public void unsetGalAccountId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAccountId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of GAL sync accounts
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=831)
    public Map<String,Object> unsetGalAccountId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAccountId, "");
        return attrs;
    }

    /**
     * When set to TRUE, GAL search will always include local calendar
     * resources regardless of zmailGalMode.
     *
     * @return zmailGalAlwaysIncludeLocalCalendarResources, or false if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1093)
    public boolean isGalAlwaysIncludeLocalCalendarResources() {
        return getBooleanAttr(Provisioning.A_zmailGalAlwaysIncludeLocalCalendarResources, false);
    }

    /**
     * When set to TRUE, GAL search will always include local calendar
     * resources regardless of zmailGalMode.
     *
     * @param zmailGalAlwaysIncludeLocalCalendarResources new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1093)
    public void setGalAlwaysIncludeLocalCalendarResources(boolean zmailGalAlwaysIncludeLocalCalendarResources) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAlwaysIncludeLocalCalendarResources, zmailGalAlwaysIncludeLocalCalendarResources ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * When set to TRUE, GAL search will always include local calendar
     * resources regardless of zmailGalMode.
     *
     * @param zmailGalAlwaysIncludeLocalCalendarResources new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1093)
    public Map<String,Object> setGalAlwaysIncludeLocalCalendarResources(boolean zmailGalAlwaysIncludeLocalCalendarResources, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAlwaysIncludeLocalCalendarResources, zmailGalAlwaysIncludeLocalCalendarResources ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * When set to TRUE, GAL search will always include local calendar
     * resources regardless of zmailGalMode.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1093)
    public void unsetGalAlwaysIncludeLocalCalendarResources() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAlwaysIncludeLocalCalendarResources, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * When set to TRUE, GAL search will always include local calendar
     * resources regardless of zmailGalMode.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1093)
    public Map<String,Object> unsetGalAlwaysIncludeLocalCalendarResources(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAlwaysIncludeLocalCalendarResources, "");
        return attrs;
    }

    /**
     * LDAP search filter for external GAL auto-complete queries
     *
     * @return zmailGalAutoCompleteLdapFilter, or "externalLdapAutoComplete" if unset
     */
    @ZAttr(id=360)
    public String getGalAutoCompleteLdapFilter() {
        return getAttr(Provisioning.A_zmailGalAutoCompleteLdapFilter, "externalLdapAutoComplete");
    }

    /**
     * LDAP search filter for external GAL auto-complete queries
     *
     * @param zmailGalAutoCompleteLdapFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=360)
    public void setGalAutoCompleteLdapFilter(String zmailGalAutoCompleteLdapFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAutoCompleteLdapFilter, zmailGalAutoCompleteLdapFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for external GAL auto-complete queries
     *
     * @param zmailGalAutoCompleteLdapFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=360)
    public Map<String,Object> setGalAutoCompleteLdapFilter(String zmailGalAutoCompleteLdapFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAutoCompleteLdapFilter, zmailGalAutoCompleteLdapFilter);
        return attrs;
    }

    /**
     * LDAP search filter for external GAL auto-complete queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=360)
    public void unsetGalAutoCompleteLdapFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAutoCompleteLdapFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for external GAL auto-complete queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=360)
    public Map<String,Object> unsetGalAutoCompleteLdapFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalAutoCompleteLdapFilter, "");
        return attrs;
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * <p>Use getGalDefinitionLastModifiedTimeAsString to access value as a string.
     *
     * @see #getGalDefinitionLastModifiedTimeAsString()
     *
     * @return zmailGalDefinitionLastModifiedTime as Date, null if unset or unable to parse
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public Date getGalDefinitionLastModifiedTime() {
        return getGeneralizedTimeAttr(Provisioning.A_zmailGalDefinitionLastModifiedTime, null);
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @return zmailGalDefinitionLastModifiedTime, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public String getGalDefinitionLastModifiedTimeAsString() {
        return getAttr(Provisioning.A_zmailGalDefinitionLastModifiedTime, null);
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @param zmailGalDefinitionLastModifiedTime new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public void setGalDefinitionLastModifiedTime(Date zmailGalDefinitionLastModifiedTime) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalDefinitionLastModifiedTime, zmailGalDefinitionLastModifiedTime==null ? "" : DateUtil.toGeneralizedTime(zmailGalDefinitionLastModifiedTime));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @param zmailGalDefinitionLastModifiedTime new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public Map<String,Object> setGalDefinitionLastModifiedTime(Date zmailGalDefinitionLastModifiedTime, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalDefinitionLastModifiedTime, zmailGalDefinitionLastModifiedTime==null ? "" : DateUtil.toGeneralizedTime(zmailGalDefinitionLastModifiedTime));
        return attrs;
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @param zmailGalDefinitionLastModifiedTime new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public void setGalDefinitionLastModifiedTimeAsString(String zmailGalDefinitionLastModifiedTime) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalDefinitionLastModifiedTime, zmailGalDefinitionLastModifiedTime);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @param zmailGalDefinitionLastModifiedTime new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public Map<String,Object> setGalDefinitionLastModifiedTimeAsString(String zmailGalDefinitionLastModifiedTime, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalDefinitionLastModifiedTime, zmailGalDefinitionLastModifiedTime);
        return attrs;
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public void unsetGalDefinitionLastModifiedTime() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalDefinitionLastModifiedTime, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the time at which GAL definition is last modified.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1413)
    public Map<String,Object> unsetGalDefinitionLastModifiedTime(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalDefinitionLastModifiedTime, "");
        return attrs;
    }

    /**
     * whether to indicate if an email address on a message is a GAL group
     *
     * @return zmailGalGroupIndicatorEnabled, or true if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1153)
    public boolean isGalGroupIndicatorEnabled() {
        return getBooleanAttr(Provisioning.A_zmailGalGroupIndicatorEnabled, true);
    }

    /**
     * whether to indicate if an email address on a message is a GAL group
     *
     * @param zmailGalGroupIndicatorEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1153)
    public void setGalGroupIndicatorEnabled(boolean zmailGalGroupIndicatorEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalGroupIndicatorEnabled, zmailGalGroupIndicatorEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to indicate if an email address on a message is a GAL group
     *
     * @param zmailGalGroupIndicatorEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1153)
    public Map<String,Object> setGalGroupIndicatorEnabled(boolean zmailGalGroupIndicatorEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalGroupIndicatorEnabled, zmailGalGroupIndicatorEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether to indicate if an email address on a message is a GAL group
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1153)
    public void unsetGalGroupIndicatorEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalGroupIndicatorEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to indicate if an email address on a message is a GAL group
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1153)
    public Map<String,Object> unsetGalGroupIndicatorEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalGroupIndicatorEnabled, "");
        return attrs;
    }

    /**
     * LDAP search base for internal GAL queries (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains)
     *
     * @return zmailGalInternalSearchBase, or "DOMAIN" if unset
     */
    @ZAttr(id=358)
    public String getGalInternalSearchBase() {
        return getAttr(Provisioning.A_zmailGalInternalSearchBase, "DOMAIN");
    }

    /**
     * LDAP search base for internal GAL queries (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains)
     *
     * @param zmailGalInternalSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=358)
    public void setGalInternalSearchBase(String zmailGalInternalSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalInternalSearchBase, zmailGalInternalSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for internal GAL queries (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains)
     *
     * @param zmailGalInternalSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=358)
    public Map<String,Object> setGalInternalSearchBase(String zmailGalInternalSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalInternalSearchBase, zmailGalInternalSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for internal GAL queries (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=358)
    public void unsetGalInternalSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalInternalSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for internal GAL queries (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=358)
    public Map<String,Object> unsetGalInternalSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalInternalSearchBase, "");
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @return zmailGalLdapAttrMap, or empty array if unset
     */
    @ZAttr(id=153)
    public String[] getGalLdapAttrMap() {
        String[] value = getMultiAttr(Provisioning.A_zmailGalLdapAttrMap); return value.length > 0 ? value : new String[] {"co=workCountry","company=company","zmailPhoneticCompany,ms-DS-Phonetic-Company-Name=phoneticCompany","givenName,gn=firstName","zmailPhoneticFirstName,ms-DS-Phonetic-First-Name=phoneticFirstName","sn=lastName","zmailPhoneticLastName,ms-DS-Phonetic-Last-Name=phoneticLastName","displayName,cn=fullName,fullName2,fullName3,fullName4,fullName5,fullName6,fullName7,fullName8,fullName9,fullName10","initials=initials","description=notes","l=workCity","physicalDeliveryOfficeName=office","ou=department","street,streetAddress=workStreet","postalCode=workPostalCode","facsimileTelephoneNumber,fax=workFax","homeTelephoneNumber,homePhone=homePhone","mobileTelephoneNumber,mobile=mobilePhone","pagerTelephoneNumber,pager=pager","telephoneNumber=workPhone","st=workState","zmailMailDeliveryAddress,zmailMailAlias,mail=email,email2,email3,email4,email5,email6,email7,email8,email9,email10,email11,email12,email13,email14,email15,email16","title=jobTitle","whenChanged,modifyTimeStamp=modifyTimeStamp","whenCreated,createTimeStamp=createTimeStamp","zmailId=zmailId","objectClass=objectClass","zmailMailForwardingAddress=member","zmailCalResType,msExchResourceSearchProperties=zmailCalResType","zmailCalResLocationDisplayName=zmailCalResLocationDisplayName","zmailCalResBuilding=zmailCalResBuilding","zmailCalResCapacity,msExchResourceCapacity=zmailCalResCapacity","zmailCalResFloor=zmailCalResFloor","zmailCalResSite=zmailCalResSite","zmailCalResContactEmail=zmailCalResContactEmail","zmailDistributionListSubscriptionPolicy=zmailDistributionListSubscriptionPolicy","zmailDistributionListUnsubscriptionPolicy=zmailDistributionListUnsubscriptionPolicy","msExchResourceSearchProperties=zmailAccountCalendarUserType","(certificate) userCertificate=userCertificate","(binary) userSMIMECertificate=userSMIMECertificate"};
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param zmailGalLdapAttrMap new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=153)
    public void setGalLdapAttrMap(String[] zmailGalLdapAttrMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAttrMap, zmailGalLdapAttrMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param zmailGalLdapAttrMap new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=153)
    public Map<String,Object> setGalLdapAttrMap(String[] zmailGalLdapAttrMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAttrMap, zmailGalLdapAttrMap);
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param zmailGalLdapAttrMap new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=153)
    public void addGalLdapAttrMap(String zmailGalLdapAttrMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapAttrMap, zmailGalLdapAttrMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param zmailGalLdapAttrMap new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=153)
    public Map<String,Object> addGalLdapAttrMap(String zmailGalLdapAttrMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapAttrMap, zmailGalLdapAttrMap);
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param zmailGalLdapAttrMap existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=153)
    public void removeGalLdapAttrMap(String zmailGalLdapAttrMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapAttrMap, zmailGalLdapAttrMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param zmailGalLdapAttrMap existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=153)
    public Map<String,Object> removeGalLdapAttrMap(String zmailGalLdapAttrMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapAttrMap, zmailGalLdapAttrMap);
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=153)
    public void unsetGalLdapAttrMap() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAttrMap, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact attr mapping
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=153)
    public Map<String,Object> unsetGalLdapAttrMap(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAttrMap, "");
        return attrs;
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @return zmailGalLdapAuthMech, or null if unset and/or has invalid value
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public ZAttrProvisioning.GalLdapAuthMech getGalLdapAuthMech() {
        try { String v = getAttr(Provisioning.A_zmailGalLdapAuthMech); return v == null ? null : ZAttrProvisioning.GalLdapAuthMech.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @return zmailGalLdapAuthMech, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public String getGalLdapAuthMechAsString() {
        return getAttr(Provisioning.A_zmailGalLdapAuthMech, null);
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalLdapAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public void setGalLdapAuthMech(ZAttrProvisioning.GalLdapAuthMech zmailGalLdapAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAuthMech, zmailGalLdapAuthMech.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalLdapAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public Map<String,Object> setGalLdapAuthMech(ZAttrProvisioning.GalLdapAuthMech zmailGalLdapAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAuthMech, zmailGalLdapAuthMech.toString());
        return attrs;
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalLdapAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public void setGalLdapAuthMechAsString(String zmailGalLdapAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAuthMech, zmailGalLdapAuthMech);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalLdapAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public Map<String,Object> setGalLdapAuthMechAsString(String zmailGalLdapAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAuthMech, zmailGalLdapAuthMech);
        return attrs;
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public void unsetGalLdapAuthMech() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAuthMech, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external LDAP GAL authentication mechanism none: anonymous binding
     * simple: zmailGalLdapBindDn and zmailGalLdapBindPassword has to be
     * set kerberos5: zmailGalLdapKerberos5Principal and
     * zmailGalLdapKerberos5Keytab has to be set
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=549)
    public Map<String,Object> unsetGalLdapAuthMech(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapAuthMech, "");
        return attrs;
    }

    /**
     * LDAP bind dn for external GAL queries
     *
     * @return zmailGalLdapBindDn, or null if unset
     */
    @ZAttr(id=49)
    public String getGalLdapBindDn() {
        return getAttr(Provisioning.A_zmailGalLdapBindDn, null);
    }

    /**
     * LDAP bind dn for external GAL queries
     *
     * @param zmailGalLdapBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=49)
    public void setGalLdapBindDn(String zmailGalLdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindDn, zmailGalLdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind dn for external GAL queries
     *
     * @param zmailGalLdapBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=49)
    public Map<String,Object> setGalLdapBindDn(String zmailGalLdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindDn, zmailGalLdapBindDn);
        return attrs;
    }

    /**
     * LDAP bind dn for external GAL queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=49)
    public void unsetGalLdapBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind dn for external GAL queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=49)
    public Map<String,Object> unsetGalLdapBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindDn, "");
        return attrs;
    }

    /**
     * LDAP bind password for external GAL queries
     *
     * @return zmailGalLdapBindPassword, or null if unset
     */
    @ZAttr(id=50)
    public String getGalLdapBindPassword() {
        return getAttr(Provisioning.A_zmailGalLdapBindPassword, null);
    }

    /**
     * LDAP bind password for external GAL queries
     *
     * @param zmailGalLdapBindPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=50)
    public void setGalLdapBindPassword(String zmailGalLdapBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindPassword, zmailGalLdapBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for external GAL queries
     *
     * @param zmailGalLdapBindPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=50)
    public Map<String,Object> setGalLdapBindPassword(String zmailGalLdapBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindPassword, zmailGalLdapBindPassword);
        return attrs;
    }

    /**
     * LDAP bind password for external GAL queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=50)
    public void unsetGalLdapBindPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for external GAL queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=50)
    public Map<String,Object> unsetGalLdapBindPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapBindPassword, "");
        return attrs;
    }

    /**
     * LDAP search filter for external GAL search queries
     *
     * @return zmailGalLdapFilter, or null if unset
     */
    @ZAttr(id=51)
    public String getGalLdapFilter() {
        return getAttr(Provisioning.A_zmailGalLdapFilter, null);
    }

    /**
     * LDAP search filter for external GAL search queries
     *
     * @param zmailGalLdapFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=51)
    public void setGalLdapFilter(String zmailGalLdapFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapFilter, zmailGalLdapFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for external GAL search queries
     *
     * @param zmailGalLdapFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=51)
    public Map<String,Object> setGalLdapFilter(String zmailGalLdapFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapFilter, zmailGalLdapFilter);
        return attrs;
    }

    /**
     * LDAP search filter for external GAL search queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=51)
    public void unsetGalLdapFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for external GAL search queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=51)
    public Map<String,Object> unsetGalLdapFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapFilter, "");
        return attrs;
    }

    /**
     * the handler class for mapping groups from GAL source to zmail GAL
     * contacts for external GAL
     *
     * @return zmailGalLdapGroupHandlerClass, or null if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1112)
    public String getGalLdapGroupHandlerClass() {
        return getAttr(Provisioning.A_zmailGalLdapGroupHandlerClass, null);
    }

    /**
     * the handler class for mapping groups from GAL source to zmail GAL
     * contacts for external GAL
     *
     * @param zmailGalLdapGroupHandlerClass new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1112)
    public void setGalLdapGroupHandlerClass(String zmailGalLdapGroupHandlerClass) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapGroupHandlerClass, zmailGalLdapGroupHandlerClass);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the handler class for mapping groups from GAL source to zmail GAL
     * contacts for external GAL
     *
     * @param zmailGalLdapGroupHandlerClass new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1112)
    public Map<String,Object> setGalLdapGroupHandlerClass(String zmailGalLdapGroupHandlerClass, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapGroupHandlerClass, zmailGalLdapGroupHandlerClass);
        return attrs;
    }

    /**
     * the handler class for mapping groups from GAL source to zmail GAL
     * contacts for external GAL
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1112)
    public void unsetGalLdapGroupHandlerClass() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapGroupHandlerClass, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the handler class for mapping groups from GAL source to zmail GAL
     * contacts for external GAL
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1112)
    public Map<String,Object> unsetGalLdapGroupHandlerClass(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapGroupHandlerClass, "");
        return attrs;
    }

    /**
     * kerberos5 keytab file path for external GAL queries
     *
     * @return zmailGalLdapKerberos5Keytab, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=551)
    public String getGalLdapKerberos5Keytab() {
        return getAttr(Provisioning.A_zmailGalLdapKerberos5Keytab, null);
    }

    /**
     * kerberos5 keytab file path for external GAL queries
     *
     * @param zmailGalLdapKerberos5Keytab new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=551)
    public void setGalLdapKerberos5Keytab(String zmailGalLdapKerberos5Keytab) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Keytab, zmailGalLdapKerberos5Keytab);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 keytab file path for external GAL queries
     *
     * @param zmailGalLdapKerberos5Keytab new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=551)
    public Map<String,Object> setGalLdapKerberos5Keytab(String zmailGalLdapKerberos5Keytab, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Keytab, zmailGalLdapKerberos5Keytab);
        return attrs;
    }

    /**
     * kerberos5 keytab file path for external GAL queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=551)
    public void unsetGalLdapKerberos5Keytab() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Keytab, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 keytab file path for external GAL queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=551)
    public Map<String,Object> unsetGalLdapKerberos5Keytab(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Keytab, "");
        return attrs;
    }

    /**
     * kerberos5 principal for external GAL queries
     *
     * @return zmailGalLdapKerberos5Principal, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=550)
    public String getGalLdapKerberos5Principal() {
        return getAttr(Provisioning.A_zmailGalLdapKerberos5Principal, null);
    }

    /**
     * kerberos5 principal for external GAL queries
     *
     * @param zmailGalLdapKerberos5Principal new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=550)
    public void setGalLdapKerberos5Principal(String zmailGalLdapKerberos5Principal) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Principal, zmailGalLdapKerberos5Principal);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 principal for external GAL queries
     *
     * @param zmailGalLdapKerberos5Principal new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=550)
    public Map<String,Object> setGalLdapKerberos5Principal(String zmailGalLdapKerberos5Principal, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Principal, zmailGalLdapKerberos5Principal);
        return attrs;
    }

    /**
     * kerberos5 principal for external GAL queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=550)
    public void unsetGalLdapKerberos5Principal() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Principal, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 principal for external GAL queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=550)
    public Map<String,Object> unsetGalLdapKerberos5Principal(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapKerberos5Principal, "");
        return attrs;
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled.
     *
     * @return zmailGalLdapPageSize, or 1000 if unset
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=583)
    public int getGalLdapPageSize() {
        return getIntAttr(Provisioning.A_zmailGalLdapPageSize, 1000);
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled.
     *
     * @param zmailGalLdapPageSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=583)
    public void setGalLdapPageSize(int zmailGalLdapPageSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapPageSize, Integer.toString(zmailGalLdapPageSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled.
     *
     * @param zmailGalLdapPageSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=583)
    public Map<String,Object> setGalLdapPageSize(int zmailGalLdapPageSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapPageSize, Integer.toString(zmailGalLdapPageSize));
        return attrs;
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=583)
    public void unsetGalLdapPageSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapPageSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=583)
    public Map<String,Object> unsetGalLdapPageSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapPageSize, "");
        return attrs;
    }

    /**
     * LDAP search base for external GAL queries
     *
     * @return zmailGalLdapSearchBase, or null if unset
     */
    @ZAttr(id=48)
    public String getGalLdapSearchBase() {
        return getAttr(Provisioning.A_zmailGalLdapSearchBase, null);
    }

    /**
     * LDAP search base for external GAL queries
     *
     * @param zmailGalLdapSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=48)
    public void setGalLdapSearchBase(String zmailGalLdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapSearchBase, zmailGalLdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for external GAL queries
     *
     * @param zmailGalLdapSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=48)
    public Map<String,Object> setGalLdapSearchBase(String zmailGalLdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapSearchBase, zmailGalLdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for external GAL queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=48)
    public void unsetGalLdapSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for external GAL queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=48)
    public Map<String,Object> unsetGalLdapSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapSearchBase, "");
        return attrs;
    }

    /**
     * whether to use startTLS for external GAL. startTLS will be used for
     * external GAL access only if this attribute is true and
     * zmailGalLdapURL(or zmailGalSyncLdapURL for sync) does not contain a
     * ldaps URL.
     *
     * @return zmailGalLdapStartTlsEnabled, or false if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=655)
    public boolean isGalLdapStartTlsEnabled() {
        return getBooleanAttr(Provisioning.A_zmailGalLdapStartTlsEnabled, false);
    }

    /**
     * whether to use startTLS for external GAL. startTLS will be used for
     * external GAL access only if this attribute is true and
     * zmailGalLdapURL(or zmailGalSyncLdapURL for sync) does not contain a
     * ldaps URL.
     *
     * @param zmailGalLdapStartTlsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=655)
    public void setGalLdapStartTlsEnabled(boolean zmailGalLdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapStartTlsEnabled, zmailGalLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to use startTLS for external GAL. startTLS will be used for
     * external GAL access only if this attribute is true and
     * zmailGalLdapURL(or zmailGalSyncLdapURL for sync) does not contain a
     * ldaps URL.
     *
     * @param zmailGalLdapStartTlsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=655)
    public Map<String,Object> setGalLdapStartTlsEnabled(boolean zmailGalLdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapStartTlsEnabled, zmailGalLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether to use startTLS for external GAL. startTLS will be used for
     * external GAL access only if this attribute is true and
     * zmailGalLdapURL(or zmailGalSyncLdapURL for sync) does not contain a
     * ldaps URL.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=655)
    public void unsetGalLdapStartTlsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapStartTlsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to use startTLS for external GAL. startTLS will be used for
     * external GAL access only if this attribute is true and
     * zmailGalLdapURL(or zmailGalSyncLdapURL for sync) does not contain a
     * ldaps URL.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=655)
    public Map<String,Object> unsetGalLdapStartTlsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapStartTlsEnabled, "");
        return attrs;
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @return zmailGalLdapURL, or empty array if unset
     */
    @ZAttr(id=47)
    public String[] getGalLdapURL() {
        return getMultiAttr(Provisioning.A_zmailGalLdapURL);
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param zmailGalLdapURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=47)
    public void setGalLdapURL(String[] zmailGalLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapURL, zmailGalLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param zmailGalLdapURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=47)
    public Map<String,Object> setGalLdapURL(String[] zmailGalLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapURL, zmailGalLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param zmailGalLdapURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=47)
    public void addGalLdapURL(String zmailGalLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapURL, zmailGalLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param zmailGalLdapURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=47)
    public Map<String,Object> addGalLdapURL(String zmailGalLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapURL, zmailGalLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param zmailGalLdapURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=47)
    public void removeGalLdapURL(String zmailGalLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapURL, zmailGalLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param zmailGalLdapURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=47)
    public Map<String,Object> removeGalLdapURL(String zmailGalLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapURL, zmailGalLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=47)
    public void unsetGalLdapURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL queries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=47)
    public Map<String,Object> unsetGalLdapURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapURL, "");
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @return zmailGalLdapValueMap, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public String[] getGalLdapValueMap() {
        String[] value = getMultiAttr(Provisioning.A_zmailGalLdapValueMap); return value.length > 0 ? value : new String[] {"zmailCalResType: Room Location","zmailAccountCalendarUserType: Room|Equipment RESOURCE"};
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param zmailGalLdapValueMap new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public void setGalLdapValueMap(String[] zmailGalLdapValueMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapValueMap, zmailGalLdapValueMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param zmailGalLdapValueMap new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public Map<String,Object> setGalLdapValueMap(String[] zmailGalLdapValueMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapValueMap, zmailGalLdapValueMap);
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param zmailGalLdapValueMap new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public void addGalLdapValueMap(String zmailGalLdapValueMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapValueMap, zmailGalLdapValueMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param zmailGalLdapValueMap new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public Map<String,Object> addGalLdapValueMap(String zmailGalLdapValueMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalLdapValueMap, zmailGalLdapValueMap);
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param zmailGalLdapValueMap existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public void removeGalLdapValueMap(String zmailGalLdapValueMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapValueMap, zmailGalLdapValueMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param zmailGalLdapValueMap existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public Map<String,Object> removeGalLdapValueMap(String zmailGalLdapValueMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalLdapValueMap, zmailGalLdapValueMap);
        return attrs;
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public void unsetGalLdapValueMap() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapValueMap, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP Gal attribute to contact value mapping. Each value is in the
     * format of {gal contact filed}: {regex} {replacement}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1110)
    public Map<String,Object> unsetGalLdapValueMap(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalLdapValueMap, "");
        return attrs;
    }

    /**
     * maximum number of gal entries to return from a search
     *
     * @return zmailGalMaxResults, or 100 if unset
     */
    @ZAttr(id=53)
    public int getGalMaxResults() {
        return getIntAttr(Provisioning.A_zmailGalMaxResults, 100);
    }

    /**
     * maximum number of gal entries to return from a search
     *
     * @param zmailGalMaxResults new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=53)
    public void setGalMaxResults(int zmailGalMaxResults) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMaxResults, Integer.toString(zmailGalMaxResults));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of gal entries to return from a search
     *
     * @param zmailGalMaxResults new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=53)
    public Map<String,Object> setGalMaxResults(int zmailGalMaxResults, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMaxResults, Integer.toString(zmailGalMaxResults));
        return attrs;
    }

    /**
     * maximum number of gal entries to return from a search
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=53)
    public void unsetGalMaxResults() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMaxResults, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum number of gal entries to return from a search
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=53)
    public Map<String,Object> unsetGalMaxResults(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMaxResults, "");
        return attrs;
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @return zmailGalMode, or null if unset and/or has invalid value
     */
    @ZAttr(id=46)
    public ZAttrProvisioning.GalMode getGalMode() {
        try { String v = getAttr(Provisioning.A_zmailGalMode); return v == null ? null : ZAttrProvisioning.GalMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @return zmailGalMode, or null if unset
     */
    @ZAttr(id=46)
    public String getGalModeAsString() {
        return getAttr(Provisioning.A_zmailGalMode, null);
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @param zmailGalMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=46)
    public void setGalMode(ZAttrProvisioning.GalMode zmailGalMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMode, zmailGalMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @param zmailGalMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=46)
    public Map<String,Object> setGalMode(ZAttrProvisioning.GalMode zmailGalMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMode, zmailGalMode.toString());
        return attrs;
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @param zmailGalMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=46)
    public void setGalModeAsString(String zmailGalMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMode, zmailGalMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @param zmailGalMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=46)
    public Map<String,Object> setGalModeAsString(String zmailGalMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMode, zmailGalMode);
        return attrs;
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=46)
    public void unsetGalMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * valid modes are &quot;zmail&quot; (query internal directory only),
     * &quot;ldap&quot; (query external directory only), or &quot;both&quot;
     * (query internal and external directory)
     *
     * <p>Valid values: [ldap, both, zmail]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=46)
    public Map<String,Object> unsetGalMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalMode, "");
        return attrs;
    }

    /**
     * LDAP search base for internal GAL sync (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains) If not set fallback
     * to zmailGalInternalSearchBase
     *
     * @return zmailGalSyncInternalSearchBase, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=598)
    public String getGalSyncInternalSearchBase() {
        return getAttr(Provisioning.A_zmailGalSyncInternalSearchBase, null);
    }

    /**
     * LDAP search base for internal GAL sync (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains) If not set fallback
     * to zmailGalInternalSearchBase
     *
     * @param zmailGalSyncInternalSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=598)
    public void setGalSyncInternalSearchBase(String zmailGalSyncInternalSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncInternalSearchBase, zmailGalSyncInternalSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for internal GAL sync (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains) If not set fallback
     * to zmailGalInternalSearchBase
     *
     * @param zmailGalSyncInternalSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=598)
    public Map<String,Object> setGalSyncInternalSearchBase(String zmailGalSyncInternalSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncInternalSearchBase, zmailGalSyncInternalSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for internal GAL sync (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains) If not set fallback
     * to zmailGalInternalSearchBase
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=598)
    public void unsetGalSyncInternalSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncInternalSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for internal GAL sync (special values:
     * &quot;ROOT&quot; for top, &quot;DOMAIN&quot; for domain only,
     * &quot;SUBDOMAINS&quot; for domain and subdomains) If not set fallback
     * to zmailGalInternalSearchBase
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=598)
    public Map<String,Object> unsetGalSyncInternalSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncInternalSearchBase, "");
        return attrs;
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @return zmailGalSyncLdapAuthMech, or null if unset and/or has invalid value
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public ZAttrProvisioning.GalSyncLdapAuthMech getGalSyncLdapAuthMech() {
        try { String v = getAttr(Provisioning.A_zmailGalSyncLdapAuthMech); return v == null ? null : ZAttrProvisioning.GalSyncLdapAuthMech.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @return zmailGalSyncLdapAuthMech, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public String getGalSyncLdapAuthMechAsString() {
        return getAttr(Provisioning.A_zmailGalSyncLdapAuthMech, null);
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalSyncLdapAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public void setGalSyncLdapAuthMech(ZAttrProvisioning.GalSyncLdapAuthMech zmailGalSyncLdapAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapAuthMech, zmailGalSyncLdapAuthMech.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalSyncLdapAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public Map<String,Object> setGalSyncLdapAuthMech(ZAttrProvisioning.GalSyncLdapAuthMech zmailGalSyncLdapAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapAuthMech, zmailGalSyncLdapAuthMech.toString());
        return attrs;
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalSyncLdapAuthMech new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public void setGalSyncLdapAuthMechAsString(String zmailGalSyncLdapAuthMech) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapAuthMech, zmailGalSyncLdapAuthMech);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param zmailGalSyncLdapAuthMech new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public Map<String,Object> setGalSyncLdapAuthMechAsString(String zmailGalSyncLdapAuthMech, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapAuthMech, zmailGalSyncLdapAuthMech);
        return attrs;
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public void unsetGalSyncLdapAuthMech() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapAuthMech, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * external LDAP GAL authentication mechanism for GAL sync none:
     * anonymous binding simple: zmailGalLdapBindDn and
     * zmailGalLdapBindPassword has to be set kerberos5:
     * zmailGalLdapKerberos5Principal and zmailGalLdapKerberos5Keytab has
     * to be set if not set fallback to zmailGalLdapAuthMech
     *
     * <p>Valid values: [none, kerberos5, simple]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=592)
    public Map<String,Object> unsetGalSyncLdapAuthMech(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapAuthMech, "");
        return attrs;
    }

    /**
     * LDAP bind dn for external GAL sync queries, if not set fallback to
     * zmailGalLdapBindDn
     *
     * @return zmailGalSyncLdapBindDn, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=593)
    public String getGalSyncLdapBindDn() {
        return getAttr(Provisioning.A_zmailGalSyncLdapBindDn, null);
    }

    /**
     * LDAP bind dn for external GAL sync queries, if not set fallback to
     * zmailGalLdapBindDn
     *
     * @param zmailGalSyncLdapBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=593)
    public void setGalSyncLdapBindDn(String zmailGalSyncLdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindDn, zmailGalSyncLdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind dn for external GAL sync queries, if not set fallback to
     * zmailGalLdapBindDn
     *
     * @param zmailGalSyncLdapBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=593)
    public Map<String,Object> setGalSyncLdapBindDn(String zmailGalSyncLdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindDn, zmailGalSyncLdapBindDn);
        return attrs;
    }

    /**
     * LDAP bind dn for external GAL sync queries, if not set fallback to
     * zmailGalLdapBindDn
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=593)
    public void unsetGalSyncLdapBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind dn for external GAL sync queries, if not set fallback to
     * zmailGalLdapBindDn
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=593)
    public Map<String,Object> unsetGalSyncLdapBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindDn, "");
        return attrs;
    }

    /**
     * LDAP bind password for external GAL sync queries, if not set fallback
     * to zmailGalLdapBindPassword
     *
     * @return zmailGalSyncLdapBindPassword, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=594)
    public String getGalSyncLdapBindPassword() {
        return getAttr(Provisioning.A_zmailGalSyncLdapBindPassword, null);
    }

    /**
     * LDAP bind password for external GAL sync queries, if not set fallback
     * to zmailGalLdapBindPassword
     *
     * @param zmailGalSyncLdapBindPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=594)
    public void setGalSyncLdapBindPassword(String zmailGalSyncLdapBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindPassword, zmailGalSyncLdapBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for external GAL sync queries, if not set fallback
     * to zmailGalLdapBindPassword
     *
     * @param zmailGalSyncLdapBindPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=594)
    public Map<String,Object> setGalSyncLdapBindPassword(String zmailGalSyncLdapBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindPassword, zmailGalSyncLdapBindPassword);
        return attrs;
    }

    /**
     * LDAP bind password for external GAL sync queries, if not set fallback
     * to zmailGalLdapBindPassword
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=594)
    public void unsetGalSyncLdapBindPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for external GAL sync queries, if not set fallback
     * to zmailGalLdapBindPassword
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=594)
    public Map<String,Object> unsetGalSyncLdapBindPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapBindPassword, "");
        return attrs;
    }

    /**
     * LDAP search filter for external GAL sync queries, if not set fallback
     * to zmailGalLdapFilter
     *
     * @return zmailGalSyncLdapFilter, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=591)
    public String getGalSyncLdapFilter() {
        return getAttr(Provisioning.A_zmailGalSyncLdapFilter, null);
    }

    /**
     * LDAP search filter for external GAL sync queries, if not set fallback
     * to zmailGalLdapFilter
     *
     * @param zmailGalSyncLdapFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=591)
    public void setGalSyncLdapFilter(String zmailGalSyncLdapFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapFilter, zmailGalSyncLdapFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for external GAL sync queries, if not set fallback
     * to zmailGalLdapFilter
     *
     * @param zmailGalSyncLdapFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=591)
    public Map<String,Object> setGalSyncLdapFilter(String zmailGalSyncLdapFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapFilter, zmailGalSyncLdapFilter);
        return attrs;
    }

    /**
     * LDAP search filter for external GAL sync queries, if not set fallback
     * to zmailGalLdapFilter
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=591)
    public void unsetGalSyncLdapFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for external GAL sync queries, if not set fallback
     * to zmailGalLdapFilter
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=591)
    public Map<String,Object> unsetGalSyncLdapFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapFilter, "");
        return attrs;
    }

    /**
     * kerberos5 keytab file path for external GAL sync queries, if not set
     * fallback to zmailGalLdapKerberos5Keytab
     *
     * @return zmailGalSyncLdapKerberos5Keytab, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=596)
    public String getGalSyncLdapKerberos5Keytab() {
        return getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Keytab, null);
    }

    /**
     * kerberos5 keytab file path for external GAL sync queries, if not set
     * fallback to zmailGalLdapKerberos5Keytab
     *
     * @param zmailGalSyncLdapKerberos5Keytab new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=596)
    public void setGalSyncLdapKerberos5Keytab(String zmailGalSyncLdapKerberos5Keytab) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Keytab, zmailGalSyncLdapKerberos5Keytab);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 keytab file path for external GAL sync queries, if not set
     * fallback to zmailGalLdapKerberos5Keytab
     *
     * @param zmailGalSyncLdapKerberos5Keytab new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=596)
    public Map<String,Object> setGalSyncLdapKerberos5Keytab(String zmailGalSyncLdapKerberos5Keytab, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Keytab, zmailGalSyncLdapKerberos5Keytab);
        return attrs;
    }

    /**
     * kerberos5 keytab file path for external GAL sync queries, if not set
     * fallback to zmailGalLdapKerberos5Keytab
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=596)
    public void unsetGalSyncLdapKerberos5Keytab() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Keytab, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 keytab file path for external GAL sync queries, if not set
     * fallback to zmailGalLdapKerberos5Keytab
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=596)
    public Map<String,Object> unsetGalSyncLdapKerberos5Keytab(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Keytab, "");
        return attrs;
    }

    /**
     * kerberos5 principal for external GAL sync queries, if not set fallback
     * to zmailGalLdapKerberos5Principal
     *
     * @return zmailGalSyncLdapKerberos5Principal, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=595)
    public String getGalSyncLdapKerberos5Principal() {
        return getAttr(Provisioning.A_zmailGalSyncLdapKerberos5Principal, null);
    }

    /**
     * kerberos5 principal for external GAL sync queries, if not set fallback
     * to zmailGalLdapKerberos5Principal
     *
     * @param zmailGalSyncLdapKerberos5Principal new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=595)
    public void setGalSyncLdapKerberos5Principal(String zmailGalSyncLdapKerberos5Principal) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Principal, zmailGalSyncLdapKerberos5Principal);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 principal for external GAL sync queries, if not set fallback
     * to zmailGalLdapKerberos5Principal
     *
     * @param zmailGalSyncLdapKerberos5Principal new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=595)
    public Map<String,Object> setGalSyncLdapKerberos5Principal(String zmailGalSyncLdapKerberos5Principal, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Principal, zmailGalSyncLdapKerberos5Principal);
        return attrs;
    }

    /**
     * kerberos5 principal for external GAL sync queries, if not set fallback
     * to zmailGalLdapKerberos5Principal
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=595)
    public void unsetGalSyncLdapKerberos5Principal() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Principal, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * kerberos5 principal for external GAL sync queries, if not set fallback
     * to zmailGalLdapKerberos5Principal
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=595)
    public Map<String,Object> unsetGalSyncLdapKerberos5Principal(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapKerberos5Principal, "");
        return attrs;
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL sync. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled. If not set fallback to
     * zmailGalLdapPageSize
     *
     * @return zmailGalSyncLdapPageSize, or 1000 if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=597)
    public int getGalSyncLdapPageSize() {
        return getIntAttr(Provisioning.A_zmailGalSyncLdapPageSize, 1000);
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL sync. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled. If not set fallback to
     * zmailGalLdapPageSize
     *
     * @param zmailGalSyncLdapPageSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=597)
    public void setGalSyncLdapPageSize(int zmailGalSyncLdapPageSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapPageSize, Integer.toString(zmailGalSyncLdapPageSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL sync. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled. If not set fallback to
     * zmailGalLdapPageSize
     *
     * @param zmailGalSyncLdapPageSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=597)
    public Map<String,Object> setGalSyncLdapPageSize(int zmailGalSyncLdapPageSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapPageSize, Integer.toString(zmailGalSyncLdapPageSize));
        return attrs;
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL sync. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled. If not set fallback to
     * zmailGalLdapPageSize
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=597)
    public void unsetGalSyncLdapPageSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapPageSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP page size for paged search control while accessing LDAP server
     * for GAL sync. This applies to both Zmail and external LDAP servers. A
     * value of 0 means paging is not enabled. If not set fallback to
     * zmailGalLdapPageSize
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=597)
    public Map<String,Object> unsetGalSyncLdapPageSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapPageSize, "");
        return attrs;
    }

    /**
     * LDAP search base for external GAL sync queries, if not set fallback to
     * zmailGalLdapSearchBase
     *
     * @return zmailGalSyncLdapSearchBase, or null if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=590)
    public String getGalSyncLdapSearchBase() {
        return getAttr(Provisioning.A_zmailGalSyncLdapSearchBase, null);
    }

    /**
     * LDAP search base for external GAL sync queries, if not set fallback to
     * zmailGalLdapSearchBase
     *
     * @param zmailGalSyncLdapSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=590)
    public void setGalSyncLdapSearchBase(String zmailGalSyncLdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapSearchBase, zmailGalSyncLdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for external GAL sync queries, if not set fallback to
     * zmailGalLdapSearchBase
     *
     * @param zmailGalSyncLdapSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=590)
    public Map<String,Object> setGalSyncLdapSearchBase(String zmailGalSyncLdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapSearchBase, zmailGalSyncLdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for external GAL sync queries, if not set fallback to
     * zmailGalLdapSearchBase
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=590)
    public void unsetGalSyncLdapSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for external GAL sync queries, if not set fallback to
     * zmailGalLdapSearchBase
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=590)
    public Map<String,Object> unsetGalSyncLdapSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapSearchBase, "");
        return attrs;
    }

    /**
     * whether to use startTLS for external GAL sync, if not set fallback to
     * zmailGalLdapStartTlsEnabled
     *
     * @return zmailGalSyncLdapStartTlsEnabled, or false if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=656)
    public boolean isGalSyncLdapStartTlsEnabled() {
        return getBooleanAttr(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, false);
    }

    /**
     * whether to use startTLS for external GAL sync, if not set fallback to
     * zmailGalLdapStartTlsEnabled
     *
     * @param zmailGalSyncLdapStartTlsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=656)
    public void setGalSyncLdapStartTlsEnabled(boolean zmailGalSyncLdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, zmailGalSyncLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to use startTLS for external GAL sync, if not set fallback to
     * zmailGalLdapStartTlsEnabled
     *
     * @param zmailGalSyncLdapStartTlsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=656)
    public Map<String,Object> setGalSyncLdapStartTlsEnabled(boolean zmailGalSyncLdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, zmailGalSyncLdapStartTlsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether to use startTLS for external GAL sync, if not set fallback to
     * zmailGalLdapStartTlsEnabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=656)
    public void unsetGalSyncLdapStartTlsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to use startTLS for external GAL sync, if not set fallback to
     * zmailGalLdapStartTlsEnabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=656)
    public Map<String,Object> unsetGalSyncLdapStartTlsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapStartTlsEnabled, "");
        return attrs;
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @return zmailGalSyncLdapURL, or empty array if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public String[] getGalSyncLdapURL() {
        return getMultiAttr(Provisioning.A_zmailGalSyncLdapURL);
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param zmailGalSyncLdapURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public void setGalSyncLdapURL(String[] zmailGalSyncLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapURL, zmailGalSyncLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param zmailGalSyncLdapURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public Map<String,Object> setGalSyncLdapURL(String[] zmailGalSyncLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapURL, zmailGalSyncLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param zmailGalSyncLdapURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public void addGalSyncLdapURL(String zmailGalSyncLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalSyncLdapURL, zmailGalSyncLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param zmailGalSyncLdapURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public Map<String,Object> addGalSyncLdapURL(String zmailGalSyncLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailGalSyncLdapURL, zmailGalSyncLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param zmailGalSyncLdapURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public void removeGalSyncLdapURL(String zmailGalSyncLdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalSyncLdapURL, zmailGalSyncLdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param zmailGalSyncLdapURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public Map<String,Object> removeGalSyncLdapURL(String zmailGalSyncLdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailGalSyncLdapURL, zmailGalSyncLdapURL);
        return attrs;
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public void unsetGalSyncLdapURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL for external GAL sync, if not set fallback to
     * zmailGalLdapURL
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=589)
    public Map<String,Object> unsetGalSyncLdapURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncLdapURL, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent GAL sync requests allowed on the system /
     * domain.
     *
     * @return zmailGalSyncMaxConcurrentClients, or 2 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1154)
    public int getGalSyncMaxConcurrentClients() {
        return getIntAttr(Provisioning.A_zmailGalSyncMaxConcurrentClients, 2);
    }

    /**
     * Maximum number of concurrent GAL sync requests allowed on the system /
     * domain.
     *
     * @param zmailGalSyncMaxConcurrentClients new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1154)
    public void setGalSyncMaxConcurrentClients(int zmailGalSyncMaxConcurrentClients) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncMaxConcurrentClients, Integer.toString(zmailGalSyncMaxConcurrentClients));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent GAL sync requests allowed on the system /
     * domain.
     *
     * @param zmailGalSyncMaxConcurrentClients new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1154)
    public Map<String,Object> setGalSyncMaxConcurrentClients(int zmailGalSyncMaxConcurrentClients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncMaxConcurrentClients, Integer.toString(zmailGalSyncMaxConcurrentClients));
        return attrs;
    }

    /**
     * Maximum number of concurrent GAL sync requests allowed on the system /
     * domain.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1154)
    public void unsetGalSyncMaxConcurrentClients() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncMaxConcurrentClients, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent GAL sync requests allowed on the system /
     * domain.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1154)
    public Map<String,Object> unsetGalSyncMaxConcurrentClients(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncMaxConcurrentClients, "");
        return attrs;
    }

    /**
     * LDAP generalized time format for external GAL sync
     *
     * @return zmailGalSyncTimestampFormat, or "yyyyMMddHHmmss'Z'" if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1019)
    public String getGalSyncTimestampFormat() {
        return getAttr(Provisioning.A_zmailGalSyncTimestampFormat, "yyyyMMddHHmmss'Z'");
    }

    /**
     * LDAP generalized time format for external GAL sync
     *
     * @param zmailGalSyncTimestampFormat new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1019)
    public void setGalSyncTimestampFormat(String zmailGalSyncTimestampFormat) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncTimestampFormat, zmailGalSyncTimestampFormat);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP generalized time format for external GAL sync
     *
     * @param zmailGalSyncTimestampFormat new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1019)
    public Map<String,Object> setGalSyncTimestampFormat(String zmailGalSyncTimestampFormat, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncTimestampFormat, zmailGalSyncTimestampFormat);
        return attrs;
    }

    /**
     * LDAP generalized time format for external GAL sync
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1019)
    public void unsetGalSyncTimestampFormat() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncTimestampFormat, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP generalized time format for external GAL sync
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1019)
    public Map<String,Object> unsetGalSyncTimestampFormat(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalSyncTimestampFormat, "");
        return attrs;
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @return zmailGalTokenizeAutoCompleteKey, or ZAttrProvisioning.GalTokenizeAutoCompleteKey.and if unset and/or has invalid value
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public ZAttrProvisioning.GalTokenizeAutoCompleteKey getGalTokenizeAutoCompleteKey() {
        try { String v = getAttr(Provisioning.A_zmailGalTokenizeAutoCompleteKey); return v == null ? ZAttrProvisioning.GalTokenizeAutoCompleteKey.and : ZAttrProvisioning.GalTokenizeAutoCompleteKey.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.GalTokenizeAutoCompleteKey.and; }
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @return zmailGalTokenizeAutoCompleteKey, or "and" if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public String getGalTokenizeAutoCompleteKeyAsString() {
        return getAttr(Provisioning.A_zmailGalTokenizeAutoCompleteKey, "and");
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeAutoCompleteKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public void setGalTokenizeAutoCompleteKey(ZAttrProvisioning.GalTokenizeAutoCompleteKey zmailGalTokenizeAutoCompleteKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeAutoCompleteKey, zmailGalTokenizeAutoCompleteKey.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeAutoCompleteKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public Map<String,Object> setGalTokenizeAutoCompleteKey(ZAttrProvisioning.GalTokenizeAutoCompleteKey zmailGalTokenizeAutoCompleteKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeAutoCompleteKey, zmailGalTokenizeAutoCompleteKey.toString());
        return attrs;
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeAutoCompleteKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public void setGalTokenizeAutoCompleteKeyAsString(String zmailGalTokenizeAutoCompleteKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeAutoCompleteKey, zmailGalTokenizeAutoCompleteKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeAutoCompleteKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public Map<String,Object> setGalTokenizeAutoCompleteKeyAsString(String zmailGalTokenizeAutoCompleteKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeAutoCompleteKey, zmailGalTokenizeAutoCompleteKey);
        return attrs;
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public void unsetGalTokenizeAutoCompleteKey() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeAutoCompleteKey, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * auto complete, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=599)
    public Map<String,Object> unsetGalTokenizeAutoCompleteKey(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeAutoCompleteKey, "");
        return attrs;
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @return zmailGalTokenizeSearchKey, or ZAttrProvisioning.GalTokenizeSearchKey.and if unset and/or has invalid value
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public ZAttrProvisioning.GalTokenizeSearchKey getGalTokenizeSearchKey() {
        try { String v = getAttr(Provisioning.A_zmailGalTokenizeSearchKey); return v == null ? ZAttrProvisioning.GalTokenizeSearchKey.and : ZAttrProvisioning.GalTokenizeSearchKey.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.GalTokenizeSearchKey.and; }
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @return zmailGalTokenizeSearchKey, or "and" if unset
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public String getGalTokenizeSearchKeyAsString() {
        return getAttr(Provisioning.A_zmailGalTokenizeSearchKey, "and");
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeSearchKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public void setGalTokenizeSearchKey(ZAttrProvisioning.GalTokenizeSearchKey zmailGalTokenizeSearchKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeSearchKey, zmailGalTokenizeSearchKey.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeSearchKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public Map<String,Object> setGalTokenizeSearchKey(ZAttrProvisioning.GalTokenizeSearchKey zmailGalTokenizeSearchKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeSearchKey, zmailGalTokenizeSearchKey.toString());
        return attrs;
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeSearchKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public void setGalTokenizeSearchKeyAsString(String zmailGalTokenizeSearchKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeSearchKey, zmailGalTokenizeSearchKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param zmailGalTokenizeSearchKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public Map<String,Object> setGalTokenizeSearchKeyAsString(String zmailGalTokenizeSearchKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeSearchKey, zmailGalTokenizeSearchKey);
        return attrs;
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public void unsetGalTokenizeSearchKey() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeSearchKey, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to tokenize key and AND or OR the tokenized queries for GAL
     * search, if not set, key is not tokenized
     *
     * <p>Valid values: [or, and]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.2
     */
    @ZAttr(id=600)
    public Map<String,Object> unsetGalTokenizeSearchKey(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGalTokenizeSearchKey, "");
        return attrs;
    }

    /**
     * help URL for admin
     *
     * @return zmailHelpAdminURL, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=674)
    public String getHelpAdminURL() {
        return getAttr(Provisioning.A_zmailHelpAdminURL, null);
    }

    /**
     * help URL for admin
     *
     * @param zmailHelpAdminURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=674)
    public void setHelpAdminURL(String zmailHelpAdminURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdminURL, zmailHelpAdminURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for admin
     *
     * @param zmailHelpAdminURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=674)
    public Map<String,Object> setHelpAdminURL(String zmailHelpAdminURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdminURL, zmailHelpAdminURL);
        return attrs;
    }

    /**
     * help URL for admin
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=674)
    public void unsetHelpAdminURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdminURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for admin
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=674)
    public Map<String,Object> unsetHelpAdminURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdminURL, "");
        return attrs;
    }

    /**
     * help URL for advanced client
     *
     * @return zmailHelpAdvancedURL, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=676)
    public String getHelpAdvancedURL() {
        return getAttr(Provisioning.A_zmailHelpAdvancedURL, null);
    }

    /**
     * help URL for advanced client
     *
     * @param zmailHelpAdvancedURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=676)
    public void setHelpAdvancedURL(String zmailHelpAdvancedURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdvancedURL, zmailHelpAdvancedURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for advanced client
     *
     * @param zmailHelpAdvancedURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=676)
    public Map<String,Object> setHelpAdvancedURL(String zmailHelpAdvancedURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdvancedURL, zmailHelpAdvancedURL);
        return attrs;
    }

    /**
     * help URL for advanced client
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=676)
    public void unsetHelpAdvancedURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdvancedURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for advanced client
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=676)
    public Map<String,Object> unsetHelpAdvancedURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpAdvancedURL, "");
        return attrs;
    }

    /**
     * help URL for delegated admin
     *
     * @return zmailHelpDelegatedURL, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=675)
    public String getHelpDelegatedURL() {
        return getAttr(Provisioning.A_zmailHelpDelegatedURL, null);
    }

    /**
     * help URL for delegated admin
     *
     * @param zmailHelpDelegatedURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=675)
    public void setHelpDelegatedURL(String zmailHelpDelegatedURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpDelegatedURL, zmailHelpDelegatedURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for delegated admin
     *
     * @param zmailHelpDelegatedURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=675)
    public Map<String,Object> setHelpDelegatedURL(String zmailHelpDelegatedURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpDelegatedURL, zmailHelpDelegatedURL);
        return attrs;
    }

    /**
     * help URL for delegated admin
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=675)
    public void unsetHelpDelegatedURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpDelegatedURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for delegated admin
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=675)
    public Map<String,Object> unsetHelpDelegatedURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpDelegatedURL, "");
        return attrs;
    }

    /**
     * help URL for standard client
     *
     * @return zmailHelpStandardURL, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=677)
    public String getHelpStandardURL() {
        return getAttr(Provisioning.A_zmailHelpStandardURL, null);
    }

    /**
     * help URL for standard client
     *
     * @param zmailHelpStandardURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=677)
    public void setHelpStandardURL(String zmailHelpStandardURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpStandardURL, zmailHelpStandardURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for standard client
     *
     * @param zmailHelpStandardURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=677)
    public Map<String,Object> setHelpStandardURL(String zmailHelpStandardURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpStandardURL, zmailHelpStandardURL);
        return attrs;
    }

    /**
     * help URL for standard client
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=677)
    public void unsetHelpStandardURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpStandardURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * help URL for standard client
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=677)
    public Map<String,Object> unsetHelpStandardURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHelpStandardURL, "");
        return attrs;
    }

    /**
     * Zmail Systems Unique ID
     *
     * @return zmailId, or null if unset
     */
    @ZAttr(id=1)
    public String getId() {
        return getAttr(Provisioning.A_zmailId, null);
    }

    /**
     * Zmail Systems Unique ID
     *
     * @param zmailId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=1)
    public void setId(String zmailId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailId, zmailId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zmail Systems Unique ID
     *
     * @param zmailId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=1)
    public Map<String,Object> setId(String zmailId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailId, zmailId);
        return attrs;
    }

    /**
     * Zmail Systems Unique ID
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=1)
    public void unsetId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zmail Systems Unique ID
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=1)
    public Map<String,Object> unsetId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailId, "");
        return attrs;
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @return zmailInternalSendersDomain, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public String[] getInternalSendersDomain() {
        return getMultiAttr(Provisioning.A_zmailInternalSendersDomain);
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param zmailInternalSendersDomain new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public void setInternalSendersDomain(String[] zmailInternalSendersDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSendersDomain, zmailInternalSendersDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param zmailInternalSendersDomain new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public Map<String,Object> setInternalSendersDomain(String[] zmailInternalSendersDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSendersDomain, zmailInternalSendersDomain);
        return attrs;
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param zmailInternalSendersDomain new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public void addInternalSendersDomain(String zmailInternalSendersDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailInternalSendersDomain, zmailInternalSendersDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param zmailInternalSendersDomain new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public Map<String,Object> addInternalSendersDomain(String zmailInternalSendersDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailInternalSendersDomain, zmailInternalSendersDomain);
        return attrs;
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param zmailInternalSendersDomain existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public void removeInternalSendersDomain(String zmailInternalSendersDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailInternalSendersDomain, zmailInternalSendersDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param zmailInternalSendersDomain existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public Map<String,Object> removeInternalSendersDomain(String zmailInternalSendersDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailInternalSendersDomain, zmailInternalSendersDomain);
        return attrs;
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public void unsetInternalSendersDomain() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSendersDomain, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * additional domains considered as internal w.r.t. recipient
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1319)
    public Map<String,Object> unsetInternalSendersDomain(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSendersDomain, "");
        return attrs;
    }

    /**
     * whether sharing with accounts and groups of all other domains hosted
     * on this deployment be considered internal sharing
     *
     * @return zmailInternalSharingCrossDomainEnabled, or true if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1386)
    public boolean isInternalSharingCrossDomainEnabled() {
        return getBooleanAttr(Provisioning.A_zmailInternalSharingCrossDomainEnabled, true);
    }

    /**
     * whether sharing with accounts and groups of all other domains hosted
     * on this deployment be considered internal sharing
     *
     * @param zmailInternalSharingCrossDomainEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1386)
    public void setInternalSharingCrossDomainEnabled(boolean zmailInternalSharingCrossDomainEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingCrossDomainEnabled, zmailInternalSharingCrossDomainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether sharing with accounts and groups of all other domains hosted
     * on this deployment be considered internal sharing
     *
     * @param zmailInternalSharingCrossDomainEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1386)
    public Map<String,Object> setInternalSharingCrossDomainEnabled(boolean zmailInternalSharingCrossDomainEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingCrossDomainEnabled, zmailInternalSharingCrossDomainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether sharing with accounts and groups of all other domains hosted
     * on this deployment be considered internal sharing
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1386)
    public void unsetInternalSharingCrossDomainEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingCrossDomainEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether sharing with accounts and groups of all other domains hosted
     * on this deployment be considered internal sharing
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1386)
    public Map<String,Object> unsetInternalSharingCrossDomainEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingCrossDomainEnabled, "");
        return attrs;
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @return zmailInternalSharingDomain, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public String[] getInternalSharingDomain() {
        return getMultiAttr(Provisioning.A_zmailInternalSharingDomain);
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param zmailInternalSharingDomain new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public void setInternalSharingDomain(String[] zmailInternalSharingDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingDomain, zmailInternalSharingDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param zmailInternalSharingDomain new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public Map<String,Object> setInternalSharingDomain(String[] zmailInternalSharingDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingDomain, zmailInternalSharingDomain);
        return attrs;
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param zmailInternalSharingDomain new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public void addInternalSharingDomain(String zmailInternalSharingDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailInternalSharingDomain, zmailInternalSharingDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param zmailInternalSharingDomain new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public Map<String,Object> addInternalSharingDomain(String zmailInternalSharingDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailInternalSharingDomain, zmailInternalSharingDomain);
        return attrs;
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param zmailInternalSharingDomain existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public void removeInternalSharingDomain(String zmailInternalSharingDomain) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailInternalSharingDomain, zmailInternalSharingDomain);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param zmailInternalSharingDomain existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public Map<String,Object> removeInternalSharingDomain(String zmailInternalSharingDomain, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailInternalSharingDomain, zmailInternalSharingDomain);
        return attrs;
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public void unsetInternalSharingDomain() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingDomain, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Domains hosted on this deployment, accounts and groups of which are
     * considered internal during sharing. Applicable when
     * zmailInternalSharingCrossDomainEnabled is set to FALSE.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1387)
    public Map<String,Object> unsetInternalSharingDomain(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailInternalSharingDomain, "");
        return attrs;
    }

    /**
     * whether ldap based galsync disabled or not
     *
     * @return zmailLdapGalSyncDisabled, or false if unset
     *
     * @since ZCS 7.2.2
     */
    @ZAttr(id=1420)
    public boolean isLdapGalSyncDisabled() {
        return getBooleanAttr(Provisioning.A_zmailLdapGalSyncDisabled, false);
    }

    /**
     * whether ldap based galsync disabled or not
     *
     * @param zmailLdapGalSyncDisabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.2
     */
    @ZAttr(id=1420)
    public void setLdapGalSyncDisabled(boolean zmailLdapGalSyncDisabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLdapGalSyncDisabled, zmailLdapGalSyncDisabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether ldap based galsync disabled or not
     *
     * @param zmailLdapGalSyncDisabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.2
     */
    @ZAttr(id=1420)
    public Map<String,Object> setLdapGalSyncDisabled(boolean zmailLdapGalSyncDisabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLdapGalSyncDisabled, zmailLdapGalSyncDisabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether ldap based galsync disabled or not
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.2
     */
    @ZAttr(id=1420)
    public void unsetLdapGalSyncDisabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLdapGalSyncDisabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether ldap based galsync disabled or not
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.2
     */
    @ZAttr(id=1420)
    public Map<String,Object> unsetLdapGalSyncDisabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLdapGalSyncDisabled, "");
        return attrs;
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @return zmailLocale, or null if unset
     */
    @ZAttr(id=345)
    public String getLocaleAsString() {
        return getAttr(Provisioning.A_zmailLocale, null);
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @param zmailLocale new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=345)
    public void setLocale(String zmailLocale) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLocale, zmailLocale);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @param zmailLocale new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=345)
    public Map<String,Object> setLocale(String zmailLocale, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLocale, zmailLocale);
        return attrs;
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=345)
    public void unsetLocale() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLocale, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=345)
    public Map<String,Object> unsetLocale(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLocale, "");
        return attrs;
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @return zmailMailAddressValidationRegex, or empty array if unset
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public String[] getMailAddressValidationRegex() {
        return getMultiAttr(Provisioning.A_zmailMailAddressValidationRegex);
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param zmailMailAddressValidationRegex new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public void setMailAddressValidationRegex(String[] zmailMailAddressValidationRegex) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailAddressValidationRegex, zmailMailAddressValidationRegex);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param zmailMailAddressValidationRegex new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public Map<String,Object> setMailAddressValidationRegex(String[] zmailMailAddressValidationRegex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailAddressValidationRegex, zmailMailAddressValidationRegex);
        return attrs;
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param zmailMailAddressValidationRegex new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public void addMailAddressValidationRegex(String zmailMailAddressValidationRegex) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMailAddressValidationRegex, zmailMailAddressValidationRegex);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param zmailMailAddressValidationRegex new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public Map<String,Object> addMailAddressValidationRegex(String zmailMailAddressValidationRegex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMailAddressValidationRegex, zmailMailAddressValidationRegex);
        return attrs;
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param zmailMailAddressValidationRegex existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public void removeMailAddressValidationRegex(String zmailMailAddressValidationRegex) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMailAddressValidationRegex, zmailMailAddressValidationRegex);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param zmailMailAddressValidationRegex existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public Map<String,Object> removeMailAddressValidationRegex(String zmailMailAddressValidationRegex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMailAddressValidationRegex, zmailMailAddressValidationRegex);
        return attrs;
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public void unsetMailAddressValidationRegex() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailAddressValidationRegex, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * optional regex used by web client to validate email address
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1241)
    public Map<String,Object> unsetMailAddressValidationRegex(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailAddressValidationRegex, "");
        return attrs;
    }

    /**
     * Maximum mailbox quota for the domain in bytes. The effective quota for
     * a mailbox would be the minimum of this and zmailMailQuota.
     *
     * @return zmailMailDomainQuota, or 0 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1326)
    public long getMailDomainQuota() {
        return getLongAttr(Provisioning.A_zmailMailDomainQuota, 0L);
    }

    /**
     * Maximum mailbox quota for the domain in bytes. The effective quota for
     * a mailbox would be the minimum of this and zmailMailQuota.
     *
     * @param zmailMailDomainQuota new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1326)
    public void setMailDomainQuota(long zmailMailDomainQuota) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDomainQuota, Long.toString(zmailMailDomainQuota));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum mailbox quota for the domain in bytes. The effective quota for
     * a mailbox would be the minimum of this and zmailMailQuota.
     *
     * @param zmailMailDomainQuota new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1326)
    public Map<String,Object> setMailDomainQuota(long zmailMailDomainQuota, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDomainQuota, Long.toString(zmailMailDomainQuota));
        return attrs;
    }

    /**
     * Maximum mailbox quota for the domain in bytes. The effective quota for
     * a mailbox would be the minimum of this and zmailMailQuota.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1326)
    public void unsetMailDomainQuota() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDomainQuota, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum mailbox quota for the domain in bytes. The effective quota for
     * a mailbox would be the minimum of this and zmailMailQuota.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1326)
    public Map<String,Object> unsetMailDomainQuota(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDomainQuota, "");
        return attrs;
    }

    /**
     * Map from a certificate field to a Zmail account key that can uniquely
     * identify a Zmail account for client certificate authentication. Value
     * is a comma-separated list of mapping rules, each mapping maps a
     * certificate field to a Zmail account key. Each is attempted in
     * sequence until a unique account can be resolved. e.g. a value can be:
     * SUBJECTALTNAME_OTHERNAME_UPN=zmailForeignPrincipal,(uid=%{SUBJECT_CN})
     * value: comma-separated mapping-rule mapping-rule:
     * {cert-field-to-zmail-key-map} | {LDAP-filter}
     * cert-field-to-zmail-key-map: {certificate-field}={Zmail-account-key}
     * certificate-field: SUBJECT_{an RDN attr, e.g. CN}: a RND in DN of
     * Subject SUBJECT_DN: entire DN of Subject SUBJECTALTNAME_OTHERNAME_UPN:
     * UPN(aka Principal Name) in otherName in subjectAltName extension
     * SUBJECTALTNAME_RFC822NAME: rfc822Name in subjectAltName extension
     * Zmail-account-key: name: primary name or any of the aliases of an
     * account zmailId: zmailId of an account zmailForeignPrincipal:
     * zmailForeignPrincipal of an account. The matching value on the
     * zmailForeignPrincipal must be prefixed with &quot;cert
     * {supported-certificate-filed}:&quot; e.g. cert
     * SUBJECTALTNAME_OTHERNAME_UPN:123456@mydomain LDAP-filter: An LDAP
     * filter template with placeholders to be substituted by certificate
     * field values. (objectClass=zmailAccount) is internally ANDed with the
     * supplied filter. e.g.
     * (|(uid=%{SUBJECT_CN})(mail=%{SUBJECTALTNAME_RFC822NAME})) Note: it is
     * recommended not to use LDAP-filter rule, as it will trigger an LDAP
     * search for each cert auth request. LDAP-filter is disabled by default.
     * To enable it globally, set
     * zmailMailSSLClientCertPrincipalMapLdapFilterEnabled on global config
     * to TRUE. If LDAP-filter is not enabled, all client certificate
     * authentication will fail on domains configured with LDAP-filter.
     *
     * @return zmailMailSSLClientCertPrincipalMap, or "SUBJECT_EMAILADDRESS=name" if unset
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1215)
    public String getMailSSLClientCertPrincipalMap() {
        return getAttr(Provisioning.A_zmailMailSSLClientCertPrincipalMap, "SUBJECT_EMAILADDRESS=name");
    }

    /**
     * Map from a certificate field to a Zmail account key that can uniquely
     * identify a Zmail account for client certificate authentication. Value
     * is a comma-separated list of mapping rules, each mapping maps a
     * certificate field to a Zmail account key. Each is attempted in
     * sequence until a unique account can be resolved. e.g. a value can be:
     * SUBJECTALTNAME_OTHERNAME_UPN=zmailForeignPrincipal,(uid=%{SUBJECT_CN})
     * value: comma-separated mapping-rule mapping-rule:
     * {cert-field-to-zmail-key-map} | {LDAP-filter}
     * cert-field-to-zmail-key-map: {certificate-field}={Zmail-account-key}
     * certificate-field: SUBJECT_{an RDN attr, e.g. CN}: a RND in DN of
     * Subject SUBJECT_DN: entire DN of Subject SUBJECTALTNAME_OTHERNAME_UPN:
     * UPN(aka Principal Name) in otherName in subjectAltName extension
     * SUBJECTALTNAME_RFC822NAME: rfc822Name in subjectAltName extension
     * Zmail-account-key: name: primary name or any of the aliases of an
     * account zmailId: zmailId of an account zmailForeignPrincipal:
     * zmailForeignPrincipal of an account. The matching value on the
     * zmailForeignPrincipal must be prefixed with &quot;cert
     * {supported-certificate-filed}:&quot; e.g. cert
     * SUBJECTALTNAME_OTHERNAME_UPN:123456@mydomain LDAP-filter: An LDAP
     * filter template with placeholders to be substituted by certificate
     * field values. (objectClass=zmailAccount) is internally ANDed with the
     * supplied filter. e.g.
     * (|(uid=%{SUBJECT_CN})(mail=%{SUBJECTALTNAME_RFC822NAME})) Note: it is
     * recommended not to use LDAP-filter rule, as it will trigger an LDAP
     * search for each cert auth request. LDAP-filter is disabled by default.
     * To enable it globally, set
     * zmailMailSSLClientCertPrincipalMapLdapFilterEnabled on global config
     * to TRUE. If LDAP-filter is not enabled, all client certificate
     * authentication will fail on domains configured with LDAP-filter.
     *
     * @param zmailMailSSLClientCertPrincipalMap new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1215)
    public void setMailSSLClientCertPrincipalMap(String zmailMailSSLClientCertPrincipalMap) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPrincipalMap, zmailMailSSLClientCertPrincipalMap);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Map from a certificate field to a Zmail account key that can uniquely
     * identify a Zmail account for client certificate authentication. Value
     * is a comma-separated list of mapping rules, each mapping maps a
     * certificate field to a Zmail account key. Each is attempted in
     * sequence until a unique account can be resolved. e.g. a value can be:
     * SUBJECTALTNAME_OTHERNAME_UPN=zmailForeignPrincipal,(uid=%{SUBJECT_CN})
     * value: comma-separated mapping-rule mapping-rule:
     * {cert-field-to-zmail-key-map} | {LDAP-filter}
     * cert-field-to-zmail-key-map: {certificate-field}={Zmail-account-key}
     * certificate-field: SUBJECT_{an RDN attr, e.g. CN}: a RND in DN of
     * Subject SUBJECT_DN: entire DN of Subject SUBJECTALTNAME_OTHERNAME_UPN:
     * UPN(aka Principal Name) in otherName in subjectAltName extension
     * SUBJECTALTNAME_RFC822NAME: rfc822Name in subjectAltName extension
     * Zmail-account-key: name: primary name or any of the aliases of an
     * account zmailId: zmailId of an account zmailForeignPrincipal:
     * zmailForeignPrincipal of an account. The matching value on the
     * zmailForeignPrincipal must be prefixed with &quot;cert
     * {supported-certificate-filed}:&quot; e.g. cert
     * SUBJECTALTNAME_OTHERNAME_UPN:123456@mydomain LDAP-filter: An LDAP
     * filter template with placeholders to be substituted by certificate
     * field values. (objectClass=zmailAccount) is internally ANDed with the
     * supplied filter. e.g.
     * (|(uid=%{SUBJECT_CN})(mail=%{SUBJECTALTNAME_RFC822NAME})) Note: it is
     * recommended not to use LDAP-filter rule, as it will trigger an LDAP
     * search for each cert auth request. LDAP-filter is disabled by default.
     * To enable it globally, set
     * zmailMailSSLClientCertPrincipalMapLdapFilterEnabled on global config
     * to TRUE. If LDAP-filter is not enabled, all client certificate
     * authentication will fail on domains configured with LDAP-filter.
     *
     * @param zmailMailSSLClientCertPrincipalMap new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1215)
    public Map<String,Object> setMailSSLClientCertPrincipalMap(String zmailMailSSLClientCertPrincipalMap, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPrincipalMap, zmailMailSSLClientCertPrincipalMap);
        return attrs;
    }

    /**
     * Map from a certificate field to a Zmail account key that can uniquely
     * identify a Zmail account for client certificate authentication. Value
     * is a comma-separated list of mapping rules, each mapping maps a
     * certificate field to a Zmail account key. Each is attempted in
     * sequence until a unique account can be resolved. e.g. a value can be:
     * SUBJECTALTNAME_OTHERNAME_UPN=zmailForeignPrincipal,(uid=%{SUBJECT_CN})
     * value: comma-separated mapping-rule mapping-rule:
     * {cert-field-to-zmail-key-map} | {LDAP-filter}
     * cert-field-to-zmail-key-map: {certificate-field}={Zmail-account-key}
     * certificate-field: SUBJECT_{an RDN attr, e.g. CN}: a RND in DN of
     * Subject SUBJECT_DN: entire DN of Subject SUBJECTALTNAME_OTHERNAME_UPN:
     * UPN(aka Principal Name) in otherName in subjectAltName extension
     * SUBJECTALTNAME_RFC822NAME: rfc822Name in subjectAltName extension
     * Zmail-account-key: name: primary name or any of the aliases of an
     * account zmailId: zmailId of an account zmailForeignPrincipal:
     * zmailForeignPrincipal of an account. The matching value on the
     * zmailForeignPrincipal must be prefixed with &quot;cert
     * {supported-certificate-filed}:&quot; e.g. cert
     * SUBJECTALTNAME_OTHERNAME_UPN:123456@mydomain LDAP-filter: An LDAP
     * filter template with placeholders to be substituted by certificate
     * field values. (objectClass=zmailAccount) is internally ANDed with the
     * supplied filter. e.g.
     * (|(uid=%{SUBJECT_CN})(mail=%{SUBJECTALTNAME_RFC822NAME})) Note: it is
     * recommended not to use LDAP-filter rule, as it will trigger an LDAP
     * search for each cert auth request. LDAP-filter is disabled by default.
     * To enable it globally, set
     * zmailMailSSLClientCertPrincipalMapLdapFilterEnabled on global config
     * to TRUE. If LDAP-filter is not enabled, all client certificate
     * authentication will fail on domains configured with LDAP-filter.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1215)
    public void unsetMailSSLClientCertPrincipalMap() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPrincipalMap, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Map from a certificate field to a Zmail account key that can uniquely
     * identify a Zmail account for client certificate authentication. Value
     * is a comma-separated list of mapping rules, each mapping maps a
     * certificate field to a Zmail account key. Each is attempted in
     * sequence until a unique account can be resolved. e.g. a value can be:
     * SUBJECTALTNAME_OTHERNAME_UPN=zmailForeignPrincipal,(uid=%{SUBJECT_CN})
     * value: comma-separated mapping-rule mapping-rule:
     * {cert-field-to-zmail-key-map} | {LDAP-filter}
     * cert-field-to-zmail-key-map: {certificate-field}={Zmail-account-key}
     * certificate-field: SUBJECT_{an RDN attr, e.g. CN}: a RND in DN of
     * Subject SUBJECT_DN: entire DN of Subject SUBJECTALTNAME_OTHERNAME_UPN:
     * UPN(aka Principal Name) in otherName in subjectAltName extension
     * SUBJECTALTNAME_RFC822NAME: rfc822Name in subjectAltName extension
     * Zmail-account-key: name: primary name or any of the aliases of an
     * account zmailId: zmailId of an account zmailForeignPrincipal:
     * zmailForeignPrincipal of an account. The matching value on the
     * zmailForeignPrincipal must be prefixed with &quot;cert
     * {supported-certificate-filed}:&quot; e.g. cert
     * SUBJECTALTNAME_OTHERNAME_UPN:123456@mydomain LDAP-filter: An LDAP
     * filter template with placeholders to be substituted by certificate
     * field values. (objectClass=zmailAccount) is internally ANDed with the
     * supplied filter. e.g.
     * (|(uid=%{SUBJECT_CN})(mail=%{SUBJECTALTNAME_RFC822NAME})) Note: it is
     * recommended not to use LDAP-filter rule, as it will trigger an LDAP
     * search for each cert auth request. LDAP-filter is disabled by default.
     * To enable it globally, set
     * zmailMailSSLClientCertPrincipalMapLdapFilterEnabled on global config
     * to TRUE. If LDAP-filter is not enabled, all client certificate
     * authentication will fail on domains configured with LDAP-filter.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.2
     */
    @ZAttr(id=1215)
    public Map<String,Object> unsetMailSSLClientCertPrincipalMap(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPrincipalMap, "");
        return attrs;
    }

    /**
     * Maximum number of entries for zmailPrefMailTrustedSenderList.
     *
     * @return zmailMailTrustedSenderListMaxNumEntries, or -1 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1139)
    public int getMailTrustedSenderListMaxNumEntries() {
        return getIntAttr(Provisioning.A_zmailMailTrustedSenderListMaxNumEntries, -1);
    }

    /**
     * Maximum number of entries for zmailPrefMailTrustedSenderList.
     *
     * @param zmailMailTrustedSenderListMaxNumEntries new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1139)
    public void setMailTrustedSenderListMaxNumEntries(int zmailMailTrustedSenderListMaxNumEntries) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedSenderListMaxNumEntries, Integer.toString(zmailMailTrustedSenderListMaxNumEntries));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of entries for zmailPrefMailTrustedSenderList.
     *
     * @param zmailMailTrustedSenderListMaxNumEntries new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1139)
    public Map<String,Object> setMailTrustedSenderListMaxNumEntries(int zmailMailTrustedSenderListMaxNumEntries, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedSenderListMaxNumEntries, Integer.toString(zmailMailTrustedSenderListMaxNumEntries));
        return attrs;
    }

    /**
     * Maximum number of entries for zmailPrefMailTrustedSenderList.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1139)
    public void unsetMailTrustedSenderListMaxNumEntries() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedSenderListMaxNumEntries, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of entries for zmailPrefMailTrustedSenderList.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1139)
    public Map<String,Object> unsetMailTrustedSenderListMaxNumEntries(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedSenderListMaxNumEntries, "");
        return attrs;
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @return zmailMobileItemsToTrackPerFolderMaxSize, or empty array if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public String[] getMobileItemsToTrackPerFolderMaxSize() {
        return getMultiAttr(Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize);
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param zmailMobileItemsToTrackPerFolderMaxSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public void setMobileItemsToTrackPerFolderMaxSize(String[] zmailMobileItemsToTrackPerFolderMaxSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, zmailMobileItemsToTrackPerFolderMaxSize);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param zmailMobileItemsToTrackPerFolderMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public Map<String,Object> setMobileItemsToTrackPerFolderMaxSize(String[] zmailMobileItemsToTrackPerFolderMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, zmailMobileItemsToTrackPerFolderMaxSize);
        return attrs;
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param zmailMobileItemsToTrackPerFolderMaxSize new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public void addMobileItemsToTrackPerFolderMaxSize(String zmailMobileItemsToTrackPerFolderMaxSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, zmailMobileItemsToTrackPerFolderMaxSize);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param zmailMobileItemsToTrackPerFolderMaxSize new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public Map<String,Object> addMobileItemsToTrackPerFolderMaxSize(String zmailMobileItemsToTrackPerFolderMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, zmailMobileItemsToTrackPerFolderMaxSize);
        return attrs;
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param zmailMobileItemsToTrackPerFolderMaxSize existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public void removeMobileItemsToTrackPerFolderMaxSize(String zmailMobileItemsToTrackPerFolderMaxSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, zmailMobileItemsToTrackPerFolderMaxSize);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param zmailMobileItemsToTrackPerFolderMaxSize existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public Map<String,Object> removeMobileItemsToTrackPerFolderMaxSize(String zmailMobileItemsToTrackPerFolderMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, zmailMobileItemsToTrackPerFolderMaxSize);
        return attrs;
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public void unsetMobileItemsToTrackPerFolderMaxSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Max size of items in a folder that server tracks, categorized by
     * collection type (Email,Calendar,Contacts,Tasks). e.g. Email:3000 makes
     * the max size of items to track for an Email folder to be 3000. If not
     * specify, default value is Integer.MAX_VALUE
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1426)
    public Map<String,Object> unsetMobileItemsToTrackPerFolderMaxSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileItemsToTrackPerFolderMaxSize, "");
        return attrs;
    }

    /**
     * whether or not to enable truncating on client metadata size, if
     * enabled server will only track recent items on client device instead
     * of all
     *
     * @return zmailMobileMetadataMaxSizeEnabled, or false if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1425)
    public boolean isMobileMetadataMaxSizeEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMobileMetadataMaxSizeEnabled, false);
    }

    /**
     * whether or not to enable truncating on client metadata size, if
     * enabled server will only track recent items on client device instead
     * of all
     *
     * @param zmailMobileMetadataMaxSizeEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1425)
    public void setMobileMetadataMaxSizeEnabled(boolean zmailMobileMetadataMaxSizeEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileMetadataMaxSizeEnabled, zmailMobileMetadataMaxSizeEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to enable truncating on client metadata size, if
     * enabled server will only track recent items on client device instead
     * of all
     *
     * @param zmailMobileMetadataMaxSizeEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1425)
    public Map<String,Object> setMobileMetadataMaxSizeEnabled(boolean zmailMobileMetadataMaxSizeEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileMetadataMaxSizeEnabled, zmailMobileMetadataMaxSizeEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether or not to enable truncating on client metadata size, if
     * enabled server will only track recent items on client device instead
     * of all
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1425)
    public void unsetMobileMetadataMaxSizeEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileMetadataMaxSizeEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to enable truncating on client metadata size, if
     * enabled server will only track recent items on client device instead
     * of all
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1425)
    public Map<String,Object> unsetMobileMetadataMaxSizeEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMobileMetadataMaxSizeEnabled, "");
        return attrs;
    }

    /**
     * certificate to be used for validating the SAML assertions received
     * from myonelogin (tricipher)
     *
     * @return zmailMyoneloginSamlSigningCert, or null if unset
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1169)
    public String getMyoneloginSamlSigningCert() {
        return getAttr(Provisioning.A_zmailMyoneloginSamlSigningCert, null);
    }

    /**
     * certificate to be used for validating the SAML assertions received
     * from myonelogin (tricipher)
     *
     * @param zmailMyoneloginSamlSigningCert new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1169)
    public void setMyoneloginSamlSigningCert(String zmailMyoneloginSamlSigningCert) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMyoneloginSamlSigningCert, zmailMyoneloginSamlSigningCert);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * certificate to be used for validating the SAML assertions received
     * from myonelogin (tricipher)
     *
     * @param zmailMyoneloginSamlSigningCert new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1169)
    public Map<String,Object> setMyoneloginSamlSigningCert(String zmailMyoneloginSamlSigningCert, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMyoneloginSamlSigningCert, zmailMyoneloginSamlSigningCert);
        return attrs;
    }

    /**
     * certificate to be used for validating the SAML assertions received
     * from myonelogin (tricipher)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1169)
    public void unsetMyoneloginSamlSigningCert() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMyoneloginSamlSigningCert, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * certificate to be used for validating the SAML assertions received
     * from myonelogin (tricipher)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1169)
    public Map<String,Object> unsetMyoneloginSamlSigningCert(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMyoneloginSamlSigningCert, "");
        return attrs;
    }

    /**
     * Deprecated since: 7.0.0. See bug 39647. Orig desc: Account for storing
     * templates and providing space for public wiki
     *
     * @return zmailNotebookAccount, or null if unset
     */
    @ZAttr(id=363)
    public String getNotebookAccount() {
        return getAttr(Provisioning.A_zmailNotebookAccount, null);
    }

    /**
     * Deprecated since: 7.0.0. See bug 39647. Orig desc: Account for storing
     * templates and providing space for public wiki
     *
     * @param zmailNotebookAccount new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=363)
    public void setNotebookAccount(String zmailNotebookAccount) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookAccount, zmailNotebookAccount);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 7.0.0. See bug 39647. Orig desc: Account for storing
     * templates and providing space for public wiki
     *
     * @param zmailNotebookAccount new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=363)
    public Map<String,Object> setNotebookAccount(String zmailNotebookAccount, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookAccount, zmailNotebookAccount);
        return attrs;
    }

    /**
     * Deprecated since: 7.0.0. See bug 39647. Orig desc: Account for storing
     * templates and providing space for public wiki
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=363)
    public void unsetNotebookAccount() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookAccount, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 7.0.0. See bug 39647. Orig desc: Account for storing
     * templates and providing space for public wiki
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=363)
    public Map<String,Object> unsetNotebookAccount(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookAccount, "");
        return attrs;
    }

    /**
     * administrative notes
     *
     * @return zmailNotes, or null if unset
     */
    @ZAttr(id=9)
    public String getNotes() {
        return getAttr(Provisioning.A_zmailNotes, null);
    }

    /**
     * administrative notes
     *
     * @param zmailNotes new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=9)
    public void setNotes(String zmailNotes) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotes, zmailNotes);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * administrative notes
     *
     * @param zmailNotes new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=9)
    public Map<String,Object> setNotes(String zmailNotes, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotes, zmailNotes);
        return attrs;
    }

    /**
     * administrative notes
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=9)
    public void unsetNotes() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotes, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * administrative notes
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=9)
    public Map<String,Object> unsetNotes(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotes, "");
        return attrs;
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @return zmailOAuthConsumerCredentials, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public String[] getOAuthConsumerCredentials() {
        return getMultiAttr(Provisioning.A_zmailOAuthConsumerCredentials);
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param zmailOAuthConsumerCredentials new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public void setOAuthConsumerCredentials(String[] zmailOAuthConsumerCredentials) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOAuthConsumerCredentials, zmailOAuthConsumerCredentials);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param zmailOAuthConsumerCredentials new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public Map<String,Object> setOAuthConsumerCredentials(String[] zmailOAuthConsumerCredentials, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOAuthConsumerCredentials, zmailOAuthConsumerCredentials);
        return attrs;
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param zmailOAuthConsumerCredentials new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public void addOAuthConsumerCredentials(String zmailOAuthConsumerCredentials) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailOAuthConsumerCredentials, zmailOAuthConsumerCredentials);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param zmailOAuthConsumerCredentials new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public Map<String,Object> addOAuthConsumerCredentials(String zmailOAuthConsumerCredentials, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailOAuthConsumerCredentials, zmailOAuthConsumerCredentials);
        return attrs;
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param zmailOAuthConsumerCredentials existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public void removeOAuthConsumerCredentials(String zmailOAuthConsumerCredentials) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailOAuthConsumerCredentials, zmailOAuthConsumerCredentials);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param zmailOAuthConsumerCredentials existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public Map<String,Object> removeOAuthConsumerCredentials(String zmailOAuthConsumerCredentials, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailOAuthConsumerCredentials, zmailOAuthConsumerCredentials);
        return attrs;
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public void unsetOAuthConsumerCredentials() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOAuthConsumerCredentials, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * OAuth consumer ids and secrets. It is in the format of
     * {consumer-id]:{secrets}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1131)
    public Map<String,Object> unsetOAuthConsumerCredentials(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOAuthConsumerCredentials, "");
        return attrs;
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @return zmailOpenidConsumerAllowedOPEndpointURL, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public String[] getOpenidConsumerAllowedOPEndpointURL() {
        return getMultiAttr(Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL);
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param zmailOpenidConsumerAllowedOPEndpointURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public void setOpenidConsumerAllowedOPEndpointURL(String[] zmailOpenidConsumerAllowedOPEndpointURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, zmailOpenidConsumerAllowedOPEndpointURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param zmailOpenidConsumerAllowedOPEndpointURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public Map<String,Object> setOpenidConsumerAllowedOPEndpointURL(String[] zmailOpenidConsumerAllowedOPEndpointURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, zmailOpenidConsumerAllowedOPEndpointURL);
        return attrs;
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param zmailOpenidConsumerAllowedOPEndpointURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public void addOpenidConsumerAllowedOPEndpointURL(String zmailOpenidConsumerAllowedOPEndpointURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, zmailOpenidConsumerAllowedOPEndpointURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param zmailOpenidConsumerAllowedOPEndpointURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public Map<String,Object> addOpenidConsumerAllowedOPEndpointURL(String zmailOpenidConsumerAllowedOPEndpointURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, zmailOpenidConsumerAllowedOPEndpointURL);
        return attrs;
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param zmailOpenidConsumerAllowedOPEndpointURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public void removeOpenidConsumerAllowedOPEndpointURL(String zmailOpenidConsumerAllowedOPEndpointURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, zmailOpenidConsumerAllowedOPEndpointURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param zmailOpenidConsumerAllowedOPEndpointURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public Map<String,Object> removeOpenidConsumerAllowedOPEndpointURL(String zmailOpenidConsumerAllowedOPEndpointURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, zmailOpenidConsumerAllowedOPEndpointURL);
        return attrs;
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public void unsetOpenidConsumerAllowedOPEndpointURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * allowed OpenID Provider Endpoint URLs for authentication
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1191)
    public Map<String,Object> unsetOpenidConsumerAllowedOPEndpointURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerAllowedOPEndpointURL, "");
        return attrs;
    }

    /**
     * registered change password listener name
     *
     * @return zmailPasswordChangeListener, or null if unset
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=586)
    public String getPasswordChangeListener() {
        return getAttr(Provisioning.A_zmailPasswordChangeListener, null);
    }

    /**
     * registered change password listener name
     *
     * @param zmailPasswordChangeListener new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=586)
    public void setPasswordChangeListener(String zmailPasswordChangeListener) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPasswordChangeListener, zmailPasswordChangeListener);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * registered change password listener name
     *
     * @param zmailPasswordChangeListener new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=586)
    public Map<String,Object> setPasswordChangeListener(String zmailPasswordChangeListener, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPasswordChangeListener, zmailPasswordChangeListener);
        return attrs;
    }

    /**
     * registered change password listener name
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=586)
    public void unsetPasswordChangeListener() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPasswordChangeListener, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * registered change password listener name
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.1
     */
    @ZAttr(id=586)
    public Map<String,Object> unsetPasswordChangeListener(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPasswordChangeListener, "");
        return attrs;
    }

    /**
     * preauth secret key
     *
     * @return zmailPreAuthKey, or null if unset
     */
    @ZAttr(id=307)
    public String getPreAuthKey() {
        return getAttr(Provisioning.A_zmailPreAuthKey, null);
    }

    /**
     * preauth secret key
     *
     * @param zmailPreAuthKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=307)
    public void setPreAuthKey(String zmailPreAuthKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPreAuthKey, zmailPreAuthKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * preauth secret key
     *
     * @param zmailPreAuthKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=307)
    public Map<String,Object> setPreAuthKey(String zmailPreAuthKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPreAuthKey, zmailPreAuthKey);
        return attrs;
    }

    /**
     * preauth secret key
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=307)
    public void unsetPreAuthKey() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPreAuthKey, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * preauth secret key
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=307)
    public Map<String,Object> unsetPreAuthKey(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPreAuthKey, "");
        return attrs;
    }

    /**
     * whether or not to use tag color as the color for message items
     *
     * @return zmailPrefColorMessagesEnabled, or false if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1424)
    public boolean isPrefColorMessagesEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPrefColorMessagesEnabled, false);
    }

    /**
     * whether or not to use tag color as the color for message items
     *
     * @param zmailPrefColorMessagesEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1424)
    public void setPrefColorMessagesEnabled(boolean zmailPrefColorMessagesEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefColorMessagesEnabled, zmailPrefColorMessagesEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to use tag color as the color for message items
     *
     * @param zmailPrefColorMessagesEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1424)
    public Map<String,Object> setPrefColorMessagesEnabled(boolean zmailPrefColorMessagesEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefColorMessagesEnabled, zmailPrefColorMessagesEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether or not to use tag color as the color for message items
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1424)
    public void unsetPrefColorMessagesEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefColorMessagesEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to use tag color as the color for message items
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1424)
    public Map<String,Object> unsetPrefColorMessagesEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefColorMessagesEnabled, "");
        return attrs;
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @return zmailPrefMailTrustedSenderList, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public String[] getPrefMailTrustedSenderList() {
        return getMultiAttr(Provisioning.A_zmailPrefMailTrustedSenderList);
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param zmailPrefMailTrustedSenderList new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public void setPrefMailTrustedSenderList(String[] zmailPrefMailTrustedSenderList) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefMailTrustedSenderList, zmailPrefMailTrustedSenderList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param zmailPrefMailTrustedSenderList new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public Map<String,Object> setPrefMailTrustedSenderList(String[] zmailPrefMailTrustedSenderList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefMailTrustedSenderList, zmailPrefMailTrustedSenderList);
        return attrs;
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param zmailPrefMailTrustedSenderList new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public void addPrefMailTrustedSenderList(String zmailPrefMailTrustedSenderList) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefMailTrustedSenderList, zmailPrefMailTrustedSenderList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param zmailPrefMailTrustedSenderList new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public Map<String,Object> addPrefMailTrustedSenderList(String zmailPrefMailTrustedSenderList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefMailTrustedSenderList, zmailPrefMailTrustedSenderList);
        return attrs;
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param zmailPrefMailTrustedSenderList existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public void removePrefMailTrustedSenderList(String zmailPrefMailTrustedSenderList) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefMailTrustedSenderList, zmailPrefMailTrustedSenderList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param zmailPrefMailTrustedSenderList existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public Map<String,Object> removePrefMailTrustedSenderList(String zmailPrefMailTrustedSenderList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefMailTrustedSenderList, zmailPrefMailTrustedSenderList);
        return attrs;
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public void unsetPrefMailTrustedSenderList() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefMailTrustedSenderList, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Trusted sender email addresses or domains. External images in emails
     * sent by trusted senders are automatically loaded in the message view.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1138)
    public Map<String,Object> unsetPrefMailTrustedSenderList(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefMailTrustedSenderList, "");
        return attrs;
    }

    /**
     * Skin to use for this account
     *
     * @return zmailPrefSkin, or null if unset
     */
    @ZAttr(id=355)
    public String getPrefSkin() {
        return getAttr(Provisioning.A_zmailPrefSkin, null);
    }

    /**
     * Skin to use for this account
     *
     * @param zmailPrefSkin new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=355)
    public void setPrefSkin(String zmailPrefSkin) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSkin, zmailPrefSkin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Skin to use for this account
     *
     * @param zmailPrefSkin new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=355)
    public Map<String,Object> setPrefSkin(String zmailPrefSkin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSkin, zmailPrefSkin);
        return attrs;
    }

    /**
     * Skin to use for this account
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=355)
    public void unsetPrefSkin() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSkin, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Skin to use for this account
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=355)
    public Map<String,Object> unsetPrefSkin(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSkin, "");
        return attrs;
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @return zmailPrefSpellIgnoreWord, or empty array if unset
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public String[] getPrefSpellIgnoreWord() {
        return getMultiAttr(Provisioning.A_zmailPrefSpellIgnoreWord);
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param zmailPrefSpellIgnoreWord new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public void setPrefSpellIgnoreWord(String[] zmailPrefSpellIgnoreWord) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSpellIgnoreWord, zmailPrefSpellIgnoreWord);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param zmailPrefSpellIgnoreWord new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public Map<String,Object> setPrefSpellIgnoreWord(String[] zmailPrefSpellIgnoreWord, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSpellIgnoreWord, zmailPrefSpellIgnoreWord);
        return attrs;
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param zmailPrefSpellIgnoreWord new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public void addPrefSpellIgnoreWord(String zmailPrefSpellIgnoreWord) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefSpellIgnoreWord, zmailPrefSpellIgnoreWord);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param zmailPrefSpellIgnoreWord new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public Map<String,Object> addPrefSpellIgnoreWord(String zmailPrefSpellIgnoreWord, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefSpellIgnoreWord, zmailPrefSpellIgnoreWord);
        return attrs;
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param zmailPrefSpellIgnoreWord existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public void removePrefSpellIgnoreWord(String zmailPrefSpellIgnoreWord) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefSpellIgnoreWord, zmailPrefSpellIgnoreWord);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param zmailPrefSpellIgnoreWord existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public Map<String,Object> removePrefSpellIgnoreWord(String zmailPrefSpellIgnoreWord, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefSpellIgnoreWord, zmailPrefSpellIgnoreWord);
        return attrs;
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public void unsetPrefSpellIgnoreWord() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSpellIgnoreWord, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of words to ignore when checking spelling. The word list of an
     * account includes the words specified for its cos and domain.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1073)
    public Map<String,Object> unsetPrefSpellIgnoreWord(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefSpellIgnoreWord, "");
        return attrs;
    }

    /**
     * time zone of user or COS
     *
     * @return zmailPrefTimeZoneId, or empty array if unset
     */
    @ZAttr(id=235)
    public String[] getPrefTimeZoneId() {
        return getMultiAttr(Provisioning.A_zmailPrefTimeZoneId);
    }

    /**
     * time zone of user or COS
     *
     * @param zmailPrefTimeZoneId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=235)
    public void setPrefTimeZoneId(String[] zmailPrefTimeZoneId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefTimeZoneId, zmailPrefTimeZoneId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time zone of user or COS
     *
     * @param zmailPrefTimeZoneId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=235)
    public Map<String,Object> setPrefTimeZoneId(String[] zmailPrefTimeZoneId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefTimeZoneId, zmailPrefTimeZoneId);
        return attrs;
    }

    /**
     * time zone of user or COS
     *
     * @param zmailPrefTimeZoneId new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=235)
    public void addPrefTimeZoneId(String zmailPrefTimeZoneId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefTimeZoneId, zmailPrefTimeZoneId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time zone of user or COS
     *
     * @param zmailPrefTimeZoneId new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=235)
    public Map<String,Object> addPrefTimeZoneId(String zmailPrefTimeZoneId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefTimeZoneId, zmailPrefTimeZoneId);
        return attrs;
    }

    /**
     * time zone of user or COS
     *
     * @param zmailPrefTimeZoneId existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=235)
    public void removePrefTimeZoneId(String zmailPrefTimeZoneId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefTimeZoneId, zmailPrefTimeZoneId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time zone of user or COS
     *
     * @param zmailPrefTimeZoneId existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=235)
    public Map<String,Object> removePrefTimeZoneId(String zmailPrefTimeZoneId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefTimeZoneId, zmailPrefTimeZoneId);
        return attrs;
    }

    /**
     * time zone of user or COS
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=235)
    public void unsetPrefTimeZoneId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefTimeZoneId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time zone of user or COS
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=235)
    public Map<String,Object> unsetPrefTimeZoneId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefTimeZoneId, "");
        return attrs;
    }

    /**
     * Name to be used in public API such as REST or SOAP proxy.
     *
     * @return zmailPublicServiceHostname, or null if unset
     */
    @ZAttr(id=377)
    public String getPublicServiceHostname() {
        return getAttr(Provisioning.A_zmailPublicServiceHostname, null);
    }

    /**
     * Name to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServiceHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=377)
    public void setPublicServiceHostname(String zmailPublicServiceHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceHostname, zmailPublicServiceHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Name to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServiceHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=377)
    public Map<String,Object> setPublicServiceHostname(String zmailPublicServiceHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceHostname, zmailPublicServiceHostname);
        return attrs;
    }

    /**
     * Name to be used in public API such as REST or SOAP proxy.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=377)
    public void unsetPublicServiceHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Name to be used in public API such as REST or SOAP proxy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=377)
    public Map<String,Object> unsetPublicServiceHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceHostname, "");
        return attrs;
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * <p>Use getPublicServicePortAsString to access value as a string.
     *
     * @see #getPublicServicePortAsString()
     *
     * @return zmailPublicServicePort, or -1 if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public int getPublicServicePort() {
        return getIntAttr(Provisioning.A_zmailPublicServicePort, -1);
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @return zmailPublicServicePort, or null if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public String getPublicServicePortAsString() {
        return getAttr(Provisioning.A_zmailPublicServicePort, null);
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServicePort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public void setPublicServicePort(int zmailPublicServicePort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServicePort, Integer.toString(zmailPublicServicePort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServicePort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public Map<String,Object> setPublicServicePort(int zmailPublicServicePort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServicePort, Integer.toString(zmailPublicServicePort));
        return attrs;
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServicePort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public void setPublicServicePortAsString(String zmailPublicServicePort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServicePort, zmailPublicServicePort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServicePort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public Map<String,Object> setPublicServicePortAsString(String zmailPublicServicePort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServicePort, zmailPublicServicePort);
        return attrs;
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public void unsetPublicServicePort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServicePort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Port to be used in public API such as REST or SOAP proxy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=699)
    public Map<String,Object> unsetPublicServicePort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServicePort, "");
        return attrs;
    }

    /**
     * Protocol to be used in public API such as REST or SOAP proxy.
     *
     * @return zmailPublicServiceProtocol, or null if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=698)
    public String getPublicServiceProtocol() {
        return getAttr(Provisioning.A_zmailPublicServiceProtocol, null);
    }

    /**
     * Protocol to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServiceProtocol new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=698)
    public void setPublicServiceProtocol(String zmailPublicServiceProtocol) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceProtocol, zmailPublicServiceProtocol);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Protocol to be used in public API such as REST or SOAP proxy.
     *
     * @param zmailPublicServiceProtocol new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=698)
    public Map<String,Object> setPublicServiceProtocol(String zmailPublicServiceProtocol, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceProtocol, zmailPublicServiceProtocol);
        return attrs;
    }

    /**
     * Protocol to be used in public API such as REST or SOAP proxy.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=698)
    public void unsetPublicServiceProtocol() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceProtocol, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Protocol to be used in public API such as REST or SOAP proxy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=698)
    public Map<String,Object> unsetPublicServiceProtocol(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicServiceProtocol, "");
        return attrs;
    }

    /**
     * switch for turning public sharing on/off
     *
     * @return zmailPublicSharingEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1351)
    public boolean isPublicSharingEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPublicSharingEnabled, false);
    }

    /**
     * switch for turning public sharing on/off
     *
     * @param zmailPublicSharingEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1351)
    public void setPublicSharingEnabled(boolean zmailPublicSharingEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicSharingEnabled, zmailPublicSharingEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * switch for turning public sharing on/off
     *
     * @param zmailPublicSharingEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1351)
    public Map<String,Object> setPublicSharingEnabled(boolean zmailPublicSharingEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicSharingEnabled, zmailPublicSharingEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * switch for turning public sharing on/off
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1351)
    public void unsetPublicSharingEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicSharingEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * switch for turning public sharing on/off
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1351)
    public Map<String,Object> unsetPublicSharingEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPublicSharingEnabled, "");
        return attrs;
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @return zmailResponseHeader, or empty array if unset
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public String[] getResponseHeader() {
        return getMultiAttr(Provisioning.A_zmailResponseHeader);
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param zmailResponseHeader new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public void setResponseHeader(String[] zmailResponseHeader) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailResponseHeader, zmailResponseHeader);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param zmailResponseHeader new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public Map<String,Object> setResponseHeader(String[] zmailResponseHeader, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailResponseHeader, zmailResponseHeader);
        return attrs;
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param zmailResponseHeader new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public void addResponseHeader(String zmailResponseHeader) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailResponseHeader, zmailResponseHeader);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param zmailResponseHeader new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public Map<String,Object> addResponseHeader(String zmailResponseHeader, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailResponseHeader, zmailResponseHeader);
        return attrs;
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param zmailResponseHeader existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public void removeResponseHeader(String zmailResponseHeader) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailResponseHeader, zmailResponseHeader);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param zmailResponseHeader existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public Map<String,Object> removeResponseHeader(String zmailResponseHeader, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailResponseHeader, zmailResponseHeader);
        return attrs;
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public void unsetResponseHeader() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailResponseHeader, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Custom response headers. For example, can be used to add a P3P header
     * for user agents to understand the sites privacy policy. Note: the
     * value MUST be the entire header line (e.g. X-Foo: Bar).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.5
     */
    @ZAttr(id=1074)
    public Map<String,Object> unsetResponseHeader(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailResponseHeader, "");
        return attrs;
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @return zmailReverseProxyClientCertCA, or null if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public String getReverseProxyClientCertCA() {
        return getAttr(Provisioning.A_zmailReverseProxyClientCertCA, null);
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @param zmailReverseProxyClientCertCA new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public void setReverseProxyClientCertCA(String zmailReverseProxyClientCertCA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertCA, zmailReverseProxyClientCertCA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @param zmailReverseProxyClientCertCA new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public Map<String,Object> setReverseProxyClientCertCA(String zmailReverseProxyClientCertCA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertCA, zmailReverseProxyClientCertCA);
        return attrs;
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public void unsetReverseProxyClientCertCA() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertCA, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public Map<String,Object> unsetReverseProxyClientCertCA(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertCA, "");
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @return zmailReverseProxyClientCertMode, or ZAttrProvisioning.ReverseProxyClientCertMode.off if unset and/or has invalid value
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public ZAttrProvisioning.ReverseProxyClientCertMode getReverseProxyClientCertMode() {
        try { String v = getAttr(Provisioning.A_zmailReverseProxyClientCertMode); return v == null ? ZAttrProvisioning.ReverseProxyClientCertMode.off : ZAttrProvisioning.ReverseProxyClientCertMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyClientCertMode.off; }
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @return zmailReverseProxyClientCertMode, or "off" if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public String getReverseProxyClientCertModeAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyClientCertMode, "off");
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zmailReverseProxyClientCertMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public void setReverseProxyClientCertMode(ZAttrProvisioning.ReverseProxyClientCertMode zmailReverseProxyClientCertMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertMode, zmailReverseProxyClientCertMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zmailReverseProxyClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public Map<String,Object> setReverseProxyClientCertMode(ZAttrProvisioning.ReverseProxyClientCertMode zmailReverseProxyClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertMode, zmailReverseProxyClientCertMode.toString());
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zmailReverseProxyClientCertMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public void setReverseProxyClientCertModeAsString(String zmailReverseProxyClientCertMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertMode, zmailReverseProxyClientCertMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zmailReverseProxyClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public Map<String,Object> setReverseProxyClientCertModeAsString(String zmailReverseProxyClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertMode, zmailReverseProxyClientCertMode);
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public void unsetReverseProxyClientCertMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public Map<String,Object> unsetReverseProxyClientCertMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyClientCertMode, "");
        return attrs;
    }

    /**
     * There is a deployment scenario for migrations where all of the
     * customers users are pointed at the zmail POP IMAP reverse proxy. We
     * then want their connections proxied back to the legacy system for
     * not-yet-non-migrated users. If this attribute is TRUE, reverse proxy
     * lookup servlet should check to see if zmailExternal* is set on the
     * domain. If so it is used. If not, lookup proceeds as usual.
     *
     * @return zmailReverseProxyUseExternalRoute, or false if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=779)
    public boolean isReverseProxyUseExternalRoute() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyUseExternalRoute, false);
    }

    /**
     * There is a deployment scenario for migrations where all of the
     * customers users are pointed at the zmail POP IMAP reverse proxy. We
     * then want their connections proxied back to the legacy system for
     * not-yet-non-migrated users. If this attribute is TRUE, reverse proxy
     * lookup servlet should check to see if zmailExternal* is set on the
     * domain. If so it is used. If not, lookup proceeds as usual.
     *
     * @param zmailReverseProxyUseExternalRoute new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=779)
    public void setReverseProxyUseExternalRoute(boolean zmailReverseProxyUseExternalRoute) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRoute, zmailReverseProxyUseExternalRoute ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * There is a deployment scenario for migrations where all of the
     * customers users are pointed at the zmail POP IMAP reverse proxy. We
     * then want their connections proxied back to the legacy system for
     * not-yet-non-migrated users. If this attribute is TRUE, reverse proxy
     * lookup servlet should check to see if zmailExternal* is set on the
     * domain. If so it is used. If not, lookup proceeds as usual.
     *
     * @param zmailReverseProxyUseExternalRoute new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=779)
    public Map<String,Object> setReverseProxyUseExternalRoute(boolean zmailReverseProxyUseExternalRoute, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRoute, zmailReverseProxyUseExternalRoute ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * There is a deployment scenario for migrations where all of the
     * customers users are pointed at the zmail POP IMAP reverse proxy. We
     * then want their connections proxied back to the legacy system for
     * not-yet-non-migrated users. If this attribute is TRUE, reverse proxy
     * lookup servlet should check to see if zmailExternal* is set on the
     * domain. If so it is used. If not, lookup proceeds as usual.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=779)
    public void unsetReverseProxyUseExternalRoute() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRoute, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * There is a deployment scenario for migrations where all of the
     * customers users are pointed at the zmail POP IMAP reverse proxy. We
     * then want their connections proxied back to the legacy system for
     * not-yet-non-migrated users. If this attribute is TRUE, reverse proxy
     * lookup servlet should check to see if zmailExternal* is set on the
     * domain. If so it is used. If not, lookup proceeds as usual.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=779)
    public Map<String,Object> unsetReverseProxyUseExternalRoute(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRoute, "");
        return attrs;
    }

    /**
     * Use external route configured on domain if account cannot be found.
     * Also see zmailReverseProxyUseExternalRoute.
     *
     * @return zmailReverseProxyUseExternalRouteIfAccountNotExist, or false if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1132)
    public boolean isReverseProxyUseExternalRouteIfAccountNotExist() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyUseExternalRouteIfAccountNotExist, false);
    }

    /**
     * Use external route configured on domain if account cannot be found.
     * Also see zmailReverseProxyUseExternalRoute.
     *
     * @param zmailReverseProxyUseExternalRouteIfAccountNotExist new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1132)
    public void setReverseProxyUseExternalRouteIfAccountNotExist(boolean zmailReverseProxyUseExternalRouteIfAccountNotExist) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRouteIfAccountNotExist, zmailReverseProxyUseExternalRouteIfAccountNotExist ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Use external route configured on domain if account cannot be found.
     * Also see zmailReverseProxyUseExternalRoute.
     *
     * @param zmailReverseProxyUseExternalRouteIfAccountNotExist new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1132)
    public Map<String,Object> setReverseProxyUseExternalRouteIfAccountNotExist(boolean zmailReverseProxyUseExternalRouteIfAccountNotExist, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRouteIfAccountNotExist, zmailReverseProxyUseExternalRouteIfAccountNotExist ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Use external route configured on domain if account cannot be found.
     * Also see zmailReverseProxyUseExternalRoute.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1132)
    public void unsetReverseProxyUseExternalRouteIfAccountNotExist() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRouteIfAccountNotExist, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Use external route configured on domain if account cannot be found.
     * Also see zmailReverseProxyUseExternalRoute.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1132)
    public Map<String,Object> unsetReverseProxyUseExternalRouteIfAccountNotExist(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUseExternalRouteIfAccountNotExist, "");
        return attrs;
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @return zmailSMIMELdapAttribute, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public String[] getSMIMELdapAttribute() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapAttribute);
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapAttribute new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public void setSMIMELdapAttribute(String[] zmailSMIMELdapAttribute) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapAttribute, zmailSMIMELdapAttribute);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapAttribute new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public Map<String,Object> setSMIMELdapAttribute(String[] zmailSMIMELdapAttribute, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapAttribute, zmailSMIMELdapAttribute);
        return attrs;
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapAttribute new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public void addSMIMELdapAttribute(String zmailSMIMELdapAttribute) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapAttribute, zmailSMIMELdapAttribute);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapAttribute new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public Map<String,Object> addSMIMELdapAttribute(String zmailSMIMELdapAttribute, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapAttribute, zmailSMIMELdapAttribute);
        return attrs;
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapAttribute existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public void removeSMIMELdapAttribute(String zmailSMIMELdapAttribute) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapAttribute, zmailSMIMELdapAttribute);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapAttribute existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public Map<String,Object> removeSMIMELdapAttribute(String zmailSMIMELdapAttribute, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapAttribute, zmailSMIMELdapAttribute);
        return attrs;
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public void unsetSMIMELdapAttribute() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapAttribute, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP attribute(s) for public key lookup for S/MIME via external LDAP.
     * Multiple attributes can be separated by comma. All SMIME attributes
     * are in the format of {config-name}:{value}. A &#039;SMIME config&#039;
     * is a set of SMIME attribute values with the same {config-name}.
     * Multiple SMIME configs can be configured on a domain or on
     * globalconfig. Note: SMIME attributes on domains do not inherited
     * values from globalconfig, they are not domain-inherited attributes.
     * During SMIME public key lookup, if there are any SMIME config on the
     * domain of the account, they are used. SMIME configs on globalconfig
     * will be used only when there is no SMIME config on the domain. SMIME
     * attributes cannot be modified directly with zmprov md/mcf commands.
     * Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1182)
    public Map<String,Object> unsetSMIMELdapAttribute(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapAttribute, "");
        return attrs;
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @return zmailSMIMELdapBindDn, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public String[] getSMIMELdapBindDn() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapBindDn);
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindDn new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public void setSMIMELdapBindDn(String[] zmailSMIMELdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindDn, zmailSMIMELdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindDn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public Map<String,Object> setSMIMELdapBindDn(String[] zmailSMIMELdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindDn, zmailSMIMELdapBindDn);
        return attrs;
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindDn new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public void addSMIMELdapBindDn(String zmailSMIMELdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapBindDn, zmailSMIMELdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindDn new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public Map<String,Object> addSMIMELdapBindDn(String zmailSMIMELdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapBindDn, zmailSMIMELdapBindDn);
        return attrs;
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindDn existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public void removeSMIMELdapBindDn(String zmailSMIMELdapBindDn) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapBindDn, zmailSMIMELdapBindDn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindDn existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public Map<String,Object> removeSMIMELdapBindDn(String zmailSMIMELdapBindDn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapBindDn, zmailSMIMELdapBindDn);
        return attrs;
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public void unsetSMIMELdapBindDn() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindDn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind DN for public key lookup for S/MIME via external LDAP. Can
     * be empty for anonymous bind. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1178)
    public Map<String,Object> unsetSMIMELdapBindDn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindDn, "");
        return attrs;
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @return zmailSMIMELdapBindPassword, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public String[] getSMIMELdapBindPassword() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapBindPassword);
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public void setSMIMELdapBindPassword(String[] zmailSMIMELdapBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindPassword, zmailSMIMELdapBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public Map<String,Object> setSMIMELdapBindPassword(String[] zmailSMIMELdapBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindPassword, zmailSMIMELdapBindPassword);
        return attrs;
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindPassword new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public void addSMIMELdapBindPassword(String zmailSMIMELdapBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapBindPassword, zmailSMIMELdapBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindPassword new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public Map<String,Object> addSMIMELdapBindPassword(String zmailSMIMELdapBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapBindPassword, zmailSMIMELdapBindPassword);
        return attrs;
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindPassword existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public void removeSMIMELdapBindPassword(String zmailSMIMELdapBindPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapBindPassword, zmailSMIMELdapBindPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapBindPassword existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public Map<String,Object> removeSMIMELdapBindPassword(String zmailSMIMELdapBindPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapBindPassword, zmailSMIMELdapBindPassword);
        return attrs;
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public void unsetSMIMELdapBindPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP bind password for public key lookup for S/MIME via external LDAP.
     * Can be empty for anonymous bind. All SMIME attributes are in the
     * format of {config-name}:{value}. A &#039;SMIME config&#039; is a set
     * of SMIME attribute values with the same {config-name}. Multiple SMIME
     * configs can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1179)
    public Map<String,Object> unsetSMIMELdapBindPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapBindPassword, "");
        return attrs;
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @return zmailSMIMELdapDiscoverSearchBaseEnabled, or empty array if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public String[] getSMIMELdapDiscoverSearchBaseEnabled() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled);
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapDiscoverSearchBaseEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public void setSMIMELdapDiscoverSearchBaseEnabled(String[] zmailSMIMELdapDiscoverSearchBaseEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, zmailSMIMELdapDiscoverSearchBaseEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapDiscoverSearchBaseEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public Map<String,Object> setSMIMELdapDiscoverSearchBaseEnabled(String[] zmailSMIMELdapDiscoverSearchBaseEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, zmailSMIMELdapDiscoverSearchBaseEnabled);
        return attrs;
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapDiscoverSearchBaseEnabled new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public void addSMIMELdapDiscoverSearchBaseEnabled(String zmailSMIMELdapDiscoverSearchBaseEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, zmailSMIMELdapDiscoverSearchBaseEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapDiscoverSearchBaseEnabled new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public Map<String,Object> addSMIMELdapDiscoverSearchBaseEnabled(String zmailSMIMELdapDiscoverSearchBaseEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, zmailSMIMELdapDiscoverSearchBaseEnabled);
        return attrs;
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapDiscoverSearchBaseEnabled existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public void removeSMIMELdapDiscoverSearchBaseEnabled(String zmailSMIMELdapDiscoverSearchBaseEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, zmailSMIMELdapDiscoverSearchBaseEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapDiscoverSearchBaseEnabled existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public Map<String,Object> removeSMIMELdapDiscoverSearchBaseEnabled(String zmailSMIMELdapDiscoverSearchBaseEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, zmailSMIMELdapDiscoverSearchBaseEnabled);
        return attrs;
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public void unsetSMIMELdapDiscoverSearchBaseEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether or not to discover search base DNs if
     * zmailSMIMELdapSearchBase is not set. Allowed values are TRUE or
     * FALSE. If zmailSMIMELdapSearchBase is set for a config, this
     * attribute is ignored for the config. If not set, default for the
     * config is FALSE. In that case, if zmailSMIMELdapSearchBase is not
     * set, the search will default to the rootDSE. If multiple DNs are
     * discovered, the ldap search will use them one by one until a hit is
     * returned. All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1209)
    public Map<String,Object> unsetSMIMELdapDiscoverSearchBaseEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapDiscoverSearchBaseEnabled, "");
        return attrs;
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @return zmailSMIMELdapFilter, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public String[] getSMIMELdapFilter() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapFilter);
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapFilter new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public void setSMIMELdapFilter(String[] zmailSMIMELdapFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapFilter, zmailSMIMELdapFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapFilter new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public Map<String,Object> setSMIMELdapFilter(String[] zmailSMIMELdapFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapFilter, zmailSMIMELdapFilter);
        return attrs;
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapFilter new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public void addSMIMELdapFilter(String zmailSMIMELdapFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapFilter, zmailSMIMELdapFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapFilter new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public Map<String,Object> addSMIMELdapFilter(String zmailSMIMELdapFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapFilter, zmailSMIMELdapFilter);
        return attrs;
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapFilter existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public void removeSMIMELdapFilter(String zmailSMIMELdapFilter) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapFilter, zmailSMIMELdapFilter);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param zmailSMIMELdapFilter existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public Map<String,Object> removeSMIMELdapFilter(String zmailSMIMELdapFilter, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapFilter, zmailSMIMELdapFilter);
        return attrs;
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public void unsetSMIMELdapFilter() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapFilter, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search filter for public key lookup for S/MIME via external LDAP.
     * Can contain the following conversion variables for expansion: %n -
     * search key with @ (or without, if no @ was specified) %u - with @
     * removed e.g. (mail=%n) All SMIME attributes are in the format of
     * {config-name}:{value}. A &#039;SMIME config&#039; is a set of SMIME
     * attribute values with the same {config-name}. Multiple SMIME configs
     * can be configured on a domain or on globalconfig. Note: SMIME
     * attributes on domains do not inherited values from globalconfig, they
     * are not domain-inherited attributes. During SMIME public key lookup,
     * if there are any SMIME config on the domain of the account, they are
     * used. SMIME configs on globalconfig will be used only when there is no
     * SMIME config on the domain. SMIME attributes cannot be modified
     * directly with zmprov md/mcf commands. Use zmprov
     * gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1181)
    public Map<String,Object> unsetSMIMELdapFilter(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapFilter, "");
        return attrs;
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @return zmailSMIMELdapSearchBase, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public String[] getSMIMELdapSearchBase() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapSearchBase);
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapSearchBase new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public void setSMIMELdapSearchBase(String[] zmailSMIMELdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapSearchBase, zmailSMIMELdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapSearchBase new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public Map<String,Object> setSMIMELdapSearchBase(String[] zmailSMIMELdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapSearchBase, zmailSMIMELdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapSearchBase new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public void addSMIMELdapSearchBase(String zmailSMIMELdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapSearchBase, zmailSMIMELdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapSearchBase new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public Map<String,Object> addSMIMELdapSearchBase(String zmailSMIMELdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapSearchBase, zmailSMIMELdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapSearchBase existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public void removeSMIMELdapSearchBase(String zmailSMIMELdapSearchBase) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapSearchBase, zmailSMIMELdapSearchBase);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapSearchBase existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public Map<String,Object> removeSMIMELdapSearchBase(String zmailSMIMELdapSearchBase, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapSearchBase, zmailSMIMELdapSearchBase);
        return attrs;
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public void unsetSMIMELdapSearchBase() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapSearchBase, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP search base for public key lookup for S/MIME via external LDAP.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1180)
    public Map<String,Object> unsetSMIMELdapSearchBase(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapSearchBase, "");
        return attrs;
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @return zmailSMIMELdapStartTlsEnabled, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public String[] getSMIMELdapStartTlsEnabled() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapStartTlsEnabled);
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapStartTlsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public void setSMIMELdapStartTlsEnabled(String[] zmailSMIMELdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapStartTlsEnabled, zmailSMIMELdapStartTlsEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapStartTlsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public Map<String,Object> setSMIMELdapStartTlsEnabled(String[] zmailSMIMELdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapStartTlsEnabled, zmailSMIMELdapStartTlsEnabled);
        return attrs;
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapStartTlsEnabled new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public void addSMIMELdapStartTlsEnabled(String zmailSMIMELdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapStartTlsEnabled, zmailSMIMELdapStartTlsEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapStartTlsEnabled new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public Map<String,Object> addSMIMELdapStartTlsEnabled(String zmailSMIMELdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapStartTlsEnabled, zmailSMIMELdapStartTlsEnabled);
        return attrs;
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapStartTlsEnabled existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public void removeSMIMELdapStartTlsEnabled(String zmailSMIMELdapStartTlsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapStartTlsEnabled, zmailSMIMELdapStartTlsEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapStartTlsEnabled existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public Map<String,Object> removeSMIMELdapStartTlsEnabled(String zmailSMIMELdapStartTlsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapStartTlsEnabled, zmailSMIMELdapStartTlsEnabled);
        return attrs;
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public void unsetSMIMELdapStartTlsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapStartTlsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use startTLS for public key lookup for S/MIME via external
     * LDAP. All SMIME attributes are in the format of {config-name}:{value}.
     * A &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1177)
    public Map<String,Object> unsetSMIMELdapStartTlsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapStartTlsEnabled, "");
        return attrs;
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @return zmailSMIMELdapURL, or empty array if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public String[] getSMIMELdapURL() {
        return getMultiAttr(Provisioning.A_zmailSMIMELdapURL);
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public void setSMIMELdapURL(String[] zmailSMIMELdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapURL, zmailSMIMELdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public Map<String,Object> setSMIMELdapURL(String[] zmailSMIMELdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapURL, zmailSMIMELdapURL);
        return attrs;
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public void addSMIMELdapURL(String zmailSMIMELdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapURL, zmailSMIMELdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public Map<String,Object> addSMIMELdapURL(String zmailSMIMELdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSMIMELdapURL, zmailSMIMELdapURL);
        return attrs;
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public void removeSMIMELdapURL(String zmailSMIMELdapURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapURL, zmailSMIMELdapURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param zmailSMIMELdapURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public Map<String,Object> removeSMIMELdapURL(String zmailSMIMELdapURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSMIMELdapURL, zmailSMIMELdapURL);
        return attrs;
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public void unsetSMIMELdapURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * LDAP URL(s) for public key lookup for S/MIME via external LDAP.
     * Multiple URLs for error fallback purpose can be separated by space.
     * All SMIME attributes are in the format of {config-name}:{value}. A
     * &#039;SMIME config&#039; is a set of SMIME attribute values with the
     * same {config-name}. Multiple SMIME configs can be configured on a
     * domain or on globalconfig. Note: SMIME attributes on domains do not
     * inherited values from globalconfig, they are not domain-inherited
     * attributes. During SMIME public key lookup, if there are any SMIME
     * config on the domain of the account, they are used. SMIME configs on
     * globalconfig will be used only when there is no SMIME config on the
     * domain. SMIME attributes cannot be modified directly with zmprov
     * md/mcf commands. Use zmprov gcsc/gdsc/mcsc/mdsc/rcsc/rdsc command
     * instead.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1176)
    public Map<String,Object> unsetSMIMELdapURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSMIMELdapURL, "");
        return attrs;
    }

    /**
     * SSL certificate
     *
     * @return zmailSSLCertificate, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public String getSSLCertificate() {
        return getAttr(Provisioning.A_zmailSSLCertificate, null);
    }

    /**
     * SSL certificate
     *
     * @param zmailSSLCertificate new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public void setSSLCertificate(String zmailSSLCertificate) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLCertificate, zmailSSLCertificate);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL certificate
     *
     * @param zmailSSLCertificate new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public Map<String,Object> setSSLCertificate(String zmailSSLCertificate, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLCertificate, zmailSSLCertificate);
        return attrs;
    }

    /**
     * SSL certificate
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public void unsetSSLCertificate() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLCertificate, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL certificate
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public Map<String,Object> unsetSSLCertificate(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLCertificate, "");
        return attrs;
    }

    /**
     * SSL private key
     *
     * @return zmailSSLPrivateKey, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public String getSSLPrivateKey() {
        return getAttr(Provisioning.A_zmailSSLPrivateKey, null);
    }

    /**
     * SSL private key
     *
     * @param zmailSSLPrivateKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public void setSSLPrivateKey(String zmailSSLPrivateKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLPrivateKey, zmailSSLPrivateKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL private key
     *
     * @param zmailSSLPrivateKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public Map<String,Object> setSSLPrivateKey(String zmailSSLPrivateKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLPrivateKey, zmailSSLPrivateKey);
        return attrs;
    }

    /**
     * SSL private key
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public void unsetSSLPrivateKey() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLPrivateKey, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL private key
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public Map<String,Object> unsetSSLPrivateKey(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSSLPrivateKey, "");
        return attrs;
    }

    /**
     * background color for chameleon skin for the domain
     *
     * @return zmailSkinBackgroundColor, or null if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=648)
    public String getSkinBackgroundColor() {
        return getAttr(Provisioning.A_zmailSkinBackgroundColor, null);
    }

    /**
     * background color for chameleon skin for the domain
     *
     * @param zmailSkinBackgroundColor new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=648)
    public void setSkinBackgroundColor(String zmailSkinBackgroundColor) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinBackgroundColor, zmailSkinBackgroundColor);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * background color for chameleon skin for the domain
     *
     * @param zmailSkinBackgroundColor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=648)
    public Map<String,Object> setSkinBackgroundColor(String zmailSkinBackgroundColor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinBackgroundColor, zmailSkinBackgroundColor);
        return attrs;
    }

    /**
     * background color for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=648)
    public void unsetSkinBackgroundColor() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinBackgroundColor, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * background color for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=648)
    public Map<String,Object> unsetSkinBackgroundColor(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinBackgroundColor, "");
        return attrs;
    }

    /**
     * favicon for chameleon skin for the domain
     *
     * @return zmailSkinFavicon, or null if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=800)
    public String getSkinFavicon() {
        return getAttr(Provisioning.A_zmailSkinFavicon, null);
    }

    /**
     * favicon for chameleon skin for the domain
     *
     * @param zmailSkinFavicon new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=800)
    public void setSkinFavicon(String zmailSkinFavicon) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinFavicon, zmailSkinFavicon);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * favicon for chameleon skin for the domain
     *
     * @param zmailSkinFavicon new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=800)
    public Map<String,Object> setSkinFavicon(String zmailSkinFavicon, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinFavicon, zmailSkinFavicon);
        return attrs;
    }

    /**
     * favicon for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=800)
    public void unsetSkinFavicon() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinFavicon, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * favicon for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=800)
    public Map<String,Object> unsetSkinFavicon(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinFavicon, "");
        return attrs;
    }

    /**
     * foreground color for chameleon skin for the domain
     *
     * @return zmailSkinForegroundColor, or null if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=647)
    public String getSkinForegroundColor() {
        return getAttr(Provisioning.A_zmailSkinForegroundColor, null);
    }

    /**
     * foreground color for chameleon skin for the domain
     *
     * @param zmailSkinForegroundColor new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=647)
    public void setSkinForegroundColor(String zmailSkinForegroundColor) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinForegroundColor, zmailSkinForegroundColor);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * foreground color for chameleon skin for the domain
     *
     * @param zmailSkinForegroundColor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=647)
    public Map<String,Object> setSkinForegroundColor(String zmailSkinForegroundColor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinForegroundColor, zmailSkinForegroundColor);
        return attrs;
    }

    /**
     * foreground color for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=647)
    public void unsetSkinForegroundColor() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinForegroundColor, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * foreground color for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=647)
    public Map<String,Object> unsetSkinForegroundColor(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinForegroundColor, "");
        return attrs;
    }

    /**
     * logo app banner for chameleon skin for the domain
     *
     * @return zmailSkinLogoAppBanner, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=671)
    public String getSkinLogoAppBanner() {
        return getAttr(Provisioning.A_zmailSkinLogoAppBanner, null);
    }

    /**
     * logo app banner for chameleon skin for the domain
     *
     * @param zmailSkinLogoAppBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=671)
    public void setSkinLogoAppBanner(String zmailSkinLogoAppBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoAppBanner, zmailSkinLogoAppBanner);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logo app banner for chameleon skin for the domain
     *
     * @param zmailSkinLogoAppBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=671)
    public Map<String,Object> setSkinLogoAppBanner(String zmailSkinLogoAppBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoAppBanner, zmailSkinLogoAppBanner);
        return attrs;
    }

    /**
     * logo app banner for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=671)
    public void unsetSkinLogoAppBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoAppBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logo app banner for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=671)
    public Map<String,Object> unsetSkinLogoAppBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoAppBanner, "");
        return attrs;
    }

    /**
     * logo login banner for chameleon skin for the domain
     *
     * @return zmailSkinLogoLoginBanner, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=670)
    public String getSkinLogoLoginBanner() {
        return getAttr(Provisioning.A_zmailSkinLogoLoginBanner, null);
    }

    /**
     * logo login banner for chameleon skin for the domain
     *
     * @param zmailSkinLogoLoginBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=670)
    public void setSkinLogoLoginBanner(String zmailSkinLogoLoginBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoLoginBanner, zmailSkinLogoLoginBanner);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logo login banner for chameleon skin for the domain
     *
     * @param zmailSkinLogoLoginBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=670)
    public Map<String,Object> setSkinLogoLoginBanner(String zmailSkinLogoLoginBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoLoginBanner, zmailSkinLogoLoginBanner);
        return attrs;
    }

    /**
     * logo login banner for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=670)
    public void unsetSkinLogoLoginBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoLoginBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logo login banner for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=670)
    public Map<String,Object> unsetSkinLogoLoginBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoLoginBanner, "");
        return attrs;
    }

    /**
     * Logo URL for chameleon skin for the domain
     *
     * @return zmailSkinLogoURL, or null if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=649)
    public String getSkinLogoURL() {
        return getAttr(Provisioning.A_zmailSkinLogoURL, null);
    }

    /**
     * Logo URL for chameleon skin for the domain
     *
     * @param zmailSkinLogoURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=649)
    public void setSkinLogoURL(String zmailSkinLogoURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoURL, zmailSkinLogoURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Logo URL for chameleon skin for the domain
     *
     * @param zmailSkinLogoURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=649)
    public Map<String,Object> setSkinLogoURL(String zmailSkinLogoURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoURL, zmailSkinLogoURL);
        return attrs;
    }

    /**
     * Logo URL for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=649)
    public void unsetSkinLogoURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Logo URL for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=649)
    public Map<String,Object> unsetSkinLogoURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinLogoURL, "");
        return attrs;
    }

    /**
     * secondary color for chameleon skin for the domain
     *
     * @return zmailSkinSecondaryColor, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=668)
    public String getSkinSecondaryColor() {
        return getAttr(Provisioning.A_zmailSkinSecondaryColor, null);
    }

    /**
     * secondary color for chameleon skin for the domain
     *
     * @param zmailSkinSecondaryColor new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=668)
    public void setSkinSecondaryColor(String zmailSkinSecondaryColor) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSecondaryColor, zmailSkinSecondaryColor);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * secondary color for chameleon skin for the domain
     *
     * @param zmailSkinSecondaryColor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=668)
    public Map<String,Object> setSkinSecondaryColor(String zmailSkinSecondaryColor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSecondaryColor, zmailSkinSecondaryColor);
        return attrs;
    }

    /**
     * secondary color for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=668)
    public void unsetSkinSecondaryColor() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSecondaryColor, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * secondary color for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=668)
    public Map<String,Object> unsetSkinSecondaryColor(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSecondaryColor, "");
        return attrs;
    }

    /**
     * selection color for chameleon skin for the domain
     *
     * @return zmailSkinSelectionColor, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=669)
    public String getSkinSelectionColor() {
        return getAttr(Provisioning.A_zmailSkinSelectionColor, null);
    }

    /**
     * selection color for chameleon skin for the domain
     *
     * @param zmailSkinSelectionColor new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=669)
    public void setSkinSelectionColor(String zmailSkinSelectionColor) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSelectionColor, zmailSkinSelectionColor);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * selection color for chameleon skin for the domain
     *
     * @param zmailSkinSelectionColor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=669)
    public Map<String,Object> setSkinSelectionColor(String zmailSkinSelectionColor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSelectionColor, zmailSkinSelectionColor);
        return attrs;
    }

    /**
     * selection color for chameleon skin for the domain
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=669)
    public void unsetSkinSelectionColor() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSelectionColor, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * selection color for chameleon skin for the domain
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=669)
    public Map<String,Object> unsetSkinSelectionColor(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSkinSelectionColor, "");
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @return zmailSmtpHostname, or empty array if unset
     */
    @ZAttr(id=97)
    public String[] getSmtpHostname() {
        return getMultiAttr(Provisioning.A_zmailSmtpHostname);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zmailSmtpHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void setSmtpHostname(String[] zmailSmtpHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpHostname, zmailSmtpHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zmailSmtpHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> setSmtpHostname(String[] zmailSmtpHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpHostname, zmailSmtpHostname);
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zmailSmtpHostname new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void addSmtpHostname(String zmailSmtpHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSmtpHostname, zmailSmtpHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zmailSmtpHostname new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> addSmtpHostname(String zmailSmtpHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSmtpHostname, zmailSmtpHostname);
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zmailSmtpHostname existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void removeSmtpHostname(String zmailSmtpHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSmtpHostname, zmailSmtpHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zmailSmtpHostname existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> removeSmtpHostname(String zmailSmtpHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSmtpHostname, zmailSmtpHostname);
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void unsetSmtpHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> unsetSmtpHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpHostname, "");
        return attrs;
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * <p>Use getSmtpPortAsString to access value as a string.
     *
     * @see #getSmtpPortAsString()
     *
     * @return zmailSmtpPort, or -1 if unset
     */
    @ZAttr(id=98)
    public int getSmtpPort() {
        return getIntAttr(Provisioning.A_zmailSmtpPort, -1);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @return zmailSmtpPort, or null if unset
     */
    @ZAttr(id=98)
    public String getSmtpPortAsString() {
        return getAttr(Provisioning.A_zmailSmtpPort, null);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zmailSmtpPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=98)
    public void setSmtpPort(int zmailSmtpPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpPort, Integer.toString(zmailSmtpPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zmailSmtpPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=98)
    public Map<String,Object> setSmtpPort(int zmailSmtpPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpPort, Integer.toString(zmailSmtpPort));
        return attrs;
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zmailSmtpPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=98)
    public void setSmtpPortAsString(String zmailSmtpPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpPort, zmailSmtpPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zmailSmtpPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=98)
    public Map<String,Object> setSmtpPortAsString(String zmailSmtpPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpPort, zmailSmtpPort);
        return attrs;
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=98)
    public void unsetSmtpPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=98)
    public Map<String,Object> unsetSmtpPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpPort, "");
        return attrs;
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @return zmailSmtpSendPartial, or false if unset
     */
    @ZAttr(id=249)
    public boolean isSmtpSendPartial() {
        return getBooleanAttr(Provisioning.A_zmailSmtpSendPartial, false);
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @param zmailSmtpSendPartial new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=249)
    public void setSmtpSendPartial(boolean zmailSmtpSendPartial) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpSendPartial, zmailSmtpSendPartial ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @param zmailSmtpSendPartial new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=249)
    public Map<String,Object> setSmtpSendPartial(boolean zmailSmtpSendPartial, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpSendPartial, zmailSmtpSendPartial ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=249)
    public void unsetSmtpSendPartial() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpSendPartial, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=249)
    public Map<String,Object> unsetSmtpSendPartial(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpSendPartial, "");
        return attrs;
    }

    /**
     * timeout value in seconds
     *
     * @return zmailSmtpTimeout, or -1 if unset
     */
    @ZAttr(id=99)
    public int getSmtpTimeout() {
        return getIntAttr(Provisioning.A_zmailSmtpTimeout, -1);
    }

    /**
     * timeout value in seconds
     *
     * @param zmailSmtpTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=99)
    public void setSmtpTimeout(int zmailSmtpTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpTimeout, Integer.toString(zmailSmtpTimeout));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * timeout value in seconds
     *
     * @param zmailSmtpTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=99)
    public Map<String,Object> setSmtpTimeout(int zmailSmtpTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpTimeout, Integer.toString(zmailSmtpTimeout));
        return attrs;
    }

    /**
     * timeout value in seconds
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=99)
    public void unsetSmtpTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * timeout value in seconds
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=99)
    public Map<String,Object> unsetSmtpTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSmtpTimeout, "");
        return attrs;
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @return zmailSpamTrashAlias, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public String[] getSpamTrashAlias() {
        return getMultiAttr(Provisioning.A_zmailSpamTrashAlias);
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param zmailSpamTrashAlias new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public void setSpamTrashAlias(String[] zmailSpamTrashAlias) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpamTrashAlias, zmailSpamTrashAlias);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param zmailSpamTrashAlias new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public Map<String,Object> setSpamTrashAlias(String[] zmailSpamTrashAlias, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpamTrashAlias, zmailSpamTrashAlias);
        return attrs;
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param zmailSpamTrashAlias new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public void addSpamTrashAlias(String zmailSpamTrashAlias) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSpamTrashAlias, zmailSpamTrashAlias);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param zmailSpamTrashAlias new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public Map<String,Object> addSpamTrashAlias(String zmailSpamTrashAlias, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSpamTrashAlias, zmailSpamTrashAlias);
        return attrs;
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param zmailSpamTrashAlias existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public void removeSpamTrashAlias(String zmailSpamTrashAlias) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSpamTrashAlias, zmailSpamTrashAlias);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param zmailSpamTrashAlias existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public Map<String,Object> removeSpamTrashAlias(String zmailSpamTrashAlias, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSpamTrashAlias, zmailSpamTrashAlias);
        return attrs;
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public void unsetSpamTrashAlias() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpamTrashAlias, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Aliases of Trash folder. In case some IMAP clients use different
     * folder names other than Trash, the spam filter still special-cases
     * those folders as if they are Trash.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1167)
    public Map<String,Object> unsetSpamTrashAlias(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpamTrashAlias, "");
        return attrs;
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @return zmailStandardClientCustomPrefTab, or empty array if unset
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public String[] getStandardClientCustomPrefTab() {
        return getMultiAttr(Provisioning.A_zmailStandardClientCustomPrefTab);
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param zmailStandardClientCustomPrefTab new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public void setStandardClientCustomPrefTab(String[] zmailStandardClientCustomPrefTab) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTab, zmailStandardClientCustomPrefTab);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param zmailStandardClientCustomPrefTab new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public Map<String,Object> setStandardClientCustomPrefTab(String[] zmailStandardClientCustomPrefTab, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTab, zmailStandardClientCustomPrefTab);
        return attrs;
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param zmailStandardClientCustomPrefTab new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public void addStandardClientCustomPrefTab(String zmailStandardClientCustomPrefTab) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailStandardClientCustomPrefTab, zmailStandardClientCustomPrefTab);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param zmailStandardClientCustomPrefTab new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public Map<String,Object> addStandardClientCustomPrefTab(String zmailStandardClientCustomPrefTab, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailStandardClientCustomPrefTab, zmailStandardClientCustomPrefTab);
        return attrs;
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param zmailStandardClientCustomPrefTab existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public void removeStandardClientCustomPrefTab(String zmailStandardClientCustomPrefTab) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailStandardClientCustomPrefTab, zmailStandardClientCustomPrefTab);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param zmailStandardClientCustomPrefTab existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public Map<String,Object> removeStandardClientCustomPrefTab(String zmailStandardClientCustomPrefTab, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailStandardClientCustomPrefTab, zmailStandardClientCustomPrefTab);
        return attrs;
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public void unsetStandardClientCustomPrefTab() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTab, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * description of the custom tab in the Preferences page in HTML client
     * in the format {tab-name},{associated-URL}
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1267)
    public Map<String,Object> unsetStandardClientCustomPrefTab(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTab, "");
        return attrs;
    }

    /**
     * whether extra custom tabs in the Preferences page in HTML client are
     * enabled
     *
     * @return zmailStandardClientCustomPrefTabsEnabled, or false if unset
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1266)
    public boolean isStandardClientCustomPrefTabsEnabled() {
        return getBooleanAttr(Provisioning.A_zmailStandardClientCustomPrefTabsEnabled, false);
    }

    /**
     * whether extra custom tabs in the Preferences page in HTML client are
     * enabled
     *
     * @param zmailStandardClientCustomPrefTabsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1266)
    public void setStandardClientCustomPrefTabsEnabled(boolean zmailStandardClientCustomPrefTabsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTabsEnabled, zmailStandardClientCustomPrefTabsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether extra custom tabs in the Preferences page in HTML client are
     * enabled
     *
     * @param zmailStandardClientCustomPrefTabsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1266)
    public Map<String,Object> setStandardClientCustomPrefTabsEnabled(boolean zmailStandardClientCustomPrefTabsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTabsEnabled, zmailStandardClientCustomPrefTabsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether extra custom tabs in the Preferences page in HTML client are
     * enabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1266)
    public void unsetStandardClientCustomPrefTabsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTabsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether extra custom tabs in the Preferences page in HTML client are
     * enabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1266)
    public Map<String,Object> unsetStandardClientCustomPrefTabsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStandardClientCustomPrefTabsEnabled, "");
        return attrs;
    }

    /**
     * UC service zmailId
     *
     * @return zmailUCServiceId, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1401)
    public String getUCServiceId() {
        return getAttr(Provisioning.A_zmailUCServiceId, null);
    }

    /**
     * UC service zmailId
     *
     * @param zmailUCServiceId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1401)
    public void setUCServiceId(String zmailUCServiceId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCServiceId, zmailUCServiceId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * UC service zmailId
     *
     * @param zmailUCServiceId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1401)
    public Map<String,Object> setUCServiceId(String zmailUCServiceId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCServiceId, zmailUCServiceId);
        return attrs;
    }

    /**
     * UC service zmailId
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1401)
    public void unsetUCServiceId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCServiceId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * UC service zmailId
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1401)
    public Map<String,Object> unsetUCServiceId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCServiceId, "");
        return attrs;
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @return zmailVirtualHostname, or empty array if unset
     */
    @ZAttr(id=352)
    public String[] getVirtualHostname() {
        return getMultiAttr(Provisioning.A_zmailVirtualHostname);
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param zmailVirtualHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=352)
    public void setVirtualHostname(String[] zmailVirtualHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualHostname, zmailVirtualHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param zmailVirtualHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=352)
    public Map<String,Object> setVirtualHostname(String[] zmailVirtualHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualHostname, zmailVirtualHostname);
        return attrs;
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param zmailVirtualHostname new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=352)
    public void addVirtualHostname(String zmailVirtualHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailVirtualHostname, zmailVirtualHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param zmailVirtualHostname new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=352)
    public Map<String,Object> addVirtualHostname(String zmailVirtualHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailVirtualHostname, zmailVirtualHostname);
        return attrs;
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param zmailVirtualHostname existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=352)
    public void removeVirtualHostname(String zmailVirtualHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailVirtualHostname, zmailVirtualHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param zmailVirtualHostname existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=352)
    public Map<String,Object> removeVirtualHostname(String zmailVirtualHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailVirtualHostname, zmailVirtualHostname);
        return attrs;
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=352)
    public void unsetVirtualHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An alias for this domain, used to determine default login domain based
     * on URL client is visiting
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=352)
    public Map<String,Object> unsetVirtualHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualHostname, "");
        return attrs;
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @return zmailVirtualIPAddress, or empty array if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public String[] getVirtualIPAddress() {
        return getMultiAttr(Provisioning.A_zmailVirtualIPAddress);
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param zmailVirtualIPAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public void setVirtualIPAddress(String[] zmailVirtualIPAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualIPAddress, zmailVirtualIPAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param zmailVirtualIPAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public Map<String,Object> setVirtualIPAddress(String[] zmailVirtualIPAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualIPAddress, zmailVirtualIPAddress);
        return attrs;
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param zmailVirtualIPAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public void addVirtualIPAddress(String zmailVirtualIPAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailVirtualIPAddress, zmailVirtualIPAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param zmailVirtualIPAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public Map<String,Object> addVirtualIPAddress(String zmailVirtualIPAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailVirtualIPAddress, zmailVirtualIPAddress);
        return attrs;
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param zmailVirtualIPAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public void removeVirtualIPAddress(String zmailVirtualIPAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailVirtualIPAddress, zmailVirtualIPAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param zmailVirtualIPAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public Map<String,Object> removeVirtualIPAddress(String zmailVirtualIPAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailVirtualIPAddress, zmailVirtualIPAddress);
        return attrs;
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public void unsetVirtualIPAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualIPAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * An virtual IP address for this domain, used to determine domain based
     * on an IP address
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=562)
    public Map<String,Object> unsetVirtualIPAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirtualIPAddress, "");
        return attrs;
    }

    /**
     * link for admin users in web client
     *
     * @return zmailWebClientAdminReference, or null if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=701)
    public String getWebClientAdminReference() {
        return getAttr(Provisioning.A_zmailWebClientAdminReference, null);
    }

    /**
     * link for admin users in web client
     *
     * @param zmailWebClientAdminReference new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=701)
    public void setWebClientAdminReference(String zmailWebClientAdminReference) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientAdminReference, zmailWebClientAdminReference);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * link for admin users in web client
     *
     * @param zmailWebClientAdminReference new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=701)
    public Map<String,Object> setWebClientAdminReference(String zmailWebClientAdminReference, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientAdminReference, zmailWebClientAdminReference);
        return attrs;
    }

    /**
     * link for admin users in web client
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=701)
    public void unsetWebClientAdminReference() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientAdminReference, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * link for admin users in web client
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=701)
    public Map<String,Object> unsetWebClientAdminReference(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientAdminReference, "");
        return attrs;
    }

    /**
     * login URL for web client to send the user to upon failed login, auth
     * expired, or no/invalid auth
     *
     * @return zmailWebClientLoginURL, or null if unset
     */
    @ZAttr(id=506)
    public String getWebClientLoginURL() {
        return getAttr(Provisioning.A_zmailWebClientLoginURL, null);
    }

    /**
     * login URL for web client to send the user to upon failed login, auth
     * expired, or no/invalid auth
     *
     * @param zmailWebClientLoginURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=506)
    public void setWebClientLoginURL(String zmailWebClientLoginURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURL, zmailWebClientLoginURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * login URL for web client to send the user to upon failed login, auth
     * expired, or no/invalid auth
     *
     * @param zmailWebClientLoginURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=506)
    public Map<String,Object> setWebClientLoginURL(String zmailWebClientLoginURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURL, zmailWebClientLoginURL);
        return attrs;
    }

    /**
     * login URL for web client to send the user to upon failed login, auth
     * expired, or no/invalid auth
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=506)
    public void unsetWebClientLoginURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * login URL for web client to send the user to upon failed login, auth
     * expired, or no/invalid auth
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=506)
    public Map<String,Object> unsetWebClientLoginURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURL, "");
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @return zmailWebClientLoginURLAllowedIP, or empty array if unset
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public String[] getWebClientLoginURLAllowedIP() {
        return getMultiAttr(Provisioning.A_zmailWebClientLoginURLAllowedIP);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLoginURLAllowedIP new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public void setWebClientLoginURLAllowedIP(String[] zmailWebClientLoginURLAllowedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedIP, zmailWebClientLoginURLAllowedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLoginURLAllowedIP new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public Map<String,Object> setWebClientLoginURLAllowedIP(String[] zmailWebClientLoginURLAllowedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedIP, zmailWebClientLoginURLAllowedIP);
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLoginURLAllowedIP new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public void addWebClientLoginURLAllowedIP(String zmailWebClientLoginURLAllowedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLoginURLAllowedIP, zmailWebClientLoginURLAllowedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLoginURLAllowedIP new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public Map<String,Object> addWebClientLoginURLAllowedIP(String zmailWebClientLoginURLAllowedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLoginURLAllowedIP, zmailWebClientLoginURLAllowedIP);
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLoginURLAllowedIP existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public void removeWebClientLoginURLAllowedIP(String zmailWebClientLoginURLAllowedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLoginURLAllowedIP, zmailWebClientLoginURLAllowedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLoginURLAllowedIP existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public Map<String,Object> removeWebClientLoginURLAllowedIP(String zmailWebClientLoginURLAllowedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLoginURLAllowedIP, zmailWebClientLoginURLAllowedIP);
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public void unsetWebClientLoginURLAllowedIP() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedIP, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLoginURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1352)
    public Map<String,Object> unsetWebClientLoginURLAllowedIP(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedIP, "");
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @return zmailWebClientLoginURLAllowedUA, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public String[] getWebClientLoginURLAllowedUA() {
        return getMultiAttr(Provisioning.A_zmailWebClientLoginURLAllowedUA);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLoginURLAllowedUA new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public void setWebClientLoginURLAllowedUA(String[] zmailWebClientLoginURLAllowedUA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedUA, zmailWebClientLoginURLAllowedUA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLoginURLAllowedUA new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public Map<String,Object> setWebClientLoginURLAllowedUA(String[] zmailWebClientLoginURLAllowedUA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedUA, zmailWebClientLoginURLAllowedUA);
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLoginURLAllowedUA new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public void addWebClientLoginURLAllowedUA(String zmailWebClientLoginURLAllowedUA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLoginURLAllowedUA, zmailWebClientLoginURLAllowedUA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLoginURLAllowedUA new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public Map<String,Object> addWebClientLoginURLAllowedUA(String zmailWebClientLoginURLAllowedUA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLoginURLAllowedUA, zmailWebClientLoginURLAllowedUA);
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLoginURLAllowedUA existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public void removeWebClientLoginURLAllowedUA(String zmailWebClientLoginURLAllowedUA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLoginURLAllowedUA, zmailWebClientLoginURLAllowedUA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLoginURLAllowedUA existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public Map<String,Object> removeWebClientLoginURLAllowedUA(String zmailWebClientLoginURLAllowedUA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLoginURLAllowedUA, zmailWebClientLoginURLAllowedUA);
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public void unsetWebClientLoginURLAllowedUA() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedUA, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLoginURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1141)
    public Map<String,Object> unsetWebClientLoginURLAllowedUA(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLoginURLAllowedUA, "");
        return attrs;
    }

    /**
     * logout URL for web client to send the user to upon explicit logging
     * out
     *
     * @return zmailWebClientLogoutURL, or null if unset
     */
    @ZAttr(id=507)
    public String getWebClientLogoutURL() {
        return getAttr(Provisioning.A_zmailWebClientLogoutURL, null);
    }

    /**
     * logout URL for web client to send the user to upon explicit logging
     * out
     *
     * @param zmailWebClientLogoutURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=507)
    public void setWebClientLogoutURL(String zmailWebClientLogoutURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURL, zmailWebClientLogoutURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logout URL for web client to send the user to upon explicit logging
     * out
     *
     * @param zmailWebClientLogoutURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=507)
    public Map<String,Object> setWebClientLogoutURL(String zmailWebClientLogoutURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURL, zmailWebClientLogoutURL);
        return attrs;
    }

    /**
     * logout URL for web client to send the user to upon explicit logging
     * out
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=507)
    public void unsetWebClientLogoutURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * logout URL for web client to send the user to upon explicit logging
     * out
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=507)
    public Map<String,Object> unsetWebClientLogoutURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURL, "");
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @return zmailWebClientLogoutURLAllowedIP, or empty array if unset
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public String[] getWebClientLogoutURLAllowedIP() {
        return getMultiAttr(Provisioning.A_zmailWebClientLogoutURLAllowedIP);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLogoutURLAllowedIP new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public void setWebClientLogoutURLAllowedIP(String[] zmailWebClientLogoutURLAllowedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedIP, zmailWebClientLogoutURLAllowedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLogoutURLAllowedIP new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public Map<String,Object> setWebClientLogoutURLAllowedIP(String[] zmailWebClientLogoutURLAllowedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedIP, zmailWebClientLogoutURLAllowedIP);
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLogoutURLAllowedIP new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public void addWebClientLogoutURLAllowedIP(String zmailWebClientLogoutURLAllowedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLogoutURLAllowedIP, zmailWebClientLogoutURLAllowedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLogoutURLAllowedIP new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public Map<String,Object> addWebClientLogoutURLAllowedIP(String zmailWebClientLogoutURLAllowedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLogoutURLAllowedIP, zmailWebClientLogoutURLAllowedIP);
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLogoutURLAllowedIP existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public void removeWebClientLogoutURLAllowedIP(String zmailWebClientLogoutURLAllowedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLogoutURLAllowedIP, zmailWebClientLogoutURLAllowedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param zmailWebClientLogoutURLAllowedIP existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public Map<String,Object> removeWebClientLogoutURLAllowedIP(String zmailWebClientLogoutURLAllowedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLogoutURLAllowedIP, zmailWebClientLogoutURLAllowedIP);
        return attrs;
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public void unsetWebClientLogoutURLAllowedIP() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedIP, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex for allowed client IP addresses for honoring
     * zmailWebClientLogoutURL. If not set, all IP addresses are allowed. If
     * multiple values are set, an IP address is allowed as long as it
     * matches any one of the values.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.5
     */
    @ZAttr(id=1353)
    public Map<String,Object> unsetWebClientLogoutURLAllowedIP(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedIP, "");
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @return zmailWebClientLogoutURLAllowedUA, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public String[] getWebClientLogoutURLAllowedUA() {
        return getMultiAttr(Provisioning.A_zmailWebClientLogoutURLAllowedUA);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLogoutURLAllowedUA new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public void setWebClientLogoutURLAllowedUA(String[] zmailWebClientLogoutURLAllowedUA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedUA, zmailWebClientLogoutURLAllowedUA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLogoutURLAllowedUA new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public Map<String,Object> setWebClientLogoutURLAllowedUA(String[] zmailWebClientLogoutURLAllowedUA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedUA, zmailWebClientLogoutURLAllowedUA);
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLogoutURLAllowedUA new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public void addWebClientLogoutURLAllowedUA(String zmailWebClientLogoutURLAllowedUA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLogoutURLAllowedUA, zmailWebClientLogoutURLAllowedUA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLogoutURLAllowedUA new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public Map<String,Object> addWebClientLogoutURLAllowedUA(String zmailWebClientLogoutURLAllowedUA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailWebClientLogoutURLAllowedUA, zmailWebClientLogoutURLAllowedUA);
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLogoutURLAllowedUA existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public void removeWebClientLogoutURLAllowedUA(String zmailWebClientLogoutURLAllowedUA) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLogoutURLAllowedUA, zmailWebClientLogoutURLAllowedUA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param zmailWebClientLogoutURLAllowedUA existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public Map<String,Object> removeWebClientLogoutURLAllowedUA(String zmailWebClientLogoutURLAllowedUA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailWebClientLogoutURLAllowedUA, zmailWebClientLogoutURLAllowedUA);
        return attrs;
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public void unsetWebClientLogoutURLAllowedUA() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedUA, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * regex to be matched for allowed user agents for honoring
     * zmailWebClientLogoutURL. If not set, all UAs are allowed. If multiple
     * values are set, an UA is allowed as long as it matches any one of the
     * values. e.g. &quot;.*Windows NT.*Firefox/3.*&quot; will match firefox
     * 3 or later browsers on Windows. &quot;.*MSIE.*Windows NT.*&quot; will
     * match IE browsers on Windows.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1142)
    public Map<String,Object> unsetWebClientLogoutURLAllowedUA(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientLogoutURLAllowedUA, "");
        return attrs;
    }

    /**
     * max input buffer length for web client
     *
     * @return zmailWebClientMaxInputBufferLength, or 1024 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1339)
    public int getWebClientMaxInputBufferLength() {
        return getIntAttr(Provisioning.A_zmailWebClientMaxInputBufferLength, 1024);
    }

    /**
     * max input buffer length for web client
     *
     * @param zmailWebClientMaxInputBufferLength new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1339)
    public void setWebClientMaxInputBufferLength(int zmailWebClientMaxInputBufferLength) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientMaxInputBufferLength, Integer.toString(zmailWebClientMaxInputBufferLength));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * max input buffer length for web client
     *
     * @param zmailWebClientMaxInputBufferLength new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1339)
    public Map<String,Object> setWebClientMaxInputBufferLength(int zmailWebClientMaxInputBufferLength, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientMaxInputBufferLength, Integer.toString(zmailWebClientMaxInputBufferLength));
        return attrs;
    }

    /**
     * max input buffer length for web client
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1339)
    public void unsetWebClientMaxInputBufferLength() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientMaxInputBufferLength, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * max input buffer length for web client
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1339)
    public Map<String,Object> unsetWebClientMaxInputBufferLength(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailWebClientMaxInputBufferLength, "");
        return attrs;
    }

    /**
     * whether zimlets that send sensitive data are disabled in
     * &quot;mixed&quot; zmailMailMode
     *
     * @return zmailZimletDataSensitiveInMixedModeDisabled, or true if unset
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1269)
    public boolean isZimletDataSensitiveInMixedModeDisabled() {
        return getBooleanAttr(Provisioning.A_zmailZimletDataSensitiveInMixedModeDisabled, true);
    }

    /**
     * whether zimlets that send sensitive data are disabled in
     * &quot;mixed&quot; zmailMailMode
     *
     * @param zmailZimletDataSensitiveInMixedModeDisabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1269)
    public void setZimletDataSensitiveInMixedModeDisabled(boolean zmailZimletDataSensitiveInMixedModeDisabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDataSensitiveInMixedModeDisabled, zmailZimletDataSensitiveInMixedModeDisabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether zimlets that send sensitive data are disabled in
     * &quot;mixed&quot; zmailMailMode
     *
     * @param zmailZimletDataSensitiveInMixedModeDisabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1269)
    public Map<String,Object> setZimletDataSensitiveInMixedModeDisabled(boolean zmailZimletDataSensitiveInMixedModeDisabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDataSensitiveInMixedModeDisabled, zmailZimletDataSensitiveInMixedModeDisabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether zimlets that send sensitive data are disabled in
     * &quot;mixed&quot; zmailMailMode
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1269)
    public void unsetZimletDataSensitiveInMixedModeDisabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDataSensitiveInMixedModeDisabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether zimlets that send sensitive data are disabled in
     * &quot;mixed&quot; zmailMailMode
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.3
     */
    @ZAttr(id=1269)
    public Map<String,Object> unsetZimletDataSensitiveInMixedModeDisabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDataSensitiveInMixedModeDisabled, "");
        return attrs;
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @return zmailZimletDomainAvailableZimlets, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public String[] getZimletDomainAvailableZimlets() {
        return getMultiAttr(Provisioning.A_zmailZimletDomainAvailableZimlets);
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param zmailZimletDomainAvailableZimlets new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public void setZimletDomainAvailableZimlets(String[] zmailZimletDomainAvailableZimlets) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDomainAvailableZimlets, zmailZimletDomainAvailableZimlets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param zmailZimletDomainAvailableZimlets new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public Map<String,Object> setZimletDomainAvailableZimlets(String[] zmailZimletDomainAvailableZimlets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDomainAvailableZimlets, zmailZimletDomainAvailableZimlets);
        return attrs;
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param zmailZimletDomainAvailableZimlets new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public void addZimletDomainAvailableZimlets(String zmailZimletDomainAvailableZimlets) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailZimletDomainAvailableZimlets, zmailZimletDomainAvailableZimlets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param zmailZimletDomainAvailableZimlets new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public Map<String,Object> addZimletDomainAvailableZimlets(String zmailZimletDomainAvailableZimlets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailZimletDomainAvailableZimlets, zmailZimletDomainAvailableZimlets);
        return attrs;
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param zmailZimletDomainAvailableZimlets existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public void removeZimletDomainAvailableZimlets(String zmailZimletDomainAvailableZimlets) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailZimletDomainAvailableZimlets, zmailZimletDomainAvailableZimlets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param zmailZimletDomainAvailableZimlets existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public Map<String,Object> removeZimletDomainAvailableZimlets(String zmailZimletDomainAvailableZimlets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailZimletDomainAvailableZimlets, zmailZimletDomainAvailableZimlets);
        return attrs;
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public void unsetZimletDomainAvailableZimlets() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDomainAvailableZimlets, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * List of Zimlets available to this domain. Zimlets available to
     * accounts in the domain is the union of account/cos attribute
     * zmailZimletAvailableZimlets and this attribute. See
     * zmailZimletAvailableZimlets for value format.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=710)
    public Map<String,Object> unsetZimletDomainAvailableZimlets(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailZimletDomainAvailableZimlets, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE

}
