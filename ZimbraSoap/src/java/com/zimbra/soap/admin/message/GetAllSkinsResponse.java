/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package com.zimbra.soap.admin.message;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.type.NamedElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_GET_ALL_SKINS_RESPONSE)
public class GetAllSkinsResponse {

    /**
     * @zm-api-field-description Skins
     */
    @XmlElement(name=AdminConstants.E_SKIN, required=false)
    private List<NamedElement> skins = Lists.newArrayList();

    public GetAllSkinsResponse() {
    }

    public void setSkins(Iterable <NamedElement> skins) {
        this.skins.clear();
        if (skins != null) {
            Iterables.addAll(this.skins,skins);
        }
    }

    public GetAllSkinsResponse addSkin(NamedElement skin) {
        this.skins.add(skin);
        return this;
    }

    public List<NamedElement> getSkins() {
        return Collections.unmodifiableList(skins);
    }
}
