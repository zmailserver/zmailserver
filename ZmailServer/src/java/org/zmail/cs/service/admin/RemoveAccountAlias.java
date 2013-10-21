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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

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

        ZmailSoapContext zsc = getZmailSoapContext(context);
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
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
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