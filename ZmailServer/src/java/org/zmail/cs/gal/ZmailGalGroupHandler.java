/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.cs.gal;

import java.util.Arrays;
import java.util.List;

import org.zmail.common.account.Key.DistributionListBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.ExternalGroup;
import org.zmail.cs.account.grouphandler.GroupHandler;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.ILdapContext;
import org.zmail.cs.ldap.IAttributes.CheckBinary;

public class ZmailGalGroupHandler extends GroupHandler {

    @Override
    public boolean isGroup(IAttributes ldapAttrs) {
        try {
            List<String> objectclass = ldapAttrs.getMultiAttrStringAsList(
                    Provisioning.A_objectClass, IAttributes.CheckBinary.NOCHECK);
            return objectclass.contains(AttributeClass.OC_zmailDistributionList) ||
                   objectclass.contains(AttributeClass.OC_zmailGroup);
        } catch (ServiceException e) {
            ZmailLog.gal.warn("unable to get attribute " + Provisioning.A_objectClass, e);
        }
        return false;
    }

    @Override
    public String[] getMembers(ILdapContext ldapContext, String searchBase,
            String entryDN, IAttributes ldapAttrs) throws ServiceException {
        ZmailLog.gal.debug("Fetching members for group " + ldapAttrs.getAttrString(Provisioning.A_mail));
        List<String> objectclass =
            ldapAttrs.getMultiAttrStringAsList(Provisioning.A_objectClass, CheckBinary.NOCHECK);

        String[] members = null;
        if (objectclass.contains(AttributeClass.OC_zmailDistributionList)) {
            members = ldapAttrs.getMultiAttrString(Provisioning.A_zmailMailForwardingAddress);
        } else if (objectclass.contains(AttributeClass.OC_zmailGroup)) {
            String zmailId = ldapAttrs.getAttrString(Provisioning.A_zmailId);
            Provisioning prov = Provisioning.getInstance();
            Group group = prov.getGroupBasic(DistributionListBy.id, zmailId);
            if (group == null) {
                throw AccountServiceException.NO_SUCH_GROUP(zmailId);
            }
            members = prov.getGroupMembers(group);
        }

        Arrays.sort(members);
        return members;
    }

    @Override
    public boolean inDelegatedAdminGroup(ExternalGroup group, Account acct, boolean asAdmin)
    throws ServiceException {
        // this method is used for checking external group membership for checking
        // delegated admin rights.  Internal group grantees do not go through
        // this path.
        throw ServiceException.FAILURE("internal error", null);
    }
}
