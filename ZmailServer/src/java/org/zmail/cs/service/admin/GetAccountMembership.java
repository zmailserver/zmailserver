/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on Jun 17, 2004
 */
package org.zmail.cs.service.admin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.DynamicGroup;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class GetAccountMembership extends AdminDocumentHandler {

    /**
     * must be careful and only return accounts a domain admin can see
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        Element a = request.getElement(AdminConstants.E_ACCOUNT);
        String key = a.getAttribute(AdminConstants.A_BY);
        String value = a.getText();

        Account account = prov.get(AccountBy.fromString(key), value, zsc.getAuthToken());

        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(value);

        checkAccountRight(zsc, account, Admin.R_getAccountMembership);

        HashMap<String,String> via = new HashMap<String, String>();
        List<Group> groups = prov.getGroups(account, false, via);
        
        Element response = zsc.createElement(AdminConstants.GET_ACCOUNT_MEMBERSHIP_RESPONSE);
        for (Group group: groups) {
            Element eDL = response.addElement(AdminConstants.E_DL);
            eDL.addAttribute(AdminConstants.A_NAME, group.getName());
            eDL.addAttribute(AdminConstants.A_ID,group.getId());
            eDL.addAttribute(AdminConstants.A_DYNAMIC, group.isDynamic());
            String viaDl = via.get(group.getName());
            if (viaDl != null) {
                eDL.addAttribute(AdminConstants.A_VIA, viaDl);
            }
            
            try {
                if (group.isDynamic()) {
                    checkDynamicGroupRight(zsc, (DynamicGroup) group, needGetAttrsRight());
                } else {
                    checkDistributionListRight(zsc, (DistributionList) group, needGetAttrsRight());
                }
                
                String isAdminGroup = group.getAttr(Provisioning.A_zmailIsAdminGroup);
                if (isAdminGroup != null) {
                    eDL.addElement(AdminConstants.E_A).addAttribute(AdminConstants.A_N, Provisioning.A_zmailIsAdminGroup).setText(isAdminGroup);
                }
            } catch (ServiceException e) {
                if (ServiceException.PERM_DENIED.equals(e.getCode())) {
                    ZmailLog.acl.warn("no permission to view " + Provisioning.A_zmailIsAdminGroup + " of dl " + group.getName());
                }
            }
        }
        return response;
    }
    
    private Set<String> needGetAttrsRight() {
        Set<String> attrsNeeded = new HashSet<String>();
        attrsNeeded.add(Provisioning.A_zmailIsAdminGroup);
        return attrsNeeded;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getAccountMembership);
        notes.add("If the authed admin has get attr right on  distribution list attr " + 
                Provisioning.A_zmailIsAdminGroup + ", it is returned in the response if set.");
    }
}
