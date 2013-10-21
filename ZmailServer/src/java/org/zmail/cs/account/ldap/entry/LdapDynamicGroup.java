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
package org.zmail.cs.account.ldap.entry;

import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.DynamicGroup;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapProvisioning;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.ZAttributes;

/**
 * @author pshao
 */
public class LdapDynamicGroup extends DynamicGroup implements LdapEntry {

    private String dn;
    private DynamicUnit dynamicUnit;
    private StaticUnit staticUnit;

    public LdapDynamicGroup(String dn, String email, ZAttributes attrs, Provisioning prov)
    throws LdapException {
        super(email, attrs.getAttrString(Provisioning.A_zmailId), attrs.getAttrs(), prov);
        this.dn = dn;
    }

    public void setSubUnits(DynamicUnit dynamicUnit, StaticUnit staticUnit) {
        this.dynamicUnit = dynamicUnit;
        this.staticUnit = staticUnit;
    }

    public DynamicUnit getDynamicUnit() {
        assert(dynamicUnit != null);
        return dynamicUnit;
    }

    public StaticUnit getStaticUnit() {
        assert(staticUnit != null);
        return staticUnit;
    }

    public boolean hasExternalMembers() {
        return staticUnit != null && staticUnit.hasExternalMembers();
    }

    @Override
    public String getDN() {
        return dn;
    }

    @Override
    public String[] getAllMembers() throws ServiceException {
        return ((LdapProvisioning) getProvisioning()).getDynamicGroupMembers(this);
    }

    @Override
    public Set<String> getAllMembersSet() throws ServiceException {
        return ((LdapProvisioning) getProvisioning()).getDynamicGroupMembersSet(this);
    }

    @Override
    public String[] getAllMembers(boolean supportNonDefaultMemberURL)
    throws ServiceException {
        if (isIsACLGroup()) {
            // is a dynamic group with default memberURL, expand it by searching memberOf
            return getAllMembers();
        } else {
            if (supportNonDefaultMemberURL) {
                return ((LdapProvisioning) getProvisioning()).getNonDefaultDynamicGroupMembers(this);
            } else {
                return new String[0];
            }
        }

    }

    public static String getDefaultDynamicUnitMemberURL(String zmailId) {
        return String.format("ldap:///??sub?(zmailMemberOf=%s)", zmailId);
    }

    public static String getDefaultMemberURL(String zmailId, String staticUnitZmailId) {
        return String.format("ldap:///??sub?(|(zmailMemberOf=%s)(zmailId=%s))",
                zmailId, staticUnitZmailId);
    }

    public static class DynamicUnit extends NamedEntry implements LdapEntry {
        private final String dn;
        private final String emailAddr;

        public DynamicUnit(String dn, String name, ZAttributes attrs, Provisioning prov)
        throws LdapException {
            super(name, attrs.getAttrString(Provisioning.A_zmailId), attrs.getAttrs(), null, prov);
            this.dn = dn;
            this.emailAddr = attrs.getAttrString(Provisioning.A_mail);
        }

        @Override
        public EntryType getEntryType() {
            return EntryType.DYNAMICGROUP_DYNAMIC_UNIT;
        }

        @Override
        public String getDN() {
            return dn;
        }

        public String getEmailAddr() {
            return emailAddr;
        }
    }

    public static class StaticUnit extends NamedEntry implements LdapEntry {
        public static final String MEMBER_ATTR = Provisioning.A_zmailMailForwardingAddress;

        private String dn;

        public StaticUnit(String dn, String name, ZAttributes attrs, Provisioning prov)
        throws LdapException {
            super(name, attrs.getAttrString(Provisioning.A_zmailId), attrs.getAttrs(), null, prov);
            this.dn = dn;
        }

        @Override
        public EntryType getEntryType() {
            return EntryType.DYNAMICGROUP_STATIC_UNIT;
        }

        @Override
        public String getDN() {
            return dn;
        }

        private boolean hasExternalMembers() {
            return !getMembersSet().isEmpty();
        }

        public String[] getMembers() {
            return getMultiAttr(MEMBER_ATTR);
        }

        public Set<String> getMembersSet() {
            return getMultiAttrSet(MEMBER_ATTR);
        }
    }

}
