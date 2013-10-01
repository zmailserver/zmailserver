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
package com.zimbra.cs.account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.account.ZAttr;
import com.zimbra.common.account.ZAttrProvisioning;
import com.zimbra.common.util.DateUtil;
import com.zimbra.common.util.StringUtil;

/**
 * AUTO-GENERATED. DO NOT EDIT.
 *
 */
public abstract class ZAttrServer extends NamedEntry {

    public ZAttrServer(String name, String id, Map<String,Object> attrs, Map<String,Object> defaults, Provisioning prov) {
        super(name, id, attrs, defaults, prov);
    }

    ///// BEGIN-AUTO-GEN-REPLACE

    /* build: 9.0.0_BETA1_1111 rgadipuuri 20130510-1145 */

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @return cn, or null if unset
     */
    @ZAttr(id=-1)
    public String getCn() {
        return getAttr(Provisioning.A_cn, null);
    }

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @param cn new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setCn(String cn) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_cn, cn);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @param cn new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setCn(String cn, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_cn, cn);
        return attrs;
    }

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetCn() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_cn, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2256: common name(s) for which the entity is known by
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetCn(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_cn, "");
        return attrs;
    }

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
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setDescription(String[] description) throws com.zimbra.common.service.ServiceException {
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
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void addDescription(String description) throws com.zimbra.common.service.ServiceException {
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
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void removeDescription(String description) throws com.zimbra.common.service.ServiceException {
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
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetDescription() throws com.zimbra.common.service.ServiceException {
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
     * Zimbra access control list
     *
     * @return zimbraACE, or empty array if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public String[] getACE() {
        return getMultiAttr(Provisioning.A_zimbraACE);
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void setACE(String[] zimbraACE) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraACE, zimbraACE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> setACE(String[] zimbraACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraACE, zimbraACE);
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void addACE(String zimbraACE) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraACE, zimbraACE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> addACE(String zimbraACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraACE, zimbraACE);
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void removeACE(String zimbraACE) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraACE, zimbraACE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zimbra access control list
     *
     * @param zimbraACE existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> removeACE(String zimbraACE, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraACE, zimbraACE);
        return attrs;
    }

    /**
     * Zimbra access control list
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public void unsetACE() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraACE, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zimbra access control list
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=659)
    public Map<String,Object> unsetACE(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraACE, "");
        return attrs;
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @return zimbraAdminBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public String getAdminBindAddress() {
        return getAttr(Provisioning.A_zimbraAdminBindAddress, null);
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @param zimbraAdminBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public void setAdminBindAddress(String zimbraAdminBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminBindAddress, zimbraAdminBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @param zimbraAdminBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public Map<String,Object> setAdminBindAddress(String zimbraAdminBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminBindAddress, zimbraAdminBindAddress);
        return attrs;
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public void unsetAdminBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public Map<String,Object> unsetAdminBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminBindAddress, "");
        return attrs;
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @return zimbraAdminImapImportNumThreads, or 20 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public int getAdminImapImportNumThreads() {
        return getIntAttr(Provisioning.A_zimbraAdminImapImportNumThreads, 20);
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @param zimbraAdminImapImportNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public void setAdminImapImportNumThreads(int zimbraAdminImapImportNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminImapImportNumThreads, Integer.toString(zimbraAdminImapImportNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @param zimbraAdminImapImportNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public Map<String,Object> setAdminImapImportNumThreads(int zimbraAdminImapImportNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminImapImportNumThreads, Integer.toString(zimbraAdminImapImportNumThreads));
        return attrs;
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public void unsetAdminImapImportNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminImapImportNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public Map<String,Object> unsetAdminImapImportNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminImapImportNumThreads, "");
        return attrs;
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zimbraAdminBindAddress.
     *
     * @return zimbraAdminLocalBind, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public boolean isAdminLocalBind() {
        return getBooleanAttr(Provisioning.A_zimbraAdminLocalBind, false);
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zimbraAdminBindAddress.
     *
     * @param zimbraAdminLocalBind new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public void setAdminLocalBind(boolean zimbraAdminLocalBind) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminLocalBind, zimbraAdminLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zimbraAdminBindAddress.
     *
     * @param zimbraAdminLocalBind new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public Map<String,Object> setAdminLocalBind(boolean zimbraAdminLocalBind, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminLocalBind, zimbraAdminLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zimbraAdminBindAddress.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public void unsetAdminLocalBind() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminLocalBind, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zimbraAdminBindAddress.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public Map<String,Object> unsetAdminLocalBind(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminLocalBind, "");
        return attrs;
    }

    /**
     * SSL port for admin UI
     *
     * <p>Use getAdminPortAsString to access value as a string.
     *
     * @see #getAdminPortAsString()
     *
     * @return zimbraAdminPort, or 7071 if unset
     */
    @ZAttr(id=155)
    public int getAdminPort() {
        return getIntAttr(Provisioning.A_zimbraAdminPort, 7071);
    }

    /**
     * SSL port for admin UI
     *
     * @return zimbraAdminPort, or "7071" if unset
     */
    @ZAttr(id=155)
    public String getAdminPortAsString() {
        return getAttr(Provisioning.A_zimbraAdminPort, "7071");
    }

    /**
     * SSL port for admin UI
     *
     * @param zimbraAdminPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=155)
    public void setAdminPort(int zimbraAdminPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminPort, Integer.toString(zimbraAdminPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for admin UI
     *
     * @param zimbraAdminPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=155)
    public Map<String,Object> setAdminPort(int zimbraAdminPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminPort, Integer.toString(zimbraAdminPort));
        return attrs;
    }

    /**
     * SSL port for admin UI
     *
     * @param zimbraAdminPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=155)
    public void setAdminPortAsString(String zimbraAdminPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminPort, zimbraAdminPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for admin UI
     *
     * @param zimbraAdminPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=155)
    public Map<String,Object> setAdminPortAsString(String zimbraAdminPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminPort, zimbraAdminPort);
        return attrs;
    }

    /**
     * SSL port for admin UI
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=155)
    public void unsetAdminPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for admin UI
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=155)
    public Map<String,Object> unsetAdminPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminPort, "");
        return attrs;
    }

    /**
     * SSL proxy port for admin console UI
     *
     * <p>Use getAdminProxyPortAsString to access value as a string.
     *
     * @see #getAdminProxyPortAsString()
     *
     * @return zimbraAdminProxyPort, or 9071 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public int getAdminProxyPort() {
        return getIntAttr(Provisioning.A_zimbraAdminProxyPort, 9071);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @return zimbraAdminProxyPort, or "9071" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public String getAdminProxyPortAsString() {
        return getAttr(Provisioning.A_zimbraAdminProxyPort, "9071");
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zimbraAdminProxyPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public void setAdminProxyPort(int zimbraAdminProxyPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminProxyPort, Integer.toString(zimbraAdminProxyPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zimbraAdminProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public Map<String,Object> setAdminProxyPort(int zimbraAdminProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminProxyPort, Integer.toString(zimbraAdminProxyPort));
        return attrs;
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zimbraAdminProxyPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public void setAdminProxyPortAsString(String zimbraAdminProxyPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminProxyPort, zimbraAdminProxyPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zimbraAdminProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public Map<String,Object> setAdminProxyPortAsString(String zimbraAdminProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminProxyPort, zimbraAdminProxyPort);
        return attrs;
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public void unsetAdminProxyPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminProxyPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public Map<String,Object> unsetAdminProxyPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminProxyPort, "");
        return attrs;
    }

    /**
     * URL prefix for where the zimbraAdmin app resides on this server
     *
     * @return zimbraAdminURL, or "/zimbraAdmin" if unset
     */
    @ZAttr(id=497)
    public String getAdminURL() {
        return getAttr(Provisioning.A_zimbraAdminURL, "/zimbraAdmin");
    }

    /**
     * URL prefix for where the zimbraAdmin app resides on this server
     *
     * @param zimbraAdminURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=497)
    public void setAdminURL(String zimbraAdminURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminURL, zimbraAdminURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zimbraAdmin app resides on this server
     *
     * @param zimbraAdminURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=497)
    public Map<String,Object> setAdminURL(String zimbraAdminURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminURL, zimbraAdminURL);
        return attrs;
    }

    /**
     * URL prefix for where the zimbraAdmin app resides on this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=497)
    public void unsetAdminURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zimbraAdmin app resides on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=497)
    public Map<String,Object> unsetAdminURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAdminURL, "");
        return attrs;
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @return zimbraAttachmentsIndexedTextLimit, or 1048576 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public int getAttachmentsIndexedTextLimit() {
        return getIntAttr(Provisioning.A_zimbraAttachmentsIndexedTextLimit, 1048576);
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @param zimbraAttachmentsIndexedTextLimit new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public void setAttachmentsIndexedTextLimit(int zimbraAttachmentsIndexedTextLimit) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsIndexedTextLimit, Integer.toString(zimbraAttachmentsIndexedTextLimit));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @param zimbraAttachmentsIndexedTextLimit new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public Map<String,Object> setAttachmentsIndexedTextLimit(int zimbraAttachmentsIndexedTextLimit, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsIndexedTextLimit, Integer.toString(zimbraAttachmentsIndexedTextLimit));
        return attrs;
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public void unsetAttachmentsIndexedTextLimit() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsIndexedTextLimit, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public Map<String,Object> unsetAttachmentsIndexedTextLimit(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsIndexedTextLimit, "");
        return attrs;
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @return zimbraAttachmentsScanURL, or null if unset
     */
    @ZAttr(id=239)
    public String getAttachmentsScanURL() {
        return getAttr(Provisioning.A_zimbraAttachmentsScanURL, null);
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @param zimbraAttachmentsScanURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=239)
    public void setAttachmentsScanURL(String zimbraAttachmentsScanURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsScanURL, zimbraAttachmentsScanURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @param zimbraAttachmentsScanURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=239)
    public Map<String,Object> setAttachmentsScanURL(String zimbraAttachmentsScanURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsScanURL, zimbraAttachmentsScanURL);
        return attrs;
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=239)
    public void unsetAttachmentsScanURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsScanURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=239)
    public Map<String,Object> unsetAttachmentsScanURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAttachmentsScanURL, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zimbraAutoProvBatchSize and number of domains configured in
     * zimbraAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zimbraAutoProvScheduledDomains
     * and auto creates up to domain.zimbraAutoProvBatchSize accounts. If
     * that process takes longer than zimbraAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zimbraAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getAutoProvPollingIntervalAsString to access value as a string.
     *
     * @see #getAutoProvPollingIntervalAsString()
     *
     * @return zimbraAutoProvPollingInterval in millseconds, or 900000 (15m)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public long getAutoProvPollingInterval() {
        return getTimeInterval(Provisioning.A_zimbraAutoProvPollingInterval, 900000L);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zimbraAutoProvBatchSize and number of domains configured in
     * zimbraAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zimbraAutoProvScheduledDomains
     * and auto creates up to domain.zimbraAutoProvBatchSize accounts. If
     * that process takes longer than zimbraAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zimbraAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zimbraAutoProvPollingInterval, or "15m" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public String getAutoProvPollingIntervalAsString() {
        return getAttr(Provisioning.A_zimbraAutoProvPollingInterval, "15m");
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zimbraAutoProvBatchSize and number of domains configured in
     * zimbraAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zimbraAutoProvScheduledDomains
     * and auto creates up to domain.zimbraAutoProvBatchSize accounts. If
     * that process takes longer than zimbraAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zimbraAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraAutoProvPollingInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public void setAutoProvPollingInterval(String zimbraAutoProvPollingInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvPollingInterval, zimbraAutoProvPollingInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zimbraAutoProvBatchSize and number of domains configured in
     * zimbraAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zimbraAutoProvScheduledDomains
     * and auto creates up to domain.zimbraAutoProvBatchSize accounts. If
     * that process takes longer than zimbraAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zimbraAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraAutoProvPollingInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public Map<String,Object> setAutoProvPollingInterval(String zimbraAutoProvPollingInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvPollingInterval, zimbraAutoProvPollingInterval);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zimbraAutoProvBatchSize and number of domains configured in
     * zimbraAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zimbraAutoProvScheduledDomains
     * and auto creates up to domain.zimbraAutoProvBatchSize accounts. If
     * that process takes longer than zimbraAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zimbraAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public void unsetAutoProvPollingInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvPollingInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zimbraAutoProvBatchSize and number of domains configured in
     * zimbraAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zimbraAutoProvScheduledDomains
     * and auto creates up to domain.zimbraAutoProvBatchSize accounts. If
     * that process takes longer than zimbraAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zimbraAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public Map<String,Object> unsetAutoProvPollingInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvPollingInterval, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @return zimbraAutoProvScheduledDomains, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public String[] getAutoProvScheduledDomains() {
        return getMultiAttr(Provisioning.A_zimbraAutoProvScheduledDomains);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zimbraAutoProvScheduledDomains new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void setAutoProvScheduledDomains(String[] zimbraAutoProvScheduledDomains) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvScheduledDomains, zimbraAutoProvScheduledDomains);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zimbraAutoProvScheduledDomains new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> setAutoProvScheduledDomains(String[] zimbraAutoProvScheduledDomains, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvScheduledDomains, zimbraAutoProvScheduledDomains);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zimbraAutoProvScheduledDomains new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void addAutoProvScheduledDomains(String zimbraAutoProvScheduledDomains) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraAutoProvScheduledDomains, zimbraAutoProvScheduledDomains);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zimbraAutoProvScheduledDomains new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> addAutoProvScheduledDomains(String zimbraAutoProvScheduledDomains, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraAutoProvScheduledDomains, zimbraAutoProvScheduledDomains);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zimbraAutoProvScheduledDomains existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void removeAutoProvScheduledDomains(String zimbraAutoProvScheduledDomains) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraAutoProvScheduledDomains, zimbraAutoProvScheduledDomains);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zimbraAutoProvScheduledDomains existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> removeAutoProvScheduledDomains(String zimbraAutoProvScheduledDomains, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraAutoProvScheduledDomains, zimbraAutoProvScheduledDomains);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void unsetAutoProvScheduledDomains() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvScheduledDomains, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zimbraAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> unsetAutoProvScheduledDomains(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraAutoProvScheduledDomains, "");
        return attrs;
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @return zimbraBackupAutoGroupedInterval, or "1d" if unset
     */
    @ZAttr(id=513)
    public String getBackupAutoGroupedInterval() {
        return getAttr(Provisioning.A_zimbraBackupAutoGroupedInterval, "1d");
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @param zimbraBackupAutoGroupedInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=513)
    public void setBackupAutoGroupedInterval(String zimbraBackupAutoGroupedInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedInterval, zimbraBackupAutoGroupedInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @param zimbraBackupAutoGroupedInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=513)
    public Map<String,Object> setBackupAutoGroupedInterval(String zimbraBackupAutoGroupedInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedInterval, zimbraBackupAutoGroupedInterval);
        return attrs;
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=513)
    public void unsetBackupAutoGroupedInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=513)
    public Map<String,Object> unsetBackupAutoGroupedInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedInterval, "");
        return attrs;
    }

    /**
     * number of groups to auto-group backups over
     *
     * @return zimbraBackupAutoGroupedNumGroups, or 7 if unset
     */
    @ZAttr(id=514)
    public int getBackupAutoGroupedNumGroups() {
        return getIntAttr(Provisioning.A_zimbraBackupAutoGroupedNumGroups, 7);
    }

    /**
     * number of groups to auto-group backups over
     *
     * @param zimbraBackupAutoGroupedNumGroups new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=514)
    public void setBackupAutoGroupedNumGroups(int zimbraBackupAutoGroupedNumGroups) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedNumGroups, Integer.toString(zimbraBackupAutoGroupedNumGroups));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of groups to auto-group backups over
     *
     * @param zimbraBackupAutoGroupedNumGroups new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=514)
    public Map<String,Object> setBackupAutoGroupedNumGroups(int zimbraBackupAutoGroupedNumGroups, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedNumGroups, Integer.toString(zimbraBackupAutoGroupedNumGroups));
        return attrs;
    }

    /**
     * number of groups to auto-group backups over
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=514)
    public void unsetBackupAutoGroupedNumGroups() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedNumGroups, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of groups to auto-group backups over
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=514)
    public Map<String,Object> unsetBackupAutoGroupedNumGroups(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedNumGroups, "");
        return attrs;
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @return zimbraBackupAutoGroupedThrottled, or false if unset
     */
    @ZAttr(id=515)
    public boolean isBackupAutoGroupedThrottled() {
        return getBooleanAttr(Provisioning.A_zimbraBackupAutoGroupedThrottled, false);
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @param zimbraBackupAutoGroupedThrottled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=515)
    public void setBackupAutoGroupedThrottled(boolean zimbraBackupAutoGroupedThrottled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedThrottled, zimbraBackupAutoGroupedThrottled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @param zimbraBackupAutoGroupedThrottled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=515)
    public Map<String,Object> setBackupAutoGroupedThrottled(boolean zimbraBackupAutoGroupedThrottled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedThrottled, zimbraBackupAutoGroupedThrottled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=515)
    public void unsetBackupAutoGroupedThrottled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedThrottled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=515)
    public Map<String,Object> unsetBackupAutoGroupedThrottled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupAutoGroupedThrottled, "");
        return attrs;
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @return zimbraBackupMinFreeSpace, or "0" if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public String getBackupMinFreeSpace() {
        return getAttr(Provisioning.A_zimbraBackupMinFreeSpace, "0");
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @param zimbraBackupMinFreeSpace new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public void setBackupMinFreeSpace(String zimbraBackupMinFreeSpace) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMinFreeSpace, zimbraBackupMinFreeSpace);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @param zimbraBackupMinFreeSpace new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public Map<String,Object> setBackupMinFreeSpace(String zimbraBackupMinFreeSpace, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMinFreeSpace, zimbraBackupMinFreeSpace);
        return attrs;
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public void unsetBackupMinFreeSpace() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMinFreeSpace, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public Map<String,Object> unsetBackupMinFreeSpace(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMinFreeSpace, "");
        return attrs;
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @return zimbraBackupMode, or ZAttrProvisioning.BackupMode.Standard if unset and/or has invalid value
     */
    @ZAttr(id=512)
    public ZAttrProvisioning.BackupMode getBackupMode() {
        try { String v = getAttr(Provisioning.A_zimbraBackupMode); return v == null ? ZAttrProvisioning.BackupMode.Standard : ZAttrProvisioning.BackupMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.BackupMode.Standard; }
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @return zimbraBackupMode, or "Standard" if unset
     */
    @ZAttr(id=512)
    public String getBackupModeAsString() {
        return getAttr(Provisioning.A_zimbraBackupMode, "Standard");
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zimbraBackupMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=512)
    public void setBackupMode(ZAttrProvisioning.BackupMode zimbraBackupMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMode, zimbraBackupMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zimbraBackupMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=512)
    public Map<String,Object> setBackupMode(ZAttrProvisioning.BackupMode zimbraBackupMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMode, zimbraBackupMode.toString());
        return attrs;
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zimbraBackupMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=512)
    public void setBackupModeAsString(String zimbraBackupMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMode, zimbraBackupMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zimbraBackupMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=512)
    public Map<String,Object> setBackupModeAsString(String zimbraBackupMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMode, zimbraBackupMode);
        return attrs;
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=512)
    public void unsetBackupMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=512)
    public Map<String,Object> unsetBackupMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupMode, "");
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @return zimbraBackupReportEmailRecipients, or empty array if unset
     */
    @ZAttr(id=459)
    public String[] getBackupReportEmailRecipients() {
        return getMultiAttr(Provisioning.A_zimbraBackupReportEmailRecipients);
    }

    /**
     * Backup report email recipients
     *
     * @param zimbraBackupReportEmailRecipients new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void setBackupReportEmailRecipients(String[] zimbraBackupReportEmailRecipients) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailRecipients, zimbraBackupReportEmailRecipients);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param zimbraBackupReportEmailRecipients new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> setBackupReportEmailRecipients(String[] zimbraBackupReportEmailRecipients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailRecipients, zimbraBackupReportEmailRecipients);
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @param zimbraBackupReportEmailRecipients new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void addBackupReportEmailRecipients(String zimbraBackupReportEmailRecipients) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraBackupReportEmailRecipients, zimbraBackupReportEmailRecipients);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param zimbraBackupReportEmailRecipients new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> addBackupReportEmailRecipients(String zimbraBackupReportEmailRecipients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraBackupReportEmailRecipients, zimbraBackupReportEmailRecipients);
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @param zimbraBackupReportEmailRecipients existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void removeBackupReportEmailRecipients(String zimbraBackupReportEmailRecipients) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraBackupReportEmailRecipients, zimbraBackupReportEmailRecipients);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param zimbraBackupReportEmailRecipients existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> removeBackupReportEmailRecipients(String zimbraBackupReportEmailRecipients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraBackupReportEmailRecipients, zimbraBackupReportEmailRecipients);
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void unsetBackupReportEmailRecipients() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailRecipients, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> unsetBackupReportEmailRecipients(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailRecipients, "");
        return attrs;
    }

    /**
     * Backup report email From address
     *
     * @return zimbraBackupReportEmailSender, or null if unset
     */
    @ZAttr(id=460)
    public String getBackupReportEmailSender() {
        return getAttr(Provisioning.A_zimbraBackupReportEmailSender, null);
    }

    /**
     * Backup report email From address
     *
     * @param zimbraBackupReportEmailSender new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=460)
    public void setBackupReportEmailSender(String zimbraBackupReportEmailSender) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSender, zimbraBackupReportEmailSender);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email From address
     *
     * @param zimbraBackupReportEmailSender new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=460)
    public Map<String,Object> setBackupReportEmailSender(String zimbraBackupReportEmailSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSender, zimbraBackupReportEmailSender);
        return attrs;
    }

    /**
     * Backup report email From address
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=460)
    public void unsetBackupReportEmailSender() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSender, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email From address
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=460)
    public Map<String,Object> unsetBackupReportEmailSender(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSender, "");
        return attrs;
    }

    /**
     * Backup report email subject prefix
     *
     * @return zimbraBackupReportEmailSubjectPrefix, or "ZCS Backup Report" if unset
     */
    @ZAttr(id=461)
    public String getBackupReportEmailSubjectPrefix() {
        return getAttr(Provisioning.A_zimbraBackupReportEmailSubjectPrefix, "ZCS Backup Report");
    }

    /**
     * Backup report email subject prefix
     *
     * @param zimbraBackupReportEmailSubjectPrefix new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=461)
    public void setBackupReportEmailSubjectPrefix(String zimbraBackupReportEmailSubjectPrefix) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSubjectPrefix, zimbraBackupReportEmailSubjectPrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email subject prefix
     *
     * @param zimbraBackupReportEmailSubjectPrefix new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=461)
    public Map<String,Object> setBackupReportEmailSubjectPrefix(String zimbraBackupReportEmailSubjectPrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSubjectPrefix, zimbraBackupReportEmailSubjectPrefix);
        return attrs;
    }

    /**
     * Backup report email subject prefix
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=461)
    public void unsetBackupReportEmailSubjectPrefix() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSubjectPrefix, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email subject prefix
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=461)
    public Map<String,Object> unsetBackupReportEmailSubjectPrefix(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupReportEmailSubjectPrefix, "");
        return attrs;
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @return zimbraBackupSkipBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public boolean isBackupSkipBlobs() {
        return getBooleanAttr(Provisioning.A_zimbraBackupSkipBlobs, false);
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @param zimbraBackupSkipBlobs new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public void setBackupSkipBlobs(boolean zimbraBackupSkipBlobs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipBlobs, zimbraBackupSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @param zimbraBackupSkipBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public Map<String,Object> setBackupSkipBlobs(boolean zimbraBackupSkipBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipBlobs, zimbraBackupSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public void unsetBackupSkipBlobs() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipBlobs, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public Map<String,Object> unsetBackupSkipBlobs(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipBlobs, "");
        return attrs;
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @return zimbraBackupSkipHsmBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public boolean isBackupSkipHsmBlobs() {
        return getBooleanAttr(Provisioning.A_zimbraBackupSkipHsmBlobs, false);
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @param zimbraBackupSkipHsmBlobs new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public void setBackupSkipHsmBlobs(boolean zimbraBackupSkipHsmBlobs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipHsmBlobs, zimbraBackupSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @param zimbraBackupSkipHsmBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public Map<String,Object> setBackupSkipHsmBlobs(boolean zimbraBackupSkipHsmBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipHsmBlobs, zimbraBackupSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public void unsetBackupSkipHsmBlobs() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipHsmBlobs, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public Map<String,Object> unsetBackupSkipHsmBlobs(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipHsmBlobs, "");
        return attrs;
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @return zimbraBackupSkipSearchIndex, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public boolean isBackupSkipSearchIndex() {
        return getBooleanAttr(Provisioning.A_zimbraBackupSkipSearchIndex, false);
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @param zimbraBackupSkipSearchIndex new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public void setBackupSkipSearchIndex(boolean zimbraBackupSkipSearchIndex) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipSearchIndex, zimbraBackupSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @param zimbraBackupSkipSearchIndex new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public Map<String,Object> setBackupSkipSearchIndex(boolean zimbraBackupSkipSearchIndex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipSearchIndex, zimbraBackupSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public void unsetBackupSkipSearchIndex() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipSearchIndex, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public Map<String,Object> unsetBackupSkipSearchIndex(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupSkipSearchIndex, "");
        return attrs;
    }

    /**
     * Default backup target path
     *
     * @return zimbraBackupTarget, or "/opt/zimbra/backup" if unset
     */
    @ZAttr(id=458)
    public String getBackupTarget() {
        return getAttr(Provisioning.A_zimbraBackupTarget, "/opt/zimbra/backup");
    }

    /**
     * Default backup target path
     *
     * @param zimbraBackupTarget new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=458)
    public void setBackupTarget(String zimbraBackupTarget) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupTarget, zimbraBackupTarget);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Default backup target path
     *
     * @param zimbraBackupTarget new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=458)
    public Map<String,Object> setBackupTarget(String zimbraBackupTarget, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupTarget, zimbraBackupTarget);
        return attrs;
    }

    /**
     * Default backup target path
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=458)
    public void unsetBackupTarget() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupTarget, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Default backup target path
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=458)
    public Map<String,Object> unsetBackupTarget(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraBackupTarget, "");
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zimbraMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zimbraMailPort.
     *
     * @return zimbraCalendarCalDavClearTextPasswordEnabled, or true if unset
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public boolean isCalendarCalDavClearTextPasswordEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraCalendarCalDavClearTextPasswordEnabled, true);
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zimbraMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zimbraMailPort.
     *
     * @param zimbraCalendarCalDavClearTextPasswordEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public void setCalendarCalDavClearTextPasswordEnabled(boolean zimbraCalendarCalDavClearTextPasswordEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavClearTextPasswordEnabled, zimbraCalendarCalDavClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zimbraMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zimbraMailPort.
     *
     * @param zimbraCalendarCalDavClearTextPasswordEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public Map<String,Object> setCalendarCalDavClearTextPasswordEnabled(boolean zimbraCalendarCalDavClearTextPasswordEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavClearTextPasswordEnabled, zimbraCalendarCalDavClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zimbraMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zimbraMailPort.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public void unsetCalendarCalDavClearTextPasswordEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavClearTextPasswordEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zimbraMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zimbraMailPort.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public Map<String,Object> unsetCalendarCalDavClearTextPasswordEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavClearTextPasswordEnabled, "");
        return attrs;
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @return zimbraCalendarCalDavDefaultCalendarId, or 10 if unset
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public int getCalendarCalDavDefaultCalendarId() {
        return getIntAttr(Provisioning.A_zimbraCalendarCalDavDefaultCalendarId, 10);
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @param zimbraCalendarCalDavDefaultCalendarId new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public void setCalendarCalDavDefaultCalendarId(int zimbraCalendarCalDavDefaultCalendarId) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavDefaultCalendarId, Integer.toString(zimbraCalendarCalDavDefaultCalendarId));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @param zimbraCalendarCalDavDefaultCalendarId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public Map<String,Object> setCalendarCalDavDefaultCalendarId(int zimbraCalendarCalDavDefaultCalendarId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavDefaultCalendarId, Integer.toString(zimbraCalendarCalDavDefaultCalendarId));
        return attrs;
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public void unsetCalendarCalDavDefaultCalendarId() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavDefaultCalendarId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public Map<String,Object> unsetCalendarCalDavDefaultCalendarId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarCalDavDefaultCalendarId, "");
        return attrs;
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zimbraCalendarRecurrenceDailyMaxDays, or 730 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public int getCalendarRecurrenceDailyMaxDays() {
        return getIntAttr(Provisioning.A_zimbraCalendarRecurrenceDailyMaxDays, 730);
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceDailyMaxDays new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public void setCalendarRecurrenceDailyMaxDays(int zimbraCalendarRecurrenceDailyMaxDays) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceDailyMaxDays, Integer.toString(zimbraCalendarRecurrenceDailyMaxDays));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceDailyMaxDays new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public Map<String,Object> setCalendarRecurrenceDailyMaxDays(int zimbraCalendarRecurrenceDailyMaxDays, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceDailyMaxDays, Integer.toString(zimbraCalendarRecurrenceDailyMaxDays));
        return attrs;
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public void unsetCalendarRecurrenceDailyMaxDays() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceDailyMaxDays, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public Map<String,Object> unsetCalendarRecurrenceDailyMaxDays(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceDailyMaxDays, "");
        return attrs;
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @return zimbraCalendarRecurrenceMaxInstances, or 0 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public int getCalendarRecurrenceMaxInstances() {
        return getIntAttr(Provisioning.A_zimbraCalendarRecurrenceMaxInstances, 0);
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceMaxInstances new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public void setCalendarRecurrenceMaxInstances(int zimbraCalendarRecurrenceMaxInstances) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMaxInstances, Integer.toString(zimbraCalendarRecurrenceMaxInstances));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceMaxInstances new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public Map<String,Object> setCalendarRecurrenceMaxInstances(int zimbraCalendarRecurrenceMaxInstances, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMaxInstances, Integer.toString(zimbraCalendarRecurrenceMaxInstances));
        return attrs;
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public void unsetCalendarRecurrenceMaxInstances() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMaxInstances, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public Map<String,Object> unsetCalendarRecurrenceMaxInstances(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMaxInstances, "");
        return attrs;
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zimbraCalendarRecurrenceMonthlyMaxMonths, or 360 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public int getCalendarRecurrenceMonthlyMaxMonths() {
        return getIntAttr(Provisioning.A_zimbraCalendarRecurrenceMonthlyMaxMonths, 360);
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceMonthlyMaxMonths new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public void setCalendarRecurrenceMonthlyMaxMonths(int zimbraCalendarRecurrenceMonthlyMaxMonths) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMonthlyMaxMonths, Integer.toString(zimbraCalendarRecurrenceMonthlyMaxMonths));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceMonthlyMaxMonths new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public Map<String,Object> setCalendarRecurrenceMonthlyMaxMonths(int zimbraCalendarRecurrenceMonthlyMaxMonths, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMonthlyMaxMonths, Integer.toString(zimbraCalendarRecurrenceMonthlyMaxMonths));
        return attrs;
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public void unsetCalendarRecurrenceMonthlyMaxMonths() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMonthlyMaxMonths, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public Map<String,Object> unsetCalendarRecurrenceMonthlyMaxMonths(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceMonthlyMaxMonths, "");
        return attrs;
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @return zimbraCalendarRecurrenceOtherFrequencyMaxYears, or 1 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public int getCalendarRecurrenceOtherFrequencyMaxYears() {
        return getIntAttr(Provisioning.A_zimbraCalendarRecurrenceOtherFrequencyMaxYears, 1);
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @param zimbraCalendarRecurrenceOtherFrequencyMaxYears new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public void setCalendarRecurrenceOtherFrequencyMaxYears(int zimbraCalendarRecurrenceOtherFrequencyMaxYears) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceOtherFrequencyMaxYears, Integer.toString(zimbraCalendarRecurrenceOtherFrequencyMaxYears));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @param zimbraCalendarRecurrenceOtherFrequencyMaxYears new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public Map<String,Object> setCalendarRecurrenceOtherFrequencyMaxYears(int zimbraCalendarRecurrenceOtherFrequencyMaxYears, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceOtherFrequencyMaxYears, Integer.toString(zimbraCalendarRecurrenceOtherFrequencyMaxYears));
        return attrs;
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public void unsetCalendarRecurrenceOtherFrequencyMaxYears() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceOtherFrequencyMaxYears, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public Map<String,Object> unsetCalendarRecurrenceOtherFrequencyMaxYears(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceOtherFrequencyMaxYears, "");
        return attrs;
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zimbraCalendarRecurrenceWeeklyMaxWeeks, or 520 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public int getCalendarRecurrenceWeeklyMaxWeeks() {
        return getIntAttr(Provisioning.A_zimbraCalendarRecurrenceWeeklyMaxWeeks, 520);
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceWeeklyMaxWeeks new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public void setCalendarRecurrenceWeeklyMaxWeeks(int zimbraCalendarRecurrenceWeeklyMaxWeeks) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceWeeklyMaxWeeks, Integer.toString(zimbraCalendarRecurrenceWeeklyMaxWeeks));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceWeeklyMaxWeeks new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public Map<String,Object> setCalendarRecurrenceWeeklyMaxWeeks(int zimbraCalendarRecurrenceWeeklyMaxWeeks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceWeeklyMaxWeeks, Integer.toString(zimbraCalendarRecurrenceWeeklyMaxWeeks));
        return attrs;
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public void unsetCalendarRecurrenceWeeklyMaxWeeks() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceWeeklyMaxWeeks, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public Map<String,Object> unsetCalendarRecurrenceWeeklyMaxWeeks(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceWeeklyMaxWeeks, "");
        return attrs;
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zimbraCalendarRecurrenceYearlyMaxYears, or 100 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public int getCalendarRecurrenceYearlyMaxYears() {
        return getIntAttr(Provisioning.A_zimbraCalendarRecurrenceYearlyMaxYears, 100);
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceYearlyMaxYears new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public void setCalendarRecurrenceYearlyMaxYears(int zimbraCalendarRecurrenceYearlyMaxYears) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceYearlyMaxYears, Integer.toString(zimbraCalendarRecurrenceYearlyMaxYears));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zimbraCalendarRecurrenceYearlyMaxYears new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public Map<String,Object> setCalendarRecurrenceYearlyMaxYears(int zimbraCalendarRecurrenceYearlyMaxYears, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceYearlyMaxYears, Integer.toString(zimbraCalendarRecurrenceYearlyMaxYears));
        return attrs;
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public void unsetCalendarRecurrenceYearlyMaxYears() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceYearlyMaxYears, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public Map<String,Object> unsetCalendarRecurrenceYearlyMaxYears(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCalendarRecurrenceYearlyMaxYears, "");
        return attrs;
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @return zimbraClusterType, or ZAttrProvisioning.ClusterType.none if unset and/or has invalid value
     */
    @ZAttr(id=508)
    public ZAttrProvisioning.ClusterType getClusterType() {
        try { String v = getAttr(Provisioning.A_zimbraClusterType); return v == null ? ZAttrProvisioning.ClusterType.none : ZAttrProvisioning.ClusterType.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.ClusterType.none; }
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @return zimbraClusterType, or "none" if unset
     */
    @ZAttr(id=508)
    public String getClusterTypeAsString() {
        return getAttr(Provisioning.A_zimbraClusterType, "none");
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zimbraClusterType new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=508)
    public void setClusterType(ZAttrProvisioning.ClusterType zimbraClusterType) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraClusterType, zimbraClusterType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zimbraClusterType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=508)
    public Map<String,Object> setClusterType(ZAttrProvisioning.ClusterType zimbraClusterType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraClusterType, zimbraClusterType.toString());
        return attrs;
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zimbraClusterType new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=508)
    public void setClusterTypeAsString(String zimbraClusterType) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraClusterType, zimbraClusterType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zimbraClusterType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=508)
    public Map<String,Object> setClusterTypeAsString(String zimbraClusterType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraClusterType, zimbraClusterType);
        return attrs;
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=508)
    public void unsetClusterType() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraClusterType, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=508)
    public Map<String,Object> unsetClusterType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraClusterType, "");
        return attrs;
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @return zimbraContactHiddenAttributes, or "dn,vcardUID,vcardURL,vcardXProps,member" if unset
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public String getContactHiddenAttributes() {
        return getAttr(Provisioning.A_zimbraContactHiddenAttributes, "dn,vcardUID,vcardURL,vcardXProps,member");
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @param zimbraContactHiddenAttributes new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public void setContactHiddenAttributes(String zimbraContactHiddenAttributes) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactHiddenAttributes, zimbraContactHiddenAttributes);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @param zimbraContactHiddenAttributes new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public Map<String,Object> setContactHiddenAttributes(String zimbraContactHiddenAttributes, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactHiddenAttributes, zimbraContactHiddenAttributes);
        return attrs;
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public void unsetContactHiddenAttributes() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactHiddenAttributes, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public Map<String,Object> unsetContactHiddenAttributes(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactHiddenAttributes, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.6. Deprecated per bug 40081. Orig desc: How
     * often do we refresh contact ranking table from address book and GAL to
     * get friendly name for the email address. Use 0 to disable the
     * refresh.. Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * <p>Use getContactRankingTableRefreshIntervalAsString to access value as a string.
     *
     * @see #getContactRankingTableRefreshIntervalAsString()
     *
     * @return zimbraContactRankingTableRefreshInterval in millseconds, or 604800000 (7d)  if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public long getContactRankingTableRefreshInterval() {
        return getTimeInterval(Provisioning.A_zimbraContactRankingTableRefreshInterval, 604800000L);
    }

    /**
     * Deprecated since: 6.0.6. Deprecated per bug 40081. Orig desc: How
     * often do we refresh contact ranking table from address book and GAL to
     * get friendly name for the email address. Use 0 to disable the
     * refresh.. Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @return zimbraContactRankingTableRefreshInterval, or "7d" if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public String getContactRankingTableRefreshIntervalAsString() {
        return getAttr(Provisioning.A_zimbraContactRankingTableRefreshInterval, "7d");
    }

    /**
     * Deprecated since: 6.0.6. Deprecated per bug 40081. Orig desc: How
     * often do we refresh contact ranking table from address book and GAL to
     * get friendly name for the email address. Use 0 to disable the
     * refresh.. Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @param zimbraContactRankingTableRefreshInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public void setContactRankingTableRefreshInterval(String zimbraContactRankingTableRefreshInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactRankingTableRefreshInterval, zimbraContactRankingTableRefreshInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.6. Deprecated per bug 40081. Orig desc: How
     * often do we refresh contact ranking table from address book and GAL to
     * get friendly name for the email address. Use 0 to disable the
     * refresh.. Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @param zimbraContactRankingTableRefreshInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public Map<String,Object> setContactRankingTableRefreshInterval(String zimbraContactRankingTableRefreshInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactRankingTableRefreshInterval, zimbraContactRankingTableRefreshInterval);
        return attrs;
    }

    /**
     * Deprecated since: 6.0.6. Deprecated per bug 40081. Orig desc: How
     * often do we refresh contact ranking table from address book and GAL to
     * get friendly name for the email address. Use 0 to disable the
     * refresh.. Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public void unsetContactRankingTableRefreshInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactRankingTableRefreshInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.6. Deprecated per bug 40081. Orig desc: How
     * often do we refresh contact ranking table from address book and GAL to
     * get friendly name for the email address. Use 0 to disable the
     * refresh.. Must be in valid duration format: {digits}{time-unit}.
     * digits: 0-9, time-unit: [hmsd]|ms. h - hours, m - minutes, s -
     * seconds, d - days, ms - milliseconds. If time unit is not specified,
     * the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public Map<String,Object> unsetContactRankingTableRefreshInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraContactRankingTableRefreshInterval, "");
        return attrs;
    }

    /**
     * convertd URL
     *
     * @return zimbraConvertdURL, or null if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public String getConvertdURL() {
        return getAttr(Provisioning.A_zimbraConvertdURL, null);
    }

    /**
     * convertd URL
     *
     * @param zimbraConvertdURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public void setConvertdURL(String zimbraConvertdURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraConvertdURL, zimbraConvertdURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * convertd URL
     *
     * @param zimbraConvertdURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public Map<String,Object> setConvertdURL(String zimbraConvertdURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraConvertdURL, zimbraConvertdURL);
        return attrs;
    }

    /**
     * convertd URL
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public void unsetConvertdURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraConvertdURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * convertd URL
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public Map<String,Object> unsetConvertdURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraConvertdURL, "");
        return attrs;
    }

    /**
     * time object was created
     *
     * <p>Use getCreateTimestampAsString to access value as a string.
     *
     * @see #getCreateTimestampAsString()
     *
     * @return zimbraCreateTimestamp as Date, null if unset or unable to parse
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Date getCreateTimestamp() {
        return getGeneralizedTimeAttr(Provisioning.A_zimbraCreateTimestamp, null);
    }

    /**
     * time object was created
     *
     * @return zimbraCreateTimestamp, or null if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public String getCreateTimestampAsString() {
        return getAttr(Provisioning.A_zimbraCreateTimestamp, null);
    }

    /**
     * time object was created
     *
     * @param zimbraCreateTimestamp new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public void setCreateTimestamp(Date zimbraCreateTimestamp) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCreateTimestamp, zimbraCreateTimestamp==null ? "" : DateUtil.toGeneralizedTime(zimbraCreateTimestamp));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time object was created
     *
     * @param zimbraCreateTimestamp new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Map<String,Object> setCreateTimestamp(Date zimbraCreateTimestamp, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCreateTimestamp, zimbraCreateTimestamp==null ? "" : DateUtil.toGeneralizedTime(zimbraCreateTimestamp));
        return attrs;
    }

    /**
     * time object was created
     *
     * @param zimbraCreateTimestamp new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public void setCreateTimestampAsString(String zimbraCreateTimestamp) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCreateTimestamp, zimbraCreateTimestamp);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * time object was created
     *
     * @param zimbraCreateTimestamp new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public Map<String,Object> setCreateTimestampAsString(String zimbraCreateTimestamp, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCreateTimestamp, zimbraCreateTimestamp);
        return attrs;
    }

    /**
     * time object was created
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=790)
    public void unsetCreateTimestamp() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraCreateTimestamp, "");
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
        attrs.put(Provisioning.A_zimbraCreateTimestamp, "");
        return attrs;
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getDatabaseSlowSqlThresholdAsString to access value as a string.
     *
     * @see #getDatabaseSlowSqlThresholdAsString()
     *
     * @return zimbraDatabaseSlowSqlThreshold in millseconds, or 2000 (2s)  if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public long getDatabaseSlowSqlThreshold() {
        return getTimeInterval(Provisioning.A_zimbraDatabaseSlowSqlThreshold, 2000L);
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zimbraDatabaseSlowSqlThreshold, or "2s" if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public String getDatabaseSlowSqlThresholdAsString() {
        return getAttr(Provisioning.A_zimbraDatabaseSlowSqlThreshold, "2s");
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraDatabaseSlowSqlThreshold new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public void setDatabaseSlowSqlThreshold(String zimbraDatabaseSlowSqlThreshold) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraDatabaseSlowSqlThreshold, zimbraDatabaseSlowSqlThreshold);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraDatabaseSlowSqlThreshold new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public Map<String,Object> setDatabaseSlowSqlThreshold(String zimbraDatabaseSlowSqlThreshold, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraDatabaseSlowSqlThreshold, zimbraDatabaseSlowSqlThreshold);
        return attrs;
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public void unsetDatabaseSlowSqlThreshold() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraDatabaseSlowSqlThreshold, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public Map<String,Object> unsetDatabaseSlowSqlThreshold(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraDatabaseSlowSqlThreshold, "");
        return attrs;
    }

    /**
     * interface address on which zimbra extension server should listen; if
     * empty, binds to all interfaces
     *
     * @return zimbraExtensionBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public String getExtensionBindAddress() {
        return getAttr(Provisioning.A_zimbraExtensionBindAddress, null);
    }

    /**
     * interface address on which zimbra extension server should listen; if
     * empty, binds to all interfaces
     *
     * @param zimbraExtensionBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public void setExtensionBindAddress(String zimbraExtensionBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExtensionBindAddress, zimbraExtensionBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which zimbra extension server should listen; if
     * empty, binds to all interfaces
     *
     * @param zimbraExtensionBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public Map<String,Object> setExtensionBindAddress(String zimbraExtensionBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExtensionBindAddress, zimbraExtensionBindAddress);
        return attrs;
    }

    /**
     * interface address on which zimbra extension server should listen; if
     * empty, binds to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public void unsetExtensionBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExtensionBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which zimbra extension server should listen; if
     * empty, binds to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public Map<String,Object> unsetExtensionBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExtensionBindAddress, "");
        return attrs;
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zimbraExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getExternalAccountStatusCheckIntervalAsString to access value as a string.
     *
     * @see #getExternalAccountStatusCheckIntervalAsString()
     *
     * @return zimbraExternalAccountStatusCheckInterval in millseconds, or 86400000 (1d)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public long getExternalAccountStatusCheckInterval() {
        return getTimeInterval(Provisioning.A_zimbraExternalAccountStatusCheckInterval, 86400000L);
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zimbraExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zimbraExternalAccountStatusCheckInterval, or "1d" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public String getExternalAccountStatusCheckIntervalAsString() {
        return getAttr(Provisioning.A_zimbraExternalAccountStatusCheckInterval, "1d");
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zimbraExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraExternalAccountStatusCheckInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public void setExternalAccountStatusCheckInterval(String zimbraExternalAccountStatusCheckInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExternalAccountStatusCheckInterval, zimbraExternalAccountStatusCheckInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zimbraExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraExternalAccountStatusCheckInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public Map<String,Object> setExternalAccountStatusCheckInterval(String zimbraExternalAccountStatusCheckInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExternalAccountStatusCheckInterval, zimbraExternalAccountStatusCheckInterval);
        return attrs;
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zimbraExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public void unsetExternalAccountStatusCheckInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExternalAccountStatusCheckInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zimbraExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public Map<String,Object> unsetExternalAccountStatusCheckInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraExternalAccountStatusCheckInterval, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @return zimbraFileUploadMaxSize, or 10485760 if unset
     */
    @ZAttr(id=227)
    public long getFileUploadMaxSize() {
        return getLongAttr(Provisioning.A_zimbraFileUploadMaxSize, 10485760L);
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @param zimbraFileUploadMaxSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=227)
    public void setFileUploadMaxSize(long zimbraFileUploadMaxSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFileUploadMaxSize, Long.toString(zimbraFileUploadMaxSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @param zimbraFileUploadMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=227)
    public Map<String,Object> setFileUploadMaxSize(long zimbraFileUploadMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFileUploadMaxSize, Long.toString(zimbraFileUploadMaxSize));
        return attrs;
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=227)
    public void unsetFileUploadMaxSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFileUploadMaxSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=227)
    public Map<String,Object> unsetFileUploadMaxSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFileUploadMaxSize, "");
        return attrs;
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zimbra users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getFreebusyPropagationRetryIntervalAsString to access value as a string.
     *
     * @see #getFreebusyPropagationRetryIntervalAsString()
     *
     * @return zimbraFreebusyPropagationRetryInterval in millseconds, or 60000 (1m)  if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public long getFreebusyPropagationRetryInterval() {
        return getTimeInterval(Provisioning.A_zimbraFreebusyPropagationRetryInterval, 60000L);
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zimbra users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraFreebusyPropagationRetryInterval, or "1m" if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public String getFreebusyPropagationRetryIntervalAsString() {
        return getAttr(Provisioning.A_zimbraFreebusyPropagationRetryInterval, "1m");
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zimbra users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraFreebusyPropagationRetryInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public void setFreebusyPropagationRetryInterval(String zimbraFreebusyPropagationRetryInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFreebusyPropagationRetryInterval, zimbraFreebusyPropagationRetryInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zimbra users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraFreebusyPropagationRetryInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public Map<String,Object> setFreebusyPropagationRetryInterval(String zimbraFreebusyPropagationRetryInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFreebusyPropagationRetryInterval, zimbraFreebusyPropagationRetryInterval);
        return attrs;
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zimbra users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public void unsetFreebusyPropagationRetryInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFreebusyPropagationRetryInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zimbra users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public Map<String,Object> unsetFreebusyPropagationRetryInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraFreebusyPropagationRetryInterval, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zimbraHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * <p>Use getHsmAgeAsString to access value as a string.
     *
     * @see #getHsmAgeAsString()
     *
     * @return zimbraHsmAge in millseconds, or 2592000000 (30d)  if unset
     */
    @ZAttr(id=8)
    public long getHsmAge() {
        return getTimeInterval(Provisioning.A_zimbraHsmAge, 2592000000L);
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zimbraHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @return zimbraHsmAge, or "30d" if unset
     */
    @ZAttr(id=8)
    public String getHsmAgeAsString() {
        return getAttr(Provisioning.A_zimbraHsmAge, "30d");
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zimbraHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zimbraHsmAge new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=8)
    public void setHsmAge(String zimbraHsmAge) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmAge, zimbraHsmAge);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zimbraHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zimbraHsmAge new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=8)
    public Map<String,Object> setHsmAge(String zimbraHsmAge, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmAge, zimbraHsmAge);
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zimbraHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=8)
    public void unsetHsmAge() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmAge, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zimbraHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=8)
    public Map<String,Object> unsetHsmAge(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmAge, "");
        return attrs;
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @return zimbraHsmBatchSize, or 10000 if unset
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public int getHsmBatchSize() {
        return getIntAttr(Provisioning.A_zimbraHsmBatchSize, 10000);
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @param zimbraHsmBatchSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public void setHsmBatchSize(int zimbraHsmBatchSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmBatchSize, Integer.toString(zimbraHsmBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @param zimbraHsmBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public Map<String,Object> setHsmBatchSize(int zimbraHsmBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmBatchSize, Integer.toString(zimbraHsmBatchSize));
        return attrs;
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public void unsetHsmBatchSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmBatchSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public Map<String,Object> unsetHsmBatchSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmBatchSize, "");
        return attrs;
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zimbraHsmPolicy.
     *
     * @return zimbraHsmMovePreviousRevisions, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public boolean isHsmMovePreviousRevisions() {
        return getBooleanAttr(Provisioning.A_zimbraHsmMovePreviousRevisions, false);
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zimbraHsmPolicy.
     *
     * @param zimbraHsmMovePreviousRevisions new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public void setHsmMovePreviousRevisions(boolean zimbraHsmMovePreviousRevisions) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmMovePreviousRevisions, zimbraHsmMovePreviousRevisions ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zimbraHsmPolicy.
     *
     * @param zimbraHsmMovePreviousRevisions new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public Map<String,Object> setHsmMovePreviousRevisions(boolean zimbraHsmMovePreviousRevisions, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmMovePreviousRevisions, zimbraHsmMovePreviousRevisions ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zimbraHsmPolicy.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public void unsetHsmMovePreviousRevisions() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmMovePreviousRevisions, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zimbraHsmPolicy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public Map<String,Object> unsetHsmMovePreviousRevisions(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmMovePreviousRevisions, "");
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @return zimbraHsmPolicy, or empty array if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public String[] getHsmPolicy() {
        String[] value = getMultiAttr(Provisioning.A_zimbraHsmPolicy); return value.length > 0 ? value : new String[] {"message,document:before:-30days"};
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zimbraHsmPolicy new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void setHsmPolicy(String[] zimbraHsmPolicy) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmPolicy, zimbraHsmPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zimbraHsmPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> setHsmPolicy(String[] zimbraHsmPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmPolicy, zimbraHsmPolicy);
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zimbraHsmPolicy new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void addHsmPolicy(String zimbraHsmPolicy) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraHsmPolicy, zimbraHsmPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zimbraHsmPolicy new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> addHsmPolicy(String zimbraHsmPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraHsmPolicy, zimbraHsmPolicy);
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zimbraHsmPolicy existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void removeHsmPolicy(String zimbraHsmPolicy) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraHsmPolicy, zimbraHsmPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zimbraHsmPolicy existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> removeHsmPolicy(String zimbraHsmPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraHsmPolicy, zimbraHsmPolicy);
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void unsetHsmPolicy() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmPolicy, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> unsetHsmPolicy(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHsmPolicy, "");
        return attrs;
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @return zimbraHttpConnectorMaxIdleTimeMillis, or 60000 if unset
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public int getHttpConnectorMaxIdleTimeMillis() {
        return getIntAttr(Provisioning.A_zimbraHttpConnectorMaxIdleTimeMillis, 60000);
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @param zimbraHttpConnectorMaxIdleTimeMillis new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public void setHttpConnectorMaxIdleTimeMillis(int zimbraHttpConnectorMaxIdleTimeMillis) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpConnectorMaxIdleTimeMillis, Integer.toString(zimbraHttpConnectorMaxIdleTimeMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @param zimbraHttpConnectorMaxIdleTimeMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public Map<String,Object> setHttpConnectorMaxIdleTimeMillis(int zimbraHttpConnectorMaxIdleTimeMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpConnectorMaxIdleTimeMillis, Integer.toString(zimbraHttpConnectorMaxIdleTimeMillis));
        return attrs;
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public void unsetHttpConnectorMaxIdleTimeMillis() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpConnectorMaxIdleTimeMillis, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public Map<String,Object> unsetHttpConnectorMaxIdleTimeMillis(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpConnectorMaxIdleTimeMillis, "");
        return attrs;
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @return zimbraHttpDebugHandlerEnabled, or true if unset
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public boolean isHttpDebugHandlerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraHttpDebugHandlerEnabled, true);
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @param zimbraHttpDebugHandlerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public void setHttpDebugHandlerEnabled(boolean zimbraHttpDebugHandlerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDebugHandlerEnabled, zimbraHttpDebugHandlerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @param zimbraHttpDebugHandlerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public Map<String,Object> setHttpDebugHandlerEnabled(boolean zimbraHttpDebugHandlerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDebugHandlerEnabled, zimbraHttpDebugHandlerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public void unsetHttpDebugHandlerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDebugHandlerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public Map<String,Object> unsetHttpDebugHandlerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDebugHandlerEnabled, "");
        return attrs;
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @return zimbraHttpDosFilterDelayMillis, or -1 if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public int getHttpDosFilterDelayMillis() {
        return getIntAttr(Provisioning.A_zimbraHttpDosFilterDelayMillis, -1);
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @param zimbraHttpDosFilterDelayMillis new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public void setHttpDosFilterDelayMillis(int zimbraHttpDosFilterDelayMillis) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterDelayMillis, Integer.toString(zimbraHttpDosFilterDelayMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @param zimbraHttpDosFilterDelayMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public Map<String,Object> setHttpDosFilterDelayMillis(int zimbraHttpDosFilterDelayMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterDelayMillis, Integer.toString(zimbraHttpDosFilterDelayMillis));
        return attrs;
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public void unsetHttpDosFilterDelayMillis() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterDelayMillis, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public Map<String,Object> unsetHttpDosFilterDelayMillis(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterDelayMillis, "");
        return attrs;
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @return zimbraHttpDosFilterMaxRequestsPerSec, or 30 if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public int getHttpDosFilterMaxRequestsPerSec() {
        return getIntAttr(Provisioning.A_zimbraHttpDosFilterMaxRequestsPerSec, 30);
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @param zimbraHttpDosFilterMaxRequestsPerSec new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public void setHttpDosFilterMaxRequestsPerSec(int zimbraHttpDosFilterMaxRequestsPerSec) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterMaxRequestsPerSec, Integer.toString(zimbraHttpDosFilterMaxRequestsPerSec));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @param zimbraHttpDosFilterMaxRequestsPerSec new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public Map<String,Object> setHttpDosFilterMaxRequestsPerSec(int zimbraHttpDosFilterMaxRequestsPerSec, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterMaxRequestsPerSec, Integer.toString(zimbraHttpDosFilterMaxRequestsPerSec));
        return attrs;
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public void unsetHttpDosFilterMaxRequestsPerSec() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterMaxRequestsPerSec, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public Map<String,Object> unsetHttpDosFilterMaxRequestsPerSec(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpDosFilterMaxRequestsPerSec, "");
        return attrs;
    }

    /**
     * number of http handler threads
     *
     * @return zimbraHttpNumThreads, or 250 if unset
     */
    @ZAttr(id=518)
    public int getHttpNumThreads() {
        return getIntAttr(Provisioning.A_zimbraHttpNumThreads, 250);
    }

    /**
     * number of http handler threads
     *
     * @param zimbraHttpNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=518)
    public void setHttpNumThreads(int zimbraHttpNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpNumThreads, Integer.toString(zimbraHttpNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of http handler threads
     *
     * @param zimbraHttpNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=518)
    public Map<String,Object> setHttpNumThreads(int zimbraHttpNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpNumThreads, Integer.toString(zimbraHttpNumThreads));
        return attrs;
    }

    /**
     * number of http handler threads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=518)
    public void unsetHttpNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of http handler threads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=518)
    public Map<String,Object> unsetHttpNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpNumThreads, "");
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @return zimbraHttpProxyURL, or empty array if unset
     */
    @ZAttr(id=388)
    public String[] getHttpProxyURL() {
        return getMultiAttr(Provisioning.A_zimbraHttpProxyURL);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zimbraHttpProxyURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void setHttpProxyURL(String[] zimbraHttpProxyURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpProxyURL, zimbraHttpProxyURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zimbraHttpProxyURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> setHttpProxyURL(String[] zimbraHttpProxyURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpProxyURL, zimbraHttpProxyURL);
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zimbraHttpProxyURL new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void addHttpProxyURL(String zimbraHttpProxyURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraHttpProxyURL, zimbraHttpProxyURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zimbraHttpProxyURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> addHttpProxyURL(String zimbraHttpProxyURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraHttpProxyURL, zimbraHttpProxyURL);
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zimbraHttpProxyURL existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void removeHttpProxyURL(String zimbraHttpProxyURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraHttpProxyURL, zimbraHttpProxyURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zimbraHttpProxyURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> removeHttpProxyURL(String zimbraHttpProxyURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraHttpProxyURL, zimbraHttpProxyURL);
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void unsetHttpProxyURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpProxyURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> unsetHttpProxyURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpProxyURL, "");
        return attrs;
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @return zimbraHttpSSLNumThreads, or 50 if unset
     */
    @ZAttr(id=519)
    public int getHttpSSLNumThreads() {
        return getIntAttr(Provisioning.A_zimbraHttpSSLNumThreads, 50);
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @param zimbraHttpSSLNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=519)
    public void setHttpSSLNumThreads(int zimbraHttpSSLNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpSSLNumThreads, Integer.toString(zimbraHttpSSLNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @param zimbraHttpSSLNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=519)
    public Map<String,Object> setHttpSSLNumThreads(int zimbraHttpSSLNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpSSLNumThreads, Integer.toString(zimbraHttpSSLNumThreads));
        return attrs;
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=519)
    public void unsetHttpSSLNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpSSLNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=519)
    public Map<String,Object> unsetHttpSSLNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpSSLNumThreads, "");
        return attrs;
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @return zimbraHttpThreadPoolMaxIdleTimeMillis, or 10000 if unset
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public int getHttpThreadPoolMaxIdleTimeMillis() {
        return getIntAttr(Provisioning.A_zimbraHttpThreadPoolMaxIdleTimeMillis, 10000);
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @param zimbraHttpThreadPoolMaxIdleTimeMillis new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public void setHttpThreadPoolMaxIdleTimeMillis(int zimbraHttpThreadPoolMaxIdleTimeMillis) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThreadPoolMaxIdleTimeMillis, Integer.toString(zimbraHttpThreadPoolMaxIdleTimeMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @param zimbraHttpThreadPoolMaxIdleTimeMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public Map<String,Object> setHttpThreadPoolMaxIdleTimeMillis(int zimbraHttpThreadPoolMaxIdleTimeMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThreadPoolMaxIdleTimeMillis, Integer.toString(zimbraHttpThreadPoolMaxIdleTimeMillis));
        return attrs;
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public void unsetHttpThreadPoolMaxIdleTimeMillis() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThreadPoolMaxIdleTimeMillis, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public Map<String,Object> unsetHttpThreadPoolMaxIdleTimeMillis(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThreadPoolMaxIdleTimeMillis, "");
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @return zimbraHttpThrottleSafeIPs, or empty array if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public String[] getHttpThrottleSafeIPs() {
        return getMultiAttr(Provisioning.A_zimbraHttpThrottleSafeIPs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zimbraHttpThrottleSafeIPs new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void setHttpThrottleSafeIPs(String[] zimbraHttpThrottleSafeIPs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThrottleSafeIPs, zimbraHttpThrottleSafeIPs);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zimbraHttpThrottleSafeIPs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> setHttpThrottleSafeIPs(String[] zimbraHttpThrottleSafeIPs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThrottleSafeIPs, zimbraHttpThrottleSafeIPs);
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zimbraHttpThrottleSafeIPs new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void addHttpThrottleSafeIPs(String zimbraHttpThrottleSafeIPs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraHttpThrottleSafeIPs, zimbraHttpThrottleSafeIPs);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zimbraHttpThrottleSafeIPs new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> addHttpThrottleSafeIPs(String zimbraHttpThrottleSafeIPs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraHttpThrottleSafeIPs, zimbraHttpThrottleSafeIPs);
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zimbraHttpThrottleSafeIPs existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void removeHttpThrottleSafeIPs(String zimbraHttpThrottleSafeIPs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraHttpThrottleSafeIPs, zimbraHttpThrottleSafeIPs);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zimbraHttpThrottleSafeIPs existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> removeHttpThrottleSafeIPs(String zimbraHttpThrottleSafeIPs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraHttpThrottleSafeIPs, zimbraHttpThrottleSafeIPs);
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void unsetHttpThrottleSafeIPs() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThrottleSafeIPs, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> unsetHttpThrottleSafeIPs(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraHttpThrottleSafeIPs, "");
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @return zimbraIMBindAddress, or empty array if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public String[] getIMBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraIMBindAddress);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zimbraIMBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void setIMBindAddress(String[] zimbraIMBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIMBindAddress, zimbraIMBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zimbraIMBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> setIMBindAddress(String[] zimbraIMBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIMBindAddress, zimbraIMBindAddress);
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zimbraIMBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void addIMBindAddress(String zimbraIMBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraIMBindAddress, zimbraIMBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zimbraIMBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> addIMBindAddress(String zimbraIMBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraIMBindAddress, zimbraIMBindAddress);
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zimbraIMBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void removeIMBindAddress(String zimbraIMBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraIMBindAddress, zimbraIMBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zimbraIMBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> removeIMBindAddress(String zimbraIMBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraIMBindAddress, zimbraIMBindAddress);
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void unsetIMBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIMBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> unsetIMBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIMBindAddress, "");
        return attrs;
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @return zimbraIPMode, or ZAttrProvisioning.IPMode.ipv4 if unset and/or has invalid value
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public ZAttrProvisioning.IPMode getIPMode() {
        try { String v = getAttr(Provisioning.A_zimbraIPMode); return v == null ? ZAttrProvisioning.IPMode.ipv4 : ZAttrProvisioning.IPMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.IPMode.ipv4; }
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @return zimbraIPMode, or "ipv4" if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public String getIPModeAsString() {
        return getAttr(Provisioning.A_zimbraIPMode, "ipv4");
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zimbraIPMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public void setIPMode(ZAttrProvisioning.IPMode zimbraIPMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIPMode, zimbraIPMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zimbraIPMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public Map<String,Object> setIPMode(ZAttrProvisioning.IPMode zimbraIPMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIPMode, zimbraIPMode.toString());
        return attrs;
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zimbraIPMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public void setIPModeAsString(String zimbraIPMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIPMode, zimbraIPMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zimbraIPMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public Map<String,Object> setIPModeAsString(String zimbraIPMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIPMode, zimbraIPMode);
        return attrs;
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public void unsetIPMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIPMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public Map<String,Object> unsetIPMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIPMode, "");
        return attrs;
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @return zimbraId, or null if unset
     */
    @ZAttr(id=1)
    public String getId() {
        return getAttr(Provisioning.A_zimbraId, null);
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @param zimbraId new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=1)
    public void setId(String zimbraId) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraId, zimbraId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @param zimbraId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=1)
    public Map<String,Object> setId(String zimbraId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraId, zimbraId);
        return attrs;
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=1)
    public void unsetId() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Zimbra Systems Unique ID
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=1)
    public Map<String,Object> unsetId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraId, "");
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @return zimbraImapAdvertisedName, or null if unset
     */
    @ZAttr(id=178)
    public String getImapAdvertisedName() {
        return getAttr(Provisioning.A_zimbraImapAdvertisedName, null);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zimbraImapAdvertisedName new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=178)
    public void setImapAdvertisedName(String zimbraImapAdvertisedName) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapAdvertisedName, zimbraImapAdvertisedName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zimbraImapAdvertisedName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=178)
    public Map<String,Object> setImapAdvertisedName(String zimbraImapAdvertisedName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapAdvertisedName, zimbraImapAdvertisedName);
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=178)
    public void unsetImapAdvertisedName() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapAdvertisedName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=178)
    public Map<String,Object> unsetImapAdvertisedName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapAdvertisedName, "");
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraImapBindAddress, or empty array if unset
     */
    @ZAttr(id=179)
    public String[] getImapBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraImapBindAddress);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void setImapBindAddress(String[] zimbraImapBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindAddress, zimbraImapBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> setImapBindAddress(String[] zimbraImapBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindAddress, zimbraImapBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void addImapBindAddress(String zimbraImapBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapBindAddress, zimbraImapBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> addImapBindAddress(String zimbraImapBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapBindAddress, zimbraImapBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void removeImapBindAddress(String zimbraImapBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapBindAddress, zimbraImapBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> removeImapBindAddress(String zimbraImapBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapBindAddress, zimbraImapBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void unsetImapBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> unsetImapBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zimbraImapBindOnStartup, or true if unset
     */
    @ZAttr(id=268)
    public boolean isImapBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zimbraImapBindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraImapBindOnStartup new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=268)
    public void setImapBindOnStartup(boolean zimbraImapBindOnStartup) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindOnStartup, zimbraImapBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraImapBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=268)
    public Map<String,Object> setImapBindOnStartup(boolean zimbraImapBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindOnStartup, zimbraImapBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=268)
    public void unsetImapBindOnStartup() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindOnStartup, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=268)
    public Map<String,Object> unsetImapBindOnStartup(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which IMAP server should listen
     *
     * <p>Use getImapBindPortAsString to access value as a string.
     *
     * @see #getImapBindPortAsString()
     *
     * @return zimbraImapBindPort, or 7143 if unset
     */
    @ZAttr(id=180)
    public int getImapBindPort() {
        return getIntAttr(Provisioning.A_zimbraImapBindPort, 7143);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @return zimbraImapBindPort, or "7143" if unset
     */
    @ZAttr(id=180)
    public String getImapBindPortAsString() {
        return getAttr(Provisioning.A_zimbraImapBindPort, "7143");
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zimbraImapBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=180)
    public void setImapBindPort(int zimbraImapBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindPort, Integer.toString(zimbraImapBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zimbraImapBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=180)
    public Map<String,Object> setImapBindPort(int zimbraImapBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindPort, Integer.toString(zimbraImapBindPort));
        return attrs;
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zimbraImapBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=180)
    public void setImapBindPortAsString(String zimbraImapBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindPort, zimbraImapBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zimbraImapBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=180)
    public Map<String,Object> setImapBindPortAsString(String zimbraImapBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindPort, zimbraImapBindPort);
        return attrs;
    }

    /**
     * port number on which IMAP server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=180)
    public void unsetImapBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=180)
    public Map<String,Object> unsetImapBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapBindPort, "");
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @return zimbraImapCleartextLoginEnabled, or false if unset
     */
    @ZAttr(id=185)
    public boolean isImapCleartextLoginEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraImapCleartextLoginEnabled, false);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zimbraImapCleartextLoginEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=185)
    public void setImapCleartextLoginEnabled(boolean zimbraImapCleartextLoginEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapCleartextLoginEnabled, zimbraImapCleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zimbraImapCleartextLoginEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=185)
    public Map<String,Object> setImapCleartextLoginEnabled(boolean zimbraImapCleartextLoginEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapCleartextLoginEnabled, zimbraImapCleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=185)
    public void unsetImapCleartextLoginEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapCleartextLoginEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=185)
    public Map<String,Object> unsetImapCleartextLoginEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapCleartextLoginEnabled, "");
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @return zimbraImapDisabledCapability, or empty array if unset
     */
    @ZAttr(id=443)
    public String[] getImapDisabledCapability() {
        return getMultiAttr(Provisioning.A_zimbraImapDisabledCapability);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zimbraImapDisabledCapability new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void setImapDisabledCapability(String[] zimbraImapDisabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapDisabledCapability, zimbraImapDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zimbraImapDisabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> setImapDisabledCapability(String[] zimbraImapDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapDisabledCapability, zimbraImapDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zimbraImapDisabledCapability new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void addImapDisabledCapability(String zimbraImapDisabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapDisabledCapability, zimbraImapDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zimbraImapDisabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> addImapDisabledCapability(String zimbraImapDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapDisabledCapability, zimbraImapDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zimbraImapDisabledCapability existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void removeImapDisabledCapability(String zimbraImapDisabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapDisabledCapability, zimbraImapDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zimbraImapDisabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> removeImapDisabledCapability(String zimbraImapDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapDisabledCapability, zimbraImapDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void unsetImapDisabledCapability() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapDisabledCapability, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> unsetImapDisabledCapability(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapDisabledCapability, "");
        return attrs;
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @return zimbraImapExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public boolean isImapExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zimbraImapExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @param zimbraImapExposeVersionOnBanner new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public void setImapExposeVersionOnBanner(boolean zimbraImapExposeVersionOnBanner) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapExposeVersionOnBanner, zimbraImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @param zimbraImapExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public Map<String,Object> setImapExposeVersionOnBanner(boolean zimbraImapExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapExposeVersionOnBanner, zimbraImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public void unsetImapExposeVersionOnBanner() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapExposeVersionOnBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public Map<String,Object> unsetImapExposeVersionOnBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @return zimbraImapMaxConnections, or 200 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public int getImapMaxConnections() {
        return getIntAttr(Provisioning.A_zimbraImapMaxConnections, 200);
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zimbraImapMaxConnections new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public void setImapMaxConnections(int zimbraImapMaxConnections) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxConnections, Integer.toString(zimbraImapMaxConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zimbraImapMaxConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public Map<String,Object> setImapMaxConnections(int zimbraImapMaxConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxConnections, Integer.toString(zimbraImapMaxConnections));
        return attrs;
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public void unsetImapMaxConnections() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxConnections, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public Map<String,Object> unsetImapMaxConnections(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxConnections, "");
        return attrs;
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @return zimbraImapMaxRequestSize, or 10240 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public int getImapMaxRequestSize() {
        return getIntAttr(Provisioning.A_zimbraImapMaxRequestSize, 10240);
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @param zimbraImapMaxRequestSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public void setImapMaxRequestSize(int zimbraImapMaxRequestSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxRequestSize, Integer.toString(zimbraImapMaxRequestSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @param zimbraImapMaxRequestSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public Map<String,Object> setImapMaxRequestSize(int zimbraImapMaxRequestSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxRequestSize, Integer.toString(zimbraImapMaxRequestSize));
        return attrs;
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public void unsetImapMaxRequestSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxRequestSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public Map<String,Object> unsetImapMaxRequestSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapMaxRequestSize, "");
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @return zimbraImapNumThreads, or 200 if unset
     */
    @ZAttr(id=181)
    public int getImapNumThreads() {
        return getIntAttr(Provisioning.A_zimbraImapNumThreads, 200);
    }

    /**
     * number of handler threads
     *
     * @param zimbraImapNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=181)
    public void setImapNumThreads(int zimbraImapNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapNumThreads, Integer.toString(zimbraImapNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads
     *
     * @param zimbraImapNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=181)
    public Map<String,Object> setImapNumThreads(int zimbraImapNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapNumThreads, Integer.toString(zimbraImapNumThreads));
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=181)
    public void unsetImapNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=181)
    public Map<String,Object> unsetImapNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapNumThreads, "");
        return attrs;
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * <p>Use getImapProxyBindPortAsString to access value as a string.
     *
     * @see #getImapProxyBindPortAsString()
     *
     * @return zimbraImapProxyBindPort, or 143 if unset
     */
    @ZAttr(id=348)
    public int getImapProxyBindPort() {
        return getIntAttr(Provisioning.A_zimbraImapProxyBindPort, 143);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @return zimbraImapProxyBindPort, or "143" if unset
     */
    @ZAttr(id=348)
    public String getImapProxyBindPortAsString() {
        return getAttr(Provisioning.A_zimbraImapProxyBindPort, "143");
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zimbraImapProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=348)
    public void setImapProxyBindPort(int zimbraImapProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapProxyBindPort, Integer.toString(zimbraImapProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zimbraImapProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=348)
    public Map<String,Object> setImapProxyBindPort(int zimbraImapProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapProxyBindPort, Integer.toString(zimbraImapProxyBindPort));
        return attrs;
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zimbraImapProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=348)
    public void setImapProxyBindPortAsString(String zimbraImapProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapProxyBindPort, zimbraImapProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zimbraImapProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=348)
    public Map<String,Object> setImapProxyBindPortAsString(String zimbraImapProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapProxyBindPort, zimbraImapProxyBindPort);
        return attrs;
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=348)
    public void unsetImapProxyBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapProxyBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=348)
    public Map<String,Object> unsetImapProxyBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapProxyBindPort, "");
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraImapSSLBindAddress, or empty array if unset
     */
    @ZAttr(id=182)
    public String[] getImapSSLBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraImapSSLBindAddress);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapSSLBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void setImapSSLBindAddress(String[] zimbraImapSSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindAddress, zimbraImapSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapSSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> setImapSSLBindAddress(String[] zimbraImapSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindAddress, zimbraImapSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapSSLBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void addImapSSLBindAddress(String zimbraImapSSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapSSLBindAddress, zimbraImapSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapSSLBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> addImapSSLBindAddress(String zimbraImapSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapSSLBindAddress, zimbraImapSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapSSLBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void removeImapSSLBindAddress(String zimbraImapSSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapSSLBindAddress, zimbraImapSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraImapSSLBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> removeImapSSLBindAddress(String zimbraImapSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapSSLBindAddress, zimbraImapSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void unsetImapSSLBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> unsetImapSSLBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zimbraImapSSLBindOnStartup, or true if unset
     */
    @ZAttr(id=269)
    public boolean isImapSSLBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zimbraImapSSLBindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraImapSSLBindOnStartup new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=269)
    public void setImapSSLBindOnStartup(boolean zimbraImapSSLBindOnStartup) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindOnStartup, zimbraImapSSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraImapSSLBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=269)
    public Map<String,Object> setImapSSLBindOnStartup(boolean zimbraImapSSLBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindOnStartup, zimbraImapSSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=269)
    public void unsetImapSSLBindOnStartup() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindOnStartup, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=269)
    public Map<String,Object> unsetImapSSLBindOnStartup(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * <p>Use getImapSSLBindPortAsString to access value as a string.
     *
     * @see #getImapSSLBindPortAsString()
     *
     * @return zimbraImapSSLBindPort, or 7993 if unset
     */
    @ZAttr(id=183)
    public int getImapSSLBindPort() {
        return getIntAttr(Provisioning.A_zimbraImapSSLBindPort, 7993);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @return zimbraImapSSLBindPort, or "7993" if unset
     */
    @ZAttr(id=183)
    public String getImapSSLBindPortAsString() {
        return getAttr(Provisioning.A_zimbraImapSSLBindPort, "7993");
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zimbraImapSSLBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=183)
    public void setImapSSLBindPort(int zimbraImapSSLBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindPort, Integer.toString(zimbraImapSSLBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zimbraImapSSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=183)
    public Map<String,Object> setImapSSLBindPort(int zimbraImapSSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindPort, Integer.toString(zimbraImapSSLBindPort));
        return attrs;
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zimbraImapSSLBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=183)
    public void setImapSSLBindPortAsString(String zimbraImapSSLBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindPort, zimbraImapSSLBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zimbraImapSSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=183)
    public Map<String,Object> setImapSSLBindPortAsString(String zimbraImapSSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindPort, zimbraImapSSLBindPort);
        return attrs;
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=183)
    public void unsetImapSSLBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=183)
    public Map<String,Object> unsetImapSSLBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLBindPort, "");
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @return zimbraImapSSLDisabledCapability, or empty array if unset
     */
    @ZAttr(id=444)
    public String[] getImapSSLDisabledCapability() {
        return getMultiAttr(Provisioning.A_zimbraImapSSLDisabledCapability);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zimbraImapSSLDisabledCapability new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void setImapSSLDisabledCapability(String[] zimbraImapSSLDisabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLDisabledCapability, zimbraImapSSLDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zimbraImapSSLDisabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> setImapSSLDisabledCapability(String[] zimbraImapSSLDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLDisabledCapability, zimbraImapSSLDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zimbraImapSSLDisabledCapability new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void addImapSSLDisabledCapability(String zimbraImapSSLDisabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapSSLDisabledCapability, zimbraImapSSLDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zimbraImapSSLDisabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> addImapSSLDisabledCapability(String zimbraImapSSLDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraImapSSLDisabledCapability, zimbraImapSSLDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zimbraImapSSLDisabledCapability existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void removeImapSSLDisabledCapability(String zimbraImapSSLDisabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapSSLDisabledCapability, zimbraImapSSLDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zimbraImapSSLDisabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> removeImapSSLDisabledCapability(String zimbraImapSSLDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraImapSSLDisabledCapability, zimbraImapSSLDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void unsetImapSSLDisabledCapability() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLDisabledCapability, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> unsetImapSSLDisabledCapability(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLDisabledCapability, "");
        return attrs;
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * <p>Use getImapSSLProxyBindPortAsString to access value as a string.
     *
     * @see #getImapSSLProxyBindPortAsString()
     *
     * @return zimbraImapSSLProxyBindPort, or 993 if unset
     */
    @ZAttr(id=349)
    public int getImapSSLProxyBindPort() {
        return getIntAttr(Provisioning.A_zimbraImapSSLProxyBindPort, 993);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @return zimbraImapSSLProxyBindPort, or "993" if unset
     */
    @ZAttr(id=349)
    public String getImapSSLProxyBindPortAsString() {
        return getAttr(Provisioning.A_zimbraImapSSLProxyBindPort, "993");
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zimbraImapSSLProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=349)
    public void setImapSSLProxyBindPort(int zimbraImapSSLProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLProxyBindPort, Integer.toString(zimbraImapSSLProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zimbraImapSSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=349)
    public Map<String,Object> setImapSSLProxyBindPort(int zimbraImapSSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLProxyBindPort, Integer.toString(zimbraImapSSLProxyBindPort));
        return attrs;
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zimbraImapSSLProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=349)
    public void setImapSSLProxyBindPortAsString(String zimbraImapSSLProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLProxyBindPort, zimbraImapSSLProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zimbraImapSSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=349)
    public Map<String,Object> setImapSSLProxyBindPortAsString(String zimbraImapSSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLProxyBindPort, zimbraImapSSLProxyBindPort);
        return attrs;
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=349)
    public void unsetImapSSLProxyBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLProxyBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=349)
    public Map<String,Object> unsetImapSSLProxyBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLProxyBindPort, "");
        return attrs;
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @return zimbraImapSSLServerEnabled, or true if unset
     */
    @ZAttr(id=184)
    public boolean isImapSSLServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraImapSSLServerEnabled, true);
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @param zimbraImapSSLServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=184)
    public void setImapSSLServerEnabled(boolean zimbraImapSSLServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLServerEnabled, zimbraImapSSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @param zimbraImapSSLServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=184)
    public Map<String,Object> setImapSSLServerEnabled(boolean zimbraImapSSLServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLServerEnabled, zimbraImapSSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=184)
    public void unsetImapSSLServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=184)
    public Map<String,Object> unsetImapSSLServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSSLServerEnabled, "");
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @return zimbraImapSaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public boolean isImapSaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraImapSaslGssapiEnabled, false);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @param zimbraImapSaslGssapiEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public void setImapSaslGssapiEnabled(boolean zimbraImapSaslGssapiEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSaslGssapiEnabled, zimbraImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @param zimbraImapSaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public Map<String,Object> setImapSaslGssapiEnabled(boolean zimbraImapSaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSaslGssapiEnabled, zimbraImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public void unsetImapSaslGssapiEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSaslGssapiEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public Map<String,Object> unsetImapSaslGssapiEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapSaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @return zimbraImapServerEnabled, or true if unset
     */
    @ZAttr(id=176)
    public boolean isImapServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraImapServerEnabled, true);
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @param zimbraImapServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=176)
    public void setImapServerEnabled(boolean zimbraImapServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapServerEnabled, zimbraImapServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @param zimbraImapServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=176)
    public Map<String,Object> setImapServerEnabled(boolean zimbraImapServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapServerEnabled, zimbraImapServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=176)
    public void unsetImapServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=176)
    public Map<String,Object> unsetImapServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapServerEnabled, "");
        return attrs;
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @return zimbraImapShutdownGraceSeconds, or 10 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public int getImapShutdownGraceSeconds() {
        return getIntAttr(Provisioning.A_zimbraImapShutdownGraceSeconds, 10);
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @param zimbraImapShutdownGraceSeconds new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public void setImapShutdownGraceSeconds(int zimbraImapShutdownGraceSeconds) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapShutdownGraceSeconds, Integer.toString(zimbraImapShutdownGraceSeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @param zimbraImapShutdownGraceSeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public Map<String,Object> setImapShutdownGraceSeconds(int zimbraImapShutdownGraceSeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapShutdownGraceSeconds, Integer.toString(zimbraImapShutdownGraceSeconds));
        return attrs;
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public void unsetImapShutdownGraceSeconds() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapShutdownGraceSeconds, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public Map<String,Object> unsetImapShutdownGraceSeconds(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraImapShutdownGraceSeconds, "");
        return attrs;
    }

    /**
     * true if this server is the monitor host
     *
     * @return zimbraIsMonitorHost, or false if unset
     */
    @ZAttr(id=132)
    public boolean isIsMonitorHost() {
        return getBooleanAttr(Provisioning.A_zimbraIsMonitorHost, false);
    }

    /**
     * true if this server is the monitor host
     *
     * @param zimbraIsMonitorHost new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=132)
    public void setIsMonitorHost(boolean zimbraIsMonitorHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIsMonitorHost, zimbraIsMonitorHost ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * true if this server is the monitor host
     *
     * @param zimbraIsMonitorHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=132)
    public Map<String,Object> setIsMonitorHost(boolean zimbraIsMonitorHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIsMonitorHost, zimbraIsMonitorHost ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * true if this server is the monitor host
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=132)
    public void unsetIsMonitorHost() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIsMonitorHost, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * true if this server is the monitor host
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=132)
    public Map<String,Object> unsetIsMonitorHost(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraIsMonitorHost, "");
        return attrs;
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getLastPurgeMaxDurationAsString to access value as a string.
     *
     * @see #getLastPurgeMaxDurationAsString()
     *
     * @return zimbraLastPurgeMaxDuration in millseconds, or 2592000000 (30d)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public long getLastPurgeMaxDuration() {
        return getTimeInterval(Provisioning.A_zimbraLastPurgeMaxDuration, 2592000000L);
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zimbraLastPurgeMaxDuration, or "30d" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public String getLastPurgeMaxDurationAsString() {
        return getAttr(Provisioning.A_zimbraLastPurgeMaxDuration, "30d");
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraLastPurgeMaxDuration new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public void setLastPurgeMaxDuration(String zimbraLastPurgeMaxDuration) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLastPurgeMaxDuration, zimbraLastPurgeMaxDuration);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraLastPurgeMaxDuration new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public Map<String,Object> setLastPurgeMaxDuration(String zimbraLastPurgeMaxDuration, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLastPurgeMaxDuration, zimbraLastPurgeMaxDuration);
        return attrs;
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public void unsetLastPurgeMaxDuration() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLastPurgeMaxDuration, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public Map<String,Object> unsetLastPurgeMaxDuration(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLastPurgeMaxDuration, "");
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @return zimbraLmtpAdvertisedName, or null if unset
     */
    @ZAttr(id=23)
    public String getLmtpAdvertisedName() {
        return getAttr(Provisioning.A_zimbraLmtpAdvertisedName, null);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zimbraLmtpAdvertisedName new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=23)
    public void setLmtpAdvertisedName(String zimbraLmtpAdvertisedName) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpAdvertisedName, zimbraLmtpAdvertisedName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zimbraLmtpAdvertisedName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=23)
    public Map<String,Object> setLmtpAdvertisedName(String zimbraLmtpAdvertisedName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpAdvertisedName, zimbraLmtpAdvertisedName);
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=23)
    public void unsetLmtpAdvertisedName() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpAdvertisedName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=23)
    public Map<String,Object> unsetLmtpAdvertisedName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpAdvertisedName, "");
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraLmtpBindAddress, or empty array if unset
     */
    @ZAttr(id=25)
    public String[] getLmtpBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraLmtpBindAddress);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraLmtpBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void setLmtpBindAddress(String[] zimbraLmtpBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindAddress, zimbraLmtpBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraLmtpBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> setLmtpBindAddress(String[] zimbraLmtpBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindAddress, zimbraLmtpBindAddress);
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraLmtpBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void addLmtpBindAddress(String zimbraLmtpBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraLmtpBindAddress, zimbraLmtpBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraLmtpBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> addLmtpBindAddress(String zimbraLmtpBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraLmtpBindAddress, zimbraLmtpBindAddress);
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraLmtpBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void removeLmtpBindAddress(String zimbraLmtpBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraLmtpBindAddress, zimbraLmtpBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraLmtpBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> removeLmtpBindAddress(String zimbraLmtpBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraLmtpBindAddress, zimbraLmtpBindAddress);
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void unsetLmtpBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> unsetLmtpBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zimbraLmtpBindOnStartup, or false if unset
     */
    @ZAttr(id=270)
    public boolean isLmtpBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zimbraLmtpBindOnStartup, false);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraLmtpBindOnStartup new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=270)
    public void setLmtpBindOnStartup(boolean zimbraLmtpBindOnStartup) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindOnStartup, zimbraLmtpBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraLmtpBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=270)
    public Map<String,Object> setLmtpBindOnStartup(boolean zimbraLmtpBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindOnStartup, zimbraLmtpBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=270)
    public void unsetLmtpBindOnStartup() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindOnStartup, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=270)
    public Map<String,Object> unsetLmtpBindOnStartup(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which LMTP server should listen
     *
     * <p>Use getLmtpBindPortAsString to access value as a string.
     *
     * @see #getLmtpBindPortAsString()
     *
     * @return zimbraLmtpBindPort, or 7025 if unset
     */
    @ZAttr(id=24)
    public int getLmtpBindPort() {
        return getIntAttr(Provisioning.A_zimbraLmtpBindPort, 7025);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @return zimbraLmtpBindPort, or "7025" if unset
     */
    @ZAttr(id=24)
    public String getLmtpBindPortAsString() {
        return getAttr(Provisioning.A_zimbraLmtpBindPort, "7025");
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zimbraLmtpBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=24)
    public void setLmtpBindPort(int zimbraLmtpBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindPort, Integer.toString(zimbraLmtpBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zimbraLmtpBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=24)
    public Map<String,Object> setLmtpBindPort(int zimbraLmtpBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindPort, Integer.toString(zimbraLmtpBindPort));
        return attrs;
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zimbraLmtpBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=24)
    public void setLmtpBindPortAsString(String zimbraLmtpBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindPort, zimbraLmtpBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zimbraLmtpBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=24)
    public Map<String,Object> setLmtpBindPortAsString(String zimbraLmtpBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindPort, zimbraLmtpBindPort);
        return attrs;
    }

    /**
     * port number on which LMTP server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=24)
    public void unsetLmtpBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=24)
    public Map<String,Object> unsetLmtpBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpBindPort, "");
        return attrs;
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @return zimbraLmtpExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public boolean isLmtpExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zimbraLmtpExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @param zimbraLmtpExposeVersionOnBanner new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public void setLmtpExposeVersionOnBanner(boolean zimbraLmtpExposeVersionOnBanner) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpExposeVersionOnBanner, zimbraLmtpExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @param zimbraLmtpExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public Map<String,Object> setLmtpExposeVersionOnBanner(boolean zimbraLmtpExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpExposeVersionOnBanner, zimbraLmtpExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public void unsetLmtpExposeVersionOnBanner() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpExposeVersionOnBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public Map<String,Object> unsetLmtpExposeVersionOnBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @return zimbraLmtpNumThreads, or 20 if unset
     */
    @ZAttr(id=26)
    public int getLmtpNumThreads() {
        return getIntAttr(Provisioning.A_zimbraLmtpNumThreads, 20);
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @param zimbraLmtpNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=26)
    public void setLmtpNumThreads(int zimbraLmtpNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpNumThreads, Integer.toString(zimbraLmtpNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @param zimbraLmtpNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=26)
    public Map<String,Object> setLmtpNumThreads(int zimbraLmtpNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpNumThreads, Integer.toString(zimbraLmtpNumThreads));
        return attrs;
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=26)
    public void unsetLmtpNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=26)
    public Map<String,Object> unsetLmtpNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpNumThreads, "");
        return attrs;
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @return zimbraLmtpPermanentFailureWhenOverQuota, or false if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public boolean isLmtpPermanentFailureWhenOverQuota() {
        return getBooleanAttr(Provisioning.A_zimbraLmtpPermanentFailureWhenOverQuota, false);
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @param zimbraLmtpPermanentFailureWhenOverQuota new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public void setLmtpPermanentFailureWhenOverQuota(boolean zimbraLmtpPermanentFailureWhenOverQuota) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpPermanentFailureWhenOverQuota, zimbraLmtpPermanentFailureWhenOverQuota ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @param zimbraLmtpPermanentFailureWhenOverQuota new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public Map<String,Object> setLmtpPermanentFailureWhenOverQuota(boolean zimbraLmtpPermanentFailureWhenOverQuota, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpPermanentFailureWhenOverQuota, zimbraLmtpPermanentFailureWhenOverQuota ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public void unsetLmtpPermanentFailureWhenOverQuota() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpPermanentFailureWhenOverQuota, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public Map<String,Object> unsetLmtpPermanentFailureWhenOverQuota(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpPermanentFailureWhenOverQuota, "");
        return attrs;
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @return zimbraLmtpServerEnabled, or true if unset
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public boolean isLmtpServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraLmtpServerEnabled, true);
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @param zimbraLmtpServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public void setLmtpServerEnabled(boolean zimbraLmtpServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpServerEnabled, zimbraLmtpServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @param zimbraLmtpServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public Map<String,Object> setLmtpServerEnabled(boolean zimbraLmtpServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpServerEnabled, zimbraLmtpServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public void unsetLmtpServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public Map<String,Object> unsetLmtpServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpServerEnabled, "");
        return attrs;
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @return zimbraLmtpShutdownGraceSeconds, or 10 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public int getLmtpShutdownGraceSeconds() {
        return getIntAttr(Provisioning.A_zimbraLmtpShutdownGraceSeconds, 10);
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @param zimbraLmtpShutdownGraceSeconds new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public void setLmtpShutdownGraceSeconds(int zimbraLmtpShutdownGraceSeconds) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpShutdownGraceSeconds, Integer.toString(zimbraLmtpShutdownGraceSeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @param zimbraLmtpShutdownGraceSeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public Map<String,Object> setLmtpShutdownGraceSeconds(int zimbraLmtpShutdownGraceSeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpShutdownGraceSeconds, Integer.toString(zimbraLmtpShutdownGraceSeconds));
        return attrs;
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public void unsetLmtpShutdownGraceSeconds() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpShutdownGraceSeconds, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public Map<String,Object> unsetLmtpShutdownGraceSeconds(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLmtpShutdownGraceSeconds, "");
        return attrs;
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @return zimbraLocale, or null if unset
     */
    @ZAttr(id=345)
    public String getLocaleAsString() {
        return getAttr(Provisioning.A_zimbraLocale, null);
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @param zimbraLocale new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=345)
    public void setLocale(String zimbraLocale) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLocale, zimbraLocale);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @param zimbraLocale new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=345)
    public Map<String,Object> setLocale(String zimbraLocale, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLocale, zimbraLocale);
        return attrs;
    }

    /**
     * locale of entry, e.g. en_US
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=345)
    public void unsetLocale() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLocale, "");
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
        attrs.put(Provisioning.A_zimbraLocale, "");
        return attrs;
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @return zimbraLogToSyslog, or false if unset
     */
    @ZAttr(id=520)
    public boolean isLogToSyslog() {
        return getBooleanAttr(Provisioning.A_zimbraLogToSyslog, false);
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @param zimbraLogToSyslog new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=520)
    public void setLogToSyslog(boolean zimbraLogToSyslog) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLogToSyslog, zimbraLogToSyslog ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @param zimbraLogToSyslog new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=520)
    public Map<String,Object> setLogToSyslog(boolean zimbraLogToSyslog, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLogToSyslog, zimbraLogToSyslog ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=520)
    public void unsetLogToSyslog() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLogToSyslog, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=520)
    public Map<String,Object> unsetLogToSyslog(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraLogToSyslog, "");
        return attrs;
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraMailBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public String getMailBindAddress() {
        return getAttr(Provisioning.A_zimbraMailBindAddress, null);
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraMailBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public void setMailBindAddress(String zimbraMailBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailBindAddress, zimbraMailBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraMailBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public Map<String,Object> setMailBindAddress(String zimbraMailBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailBindAddress, zimbraMailBindAddress);
        return attrs;
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public void unsetMailBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public Map<String,Object> unsetMailBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailBindAddress, "");
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port when zimbraMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @return zimbraMailClearTextPasswordEnabled, or true if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public boolean isMailClearTextPasswordEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMailClearTextPasswordEnabled, true);
    }

    /**
     * Whether to allow password sent to non-secured port when zimbraMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @param zimbraMailClearTextPasswordEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public void setMailClearTextPasswordEnabled(boolean zimbraMailClearTextPasswordEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailClearTextPasswordEnabled, zimbraMailClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port when zimbraMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @param zimbraMailClearTextPasswordEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public Map<String,Object> setMailClearTextPasswordEnabled(boolean zimbraMailClearTextPasswordEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailClearTextPasswordEnabled, zimbraMailClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port when zimbraMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public void unsetMailClearTextPasswordEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailClearTextPasswordEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port when zimbraMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public Map<String,Object> unsetMailClearTextPasswordEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailClearTextPasswordEnabled, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @return zimbraMailContentMaxSize, or 10240000 if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public long getMailContentMaxSize() {
        return getLongAttr(Provisioning.A_zimbraMailContentMaxSize, 10240000L);
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @param zimbraMailContentMaxSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public void setMailContentMaxSize(long zimbraMailContentMaxSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailContentMaxSize, Long.toString(zimbraMailContentMaxSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @param zimbraMailContentMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public Map<String,Object> setMailContentMaxSize(long zimbraMailContentMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailContentMaxSize, Long.toString(zimbraMailContentMaxSize));
        return attrs;
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public void unsetMailContentMaxSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailContentMaxSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public Map<String,Object> unsetMailContentMaxSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailContentMaxSize, "");
        return attrs;
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @return zimbraMailDiskStreamingThreshold, or 1048576 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public int getMailDiskStreamingThreshold() {
        return getIntAttr(Provisioning.A_zimbraMailDiskStreamingThreshold, 1048576);
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @param zimbraMailDiskStreamingThreshold new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public void setMailDiskStreamingThreshold(int zimbraMailDiskStreamingThreshold) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailDiskStreamingThreshold, Integer.toString(zimbraMailDiskStreamingThreshold));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @param zimbraMailDiskStreamingThreshold new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public Map<String,Object> setMailDiskStreamingThreshold(int zimbraMailDiskStreamingThreshold, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailDiskStreamingThreshold, Integer.toString(zimbraMailDiskStreamingThreshold));
        return attrs;
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public void unsetMailDiskStreamingThreshold() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailDiskStreamingThreshold, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public Map<String,Object> unsetMailDiskStreamingThreshold(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailDiskStreamingThreshold, "");
        return attrs;
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @return zimbraMailEmptyFolderBatchSize, or 1000 if unset
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public int getMailEmptyFolderBatchSize() {
        return getIntAttr(Provisioning.A_zimbraMailEmptyFolderBatchSize, 1000);
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @param zimbraMailEmptyFolderBatchSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public void setMailEmptyFolderBatchSize(int zimbraMailEmptyFolderBatchSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchSize, Integer.toString(zimbraMailEmptyFolderBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @param zimbraMailEmptyFolderBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public Map<String,Object> setMailEmptyFolderBatchSize(int zimbraMailEmptyFolderBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchSize, Integer.toString(zimbraMailEmptyFolderBatchSize));
        return attrs;
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public void unsetMailEmptyFolderBatchSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public Map<String,Object> unsetMailEmptyFolderBatchSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchSize, "");
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zimbraMailEmptyFolderBatchSize.
     *
     * @return zimbraMailEmptyFolderBatchThreshold, or 100000 if unset
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public int getMailEmptyFolderBatchThreshold() {
        return getIntAttr(Provisioning.A_zimbraMailEmptyFolderBatchThreshold, 100000);
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zimbraMailEmptyFolderBatchSize.
     *
     * @param zimbraMailEmptyFolderBatchThreshold new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public void setMailEmptyFolderBatchThreshold(int zimbraMailEmptyFolderBatchThreshold) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchThreshold, Integer.toString(zimbraMailEmptyFolderBatchThreshold));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zimbraMailEmptyFolderBatchSize.
     *
     * @param zimbraMailEmptyFolderBatchThreshold new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public Map<String,Object> setMailEmptyFolderBatchThreshold(int zimbraMailEmptyFolderBatchThreshold, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchThreshold, Integer.toString(zimbraMailEmptyFolderBatchThreshold));
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zimbraMailEmptyFolderBatchSize.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public void unsetMailEmptyFolderBatchThreshold() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchThreshold, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zimbraMailEmptyFolderBatchSize.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public Map<String,Object> unsetMailEmptyFolderBatchThreshold(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailEmptyFolderBatchThreshold, "");
        return attrs;
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @return zimbraMailFileDescriptorBufferSize, or 4096 if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public int getMailFileDescriptorBufferSize() {
        return getIntAttr(Provisioning.A_zimbraMailFileDescriptorBufferSize, 4096);
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @param zimbraMailFileDescriptorBufferSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public void setMailFileDescriptorBufferSize(int zimbraMailFileDescriptorBufferSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorBufferSize, Integer.toString(zimbraMailFileDescriptorBufferSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @param zimbraMailFileDescriptorBufferSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public Map<String,Object> setMailFileDescriptorBufferSize(int zimbraMailFileDescriptorBufferSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorBufferSize, Integer.toString(zimbraMailFileDescriptorBufferSize));
        return attrs;
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public void unsetMailFileDescriptorBufferSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorBufferSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public Map<String,Object> unsetMailFileDescriptorBufferSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorBufferSize, "");
        return attrs;
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @return zimbraMailFileDescriptorCacheSize, or 1000 if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public int getMailFileDescriptorCacheSize() {
        return getIntAttr(Provisioning.A_zimbraMailFileDescriptorCacheSize, 1000);
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @param zimbraMailFileDescriptorCacheSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public void setMailFileDescriptorCacheSize(int zimbraMailFileDescriptorCacheSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorCacheSize, Integer.toString(zimbraMailFileDescriptorCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @param zimbraMailFileDescriptorCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public Map<String,Object> setMailFileDescriptorCacheSize(int zimbraMailFileDescriptorCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorCacheSize, Integer.toString(zimbraMailFileDescriptorCacheSize));
        return attrs;
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public void unsetMailFileDescriptorCacheSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorCacheSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public Map<String,Object> unsetMailFileDescriptorCacheSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailFileDescriptorCacheSize, "");
        return attrs;
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @return zimbraMailKeepOutWebCrawlers, or false if unset
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public boolean isMailKeepOutWebCrawlers() {
        return getBooleanAttr(Provisioning.A_zimbraMailKeepOutWebCrawlers, false);
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @param zimbraMailKeepOutWebCrawlers new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public void setMailKeepOutWebCrawlers(boolean zimbraMailKeepOutWebCrawlers) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailKeepOutWebCrawlers, zimbraMailKeepOutWebCrawlers ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @param zimbraMailKeepOutWebCrawlers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public Map<String,Object> setMailKeepOutWebCrawlers(boolean zimbraMailKeepOutWebCrawlers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailKeepOutWebCrawlers, zimbraMailKeepOutWebCrawlers ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public void unsetMailKeepOutWebCrawlers() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailKeepOutWebCrawlers, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public Map<String,Object> unsetMailKeepOutWebCrawlers(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailKeepOutWebCrawlers, "");
        return attrs;
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @return zimbraMailLastPurgedMailboxId, or -1 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public int getMailLastPurgedMailboxId() {
        return getIntAttr(Provisioning.A_zimbraMailLastPurgedMailboxId, -1);
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @param zimbraMailLastPurgedMailboxId new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public void setMailLastPurgedMailboxId(int zimbraMailLastPurgedMailboxId) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLastPurgedMailboxId, Integer.toString(zimbraMailLastPurgedMailboxId));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @param zimbraMailLastPurgedMailboxId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public Map<String,Object> setMailLastPurgedMailboxId(int zimbraMailLastPurgedMailboxId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLastPurgedMailboxId, Integer.toString(zimbraMailLastPurgedMailboxId));
        return attrs;
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public void unsetMailLastPurgedMailboxId() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLastPurgedMailboxId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public Map<String,Object> unsetMailLastPurgedMailboxId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLastPurgedMailboxId, "");
        return attrs;
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zimbraMailMode
     * and zimbraMailBindAddress.
     *
     * @return zimbraMailLocalBind, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public boolean isMailLocalBind() {
        return getBooleanAttr(Provisioning.A_zimbraMailLocalBind, false);
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zimbraMailMode
     * and zimbraMailBindAddress.
     *
     * @param zimbraMailLocalBind new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public void setMailLocalBind(boolean zimbraMailLocalBind) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLocalBind, zimbraMailLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zimbraMailMode
     * and zimbraMailBindAddress.
     *
     * @param zimbraMailLocalBind new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public Map<String,Object> setMailLocalBind(boolean zimbraMailLocalBind, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLocalBind, zimbraMailLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zimbraMailMode
     * and zimbraMailBindAddress.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public void unsetMailLocalBind() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLocalBind, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zimbraMailMode
     * and zimbraMailBindAddress.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public Map<String,Object> unsetMailLocalBind(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailLocalBind, "");
        return attrs;
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zimbraMailMode, or null if unset and/or has invalid value
     */
    @ZAttr(id=308)
    public ZAttrProvisioning.MailMode getMailMode() {
        try { String v = getAttr(Provisioning.A_zimbraMailMode); return v == null ? null : ZAttrProvisioning.MailMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return null; }
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zimbraMailMode, or null if unset
     */
    @ZAttr(id=308)
    public String getMailModeAsString() {
        return getAttr(Provisioning.A_zimbraMailMode, null);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraMailMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=308)
    public void setMailMode(ZAttrProvisioning.MailMode zimbraMailMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailMode, zimbraMailMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=308)
    public Map<String,Object> setMailMode(ZAttrProvisioning.MailMode zimbraMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailMode, zimbraMailMode.toString());
        return attrs;
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraMailMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=308)
    public void setMailModeAsString(String zimbraMailMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailMode, zimbraMailMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=308)
    public Map<String,Object> setMailModeAsString(String zimbraMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailMode, zimbraMailMode);
        return attrs;
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=308)
    public void unsetMailMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zimbraMailPort and zimbraMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=308)
    public Map<String,Object> unsetMailMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailMode, "");
        return attrs;
    }

    /**
     * HTTP port for end-user UI
     *
     * <p>Use getMailPortAsString to access value as a string.
     *
     * @see #getMailPortAsString()
     *
     * @return zimbraMailPort, or 80 if unset
     */
    @ZAttr(id=154)
    public int getMailPort() {
        return getIntAttr(Provisioning.A_zimbraMailPort, 80);
    }

    /**
     * HTTP port for end-user UI
     *
     * @return zimbraMailPort, or "80" if unset
     */
    @ZAttr(id=154)
    public String getMailPortAsString() {
        return getAttr(Provisioning.A_zimbraMailPort, "80");
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zimbraMailPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=154)
    public void setMailPort(int zimbraMailPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPort, Integer.toString(zimbraMailPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zimbraMailPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=154)
    public Map<String,Object> setMailPort(int zimbraMailPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPort, Integer.toString(zimbraMailPort));
        return attrs;
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zimbraMailPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=154)
    public void setMailPortAsString(String zimbraMailPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPort, zimbraMailPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zimbraMailPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=154)
    public Map<String,Object> setMailPortAsString(String zimbraMailPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPort, zimbraMailPort);
        return attrs;
    }

    /**
     * HTTP port for end-user UI
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=154)
    public void unsetMailPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP port for end-user UI
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=154)
    public Map<String,Object> unsetMailPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPort, "");
        return attrs;
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zimbraMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @return zimbraMailProxyMaxFails, or 1 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public int getMailProxyMaxFails() {
        return getIntAttr(Provisioning.A_zimbraMailProxyMaxFails, 1);
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zimbraMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @param zimbraMailProxyMaxFails new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public void setMailProxyMaxFails(int zimbraMailProxyMaxFails) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyMaxFails, Integer.toString(zimbraMailProxyMaxFails));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zimbraMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @param zimbraMailProxyMaxFails new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public Map<String,Object> setMailProxyMaxFails(int zimbraMailProxyMaxFails, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyMaxFails, Integer.toString(zimbraMailProxyMaxFails));
        return attrs;
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zimbraMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public void unsetMailProxyMaxFails() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyMaxFails, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zimbraMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public Map<String,Object> unsetMailProxyMaxFails(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyMaxFails, "");
        return attrs;
    }

    /**
     * HTTP proxy port
     *
     * <p>Use getMailProxyPortAsString to access value as a string.
     *
     * @see #getMailProxyPortAsString()
     *
     * @return zimbraMailProxyPort, or 0 if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public int getMailProxyPort() {
        return getIntAttr(Provisioning.A_zimbraMailProxyPort, 0);
    }

    /**
     * HTTP proxy port
     *
     * @return zimbraMailProxyPort, or "0" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public String getMailProxyPortAsString() {
        return getAttr(Provisioning.A_zimbraMailProxyPort, "0");
    }

    /**
     * HTTP proxy port
     *
     * @param zimbraMailProxyPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public void setMailProxyPort(int zimbraMailProxyPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyPort, Integer.toString(zimbraMailProxyPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP proxy port
     *
     * @param zimbraMailProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public Map<String,Object> setMailProxyPort(int zimbraMailProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyPort, Integer.toString(zimbraMailProxyPort));
        return attrs;
    }

    /**
     * HTTP proxy port
     *
     * @param zimbraMailProxyPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public void setMailProxyPortAsString(String zimbraMailProxyPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyPort, zimbraMailProxyPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP proxy port
     *
     * @param zimbraMailProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public Map<String,Object> setMailProxyPortAsString(String zimbraMailProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyPort, zimbraMailProxyPort);
        return attrs;
    }

    /**
     * HTTP proxy port
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public void unsetMailProxyPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP proxy port
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public Map<String,Object> unsetMailProxyPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyPort, "");
        return attrs;
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @return zimbraMailProxyReconnectTimeout, or "60" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public String getMailProxyReconnectTimeout() {
        return getAttr(Provisioning.A_zimbraMailProxyReconnectTimeout, "60");
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @param zimbraMailProxyReconnectTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public void setMailProxyReconnectTimeout(String zimbraMailProxyReconnectTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyReconnectTimeout, zimbraMailProxyReconnectTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @param zimbraMailProxyReconnectTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public Map<String,Object> setMailProxyReconnectTimeout(String zimbraMailProxyReconnectTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyReconnectTimeout, zimbraMailProxyReconnectTimeout);
        return attrs;
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public void unsetMailProxyReconnectTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyReconnectTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public Map<String,Object> unsetMailProxyReconnectTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailProxyReconnectTimeout, "");
        return attrs;
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @return zimbraMailPurgeBatchSize, or 1000 if unset
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public int getMailPurgeBatchSize() {
        return getIntAttr(Provisioning.A_zimbraMailPurgeBatchSize, 1000);
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @param zimbraMailPurgeBatchSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public void setMailPurgeBatchSize(int zimbraMailPurgeBatchSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeBatchSize, Integer.toString(zimbraMailPurgeBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @param zimbraMailPurgeBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public Map<String,Object> setMailPurgeBatchSize(int zimbraMailPurgeBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeBatchSize, Integer.toString(zimbraMailPurgeBatchSize));
        return attrs;
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public void unsetMailPurgeBatchSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeBatchSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public Map<String,Object> unsetMailPurgeBatchSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeBatchSize, "");
        return attrs;
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getMailPurgeSleepIntervalAsString to access value as a string.
     *
     * @see #getMailPurgeSleepIntervalAsString()
     *
     * @return zimbraMailPurgeSleepInterval in millseconds, or 60000 (1m)  if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public long getMailPurgeSleepInterval() {
        return getTimeInterval(Provisioning.A_zimbraMailPurgeSleepInterval, 60000L);
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraMailPurgeSleepInterval, or "1m" if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public String getMailPurgeSleepIntervalAsString() {
        return getAttr(Provisioning.A_zimbraMailPurgeSleepInterval, "1m");
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraMailPurgeSleepInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public void setMailPurgeSleepInterval(String zimbraMailPurgeSleepInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeSleepInterval, zimbraMailPurgeSleepInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraMailPurgeSleepInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public Map<String,Object> setMailPurgeSleepInterval(String zimbraMailPurgeSleepInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeSleepInterval, zimbraMailPurgeSleepInterval);
        return attrs;
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public void unsetMailPurgeSleepInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeSleepInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public Map<String,Object> unsetMailPurgeSleepInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailPurgeSleepInterval, "");
        return attrs;
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @return zimbraMailRedirectSetEnvelopeSender, or true if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public boolean isMailRedirectSetEnvelopeSender() {
        return getBooleanAttr(Provisioning.A_zimbraMailRedirectSetEnvelopeSender, true);
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @param zimbraMailRedirectSetEnvelopeSender new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public void setMailRedirectSetEnvelopeSender(boolean zimbraMailRedirectSetEnvelopeSender) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailRedirectSetEnvelopeSender, zimbraMailRedirectSetEnvelopeSender ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @param zimbraMailRedirectSetEnvelopeSender new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public Map<String,Object> setMailRedirectSetEnvelopeSender(boolean zimbraMailRedirectSetEnvelopeSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailRedirectSetEnvelopeSender, zimbraMailRedirectSetEnvelopeSender ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public void unsetMailRedirectSetEnvelopeSender() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailRedirectSetEnvelopeSender, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public Map<String,Object> unsetMailRedirectSetEnvelopeSender(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailRedirectSetEnvelopeSender, "");
        return attrs;
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @return zimbraMailReferMode, or ZAttrProvisioning.MailReferMode.wronghost if unset and/or has invalid value
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public ZAttrProvisioning.MailReferMode getMailReferMode() {
        try { String v = getAttr(Provisioning.A_zimbraMailReferMode); return v == null ? ZAttrProvisioning.MailReferMode.wronghost : ZAttrProvisioning.MailReferMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.MailReferMode.wronghost; }
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @return zimbraMailReferMode, or "wronghost" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public String getMailReferModeAsString() {
        return getAttr(Provisioning.A_zimbraMailReferMode, "wronghost");
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @param zimbraMailReferMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public void setMailReferMode(ZAttrProvisioning.MailReferMode zimbraMailReferMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailReferMode, zimbraMailReferMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @param zimbraMailReferMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public Map<String,Object> setMailReferMode(ZAttrProvisioning.MailReferMode zimbraMailReferMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailReferMode, zimbraMailReferMode.toString());
        return attrs;
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @param zimbraMailReferMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public void setMailReferModeAsString(String zimbraMailReferMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailReferMode, zimbraMailReferMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @param zimbraMailReferMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public Map<String,Object> setMailReferModeAsString(String zimbraMailReferMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailReferMode, zimbraMailReferMode);
        return attrs;
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public void unsetMailReferMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailReferMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to send back a refer tag in an auth response to force a client
     * redirect. always - always send refer wronghost - send refer if only if
     * the account being authenticated does not live on this mail host
     * reverse-proxied - reverse proxy is in place and should never send
     * refer
     *
     * <p>Valid values: [always, reverse-proxied, wronghost]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public Map<String,Object> unsetMailReferMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailReferMode, "");
        return attrs;
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraMailSSLBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public String getMailSSLBindAddress() {
        return getAttr(Provisioning.A_zimbraMailSSLBindAddress, null);
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraMailSSLBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public void setMailSSLBindAddress(String zimbraMailSSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLBindAddress, zimbraMailSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraMailSSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public Map<String,Object> setMailSSLBindAddress(String zimbraMailSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLBindAddress, zimbraMailSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public void unsetMailSSLBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public Map<String,Object> unsetMailSSLBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLBindAddress, "");
        return attrs;
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @return zimbraMailSSLClientCertBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public String getMailSSLClientCertBindAddress() {
        return getAttr(Provisioning.A_zimbraMailSSLClientCertBindAddress, null);
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraMailSSLClientCertBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public void setMailSSLClientCertBindAddress(String zimbraMailSSLClientCertBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertBindAddress, zimbraMailSSLClientCertBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraMailSSLClientCertBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public Map<String,Object> setMailSSLClientCertBindAddress(String zimbraMailSSLClientCertBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertBindAddress, zimbraMailSSLClientCertBindAddress);
        return attrs;
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public void unsetMailSSLClientCertBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public Map<String,Object> unsetMailSSLClientCertBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertBindAddress, "");
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @return zimbraMailSSLClientCertMode, or ZAttrProvisioning.MailSSLClientCertMode.Disabled if unset and/or has invalid value
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public ZAttrProvisioning.MailSSLClientCertMode getMailSSLClientCertMode() {
        try { String v = getAttr(Provisioning.A_zimbraMailSSLClientCertMode); return v == null ? ZAttrProvisioning.MailSSLClientCertMode.Disabled : ZAttrProvisioning.MailSSLClientCertMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.MailSSLClientCertMode.Disabled; }
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @return zimbraMailSSLClientCertMode, or "Disabled" if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public String getMailSSLClientCertModeAsString() {
        return getAttr(Provisioning.A_zimbraMailSSLClientCertMode, "Disabled");
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zimbraMailSSLClientCertMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public void setMailSSLClientCertMode(ZAttrProvisioning.MailSSLClientCertMode zimbraMailSSLClientCertMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertMode, zimbraMailSSLClientCertMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zimbraMailSSLClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public Map<String,Object> setMailSSLClientCertMode(ZAttrProvisioning.MailSSLClientCertMode zimbraMailSSLClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertMode, zimbraMailSSLClientCertMode.toString());
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zimbraMailSSLClientCertMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public void setMailSSLClientCertModeAsString(String zimbraMailSSLClientCertMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertMode, zimbraMailSSLClientCertMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zimbraMailSSLClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public Map<String,Object> setMailSSLClientCertModeAsString(String zimbraMailSSLClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertMode, zimbraMailSSLClientCertMode);
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public void unsetMailSSLClientCertMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zimbraMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zimbraMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public Map<String,Object> unsetMailSSLClientCertMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertMode, "");
        return attrs;
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @return zimbraMailSSLClientCertOCSPEnabled, or true if unset
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public boolean isMailSSLClientCertOCSPEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMailSSLClientCertOCSPEnabled, true);
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @param zimbraMailSSLClientCertOCSPEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public void setMailSSLClientCertOCSPEnabled(boolean zimbraMailSSLClientCertOCSPEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertOCSPEnabled, zimbraMailSSLClientCertOCSPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @param zimbraMailSSLClientCertOCSPEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public Map<String,Object> setMailSSLClientCertOCSPEnabled(boolean zimbraMailSSLClientCertOCSPEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertOCSPEnabled, zimbraMailSSLClientCertOCSPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public void unsetMailSSLClientCertOCSPEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertOCSPEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public Map<String,Object> unsetMailSSLClientCertOCSPEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertOCSPEnabled, "");
        return attrs;
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * <p>Use getMailSSLClientCertPortAsString to access value as a string.
     *
     * @see #getMailSSLClientCertPortAsString()
     *
     * @return zimbraMailSSLClientCertPort, or 9443 if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public int getMailSSLClientCertPort() {
        return getIntAttr(Provisioning.A_zimbraMailSSLClientCertPort, 9443);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @return zimbraMailSSLClientCertPort, or "9443" if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public String getMailSSLClientCertPortAsString() {
        return getAttr(Provisioning.A_zimbraMailSSLClientCertPort, "9443");
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zimbraMailSSLClientCertPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public void setMailSSLClientCertPort(int zimbraMailSSLClientCertPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertPort, Integer.toString(zimbraMailSSLClientCertPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zimbraMailSSLClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public Map<String,Object> setMailSSLClientCertPort(int zimbraMailSSLClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertPort, Integer.toString(zimbraMailSSLClientCertPort));
        return attrs;
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zimbraMailSSLClientCertPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public void setMailSSLClientCertPortAsString(String zimbraMailSSLClientCertPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertPort, zimbraMailSSLClientCertPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zimbraMailSSLClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public Map<String,Object> setMailSSLClientCertPortAsString(String zimbraMailSSLClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertPort, zimbraMailSSLClientCertPort);
        return attrs;
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public void unsetMailSSLClientCertPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public Map<String,Object> unsetMailSSLClientCertPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLClientCertPort, "");
        return attrs;
    }

    /**
     * SSL port for end-user UI
     *
     * <p>Use getMailSSLPortAsString to access value as a string.
     *
     * @see #getMailSSLPortAsString()
     *
     * @return zimbraMailSSLPort, or 0 if unset
     */
    @ZAttr(id=166)
    public int getMailSSLPort() {
        return getIntAttr(Provisioning.A_zimbraMailSSLPort, 0);
    }

    /**
     * SSL port for end-user UI
     *
     * @return zimbraMailSSLPort, or "0" if unset
     */
    @ZAttr(id=166)
    public String getMailSSLPortAsString() {
        return getAttr(Provisioning.A_zimbraMailSSLPort, "0");
    }

    /**
     * SSL port for end-user UI
     *
     * @param zimbraMailSSLPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=166)
    public void setMailSSLPort(int zimbraMailSSLPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLPort, Integer.toString(zimbraMailSSLPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for end-user UI
     *
     * @param zimbraMailSSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=166)
    public Map<String,Object> setMailSSLPort(int zimbraMailSSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLPort, Integer.toString(zimbraMailSSLPort));
        return attrs;
    }

    /**
     * SSL port for end-user UI
     *
     * @param zimbraMailSSLPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=166)
    public void setMailSSLPortAsString(String zimbraMailSSLPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLPort, zimbraMailSSLPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for end-user UI
     *
     * @param zimbraMailSSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=166)
    public Map<String,Object> setMailSSLPortAsString(String zimbraMailSSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLPort, zimbraMailSSLPort);
        return attrs;
    }

    /**
     * SSL port for end-user UI
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=166)
    public void unsetMailSSLPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for end-user UI
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=166)
    public Map<String,Object> unsetMailSSLPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLPort, "");
        return attrs;
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * <p>Use getMailSSLProxyClientCertPortAsString to access value as a string.
     *
     * @see #getMailSSLProxyClientCertPortAsString()
     *
     * @return zimbraMailSSLProxyClientCertPort, or 3443 if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public int getMailSSLProxyClientCertPort() {
        return getIntAttr(Provisioning.A_zimbraMailSSLProxyClientCertPort, 3443);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @return zimbraMailSSLProxyClientCertPort, or "3443" if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public String getMailSSLProxyClientCertPortAsString() {
        return getAttr(Provisioning.A_zimbraMailSSLProxyClientCertPort, "3443");
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zimbraMailSSLProxyClientCertPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public void setMailSSLProxyClientCertPort(int zimbraMailSSLProxyClientCertPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyClientCertPort, Integer.toString(zimbraMailSSLProxyClientCertPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zimbraMailSSLProxyClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public Map<String,Object> setMailSSLProxyClientCertPort(int zimbraMailSSLProxyClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyClientCertPort, Integer.toString(zimbraMailSSLProxyClientCertPort));
        return attrs;
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zimbraMailSSLProxyClientCertPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public void setMailSSLProxyClientCertPortAsString(String zimbraMailSSLProxyClientCertPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyClientCertPort, zimbraMailSSLProxyClientCertPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zimbraMailSSLProxyClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public Map<String,Object> setMailSSLProxyClientCertPortAsString(String zimbraMailSSLProxyClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyClientCertPort, zimbraMailSSLProxyClientCertPort);
        return attrs;
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public void unsetMailSSLProxyClientCertPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyClientCertPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public Map<String,Object> unsetMailSSLProxyClientCertPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyClientCertPort, "");
        return attrs;
    }

    /**
     * SSL port HTTP proxy
     *
     * <p>Use getMailSSLProxyPortAsString to access value as a string.
     *
     * @see #getMailSSLProxyPortAsString()
     *
     * @return zimbraMailSSLProxyPort, or 0 if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public int getMailSSLProxyPort() {
        return getIntAttr(Provisioning.A_zimbraMailSSLProxyPort, 0);
    }

    /**
     * SSL port HTTP proxy
     *
     * @return zimbraMailSSLProxyPort, or "0" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public String getMailSSLProxyPortAsString() {
        return getAttr(Provisioning.A_zimbraMailSSLProxyPort, "0");
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zimbraMailSSLProxyPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public void setMailSSLProxyPort(int zimbraMailSSLProxyPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyPort, Integer.toString(zimbraMailSSLProxyPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zimbraMailSSLProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public Map<String,Object> setMailSSLProxyPort(int zimbraMailSSLProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyPort, Integer.toString(zimbraMailSSLProxyPort));
        return attrs;
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zimbraMailSSLProxyPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public void setMailSSLProxyPortAsString(String zimbraMailSSLProxyPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyPort, zimbraMailSSLProxyPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zimbraMailSSLProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public Map<String,Object> setMailSSLProxyPortAsString(String zimbraMailSSLProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyPort, zimbraMailSSLProxyPort);
        return attrs;
    }

    /**
     * SSL port HTTP proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public void unsetMailSSLProxyPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port HTTP proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public Map<String,Object> unsetMailSSLProxyPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailSSLProxyPort, "");
        return attrs;
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @return zimbraMailTrustedIP, or empty array if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public String[] getMailTrustedIP() {
        return getMultiAttr(Provisioning.A_zimbraMailTrustedIP);
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param zimbraMailTrustedIP new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void setMailTrustedIP(String[] zimbraMailTrustedIP) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailTrustedIP, zimbraMailTrustedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param zimbraMailTrustedIP new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> setMailTrustedIP(String[] zimbraMailTrustedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailTrustedIP, zimbraMailTrustedIP);
        return attrs;
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param zimbraMailTrustedIP new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void addMailTrustedIP(String zimbraMailTrustedIP) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMailTrustedIP, zimbraMailTrustedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param zimbraMailTrustedIP new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> addMailTrustedIP(String zimbraMailTrustedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMailTrustedIP, zimbraMailTrustedIP);
        return attrs;
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param zimbraMailTrustedIP existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void removeMailTrustedIP(String zimbraMailTrustedIP) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMailTrustedIP, zimbraMailTrustedIP);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param zimbraMailTrustedIP existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> removeMailTrustedIP(String zimbraMailTrustedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMailTrustedIP, zimbraMailTrustedIP);
        return attrs;
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void unsetMailTrustedIP() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailTrustedIP, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * In our web app, AJAX and standard html client, we have support for
     * adding the HTTP client IP address as X-Originating-IP in an outbound
     * message. We also use the HTTP client IP address in our logging. In the
     * case of standard client making connections to the SOAP layer, the JSP
     * layer tells the SOAP layer in a http header what the remote HTTP
     * client address is. In the case where nginx or some other proxy layer
     * is fronting our webapps, the proxy tells the SOAP/JSP layers in a http
     * header what the real HTTP client s address is. Our SOAP/JSP layers
     * will trust the client/proxy only if the IP address of the client/proxy
     * is one of the IPs listed in this attribute.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> unsetMailTrustedIP(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailTrustedIP, "");
        return attrs;
    }

    /**
     * URL prefix for where the zimbra app resides on this server
     *
     * @return zimbraMailURL, or "/" if unset
     */
    @ZAttr(id=340)
    public String getMailURL() {
        return getAttr(Provisioning.A_zimbraMailURL, "/");
    }

    /**
     * URL prefix for where the zimbra app resides on this server
     *
     * @param zimbraMailURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=340)
    public void setMailURL(String zimbraMailURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailURL, zimbraMailURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zimbra app resides on this server
     *
     * @param zimbraMailURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=340)
    public Map<String,Object> setMailURL(String zimbraMailURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailURL, zimbraMailURL);
        return attrs;
    }

    /**
     * URL prefix for where the zimbra app resides on this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=340)
    public void unsetMailURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zimbra app resides on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=340)
    public Map<String,Object> unsetMailURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailURL, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @return zimbraMailUncompressedCacheMaxBytes, or 1073741824 if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public long getMailUncompressedCacheMaxBytes() {
        return getLongAttr(Provisioning.A_zimbraMailUncompressedCacheMaxBytes, 1073741824L);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @param zimbraMailUncompressedCacheMaxBytes new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public void setMailUncompressedCacheMaxBytes(long zimbraMailUncompressedCacheMaxBytes) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxBytes, Long.toString(zimbraMailUncompressedCacheMaxBytes));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @param zimbraMailUncompressedCacheMaxBytes new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public Map<String,Object> setMailUncompressedCacheMaxBytes(long zimbraMailUncompressedCacheMaxBytes, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxBytes, Long.toString(zimbraMailUncompressedCacheMaxBytes));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public void unsetMailUncompressedCacheMaxBytes() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxBytes, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public Map<String,Object> unsetMailUncompressedCacheMaxBytes(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxBytes, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @return zimbraMailUncompressedCacheMaxFiles, or 5000 if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public int getMailUncompressedCacheMaxFiles() {
        return getIntAttr(Provisioning.A_zimbraMailUncompressedCacheMaxFiles, 5000);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @param zimbraMailUncompressedCacheMaxFiles new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public void setMailUncompressedCacheMaxFiles(int zimbraMailUncompressedCacheMaxFiles) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxFiles, Integer.toString(zimbraMailUncompressedCacheMaxFiles));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @param zimbraMailUncompressedCacheMaxFiles new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public Map<String,Object> setMailUncompressedCacheMaxFiles(int zimbraMailUncompressedCacheMaxFiles, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxFiles, Integer.toString(zimbraMailUncompressedCacheMaxFiles));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public void unsetMailUncompressedCacheMaxFiles() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxFiles, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zimbraMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public Map<String,Object> unsetMailUncompressedCacheMaxFiles(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUncompressedCacheMaxFiles, "");
        return attrs;
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @return zimbraMailUseDirectBuffers, or false if unset
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public boolean isMailUseDirectBuffers() {
        return getBooleanAttr(Provisioning.A_zimbraMailUseDirectBuffers, false);
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @param zimbraMailUseDirectBuffers new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public void setMailUseDirectBuffers(boolean zimbraMailUseDirectBuffers) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUseDirectBuffers, zimbraMailUseDirectBuffers ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @param zimbraMailUseDirectBuffers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public Map<String,Object> setMailUseDirectBuffers(boolean zimbraMailUseDirectBuffers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUseDirectBuffers, zimbraMailUseDirectBuffers ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public void unsetMailUseDirectBuffers() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUseDirectBuffers, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public Map<String,Object> unsetMailUseDirectBuffers(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailUseDirectBuffers, "");
        return attrs;
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @return zimbraMailboxMoveSkipBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public boolean isMailboxMoveSkipBlobs() {
        return getBooleanAttr(Provisioning.A_zimbraMailboxMoveSkipBlobs, false);
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @param zimbraMailboxMoveSkipBlobs new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public void setMailboxMoveSkipBlobs(boolean zimbraMailboxMoveSkipBlobs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipBlobs, zimbraMailboxMoveSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @param zimbraMailboxMoveSkipBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public Map<String,Object> setMailboxMoveSkipBlobs(boolean zimbraMailboxMoveSkipBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipBlobs, zimbraMailboxMoveSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public void unsetMailboxMoveSkipBlobs() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipBlobs, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public Map<String,Object> unsetMailboxMoveSkipBlobs(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipBlobs, "");
        return attrs;
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @return zimbraMailboxMoveSkipHsmBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public boolean isMailboxMoveSkipHsmBlobs() {
        return getBooleanAttr(Provisioning.A_zimbraMailboxMoveSkipHsmBlobs, false);
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @param zimbraMailboxMoveSkipHsmBlobs new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public void setMailboxMoveSkipHsmBlobs(boolean zimbraMailboxMoveSkipHsmBlobs) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipHsmBlobs, zimbraMailboxMoveSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @param zimbraMailboxMoveSkipHsmBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public Map<String,Object> setMailboxMoveSkipHsmBlobs(boolean zimbraMailboxMoveSkipHsmBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipHsmBlobs, zimbraMailboxMoveSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public void unsetMailboxMoveSkipHsmBlobs() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipHsmBlobs, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public Map<String,Object> unsetMailboxMoveSkipHsmBlobs(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipHsmBlobs, "");
        return attrs;
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @return zimbraMailboxMoveSkipSearchIndex, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public boolean isMailboxMoveSkipSearchIndex() {
        return getBooleanAttr(Provisioning.A_zimbraMailboxMoveSkipSearchIndex, false);
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @param zimbraMailboxMoveSkipSearchIndex new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public void setMailboxMoveSkipSearchIndex(boolean zimbraMailboxMoveSkipSearchIndex) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipSearchIndex, zimbraMailboxMoveSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @param zimbraMailboxMoveSkipSearchIndex new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public Map<String,Object> setMailboxMoveSkipSearchIndex(boolean zimbraMailboxMoveSkipSearchIndex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipSearchIndex, zimbraMailboxMoveSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public void unsetMailboxMoveSkipSearchIndex() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipSearchIndex, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public Map<String,Object> unsetMailboxMoveSkipSearchIndex(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveSkipSearchIndex, "");
        return attrs;
    }

    /**
     * temp directory for mailbox move
     *
     * @return zimbraMailboxMoveTempDir, or "/opt/zimbra/backup/tmp/mboxmove" if unset
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public String getMailboxMoveTempDir() {
        return getAttr(Provisioning.A_zimbraMailboxMoveTempDir, "/opt/zimbra/backup/tmp/mboxmove");
    }

    /**
     * temp directory for mailbox move
     *
     * @param zimbraMailboxMoveTempDir new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public void setMailboxMoveTempDir(String zimbraMailboxMoveTempDir) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveTempDir, zimbraMailboxMoveTempDir);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * temp directory for mailbox move
     *
     * @param zimbraMailboxMoveTempDir new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public Map<String,Object> setMailboxMoveTempDir(String zimbraMailboxMoveTempDir, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveTempDir, zimbraMailboxMoveTempDir);
        return attrs;
    }

    /**
     * temp directory for mailbox move
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public void unsetMailboxMoveTempDir() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveTempDir, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * temp directory for mailbox move
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public Map<String,Object> unsetMailboxMoveTempDir(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMailboxMoveTempDir, "");
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @return zimbraMemcachedBindAddress, or empty array if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public String[] getMemcachedBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraMemcachedBindAddress);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zimbraMemcachedBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void setMemcachedBindAddress(String[] zimbraMemcachedBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindAddress, zimbraMemcachedBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zimbraMemcachedBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> setMemcachedBindAddress(String[] zimbraMemcachedBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindAddress, zimbraMemcachedBindAddress);
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zimbraMemcachedBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void addMemcachedBindAddress(String zimbraMemcachedBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMemcachedBindAddress, zimbraMemcachedBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zimbraMemcachedBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> addMemcachedBindAddress(String zimbraMemcachedBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMemcachedBindAddress, zimbraMemcachedBindAddress);
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zimbraMemcachedBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void removeMemcachedBindAddress(String zimbraMemcachedBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMemcachedBindAddress, zimbraMemcachedBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zimbraMemcachedBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> removeMemcachedBindAddress(String zimbraMemcachedBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMemcachedBindAddress, zimbraMemcachedBindAddress);
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void unsetMemcachedBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> unsetMemcachedBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindAddress, "");
        return attrs;
    }

    /**
     * port number on which memcached server should listen
     *
     * <p>Use getMemcachedBindPortAsString to access value as a string.
     *
     * @see #getMemcachedBindPortAsString()
     *
     * @return zimbraMemcachedBindPort, or 11211 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public int getMemcachedBindPort() {
        return getIntAttr(Provisioning.A_zimbraMemcachedBindPort, 11211);
    }

    /**
     * port number on which memcached server should listen
     *
     * @return zimbraMemcachedBindPort, or "11211" if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public String getMemcachedBindPortAsString() {
        return getAttr(Provisioning.A_zimbraMemcachedBindPort, "11211");
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zimbraMemcachedBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public void setMemcachedBindPort(int zimbraMemcachedBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindPort, Integer.toString(zimbraMemcachedBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zimbraMemcachedBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public Map<String,Object> setMemcachedBindPort(int zimbraMemcachedBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindPort, Integer.toString(zimbraMemcachedBindPort));
        return attrs;
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zimbraMemcachedBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public void setMemcachedBindPortAsString(String zimbraMemcachedBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindPort, zimbraMemcachedBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zimbraMemcachedBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public Map<String,Object> setMemcachedBindPortAsString(String zimbraMemcachedBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindPort, zimbraMemcachedBindPort);
        return attrs;
    }

    /**
     * port number on which memcached server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public void unsetMemcachedBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which memcached server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public Map<String,Object> unsetMemcachedBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedBindPort, "");
        return attrs;
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @return zimbraMemcachedClientBinaryProtocolEnabled, or false if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public boolean isMemcachedClientBinaryProtocolEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMemcachedClientBinaryProtocolEnabled, false);
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @param zimbraMemcachedClientBinaryProtocolEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public void setMemcachedClientBinaryProtocolEnabled(boolean zimbraMemcachedClientBinaryProtocolEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientBinaryProtocolEnabled, zimbraMemcachedClientBinaryProtocolEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @param zimbraMemcachedClientBinaryProtocolEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public Map<String,Object> setMemcachedClientBinaryProtocolEnabled(boolean zimbraMemcachedClientBinaryProtocolEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientBinaryProtocolEnabled, zimbraMemcachedClientBinaryProtocolEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public void unsetMemcachedClientBinaryProtocolEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientBinaryProtocolEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public Map<String,Object> unsetMemcachedClientBinaryProtocolEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientBinaryProtocolEnabled, "");
        return attrs;
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @return zimbraMemcachedClientExpirySeconds, or 86400 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public int getMemcachedClientExpirySeconds() {
        return getIntAttr(Provisioning.A_zimbraMemcachedClientExpirySeconds, 86400);
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @param zimbraMemcachedClientExpirySeconds new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public void setMemcachedClientExpirySeconds(int zimbraMemcachedClientExpirySeconds) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientExpirySeconds, Integer.toString(zimbraMemcachedClientExpirySeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @param zimbraMemcachedClientExpirySeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public Map<String,Object> setMemcachedClientExpirySeconds(int zimbraMemcachedClientExpirySeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientExpirySeconds, Integer.toString(zimbraMemcachedClientExpirySeconds));
        return attrs;
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public void unsetMemcachedClientExpirySeconds() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientExpirySeconds, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public Map<String,Object> unsetMemcachedClientExpirySeconds(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientExpirySeconds, "");
        return attrs;
    }

    /**
     * memcached hash algorithm
     *
     * @return zimbraMemcachedClientHashAlgorithm, or "KETAMA_HASH" if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public String getMemcachedClientHashAlgorithm() {
        return getAttr(Provisioning.A_zimbraMemcachedClientHashAlgorithm, "KETAMA_HASH");
    }

    /**
     * memcached hash algorithm
     *
     * @param zimbraMemcachedClientHashAlgorithm new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public void setMemcachedClientHashAlgorithm(String zimbraMemcachedClientHashAlgorithm) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientHashAlgorithm, zimbraMemcachedClientHashAlgorithm);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * memcached hash algorithm
     *
     * @param zimbraMemcachedClientHashAlgorithm new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public Map<String,Object> setMemcachedClientHashAlgorithm(String zimbraMemcachedClientHashAlgorithm, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientHashAlgorithm, zimbraMemcachedClientHashAlgorithm);
        return attrs;
    }

    /**
     * memcached hash algorithm
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public void unsetMemcachedClientHashAlgorithm() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientHashAlgorithm, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * memcached hash algorithm
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public Map<String,Object> unsetMemcachedClientHashAlgorithm(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientHashAlgorithm, "");
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @return zimbraMemcachedClientServerList, or empty array if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public String[] getMemcachedClientServerList() {
        return getMultiAttr(Provisioning.A_zimbraMemcachedClientServerList);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zimbraMemcachedClientServerList new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void setMemcachedClientServerList(String[] zimbraMemcachedClientServerList) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientServerList, zimbraMemcachedClientServerList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zimbraMemcachedClientServerList new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> setMemcachedClientServerList(String[] zimbraMemcachedClientServerList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientServerList, zimbraMemcachedClientServerList);
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zimbraMemcachedClientServerList new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void addMemcachedClientServerList(String zimbraMemcachedClientServerList) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMemcachedClientServerList, zimbraMemcachedClientServerList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zimbraMemcachedClientServerList new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> addMemcachedClientServerList(String zimbraMemcachedClientServerList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMemcachedClientServerList, zimbraMemcachedClientServerList);
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zimbraMemcachedClientServerList existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void removeMemcachedClientServerList(String zimbraMemcachedClientServerList) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMemcachedClientServerList, zimbraMemcachedClientServerList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zimbraMemcachedClientServerList existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> removeMemcachedClientServerList(String zimbraMemcachedClientServerList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMemcachedClientServerList, zimbraMemcachedClientServerList);
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void unsetMemcachedClientServerList() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientServerList, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> unsetMemcachedClientServerList(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientServerList, "");
        return attrs;
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @return zimbraMemcachedClientTimeoutMillis, or 10000 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public int getMemcachedClientTimeoutMillis() {
        return getIntAttr(Provisioning.A_zimbraMemcachedClientTimeoutMillis, 10000);
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @param zimbraMemcachedClientTimeoutMillis new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public void setMemcachedClientTimeoutMillis(int zimbraMemcachedClientTimeoutMillis) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientTimeoutMillis, Integer.toString(zimbraMemcachedClientTimeoutMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @param zimbraMemcachedClientTimeoutMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public Map<String,Object> setMemcachedClientTimeoutMillis(int zimbraMemcachedClientTimeoutMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientTimeoutMillis, Integer.toString(zimbraMemcachedClientTimeoutMillis));
        return attrs;
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public void unsetMemcachedClientTimeoutMillis() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientTimeoutMillis, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public Map<String,Object> unsetMemcachedClientTimeoutMillis(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMemcachedClientTimeoutMillis, "");
        return attrs;
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @return zimbraMessageCacheSize, or 2000 if unset
     */
    @ZAttr(id=297)
    public int getMessageCacheSize() {
        return getIntAttr(Provisioning.A_zimbraMessageCacheSize, 2000);
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @param zimbraMessageCacheSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=297)
    public void setMessageCacheSize(int zimbraMessageCacheSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageCacheSize, Integer.toString(zimbraMessageCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @param zimbraMessageCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=297)
    public Map<String,Object> setMessageCacheSize(int zimbraMessageCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageCacheSize, Integer.toString(zimbraMessageCacheSize));
        return attrs;
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=297)
    public void unsetMessageCacheSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageCacheSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=297)
    public Map<String,Object> unsetMessageCacheSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageCacheSize, "");
        return attrs;
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @return zimbraMessageChannelEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public boolean isMessageChannelEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMessageChannelEnabled, false);
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @param zimbraMessageChannelEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public void setMessageChannelEnabled(boolean zimbraMessageChannelEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelEnabled, zimbraMessageChannelEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @param zimbraMessageChannelEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public Map<String,Object> setMessageChannelEnabled(boolean zimbraMessageChannelEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelEnabled, zimbraMessageChannelEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public void unsetMessageChannelEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public Map<String,Object> unsetMessageChannelEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelEnabled, "");
        return attrs;
    }

    /**
     * port number on which message channel should listen
     *
     * @return zimbraMessageChannelPort, or 7285 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public int getMessageChannelPort() {
        return getIntAttr(Provisioning.A_zimbraMessageChannelPort, 7285);
    }

    /**
     * port number on which message channel should listen
     *
     * @param zimbraMessageChannelPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public void setMessageChannelPort(int zimbraMessageChannelPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelPort, Integer.toString(zimbraMessageChannelPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which message channel should listen
     *
     * @param zimbraMessageChannelPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public Map<String,Object> setMessageChannelPort(int zimbraMessageChannelPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelPort, Integer.toString(zimbraMessageChannelPort));
        return attrs;
    }

    /**
     * port number on which message channel should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public void unsetMessageChannelPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which message channel should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public Map<String,Object> unsetMessageChannelPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMessageChannelPort, "");
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @return zimbraMilterBindAddress, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public String[] getMilterBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraMilterBindAddress);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zimbraMilterBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void setMilterBindAddress(String[] zimbraMilterBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindAddress, zimbraMilterBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zimbraMilterBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> setMilterBindAddress(String[] zimbraMilterBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindAddress, zimbraMilterBindAddress);
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zimbraMilterBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void addMilterBindAddress(String zimbraMilterBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMilterBindAddress, zimbraMilterBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zimbraMilterBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> addMilterBindAddress(String zimbraMilterBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMilterBindAddress, zimbraMilterBindAddress);
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zimbraMilterBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void removeMilterBindAddress(String zimbraMilterBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMilterBindAddress, zimbraMilterBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zimbraMilterBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> removeMilterBindAddress(String zimbraMilterBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMilterBindAddress, zimbraMilterBindAddress);
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void unsetMilterBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> unsetMilterBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindAddress, "");
        return attrs;
    }

    /**
     * port number on which milter server should listen
     *
     * <p>Use getMilterBindPortAsString to access value as a string.
     *
     * @see #getMilterBindPortAsString()
     *
     * @return zimbraMilterBindPort, or 7026 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public int getMilterBindPort() {
        return getIntAttr(Provisioning.A_zimbraMilterBindPort, 7026);
    }

    /**
     * port number on which milter server should listen
     *
     * @return zimbraMilterBindPort, or "7026" if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public String getMilterBindPortAsString() {
        return getAttr(Provisioning.A_zimbraMilterBindPort, "7026");
    }

    /**
     * port number on which milter server should listen
     *
     * @param zimbraMilterBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public void setMilterBindPort(int zimbraMilterBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindPort, Integer.toString(zimbraMilterBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which milter server should listen
     *
     * @param zimbraMilterBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public Map<String,Object> setMilterBindPort(int zimbraMilterBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindPort, Integer.toString(zimbraMilterBindPort));
        return attrs;
    }

    /**
     * port number on which milter server should listen
     *
     * @param zimbraMilterBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public void setMilterBindPortAsString(String zimbraMilterBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindPort, zimbraMilterBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which milter server should listen
     *
     * @param zimbraMilterBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public Map<String,Object> setMilterBindPortAsString(String zimbraMilterBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindPort, zimbraMilterBindPort);
        return attrs;
    }

    /**
     * port number on which milter server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public void unsetMilterBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which milter server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public Map<String,Object> unsetMilterBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterBindPort, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @return zimbraMilterMaxConnections, or 20000 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public int getMilterMaxConnections() {
        return getIntAttr(Provisioning.A_zimbraMilterMaxConnections, 20000);
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @param zimbraMilterMaxConnections new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public void setMilterMaxConnections(int zimbraMilterMaxConnections) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterMaxConnections, Integer.toString(zimbraMilterMaxConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @param zimbraMilterMaxConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public Map<String,Object> setMilterMaxConnections(int zimbraMilterMaxConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterMaxConnections, Integer.toString(zimbraMilterMaxConnections));
        return attrs;
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public void unsetMilterMaxConnections() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterMaxConnections, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public Map<String,Object> unsetMilterMaxConnections(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterMaxConnections, "");
        return attrs;
    }

    /**
     * number of milter handler threads
     *
     * @return zimbraMilterNumThreads, or 100 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public int getMilterNumThreads() {
        return getIntAttr(Provisioning.A_zimbraMilterNumThreads, 100);
    }

    /**
     * number of milter handler threads
     *
     * @param zimbraMilterNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public void setMilterNumThreads(int zimbraMilterNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterNumThreads, Integer.toString(zimbraMilterNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of milter handler threads
     *
     * @param zimbraMilterNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public Map<String,Object> setMilterNumThreads(int zimbraMilterNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterNumThreads, Integer.toString(zimbraMilterNumThreads));
        return attrs;
    }

    /**
     * number of milter handler threads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public void unsetMilterNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of milter handler threads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public Map<String,Object> unsetMilterNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterNumThreads, "");
        return attrs;
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @return zimbraMilterServerEnabled, or false if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public boolean isMilterServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMilterServerEnabled, false);
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @param zimbraMilterServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public void setMilterServerEnabled(boolean zimbraMilterServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterServerEnabled, zimbraMilterServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @param zimbraMilterServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public Map<String,Object> setMilterServerEnabled(boolean zimbraMilterServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterServerEnabled, zimbraMilterServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public void unsetMilterServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public Map<String,Object> unsetMilterServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMilterServerEnabled, "");
        return attrs;
    }

    /**
     * mta anti spam lock method.
     *
     * @return zimbraMtaAntiSpamLockMethod, or "flock" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public String getMtaAntiSpamLockMethod() {
        return getAttr(Provisioning.A_zimbraMtaAntiSpamLockMethod, "flock");
    }

    /**
     * mta anti spam lock method.
     *
     * @param zimbraMtaAntiSpamLockMethod new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public void setMtaAntiSpamLockMethod(String zimbraMtaAntiSpamLockMethod) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAntiSpamLockMethod, zimbraMtaAntiSpamLockMethod);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mta anti spam lock method.
     *
     * @param zimbraMtaAntiSpamLockMethod new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public Map<String,Object> setMtaAntiSpamLockMethod(String zimbraMtaAntiSpamLockMethod, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAntiSpamLockMethod, zimbraMtaAntiSpamLockMethod);
        return attrs;
    }

    /**
     * mta anti spam lock method.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public void unsetMtaAntiSpamLockMethod() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAntiSpamLockMethod, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mta anti spam lock method.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public Map<String,Object> unsetMtaAntiSpamLockMethod(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAntiSpamLockMethod, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zimbraMtaTlsSecurityLevel and zimbraMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @return zimbraMtaAuthEnabled, or true if unset
     */
    @ZAttr(id=194)
    public boolean isMtaAuthEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMtaAuthEnabled, true);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zimbraMtaTlsSecurityLevel and zimbraMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @param zimbraMtaAuthEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=194)
    public void setMtaAuthEnabled(boolean zimbraMtaAuthEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthEnabled, zimbraMtaAuthEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zimbraMtaTlsSecurityLevel and zimbraMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @param zimbraMtaAuthEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=194)
    public Map<String,Object> setMtaAuthEnabled(boolean zimbraMtaAuthEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthEnabled, zimbraMtaAuthEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zimbraMtaTlsSecurityLevel and zimbraMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=194)
    public void unsetMtaAuthEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zimbraMtaTlsSecurityLevel and zimbraMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=194)
    public Map<String,Object> unsetMtaAuthEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthEnabled, "");
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @return zimbraMtaAuthHost, or empty array if unset
     */
    @ZAttr(id=309)
    public String[] getMtaAuthHost() {
        return getMultiAttr(Provisioning.A_zimbraMtaAuthHost);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param zimbraMtaAuthHost new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void setMtaAuthHost(String[] zimbraMtaAuthHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthHost, zimbraMtaAuthHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param zimbraMtaAuthHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> setMtaAuthHost(String[] zimbraMtaAuthHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthHost, zimbraMtaAuthHost);
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param zimbraMtaAuthHost new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void addMtaAuthHost(String zimbraMtaAuthHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaAuthHost, zimbraMtaAuthHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param zimbraMtaAuthHost new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> addMtaAuthHost(String zimbraMtaAuthHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaAuthHost, zimbraMtaAuthHost);
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param zimbraMtaAuthHost existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void removeMtaAuthHost(String zimbraMtaAuthHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaAuthHost, zimbraMtaAuthHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param zimbraMtaAuthHost existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> removeMtaAuthHost(String zimbraMtaAuthHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaAuthHost, zimbraMtaAuthHost);
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void unsetMtaAuthHost() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthHost, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zimbraMtaAuthURL via attr callback mechanism.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> unsetMtaAuthHost(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthHost, "");
        return attrs;
    }

    /**
     * whether this server is a mta auth target
     *
     * @return zimbraMtaAuthTarget, or false if unset
     */
    @ZAttr(id=505)
    public boolean isMtaAuthTarget() {
        return getBooleanAttr(Provisioning.A_zimbraMtaAuthTarget, false);
    }

    /**
     * whether this server is a mta auth target
     *
     * @param zimbraMtaAuthTarget new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=505)
    public void setMtaAuthTarget(boolean zimbraMtaAuthTarget) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthTarget, zimbraMtaAuthTarget ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether this server is a mta auth target
     *
     * @param zimbraMtaAuthTarget new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=505)
    public Map<String,Object> setMtaAuthTarget(boolean zimbraMtaAuthTarget, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthTarget, zimbraMtaAuthTarget ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether this server is a mta auth target
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=505)
    public void unsetMtaAuthTarget() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthTarget, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether this server is a mta auth target
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=505)
    public Map<String,Object> unsetMtaAuthTarget(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthTarget, "");
        return attrs;
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @return zimbraMtaAuthURL, or empty array if unset
     */
    @ZAttr(id=310)
    public String[] getMtaAuthURL() {
        return getMultiAttr(Provisioning.A_zimbraMtaAuthURL);
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param zimbraMtaAuthURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void setMtaAuthURL(String[] zimbraMtaAuthURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthURL, zimbraMtaAuthURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param zimbraMtaAuthURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> setMtaAuthURL(String[] zimbraMtaAuthURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthURL, zimbraMtaAuthURL);
        return attrs;
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param zimbraMtaAuthURL new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void addMtaAuthURL(String zimbraMtaAuthURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaAuthURL, zimbraMtaAuthURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param zimbraMtaAuthURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> addMtaAuthURL(String zimbraMtaAuthURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaAuthURL, zimbraMtaAuthURL);
        return attrs;
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param zimbraMtaAuthURL existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void removeMtaAuthURL(String zimbraMtaAuthURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaAuthURL, zimbraMtaAuthURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param zimbraMtaAuthURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> removeMtaAuthURL(String zimbraMtaAuthURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaAuthURL, zimbraMtaAuthURL);
        return attrs;
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void unsetMtaAuthURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zimbra saslauthd) should authenticate. Set
     * by setting zimbraMtaAuthHost.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> unsetMtaAuthURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaAuthURL, "");
        return attrs;
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @return zimbraMtaDnsLookupsEnabled, or true if unset
     */
    @ZAttr(id=197)
    public boolean isMtaDnsLookupsEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraMtaDnsLookupsEnabled, true);
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @param zimbraMtaDnsLookupsEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=197)
    public void setMtaDnsLookupsEnabled(boolean zimbraMtaDnsLookupsEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaDnsLookupsEnabled, zimbraMtaDnsLookupsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @param zimbraMtaDnsLookupsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=197)
    public Map<String,Object> setMtaDnsLookupsEnabled(boolean zimbraMtaDnsLookupsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaDnsLookupsEnabled, zimbraMtaDnsLookupsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=197)
    public void unsetMtaDnsLookupsEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaDnsLookupsEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=197)
    public Map<String,Object> unsetMtaDnsLookupsEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaDnsLookupsEnabled, "");
        return attrs;
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @return zimbraMtaFallbackRelayHost, or null if unset
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public String getMtaFallbackRelayHost() {
        return getAttr(Provisioning.A_zimbraMtaFallbackRelayHost, null);
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @param zimbraMtaFallbackRelayHost new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public void setMtaFallbackRelayHost(String zimbraMtaFallbackRelayHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaFallbackRelayHost, zimbraMtaFallbackRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @param zimbraMtaFallbackRelayHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public Map<String,Object> setMtaFallbackRelayHost(String zimbraMtaFallbackRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaFallbackRelayHost, zimbraMtaFallbackRelayHost);
        return attrs;
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public void unsetMtaFallbackRelayHost() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaFallbackRelayHost, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public Map<String,Object> unsetMtaFallbackRelayHost(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaFallbackRelayHost, "");
        return attrs;
    }

    /**
     * value of postfix mydestination
     *
     * @return zimbraMtaMyDestination, or "localhost" if unset
     */
    @ZAttr(id=524)
    public String getMtaMyDestination() {
        return getAttr(Provisioning.A_zimbraMtaMyDestination, "localhost");
    }

    /**
     * value of postfix mydestination
     *
     * @param zimbraMtaMyDestination new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=524)
    public void setMtaMyDestination(String zimbraMtaMyDestination) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyDestination, zimbraMtaMyDestination);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mydestination
     *
     * @param zimbraMtaMyDestination new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=524)
    public Map<String,Object> setMtaMyDestination(String zimbraMtaMyDestination, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyDestination, zimbraMtaMyDestination);
        return attrs;
    }

    /**
     * value of postfix mydestination
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=524)
    public void unsetMtaMyDestination() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyDestination, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mydestination
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=524)
    public Map<String,Object> unsetMtaMyDestination(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyDestination, "");
        return attrs;
    }

    /**
     * value of postfix myhostname
     *
     * @return zimbraMtaMyHostname, or null if unset
     */
    @ZAttr(id=509)
    public String getMtaMyHostname() {
        return getAttr(Provisioning.A_zimbraMtaMyHostname, null);
    }

    /**
     * value of postfix myhostname
     *
     * @param zimbraMtaMyHostname new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=509)
    public void setMtaMyHostname(String zimbraMtaMyHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyHostname, zimbraMtaMyHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix myhostname
     *
     * @param zimbraMtaMyHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=509)
    public Map<String,Object> setMtaMyHostname(String zimbraMtaMyHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyHostname, zimbraMtaMyHostname);
        return attrs;
    }

    /**
     * value of postfix myhostname
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=509)
    public void unsetMtaMyHostname() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix myhostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=509)
    public Map<String,Object> unsetMtaMyHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyHostname, "");
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @return zimbraMtaMyNetworks, or empty array if unset
     */
    @ZAttr(id=311)
    public String[] getMtaMyNetworks() {
        return getMultiAttr(Provisioning.A_zimbraMtaMyNetworks);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zimbraMtaMyNetworks new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void setMtaMyNetworks(String[] zimbraMtaMyNetworks) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyNetworks, zimbraMtaMyNetworks);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zimbraMtaMyNetworks new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> setMtaMyNetworks(String[] zimbraMtaMyNetworks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyNetworks, zimbraMtaMyNetworks);
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @param zimbraMtaMyNetworks new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void addMtaMyNetworks(String zimbraMtaMyNetworks) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaMyNetworks, zimbraMtaMyNetworks);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zimbraMtaMyNetworks new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> addMtaMyNetworks(String zimbraMtaMyNetworks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaMyNetworks, zimbraMtaMyNetworks);
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @param zimbraMtaMyNetworks existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void removeMtaMyNetworks(String zimbraMtaMyNetworks) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaMyNetworks, zimbraMtaMyNetworks);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zimbraMtaMyNetworks existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> removeMtaMyNetworks(String zimbraMtaMyNetworks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaMyNetworks, zimbraMtaMyNetworks);
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void unsetMtaMyNetworks() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyNetworks, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> unsetMtaMyNetworks(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyNetworks, "");
        return attrs;
    }

    /**
     * value of postfix myorigin
     *
     * @return zimbraMtaMyOrigin, or null if unset
     */
    @ZAttr(id=510)
    public String getMtaMyOrigin() {
        return getAttr(Provisioning.A_zimbraMtaMyOrigin, null);
    }

    /**
     * value of postfix myorigin
     *
     * @param zimbraMtaMyOrigin new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=510)
    public void setMtaMyOrigin(String zimbraMtaMyOrigin) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyOrigin, zimbraMtaMyOrigin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix myorigin
     *
     * @param zimbraMtaMyOrigin new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=510)
    public Map<String,Object> setMtaMyOrigin(String zimbraMtaMyOrigin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyOrigin, zimbraMtaMyOrigin);
        return attrs;
    }

    /**
     * value of postfix myorigin
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=510)
    public void unsetMtaMyOrigin() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyOrigin, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix myorigin
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=510)
    public Map<String,Object> unsetMtaMyOrigin(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaMyOrigin, "");
        return attrs;
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @return zimbraMtaNonSmtpdMilters, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public String getMtaNonSmtpdMilters() {
        return getAttr(Provisioning.A_zimbraMtaNonSmtpdMilters, null);
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @param zimbraMtaNonSmtpdMilters new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public void setMtaNonSmtpdMilters(String zimbraMtaNonSmtpdMilters) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaNonSmtpdMilters, zimbraMtaNonSmtpdMilters);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @param zimbraMtaNonSmtpdMilters new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public Map<String,Object> setMtaNonSmtpdMilters(String zimbraMtaNonSmtpdMilters, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaNonSmtpdMilters, zimbraMtaNonSmtpdMilters);
        return attrs;
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public void unsetMtaNonSmtpdMilters() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaNonSmtpdMilters, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public Map<String,Object> unsetMtaNonSmtpdMilters(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaNonSmtpdMilters, "");
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @return zimbraMtaRelayHost, or empty array if unset
     */
    @ZAttr(id=199)
    public String[] getMtaRelayHost() {
        return getMultiAttr(Provisioning.A_zimbraMtaRelayHost);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zimbraMtaRelayHost new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void setMtaRelayHost(String[] zimbraMtaRelayHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaRelayHost, zimbraMtaRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zimbraMtaRelayHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> setMtaRelayHost(String[] zimbraMtaRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaRelayHost, zimbraMtaRelayHost);
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zimbraMtaRelayHost new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void addMtaRelayHost(String zimbraMtaRelayHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaRelayHost, zimbraMtaRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zimbraMtaRelayHost new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> addMtaRelayHost(String zimbraMtaRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraMtaRelayHost, zimbraMtaRelayHost);
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zimbraMtaRelayHost existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void removeMtaRelayHost(String zimbraMtaRelayHost) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaRelayHost, zimbraMtaRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zimbraMtaRelayHost existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> removeMtaRelayHost(String zimbraMtaRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraMtaRelayHost, zimbraMtaRelayHost);
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void unsetMtaRelayHost() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaRelayHost, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> unsetMtaRelayHost(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaRelayHost, "");
        return attrs;
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @return zimbraMtaSaslAuthEnable, or ZAttrProvisioning.MtaSaslAuthEnable.yes if unset and/or has invalid value
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public ZAttrProvisioning.MtaSaslAuthEnable getMtaSaslAuthEnable() {
        try { String v = getAttr(Provisioning.A_zimbraMtaSaslAuthEnable); return v == null ? ZAttrProvisioning.MtaSaslAuthEnable.yes : ZAttrProvisioning.MtaSaslAuthEnable.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.MtaSaslAuthEnable.yes; }
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @return zimbraMtaSaslAuthEnable, or "yes" if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public String getMtaSaslAuthEnableAsString() {
        return getAttr(Provisioning.A_zimbraMtaSaslAuthEnable, "yes");
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zimbraMtaSaslAuthEnable new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public void setMtaSaslAuthEnable(ZAttrProvisioning.MtaSaslAuthEnable zimbraMtaSaslAuthEnable) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSaslAuthEnable, zimbraMtaSaslAuthEnable.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zimbraMtaSaslAuthEnable new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public Map<String,Object> setMtaSaslAuthEnable(ZAttrProvisioning.MtaSaslAuthEnable zimbraMtaSaslAuthEnable, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSaslAuthEnable, zimbraMtaSaslAuthEnable.toString());
        return attrs;
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zimbraMtaSaslAuthEnable new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public void setMtaSaslAuthEnableAsString(String zimbraMtaSaslAuthEnable) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSaslAuthEnable, zimbraMtaSaslAuthEnable);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zimbraMtaSaslAuthEnable new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public Map<String,Object> setMtaSaslAuthEnableAsString(String zimbraMtaSaslAuthEnable, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSaslAuthEnable, zimbraMtaSaslAuthEnable);
        return attrs;
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public void unsetMtaSaslAuthEnable() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSaslAuthEnable, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public Map<String,Object> unsetMtaSaslAuthEnable(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSaslAuthEnable, "");
        return attrs;
    }

    /**
     * value for postfix smtpd_milters
     *
     * @return zimbraMtaSmtpdMilters, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public String getMtaSmtpdMilters() {
        return getAttr(Provisioning.A_zimbraMtaSmtpdMilters, null);
    }

    /**
     * value for postfix smtpd_milters
     *
     * @param zimbraMtaSmtpdMilters new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public void setMtaSmtpdMilters(String zimbraMtaSmtpdMilters) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSmtpdMilters, zimbraMtaSmtpdMilters);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value for postfix smtpd_milters
     *
     * @param zimbraMtaSmtpdMilters new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public Map<String,Object> setMtaSmtpdMilters(String zimbraMtaSmtpdMilters, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSmtpdMilters, zimbraMtaSmtpdMilters);
        return attrs;
    }

    /**
     * value for postfix smtpd_milters
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public void unsetMtaSmtpdMilters() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSmtpdMilters, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value for postfix smtpd_milters
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public Map<String,Object> unsetMtaSmtpdMilters(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaSmtpdMilters, "");
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @return zimbraMtaTlsAuthOnly, or true if unset
     */
    @ZAttr(id=200)
    public boolean isMtaTlsAuthOnly() {
        return getBooleanAttr(Provisioning.A_zimbraMtaTlsAuthOnly, true);
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @param zimbraMtaTlsAuthOnly new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=200)
    public void setMtaTlsAuthOnly(boolean zimbraMtaTlsAuthOnly) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsAuthOnly, zimbraMtaTlsAuthOnly ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @param zimbraMtaTlsAuthOnly new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=200)
    public Map<String,Object> setMtaTlsAuthOnly(boolean zimbraMtaTlsAuthOnly, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsAuthOnly, zimbraMtaTlsAuthOnly ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=200)
    public void unsetMtaTlsAuthOnly() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsAuthOnly, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=200)
    public Map<String,Object> unsetMtaTlsAuthOnly(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsAuthOnly, "");
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @return zimbraMtaTlsSecurityLevel, or ZAttrProvisioning.MtaTlsSecurityLevel.may if unset and/or has invalid value
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public ZAttrProvisioning.MtaTlsSecurityLevel getMtaTlsSecurityLevel() {
        try { String v = getAttr(Provisioning.A_zimbraMtaTlsSecurityLevel); return v == null ? ZAttrProvisioning.MtaTlsSecurityLevel.may : ZAttrProvisioning.MtaTlsSecurityLevel.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.MtaTlsSecurityLevel.may; }
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @return zimbraMtaTlsSecurityLevel, or "may" if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public String getMtaTlsSecurityLevelAsString() {
        return getAttr(Provisioning.A_zimbraMtaTlsSecurityLevel, "may");
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zimbraMtaTlsSecurityLevel new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public void setMtaTlsSecurityLevel(ZAttrProvisioning.MtaTlsSecurityLevel zimbraMtaTlsSecurityLevel) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsSecurityLevel, zimbraMtaTlsSecurityLevel.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zimbraMtaTlsSecurityLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public Map<String,Object> setMtaTlsSecurityLevel(ZAttrProvisioning.MtaTlsSecurityLevel zimbraMtaTlsSecurityLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsSecurityLevel, zimbraMtaTlsSecurityLevel.toString());
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zimbraMtaTlsSecurityLevel new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public void setMtaTlsSecurityLevelAsString(String zimbraMtaTlsSecurityLevel) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsSecurityLevel, zimbraMtaTlsSecurityLevel);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zimbraMtaTlsSecurityLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public Map<String,Object> setMtaTlsSecurityLevelAsString(String zimbraMtaTlsSecurityLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsSecurityLevel, zimbraMtaTlsSecurityLevel);
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public void unsetMtaTlsSecurityLevel() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsSecurityLevel, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public Map<String,Object> unsetMtaTlsSecurityLevel(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraMtaTlsSecurityLevel, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @return zimbraNotebookFolderCacheSize, or 1024 if unset
     */
    @ZAttr(id=370)
    public int getNotebookFolderCacheSize() {
        return getIntAttr(Provisioning.A_zimbraNotebookFolderCacheSize, 1024);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @param zimbraNotebookFolderCacheSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=370)
    public void setNotebookFolderCacheSize(int zimbraNotebookFolderCacheSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookFolderCacheSize, Integer.toString(zimbraNotebookFolderCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @param zimbraNotebookFolderCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=370)
    public Map<String,Object> setNotebookFolderCacheSize(int zimbraNotebookFolderCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookFolderCacheSize, Integer.toString(zimbraNotebookFolderCacheSize));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=370)
    public void unsetNotebookFolderCacheSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookFolderCacheSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=370)
    public Map<String,Object> unsetNotebookFolderCacheSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookFolderCacheSize, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @return zimbraNotebookMaxCachedTemplatesPerFolder, or 256 if unset
     */
    @ZAttr(id=371)
    public int getNotebookMaxCachedTemplatesPerFolder() {
        return getIntAttr(Provisioning.A_zimbraNotebookMaxCachedTemplatesPerFolder, 256);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @param zimbraNotebookMaxCachedTemplatesPerFolder new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=371)
    public void setNotebookMaxCachedTemplatesPerFolder(int zimbraNotebookMaxCachedTemplatesPerFolder) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookMaxCachedTemplatesPerFolder, Integer.toString(zimbraNotebookMaxCachedTemplatesPerFolder));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @param zimbraNotebookMaxCachedTemplatesPerFolder new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=371)
    public Map<String,Object> setNotebookMaxCachedTemplatesPerFolder(int zimbraNotebookMaxCachedTemplatesPerFolder, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookMaxCachedTemplatesPerFolder, Integer.toString(zimbraNotebookMaxCachedTemplatesPerFolder));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=371)
    public void unsetNotebookMaxCachedTemplatesPerFolder() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookMaxCachedTemplatesPerFolder, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=371)
    public Map<String,Object> unsetNotebookMaxCachedTemplatesPerFolder(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookMaxCachedTemplatesPerFolder, "");
        return attrs;
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @return zimbraNotebookPageCacheSize, or 10240 if unset
     */
    @ZAttr(id=369)
    public int getNotebookPageCacheSize() {
        return getIntAttr(Provisioning.A_zimbraNotebookPageCacheSize, 10240);
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @param zimbraNotebookPageCacheSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=369)
    public void setNotebookPageCacheSize(int zimbraNotebookPageCacheSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookPageCacheSize, Integer.toString(zimbraNotebookPageCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @param zimbraNotebookPageCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=369)
    public Map<String,Object> setNotebookPageCacheSize(int zimbraNotebookPageCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookPageCacheSize, Integer.toString(zimbraNotebookPageCacheSize));
        return attrs;
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=369)
    public void unsetNotebookPageCacheSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookPageCacheSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=369)
    public Map<String,Object> unsetNotebookPageCacheSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotebookPageCacheSize, "");
        return attrs;
    }

    /**
     * administrative notes
     *
     * @return zimbraNotes, or null if unset
     */
    @ZAttr(id=9)
    public String getNotes() {
        return getAttr(Provisioning.A_zimbraNotes, null);
    }

    /**
     * administrative notes
     *
     * @param zimbraNotes new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=9)
    public void setNotes(String zimbraNotes) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotes, zimbraNotes);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * administrative notes
     *
     * @param zimbraNotes new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=9)
    public Map<String,Object> setNotes(String zimbraNotes, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotes, zimbraNotes);
        return attrs;
    }

    /**
     * administrative notes
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=9)
    public void unsetNotes() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotes, "");
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
        attrs.put(Provisioning.A_zimbraNotes, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @return zimbraNotifyBindAddress, or empty array if unset
     */
    @ZAttr(id=317)
    public String[] getNotifyBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraNotifyBindAddress);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zimbraNotifyBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void setNotifyBindAddress(String[] zimbraNotifyBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindAddress, zimbraNotifyBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zimbraNotifyBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> setNotifyBindAddress(String[] zimbraNotifyBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindAddress, zimbraNotifyBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zimbraNotifyBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void addNotifyBindAddress(String zimbraNotifyBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraNotifyBindAddress, zimbraNotifyBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zimbraNotifyBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> addNotifyBindAddress(String zimbraNotifyBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraNotifyBindAddress, zimbraNotifyBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zimbraNotifyBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void removeNotifyBindAddress(String zimbraNotifyBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraNotifyBindAddress, zimbraNotifyBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zimbraNotifyBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> removeNotifyBindAddress(String zimbraNotifyBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraNotifyBindAddress, zimbraNotifyBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void unsetNotifyBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> unsetNotifyBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindAddress, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @return zimbraNotifyBindPort, or 7035 if unset
     */
    @ZAttr(id=318)
    public int getNotifyBindPort() {
        return getIntAttr(Provisioning.A_zimbraNotifyBindPort, 7035);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zimbraNotifyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=318)
    public void setNotifyBindPort(int zimbraNotifyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindPort, Integer.toString(zimbraNotifyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zimbraNotifyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=318)
    public Map<String,Object> setNotifyBindPort(int zimbraNotifyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindPort, Integer.toString(zimbraNotifyBindPort));
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=318)
    public void unsetNotifyBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=318)
    public Map<String,Object> unsetNotifyBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyBindPort, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @return zimbraNotifySSLBindAddress, or empty array if unset
     */
    @ZAttr(id=320)
    public String[] getNotifySSLBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraNotifySSLBindAddress);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraNotifySSLBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void setNotifySSLBindAddress(String[] zimbraNotifySSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindAddress, zimbraNotifySSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraNotifySSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> setNotifySSLBindAddress(String[] zimbraNotifySSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindAddress, zimbraNotifySSLBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraNotifySSLBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void addNotifySSLBindAddress(String zimbraNotifySSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraNotifySSLBindAddress, zimbraNotifySSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraNotifySSLBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> addNotifySSLBindAddress(String zimbraNotifySSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraNotifySSLBindAddress, zimbraNotifySSLBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraNotifySSLBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void removeNotifySSLBindAddress(String zimbraNotifySSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraNotifySSLBindAddress, zimbraNotifySSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zimbraNotifySSLBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> removeNotifySSLBindAddress(String zimbraNotifySSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraNotifySSLBindAddress, zimbraNotifySSLBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void unsetNotifySSLBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> unsetNotifySSLBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindAddress, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @return zimbraNotifySSLBindPort, or 7036 if unset
     */
    @ZAttr(id=321)
    public int getNotifySSLBindPort() {
        return getIntAttr(Provisioning.A_zimbraNotifySSLBindPort, 7036);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zimbraNotifySSLBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=321)
    public void setNotifySSLBindPort(int zimbraNotifySSLBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindPort, Integer.toString(zimbraNotifySSLBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zimbraNotifySSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=321)
    public Map<String,Object> setNotifySSLBindPort(int zimbraNotifySSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindPort, Integer.toString(zimbraNotifySSLBindPort));
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=321)
    public void unsetNotifySSLBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=321)
    public Map<String,Object> unsetNotifySSLBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLBindPort, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @return zimbraNotifySSLServerEnabled, or true if unset
     */
    @ZAttr(id=319)
    public boolean isNotifySSLServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraNotifySSLServerEnabled, true);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @param zimbraNotifySSLServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=319)
    public void setNotifySSLServerEnabled(boolean zimbraNotifySSLServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLServerEnabled, zimbraNotifySSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @param zimbraNotifySSLServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=319)
    public Map<String,Object> setNotifySSLServerEnabled(boolean zimbraNotifySSLServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLServerEnabled, zimbraNotifySSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=319)
    public void unsetNotifySSLServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=319)
    public Map<String,Object> unsetNotifySSLServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifySSLServerEnabled, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @return zimbraNotifyServerEnabled, or true if unset
     */
    @ZAttr(id=316)
    public boolean isNotifyServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraNotifyServerEnabled, true);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @param zimbraNotifyServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=316)
    public void setNotifyServerEnabled(boolean zimbraNotifyServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyServerEnabled, zimbraNotifyServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @param zimbraNotifyServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=316)
    public Map<String,Object> setNotifyServerEnabled(boolean zimbraNotifyServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyServerEnabled, zimbraNotifyServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=316)
    public void unsetNotifyServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=316)
    public Map<String,Object> unsetNotifyServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraNotifyServerEnabled, "");
        return attrs;
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @return zimbraOpenidConsumerStatelessModeEnabled, or true if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public boolean isOpenidConsumerStatelessModeEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraOpenidConsumerStatelessModeEnabled, true);
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @param zimbraOpenidConsumerStatelessModeEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public void setOpenidConsumerStatelessModeEnabled(boolean zimbraOpenidConsumerStatelessModeEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraOpenidConsumerStatelessModeEnabled, zimbraOpenidConsumerStatelessModeEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @param zimbraOpenidConsumerStatelessModeEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public Map<String,Object> setOpenidConsumerStatelessModeEnabled(boolean zimbraOpenidConsumerStatelessModeEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraOpenidConsumerStatelessModeEnabled, zimbraOpenidConsumerStatelessModeEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public void unsetOpenidConsumerStatelessModeEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraOpenidConsumerStatelessModeEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public Map<String,Object> unsetOpenidConsumerStatelessModeEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraOpenidConsumerStatelessModeEnabled, "");
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @return zimbraPop3AdvertisedName, or null if unset
     */
    @ZAttr(id=93)
    public String getPop3AdvertisedName() {
        return getAttr(Provisioning.A_zimbraPop3AdvertisedName, null);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zimbraPop3AdvertisedName new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=93)
    public void setPop3AdvertisedName(String zimbraPop3AdvertisedName) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3AdvertisedName, zimbraPop3AdvertisedName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zimbraPop3AdvertisedName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=93)
    public Map<String,Object> setPop3AdvertisedName(String zimbraPop3AdvertisedName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3AdvertisedName, zimbraPop3AdvertisedName);
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=93)
    public void unsetPop3AdvertisedName() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3AdvertisedName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=93)
    public Map<String,Object> unsetPop3AdvertisedName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3AdvertisedName, "");
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraPop3BindAddress, or empty array if unset
     */
    @ZAttr(id=95)
    public String[] getPop3BindAddress() {
        return getMultiAttr(Provisioning.A_zimbraPop3BindAddress);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3BindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void setPop3BindAddress(String[] zimbraPop3BindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindAddress, zimbraPop3BindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3BindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> setPop3BindAddress(String[] zimbraPop3BindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindAddress, zimbraPop3BindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3BindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void addPop3BindAddress(String zimbraPop3BindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraPop3BindAddress, zimbraPop3BindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3BindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> addPop3BindAddress(String zimbraPop3BindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraPop3BindAddress, zimbraPop3BindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3BindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void removePop3BindAddress(String zimbraPop3BindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraPop3BindAddress, zimbraPop3BindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3BindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> removePop3BindAddress(String zimbraPop3BindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraPop3BindAddress, zimbraPop3BindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void unsetPop3BindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> unsetPop3BindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zimbraPop3BindOnStartup, or true if unset
     */
    @ZAttr(id=271)
    public boolean isPop3BindOnStartup() {
        return getBooleanAttr(Provisioning.A_zimbraPop3BindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraPop3BindOnStartup new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=271)
    public void setPop3BindOnStartup(boolean zimbraPop3BindOnStartup) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindOnStartup, zimbraPop3BindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraPop3BindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=271)
    public Map<String,Object> setPop3BindOnStartup(boolean zimbraPop3BindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindOnStartup, zimbraPop3BindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=271)
    public void unsetPop3BindOnStartup() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindOnStartup, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=271)
    public Map<String,Object> unsetPop3BindOnStartup(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * <p>Use getPop3BindPortAsString to access value as a string.
     *
     * @see #getPop3BindPortAsString()
     *
     * @return zimbraPop3BindPort, or 7110 if unset
     */
    @ZAttr(id=94)
    public int getPop3BindPort() {
        return getIntAttr(Provisioning.A_zimbraPop3BindPort, 7110);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @return zimbraPop3BindPort, or "7110" if unset
     */
    @ZAttr(id=94)
    public String getPop3BindPortAsString() {
        return getAttr(Provisioning.A_zimbraPop3BindPort, "7110");
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3BindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=94)
    public void setPop3BindPort(int zimbraPop3BindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindPort, Integer.toString(zimbraPop3BindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3BindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=94)
    public Map<String,Object> setPop3BindPort(int zimbraPop3BindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindPort, Integer.toString(zimbraPop3BindPort));
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3BindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=94)
    public void setPop3BindPortAsString(String zimbraPop3BindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindPort, zimbraPop3BindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3BindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=94)
    public Map<String,Object> setPop3BindPortAsString(String zimbraPop3BindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindPort, zimbraPop3BindPort);
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=94)
    public void unsetPop3BindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=94)
    public Map<String,Object> unsetPop3BindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3BindPort, "");
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @return zimbraPop3CleartextLoginEnabled, or false if unset
     */
    @ZAttr(id=189)
    public boolean isPop3CleartextLoginEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraPop3CleartextLoginEnabled, false);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zimbraPop3CleartextLoginEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=189)
    public void setPop3CleartextLoginEnabled(boolean zimbraPop3CleartextLoginEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3CleartextLoginEnabled, zimbraPop3CleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zimbraPop3CleartextLoginEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=189)
    public Map<String,Object> setPop3CleartextLoginEnabled(boolean zimbraPop3CleartextLoginEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3CleartextLoginEnabled, zimbraPop3CleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=189)
    public void unsetPop3CleartextLoginEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3CleartextLoginEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=189)
    public Map<String,Object> unsetPop3CleartextLoginEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3CleartextLoginEnabled, "");
        return attrs;
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @return zimbraPop3ExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public boolean isPop3ExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zimbraPop3ExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @param zimbraPop3ExposeVersionOnBanner new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public void setPop3ExposeVersionOnBanner(boolean zimbraPop3ExposeVersionOnBanner) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ExposeVersionOnBanner, zimbraPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @param zimbraPop3ExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public Map<String,Object> setPop3ExposeVersionOnBanner(boolean zimbraPop3ExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ExposeVersionOnBanner, zimbraPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public void unsetPop3ExposeVersionOnBanner() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ExposeVersionOnBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public Map<String,Object> unsetPop3ExposeVersionOnBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @return zimbraPop3MaxConnections, or 200 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public int getPop3MaxConnections() {
        return getIntAttr(Provisioning.A_zimbraPop3MaxConnections, 200);
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zimbraPop3MaxConnections new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public void setPop3MaxConnections(int zimbraPop3MaxConnections) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3MaxConnections, Integer.toString(zimbraPop3MaxConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zimbraPop3MaxConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public Map<String,Object> setPop3MaxConnections(int zimbraPop3MaxConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3MaxConnections, Integer.toString(zimbraPop3MaxConnections));
        return attrs;
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public void unsetPop3MaxConnections() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3MaxConnections, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public Map<String,Object> unsetPop3MaxConnections(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3MaxConnections, "");
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @return zimbraPop3NumThreads, or 100 if unset
     */
    @ZAttr(id=96)
    public int getPop3NumThreads() {
        return getIntAttr(Provisioning.A_zimbraPop3NumThreads, 100);
    }

    /**
     * number of handler threads
     *
     * @param zimbraPop3NumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=96)
    public void setPop3NumThreads(int zimbraPop3NumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3NumThreads, Integer.toString(zimbraPop3NumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads
     *
     * @param zimbraPop3NumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=96)
    public Map<String,Object> setPop3NumThreads(int zimbraPop3NumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3NumThreads, Integer.toString(zimbraPop3NumThreads));
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=96)
    public void unsetPop3NumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3NumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=96)
    public Map<String,Object> unsetPop3NumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3NumThreads, "");
        return attrs;
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * <p>Use getPop3ProxyBindPortAsString to access value as a string.
     *
     * @see #getPop3ProxyBindPortAsString()
     *
     * @return zimbraPop3ProxyBindPort, or 110 if unset
     */
    @ZAttr(id=350)
    public int getPop3ProxyBindPort() {
        return getIntAttr(Provisioning.A_zimbraPop3ProxyBindPort, 110);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @return zimbraPop3ProxyBindPort, or "110" if unset
     */
    @ZAttr(id=350)
    public String getPop3ProxyBindPortAsString() {
        return getAttr(Provisioning.A_zimbraPop3ProxyBindPort, "110");
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zimbraPop3ProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=350)
    public void setPop3ProxyBindPort(int zimbraPop3ProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ProxyBindPort, Integer.toString(zimbraPop3ProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zimbraPop3ProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=350)
    public Map<String,Object> setPop3ProxyBindPort(int zimbraPop3ProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ProxyBindPort, Integer.toString(zimbraPop3ProxyBindPort));
        return attrs;
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zimbraPop3ProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=350)
    public void setPop3ProxyBindPortAsString(String zimbraPop3ProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ProxyBindPort, zimbraPop3ProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zimbraPop3ProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=350)
    public Map<String,Object> setPop3ProxyBindPortAsString(String zimbraPop3ProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ProxyBindPort, zimbraPop3ProxyBindPort);
        return attrs;
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=350)
    public void unsetPop3ProxyBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ProxyBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=350)
    public Map<String,Object> unsetPop3ProxyBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ProxyBindPort, "");
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @return zimbraPop3SSLBindAddress, or empty array if unset
     */
    @ZAttr(id=186)
    public String[] getPop3SSLBindAddress() {
        return getMultiAttr(Provisioning.A_zimbraPop3SSLBindAddress);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3SSLBindAddress new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void setPop3SSLBindAddress(String[] zimbraPop3SSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindAddress, zimbraPop3SSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3SSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> setPop3SSLBindAddress(String[] zimbraPop3SSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindAddress, zimbraPop3SSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3SSLBindAddress new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void addPop3SSLBindAddress(String zimbraPop3SSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraPop3SSLBindAddress, zimbraPop3SSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3SSLBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> addPop3SSLBindAddress(String zimbraPop3SSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraPop3SSLBindAddress, zimbraPop3SSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3SSLBindAddress existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void removePop3SSLBindAddress(String zimbraPop3SSLBindAddress) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraPop3SSLBindAddress, zimbraPop3SSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zimbraPop3SSLBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> removePop3SSLBindAddress(String zimbraPop3SSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraPop3SSLBindAddress, zimbraPop3SSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void unsetPop3SSLBindAddress() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> unsetPop3SSLBindAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zimbraPop3SSLBindOnStartup, or true if unset
     */
    @ZAttr(id=272)
    public boolean isPop3SSLBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zimbraPop3SSLBindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraPop3SSLBindOnStartup new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=272)
    public void setPop3SSLBindOnStartup(boolean zimbraPop3SSLBindOnStartup) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindOnStartup, zimbraPop3SSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zimbraPop3SSLBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=272)
    public Map<String,Object> setPop3SSLBindOnStartup(boolean zimbraPop3SSLBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindOnStartup, zimbraPop3SSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=272)
    public void unsetPop3SSLBindOnStartup() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindOnStartup, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=272)
    public Map<String,Object> unsetPop3SSLBindOnStartup(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * <p>Use getPop3SSLBindPortAsString to access value as a string.
     *
     * @see #getPop3SSLBindPortAsString()
     *
     * @return zimbraPop3SSLBindPort, or 7995 if unset
     */
    @ZAttr(id=187)
    public int getPop3SSLBindPort() {
        return getIntAttr(Provisioning.A_zimbraPop3SSLBindPort, 7995);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @return zimbraPop3SSLBindPort, or "7995" if unset
     */
    @ZAttr(id=187)
    public String getPop3SSLBindPortAsString() {
        return getAttr(Provisioning.A_zimbraPop3SSLBindPort, "7995");
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3SSLBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=187)
    public void setPop3SSLBindPort(int zimbraPop3SSLBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindPort, Integer.toString(zimbraPop3SSLBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3SSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=187)
    public Map<String,Object> setPop3SSLBindPort(int zimbraPop3SSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindPort, Integer.toString(zimbraPop3SSLBindPort));
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3SSLBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=187)
    public void setPop3SSLBindPortAsString(String zimbraPop3SSLBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindPort, zimbraPop3SSLBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zimbraPop3SSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=187)
    public Map<String,Object> setPop3SSLBindPortAsString(String zimbraPop3SSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindPort, zimbraPop3SSLBindPort);
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=187)
    public void unsetPop3SSLBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=187)
    public Map<String,Object> unsetPop3SSLBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLBindPort, "");
        return attrs;
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * <p>Use getPop3SSLProxyBindPortAsString to access value as a string.
     *
     * @see #getPop3SSLProxyBindPortAsString()
     *
     * @return zimbraPop3SSLProxyBindPort, or 995 if unset
     */
    @ZAttr(id=351)
    public int getPop3SSLProxyBindPort() {
        return getIntAttr(Provisioning.A_zimbraPop3SSLProxyBindPort, 995);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @return zimbraPop3SSLProxyBindPort, or "995" if unset
     */
    @ZAttr(id=351)
    public String getPop3SSLProxyBindPortAsString() {
        return getAttr(Provisioning.A_zimbraPop3SSLProxyBindPort, "995");
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zimbraPop3SSLProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=351)
    public void setPop3SSLProxyBindPort(int zimbraPop3SSLProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLProxyBindPort, Integer.toString(zimbraPop3SSLProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zimbraPop3SSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=351)
    public Map<String,Object> setPop3SSLProxyBindPort(int zimbraPop3SSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLProxyBindPort, Integer.toString(zimbraPop3SSLProxyBindPort));
        return attrs;
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zimbraPop3SSLProxyBindPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=351)
    public void setPop3SSLProxyBindPortAsString(String zimbraPop3SSLProxyBindPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLProxyBindPort, zimbraPop3SSLProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zimbraPop3SSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=351)
    public Map<String,Object> setPop3SSLProxyBindPortAsString(String zimbraPop3SSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLProxyBindPort, zimbraPop3SSLProxyBindPort);
        return attrs;
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=351)
    public void unsetPop3SSLProxyBindPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLProxyBindPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=351)
    public Map<String,Object> unsetPop3SSLProxyBindPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLProxyBindPort, "");
        return attrs;
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @return zimbraPop3SSLServerEnabled, or true if unset
     */
    @ZAttr(id=188)
    public boolean isPop3SSLServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraPop3SSLServerEnabled, true);
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @param zimbraPop3SSLServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=188)
    public void setPop3SSLServerEnabled(boolean zimbraPop3SSLServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLServerEnabled, zimbraPop3SSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @param zimbraPop3SSLServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=188)
    public Map<String,Object> setPop3SSLServerEnabled(boolean zimbraPop3SSLServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLServerEnabled, zimbraPop3SSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=188)
    public void unsetPop3SSLServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=188)
    public Map<String,Object> unsetPop3SSLServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SSLServerEnabled, "");
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @return zimbraPop3SaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public boolean isPop3SaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraPop3SaslGssapiEnabled, false);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @param zimbraPop3SaslGssapiEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public void setPop3SaslGssapiEnabled(boolean zimbraPop3SaslGssapiEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SaslGssapiEnabled, zimbraPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @param zimbraPop3SaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public Map<String,Object> setPop3SaslGssapiEnabled(boolean zimbraPop3SaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SaslGssapiEnabled, zimbraPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public void unsetPop3SaslGssapiEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SaslGssapiEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public Map<String,Object> unsetPop3SaslGssapiEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3SaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @return zimbraPop3ServerEnabled, or true if unset
     */
    @ZAttr(id=177)
    public boolean isPop3ServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraPop3ServerEnabled, true);
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @param zimbraPop3ServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=177)
    public void setPop3ServerEnabled(boolean zimbraPop3ServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ServerEnabled, zimbraPop3ServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @param zimbraPop3ServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=177)
    public Map<String,Object> setPop3ServerEnabled(boolean zimbraPop3ServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ServerEnabled, zimbraPop3ServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=177)
    public void unsetPop3ServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=177)
    public Map<String,Object> unsetPop3ServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ServerEnabled, "");
        return attrs;
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @return zimbraPop3ShutdownGraceSeconds, or 10 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public int getPop3ShutdownGraceSeconds() {
        return getIntAttr(Provisioning.A_zimbraPop3ShutdownGraceSeconds, 10);
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @param zimbraPop3ShutdownGraceSeconds new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public void setPop3ShutdownGraceSeconds(int zimbraPop3ShutdownGraceSeconds) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ShutdownGraceSeconds, Integer.toString(zimbraPop3ShutdownGraceSeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @param zimbraPop3ShutdownGraceSeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public Map<String,Object> setPop3ShutdownGraceSeconds(int zimbraPop3ShutdownGraceSeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ShutdownGraceSeconds, Integer.toString(zimbraPop3ShutdownGraceSeconds));
        return attrs;
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public void unsetPop3ShutdownGraceSeconds() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ShutdownGraceSeconds, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public Map<String,Object> unsetPop3ShutdownGraceSeconds(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraPop3ShutdownGraceSeconds, "");
        return attrs;
    }

    /**
     * redolog rollover destination
     *
     * @return zimbraRedoLogArchiveDir, or "redolog/archive" if unset
     */
    @ZAttr(id=76)
    public String getRedoLogArchiveDir() {
        return getAttr(Provisioning.A_zimbraRedoLogArchiveDir, "redolog/archive");
    }

    /**
     * redolog rollover destination
     *
     * @param zimbraRedoLogArchiveDir new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=76)
    public void setRedoLogArchiveDir(String zimbraRedoLogArchiveDir) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogArchiveDir, zimbraRedoLogArchiveDir);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redolog rollover destination
     *
     * @param zimbraRedoLogArchiveDir new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=76)
    public Map<String,Object> setRedoLogArchiveDir(String zimbraRedoLogArchiveDir, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogArchiveDir, zimbraRedoLogArchiveDir);
        return attrs;
    }

    /**
     * redolog rollover destination
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=76)
    public void unsetRedoLogArchiveDir() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogArchiveDir, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redolog rollover destination
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=76)
    public Map<String,Object> unsetRedoLogArchiveDir(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogArchiveDir, "");
        return attrs;
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @return zimbraRedoLogCrashRecoveryLookbackSec, or 10 if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public int getRedoLogCrashRecoveryLookbackSec() {
        return getIntAttr(Provisioning.A_zimbraRedoLogCrashRecoveryLookbackSec, 10);
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @param zimbraRedoLogCrashRecoveryLookbackSec new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public void setRedoLogCrashRecoveryLookbackSec(int zimbraRedoLogCrashRecoveryLookbackSec) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogCrashRecoveryLookbackSec, Integer.toString(zimbraRedoLogCrashRecoveryLookbackSec));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @param zimbraRedoLogCrashRecoveryLookbackSec new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public Map<String,Object> setRedoLogCrashRecoveryLookbackSec(int zimbraRedoLogCrashRecoveryLookbackSec, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogCrashRecoveryLookbackSec, Integer.toString(zimbraRedoLogCrashRecoveryLookbackSec));
        return attrs;
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public void unsetRedoLogCrashRecoveryLookbackSec() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogCrashRecoveryLookbackSec, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public Map<String,Object> unsetRedoLogCrashRecoveryLookbackSec(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogCrashRecoveryLookbackSec, "");
        return attrs;
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @return zimbraRedoLogDeleteOnRollover, or true if unset
     */
    @ZAttr(id=251)
    public boolean isRedoLogDeleteOnRollover() {
        return getBooleanAttr(Provisioning.A_zimbraRedoLogDeleteOnRollover, true);
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @param zimbraRedoLogDeleteOnRollover new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=251)
    public void setRedoLogDeleteOnRollover(boolean zimbraRedoLogDeleteOnRollover) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogDeleteOnRollover, zimbraRedoLogDeleteOnRollover ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @param zimbraRedoLogDeleteOnRollover new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=251)
    public Map<String,Object> setRedoLogDeleteOnRollover(boolean zimbraRedoLogDeleteOnRollover, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogDeleteOnRollover, zimbraRedoLogDeleteOnRollover ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=251)
    public void unsetRedoLogDeleteOnRollover() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogDeleteOnRollover, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=251)
    public Map<String,Object> unsetRedoLogDeleteOnRollover(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogDeleteOnRollover, "");
        return attrs;
    }

    /**
     * whether redo logging is enabled
     *
     * @return zimbraRedoLogEnabled, or true if unset
     */
    @ZAttr(id=74)
    public boolean isRedoLogEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraRedoLogEnabled, true);
    }

    /**
     * whether redo logging is enabled
     *
     * @param zimbraRedoLogEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=74)
    public void setRedoLogEnabled(boolean zimbraRedoLogEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogEnabled, zimbraRedoLogEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether redo logging is enabled
     *
     * @param zimbraRedoLogEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=74)
    public Map<String,Object> setRedoLogEnabled(boolean zimbraRedoLogEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogEnabled, zimbraRedoLogEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether redo logging is enabled
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=74)
    public void unsetRedoLogEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether redo logging is enabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=74)
    public Map<String,Object> unsetRedoLogEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogEnabled, "");
        return attrs;
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @return zimbraRedoLogFsyncIntervalMS, or 10 if unset
     */
    @ZAttr(id=79)
    public int getRedoLogFsyncIntervalMS() {
        return getIntAttr(Provisioning.A_zimbraRedoLogFsyncIntervalMS, 10);
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @param zimbraRedoLogFsyncIntervalMS new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=79)
    public void setRedoLogFsyncIntervalMS(int zimbraRedoLogFsyncIntervalMS) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogFsyncIntervalMS, Integer.toString(zimbraRedoLogFsyncIntervalMS));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @param zimbraRedoLogFsyncIntervalMS new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=79)
    public Map<String,Object> setRedoLogFsyncIntervalMS(int zimbraRedoLogFsyncIntervalMS, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogFsyncIntervalMS, Integer.toString(zimbraRedoLogFsyncIntervalMS));
        return attrs;
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=79)
    public void unsetRedoLogFsyncIntervalMS() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogFsyncIntervalMS, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=79)
    public Map<String,Object> unsetRedoLogFsyncIntervalMS(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogFsyncIntervalMS, "");
        return attrs;
    }

    /**
     * name and location of the redolog file
     *
     * @return zimbraRedoLogLogPath, or "redolog/redo.log" if unset
     */
    @ZAttr(id=75)
    public String getRedoLogLogPath() {
        return getAttr(Provisioning.A_zimbraRedoLogLogPath, "redolog/redo.log");
    }

    /**
     * name and location of the redolog file
     *
     * @param zimbraRedoLogLogPath new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=75)
    public void setRedoLogLogPath(String zimbraRedoLogLogPath) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogLogPath, zimbraRedoLogLogPath);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name and location of the redolog file
     *
     * @param zimbraRedoLogLogPath new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=75)
    public Map<String,Object> setRedoLogLogPath(String zimbraRedoLogLogPath, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogLogPath, zimbraRedoLogLogPath);
        return attrs;
    }

    /**
     * name and location of the redolog file
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=75)
    public void unsetRedoLogLogPath() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogLogPath, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name and location of the redolog file
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=75)
    public Map<String,Object> unsetRedoLogLogPath(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogLogPath, "");
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @return zimbraRedoLogProvider, or empty array if unset
     */
    @ZAttr(id=225)
    public String[] getRedoLogProvider() {
        return getMultiAttr(Provisioning.A_zimbraRedoLogProvider);
    }

    /**
     * provider class name for redo logging
     *
     * @param zimbraRedoLogProvider new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void setRedoLogProvider(String[] zimbraRedoLogProvider) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogProvider, zimbraRedoLogProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param zimbraRedoLogProvider new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> setRedoLogProvider(String[] zimbraRedoLogProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogProvider, zimbraRedoLogProvider);
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @param zimbraRedoLogProvider new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void addRedoLogProvider(String zimbraRedoLogProvider) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraRedoLogProvider, zimbraRedoLogProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param zimbraRedoLogProvider new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> addRedoLogProvider(String zimbraRedoLogProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraRedoLogProvider, zimbraRedoLogProvider);
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @param zimbraRedoLogProvider existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void removeRedoLogProvider(String zimbraRedoLogProvider) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraRedoLogProvider, zimbraRedoLogProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param zimbraRedoLogProvider existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> removeRedoLogProvider(String zimbraRedoLogProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraRedoLogProvider, zimbraRedoLogProvider);
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void unsetRedoLogProvider() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogProvider, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> unsetRedoLogProvider(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogProvider, "");
        return attrs;
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @return zimbraRedoLogRolloverFileSizeKB, or 1048576 if unset
     */
    @ZAttr(id=78)
    public int getRedoLogRolloverFileSizeKB() {
        return getIntAttr(Provisioning.A_zimbraRedoLogRolloverFileSizeKB, 1048576);
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @param zimbraRedoLogRolloverFileSizeKB new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=78)
    public void setRedoLogRolloverFileSizeKB(int zimbraRedoLogRolloverFileSizeKB) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverFileSizeKB, Integer.toString(zimbraRedoLogRolloverFileSizeKB));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @param zimbraRedoLogRolloverFileSizeKB new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=78)
    public Map<String,Object> setRedoLogRolloverFileSizeKB(int zimbraRedoLogRolloverFileSizeKB, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverFileSizeKB, Integer.toString(zimbraRedoLogRolloverFileSizeKB));
        return attrs;
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=78)
    public void unsetRedoLogRolloverFileSizeKB() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverFileSizeKB, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=78)
    public Map<String,Object> unsetRedoLogRolloverFileSizeKB(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverFileSizeKB, "");
        return attrs;
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @return zimbraRedoLogRolloverHardMaxFileSizeKB, or 4194304 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public int getRedoLogRolloverHardMaxFileSizeKB() {
        return getIntAttr(Provisioning.A_zimbraRedoLogRolloverHardMaxFileSizeKB, 4194304);
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @param zimbraRedoLogRolloverHardMaxFileSizeKB new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public void setRedoLogRolloverHardMaxFileSizeKB(int zimbraRedoLogRolloverHardMaxFileSizeKB) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverHardMaxFileSizeKB, Integer.toString(zimbraRedoLogRolloverHardMaxFileSizeKB));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @param zimbraRedoLogRolloverHardMaxFileSizeKB new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public Map<String,Object> setRedoLogRolloverHardMaxFileSizeKB(int zimbraRedoLogRolloverHardMaxFileSizeKB, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverHardMaxFileSizeKB, Integer.toString(zimbraRedoLogRolloverHardMaxFileSizeKB));
        return attrs;
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public void unsetRedoLogRolloverHardMaxFileSizeKB() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverHardMaxFileSizeKB, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public Map<String,Object> unsetRedoLogRolloverHardMaxFileSizeKB(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverHardMaxFileSizeKB, "");
        return attrs;
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @return zimbraRedoLogRolloverMinFileAge, or 60 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public int getRedoLogRolloverMinFileAge() {
        return getIntAttr(Provisioning.A_zimbraRedoLogRolloverMinFileAge, 60);
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @param zimbraRedoLogRolloverMinFileAge new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public void setRedoLogRolloverMinFileAge(int zimbraRedoLogRolloverMinFileAge) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverMinFileAge, Integer.toString(zimbraRedoLogRolloverMinFileAge));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @param zimbraRedoLogRolloverMinFileAge new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public Map<String,Object> setRedoLogRolloverMinFileAge(int zimbraRedoLogRolloverMinFileAge, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverMinFileAge, Integer.toString(zimbraRedoLogRolloverMinFileAge));
        return attrs;
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public void unsetRedoLogRolloverMinFileAge() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverMinFileAge, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public Map<String,Object> unsetRedoLogRolloverMinFileAge(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRedoLogRolloverMinFileAge, "");
        return attrs;
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @return zimbraRemoteManagementCommand, or "/opt/zimbra/libexec/zmrcd" if unset
     */
    @ZAttr(id=336)
    public String getRemoteManagementCommand() {
        return getAttr(Provisioning.A_zimbraRemoteManagementCommand, "/opt/zimbra/libexec/zmrcd");
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @param zimbraRemoteManagementCommand new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=336)
    public void setRemoteManagementCommand(String zimbraRemoteManagementCommand) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementCommand, zimbraRemoteManagementCommand);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @param zimbraRemoteManagementCommand new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=336)
    public Map<String,Object> setRemoteManagementCommand(String zimbraRemoteManagementCommand, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementCommand, zimbraRemoteManagementCommand);
        return attrs;
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=336)
    public void unsetRemoteManagementCommand() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementCommand, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=336)
    public Map<String,Object> unsetRemoteManagementCommand(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementCommand, "");
        return attrs;
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @return zimbraRemoteManagementPort, or 22 if unset
     */
    @ZAttr(id=339)
    public int getRemoteManagementPort() {
        return getIntAttr(Provisioning.A_zimbraRemoteManagementPort, 22);
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @param zimbraRemoteManagementPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=339)
    public void setRemoteManagementPort(int zimbraRemoteManagementPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPort, Integer.toString(zimbraRemoteManagementPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @param zimbraRemoteManagementPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=339)
    public Map<String,Object> setRemoteManagementPort(int zimbraRemoteManagementPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPort, Integer.toString(zimbraRemoteManagementPort));
        return attrs;
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=339)
    public void unsetRemoteManagementPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=339)
    public Map<String,Object> unsetRemoteManagementPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPort, "");
        return attrs;
    }

    /**
     * Private key this server should use to access another server
     *
     * @return zimbraRemoteManagementPrivateKeyPath, or "/opt/zimbra/.ssh/zimbra_identity" if unset
     */
    @ZAttr(id=338)
    public String getRemoteManagementPrivateKeyPath() {
        return getAttr(Provisioning.A_zimbraRemoteManagementPrivateKeyPath, "/opt/zimbra/.ssh/zimbra_identity");
    }

    /**
     * Private key this server should use to access another server
     *
     * @param zimbraRemoteManagementPrivateKeyPath new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=338)
    public void setRemoteManagementPrivateKeyPath(String zimbraRemoteManagementPrivateKeyPath) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPrivateKeyPath, zimbraRemoteManagementPrivateKeyPath);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Private key this server should use to access another server
     *
     * @param zimbraRemoteManagementPrivateKeyPath new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=338)
    public Map<String,Object> setRemoteManagementPrivateKeyPath(String zimbraRemoteManagementPrivateKeyPath, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPrivateKeyPath, zimbraRemoteManagementPrivateKeyPath);
        return attrs;
    }

    /**
     * Private key this server should use to access another server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=338)
    public void unsetRemoteManagementPrivateKeyPath() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPrivateKeyPath, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Private key this server should use to access another server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=338)
    public Map<String,Object> unsetRemoteManagementPrivateKeyPath(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementPrivateKeyPath, "");
        return attrs;
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @return zimbraRemoteManagementUser, or "zimbra" if unset
     */
    @ZAttr(id=337)
    public String getRemoteManagementUser() {
        return getAttr(Provisioning.A_zimbraRemoteManagementUser, "zimbra");
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @param zimbraRemoteManagementUser new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=337)
    public void setRemoteManagementUser(String zimbraRemoteManagementUser) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementUser, zimbraRemoteManagementUser);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @param zimbraRemoteManagementUser new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=337)
    public Map<String,Object> setRemoteManagementUser(String zimbraRemoteManagementUser, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementUser, zimbraRemoteManagementUser);
        return attrs;
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=337)
    public void unsetRemoteManagementUser() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementUser, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=337)
    public Map<String,Object> unsetRemoteManagementUser(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraRemoteManagementUser, "");
        return attrs;
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @return zimbraReverseProxyAdminEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public boolean isReverseProxyAdminEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyAdminEnabled, false);
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @param zimbraReverseProxyAdminEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public void setReverseProxyAdminEnabled(boolean zimbraReverseProxyAdminEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAdminEnabled, zimbraReverseProxyAdminEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @param zimbraReverseProxyAdminEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public Map<String,Object> setReverseProxyAdminEnabled(boolean zimbraReverseProxyAdminEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAdminEnabled, zimbraReverseProxyAdminEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public void unsetReverseProxyAdminEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAdminEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public Map<String,Object> unsetReverseProxyAdminEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAdminEnabled, "");
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @return zimbraReverseProxyAvailableLookupTargets, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public String[] getReverseProxyAvailableLookupTargets() {
        return getMultiAttr(Provisioning.A_zimbraReverseProxyAvailableLookupTargets);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zimbraReverseProxyAvailableLookupTargets new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void setReverseProxyAvailableLookupTargets(String[] zimbraReverseProxyAvailableLookupTargets) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAvailableLookupTargets, zimbraReverseProxyAvailableLookupTargets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zimbraReverseProxyAvailableLookupTargets new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> setReverseProxyAvailableLookupTargets(String[] zimbraReverseProxyAvailableLookupTargets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAvailableLookupTargets, zimbraReverseProxyAvailableLookupTargets);
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zimbraReverseProxyAvailableLookupTargets new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void addReverseProxyAvailableLookupTargets(String zimbraReverseProxyAvailableLookupTargets) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyAvailableLookupTargets, zimbraReverseProxyAvailableLookupTargets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zimbraReverseProxyAvailableLookupTargets new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> addReverseProxyAvailableLookupTargets(String zimbraReverseProxyAvailableLookupTargets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyAvailableLookupTargets, zimbraReverseProxyAvailableLookupTargets);
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zimbraReverseProxyAvailableLookupTargets existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void removeReverseProxyAvailableLookupTargets(String zimbraReverseProxyAvailableLookupTargets) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyAvailableLookupTargets, zimbraReverseProxyAvailableLookupTargets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zimbraReverseProxyAvailableLookupTargets existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> removeReverseProxyAvailableLookupTargets(String zimbraReverseProxyAvailableLookupTargets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyAvailableLookupTargets, zimbraReverseProxyAvailableLookupTargets);
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void unsetReverseProxyAvailableLookupTargets() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAvailableLookupTargets, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zimbraReverseProxyLookupTarget
     * is TRUE.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> unsetReverseProxyAvailableLookupTargets(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyAvailableLookupTargets, "");
        return attrs;
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @return zimbraReverseProxyClientCertCA, or null if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public String getReverseProxyClientCertCA() {
        return getAttr(Provisioning.A_zimbraReverseProxyClientCertCA, null);
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @param zimbraReverseProxyClientCertCA new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public void setReverseProxyClientCertCA(String zimbraReverseProxyClientCertCA) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertCA, zimbraReverseProxyClientCertCA);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @param zimbraReverseProxyClientCertCA new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public Map<String,Object> setReverseProxyClientCertCA(String zimbraReverseProxyClientCertCA, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertCA, zimbraReverseProxyClientCertCA);
        return attrs;
    }

    /**
     * CA certificate for authenticating client certificates in nginx proxy
     * (https only)
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1201)
    public void unsetReverseProxyClientCertCA() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertCA, "");
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
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertCA, "");
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @return zimbraReverseProxyClientCertMode, or ZAttrProvisioning.ReverseProxyClientCertMode.off if unset and/or has invalid value
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public ZAttrProvisioning.ReverseProxyClientCertMode getReverseProxyClientCertMode() {
        try { String v = getAttr(Provisioning.A_zimbraReverseProxyClientCertMode); return v == null ? ZAttrProvisioning.ReverseProxyClientCertMode.off : ZAttrProvisioning.ReverseProxyClientCertMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyClientCertMode.off; }
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @return zimbraReverseProxyClientCertMode, or "off" if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public String getReverseProxyClientCertModeAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyClientCertMode, "off");
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zimbraReverseProxyClientCertMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public void setReverseProxyClientCertMode(ZAttrProvisioning.ReverseProxyClientCertMode zimbraReverseProxyClientCertMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertMode, zimbraReverseProxyClientCertMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zimbraReverseProxyClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public Map<String,Object> setReverseProxyClientCertMode(ZAttrProvisioning.ReverseProxyClientCertMode zimbraReverseProxyClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertMode, zimbraReverseProxyClientCertMode.toString());
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zimbraReverseProxyClientCertMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public void setReverseProxyClientCertModeAsString(String zimbraReverseProxyClientCertMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertMode, zimbraReverseProxyClientCertMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @param zimbraReverseProxyClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public Map<String,Object> setReverseProxyClientCertModeAsString(String zimbraReverseProxyClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertMode, zimbraReverseProxyClientCertMode);
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate in nginx proxy
     * (https only)
     *
     * <p>Valid values: [off, on, optional]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1200)
    public void unsetReverseProxyClientCertMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertMode, "");
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
        attrs.put(Provisioning.A_zimbraReverseProxyClientCertMode, "");
        return attrs;
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getReverseProxyConnectTimeoutAsString to access value as a string.
     *
     * @see #getReverseProxyConnectTimeoutAsString()
     *
     * @return zimbraReverseProxyConnectTimeout in millseconds, or 120000 (120000ms)  if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public long getReverseProxyConnectTimeout() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyConnectTimeout, 120000L);
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zimbraReverseProxyConnectTimeout, or "120000ms" if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public String getReverseProxyConnectTimeoutAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyConnectTimeout, "120000ms");
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraReverseProxyConnectTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public void setReverseProxyConnectTimeout(String zimbraReverseProxyConnectTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyConnectTimeout, zimbraReverseProxyConnectTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraReverseProxyConnectTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public Map<String,Object> setReverseProxyConnectTimeout(String zimbraReverseProxyConnectTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyConnectTimeout, zimbraReverseProxyConnectTimeout);
        return attrs;
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public void unsetReverseProxyConnectTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyConnectTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public Map<String,Object> unsetReverseProxyConnectTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyConnectTimeout, "");
        return attrs;
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @return zimbraReverseProxyDefaultRealm, or null if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public String getReverseProxyDefaultRealm() {
        return getAttr(Provisioning.A_zimbraReverseProxyDefaultRealm, null);
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @param zimbraReverseProxyDefaultRealm new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public void setReverseProxyDefaultRealm(String zimbraReverseProxyDefaultRealm) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDefaultRealm, zimbraReverseProxyDefaultRealm);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @param zimbraReverseProxyDefaultRealm new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public Map<String,Object> setReverseProxyDefaultRealm(String zimbraReverseProxyDefaultRealm, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDefaultRealm, zimbraReverseProxyDefaultRealm);
        return attrs;
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public void unsetReverseProxyDefaultRealm() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDefaultRealm, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public Map<String,Object> unsetReverseProxyDefaultRealm(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDefaultRealm, "");
        return attrs;
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zimbraMailHost) is directly sent to proxy.
     *
     * @return zimbraReverseProxyDnsLookupInServerEnabled, or true if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public boolean isReverseProxyDnsLookupInServerEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyDnsLookupInServerEnabled, true);
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zimbraMailHost) is directly sent to proxy.
     *
     * @param zimbraReverseProxyDnsLookupInServerEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public void setReverseProxyDnsLookupInServerEnabled(boolean zimbraReverseProxyDnsLookupInServerEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDnsLookupInServerEnabled, zimbraReverseProxyDnsLookupInServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zimbraMailHost) is directly sent to proxy.
     *
     * @param zimbraReverseProxyDnsLookupInServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public Map<String,Object> setReverseProxyDnsLookupInServerEnabled(boolean zimbraReverseProxyDnsLookupInServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDnsLookupInServerEnabled, zimbraReverseProxyDnsLookupInServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zimbraMailHost) is directly sent to proxy.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public void unsetReverseProxyDnsLookupInServerEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDnsLookupInServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zimbraMailHost) is directly sent to proxy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public Map<String,Object> unsetReverseProxyDnsLookupInServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyDnsLookupInServerEnabled, "");
        return attrs;
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @return zimbraReverseProxyErrorHandlerURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public String getReverseProxyErrorHandlerURL() {
        return getAttr(Provisioning.A_zimbraReverseProxyErrorHandlerURL, null);
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @param zimbraReverseProxyErrorHandlerURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public void setReverseProxyErrorHandlerURL(String zimbraReverseProxyErrorHandlerURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyErrorHandlerURL, zimbraReverseProxyErrorHandlerURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @param zimbraReverseProxyErrorHandlerURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public Map<String,Object> setReverseProxyErrorHandlerURL(String zimbraReverseProxyErrorHandlerURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyErrorHandlerURL, zimbraReverseProxyErrorHandlerURL);
        return attrs;
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public void unsetReverseProxyErrorHandlerURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyErrorHandlerURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public Map<String,Object> unsetReverseProxyErrorHandlerURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyErrorHandlerURL, "");
        return attrs;
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @return zimbraReverseProxyGenConfigPerVirtualHostname, or true if unset
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public boolean isReverseProxyGenConfigPerVirtualHostname() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyGenConfigPerVirtualHostname, true);
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @param zimbraReverseProxyGenConfigPerVirtualHostname new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public void setReverseProxyGenConfigPerVirtualHostname(boolean zimbraReverseProxyGenConfigPerVirtualHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyGenConfigPerVirtualHostname, zimbraReverseProxyGenConfigPerVirtualHostname ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @param zimbraReverseProxyGenConfigPerVirtualHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public Map<String,Object> setReverseProxyGenConfigPerVirtualHostname(boolean zimbraReverseProxyGenConfigPerVirtualHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyGenConfigPerVirtualHostname, zimbraReverseProxyGenConfigPerVirtualHostname ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public void unsetReverseProxyGenConfigPerVirtualHostname() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyGenConfigPerVirtualHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public Map<String,Object> unsetReverseProxyGenConfigPerVirtualHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyGenConfigPerVirtualHostname, "");
        return attrs;
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @return zimbraReverseProxyHttpEnabled, or false if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public boolean isReverseProxyHttpEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyHttpEnabled, false);
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @param zimbraReverseProxyHttpEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public void setReverseProxyHttpEnabled(boolean zimbraReverseProxyHttpEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyHttpEnabled, zimbraReverseProxyHttpEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @param zimbraReverseProxyHttpEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public Map<String,Object> setReverseProxyHttpEnabled(boolean zimbraReverseProxyHttpEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyHttpEnabled, zimbraReverseProxyHttpEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public void unsetReverseProxyHttpEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyHttpEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public Map<String,Object> unsetReverseProxyHttpEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyHttpEnabled, "");
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @return zimbraReverseProxyImapEnabledCapability, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public String[] getReverseProxyImapEnabledCapability() {
        String[] value = getMultiAttr(Provisioning.A_zimbraReverseProxyImapEnabledCapability); return value.length > 0 ? value : new String[] {"IMAP4rev1","ACL","BINARY","CATENATE","CHILDREN","CONDSTORE","ENABLE","ESEARCH","ESORT","I18NLEVEL=1","ID","IDLE","LIST-EXTENDED","LIST-STATUS","LITERAL+","MULTIAPPEND","NAMESPACE","QRESYNC","QUOTA","RIGHTS=ektx","SASL-IR","SEARCHRES","SORT","THREAD=ORDEREDSUBJECT","UIDPLUS","UNSELECT","WITHIN","XLIST"};
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zimbraReverseProxyImapEnabledCapability new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void setReverseProxyImapEnabledCapability(String[] zimbraReverseProxyImapEnabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapEnabledCapability, zimbraReverseProxyImapEnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zimbraReverseProxyImapEnabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> setReverseProxyImapEnabledCapability(String[] zimbraReverseProxyImapEnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapEnabledCapability, zimbraReverseProxyImapEnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zimbraReverseProxyImapEnabledCapability new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void addReverseProxyImapEnabledCapability(String zimbraReverseProxyImapEnabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyImapEnabledCapability, zimbraReverseProxyImapEnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zimbraReverseProxyImapEnabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> addReverseProxyImapEnabledCapability(String zimbraReverseProxyImapEnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyImapEnabledCapability, zimbraReverseProxyImapEnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zimbraReverseProxyImapEnabledCapability existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void removeReverseProxyImapEnabledCapability(String zimbraReverseProxyImapEnabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyImapEnabledCapability, zimbraReverseProxyImapEnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zimbraReverseProxyImapEnabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> removeReverseProxyImapEnabledCapability(String zimbraReverseProxyImapEnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyImapEnabledCapability, zimbraReverseProxyImapEnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void unsetReverseProxyImapEnabledCapability() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapEnabledCapability, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> unsetReverseProxyImapEnabledCapability(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapEnabledCapability, "");
        return attrs;
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @return zimbraReverseProxyImapExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public boolean isReverseProxyImapExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyImapExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @param zimbraReverseProxyImapExposeVersionOnBanner new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public void setReverseProxyImapExposeVersionOnBanner(boolean zimbraReverseProxyImapExposeVersionOnBanner) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapExposeVersionOnBanner, zimbraReverseProxyImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @param zimbraReverseProxyImapExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public Map<String,Object> setReverseProxyImapExposeVersionOnBanner(boolean zimbraReverseProxyImapExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapExposeVersionOnBanner, zimbraReverseProxyImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public void unsetReverseProxyImapExposeVersionOnBanner() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapExposeVersionOnBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public Map<String,Object> unsetReverseProxyImapExposeVersionOnBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @return zimbraReverseProxyImapSaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public boolean isReverseProxyImapSaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyImapSaslGssapiEnabled, false);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @param zimbraReverseProxyImapSaslGssapiEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public void setReverseProxyImapSaslGssapiEnabled(boolean zimbraReverseProxyImapSaslGssapiEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslGssapiEnabled, zimbraReverseProxyImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @param zimbraReverseProxyImapSaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public Map<String,Object> setReverseProxyImapSaslGssapiEnabled(boolean zimbraReverseProxyImapSaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslGssapiEnabled, zimbraReverseProxyImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public void unsetReverseProxyImapSaslGssapiEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslGssapiEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public Map<String,Object> unsetReverseProxyImapSaslGssapiEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @return zimbraReverseProxyImapSaslPlainEnabled, or true if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public boolean isReverseProxyImapSaslPlainEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyImapSaslPlainEnabled, true);
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @param zimbraReverseProxyImapSaslPlainEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public void setReverseProxyImapSaslPlainEnabled(boolean zimbraReverseProxyImapSaslPlainEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslPlainEnabled, zimbraReverseProxyImapSaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @param zimbraReverseProxyImapSaslPlainEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public Map<String,Object> setReverseProxyImapSaslPlainEnabled(boolean zimbraReverseProxyImapSaslPlainEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslPlainEnabled, zimbraReverseProxyImapSaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public void unsetReverseProxyImapSaslPlainEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslPlainEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public Map<String,Object> unsetReverseProxyImapSaslPlainEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapSaslPlainEnabled, "");
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zimbraReverseProxyImapStartTlsMode, or ZAttrProvisioning.ReverseProxyImapStartTlsMode.only if unset and/or has invalid value
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public ZAttrProvisioning.ReverseProxyImapStartTlsMode getReverseProxyImapStartTlsMode() {
        try { String v = getAttr(Provisioning.A_zimbraReverseProxyImapStartTlsMode); return v == null ? ZAttrProvisioning.ReverseProxyImapStartTlsMode.only : ZAttrProvisioning.ReverseProxyImapStartTlsMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyImapStartTlsMode.only; }
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zimbraReverseProxyImapStartTlsMode, or "only" if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public String getReverseProxyImapStartTlsModeAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyImapStartTlsMode, "only");
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyImapStartTlsMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public void setReverseProxyImapStartTlsMode(ZAttrProvisioning.ReverseProxyImapStartTlsMode zimbraReverseProxyImapStartTlsMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapStartTlsMode, zimbraReverseProxyImapStartTlsMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyImapStartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public Map<String,Object> setReverseProxyImapStartTlsMode(ZAttrProvisioning.ReverseProxyImapStartTlsMode zimbraReverseProxyImapStartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapStartTlsMode, zimbraReverseProxyImapStartTlsMode.toString());
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyImapStartTlsMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public void setReverseProxyImapStartTlsModeAsString(String zimbraReverseProxyImapStartTlsMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapStartTlsMode, zimbraReverseProxyImapStartTlsMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyImapStartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public Map<String,Object> setReverseProxyImapStartTlsModeAsString(String zimbraReverseProxyImapStartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapStartTlsMode, zimbraReverseProxyImapStartTlsMode);
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public void unsetReverseProxyImapStartTlsMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapStartTlsMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public Map<String,Object> unsetReverseProxyImapStartTlsMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyImapStartTlsMode, "");
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. deprecated in favor of local config
     * &quot;imap_max_idle_time&quot;, &quot;pop3_max_idle_time&quot;,
     * &quot;imap_authenticated_max_idle_time&quot; in bug 59685. Orig desc:
     * Time interval after which NGINX mail proxy will disconnect an inactive
     * IMAP/POP connection. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getReverseProxyInactivityTimeoutAsString to access value as a string.
     *
     * @see #getReverseProxyInactivityTimeoutAsString()
     *
     * @return zimbraReverseProxyInactivityTimeout in millseconds, or 3600000 (1h)  if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public long getReverseProxyInactivityTimeout() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyInactivityTimeout, 3600000L);
    }

    /**
     * Deprecated since: 8.0.0. deprecated in favor of local config
     * &quot;imap_max_idle_time&quot;, &quot;pop3_max_idle_time&quot;,
     * &quot;imap_authenticated_max_idle_time&quot; in bug 59685. Orig desc:
     * Time interval after which NGINX mail proxy will disconnect an inactive
     * IMAP/POP connection. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraReverseProxyInactivityTimeout, or "1h" if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public String getReverseProxyInactivityTimeoutAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyInactivityTimeout, "1h");
    }

    /**
     * Deprecated since: 8.0.0. deprecated in favor of local config
     * &quot;imap_max_idle_time&quot;, &quot;pop3_max_idle_time&quot;,
     * &quot;imap_authenticated_max_idle_time&quot; in bug 59685. Orig desc:
     * Time interval after which NGINX mail proxy will disconnect an inactive
     * IMAP/POP connection. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyInactivityTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public void setReverseProxyInactivityTimeout(String zimbraReverseProxyInactivityTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyInactivityTimeout, zimbraReverseProxyInactivityTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. deprecated in favor of local config
     * &quot;imap_max_idle_time&quot;, &quot;pop3_max_idle_time&quot;,
     * &quot;imap_authenticated_max_idle_time&quot; in bug 59685. Orig desc:
     * Time interval after which NGINX mail proxy will disconnect an inactive
     * IMAP/POP connection. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyInactivityTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public Map<String,Object> setReverseProxyInactivityTimeout(String zimbraReverseProxyInactivityTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyInactivityTimeout, zimbraReverseProxyInactivityTimeout);
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. deprecated in favor of local config
     * &quot;imap_max_idle_time&quot;, &quot;pop3_max_idle_time&quot;,
     * &quot;imap_authenticated_max_idle_time&quot; in bug 59685. Orig desc:
     * Time interval after which NGINX mail proxy will disconnect an inactive
     * IMAP/POP connection. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public void unsetReverseProxyInactivityTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyInactivityTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. deprecated in favor of local config
     * &quot;imap_max_idle_time&quot;, &quot;pop3_max_idle_time&quot;,
     * &quot;imap_authenticated_max_idle_time&quot; in bug 59685. Orig desc:
     * Time interval after which NGINX mail proxy will disconnect an inactive
     * IMAP/POP connection. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public Map<String,Object> unsetReverseProxyInactivityTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyInactivityTimeout, "");
        return attrs;
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @return zimbraReverseProxyLogLevel, or ZAttrProvisioning.ReverseProxyLogLevel.info if unset and/or has invalid value
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public ZAttrProvisioning.ReverseProxyLogLevel getReverseProxyLogLevel() {
        try { String v = getAttr(Provisioning.A_zimbraReverseProxyLogLevel); return v == null ? ZAttrProvisioning.ReverseProxyLogLevel.info : ZAttrProvisioning.ReverseProxyLogLevel.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyLogLevel.info; }
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @return zimbraReverseProxyLogLevel, or "info" if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public String getReverseProxyLogLevelAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyLogLevel, "info");
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @param zimbraReverseProxyLogLevel new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public void setReverseProxyLogLevel(ZAttrProvisioning.ReverseProxyLogLevel zimbraReverseProxyLogLevel) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLogLevel, zimbraReverseProxyLogLevel.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @param zimbraReverseProxyLogLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public Map<String,Object> setReverseProxyLogLevel(ZAttrProvisioning.ReverseProxyLogLevel zimbraReverseProxyLogLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLogLevel, zimbraReverseProxyLogLevel.toString());
        return attrs;
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @param zimbraReverseProxyLogLevel new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public void setReverseProxyLogLevelAsString(String zimbraReverseProxyLogLevel) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLogLevel, zimbraReverseProxyLogLevel);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @param zimbraReverseProxyLogLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public Map<String,Object> setReverseProxyLogLevelAsString(String zimbraReverseProxyLogLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLogLevel, zimbraReverseProxyLogLevel);
        return attrs;
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public void unsetReverseProxyLogLevel() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLogLevel, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zimbra, notice, debug_core, info]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public Map<String,Object> unsetReverseProxyLogLevel(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLogLevel, "");
        return attrs;
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @return zimbraReverseProxyLookupTarget, or false if unset
     */
    @ZAttr(id=504)
    public boolean isReverseProxyLookupTarget() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyLookupTarget, false);
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @param zimbraReverseProxyLookupTarget new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=504)
    public void setReverseProxyLookupTarget(boolean zimbraReverseProxyLookupTarget) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLookupTarget, zimbraReverseProxyLookupTarget ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @param zimbraReverseProxyLookupTarget new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=504)
    public Map<String,Object> setReverseProxyLookupTarget(boolean zimbraReverseProxyLookupTarget, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLookupTarget, zimbraReverseProxyLookupTarget ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=504)
    public void unsetReverseProxyLookupTarget() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLookupTarget, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=504)
    public Map<String,Object> unsetReverseProxyLookupTarget(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyLookupTarget, "");
        return attrs;
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @return zimbraReverseProxyMailEnabled, or true if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public boolean isReverseProxyMailEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyMailEnabled, true);
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @param zimbraReverseProxyMailEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public void setReverseProxyMailEnabled(boolean zimbraReverseProxyMailEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailEnabled, zimbraReverseProxyMailEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @param zimbraReverseProxyMailEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public Map<String,Object> setReverseProxyMailEnabled(boolean zimbraReverseProxyMailEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailEnabled, zimbraReverseProxyMailEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public void unsetReverseProxyMailEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public Map<String,Object> unsetReverseProxyMailEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailEnabled, "");
        return attrs;
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zimbraReverseProxyMailMode, or null if unset and/or has invalid value
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public ZAttrProvisioning.ReverseProxyMailMode getReverseProxyMailMode() {
        try { String v = getAttr(Provisioning.A_zimbraReverseProxyMailMode); return v == null ? null : ZAttrProvisioning.ReverseProxyMailMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return null; }
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zimbraReverseProxyMailMode, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public String getReverseProxyMailModeAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyMailMode, null);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraReverseProxyMailMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public void setReverseProxyMailMode(ZAttrProvisioning.ReverseProxyMailMode zimbraReverseProxyMailMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailMode, zimbraReverseProxyMailMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraReverseProxyMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public Map<String,Object> setReverseProxyMailMode(ZAttrProvisioning.ReverseProxyMailMode zimbraReverseProxyMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailMode, zimbraReverseProxyMailMode.toString());
        return attrs;
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraReverseProxyMailMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public void setReverseProxyMailModeAsString(String zimbraReverseProxyMailMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailMode, zimbraReverseProxyMailMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zimbraReverseProxyMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public Map<String,Object> setReverseProxyMailModeAsString(String zimbraReverseProxyMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailMode, zimbraReverseProxyMailMode);
        return attrs;
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public void unsetReverseProxyMailMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zimbraMailProxyPort and
     * zimbraMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public Map<String,Object> unsetReverseProxyMailMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyMailMode, "");
        return attrs;
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @return zimbraReverseProxyPassErrors, or true if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public boolean isReverseProxyPassErrors() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyPassErrors, true);
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @param zimbraReverseProxyPassErrors new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public void setReverseProxyPassErrors(boolean zimbraReverseProxyPassErrors) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPassErrors, zimbraReverseProxyPassErrors ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @param zimbraReverseProxyPassErrors new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public Map<String,Object> setReverseProxyPassErrors(boolean zimbraReverseProxyPassErrors, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPassErrors, zimbraReverseProxyPassErrors ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public void unsetReverseProxyPassErrors() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPassErrors, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public Map<String,Object> unsetReverseProxyPassErrors(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPassErrors, "");
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @return zimbraReverseProxyPop3EnabledCapability, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public String[] getReverseProxyPop3EnabledCapability() {
        String[] value = getMultiAttr(Provisioning.A_zimbraReverseProxyPop3EnabledCapability); return value.length > 0 ? value : new String[] {"TOP","USER","UIDL","EXPIRE 31 USER","XOIP"};
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zimbraReverseProxyPop3EnabledCapability new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void setReverseProxyPop3EnabledCapability(String[] zimbraReverseProxyPop3EnabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3EnabledCapability, zimbraReverseProxyPop3EnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zimbraReverseProxyPop3EnabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> setReverseProxyPop3EnabledCapability(String[] zimbraReverseProxyPop3EnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3EnabledCapability, zimbraReverseProxyPop3EnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zimbraReverseProxyPop3EnabledCapability new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void addReverseProxyPop3EnabledCapability(String zimbraReverseProxyPop3EnabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyPop3EnabledCapability, zimbraReverseProxyPop3EnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zimbraReverseProxyPop3EnabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> addReverseProxyPop3EnabledCapability(String zimbraReverseProxyPop3EnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyPop3EnabledCapability, zimbraReverseProxyPop3EnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zimbraReverseProxyPop3EnabledCapability existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void removeReverseProxyPop3EnabledCapability(String zimbraReverseProxyPop3EnabledCapability) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyPop3EnabledCapability, zimbraReverseProxyPop3EnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zimbraReverseProxyPop3EnabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> removeReverseProxyPop3EnabledCapability(String zimbraReverseProxyPop3EnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyPop3EnabledCapability, zimbraReverseProxyPop3EnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void unsetReverseProxyPop3EnabledCapability() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3EnabledCapability, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> unsetReverseProxyPop3EnabledCapability(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3EnabledCapability, "");
        return attrs;
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @return zimbraReverseProxyPop3ExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public boolean isReverseProxyPop3ExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyPop3ExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @param zimbraReverseProxyPop3ExposeVersionOnBanner new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public void setReverseProxyPop3ExposeVersionOnBanner(boolean zimbraReverseProxyPop3ExposeVersionOnBanner) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3ExposeVersionOnBanner, zimbraReverseProxyPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @param zimbraReverseProxyPop3ExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public Map<String,Object> setReverseProxyPop3ExposeVersionOnBanner(boolean zimbraReverseProxyPop3ExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3ExposeVersionOnBanner, zimbraReverseProxyPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public void unsetReverseProxyPop3ExposeVersionOnBanner() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3ExposeVersionOnBanner, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public Map<String,Object> unsetReverseProxyPop3ExposeVersionOnBanner(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3ExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @return zimbraReverseProxyPop3SaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public boolean isReverseProxyPop3SaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyPop3SaslGssapiEnabled, false);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @param zimbraReverseProxyPop3SaslGssapiEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public void setReverseProxyPop3SaslGssapiEnabled(boolean zimbraReverseProxyPop3SaslGssapiEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslGssapiEnabled, zimbraReverseProxyPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @param zimbraReverseProxyPop3SaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public Map<String,Object> setReverseProxyPop3SaslGssapiEnabled(boolean zimbraReverseProxyPop3SaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslGssapiEnabled, zimbraReverseProxyPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public void unsetReverseProxyPop3SaslGssapiEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslGssapiEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public Map<String,Object> unsetReverseProxyPop3SaslGssapiEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @return zimbraReverseProxyPop3SaslPlainEnabled, or true if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public boolean isReverseProxyPop3SaslPlainEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxyPop3SaslPlainEnabled, true);
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @param zimbraReverseProxyPop3SaslPlainEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public void setReverseProxyPop3SaslPlainEnabled(boolean zimbraReverseProxyPop3SaslPlainEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslPlainEnabled, zimbraReverseProxyPop3SaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @param zimbraReverseProxyPop3SaslPlainEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public Map<String,Object> setReverseProxyPop3SaslPlainEnabled(boolean zimbraReverseProxyPop3SaslPlainEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslPlainEnabled, zimbraReverseProxyPop3SaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public void unsetReverseProxyPop3SaslPlainEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslPlainEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public Map<String,Object> unsetReverseProxyPop3SaslPlainEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3SaslPlainEnabled, "");
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zimbraReverseProxyPop3StartTlsMode, or ZAttrProvisioning.ReverseProxyPop3StartTlsMode.only if unset and/or has invalid value
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public ZAttrProvisioning.ReverseProxyPop3StartTlsMode getReverseProxyPop3StartTlsMode() {
        try { String v = getAttr(Provisioning.A_zimbraReverseProxyPop3StartTlsMode); return v == null ? ZAttrProvisioning.ReverseProxyPop3StartTlsMode.only : ZAttrProvisioning.ReverseProxyPop3StartTlsMode.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyPop3StartTlsMode.only; }
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zimbraReverseProxyPop3StartTlsMode, or "only" if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public String getReverseProxyPop3StartTlsModeAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, "only");
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyPop3StartTlsMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public void setReverseProxyPop3StartTlsMode(ZAttrProvisioning.ReverseProxyPop3StartTlsMode zimbraReverseProxyPop3StartTlsMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, zimbraReverseProxyPop3StartTlsMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyPop3StartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public Map<String,Object> setReverseProxyPop3StartTlsMode(ZAttrProvisioning.ReverseProxyPop3StartTlsMode zimbraReverseProxyPop3StartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, zimbraReverseProxyPop3StartTlsMode.toString());
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyPop3StartTlsMode new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public void setReverseProxyPop3StartTlsModeAsString(String zimbraReverseProxyPop3StartTlsMode) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, zimbraReverseProxyPop3StartTlsMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zimbraReverseProxyPop3StartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public Map<String,Object> setReverseProxyPop3StartTlsModeAsString(String zimbraReverseProxyPop3StartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, zimbraReverseProxyPop3StartTlsMode);
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public void unsetReverseProxyPop3StartTlsMode() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public Map<String,Object> unsetReverseProxyPop3StartTlsMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyPop3StartTlsMode, "");
        return attrs;
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getReverseProxyRouteLookupTimeoutAsString to access value as a string.
     *
     * @see #getReverseProxyRouteLookupTimeoutAsString()
     *
     * @return zimbraReverseProxyRouteLookupTimeout in millseconds, or 15000 (15s)  if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public long getReverseProxyRouteLookupTimeout() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyRouteLookupTimeout, 15000L);
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraReverseProxyRouteLookupTimeout, or "15s" if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public String getReverseProxyRouteLookupTimeoutAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyRouteLookupTimeout, "15s");
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyRouteLookupTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public void setReverseProxyRouteLookupTimeout(String zimbraReverseProxyRouteLookupTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeout, zimbraReverseProxyRouteLookupTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyRouteLookupTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public Map<String,Object> setReverseProxyRouteLookupTimeout(String zimbraReverseProxyRouteLookupTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeout, zimbraReverseProxyRouteLookupTimeout);
        return attrs;
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public void unsetReverseProxyRouteLookupTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public Map<String,Object> unsetReverseProxyRouteLookupTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeout, "");
        return attrs;
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getReverseProxyRouteLookupTimeoutCacheAsString to access value as a string.
     *
     * @see #getReverseProxyRouteLookupTimeoutCacheAsString()
     *
     * @return zimbraReverseProxyRouteLookupTimeoutCache in millseconds, or 60000 (60s)  if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public long getReverseProxyRouteLookupTimeoutCache() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyRouteLookupTimeoutCache, 60000L);
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraReverseProxyRouteLookupTimeoutCache, or "60s" if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public String getReverseProxyRouteLookupTimeoutCacheAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyRouteLookupTimeoutCache, "60s");
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyRouteLookupTimeoutCache new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public void setReverseProxyRouteLookupTimeoutCache(String zimbraReverseProxyRouteLookupTimeoutCache) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeoutCache, zimbraReverseProxyRouteLookupTimeoutCache);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyRouteLookupTimeoutCache new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public Map<String,Object> setReverseProxyRouteLookupTimeoutCache(String zimbraReverseProxyRouteLookupTimeoutCache, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeoutCache, zimbraReverseProxyRouteLookupTimeoutCache);
        return attrs;
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public void unsetReverseProxyRouteLookupTimeoutCache() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeoutCache, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public Map<String,Object> unsetReverseProxyRouteLookupTimeoutCache(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyRouteLookupTimeoutCache, "");
        return attrs;
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @return zimbraReverseProxySSLToUpstreamEnabled, or true if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public boolean isReverseProxySSLToUpstreamEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraReverseProxySSLToUpstreamEnabled, true);
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @param zimbraReverseProxySSLToUpstreamEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public void setReverseProxySSLToUpstreamEnabled(boolean zimbraReverseProxySSLToUpstreamEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxySSLToUpstreamEnabled, zimbraReverseProxySSLToUpstreamEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @param zimbraReverseProxySSLToUpstreamEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public Map<String,Object> setReverseProxySSLToUpstreamEnabled(boolean zimbraReverseProxySSLToUpstreamEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxySSLToUpstreamEnabled, zimbraReverseProxySSLToUpstreamEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public void unsetReverseProxySSLToUpstreamEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxySSLToUpstreamEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public Map<String,Object> unsetReverseProxySSLToUpstreamEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxySSLToUpstreamEnabled, "");
        return attrs;
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @return zimbraReverseProxyUpstreamConnectTimeout, or 25 if unset
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public int getReverseProxyUpstreamConnectTimeout() {
        return getIntAttr(Provisioning.A_zimbraReverseProxyUpstreamConnectTimeout, 25);
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @param zimbraReverseProxyUpstreamConnectTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public void setReverseProxyUpstreamConnectTimeout(int zimbraReverseProxyUpstreamConnectTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamConnectTimeout, Integer.toString(zimbraReverseProxyUpstreamConnectTimeout));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @param zimbraReverseProxyUpstreamConnectTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public Map<String,Object> setReverseProxyUpstreamConnectTimeout(int zimbraReverseProxyUpstreamConnectTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamConnectTimeout, Integer.toString(zimbraReverseProxyUpstreamConnectTimeout));
        return attrs;
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public void unsetReverseProxyUpstreamConnectTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamConnectTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public Map<String,Object> unsetReverseProxyUpstreamConnectTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamConnectTimeout, "");
        return attrs;
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getReverseProxyUpstreamPollingTimeoutAsString to access value as a string.
     *
     * @see #getReverseProxyUpstreamPollingTimeoutAsString()
     *
     * @return zimbraReverseProxyUpstreamPollingTimeout in millseconds, or 3600000 (1h)  if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public long getReverseProxyUpstreamPollingTimeout() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyUpstreamPollingTimeout, 3600000L);
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraReverseProxyUpstreamPollingTimeout, or "1h" if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public String getReverseProxyUpstreamPollingTimeoutAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyUpstreamPollingTimeout, "1h");
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyUpstreamPollingTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public void setReverseProxyUpstreamPollingTimeout(String zimbraReverseProxyUpstreamPollingTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamPollingTimeout, zimbraReverseProxyUpstreamPollingTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyUpstreamPollingTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public Map<String,Object> setReverseProxyUpstreamPollingTimeout(String zimbraReverseProxyUpstreamPollingTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamPollingTimeout, zimbraReverseProxyUpstreamPollingTimeout);
        return attrs;
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public void unsetReverseProxyUpstreamPollingTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamPollingTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public Map<String,Object> unsetReverseProxyUpstreamPollingTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamPollingTimeout, "");
        return attrs;
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getReverseProxyUpstreamReadTimeoutAsString to access value as a string.
     *
     * @see #getReverseProxyUpstreamReadTimeoutAsString()
     *
     * @return zimbraReverseProxyUpstreamReadTimeout in millseconds, or 60000 (60s)  if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public long getReverseProxyUpstreamReadTimeout() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyUpstreamReadTimeout, 60000L);
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zimbraReverseProxyUpstreamReadTimeout, or "60s" if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public String getReverseProxyUpstreamReadTimeoutAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyUpstreamReadTimeout, "60s");
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraReverseProxyUpstreamReadTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public void setReverseProxyUpstreamReadTimeout(String zimbraReverseProxyUpstreamReadTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamReadTimeout, zimbraReverseProxyUpstreamReadTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zimbraReverseProxyUpstreamReadTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public Map<String,Object> setReverseProxyUpstreamReadTimeout(String zimbraReverseProxyUpstreamReadTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamReadTimeout, zimbraReverseProxyUpstreamReadTimeout);
        return attrs;
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public void unsetReverseProxyUpstreamReadTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamReadTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public Map<String,Object> unsetReverseProxyUpstreamReadTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamReadTimeout, "");
        return attrs;
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getReverseProxyUpstreamSendTimeoutAsString to access value as a string.
     *
     * @see #getReverseProxyUpstreamSendTimeoutAsString()
     *
     * @return zimbraReverseProxyUpstreamSendTimeout in millseconds, or 60000 (60s)  if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public long getReverseProxyUpstreamSendTimeout() {
        return getTimeInterval(Provisioning.A_zimbraReverseProxyUpstreamSendTimeout, 60000L);
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraReverseProxyUpstreamSendTimeout, or "60s" if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public String getReverseProxyUpstreamSendTimeoutAsString() {
        return getAttr(Provisioning.A_zimbraReverseProxyUpstreamSendTimeout, "60s");
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyUpstreamSendTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public void setReverseProxyUpstreamSendTimeout(String zimbraReverseProxyUpstreamSendTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamSendTimeout, zimbraReverseProxyUpstreamSendTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraReverseProxyUpstreamSendTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public Map<String,Object> setReverseProxyUpstreamSendTimeout(String zimbraReverseProxyUpstreamSendTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamSendTimeout, zimbraReverseProxyUpstreamSendTimeout);
        return attrs;
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public void unsetReverseProxyUpstreamSendTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamSendTimeout, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public Map<String,Object> unsetReverseProxyUpstreamSendTimeout(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamSendTimeout, "");
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @return zimbraReverseProxyUpstreamServers, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public String[] getReverseProxyUpstreamServers() {
        return getMultiAttr(Provisioning.A_zimbraReverseProxyUpstreamServers);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param zimbraReverseProxyUpstreamServers new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void setReverseProxyUpstreamServers(String[] zimbraReverseProxyUpstreamServers) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamServers, zimbraReverseProxyUpstreamServers);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param zimbraReverseProxyUpstreamServers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> setReverseProxyUpstreamServers(String[] zimbraReverseProxyUpstreamServers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamServers, zimbraReverseProxyUpstreamServers);
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param zimbraReverseProxyUpstreamServers new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void addReverseProxyUpstreamServers(String zimbraReverseProxyUpstreamServers) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyUpstreamServers, zimbraReverseProxyUpstreamServers);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param zimbraReverseProxyUpstreamServers new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> addReverseProxyUpstreamServers(String zimbraReverseProxyUpstreamServers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraReverseProxyUpstreamServers, zimbraReverseProxyUpstreamServers);
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param zimbraReverseProxyUpstreamServers existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void removeReverseProxyUpstreamServers(String zimbraReverseProxyUpstreamServers) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyUpstreamServers, zimbraReverseProxyUpstreamServers);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param zimbraReverseProxyUpstreamServers existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> removeReverseProxyUpstreamServers(String zimbraReverseProxyUpstreamServers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraReverseProxyUpstreamServers, zimbraReverseProxyUpstreamServers);
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void unsetReverseProxyUpstreamServers() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamServers, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zimbraReverseProxyLookupTarget is TRUE.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> unsetReverseProxyUpstreamServers(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyUpstreamServers, "");
        return attrs;
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @return zimbraReverseProxyWorkerConnections, or 10240 if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public int getReverseProxyWorkerConnections() {
        return getIntAttr(Provisioning.A_zimbraReverseProxyWorkerConnections, 10240);
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @param zimbraReverseProxyWorkerConnections new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public void setReverseProxyWorkerConnections(int zimbraReverseProxyWorkerConnections) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerConnections, Integer.toString(zimbraReverseProxyWorkerConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @param zimbraReverseProxyWorkerConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public Map<String,Object> setReverseProxyWorkerConnections(int zimbraReverseProxyWorkerConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerConnections, Integer.toString(zimbraReverseProxyWorkerConnections));
        return attrs;
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public void unsetReverseProxyWorkerConnections() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerConnections, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public Map<String,Object> unsetReverseProxyWorkerConnections(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerConnections, "");
        return attrs;
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @return zimbraReverseProxyWorkerProcesses, or 4 if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public int getReverseProxyWorkerProcesses() {
        return getIntAttr(Provisioning.A_zimbraReverseProxyWorkerProcesses, 4);
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @param zimbraReverseProxyWorkerProcesses new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public void setReverseProxyWorkerProcesses(int zimbraReverseProxyWorkerProcesses) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerProcesses, Integer.toString(zimbraReverseProxyWorkerProcesses));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @param zimbraReverseProxyWorkerProcesses new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public Map<String,Object> setReverseProxyWorkerProcesses(int zimbraReverseProxyWorkerProcesses, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerProcesses, Integer.toString(zimbraReverseProxyWorkerProcesses));
        return attrs;
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public void unsetReverseProxyWorkerProcesses() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerProcesses, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public Map<String,Object> unsetReverseProxyWorkerProcesses(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraReverseProxyWorkerProcesses, "");
        return attrs;
    }

    /**
     * SSL certificate
     *
     * @return zimbraSSLCertificate, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public String getSSLCertificate() {
        return getAttr(Provisioning.A_zimbraSSLCertificate, null);
    }

    /**
     * SSL certificate
     *
     * @param zimbraSSLCertificate new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public void setSSLCertificate(String zimbraSSLCertificate) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSSLCertificate, zimbraSSLCertificate);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL certificate
     *
     * @param zimbraSSLCertificate new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public Map<String,Object> setSSLCertificate(String zimbraSSLCertificate, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSSLCertificate, zimbraSSLCertificate);
        return attrs;
    }

    /**
     * SSL certificate
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=563)
    public void unsetSSLCertificate() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSSLCertificate, "");
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
        attrs.put(Provisioning.A_zimbraSSLCertificate, "");
        return attrs;
    }

    /**
     * SSL private key
     *
     * @return zimbraSSLPrivateKey, or null if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public String getSSLPrivateKey() {
        return getAttr(Provisioning.A_zimbraSSLPrivateKey, null);
    }

    /**
     * SSL private key
     *
     * @param zimbraSSLPrivateKey new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public void setSSLPrivateKey(String zimbraSSLPrivateKey) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSSLPrivateKey, zimbraSSLPrivateKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL private key
     *
     * @param zimbraSSLPrivateKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public Map<String,Object> setSSLPrivateKey(String zimbraSSLPrivateKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSSLPrivateKey, zimbraSSLPrivateKey);
        return attrs;
    }

    /**
     * SSL private key
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=564)
    public void unsetSSLPrivateKey() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSSLPrivateKey, "");
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
        attrs.put(Provisioning.A_zimbraSSLPrivateKey, "");
        return attrs;
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @return zimbraSaslGssapiRequiresTls, or false if unset
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public boolean isSaslGssapiRequiresTls() {
        return getBooleanAttr(Provisioning.A_zimbraSaslGssapiRequiresTls, false);
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @param zimbraSaslGssapiRequiresTls new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public void setSaslGssapiRequiresTls(boolean zimbraSaslGssapiRequiresTls) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSaslGssapiRequiresTls, zimbraSaslGssapiRequiresTls ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @param zimbraSaslGssapiRequiresTls new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public Map<String,Object> setSaslGssapiRequiresTls(boolean zimbraSaslGssapiRequiresTls, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSaslGssapiRequiresTls, zimbraSaslGssapiRequiresTls ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public void unsetSaslGssapiRequiresTls() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSaslGssapiRequiresTls, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public Map<String,Object> unsetSaslGssapiRequiresTls(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSaslGssapiRequiresTls, "");
        return attrs;
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @return zimbraScheduledTaskNumThreads, or 20 if unset
     */
    @ZAttr(id=522)
    public int getScheduledTaskNumThreads() {
        return getIntAttr(Provisioning.A_zimbraScheduledTaskNumThreads, 20);
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @param zimbraScheduledTaskNumThreads new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=522)
    public void setScheduledTaskNumThreads(int zimbraScheduledTaskNumThreads) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraScheduledTaskNumThreads, Integer.toString(zimbraScheduledTaskNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @param zimbraScheduledTaskNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=522)
    public Map<String,Object> setScheduledTaskNumThreads(int zimbraScheduledTaskNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraScheduledTaskNumThreads, Integer.toString(zimbraScheduledTaskNumThreads));
        return attrs;
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=522)
    public void unsetScheduledTaskNumThreads() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraScheduledTaskNumThreads, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=522)
    public Map<String,Object> unsetScheduledTaskNumThreads(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraScheduledTaskNumThreads, "");
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @return zimbraServiceEnabled, or empty array if unset
     */
    @ZAttr(id=220)
    public String[] getServiceEnabled() {
        return getMultiAttr(Provisioning.A_zimbraServiceEnabled);
    }

    /**
     * services that are enabled on this server
     *
     * @param zimbraServiceEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void setServiceEnabled(String[] zimbraServiceEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceEnabled, zimbraServiceEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param zimbraServiceEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> setServiceEnabled(String[] zimbraServiceEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceEnabled, zimbraServiceEnabled);
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @param zimbraServiceEnabled new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void addServiceEnabled(String zimbraServiceEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraServiceEnabled, zimbraServiceEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param zimbraServiceEnabled new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> addServiceEnabled(String zimbraServiceEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraServiceEnabled, zimbraServiceEnabled);
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @param zimbraServiceEnabled existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void removeServiceEnabled(String zimbraServiceEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraServiceEnabled, zimbraServiceEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param zimbraServiceEnabled existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> removeServiceEnabled(String zimbraServiceEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraServiceEnabled, zimbraServiceEnabled);
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void unsetServiceEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> unsetServiceEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceEnabled, "");
        return attrs;
    }

    /**
     * public hostname of the host
     *
     * @return zimbraServiceHostname, or null if unset
     */
    @ZAttr(id=65)
    public String getServiceHostname() {
        return getAttr(Provisioning.A_zimbraServiceHostname, null);
    }

    /**
     * public hostname of the host
     *
     * @param zimbraServiceHostname new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=65)
    public void setServiceHostname(String zimbraServiceHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceHostname, zimbraServiceHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * public hostname of the host
     *
     * @param zimbraServiceHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=65)
    public Map<String,Object> setServiceHostname(String zimbraServiceHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceHostname, zimbraServiceHostname);
        return attrs;
    }

    /**
     * public hostname of the host
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=65)
    public void unsetServiceHostname() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * public hostname of the host
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=65)
    public Map<String,Object> unsetServiceHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceHostname, "");
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @return zimbraServiceInstalled, or empty array if unset
     */
    @ZAttr(id=221)
    public String[] getServiceInstalled() {
        return getMultiAttr(Provisioning.A_zimbraServiceInstalled);
    }

    /**
     * services that are installed on this server
     *
     * @param zimbraServiceInstalled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void setServiceInstalled(String[] zimbraServiceInstalled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceInstalled, zimbraServiceInstalled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param zimbraServiceInstalled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> setServiceInstalled(String[] zimbraServiceInstalled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceInstalled, zimbraServiceInstalled);
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @param zimbraServiceInstalled new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void addServiceInstalled(String zimbraServiceInstalled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraServiceInstalled, zimbraServiceInstalled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param zimbraServiceInstalled new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> addServiceInstalled(String zimbraServiceInstalled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraServiceInstalled, zimbraServiceInstalled);
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @param zimbraServiceInstalled existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void removeServiceInstalled(String zimbraServiceInstalled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraServiceInstalled, zimbraServiceInstalled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param zimbraServiceInstalled existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> removeServiceInstalled(String zimbraServiceInstalled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraServiceInstalled, zimbraServiceInstalled);
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void unsetServiceInstalled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceInstalled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> unsetServiceInstalled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraServiceInstalled, "");
        return attrs;
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @return zimbraShareNotificationMtaAuthAccount, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public String getShareNotificationMtaAuthAccount() {
        return getAttr(Provisioning.A_zimbraShareNotificationMtaAuthAccount, null);
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @param zimbraShareNotificationMtaAuthAccount new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public void setShareNotificationMtaAuthAccount(String zimbraShareNotificationMtaAuthAccount) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthAccount, zimbraShareNotificationMtaAuthAccount);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @param zimbraShareNotificationMtaAuthAccount new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public Map<String,Object> setShareNotificationMtaAuthAccount(String zimbraShareNotificationMtaAuthAccount, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthAccount, zimbraShareNotificationMtaAuthAccount);
        return attrs;
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public void unsetShareNotificationMtaAuthAccount() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthAccount, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public Map<String,Object> unsetShareNotificationMtaAuthAccount(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthAccount, "");
        return attrs;
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @return zimbraShareNotificationMtaAuthPassword, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public String getShareNotificationMtaAuthPassword() {
        return getAttr(Provisioning.A_zimbraShareNotificationMtaAuthPassword, null);
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @param zimbraShareNotificationMtaAuthPassword new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public void setShareNotificationMtaAuthPassword(String zimbraShareNotificationMtaAuthPassword) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthPassword, zimbraShareNotificationMtaAuthPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @param zimbraShareNotificationMtaAuthPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public Map<String,Object> setShareNotificationMtaAuthPassword(String zimbraShareNotificationMtaAuthPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthPassword, zimbraShareNotificationMtaAuthPassword);
        return attrs;
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public void unsetShareNotificationMtaAuthPassword() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthPassword, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public Map<String,Object> unsetShareNotificationMtaAuthPassword(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthPassword, "");
        return attrs;
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @return zimbraShareNotificationMtaAuthRequired, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public boolean isShareNotificationMtaAuthRequired() {
        return getBooleanAttr(Provisioning.A_zimbraShareNotificationMtaAuthRequired, false);
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @param zimbraShareNotificationMtaAuthRequired new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public void setShareNotificationMtaAuthRequired(boolean zimbraShareNotificationMtaAuthRequired) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthRequired, zimbraShareNotificationMtaAuthRequired ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @param zimbraShareNotificationMtaAuthRequired new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public Map<String,Object> setShareNotificationMtaAuthRequired(boolean zimbraShareNotificationMtaAuthRequired, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthRequired, zimbraShareNotificationMtaAuthRequired ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public void unsetShareNotificationMtaAuthRequired() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthRequired, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public Map<String,Object> unsetShareNotificationMtaAuthRequired(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaAuthRequired, "");
        return attrs;
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @return zimbraShareNotificationMtaConnectionType, or ZAttrProvisioning.ShareNotificationMtaConnectionType.CLEARTEXT if unset and/or has invalid value
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public ZAttrProvisioning.ShareNotificationMtaConnectionType getShareNotificationMtaConnectionType() {
        try { String v = getAttr(Provisioning.A_zimbraShareNotificationMtaConnectionType); return v == null ? ZAttrProvisioning.ShareNotificationMtaConnectionType.CLEARTEXT : ZAttrProvisioning.ShareNotificationMtaConnectionType.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.ShareNotificationMtaConnectionType.CLEARTEXT; }
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @return zimbraShareNotificationMtaConnectionType, or "CLEARTEXT" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public String getShareNotificationMtaConnectionTypeAsString() {
        return getAttr(Provisioning.A_zimbraShareNotificationMtaConnectionType, "CLEARTEXT");
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zimbraShareNotificationMtaConnectionType new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public void setShareNotificationMtaConnectionType(ZAttrProvisioning.ShareNotificationMtaConnectionType zimbraShareNotificationMtaConnectionType) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaConnectionType, zimbraShareNotificationMtaConnectionType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zimbraShareNotificationMtaConnectionType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public Map<String,Object> setShareNotificationMtaConnectionType(ZAttrProvisioning.ShareNotificationMtaConnectionType zimbraShareNotificationMtaConnectionType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaConnectionType, zimbraShareNotificationMtaConnectionType.toString());
        return attrs;
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zimbraShareNotificationMtaConnectionType new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public void setShareNotificationMtaConnectionTypeAsString(String zimbraShareNotificationMtaConnectionType) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaConnectionType, zimbraShareNotificationMtaConnectionType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zimbraShareNotificationMtaConnectionType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public Map<String,Object> setShareNotificationMtaConnectionTypeAsString(String zimbraShareNotificationMtaConnectionType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaConnectionType, zimbraShareNotificationMtaConnectionType);
        return attrs;
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public void unsetShareNotificationMtaConnectionType() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaConnectionType, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public Map<String,Object> unsetShareNotificationMtaConnectionType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaConnectionType, "");
        return attrs;
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @return zimbraShareNotificationMtaEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public boolean isShareNotificationMtaEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraShareNotificationMtaEnabled, false);
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @param zimbraShareNotificationMtaEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public void setShareNotificationMtaEnabled(boolean zimbraShareNotificationMtaEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaEnabled, zimbraShareNotificationMtaEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @param zimbraShareNotificationMtaEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public Map<String,Object> setShareNotificationMtaEnabled(boolean zimbraShareNotificationMtaEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaEnabled, zimbraShareNotificationMtaEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public void unsetShareNotificationMtaEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public Map<String,Object> unsetShareNotificationMtaEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaEnabled, "");
        return attrs;
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @return zimbraShareNotificationMtaHostname, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public String getShareNotificationMtaHostname() {
        return getAttr(Provisioning.A_zimbraShareNotificationMtaHostname, null);
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @param zimbraShareNotificationMtaHostname new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public void setShareNotificationMtaHostname(String zimbraShareNotificationMtaHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaHostname, zimbraShareNotificationMtaHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @param zimbraShareNotificationMtaHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public Map<String,Object> setShareNotificationMtaHostname(String zimbraShareNotificationMtaHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaHostname, zimbraShareNotificationMtaHostname);
        return attrs;
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public void unsetShareNotificationMtaHostname() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaHostname, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public Map<String,Object> unsetShareNotificationMtaHostname(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaHostname, "");
        return attrs;
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @return zimbraShareNotificationMtaPort, or -1 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public int getShareNotificationMtaPort() {
        return getIntAttr(Provisioning.A_zimbraShareNotificationMtaPort, -1);
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @param zimbraShareNotificationMtaPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public void setShareNotificationMtaPort(int zimbraShareNotificationMtaPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaPort, Integer.toString(zimbraShareNotificationMtaPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @param zimbraShareNotificationMtaPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public Map<String,Object> setShareNotificationMtaPort(int zimbraShareNotificationMtaPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaPort, Integer.toString(zimbraShareNotificationMtaPort));
        return attrs;
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public void unsetShareNotificationMtaPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaPort, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public Map<String,Object> unsetShareNotificationMtaPort(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraShareNotificationMtaPort, "");
        return attrs;
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getSharingUpdatePublishIntervalAsString to access value as a string.
     *
     * @see #getSharingUpdatePublishIntervalAsString()
     *
     * @return zimbraSharingUpdatePublishInterval in millseconds, or 900000 (15m)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public long getSharingUpdatePublishInterval() {
        return getTimeInterval(Provisioning.A_zimbraSharingUpdatePublishInterval, 900000L);
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zimbraSharingUpdatePublishInterval, or "15m" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public String getSharingUpdatePublishIntervalAsString() {
        return getAttr(Provisioning.A_zimbraSharingUpdatePublishInterval, "15m");
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraSharingUpdatePublishInterval new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public void setSharingUpdatePublishInterval(String zimbraSharingUpdatePublishInterval) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSharingUpdatePublishInterval, zimbraSharingUpdatePublishInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zimbraSharingUpdatePublishInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public Map<String,Object> setSharingUpdatePublishInterval(String zimbraSharingUpdatePublishInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSharingUpdatePublishInterval, zimbraSharingUpdatePublishInterval);
        return attrs;
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public void unsetSharingUpdatePublishInterval() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSharingUpdatePublishInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public Map<String,Object> unsetSharingUpdatePublishInterval(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSharingUpdatePublishInterval, "");
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @return zimbraSmtpHostname, or empty array if unset
     */
    @ZAttr(id=97)
    public String[] getSmtpHostname() {
        String[] value = getMultiAttr(Provisioning.A_zimbraSmtpHostname); return value.length > 0 ? value : new String[] {"localhost"};
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zimbraSmtpHostname new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void setSmtpHostname(String[] zimbraSmtpHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpHostname, zimbraSmtpHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zimbraSmtpHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> setSmtpHostname(String[] zimbraSmtpHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpHostname, zimbraSmtpHostname);
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zimbraSmtpHostname new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void addSmtpHostname(String zimbraSmtpHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraSmtpHostname, zimbraSmtpHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zimbraSmtpHostname new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> addSmtpHostname(String zimbraSmtpHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraSmtpHostname, zimbraSmtpHostname);
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zimbraSmtpHostname existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void removeSmtpHostname(String zimbraSmtpHostname) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraSmtpHostname, zimbraSmtpHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @param zimbraSmtpHostname existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=97)
    public Map<String,Object> removeSmtpHostname(String zimbraSmtpHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraSmtpHostname, zimbraSmtpHostname);
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=97)
    public void unsetSmtpHostname() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpHostname, "");
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
        attrs.put(Provisioning.A_zimbraSmtpHostname, "");
        return attrs;
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * <p>Use getSmtpPortAsString to access value as a string.
     *
     * @see #getSmtpPortAsString()
     *
     * @return zimbraSmtpPort, or 25 if unset
     */
    @ZAttr(id=98)
    public int getSmtpPort() {
        return getIntAttr(Provisioning.A_zimbraSmtpPort, 25);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @return zimbraSmtpPort, or "25" if unset
     */
    @ZAttr(id=98)
    public String getSmtpPortAsString() {
        return getAttr(Provisioning.A_zimbraSmtpPort, "25");
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zimbraSmtpPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=98)
    public void setSmtpPort(int zimbraSmtpPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpPort, Integer.toString(zimbraSmtpPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zimbraSmtpPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=98)
    public Map<String,Object> setSmtpPort(int zimbraSmtpPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpPort, Integer.toString(zimbraSmtpPort));
        return attrs;
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zimbraSmtpPort new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=98)
    public void setSmtpPortAsString(String zimbraSmtpPort) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpPort, zimbraSmtpPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @param zimbraSmtpPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=98)
    public Map<String,Object> setSmtpPortAsString(String zimbraSmtpPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpPort, zimbraSmtpPort);
        return attrs;
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=98)
    public void unsetSmtpPort() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpPort, "");
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
        attrs.put(Provisioning.A_zimbraSmtpPort, "");
        return attrs;
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @return zimbraSmtpSendPartial, or false if unset
     */
    @ZAttr(id=249)
    public boolean isSmtpSendPartial() {
        return getBooleanAttr(Provisioning.A_zimbraSmtpSendPartial, false);
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @param zimbraSmtpSendPartial new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=249)
    public void setSmtpSendPartial(boolean zimbraSmtpSendPartial) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpSendPartial, zimbraSmtpSendPartial ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @param zimbraSmtpSendPartial new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=249)
    public Map<String,Object> setSmtpSendPartial(boolean zimbraSmtpSendPartial, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpSendPartial, zimbraSmtpSendPartial ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Value of the mail.smtp.sendpartial property
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=249)
    public void unsetSmtpSendPartial() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpSendPartial, "");
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
        attrs.put(Provisioning.A_zimbraSmtpSendPartial, "");
        return attrs;
    }

    /**
     * timeout value in seconds
     *
     * @return zimbraSmtpTimeout, or 60 if unset
     */
    @ZAttr(id=99)
    public int getSmtpTimeout() {
        return getIntAttr(Provisioning.A_zimbraSmtpTimeout, 60);
    }

    /**
     * timeout value in seconds
     *
     * @param zimbraSmtpTimeout new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=99)
    public void setSmtpTimeout(int zimbraSmtpTimeout) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpTimeout, Integer.toString(zimbraSmtpTimeout));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * timeout value in seconds
     *
     * @param zimbraSmtpTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=99)
    public Map<String,Object> setSmtpTimeout(int zimbraSmtpTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpTimeout, Integer.toString(zimbraSmtpTimeout));
        return attrs;
    }

    /**
     * timeout value in seconds
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=99)
    public void unsetSmtpTimeout() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSmtpTimeout, "");
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
        attrs.put(Provisioning.A_zimbraSmtpTimeout, "");
        return attrs;
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @return zimbraSoapExposeVersion, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public boolean isSoapExposeVersion() {
        return getBooleanAttr(Provisioning.A_zimbraSoapExposeVersion, false);
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @param zimbraSoapExposeVersion new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public void setSoapExposeVersion(boolean zimbraSoapExposeVersion) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapExposeVersion, zimbraSoapExposeVersion ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @param zimbraSoapExposeVersion new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public Map<String,Object> setSoapExposeVersion(boolean zimbraSoapExposeVersion, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapExposeVersion, zimbraSoapExposeVersion ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public void unsetSoapExposeVersion() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapExposeVersion, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public Map<String,Object> unsetSoapExposeVersion(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapExposeVersion, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @return zimbraSoapRequestMaxSize, or 15360000 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public int getSoapRequestMaxSize() {
        return getIntAttr(Provisioning.A_zimbraSoapRequestMaxSize, 15360000);
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @param zimbraSoapRequestMaxSize new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public void setSoapRequestMaxSize(int zimbraSoapRequestMaxSize) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapRequestMaxSize, Integer.toString(zimbraSoapRequestMaxSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @param zimbraSoapRequestMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public Map<String,Object> setSoapRequestMaxSize(int zimbraSoapRequestMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapRequestMaxSize, Integer.toString(zimbraSoapRequestMaxSize));
        return attrs;
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public void unsetSoapRequestMaxSize() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapRequestMaxSize, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public Map<String,Object> unsetSoapRequestMaxSize(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSoapRequestMaxSize, "");
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @return zimbraSpellAvailableDictionary, or empty array if unset
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public String[] getSpellAvailableDictionary() {
        String[] value = getMultiAttr(Provisioning.A_zimbraSpellAvailableDictionary); return value.length > 0 ? value : new String[] {"en_US"};
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zimbraSpellAvailableDictionary new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void setSpellAvailableDictionary(String[] zimbraSpellAvailableDictionary) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellAvailableDictionary, zimbraSpellAvailableDictionary);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zimbraSpellAvailableDictionary new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> setSpellAvailableDictionary(String[] zimbraSpellAvailableDictionary, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellAvailableDictionary, zimbraSpellAvailableDictionary);
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zimbraSpellAvailableDictionary new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void addSpellAvailableDictionary(String zimbraSpellAvailableDictionary) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraSpellAvailableDictionary, zimbraSpellAvailableDictionary);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zimbraSpellAvailableDictionary new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> addSpellAvailableDictionary(String zimbraSpellAvailableDictionary, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraSpellAvailableDictionary, zimbraSpellAvailableDictionary);
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zimbraSpellAvailableDictionary existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void removeSpellAvailableDictionary(String zimbraSpellAvailableDictionary) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraSpellAvailableDictionary, zimbraSpellAvailableDictionary);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zimbraSpellAvailableDictionary existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> removeSpellAvailableDictionary(String zimbraSpellAvailableDictionary, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraSpellAvailableDictionary, zimbraSpellAvailableDictionary);
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void unsetSpellAvailableDictionary() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellAvailableDictionary, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> unsetSpellAvailableDictionary(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellAvailableDictionary, "");
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @return zimbraSpellCheckURL, or empty array if unset
     */
    @ZAttr(id=267)
    public String[] getSpellCheckURL() {
        return getMultiAttr(Provisioning.A_zimbraSpellCheckURL);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zimbraSpellCheckURL new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void setSpellCheckURL(String[] zimbraSpellCheckURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellCheckURL, zimbraSpellCheckURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zimbraSpellCheckURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> setSpellCheckURL(String[] zimbraSpellCheckURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellCheckURL, zimbraSpellCheckURL);
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zimbraSpellCheckURL new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void addSpellCheckURL(String zimbraSpellCheckURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraSpellCheckURL, zimbraSpellCheckURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zimbraSpellCheckURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> addSpellCheckURL(String zimbraSpellCheckURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraSpellCheckURL, zimbraSpellCheckURL);
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zimbraSpellCheckURL existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void removeSpellCheckURL(String zimbraSpellCheckURL) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraSpellCheckURL, zimbraSpellCheckURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zimbraSpellCheckURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> removeSpellCheckURL(String zimbraSpellCheckURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraSpellCheckURL, zimbraSpellCheckURL);
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void unsetSpellCheckURL() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellCheckURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> unsetSpellCheckURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpellCheckURL, "");
        return attrs;
    }

    /**
     * spnego auth principal
     *
     * @return zimbraSpnegoAuthPrincipal, or null if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public String getSpnegoAuthPrincipal() {
        return getAttr(Provisioning.A_zimbraSpnegoAuthPrincipal, null);
    }

    /**
     * spnego auth principal
     *
     * @param zimbraSpnegoAuthPrincipal new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public void setSpnegoAuthPrincipal(String zimbraSpnegoAuthPrincipal) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthPrincipal, zimbraSpnegoAuthPrincipal);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * spnego auth principal
     *
     * @param zimbraSpnegoAuthPrincipal new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public Map<String,Object> setSpnegoAuthPrincipal(String zimbraSpnegoAuthPrincipal, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthPrincipal, zimbraSpnegoAuthPrincipal);
        return attrs;
    }

    /**
     * spnego auth principal
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public void unsetSpnegoAuthPrincipal() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthPrincipal, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * spnego auth principal
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public Map<String,Object> unsetSpnegoAuthPrincipal(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthPrincipal, "");
        return attrs;
    }

    /**
     * spnego auth target name
     *
     * @return zimbraSpnegoAuthTargetName, or null if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public String getSpnegoAuthTargetName() {
        return getAttr(Provisioning.A_zimbraSpnegoAuthTargetName, null);
    }

    /**
     * spnego auth target name
     *
     * @param zimbraSpnegoAuthTargetName new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public void setSpnegoAuthTargetName(String zimbraSpnegoAuthTargetName) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthTargetName, zimbraSpnegoAuthTargetName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * spnego auth target name
     *
     * @param zimbraSpnegoAuthTargetName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public Map<String,Object> setSpnegoAuthTargetName(String zimbraSpnegoAuthTargetName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthTargetName, zimbraSpnegoAuthTargetName);
        return attrs;
    }

    /**
     * spnego auth target name
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public void unsetSpnegoAuthTargetName() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthTargetName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * spnego auth target name
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public Map<String,Object> unsetSpnegoAuthTargetName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSpnegoAuthTargetName, "");
        return attrs;
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @return zimbraSshPublicKey, or null if unset
     */
    @ZAttr(id=262)
    public String getSshPublicKey() {
        return getAttr(Provisioning.A_zimbraSshPublicKey, null);
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @param zimbraSshPublicKey new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=262)
    public void setSshPublicKey(String zimbraSshPublicKey) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSshPublicKey, zimbraSshPublicKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @param zimbraSshPublicKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=262)
    public Map<String,Object> setSshPublicKey(String zimbraSshPublicKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSshPublicKey, zimbraSshPublicKey);
        return attrs;
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=262)
    public void unsetSshPublicKey() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSshPublicKey, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=262)
    public Map<String,Object> unsetSshPublicKey(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraSshPublicKey, "");
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @return zimbraStatThreadNamePrefix, or empty array if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public String[] getStatThreadNamePrefix() {
        String[] value = getMultiAttr(Provisioning.A_zimbraStatThreadNamePrefix); return value.length > 0 ? value : new String[] {"btpool","pool","LmtpServer","ImapServer","ImapSSLServer","Pop3Server","Pop3SSLServer","ScheduledTask","Timer","AnonymousIoService","CloudRoutingReaderThread","GC","SocketAcceptor","Thread"};
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zimbraStatThreadNamePrefix new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void setStatThreadNamePrefix(String[] zimbraStatThreadNamePrefix) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraStatThreadNamePrefix, zimbraStatThreadNamePrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zimbraStatThreadNamePrefix new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> setStatThreadNamePrefix(String[] zimbraStatThreadNamePrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraStatThreadNamePrefix, zimbraStatThreadNamePrefix);
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zimbraStatThreadNamePrefix new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void addStatThreadNamePrefix(String zimbraStatThreadNamePrefix) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraStatThreadNamePrefix, zimbraStatThreadNamePrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zimbraStatThreadNamePrefix new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> addStatThreadNamePrefix(String zimbraStatThreadNamePrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraStatThreadNamePrefix, zimbraStatThreadNamePrefix);
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zimbraStatThreadNamePrefix existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void removeStatThreadNamePrefix(String zimbraStatThreadNamePrefix) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraStatThreadNamePrefix, zimbraStatThreadNamePrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zimbraStatThreadNamePrefix existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> removeStatThreadNamePrefix(String zimbraStatThreadNamePrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraStatThreadNamePrefix, zimbraStatThreadNamePrefix);
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void unsetStatThreadNamePrefix() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraStatThreadNamePrefix, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> unsetStatThreadNamePrefix(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraStatThreadNamePrefix, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @return zimbraTableMaintenanceGrowthFactor, or 10 if unset
     */
    @ZAttr(id=171)
    public int getTableMaintenanceGrowthFactor() {
        return getIntAttr(Provisioning.A_zimbraTableMaintenanceGrowthFactor, 10);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @param zimbraTableMaintenanceGrowthFactor new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=171)
    public void setTableMaintenanceGrowthFactor(int zimbraTableMaintenanceGrowthFactor) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceGrowthFactor, Integer.toString(zimbraTableMaintenanceGrowthFactor));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @param zimbraTableMaintenanceGrowthFactor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=171)
    public Map<String,Object> setTableMaintenanceGrowthFactor(int zimbraTableMaintenanceGrowthFactor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceGrowthFactor, Integer.toString(zimbraTableMaintenanceGrowthFactor));
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=171)
    public void unsetTableMaintenanceGrowthFactor() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceGrowthFactor, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=171)
    public Map<String,Object> unsetTableMaintenanceGrowthFactor(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceGrowthFactor, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @return zimbraTableMaintenanceMaxRows, or 1000000 if unset
     */
    @ZAttr(id=169)
    public int getTableMaintenanceMaxRows() {
        return getIntAttr(Provisioning.A_zimbraTableMaintenanceMaxRows, 1000000);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @param zimbraTableMaintenanceMaxRows new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=169)
    public void setTableMaintenanceMaxRows(int zimbraTableMaintenanceMaxRows) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMaxRows, Integer.toString(zimbraTableMaintenanceMaxRows));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @param zimbraTableMaintenanceMaxRows new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=169)
    public Map<String,Object> setTableMaintenanceMaxRows(int zimbraTableMaintenanceMaxRows, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMaxRows, Integer.toString(zimbraTableMaintenanceMaxRows));
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=169)
    public void unsetTableMaintenanceMaxRows() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMaxRows, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=169)
    public Map<String,Object> unsetTableMaintenanceMaxRows(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMaxRows, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @return zimbraTableMaintenanceMinRows, or 10000 if unset
     */
    @ZAttr(id=168)
    public int getTableMaintenanceMinRows() {
        return getIntAttr(Provisioning.A_zimbraTableMaintenanceMinRows, 10000);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @param zimbraTableMaintenanceMinRows new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=168)
    public void setTableMaintenanceMinRows(int zimbraTableMaintenanceMinRows) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMinRows, Integer.toString(zimbraTableMaintenanceMinRows));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @param zimbraTableMaintenanceMinRows new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=168)
    public Map<String,Object> setTableMaintenanceMinRows(int zimbraTableMaintenanceMinRows, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMinRows, Integer.toString(zimbraTableMaintenanceMinRows));
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=168)
    public void unsetTableMaintenanceMinRows() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMinRows, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=168)
    public Map<String,Object> unsetTableMaintenanceMinRows(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceMinRows, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @return zimbraTableMaintenanceOperation, or ZAttrProvisioning.TableMaintenanceOperation.ANALYZE if unset and/or has invalid value
     */
    @ZAttr(id=170)
    public ZAttrProvisioning.TableMaintenanceOperation getTableMaintenanceOperation() {
        try { String v = getAttr(Provisioning.A_zimbraTableMaintenanceOperation); return v == null ? ZAttrProvisioning.TableMaintenanceOperation.ANALYZE : ZAttrProvisioning.TableMaintenanceOperation.fromString(v); } catch(com.zimbra.common.service.ServiceException e) { return ZAttrProvisioning.TableMaintenanceOperation.ANALYZE; }
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @return zimbraTableMaintenanceOperation, or "ANALYZE" if unset
     */
    @ZAttr(id=170)
    public String getTableMaintenanceOperationAsString() {
        return getAttr(Provisioning.A_zimbraTableMaintenanceOperation, "ANALYZE");
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zimbraTableMaintenanceOperation new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=170)
    public void setTableMaintenanceOperation(ZAttrProvisioning.TableMaintenanceOperation zimbraTableMaintenanceOperation) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceOperation, zimbraTableMaintenanceOperation.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zimbraTableMaintenanceOperation new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=170)
    public Map<String,Object> setTableMaintenanceOperation(ZAttrProvisioning.TableMaintenanceOperation zimbraTableMaintenanceOperation, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceOperation, zimbraTableMaintenanceOperation.toString());
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zimbraTableMaintenanceOperation new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=170)
    public void setTableMaintenanceOperationAsString(String zimbraTableMaintenanceOperation) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceOperation, zimbraTableMaintenanceOperation);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zimbraTableMaintenanceOperation new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=170)
    public Map<String,Object> setTableMaintenanceOperationAsString(String zimbraTableMaintenanceOperation, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceOperation, zimbraTableMaintenanceOperation);
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=170)
    public void unsetTableMaintenanceOperation() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceOperation, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=170)
    public Map<String,Object> unsetTableMaintenanceOperation(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraTableMaintenanceOperation, "");
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @return zimbraThrottleSafeHosts, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public String[] getThrottleSafeHosts() {
        return getMultiAttr(Provisioning.A_zimbraThrottleSafeHosts);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zimbraThrottleSafeHosts new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void setThrottleSafeHosts(String[] zimbraThrottleSafeHosts) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraThrottleSafeHosts, zimbraThrottleSafeHosts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zimbraThrottleSafeHosts new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> setThrottleSafeHosts(String[] zimbraThrottleSafeHosts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraThrottleSafeHosts, zimbraThrottleSafeHosts);
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zimbraThrottleSafeHosts new to add to existing values
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void addThrottleSafeHosts(String zimbraThrottleSafeHosts) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraThrottleSafeHosts, zimbraThrottleSafeHosts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zimbraThrottleSafeHosts new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> addThrottleSafeHosts(String zimbraThrottleSafeHosts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zimbraThrottleSafeHosts, zimbraThrottleSafeHosts);
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zimbraThrottleSafeHosts existing value to remove
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void removeThrottleSafeHosts(String zimbraThrottleSafeHosts) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraThrottleSafeHosts, zimbraThrottleSafeHosts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zimbraThrottleSafeHosts existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> removeThrottleSafeHosts(String zimbraThrottleSafeHosts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zimbraThrottleSafeHosts, zimbraThrottleSafeHosts);
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void unsetThrottleSafeHosts() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraThrottleSafeHosts, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> unsetThrottleSafeHosts(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraThrottleSafeHosts, "");
        return attrs;
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @return zimbraUserServicesEnabled, or false if unset
     */
    @ZAttr(id=146)
    public boolean isUserServicesEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraUserServicesEnabled, false);
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @param zimbraUserServicesEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=146)
    public void setUserServicesEnabled(boolean zimbraUserServicesEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraUserServicesEnabled, zimbraUserServicesEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @param zimbraUserServicesEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=146)
    public Map<String,Object> setUserServicesEnabled(boolean zimbraUserServicesEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraUserServicesEnabled, zimbraUserServicesEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=146)
    public void unsetUserServicesEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraUserServicesEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=146)
    public Map<String,Object> unsetUserServicesEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraUserServicesEnabled, "");
        return attrs;
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * <p>Use getVirusDefinitionsUpdateFrequencyAsString to access value as a string.
     *
     * @see #getVirusDefinitionsUpdateFrequencyAsString()
     *
     * @return zimbraVirusDefinitionsUpdateFrequency in millseconds, or 7200000 (2h)  if unset
     */
    @ZAttr(id=191)
    public long getVirusDefinitionsUpdateFrequency() {
        return getTimeInterval(Provisioning.A_zimbraVirusDefinitionsUpdateFrequency, 7200000L);
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @return zimbraVirusDefinitionsUpdateFrequency, or "2h" if unset
     */
    @ZAttr(id=191)
    public String getVirusDefinitionsUpdateFrequencyAsString() {
        return getAttr(Provisioning.A_zimbraVirusDefinitionsUpdateFrequency, "2h");
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zimbraVirusDefinitionsUpdateFrequency new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=191)
    public void setVirusDefinitionsUpdateFrequency(String zimbraVirusDefinitionsUpdateFrequency) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraVirusDefinitionsUpdateFrequency, zimbraVirusDefinitionsUpdateFrequency);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zimbraVirusDefinitionsUpdateFrequency new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=191)
    public Map<String,Object> setVirusDefinitionsUpdateFrequency(String zimbraVirusDefinitionsUpdateFrequency, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraVirusDefinitionsUpdateFrequency, zimbraVirusDefinitionsUpdateFrequency);
        return attrs;
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=191)
    public void unsetVirusDefinitionsUpdateFrequency() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraVirusDefinitionsUpdateFrequency, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=191)
    public Map<String,Object> unsetVirusDefinitionsUpdateFrequency(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraVirusDefinitionsUpdateFrequency, "");
        return attrs;
    }

    /**
     * Enable XMPP support for IM
     *
     * @return zimbraXMPPEnabled, or true if unset
     */
    @ZAttr(id=397)
    public boolean isXMPPEnabled() {
        return getBooleanAttr(Provisioning.A_zimbraXMPPEnabled, true);
    }

    /**
     * Enable XMPP support for IM
     *
     * @param zimbraXMPPEnabled new value
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=397)
    public void setXMPPEnabled(boolean zimbraXMPPEnabled) throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraXMPPEnabled, zimbraXMPPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Enable XMPP support for IM
     *
     * @param zimbraXMPPEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=397)
    public Map<String,Object> setXMPPEnabled(boolean zimbraXMPPEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraXMPPEnabled, zimbraXMPPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Enable XMPP support for IM
     *
     * @throws com.zimbra.common.service.ServiceException if error during update
     */
    @ZAttr(id=397)
    public void unsetXMPPEnabled() throws com.zimbra.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraXMPPEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Enable XMPP support for IM
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=397)
    public Map<String,Object> unsetXMPPEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zimbraXMPPEnabled, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE

}
