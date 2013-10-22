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

package com.zimbra.soap.admin.message;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.XMbxSearchConstants;
import com.zimbra.soap.admin.type.SearchNode;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=XMbxSearchConstants.E_ABORT_XMBX_SEARCH_RESPONSE)
@XmlType(propOrder = {})
public class AbortXMbxSearchResponse {

    /**
     * @zm-api-field-description Search task information
     */
    @XmlElement(name=XMbxSearchConstants.E_SrchTask /* searchtask */, required=true)
    private final SearchNode searchNode;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private AbortXMbxSearchResponse() {
        this((SearchNode) null);
    }

    public AbortXMbxSearchResponse(SearchNode searchNode) {
        this.searchNode = searchNode;
    }

    public SearchNode getSearchNode() { return searchNode; }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("searchNode", searchNode);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
