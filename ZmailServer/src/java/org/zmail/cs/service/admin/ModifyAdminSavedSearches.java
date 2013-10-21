/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2012 VMware, Inc.
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.service.admin.GetAdminSavedSearches;
import org.zmail.soap.ZmailSoapContext;

public class ModifyAdminSavedSearches extends AdminDocumentHandler {
    
    /**
     * must be careful and only allow on accounts domain admin has access to
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account acct = getRequestedAccount(zsc);
        
        checkAccountRight(zsc, acct, Admin.R_setAdminSavedSearch);

        Element response = zsc.createElement(AdminConstants.MODIFY_ADMIN_SAVED_SEARCHES_RESPONSE);
        
        HashMap<String, String> searches = null;
        for (Iterator it = request.elementIterator(AdminConstants.E_SEARCH); it.hasNext(); ) {
            if (searches == null)
                searches = new HashMap<String, String>();
            Element e = (Element) it.next();
            String name = e.getAttribute(AdminConstants.A_NAME);
            String query = e.getText();
            if (name != null && name.length() != 0)
                searches.put(name, query);
            else
                ZmailLog.account.warn("ModifyAdminSavedSearches: empty search name ignored");
        }

        handle(acct, response, searches);
        return response;
    }
    
    public void handle(Account acct, Element response, HashMap<String, String> modSearches) throws ServiceException {
        String[] searches = acct.getMultiAttr(Provisioning.A_zmailAdminSavedSearches);
        Map<String, GetAdminSavedSearches.AdminSearch> curSearches = new HashMap<String, GetAdminSavedSearches.AdminSearch>();
        for (int i = 0; i < searches.length; i++) {
            GetAdminSavedSearches.AdminSearch as = GetAdminSavedSearches.AdminSearch.parse(searches[i]);
            curSearches.put(as.getName(), as);
        }
        
        for (Iterator it = modSearches.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            String query = (String)entry.getValue();
            GetAdminSavedSearches.AdminSearch mod = new GetAdminSavedSearches.AdminSearch(name, query);
            if (curSearches.containsKey(name)) {
                if (query.length() == 0)
                    curSearches.remove(name);             
                else
                    curSearches.put(name, mod);
            } else {
                if (query.length() == 0)
                    throw ServiceException.INVALID_REQUEST("query for " + name + " is empty", null);
                else
                    curSearches.put(name, mod);
            }
        }

        String[] mods = new String[curSearches.size()];
        int i = 0;
        for (Iterator it = curSearches.values().iterator(); it.hasNext(); ) {
            GetAdminSavedSearches.AdminSearch m = (GetAdminSavedSearches.AdminSearch)it.next();
            mods[i++] = m.encode();
        }
            
        Provisioning prov = Provisioning.getInstance();
        Map<String,String[]> modmap = new HashMap<String,String[]>();
        modmap.put(Provisioning.A_zmailAdminSavedSearches, mods);
        prov.modifyAttrs(acct, modmap);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_setAdminSavedSearch);
    }
}
