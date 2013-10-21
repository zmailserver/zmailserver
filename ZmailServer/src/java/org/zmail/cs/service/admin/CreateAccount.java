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

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.CosBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.soap.ZmailSoapContext;

import java.util.List;
import java.util.Map;

/**
 * @author schemers
 */
public class CreateAccount extends AdminDocumentHandler {

    /**
     * must be careful and only create accounts for the domain admin!
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String name = request.getAttribute(AdminConstants.E_NAME).toLowerCase();
	    String password = request.getAttribute(AdminConstants.E_PASSWORD, null);
	    Map<String, Object> attrs = AdminService.getAttrs(request, true);

	    checkDomainRightByEmail(zsc, name, Admin.R_createAccount);
	    checkSetAttrsOnCreate(zsc, TargetType.account, name, attrs);
	    checkCos(zsc, attrs);
        
	    Account account = prov.createAccount(name, password, attrs);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "CreateAccount","name", name}, attrs));         

	    Element response = zsc.createElement(AdminConstants.CREATE_ACCOUNT_RESPONSE);

        ToXML.encodeAccount(response, account);

	    return response;
	}
	
	private void checkCos(ZmailSoapContext zsc, Map<String, Object> attrs) throws ServiceException {
        String cosId = ModifyAccount.getStringAttrNewValue(Provisioning.A_zmailCOSId, attrs);
        if (cosId == null)
            return;  // not setting it
        
        Provisioning prov = Provisioning.getInstance();

        Cos cos = prov.get(Key.CosBy.id, cosId);
        if (cos == null) {
            throw AccountServiceException.NO_SUCH_COS(cosId);
        }
        
        // call checkRight instead of checkCosRight, because:
        // 1. no domain based access manager backward compatibility issue
        // 2. we only want to check right if we are using pure ACL based access manager. 
        checkRight(zsc, cos, Admin.R_assignCos);
    }
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_createAccount);
        
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY, 
                Admin.R_modifyAccount.getName(), "account"));
        
        notes.add("Notes on " + Provisioning.A_zmailCOSId + ": " +
                "If setting " + Provisioning.A_zmailCOSId + ", needs the " + Admin.R_assignCos.getName() + 
                " right on the cos.");
    }
}