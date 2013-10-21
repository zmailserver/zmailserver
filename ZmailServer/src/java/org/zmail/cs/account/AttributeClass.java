/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
/**
 *
 */
package org.zmail.cs.account;

import java.util.HashMap;
import java.util.Map;

import org.zmail.common.service.ServiceException;

public enum AttributeClass {
    mailRecipient("zmailMailRecipient",        false),
    account("zmailAccount",                    true),
    alias("zmailAlias",                        true),
    distributionList("zmailDistributionList",  true),
    cos("zmailCOS",                            true),
    globalConfig("zmailGlobalConfig",          true),
    domain("zmailDomain",                      true),
    securityGroup("zmailSecurityGroup",        false),
    server("zmailServer",                      true),
    ucService("zmailUCService",                true),
    mimeEntry("zmailMimeEntry",                true),
    objectEntry("zmailObjectEntry",            false),
    timeZone("zmailTimeZone",                  false),
    zimletEntry("zmailZimletEntry",            true),
    calendarResource("zmailCalendarResource",  true),
    identity("zmailIdentity",                  true),
    dataSource("zmailDataSource",              true),
    pop3DataSource("zmailPop3DataSource",      true),
    imapDataSource("zmailImapDataSource",      true),
    rssDataSource("zmailRssDataSource",        true),
    liveDataSource("zmailLiveDataSource",      true),
    galDataSource("zmailGalDataSource",        true),
    signature("zmailSignature",                true),
    xmppComponent("zmailXMPPComponent",        true),
    aclTarget("zmailAclTarget",                true),
    group("zmailGroup",                        true),
    groupDynamicUnit("zmailGroupDynamicUnit",  false),
    groupStaticUnit("zmailGroupStaticUnit",    false),
    shareLocator("zmailShareLocator",          true);

    public static final String OC_zmailAccount = account.getOCName();
    public static final String OC_zmailAclTarget = aclTarget.getOCName();
    public static final String OC_zmailAlias = alias.getOCName();
    public static final String OC_zmailCalendarResource = calendarResource.getOCName();
    public static final String OC_zmailCOS = cos.getOCName();
    public static final String OC_zmailDataSource = dataSource.getOCName();
    public static final String OC_zmailDistributionList = distributionList.getOCName();
    public static final String OC_zmailDomain = domain.getOCName();
    public static final String OC_zmailGalDataSource = galDataSource.getOCName();
    public static final String OC_zmailGlobalConfig = globalConfig.getOCName();
    public static final String OC_zmailGroup = group.getOCName();
    public static final String OC_zmailGroupDynamicUnit = groupDynamicUnit.getOCName();
    public static final String OC_zmailGroupStaticUnit = groupStaticUnit.getOCName();
    public static final String OC_zmailIdentity = identity.getOCName();
    public static final String OC_zmailImapDataSource = imapDataSource.getOCName();
    public static final String OC_zmailMailRecipient = mailRecipient.getOCName();
    public static final String OC_zmailMimeEntry = mimeEntry.getOCName();
    public static final String OC_zmailPop3DataSource = pop3DataSource.getOCName();
    public static final String OC_zmailRssDataSource = rssDataSource.getOCName();
    public static final String OC_zmailServer = server.getOCName();
    public static final String OC_zmailUCService = ucService.getOCName();
    public static final String OC_zmailSignature = signature.getOCName();
    public static final String OC_zmailXMPPComponent = xmppComponent.getOCName();
    public static final String OC_zmailZimletEntry = zimletEntry.getOCName();
    public static final String OC_zmailShareLocator = shareLocator.getOCName();

    private static class TM {
        static Map<String, AttributeClass> sOCMap = new HashMap<String, AttributeClass>();
    }

    String mOCName;
    boolean mProvisionable;

    AttributeClass(String ocName, boolean provisionable) {
        mOCName = ocName;
        mProvisionable = provisionable;

        TM.sOCMap.put(ocName, this);
    }

    public static AttributeClass getAttributeClass(String ocName) {
        return TM.sOCMap.get(ocName);
    }

    public static AttributeClass fromString(String s) throws ServiceException {
        try {
            return AttributeClass.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw ServiceException.PARSE_ERROR("unknown attribute class: " + s, e);
        }
    }

    public String getOCName() {
        return mOCName;
    }

    public boolean isProvisionable() {
        return mProvisionable;
    }

}
