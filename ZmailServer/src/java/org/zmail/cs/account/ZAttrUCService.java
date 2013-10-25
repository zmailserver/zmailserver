/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012, 2013 VMware, Inc.
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
public abstract class ZAttrUCService extends NamedEntry {

    public ZAttrUCService(String name, String id, Map<String,Object> attrs, Provisioning prov) {
        super(name, id, attrs, null, prov);
    }

    ///// BEGIN-AUTO-GEN-REPLACE

    /* build: 8.0.0_BETA1_1111 norman 20131010-1457 */

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
     * call control service URL for the UC service
     *
     * @return zmailUCCallControlURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1405)
    public String getUCCallControlURL() {
        return getAttr(Provisioning.A_zmailUCCallControlURL, null);
    }

    /**
     * call control service URL for the UC service
     *
     * @param zmailUCCallControlURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1405)
    public void setUCCallControlURL(String zmailUCCallControlURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCCallControlURL, zmailUCCallControlURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * call control service URL for the UC service
     *
     * @param zmailUCCallControlURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1405)
    public Map<String,Object> setUCCallControlURL(String zmailUCCallControlURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCCallControlURL, zmailUCCallControlURL);
        return attrs;
    }

    /**
     * call control service URL for the UC service
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1405)
    public void unsetUCCallControlURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCCallControlURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * call control service URL for the UC service
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1405)
    public Map<String,Object> unsetUCCallControlURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCCallControlURL, "");
        return attrs;
    }

    /**
     * presence session id for Cisco presence service
     *
     * @return zmailUCPresenceSessionId, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1407)
    public String getUCPresenceSessionId() {
        return getAttr(Provisioning.A_zmailUCPresenceSessionId, null);
    }

    /**
     * presence session id for Cisco presence service
     *
     * @param zmailUCPresenceSessionId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1407)
    public void setUCPresenceSessionId(String zmailUCPresenceSessionId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceSessionId, zmailUCPresenceSessionId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * presence session id for Cisco presence service
     *
     * @param zmailUCPresenceSessionId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1407)
    public Map<String,Object> setUCPresenceSessionId(String zmailUCPresenceSessionId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceSessionId, zmailUCPresenceSessionId);
        return attrs;
    }

    /**
     * presence session id for Cisco presence service
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1407)
    public void unsetUCPresenceSessionId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceSessionId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * presence session id for Cisco presence service
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1407)
    public Map<String,Object> unsetUCPresenceSessionId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceSessionId, "");
        return attrs;
    }

    /**
     * presence service URL for the UC service
     *
     * @return zmailUCPresenceURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1406)
    public String getUCPresenceURL() {
        return getAttr(Provisioning.A_zmailUCPresenceURL, null);
    }

    /**
     * presence service URL for the UC service
     *
     * @param zmailUCPresenceURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1406)
    public void setUCPresenceURL(String zmailUCPresenceURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceURL, zmailUCPresenceURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * presence service URL for the UC service
     *
     * @param zmailUCPresenceURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1406)
    public Map<String,Object> setUCPresenceURL(String zmailUCPresenceURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceURL, zmailUCPresenceURL);
        return attrs;
    }

    /**
     * presence service URL for the UC service
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1406)
    public void unsetUCPresenceURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * presence service URL for the UC service
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1406)
    public Map<String,Object> unsetUCPresenceURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCPresenceURL, "");
        return attrs;
    }

    /**
     * provider for the UC service
     *
     * @return zmailUCProvider, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1402)
    public String getUCProvider() {
        return getAttr(Provisioning.A_zmailUCProvider, null);
    }

    /**
     * provider for the UC service
     *
     * @param zmailUCProvider new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1402)
    public void setUCProvider(String zmailUCProvider) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCProvider, zmailUCProvider);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider for the UC service
     *
     * @param zmailUCProvider new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1402)
    public Map<String,Object> setUCProvider(String zmailUCProvider, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCProvider, zmailUCProvider);
        return attrs;
    }

    /**
     * provider for the UC service
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1402)
    public void unsetUCProvider() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCProvider, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * provider for the UC service
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1402)
    public Map<String,Object> unsetUCProvider(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCProvider, "");
        return attrs;
    }

    /**
     * user info service URL for the UC service
     *
     * @return zmailUCUserURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1403)
    public String getUCUserURL() {
        return getAttr(Provisioning.A_zmailUCUserURL, null);
    }

    /**
     * user info service URL for the UC service
     *
     * @param zmailUCUserURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1403)
    public void setUCUserURL(String zmailUCUserURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCUserURL, zmailUCUserURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * user info service URL for the UC service
     *
     * @param zmailUCUserURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1403)
    public Map<String,Object> setUCUserURL(String zmailUCUserURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCUserURL, zmailUCUserURL);
        return attrs;
    }

    /**
     * user info service URL for the UC service
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1403)
    public void unsetUCUserURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCUserURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * user info service URL for the UC service
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1403)
    public Map<String,Object> unsetUCUserURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCUserURL, "");
        return attrs;
    }

    /**
     * voicemail service URL for the UC service
     *
     * @return zmailUCVoicemailURL, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1404)
    public String getUCVoicemailURL() {
        return getAttr(Provisioning.A_zmailUCVoicemailURL, null);
    }

    /**
     * voicemail service URL for the UC service
     *
     * @param zmailUCVoicemailURL new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1404)
    public void setUCVoicemailURL(String zmailUCVoicemailURL) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCVoicemailURL, zmailUCVoicemailURL);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * voicemail service URL for the UC service
     *
     * @param zmailUCVoicemailURL new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1404)
    public Map<String,Object> setUCVoicemailURL(String zmailUCVoicemailURL, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCVoicemailURL, zmailUCVoicemailURL);
        return attrs;
    }

    /**
     * voicemail service URL for the UC service
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1404)
    public void unsetUCVoicemailURL() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCVoicemailURL, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * voicemail service URL for the UC service
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1404)
    public Map<String,Object> unsetUCVoicemailURL(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailUCVoicemailURL, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE

}
