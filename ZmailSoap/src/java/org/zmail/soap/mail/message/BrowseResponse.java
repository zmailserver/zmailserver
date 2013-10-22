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

package com.zimbra.soap.mail.message;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.mail.type.BrowseData;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_BROWSE_RESPONSE)
public class BrowseResponse {

    /**
     * @zm-api-field-description Browse data
     */
    @XmlElement(name=MailConstants.E_BROWSE_DATA, required=false)
    private List<BrowseData> browseDatas = Lists.newArrayList();

    public BrowseResponse() {
    }

    public void setBrowseDatas(Iterable <BrowseData> browseDatas) {
        this.browseDatas.clear();
        if (browseDatas != null) {
            Iterables.addAll(this.browseDatas,browseDatas);
        }
    }

    public BrowseResponse addBrowseData(BrowseData browseData) {
        this.browseDatas.add(browseData);
        return this;
    }

    public List<BrowseData> getBrowseDatas() {
        return Collections.unmodifiableList(browseDatas);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("browseDatas", browseDatas)
            .toString();
    }
}
