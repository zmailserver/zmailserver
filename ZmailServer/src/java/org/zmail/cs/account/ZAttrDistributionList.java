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
public abstract class ZAttrDistributionList extends Group {

    protected ZAttrDistributionList(String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(name, id, attrs, prov);
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
     * RFC2798: preferred name to be used when displaying entries
     *
     * @return displayName, or null if unset
     */
    @ZAttr(id=-1)
    public String getDisplayName() {
        return getAttr(Provisioning.A_displayName, null);
    }

    /**
     * RFC2798: preferred name to be used when displaying entries
     *
     * @param displayName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setDisplayName(String displayName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_displayName, displayName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2798: preferred name to be used when displaying entries
     *
     * @param displayName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setDisplayName(String displayName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_displayName, displayName);
        return attrs;
    }

    /**
     * RFC2798: preferred name to be used when displaying entries
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetDisplayName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_displayName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC2798: preferred name to be used when displaying entries
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetDisplayName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_displayName, "");
        return attrs;
    }

    /**
     * RFC1274: RFC822 Mailbox
     *
     * @return mail, or null if unset
     */
    @ZAttr(id=-1)
    public String getMail() {
        return getAttr(Provisioning.A_mail, null);
    }

    /**
     * RFC1274: RFC822 Mailbox
     *
     * @param mail new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setMail(String mail) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_mail, mail);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC1274: RFC822 Mailbox
     *
     * @param mail new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setMail(String mail, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_mail, mail);
        return attrs;
    }

    /**
     * RFC1274: RFC822 Mailbox
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetMail() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_mail, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC1274: RFC822 Mailbox
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetMail(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_mail, "");
        return attrs;
    }

    /**
     * RFC1274: user identifier
     *
     * @return uid, or null if unset
     */
    @ZAttr(id=-1)
    public String getUid() {
        return getAttr(Provisioning.A_uid, null);
    }

    /**
     * RFC1274: user identifier
     *
     * @param uid new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void setUid(String uid) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_uid, uid);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC1274: user identifier
     *
     * @param uid new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> setUid(String uid, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_uid, uid);
        return attrs;
    }

    /**
     * RFC1274: user identifier
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=-1)
    public void unsetUid() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_uid, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * RFC1274: user identifier
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=-1)
    public Map<String,Object> unsetUid(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_uid, "");
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
     * UI components available for the authed admin in admin console
     *
     * @return zmailAdminConsoleUIComponents, or empty array if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public String[] getAdminConsoleUIComponents() {
        return getMultiAttr(Provisioning.A_zmailAdminConsoleUIComponents);
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param zmailAdminConsoleUIComponents new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public void setAdminConsoleUIComponents(String[] zmailAdminConsoleUIComponents) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleUIComponents, zmailAdminConsoleUIComponents);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param zmailAdminConsoleUIComponents new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public Map<String,Object> setAdminConsoleUIComponents(String[] zmailAdminConsoleUIComponents, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleUIComponents, zmailAdminConsoleUIComponents);
        return attrs;
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param zmailAdminConsoleUIComponents new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public void addAdminConsoleUIComponents(String zmailAdminConsoleUIComponents) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAdminConsoleUIComponents, zmailAdminConsoleUIComponents);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param zmailAdminConsoleUIComponents new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public Map<String,Object> addAdminConsoleUIComponents(String zmailAdminConsoleUIComponents, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailAdminConsoleUIComponents, zmailAdminConsoleUIComponents);
        return attrs;
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param zmailAdminConsoleUIComponents existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public void removeAdminConsoleUIComponents(String zmailAdminConsoleUIComponents) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAdminConsoleUIComponents, zmailAdminConsoleUIComponents);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param zmailAdminConsoleUIComponents existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public Map<String,Object> removeAdminConsoleUIComponents(String zmailAdminConsoleUIComponents, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailAdminConsoleUIComponents, zmailAdminConsoleUIComponents);
        return attrs;
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public void unsetAdminConsoleUIComponents() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleUIComponents, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * UI components available for the authed admin in admin console
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=761)
    public Map<String,Object> unsetAdminConsoleUIComponents(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAdminConsoleUIComponents, "");
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
     * Email address to put in from header for the share info email. If not
     * set, email address of the authenticated admin account will be used.
     *
     * @return zmailDistributionListSendShareMessageFromAddress, or null if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=811)
    public String getDistributionListSendShareMessageFromAddress() {
        return getAttr(Provisioning.A_zmailDistributionListSendShareMessageFromAddress, null);
    }

    /**
     * Email address to put in from header for the share info email. If not
     * set, email address of the authenticated admin account will be used.
     *
     * @param zmailDistributionListSendShareMessageFromAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=811)
    public void setDistributionListSendShareMessageFromAddress(String zmailDistributionListSendShareMessageFromAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageFromAddress, zmailDistributionListSendShareMessageFromAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Email address to put in from header for the share info email. If not
     * set, email address of the authenticated admin account will be used.
     *
     * @param zmailDistributionListSendShareMessageFromAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=811)
    public Map<String,Object> setDistributionListSendShareMessageFromAddress(String zmailDistributionListSendShareMessageFromAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageFromAddress, zmailDistributionListSendShareMessageFromAddress);
        return attrs;
    }

    /**
     * Email address to put in from header for the share info email. If not
     * set, email address of the authenticated admin account will be used.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=811)
    public void unsetDistributionListSendShareMessageFromAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageFromAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Email address to put in from header for the share info email. If not
     * set, email address of the authenticated admin account will be used.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=811)
    public Map<String,Object> unsetDistributionListSendShareMessageFromAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageFromAddress, "");
        return attrs;
    }

    /**
     * Whether to send an email with all the shares of the group when a new
     * member is added to the group. If not set, default is to send the
     * email.
     *
     * @return zmailDistributionListSendShareMessageToNewMembers, or false if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=810)
    public boolean isDistributionListSendShareMessageToNewMembers() {
        return getBooleanAttr(Provisioning.A_zmailDistributionListSendShareMessageToNewMembers, false);
    }

    /**
     * Whether to send an email with all the shares of the group when a new
     * member is added to the group. If not set, default is to send the
     * email.
     *
     * @param zmailDistributionListSendShareMessageToNewMembers new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=810)
    public void setDistributionListSendShareMessageToNewMembers(boolean zmailDistributionListSendShareMessageToNewMembers) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageToNewMembers, zmailDistributionListSendShareMessageToNewMembers ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to send an email with all the shares of the group when a new
     * member is added to the group. If not set, default is to send the
     * email.
     *
     * @param zmailDistributionListSendShareMessageToNewMembers new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=810)
    public Map<String,Object> setDistributionListSendShareMessageToNewMembers(boolean zmailDistributionListSendShareMessageToNewMembers, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageToNewMembers, zmailDistributionListSendShareMessageToNewMembers ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether to send an email with all the shares of the group when a new
     * member is added to the group. If not set, default is to send the
     * email.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=810)
    public void unsetDistributionListSendShareMessageToNewMembers() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageToNewMembers, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether to send an email with all the shares of the group when a new
     * member is added to the group. If not set, default is to send the
     * email.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=810)
    public Map<String,Object> unsetDistributionListSendShareMessageToNewMembers(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSendShareMessageToNewMembers, "");
        return attrs;
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @return zmailDistributionListSubscriptionPolicy, or null if unset and/or has invalid value
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public ZAttrProvisioning.DistributionListSubscriptionPolicy getDistributionListSubscriptionPolicy() {
        try { String v = getAttr(Provisioning.A_zmailDistributionListSubscriptionPolicy); return v == null ? null : ZAttrProvisioning.DistributionListSubscriptionPolicy.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @return zmailDistributionListSubscriptionPolicy, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public String getDistributionListSubscriptionPolicyAsString() {
        return getAttr(Provisioning.A_zmailDistributionListSubscriptionPolicy, null);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListSubscriptionPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public void setDistributionListSubscriptionPolicy(ZAttrProvisioning.DistributionListSubscriptionPolicy zmailDistributionListSubscriptionPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, zmailDistributionListSubscriptionPolicy.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListSubscriptionPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public Map<String,Object> setDistributionListSubscriptionPolicy(ZAttrProvisioning.DistributionListSubscriptionPolicy zmailDistributionListSubscriptionPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, zmailDistributionListSubscriptionPolicy.toString());
        return attrs;
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListSubscriptionPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public void setDistributionListSubscriptionPolicyAsString(String zmailDistributionListSubscriptionPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, zmailDistributionListSubscriptionPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListSubscriptionPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public Map<String,Object> setDistributionListSubscriptionPolicyAsString(String zmailDistributionListSubscriptionPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, zmailDistributionListSubscriptionPolicy);
        return attrs;
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public void unsetDistributionListSubscriptionPolicy() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1275)
    public Map<String,Object> unsetDistributionListSubscriptionPolicy(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListSubscriptionPolicy, "");
        return attrs;
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @return zmailDistributionListUnsubscriptionPolicy, or null if unset and/or has invalid value
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public ZAttrProvisioning.DistributionListUnsubscriptionPolicy getDistributionListUnsubscriptionPolicy() {
        try { String v = getAttr(Provisioning.A_zmailDistributionListUnsubscriptionPolicy); return v == null ? null : ZAttrProvisioning.DistributionListUnsubscriptionPolicy.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @return zmailDistributionListUnsubscriptionPolicy, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public String getDistributionListUnsubscriptionPolicyAsString() {
        return getAttr(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, null);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListUnsubscriptionPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public void setDistributionListUnsubscriptionPolicy(ZAttrProvisioning.DistributionListUnsubscriptionPolicy zmailDistributionListUnsubscriptionPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, zmailDistributionListUnsubscriptionPolicy.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListUnsubscriptionPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public Map<String,Object> setDistributionListUnsubscriptionPolicy(ZAttrProvisioning.DistributionListUnsubscriptionPolicy zmailDistributionListUnsubscriptionPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, zmailDistributionListUnsubscriptionPolicy.toString());
        return attrs;
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListUnsubscriptionPolicy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public void setDistributionListUnsubscriptionPolicyAsString(String zmailDistributionListUnsubscriptionPolicy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, zmailDistributionListUnsubscriptionPolicy);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param zmailDistributionListUnsubscriptionPolicy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public Map<String,Object> setDistributionListUnsubscriptionPolicyAsString(String zmailDistributionListUnsubscriptionPolicy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, zmailDistributionListUnsubscriptionPolicy);
        return attrs;
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public void unsetDistributionListUnsubscriptionPolicy() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * distribution subscription policy. ACCEPT: always accept, REJECT:
     * always reject, APPROVAL: require owners approval.
     *
     * <p>Valid values: [ACCEPT, APPROVAL, REJECT]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1276)
    public Map<String,Object> unsetDistributionListUnsubscriptionPolicy(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailDistributionListUnsubscriptionPolicy, "");
        return attrs;
    }

    /**
     * zmailId of the main dynamic group for the dynamic group unit
     *
     * @return zmailGroupId, or null if unset
     */
    @ZAttr(id=325)
    public String getGroupId() {
        return getAttr(Provisioning.A_zmailGroupId, null);
    }

    /**
     * zmailId of the main dynamic group for the dynamic group unit
     *
     * @param zmailGroupId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=325)
    public void setGroupId(String zmailGroupId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGroupId, zmailGroupId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of the main dynamic group for the dynamic group unit
     *
     * @param zmailGroupId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=325)
    public Map<String,Object> setGroupId(String zmailGroupId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGroupId, zmailGroupId);
        return attrs;
    }

    /**
     * zmailId of the main dynamic group for the dynamic group unit
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=325)
    public void unsetGroupId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGroupId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * zmailId of the main dynamic group for the dynamic group unit
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=325)
    public Map<String,Object> unsetGroupId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailGroupId, "");
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
     * set to true for admin groups
     *
     * @return zmailIsAdminGroup, or false if unset
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=802)
    public boolean isIsAdminGroup() {
        return getBooleanAttr(Provisioning.A_zmailIsAdminGroup, false);
    }

    /**
     * set to true for admin groups
     *
     * @param zmailIsAdminGroup new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=802)
    public void setIsAdminGroup(boolean zmailIsAdminGroup) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsAdminGroup, zmailIsAdminGroup ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * set to true for admin groups
     *
     * @param zmailIsAdminGroup new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=802)
    public Map<String,Object> setIsAdminGroup(boolean zmailIsAdminGroup, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsAdminGroup, zmailIsAdminGroup ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * set to true for admin groups
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=802)
    public void unsetIsAdminGroup() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsAdminGroup, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * set to true for admin groups
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 6.0.0_BETA1
     */
    @ZAttr(id=802)
    public Map<String,Object> unsetIsAdminGroup(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailIsAdminGroup, "");
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
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @return zmailPrefAllowAddressForDelegatedSender, or empty array if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public String[] getPrefAllowAddressForDelegatedSender() {
        return getMultiAttr(Provisioning.A_zmailPrefAllowAddressForDelegatedSender);
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param zmailPrefAllowAddressForDelegatedSender new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public void setPrefAllowAddressForDelegatedSender(String[] zmailPrefAllowAddressForDelegatedSender) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefAllowAddressForDelegatedSender, zmailPrefAllowAddressForDelegatedSender);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param zmailPrefAllowAddressForDelegatedSender new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public Map<String,Object> setPrefAllowAddressForDelegatedSender(String[] zmailPrefAllowAddressForDelegatedSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefAllowAddressForDelegatedSender, zmailPrefAllowAddressForDelegatedSender);
        return attrs;
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param zmailPrefAllowAddressForDelegatedSender new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public void addPrefAllowAddressForDelegatedSender(String zmailPrefAllowAddressForDelegatedSender) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefAllowAddressForDelegatedSender, zmailPrefAllowAddressForDelegatedSender);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param zmailPrefAllowAddressForDelegatedSender new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public Map<String,Object> addPrefAllowAddressForDelegatedSender(String zmailPrefAllowAddressForDelegatedSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailPrefAllowAddressForDelegatedSender, zmailPrefAllowAddressForDelegatedSender);
        return attrs;
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param zmailPrefAllowAddressForDelegatedSender existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public void removePrefAllowAddressForDelegatedSender(String zmailPrefAllowAddressForDelegatedSender) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefAllowAddressForDelegatedSender, zmailPrefAllowAddressForDelegatedSender);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param zmailPrefAllowAddressForDelegatedSender existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public Map<String,Object> removePrefAllowAddressForDelegatedSender(String zmailPrefAllowAddressForDelegatedSender, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailPrefAllowAddressForDelegatedSender, zmailPrefAllowAddressForDelegatedSender);
        return attrs;
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public void unsetPrefAllowAddressForDelegatedSender() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefAllowAddressForDelegatedSender, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Addresses of the account that can be used by allowed delegated senders
     * as From and Sender address.
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1333)
    public Map<String,Object> unsetPrefAllowAddressForDelegatedSender(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefAllowAddressForDelegatedSender, "");
        return attrs;
    }

    /**
     * address to put in reply-to header
     *
     * @return zmailPrefReplyToAddress, or null if unset
     */
    @ZAttr(id=60)
    public String getPrefReplyToAddress() {
        return getAttr(Provisioning.A_zmailPrefReplyToAddress, null);
    }

    /**
     * address to put in reply-to header
     *
     * @param zmailPrefReplyToAddress new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=60)
    public void setPrefReplyToAddress(String zmailPrefReplyToAddress) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToAddress, zmailPrefReplyToAddress);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * address to put in reply-to header
     *
     * @param zmailPrefReplyToAddress new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=60)
    public Map<String,Object> setPrefReplyToAddress(String zmailPrefReplyToAddress, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToAddress, zmailPrefReplyToAddress);
        return attrs;
    }

    /**
     * address to put in reply-to header
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=60)
    public void unsetPrefReplyToAddress() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToAddress, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * address to put in reply-to header
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=60)
    public Map<String,Object> unsetPrefReplyToAddress(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToAddress, "");
        return attrs;
    }

    /**
     * personal part of email address put in reply-to header
     *
     * @return zmailPrefReplyToDisplay, or null if unset
     */
    @ZAttr(id=404)
    public String getPrefReplyToDisplay() {
        return getAttr(Provisioning.A_zmailPrefReplyToDisplay, null);
    }

    /**
     * personal part of email address put in reply-to header
     *
     * @param zmailPrefReplyToDisplay new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=404)
    public void setPrefReplyToDisplay(String zmailPrefReplyToDisplay) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToDisplay, zmailPrefReplyToDisplay);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * personal part of email address put in reply-to header
     *
     * @param zmailPrefReplyToDisplay new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=404)
    public Map<String,Object> setPrefReplyToDisplay(String zmailPrefReplyToDisplay, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToDisplay, zmailPrefReplyToDisplay);
        return attrs;
    }

    /**
     * personal part of email address put in reply-to header
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=404)
    public void unsetPrefReplyToDisplay() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToDisplay, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * personal part of email address put in reply-to header
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=404)
    public Map<String,Object> unsetPrefReplyToDisplay(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToDisplay, "");
        return attrs;
    }

    /**
     * TRUE if we should set a reply-to header
     *
     * @return zmailPrefReplyToEnabled, or false if unset
     */
    @ZAttr(id=405)
    public boolean isPrefReplyToEnabled() {
        return getBooleanAttr(Provisioning.A_zmailPrefReplyToEnabled, false);
    }

    /**
     * TRUE if we should set a reply-to header
     *
     * @param zmailPrefReplyToEnabled new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=405)
    public void setPrefReplyToEnabled(boolean zmailPrefReplyToEnabled) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToEnabled, zmailPrefReplyToEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * TRUE if we should set a reply-to header
     *
     * @param zmailPrefReplyToEnabled new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=405)
    public Map<String,Object> setPrefReplyToEnabled(boolean zmailPrefReplyToEnabled, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToEnabled, zmailPrefReplyToEnabled ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * TRUE if we should set a reply-to header
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=405)
    public void unsetPrefReplyToEnabled() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToEnabled, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * TRUE if we should set a reply-to header
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=405)
    public Map<String,Object> unsetPrefReplyToEnabled(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailPrefReplyToEnabled, "");
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @return zmailShareInfo, or empty array if unset
     */
    @ZAttr(id=357)
    public String[] getShareInfo() {
        return getMultiAttr(Provisioning.A_zmailShareInfo);
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param zmailShareInfo new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=357)
    public void setShareInfo(String[] zmailShareInfo) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareInfo, zmailShareInfo);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param zmailShareInfo new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=357)
    public Map<String,Object> setShareInfo(String[] zmailShareInfo, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareInfo, zmailShareInfo);
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param zmailShareInfo new to add to existing values
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=357)
    public void addShareInfo(String zmailShareInfo) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailShareInfo, zmailShareInfo);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param zmailShareInfo new to add to existing values
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=357)
    public Map<String,Object> addShareInfo(String zmailShareInfo, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "+" + Provisioning.A_zmailShareInfo, zmailShareInfo);
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param zmailShareInfo existing value to remove
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=357)
    public void removeShareInfo(String zmailShareInfo) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailShareInfo, zmailShareInfo);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param zmailShareInfo existing value to remove
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=357)
    public Map<String,Object> removeShareInfo(String zmailShareInfo, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        StringUtil.addToMultiMap(attrs, "-" + Provisioning.A_zmailShareInfo, zmailShareInfo);
        return attrs;
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=357)
    public void unsetShareInfo() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareInfo, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Deprecated since: 8.0.0. Manual publishing of shares by admin is no
     * longer required since now automated publishing of sharing info updates
     * to LDAP is supported. Orig desc: items an account or group has shared
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=357)
    public Map<String,Object> unsetShareInfo(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareInfo, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE


}
