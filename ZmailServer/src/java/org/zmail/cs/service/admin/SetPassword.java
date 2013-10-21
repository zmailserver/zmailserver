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
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.cs.account.Provisioning.SetPasswordResult;
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
public class SetPassword extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow on accounts domain admin has access to
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String id = request.getAttribute(AdminConstants.E_ID);
        String newPassword = request.getAttribute(AdminConstants.E_NEW_PASSWORD);

	    Account account = prov.get(AccountBy.id, id, zsc.getAuthToken());
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);
        
        boolean enforcePasswordPolicy;
        if (account.isCalendarResource()) {
            // need a CalendarResource instance for RightChecker
            CalendarResource resource = prov.get(CalendarResourceBy.id, id);
            enforcePasswordPolicy = checkCalendarResourceRights(zsc, resource);
        } else {
            enforcePasswordPolicy = checkAccountRights(zsc, account);
        }
        
        SetPasswordResult result = prov.setPassword(account, newPassword, enforcePasswordPolicy);
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "SetPassword","name", account.getName()}));


	    Element response = zsc.createElement(AdminConstants.SET_PASSWORD_RESPONSE);
	            
        if (result.hasMessage()) {
            ZmailLog.security.info(result.getMessage());
            response.addElement(AdminConstants.E_MESSAGE).setText(result.getMessage());
        }
        
	    return response;
	}
	
	/*
	 * returns whether password strength policies should be enforced for the authed user
	 * 
	 * returns false if user can setAccountPassword
	 * returns true if user cannot setAccountPassword but can changeAccountPassword
	 * 
	 * throws PERM_DENIED if user doesn't have either right
	 */
	private boolean checkAccountRights(ZmailSoapContext zsc, Account acct) 
	throws ServiceException {
	    try {
	        checkAccountRight(zsc, acct, Admin.R_setAccountPassword);
	        return false;
	    } catch (ServiceException e) {
	        if (ServiceException.PERM_DENIED.equals(e.getCode())) {
	            checkAccountRight(zsc, acct, Admin.R_changeAccountPassword);
	            return true;
	        } else {
	            throw e;
	        }
	    }
	}

	/*
     * returns whether password strength policies should be enforced for the authed user
     * 
     * returns false if user can setCalendarResourcePassword
     * returns true if user cannot setCalendarResourcePassword but can changeCalendarResourcePassword
     * 
     * throws PERM_DENIED if user doesn't have either right
     */
    private boolean checkCalendarResourceRights(ZmailSoapContext zsc, CalendarResource cr) 
    throws ServiceException {
        try {
            checkCalendarResourceRight(zsc, cr, Admin.R_setCalendarResourcePassword);
            return false;
        } catch (ServiceException e) {
            if (ServiceException.PERM_DENIED.equals(e.getCode())) {
                checkCalendarResourceRight(zsc, cr, Admin.R_changeCalendarResourcePassword);
                return true;
            } else {
                throw e;
            }
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_setAccountPassword);
        relatedRights.add(Admin.R_changeAccountPassword);
        relatedRights.add(Admin.R_setCalendarResourcePassword);
        relatedRights.add(Admin.R_changeCalendarResourcePassword);
    }
}