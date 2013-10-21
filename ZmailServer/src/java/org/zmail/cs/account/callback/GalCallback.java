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
package org.zmail.cs.account.callback;

import java.util.Date;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeCallback;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;

public class GalCallback extends AttributeCallback {
    
    private String oldValue = null;
    private String newValue = null;

    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {
        
        if (entry != null) {
            oldValue = entry.getAttr(attrName, true);
        }
        SingleValueMod mod = singleValueMod(attrsToModify, attrName);
        newValue = mod.value();
        if (attrName.equals("zmailGalLdapFilter")) {
            if (mod.unsetting())
                return;
            if ("ad".equalsIgnoreCase(newValue)) {
                attrsToModify.put(Provisioning.A_zmailGalLdapGroupHandlerClass, 
                        org.zmail.cs.account.grouphandler.ADGroupHandler.class.getCanonicalName());
            }
        }
    }

    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
        if (entry instanceof Domain) {
            try {
                if (!StringUtil.equal(oldValue, newValue)) {
                    ((Domain) entry).setGalDefinitionLastModifiedTime(new Date());
                }
            } catch (ServiceException e) {
                ZmailLog.misc.warn("Unable to set zmailGalDefinitionLastModifiedTime " + e);
            }
        }
    }
    
}
