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
package org.zmail.cs.service.mail;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.GranteeType;
import org.zmail.cs.account.accesscontrol.Right;
import org.zmail.cs.account.accesscontrol.ACLUtil;
import org.zmail.cs.account.accesscontrol.RightManager;
import org.zmail.cs.account.accesscontrol.RightModifier;
import org.zmail.cs.account.accesscontrol.ZmailACE;
import org.zmail.soap.ZmailSoapContext;

/*
 * Delete this class in bug 66989
 */

public class GrantPermission extends MailDocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canAccessAccount(zsc, account))
            throw ServiceException.PERM_DENIED("can not access account");
        
        Set<ZmailACE> aces = new HashSet<ZmailACE>();
        for (Element eACE : request.listElements(MailConstants.E_ACE)) {
            ZmailACE ace = handleACE(eACE, zsc, true);
            aces.add(ace);
        }

        List<ZmailACE> granted = ACLUtil.grantRight(Provisioning.getInstance(), account, aces);
        Element response = zsc.createElement(MailConstants.GRANT_PERMISSION_RESPONSE);
        if (aces != null) {
            for (ZmailACE ace : granted)
                ToXML.encodeACE(response, ace);
        }

        return response;
    }
    
    /**
     * // orig: FolderAction
     * 
     * @param eACE
     * @param zsc
     * @param granting true if granting, false if revoking
     * @return
     * @throws ServiceException
     */
    static ZmailACE handleACE(Element eACE, ZmailSoapContext zsc, boolean granting) throws ServiceException {
        Right right = RightManager.getInstance().getUserRight(eACE.getAttribute(MailConstants.A_RIGHT));
        GranteeType gtype = GranteeType.fromCode(eACE.getAttribute(MailConstants.A_GRANT_TYPE));
        String zid = eACE.getAttribute(MailConstants.A_ZIMBRA_ID, null);
        boolean deny = eACE.getAttributeBool(MailConstants.A_DENY, false);
        String secret = null;
        NamedEntry nentry = null;
        
        if (gtype == GranteeType.GT_AUTHUSER) {
            zid = GuestAccount.GUID_AUTHUSER;
        } else if (gtype == GranteeType.GT_PUBLIC) {
            zid = GuestAccount.GUID_PUBLIC;
        } else if (gtype == GranteeType.GT_GUEST) {
            zid = eACE.getAttribute(MailConstants.A_DISPLAY);
            if (zid == null || zid.indexOf('@') < 0)
                throw ServiceException.INVALID_REQUEST("invalid guest id or password", null);
            // make sure they didn't accidentally specify "guest" instead of "usr"
            try {
                nentry = lookupGranteeByName(zid, GranteeType.GT_USER, zsc);
                zid = nentry.getId();
                gtype = nentry instanceof DistributionList ? GranteeType.GT_GROUP : GranteeType.GT_USER;
            } catch (ServiceException e) {
                // this is the normal path, where lookupGranteeByName throws account.NO_SUCH_USER
                secret = eACE.getAttribute(MailConstants.A_PASSWORD);
            }
        } else if (gtype == GranteeType.GT_KEY) {
            zid = eACE.getAttribute(MailConstants.A_DISPLAY);
            // unlike guest, we do not require the display name to be an email address
            /*
            if (zid == null || zid.indexOf('@') < 0)
                throw ServiceException.INVALID_REQUEST("invalid guest id or key", null);
            */    
            // unlike guest, we do not fixup grantee type for key grantees if they specify an internal user
            
            // get the optional accesskey
            secret = eACE.getAttribute(MailConstants.A_ACCESSKEY, null);
         
        } else if (zid != null) {
            nentry = lookupGranteeByZmailId(zid, gtype, granting);
        } else {
            nentry = lookupGranteeByName(eACE.getAttribute(MailConstants.A_DISPLAY), gtype, zsc);
            zid = nentry.getId();
            // make sure they didn't accidentally specify "usr" instead of "grp"
            if (gtype == GranteeType.GT_USER && nentry instanceof DistributionList)
                gtype = GranteeType.GT_GROUP;
        }
        
        RightModifier rightModifier = null;
        if (deny)
            rightModifier = RightModifier.RM_DENY;
        return new ZmailACE(zid, gtype, right, rightModifier, secret);

    }
    
    
    /*
     * lookupEmailAddress, lookupGranteeByName, lookupGranteeByZmailId are borrowed from FolderAction
     * and transplanted to work with ACL in accesscontrol package for usr space account level rights.
     * 
     * The purpose is to match the existing folder grant SOAP interface, which is more flexible/liberal 
     * on identifying grantee and target.
     *   
     * These methods are *not* used for admin space ACL SOAPs. 
     */
    
    // orig: FolderAction.lookupEmailAddress
    private static NamedEntry lookupEmailAddress(String name) throws ServiceException {
        NamedEntry nentry = null;
        Provisioning prov = Provisioning.getInstance();
        nentry = prov.get(AccountBy.name, name);
        if (nentry == null)
            nentry = prov.get(Key.DistributionListBy.name, name);
        return nentry;
    }
    
    // orig: FolderAction.lookupGranteeByName
    private static NamedEntry lookupGranteeByName(String name, GranteeType type, ZmailSoapContext zsc) throws ServiceException {
        if (type == GranteeType.GT_AUTHUSER || type == GranteeType.GT_PUBLIC || type == GranteeType.GT_GUEST || type == GranteeType.GT_KEY)
            return null;

        Provisioning prov = Provisioning.getInstance();
        // for addresses, default to the authenticated user's domain
        if ((type == GranteeType.GT_USER || type == GranteeType.GT_GROUP) && name.indexOf('@') == -1) {
            Account authacct = prov.get(AccountBy.id, zsc.getAuthtokenAccountId(), zsc.getAuthToken());
            String authname = (authacct == null ? null : authacct.getName());
            if (authacct != null)
                name += authname.substring(authname.indexOf('@'));
        }

        NamedEntry nentry = null;
        if (name != null)
            switch (type) {
                case GT_USER:    nentry = lookupEmailAddress(name);                 break;
                case GT_GROUP:   nentry = prov.get(Key.DistributionListBy.name, name);  break;
                case GT_DOMAIN:  nentry = prov.get(Key.DomainBy.name, name);            break;
            }

        if (nentry != null)
            return nentry;
        switch (type) {
            case GT_USER:    throw AccountServiceException.NO_SUCH_ACCOUNT(name);
            case GT_GROUP:   throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(name);
            case GT_DOMAIN:  throw AccountServiceException.NO_SUCH_DOMAIN(name);
            default:  throw ServiceException.FAILURE("LDAP entry not found for " + name + " : " + type, null);
        }
    }

    // orig: FolderAction.lookupGranteeByZmailId
    private static NamedEntry lookupGranteeByZmailId(String zid, GranteeType type, boolean granting) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        NamedEntry nentry = null;
        try {
            switch (type) {
                case GT_USER:    
                    nentry = prov.get(AccountBy.id, zid); 
                    if (nentry == null && granting)
                        throw AccountServiceException.NO_SUCH_ACCOUNT(zid);
                    else
                        return nentry;
                case GT_GROUP:   
                    nentry = prov.get(Key.DistributionListBy.id, zid);
                    if (nentry == null && granting)
                        throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(zid);
                    else
                        return nentry;
                case GT_DOMAIN:   
                    nentry = prov.get(Key.DomainBy.id, zid);
                    if (nentry == null && granting)
                        throw AccountServiceException.NO_SUCH_DOMAIN(zid);
                    else
                        return nentry;
                case GT_GUEST:
                case GT_KEY:    
                case GT_AUTHUSER:
                case GT_PUBLIC:
                default:         return null;
            }
        } catch (ServiceException e) {
            if (granting)
                throw e;
            else
                return null;
        }
    }


}
