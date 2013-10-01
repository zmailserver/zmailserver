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

/*
 * Created on Jun 17, 2004
 */
package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.CalendarResource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.common.account.Key.CalendarResourceBy;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.soap.ZimbraSoapContext;

/**
 * @author schemers
 */
public class RemoveAccountAlias extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow access to domain if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZimbraSoapContext zsc = getZimbraSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String id = request.getAttribute(AdminConstants.E_ID, null);
        String alias = request.getAttribute(AdminConstants.E_ALIAS);

	    Account account = null;
	    if (id != null)
	        account = prov.get(AccountBy.id, id, zsc.getAuthToken());
        
        String acctName = "";
        if (account != null) {
            if (account.isCalendarResource()) {
                // need a CalendarResource instance for RightChecker
                CalendarResource resource = prov.get(Key.CalendarResourceBy.id, id);
                checkCalendarResourceRight(zsc, resource, Admin.R_removeCalendarResourceAlias);
            } else
                checkAccountRight(zsc, account, Admin.R_removeAccountAlias);

            acctName = account.getName();
        }
        
        // if the admin can remove an alias in the domain
        checkDomainRightByEmail(zsc, alias, Admin.R_deleteAlias);
        
        prov.removeAlias(account, alias);
        
        ZimbraLog.security.info(ZimbraLog.encodeAttrs(
                new String[] {"cmd", "RemoveAccountAlias","name", acctName, "alias", alias})); 
        
	    Element response = zsc.createElement(AdminConstants.REMOVE_ACCOUNT_ALIAS_RESPONSE);
	    return response;
	}
	
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_removeAccountAlias);
        relatedRights.add(Admin.R_removeCalendarResourceAlias);
        relatedRights.add(Admin.R_deleteAlias);
    }
}