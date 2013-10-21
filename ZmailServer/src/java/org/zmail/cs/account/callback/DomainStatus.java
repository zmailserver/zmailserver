/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;

public class DomainStatus extends AttributeCallback {

    @Override
    public void preModify(CallbackContext context, String attrName, Object value,
            Map attrsToModify, Entry entry) 
    throws ServiceException {
        
        if (!(value instanceof String))
            throw ServiceException.INVALID_REQUEST(Provisioning.A_zmailDomainStatus+" is a single-valued attribute", null);
        
        String status = (String) value;

        if (status.equals(Provisioning.DOMAIN_STATUS_SHUTDOWN)) {
            throw ServiceException.INVALID_REQUEST("Setting " + Provisioning.A_zmailDomainStatus + " to " + Provisioning.DOMAIN_STATUS_SHUTDOWN + " is not allowed.  It is an internal status and can only be set by server", null);
            
        } else if (status.equals(Provisioning.DOMAIN_STATUS_CLOSED)) {
            attrsToModify.put(Provisioning.A_zmailMailStatus, Provisioning.MAIL_STATUS_DISABLED);
            
        } else {
            if (entry != null) {
                Domain domain = (Domain)entry;
                if (domain.beingRenamed())
                    throw ServiceException.INVALID_REQUEST("domain " + domain.getName() + " is being renamed, cannot change " + Provisioning.A_zmailDomainStatus, null);
            }
            
            String alsoModifyingMailStatus = (String)attrsToModify.get(Provisioning.A_zmailMailStatus);
            if (alsoModifyingMailStatus == null) {
                if (entry != null) {
                    String curMailStatus = entry.getAttr(Provisioning.A_zmailMailStatus);
                    if (status.equals(Provisioning.DOMAIN_STATUS_SUSPENDED) && 
                        curMailStatus != null &&
                        curMailStatus.equals(Provisioning.MAIL_STATUS_DISABLED))
                        return;
                }
                
                attrsToModify.put(Provisioning.A_zmailMailStatus, Provisioning.MAIL_STATUS_ENABLED);
            }
        }

    }
    
    @Override
    public void postModify(CallbackContext context, String attrName, Entry entry) {
    }
    
}
