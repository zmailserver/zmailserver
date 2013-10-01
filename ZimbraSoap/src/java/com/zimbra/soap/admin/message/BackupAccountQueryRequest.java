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

import com.zimbra.common.soap.BackupConstants;
import com.zimbra.soap.admin.type.BackupAccountQuerySpec;

/**
 * @zm-api-command-network-edition
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Backup Account query
 * <br />
 * For each account &lt;backup> is listed from the most recent to earlier ones.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=BackupConstants.E_BACKUP_ACCOUNT_QUERY_REQUEST)
public class BackupAccountQueryRequest {

    /**
     * @zm-api-field-description Query
     */
    @XmlElement(name=BackupConstants.E_QUERY /* query */, required=true)
    private final BackupAccountQuerySpec query;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private BackupAccountQueryRequest() {
        this((BackupAccountQuerySpec) null);
    }

    public BackupAccountQueryRequest(BackupAccountQuerySpec query) {
        this.query = query;
    }

    public BackupAccountQuerySpec getQuery() { return query; }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("query", query);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
