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

package org.zmail.cs.service.account;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Group.GroupOwner;
import org.zmail.cs.account.accesscontrol.ACLUtil;
import org.zmail.cs.account.accesscontrol.Right;
import org.zmail.cs.account.accesscontrol.RightManager;
import org.zmail.cs.account.accesscontrol.ZmailACE;

import org.zmail.soap.ZmailSoapContext;

public class GetDistributionList extends DistributionListDocumentHandler {

    private static final Set<String> OWNER_ATTRS = Sets.newHashSet(
            Provisioning.A_description,
            Provisioning.A_displayName,
            Provisioning.A_mail,
            Provisioning.A_zmailHideInGal,
            Provisioning.A_zmailIsAdminGroup,
            Provisioning.A_zmailLocale,
            Provisioning.A_zmailMailAlias,
            Provisioning.A_zmailMailStatus,
            Provisioning.A_zmailNotes,
            Provisioning.A_zmailPrefReplyToAddress,
            Provisioning.A_zmailPrefReplyToDisplay,
            Provisioning.A_zmailPrefReplyToEnabled,
            Provisioning.A_zmailDistributionListSubscriptionPolicy,
            Provisioning.A_zmailDistributionListUnsubscriptionPolicy);

    private static final Set<String> NON_OWNER_ATTRS = Sets.newHashSet(
            Provisioning.A_description,
            Provisioning.A_displayName,
            Provisioning.A_zmailHideInGal,
            Provisioning.A_zmailNotes,
            Provisioning.A_zmailDistributionListSubscriptionPolicy,
            Provisioning.A_zmailDistributionListUnsubscriptionPolicy);

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account acct = getAuthenticatedAccount(zsc);

        Element response = zsc.createElement(AccountConstants.GET_DISTRIBUTION_LIST_RESPONSE);

        Group group = getGroupBasic(request, prov);
        GetDistributionListHandler handler = new GetDistributionListHandler(
                group, request, response, prov, acct);
        handler.handle();

        return response;
    }

    private static class GetDistributionListHandler extends SynchronizedGroupHandler {
        private Element request;
        private Element response;
        private Provisioning prov;
        private Account acct;

        protected GetDistributionListHandler(Group group,
                Element request, Element response,
                Provisioning prov, Account acct) {
            super(group);
            this.request = request;
            this.response = response;
            this.prov = prov;
            this.acct = acct;
        }

        @Override
        protected void handleRequest() throws ServiceException {
            boolean isOwner = GroupOwner.isOwner(acct, group);

            // isMember
            boolean isMember = group.isMemberOf(acct);

            boolean needOwners = request.getAttributeBool(AccountConstants.A_NEED_OWNERS, false);

            // set encodeAttrs to false, we will be encoded attrs using addKeyValuePair,
            // which is more json friendly
            Element eDL = org.zmail.cs.service.admin.GetDistributionList.encodeDistributionList(
                    response, group, true, !needOwners, false, null, null);

            if (isMember) {
                eDL.addAttribute(AccountConstants.A_IS_MEMBER, true);
            }

            if (isOwner) {
                eDL.addAttribute(AccountConstants.A_IS_OWNER, true);
            }

            encodeAttrs(group, eDL, isOwner ? OWNER_ATTRS : NON_OWNER_ATTRS);

            if (isOwner) {
                encodeRights(eDL);
            }
        }

        private void encodeRights(Element eDL) throws ServiceException {
            String needRights = request.getAttribute(AccountConstants.A_NEED_RIGHTS, null);
            if (needRights == null) {
                return;
            }
            String[] rights = needRights.split(",");

            RightManager rightMgr = RightManager.getInstance();

            Element eRights = eDL.addElement(AccountConstants.E_RIGHTS);
            for (String rightStr : rights) {
                Right right = rightMgr.getUserRight(rightStr);

                if (Group.GroupOwner.GROUP_OWNER_RIGHT == right) {
                    throw ServiceException.INVALID_REQUEST(right.getName() +
                            " cannot be queried directly, owners are returned in the" +
                            " owners section", null);
                }

                Element eRight = eRights.addElement(AccountConstants.E_RIGHT);
                eRight.addAttribute(AccountConstants.A_RIGHT, right.getName());

                List<ZmailACE> acl = ACLUtil.getACEs(group, Collections.singleton(right));
                if (acl != null) {
                    for (ZmailACE ace : acl) {
                        Element eGrantee = eRight.addElement(AccountConstants.E_GRANTEE);
                        eGrantee.addAttribute(AccountConstants.A_TYPE, ace.getGranteeType().getCode());
                        eGrantee.addAttribute(AccountConstants.A_ID, ace.getGrantee());
                        eGrantee.addAttribute(AccountConstants.A_NAME, ace.getGranteeDisplayName());
                    }
                }
            }
        }
    }

    public static void encodeAttrs(Group group, Element eParent, Set<String> specificAttrs) {
        Map<String, Object> attrsMap = group.getUnicodeAttrs();

        if (specificAttrs == null || !specificAttrs.isEmpty()) {
            for (Map.Entry<String, Object> entry : attrsMap.entrySet()) {
                String key = entry.getKey();

                if (specificAttrs != null && !specificAttrs.contains(key)) {
                    continue;
                }

                if (key.equals(Provisioning.A_zmailDistributionListSubscriptionPolicy) ||
                    key.equals(Provisioning.A_zmailDistributionListUnsubscriptionPolicy)) {
                    // subscription policies are encoded differently, using Group API that returns
                    // default policy if the policy attrs are not set.
                } else {
                    Object value = entry.getValue();
                    if (value instanceof String[]) {
                        String sa[] = (String[]) value;
                        for (int i = 0; i < sa.length; i++) {
                            eParent.addKeyValuePair(key, sa[i], AccountConstants.E_A, AccountConstants.A_N);
                        }
                    } else {
                        eParent.addKeyValuePair(key, (String) value, AccountConstants.E_A, AccountConstants.A_N);
                    }
                }
            }
        }

        if (specificAttrs == null || specificAttrs.contains(Provisioning.A_zmailDistributionListSubscriptionPolicy)) {
            eParent.addKeyValuePair(Provisioning.A_zmailDistributionListSubscriptionPolicy,
                    group.getSubscriptionPolicy().name(),
                    AccountConstants.E_A, AccountConstants.A_N);
        }

        if (specificAttrs == null || specificAttrs.contains(Provisioning.A_zmailDistributionListUnsubscriptionPolicy)) {
            eParent.addKeyValuePair(Provisioning.A_zmailDistributionListUnsubscriptionPolicy,
                    group.getUnsubscriptionPolicy().name(),
                    AccountConstants.E_A, AccountConstants.A_N);
        }

    }

    public static Set<String> visibleAttrs(Iterable<String> needAttrs, boolean isOwner) {
        Set<String> visibleAttrs = Sets.newHashSet();

        Set<String> allowedAttrs = isOwner ? OWNER_ATTRS : NON_OWNER_ATTRS;

        for (String attr : needAttrs) {
            if (allowedAttrs.contains(attr)) {
                visibleAttrs.add(attr);
            }
        }

        return visibleAttrs;
    }

}

