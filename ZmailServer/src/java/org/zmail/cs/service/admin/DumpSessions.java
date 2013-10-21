/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Server;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.session.Session;
import org.zmail.cs.session.SessionCache;
import org.zmail.soap.ZmailSoapContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DumpSessions extends AdminDocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_getSessions);
        
        Element response = zsc.createElement(AdminConstants.DUMP_SESSIONS_RESPONSE);
        
        boolean includeAccounts = request.getAttributeBool(AdminConstants.A_LIST_SESSIONS, false);
        boolean groupByAccount = request.getAttributeBool(AdminConstants.A_GROUP_BY_ACCOUNT, false);
        int totalActiveSessions = 0;
        Provisioning prov = Provisioning.getInstance();
        
        for (Session.Type type : Session.Type.values()) {
            if (type == Session.Type.NULL)
                continue;
            
            if (!includeAccounts) {
                int[] stats = SessionCache.countActive(type);

                if (stats[1] == 0)
                    continue; // no active sessions, skip this type!
                
                Element e = response.addElement(type.name().toLowerCase());
                totalActiveSessions += stats[1];
                e.addAttribute(AdminConstants.A_ACTIVE_ACCOUNTS, stats[0]);
                e.addAttribute(AdminConstants.A_ACTIVE_SESSIONS, stats[1]);
            } else {
                List<Session> sessions = SessionCache.getActiveSessions(type);
                if (sessions.size() == 0)
                    continue; // no active sessions, skip this type!
                
                Element e = response.addElement(type.name().toLowerCase());
                totalActiveSessions+=sessions.size();
                e.addAttribute(AdminConstants.A_ACTIVE_SESSIONS, sessions.size());
                if (sessions.size() == 0)
                    continue;
                
                if (groupByAccount) {
                    // stick the sessions into a big map organized by the account ID
                    HashMap<String/*accountid*/, List<Session>> map = new HashMap<String, List<Session>>();
                    for (Session s : sessions) {
                        List<Session> list = map.get(s.getAuthenticatedAccountId());
                        if (list == null) {
                            list = new ArrayList<Session>();
                            map.put(s.getAuthenticatedAccountId(), list);
                        }
                        list.add(s);
                    }
                    
                    e.addAttribute(AdminConstants.A_ACTIVE_ACCOUNTS, map.size());
                    
                    for (Map.Entry<String, List<Session>> entry : map.entrySet()) {
                        Element acctElt = e.addElement(AdminConstants.A_ZIMBRA_ID);
                        acctElt.addAttribute(AdminConstants.A_NAME, getName(prov, entry.getKey()));
                        acctElt.addAttribute(AdminConstants.A_ID, entry.getKey());
                        for (Session s : entry.getValue()) {
                            encodeSession(acctElt, s, false, prov);
                        }
                    }
                } else {
                    int[] stats = SessionCache.countActive(type);
                    e.addAttribute(AdminConstants.A_ACTIVE_ACCOUNTS, stats[0]);
                    
                    for (Session s : sessions) {
                        encodeSession(e, s, true, prov);
                    }
                }
            }
        }
        response.addAttribute(AdminConstants.A_ACTIVE_SESSIONS, totalActiveSessions);
        
        return response;
    }

    private static String getName(Provisioning prov, String id) {
        try {
            Account acct = prov.get(AccountBy.id, id);
            return acct == null ? id : acct.getName();
        } catch (ServiceException e) {
            return id;
        }
    }

    private static void encodeSession(Element parent, Session s, boolean includeAcct, Provisioning prov) {
        Element sElt = parent.addElement("s");
        if (includeAcct) {
            sElt.addAttribute(AdminConstants.A_ZIMBRA_ID, s.getAuthenticatedAccountId());
            sElt.addAttribute(AdminConstants.A_NAME, getName(prov, s.getAuthenticatedAccountId()));
        }
        sElt.addAttribute(AdminConstants.A_SESSION_ID, s.getSessionId());
        sElt.addAttribute(AdminConstants.A_CREATED_DATE, s.getCreationTime());
        sElt.addAttribute(AdminConstants.A_LAST_ACCESSED_DATE, s.getLastAccessTime());
        s.encodeState(sElt);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getSessions);
    }
}
