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
import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * Removes a custom logger from the given account.
 * 
 * @author bburtin
 */
public class RemoveAccountLogger extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_manageAccountLogger);
        
        // Look up account, if specified.
        Account account = null;
        String accountName = null;
        if (request.getOptionalElement(AdminConstants.E_ID) != null ||
            request.getOptionalElement(AdminConstants.E_ACCOUNT) != null) {
            account = AddAccountLogger.getAccountFromLoggerRequest(request);
            accountName = account.getName();
        }
        
        // Look up log category, if specified.
        Element eLogger = request.getOptionalElement(AdminConstants.E_LOGGER);
        String category = null;
        if (eLogger != null) {
            category = eLogger.getAttribute(AdminConstants.A_CATEGORY);
            if (category.equalsIgnoreCase(AddAccountLogger.CATEGORY_ALL)) {
                category = null;
            } else if (!LogFactory.logExists(category)) {
                throw ServiceException.INVALID_REQUEST("Log category " + category + " does not exist.", null);
            }
        }

        // Do the work.
        for (Log log : LogFactory.getAllLoggers()) {
            if (category == null || log.getCategory().equals(category)) {
                if (accountName != null) {
                    boolean removed = log.removeAccountLogger(accountName);
                    if (removed) {
                        ZmailLog.misc.info("Removed logger for account %s from category %s.",
                            accountName, log.getCategory());
                    }
                } else {
                    int count = log.removeAccountLoggers();
                    if (count > 0) {
                        ZmailLog.misc.info("Removed %d custom loggers from category %s.",
                            count, log.getCategory());
                    }
                }
            }
        }
        
        // Send response.
        Element response = zsc.createElement(AdminConstants.REMOVE_ACCOUNT_LOGGER_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageAccountLogger);
    }
}
