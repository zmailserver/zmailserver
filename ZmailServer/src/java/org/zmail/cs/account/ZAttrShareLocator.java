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

package org.zmail.cs.account;

import java.util.HashMap;
import java.util.Map;

import org.zmail.common.account.ZAttr;

/**
 * AUTO-GENERATED. DO NOT EDIT.
 *
 */
public abstract class ZAttrShareLocator extends NamedEntry {

    public ZAttrShareLocator(String id, Map<String,Object> attrs, Provisioning prov) {
        super(null, id, attrs, null, prov);
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
     * account ID of the owner of the shared folder
     *
     * @return zmailShareOwnerAccountId, or null if unset
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1375)
    public String getShareOwnerAccountId() {
        return getAttr(Provisioning.A_zmailShareOwnerAccountId, null);
    }

    /**
     * account ID of the owner of the shared folder
     *
     * @param zmailShareOwnerAccountId new value
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1375)
    public void setShareOwnerAccountId(String zmailShareOwnerAccountId) throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareOwnerAccountId, zmailShareOwnerAccountId);
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * account ID of the owner of the shared folder
     *
     * @param zmailShareOwnerAccountId new value
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1375)
    public Map<String,Object> setShareOwnerAccountId(String zmailShareOwnerAccountId, Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareOwnerAccountId, zmailShareOwnerAccountId);
        return attrs;
    }

    /**
     * account ID of the owner of the shared folder
     *
     * @throws org.zmail.common.service.ServiceException if error during update
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1375)
    public void unsetShareOwnerAccountId() throws org.zmail.common.service.ServiceException {
        HashMap<String,Object> attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareOwnerAccountId, "");
        getProvisioning().modifyAttrs(this, attrs);
    }

    /**
     * account ID of the owner of the shared folder
     *
     * @param attrs existing map to populate, or null to create a new map
     * @return populated map to pass into Provisioning.modifyAttrs
     *
     * @since ZCS 8.0.0
     */
    @ZAttr(id=1375)
    public Map<String,Object> unsetShareOwnerAccountId(Map<String,Object> attrs) {
        if (attrs == null) attrs = new HashMap<String,Object>();
        attrs.put(Provisioning.A_zmailShareOwnerAccountId, "");
        return attrs;
    }

    ///// END-AUTO-GEN-REPLACE

}
