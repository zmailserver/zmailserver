/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.AccountLogger;
import org.zmail.common.util.LogFactory;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

public class GetAccountLoggers extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_manageAccountLogger);
        
        // Look up account
        Account account = AddAccountLogger.getAccountFromLoggerRequest(request);
        
        Element response = zsc.createElement(AdminConstants.GET_ACCOUNT_LOGGERS_RESPONSE);
        for (AccountLogger al : LogFactory.getAllAccountLoggers()) {
            if (al.getAccountName().equals(account.getName())) {
                Element eLogger = response.addElement(AdminConstants.E_LOGGER);
                eLogger.addAttribute(AdminConstants.A_CATEGORY, al.getCategory());
                eLogger.addAttribute(AdminConstants.A_LEVEL, al.getLevel().toString());
            }
        }
        
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageAccountLogger);
    }
}
