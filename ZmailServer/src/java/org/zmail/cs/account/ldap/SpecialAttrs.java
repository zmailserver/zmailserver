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
package org.zmail.cs.account.ldap;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.LdapUtil;

public class SpecialAttrs {
    
    // special Zmail attrs
    public static final String SA_zmailId  = Provisioning.A_zmailId;
    
    // pseudo attrs
    public static final String PA_ldapBase    = "ldap.baseDN";
    
    private String mZmailId;
    private String mLdapBaseDn;
    
    public String getZmailId()     { return mZmailId; }
    public String getLdapBaseDn()   { return mLdapBaseDn; }
    
    public static String getSingleValuedAttr(Map<String, Object> attrs, String attr) throws ServiceException {
        Object value = attrs.get(attr);
        if (value == null)
            return null;
        
        if (!(value instanceof String))
            throw ServiceException.INVALID_REQUEST(attr + " is a single-valued attribute", null);
        else
            return (String)value;
    }
    
    public void handleZmailId(Map<String, Object> attrs) throws ServiceException  {
        String zmailId = getSingleValuedAttr(attrs, SA_zmailId);
        
        if (zmailId != null) {
            // present, validate if it is a valid uuid
            try {
                if (!LdapUtil.isValidUUID(zmailId))
                throw ServiceException.INVALID_REQUEST(zmailId + " is not a valid UUID", null);
            } catch (IllegalArgumentException e) {
                throw ServiceException.INVALID_REQUEST(zmailId + " is not a valid UUID", e);
            }
        
            /* check for uniqueness of the zmailId
            * 
            * for now we go with GIGO (garbage in, garbage out) and not check, since there is a race condition 
            * that an entry is added after our check.
            * There is a way to do the uniqueness check in OpenLDAP with an overlay, we will address the uniqueness
            * when we do that.
            */
            /*
            if (getAccountById(uuid) != null)
                throw AccountServiceException.ACCOUNT_EXISTS(emailAddress);
            */
        
            // remove it from the attr list
            attrs.remove(SA_zmailId);
            mZmailId = zmailId;
        }
    }
        
    public void handleLdapBaseDn(Map<String, Object> attrs) throws ServiceException {
        String baseDn = getSingleValuedAttr(attrs, PA_ldapBase);
        if (baseDn != null) {
            attrs.remove(PA_ldapBase);
            mLdapBaseDn = baseDn;
        }
    }

}