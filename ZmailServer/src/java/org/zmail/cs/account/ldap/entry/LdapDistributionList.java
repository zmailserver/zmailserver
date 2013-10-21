/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.Set;

import org.zmail.common.account.Key.DistributionListBy;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapProvisioning;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.ZAttributes;

/**
 * @author pshao
 */
public class LdapDistributionList extends DistributionList implements LdapEntry {
    private String mDn;
    private boolean mIsBasic; // contains only basic attrs in Ldap

    public LdapDistributionList(String dn, String email, ZAttributes attrs,
            boolean isBasic, Provisioning prov) throws LdapException {
        super(email, attrs.getAttrString(Provisioning.A_zmailId),
                attrs.getAttrs(), prov);
        mDn = dn;
        mIsBasic = isBasic;
    }

    public String getDN() {
        return mDn;
    }

    @Override
    public String[] getAllMembers() throws ServiceException {
        // need to re-get the DistributionList in full if this object was
        // created from getDLBasic, which does not bring in members
        if (mIsBasic) {
            DistributionList dl = getProvisioning().get(DistributionListBy.id, getId());
            return dl.getMultiAttr(MEMBER_ATTR);
        } else {
            return super.getAllMembers();
        }
    }

    @Override
    public Set<String> getAllMembersSet() throws ServiceException {
        // need to re-get the DistributionList if this object was
        // created from getDLBasic, which does not bring in members
        if (mIsBasic) {
            DistributionList dl = getProvisioning().get(DistributionListBy.id, getId());
            return dl.getMultiAttrSet(MEMBER_ATTR);
        } else {
            return super.getAllMembersSet();
        }
    }

}
