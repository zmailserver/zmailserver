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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.AdminConstants;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Rename Unified Communication Service
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_RENAME_UC_SERVICE_REQUEST)
@XmlType(propOrder = {})
public class RenameUCServiceRequest {

    /**
     * @zm-api-field-tag value-of-zimbra-id
     * @zm-api-field-description Zimbra ID
     */
    @XmlElement(name=AdminConstants.E_ID, required=true)
    private final String id;

    /**
     * @zm-api-field-tag new-uc-service-name
     * @zm-api-field-description new UC Service name
     */
    @XmlElement(name=AdminConstants.E_NEW_NAME, required=true)
    private final String newName;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private RenameUCServiceRequest() {
        this(null, null);
    }

    public RenameUCServiceRequest(String id, String newName) {
        this.id = id;
        this.newName = newName;
    }

    public String getId() { return id; }
    public String getNewName() { return newName; }
}
