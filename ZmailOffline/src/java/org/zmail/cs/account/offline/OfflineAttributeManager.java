/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.account.offline;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Version;
import com.zimbra.cs.account.AttributeCallback;
import com.zimbra.cs.account.AttributeCardinality;
import com.zimbra.cs.account.AttributeClass;
import com.zimbra.cs.account.AttributeFlag;
import com.zimbra.cs.account.AttributeInfo;
import com.zimbra.cs.account.AttributeManager;
import com.zimbra.cs.account.AttributeOrder;
import com.zimbra.cs.account.AttributeServerType;
import com.zimbra.cs.account.AttributeType;

import java.util.List;
import java.util.Set;

/**
 * @author vmahajan
 */
public class OfflineAttributeManager extends AttributeManager {

    public OfflineAttributeManager(String dir) throws ServiceException {
        super(dir);
    }

    @Override
    protected AttributeInfo createAttributeInfo(
            String name, int id, String parentOid, int groupId,
            AttributeCallback callback, AttributeType type, AttributeOrder order,
            String value, boolean immutable, String min, String max,
            AttributeCardinality cardinality, Set<AttributeClass> requiredIn,
            Set<AttributeClass> optionalIn, Set<AttributeFlag> flags,
            List<String> globalConfigValues, List<String> defaultCOSValues,
            List<String> defaultExternalCOSValues, List<String> globalConfigValuesUpgrade,
            List<String> defaultCOSValuesUpgrade, String description, List<AttributeServerType> requiresRestart,
            Version sinceVer, Version deprecatedSinceVer) {
        return new OfflineAttributeInfo(
                name, id, parentOid, groupId, callback, type, order, value, immutable, min, max,
                cardinality, requiredIn, optionalIn, flags, globalConfigValues, defaultCOSValues,
                globalConfigValuesUpgrade, defaultCOSValuesUpgrade,
                description, requiresRestart, sinceVer, deprecatedSinceVer);
    }
}
