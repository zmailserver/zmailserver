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
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class RenameAccount extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    /**
     * must be careful and only allow renames from/to domains a domain admin can see
     */
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String id = request.getAttribute(AdminConstants.E_ID);
        String newName = request.getAttribute(AdminConstants.E_NEW_NAME);

	    Account account = prov.get(AccountBy.id, id, zsc.getAuthToken());
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);

        String oldName = account.getName();
        
        // check if the admin can rename the account
        checkAccountRight(zsc, account, Admin.R_renameAccount);

        // check if the admin can "create account" in the domain (can be same or diff)
        checkDomainRightByEmail(zsc, newName, Admin.R_createAccount);

        Mailbox mbox = Provisioning.onLocalServer(account) ? MailboxManager.getInstance().getMailboxByAccount(account) : null;
        prov.renameAccount(id, newName);
        if (mbox != null)
            mbox.renameMailbox(oldName, newName);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "RenameAccount","name", oldName, "newName", newName})); 
        
        // get again with new name...
        account = prov.get(AccountBy.id, id, true, zsc.getAuthToken());
        if (account == null)
            throw ServiceException.FAILURE("unable to get account after rename: " + id, null);
	    Element response = zsc.createElement(AdminConstants.RENAME_ACCOUNT_RESPONSE);
        ToXML.encodeAccount(response, account);
	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_renameAccount);
        relatedRights.add(Admin.R_createAccount);
    }
}
