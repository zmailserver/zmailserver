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

package com.zimbra.soap.admin.type;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.ZimletConstants;
import com.zimbra.soap.base.ZimletHostConfigInfo;
import com.zimbra.soap.base.ZimletProperty;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {})
public class AdminZimletHostConfigInfo
implements ZimletHostConfigInfo {

    /**
     * @zm-api-field-tag zimlet-host-name
     * @zm-api-field-description Designates the zimbra host name for the properties.
     * <br />
     * Must be a valid Zimbra host name
     */
    @XmlAttribute(name=ZimletConstants.ZIMLET_ATTR_NAME /* name */, required=false)
    private String name;

    /**
     * @zm-api-field-description Host specifice zimlet configuration properties
     */
    @XmlElement(name=ZimletConstants.ZIMLET_TAG_PROPERTY /* property */, required=false)
    private List<AdminZimletProperty> properties = Lists.newArrayList();

    public AdminZimletHostConfigInfo() {
    }

    private AdminZimletHostConfigInfo(String name) {
        setName(name);
    }

    public static AdminZimletHostConfigInfo createForName(String name) {
        return new AdminZimletHostConfigInfo(name);
    }

    @Override
    public void setName(String name) { this.name = name; }
    public void setProperties(Iterable <AdminZimletProperty> properties) {
        this.properties.clear();
        if (properties != null) {
            Iterables.addAll(this.properties,properties);
        }
    }

    public void addProperty(AdminZimletProperty property) {
        this.properties.add(property);
    }

    @Override
    public String getName() { return name; }
    public List<AdminZimletProperty> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    @Override
    public void setZimletProperties(Iterable<ZimletProperty> properties) {
        setProperties(AdminZimletProperty.fromInterfaces(properties));
    }

    @Override
    public void addZimletProperty(ZimletProperty property) {
        addProperty((AdminZimletProperty) property);
    }

    @Override
    public List<ZimletProperty> getZimletProperties() {
        return AdminZimletProperty.toInterfaces(properties);
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("name", name)
            .add("properties", properties);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
