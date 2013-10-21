/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

// use LinkedHashSet to preserve the order and uniqueness of entries,
// not that order/uniqueness matters to LDAP server, just cleaner this way
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Provisioning;

/**
 * @author pshao
 */
public class LdapObjectClass {

    /*
     * as of bug 60444, our default person OC for accounts is changed
     * from organizationalPerson to inetOrgPerson
     */
    public static String ZIMBRA_DEFAULT_PERSON_OC = "inetOrgPerson";

    private static void addExtraObjectClasses(Set<String> ocs, Provisioning prov,
            String extraOCAttr) throws ServiceException {
        String[] extraObjectClasses = prov.getConfig().getMultiAttr(extraOCAttr);
        for (String eoc : extraObjectClasses) {
            ocs.add(eoc);
        }

        /*
        if (additionalObjectClasses != null) {
            for (int i = 0; i < additionalObjectClasses.length; i++)
                ocs.add(additionalObjectClasses[i]);
        }
        */
    }

    public static Set<String> getAccountObjectClasses(Provisioning prov,
            boolean zmailDefaultOnly) throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add(ZIMBRA_DEFAULT_PERSON_OC);
        ocs.add(AttributeClass.OC_zmailAccount);

        if (!zmailDefaultOnly)
            addExtraObjectClasses(ocs, prov, Provisioning.A_zmailAccountExtraObjectClass);
        return ocs;
    }

    public static Set<String> getAccountObjectClasses(Provisioning prov)
    throws ServiceException {
        return getAccountObjectClasses(prov, false);
    }

    public static Set<String> getCalendarResourceObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add(AttributeClass.OC_zmailCalendarResource);

        addExtraObjectClasses(ocs, prov, Provisioning.A_zmailCalendarResourceExtraObjectClass);
        return ocs;
    }

    public static Set<String> getCosObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add(AttributeClass.OC_zmailCOS);

        addExtraObjectClasses(ocs, prov, Provisioning.A_zmailCosExtraObjectClass);
        return ocs;
    }

    public static Set<String> getDomainObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add("dcObject");
        ocs.add("organization");
        ocs.add(AttributeClass.OC_zmailDomain);

        addExtraObjectClasses(ocs, prov, Provisioning.A_zmailDomainExtraObjectClass);
        return ocs;
    }

    public static Set<String> getDistributionListObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();
        ocs.add(AttributeClass.OC_zmailDistributionList);
        ocs.add(AttributeClass.OC_zmailMailRecipient);
        return ocs;
    }

    public static Set<String> getGroupObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add("groupOfURLs");
        ocs.add("dgIdentityAux");
        ocs.add(AttributeClass.OC_zmailGroup);
        // ocs.add(AttributeClass.OC_zmailMailRecipient);  // should we?

        return ocs;
    }

    public static Set<String> getGroupDynamicUnitObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add("groupOfURLs");
        ocs.add("dgIdentityAux");
        ocs.add(AttributeClass.OC_zmailGroupDynamicUnit);

        return ocs;
    }

    public static Set<String> getGroupStaticUnitObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add(AttributeClass.OC_zmailGroupStaticUnit);

        return ocs;
    }

    public static Set<String> getServerObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();

        ocs.add(AttributeClass.OC_zmailServer);

        addExtraObjectClasses(ocs, prov, Provisioning.A_zmailServerExtraObjectClass);
        return ocs;
    }

    public static Set<String> getUCServiceObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();
        ocs.add(AttributeClass.OC_zmailUCService);
        return ocs;
    }

    public static Set<String> getShareLocatorObjectClasses(Provisioning prov)
    throws ServiceException {
        Set<String> ocs = new LinkedHashSet<String>();
        ocs.add(AttributeClass.OC_zmailShareLocator);
        return ocs;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        Set<String> ocs = LdapObjectClass.getCalendarResourceObjectClasses(prov);

        for (String oc : ocs) {
            System.out.println(oc);
        }

    }

}
