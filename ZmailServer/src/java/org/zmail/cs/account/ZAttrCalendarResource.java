/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

/**
 * AUTO-GENERATED. DO NOT EDIT.
 *
 */
public class ZAttrCalendarResource extends Account {

    public ZAttrCalendarResource(String name, String id, Map<String, Object> attrs, Map<String, Object> defaults, Provisioning prov) {
        super(name, id, attrs, defaults, prov);
    }

    ///// BEGIN-AUTO-GEN-REPLACE

    /* build: 8.0.0_BETA1_1111 norman 20131010-1457 */

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
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @return zmailAccountCalendarUserType, or null if unset and/or has invalid value
     */
    @ZAttr(id=313)
    public ZAttrProvisioning.AccountCalendarUserType getAccountCalendarUserType() {
        try { String v = getAttr(Provisioning.A_zmailAccountCalendarUserType); return v == null ? null : ZAttrProvisioning.AccountCalendarUserType.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @return zmailAccountCalendarUserType, or null if unset
     */
    @ZAttr(id=313)
    public String getAccountCalendarUserTypeAsString() {
        return getAttr(Provisioning.A_zmailAccountCalendarUserType, null);
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @param zmailAccountCalendarUserType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=313)
    public void setAccountCalendarUserType(ZAttrProvisioning.AccountCalendarUserType zmailAccountCalendarUserType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAccountCalendarUserType, zmailAccountCalendarUserType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @param zmailAccountCalendarUserType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=313)
    public Map<String,Object> setAccountCalendarUserType(ZAttrProvisioning.AccountCalendarUserType zmailAccountCalendarUserType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAccountCalendarUserType, zmailAccountCalendarUserType.toString());
        return attrs;
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @param zmailAccountCalendarUserType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=313)
    public void setAccountCalendarUserTypeAsString(String zmailAccountCalendarUserType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAccountCalendarUserType, zmailAccountCalendarUserType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @param zmailAccountCalendarUserType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=313)
    public Map<String,Object> setAccountCalendarUserTypeAsString(String zmailAccountCalendarUserType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAccountCalendarUserType, zmailAccountCalendarUserType);
        return attrs;
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=313)
    public void unsetAccountCalendarUserType() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAccountCalendarUserType, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * calendar user type - USER (default) or RESOURCE
     *
     * <p>Valid values: [RESOURCE, USER]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=313)
    public Map<String,Object> unsetAccountCalendarUserType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailAccountCalendarUserType, "");
        return attrs;
    }

    /**
     * Whether this calendar resource accepts/declines meeting invites
     * automatically; default TRUE
     *
     * @return zmailCalResAutoAcceptDecline, or false if unset
     */
    @ZAttr(id=315)
    public boolean isCalResAutoAcceptDecline() {
        return getBooleanAttr(Provisioning.A_zmailCalResAutoAcceptDecline, false);
    }

    /**
     * Whether this calendar resource accepts/declines meeting invites
     * automatically; default TRUE
     *
     * @param zmailCalResAutoAcceptDecline new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=315)
    public void setCalResAutoAcceptDecline(boolean zmailCalResAutoAcceptDecline) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoAcceptDecline, zmailCalResAutoAcceptDecline ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether this calendar resource accepts/declines meeting invites
     * automatically; default TRUE
     *
     * @param zmailCalResAutoAcceptDecline new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=315)
    public Map<String,Object> setCalResAutoAcceptDecline(boolean zmailCalResAutoAcceptDecline, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoAcceptDecline, zmailCalResAutoAcceptDecline ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether this calendar resource accepts/declines meeting invites
     * automatically; default TRUE
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=315)
    public void unsetCalResAutoAcceptDecline() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoAcceptDecline, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether this calendar resource accepts/declines meeting invites
     * automatically; default TRUE
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=315)
    public Map<String,Object> unsetCalResAutoAcceptDecline(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoAcceptDecline, "");
        return attrs;
    }

    /**
     * Whether this calendar resource declines invite if already busy;
     * default TRUE
     *
     * @return zmailCalResAutoDeclineIfBusy, or false if unset
     */
    @ZAttr(id=322)
    public boolean isCalResAutoDeclineIfBusy() {
        return getBooleanAttr(Provisioning.A_zmailCalResAutoDeclineIfBusy, false);
    }

    /**
     * Whether this calendar resource declines invite if already busy;
     * default TRUE
     *
     * @param zmailCalResAutoDeclineIfBusy new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=322)
    public void setCalResAutoDeclineIfBusy(boolean zmailCalResAutoDeclineIfBusy) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineIfBusy, zmailCalResAutoDeclineIfBusy ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether this calendar resource declines invite if already busy;
     * default TRUE
     *
     * @param zmailCalResAutoDeclineIfBusy new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=322)
    public Map<String,Object> setCalResAutoDeclineIfBusy(boolean zmailCalResAutoDeclineIfBusy, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineIfBusy, zmailCalResAutoDeclineIfBusy ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether this calendar resource declines invite if already busy;
     * default TRUE
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=322)
    public void unsetCalResAutoDeclineIfBusy() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineIfBusy, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether this calendar resource declines invite if already busy;
     * default TRUE
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=322)
    public Map<String,Object> unsetCalResAutoDeclineIfBusy(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineIfBusy, "");
        return attrs;
    }

    /**
     * Whether this calendar resource declines invites to recurring
     * appointments; default FALSE
     *
     * @return zmailCalResAutoDeclineRecurring, or false if unset
     */
    @ZAttr(id=323)
    public boolean isCalResAutoDeclineRecurring() {
        return getBooleanAttr(Provisioning.A_zmailCalResAutoDeclineRecurring, false);
    }

    /**
     * Whether this calendar resource declines invites to recurring
     * appointments; default FALSE
     *
     * @param zmailCalResAutoDeclineRecurring new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=323)
    public void setCalResAutoDeclineRecurring(boolean zmailCalResAutoDeclineRecurring) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineRecurring, zmailCalResAutoDeclineRecurring ? Provisioning.TRUE : Provisioning.FALSE);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether this calendar resource declines invites to recurring
     * appointments; default FALSE
     *
     * @param zmailCalResAutoDeclineRecurring new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=323)
    public Map<String,Object> setCalResAutoDeclineRecurring(boolean zmailCalResAutoDeclineRecurring, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineRecurring, zmailCalResAutoDeclineRecurring ? Provisioning.TRUE : Provisioning.FALSE);
        return attrs;
    }

    /**
     * Whether this calendar resource declines invites to recurring
     * appointments; default FALSE
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=323)
    public void unsetCalResAutoDeclineRecurring() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineRecurring, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Whether this calendar resource declines invites to recurring
     * appointments; default FALSE
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=323)
    public Map<String,Object> unsetCalResAutoDeclineRecurring(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResAutoDeclineRecurring, "");
        return attrs;
    }

    /**
     * building number or name
     *
     * @return zmailCalResBuilding, or null if unset
     */
    @ZAttr(id=327)
    public String getCalResBuilding() {
        return getAttr(Provisioning.A_zmailCalResBuilding, null);
    }

    /**
     * building number or name
     *
     * @param zmailCalResBuilding new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=327)
    public void setCalResBuilding(String zmailCalResBuilding) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResBuilding, zmailCalResBuilding);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * building number or name
     *
     * @param zmailCalResBuilding new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=327)
    public Map<String,Object> setCalResBuilding(String zmailCalResBuilding, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResBuilding, zmailCalResBuilding);
        return attrs;
    }

    /**
     * building number or name
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=327)
    public void unsetCalResBuilding() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResBuilding, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * building number or name
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=327)
    public Map<String,Object> unsetCalResBuilding(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResBuilding, "");
        return attrs;
    }

    /**
     * capacity
     *
     * @return zmailCalResCapacity, or -1 if unset
     */
    @ZAttr(id=330)
    public int getCalResCapacity() {
        return getIntAttr(Provisioning.A_zmailCalResCapacity, -1);
    }

    /**
     * capacity
     *
     * @param zmailCalResCapacity new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=330)
    public void setCalResCapacity(int zmailCalResCapacity) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResCapacity, Integer.toString(zmailCalResCapacity));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * capacity
     *
     * @param zmailCalResCapacity new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=330)
    public Map<String,Object> setCalResCapacity(int zmailCalResCapacity, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResCapacity, Integer.toString(zmailCalResCapacity));
        return attrs;
    }

    /**
     * capacity
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=330)
    public void unsetCalResCapacity() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResCapacity, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * capacity
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=330)
    public Map<String,Object> unsetCalResCapacity(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResCapacity, "");
        return attrs;
    }

    /**
     * email of contact in charge of resource
     *
     * @return zmailCalResContactEmail, or null if unset
     */
    @ZAttr(id=332)
    public String getCalResContactEmail() {
        return getAttr(Provisioning.A_zmailCalResContactEmail, null);
    }

    /**
     * email of contact in charge of resource
     *
     * @param zmailCalResContactEmail new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=332)
    public void setCalResContactEmail(String zmailCalResContactEmail) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactEmail, zmailCalResContactEmail);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * email of contact in charge of resource
     *
     * @param zmailCalResContactEmail new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=332)
    public Map<String,Object> setCalResContactEmail(String zmailCalResContactEmail, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactEmail, zmailCalResContactEmail);
        return attrs;
    }

    /**
     * email of contact in charge of resource
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=332)
    public void unsetCalResContactEmail() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactEmail, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * email of contact in charge of resource
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=332)
    public Map<String,Object> unsetCalResContactEmail(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactEmail, "");
        return attrs;
    }

    /**
     * name of contact in charge of resource
     *
     * @return zmailCalResContactName, or null if unset
     */
    @ZAttr(id=331)
    public String getCalResContactName() {
        return getAttr(Provisioning.A_zmailCalResContactName, null);
    }

    /**
     * name of contact in charge of resource
     *
     * @param zmailCalResContactName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=331)
    public void setCalResContactName(String zmailCalResContactName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactName, zmailCalResContactName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name of contact in charge of resource
     *
     * @param zmailCalResContactName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=331)
    public Map<String,Object> setCalResContactName(String zmailCalResContactName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactName, zmailCalResContactName);
        return attrs;
    }

    /**
     * name of contact in charge of resource
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=331)
    public void unsetCalResContactName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * name of contact in charge of resource
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=331)
    public Map<String,Object> unsetCalResContactName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactName, "");
        return attrs;
    }

    /**
     * phone number of contact in charge of resource
     *
     * @return zmailCalResContactPhone, or null if unset
     */
    @ZAttr(id=333)
    public String getCalResContactPhone() {
        return getAttr(Provisioning.A_zmailCalResContactPhone, null);
    }

    /**
     * phone number of contact in charge of resource
     *
     * @param zmailCalResContactPhone new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=333)
    public void setCalResContactPhone(String zmailCalResContactPhone) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactPhone, zmailCalResContactPhone);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * phone number of contact in charge of resource
     *
     * @param zmailCalResContactPhone new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=333)
    public Map<String,Object> setCalResContactPhone(String zmailCalResContactPhone, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactPhone, zmailCalResContactPhone);
        return attrs;
    }

    /**
     * phone number of contact in charge of resource
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=333)
    public void unsetCalResContactPhone() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactPhone, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * phone number of contact in charge of resource
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=333)
    public Map<String,Object> unsetCalResContactPhone(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResContactPhone, "");
        return attrs;
    }

    /**
     * floor number or name
     *
     * @return zmailCalResFloor, or null if unset
     */
    @ZAttr(id=328)
    public String getCalResFloor() {
        return getAttr(Provisioning.A_zmailCalResFloor, null);
    }

    /**
     * floor number or name
     *
     * @param zmailCalResFloor new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=328)
    public void setCalResFloor(String zmailCalResFloor) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResFloor, zmailCalResFloor);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * floor number or name
     *
     * @param zmailCalResFloor new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=328)
    public Map<String,Object> setCalResFloor(String zmailCalResFloor, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResFloor, zmailCalResFloor);
        return attrs;
    }

    /**
     * floor number or name
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=328)
    public void unsetCalResFloor() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResFloor, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * floor number or name
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=328)
    public Map<String,Object> unsetCalResFloor(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResFloor, "");
        return attrs;
    }

    /**
     * display name for resource location
     *
     * @return zmailCalResLocationDisplayName, or null if unset
     */
    @ZAttr(id=324)
    public String getCalResLocationDisplayName() {
        return getAttr(Provisioning.A_zmailCalResLocationDisplayName, null);
    }

    /**
     * display name for resource location
     *
     * @param zmailCalResLocationDisplayName new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=324)
    public void setCalResLocationDisplayName(String zmailCalResLocationDisplayName) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResLocationDisplayName, zmailCalResLocationDisplayName);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * display name for resource location
     *
     * @param zmailCalResLocationDisplayName new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=324)
    public Map<String,Object> setCalResLocationDisplayName(String zmailCalResLocationDisplayName, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResLocationDisplayName, zmailCalResLocationDisplayName);
        return attrs;
    }

    /**
     * display name for resource location
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=324)
    public void unsetCalResLocationDisplayName() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResLocationDisplayName, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * display name for resource location
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=324)
    public Map<String,Object> unsetCalResLocationDisplayName(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResLocationDisplayName, "");
        return attrs;
    }

    /**
     * Maximum number of conflicting instances allowed before declining
     * schedule request for a recurring appointments; default 0 (means
     * decline on any conflict)
     *
     * @return zmailCalResMaxNumConflictsAllowed, or -1 if unset
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=808)
    public int getCalResMaxNumConflictsAllowed() {
        return getIntAttr(Provisioning.A_zmailCalResMaxNumConflictsAllowed, -1);
    }

    /**
     * Maximum number of conflicting instances allowed before declining
     * schedule request for a recurring appointments; default 0 (means
     * decline on any conflict)
     *
     * @param zmailCalResMaxNumConflictsAllowed new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=808)
    public void setCalResMaxNumConflictsAllowed(int zmailCalResMaxNumConflictsAllowed) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxNumConflictsAllowed, Integer.toString(zmailCalResMaxNumConflictsAllowed));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of conflicting instances allowed before declining
     * schedule request for a recurring appointments; default 0 (means
     * decline on any conflict)
     *
     * @param zmailCalResMaxNumConflictsAllowed new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=808)
    public Map<String,Object> setCalResMaxNumConflictsAllowed(int zmailCalResMaxNumConflictsAllowed, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxNumConflictsAllowed, Integer.toString(zmailCalResMaxNumConflictsAllowed));
        return attrs;
    }

    /**
     * Maximum number of conflicting instances allowed before declining
     * schedule request for a recurring appointments; default 0 (means
     * decline on any conflict)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=808)
    public void unsetCalResMaxNumConflictsAllowed() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxNumConflictsAllowed, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum number of conflicting instances allowed before declining
     * schedule request for a recurring appointments; default 0 (means
     * decline on any conflict)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=808)
    public Map<String,Object> unsetCalResMaxNumConflictsAllowed(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxNumConflictsAllowed, "");
        return attrs;
    }

    /**
     * Maximum percent of conflicting instances allowed before declining
     * schedule request for a recurring appointment; default 0 (means decline
     * on any conflict)
     *
     * @return zmailCalResMaxPercentConflictsAllowed, or -1 if unset
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=809)
    public int getCalResMaxPercentConflictsAllowed() {
        return getIntAttr(Provisioning.A_zmailCalResMaxPercentConflictsAllowed, -1);
    }

    /**
     * Maximum percent of conflicting instances allowed before declining
     * schedule request for a recurring appointment; default 0 (means decline
     * on any conflict)
     *
     * @param zmailCalResMaxPercentConflictsAllowed new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=809)
    public void setCalResMaxPercentConflictsAllowed(int zmailCalResMaxPercentConflictsAllowed) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxPercentConflictsAllowed, Integer.toString(zmailCalResMaxPercentConflictsAllowed));
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum percent of conflicting instances allowed before declining
     * schedule request for a recurring appointment; default 0 (means decline
     * on any conflict)
     *
     * @param zmailCalResMaxPercentConflictsAllowed new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=809)
    public Map<String,Object> setCalResMaxPercentConflictsAllowed(int zmailCalResMaxPercentConflictsAllowed, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxPercentConflictsAllowed, Integer.toString(zmailCalResMaxPercentConflictsAllowed));
        return attrs;
    }

    /**
     * Maximum percent of conflicting instances allowed before declining
     * schedule request for a recurring appointment; default 0 (means decline
     * on any conflict)
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=809)
    public void unsetCalResMaxPercentConflictsAllowed() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxPercentConflictsAllowed, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * Maximum percent of conflicting instances allowed before declining
     * schedule request for a recurring appointment; default 0 (means decline
     * on any conflict)
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 5.0.14
     */
    @ZAttr(id=809)
    public Map<String,Object> unsetCalResMaxPercentConflictsAllowed(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResMaxPercentConflictsAllowed, "");
        return attrs;
    }

    /**
     * room number or name
     *
     * @return zmailCalResRoom, or null if unset
     */
    @ZAttr(id=329)
    public String getCalResRoom() {
        return getAttr(Provisioning.A_zmailCalResRoom, null);
    }

    /**
     * room number or name
     *
     * @param zmailCalResRoom new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=329)
    public void setCalResRoom(String zmailCalResRoom) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResRoom, zmailCalResRoom);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * room number or name
     *
     * @param zmailCalResRoom new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=329)
    public Map<String,Object> setCalResRoom(String zmailCalResRoom, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResRoom, zmailCalResRoom);
        return attrs;
    }

    /**
     * room number or name
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=329)
    public void unsetCalResRoom() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResRoom, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * room number or name
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=329)
    public Map<String,Object> unsetCalResRoom(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResRoom, "");
        return attrs;
    }

    /**
     * site name
     *
     * @return zmailCalResSite, or null if unset
     */
    @ZAttr(id=326)
    public String getCalResSite() {
        return getAttr(Provisioning.A_zmailCalResSite, null);
    }

    /**
     * site name
     *
     * @param zmailCalResSite new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=326)
    public void setCalResSite(String zmailCalResSite) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResSite, zmailCalResSite);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * site name
     *
     * @param zmailCalResSite new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=326)
    public Map<String,Object> setCalResSite(String zmailCalResSite, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResSite, zmailCalResSite);
        return attrs;
    }

    /**
     * site name
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=326)
    public void unsetCalResSite() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResSite, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * site name
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=326)
    public Map<String,Object> unsetCalResSite(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResSite, "");
        return attrs;
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @return zmailCalResType, or null if unset and/or has invalid value
     */
    @ZAttr(id=314)
    public ZAttrProvisioning.CalResType getCalResType() {
        try { String v = getAttr(Provisioning.A_zmailCalResType); return v == null ? null : ZAttrProvisioning.CalResType.fromString(v); } catch(org.zmail.common.service.ServiceException e) { return null; }
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @return zmailCalResType, or null if unset
     */
    @ZAttr(id=314)
    public String getCalResTypeAsString() {
        return getAttr(Provisioning.A_zmailCalResType, null);
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @param zmailCalResType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=314)
    public void setCalResType(ZAttrProvisioning.CalResType zmailCalResType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResType, zmailCalResType.toString());
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @param zmailCalResType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=314)
    public Map<String,Object> setCalResType(ZAttrProvisioning.CalResType zmailCalResType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResType, zmailCalResType.toString());
        return attrs;
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @param zmailCalResType new value
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=314)
    public void setCalResTypeAsString(String zmailCalResType) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResType, zmailCalResType);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @param zmailCalResType new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=314)
    public Map<String,Object> setCalResTypeAsString(String zmailCalResType, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResType, zmailCalResType);
        return attrs;
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     */
    @ZAttr(id=314)
    public void unsetCalResType() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResType, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * calendar resource type - Location or Equipment
     *
     * <p>Valid values: [Location, Equipment]
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     */
    @ZAttr(id=314)
    public Map<String,Object> unsetCalResType(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailCalResType, "");
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

    ///// END-AUTO-GEN-REPLACE
}
