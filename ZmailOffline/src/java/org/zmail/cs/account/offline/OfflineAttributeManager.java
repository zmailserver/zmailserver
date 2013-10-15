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
package org.zmail.cs.account.offline;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Version;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.AttributeCardinality;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.AttributeFlag;
import org.zmail.cs.account.AttributeInfo;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.AttributeOrder;
import org.zmail.cs.account.AttributeServerType;
import org.zmail.cs.account.AttributeType;

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
