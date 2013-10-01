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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.type.ZmBoolean;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Get Quota Usage
 * <br />
 * The target server should be specified in the soap header (see soap.txt, <b>&lt;targetServer></b>).
 * <br />
 * When sorting by "quotaLimit", 0 is treated as the highest value possible.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_GET_QUOTA_USAGE_REQUEST)
public class GetQuotaUsageRequest {

    /**
     * @zm-api-field-tag limit-to-domain
     * @zm-api-field-description Domain - the domain name to limit the search to
     */
    @XmlAttribute(name=AdminConstants.A_DOMAIN, required=false)
    private String domain;

    /**
     * @zm-api-field-tag all-servers
     * @zm-api-field-description whether to fetch quota usage for all domain accounts from across all mailbox servers,
     *   default is false, applicable when domain attribute is specified
     */
    @XmlAttribute(name=AdminConstants.A_ALL_SERVERS, required=false)
    private ZmBoolean allServers;

    /**
     * @zm-api-field-description Limit - the number of accounts to return (0 is default and means all)
     */
    @XmlAttribute(name=AdminConstants.A_LIMIT, required=false)
    private Integer limit;

    /**
     * @zm-api-field-description Offset - the starting offset (0, 25, etc)
     */
    @XmlAttribute(name=AdminConstants.A_OFFSET, required=false)
    private Integer offset;

    /**
     * @zm-api-field-tag sort-by
     * @zm-api-field-description SortBy - valid values: "percentUsed", "totalUsed", "quotaLimit"
     */
    @XmlAttribute(name=AdminConstants.A_SORT_BY, required=false)
    private String sortBy;

    /**
     * @zm-api-field-tag sort-ascending
     * @zm-api-field-description Whether to sort in ascending order <b>0 (false)</b> is default, so highest quotas
     * are returned first
     */
    @XmlAttribute(name=AdminConstants.A_SORT_ASCENDING, required=false)
    private ZmBoolean sortAscending;

    /**
     * @zm-api-field-description Refresh - whether to always recalculate the data even when cached values are
     * available.  <b>0 (false)</b> is the default.
     */
    @XmlAttribute(name=AdminConstants.A_REFRESH, required=false)
    private ZmBoolean refresh;

    public GetQuotaUsageRequest() {
        this(null, null, null, null, null, null, null);
    }

    public GetQuotaUsageRequest(String domain, Boolean allServers, Integer limit, Integer offset,
            String sortBy, Boolean sortAscending, Boolean refresh) {
        this.domain = domain;
        this.allServers = ZmBoolean.fromBool(allServers);
        this.limit = limit;
        this.offset = offset;
        this.sortBy = sortBy;
        this.sortAscending = ZmBoolean.fromBool(sortAscending);
        this.refresh = ZmBoolean.fromBool(refresh);
    }

    public String getDomain() { return domain; }
    public Boolean isAllServers() { return ZmBoolean.toBool(allServers); }
    public Integer getLimit() { return limit; }
    public Integer getOffset() { return offset; }
    public String getSortBy() { return sortBy; }
    public Boolean isSortAscending() { return ZmBoolean.toBool(sortAscending); }
    public Boolean isRefresh() { return ZmBoolean.toBool(refresh); }
}
