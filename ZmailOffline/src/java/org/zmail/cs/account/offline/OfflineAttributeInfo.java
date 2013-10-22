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
import com.zimbra.common.util.SystemUtil;
import com.zimbra.common.util.Version;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.AttributeCallback;
import com.zimbra.cs.account.AttributeCardinality;
import com.zimbra.cs.account.AttributeClass;
import com.zimbra.cs.account.AttributeFlag;
import com.zimbra.cs.account.AttributeInfo;
import com.zimbra.cs.account.AttributeOrder;
import com.zimbra.cs.account.AttributeServerType;
import com.zimbra.cs.account.AttributeType;
import com.zimbra.cs.offline.OfflineLog;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public class OfflineAttributeInfo extends AttributeInfo {

    public OfflineAttributeInfo(
            String attrName, int id, String parentId, int groupId, AttributeCallback callback,
            AttributeType type, AttributeOrder order, String value, boolean immutable, String min,
            String max, AttributeCardinality cardinality, Set<AttributeClass> requiredIn,
            Set<AttributeClass> optionalIn, Set<AttributeFlag> flags, List<String> globalConfigValues,
            List<String> defaultCOSValues, List<String> globalConfigValuesUpgrade,
            List<String> defaultCOSValuesUpgrade, String description, List<AttributeServerType> requiresRestart,
            Version since, Version deprecatedSince) {
        super(attrName, id, parentId, groupId, callback, type, order, value, immutable, min, max, cardinality,
              requiredIn, optionalIn, flags, globalConfigValues, defaultCOSValues, null, globalConfigValuesUpgrade,
              defaultCOSValuesUpgrade, description, requiresRestart, since, deprecatedSince);
    }

    @Override
    protected void checkValue(String value, Map attrsToModify) throws ServiceException {
        try {
            super.checkValue(value, attrsToModify);
        } catch (AccountServiceException e) {
            if (AccountServiceException.INVALID_ATTR_VALUE.equals(e.getCode()) && mType == AttributeType.TYPE_ENUM) {
                // use the default value and ignore exception
                if (mDefaultCOSValues != null && !mDefaultCOSValues.isEmpty()) {
                    if (mDefaultCOSValues.size() == 1)
                        attrsToModify.put(mName, mDefaultCOSValues.get(0));
                    else
                        attrsToModify.put(mName, mDefaultCOSValues.toArray());
                    OfflineLog.offline.warn(SystemUtil.getStackTrace(e));
                    OfflineLog.offline.warn("Using default value '" + mDefaultCOSValues + "' for attribute '" + mName + "'");
                    return;
                }
            }
            throw e;
        }
    }
}
