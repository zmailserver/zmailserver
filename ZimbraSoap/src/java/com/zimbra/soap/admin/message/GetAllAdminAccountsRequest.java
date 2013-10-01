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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.type.ZmBoolean;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Get all Admin accounts
 */
@XmlRootElement(name=AdminConstants.E_GET_ALL_ADMIN_ACCOUNTS_REQUEST)
public class GetAllAdminAccountsRequest {

    /**
     * @zm-api-field-tag apply-cos
     * @zm-api-field-description Apply COS [default 1 (true)]
     */
    @XmlAttribute(name=AdminConstants.A_APPLY_COS, required=false)
    private ZmBoolean applyCos = ZmBoolean.ONE /* true */;

    public GetAllAdminAccountsRequest() {
    }

    public GetAllAdminAccountsRequest(Boolean applyCos) {
        setApplyCos(applyCos);
    }

    public void setApplyCos(Boolean applyCos) {
        this.applyCos = ZmBoolean.fromBool(applyCos);
    }

    public Boolean isApplyCos() { return ZmBoolean.toBool(applyCos); }
}
