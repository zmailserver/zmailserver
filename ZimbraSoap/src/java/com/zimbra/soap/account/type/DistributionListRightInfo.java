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
package com.zimbra.soap.account.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.zimbra.common.soap.AccountConstants;

public class DistributionListRightInfo {

    /**
     * @zm-api-field-description Right
     */
    @XmlAttribute(name=AccountConstants.A_RIGHT, required=true)
    private final String right;

    /**
     * @zm-api-field-description Grantees
     */
    @XmlElement(name=AccountConstants.E_GRANTEE, required=false)
    protected List<DistributionListGranteeInfo> grantees;

    public DistributionListRightInfo() {
        this(null);
    }

    public DistributionListRightInfo(String right) {
        this.right = right;
    }

    public String getRight() {
        return right;
    }

    public void addGrantee(DistributionListGranteeInfo grantee) {
        if (grantees == null) {
            grantees = Lists.newArrayList();
        }
        grantees.add(grantee);
    }

    public void setGrantees(List<DistributionListGranteeInfo> grantees) {
        this.grantees = null;
        if (grantees != null) {
            this.grantees = Lists.newArrayList();
            Iterables.addAll(this.grantees, grantees);
        }
    }

    public List<DistributionListGranteeInfo> getGrantees() {
        return grantees;
    }
}
