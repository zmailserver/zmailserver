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

package org.zmail.soap.admin.message;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.AdminConstants;
import org.zmail.soap.admin.type.AdminAttrsImpl;
import org.zmail.soap.admin.type.Attr;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Modify attributes for a Distribution List
 * <br />
 * Notes: an empty attribute value removes the specified attr
 * <br />
 * <b>Access</b>: domain admin sufficient
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_MODIFY_DISTRIBUTION_LIST_REQUEST)
public class ModifyDistributionListRequest extends AdminAttrsImpl {

    /**
     * @zm-api-field-tag value-of-zmail-id
     * @zm-api-field-description Zmail ID
     */
    @XmlAttribute(name=AdminConstants.E_ID, required=true)
    private String id;

    public ModifyDistributionListRequest() {
        this((String) null, (Collection<Attr>) null);
    }

    public ModifyDistributionListRequest(String id) {
        this((String) id, (Collection<Attr>) null);
    }

    public ModifyDistributionListRequest(String id, Collection<Attr> attrs) {
        super(attrs);
        this.id = id;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }
}
