/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account.ldap.entry;

import org.zmail.cs.account.Alias;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.ZAttributes;

/**
 * 
 * @author pshao
 *
 */
public class LdapAlias extends Alias implements LdapEntry {
    private String mDn;
    
    public LdapAlias(String dn, String email, ZAttributes attrs, Provisioning prov) throws LdapException {
        super(email, attrs.getAttrString(Provisioning.A_zmailId), attrs.getAttrs(), prov);
        mDn = dn;
    }

    public String getDN() {
        return mDn;
    }
}
