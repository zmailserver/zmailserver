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

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.admin.type.WaitSetInfo;

// note: soap-waitset.txt differs significantly but the SOAP handler only adds E_WAITSET elements to the response
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_QUERY_WAIT_SET_RESPONSE)
public class QueryWaitSetResponse {

    /**
     * @zm-api-field-description Information about WaitSets
     */
    @XmlElement(name=AdminConstants.E_WAITSET /* waitSet */, required=false)
    private List<WaitSetInfo> waitsets = Lists.newArrayList();

    public QueryWaitSetResponse() {
    }

    public void setWaitsets(Iterable <WaitSetInfo> waitsets) {
        this.waitsets.clear();
        if (waitsets != null) {
            Iterables.addAll(this.waitsets,waitsets);
        }
    }

    public QueryWaitSetResponse addWaitset(WaitSetInfo waitset) {
        this.waitsets.add(waitset);
        return this;
    }

    public List<WaitSetInfo> getWaitsets() {
        return Collections.unmodifiableList(waitsets);
    }
}
