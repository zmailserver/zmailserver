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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DeleteGalSyncAccount extends AdminDocumentHandler {

    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    Element acctElem = request.getElement(AdminConstants.E_ACCOUNT);
	    String acctKey = acctElem.getAttribute(AdminConstants.A_BY);
        String acctValue = acctElem.getText();

        Account account = prov.get(AccountBy.fromString(acctKey), acctValue);
		if (account == null)
			throw AccountServiceException.NO_SUCH_ACCOUNT(acctValue);
		
		checkAccountRight(zsc, account, Admin.R_deleteAccount); 
		
		String id = account.getId();
		HashSet<String> acctIds = new HashSet<String>();
		Domain domain = prov.getDomain(account);
		Collections.addAll(acctIds, domain.getGalAccountId());
		if (!acctIds.contains(id))
			throw AccountServiceException.NO_SUCH_ACCOUNT(id);
		acctIds.remove(id);
		HashMap<String,Object> attrs = new HashMap<String,Object>();
		attrs.put(Provisioning.A_zmailGalAccountId, acctIds);
		prov.modifyAttrs(domain, attrs);
		prov.deleteAccount(id);
		
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "DeleteGalSyncAccount", "id", id} ));         

	    Element response = zsc.createElement(AdminConstants.DELETE_GAL_SYNC_ACCOUNT_RESPONSE);
	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteAccount);
    }
}