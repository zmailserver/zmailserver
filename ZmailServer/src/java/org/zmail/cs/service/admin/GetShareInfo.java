/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ShareInfo;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.service.account.GetShareInfo.ResultFilter;
import org.zmail.cs.service.account.GetShareInfo.ResultFilterByTarget;
import org.zmail.cs.service.account.GetShareInfo.ShareInfoVisitor;
import org.zmail.soap.ZmailSoapContext;

public class GetShareInfo extends ShareInfoHandler {
    
    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_OWNER };
    protected String[] getProxiedAccountElementPath()  { return TARGET_ACCOUNT_PATH; }
    
    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        OperationContext octxt = getOperationContext(zsc, context);
        Provisioning prov = Provisioning.getInstance();
        
        Element eGrantee = request.getOptionalElement(AccountConstants.E_GRANTEE);
        byte granteeType = org.zmail.cs.service.account.GetShareInfo.getGranteeType(eGrantee);
        String granteeId = eGrantee == null? null : eGrantee.getAttribute(AccountConstants.A_ID, null);
        String granteeName = eGrantee == null? null : eGrantee.getAttribute(AccountConstants.A_NAME, null);
        
        Element eOwner = request.getElement(AccountConstants.E_OWNER);
        Account ownerAcct = null;
        AccountBy acctBy = AccountBy.fromString(eOwner.getAttribute(AccountConstants.A_BY));
        String key = eOwner.getText();
        ownerAcct = prov.get(acctBy, key);
            
        // in the account namespace GetShareInfo
        // to defend against harvest attacks return "no shares" instead of error 
        // when an invalid user name/id is used.
        //
        // this is the admin namespace GetShareInfo, we want to let the admin know if 
        // the owner name is bad
        if (ownerAcct == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(key);
        
        checkAdminLoginAsRight(zsc, prov, ownerAcct);
        
        Element response = zsc.createElement(AdminConstants.GET_SHARE_INFO_RESPONSE);
        
        ResultFilter resultFilter = new ResultFilterByTarget(granteeId, granteeName);
        ShareInfoVisitor visitor = new ShareInfoVisitor(prov, response, null, resultFilter);
        ShareInfo.Discover.discover(octxt, prov, null, granteeType, ownerAcct, visitor);
        visitor.finish();
        
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_adminLoginAs);
        relatedRights.add(Admin.R_adminLoginCalendarResourceAs);
        notes.add(AdminRightCheckPoint.Notes.ADMIN_LOGIN_AS);
    }
}