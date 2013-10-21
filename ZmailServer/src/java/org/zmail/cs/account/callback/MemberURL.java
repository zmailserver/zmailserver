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
package org.zmail.cs.account.callback;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.DynamicGroup;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;

public class MemberURL extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object attrValue,
            Map attrsToModify, Entry entry)
    throws ServiceException {

        if (context.isDoneAndSetIfNot(MemberURL.class)) {
            return;
        }

        if (context.isCreate()) {
            // memberURL can be set to anything during create
            // zmailIsACLGroup will be checked in createDynamicGroup
            return;
        }

        // not creating, ensure zmailIsACLGroup must be FALSE
        boolean isACLGroup = entry.getBooleanAttr(Provisioning.A_zmailIsACLGroup, true);

        if (isACLGroup) {
            throw ServiceException.INVALID_REQUEST("cannot modify " + Provisioning.A_memberURL +
                    " when " +  Provisioning.A_zmailIsACLGroup + " is TRUE", null);
        }

    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
}
