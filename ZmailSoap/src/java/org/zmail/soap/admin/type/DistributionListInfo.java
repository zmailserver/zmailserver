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

import java.util.List;
import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.type.ZmBoolean;
import com.zimbra.soap.json.jackson.annotate.ZimbraJsonArrayForWrapper;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_DL)
public class DistributionListInfo extends AdminObjectInfo {

    /**
     * @zm-api-field-tag dl-is-dynamic
     * @zm-api-field-description Flags whether this is a dynamic distribution list or not
     */
    @XmlAttribute(name=AdminConstants.A_DYNAMIC /* dynamic */, required=false)
    ZmBoolean dynamic;
    /**
     * @zm-api-field-description dl-members
     */
    @XmlElement(name=AdminConstants.E_DLM /* dlm */, required=false)
    private List<String> members;

    /**
     * @zm-api-field-description Owner information
     */
    @ZimbraJsonArrayForWrapper
    @XmlElementWrapper(name=AdminConstants.E_DL_OWNERS /* owners */, required=false)
    @XmlElement(name=AdminConstants.E_DL_OWNER /* owner */, required=false)
    private List<GranteeInfo> owners = Lists.newArrayList();

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private DistributionListInfo() {
        this((String) null, (String) null,
            (Collection <String>) null, (Collection <Attr>) null);
    }

    public DistributionListInfo(String id, String name) {
        this((String)id, (String)name,
            (Collection <String>) null, (Collection <Attr>) null);
    }

    public DistributionListInfo(String id, String name,
            Collection <String> members, Collection <Attr> attrs) {
        super(id, name, attrs);
        setMembers(members);
    }

    public void setMembers(Collection <String> members) {
        this.members = Lists.newArrayList();
        if (members != null) {
            this.members.addAll(members);
        }
    }

    public List<String> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = ZmBoolean.fromBool(dynamic);
    }

    public Boolean isDynamic() {
        return ZmBoolean.toBool(dynamic, false);
    }

    public void setOwners(List<GranteeInfo> owners) {
        this.owners = owners;
    }

    public void addOwner(GranteeInfo owner) {
        owners.add(owner);
    }

    public List<GranteeInfo> getOwners() {
        return Collections.unmodifiableList(owners);
    }

}
