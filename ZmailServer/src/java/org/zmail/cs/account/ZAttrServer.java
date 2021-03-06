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
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setCn(String cn) throws org.zmail.common.service.ServiceException {
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
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetCn() throws org.zmail.common.service.ServiceException {
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
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @return zmailAdminBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public String getAdminBindAddress() {
        return getAttr(Provisioning.A_zmailAdminBindAddress, null);
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @param zmailAdminBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public void setAdminBindAddress(String zmailAdminBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminBindAddress, zmailAdminBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @param zmailAdminBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public Map<String,Object> setAdminBindAddress(String zmailAdminBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminBindAddress, zmailAdminBindAddress);
        return attrs;
    }

    /**
     * interface address on which Admin HTTPS server should listen; if empty,
     * binds to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1368)
    public void unsetAdminBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminBindAddress, "");
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
        attrs.put(Provisioning.A_zmailAdminBindAddress, "");
        return attrs;
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @return zmailAdminImapImportNumThreads, or 20 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public int getAdminImapImportNumThreads() {
        return getIntAttr(Provisioning.A_zmailAdminImapImportNumThreads, 20);
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @param zmailAdminImapImportNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public void setAdminImapImportNumThreads(int zmailAdminImapImportNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminImapImportNumThreads, Integer.toString(zmailAdminImapImportNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @param zmailAdminImapImportNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public Map<String,Object> setAdminImapImportNumThreads(int zmailAdminImapImportNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminImapImportNumThreads, Integer.toString(zmailAdminImapImportNumThreads));
        return attrs;
    }

    /**
     * number of admin initiated imap import handler threads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1113)
    public void unsetAdminImapImportNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminImapImportNumThreads, "");
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
        attrs.put(Provisioning.A_zmailAdminImapImportNumThreads, "");
        return attrs;
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zmailAdminBindAddress.
     *
     * @return zmailAdminLocalBind, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public boolean isAdminLocalBind() {
        return getBooleanAttr(Provisioning.A_zmailAdminLocalBind, false);
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zmailAdminBindAddress.
     *
     * @param zmailAdminLocalBind new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public void setAdminLocalBind(boolean zmailAdminLocalBind) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminLocalBind, zmailAdminLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zmailAdminBindAddress.
     *
     * @param zmailAdminLocalBind new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public Map<String,Object> setAdminLocalBind(boolean zmailAdminLocalBind, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminLocalBind, zmailAdminLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zmailAdminBindAddress.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public void unsetAdminLocalBind() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminLocalBind, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the admin server should bound to localhost or not.
     * This is an immutable property and is generated based on
     * zmailAdminBindAddress.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1377)
    public Map<String,Object> unsetAdminLocalBind(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminLocalBind, "");
        return attrs;
    }

    /**
     * SSL port for admin UI
     *
     * <p>Use getAdminPortAsString to access value as a string.
     *
     * @see #getAdminPortAsString()
     *
     * @return zmailAdminPort, or 7071 if unset
     */
    @ZAttr(id=155)
    public int getAdminPort() {
        return getIntAttr(Provisioning.A_zmailAdminPort, 7071);
    }

    /**
     * SSL port for admin UI
     *
     * @return zmailAdminPort, or "7071" if unset
     */
    @ZAttr(id=155)
    public String getAdminPortAsString() {
        return getAttr(Provisioning.A_zmailAdminPort, "7071");
    }

    /**
     * SSL port for admin UI
     *
     * @param zmailAdminPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=155)
    public void setAdminPort(int zmailAdminPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminPort, Integer.toString(zmailAdminPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for admin UI
     *
     * @param zmailAdminPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=155)
    public Map<String,Object> setAdminPort(int zmailAdminPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminPort, Integer.toString(zmailAdminPort));
        return attrs;
    }

    /**
     * SSL port for admin UI
     *
     * @param zmailAdminPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=155)
    public void setAdminPortAsString(String zmailAdminPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminPort, zmailAdminPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for admin UI
     *
     * @param zmailAdminPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=155)
    public Map<String,Object> setAdminPortAsString(String zmailAdminPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminPort, zmailAdminPort);
        return attrs;
    }

    /**
     * SSL port for admin UI
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=155)
    public void unsetAdminPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminPort, "");
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
        attrs.put(Provisioning.A_zmailAdminPort, "");
        return attrs;
    }

    /**
     * SSL proxy port for admin console UI
     *
     * <p>Use getAdminProxyPortAsString to access value as a string.
     *
     * @see #getAdminProxyPortAsString()
     *
     * @return zmailAdminProxyPort, or 9071 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public int getAdminProxyPort() {
        return getIntAttr(Provisioning.A_zmailAdminProxyPort, 9071);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @return zmailAdminProxyPort, or "9071" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public String getAdminProxyPortAsString() {
        return getAttr(Provisioning.A_zmailAdminProxyPort, "9071");
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zmailAdminProxyPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public void setAdminProxyPort(int zmailAdminProxyPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminProxyPort, Integer.toString(zmailAdminProxyPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zmailAdminProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public Map<String,Object> setAdminProxyPort(int zmailAdminProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminProxyPort, Integer.toString(zmailAdminProxyPort));
        return attrs;
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zmailAdminProxyPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public void setAdminProxyPortAsString(String zmailAdminProxyPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminProxyPort, zmailAdminProxyPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @param zmailAdminProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public Map<String,Object> setAdminProxyPortAsString(String zmailAdminProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminProxyPort, zmailAdminProxyPort);
        return attrs;
    }

    /**
     * SSL proxy port for admin console UI
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1322)
    public void unsetAdminProxyPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminProxyPort, "");
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
        attrs.put(Provisioning.A_zmailAdminProxyPort, "");
        return attrs;
    }

    /**
     * URL prefix for where the zmailAdmin app resides on this server
     *
     * @return zmailAdminURL, or "/zmailAdmin" if unset
     */
    @ZAttr(id=497)
    public String getAdminURL() {
        return getAttr(Provisioning.A_zmailAdminURL, "/zmailAdmin");
    }

    /**
     * URL prefix for where the zmailAdmin app resides on this server
     *
     * @param zmailAdminURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=497)
    public void setAdminURL(String zmailAdminURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminURL, zmailAdminURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zmailAdmin app resides on this server
     *
     * @param zmailAdminURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=497)
    public Map<String,Object> setAdminURL(String zmailAdminURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminURL, zmailAdminURL);
        return attrs;
    }

    /**
     * URL prefix for where the zmailAdmin app resides on this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=497)
    public void unsetAdminURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zmailAdmin app resides on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=497)
    public Map<String,Object> unsetAdminURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminURL, "");
        return attrs;
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @return zmailAttachmentsIndexedTextLimit, or 1048576 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public int getAttachmentsIndexedTextLimit() {
        return getIntAttr(Provisioning.A_zmailAttachmentsIndexedTextLimit, 1048576);
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @param zmailAttachmentsIndexedTextLimit new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public void setAttachmentsIndexedTextLimit(int zmailAttachmentsIndexedTextLimit) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAttachmentsIndexedTextLimit, Integer.toString(zmailAttachmentsIndexedTextLimit));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @param zmailAttachmentsIndexedTextLimit new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public Map<String,Object> setAttachmentsIndexedTextLimit(int zmailAttachmentsIndexedTextLimit, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAttachmentsIndexedTextLimit, Integer.toString(zmailAttachmentsIndexedTextLimit));
        return attrs;
    }

    /**
     * Maximum number of characters that will be indexed for a given MIME
     * part.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=582)
    public void unsetAttachmentsIndexedTextLimit() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAttachmentsIndexedTextLimit, "");
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
        attrs.put(Provisioning.A_zmailAttachmentsIndexedTextLimit, "");
        return attrs;
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @return zmailAttachmentsScanURL, or null if unset
     */
    @ZAttr(id=239)
    public String getAttachmentsScanURL() {
        return getAttr(Provisioning.A_zmailAttachmentsScanURL, null);
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @param zmailAttachmentsScanURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=239)
    public void setAttachmentsScanURL(String zmailAttachmentsScanURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAttachmentsScanURL, zmailAttachmentsScanURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @param zmailAttachmentsScanURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=239)
    public Map<String,Object> setAttachmentsScanURL(String zmailAttachmentsScanURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAttachmentsScanURL, zmailAttachmentsScanURL);
        return attrs;
    }

    /**
     * Data for class that scans attachments during compose
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=239)
    public void unsetAttachmentsScanURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAttachmentsScanURL, "");
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
        attrs.put(Provisioning.A_zmailAttachmentsScanURL, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zmailAutoProvBatchSize and number of domains configured in
     * zmailAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zmailAutoProvScheduledDomains
     * and auto creates up to domain.zmailAutoProvBatchSize accounts. If
     * that process takes longer than zmailAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zmailAutoProvPollingInterval amount of time. If set to 0 when server
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
     * @return zmailAutoProvPollingInterval in millseconds, or 900000 (15m)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public long getAutoProvPollingInterval() {
        return getTimeInterval(Provisioning.A_zmailAutoProvPollingInterval, 900000L);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zmailAutoProvBatchSize and number of domains configured in
     * zmailAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zmailAutoProvScheduledDomains
     * and auto creates up to domain.zmailAutoProvBatchSize accounts. If
     * that process takes longer than zmailAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zmailAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailAutoProvPollingInterval, or "15m" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public String getAutoProvPollingIntervalAsString() {
        return getAttr(Provisioning.A_zmailAutoProvPollingInterval, "15m");
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zmailAutoProvBatchSize and number of domains configured in
     * zmailAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zmailAutoProvScheduledDomains
     * and auto creates up to domain.zmailAutoProvBatchSize accounts. If
     * that process takes longer than zmailAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zmailAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailAutoProvPollingInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public void setAutoProvPollingInterval(String zmailAutoProvPollingInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvPollingInterval, zmailAutoProvPollingInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zmailAutoProvBatchSize and number of domains configured in
     * zmailAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zmailAutoProvScheduledDomains
     * and auto creates up to domain.zmailAutoProvBatchSize accounts. If
     * that process takes longer than zmailAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zmailAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailAutoProvPollingInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public Map<String,Object> setAutoProvPollingInterval(String zmailAutoProvPollingInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvPollingInterval, zmailAutoProvPollingInterval);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zmailAutoProvBatchSize and number of domains configured in
     * zmailAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zmailAutoProvScheduledDomains
     * and auto creates up to domain.zmailAutoProvBatchSize accounts. If
     * that process takes longer than zmailAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zmailAutoProvPollingInterval amount of time. If set to 0 when server
     * starts up, the auto provision thread will not start. If changed from a
     * non-0 value to 0 while server is running, the auto provision thread
     * will be shutdown. If changed from 0 to a non-0 value while server is
     * running, the auto provision thread will be started. . Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1238)
    public void unsetAutoProvPollingInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvPollingInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Interval between
     * successive polling and provisioning accounts in EAGER mode. The actual
     * interval may take longer since it can be affected by two other
     * factors: zmailAutoProvBatchSize and number of domains configured in
     * zmailAutoProvScheduledDomains. At each interval, the auto provision
     * thread iterates through all domains in zmailAutoProvScheduledDomains
     * and auto creates up to domain.zmailAutoProvBatchSize accounts. If
     * that process takes longer than zmailAutoProvPollingInterval then the
     * next iteration will start immediately instead of waiting for
     * zmailAutoProvPollingInterval amount of time. If set to 0 when server
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
        attrs.put(Provisioning.A_zmailAutoProvPollingInterval, "");
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @return zmailAutoProvScheduledDomains, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public String[] getAutoProvScheduledDomains() {
        return getMultiAttr(Provisioning.A_zmailAutoProvScheduledDomains);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zmailAutoProvScheduledDomains new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void setAutoProvScheduledDomains(String[] zmailAutoProvScheduledDomains) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvScheduledDomains, zmailAutoProvScheduledDomains);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zmailAutoProvScheduledDomains new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> setAutoProvScheduledDomains(String[] zmailAutoProvScheduledDomains, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvScheduledDomains, zmailAutoProvScheduledDomains);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zmailAutoProvScheduledDomains new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void addAutoProvScheduledDomains(String zmailAutoProvScheduledDomains) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAutoProvScheduledDomains, zmailAutoProvScheduledDomains);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zmailAutoProvScheduledDomains new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> addAutoProvScheduledDomains(String zmailAutoProvScheduledDomains, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAutoProvScheduledDomains, zmailAutoProvScheduledDomains);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zmailAutoProvScheduledDomains existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void removeAutoProvScheduledDomains(String zmailAutoProvScheduledDomains) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAutoProvScheduledDomains, zmailAutoProvScheduledDomains);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @param zmailAutoProvScheduledDomains existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public Map<String,Object> removeAutoProvScheduledDomains(String zmailAutoProvScheduledDomains, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAutoProvScheduledDomains, zmailAutoProvScheduledDomains);
        return attrs;
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
     * scheduled on a server for EAGER auto provision. Also, a domain can be
     * scheduled on multiple servers for EAGER auto provision.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1237)
    public void unsetAutoProvScheduledDomains() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAutoProvScheduledDomains, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * EAGER mode: required LAZY mode: N/A MANUAL mode: N/A Domain scheduled
     * for eager auto provision on this server. Scheduled domains must have
     * EAGER mode enabled in zmailAutoProvMode. Multiple domains can be
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
        attrs.put(Provisioning.A_zmailAutoProvScheduledDomains, "");
        return attrs;
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @return zmailBackupAutoGroupedInterval, or "1d" if unset
     */
    @ZAttr(id=513)
    public String getBackupAutoGroupedInterval() {
        return getAttr(Provisioning.A_zmailBackupAutoGroupedInterval, "1d");
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @param zmailBackupAutoGroupedInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=513)
    public void setBackupAutoGroupedInterval(String zmailBackupAutoGroupedInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedInterval, zmailBackupAutoGroupedInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @param zmailBackupAutoGroupedInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=513)
    public Map<String,Object> setBackupAutoGroupedInterval(String zmailBackupAutoGroupedInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedInterval, zmailBackupAutoGroupedInterval);
        return attrs;
    }

    /**
     * length of each interval in auto-grouped backup
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=513)
    public void unsetBackupAutoGroupedInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedInterval, "");
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
        attrs.put(Provisioning.A_zmailBackupAutoGroupedInterval, "");
        return attrs;
    }

    /**
     * number of groups to auto-group backups over
     *
     * @return zmailBackupAutoGroupedNumGroups, or 7 if unset
     */
    @ZAttr(id=514)
    public int getBackupAutoGroupedNumGroups() {
        return getIntAttr(Provisioning.A_zmailBackupAutoGroupedNumGroups, 7);
    }

    /**
     * number of groups to auto-group backups over
     *
     * @param zmailBackupAutoGroupedNumGroups new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=514)
    public void setBackupAutoGroupedNumGroups(int zmailBackupAutoGroupedNumGroups) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedNumGroups, Integer.toString(zmailBackupAutoGroupedNumGroups));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of groups to auto-group backups over
     *
     * @param zmailBackupAutoGroupedNumGroups new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=514)
    public Map<String,Object> setBackupAutoGroupedNumGroups(int zmailBackupAutoGroupedNumGroups, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedNumGroups, Integer.toString(zmailBackupAutoGroupedNumGroups));
        return attrs;
    }

    /**
     * number of groups to auto-group backups over
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=514)
    public void unsetBackupAutoGroupedNumGroups() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedNumGroups, "");
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
        attrs.put(Provisioning.A_zmailBackupAutoGroupedNumGroups, "");
        return attrs;
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @return zmailBackupAutoGroupedThrottled, or false if unset
     */
    @ZAttr(id=515)
    public boolean isBackupAutoGroupedThrottled() {
        return getBooleanAttr(Provisioning.A_zmailBackupAutoGroupedThrottled, false);
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @param zmailBackupAutoGroupedThrottled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=515)
    public void setBackupAutoGroupedThrottled(boolean zmailBackupAutoGroupedThrottled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedThrottled, zmailBackupAutoGroupedThrottled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @param zmailBackupAutoGroupedThrottled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=515)
    public Map<String,Object> setBackupAutoGroupedThrottled(boolean zmailBackupAutoGroupedThrottled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedThrottled, zmailBackupAutoGroupedThrottled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, limit the number of mailboxes in auto-grouped backup to total
     * mailboxes divided by auto-group days
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=515)
    public void unsetBackupAutoGroupedThrottled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupAutoGroupedThrottled, "");
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
        attrs.put(Provisioning.A_zmailBackupAutoGroupedThrottled, "");
        return attrs;
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @return zmailBackupMinFreeSpace, or "0" if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public String getBackupMinFreeSpace() {
        return getAttr(Provisioning.A_zmailBackupMinFreeSpace, "0");
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @param zmailBackupMinFreeSpace new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public void setBackupMinFreeSpace(String zmailBackupMinFreeSpace) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMinFreeSpace, zmailBackupMinFreeSpace);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @param zmailBackupMinFreeSpace new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public Map<String,Object> setBackupMinFreeSpace(String zmailBackupMinFreeSpace, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMinFreeSpace, zmailBackupMinFreeSpace);
        return attrs;
    }

    /**
     * Minimum percentage or TB/GB/MB/KB/bytes of free space on backup target
     * to allow a full or auto-grouped backup to start; 0 = no minimum is
     * enforced. Examples: 25%, 10GB
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1111)
    public void unsetBackupMinFreeSpace() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMinFreeSpace, "");
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
        attrs.put(Provisioning.A_zmailBackupMinFreeSpace, "");
        return attrs;
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @return zmailBackupMode, or ZAttrProvisioning.BackupMode.Standard if unset and/or has invalid value
     */
    @ZAttr(id=512)
    public ZAttrProvisioning.BackupMode getBackupMode() {
        try { String v = getAttr(Provisioning.A_zmailBackupMode); return v == null ? ZAttrProvisioning.BackupMode.Standard : ZAttrProvisioning.BackupMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.BackupMode.Standard; }
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @return zmailBackupMode, or "Standard" if unset
     */
    @ZAttr(id=512)
    public String getBackupModeAsString() {
        return getAttr(Provisioning.A_zmailBackupMode, "Standard");
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zmailBackupMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=512)
    public void setBackupMode(ZAttrProvisioning.BackupMode zmailBackupMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMode, zmailBackupMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zmailBackupMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=512)
    public Map<String,Object> setBackupMode(ZAttrProvisioning.BackupMode zmailBackupMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMode, zmailBackupMode.toString());
        return attrs;
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zmailBackupMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=512)
    public void setBackupModeAsString(String zmailBackupMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMode, zmailBackupMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @param zmailBackupMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=512)
    public Map<String,Object> setBackupModeAsString(String zmailBackupMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMode, zmailBackupMode);
        return attrs;
    }

    /**
     * backup mode
     *
     * <p>Valid values: [Auto-Grouped, Standard]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=512)
    public void unsetBackupMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupMode, "");
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
        attrs.put(Provisioning.A_zmailBackupMode, "");
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @return zmailBackupReportEmailRecipients, or empty array if unset
     */
    @ZAttr(id=459)
    public String[] getBackupReportEmailRecipients() {
        return getMultiAttr(Provisioning.A_zmailBackupReportEmailRecipients);
    }

    /**
     * Backup report email recipients
     *
     * @param zmailBackupReportEmailRecipients new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void setBackupReportEmailRecipients(String[] zmailBackupReportEmailRecipients) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailRecipients, zmailBackupReportEmailRecipients);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param zmailBackupReportEmailRecipients new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> setBackupReportEmailRecipients(String[] zmailBackupReportEmailRecipients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailRecipients, zmailBackupReportEmailRecipients);
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @param zmailBackupReportEmailRecipients new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void addBackupReportEmailRecipients(String zmailBackupReportEmailRecipients) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailBackupReportEmailRecipients, zmailBackupReportEmailRecipients);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param zmailBackupReportEmailRecipients new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> addBackupReportEmailRecipients(String zmailBackupReportEmailRecipients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailBackupReportEmailRecipients, zmailBackupReportEmailRecipients);
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @param zmailBackupReportEmailRecipients existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void removeBackupReportEmailRecipients(String zmailBackupReportEmailRecipients) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailBackupReportEmailRecipients, zmailBackupReportEmailRecipients);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email recipients
     *
     * @param zmailBackupReportEmailRecipients existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=459)
    public Map<String,Object> removeBackupReportEmailRecipients(String zmailBackupReportEmailRecipients, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailBackupReportEmailRecipients, zmailBackupReportEmailRecipients);
        return attrs;
    }

    /**
     * Backup report email recipients
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=459)
    public void unsetBackupReportEmailRecipients() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailRecipients, "");
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
        attrs.put(Provisioning.A_zmailBackupReportEmailRecipients, "");
        return attrs;
    }

    /**
     * Backup report email From address
     *
     * @return zmailBackupReportEmailSender, or null if unset
     */
    @ZAttr(id=460)
    public String getBackupReportEmailSender() {
        return getAttr(Provisioning.A_zmailBackupReportEmailSender, null);
    }

    /**
     * Backup report email From address
     *
     * @param zmailBackupReportEmailSender new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=460)
    public void setBackupReportEmailSender(String zmailBackupReportEmailSender) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailSender, zmailBackupReportEmailSender);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email From address
     *
     * @param zmailBackupReportEmailSender new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=460)
    public Map<String,Object> setBackupReportEmailSender(String zmailBackupReportEmailSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailSender, zmailBackupReportEmailSender);
        return attrs;
    }

    /**
     * Backup report email From address
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=460)
    public void unsetBackupReportEmailSender() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailSender, "");
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
        attrs.put(Provisioning.A_zmailBackupReportEmailSender, "");
        return attrs;
    }

    /**
     * Backup report email subject prefix
     *
     * @return zmailBackupReportEmailSubjectPrefix, or "ZCS Backup Report" if unset
     */
    @ZAttr(id=461)
    public String getBackupReportEmailSubjectPrefix() {
        return getAttr(Provisioning.A_zmailBackupReportEmailSubjectPrefix, "ZCS Backup Report");
    }

    /**
     * Backup report email subject prefix
     *
     * @param zmailBackupReportEmailSubjectPrefix new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=461)
    public void setBackupReportEmailSubjectPrefix(String zmailBackupReportEmailSubjectPrefix) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailSubjectPrefix, zmailBackupReportEmailSubjectPrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Backup report email subject prefix
     *
     * @param zmailBackupReportEmailSubjectPrefix new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=461)
    public Map<String,Object> setBackupReportEmailSubjectPrefix(String zmailBackupReportEmailSubjectPrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailSubjectPrefix, zmailBackupReportEmailSubjectPrefix);
        return attrs;
    }

    /**
     * Backup report email subject prefix
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=461)
    public void unsetBackupReportEmailSubjectPrefix() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupReportEmailSubjectPrefix, "");
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
        attrs.put(Provisioning.A_zmailBackupReportEmailSubjectPrefix, "");
        return attrs;
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @return zmailBackupSkipBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public boolean isBackupSkipBlobs() {
        return getBooleanAttr(Provisioning.A_zmailBackupSkipBlobs, false);
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @param zmailBackupSkipBlobs new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public void setBackupSkipBlobs(boolean zmailBackupSkipBlobs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipBlobs, zmailBackupSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @param zmailBackupSkipBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public Map<String,Object> setBackupSkipBlobs(boolean zmailBackupSkipBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipBlobs, zmailBackupSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, do not backup blobs (HSM or not) during a full backup
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1004)
    public void unsetBackupSkipBlobs() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipBlobs, "");
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
        attrs.put(Provisioning.A_zmailBackupSkipBlobs, "");
        return attrs;
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @return zmailBackupSkipHsmBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public boolean isBackupSkipHsmBlobs() {
        return getBooleanAttr(Provisioning.A_zmailBackupSkipHsmBlobs, false);
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @param zmailBackupSkipHsmBlobs new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public void setBackupSkipHsmBlobs(boolean zmailBackupSkipHsmBlobs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipHsmBlobs, zmailBackupSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @param zmailBackupSkipHsmBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public Map<String,Object> setBackupSkipHsmBlobs(boolean zmailBackupSkipHsmBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipHsmBlobs, zmailBackupSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, do not backup blobs on secondary (HSM) volumes during a full
     * backup
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1005)
    public void unsetBackupSkipHsmBlobs() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipHsmBlobs, "");
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
        attrs.put(Provisioning.A_zmailBackupSkipHsmBlobs, "");
        return attrs;
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @return zmailBackupSkipSearchIndex, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public boolean isBackupSkipSearchIndex() {
        return getBooleanAttr(Provisioning.A_zmailBackupSkipSearchIndex, false);
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @param zmailBackupSkipSearchIndex new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public void setBackupSkipSearchIndex(boolean zmailBackupSkipSearchIndex) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipSearchIndex, zmailBackupSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @param zmailBackupSkipSearchIndex new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public Map<String,Object> setBackupSkipSearchIndex(boolean zmailBackupSkipSearchIndex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipSearchIndex, zmailBackupSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, do not backup search index during a full backup
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1003)
    public void unsetBackupSkipSearchIndex() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupSkipSearchIndex, "");
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
        attrs.put(Provisioning.A_zmailBackupSkipSearchIndex, "");
        return attrs;
    }

    /**
     * Default backup target path
     *
     * @return zmailBackupTarget, or "/opt/zmail/backup" if unset
     */
    @ZAttr(id=458)
    public String getBackupTarget() {
        return getAttr(Provisioning.A_zmailBackupTarget, "/opt/zmail/backup");
    }

    /**
     * Default backup target path
     *
     * @param zmailBackupTarget new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=458)
    public void setBackupTarget(String zmailBackupTarget) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupTarget, zmailBackupTarget);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Default backup target path
     *
     * @param zmailBackupTarget new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=458)
    public Map<String,Object> setBackupTarget(String zmailBackupTarget, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupTarget, zmailBackupTarget);
        return attrs;
    }

    /**
     * Default backup target path
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=458)
    public void unsetBackupTarget() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailBackupTarget, "");
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
        attrs.put(Provisioning.A_zmailBackupTarget, "");
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zmailMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zmailMailPort.
     *
     * @return zmailCalendarCalDavClearTextPasswordEnabled, or true if unset
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public boolean isCalendarCalDavClearTextPasswordEnabled() {
        return getBooleanAttr(Provisioning.A_zmailCalendarCalDavClearTextPasswordEnabled, true);
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zmailMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zmailMailPort.
     *
     * @param zmailCalendarCalDavClearTextPasswordEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public void setCalendarCalDavClearTextPasswordEnabled(boolean zmailCalendarCalDavClearTextPasswordEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavClearTextPasswordEnabled, zmailCalendarCalDavClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zmailMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zmailMailPort.
     *
     * @param zmailCalendarCalDavClearTextPasswordEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public Map<String,Object> setCalendarCalDavClearTextPasswordEnabled(boolean zmailCalendarCalDavClearTextPasswordEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavClearTextPasswordEnabled, zmailCalendarCalDavClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zmailMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zmailMailPort.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public void unsetCalendarCalDavClearTextPasswordEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavClearTextPasswordEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port from CalDAV
     * clients. If it set to TRUE the server will allow access from CalDAV
     * client to zmailMailPort. If it set to FALSE the server will return an
     * error if a request is made from CalDAV client to zmailMailPort.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=820)
    public Map<String,Object> unsetCalendarCalDavClearTextPasswordEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavClearTextPasswordEnabled, "");
        return attrs;
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @return zmailCalendarCalDavDefaultCalendarId, or 10 if unset
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public int getCalendarCalDavDefaultCalendarId() {
        return getIntAttr(Provisioning.A_zmailCalendarCalDavDefaultCalendarId, 10);
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @param zmailCalendarCalDavDefaultCalendarId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public void setCalendarCalDavDefaultCalendarId(int zmailCalendarCalDavDefaultCalendarId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavDefaultCalendarId, Integer.toString(zmailCalendarCalDavDefaultCalendarId));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @param zmailCalendarCalDavDefaultCalendarId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public Map<String,Object> setCalendarCalDavDefaultCalendarId(int zmailCalendarCalDavDefaultCalendarId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavDefaultCalendarId, Integer.toString(zmailCalendarCalDavDefaultCalendarId));
        return attrs;
    }

    /**
     * Id of calendar folder to advertise as the default calendar to CalDAV
     * client.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1078)
    public void unsetCalendarCalDavDefaultCalendarId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarCalDavDefaultCalendarId, "");
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
        attrs.put(Provisioning.A_zmailCalendarCalDavDefaultCalendarId, "");
        return attrs;
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zmailCalendarRecurrenceDailyMaxDays, or 730 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public int getCalendarRecurrenceDailyMaxDays() {
        return getIntAttr(Provisioning.A_zmailCalendarRecurrenceDailyMaxDays, 730);
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceDailyMaxDays new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public void setCalendarRecurrenceDailyMaxDays(int zmailCalendarRecurrenceDailyMaxDays) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceDailyMaxDays, Integer.toString(zmailCalendarRecurrenceDailyMaxDays));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceDailyMaxDays new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public Map<String,Object> setCalendarRecurrenceDailyMaxDays(int zmailCalendarRecurrenceDailyMaxDays, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceDailyMaxDays, Integer.toString(zmailCalendarRecurrenceDailyMaxDays));
        return attrs;
    }

    /**
     * Maximum number of days a DAILY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=661)
    public void unsetCalendarRecurrenceDailyMaxDays() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceDailyMaxDays, "");
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
        attrs.put(Provisioning.A_zmailCalendarRecurrenceDailyMaxDays, "");
        return attrs;
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @return zmailCalendarRecurrenceMaxInstances, or 0 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public int getCalendarRecurrenceMaxInstances() {
        return getIntAttr(Provisioning.A_zmailCalendarRecurrenceMaxInstances, 0);
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceMaxInstances new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public void setCalendarRecurrenceMaxInstances(int zmailCalendarRecurrenceMaxInstances) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMaxInstances, Integer.toString(zmailCalendarRecurrenceMaxInstances));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceMaxInstances new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public Map<String,Object> setCalendarRecurrenceMaxInstances(int zmailCalendarRecurrenceMaxInstances, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMaxInstances, Integer.toString(zmailCalendarRecurrenceMaxInstances));
        return attrs;
    }

    /**
     * Maximum number of instances expanded per recurrence rule; 0 means
     * unlimited
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=660)
    public void unsetCalendarRecurrenceMaxInstances() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMaxInstances, "");
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
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMaxInstances, "");
        return attrs;
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zmailCalendarRecurrenceMonthlyMaxMonths, or 360 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public int getCalendarRecurrenceMonthlyMaxMonths() {
        return getIntAttr(Provisioning.A_zmailCalendarRecurrenceMonthlyMaxMonths, 360);
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceMonthlyMaxMonths new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public void setCalendarRecurrenceMonthlyMaxMonths(int zmailCalendarRecurrenceMonthlyMaxMonths) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMonthlyMaxMonths, Integer.toString(zmailCalendarRecurrenceMonthlyMaxMonths));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceMonthlyMaxMonths new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public Map<String,Object> setCalendarRecurrenceMonthlyMaxMonths(int zmailCalendarRecurrenceMonthlyMaxMonths, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMonthlyMaxMonths, Integer.toString(zmailCalendarRecurrenceMonthlyMaxMonths));
        return attrs;
    }

    /**
     * Maximum number of months a MONTHLY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=663)
    public void unsetCalendarRecurrenceMonthlyMaxMonths() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMonthlyMaxMonths, "");
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
        attrs.put(Provisioning.A_zmailCalendarRecurrenceMonthlyMaxMonths, "");
        return attrs;
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @return zmailCalendarRecurrenceOtherFrequencyMaxYears, or 1 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public int getCalendarRecurrenceOtherFrequencyMaxYears() {
        return getIntAttr(Provisioning.A_zmailCalendarRecurrenceOtherFrequencyMaxYears, 1);
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @param zmailCalendarRecurrenceOtherFrequencyMaxYears new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public void setCalendarRecurrenceOtherFrequencyMaxYears(int zmailCalendarRecurrenceOtherFrequencyMaxYears) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceOtherFrequencyMaxYears, Integer.toString(zmailCalendarRecurrenceOtherFrequencyMaxYears));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @param zmailCalendarRecurrenceOtherFrequencyMaxYears new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public Map<String,Object> setCalendarRecurrenceOtherFrequencyMaxYears(int zmailCalendarRecurrenceOtherFrequencyMaxYears, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceOtherFrequencyMaxYears, Integer.toString(zmailCalendarRecurrenceOtherFrequencyMaxYears));
        return attrs;
    }

    /**
     * Maximum number of years a recurrence rule can span for frequencies
     * other than DAILY/WEEKLY/MONTHLY/YEARLY; 0 means unlimited
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=665)
    public void unsetCalendarRecurrenceOtherFrequencyMaxYears() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceOtherFrequencyMaxYears, "");
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
        attrs.put(Provisioning.A_zmailCalendarRecurrenceOtherFrequencyMaxYears, "");
        return attrs;
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zmailCalendarRecurrenceWeeklyMaxWeeks, or 520 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public int getCalendarRecurrenceWeeklyMaxWeeks() {
        return getIntAttr(Provisioning.A_zmailCalendarRecurrenceWeeklyMaxWeeks, 520);
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceWeeklyMaxWeeks new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public void setCalendarRecurrenceWeeklyMaxWeeks(int zmailCalendarRecurrenceWeeklyMaxWeeks) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceWeeklyMaxWeeks, Integer.toString(zmailCalendarRecurrenceWeeklyMaxWeeks));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceWeeklyMaxWeeks new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public Map<String,Object> setCalendarRecurrenceWeeklyMaxWeeks(int zmailCalendarRecurrenceWeeklyMaxWeeks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceWeeklyMaxWeeks, Integer.toString(zmailCalendarRecurrenceWeeklyMaxWeeks));
        return attrs;
    }

    /**
     * Maximum number of weeks a WEEKLY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=662)
    public void unsetCalendarRecurrenceWeeklyMaxWeeks() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceWeeklyMaxWeeks, "");
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
        attrs.put(Provisioning.A_zmailCalendarRecurrenceWeeklyMaxWeeks, "");
        return attrs;
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @return zmailCalendarRecurrenceYearlyMaxYears, or 100 if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public int getCalendarRecurrenceYearlyMaxYears() {
        return getIntAttr(Provisioning.A_zmailCalendarRecurrenceYearlyMaxYears, 100);
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceYearlyMaxYears new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public void setCalendarRecurrenceYearlyMaxYears(int zmailCalendarRecurrenceYearlyMaxYears) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceYearlyMaxYears, Integer.toString(zmailCalendarRecurrenceYearlyMaxYears));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @param zmailCalendarRecurrenceYearlyMaxYears new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public Map<String,Object> setCalendarRecurrenceYearlyMaxYears(int zmailCalendarRecurrenceYearlyMaxYears, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceYearlyMaxYears, Integer.toString(zmailCalendarRecurrenceYearlyMaxYears));
        return attrs;
    }

    /**
     * Maximum number of years a YEARLY recurrence rule can span; 0 means
     * unlimited
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=664)
    public void unsetCalendarRecurrenceYearlyMaxYears() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalendarRecurrenceYearlyMaxYears, "");
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
        attrs.put(Provisioning.A_zmailCalendarRecurrenceYearlyMaxYears, "");
        return attrs;
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @return zmailClusterType, or ZAttrProvisioning.ClusterType.none if unset and/or has invalid value
     */
    @ZAttr(id=508)
    public ZAttrProvisioning.ClusterType getClusterType() {
        try { String v = getAttr(Provisioning.A_zmailClusterType); return v == null ? ZAttrProvisioning.ClusterType.none : ZAttrProvisioning.ClusterType.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.ClusterType.none; }
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @return zmailClusterType, or "none" if unset
     */
    @ZAttr(id=508)
    public String getClusterTypeAsString() {
        return getAttr(Provisioning.A_zmailClusterType, "none");
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zmailClusterType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=508)
    public void setClusterType(ZAttrProvisioning.ClusterType zmailClusterType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailClusterType, zmailClusterType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zmailClusterType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=508)
    public Map<String,Object> setClusterType(ZAttrProvisioning.ClusterType zmailClusterType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailClusterType, zmailClusterType.toString());
        return attrs;
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zmailClusterType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=508)
    public void setClusterTypeAsString(String zmailClusterType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailClusterType, zmailClusterType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @param zmailClusterType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=508)
    public Map<String,Object> setClusterTypeAsString(String zmailClusterType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailClusterType, zmailClusterType);
        return attrs;
    }

    /**
     * Type of HA cluster software in use; &quot;none&quot; by default,
     * &quot;RedHat&quot; for Red Hat cluster or &quot;Veritas&quot; for
     * Veritas Cluster Server from Symantec
     *
     * <p>Valid values: [RedHat, none, Veritas]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=508)
    public void unsetClusterType() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailClusterType, "");
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
        attrs.put(Provisioning.A_zmailClusterType, "");
        return attrs;
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @return zmailContactHiddenAttributes, or "dn,vcardUID,vcardURL,vcardXProps,member" if unset
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public String getContactHiddenAttributes() {
        return getAttr(Provisioning.A_zmailContactHiddenAttributes, "dn,vcardUID,vcardURL,vcardXProps,member");
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @param zmailContactHiddenAttributes new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public void setContactHiddenAttributes(String zmailContactHiddenAttributes) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailContactHiddenAttributes, zmailContactHiddenAttributes);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @param zmailContactHiddenAttributes new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public Map<String,Object> setContactHiddenAttributes(String zmailContactHiddenAttributes, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailContactHiddenAttributes, zmailContactHiddenAttributes);
        return attrs;
    }

    /**
     * Comma separated list of Contact attributes that should be hidden from
     * clients and export of contacts.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.6
     */
    @ZAttr(id=1086)
    public void unsetContactHiddenAttributes() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailContactHiddenAttributes, "");
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
        attrs.put(Provisioning.A_zmailContactHiddenAttributes, "");
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
     * @return zmailContactRankingTableRefreshInterval in millseconds, or 604800000 (7d)  if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public long getContactRankingTableRefreshInterval() {
        return getTimeInterval(Provisioning.A_zmailContactRankingTableRefreshInterval, 604800000L);
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
     * @return zmailContactRankingTableRefreshInterval, or "7d" if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public String getContactRankingTableRefreshIntervalAsString() {
        return getAttr(Provisioning.A_zmailContactRankingTableRefreshInterval, "7d");
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
     * @param zmailContactRankingTableRefreshInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public void setContactRankingTableRefreshInterval(String zmailContactRankingTableRefreshInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailContactRankingTableRefreshInterval, zmailContactRankingTableRefreshInterval);
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
     * @param zmailContactRankingTableRefreshInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public Map<String,Object> setContactRankingTableRefreshInterval(String zmailContactRankingTableRefreshInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailContactRankingTableRefreshInterval, zmailContactRankingTableRefreshInterval);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1023)
    public void unsetContactRankingTableRefreshInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailContactRankingTableRefreshInterval, "");
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
        attrs.put(Provisioning.A_zmailContactRankingTableRefreshInterval, "");
        return attrs;
    }

    /**
     * convertd URL
     *
     * @return zmailConvertdURL, or null if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public String getConvertdURL() {
        return getAttr(Provisioning.A_zmailConvertdURL, null);
    }

    /**
     * convertd URL
     *
     * @param zmailConvertdURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public void setConvertdURL(String zmailConvertdURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailConvertdURL, zmailConvertdURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * convertd URL
     *
     * @param zmailConvertdURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public Map<String,Object> setConvertdURL(String zmailConvertdURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailConvertdURL, zmailConvertdURL);
        return attrs;
    }

    /**
     * convertd URL
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=776)
    public void unsetConvertdURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailConvertdURL, "");
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
        attrs.put(Provisioning.A_zmailConvertdURL, "");
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
     * @return zmailDatabaseSlowSqlThreshold in millseconds, or 2000 (2s)  if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public long getDatabaseSlowSqlThreshold() {
        return getTimeInterval(Provisioning.A_zmailDatabaseSlowSqlThreshold, 2000L);
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailDatabaseSlowSqlThreshold, or "2s" if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public String getDatabaseSlowSqlThresholdAsString() {
        return getAttr(Provisioning.A_zmailDatabaseSlowSqlThreshold, "2s");
    }

    /**
     * SQL statements that take longer than this duration to execute will be
     * logged to the sqltrace category in mailbox.log.. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailDatabaseSlowSqlThreshold new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public void setDatabaseSlowSqlThreshold(String zmailDatabaseSlowSqlThreshold) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDatabaseSlowSqlThreshold, zmailDatabaseSlowSqlThreshold);
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
     * @param zmailDatabaseSlowSqlThreshold new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public Map<String,Object> setDatabaseSlowSqlThreshold(String zmailDatabaseSlowSqlThreshold, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDatabaseSlowSqlThreshold, zmailDatabaseSlowSqlThreshold);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1038)
    public void unsetDatabaseSlowSqlThreshold() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDatabaseSlowSqlThreshold, "");
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
        attrs.put(Provisioning.A_zmailDatabaseSlowSqlThreshold, "");
        return attrs;
    }

    /**
     * interface address on which zmail extension server should listen; if
     * empty, binds to all interfaces
     *
     * @return zmailExtensionBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public String getExtensionBindAddress() {
        return getAttr(Provisioning.A_zmailExtensionBindAddress, null);
    }

    /**
     * interface address on which zmail extension server should listen; if
     * empty, binds to all interfaces
     *
     * @param zmailExtensionBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public void setExtensionBindAddress(String zmailExtensionBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExtensionBindAddress, zmailExtensionBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which zmail extension server should listen; if
     * empty, binds to all interfaces
     *
     * @param zmailExtensionBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public Map<String,Object> setExtensionBindAddress(String zmailExtensionBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExtensionBindAddress, zmailExtensionBindAddress);
        return attrs;
    }

    /**
     * interface address on which zmail extension server should listen; if
     * empty, binds to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1369)
    public void unsetExtensionBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExtensionBindAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which zmail extension server should listen; if
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
        attrs.put(Provisioning.A_zmailExtensionBindAddress, "");
        return attrs;
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zmailExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * <p>Use getExternalAccountStatusCheckIntervalAsString to access value as a string.
     *
     * @see #getExternalAccountStatusCheckIntervalAsString()
     *
     * @return zmailExternalAccountStatusCheckInterval in millseconds, or 86400000 (1d)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public long getExternalAccountStatusCheckInterval() {
        return getTimeInterval(Provisioning.A_zmailExternalAccountStatusCheckInterval, 86400000L);
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zmailExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailExternalAccountStatusCheckInterval, or "1d" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public String getExternalAccountStatusCheckIntervalAsString() {
        return getAttr(Provisioning.A_zmailExternalAccountStatusCheckInterval, "1d");
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zmailExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailExternalAccountStatusCheckInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public void setExternalAccountStatusCheckInterval(String zmailExternalAccountStatusCheckInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalAccountStatusCheckInterval, zmailExternalAccountStatusCheckInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zmailExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailExternalAccountStatusCheckInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public Map<String,Object> setExternalAccountStatusCheckInterval(String zmailExternalAccountStatusCheckInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalAccountStatusCheckInterval, zmailExternalAccountStatusCheckInterval);
        return attrs;
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zmailExternalAccountLifetimeAfterDisabled of being disabled. . Must
     * be in valid duration format: {digits}{time-unit}. digits: 0-9,
     * time-unit: [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days,
     * ms - milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1370)
    public void unsetExternalAccountStatusCheckInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailExternalAccountStatusCheckInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that: - disables an
     * external virtual account when all its accessible shares have been
     * revoked or expired. - deletes an external virtual account after
     * zmailExternalAccountLifetimeAfterDisabled of being disabled. . Must
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
        attrs.put(Provisioning.A_zmailExternalAccountStatusCheckInterval, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @return zmailFileUploadMaxSize, or 10485760 if unset
     */
    @ZAttr(id=227)
    public long getFileUploadMaxSize() {
        return getLongAttr(Provisioning.A_zmailFileUploadMaxSize, 10485760L);
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @param zmailFileUploadMaxSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=227)
    public void setFileUploadMaxSize(long zmailFileUploadMaxSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSize, Long.toString(zmailFileUploadMaxSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @param zmailFileUploadMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=227)
    public Map<String,Object> setFileUploadMaxSize(long zmailFileUploadMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSize, Long.toString(zmailFileUploadMaxSize));
        return attrs;
    }

    /**
     * Maximum size in bytes for file uploads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=227)
    public void unsetFileUploadMaxSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFileUploadMaxSize, "");
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
        attrs.put(Provisioning.A_zmailFileUploadMaxSize, "");
        return attrs;
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zmail users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * <p>Use getFreebusyPropagationRetryIntervalAsString to access value as a string.
     *
     * @see #getFreebusyPropagationRetryIntervalAsString()
     *
     * @return zmailFreebusyPropagationRetryInterval in millseconds, or 60000 (1m)  if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public long getFreebusyPropagationRetryInterval() {
        return getTimeInterval(Provisioning.A_zmailFreebusyPropagationRetryInterval, 60000L);
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zmail users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailFreebusyPropagationRetryInterval, or "1m" if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public String getFreebusyPropagationRetryIntervalAsString() {
        return getAttr(Provisioning.A_zmailFreebusyPropagationRetryInterval, "1m");
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zmail users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailFreebusyPropagationRetryInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public void setFreebusyPropagationRetryInterval(String zmailFreebusyPropagationRetryInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyPropagationRetryInterval, zmailFreebusyPropagationRetryInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zmail users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailFreebusyPropagationRetryInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public Map<String,Object> setFreebusyPropagationRetryInterval(String zmailFreebusyPropagationRetryInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyPropagationRetryInterval, zmailFreebusyPropagationRetryInterval);
        return attrs;
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zmail users free/busy information to external provider
     * such as Exchange. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1026)
    public void unsetFreebusyPropagationRetryInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailFreebusyPropagationRetryInterval, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The interval to wait when the server encounters problems while
     * propagating Zmail users free/busy information to external provider
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
        attrs.put(Provisioning.A_zmailFreebusyPropagationRetryInterval, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zmailHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * <p>Use getHsmAgeAsString to access value as a string.
     *
     * @see #getHsmAgeAsString()
     *
     * @return zmailHsmAge in millseconds, or 2592000000 (30d)  if unset
     */
    @ZAttr(id=8)
    public long getHsmAge() {
        return getTimeInterval(Provisioning.A_zmailHsmAge, 2592000000L);
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zmailHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @return zmailHsmAge, or "30d" if unset
     */
    @ZAttr(id=8)
    public String getHsmAgeAsString() {
        return getAttr(Provisioning.A_zmailHsmAge, "30d");
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zmailHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zmailHsmAge new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=8)
    public void setHsmAge(String zmailHsmAge) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmAge, zmailHsmAge);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zmailHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zmailHsmAge new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=8)
    public Map<String,Object> setHsmAge(String zmailHsmAge, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmAge, zmailHsmAge);
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zmailHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
     * data will be moved to secondary storage.. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=8)
    public void unsetHsmAge() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmAge, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA2. deprecated in favor for
     * zmailHsmPolicy. Orig desc: Minimum age of mail items whose filesystem
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
        attrs.put(Provisioning.A_zmailHsmAge, "");
        return attrs;
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @return zmailHsmBatchSize, or 10000 if unset
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public int getHsmBatchSize() {
        return getIntAttr(Provisioning.A_zmailHsmBatchSize, 10000);
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @param zmailHsmBatchSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public void setHsmBatchSize(int zmailHsmBatchSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmBatchSize, Integer.toString(zmailHsmBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @param zmailHsmBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public Map<String,Object> setHsmBatchSize(int zmailHsmBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmBatchSize, Integer.toString(zmailHsmBatchSize));
        return attrs;
    }

    /**
     * Maximum number of items to move during a single HSM operation. If the
     * limit is exceeded, the HSM operation is repeated until all qualifying
     * items are moved.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.1
     */
    @ZAttr(id=1316)
    public void unsetHsmBatchSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmBatchSize, "");
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
        attrs.put(Provisioning.A_zmailHsmBatchSize, "");
        return attrs;
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zmailHsmPolicy.
     *
     * @return zmailHsmMovePreviousRevisions, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public boolean isHsmMovePreviousRevisions() {
        return getBooleanAttr(Provisioning.A_zmailHsmMovePreviousRevisions, false);
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zmailHsmPolicy.
     *
     * @param zmailHsmMovePreviousRevisions new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public void setHsmMovePreviousRevisions(boolean zmailHsmMovePreviousRevisions) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmMovePreviousRevisions, zmailHsmMovePreviousRevisions ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zmailHsmPolicy.
     *
     * @param zmailHsmMovePreviousRevisions new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public Map<String,Object> setHsmMovePreviousRevisions(boolean zmailHsmMovePreviousRevisions, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmMovePreviousRevisions, zmailHsmMovePreviousRevisions ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zmailHsmPolicy.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public void unsetHsmMovePreviousRevisions() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmMovePreviousRevisions, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Keep only the tip revision in the main volume, and move all the old
     * revisions to the secondary volume. For document type mail items only,
     * works independently of zmailHsmPolicy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1393)
    public Map<String,Object> unsetHsmMovePreviousRevisions(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmMovePreviousRevisions, "");
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @return zmailHsmPolicy, or empty array if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public String[] getHsmPolicy() {
        String[] value = getMultiAttr(Provisioning.A_zmailHsmPolicy); return value.length > 0 ? value : new String[] {"message,document:before:-30days"};
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zmailHsmPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void setHsmPolicy(String[] zmailHsmPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmPolicy, zmailHsmPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zmailHsmPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> setHsmPolicy(String[] zmailHsmPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmPolicy, zmailHsmPolicy);
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zmailHsmPolicy new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void addHsmPolicy(String zmailHsmPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailHsmPolicy, zmailHsmPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zmailHsmPolicy new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> addHsmPolicy(String zmailHsmPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailHsmPolicy, zmailHsmPolicy);
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zmailHsmPolicy existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void removeHsmPolicy(String zmailHsmPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailHsmPolicy, zmailHsmPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @param zmailHsmPolicy existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public Map<String,Object> removeHsmPolicy(String zmailHsmPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailHsmPolicy, zmailHsmPolicy);
        return attrs;
    }

    /**
     * The policy that determines which mail items get moved to secondary
     * storage during HSM. Each value specifies a comma-separated list of
     * item types and the search query used to select items to move. See the
     * spec for &lt;SearchRequest&gt; for the complete list of item types and
     * query.txt for the search query spec.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1024)
    public void unsetHsmPolicy() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHsmPolicy, "");
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
        attrs.put(Provisioning.A_zmailHsmPolicy, "");
        return attrs;
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @return zmailHttpConnectorMaxIdleTimeMillis, or 60000 if unset
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public int getHttpConnectorMaxIdleTimeMillis() {
        return getIntAttr(Provisioning.A_zmailHttpConnectorMaxIdleTimeMillis, 60000);
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @param zmailHttpConnectorMaxIdleTimeMillis new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public void setHttpConnectorMaxIdleTimeMillis(int zmailHttpConnectorMaxIdleTimeMillis) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpConnectorMaxIdleTimeMillis, Integer.toString(zmailHttpConnectorMaxIdleTimeMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @param zmailHttpConnectorMaxIdleTimeMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public Map<String,Object> setHttpConnectorMaxIdleTimeMillis(int zmailHttpConnectorMaxIdleTimeMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpConnectorMaxIdleTimeMillis, Integer.toString(zmailHttpConnectorMaxIdleTimeMillis));
        return attrs;
    }

    /**
     * Maximum Idle time in milli seconds for a connection. This is applied
     * when waiting for a new request to be received on a connection; when
     * reading the headers and content of a request; when writing the headers
     * and content of a response.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1428)
    public void unsetHttpConnectorMaxIdleTimeMillis() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpConnectorMaxIdleTimeMillis, "");
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
        attrs.put(Provisioning.A_zmailHttpConnectorMaxIdleTimeMillis, "");
        return attrs;
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @return zmailHttpDebugHandlerEnabled, or true if unset
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public boolean isHttpDebugHandlerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailHttpDebugHandlerEnabled, true);
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @param zmailHttpDebugHandlerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public void setHttpDebugHandlerEnabled(boolean zmailHttpDebugHandlerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDebugHandlerEnabled, zmailHttpDebugHandlerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @param zmailHttpDebugHandlerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public Map<String,Object> setHttpDebugHandlerEnabled(boolean zmailHttpDebugHandlerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDebugHandlerEnabled, zmailHttpDebugHandlerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to enable http debug handler on a server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1043)
    public void unsetHttpDebugHandlerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDebugHandlerEnabled, "");
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
        attrs.put(Provisioning.A_zmailHttpDebugHandlerEnabled, "");
        return attrs;
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @return zmailHttpDosFilterDelayMillis, or -1 if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public int getHttpDosFilterDelayMillis() {
        return getIntAttr(Provisioning.A_zmailHttpDosFilterDelayMillis, -1);
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @param zmailHttpDosFilterDelayMillis new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public void setHttpDosFilterDelayMillis(int zmailHttpDosFilterDelayMillis) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDosFilterDelayMillis, Integer.toString(zmailHttpDosFilterDelayMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @param zmailHttpDosFilterDelayMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public Map<String,Object> setHttpDosFilterDelayMillis(int zmailHttpDosFilterDelayMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDosFilterDelayMillis, Integer.toString(zmailHttpDosFilterDelayMillis));
        return attrs;
    }

    /**
     * Delay imposed on all requests over the rate limit, before they are
     * considered at all. -1 = Reject request, 0 = No delay, any other value
     * = Delay in ms
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1430)
    public void unsetHttpDosFilterDelayMillis() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDosFilterDelayMillis, "");
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
        attrs.put(Provisioning.A_zmailHttpDosFilterDelayMillis, "");
        return attrs;
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @return zmailHttpDosFilterMaxRequestsPerSec, or 30 if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public int getHttpDosFilterMaxRequestsPerSec() {
        return getIntAttr(Provisioning.A_zmailHttpDosFilterMaxRequestsPerSec, 30);
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @param zmailHttpDosFilterMaxRequestsPerSec new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public void setHttpDosFilterMaxRequestsPerSec(int zmailHttpDosFilterMaxRequestsPerSec) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDosFilterMaxRequestsPerSec, Integer.toString(zmailHttpDosFilterMaxRequestsPerSec));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @param zmailHttpDosFilterMaxRequestsPerSec new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public Map<String,Object> setHttpDosFilterMaxRequestsPerSec(int zmailHttpDosFilterMaxRequestsPerSec, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDosFilterMaxRequestsPerSec, Integer.toString(zmailHttpDosFilterMaxRequestsPerSec));
        return attrs;
    }

    /**
     * Maximum number of requests from a connection per second. Requests in
     * excess of this are throttled.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1431)
    public void unsetHttpDosFilterMaxRequestsPerSec() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpDosFilterMaxRequestsPerSec, "");
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
        attrs.put(Provisioning.A_zmailHttpDosFilterMaxRequestsPerSec, "");
        return attrs;
    }

    /**
     * number of http handler threads
     *
     * @return zmailHttpNumThreads, or 250 if unset
     */
    @ZAttr(id=518)
    public int getHttpNumThreads() {
        return getIntAttr(Provisioning.A_zmailHttpNumThreads, 250);
    }

    /**
     * number of http handler threads
     *
     * @param zmailHttpNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=518)
    public void setHttpNumThreads(int zmailHttpNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpNumThreads, Integer.toString(zmailHttpNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of http handler threads
     *
     * @param zmailHttpNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=518)
    public Map<String,Object> setHttpNumThreads(int zmailHttpNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpNumThreads, Integer.toString(zmailHttpNumThreads));
        return attrs;
    }

    /**
     * number of http handler threads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=518)
    public void unsetHttpNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpNumThreads, "");
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
        attrs.put(Provisioning.A_zmailHttpNumThreads, "");
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @return zmailHttpProxyURL, or empty array if unset
     */
    @ZAttr(id=388)
    public String[] getHttpProxyURL() {
        return getMultiAttr(Provisioning.A_zmailHttpProxyURL);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zmailHttpProxyURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void setHttpProxyURL(String[] zmailHttpProxyURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpProxyURL, zmailHttpProxyURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zmailHttpProxyURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> setHttpProxyURL(String[] zmailHttpProxyURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpProxyURL, zmailHttpProxyURL);
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zmailHttpProxyURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void addHttpProxyURL(String zmailHttpProxyURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailHttpProxyURL, zmailHttpProxyURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zmailHttpProxyURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> addHttpProxyURL(String zmailHttpProxyURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailHttpProxyURL, zmailHttpProxyURL);
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zmailHttpProxyURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void removeHttpProxyURL(String zmailHttpProxyURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailHttpProxyURL, zmailHttpProxyURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @param zmailHttpProxyURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=388)
    public Map<String,Object> removeHttpProxyURL(String zmailHttpProxyURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailHttpProxyURL, zmailHttpProxyURL);
        return attrs;
    }

    /**
     * the http proxy URL to connect to when making outgoing connections
     * (Zimlet proxy, RSS/ATOM feeds, etc)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=388)
    public void unsetHttpProxyURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpProxyURL, "");
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
        attrs.put(Provisioning.A_zmailHttpProxyURL, "");
        return attrs;
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @return zmailHttpSSLNumThreads, or 50 if unset
     */
    @ZAttr(id=519)
    public int getHttpSSLNumThreads() {
        return getIntAttr(Provisioning.A_zmailHttpSSLNumThreads, 50);
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @param zmailHttpSSLNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=519)
    public void setHttpSSLNumThreads(int zmailHttpSSLNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpSSLNumThreads, Integer.toString(zmailHttpSSLNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @param zmailHttpSSLNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=519)
    public Map<String,Object> setHttpSSLNumThreads(int zmailHttpSSLNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpSSLNumThreads, Integer.toString(zmailHttpSSLNumThreads));
        return attrs;
    }

    /**
     * Deprecated since: 5.0. not applicable for jetty. Orig desc: number of
     * https handler threads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=519)
    public void unsetHttpSSLNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpSSLNumThreads, "");
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
        attrs.put(Provisioning.A_zmailHttpSSLNumThreads, "");
        return attrs;
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @return zmailHttpThreadPoolMaxIdleTimeMillis, or 10000 if unset
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public int getHttpThreadPoolMaxIdleTimeMillis() {
        return getIntAttr(Provisioning.A_zmailHttpThreadPoolMaxIdleTimeMillis, 10000);
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @param zmailHttpThreadPoolMaxIdleTimeMillis new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public void setHttpThreadPoolMaxIdleTimeMillis(int zmailHttpThreadPoolMaxIdleTimeMillis) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpThreadPoolMaxIdleTimeMillis, Integer.toString(zmailHttpThreadPoolMaxIdleTimeMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @param zmailHttpThreadPoolMaxIdleTimeMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public Map<String,Object> setHttpThreadPoolMaxIdleTimeMillis(int zmailHttpThreadPoolMaxIdleTimeMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpThreadPoolMaxIdleTimeMillis, Integer.toString(zmailHttpThreadPoolMaxIdleTimeMillis));
        return attrs;
    }

    /**
     * The maximum thread idle time in milli seconds. Threads that are idle
     * for longer than this period may be stopped.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.3
     */
    @ZAttr(id=1429)
    public void unsetHttpThreadPoolMaxIdleTimeMillis() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpThreadPoolMaxIdleTimeMillis, "");
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
        attrs.put(Provisioning.A_zmailHttpThreadPoolMaxIdleTimeMillis, "");
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @return zmailHttpThrottleSafeIPs, or empty array if unset
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public String[] getHttpThrottleSafeIPs() {
        return getMultiAttr(Provisioning.A_zmailHttpThrottleSafeIPs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zmailHttpThrottleSafeIPs new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void setHttpThrottleSafeIPs(String[] zmailHttpThrottleSafeIPs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpThrottleSafeIPs, zmailHttpThrottleSafeIPs);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zmailHttpThrottleSafeIPs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> setHttpThrottleSafeIPs(String[] zmailHttpThrottleSafeIPs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpThrottleSafeIPs, zmailHttpThrottleSafeIPs);
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zmailHttpThrottleSafeIPs new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void addHttpThrottleSafeIPs(String zmailHttpThrottleSafeIPs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailHttpThrottleSafeIPs, zmailHttpThrottleSafeIPs);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zmailHttpThrottleSafeIPs new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> addHttpThrottleSafeIPs(String zmailHttpThrottleSafeIPs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailHttpThrottleSafeIPs, zmailHttpThrottleSafeIPs);
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zmailHttpThrottleSafeIPs existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void removeHttpThrottleSafeIPs(String zmailHttpThrottleSafeIPs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailHttpThrottleSafeIPs, zmailHttpThrottleSafeIPs);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @param zmailHttpThrottleSafeIPs existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public Map<String,Object> removeHttpThrottleSafeIPs(String zmailHttpThrottleSafeIPs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailHttpThrottleSafeIPs, zmailHttpThrottleSafeIPs);
        return attrs;
    }

    /**
     * IP addresses to ignore when applying Jetty DosFilter.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.3
     */
    @ZAttr(id=1427)
    public void unsetHttpThrottleSafeIPs() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailHttpThrottleSafeIPs, "");
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
        attrs.put(Provisioning.A_zmailHttpThrottleSafeIPs, "");
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @return zmailIMBindAddress, or empty array if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public String[] getIMBindAddress() {
        return getMultiAttr(Provisioning.A_zmailIMBindAddress);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zmailIMBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void setIMBindAddress(String[] zmailIMBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIMBindAddress, zmailIMBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zmailIMBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> setIMBindAddress(String[] zmailIMBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIMBindAddress, zmailIMBindAddress);
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zmailIMBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void addIMBindAddress(String zmailIMBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailIMBindAddress, zmailIMBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zmailIMBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> addIMBindAddress(String zmailIMBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailIMBindAddress, zmailIMBindAddress);
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zmailIMBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void removeIMBindAddress(String zmailIMBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailIMBindAddress, zmailIMBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @param zmailIMBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public Map<String,Object> removeIMBindAddress(String zmailIMBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailIMBindAddress, zmailIMBindAddress);
        return attrs;
    }

    /**
     * interface address on which IM server should listen; if empty, binds to
     * all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=567)
    public void unsetIMBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIMBindAddress, "");
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
        attrs.put(Provisioning.A_zmailIMBindAddress, "");
        return attrs;
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @return zmailIPMode, or ZAttrProvisioning.IPMode.ipv4 if unset and/or has invalid value
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public ZAttrProvisioning.IPMode getIPMode() {
        try { String v = getAttr(Provisioning.A_zmailIPMode); return v == null ? ZAttrProvisioning.IPMode.ipv4 : ZAttrProvisioning.IPMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.IPMode.ipv4; }
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @return zmailIPMode, or "ipv4" if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public String getIPModeAsString() {
        return getAttr(Provisioning.A_zmailIPMode, "ipv4");
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zmailIPMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public void setIPMode(ZAttrProvisioning.IPMode zmailIPMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIPMode, zmailIPMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zmailIPMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public Map<String,Object> setIPMode(ZAttrProvisioning.IPMode zmailIPMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIPMode, zmailIPMode.toString());
        return attrs;
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zmailIPMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public void setIPModeAsString(String zmailIPMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIPMode, zmailIPMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @param zmailIPMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public Map<String,Object> setIPModeAsString(String zmailIPMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIPMode, zmailIPMode);
        return attrs;
    }

    /**
     * supported IP mode
     *
     * <p>Valid values: [both, ipv6, ipv4]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1171)
    public void unsetIPMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIPMode, "");
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
        attrs.put(Provisioning.A_zmailIPMode, "");
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
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @return zmailImapAdvertisedName, or null if unset
     */
    @ZAttr(id=178)
    public String getImapAdvertisedName() {
        return getAttr(Provisioning.A_zmailImapAdvertisedName, null);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zmailImapAdvertisedName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=178)
    public void setImapAdvertisedName(String zmailImapAdvertisedName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapAdvertisedName, zmailImapAdvertisedName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zmailImapAdvertisedName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=178)
    public Map<String,Object> setImapAdvertisedName(String zmailImapAdvertisedName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapAdvertisedName, zmailImapAdvertisedName);
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=178)
    public void unsetImapAdvertisedName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapAdvertisedName, "");
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
        attrs.put(Provisioning.A_zmailImapAdvertisedName, "");
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailImapBindAddress, or empty array if unset
     */
    @ZAttr(id=179)
    public String[] getImapBindAddress() {
        return getMultiAttr(Provisioning.A_zmailImapBindAddress);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void setImapBindAddress(String[] zmailImapBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindAddress, zmailImapBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> setImapBindAddress(String[] zmailImapBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindAddress, zmailImapBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void addImapBindAddress(String zmailImapBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapBindAddress, zmailImapBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> addImapBindAddress(String zmailImapBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapBindAddress, zmailImapBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void removeImapBindAddress(String zmailImapBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapBindAddress, zmailImapBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=179)
    public Map<String,Object> removeImapBindAddress(String zmailImapBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapBindAddress, zmailImapBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=179)
    public void unsetImapBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindAddress, "");
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
        attrs.put(Provisioning.A_zmailImapBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zmailImapBindOnStartup, or true if unset
     */
    @ZAttr(id=268)
    public boolean isImapBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zmailImapBindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailImapBindOnStartup new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=268)
    public void setImapBindOnStartup(boolean zmailImapBindOnStartup) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindOnStartup, zmailImapBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailImapBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=268)
    public Map<String,Object> setImapBindOnStartup(boolean zmailImapBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindOnStartup, zmailImapBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=268)
    public void unsetImapBindOnStartup() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindOnStartup, "");
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
        attrs.put(Provisioning.A_zmailImapBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which IMAP server should listen
     *
     * <p>Use getImapBindPortAsString to access value as a string.
     *
     * @see #getImapBindPortAsString()
     *
     * @return zmailImapBindPort, or 7143 if unset
     */
    @ZAttr(id=180)
    public int getImapBindPort() {
        return getIntAttr(Provisioning.A_zmailImapBindPort, 7143);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @return zmailImapBindPort, or "7143" if unset
     */
    @ZAttr(id=180)
    public String getImapBindPortAsString() {
        return getAttr(Provisioning.A_zmailImapBindPort, "7143");
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zmailImapBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=180)
    public void setImapBindPort(int zmailImapBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindPort, Integer.toString(zmailImapBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zmailImapBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=180)
    public Map<String,Object> setImapBindPort(int zmailImapBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindPort, Integer.toString(zmailImapBindPort));
        return attrs;
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zmailImapBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=180)
    public void setImapBindPortAsString(String zmailImapBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindPort, zmailImapBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP server should listen
     *
     * @param zmailImapBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=180)
    public Map<String,Object> setImapBindPortAsString(String zmailImapBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindPort, zmailImapBindPort);
        return attrs;
    }

    /**
     * port number on which IMAP server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=180)
    public void unsetImapBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapBindPort, "");
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
        attrs.put(Provisioning.A_zmailImapBindPort, "");
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @return zmailImapCleartextLoginEnabled, or false if unset
     */
    @ZAttr(id=185)
    public boolean isImapCleartextLoginEnabled() {
        return getBooleanAttr(Provisioning.A_zmailImapCleartextLoginEnabled, false);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zmailImapCleartextLoginEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=185)
    public void setImapCleartextLoginEnabled(boolean zmailImapCleartextLoginEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapCleartextLoginEnabled, zmailImapCleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zmailImapCleartextLoginEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=185)
    public Map<String,Object> setImapCleartextLoginEnabled(boolean zmailImapCleartextLoginEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapCleartextLoginEnabled, zmailImapCleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=185)
    public void unsetImapCleartextLoginEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapCleartextLoginEnabled, "");
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
        attrs.put(Provisioning.A_zmailImapCleartextLoginEnabled, "");
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @return zmailImapDisabledCapability, or empty array if unset
     */
    @ZAttr(id=443)
    public String[] getImapDisabledCapability() {
        return getMultiAttr(Provisioning.A_zmailImapDisabledCapability);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zmailImapDisabledCapability new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void setImapDisabledCapability(String[] zmailImapDisabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapDisabledCapability, zmailImapDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zmailImapDisabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> setImapDisabledCapability(String[] zmailImapDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapDisabledCapability, zmailImapDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zmailImapDisabledCapability new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void addImapDisabledCapability(String zmailImapDisabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapDisabledCapability, zmailImapDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zmailImapDisabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> addImapDisabledCapability(String zmailImapDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapDisabledCapability, zmailImapDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zmailImapDisabledCapability existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void removeImapDisabledCapability(String zmailImapDisabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapDisabledCapability, zmailImapDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @param zmailImapDisabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=443)
    public Map<String,Object> removeImapDisabledCapability(String zmailImapDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapDisabledCapability, zmailImapDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP capabilities. Capabilities are listed on the CAPABILITY
     * line, also known in RFCs as extensions
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=443)
    public void unsetImapDisabledCapability() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapDisabledCapability, "");
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
        attrs.put(Provisioning.A_zmailImapDisabledCapability, "");
        return attrs;
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @return zmailImapExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public boolean isImapExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zmailImapExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @param zmailImapExposeVersionOnBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public void setImapExposeVersionOnBanner(boolean zmailImapExposeVersionOnBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapExposeVersionOnBanner, zmailImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @param zmailImapExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public Map<String,Object> setImapExposeVersionOnBanner(boolean zmailImapExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapExposeVersionOnBanner, zmailImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on IMAP banner
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=693)
    public void unsetImapExposeVersionOnBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapExposeVersionOnBanner, "");
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
        attrs.put(Provisioning.A_zmailImapExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @return zmailImapMaxConnections, or 200 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public int getImapMaxConnections() {
        return getIntAttr(Provisioning.A_zmailImapMaxConnections, 200);
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zmailImapMaxConnections new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public void setImapMaxConnections(int zmailImapMaxConnections) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapMaxConnections, Integer.toString(zmailImapMaxConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zmailImapMaxConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public Map<String,Object> setImapMaxConnections(int zmailImapMaxConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapMaxConnections, Integer.toString(zmailImapMaxConnections));
        return attrs;
    }

    /**
     * Maximum number of concurrent IMAP connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1156)
    public void unsetImapMaxConnections() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapMaxConnections, "");
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
        attrs.put(Provisioning.A_zmailImapMaxConnections, "");
        return attrs;
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @return zmailImapMaxRequestSize, or 10240 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public int getImapMaxRequestSize() {
        return getIntAttr(Provisioning.A_zmailImapMaxRequestSize, 10240);
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @param zmailImapMaxRequestSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public void setImapMaxRequestSize(int zmailImapMaxRequestSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapMaxRequestSize, Integer.toString(zmailImapMaxRequestSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @param zmailImapMaxRequestSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public Map<String,Object> setImapMaxRequestSize(int zmailImapMaxRequestSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapMaxRequestSize, Integer.toString(zmailImapMaxRequestSize));
        return attrs;
    }

    /**
     * maximum size of IMAP request in bytes excluding literal data
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1085)
    public void unsetImapMaxRequestSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapMaxRequestSize, "");
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
        attrs.put(Provisioning.A_zmailImapMaxRequestSize, "");
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @return zmailImapNumThreads, or 200 if unset
     */
    @ZAttr(id=181)
    public int getImapNumThreads() {
        return getIntAttr(Provisioning.A_zmailImapNumThreads, 200);
    }

    /**
     * number of handler threads
     *
     * @param zmailImapNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=181)
    public void setImapNumThreads(int zmailImapNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapNumThreads, Integer.toString(zmailImapNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads
     *
     * @param zmailImapNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=181)
    public Map<String,Object> setImapNumThreads(int zmailImapNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapNumThreads, Integer.toString(zmailImapNumThreads));
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=181)
    public void unsetImapNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapNumThreads, "");
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
        attrs.put(Provisioning.A_zmailImapNumThreads, "");
        return attrs;
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * <p>Use getImapProxyBindPortAsString to access value as a string.
     *
     * @see #getImapProxyBindPortAsString()
     *
     * @return zmailImapProxyBindPort, or 143 if unset
     */
    @ZAttr(id=348)
    public int getImapProxyBindPort() {
        return getIntAttr(Provisioning.A_zmailImapProxyBindPort, 143);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @return zmailImapProxyBindPort, or "143" if unset
     */
    @ZAttr(id=348)
    public String getImapProxyBindPortAsString() {
        return getAttr(Provisioning.A_zmailImapProxyBindPort, "143");
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zmailImapProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=348)
    public void setImapProxyBindPort(int zmailImapProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapProxyBindPort, Integer.toString(zmailImapProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zmailImapProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=348)
    public Map<String,Object> setImapProxyBindPort(int zmailImapProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapProxyBindPort, Integer.toString(zmailImapProxyBindPort));
        return attrs;
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zmailImapProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=348)
    public void setImapProxyBindPortAsString(String zmailImapProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapProxyBindPort, zmailImapProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @param zmailImapProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=348)
    public Map<String,Object> setImapProxyBindPortAsString(String zmailImapProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapProxyBindPort, zmailImapProxyBindPort);
        return attrs;
    }

    /**
     * port number on which IMAP proxy server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=348)
    public void unsetImapProxyBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapProxyBindPort, "");
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
        attrs.put(Provisioning.A_zmailImapProxyBindPort, "");
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailImapSSLBindAddress, or empty array if unset
     */
    @ZAttr(id=182)
    public String[] getImapSSLBindAddress() {
        return getMultiAttr(Provisioning.A_zmailImapSSLBindAddress);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapSSLBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void setImapSSLBindAddress(String[] zmailImapSSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindAddress, zmailImapSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapSSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> setImapSSLBindAddress(String[] zmailImapSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindAddress, zmailImapSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapSSLBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void addImapSSLBindAddress(String zmailImapSSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapSSLBindAddress, zmailImapSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapSSLBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> addImapSSLBindAddress(String zmailImapSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapSSLBindAddress, zmailImapSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapSSLBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void removeImapSSLBindAddress(String zmailImapSSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapSSLBindAddress, zmailImapSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailImapSSLBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=182)
    public Map<String,Object> removeImapSSLBindAddress(String zmailImapSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapSSLBindAddress, zmailImapSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which IMAP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=182)
    public void unsetImapSSLBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindAddress, "");
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
        attrs.put(Provisioning.A_zmailImapSSLBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zmailImapSSLBindOnStartup, or true if unset
     */
    @ZAttr(id=269)
    public boolean isImapSSLBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zmailImapSSLBindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailImapSSLBindOnStartup new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=269)
    public void setImapSSLBindOnStartup(boolean zmailImapSSLBindOnStartup) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindOnStartup, zmailImapSSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailImapSSLBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=269)
    public Map<String,Object> setImapSSLBindOnStartup(boolean zmailImapSSLBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindOnStartup, zmailImapSSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=269)
    public void unsetImapSSLBindOnStartup() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindOnStartup, "");
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
        attrs.put(Provisioning.A_zmailImapSSLBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * <p>Use getImapSSLBindPortAsString to access value as a string.
     *
     * @see #getImapSSLBindPortAsString()
     *
     * @return zmailImapSSLBindPort, or 7993 if unset
     */
    @ZAttr(id=183)
    public int getImapSSLBindPort() {
        return getIntAttr(Provisioning.A_zmailImapSSLBindPort, 7993);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @return zmailImapSSLBindPort, or "7993" if unset
     */
    @ZAttr(id=183)
    public String getImapSSLBindPortAsString() {
        return getAttr(Provisioning.A_zmailImapSSLBindPort, "7993");
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zmailImapSSLBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=183)
    public void setImapSSLBindPort(int zmailImapSSLBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindPort, Integer.toString(zmailImapSSLBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zmailImapSSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=183)
    public Map<String,Object> setImapSSLBindPort(int zmailImapSSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindPort, Integer.toString(zmailImapSSLBindPort));
        return attrs;
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zmailImapSSLBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=183)
    public void setImapSSLBindPortAsString(String zmailImapSSLBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindPort, zmailImapSSLBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @param zmailImapSSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=183)
    public Map<String,Object> setImapSSLBindPortAsString(String zmailImapSSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindPort, zmailImapSSLBindPort);
        return attrs;
    }

    /**
     * port number on which IMAP SSL server should listen on
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=183)
    public void unsetImapSSLBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLBindPort, "");
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
        attrs.put(Provisioning.A_zmailImapSSLBindPort, "");
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @return zmailImapSSLDisabledCapability, or empty array if unset
     */
    @ZAttr(id=444)
    public String[] getImapSSLDisabledCapability() {
        return getMultiAttr(Provisioning.A_zmailImapSSLDisabledCapability);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zmailImapSSLDisabledCapability new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void setImapSSLDisabledCapability(String[] zmailImapSSLDisabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLDisabledCapability, zmailImapSSLDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zmailImapSSLDisabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> setImapSSLDisabledCapability(String[] zmailImapSSLDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLDisabledCapability, zmailImapSSLDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zmailImapSSLDisabledCapability new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void addImapSSLDisabledCapability(String zmailImapSSLDisabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapSSLDisabledCapability, zmailImapSSLDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zmailImapSSLDisabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> addImapSSLDisabledCapability(String zmailImapSSLDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailImapSSLDisabledCapability, zmailImapSSLDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zmailImapSSLDisabledCapability existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void removeImapSSLDisabledCapability(String zmailImapSSLDisabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapSSLDisabledCapability, zmailImapSSLDisabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @param zmailImapSSLDisabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=444)
    public Map<String,Object> removeImapSSLDisabledCapability(String zmailImapSSLDisabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailImapSSLDisabledCapability, zmailImapSSLDisabledCapability);
        return attrs;
    }

    /**
     * disabled IMAP SSL capabilities. Capabilities are listed on the
     * CAPABILITY line, also known in RFCs as extensions
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=444)
    public void unsetImapSSLDisabledCapability() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLDisabledCapability, "");
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
        attrs.put(Provisioning.A_zmailImapSSLDisabledCapability, "");
        return attrs;
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * <p>Use getImapSSLProxyBindPortAsString to access value as a string.
     *
     * @see #getImapSSLProxyBindPortAsString()
     *
     * @return zmailImapSSLProxyBindPort, or 993 if unset
     */
    @ZAttr(id=349)
    public int getImapSSLProxyBindPort() {
        return getIntAttr(Provisioning.A_zmailImapSSLProxyBindPort, 993);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @return zmailImapSSLProxyBindPort, or "993" if unset
     */
    @ZAttr(id=349)
    public String getImapSSLProxyBindPortAsString() {
        return getAttr(Provisioning.A_zmailImapSSLProxyBindPort, "993");
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zmailImapSSLProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=349)
    public void setImapSSLProxyBindPort(int zmailImapSSLProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLProxyBindPort, Integer.toString(zmailImapSSLProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zmailImapSSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=349)
    public Map<String,Object> setImapSSLProxyBindPort(int zmailImapSSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLProxyBindPort, Integer.toString(zmailImapSSLProxyBindPort));
        return attrs;
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zmailImapSSLProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=349)
    public void setImapSSLProxyBindPortAsString(String zmailImapSSLProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLProxyBindPort, zmailImapSSLProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @param zmailImapSSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=349)
    public Map<String,Object> setImapSSLProxyBindPortAsString(String zmailImapSSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLProxyBindPort, zmailImapSSLProxyBindPort);
        return attrs;
    }

    /**
     * port number on which IMAPS proxy server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=349)
    public void unsetImapSSLProxyBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLProxyBindPort, "");
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
        attrs.put(Provisioning.A_zmailImapSSLProxyBindPort, "");
        return attrs;
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @return zmailImapSSLServerEnabled, or true if unset
     */
    @ZAttr(id=184)
    public boolean isImapSSLServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailImapSSLServerEnabled, true);
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @param zmailImapSSLServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=184)
    public void setImapSSLServerEnabled(boolean zmailImapSSLServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLServerEnabled, zmailImapSSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @param zmailImapSSLServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=184)
    public Map<String,Object> setImapSSLServerEnabled(boolean zmailImapSSLServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLServerEnabled, zmailImapSSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SSL server is enabled for a given server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=184)
    public void unsetImapSSLServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSSLServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailImapSSLServerEnabled, "");
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @return zmailImapSaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public boolean isImapSaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zmailImapSaslGssapiEnabled, false);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @param zmailImapSaslGssapiEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public void setImapSaslGssapiEnabled(boolean zmailImapSaslGssapiEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSaslGssapiEnabled, zmailImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @param zmailImapSaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public Map<String,Object> setImapSaslGssapiEnabled(boolean zmailImapSaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSaslGssapiEnabled, zmailImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for a given server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=555)
    public void unsetImapSaslGssapiEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapSaslGssapiEnabled, "");
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
        attrs.put(Provisioning.A_zmailImapSaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @return zmailImapServerEnabled, or true if unset
     */
    @ZAttr(id=176)
    public boolean isImapServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailImapServerEnabled, true);
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @param zmailImapServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=176)
    public void setImapServerEnabled(boolean zmailImapServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapServerEnabled, zmailImapServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @param zmailImapServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=176)
    public Map<String,Object> setImapServerEnabled(boolean zmailImapServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapServerEnabled, zmailImapServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP is enabled for a server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=176)
    public void unsetImapServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailImapServerEnabled, "");
        return attrs;
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @return zmailImapShutdownGraceSeconds, or 10 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public int getImapShutdownGraceSeconds() {
        return getIntAttr(Provisioning.A_zmailImapShutdownGraceSeconds, 10);
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @param zmailImapShutdownGraceSeconds new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public void setImapShutdownGraceSeconds(int zmailImapShutdownGraceSeconds) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapShutdownGraceSeconds, Integer.toString(zmailImapShutdownGraceSeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @param zmailImapShutdownGraceSeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public Map<String,Object> setImapShutdownGraceSeconds(int zmailImapShutdownGraceSeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapShutdownGraceSeconds, Integer.toString(zmailImapShutdownGraceSeconds));
        return attrs;
    }

    /**
     * number of seconds to wait before forcing IMAP server shutdown
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1080)
    public void unsetImapShutdownGraceSeconds() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailImapShutdownGraceSeconds, "");
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
        attrs.put(Provisioning.A_zmailImapShutdownGraceSeconds, "");
        return attrs;
    }

    /**
     * true if this server is the monitor host
     *
     * @return zmailIsMonitorHost, or false if unset
     */
    @ZAttr(id=132)
    public boolean isIsMonitorHost() {
        return getBooleanAttr(Provisioning.A_zmailIsMonitorHost, false);
    }

    /**
     * true if this server is the monitor host
     *
     * @param zmailIsMonitorHost new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=132)
    public void setIsMonitorHost(boolean zmailIsMonitorHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsMonitorHost, zmailIsMonitorHost ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * true if this server is the monitor host
     *
     * @param zmailIsMonitorHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=132)
    public Map<String,Object> setIsMonitorHost(boolean zmailIsMonitorHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsMonitorHost, zmailIsMonitorHost ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * true if this server is the monitor host
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=132)
    public void unsetIsMonitorHost() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsMonitorHost, "");
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
        attrs.put(Provisioning.A_zmailIsMonitorHost, "");
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
     * @return zmailLastPurgeMaxDuration in millseconds, or 2592000000 (30d)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public long getLastPurgeMaxDuration() {
        return getTimeInterval(Provisioning.A_zmailLastPurgeMaxDuration, 2592000000L);
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailLastPurgeMaxDuration, or "30d" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public String getLastPurgeMaxDurationAsString() {
        return getAttr(Provisioning.A_zmailLastPurgeMaxDuration, "30d");
    }

    /**
     * Maximum duration beyond which the mailbox must be scheduled for purge
     * irrespective of whether it is loaded into memory or not.. Must be in
     * valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailLastPurgeMaxDuration new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public void setLastPurgeMaxDuration(String zmailLastPurgeMaxDuration) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLastPurgeMaxDuration, zmailLastPurgeMaxDuration);
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
     * @param zmailLastPurgeMaxDuration new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public Map<String,Object> setLastPurgeMaxDuration(String zmailLastPurgeMaxDuration, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLastPurgeMaxDuration, zmailLastPurgeMaxDuration);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1382)
    public void unsetLastPurgeMaxDuration() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLastPurgeMaxDuration, "");
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
        attrs.put(Provisioning.A_zmailLastPurgeMaxDuration, "");
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @return zmailLmtpAdvertisedName, or null if unset
     */
    @ZAttr(id=23)
    public String getLmtpAdvertisedName() {
        return getAttr(Provisioning.A_zmailLmtpAdvertisedName, null);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zmailLmtpAdvertisedName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=23)
    public void setLmtpAdvertisedName(String zmailLmtpAdvertisedName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpAdvertisedName, zmailLmtpAdvertisedName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zmailLmtpAdvertisedName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=23)
    public Map<String,Object> setLmtpAdvertisedName(String zmailLmtpAdvertisedName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpAdvertisedName, zmailLmtpAdvertisedName);
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=23)
    public void unsetLmtpAdvertisedName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpAdvertisedName, "");
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
        attrs.put(Provisioning.A_zmailLmtpAdvertisedName, "");
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailLmtpBindAddress, or empty array if unset
     */
    @ZAttr(id=25)
    public String[] getLmtpBindAddress() {
        return getMultiAttr(Provisioning.A_zmailLmtpBindAddress);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailLmtpBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void setLmtpBindAddress(String[] zmailLmtpBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindAddress, zmailLmtpBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailLmtpBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> setLmtpBindAddress(String[] zmailLmtpBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindAddress, zmailLmtpBindAddress);
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailLmtpBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void addLmtpBindAddress(String zmailLmtpBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailLmtpBindAddress, zmailLmtpBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailLmtpBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> addLmtpBindAddress(String zmailLmtpBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailLmtpBindAddress, zmailLmtpBindAddress);
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailLmtpBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void removeLmtpBindAddress(String zmailLmtpBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailLmtpBindAddress, zmailLmtpBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailLmtpBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=25)
    public Map<String,Object> removeLmtpBindAddress(String zmailLmtpBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailLmtpBindAddress, zmailLmtpBindAddress);
        return attrs;
    }

    /**
     * interface address on which LMTP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=25)
    public void unsetLmtpBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindAddress, "");
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
        attrs.put(Provisioning.A_zmailLmtpBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zmailLmtpBindOnStartup, or false if unset
     */
    @ZAttr(id=270)
    public boolean isLmtpBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zmailLmtpBindOnStartup, false);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailLmtpBindOnStartup new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=270)
    public void setLmtpBindOnStartup(boolean zmailLmtpBindOnStartup) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindOnStartup, zmailLmtpBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailLmtpBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=270)
    public Map<String,Object> setLmtpBindOnStartup(boolean zmailLmtpBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindOnStartup, zmailLmtpBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=270)
    public void unsetLmtpBindOnStartup() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindOnStartup, "");
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
        attrs.put(Provisioning.A_zmailLmtpBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which LMTP server should listen
     *
     * <p>Use getLmtpBindPortAsString to access value as a string.
     *
     * @see #getLmtpBindPortAsString()
     *
     * @return zmailLmtpBindPort, or 7025 if unset
     */
    @ZAttr(id=24)
    public int getLmtpBindPort() {
        return getIntAttr(Provisioning.A_zmailLmtpBindPort, 7025);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @return zmailLmtpBindPort, or "7025" if unset
     */
    @ZAttr(id=24)
    public String getLmtpBindPortAsString() {
        return getAttr(Provisioning.A_zmailLmtpBindPort, "7025");
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zmailLmtpBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=24)
    public void setLmtpBindPort(int zmailLmtpBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindPort, Integer.toString(zmailLmtpBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zmailLmtpBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=24)
    public Map<String,Object> setLmtpBindPort(int zmailLmtpBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindPort, Integer.toString(zmailLmtpBindPort));
        return attrs;
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zmailLmtpBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=24)
    public void setLmtpBindPortAsString(String zmailLmtpBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindPort, zmailLmtpBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which LMTP server should listen
     *
     * @param zmailLmtpBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=24)
    public Map<String,Object> setLmtpBindPortAsString(String zmailLmtpBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindPort, zmailLmtpBindPort);
        return attrs;
    }

    /**
     * port number on which LMTP server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=24)
    public void unsetLmtpBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpBindPort, "");
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
        attrs.put(Provisioning.A_zmailLmtpBindPort, "");
        return attrs;
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @return zmailLmtpExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public boolean isLmtpExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zmailLmtpExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @param zmailLmtpExposeVersionOnBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public void setLmtpExposeVersionOnBanner(boolean zmailLmtpExposeVersionOnBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpExposeVersionOnBanner, zmailLmtpExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @param zmailLmtpExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public Map<String,Object> setLmtpExposeVersionOnBanner(boolean zmailLmtpExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpExposeVersionOnBanner, zmailLmtpExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on LMTP banner
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=691)
    public void unsetLmtpExposeVersionOnBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpExposeVersionOnBanner, "");
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
        attrs.put(Provisioning.A_zmailLmtpExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @return zmailLmtpNumThreads, or 20 if unset
     */
    @ZAttr(id=26)
    public int getLmtpNumThreads() {
        return getIntAttr(Provisioning.A_zmailLmtpNumThreads, 20);
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @param zmailLmtpNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=26)
    public void setLmtpNumThreads(int zmailLmtpNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpNumThreads, Integer.toString(zmailLmtpNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @param zmailLmtpNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=26)
    public Map<String,Object> setLmtpNumThreads(int zmailLmtpNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpNumThreads, Integer.toString(zmailLmtpNumThreads));
        return attrs;
    }

    /**
     * number of handler threads, should match MTA concurrency setting for
     * this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=26)
    public void unsetLmtpNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpNumThreads, "");
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
        attrs.put(Provisioning.A_zmailLmtpNumThreads, "");
        return attrs;
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @return zmailLmtpPermanentFailureWhenOverQuota, or false if unset
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public boolean isLmtpPermanentFailureWhenOverQuota() {
        return getBooleanAttr(Provisioning.A_zmailLmtpPermanentFailureWhenOverQuota, false);
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @param zmailLmtpPermanentFailureWhenOverQuota new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public void setLmtpPermanentFailureWhenOverQuota(boolean zmailLmtpPermanentFailureWhenOverQuota) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpPermanentFailureWhenOverQuota, zmailLmtpPermanentFailureWhenOverQuota ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @param zmailLmtpPermanentFailureWhenOverQuota new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public Map<String,Object> setLmtpPermanentFailureWhenOverQuota(boolean zmailLmtpPermanentFailureWhenOverQuota, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpPermanentFailureWhenOverQuota, zmailLmtpPermanentFailureWhenOverQuota ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If true, a permanent failure (552) is returned when the user is over
     * quota. If false, a temporary failure (452) is returned.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.6
     */
    @ZAttr(id=657)
    public void unsetLmtpPermanentFailureWhenOverQuota() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpPermanentFailureWhenOverQuota, "");
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
        attrs.put(Provisioning.A_zmailLmtpPermanentFailureWhenOverQuota, "");
        return attrs;
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @return zmailLmtpServerEnabled, or true if unset
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public boolean isLmtpServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailLmtpServerEnabled, true);
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @param zmailLmtpServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public void setLmtpServerEnabled(boolean zmailLmtpServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpServerEnabled, zmailLmtpServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @param zmailLmtpServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public Map<String,Object> setLmtpServerEnabled(boolean zmailLmtpServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpServerEnabled, zmailLmtpServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether LMTP server is enabled for a given server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.4
     */
    @ZAttr(id=630)
    public void unsetLmtpServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailLmtpServerEnabled, "");
        return attrs;
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @return zmailLmtpShutdownGraceSeconds, or 10 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public int getLmtpShutdownGraceSeconds() {
        return getIntAttr(Provisioning.A_zmailLmtpShutdownGraceSeconds, 10);
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @param zmailLmtpShutdownGraceSeconds new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public void setLmtpShutdownGraceSeconds(int zmailLmtpShutdownGraceSeconds) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpShutdownGraceSeconds, Integer.toString(zmailLmtpShutdownGraceSeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @param zmailLmtpShutdownGraceSeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public Map<String,Object> setLmtpShutdownGraceSeconds(int zmailLmtpShutdownGraceSeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpShutdownGraceSeconds, Integer.toString(zmailLmtpShutdownGraceSeconds));
        return attrs;
    }

    /**
     * number of seconds to wait before forcing LMTP server shutdown
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1082)
    public void unsetLmtpShutdownGraceSeconds() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLmtpShutdownGraceSeconds, "");
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
        attrs.put(Provisioning.A_zmailLmtpShutdownGraceSeconds, "");
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
     * whether mailbox server should log to syslog
     *
     * @return zmailLogToSyslog, or false if unset
     */
    @ZAttr(id=520)
    public boolean isLogToSyslog() {
        return getBooleanAttr(Provisioning.A_zmailLogToSyslog, false);
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @param zmailLogToSyslog new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=520)
    public void setLogToSyslog(boolean zmailLogToSyslog) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLogToSyslog, zmailLogToSyslog ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @param zmailLogToSyslog new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=520)
    public Map<String,Object> setLogToSyslog(boolean zmailLogToSyslog, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLogToSyslog, zmailLogToSyslog ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether mailbox server should log to syslog
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=520)
    public void unsetLogToSyslog() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailLogToSyslog, "");
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
        attrs.put(Provisioning.A_zmailLogToSyslog, "");
        return attrs;
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailMailBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public String getMailBindAddress() {
        return getAttr(Provisioning.A_zmailMailBindAddress, null);
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailMailBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public void setMailBindAddress(String zmailMailBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailBindAddress, zmailMailBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailMailBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public Map<String,Object> setMailBindAddress(String zmailMailBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailBindAddress, zmailMailBindAddress);
        return attrs;
    }

    /**
     * interface address on which HTTP server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1365)
    public void unsetMailBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailBindAddress, "");
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
        attrs.put(Provisioning.A_zmailMailBindAddress, "");
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port when zmailMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @return zmailMailClearTextPasswordEnabled, or true if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public boolean isMailClearTextPasswordEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMailClearTextPasswordEnabled, true);
    }

    /**
     * Whether to allow password sent to non-secured port when zmailMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @param zmailMailClearTextPasswordEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public void setMailClearTextPasswordEnabled(boolean zmailMailClearTextPasswordEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailClearTextPasswordEnabled, zmailMailClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port when zmailMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @param zmailMailClearTextPasswordEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public Map<String,Object> setMailClearTextPasswordEnabled(boolean zmailMailClearTextPasswordEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailClearTextPasswordEnabled, zmailMailClearTextPasswordEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to allow password sent to non-secured port when zmailMailMode
     * is mixed. If it set to TRUE the server will allow login with clear
     * text AuthRequests and change password with clear text
     * ChangePasswordRequest. If it set to FALSE the server will return an
     * error if an attempt is made to ChangePasswordRequest or AuthRequest.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=791)
    public void unsetMailClearTextPasswordEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailClearTextPasswordEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to allow password sent to non-secured port when zmailMailMode
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
        attrs.put(Provisioning.A_zmailMailClearTextPasswordEnabled, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @return zmailMailContentMaxSize, or 10240000 if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public long getMailContentMaxSize() {
        return getLongAttr(Provisioning.A_zmailMailContentMaxSize, 10240000L);
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @param zmailMailContentMaxSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public void setMailContentMaxSize(long zmailMailContentMaxSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailContentMaxSize, Long.toString(zmailMailContentMaxSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @param zmailMailContentMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public Map<String,Object> setMailContentMaxSize(long zmailMailContentMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailContentMaxSize, Long.toString(zmailMailContentMaxSize));
        return attrs;
    }

    /**
     * Maximum size in bytes for the &lt;content &gt; element in SOAP. Mail
     * content larger than this limit will be truncated.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=807)
    public void unsetMailContentMaxSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailContentMaxSize, "");
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
        attrs.put(Provisioning.A_zmailMailContentMaxSize, "");
        return attrs;
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @return zmailMailDiskStreamingThreshold, or 1048576 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public int getMailDiskStreamingThreshold() {
        return getIntAttr(Provisioning.A_zmailMailDiskStreamingThreshold, 1048576);
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @param zmailMailDiskStreamingThreshold new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public void setMailDiskStreamingThreshold(int zmailMailDiskStreamingThreshold) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDiskStreamingThreshold, Integer.toString(zmailMailDiskStreamingThreshold));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @param zmailMailDiskStreamingThreshold new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public Map<String,Object> setMailDiskStreamingThreshold(int zmailMailDiskStreamingThreshold, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDiskStreamingThreshold, Integer.toString(zmailMailDiskStreamingThreshold));
        return attrs;
    }

    /**
     * Incoming messages larger than this number of bytes are streamed to
     * disk during LMTP delivery, instead of being read into memory. This
     * limits memory consumption at the expense of higher disk utilization.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=565)
    public void unsetMailDiskStreamingThreshold() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailDiskStreamingThreshold, "");
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
        attrs.put(Provisioning.A_zmailMailDiskStreamingThreshold, "");
        return attrs;
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @return zmailMailEmptyFolderBatchSize, or 1000 if unset
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public int getMailEmptyFolderBatchSize() {
        return getIntAttr(Provisioning.A_zmailMailEmptyFolderBatchSize, 1000);
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @param zmailMailEmptyFolderBatchSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public void setMailEmptyFolderBatchSize(int zmailMailEmptyFolderBatchSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchSize, Integer.toString(zmailMailEmptyFolderBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @param zmailMailEmptyFolderBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public Map<String,Object> setMailEmptyFolderBatchSize(int zmailMailEmptyFolderBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchSize, Integer.toString(zmailMailEmptyFolderBatchSize));
        return attrs;
    }

    /**
     * Maximum number of messages to delete during a single transaction when
     * emptying a large folder.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1097)
    public void unsetMailEmptyFolderBatchSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchSize, "");
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
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchSize, "");
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zmailMailEmptyFolderBatchSize.
     *
     * @return zmailMailEmptyFolderBatchThreshold, or 100000 if unset
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public int getMailEmptyFolderBatchThreshold() {
        return getIntAttr(Provisioning.A_zmailMailEmptyFolderBatchThreshold, 100000);
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zmailMailEmptyFolderBatchSize.
     *
     * @param zmailMailEmptyFolderBatchThreshold new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public void setMailEmptyFolderBatchThreshold(int zmailMailEmptyFolderBatchThreshold) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchThreshold, Integer.toString(zmailMailEmptyFolderBatchThreshold));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zmailMailEmptyFolderBatchSize.
     *
     * @param zmailMailEmptyFolderBatchThreshold new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public Map<String,Object> setMailEmptyFolderBatchThreshold(int zmailMailEmptyFolderBatchThreshold, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchThreshold, Integer.toString(zmailMailEmptyFolderBatchThreshold));
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zmailMailEmptyFolderBatchSize.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public void unsetMailEmptyFolderBatchThreshold() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchThreshold, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Empty folder operation now always deletes
     * items in batches, hence a threshold is no longer applicable.. Orig
     * desc: Folders that contain more than this many messages will be
     * emptied in batches of size zmailMailEmptyFolderBatchSize.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.13
     */
    @ZAttr(id=1208)
    public Map<String,Object> unsetMailEmptyFolderBatchThreshold(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailEmptyFolderBatchThreshold, "");
        return attrs;
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @return zmailMailFileDescriptorBufferSize, or 4096 if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public int getMailFileDescriptorBufferSize() {
        return getIntAttr(Provisioning.A_zmailMailFileDescriptorBufferSize, 4096);
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @param zmailMailFileDescriptorBufferSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public void setMailFileDescriptorBufferSize(int zmailMailFileDescriptorBufferSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailFileDescriptorBufferSize, Integer.toString(zmailMailFileDescriptorBufferSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @param zmailMailFileDescriptorBufferSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public Map<String,Object> setMailFileDescriptorBufferSize(int zmailMailFileDescriptorBufferSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailFileDescriptorBufferSize, Integer.toString(zmailMailFileDescriptorBufferSize));
        return attrs;
    }

    /**
     * Number of bytes to buffer in memory per file descriptor in the cache.
     * Larger values result in fewer disk reads, but increase memory
     * consumption.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1035)
    public void unsetMailFileDescriptorBufferSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailFileDescriptorBufferSize, "");
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
        attrs.put(Provisioning.A_zmailMailFileDescriptorBufferSize, "");
        return attrs;
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @return zmailMailFileDescriptorCacheSize, or 1000 if unset
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public int getMailFileDescriptorCacheSize() {
        return getIntAttr(Provisioning.A_zmailMailFileDescriptorCacheSize, 1000);
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @param zmailMailFileDescriptorCacheSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public void setMailFileDescriptorCacheSize(int zmailMailFileDescriptorCacheSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailFileDescriptorCacheSize, Integer.toString(zmailMailFileDescriptorCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @param zmailMailFileDescriptorCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public Map<String,Object> setMailFileDescriptorCacheSize(int zmailMailFileDescriptorCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailFileDescriptorCacheSize, Integer.toString(zmailMailFileDescriptorCacheSize));
        return attrs;
    }

    /**
     * Maximum number of file descriptors that are opened for accessing
     * message content.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_RC1
     */
    @ZAttr(id=1034)
    public void unsetMailFileDescriptorCacheSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailFileDescriptorCacheSize, "");
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
        attrs.put(Provisioning.A_zmailMailFileDescriptorCacheSize, "");
        return attrs;
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @return zmailMailKeepOutWebCrawlers, or false if unset
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public boolean isMailKeepOutWebCrawlers() {
        return getBooleanAttr(Provisioning.A_zmailMailKeepOutWebCrawlers, false);
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @param zmailMailKeepOutWebCrawlers new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public void setMailKeepOutWebCrawlers(boolean zmailMailKeepOutWebCrawlers) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailKeepOutWebCrawlers, zmailMailKeepOutWebCrawlers ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @param zmailMailKeepOutWebCrawlers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public Map<String,Object> setMailKeepOutWebCrawlers(boolean zmailMailKeepOutWebCrawlers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailKeepOutWebCrawlers, zmailMailKeepOutWebCrawlers ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * When set to true, robots.txt on mailboxd will be set up to keep web
     * crawlers out
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1161)
    public void unsetMailKeepOutWebCrawlers() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailKeepOutWebCrawlers, "");
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
        attrs.put(Provisioning.A_zmailMailKeepOutWebCrawlers, "");
        return attrs;
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @return zmailMailLastPurgedMailboxId, or -1 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public int getMailLastPurgedMailboxId() {
        return getIntAttr(Provisioning.A_zmailMailLastPurgedMailboxId, -1);
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @param zmailMailLastPurgedMailboxId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public void setMailLastPurgedMailboxId(int zmailMailLastPurgedMailboxId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLastPurgedMailboxId, Integer.toString(zmailMailLastPurgedMailboxId));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @param zmailMailLastPurgedMailboxId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public Map<String,Object> setMailLastPurgedMailboxId(int zmailMailLastPurgedMailboxId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLastPurgedMailboxId, Integer.toString(zmailMailLastPurgedMailboxId));
        return attrs;
    }

    /**
     * Deprecated since: 5.0.7. deprecated per bug 28842. Orig desc: The id
     * of the last purged mailbox.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=543)
    public void unsetMailLastPurgedMailboxId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLastPurgedMailboxId, "");
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
        attrs.put(Provisioning.A_zmailMailLastPurgedMailboxId, "");
        return attrs;
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zmailMailMode
     * and zmailMailBindAddress.
     *
     * @return zmailMailLocalBind, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public boolean isMailLocalBind() {
        return getBooleanAttr(Provisioning.A_zmailMailLocalBind, false);
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zmailMailMode
     * and zmailMailBindAddress.
     *
     * @param zmailMailLocalBind new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public void setMailLocalBind(boolean zmailMailLocalBind) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLocalBind, zmailMailLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zmailMailMode
     * and zmailMailBindAddress.
     *
     * @param zmailMailLocalBind new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public Map<String,Object> setMailLocalBind(boolean zmailMailLocalBind, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLocalBind, zmailMailLocalBind ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zmailMailMode
     * and zmailMailBindAddress.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public void unsetMailLocalBind() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLocalBind, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Specifies whether the http server should bound to localhost or not.
     * This is an immutable property and is generated based on zmailMailMode
     * and zmailMailBindAddress.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1380)
    public Map<String,Object> unsetMailLocalBind(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailLocalBind, "");
        return attrs;
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zmailMailMode, or null if unset and/or has invalid value
     */
    @ZAttr(id=308)
    public ZAttrProvisioning.MailMode getMailMode() {
        try { String v = getAttr(Provisioning.A_zmailMailMode); return v == null ? null : ZAttrProvisioning.MailMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zmailMailMode, or null if unset
     */
    @ZAttr(id=308)
    public String getMailModeAsString() {
        return getAttr(Provisioning.A_zmailMailMode, null);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailMailMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=308)
    public void setMailMode(ZAttrProvisioning.MailMode zmailMailMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailMode, zmailMailMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=308)
    public Map<String,Object> setMailMode(ZAttrProvisioning.MailMode zmailMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailMode, zmailMailMode.toString());
        return attrs;
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailMailMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=308)
    public void setMailModeAsString(String zmailMailMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailMode, zmailMailMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=308)
    public Map<String,Object> setMailModeAsString(String zmailMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailMode, zmailMailMode);
        return attrs;
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=308)
    public void unsetMailMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run HTTP or HTTPS or both/mixed mode or redirect mode. See
     * also related attributes zmailMailPort and zmailMailSSLPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=308)
    public Map<String,Object> unsetMailMode(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailMode, "");
        return attrs;
    }

    /**
     * HTTP port for end-user UI
     *
     * <p>Use getMailPortAsString to access value as a string.
     *
     * @see #getMailPortAsString()
     *
     * @return zmailMailPort, or 80 if unset
     */
    @ZAttr(id=154)
    public int getMailPort() {
        return getIntAttr(Provisioning.A_zmailMailPort, 80);
    }

    /**
     * HTTP port for end-user UI
     *
     * @return zmailMailPort, or "80" if unset
     */
    @ZAttr(id=154)
    public String getMailPortAsString() {
        return getAttr(Provisioning.A_zmailMailPort, "80");
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zmailMailPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=154)
    public void setMailPort(int zmailMailPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPort, Integer.toString(zmailMailPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zmailMailPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=154)
    public Map<String,Object> setMailPort(int zmailMailPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPort, Integer.toString(zmailMailPort));
        return attrs;
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zmailMailPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=154)
    public void setMailPortAsString(String zmailMailPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPort, zmailMailPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP port for end-user UI
     *
     * @param zmailMailPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=154)
    public Map<String,Object> setMailPortAsString(String zmailMailPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPort, zmailMailPort);
        return attrs;
    }

    /**
     * HTTP port for end-user UI
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=154)
    public void unsetMailPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPort, "");
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
        attrs.put(Provisioning.A_zmailMailPort, "");
        return attrs;
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zmailMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @return zmailMailProxyMaxFails, or 1 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public int getMailProxyMaxFails() {
        return getIntAttr(Provisioning.A_zmailMailProxyMaxFails, 1);
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zmailMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @param zmailMailProxyMaxFails new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public void setMailProxyMaxFails(int zmailMailProxyMaxFails) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyMaxFails, Integer.toString(zmailMailProxyMaxFails));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zmailMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @param zmailMailProxyMaxFails new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public Map<String,Object> setMailProxyMaxFails(int zmailMailProxyMaxFails, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyMaxFails, Integer.toString(zmailMailProxyMaxFails));
        return attrs;
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zmailMailProxyReconnectTimeout and then try to reconnect. Default
     * value is 1. Setting this to 0 means turning this check off.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1358)
    public void unsetMailProxyMaxFails() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyMaxFails, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The max number of unsuccessful attempts to connect to the current
     * server (as an upstream). If this number is reached, proxy will refuse
     * to connect to the current server, wait for
     * zmailMailProxyReconnectTimeout and then try to reconnect. Default
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
        attrs.put(Provisioning.A_zmailMailProxyMaxFails, "");
        return attrs;
    }

    /**
     * HTTP proxy port
     *
     * <p>Use getMailProxyPortAsString to access value as a string.
     *
     * @see #getMailProxyPortAsString()
     *
     * @return zmailMailProxyPort, or 0 if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public int getMailProxyPort() {
        return getIntAttr(Provisioning.A_zmailMailProxyPort, 0);
    }

    /**
     * HTTP proxy port
     *
     * @return zmailMailProxyPort, or "0" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public String getMailProxyPortAsString() {
        return getAttr(Provisioning.A_zmailMailProxyPort, "0");
    }

    /**
     * HTTP proxy port
     *
     * @param zmailMailProxyPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public void setMailProxyPort(int zmailMailProxyPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyPort, Integer.toString(zmailMailProxyPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP proxy port
     *
     * @param zmailMailProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public Map<String,Object> setMailProxyPort(int zmailMailProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyPort, Integer.toString(zmailMailProxyPort));
        return attrs;
    }

    /**
     * HTTP proxy port
     *
     * @param zmailMailProxyPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public void setMailProxyPortAsString(String zmailMailProxyPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyPort, zmailMailProxyPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * HTTP proxy port
     *
     * @param zmailMailProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public Map<String,Object> setMailProxyPortAsString(String zmailMailProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyPort, zmailMailProxyPort);
        return attrs;
    }

    /**
     * HTTP proxy port
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=626)
    public void unsetMailProxyPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyPort, "");
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
        attrs.put(Provisioning.A_zmailMailProxyPort, "");
        return attrs;
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @return zmailMailProxyReconnectTimeout, or "60" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public String getMailProxyReconnectTimeout() {
        return getAttr(Provisioning.A_zmailMailProxyReconnectTimeout, "60");
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @param zmailMailProxyReconnectTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public void setMailProxyReconnectTimeout(String zmailMailProxyReconnectTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyReconnectTimeout, zmailMailProxyReconnectTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @param zmailMailProxyReconnectTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public Map<String,Object> setMailProxyReconnectTimeout(String zmailMailProxyReconnectTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyReconnectTimeout, zmailMailProxyReconnectTimeout);
        return attrs;
    }

    /**
     * the time in sec that proxy will reconnect the current server (as an
     * upstream) after connection errors happened before
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1268)
    public void unsetMailProxyReconnectTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailProxyReconnectTimeout, "");
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
        attrs.put(Provisioning.A_zmailMailProxyReconnectTimeout, "");
        return attrs;
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @return zmailMailPurgeBatchSize, or 1000 if unset
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public int getMailPurgeBatchSize() {
        return getIntAttr(Provisioning.A_zmailMailPurgeBatchSize, 1000);
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @param zmailMailPurgeBatchSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public void setMailPurgeBatchSize(int zmailMailPurgeBatchSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPurgeBatchSize, Integer.toString(zmailMailPurgeBatchSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @param zmailMailPurgeBatchSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public Map<String,Object> setMailPurgeBatchSize(int zmailMailPurgeBatchSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPurgeBatchSize, Integer.toString(zmailMailPurgeBatchSize));
        return attrs;
    }

    /**
     * Maximum number of messages to delete from a folder during a single
     * purge operation. If the limit is exceeded, the mailbox is purged again
     * at the end of the purge cycle until all qualifying messages are
     * purged.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.8
     */
    @ZAttr(id=1096)
    public void unsetMailPurgeBatchSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPurgeBatchSize, "");
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
        attrs.put(Provisioning.A_zmailMailPurgeBatchSize, "");
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
     * @return zmailMailPurgeSleepInterval in millseconds, or 60000 (1m)  if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public long getMailPurgeSleepInterval() {
        return getTimeInterval(Provisioning.A_zmailMailPurgeSleepInterval, 60000L);
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailMailPurgeSleepInterval, or "1m" if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public String getMailPurgeSleepIntervalAsString() {
        return getAttr(Provisioning.A_zmailMailPurgeSleepInterval, "1m");
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailMailPurgeSleepInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public void setMailPurgeSleepInterval(String zmailMailPurgeSleepInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPurgeSleepInterval, zmailMailPurgeSleepInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailMailPurgeSleepInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public Map<String,Object> setMailPurgeSleepInterval(String zmailMailPurgeSleepInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPurgeSleepInterval, zmailMailPurgeSleepInterval);
        return attrs;
    }

    /**
     * Sleep time between subsequent mailbox purges. 0 means that mailbox
     * purging is disabled. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=542)
    public void unsetMailPurgeSleepInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailPurgeSleepInterval, "");
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
        attrs.put(Provisioning.A_zmailMailPurgeSleepInterval, "");
        return attrs;
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @return zmailMailRedirectSetEnvelopeSender, or true if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public boolean isMailRedirectSetEnvelopeSender() {
        return getBooleanAttr(Provisioning.A_zmailMailRedirectSetEnvelopeSender, true);
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @param zmailMailRedirectSetEnvelopeSender new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public void setMailRedirectSetEnvelopeSender(boolean zmailMailRedirectSetEnvelopeSender) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailRedirectSetEnvelopeSender, zmailMailRedirectSetEnvelopeSender ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @param zmailMailRedirectSetEnvelopeSender new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public Map<String,Object> setMailRedirectSetEnvelopeSender(boolean zmailMailRedirectSetEnvelopeSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailRedirectSetEnvelopeSender, zmailMailRedirectSetEnvelopeSender ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If TRUE, the envelope sender of a message redirected by mail filters
     * will be set to the users address. If FALSE, the envelope sender will
     * be set to the From address of the redirected message.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=764)
    public void unsetMailRedirectSetEnvelopeSender() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailRedirectSetEnvelopeSender, "");
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
        attrs.put(Provisioning.A_zmailMailRedirectSetEnvelopeSender, "");
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
     * @return zmailMailReferMode, or ZAttrProvisioning.MailReferMode.wronghost if unset and/or has invalid value
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public ZAttrProvisioning.MailReferMode getMailReferMode() {
        try { String v = getAttr(Provisioning.A_zmailMailReferMode); return v == null ? ZAttrProvisioning.MailReferMode.wronghost : ZAttrProvisioning.MailReferMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.MailReferMode.wronghost; }
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
     * @return zmailMailReferMode, or "wronghost" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public String getMailReferModeAsString() {
        return getAttr(Provisioning.A_zmailMailReferMode, "wronghost");
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
     * @param zmailMailReferMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public void setMailReferMode(ZAttrProvisioning.MailReferMode zmailMailReferMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailReferMode, zmailMailReferMode.toString());
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
     * @param zmailMailReferMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public Map<String,Object> setMailReferMode(ZAttrProvisioning.MailReferMode zmailMailReferMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailReferMode, zmailMailReferMode.toString());
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
     * @param zmailMailReferMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public void setMailReferModeAsString(String zmailMailReferMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailReferMode, zmailMailReferMode);
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
     * @param zmailMailReferMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public Map<String,Object> setMailReferModeAsString(String zmailMailReferMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailReferMode, zmailMailReferMode);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=613)
    public void unsetMailReferMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailReferMode, "");
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
        attrs.put(Provisioning.A_zmailMailReferMode, "");
        return attrs;
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailMailSSLBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public String getMailSSLBindAddress() {
        return getAttr(Provisioning.A_zmailMailSSLBindAddress, null);
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailMailSSLBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public void setMailSSLBindAddress(String zmailMailSSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLBindAddress, zmailMailSSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailMailSSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public Map<String,Object> setMailSSLBindAddress(String zmailMailSSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLBindAddress, zmailMailSSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which HTTPS server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1366)
    public void unsetMailSSLBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLBindAddress, "");
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
        attrs.put(Provisioning.A_zmailMailSSLBindAddress, "");
        return attrs;
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @return zmailMailSSLClientCertBindAddress, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public String getMailSSLClientCertBindAddress() {
        return getAttr(Provisioning.A_zmailMailSSLClientCertBindAddress, null);
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailMailSSLClientCertBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public void setMailSSLClientCertBindAddress(String zmailMailSSLClientCertBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertBindAddress, zmailMailSSLClientCertBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailMailSSLClientCertBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public Map<String,Object> setMailSSLClientCertBindAddress(String zmailMailSSLClientCertBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertBindAddress, zmailMailSSLClientCertBindAddress);
        return attrs;
    }

    /**
     * interface address on which HTTPS server accepting client certificates
     * should listen; if empty, binds to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1367)
    public void unsetMailSSLClientCertBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertBindAddress, "");
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
        attrs.put(Provisioning.A_zmailMailSSLClientCertBindAddress, "");
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @return zmailMailSSLClientCertMode, or ZAttrProvisioning.MailSSLClientCertMode.Disabled if unset and/or has invalid value
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public ZAttrProvisioning.MailSSLClientCertMode getMailSSLClientCertMode() {
        try { String v = getAttr(Provisioning.A_zmailMailSSLClientCertMode); return v == null ? ZAttrProvisioning.MailSSLClientCertMode.Disabled : ZAttrProvisioning.MailSSLClientCertMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.MailSSLClientCertMode.Disabled; }
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @return zmailMailSSLClientCertMode, or "Disabled" if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public String getMailSSLClientCertModeAsString() {
        return getAttr(Provisioning.A_zmailMailSSLClientCertMode, "Disabled");
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zmailMailSSLClientCertMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public void setMailSSLClientCertMode(ZAttrProvisioning.MailSSLClientCertMode zmailMailSSLClientCertMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertMode, zmailMailSSLClientCertMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zmailMailSSLClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public Map<String,Object> setMailSSLClientCertMode(ZAttrProvisioning.MailSSLClientCertMode zmailMailSSLClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertMode, zmailMailSSLClientCertMode.toString());
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zmailMailSSLClientCertMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public void setMailSSLClientCertModeAsString(String zmailMailSSLClientCertMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertMode, zmailMailSSLClientCertMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @param zmailMailSSLClientCertMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public Map<String,Object> setMailSSLClientCertModeAsString(String zmailMailSSLClientCertMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertMode, zmailMailSSLClientCertMode);
        return attrs;
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
     * the client does not present a certificate to authenticate. In the case
     * when client does not send a certificate, user will be redirected to
     * the usual entry page of the requested webapp, where username/password
     * is prompted.
     *
     * <p>Valid values: [NeedClientAuth, WantClientAuth, Disabled]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1190)
    public void unsetMailSSLClientCertMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable authentication via X.509 Client Certificate. Disabled: client
     * authentication is disabled. NeedClientAuth: client authentication is
     * required during SSL handshake on the SSL mutual authentication
     * port(see zmailMailSSLClientCertPort). The SSL handshake will fail if
     * the client does not present a certificate to authenticate.
     * WantClientAuth: client authentication is requested during SSL
     * handshake on the SSL mutual authentication port(see
     * zmailMailSSLClientCertPort). The SSL handshake will still proceed if
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
        attrs.put(Provisioning.A_zmailMailSSLClientCertMode, "");
        return attrs;
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @return zmailMailSSLClientCertOCSPEnabled, or true if unset
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public boolean isMailSSLClientCertOCSPEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMailSSLClientCertOCSPEnabled, true);
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @param zmailMailSSLClientCertOCSPEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public void setMailSSLClientCertOCSPEnabled(boolean zmailMailSSLClientCertOCSPEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertOCSPEnabled, zmailMailSSLClientCertOCSPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @param zmailMailSSLClientCertOCSPEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public Map<String,Object> setMailSSLClientCertOCSPEnabled(boolean zmailMailSSLClientCertOCSPEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertOCSPEnabled, zmailMailSSLClientCertOCSPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * enable OCSP support for two way authentication.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1395)
    public void unsetMailSSLClientCertOCSPEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertOCSPEnabled, "");
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
        attrs.put(Provisioning.A_zmailMailSSLClientCertOCSPEnabled, "");
        return attrs;
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * <p>Use getMailSSLClientCertPortAsString to access value as a string.
     *
     * @see #getMailSSLClientCertPortAsString()
     *
     * @return zmailMailSSLClientCertPort, or 9443 if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public int getMailSSLClientCertPort() {
        return getIntAttr(Provisioning.A_zmailMailSSLClientCertPort, 9443);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @return zmailMailSSLClientCertPort, or "9443" if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public String getMailSSLClientCertPortAsString() {
        return getAttr(Provisioning.A_zmailMailSSLClientCertPort, "9443");
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zmailMailSSLClientCertPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public void setMailSSLClientCertPort(int zmailMailSSLClientCertPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPort, Integer.toString(zmailMailSSLClientCertPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zmailMailSSLClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public Map<String,Object> setMailSSLClientCertPort(int zmailMailSSLClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPort, Integer.toString(zmailMailSSLClientCertPort));
        return attrs;
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zmailMailSSLClientCertPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public void setMailSSLClientCertPortAsString(String zmailMailSSLClientCertPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPort, zmailMailSSLClientCertPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @param zmailMailSSLClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public Map<String,Object> setMailSSLClientCertPortAsString(String zmailMailSSLClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPort, zmailMailSSLClientCertPort);
        return attrs;
    }

    /**
     * SSL port requesting client certificate for end-user UI
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1199)
    public void unsetMailSSLClientCertPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLClientCertPort, "");
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
        attrs.put(Provisioning.A_zmailMailSSLClientCertPort, "");
        return attrs;
    }

    /**
     * SSL port for end-user UI
     *
     * <p>Use getMailSSLPortAsString to access value as a string.
     *
     * @see #getMailSSLPortAsString()
     *
     * @return zmailMailSSLPort, or 0 if unset
     */
    @ZAttr(id=166)
    public int getMailSSLPort() {
        return getIntAttr(Provisioning.A_zmailMailSSLPort, 0);
    }

    /**
     * SSL port for end-user UI
     *
     * @return zmailMailSSLPort, or "0" if unset
     */
    @ZAttr(id=166)
    public String getMailSSLPortAsString() {
        return getAttr(Provisioning.A_zmailMailSSLPort, "0");
    }

    /**
     * SSL port for end-user UI
     *
     * @param zmailMailSSLPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=166)
    public void setMailSSLPort(int zmailMailSSLPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLPort, Integer.toString(zmailMailSSLPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for end-user UI
     *
     * @param zmailMailSSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=166)
    public Map<String,Object> setMailSSLPort(int zmailMailSSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLPort, Integer.toString(zmailMailSSLPort));
        return attrs;
    }

    /**
     * SSL port for end-user UI
     *
     * @param zmailMailSSLPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=166)
    public void setMailSSLPortAsString(String zmailMailSSLPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLPort, zmailMailSSLPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port for end-user UI
     *
     * @param zmailMailSSLPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=166)
    public Map<String,Object> setMailSSLPortAsString(String zmailMailSSLPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLPort, zmailMailSSLPort);
        return attrs;
    }

    /**
     * SSL port for end-user UI
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=166)
    public void unsetMailSSLPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLPort, "");
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
        attrs.put(Provisioning.A_zmailMailSSLPort, "");
        return attrs;
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * <p>Use getMailSSLProxyClientCertPortAsString to access value as a string.
     *
     * @see #getMailSSLProxyClientCertPortAsString()
     *
     * @return zmailMailSSLProxyClientCertPort, or 3443 if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public int getMailSSLProxyClientCertPort() {
        return getIntAttr(Provisioning.A_zmailMailSSLProxyClientCertPort, 3443);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @return zmailMailSSLProxyClientCertPort, or "3443" if unset
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public String getMailSSLProxyClientCertPortAsString() {
        return getAttr(Provisioning.A_zmailMailSSLProxyClientCertPort, "3443");
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zmailMailSSLProxyClientCertPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public void setMailSSLProxyClientCertPort(int zmailMailSSLProxyClientCertPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyClientCertPort, Integer.toString(zmailMailSSLProxyClientCertPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zmailMailSSLProxyClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public Map<String,Object> setMailSSLProxyClientCertPort(int zmailMailSSLProxyClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyClientCertPort, Integer.toString(zmailMailSSLProxyClientCertPort));
        return attrs;
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zmailMailSSLProxyClientCertPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public void setMailSSLProxyClientCertPortAsString(String zmailMailSSLProxyClientCertPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyClientCertPort, zmailMailSSLProxyClientCertPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @param zmailMailSSLProxyClientCertPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public Map<String,Object> setMailSSLProxyClientCertPortAsString(String zmailMailSSLProxyClientCertPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyClientCertPort, zmailMailSSLProxyClientCertPort);
        return attrs;
    }

    /**
     * SSL client certificate port for HTTP proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.1
     */
    @ZAttr(id=1212)
    public void unsetMailSSLProxyClientCertPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyClientCertPort, "");
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
        attrs.put(Provisioning.A_zmailMailSSLProxyClientCertPort, "");
        return attrs;
    }

    /**
     * SSL port HTTP proxy
     *
     * <p>Use getMailSSLProxyPortAsString to access value as a string.
     *
     * @see #getMailSSLProxyPortAsString()
     *
     * @return zmailMailSSLProxyPort, or 0 if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public int getMailSSLProxyPort() {
        return getIntAttr(Provisioning.A_zmailMailSSLProxyPort, 0);
    }

    /**
     * SSL port HTTP proxy
     *
     * @return zmailMailSSLProxyPort, or "0" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public String getMailSSLProxyPortAsString() {
        return getAttr(Provisioning.A_zmailMailSSLProxyPort, "0");
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zmailMailSSLProxyPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public void setMailSSLProxyPort(int zmailMailSSLProxyPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyPort, Integer.toString(zmailMailSSLProxyPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zmailMailSSLProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public Map<String,Object> setMailSSLProxyPort(int zmailMailSSLProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyPort, Integer.toString(zmailMailSSLProxyPort));
        return attrs;
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zmailMailSSLProxyPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public void setMailSSLProxyPortAsString(String zmailMailSSLProxyPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyPort, zmailMailSSLProxyPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SSL port HTTP proxy
     *
     * @param zmailMailSSLProxyPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public Map<String,Object> setMailSSLProxyPortAsString(String zmailMailSSLProxyPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyPort, zmailMailSSLProxyPort);
        return attrs;
    }

    /**
     * SSL port HTTP proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=627)
    public void unsetMailSSLProxyPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailSSLProxyPort, "");
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
        attrs.put(Provisioning.A_zmailMailSSLProxyPort, "");
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
     * @return zmailMailTrustedIP, or empty array if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public String[] getMailTrustedIP() {
        return getMultiAttr(Provisioning.A_zmailMailTrustedIP);
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
     * @param zmailMailTrustedIP new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void setMailTrustedIP(String[] zmailMailTrustedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedIP, zmailMailTrustedIP);
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
     * @param zmailMailTrustedIP new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> setMailTrustedIP(String[] zmailMailTrustedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedIP, zmailMailTrustedIP);
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
     * @param zmailMailTrustedIP new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void addMailTrustedIP(String zmailMailTrustedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMailTrustedIP, zmailMailTrustedIP);
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
     * @param zmailMailTrustedIP new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> addMailTrustedIP(String zmailMailTrustedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMailTrustedIP, zmailMailTrustedIP);
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
     * @param zmailMailTrustedIP existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void removeMailTrustedIP(String zmailMailTrustedIP) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMailTrustedIP, zmailMailTrustedIP);
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
     * @param zmailMailTrustedIP existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public Map<String,Object> removeMailTrustedIP(String zmailMailTrustedIP, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMailTrustedIP, zmailMailTrustedIP);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1025)
    public void unsetMailTrustedIP() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailTrustedIP, "");
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
        attrs.put(Provisioning.A_zmailMailTrustedIP, "");
        return attrs;
    }

    /**
     * URL prefix for where the zmail app resides on this server
     *
     * @return zmailMailURL, or "/" if unset
     */
    @ZAttr(id=340)
    public String getMailURL() {
        return getAttr(Provisioning.A_zmailMailURL, "/");
    }

    /**
     * URL prefix for where the zmail app resides on this server
     *
     * @param zmailMailURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=340)
    public void setMailURL(String zmailMailURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailURL, zmailMailURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zmail app resides on this server
     *
     * @param zmailMailURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=340)
    public Map<String,Object> setMailURL(String zmailMailURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailURL, zmailMailURL);
        return attrs;
    }

    /**
     * URL prefix for where the zmail app resides on this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=340)
    public void unsetMailURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL prefix for where the zmail app resides on this server
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=340)
    public Map<String,Object> unsetMailURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailURL, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @return zmailMailUncompressedCacheMaxBytes, or 1073741824 if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public long getMailUncompressedCacheMaxBytes() {
        return getLongAttr(Provisioning.A_zmailMailUncompressedCacheMaxBytes, 1073741824L);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @param zmailMailUncompressedCacheMaxBytes new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public void setMailUncompressedCacheMaxBytes(long zmailMailUncompressedCacheMaxBytes) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxBytes, Long.toString(zmailMailUncompressedCacheMaxBytes));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @param zmailMailUncompressedCacheMaxBytes new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public Map<String,Object> setMailUncompressedCacheMaxBytes(long zmailMailUncompressedCacheMaxBytes, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxBytes, Long.toString(zmailMailUncompressedCacheMaxBytes));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of bytes
     * stored in the uncompressed blob cache on disk
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=825)
    public void unsetMailUncompressedCacheMaxBytes() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxBytes, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of bytes
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
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxBytes, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @return zmailMailUncompressedCacheMaxFiles, or 5000 if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public int getMailUncompressedCacheMaxFiles() {
        return getIntAttr(Provisioning.A_zmailMailUncompressedCacheMaxFiles, 5000);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @param zmailMailUncompressedCacheMaxFiles new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public void setMailUncompressedCacheMaxFiles(int zmailMailUncompressedCacheMaxFiles) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxFiles, Integer.toString(zmailMailUncompressedCacheMaxFiles));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @param zmailMailUncompressedCacheMaxFiles new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public Map<String,Object> setMailUncompressedCacheMaxFiles(int zmailMailUncompressedCacheMaxFiles, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxFiles, Integer.toString(zmailMailUncompressedCacheMaxFiles));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of files in
     * the uncompressed blob cache on disk
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=824)
    public void unsetMailUncompressedCacheMaxFiles() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxFiles, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.7. Deprecated per bug 43497. The number of
     * uncompressed files on disk will never exceed
     * zmailMailFileDescriptorCacheSize.. Orig desc: max number of files in
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
        attrs.put(Provisioning.A_zmailMailUncompressedCacheMaxFiles, "");
        return attrs;
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @return zmailMailUseDirectBuffers, or false if unset
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public boolean isMailUseDirectBuffers() {
        return getBooleanAttr(Provisioning.A_zmailMailUseDirectBuffers, false);
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @param zmailMailUseDirectBuffers new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public void setMailUseDirectBuffers(boolean zmailMailUseDirectBuffers) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUseDirectBuffers, zmailMailUseDirectBuffers ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @param zmailMailUseDirectBuffers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public Map<String,Object> setMailUseDirectBuffers(boolean zmailMailUseDirectBuffers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUseDirectBuffers, zmailMailUseDirectBuffers ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Used to control whether Java NIO direct buffers are used. Value is
     * propagated to Jetty configuration. In the future, other NIO pieces
     * (IMAP/POP/LMTP) will also honor this.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.22
     */
    @ZAttr(id=1002)
    public void unsetMailUseDirectBuffers() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailUseDirectBuffers, "");
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
        attrs.put(Provisioning.A_zmailMailUseDirectBuffers, "");
        return attrs;
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @return zmailMailboxMoveSkipBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public boolean isMailboxMoveSkipBlobs() {
        return getBooleanAttr(Provisioning.A_zmailMailboxMoveSkipBlobs, false);
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @param zmailMailboxMoveSkipBlobs new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public void setMailboxMoveSkipBlobs(boolean zmailMailboxMoveSkipBlobs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipBlobs, zmailMailboxMoveSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @param zmailMailboxMoveSkipBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public Map<String,Object> setMailboxMoveSkipBlobs(boolean zmailMailboxMoveSkipBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipBlobs, zmailMailboxMoveSkipBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, exclude blobs (HSM or not) from mailbox move
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1007)
    public void unsetMailboxMoveSkipBlobs() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipBlobs, "");
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
        attrs.put(Provisioning.A_zmailMailboxMoveSkipBlobs, "");
        return attrs;
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @return zmailMailboxMoveSkipHsmBlobs, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public boolean isMailboxMoveSkipHsmBlobs() {
        return getBooleanAttr(Provisioning.A_zmailMailboxMoveSkipHsmBlobs, false);
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @param zmailMailboxMoveSkipHsmBlobs new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public void setMailboxMoveSkipHsmBlobs(boolean zmailMailboxMoveSkipHsmBlobs) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipHsmBlobs, zmailMailboxMoveSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @param zmailMailboxMoveSkipHsmBlobs new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public Map<String,Object> setMailboxMoveSkipHsmBlobs(boolean zmailMailboxMoveSkipHsmBlobs, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipHsmBlobs, zmailMailboxMoveSkipHsmBlobs ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, exclude blobs on secondary (HSM) volumes from mailbox move
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1008)
    public void unsetMailboxMoveSkipHsmBlobs() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipHsmBlobs, "");
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
        attrs.put(Provisioning.A_zmailMailboxMoveSkipHsmBlobs, "");
        return attrs;
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @return zmailMailboxMoveSkipSearchIndex, or false if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public boolean isMailboxMoveSkipSearchIndex() {
        return getBooleanAttr(Provisioning.A_zmailMailboxMoveSkipSearchIndex, false);
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @param zmailMailboxMoveSkipSearchIndex new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public void setMailboxMoveSkipSearchIndex(boolean zmailMailboxMoveSkipSearchIndex) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipSearchIndex, zmailMailboxMoveSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @param zmailMailboxMoveSkipSearchIndex new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public Map<String,Object> setMailboxMoveSkipSearchIndex(boolean zmailMailboxMoveSkipSearchIndex, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipSearchIndex, zmailMailboxMoveSkipSearchIndex ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, exclude search index from mailbox move
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1006)
    public void unsetMailboxMoveSkipSearchIndex() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveSkipSearchIndex, "");
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
        attrs.put(Provisioning.A_zmailMailboxMoveSkipSearchIndex, "");
        return attrs;
    }

    /**
     * temp directory for mailbox move
     *
     * @return zmailMailboxMoveTempDir, or "/opt/zmail/backup/tmp/mboxmove" if unset
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public String getMailboxMoveTempDir() {
        return getAttr(Provisioning.A_zmailMailboxMoveTempDir, "/opt/zmail/backup/tmp/mboxmove");
    }

    /**
     * temp directory for mailbox move
     *
     * @param zmailMailboxMoveTempDir new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public void setMailboxMoveTempDir(String zmailMailboxMoveTempDir) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveTempDir, zmailMailboxMoveTempDir);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * temp directory for mailbox move
     *
     * @param zmailMailboxMoveTempDir new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public Map<String,Object> setMailboxMoveTempDir(String zmailMailboxMoveTempDir, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveTempDir, zmailMailboxMoveTempDir);
        return attrs;
    }

    /**
     * temp directory for mailbox move
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.1
     */
    @ZAttr(id=1175)
    public void unsetMailboxMoveTempDir() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMailboxMoveTempDir, "");
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
        attrs.put(Provisioning.A_zmailMailboxMoveTempDir, "");
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @return zmailMemcachedBindAddress, or empty array if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public String[] getMemcachedBindAddress() {
        return getMultiAttr(Provisioning.A_zmailMemcachedBindAddress);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zmailMemcachedBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void setMemcachedBindAddress(String[] zmailMemcachedBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindAddress, zmailMemcachedBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zmailMemcachedBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> setMemcachedBindAddress(String[] zmailMemcachedBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindAddress, zmailMemcachedBindAddress);
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zmailMemcachedBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void addMemcachedBindAddress(String zmailMemcachedBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMemcachedBindAddress, zmailMemcachedBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zmailMemcachedBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> addMemcachedBindAddress(String zmailMemcachedBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMemcachedBindAddress, zmailMemcachedBindAddress);
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zmailMemcachedBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void removeMemcachedBindAddress(String zmailMemcachedBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMemcachedBindAddress, zmailMemcachedBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which memcached server should listen
     *
     * @param zmailMemcachedBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public Map<String,Object> removeMemcachedBindAddress(String zmailMemcachedBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMemcachedBindAddress, zmailMemcachedBindAddress);
        return attrs;
    }

    /**
     * interface address on which memcached server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=581)
    public void unsetMemcachedBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindAddress, "");
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
        attrs.put(Provisioning.A_zmailMemcachedBindAddress, "");
        return attrs;
    }

    /**
     * port number on which memcached server should listen
     *
     * <p>Use getMemcachedBindPortAsString to access value as a string.
     *
     * @see #getMemcachedBindPortAsString()
     *
     * @return zmailMemcachedBindPort, or 11211 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public int getMemcachedBindPort() {
        return getIntAttr(Provisioning.A_zmailMemcachedBindPort, 11211);
    }

    /**
     * port number on which memcached server should listen
     *
     * @return zmailMemcachedBindPort, or "11211" if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public String getMemcachedBindPortAsString() {
        return getAttr(Provisioning.A_zmailMemcachedBindPort, "11211");
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zmailMemcachedBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public void setMemcachedBindPort(int zmailMemcachedBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindPort, Integer.toString(zmailMemcachedBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zmailMemcachedBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public Map<String,Object> setMemcachedBindPort(int zmailMemcachedBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindPort, Integer.toString(zmailMemcachedBindPort));
        return attrs;
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zmailMemcachedBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public void setMemcachedBindPortAsString(String zmailMemcachedBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindPort, zmailMemcachedBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which memcached server should listen
     *
     * @param zmailMemcachedBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public Map<String,Object> setMemcachedBindPortAsString(String zmailMemcachedBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindPort, zmailMemcachedBindPort);
        return attrs;
    }

    /**
     * port number on which memcached server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=580)
    public void unsetMemcachedBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedBindPort, "");
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
        attrs.put(Provisioning.A_zmailMemcachedBindPort, "");
        return attrs;
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @return zmailMemcachedClientBinaryProtocolEnabled, or false if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public boolean isMemcachedClientBinaryProtocolEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMemcachedClientBinaryProtocolEnabled, false);
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @param zmailMemcachedClientBinaryProtocolEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public void setMemcachedClientBinaryProtocolEnabled(boolean zmailMemcachedClientBinaryProtocolEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientBinaryProtocolEnabled, zmailMemcachedClientBinaryProtocolEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @param zmailMemcachedClientBinaryProtocolEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public Map<String,Object> setMemcachedClientBinaryProtocolEnabled(boolean zmailMemcachedClientBinaryProtocolEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientBinaryProtocolEnabled, zmailMemcachedClientBinaryProtocolEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * if true, use binary protocol of memcached; if false, use ascii
     * protocol
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1015)
    public void unsetMemcachedClientBinaryProtocolEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientBinaryProtocolEnabled, "");
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
        attrs.put(Provisioning.A_zmailMemcachedClientBinaryProtocolEnabled, "");
        return attrs;
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @return zmailMemcachedClientExpirySeconds, or 86400 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public int getMemcachedClientExpirySeconds() {
        return getIntAttr(Provisioning.A_zmailMemcachedClientExpirySeconds, 86400);
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @param zmailMemcachedClientExpirySeconds new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public void setMemcachedClientExpirySeconds(int zmailMemcachedClientExpirySeconds) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientExpirySeconds, Integer.toString(zmailMemcachedClientExpirySeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @param zmailMemcachedClientExpirySeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public Map<String,Object> setMemcachedClientExpirySeconds(int zmailMemcachedClientExpirySeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientExpirySeconds, Integer.toString(zmailMemcachedClientExpirySeconds));
        return attrs;
    }

    /**
     * default expiration time in seconds for memcached values; default is 1
     * day
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1017)
    public void unsetMemcachedClientExpirySeconds() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientExpirySeconds, "");
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
        attrs.put(Provisioning.A_zmailMemcachedClientExpirySeconds, "");
        return attrs;
    }

    /**
     * memcached hash algorithm
     *
     * @return zmailMemcachedClientHashAlgorithm, or "KETAMA_HASH" if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public String getMemcachedClientHashAlgorithm() {
        return getAttr(Provisioning.A_zmailMemcachedClientHashAlgorithm, "KETAMA_HASH");
    }

    /**
     * memcached hash algorithm
     *
     * @param zmailMemcachedClientHashAlgorithm new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public void setMemcachedClientHashAlgorithm(String zmailMemcachedClientHashAlgorithm) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientHashAlgorithm, zmailMemcachedClientHashAlgorithm);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * memcached hash algorithm
     *
     * @param zmailMemcachedClientHashAlgorithm new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public Map<String,Object> setMemcachedClientHashAlgorithm(String zmailMemcachedClientHashAlgorithm, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientHashAlgorithm, zmailMemcachedClientHashAlgorithm);
        return attrs;
    }

    /**
     * memcached hash algorithm
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1016)
    public void unsetMemcachedClientHashAlgorithm() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientHashAlgorithm, "");
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
        attrs.put(Provisioning.A_zmailMemcachedClientHashAlgorithm, "");
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @return zmailMemcachedClientServerList, or empty array if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public String[] getMemcachedClientServerList() {
        return getMultiAttr(Provisioning.A_zmailMemcachedClientServerList);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zmailMemcachedClientServerList new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void setMemcachedClientServerList(String[] zmailMemcachedClientServerList) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientServerList, zmailMemcachedClientServerList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zmailMemcachedClientServerList new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> setMemcachedClientServerList(String[] zmailMemcachedClientServerList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientServerList, zmailMemcachedClientServerList);
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zmailMemcachedClientServerList new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void addMemcachedClientServerList(String zmailMemcachedClientServerList) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMemcachedClientServerList, zmailMemcachedClientServerList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zmailMemcachedClientServerList new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> addMemcachedClientServerList(String zmailMemcachedClientServerList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMemcachedClientServerList, zmailMemcachedClientServerList);
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zmailMemcachedClientServerList existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void removeMemcachedClientServerList(String zmailMemcachedClientServerList) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMemcachedClientServerList, zmailMemcachedClientServerList);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @param zmailMemcachedClientServerList existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public Map<String,Object> removeMemcachedClientServerList(String zmailMemcachedClientServerList, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMemcachedClientServerList, zmailMemcachedClientServerList);
        return attrs;
    }

    /**
     * list of host:port for memcached servers; set to empty value to disable
     * the use of memcached
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1014)
    public void unsetMemcachedClientServerList() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientServerList, "");
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
        attrs.put(Provisioning.A_zmailMemcachedClientServerList, "");
        return attrs;
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @return zmailMemcachedClientTimeoutMillis, or 10000 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public int getMemcachedClientTimeoutMillis() {
        return getIntAttr(Provisioning.A_zmailMemcachedClientTimeoutMillis, 10000);
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @param zmailMemcachedClientTimeoutMillis new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public void setMemcachedClientTimeoutMillis(int zmailMemcachedClientTimeoutMillis) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientTimeoutMillis, Integer.toString(zmailMemcachedClientTimeoutMillis));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @param zmailMemcachedClientTimeoutMillis new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public Map<String,Object> setMemcachedClientTimeoutMillis(int zmailMemcachedClientTimeoutMillis, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientTimeoutMillis, Integer.toString(zmailMemcachedClientTimeoutMillis));
        return attrs;
    }

    /**
     * default timeout in milliseconds for async memcached operations
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1018)
    public void unsetMemcachedClientTimeoutMillis() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMemcachedClientTimeoutMillis, "");
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
        attrs.put(Provisioning.A_zmailMemcachedClientTimeoutMillis, "");
        return attrs;
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @return zmailMessageCacheSize, or 2000 if unset
     */
    @ZAttr(id=297)
    public int getMessageCacheSize() {
        return getIntAttr(Provisioning.A_zmailMessageCacheSize, 2000);
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @param zmailMessageCacheSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=297)
    public void setMessageCacheSize(int zmailMessageCacheSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageCacheSize, Integer.toString(zmailMessageCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @param zmailMessageCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=297)
    public Map<String,Object> setMessageCacheSize(int zmailMessageCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageCacheSize, Integer.toString(zmailMessageCacheSize));
        return attrs;
    }

    /**
     * Maximum number of JavaMail MimeMessage objects in the message cache.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=297)
    public void unsetMessageCacheSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageCacheSize, "");
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
        attrs.put(Provisioning.A_zmailMessageCacheSize, "");
        return attrs;
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @return zmailMessageChannelEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public boolean isMessageChannelEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMessageChannelEnabled, false);
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @param zmailMessageChannelEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public void setMessageChannelEnabled(boolean zmailMessageChannelEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageChannelEnabled, zmailMessageChannelEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @param zmailMessageChannelEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public Map<String,Object> setMessageChannelEnabled(boolean zmailMessageChannelEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageChannelEnabled, zmailMessageChannelEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether message channel service is enabled on this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1417)
    public void unsetMessageChannelEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageChannelEnabled, "");
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
        attrs.put(Provisioning.A_zmailMessageChannelEnabled, "");
        return attrs;
    }

    /**
     * port number on which message channel should listen
     *
     * @return zmailMessageChannelPort, or 7285 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public int getMessageChannelPort() {
        return getIntAttr(Provisioning.A_zmailMessageChannelPort, 7285);
    }

    /**
     * port number on which message channel should listen
     *
     * @param zmailMessageChannelPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public void setMessageChannelPort(int zmailMessageChannelPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageChannelPort, Integer.toString(zmailMessageChannelPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which message channel should listen
     *
     * @param zmailMessageChannelPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public Map<String,Object> setMessageChannelPort(int zmailMessageChannelPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageChannelPort, Integer.toString(zmailMessageChannelPort));
        return attrs;
    }

    /**
     * port number on which message channel should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1415)
    public void unsetMessageChannelPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMessageChannelPort, "");
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
        attrs.put(Provisioning.A_zmailMessageChannelPort, "");
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @return zmailMilterBindAddress, or empty array if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public String[] getMilterBindAddress() {
        return getMultiAttr(Provisioning.A_zmailMilterBindAddress);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zmailMilterBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void setMilterBindAddress(String[] zmailMilterBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindAddress, zmailMilterBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zmailMilterBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> setMilterBindAddress(String[] zmailMilterBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindAddress, zmailMilterBindAddress);
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zmailMilterBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void addMilterBindAddress(String zmailMilterBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMilterBindAddress, zmailMilterBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zmailMilterBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> addMilterBindAddress(String zmailMilterBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMilterBindAddress, zmailMilterBindAddress);
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zmailMilterBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void removeMilterBindAddress(String zmailMilterBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMilterBindAddress, zmailMilterBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @param zmailMilterBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public Map<String,Object> removeMilterBindAddress(String zmailMilterBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMilterBindAddress, zmailMilterBindAddress);
        return attrs;
    }

    /**
     * interface address on which milter server should listen; if not
     * specified, binds to 127.0.0.1
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1115)
    public void unsetMilterBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindAddress, "");
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
        attrs.put(Provisioning.A_zmailMilterBindAddress, "");
        return attrs;
    }

    /**
     * port number on which milter server should listen
     *
     * <p>Use getMilterBindPortAsString to access value as a string.
     *
     * @see #getMilterBindPortAsString()
     *
     * @return zmailMilterBindPort, or 7026 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public int getMilterBindPort() {
        return getIntAttr(Provisioning.A_zmailMilterBindPort, 7026);
    }

    /**
     * port number on which milter server should listen
     *
     * @return zmailMilterBindPort, or "7026" if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public String getMilterBindPortAsString() {
        return getAttr(Provisioning.A_zmailMilterBindPort, "7026");
    }

    /**
     * port number on which milter server should listen
     *
     * @param zmailMilterBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public void setMilterBindPort(int zmailMilterBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindPort, Integer.toString(zmailMilterBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which milter server should listen
     *
     * @param zmailMilterBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public Map<String,Object> setMilterBindPort(int zmailMilterBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindPort, Integer.toString(zmailMilterBindPort));
        return attrs;
    }

    /**
     * port number on which milter server should listen
     *
     * @param zmailMilterBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public void setMilterBindPortAsString(String zmailMilterBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindPort, zmailMilterBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which milter server should listen
     *
     * @param zmailMilterBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public Map<String,Object> setMilterBindPortAsString(String zmailMilterBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindPort, zmailMilterBindPort);
        return attrs;
    }

    /**
     * port number on which milter server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1114)
    public void unsetMilterBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterBindPort, "");
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
        attrs.put(Provisioning.A_zmailMilterBindPort, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @return zmailMilterMaxConnections, or 20000 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public int getMilterMaxConnections() {
        return getIntAttr(Provisioning.A_zmailMilterMaxConnections, 20000);
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @param zmailMilterMaxConnections new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public void setMilterMaxConnections(int zmailMilterMaxConnections) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterMaxConnections, Integer.toString(zmailMilterMaxConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @param zmailMilterMaxConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public Map<String,Object> setMilterMaxConnections(int zmailMilterMaxConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterMaxConnections, Integer.toString(zmailMilterMaxConnections));
        return attrs;
    }

    /**
     * Maximum number of concurrent MILTER connections allowed. New
     * connections exceeding this limit are rejected.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1157)
    public void unsetMilterMaxConnections() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterMaxConnections, "");
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
        attrs.put(Provisioning.A_zmailMilterMaxConnections, "");
        return attrs;
    }

    /**
     * number of milter handler threads
     *
     * @return zmailMilterNumThreads, or 100 if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public int getMilterNumThreads() {
        return getIntAttr(Provisioning.A_zmailMilterNumThreads, 100);
    }

    /**
     * number of milter handler threads
     *
     * @param zmailMilterNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public void setMilterNumThreads(int zmailMilterNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterNumThreads, Integer.toString(zmailMilterNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of milter handler threads
     *
     * @param zmailMilterNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public Map<String,Object> setMilterNumThreads(int zmailMilterNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterNumThreads, Integer.toString(zmailMilterNumThreads));
        return attrs;
    }

    /**
     * number of milter handler threads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1144)
    public void unsetMilterNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterNumThreads, "");
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
        attrs.put(Provisioning.A_zmailMilterNumThreads, "");
        return attrs;
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @return zmailMilterServerEnabled, or false if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public boolean isMilterServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMilterServerEnabled, false);
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @param zmailMilterServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public void setMilterServerEnabled(boolean zmailMilterServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterServerEnabled, zmailMilterServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @param zmailMilterServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public Map<String,Object> setMilterServerEnabled(boolean zmailMilterServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterServerEnabled, zmailMilterServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether milter server is enabled for a given server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1116)
    public void unsetMilterServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMilterServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailMilterServerEnabled, "");
        return attrs;
    }

    /**
     * mta anti spam lock method.
     *
     * @return zmailMtaAntiSpamLockMethod, or "flock" if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public String getMtaAntiSpamLockMethod() {
        return getAttr(Provisioning.A_zmailMtaAntiSpamLockMethod, "flock");
    }

    /**
     * mta anti spam lock method.
     *
     * @param zmailMtaAntiSpamLockMethod new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public void setMtaAntiSpamLockMethod(String zmailMtaAntiSpamLockMethod) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAntiSpamLockMethod, zmailMtaAntiSpamLockMethod);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * mta anti spam lock method.
     *
     * @param zmailMtaAntiSpamLockMethod new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public Map<String,Object> setMtaAntiSpamLockMethod(String zmailMtaAntiSpamLockMethod, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAntiSpamLockMethod, zmailMtaAntiSpamLockMethod);
        return attrs;
    }

    /**
     * mta anti spam lock method.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=612)
    public void unsetMtaAntiSpamLockMethod() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAntiSpamLockMethod, "");
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
        attrs.put(Provisioning.A_zmailMtaAntiSpamLockMethod, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zmailMtaTlsSecurityLevel and zmailMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @return zmailMtaAuthEnabled, or true if unset
     */
    @ZAttr(id=194)
    public boolean isMtaAuthEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMtaAuthEnabled, true);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zmailMtaTlsSecurityLevel and zmailMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @param zmailMtaAuthEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=194)
    public void setMtaAuthEnabled(boolean zmailMtaAuthEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthEnabled, zmailMtaAuthEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zmailMtaTlsSecurityLevel and zmailMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @param zmailMtaAuthEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=194)
    public Map<String,Object> setMtaAuthEnabled(boolean zmailMtaAuthEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthEnabled, zmailMtaAuthEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zmailMtaTlsSecurityLevel and zmailMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=194)
    public void unsetMtaAuthEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated in favor of
     * zmailMtaTlsSecurityLevel and zmailMtaSaslAuthEnable. Orig desc:
     * Value for postconf smtpd_tls_security_level
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=194)
    public Map<String,Object> unsetMtaAuthEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthEnabled, "");
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @return zmailMtaAuthHost, or empty array if unset
     */
    @ZAttr(id=309)
    public String[] getMtaAuthHost() {
        return getMultiAttr(Provisioning.A_zmailMtaAuthHost);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param zmailMtaAuthHost new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void setMtaAuthHost(String[] zmailMtaAuthHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthHost, zmailMtaAuthHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param zmailMtaAuthHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> setMtaAuthHost(String[] zmailMtaAuthHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthHost, zmailMtaAuthHost);
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param zmailMtaAuthHost new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void addMtaAuthHost(String zmailMtaAuthHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaAuthHost, zmailMtaAuthHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param zmailMtaAuthHost new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> addMtaAuthHost(String zmailMtaAuthHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaAuthHost, zmailMtaAuthHost);
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param zmailMtaAuthHost existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void removeMtaAuthHost(String zmailMtaAuthHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaAuthHost, zmailMtaAuthHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param zmailMtaAuthHost existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> removeMtaAuthHost(String zmailMtaAuthHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaAuthHost, zmailMtaAuthHost);
        return attrs;
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=309)
    public void unsetMtaAuthHost() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthHost, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Host running SOAP service for use by MTA auth. Setting this sets
     * zmailMtaAuthURL via attr callback mechanism.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=309)
    public Map<String,Object> unsetMtaAuthHost(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthHost, "");
        return attrs;
    }

    /**
     * whether this server is a mta auth target
     *
     * @return zmailMtaAuthTarget, or false if unset
     */
    @ZAttr(id=505)
    public boolean isMtaAuthTarget() {
        return getBooleanAttr(Provisioning.A_zmailMtaAuthTarget, false);
    }

    /**
     * whether this server is a mta auth target
     *
     * @param zmailMtaAuthTarget new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=505)
    public void setMtaAuthTarget(boolean zmailMtaAuthTarget) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthTarget, zmailMtaAuthTarget ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether this server is a mta auth target
     *
     * @param zmailMtaAuthTarget new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=505)
    public Map<String,Object> setMtaAuthTarget(boolean zmailMtaAuthTarget, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthTarget, zmailMtaAuthTarget ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether this server is a mta auth target
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=505)
    public void unsetMtaAuthTarget() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthTarget, "");
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
        attrs.put(Provisioning.A_zmailMtaAuthTarget, "");
        return attrs;
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @return zmailMtaAuthURL, or empty array if unset
     */
    @ZAttr(id=310)
    public String[] getMtaAuthURL() {
        return getMultiAttr(Provisioning.A_zmailMtaAuthURL);
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param zmailMtaAuthURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void setMtaAuthURL(String[] zmailMtaAuthURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthURL, zmailMtaAuthURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param zmailMtaAuthURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> setMtaAuthURL(String[] zmailMtaAuthURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthURL, zmailMtaAuthURL);
        return attrs;
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param zmailMtaAuthURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void addMtaAuthURL(String zmailMtaAuthURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaAuthURL, zmailMtaAuthURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param zmailMtaAuthURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> addMtaAuthURL(String zmailMtaAuthURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaAuthURL, zmailMtaAuthURL);
        return attrs;
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param zmailMtaAuthURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void removeMtaAuthURL(String zmailMtaAuthURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaAuthURL, zmailMtaAuthURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param zmailMtaAuthURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> removeMtaAuthURL(String zmailMtaAuthURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaAuthURL, zmailMtaAuthURL);
        return attrs;
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=310)
    public void unsetMtaAuthURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL at which this MTA (via zmail saslauthd) should authenticate. Set
     * by setting zmailMtaAuthHost.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=310)
    public Map<String,Object> unsetMtaAuthURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaAuthURL, "");
        return attrs;
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @return zmailMtaDnsLookupsEnabled, or true if unset
     */
    @ZAttr(id=197)
    public boolean isMtaDnsLookupsEnabled() {
        return getBooleanAttr(Provisioning.A_zmailMtaDnsLookupsEnabled, true);
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @param zmailMtaDnsLookupsEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=197)
    public void setMtaDnsLookupsEnabled(boolean zmailMtaDnsLookupsEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaDnsLookupsEnabled, zmailMtaDnsLookupsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @param zmailMtaDnsLookupsEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=197)
    public Map<String,Object> setMtaDnsLookupsEnabled(boolean zmailMtaDnsLookupsEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaDnsLookupsEnabled, zmailMtaDnsLookupsEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Value for postconf disable_dns_lookups (note enable v. disable)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=197)
    public void unsetMtaDnsLookupsEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaDnsLookupsEnabled, "");
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
        attrs.put(Provisioning.A_zmailMtaDnsLookupsEnabled, "");
        return attrs;
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @return zmailMtaFallbackRelayHost, or null if unset
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public String getMtaFallbackRelayHost() {
        return getAttr(Provisioning.A_zmailMtaFallbackRelayHost, null);
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @param zmailMtaFallbackRelayHost new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public void setMtaFallbackRelayHost(String zmailMtaFallbackRelayHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaFallbackRelayHost, zmailMtaFallbackRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @param zmailMtaFallbackRelayHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public Map<String,Object> setMtaFallbackRelayHost(String zmailMtaFallbackRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaFallbackRelayHost, zmailMtaFallbackRelayHost);
        return attrs;
    }

    /**
     * Fallback value for postconf relayhost.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1435)
    public void unsetMtaFallbackRelayHost() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaFallbackRelayHost, "");
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
        attrs.put(Provisioning.A_zmailMtaFallbackRelayHost, "");
        return attrs;
    }

    /**
     * value of postfix mydestination
     *
     * @return zmailMtaMyDestination, or "localhost" if unset
     */
    @ZAttr(id=524)
    public String getMtaMyDestination() {
        return getAttr(Provisioning.A_zmailMtaMyDestination, "localhost");
    }

    /**
     * value of postfix mydestination
     *
     * @param zmailMtaMyDestination new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=524)
    public void setMtaMyDestination(String zmailMtaMyDestination) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyDestination, zmailMtaMyDestination);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mydestination
     *
     * @param zmailMtaMyDestination new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=524)
    public Map<String,Object> setMtaMyDestination(String zmailMtaMyDestination, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyDestination, zmailMtaMyDestination);
        return attrs;
    }

    /**
     * value of postfix mydestination
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=524)
    public void unsetMtaMyDestination() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyDestination, "");
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
        attrs.put(Provisioning.A_zmailMtaMyDestination, "");
        return attrs;
    }

    /**
     * value of postfix myhostname
     *
     * @return zmailMtaMyHostname, or null if unset
     */
    @ZAttr(id=509)
    public String getMtaMyHostname() {
        return getAttr(Provisioning.A_zmailMtaMyHostname, null);
    }

    /**
     * value of postfix myhostname
     *
     * @param zmailMtaMyHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=509)
    public void setMtaMyHostname(String zmailMtaMyHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyHostname, zmailMtaMyHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix myhostname
     *
     * @param zmailMtaMyHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=509)
    public Map<String,Object> setMtaMyHostname(String zmailMtaMyHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyHostname, zmailMtaMyHostname);
        return attrs;
    }

    /**
     * value of postfix myhostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=509)
    public void unsetMtaMyHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyHostname, "");
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
        attrs.put(Provisioning.A_zmailMtaMyHostname, "");
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @return zmailMtaMyNetworks, or empty array if unset
     */
    @ZAttr(id=311)
    public String[] getMtaMyNetworks() {
        return getMultiAttr(Provisioning.A_zmailMtaMyNetworks);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zmailMtaMyNetworks new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void setMtaMyNetworks(String[] zmailMtaMyNetworks) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyNetworks, zmailMtaMyNetworks);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zmailMtaMyNetworks new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> setMtaMyNetworks(String[] zmailMtaMyNetworks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyNetworks, zmailMtaMyNetworks);
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @param zmailMtaMyNetworks new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void addMtaMyNetworks(String zmailMtaMyNetworks) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaMyNetworks, zmailMtaMyNetworks);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zmailMtaMyNetworks new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> addMtaMyNetworks(String zmailMtaMyNetworks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaMyNetworks, zmailMtaMyNetworks);
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @param zmailMtaMyNetworks existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void removeMtaMyNetworks(String zmailMtaMyNetworks) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaMyNetworks, zmailMtaMyNetworks);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix mynetworks
     *
     * @param zmailMtaMyNetworks existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=311)
    public Map<String,Object> removeMtaMyNetworks(String zmailMtaMyNetworks, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaMyNetworks, zmailMtaMyNetworks);
        return attrs;
    }

    /**
     * value of postfix mynetworks
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=311)
    public void unsetMtaMyNetworks() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyNetworks, "");
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
        attrs.put(Provisioning.A_zmailMtaMyNetworks, "");
        return attrs;
    }

    /**
     * value of postfix myorigin
     *
     * @return zmailMtaMyOrigin, or null if unset
     */
    @ZAttr(id=510)
    public String getMtaMyOrigin() {
        return getAttr(Provisioning.A_zmailMtaMyOrigin, null);
    }

    /**
     * value of postfix myorigin
     *
     * @param zmailMtaMyOrigin new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=510)
    public void setMtaMyOrigin(String zmailMtaMyOrigin) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyOrigin, zmailMtaMyOrigin);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value of postfix myorigin
     *
     * @param zmailMtaMyOrigin new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=510)
    public Map<String,Object> setMtaMyOrigin(String zmailMtaMyOrigin, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyOrigin, zmailMtaMyOrigin);
        return attrs;
    }

    /**
     * value of postfix myorigin
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=510)
    public void unsetMtaMyOrigin() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaMyOrigin, "");
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
        attrs.put(Provisioning.A_zmailMtaMyOrigin, "");
        return attrs;
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @return zmailMtaNonSmtpdMilters, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public String getMtaNonSmtpdMilters() {
        return getAttr(Provisioning.A_zmailMtaNonSmtpdMilters, null);
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @param zmailMtaNonSmtpdMilters new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public void setMtaNonSmtpdMilters(String zmailMtaNonSmtpdMilters) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaNonSmtpdMilters, zmailMtaNonSmtpdMilters);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @param zmailMtaNonSmtpdMilters new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public Map<String,Object> setMtaNonSmtpdMilters(String zmailMtaNonSmtpdMilters, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaNonSmtpdMilters, zmailMtaNonSmtpdMilters);
        return attrs;
    }

    /**
     * value for postfix non_smtpd_milters
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=673)
    public void unsetMtaNonSmtpdMilters() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaNonSmtpdMilters, "");
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
        attrs.put(Provisioning.A_zmailMtaNonSmtpdMilters, "");
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @return zmailMtaRelayHost, or empty array if unset
     */
    @ZAttr(id=199)
    public String[] getMtaRelayHost() {
        return getMultiAttr(Provisioning.A_zmailMtaRelayHost);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zmailMtaRelayHost new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void setMtaRelayHost(String[] zmailMtaRelayHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaRelayHost, zmailMtaRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zmailMtaRelayHost new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> setMtaRelayHost(String[] zmailMtaRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaRelayHost, zmailMtaRelayHost);
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zmailMtaRelayHost new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void addMtaRelayHost(String zmailMtaRelayHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaRelayHost, zmailMtaRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zmailMtaRelayHost new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> addMtaRelayHost(String zmailMtaRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailMtaRelayHost, zmailMtaRelayHost);
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zmailMtaRelayHost existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void removeMtaRelayHost(String zmailMtaRelayHost) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaRelayHost, zmailMtaRelayHost);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @param zmailMtaRelayHost existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=199)
    public Map<String,Object> removeMtaRelayHost(String zmailMtaRelayHost, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailMtaRelayHost, zmailMtaRelayHost);
        return attrs;
    }

    /**
     * Value for postconf relayhost. Note: there can be only one value on
     * this attribute, see bug 50697.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=199)
    public void unsetMtaRelayHost() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaRelayHost, "");
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
        attrs.put(Provisioning.A_zmailMtaRelayHost, "");
        return attrs;
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @return zmailMtaSaslAuthEnable, or ZAttrProvisioning.MtaSaslAuthEnable.yes if unset and/or has invalid value
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public ZAttrProvisioning.MtaSaslAuthEnable getMtaSaslAuthEnable() {
        try { String v = getAttr(Provisioning.A_zmailMtaSaslAuthEnable); return v == null ? ZAttrProvisioning.MtaSaslAuthEnable.yes : ZAttrProvisioning.MtaSaslAuthEnable.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.MtaSaslAuthEnable.yes; }
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @return zmailMtaSaslAuthEnable, or "yes" if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public String getMtaSaslAuthEnableAsString() {
        return getAttr(Provisioning.A_zmailMtaSaslAuthEnable, "yes");
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zmailMtaSaslAuthEnable new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public void setMtaSaslAuthEnable(ZAttrProvisioning.MtaSaslAuthEnable zmailMtaSaslAuthEnable) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSaslAuthEnable, zmailMtaSaslAuthEnable.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zmailMtaSaslAuthEnable new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public Map<String,Object> setMtaSaslAuthEnable(ZAttrProvisioning.MtaSaslAuthEnable zmailMtaSaslAuthEnable, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSaslAuthEnable, zmailMtaSaslAuthEnable.toString());
        return attrs;
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zmailMtaSaslAuthEnable new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public void setMtaSaslAuthEnableAsString(String zmailMtaSaslAuthEnable) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSaslAuthEnable, zmailMtaSaslAuthEnable);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @param zmailMtaSaslAuthEnable new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public Map<String,Object> setMtaSaslAuthEnableAsString(String zmailMtaSaslAuthEnable, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSaslAuthEnable, zmailMtaSaslAuthEnable);
        return attrs;
    }

    /**
     * Value for postconf smtpd_sasl_auth_enable
     *
     * <p>Valid values: [yes, no]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=796)
    public void unsetMtaSaslAuthEnable() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSaslAuthEnable, "");
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
        attrs.put(Provisioning.A_zmailMtaSaslAuthEnable, "");
        return attrs;
    }

    /**
     * value for postfix smtpd_milters
     *
     * @return zmailMtaSmtpdMilters, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public String getMtaSmtpdMilters() {
        return getAttr(Provisioning.A_zmailMtaSmtpdMilters, null);
    }

    /**
     * value for postfix smtpd_milters
     *
     * @param zmailMtaSmtpdMilters new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public void setMtaSmtpdMilters(String zmailMtaSmtpdMilters) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSmtpdMilters, zmailMtaSmtpdMilters);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * value for postfix smtpd_milters
     *
     * @param zmailMtaSmtpdMilters new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public Map<String,Object> setMtaSmtpdMilters(String zmailMtaSmtpdMilters, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSmtpdMilters, zmailMtaSmtpdMilters);
        return attrs;
    }

    /**
     * value for postfix smtpd_milters
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=672)
    public void unsetMtaSmtpdMilters() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaSmtpdMilters, "");
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
        attrs.put(Provisioning.A_zmailMtaSmtpdMilters, "");
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @return zmailMtaTlsAuthOnly, or true if unset
     */
    @ZAttr(id=200)
    public boolean isMtaTlsAuthOnly() {
        return getBooleanAttr(Provisioning.A_zmailMtaTlsAuthOnly, true);
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @param zmailMtaTlsAuthOnly new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=200)
    public void setMtaTlsAuthOnly(boolean zmailMtaTlsAuthOnly) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsAuthOnly, zmailMtaTlsAuthOnly ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @param zmailMtaTlsAuthOnly new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=200)
    public Map<String,Object> setMtaTlsAuthOnly(boolean zmailMtaTlsAuthOnly, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsAuthOnly, zmailMtaTlsAuthOnly ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_auth_only
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=200)
    public void unsetMtaTlsAuthOnly() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsAuthOnly, "");
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
        attrs.put(Provisioning.A_zmailMtaTlsAuthOnly, "");
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @return zmailMtaTlsSecurityLevel, or ZAttrProvisioning.MtaTlsSecurityLevel.may if unset and/or has invalid value
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public ZAttrProvisioning.MtaTlsSecurityLevel getMtaTlsSecurityLevel() {
        try { String v = getAttr(Provisioning.A_zmailMtaTlsSecurityLevel); return v == null ? ZAttrProvisioning.MtaTlsSecurityLevel.may : ZAttrProvisioning.MtaTlsSecurityLevel.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.MtaTlsSecurityLevel.may; }
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @return zmailMtaTlsSecurityLevel, or "may" if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public String getMtaTlsSecurityLevelAsString() {
        return getAttr(Provisioning.A_zmailMtaTlsSecurityLevel, "may");
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zmailMtaTlsSecurityLevel new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public void setMtaTlsSecurityLevel(ZAttrProvisioning.MtaTlsSecurityLevel zmailMtaTlsSecurityLevel) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsSecurityLevel, zmailMtaTlsSecurityLevel.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zmailMtaTlsSecurityLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public Map<String,Object> setMtaTlsSecurityLevel(ZAttrProvisioning.MtaTlsSecurityLevel zmailMtaTlsSecurityLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsSecurityLevel, zmailMtaTlsSecurityLevel.toString());
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zmailMtaTlsSecurityLevel new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public void setMtaTlsSecurityLevelAsString(String zmailMtaTlsSecurityLevel) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsSecurityLevel, zmailMtaTlsSecurityLevel);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @param zmailMtaTlsSecurityLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public Map<String,Object> setMtaTlsSecurityLevelAsString(String zmailMtaTlsSecurityLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsSecurityLevel, zmailMtaTlsSecurityLevel);
        return attrs;
    }

    /**
     * Value for postconf smtpd_tls_security_level
     *
     * <p>Valid values: [none, may]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=795)
    public void unsetMtaTlsSecurityLevel() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailMtaTlsSecurityLevel, "");
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
        attrs.put(Provisioning.A_zmailMtaTlsSecurityLevel, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @return zmailNotebookFolderCacheSize, or 1024 if unset
     */
    @ZAttr(id=370)
    public int getNotebookFolderCacheSize() {
        return getIntAttr(Provisioning.A_zmailNotebookFolderCacheSize, 1024);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @param zmailNotebookFolderCacheSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=370)
    public void setNotebookFolderCacheSize(int zmailNotebookFolderCacheSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookFolderCacheSize, Integer.toString(zmailNotebookFolderCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @param zmailNotebookFolderCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=370)
    public Map<String,Object> setNotebookFolderCacheSize(int zmailNotebookFolderCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookFolderCacheSize, Integer.toString(zmailNotebookFolderCacheSize));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The size of Wiki
     * / Notebook folder cache on the server.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=370)
    public void unsetNotebookFolderCacheSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookFolderCacheSize, "");
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
        attrs.put(Provisioning.A_zmailNotebookFolderCacheSize, "");
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @return zmailNotebookMaxCachedTemplatesPerFolder, or 256 if unset
     */
    @ZAttr(id=371)
    public int getNotebookMaxCachedTemplatesPerFolder() {
        return getIntAttr(Provisioning.A_zmailNotebookMaxCachedTemplatesPerFolder, 256);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @param zmailNotebookMaxCachedTemplatesPerFolder new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=371)
    public void setNotebookMaxCachedTemplatesPerFolder(int zmailNotebookMaxCachedTemplatesPerFolder) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookMaxCachedTemplatesPerFolder, Integer.toString(zmailNotebookMaxCachedTemplatesPerFolder));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @param zmailNotebookMaxCachedTemplatesPerFolder new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=371)
    public Map<String,Object> setNotebookMaxCachedTemplatesPerFolder(int zmailNotebookMaxCachedTemplatesPerFolder, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookMaxCachedTemplatesPerFolder, Integer.toString(zmailNotebookMaxCachedTemplatesPerFolder));
        return attrs;
    }

    /**
     * Deprecated since: 6.0.0_BETA1. deprecated. Orig desc: The maximum
     * number of cached templates in each Wiki / Notebook folder cache.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=371)
    public void unsetNotebookMaxCachedTemplatesPerFolder() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookMaxCachedTemplatesPerFolder, "");
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
        attrs.put(Provisioning.A_zmailNotebookMaxCachedTemplatesPerFolder, "");
        return attrs;
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @return zmailNotebookPageCacheSize, or 10240 if unset
     */
    @ZAttr(id=369)
    public int getNotebookPageCacheSize() {
        return getIntAttr(Provisioning.A_zmailNotebookPageCacheSize, 10240);
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @param zmailNotebookPageCacheSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=369)
    public void setNotebookPageCacheSize(int zmailNotebookPageCacheSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookPageCacheSize, Integer.toString(zmailNotebookPageCacheSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @param zmailNotebookPageCacheSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=369)
    public Map<String,Object> setNotebookPageCacheSize(int zmailNotebookPageCacheSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookPageCacheSize, Integer.toString(zmailNotebookPageCacheSize));
        return attrs;
    }

    /**
     * The size of composed Wiki / Notebook page cache on the server.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=369)
    public void unsetNotebookPageCacheSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotebookPageCacheSize, "");
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
        attrs.put(Provisioning.A_zmailNotebookPageCacheSize, "");
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
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @return zmailNotifyBindAddress, or empty array if unset
     */
    @ZAttr(id=317)
    public String[] getNotifyBindAddress() {
        return getMultiAttr(Provisioning.A_zmailNotifyBindAddress);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zmailNotifyBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void setNotifyBindAddress(String[] zmailNotifyBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyBindAddress, zmailNotifyBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zmailNotifyBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> setNotifyBindAddress(String[] zmailNotifyBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyBindAddress, zmailNotifyBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zmailNotifyBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void addNotifyBindAddress(String zmailNotifyBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailNotifyBindAddress, zmailNotifyBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zmailNotifyBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> addNotifyBindAddress(String zmailNotifyBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailNotifyBindAddress, zmailNotifyBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zmailNotifyBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void removeNotifyBindAddress(String zmailNotifyBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailNotifyBindAddress, zmailNotifyBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @param zmailNotifyBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=317)
    public Map<String,Object> removeNotifyBindAddress(String zmailNotifyBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailNotifyBindAddress, zmailNotifyBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which notification server
     * should listen; if empty, binds to all interfaces.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=317)
    public void unsetNotifyBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyBindAddress, "");
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
        attrs.put(Provisioning.A_zmailNotifyBindAddress, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @return zmailNotifyBindPort, or 7035 if unset
     */
    @ZAttr(id=318)
    public int getNotifyBindPort() {
        return getIntAttr(Provisioning.A_zmailNotifyBindPort, 7035);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zmailNotifyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=318)
    public void setNotifyBindPort(int zmailNotifyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyBindPort, Integer.toString(zmailNotifyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zmailNotifyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=318)
    public Map<String,Object> setNotifyBindPort(int zmailNotifyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyBindPort, Integer.toString(zmailNotifyBindPort));
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=318)
    public void unsetNotifyBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyBindPort, "");
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
        attrs.put(Provisioning.A_zmailNotifyBindPort, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @return zmailNotifySSLBindAddress, or empty array if unset
     */
    @ZAttr(id=320)
    public String[] getNotifySSLBindAddress() {
        return getMultiAttr(Provisioning.A_zmailNotifySSLBindAddress);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailNotifySSLBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void setNotifySSLBindAddress(String[] zmailNotifySSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLBindAddress, zmailNotifySSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailNotifySSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> setNotifySSLBindAddress(String[] zmailNotifySSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLBindAddress, zmailNotifySSLBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailNotifySSLBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void addNotifySSLBindAddress(String zmailNotifySSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailNotifySSLBindAddress, zmailNotifySSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailNotifySSLBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> addNotifySSLBindAddress(String zmailNotifySSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailNotifySSLBindAddress, zmailNotifySSLBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailNotifySSLBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void removeNotifySSLBindAddress(String zmailNotifySSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailNotifySSLBindAddress, zmailNotifySSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @param zmailNotifySSLBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=320)
    public Map<String,Object> removeNotifySSLBindAddress(String zmailNotifySSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailNotifySSLBindAddress, zmailNotifySSLBindAddress);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Network interface on which SSL notification server
     * should listen; if empty, binds to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=320)
    public void unsetNotifySSLBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLBindAddress, "");
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
        attrs.put(Provisioning.A_zmailNotifySSLBindAddress, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @return zmailNotifySSLBindPort, or 7036 if unset
     */
    @ZAttr(id=321)
    public int getNotifySSLBindPort() {
        return getIntAttr(Provisioning.A_zmailNotifySSLBindPort, 7036);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zmailNotifySSLBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=321)
    public void setNotifySSLBindPort(int zmailNotifySSLBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLBindPort, Integer.toString(zmailNotifySSLBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @param zmailNotifySSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=321)
    public Map<String,Object> setNotifySSLBindPort(int zmailNotifySSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLBindPort, Integer.toString(zmailNotifySSLBindPort));
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Port number on which notification server should
     * listen.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=321)
    public void unsetNotifySSLBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLBindPort, "");
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
        attrs.put(Provisioning.A_zmailNotifySSLBindPort, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @return zmailNotifySSLServerEnabled, or true if unset
     */
    @ZAttr(id=319)
    public boolean isNotifySSLServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailNotifySSLServerEnabled, true);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @param zmailNotifySSLServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=319)
    public void setNotifySSLServerEnabled(boolean zmailNotifySSLServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLServerEnabled, zmailNotifySSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @param zmailNotifySSLServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=319)
    public Map<String,Object> setNotifySSLServerEnabled(boolean zmailNotifySSLServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLServerEnabled, zmailNotifySSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether SSL notification server should be enabled.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=319)
    public void unsetNotifySSLServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifySSLServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailNotifySSLServerEnabled, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @return zmailNotifyServerEnabled, or true if unset
     */
    @ZAttr(id=316)
    public boolean isNotifyServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailNotifyServerEnabled, true);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @param zmailNotifyServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=316)
    public void setNotifyServerEnabled(boolean zmailNotifyServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyServerEnabled, zmailNotifyServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @param zmailNotifyServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=316)
    public Map<String,Object> setNotifyServerEnabled(boolean zmailNotifyServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyServerEnabled, zmailNotifyServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Deprecated since: 4.0. was experimental and never part of any shipping
     * feature. Orig desc: Whether notification server should be enabled.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=316)
    public void unsetNotifyServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailNotifyServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailNotifyServerEnabled, "");
        return attrs;
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @return zmailOpenidConsumerStatelessModeEnabled, or true if unset
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public boolean isOpenidConsumerStatelessModeEnabled() {
        return getBooleanAttr(Provisioning.A_zmailOpenidConsumerStatelessModeEnabled, true);
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @param zmailOpenidConsumerStatelessModeEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public void setOpenidConsumerStatelessModeEnabled(boolean zmailOpenidConsumerStatelessModeEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerStatelessModeEnabled, zmailOpenidConsumerStatelessModeEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @param zmailOpenidConsumerStatelessModeEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public Map<String,Object> setOpenidConsumerStatelessModeEnabled(boolean zmailOpenidConsumerStatelessModeEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerStatelessModeEnabled, zmailOpenidConsumerStatelessModeEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether stateless mode (not establishing an association with the
     * OpenID Provider) in OpenID Consumer is enabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.0
     */
    @ZAttr(id=1189)
    public void unsetOpenidConsumerStatelessModeEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailOpenidConsumerStatelessModeEnabled, "");
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
        attrs.put(Provisioning.A_zmailOpenidConsumerStatelessModeEnabled, "");
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @return zmailPop3AdvertisedName, or null if unset
     */
    @ZAttr(id=93)
    public String getPop3AdvertisedName() {
        return getAttr(Provisioning.A_zmailPop3AdvertisedName, null);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zmailPop3AdvertisedName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=93)
    public void setPop3AdvertisedName(String zmailPop3AdvertisedName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3AdvertisedName, zmailPop3AdvertisedName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @param zmailPop3AdvertisedName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=93)
    public Map<String,Object> setPop3AdvertisedName(String zmailPop3AdvertisedName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3AdvertisedName, zmailPop3AdvertisedName);
        return attrs;
    }

    /**
     * name to use in greeting and sign-off; if empty, uses hostname
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=93)
    public void unsetPop3AdvertisedName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3AdvertisedName, "");
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
        attrs.put(Provisioning.A_zmailPop3AdvertisedName, "");
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailPop3BindAddress, or empty array if unset
     */
    @ZAttr(id=95)
    public String[] getPop3BindAddress() {
        return getMultiAttr(Provisioning.A_zmailPop3BindAddress);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3BindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void setPop3BindAddress(String[] zmailPop3BindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindAddress, zmailPop3BindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3BindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> setPop3BindAddress(String[] zmailPop3BindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindAddress, zmailPop3BindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3BindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void addPop3BindAddress(String zmailPop3BindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPop3BindAddress, zmailPop3BindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3BindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> addPop3BindAddress(String zmailPop3BindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPop3BindAddress, zmailPop3BindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3BindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void removePop3BindAddress(String zmailPop3BindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPop3BindAddress, zmailPop3BindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3BindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=95)
    public Map<String,Object> removePop3BindAddress(String zmailPop3BindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPop3BindAddress, zmailPop3BindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=95)
    public void unsetPop3BindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindAddress, "");
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
        attrs.put(Provisioning.A_zmailPop3BindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zmailPop3BindOnStartup, or true if unset
     */
    @ZAttr(id=271)
    public boolean isPop3BindOnStartup() {
        return getBooleanAttr(Provisioning.A_zmailPop3BindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailPop3BindOnStartup new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=271)
    public void setPop3BindOnStartup(boolean zmailPop3BindOnStartup) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindOnStartup, zmailPop3BindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailPop3BindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=271)
    public Map<String,Object> setPop3BindOnStartup(boolean zmailPop3BindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindOnStartup, zmailPop3BindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=271)
    public void unsetPop3BindOnStartup() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindOnStartup, "");
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
        attrs.put(Provisioning.A_zmailPop3BindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * <p>Use getPop3BindPortAsString to access value as a string.
     *
     * @see #getPop3BindPortAsString()
     *
     * @return zmailPop3BindPort, or 7110 if unset
     */
    @ZAttr(id=94)
    public int getPop3BindPort() {
        return getIntAttr(Provisioning.A_zmailPop3BindPort, 7110);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @return zmailPop3BindPort, or "7110" if unset
     */
    @ZAttr(id=94)
    public String getPop3BindPortAsString() {
        return getAttr(Provisioning.A_zmailPop3BindPort, "7110");
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3BindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=94)
    public void setPop3BindPort(int zmailPop3BindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindPort, Integer.toString(zmailPop3BindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3BindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=94)
    public Map<String,Object> setPop3BindPort(int zmailPop3BindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindPort, Integer.toString(zmailPop3BindPort));
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3BindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=94)
    public void setPop3BindPortAsString(String zmailPop3BindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindPort, zmailPop3BindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3BindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=94)
    public Map<String,Object> setPop3BindPortAsString(String zmailPop3BindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindPort, zmailPop3BindPort);
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=94)
    public void unsetPop3BindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3BindPort, "");
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
        attrs.put(Provisioning.A_zmailPop3BindPort, "");
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @return zmailPop3CleartextLoginEnabled, or false if unset
     */
    @ZAttr(id=189)
    public boolean isPop3CleartextLoginEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPop3CleartextLoginEnabled, false);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zmailPop3CleartextLoginEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=189)
    public void setPop3CleartextLoginEnabled(boolean zmailPop3CleartextLoginEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3CleartextLoginEnabled, zmailPop3CleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @param zmailPop3CleartextLoginEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=189)
    public Map<String,Object> setPop3CleartextLoginEnabled(boolean zmailPop3CleartextLoginEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3CleartextLoginEnabled, zmailPop3CleartextLoginEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether or not to allow cleartext logins over a non SSL/TLS connection
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=189)
    public void unsetPop3CleartextLoginEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3CleartextLoginEnabled, "");
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
        attrs.put(Provisioning.A_zmailPop3CleartextLoginEnabled, "");
        return attrs;
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @return zmailPop3ExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public boolean isPop3ExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zmailPop3ExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @param zmailPop3ExposeVersionOnBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public void setPop3ExposeVersionOnBanner(boolean zmailPop3ExposeVersionOnBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ExposeVersionOnBanner, zmailPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @param zmailPop3ExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public Map<String,Object> setPop3ExposeVersionOnBanner(boolean zmailPop3ExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ExposeVersionOnBanner, zmailPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on POP3 banner
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=692)
    public void unsetPop3ExposeVersionOnBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ExposeVersionOnBanner, "");
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
        attrs.put(Provisioning.A_zmailPop3ExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @return zmailPop3MaxConnections, or 200 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public int getPop3MaxConnections() {
        return getIntAttr(Provisioning.A_zmailPop3MaxConnections, 200);
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zmailPop3MaxConnections new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public void setPop3MaxConnections(int zmailPop3MaxConnections) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3MaxConnections, Integer.toString(zmailPop3MaxConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @param zmailPop3MaxConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public Map<String,Object> setPop3MaxConnections(int zmailPop3MaxConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3MaxConnections, Integer.toString(zmailPop3MaxConnections));
        return attrs;
    }

    /**
     * Maximum number of concurrent POP3 connections allowed. New connections
     * exceeding this limit are rejected.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1155)
    public void unsetPop3MaxConnections() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3MaxConnections, "");
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
        attrs.put(Provisioning.A_zmailPop3MaxConnections, "");
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @return zmailPop3NumThreads, or 100 if unset
     */
    @ZAttr(id=96)
    public int getPop3NumThreads() {
        return getIntAttr(Provisioning.A_zmailPop3NumThreads, 100);
    }

    /**
     * number of handler threads
     *
     * @param zmailPop3NumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=96)
    public void setPop3NumThreads(int zmailPop3NumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3NumThreads, Integer.toString(zmailPop3NumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of handler threads
     *
     * @param zmailPop3NumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=96)
    public Map<String,Object> setPop3NumThreads(int zmailPop3NumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3NumThreads, Integer.toString(zmailPop3NumThreads));
        return attrs;
    }

    /**
     * number of handler threads
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=96)
    public void unsetPop3NumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3NumThreads, "");
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
        attrs.put(Provisioning.A_zmailPop3NumThreads, "");
        return attrs;
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * <p>Use getPop3ProxyBindPortAsString to access value as a string.
     *
     * @see #getPop3ProxyBindPortAsString()
     *
     * @return zmailPop3ProxyBindPort, or 110 if unset
     */
    @ZAttr(id=350)
    public int getPop3ProxyBindPort() {
        return getIntAttr(Provisioning.A_zmailPop3ProxyBindPort, 110);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @return zmailPop3ProxyBindPort, or "110" if unset
     */
    @ZAttr(id=350)
    public String getPop3ProxyBindPortAsString() {
        return getAttr(Provisioning.A_zmailPop3ProxyBindPort, "110");
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zmailPop3ProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=350)
    public void setPop3ProxyBindPort(int zmailPop3ProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ProxyBindPort, Integer.toString(zmailPop3ProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zmailPop3ProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=350)
    public Map<String,Object> setPop3ProxyBindPort(int zmailPop3ProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ProxyBindPort, Integer.toString(zmailPop3ProxyBindPort));
        return attrs;
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zmailPop3ProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=350)
    public void setPop3ProxyBindPortAsString(String zmailPop3ProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ProxyBindPort, zmailPop3ProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @param zmailPop3ProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=350)
    public Map<String,Object> setPop3ProxyBindPortAsString(String zmailPop3ProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ProxyBindPort, zmailPop3ProxyBindPort);
        return attrs;
    }

    /**
     * port number on which POP3 proxy server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=350)
    public void unsetPop3ProxyBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ProxyBindPort, "");
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
        attrs.put(Provisioning.A_zmailPop3ProxyBindPort, "");
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @return zmailPop3SSLBindAddress, or empty array if unset
     */
    @ZAttr(id=186)
    public String[] getPop3SSLBindAddress() {
        return getMultiAttr(Provisioning.A_zmailPop3SSLBindAddress);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3SSLBindAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void setPop3SSLBindAddress(String[] zmailPop3SSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindAddress, zmailPop3SSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3SSLBindAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> setPop3SSLBindAddress(String[] zmailPop3SSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindAddress, zmailPop3SSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3SSLBindAddress new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void addPop3SSLBindAddress(String zmailPop3SSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPop3SSLBindAddress, zmailPop3SSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3SSLBindAddress new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> addPop3SSLBindAddress(String zmailPop3SSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPop3SSLBindAddress, zmailPop3SSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3SSLBindAddress existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void removePop3SSLBindAddress(String zmailPop3SSLBindAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPop3SSLBindAddress, zmailPop3SSLBindAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @param zmailPop3SSLBindAddress existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=186)
    public Map<String,Object> removePop3SSLBindAddress(String zmailPop3SSLBindAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPop3SSLBindAddress, zmailPop3SSLBindAddress);
        return attrs;
    }

    /**
     * interface address on which POP3 server should listen; if empty, binds
     * to all interfaces
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=186)
    public void unsetPop3SSLBindAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindAddress, "");
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
        attrs.put(Provisioning.A_zmailPop3SSLBindAddress, "");
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @return zmailPop3SSLBindOnStartup, or true if unset
     */
    @ZAttr(id=272)
    public boolean isPop3SSLBindOnStartup() {
        return getBooleanAttr(Provisioning.A_zmailPop3SSLBindOnStartup, true);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailPop3SSLBindOnStartup new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=272)
    public void setPop3SSLBindOnStartup(boolean zmailPop3SSLBindOnStartup) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindOnStartup, zmailPop3SSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @param zmailPop3SSLBindOnStartup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=272)
    public Map<String,Object> setPop3SSLBindOnStartup(boolean zmailPop3SSLBindOnStartup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindOnStartup, zmailPop3SSLBindOnStartup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to bind to port on startup irrespective of whether the server
     * is enabled. Useful when port to bind is privileged and must be bound
     * early.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=272)
    public void unsetPop3SSLBindOnStartup() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindOnStartup, "");
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
        attrs.put(Provisioning.A_zmailPop3SSLBindOnStartup, "");
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * <p>Use getPop3SSLBindPortAsString to access value as a string.
     *
     * @see #getPop3SSLBindPortAsString()
     *
     * @return zmailPop3SSLBindPort, or 7995 if unset
     */
    @ZAttr(id=187)
    public int getPop3SSLBindPort() {
        return getIntAttr(Provisioning.A_zmailPop3SSLBindPort, 7995);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @return zmailPop3SSLBindPort, or "7995" if unset
     */
    @ZAttr(id=187)
    public String getPop3SSLBindPortAsString() {
        return getAttr(Provisioning.A_zmailPop3SSLBindPort, "7995");
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3SSLBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=187)
    public void setPop3SSLBindPort(int zmailPop3SSLBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindPort, Integer.toString(zmailPop3SSLBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3SSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=187)
    public Map<String,Object> setPop3SSLBindPort(int zmailPop3SSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindPort, Integer.toString(zmailPop3SSLBindPort));
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3SSLBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=187)
    public void setPop3SSLBindPortAsString(String zmailPop3SSLBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindPort, zmailPop3SSLBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3 server should listen
     *
     * @param zmailPop3SSLBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=187)
    public Map<String,Object> setPop3SSLBindPortAsString(String zmailPop3SSLBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindPort, zmailPop3SSLBindPort);
        return attrs;
    }

    /**
     * port number on which POP3 server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=187)
    public void unsetPop3SSLBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLBindPort, "");
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
        attrs.put(Provisioning.A_zmailPop3SSLBindPort, "");
        return attrs;
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * <p>Use getPop3SSLProxyBindPortAsString to access value as a string.
     *
     * @see #getPop3SSLProxyBindPortAsString()
     *
     * @return zmailPop3SSLProxyBindPort, or 995 if unset
     */
    @ZAttr(id=351)
    public int getPop3SSLProxyBindPort() {
        return getIntAttr(Provisioning.A_zmailPop3SSLProxyBindPort, 995);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @return zmailPop3SSLProxyBindPort, or "995" if unset
     */
    @ZAttr(id=351)
    public String getPop3SSLProxyBindPortAsString() {
        return getAttr(Provisioning.A_zmailPop3SSLProxyBindPort, "995");
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zmailPop3SSLProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=351)
    public void setPop3SSLProxyBindPort(int zmailPop3SSLProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLProxyBindPort, Integer.toString(zmailPop3SSLProxyBindPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zmailPop3SSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=351)
    public Map<String,Object> setPop3SSLProxyBindPort(int zmailPop3SSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLProxyBindPort, Integer.toString(zmailPop3SSLProxyBindPort));
        return attrs;
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zmailPop3SSLProxyBindPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=351)
    public void setPop3SSLProxyBindPortAsString(String zmailPop3SSLProxyBindPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLProxyBindPort, zmailPop3SSLProxyBindPort);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @param zmailPop3SSLProxyBindPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=351)
    public Map<String,Object> setPop3SSLProxyBindPortAsString(String zmailPop3SSLProxyBindPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLProxyBindPort, zmailPop3SSLProxyBindPort);
        return attrs;
    }

    /**
     * port number on which POP3S proxy server should listen
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=351)
    public void unsetPop3SSLProxyBindPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLProxyBindPort, "");
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
        attrs.put(Provisioning.A_zmailPop3SSLProxyBindPort, "");
        return attrs;
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @return zmailPop3SSLServerEnabled, or true if unset
     */
    @ZAttr(id=188)
    public boolean isPop3SSLServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPop3SSLServerEnabled, true);
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @param zmailPop3SSLServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=188)
    public void setPop3SSLServerEnabled(boolean zmailPop3SSLServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLServerEnabled, zmailPop3SSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @param zmailPop3SSLServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=188)
    public Map<String,Object> setPop3SSLServerEnabled(boolean zmailPop3SSLServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLServerEnabled, zmailPop3SSLServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SSL server is enabled for a server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=188)
    public void unsetPop3SSLServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SSLServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailPop3SSLServerEnabled, "");
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @return zmailPop3SaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public boolean isPop3SaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPop3SaslGssapiEnabled, false);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @param zmailPop3SaslGssapiEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public void setPop3SaslGssapiEnabled(boolean zmailPop3SaslGssapiEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SaslGssapiEnabled, zmailPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @param zmailPop3SaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public Map<String,Object> setPop3SaslGssapiEnabled(boolean zmailPop3SaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SaslGssapiEnabled, zmailPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for a given server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=554)
    public void unsetPop3SaslGssapiEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3SaslGssapiEnabled, "");
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
        attrs.put(Provisioning.A_zmailPop3SaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @return zmailPop3ServerEnabled, or true if unset
     */
    @ZAttr(id=177)
    public boolean isPop3ServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPop3ServerEnabled, true);
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @param zmailPop3ServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=177)
    public void setPop3ServerEnabled(boolean zmailPop3ServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ServerEnabled, zmailPop3ServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @param zmailPop3ServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=177)
    public Map<String,Object> setPop3ServerEnabled(boolean zmailPop3ServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ServerEnabled, zmailPop3ServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 is enabled for a server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=177)
    public void unsetPop3ServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ServerEnabled, "");
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
        attrs.put(Provisioning.A_zmailPop3ServerEnabled, "");
        return attrs;
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @return zmailPop3ShutdownGraceSeconds, or 10 if unset
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public int getPop3ShutdownGraceSeconds() {
        return getIntAttr(Provisioning.A_zmailPop3ShutdownGraceSeconds, 10);
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @param zmailPop3ShutdownGraceSeconds new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public void setPop3ShutdownGraceSeconds(int zmailPop3ShutdownGraceSeconds) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ShutdownGraceSeconds, Integer.toString(zmailPop3ShutdownGraceSeconds));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @param zmailPop3ShutdownGraceSeconds new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public Map<String,Object> setPop3ShutdownGraceSeconds(int zmailPop3ShutdownGraceSeconds, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ShutdownGraceSeconds, Integer.toString(zmailPop3ShutdownGraceSeconds));
        return attrs;
    }

    /**
     * number of seconds to wait before forcing POP3 server shutdown
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.7
     */
    @ZAttr(id=1081)
    public void unsetPop3ShutdownGraceSeconds() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPop3ShutdownGraceSeconds, "");
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
        attrs.put(Provisioning.A_zmailPop3ShutdownGraceSeconds, "");
        return attrs;
    }

    /**
     * redolog rollover destination
     *
     * @return zmailRedoLogArchiveDir, or "redolog/archive" if unset
     */
    @ZAttr(id=76)
    public String getRedoLogArchiveDir() {
        return getAttr(Provisioning.A_zmailRedoLogArchiveDir, "redolog/archive");
    }

    /**
     * redolog rollover destination
     *
     * @param zmailRedoLogArchiveDir new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=76)
    public void setRedoLogArchiveDir(String zmailRedoLogArchiveDir) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogArchiveDir, zmailRedoLogArchiveDir);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redolog rollover destination
     *
     * @param zmailRedoLogArchiveDir new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=76)
    public Map<String,Object> setRedoLogArchiveDir(String zmailRedoLogArchiveDir, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogArchiveDir, zmailRedoLogArchiveDir);
        return attrs;
    }

    /**
     * redolog rollover destination
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=76)
    public void unsetRedoLogArchiveDir() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogArchiveDir, "");
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
        attrs.put(Provisioning.A_zmailRedoLogArchiveDir, "");
        return attrs;
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @return zmailRedoLogCrashRecoveryLookbackSec, or 10 if unset
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public int getRedoLogCrashRecoveryLookbackSec() {
        return getIntAttr(Provisioning.A_zmailRedoLogCrashRecoveryLookbackSec, 10);
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @param zmailRedoLogCrashRecoveryLookbackSec new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public void setRedoLogCrashRecoveryLookbackSec(int zmailRedoLogCrashRecoveryLookbackSec) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogCrashRecoveryLookbackSec, Integer.toString(zmailRedoLogCrashRecoveryLookbackSec));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @param zmailRedoLogCrashRecoveryLookbackSec new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public Map<String,Object> setRedoLogCrashRecoveryLookbackSec(int zmailRedoLogCrashRecoveryLookbackSec, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogCrashRecoveryLookbackSec, Integer.toString(zmailRedoLogCrashRecoveryLookbackSec));
        return attrs;
    }

    /**
     * how many seconds worth of committed redo ops to re-execute during
     * crash recovery; related to mysql parameter
     * innodb_flush_log_at_trx_commit=0
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA2
     */
    @ZAttr(id=1009)
    public void unsetRedoLogCrashRecoveryLookbackSec() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogCrashRecoveryLookbackSec, "");
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
        attrs.put(Provisioning.A_zmailRedoLogCrashRecoveryLookbackSec, "");
        return attrs;
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @return zmailRedoLogDeleteOnRollover, or true if unset
     */
    @ZAttr(id=251)
    public boolean isRedoLogDeleteOnRollover() {
        return getBooleanAttr(Provisioning.A_zmailRedoLogDeleteOnRollover, true);
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @param zmailRedoLogDeleteOnRollover new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=251)
    public void setRedoLogDeleteOnRollover(boolean zmailRedoLogDeleteOnRollover) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogDeleteOnRollover, zmailRedoLogDeleteOnRollover ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @param zmailRedoLogDeleteOnRollover new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=251)
    public Map<String,Object> setRedoLogDeleteOnRollover(boolean zmailRedoLogDeleteOnRollover, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogDeleteOnRollover, zmailRedoLogDeleteOnRollover ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether logs are delete on rollover or archived
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=251)
    public void unsetRedoLogDeleteOnRollover() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogDeleteOnRollover, "");
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
        attrs.put(Provisioning.A_zmailRedoLogDeleteOnRollover, "");
        return attrs;
    }

    /**
     * whether redo logging is enabled
     *
     * @return zmailRedoLogEnabled, or true if unset
     */
    @ZAttr(id=74)
    public boolean isRedoLogEnabled() {
        return getBooleanAttr(Provisioning.A_zmailRedoLogEnabled, true);
    }

    /**
     * whether redo logging is enabled
     *
     * @param zmailRedoLogEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=74)
    public void setRedoLogEnabled(boolean zmailRedoLogEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogEnabled, zmailRedoLogEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether redo logging is enabled
     *
     * @param zmailRedoLogEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=74)
    public Map<String,Object> setRedoLogEnabled(boolean zmailRedoLogEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogEnabled, zmailRedoLogEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether redo logging is enabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=74)
    public void unsetRedoLogEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogEnabled, "");
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
        attrs.put(Provisioning.A_zmailRedoLogEnabled, "");
        return attrs;
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @return zmailRedoLogFsyncIntervalMS, or 10 if unset
     */
    @ZAttr(id=79)
    public int getRedoLogFsyncIntervalMS() {
        return getIntAttr(Provisioning.A_zmailRedoLogFsyncIntervalMS, 10);
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @param zmailRedoLogFsyncIntervalMS new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=79)
    public void setRedoLogFsyncIntervalMS(int zmailRedoLogFsyncIntervalMS) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogFsyncIntervalMS, Integer.toString(zmailRedoLogFsyncIntervalMS));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @param zmailRedoLogFsyncIntervalMS new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=79)
    public Map<String,Object> setRedoLogFsyncIntervalMS(int zmailRedoLogFsyncIntervalMS, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogFsyncIntervalMS, Integer.toString(zmailRedoLogFsyncIntervalMS));
        return attrs;
    }

    /**
     * how frequently writes to redo log get fsynced to disk
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=79)
    public void unsetRedoLogFsyncIntervalMS() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogFsyncIntervalMS, "");
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
        attrs.put(Provisioning.A_zmailRedoLogFsyncIntervalMS, "");
        return attrs;
    }

    /**
     * name and location of the redolog file
     *
     * @return zmailRedoLogLogPath, or "redolog/redo.log" if unset
     */
    @ZAttr(id=75)
    public String getRedoLogLogPath() {
        return getAttr(Provisioning.A_zmailRedoLogLogPath, "redolog/redo.log");
    }

    /**
     * name and location of the redolog file
     *
     * @param zmailRedoLogLogPath new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=75)
    public void setRedoLogLogPath(String zmailRedoLogLogPath) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogLogPath, zmailRedoLogLogPath);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name and location of the redolog file
     *
     * @param zmailRedoLogLogPath new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=75)
    public Map<String,Object> setRedoLogLogPath(String zmailRedoLogLogPath, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogLogPath, zmailRedoLogLogPath);
        return attrs;
    }

    /**
     * name and location of the redolog file
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=75)
    public void unsetRedoLogLogPath() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogLogPath, "");
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
        attrs.put(Provisioning.A_zmailRedoLogLogPath, "");
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @return zmailRedoLogProvider, or empty array if unset
     */
    @ZAttr(id=225)
    public String[] getRedoLogProvider() {
        return getMultiAttr(Provisioning.A_zmailRedoLogProvider);
    }

    /**
     * provider class name for redo logging
     *
     * @param zmailRedoLogProvider new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void setRedoLogProvider(String[] zmailRedoLogProvider) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogProvider, zmailRedoLogProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param zmailRedoLogProvider new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> setRedoLogProvider(String[] zmailRedoLogProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogProvider, zmailRedoLogProvider);
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @param zmailRedoLogProvider new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void addRedoLogProvider(String zmailRedoLogProvider) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailRedoLogProvider, zmailRedoLogProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param zmailRedoLogProvider new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> addRedoLogProvider(String zmailRedoLogProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailRedoLogProvider, zmailRedoLogProvider);
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @param zmailRedoLogProvider existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void removeRedoLogProvider(String zmailRedoLogProvider) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailRedoLogProvider, zmailRedoLogProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider class name for redo logging
     *
     * @param zmailRedoLogProvider existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=225)
    public Map<String,Object> removeRedoLogProvider(String zmailRedoLogProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailRedoLogProvider, zmailRedoLogProvider);
        return attrs;
    }

    /**
     * provider class name for redo logging
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=225)
    public void unsetRedoLogProvider() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogProvider, "");
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
        attrs.put(Provisioning.A_zmailRedoLogProvider, "");
        return attrs;
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @return zmailRedoLogRolloverFileSizeKB, or 1048576 if unset
     */
    @ZAttr(id=78)
    public int getRedoLogRolloverFileSizeKB() {
        return getIntAttr(Provisioning.A_zmailRedoLogRolloverFileSizeKB, 1048576);
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @param zmailRedoLogRolloverFileSizeKB new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=78)
    public void setRedoLogRolloverFileSizeKB(int zmailRedoLogRolloverFileSizeKB) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverFileSizeKB, Integer.toString(zmailRedoLogRolloverFileSizeKB));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @param zmailRedoLogRolloverFileSizeKB new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=78)
    public Map<String,Object> setRedoLogRolloverFileSizeKB(int zmailRedoLogRolloverFileSizeKB, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverFileSizeKB, Integer.toString(zmailRedoLogRolloverFileSizeKB));
        return attrs;
    }

    /**
     * redo.log file becomes eligible for rollover over when it goes over
     * this size
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=78)
    public void unsetRedoLogRolloverFileSizeKB() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverFileSizeKB, "");
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
        attrs.put(Provisioning.A_zmailRedoLogRolloverFileSizeKB, "");
        return attrs;
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @return zmailRedoLogRolloverHardMaxFileSizeKB, or 4194304 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public int getRedoLogRolloverHardMaxFileSizeKB() {
        return getIntAttr(Provisioning.A_zmailRedoLogRolloverHardMaxFileSizeKB, 4194304);
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @param zmailRedoLogRolloverHardMaxFileSizeKB new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public void setRedoLogRolloverHardMaxFileSizeKB(int zmailRedoLogRolloverHardMaxFileSizeKB) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverHardMaxFileSizeKB, Integer.toString(zmailRedoLogRolloverHardMaxFileSizeKB));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @param zmailRedoLogRolloverHardMaxFileSizeKB new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public Map<String,Object> setRedoLogRolloverHardMaxFileSizeKB(int zmailRedoLogRolloverHardMaxFileSizeKB, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverHardMaxFileSizeKB, Integer.toString(zmailRedoLogRolloverHardMaxFileSizeKB));
        return attrs;
    }

    /**
     * redo.log file rolls over when it goes over this size, even if it does
     * not meet the minimum file age requirement
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1021)
    public void unsetRedoLogRolloverHardMaxFileSizeKB() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverHardMaxFileSizeKB, "");
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
        attrs.put(Provisioning.A_zmailRedoLogRolloverHardMaxFileSizeKB, "");
        return attrs;
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @return zmailRedoLogRolloverMinFileAge, or 60 if unset
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public int getRedoLogRolloverMinFileAge() {
        return getIntAttr(Provisioning.A_zmailRedoLogRolloverMinFileAge, 60);
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @param zmailRedoLogRolloverMinFileAge new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public void setRedoLogRolloverMinFileAge(int zmailRedoLogRolloverMinFileAge) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverMinFileAge, Integer.toString(zmailRedoLogRolloverMinFileAge));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @param zmailRedoLogRolloverMinFileAge new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public Map<String,Object> setRedoLogRolloverMinFileAge(int zmailRedoLogRolloverMinFileAge, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverMinFileAge, Integer.toString(zmailRedoLogRolloverMinFileAge));
        return attrs;
    }

    /**
     * minimum age in minutes for redo.log file before it becomes eligible
     * for rollover based on size
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.17
     */
    @ZAttr(id=1020)
    public void unsetRedoLogRolloverMinFileAge() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRedoLogRolloverMinFileAge, "");
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
        attrs.put(Provisioning.A_zmailRedoLogRolloverMinFileAge, "");
        return attrs;
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @return zmailRemoteManagementCommand, or "/opt/zmail/libexec/zmrcd" if unset
     */
    @ZAttr(id=336)
    public String getRemoteManagementCommand() {
        return getAttr(Provisioning.A_zmailRemoteManagementCommand, "/opt/zmail/libexec/zmrcd");
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @param zmailRemoteManagementCommand new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=336)
    public void setRemoteManagementCommand(String zmailRemoteManagementCommand) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementCommand, zmailRemoteManagementCommand);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @param zmailRemoteManagementCommand new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=336)
    public Map<String,Object> setRemoteManagementCommand(String zmailRemoteManagementCommand, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementCommand, zmailRemoteManagementCommand);
        return attrs;
    }

    /**
     * Path to remote management command to execute on this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=336)
    public void unsetRemoteManagementCommand() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementCommand, "");
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
        attrs.put(Provisioning.A_zmailRemoteManagementCommand, "");
        return attrs;
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @return zmailRemoteManagementPort, or 22 if unset
     */
    @ZAttr(id=339)
    public int getRemoteManagementPort() {
        return getIntAttr(Provisioning.A_zmailRemoteManagementPort, 22);
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @param zmailRemoteManagementPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=339)
    public void setRemoteManagementPort(int zmailRemoteManagementPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementPort, Integer.toString(zmailRemoteManagementPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @param zmailRemoteManagementPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=339)
    public Map<String,Object> setRemoteManagementPort(int zmailRemoteManagementPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementPort, Integer.toString(zmailRemoteManagementPort));
        return attrs;
    }

    /**
     * Port on which remote management sshd listening on this server.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=339)
    public void unsetRemoteManagementPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementPort, "");
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
        attrs.put(Provisioning.A_zmailRemoteManagementPort, "");
        return attrs;
    }

    /**
     * Private key this server should use to access another server
     *
     * @return zmailRemoteManagementPrivateKeyPath, or "/opt/zmail/.ssh/zmail_identity" if unset
     */
    @ZAttr(id=338)
    public String getRemoteManagementPrivateKeyPath() {
        return getAttr(Provisioning.A_zmailRemoteManagementPrivateKeyPath, "/opt/zmail/.ssh/zmail_identity");
    }

    /**
     * Private key this server should use to access another server
     *
     * @param zmailRemoteManagementPrivateKeyPath new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=338)
    public void setRemoteManagementPrivateKeyPath(String zmailRemoteManagementPrivateKeyPath) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementPrivateKeyPath, zmailRemoteManagementPrivateKeyPath);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Private key this server should use to access another server
     *
     * @param zmailRemoteManagementPrivateKeyPath new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=338)
    public Map<String,Object> setRemoteManagementPrivateKeyPath(String zmailRemoteManagementPrivateKeyPath, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementPrivateKeyPath, zmailRemoteManagementPrivateKeyPath);
        return attrs;
    }

    /**
     * Private key this server should use to access another server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=338)
    public void unsetRemoteManagementPrivateKeyPath() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementPrivateKeyPath, "");
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
        attrs.put(Provisioning.A_zmailRemoteManagementPrivateKeyPath, "");
        return attrs;
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @return zmailRemoteManagementUser, or "zmail" if unset
     */
    @ZAttr(id=337)
    public String getRemoteManagementUser() {
        return getAttr(Provisioning.A_zmailRemoteManagementUser, "zmail");
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @param zmailRemoteManagementUser new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=337)
    public void setRemoteManagementUser(String zmailRemoteManagementUser) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementUser, zmailRemoteManagementUser);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @param zmailRemoteManagementUser new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=337)
    public Map<String,Object> setRemoteManagementUser(String zmailRemoteManagementUser, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementUser, zmailRemoteManagementUser);
        return attrs;
    }

    /**
     * Login name of user allowed to execute remote management command
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=337)
    public void unsetRemoteManagementUser() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailRemoteManagementUser, "");
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
        attrs.put(Provisioning.A_zmailRemoteManagementUser, "");
        return attrs;
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @return zmailReverseProxyAdminEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public boolean isReverseProxyAdminEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyAdminEnabled, false);
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @param zmailReverseProxyAdminEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public void setReverseProxyAdminEnabled(boolean zmailReverseProxyAdminEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyAdminEnabled, zmailReverseProxyAdminEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @param zmailReverseProxyAdminEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public Map<String,Object> setReverseProxyAdminEnabled(boolean zmailReverseProxyAdminEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyAdminEnabled, zmailReverseProxyAdminEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * indicate whether to turn on admin console proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1321)
    public void unsetReverseProxyAdminEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyAdminEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyAdminEnabled, "");
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @return zmailReverseProxyAvailableLookupTargets, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public String[] getReverseProxyAvailableLookupTargets() {
        return getMultiAttr(Provisioning.A_zmailReverseProxyAvailableLookupTargets);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zmailReverseProxyAvailableLookupTargets new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void setReverseProxyAvailableLookupTargets(String[] zmailReverseProxyAvailableLookupTargets) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyAvailableLookupTargets, zmailReverseProxyAvailableLookupTargets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zmailReverseProxyAvailableLookupTargets new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> setReverseProxyAvailableLookupTargets(String[] zmailReverseProxyAvailableLookupTargets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyAvailableLookupTargets, zmailReverseProxyAvailableLookupTargets);
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zmailReverseProxyAvailableLookupTargets new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void addReverseProxyAvailableLookupTargets(String zmailReverseProxyAvailableLookupTargets) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyAvailableLookupTargets, zmailReverseProxyAvailableLookupTargets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zmailReverseProxyAvailableLookupTargets new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> addReverseProxyAvailableLookupTargets(String zmailReverseProxyAvailableLookupTargets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyAvailableLookupTargets, zmailReverseProxyAvailableLookupTargets);
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zmailReverseProxyAvailableLookupTargets existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void removeReverseProxyAvailableLookupTargets(String zmailReverseProxyAvailableLookupTargets) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyAvailableLookupTargets, zmailReverseProxyAvailableLookupTargets);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @param zmailReverseProxyAvailableLookupTargets existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public Map<String,Object> removeReverseProxyAvailableLookupTargets(String zmailReverseProxyAvailableLookupTargets, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyAvailableLookupTargets, zmailReverseProxyAvailableLookupTargets);
        return attrs;
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
     * is TRUE.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1379)
    public void unsetReverseProxyAvailableLookupTargets() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyAvailableLookupTargets, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the proxy lookup hanlders list. Proxy
     * will only use the servers specified here to do the lookup. Leaving
     * empty means using all the servers whose zmailReverseProxyLookupTarget
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
        attrs.put(Provisioning.A_zmailReverseProxyAvailableLookupTargets, "");
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
     * @return zmailReverseProxyConnectTimeout in millseconds, or 120000 (120000ms)  if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public long getReverseProxyConnectTimeout() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyConnectTimeout, 120000L);
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailReverseProxyConnectTimeout, or "120000ms" if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public String getReverseProxyConnectTimeoutAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyConnectTimeout, "120000ms");
    }

    /**
     * Time interval after which NGINX mail proxy will disconnect while
     * establishing an upstream IMAP/POP connection. Must be in valid
     * duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailReverseProxyConnectTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public void setReverseProxyConnectTimeout(String zmailReverseProxyConnectTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyConnectTimeout, zmailReverseProxyConnectTimeout);
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
     * @param zmailReverseProxyConnectTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public Map<String,Object> setReverseProxyConnectTimeout(String zmailReverseProxyConnectTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyConnectTimeout, zmailReverseProxyConnectTimeout);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=797)
    public void unsetReverseProxyConnectTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyConnectTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyConnectTimeout, "");
        return attrs;
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @return zmailReverseProxyDefaultRealm, or null if unset
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public String getReverseProxyDefaultRealm() {
        return getAttr(Provisioning.A_zmailReverseProxyDefaultRealm, null);
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @param zmailReverseProxyDefaultRealm new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public void setReverseProxyDefaultRealm(String zmailReverseProxyDefaultRealm) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDefaultRealm, zmailReverseProxyDefaultRealm);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @param zmailReverseProxyDefaultRealm new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public Map<String,Object> setReverseProxyDefaultRealm(String zmailReverseProxyDefaultRealm, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDefaultRealm, zmailReverseProxyDefaultRealm);
        return attrs;
    }

    /**
     * The default realm that will be used by NGINX mail proxy, when the
     * realm is not specified in GSSAPI Authentication
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.9
     */
    @ZAttr(id=703)
    public void unsetReverseProxyDefaultRealm() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDefaultRealm, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyDefaultRealm, "");
        return attrs;
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zmailMailHost) is directly sent to proxy.
     *
     * @return zmailReverseProxyDnsLookupInServerEnabled, or true if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public boolean isReverseProxyDnsLookupInServerEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyDnsLookupInServerEnabled, true);
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zmailMailHost) is directly sent to proxy.
     *
     * @param zmailReverseProxyDnsLookupInServerEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public void setReverseProxyDnsLookupInServerEnabled(boolean zmailReverseProxyDnsLookupInServerEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDnsLookupInServerEnabled, zmailReverseProxyDnsLookupInServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zmailMailHost) is directly sent to proxy.
     *
     * @param zmailReverseProxyDnsLookupInServerEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public Map<String,Object> setReverseProxyDnsLookupInServerEnabled(boolean zmailReverseProxyDnsLookupInServerEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDnsLookupInServerEnabled, zmailReverseProxyDnsLookupInServerEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zmailMailHost) is directly sent to proxy.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public void unsetReverseProxyDnsLookupInServerEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDnsLookupInServerEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether force the server side do the DNS lookup and send the
     * result IP back to proxy. If set false, the raw address configured
     * (e.g. zmailMailHost) is directly sent to proxy.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1384)
    public Map<String,Object> unsetReverseProxyDnsLookupInServerEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyDnsLookupInServerEnabled, "");
        return attrs;
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @return zmailReverseProxyErrorHandlerURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public String getReverseProxyErrorHandlerURL() {
        return getAttr(Provisioning.A_zmailReverseProxyErrorHandlerURL, null);
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @param zmailReverseProxyErrorHandlerURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public void setReverseProxyErrorHandlerURL(String zmailReverseProxyErrorHandlerURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyErrorHandlerURL, zmailReverseProxyErrorHandlerURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @param zmailReverseProxyErrorHandlerURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public Map<String,Object> setReverseProxyErrorHandlerURL(String zmailReverseProxyErrorHandlerURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyErrorHandlerURL, zmailReverseProxyErrorHandlerURL);
        return attrs;
    }

    /**
     * the URL of customized proxy error handler. If set, when errors happen
     * in proxy, proxy will redirect to this URL with two paras - err: error
     * code; up: the addr of upstream server connecting to which the error
     * happens
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1332)
    public void unsetReverseProxyErrorHandlerURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyErrorHandlerURL, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyErrorHandlerURL, "");
        return attrs;
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @return zmailReverseProxyGenConfigPerVirtualHostname, or true if unset
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public boolean isReverseProxyGenConfigPerVirtualHostname() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyGenConfigPerVirtualHostname, true);
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @param zmailReverseProxyGenConfigPerVirtualHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public void setReverseProxyGenConfigPerVirtualHostname(boolean zmailReverseProxyGenConfigPerVirtualHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyGenConfigPerVirtualHostname, zmailReverseProxyGenConfigPerVirtualHostname ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @param zmailReverseProxyGenConfigPerVirtualHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public Map<String,Object> setReverseProxyGenConfigPerVirtualHostname(boolean zmailReverseProxyGenConfigPerVirtualHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyGenConfigPerVirtualHostname, zmailReverseProxyGenConfigPerVirtualHostname ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Control whether to generate per virtual hostname nginx configuration.
     * This would be helpful when multiple virtual host names are defined,
     * but they are actually share the same configuration (like ssl cert,
     * client CA, ...). This attr has to be set as &quot;TRUE&quot; to enable
     * the features like cert per domain.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.2.0
     */
    @ZAttr(id=1374)
    public void unsetReverseProxyGenConfigPerVirtualHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyGenConfigPerVirtualHostname, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyGenConfigPerVirtualHostname, "");
        return attrs;
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @return zmailReverseProxyHttpEnabled, or false if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public boolean isReverseProxyHttpEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyHttpEnabled, false);
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @param zmailReverseProxyHttpEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public void setReverseProxyHttpEnabled(boolean zmailReverseProxyHttpEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyHttpEnabled, zmailReverseProxyHttpEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @param zmailReverseProxyHttpEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public Map<String,Object> setReverseProxyHttpEnabled(boolean zmailReverseProxyHttpEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyHttpEnabled, zmailReverseProxyHttpEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to enable HTTP proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=628)
    public void unsetReverseProxyHttpEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyHttpEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyHttpEnabled, "");
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @return zmailReverseProxyImapEnabledCapability, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public String[] getReverseProxyImapEnabledCapability() {
        String[] value = getMultiAttr(Provisioning.A_zmailReverseProxyImapEnabledCapability); return value.length > 0 ? value : new String[] {"IMAP4rev1","ACL","BINARY","CATENATE","CHILDREN","CONDSTORE","ENABLE","ESEARCH","ESORT","I18NLEVEL=1","ID","IDLE","LIST-EXTENDED","LIST-STATUS","LITERAL+","MULTIAPPEND","NAMESPACE","QRESYNC","QUOTA","RIGHTS=ektx","SASL-IR","SEARCHRES","SORT","THREAD=ORDEREDSUBJECT","UIDPLUS","UNSELECT","WITHIN","XLIST"};
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zmailReverseProxyImapEnabledCapability new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void setReverseProxyImapEnabledCapability(String[] zmailReverseProxyImapEnabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapEnabledCapability, zmailReverseProxyImapEnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zmailReverseProxyImapEnabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> setReverseProxyImapEnabledCapability(String[] zmailReverseProxyImapEnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapEnabledCapability, zmailReverseProxyImapEnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zmailReverseProxyImapEnabledCapability new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void addReverseProxyImapEnabledCapability(String zmailReverseProxyImapEnabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyImapEnabledCapability, zmailReverseProxyImapEnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zmailReverseProxyImapEnabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> addReverseProxyImapEnabledCapability(String zmailReverseProxyImapEnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyImapEnabledCapability, zmailReverseProxyImapEnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zmailReverseProxyImapEnabledCapability existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void removeReverseProxyImapEnabledCapability(String zmailReverseProxyImapEnabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyImapEnabledCapability, zmailReverseProxyImapEnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @param zmailReverseProxyImapEnabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public Map<String,Object> removeReverseProxyImapEnabledCapability(String zmailReverseProxyImapEnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyImapEnabledCapability, zmailReverseProxyImapEnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy imap capabilities
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=719)
    public void unsetReverseProxyImapEnabledCapability() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapEnabledCapability, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyImapEnabledCapability, "");
        return attrs;
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @return zmailReverseProxyImapExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public boolean isReverseProxyImapExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyImapExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @param zmailReverseProxyImapExposeVersionOnBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public void setReverseProxyImapExposeVersionOnBanner(boolean zmailReverseProxyImapExposeVersionOnBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapExposeVersionOnBanner, zmailReverseProxyImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @param zmailReverseProxyImapExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public Map<String,Object> setReverseProxyImapExposeVersionOnBanner(boolean zmailReverseProxyImapExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapExposeVersionOnBanner, zmailReverseProxyImapExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on Proxy IMAP banner
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=713)
    public void unsetReverseProxyImapExposeVersionOnBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapExposeVersionOnBanner, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyImapExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @return zmailReverseProxyImapSaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public boolean isReverseProxyImapSaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyImapSaslGssapiEnabled, false);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @param zmailReverseProxyImapSaslGssapiEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public void setReverseProxyImapSaslGssapiEnabled(boolean zmailReverseProxyImapSaslGssapiEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslGssapiEnabled, zmailReverseProxyImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @param zmailReverseProxyImapSaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public Map<String,Object> setReverseProxyImapSaslGssapiEnabled(boolean zmailReverseProxyImapSaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslGssapiEnabled, zmailReverseProxyImapSaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SASL GSSAPI is enabled for reverse proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=643)
    public void unsetReverseProxyImapSaslGssapiEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslGssapiEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @return zmailReverseProxyImapSaslPlainEnabled, or true if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public boolean isReverseProxyImapSaslPlainEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyImapSaslPlainEnabled, true);
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @param zmailReverseProxyImapSaslPlainEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public void setReverseProxyImapSaslPlainEnabled(boolean zmailReverseProxyImapSaslPlainEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslPlainEnabled, zmailReverseProxyImapSaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @param zmailReverseProxyImapSaslPlainEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public Map<String,Object> setReverseProxyImapSaslPlainEnabled(boolean zmailReverseProxyImapSaslPlainEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslPlainEnabled, zmailReverseProxyImapSaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether IMAP SASL PLAIN is enabled for reverse proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=728)
    public void unsetReverseProxyImapSaslPlainEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslPlainEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyImapSaslPlainEnabled, "");
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zmailReverseProxyImapStartTlsMode, or ZAttrProvisioning.ReverseProxyImapStartTlsMode.only if unset and/or has invalid value
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public ZAttrProvisioning.ReverseProxyImapStartTlsMode getReverseProxyImapStartTlsMode() {
        try { String v = getAttr(Provisioning.A_zmailReverseProxyImapStartTlsMode); return v == null ? ZAttrProvisioning.ReverseProxyImapStartTlsMode.only : ZAttrProvisioning.ReverseProxyImapStartTlsMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyImapStartTlsMode.only; }
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zmailReverseProxyImapStartTlsMode, or "only" if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public String getReverseProxyImapStartTlsModeAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyImapStartTlsMode, "only");
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyImapStartTlsMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public void setReverseProxyImapStartTlsMode(ZAttrProvisioning.ReverseProxyImapStartTlsMode zmailReverseProxyImapStartTlsMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapStartTlsMode, zmailReverseProxyImapStartTlsMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyImapStartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public Map<String,Object> setReverseProxyImapStartTlsMode(ZAttrProvisioning.ReverseProxyImapStartTlsMode zmailReverseProxyImapStartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapStartTlsMode, zmailReverseProxyImapStartTlsMode.toString());
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyImapStartTlsMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public void setReverseProxyImapStartTlsModeAsString(String zmailReverseProxyImapStartTlsMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapStartTlsMode, zmailReverseProxyImapStartTlsMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyImapStartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public Map<String,Object> setReverseProxyImapStartTlsModeAsString(String zmailReverseProxyImapStartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapStartTlsMode, zmailReverseProxyImapStartTlsMode);
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=641)
    public void unsetReverseProxyImapStartTlsMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyImapStartTlsMode, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyImapStartTlsMode, "");
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
     * @return zmailReverseProxyInactivityTimeout in millseconds, or 3600000 (1h)  if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public long getReverseProxyInactivityTimeout() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyInactivityTimeout, 3600000L);
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
     * @return zmailReverseProxyInactivityTimeout, or "1h" if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public String getReverseProxyInactivityTimeoutAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyInactivityTimeout, "1h");
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
     * @param zmailReverseProxyInactivityTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public void setReverseProxyInactivityTimeout(String zmailReverseProxyInactivityTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyInactivityTimeout, zmailReverseProxyInactivityTimeout);
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
     * @param zmailReverseProxyInactivityTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public Map<String,Object> setReverseProxyInactivityTimeout(String zmailReverseProxyInactivityTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyInactivityTimeout, zmailReverseProxyInactivityTimeout);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=735)
    public void unsetReverseProxyInactivityTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyInactivityTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyInactivityTimeout, "");
        return attrs;
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @return zmailReverseProxyLogLevel, or ZAttrProvisioning.ReverseProxyLogLevel.info if unset and/or has invalid value
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public ZAttrProvisioning.ReverseProxyLogLevel getReverseProxyLogLevel() {
        try { String v = getAttr(Provisioning.A_zmailReverseProxyLogLevel); return v == null ? ZAttrProvisioning.ReverseProxyLogLevel.info : ZAttrProvisioning.ReverseProxyLogLevel.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyLogLevel.info; }
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @return zmailReverseProxyLogLevel, or "info" if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public String getReverseProxyLogLevelAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyLogLevel, "info");
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @param zmailReverseProxyLogLevel new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public void setReverseProxyLogLevel(ZAttrProvisioning.ReverseProxyLogLevel zmailReverseProxyLogLevel) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLogLevel, zmailReverseProxyLogLevel.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @param zmailReverseProxyLogLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public Map<String,Object> setReverseProxyLogLevel(ZAttrProvisioning.ReverseProxyLogLevel zmailReverseProxyLogLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLogLevel, zmailReverseProxyLogLevel.toString());
        return attrs;
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @param zmailReverseProxyLogLevel new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public void setReverseProxyLogLevelAsString(String zmailReverseProxyLogLevel) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLogLevel, zmailReverseProxyLogLevel);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @param zmailReverseProxyLogLevel new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public Map<String,Object> setReverseProxyLogLevelAsString(String zmailReverseProxyLogLevel, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLogLevel, zmailReverseProxyLogLevel);
        return attrs;
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public void unsetReverseProxyLogLevel() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLogLevel, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Log level for NGINX Proxy error log
     *
     * <p>Valid values: [warn, debug_http, error, crit, debug_mail, debug, debug_zmail, notice, debug_core, info]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=723)
    public Map<String,Object> unsetReverseProxyLogLevel(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLogLevel, "");
        return attrs;
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @return zmailReverseProxyLookupTarget, or false if unset
     */
    @ZAttr(id=504)
    public boolean isReverseProxyLookupTarget() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyLookupTarget, false);
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @param zmailReverseProxyLookupTarget new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=504)
    public void setReverseProxyLookupTarget(boolean zmailReverseProxyLookupTarget) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLookupTarget, zmailReverseProxyLookupTarget ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @param zmailReverseProxyLookupTarget new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=504)
    public Map<String,Object> setReverseProxyLookupTarget(boolean zmailReverseProxyLookupTarget, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLookupTarget, zmailReverseProxyLookupTarget ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether this server is a reverse proxy lookup target
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=504)
    public void unsetReverseProxyLookupTarget() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyLookupTarget, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyLookupTarget, "");
        return attrs;
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @return zmailReverseProxyMailEnabled, or true if unset
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public boolean isReverseProxyMailEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyMailEnabled, true);
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @param zmailReverseProxyMailEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public void setReverseProxyMailEnabled(boolean zmailReverseProxyMailEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailEnabled, zmailReverseProxyMailEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @param zmailReverseProxyMailEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public Map<String,Object> setReverseProxyMailEnabled(boolean zmailReverseProxyMailEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailEnabled, zmailReverseProxyMailEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to enable IMAP/POP proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.3
     */
    @ZAttr(id=629)
    public void unsetReverseProxyMailEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyMailEnabled, "");
        return attrs;
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zmailReverseProxyMailMode, or null if unset and/or has invalid value
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public ZAttrProvisioning.ReverseProxyMailMode getReverseProxyMailMode() {
        try { String v = getAttr(Provisioning.A_zmailReverseProxyMailMode); return v == null ? null : ZAttrProvisioning.ReverseProxyMailMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @return zmailReverseProxyMailMode, or null if unset
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public String getReverseProxyMailModeAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyMailMode, null);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailReverseProxyMailMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public void setReverseProxyMailMode(ZAttrProvisioning.ReverseProxyMailMode zmailReverseProxyMailMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailMode, zmailReverseProxyMailMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailReverseProxyMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public Map<String,Object> setReverseProxyMailMode(ZAttrProvisioning.ReverseProxyMailMode zmailReverseProxyMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailMode, zmailReverseProxyMailMode.toString());
        return attrs;
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailReverseProxyMailMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public void setReverseProxyMailModeAsString(String zmailReverseProxyMailMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailMode, zmailReverseProxyMailMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @param zmailReverseProxyMailMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public Map<String,Object> setReverseProxyMailModeAsString(String zmailReverseProxyMailMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailMode, zmailReverseProxyMailMode);
        return attrs;
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
     *
     * <p>Valid values: [https, mixed, redirect, both, http]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.7
     */
    @ZAttr(id=685)
    public void unsetReverseProxyMailMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyMailMode, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether to run proxy in HTTP, HTTPS, both, mixed, or redirect mode.
     * See also related attributes zmailMailProxyPort and
     * zmailMailSSLProxyPort
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
        attrs.put(Provisioning.A_zmailReverseProxyMailMode, "");
        return attrs;
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @return zmailReverseProxyPassErrors, or true if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public boolean isReverseProxyPassErrors() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyPassErrors, true);
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @param zmailReverseProxyPassErrors new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public void setReverseProxyPassErrors(boolean zmailReverseProxyPassErrors) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPassErrors, zmailReverseProxyPassErrors ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @param zmailReverseProxyPassErrors new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public Map<String,Object> setReverseProxyPassErrors(boolean zmailReverseProxyPassErrors, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPassErrors, zmailReverseProxyPassErrors ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether NGINX mail proxy will pass upstream server errors back to the
     * downstream email clients
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=736)
    public void unsetReverseProxyPassErrors() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPassErrors, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyPassErrors, "");
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @return zmailReverseProxyPop3EnabledCapability, or empty array if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public String[] getReverseProxyPop3EnabledCapability() {
        String[] value = getMultiAttr(Provisioning.A_zmailReverseProxyPop3EnabledCapability); return value.length > 0 ? value : new String[] {"TOP","USER","UIDL","EXPIRE 31 USER","XOIP"};
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zmailReverseProxyPop3EnabledCapability new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void setReverseProxyPop3EnabledCapability(String[] zmailReverseProxyPop3EnabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3EnabledCapability, zmailReverseProxyPop3EnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zmailReverseProxyPop3EnabledCapability new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> setReverseProxyPop3EnabledCapability(String[] zmailReverseProxyPop3EnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3EnabledCapability, zmailReverseProxyPop3EnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zmailReverseProxyPop3EnabledCapability new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void addReverseProxyPop3EnabledCapability(String zmailReverseProxyPop3EnabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyPop3EnabledCapability, zmailReverseProxyPop3EnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zmailReverseProxyPop3EnabledCapability new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> addReverseProxyPop3EnabledCapability(String zmailReverseProxyPop3EnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyPop3EnabledCapability, zmailReverseProxyPop3EnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zmailReverseProxyPop3EnabledCapability existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void removeReverseProxyPop3EnabledCapability(String zmailReverseProxyPop3EnabledCapability) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyPop3EnabledCapability, zmailReverseProxyPop3EnabledCapability);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @param zmailReverseProxyPop3EnabledCapability existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public Map<String,Object> removeReverseProxyPop3EnabledCapability(String zmailReverseProxyPop3EnabledCapability, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyPop3EnabledCapability, zmailReverseProxyPop3EnabledCapability);
        return attrs;
    }

    /**
     * NGINX reverse proxy pop3 capabilities
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=721)
    public void unsetReverseProxyPop3EnabledCapability() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3EnabledCapability, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyPop3EnabledCapability, "");
        return attrs;
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @return zmailReverseProxyPop3ExposeVersionOnBanner, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public boolean isReverseProxyPop3ExposeVersionOnBanner() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyPop3ExposeVersionOnBanner, false);
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @param zmailReverseProxyPop3ExposeVersionOnBanner new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public void setReverseProxyPop3ExposeVersionOnBanner(boolean zmailReverseProxyPop3ExposeVersionOnBanner) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3ExposeVersionOnBanner, zmailReverseProxyPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @param zmailReverseProxyPop3ExposeVersionOnBanner new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public Map<String,Object> setReverseProxyPop3ExposeVersionOnBanner(boolean zmailReverseProxyPop3ExposeVersionOnBanner, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3ExposeVersionOnBanner, zmailReverseProxyPop3ExposeVersionOnBanner ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to expose version on Proxy POP3 banner
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=712)
    public void unsetReverseProxyPop3ExposeVersionOnBanner() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3ExposeVersionOnBanner, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyPop3ExposeVersionOnBanner, "");
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @return zmailReverseProxyPop3SaslGssapiEnabled, or false if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public boolean isReverseProxyPop3SaslGssapiEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyPop3SaslGssapiEnabled, false);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @param zmailReverseProxyPop3SaslGssapiEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public void setReverseProxyPop3SaslGssapiEnabled(boolean zmailReverseProxyPop3SaslGssapiEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslGssapiEnabled, zmailReverseProxyPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @param zmailReverseProxyPop3SaslGssapiEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public Map<String,Object> setReverseProxyPop3SaslGssapiEnabled(boolean zmailReverseProxyPop3SaslGssapiEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslGssapiEnabled, zmailReverseProxyPop3SaslGssapiEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SASL GSSAPI is enabled for reverse proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=644)
    public void unsetReverseProxyPop3SaslGssapiEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslGssapiEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslGssapiEnabled, "");
        return attrs;
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @return zmailReverseProxyPop3SaslPlainEnabled, or true if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public boolean isReverseProxyPop3SaslPlainEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxyPop3SaslPlainEnabled, true);
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @param zmailReverseProxyPop3SaslPlainEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public void setReverseProxyPop3SaslPlainEnabled(boolean zmailReverseProxyPop3SaslPlainEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslPlainEnabled, zmailReverseProxyPop3SaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @param zmailReverseProxyPop3SaslPlainEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public Map<String,Object> setReverseProxyPop3SaslPlainEnabled(boolean zmailReverseProxyPop3SaslPlainEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslPlainEnabled, zmailReverseProxyPop3SaslPlainEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether POP3 SASL PLAIN is enabled for reverse proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=729)
    public void unsetReverseProxyPop3SaslPlainEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslPlainEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyPop3SaslPlainEnabled, "");
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zmailReverseProxyPop3StartTlsMode, or ZAttrProvisioning.ReverseProxyPop3StartTlsMode.only if unset and/or has invalid value
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public ZAttrProvisioning.ReverseProxyPop3StartTlsMode getReverseProxyPop3StartTlsMode() {
        try { String v = getAttr(Provisioning.A_zmailReverseProxyPop3StartTlsMode); return v == null ? ZAttrProvisioning.ReverseProxyPop3StartTlsMode.only : ZAttrProvisioning.ReverseProxyPop3StartTlsMode.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.ReverseProxyPop3StartTlsMode.only; }
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @return zmailReverseProxyPop3StartTlsMode, or "only" if unset
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public String getReverseProxyPop3StartTlsModeAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyPop3StartTlsMode, "only");
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyPop3StartTlsMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public void setReverseProxyPop3StartTlsMode(ZAttrProvisioning.ReverseProxyPop3StartTlsMode zmailReverseProxyPop3StartTlsMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3StartTlsMode, zmailReverseProxyPop3StartTlsMode.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyPop3StartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public Map<String,Object> setReverseProxyPop3StartTlsMode(ZAttrProvisioning.ReverseProxyPop3StartTlsMode zmailReverseProxyPop3StartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3StartTlsMode, zmailReverseProxyPop3StartTlsMode.toString());
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyPop3StartTlsMode new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public void setReverseProxyPop3StartTlsModeAsString(String zmailReverseProxyPop3StartTlsMode) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3StartTlsMode, zmailReverseProxyPop3StartTlsMode);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @param zmailReverseProxyPop3StartTlsMode new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public Map<String,Object> setReverseProxyPop3StartTlsModeAsString(String zmailReverseProxyPop3StartTlsMode, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3StartTlsMode, zmailReverseProxyPop3StartTlsMode);
        return attrs;
    }

    /**
     * on - on the plain POP/IMAP port, starttls is allowed off - no starttls
     * is offered on plain port only - you have to use starttls before clear
     * text login
     *
     * <p>Valid values: [off, only, on]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.5
     */
    @ZAttr(id=642)
    public void unsetReverseProxyPop3StartTlsMode() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyPop3StartTlsMode, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyPop3StartTlsMode, "");
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
     * @return zmailReverseProxyRouteLookupTimeout in millseconds, or 15000 (15s)  if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public long getReverseProxyRouteLookupTimeout() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyRouteLookupTimeout, 15000L);
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailReverseProxyRouteLookupTimeout, or "15s" if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public String getReverseProxyRouteLookupTimeoutAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyRouteLookupTimeout, "15s");
    }

    /**
     * Time interval after which NGINX will fail over to the next route
     * lookup handler, if a handler does not respond to the route lookup
     * request within this time. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailReverseProxyRouteLookupTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public void setReverseProxyRouteLookupTimeout(String zmailReverseProxyRouteLookupTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeout, zmailReverseProxyRouteLookupTimeout);
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
     * @param zmailReverseProxyRouteLookupTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public Map<String,Object> setReverseProxyRouteLookupTimeout(String zmailReverseProxyRouteLookupTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeout, zmailReverseProxyRouteLookupTimeout);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=745)
    public void unsetReverseProxyRouteLookupTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeout, "");
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
     * @return zmailReverseProxyRouteLookupTimeoutCache in millseconds, or 60000 (60s)  if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public long getReverseProxyRouteLookupTimeoutCache() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyRouteLookupTimeoutCache, 60000L);
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailReverseProxyRouteLookupTimeoutCache, or "60s" if unset
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public String getReverseProxyRouteLookupTimeoutCacheAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyRouteLookupTimeoutCache, "60s");
    }

    /**
     * Time interval (ms) given to mail route lookup handler to cache a
     * failed response to route a previous lookup request (after this time
     * elapses, Proxy retries this host). Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailReverseProxyRouteLookupTimeoutCache new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public void setReverseProxyRouteLookupTimeoutCache(String zmailReverseProxyRouteLookupTimeoutCache) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeoutCache, zmailReverseProxyRouteLookupTimeoutCache);
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
     * @param zmailReverseProxyRouteLookupTimeoutCache new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public Map<String,Object> setReverseProxyRouteLookupTimeoutCache(String zmailReverseProxyRouteLookupTimeoutCache, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeoutCache, zmailReverseProxyRouteLookupTimeoutCache);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.12
     */
    @ZAttr(id=778)
    public void unsetReverseProxyRouteLookupTimeoutCache() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeoutCache, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyRouteLookupTimeoutCache, "");
        return attrs;
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @return zmailReverseProxySSLToUpstreamEnabled, or true if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public boolean isReverseProxySSLToUpstreamEnabled() {
        return getBooleanAttr(Provisioning.A_zmailReverseProxySSLToUpstreamEnabled, true);
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @param zmailReverseProxySSLToUpstreamEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public void setReverseProxySSLToUpstreamEnabled(boolean zmailReverseProxySSLToUpstreamEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxySSLToUpstreamEnabled, zmailReverseProxySSLToUpstreamEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @param zmailReverseProxySSLToUpstreamEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public Map<String,Object> setReverseProxySSLToUpstreamEnabled(boolean zmailReverseProxySSLToUpstreamEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxySSLToUpstreamEnabled, zmailReverseProxySSLToUpstreamEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If set as TRUE, proxy will use SSL to connect to the upstream mail
     * servers for web and mail proxy. Note admin console proxy always use
     * https no matter how this attr is set.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1360)
    public void unsetReverseProxySSLToUpstreamEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxySSLToUpstreamEnabled, "");
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
        attrs.put(Provisioning.A_zmailReverseProxySSLToUpstreamEnabled, "");
        return attrs;
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @return zmailReverseProxyUpstreamConnectTimeout, or 25 if unset
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public int getReverseProxyUpstreamConnectTimeout() {
        return getIntAttr(Provisioning.A_zmailReverseProxyUpstreamConnectTimeout, 25);
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @param zmailReverseProxyUpstreamConnectTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public void setReverseProxyUpstreamConnectTimeout(int zmailReverseProxyUpstreamConnectTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamConnectTimeout, Integer.toString(zmailReverseProxyUpstreamConnectTimeout));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @param zmailReverseProxyUpstreamConnectTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public Map<String,Object> setReverseProxyUpstreamConnectTimeout(int zmailReverseProxyUpstreamConnectTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamConnectTimeout, Integer.toString(zmailReverseProxyUpstreamConnectTimeout));
        return attrs;
    }

    /**
     * The connect timeout is the time interval after which NGINX will
     * disconnect while establishing an upstream HTTP connection. Measured in
     * seconds, should not be more than 75 seconds.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.4
     */
    @ZAttr(id=1440)
    public void unsetReverseProxyUpstreamConnectTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamConnectTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamConnectTimeout, "");
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
     * @return zmailReverseProxyUpstreamPollingTimeout in millseconds, or 3600000 (1h)  if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public long getReverseProxyUpstreamPollingTimeout() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyUpstreamPollingTimeout, 3600000L);
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailReverseProxyUpstreamPollingTimeout, or "1h" if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public String getReverseProxyUpstreamPollingTimeoutAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyUpstreamPollingTimeout, "1h");
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailReverseProxyUpstreamPollingTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public void setReverseProxyUpstreamPollingTimeout(String zmailReverseProxyUpstreamPollingTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamPollingTimeout, zmailReverseProxyUpstreamPollingTimeout);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailReverseProxyUpstreamPollingTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public Map<String,Object> setReverseProxyUpstreamPollingTimeout(String zmailReverseProxyUpstreamPollingTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamPollingTimeout, zmailReverseProxyUpstreamPollingTimeout);
        return attrs;
    }

    /**
     * The read timeout for long polling support by proxy, e.g. ActiveSync
     * for mobile devices. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1337)
    public void unsetReverseProxyUpstreamPollingTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamPollingTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamPollingTimeout, "");
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
     * @return zmailReverseProxyUpstreamReadTimeout in millseconds, or 60000 (60s)  if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public long getReverseProxyUpstreamReadTimeout() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyUpstreamReadTimeout, 60000L);
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @return zmailReverseProxyUpstreamReadTimeout, or "60s" if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public String getReverseProxyUpstreamReadTimeoutAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyUpstreamReadTimeout, "60s");
    }

    /**
     * The read timeout for the response of upstream server, which determines
     * how long nginx will wait to get the response to a request. . Must be
     * in valid duration format: {digits}{time-unit}. digits: 0-9, time-unit:
     * [hmsd]|ms. h - hours, m - minutes, s - seconds, d - days, ms -
     * milliseconds. If time unit is not specified, the default is
     * s(seconds).
     *
     * @param zmailReverseProxyUpstreamReadTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public void setReverseProxyUpstreamReadTimeout(String zmailReverseProxyUpstreamReadTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamReadTimeout, zmailReverseProxyUpstreamReadTimeout);
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
     * @param zmailReverseProxyUpstreamReadTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public Map<String,Object> setReverseProxyUpstreamReadTimeout(String zmailReverseProxyUpstreamReadTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamReadTimeout, zmailReverseProxyUpstreamReadTimeout);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1335)
    public void unsetReverseProxyUpstreamReadTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamReadTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamReadTimeout, "");
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
     * @return zmailReverseProxyUpstreamSendTimeout in millseconds, or 60000 (60s)  if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public long getReverseProxyUpstreamSendTimeout() {
        return getTimeInterval(Provisioning.A_zmailReverseProxyUpstreamSendTimeout, 60000L);
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailReverseProxyUpstreamSendTimeout, or "60s" if unset
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public String getReverseProxyUpstreamSendTimeoutAsString() {
        return getAttr(Provisioning.A_zmailReverseProxyUpstreamSendTimeout, "60s");
    }

    /**
     * The send timeout of transfering a request to the upstream server. If
     * after this time the upstream server doesn&#039;t take new data, proxy
     * will close the connection. . Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailReverseProxyUpstreamSendTimeout new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public void setReverseProxyUpstreamSendTimeout(String zmailReverseProxyUpstreamSendTimeout) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamSendTimeout, zmailReverseProxyUpstreamSendTimeout);
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
     * @param zmailReverseProxyUpstreamSendTimeout new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public Map<String,Object> setReverseProxyUpstreamSendTimeout(String zmailReverseProxyUpstreamSendTimeout, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamSendTimeout, zmailReverseProxyUpstreamSendTimeout);
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
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.1.4
     */
    @ZAttr(id=1336)
    public void unsetReverseProxyUpstreamSendTimeout() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamSendTimeout, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamSendTimeout, "");
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @return zmailReverseProxyUpstreamServers, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public String[] getReverseProxyUpstreamServers() {
        return getMultiAttr(Provisioning.A_zmailReverseProxyUpstreamServers);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param zmailReverseProxyUpstreamServers new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void setReverseProxyUpstreamServers(String[] zmailReverseProxyUpstreamServers) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamServers, zmailReverseProxyUpstreamServers);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param zmailReverseProxyUpstreamServers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> setReverseProxyUpstreamServers(String[] zmailReverseProxyUpstreamServers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamServers, zmailReverseProxyUpstreamServers);
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param zmailReverseProxyUpstreamServers new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void addReverseProxyUpstreamServers(String zmailReverseProxyUpstreamServers) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyUpstreamServers, zmailReverseProxyUpstreamServers);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param zmailReverseProxyUpstreamServers new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> addReverseProxyUpstreamServers(String zmailReverseProxyUpstreamServers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailReverseProxyUpstreamServers, zmailReverseProxyUpstreamServers);
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param zmailReverseProxyUpstreamServers existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void removeReverseProxyUpstreamServers(String zmailReverseProxyUpstreamServers) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyUpstreamServers, zmailReverseProxyUpstreamServers);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param zmailReverseProxyUpstreamServers existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> removeReverseProxyUpstreamServers(String zmailReverseProxyUpstreamServers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailReverseProxyUpstreamServers, zmailReverseProxyUpstreamServers);
        return attrs;
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public void unsetReverseProxyUpstreamServers() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamServers, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The servers to be included in the &quot;upstream&quot; block in the
     * nginx web proxy config file. The servers configured here will only
     * affect the proxy of pre-login requests. Leaving empty means using all
     * the servers whose zmailReverseProxyLookupTarget is TRUE.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1378)
    public Map<String,Object> unsetReverseProxyUpstreamServers(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyUpstreamServers, "");
        return attrs;
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @return zmailReverseProxyWorkerConnections, or 10240 if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public int getReverseProxyWorkerConnections() {
        return getIntAttr(Provisioning.A_zmailReverseProxyWorkerConnections, 10240);
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @param zmailReverseProxyWorkerConnections new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public void setReverseProxyWorkerConnections(int zmailReverseProxyWorkerConnections) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyWorkerConnections, Integer.toString(zmailReverseProxyWorkerConnections));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @param zmailReverseProxyWorkerConnections new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public Map<String,Object> setReverseProxyWorkerConnections(int zmailReverseProxyWorkerConnections, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyWorkerConnections, Integer.toString(zmailReverseProxyWorkerConnections));
        return attrs;
    }

    /**
     * Maximum number of connections that an NGINX Proxy worker process is
     * allowed to handle
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=725)
    public void unsetReverseProxyWorkerConnections() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyWorkerConnections, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyWorkerConnections, "");
        return attrs;
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @return zmailReverseProxyWorkerProcesses, or 4 if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public int getReverseProxyWorkerProcesses() {
        return getIntAttr(Provisioning.A_zmailReverseProxyWorkerProcesses, 4);
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @param zmailReverseProxyWorkerProcesses new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public void setReverseProxyWorkerProcesses(int zmailReverseProxyWorkerProcesses) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyWorkerProcesses, Integer.toString(zmailReverseProxyWorkerProcesses));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @param zmailReverseProxyWorkerProcesses new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public Map<String,Object> setReverseProxyWorkerProcesses(int zmailReverseProxyWorkerProcesses, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyWorkerProcesses, Integer.toString(zmailReverseProxyWorkerProcesses));
        return attrs;
    }

    /**
     * Number of worker processes of NGINX Proxy
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=724)
    public void unsetReverseProxyWorkerProcesses() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailReverseProxyWorkerProcesses, "");
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
        attrs.put(Provisioning.A_zmailReverseProxyWorkerProcesses, "");
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
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @return zmailSaslGssapiRequiresTls, or false if unset
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public boolean isSaslGssapiRequiresTls() {
        return getBooleanAttr(Provisioning.A_zmailSaslGssapiRequiresTls, false);
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @param zmailSaslGssapiRequiresTls new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public void setSaslGssapiRequiresTls(boolean zmailSaslGssapiRequiresTls) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSaslGssapiRequiresTls, zmailSaslGssapiRequiresTls ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @param zmailSaslGssapiRequiresTls new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public Map<String,Object> setSaslGssapiRequiresTls(boolean zmailSaslGssapiRequiresTls, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSaslGssapiRequiresTls, zmailSaslGssapiRequiresTls ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether TLS is required for IMAP/POP GSSAPI auth
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.20
     */
    @ZAttr(id=1068)
    public void unsetSaslGssapiRequiresTls() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSaslGssapiRequiresTls, "");
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
        attrs.put(Provisioning.A_zmailSaslGssapiRequiresTls, "");
        return attrs;
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @return zmailScheduledTaskNumThreads, or 20 if unset
     */
    @ZAttr(id=522)
    public int getScheduledTaskNumThreads() {
        return getIntAttr(Provisioning.A_zmailScheduledTaskNumThreads, 20);
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @param zmailScheduledTaskNumThreads new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=522)
    public void setScheduledTaskNumThreads(int zmailScheduledTaskNumThreads) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailScheduledTaskNumThreads, Integer.toString(zmailScheduledTaskNumThreads));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @param zmailScheduledTaskNumThreads new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=522)
    public Map<String,Object> setScheduledTaskNumThreads(int zmailScheduledTaskNumThreads, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailScheduledTaskNumThreads, Integer.toString(zmailScheduledTaskNumThreads));
        return attrs;
    }

    /**
     * Maximum number of scheduled tasks that can run simultaneously.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=522)
    public void unsetScheduledTaskNumThreads() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailScheduledTaskNumThreads, "");
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
        attrs.put(Provisioning.A_zmailScheduledTaskNumThreads, "");
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @return zmailServiceEnabled, or empty array if unset
     */
    @ZAttr(id=220)
    public String[] getServiceEnabled() {
        return getMultiAttr(Provisioning.A_zmailServiceEnabled);
    }

    /**
     * services that are enabled on this server
     *
     * @param zmailServiceEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void setServiceEnabled(String[] zmailServiceEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceEnabled, zmailServiceEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param zmailServiceEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> setServiceEnabled(String[] zmailServiceEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceEnabled, zmailServiceEnabled);
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @param zmailServiceEnabled new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void addServiceEnabled(String zmailServiceEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailServiceEnabled, zmailServiceEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param zmailServiceEnabled new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> addServiceEnabled(String zmailServiceEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailServiceEnabled, zmailServiceEnabled);
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @param zmailServiceEnabled existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void removeServiceEnabled(String zmailServiceEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailServiceEnabled, zmailServiceEnabled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are enabled on this server
     *
     * @param zmailServiceEnabled existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=220)
    public Map<String,Object> removeServiceEnabled(String zmailServiceEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailServiceEnabled, zmailServiceEnabled);
        return attrs;
    }

    /**
     * services that are enabled on this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=220)
    public void unsetServiceEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceEnabled, "");
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
        attrs.put(Provisioning.A_zmailServiceEnabled, "");
        return attrs;
    }

    /**
     * public hostname of the host
     *
     * @return zmailServiceHostname, or null if unset
     */
    @ZAttr(id=65)
    public String getServiceHostname() {
        return getAttr(Provisioning.A_zmailServiceHostname, null);
    }

    /**
     * public hostname of the host
     *
     * @param zmailServiceHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=65)
    public void setServiceHostname(String zmailServiceHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceHostname, zmailServiceHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * public hostname of the host
     *
     * @param zmailServiceHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=65)
    public Map<String,Object> setServiceHostname(String zmailServiceHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceHostname, zmailServiceHostname);
        return attrs;
    }

    /**
     * public hostname of the host
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=65)
    public void unsetServiceHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceHostname, "");
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
        attrs.put(Provisioning.A_zmailServiceHostname, "");
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @return zmailServiceInstalled, or empty array if unset
     */
    @ZAttr(id=221)
    public String[] getServiceInstalled() {
        return getMultiAttr(Provisioning.A_zmailServiceInstalled);
    }

    /**
     * services that are installed on this server
     *
     * @param zmailServiceInstalled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void setServiceInstalled(String[] zmailServiceInstalled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceInstalled, zmailServiceInstalled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param zmailServiceInstalled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> setServiceInstalled(String[] zmailServiceInstalled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceInstalled, zmailServiceInstalled);
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @param zmailServiceInstalled new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void addServiceInstalled(String zmailServiceInstalled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailServiceInstalled, zmailServiceInstalled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param zmailServiceInstalled new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> addServiceInstalled(String zmailServiceInstalled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailServiceInstalled, zmailServiceInstalled);
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @param zmailServiceInstalled existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void removeServiceInstalled(String zmailServiceInstalled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailServiceInstalled, zmailServiceInstalled);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * services that are installed on this server
     *
     * @param zmailServiceInstalled existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=221)
    public Map<String,Object> removeServiceInstalled(String zmailServiceInstalled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailServiceInstalled, zmailServiceInstalled);
        return attrs;
    }

    /**
     * services that are installed on this server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=221)
    public void unsetServiceInstalled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailServiceInstalled, "");
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
        attrs.put(Provisioning.A_zmailServiceInstalled, "");
        return attrs;
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @return zmailShareNotificationMtaAuthAccount, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public String getShareNotificationMtaAuthAccount() {
        return getAttr(Provisioning.A_zmailShareNotificationMtaAuthAccount, null);
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @param zmailShareNotificationMtaAuthAccount new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public void setShareNotificationMtaAuthAccount(String zmailShareNotificationMtaAuthAccount) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthAccount, zmailShareNotificationMtaAuthAccount);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @param zmailShareNotificationMtaAuthAccount new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public Map<String,Object> setShareNotificationMtaAuthAccount(String zmailShareNotificationMtaAuthAccount, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthAccount, zmailShareNotificationMtaAuthAccount);
        return attrs;
    }

    /**
     * Account name for authenticating to share notification MTA.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1343)
    public void unsetShareNotificationMtaAuthAccount() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthAccount, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthAccount, "");
        return attrs;
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @return zmailShareNotificationMtaAuthPassword, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public String getShareNotificationMtaAuthPassword() {
        return getAttr(Provisioning.A_zmailShareNotificationMtaAuthPassword, null);
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @param zmailShareNotificationMtaAuthPassword new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public void setShareNotificationMtaAuthPassword(String zmailShareNotificationMtaAuthPassword) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthPassword, zmailShareNotificationMtaAuthPassword);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @param zmailShareNotificationMtaAuthPassword new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public Map<String,Object> setShareNotificationMtaAuthPassword(String zmailShareNotificationMtaAuthPassword, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthPassword, zmailShareNotificationMtaAuthPassword);
        return attrs;
    }

    /**
     * Password for authenticating to share notification MTA.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1344)
    public void unsetShareNotificationMtaAuthPassword() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthPassword, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthPassword, "");
        return attrs;
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @return zmailShareNotificationMtaAuthRequired, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public boolean isShareNotificationMtaAuthRequired() {
        return getBooleanAttr(Provisioning.A_zmailShareNotificationMtaAuthRequired, false);
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @param zmailShareNotificationMtaAuthRequired new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public void setShareNotificationMtaAuthRequired(boolean zmailShareNotificationMtaAuthRequired) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthRequired, zmailShareNotificationMtaAuthRequired ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @param zmailShareNotificationMtaAuthRequired new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public Map<String,Object> setShareNotificationMtaAuthRequired(boolean zmailShareNotificationMtaAuthRequired, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthRequired, zmailShareNotificationMtaAuthRequired ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to use credential to authenticate to share notification MTA.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1346)
    public void unsetShareNotificationMtaAuthRequired() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthRequired, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaAuthRequired, "");
        return attrs;
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @return zmailShareNotificationMtaConnectionType, or ZAttrProvisioning.ShareNotificationMtaConnectionType.CLEARTEXT if unset and/or has invalid value
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public ZAttrProvisioning.ShareNotificationMtaConnectionType getShareNotificationMtaConnectionType() {
        try { String v = getAttr(Provisioning.A_zmailShareNotificationMtaConnectionType); return v == null ? ZAttrProvisioning.ShareNotificationMtaConnectionType.CLEARTEXT : ZAttrProvisioning.ShareNotificationMtaConnectionType.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.ShareNotificationMtaConnectionType.CLEARTEXT; }
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @return zmailShareNotificationMtaConnectionType, or "CLEARTEXT" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public String getShareNotificationMtaConnectionTypeAsString() {
        return getAttr(Provisioning.A_zmailShareNotificationMtaConnectionType, "CLEARTEXT");
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zmailShareNotificationMtaConnectionType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public void setShareNotificationMtaConnectionType(ZAttrProvisioning.ShareNotificationMtaConnectionType zmailShareNotificationMtaConnectionType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaConnectionType, zmailShareNotificationMtaConnectionType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zmailShareNotificationMtaConnectionType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public Map<String,Object> setShareNotificationMtaConnectionType(ZAttrProvisioning.ShareNotificationMtaConnectionType zmailShareNotificationMtaConnectionType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaConnectionType, zmailShareNotificationMtaConnectionType.toString());
        return attrs;
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zmailShareNotificationMtaConnectionType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public void setShareNotificationMtaConnectionTypeAsString(String zmailShareNotificationMtaConnectionType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaConnectionType, zmailShareNotificationMtaConnectionType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @param zmailShareNotificationMtaConnectionType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public Map<String,Object> setShareNotificationMtaConnectionTypeAsString(String zmailShareNotificationMtaConnectionType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaConnectionType, zmailShareNotificationMtaConnectionType);
        return attrs;
    }

    /**
     * Connection mode when connecting to share notification MTA.
     *
     * <p>Valid values: [SSL, CLEARTEXT, STARTTLS]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1345)
    public void unsetShareNotificationMtaConnectionType() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaConnectionType, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaConnectionType, "");
        return attrs;
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @return zmailShareNotificationMtaEnabled, or false if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public boolean isShareNotificationMtaEnabled() {
        return getBooleanAttr(Provisioning.A_zmailShareNotificationMtaEnabled, false);
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @param zmailShareNotificationMtaEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public void setShareNotificationMtaEnabled(boolean zmailShareNotificationMtaEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaEnabled, zmailShareNotificationMtaEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @param zmailShareNotificationMtaEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public Map<String,Object> setShareNotificationMtaEnabled(boolean zmailShareNotificationMtaEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaEnabled, zmailShareNotificationMtaEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether share notification MTA is enabled.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1361)
    public void unsetShareNotificationMtaEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaEnabled, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaEnabled, "");
        return attrs;
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @return zmailShareNotificationMtaHostname, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public String getShareNotificationMtaHostname() {
        return getAttr(Provisioning.A_zmailShareNotificationMtaHostname, null);
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @param zmailShareNotificationMtaHostname new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public void setShareNotificationMtaHostname(String zmailShareNotificationMtaHostname) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaHostname, zmailShareNotificationMtaHostname);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @param zmailShareNotificationMtaHostname new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public Map<String,Object> setShareNotificationMtaHostname(String zmailShareNotificationMtaHostname, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaHostname, zmailShareNotificationMtaHostname);
        return attrs;
    }

    /**
     * SMTP hostname for share notification MTA used for sending email
     * notifications.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1341)
    public void unsetShareNotificationMtaHostname() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaHostname, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaHostname, "");
        return attrs;
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @return zmailShareNotificationMtaPort, or -1 if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public int getShareNotificationMtaPort() {
        return getIntAttr(Provisioning.A_zmailShareNotificationMtaPort, -1);
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @param zmailShareNotificationMtaPort new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public void setShareNotificationMtaPort(int zmailShareNotificationMtaPort) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaPort, Integer.toString(zmailShareNotificationMtaPort));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @param zmailShareNotificationMtaPort new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public Map<String,Object> setShareNotificationMtaPort(int zmailShareNotificationMtaPort, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaPort, Integer.toString(zmailShareNotificationMtaPort));
        return attrs;
    }

    /**
     * SMTP port for share notification MTA used for sending email
     * notifications.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1342)
    public void unsetShareNotificationMtaPort() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareNotificationMtaPort, "");
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
        attrs.put(Provisioning.A_zmailShareNotificationMtaPort, "");
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
     * @return zmailSharingUpdatePublishInterval in millseconds, or 900000 (15m)  if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public long getSharingUpdatePublishInterval() {
        return getTimeInterval(Provisioning.A_zmailSharingUpdatePublishInterval, 900000L);
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @return zmailSharingUpdatePublishInterval, or "15m" if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public String getSharingUpdatePublishIntervalAsString() {
        return getAttr(Provisioning.A_zmailSharingUpdatePublishInterval, "15m");
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailSharingUpdatePublishInterval new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public void setSharingUpdatePublishInterval(String zmailSharingUpdatePublishInterval) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSharingUpdatePublishInterval, zmailSharingUpdatePublishInterval);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @param zmailSharingUpdatePublishInterval new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public Map<String,Object> setSharingUpdatePublishInterval(String zmailSharingUpdatePublishInterval, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSharingUpdatePublishInterval, zmailSharingUpdatePublishInterval);
        return attrs;
    }

    /**
     * Interval between successive executions of the task that publishes
     * shared item updates to LDAP. Must be in valid duration format:
     * {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h - hours, m -
     * minutes, s - seconds, d - days, ms - milliseconds. If time unit is not
     * specified, the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1220)
    public void unsetSharingUpdatePublishInterval() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSharingUpdatePublishInterval, "");
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
        attrs.put(Provisioning.A_zmailSharingUpdatePublishInterval, "");
        return attrs;
    }

    /**
     * the SMTP server to connect to when sending mail
     *
     * @return zmailSmtpHostname, or empty array if unset
     */
    @ZAttr(id=97)
    public String[] getSmtpHostname() {
        String[] value = getMultiAttr(Provisioning.A_zmailSmtpHostname); return value.length > 0 ? value : new String[] {"localhost"};
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
     * @return zmailSmtpPort, or 25 if unset
     */
    @ZAttr(id=98)
    public int getSmtpPort() {
        return getIntAttr(Provisioning.A_zmailSmtpPort, 25);
    }

    /**
     * the SMTP server port to connect to when sending mail
     *
     * @return zmailSmtpPort, or "25" if unset
     */
    @ZAttr(id=98)
    public String getSmtpPortAsString() {
        return getAttr(Provisioning.A_zmailSmtpPort, "25");
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
     * @return zmailSmtpTimeout, or 60 if unset
     */
    @ZAttr(id=99)
    public int getSmtpTimeout() {
        return getIntAttr(Provisioning.A_zmailSmtpTimeout, 60);
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
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @return zmailSoapExposeVersion, or false if unset
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public boolean isSoapExposeVersion() {
        return getBooleanAttr(Provisioning.A_zmailSoapExposeVersion, false);
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @param zmailSoapExposeVersion new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public void setSoapExposeVersion(boolean zmailSoapExposeVersion) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSoapExposeVersion, zmailSoapExposeVersion ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @param zmailSoapExposeVersion new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public Map<String,Object> setSoapExposeVersion(boolean zmailSoapExposeVersion, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSoapExposeVersion, zmailSoapExposeVersion ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * If TRUE, enables support for GetVersionInfo for account SOAP requests.
     * If FALSE, GetVersionInfoRequest returns a SOAP fault.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.10
     */
    @ZAttr(id=708)
    public void unsetSoapExposeVersion() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSoapExposeVersion, "");
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
        attrs.put(Provisioning.A_zmailSoapExposeVersion, "");
        return attrs;
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @return zmailSoapRequestMaxSize, or 15360000 if unset
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public int getSoapRequestMaxSize() {
        return getIntAttr(Provisioning.A_zmailSoapRequestMaxSize, 15360000);
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @param zmailSoapRequestMaxSize new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public void setSoapRequestMaxSize(int zmailSoapRequestMaxSize) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSoapRequestMaxSize, Integer.toString(zmailSoapRequestMaxSize));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @param zmailSoapRequestMaxSize new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public Map<String,Object> setSoapRequestMaxSize(int zmailSoapRequestMaxSize, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSoapRequestMaxSize, Integer.toString(zmailSoapRequestMaxSize));
        return attrs;
    }

    /**
     * Maximum size in bytes for incoming SOAP requests. 0 means no limit.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.0
     */
    @ZAttr(id=557)
    public void unsetSoapRequestMaxSize() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSoapRequestMaxSize, "");
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
        attrs.put(Provisioning.A_zmailSoapRequestMaxSize, "");
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @return zmailSpellAvailableDictionary, or empty array if unset
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public String[] getSpellAvailableDictionary() {
        String[] value = getMultiAttr(Provisioning.A_zmailSpellAvailableDictionary); return value.length > 0 ? value : new String[] {"en_US"};
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zmailSpellAvailableDictionary new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void setSpellAvailableDictionary(String[] zmailSpellAvailableDictionary) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpellAvailableDictionary, zmailSpellAvailableDictionary);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zmailSpellAvailableDictionary new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> setSpellAvailableDictionary(String[] zmailSpellAvailableDictionary, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpellAvailableDictionary, zmailSpellAvailableDictionary);
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zmailSpellAvailableDictionary new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void addSpellAvailableDictionary(String zmailSpellAvailableDictionary) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSpellAvailableDictionary, zmailSpellAvailableDictionary);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zmailSpellAvailableDictionary new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> addSpellAvailableDictionary(String zmailSpellAvailableDictionary, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSpellAvailableDictionary, zmailSpellAvailableDictionary);
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zmailSpellAvailableDictionary existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void removeSpellAvailableDictionary(String zmailSpellAvailableDictionary) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSpellAvailableDictionary, zmailSpellAvailableDictionary);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @param zmailSpellAvailableDictionary existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public Map<String,Object> removeSpellAvailableDictionary(String zmailSpellAvailableDictionary, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSpellAvailableDictionary, zmailSpellAvailableDictionary);
        return attrs;
    }

    /**
     * The list of available dictionaries that can be used for spell
     * checking.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_GA
     */
    @ZAttr(id=1042)
    public void unsetSpellAvailableDictionary() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpellAvailableDictionary, "");
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
        attrs.put(Provisioning.A_zmailSpellAvailableDictionary, "");
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @return zmailSpellCheckURL, or empty array if unset
     */
    @ZAttr(id=267)
    public String[] getSpellCheckURL() {
        return getMultiAttr(Provisioning.A_zmailSpellCheckURL);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zmailSpellCheckURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void setSpellCheckURL(String[] zmailSpellCheckURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpellCheckURL, zmailSpellCheckURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zmailSpellCheckURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> setSpellCheckURL(String[] zmailSpellCheckURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpellCheckURL, zmailSpellCheckURL);
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zmailSpellCheckURL new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void addSpellCheckURL(String zmailSpellCheckURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSpellCheckURL, zmailSpellCheckURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zmailSpellCheckURL new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> addSpellCheckURL(String zmailSpellCheckURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailSpellCheckURL, zmailSpellCheckURL);
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zmailSpellCheckURL existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void removeSpellCheckURL(String zmailSpellCheckURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSpellCheckURL, zmailSpellCheckURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @param zmailSpellCheckURL existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=267)
    public Map<String,Object> removeSpellCheckURL(String zmailSpellCheckURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailSpellCheckURL, zmailSpellCheckURL);
        return attrs;
    }

    /**
     * URL of the server running the spell checking service. Multi-valued
     * attribute that allows multiple spell check servers to be specified. If
     * the request to the first server fails, a request to the second server
     * is sent and so on.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=267)
    public void unsetSpellCheckURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpellCheckURL, "");
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
        attrs.put(Provisioning.A_zmailSpellCheckURL, "");
        return attrs;
    }

    /**
     * spnego auth principal
     *
     * @return zmailSpnegoAuthPrincipal, or null if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public String getSpnegoAuthPrincipal() {
        return getAttr(Provisioning.A_zmailSpnegoAuthPrincipal, null);
    }

    /**
     * spnego auth principal
     *
     * @param zmailSpnegoAuthPrincipal new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public void setSpnegoAuthPrincipal(String zmailSpnegoAuthPrincipal) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpnegoAuthPrincipal, zmailSpnegoAuthPrincipal);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * spnego auth principal
     *
     * @param zmailSpnegoAuthPrincipal new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public Map<String,Object> setSpnegoAuthPrincipal(String zmailSpnegoAuthPrincipal, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpnegoAuthPrincipal, zmailSpnegoAuthPrincipal);
        return attrs;
    }

    /**
     * spnego auth principal
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1122)
    public void unsetSpnegoAuthPrincipal() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpnegoAuthPrincipal, "");
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
        attrs.put(Provisioning.A_zmailSpnegoAuthPrincipal, "");
        return attrs;
    }

    /**
     * spnego auth target name
     *
     * @return zmailSpnegoAuthTargetName, or null if unset
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public String getSpnegoAuthTargetName() {
        return getAttr(Provisioning.A_zmailSpnegoAuthTargetName, null);
    }

    /**
     * spnego auth target name
     *
     * @param zmailSpnegoAuthTargetName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public void setSpnegoAuthTargetName(String zmailSpnegoAuthTargetName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpnegoAuthTargetName, zmailSpnegoAuthTargetName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * spnego auth target name
     *
     * @param zmailSpnegoAuthTargetName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public Map<String,Object> setSpnegoAuthTargetName(String zmailSpnegoAuthTargetName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpnegoAuthTargetName, zmailSpnegoAuthTargetName);
        return attrs;
    }

    /**
     * spnego auth target name
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 7.0.0
     */
    @ZAttr(id=1123)
    public void unsetSpnegoAuthTargetName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSpnegoAuthTargetName, "");
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
        attrs.put(Provisioning.A_zmailSpnegoAuthTargetName, "");
        return attrs;
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @return zmailSshPublicKey, or null if unset
     */
    @ZAttr(id=262)
    public String getSshPublicKey() {
        return getAttr(Provisioning.A_zmailSshPublicKey, null);
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @param zmailSshPublicKey new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=262)
    public void setSshPublicKey(String zmailSshPublicKey) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSshPublicKey, zmailSshPublicKey);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @param zmailSshPublicKey new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=262)
    public Map<String,Object> setSshPublicKey(String zmailSshPublicKey, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSshPublicKey, zmailSshPublicKey);
        return attrs;
    }

    /**
     * Public key of this server, used by other hosts to authorize this
     * server to login.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=262)
    public void unsetSshPublicKey() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailSshPublicKey, "");
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
        attrs.put(Provisioning.A_zmailSshPublicKey, "");
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @return zmailStatThreadNamePrefix, or empty array if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public String[] getStatThreadNamePrefix() {
        String[] value = getMultiAttr(Provisioning.A_zmailStatThreadNamePrefix); return value.length > 0 ? value : new String[] {"btpool","pool","LmtpServer","ImapServer","ImapSSLServer","Pop3Server","Pop3SSLServer","ScheduledTask","Timer","AnonymousIoService","CloudRoutingReaderThread","GC","SocketAcceptor","Thread"};
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zmailStatThreadNamePrefix new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void setStatThreadNamePrefix(String[] zmailStatThreadNamePrefix) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStatThreadNamePrefix, zmailStatThreadNamePrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zmailStatThreadNamePrefix new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> setStatThreadNamePrefix(String[] zmailStatThreadNamePrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStatThreadNamePrefix, zmailStatThreadNamePrefix);
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zmailStatThreadNamePrefix new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void addStatThreadNamePrefix(String zmailStatThreadNamePrefix) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailStatThreadNamePrefix, zmailStatThreadNamePrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zmailStatThreadNamePrefix new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> addStatThreadNamePrefix(String zmailStatThreadNamePrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailStatThreadNamePrefix, zmailStatThreadNamePrefix);
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zmailStatThreadNamePrefix existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void removeStatThreadNamePrefix(String zmailStatThreadNamePrefix) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailStatThreadNamePrefix, zmailStatThreadNamePrefix);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @param zmailStatThreadNamePrefix existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public Map<String,Object> removeStatThreadNamePrefix(String zmailStatThreadNamePrefix, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailStatThreadNamePrefix, zmailStatThreadNamePrefix);
        return attrs;
    }

    /**
     * Prefixes of thread names. Each value is a column in threads.csv that
     * tracks the number of threads whose name starts with the given prefix.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=792)
    public void unsetStatThreadNamePrefix() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailStatThreadNamePrefix, "");
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
        attrs.put(Provisioning.A_zmailStatThreadNamePrefix, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @return zmailTableMaintenanceGrowthFactor, or 10 if unset
     */
    @ZAttr(id=171)
    public int getTableMaintenanceGrowthFactor() {
        return getIntAttr(Provisioning.A_zmailTableMaintenanceGrowthFactor, 10);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @param zmailTableMaintenanceGrowthFactor new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=171)
    public void setTableMaintenanceGrowthFactor(int zmailTableMaintenanceGrowthFactor) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceGrowthFactor, Integer.toString(zmailTableMaintenanceGrowthFactor));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @param zmailTableMaintenanceGrowthFactor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=171)
    public Map<String,Object> setTableMaintenanceGrowthFactor(int zmailTableMaintenanceGrowthFactor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceGrowthFactor, Integer.toString(zmailTableMaintenanceGrowthFactor));
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance will be performed if the
     * number of rows grows by this factor
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=171)
    public void unsetTableMaintenanceGrowthFactor() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceGrowthFactor, "");
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
        attrs.put(Provisioning.A_zmailTableMaintenanceGrowthFactor, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @return zmailTableMaintenanceMaxRows, or 1000000 if unset
     */
    @ZAttr(id=169)
    public int getTableMaintenanceMaxRows() {
        return getIntAttr(Provisioning.A_zmailTableMaintenanceMaxRows, 1000000);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @param zmailTableMaintenanceMaxRows new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=169)
    public void setTableMaintenanceMaxRows(int zmailTableMaintenanceMaxRows) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceMaxRows, Integer.toString(zmailTableMaintenanceMaxRows));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @param zmailTableMaintenanceMaxRows new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=169)
    public Map<String,Object> setTableMaintenanceMaxRows(int zmailTableMaintenanceMaxRows, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceMaxRows, Integer.toString(zmailTableMaintenanceMaxRows));
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: maximum number of rows required for database
     * table maintenance
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=169)
    public void unsetTableMaintenanceMaxRows() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceMaxRows, "");
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
        attrs.put(Provisioning.A_zmailTableMaintenanceMaxRows, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @return zmailTableMaintenanceMinRows, or 10000 if unset
     */
    @ZAttr(id=168)
    public int getTableMaintenanceMinRows() {
        return getIntAttr(Provisioning.A_zmailTableMaintenanceMinRows, 10000);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @param zmailTableMaintenanceMinRows new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=168)
    public void setTableMaintenanceMinRows(int zmailTableMaintenanceMinRows) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceMinRows, Integer.toString(zmailTableMaintenanceMinRows));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @param zmailTableMaintenanceMinRows new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=168)
    public Map<String,Object> setTableMaintenanceMinRows(int zmailTableMaintenanceMinRows, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceMinRows, Integer.toString(zmailTableMaintenanceMinRows));
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: minimum number of rows required for database
     * table maintenance
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=168)
    public void unsetTableMaintenanceMinRows() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceMinRows, "");
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
        attrs.put(Provisioning.A_zmailTableMaintenanceMinRows, "");
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @return zmailTableMaintenanceOperation, or ZAttrProvisioning.TableMaintenanceOperation.ANALYZE if unset and/or has invalid value
     */
    @ZAttr(id=170)
    public ZAttrProvisioning.TableMaintenanceOperation getTableMaintenanceOperation() {
        try { String v = getAttr(Provisioning.A_zmailTableMaintenanceOperation); return v == null ? ZAttrProvisioning.TableMaintenanceOperation.ANALYZE : ZAttrProvisioning.TableMaintenanceOperation.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return ZAttrProvisioning.TableMaintenanceOperation.ANALYZE; }
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @return zmailTableMaintenanceOperation, or "ANALYZE" if unset
     */
    @ZAttr(id=170)
    public String getTableMaintenanceOperationAsString() {
        return getAttr(Provisioning.A_zmailTableMaintenanceOperation, "ANALYZE");
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zmailTableMaintenanceOperation new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=170)
    public void setTableMaintenanceOperation(ZAttrProvisioning.TableMaintenanceOperation zmailTableMaintenanceOperation) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceOperation, zmailTableMaintenanceOperation.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zmailTableMaintenanceOperation new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=170)
    public Map<String,Object> setTableMaintenanceOperation(ZAttrProvisioning.TableMaintenanceOperation zmailTableMaintenanceOperation, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceOperation, zmailTableMaintenanceOperation.toString());
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zmailTableMaintenanceOperation new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=170)
    public void setTableMaintenanceOperationAsString(String zmailTableMaintenanceOperation) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceOperation, zmailTableMaintenanceOperation);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @param zmailTableMaintenanceOperation new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=170)
    public Map<String,Object> setTableMaintenanceOperationAsString(String zmailTableMaintenanceOperation, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceOperation, zmailTableMaintenanceOperation);
        return attrs;
    }

    /**
     * Deprecated since: 4.5.7. We now maintain all tables unconditionally.
     * See bug 19145. Orig desc: table maintenance operation that will be
     * performed. Valid options: &quot;ANALYZE&quot;, &quot;OPTIMIZE&quot;
     *
     * <p>Valid values: [ANALYZE, OPTIMIZE]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=170)
    public void unsetTableMaintenanceOperation() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailTableMaintenanceOperation, "");
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
        attrs.put(Provisioning.A_zmailTableMaintenanceOperation, "");
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @return zmailThrottleSafeHosts, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public String[] getThrottleSafeHosts() {
        return getMultiAttr(Provisioning.A_zmailThrottleSafeHosts);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zmailThrottleSafeHosts new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void setThrottleSafeHosts(String[] zmailThrottleSafeHosts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailThrottleSafeHosts, zmailThrottleSafeHosts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zmailThrottleSafeHosts new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> setThrottleSafeHosts(String[] zmailThrottleSafeHosts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailThrottleSafeHosts, zmailThrottleSafeHosts);
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zmailThrottleSafeHosts new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void addThrottleSafeHosts(String zmailThrottleSafeHosts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailThrottleSafeHosts, zmailThrottleSafeHosts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zmailThrottleSafeHosts new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> addThrottleSafeHosts(String zmailThrottleSafeHosts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailThrottleSafeHosts, zmailThrottleSafeHosts);
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zmailThrottleSafeHosts existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void removeThrottleSafeHosts(String zmailThrottleSafeHosts) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailThrottleSafeHosts, zmailThrottleSafeHosts);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @param zmailThrottleSafeHosts existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public Map<String,Object> removeThrottleSafeHosts(String zmailThrottleSafeHosts, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailThrottleSafeHosts, zmailThrottleSafeHosts);
        return attrs;
    }

    /**
     * Hosts to ignore during IP based throttling. Typically should list
     * nginx hostname and any other mailbox servers which can proxy to this
     * server
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1383)
    public void unsetThrottleSafeHosts() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailThrottleSafeHosts, "");
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
        attrs.put(Provisioning.A_zmailThrottleSafeHosts, "");
        return attrs;
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @return zmailUserServicesEnabled, or false if unset
     */
    @ZAttr(id=146)
    public boolean isUserServicesEnabled() {
        return getBooleanAttr(Provisioning.A_zmailUserServicesEnabled, false);
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @param zmailUserServicesEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=146)
    public void setUserServicesEnabled(boolean zmailUserServicesEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUserServicesEnabled, zmailUserServicesEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @param zmailUserServicesEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=146)
    public Map<String,Object> setUserServicesEnabled(boolean zmailUserServicesEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUserServicesEnabled, zmailUserServicesEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * whether end-user services on SOAP and LMTP interfaces are enabled
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=146)
    public void unsetUserServicesEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUserServicesEnabled, "");
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
        attrs.put(Provisioning.A_zmailUserServicesEnabled, "");
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
     * @return zmailVirusDefinitionsUpdateFrequency in millseconds, or 7200000 (2h)  if unset
     */
    @ZAttr(id=191)
    public long getVirusDefinitionsUpdateFrequency() {
        return getTimeInterval(Provisioning.A_zmailVirusDefinitionsUpdateFrequency, 7200000L);
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @return zmailVirusDefinitionsUpdateFrequency, or "2h" if unset
     */
    @ZAttr(id=191)
    public String getVirusDefinitionsUpdateFrequencyAsString() {
        return getAttr(Provisioning.A_zmailVirusDefinitionsUpdateFrequency, "2h");
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zmailVirusDefinitionsUpdateFrequency new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=191)
    public void setVirusDefinitionsUpdateFrequency(String zmailVirusDefinitionsUpdateFrequency) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirusDefinitionsUpdateFrequency, zmailVirusDefinitionsUpdateFrequency);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @param zmailVirusDefinitionsUpdateFrequency new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=191)
    public Map<String,Object> setVirusDefinitionsUpdateFrequency(String zmailVirusDefinitionsUpdateFrequency, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirusDefinitionsUpdateFrequency, zmailVirusDefinitionsUpdateFrequency);
        return attrs;
    }

    /**
     * how often the virus definitions are updated. Must be in valid duration
     * format: {digits}{time-unit}. digits: 0-9, time-unit: [hmsd]|ms. h -
     * hours, m - minutes, s - seconds, d - days, ms - milliseconds. If time
     * unit is not specified, the default is s(seconds).
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=191)
    public void unsetVirusDefinitionsUpdateFrequency() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailVirusDefinitionsUpdateFrequency, "");
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
        attrs.put(Provisioning.A_zmailVirusDefinitionsUpdateFrequency, "");
        return attrs;
    }

    /**
     * Enable XMPP support for IM
     *
     * @return zmailXMPPEnabled, or true if unset
     */
    @ZAttr(id=397)
    public boolean isXMPPEnabled() {
        return getBooleanAttr(Provisioning.A_zmailXMPPEnabled, true);
    }

    /**
     * Enable XMPP support for IM
     *
     * @param zmailXMPPEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=397)
    public void setXMPPEnabled(boolean zmailXMPPEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailXMPPEnabled, zmailXMPPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Enable XMPP support for IM
     *
     * @param zmailXMPPEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=397)
    public Map<String,Object> setXMPPEnabled(boolean zmailXMPPEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailXMPPEnabled, zmailXMPPEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Enable XMPP support for IM
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=397)
    public void unsetXMPPEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailXMPPEnabled, "");
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
        attrs.put(Provisioning.A_zmailXMPPEnabled, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE

}
