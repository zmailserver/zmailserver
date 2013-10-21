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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.zmail.common.soap.AdminConstants;
import org.zmail.soap.admin.type.AdminAttrsImpl;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Modify Class of Service (COS) attributes
 * <br />
 * Note: an empty attribute value removes the specified attr
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_MODIFY_COS_REQUEST)
@XmlType(propOrder = {})
public class ModifyCosRequest extends AdminAttrsImpl {

    /**
     * @zm-api-field-tag value-of-zmail-id
     * @zm-api-field-description Zmail ID
     */
    @XmlElement(name=AdminConstants.E_ID)
    private String id;

    public ModifyCosRequest() {
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getId() { return id; }
}
