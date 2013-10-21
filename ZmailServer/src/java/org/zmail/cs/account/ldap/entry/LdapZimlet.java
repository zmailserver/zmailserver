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

import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZSearchResultEntry;

/**
 * 
 * @author pshao
 *
 */
public class LdapZimlet extends Zimlet implements LdapEntry {

    private String mDn;
    
	public LdapZimlet(String dn, ZAttributes attrs, Provisioning prov) throws LdapException {
        super(attrs.getAttrString(Provisioning.A_cn), 
                attrs.getAttrString(Provisioning.A_cn),                 
                attrs.getAttrs(), prov);
        mDn = dn;
	}
	
    public String getDN() {
        return mDn;
    }
}
