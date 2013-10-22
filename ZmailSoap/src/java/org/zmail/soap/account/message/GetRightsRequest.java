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

package com.zimbra.soap.account.message;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AccountConstants;
import com.zimbra.soap.account.type.Right;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Get account level rights.
 * <br />
 * If no <b>&lt;ace></b> elements are provided, all ACEs are returned in the response.
 * <br />
 * If <b>&lt;ace></b> elements are provided, only those ACEs with specified rights are returned in the response.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AccountConstants.E_GET_RIGHTS_REQUEST)
public class GetRightsRequest {

    /**
     * @zm-api-field-description Specify Access Control Entries to return
     */
    @XmlElement(name=AccountConstants.E_ACE /* ace */, required=false)
    private List<Right> aces = Lists.newArrayList();

    public GetRightsRequest() {
    }

    public void setAces(Iterable <Right> aces) {
        this.aces.clear();
        if (aces != null) {
            Iterables.addAll(this.aces,aces);
        }
    }

    public GetRightsRequest addAce(Right ace) {
        this.aces.add(ace);
        return this;
    }

    public List<Right> getAces() {
        return Collections.unmodifiableList(aces);
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("aces", aces);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
