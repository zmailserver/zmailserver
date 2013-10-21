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
package org.zmail.cs.service.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.fb.FreeBusyProvider;
import org.zmail.soap.ZmailSoapContext;

public class PushFreeBusy extends AdminDocumentHandler {
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
    	Provisioning prov = Provisioning.getInstance();
        
    	Element domainElem = request.getOptionalElement(AdminConstants.E_DOMAIN);
        if (domainElem == null) {
        	Iterator<Element> accounts = request.elementIterator(AdminConstants.E_ACCOUNT);
        	while (accounts.hasNext()) {
        		String accountId = accounts.next().getAttribute(AdminConstants.A_ID, null);
        		if (accountId == null)
        			continue;
        		Account acct = prov.get(Key.AccountBy.id, accountId, zsc.getAuthToken());
        		if (acct == null) {
        			ZmailLog.misc.warn("invalid accountId: "+accountId);
        			continue;
        		}
        		if (!Provisioning.onLocalServer(acct)) {
        			ZmailLog.misc.warn("account is not on this server: "+accountId);
        			continue;
        		}
                checkAdminLoginAsRight(zsc, prov, acct);
            	FreeBusyProvider.mailboxChanged(accountId);
        	}
        } else {
        	String[] domains = domainElem.getAttribute(AdminConstants.A_NAME).split(",");
        	Server s = prov.getLocalServer();
    		NamedEntry.Visitor visitor = new PushFreeBusyVisitor(zsc, prov, this);
        	for (String domain : domains) {
            	Domain d = prov.get(Key.DomainBy.name, domain);
        		prov.getAllAccounts(d, s, visitor);
        	}
        }

        Element response = zsc.createElement(AdminConstants.PUSH_FREE_BUSY_RESPONSE);
        return response;
    }
    
    private static class PushFreeBusyVisitor implements NamedEntry.Visitor {
        
        ZmailSoapContext mZsc;
        Provisioning mProv;
        AdminDocumentHandler mHandler;
        
        PushFreeBusyVisitor(ZmailSoapContext zsc, Provisioning prov, AdminDocumentHandler handler) {
            mZsc = zsc;
            mProv = prov;
            mHandler = handler;
        }
        
        public void visit(NamedEntry entry) throws ServiceException {
            if (entry instanceof Account && Provisioning.onLocalServer((Account)entry)) {
            	Account acct = (Account) entry;
                String[] fps = acct.getForeignPrincipal();
				if (fps != null && fps.length > 0) {
					for (String fp : fps) {
						if (fp.startsWith(Provisioning.FP_PREFIX_AD)) {
							int idx = fp.indexOf(':');
							if (idx != -1) {
				                mHandler.checkAdminLoginAsRight(mZsc, mProv, acct);
				                FreeBusyProvider.mailboxChanged(acct.getId());
				                break;
							}
						}
					}
				}
            }
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_adminLoginAs);
        relatedRights.add(Admin.R_adminLoginCalendarResourceAs);
        notes.add(AdminRightCheckPoint.Notes.ADMIN_LOGIN_AS);
    }
}