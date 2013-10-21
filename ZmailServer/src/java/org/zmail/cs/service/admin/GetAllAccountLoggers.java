/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.AccountLogger;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

public class GetAllAccountLoggers extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_manageAccountLogger);
        
        Provisioning prov = Provisioning.getInstance();
        Map<String, Element> accountElements = new HashMap<String, Element>();
        
        Element response = zsc.createElement(AdminConstants.GET_ALL_ACCOUNT_LOGGERS_RESPONSE);
        for (AccountLogger al : LogFactory.getAllAccountLoggers()) {
            // Look up account
            Account account = prov.get(AccountBy.name, al.getAccountName(), zsc.getAuthToken());
            if (account == null) {
                ZmailLog.misc.info("GetAllAccountLoggers: unable to find account '%s'.  Ignoring account logger.",
                    al.getAccountName());
                continue;
            }
            
            // Add elements
            Element eAccountLogger = accountElements.get(account.getId());
            if (eAccountLogger == null) {
                eAccountLogger = response.addElement(AdminConstants.E_ACCOUNT_LOGGER);
                accountElements.put(account.getId(), eAccountLogger);
            }
            eAccountLogger.addAttribute(AdminConstants.A_ID, account.getId());
            eAccountLogger.addAttribute(AdminConstants.A_NAME, account.getName());
            
            Element eLogger = eAccountLogger.addElement(AdminConstants.E_LOGGER);
            eLogger.addAttribute(AdminConstants.A_CATEGORY, al.getCategory());
            eLogger.addAttribute(AdminConstants.A_LEVEL, al.getLevel().toString());
        }
        
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageAccountLogger);
    }
}
