/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.DataSourceBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.soap.ZmailSoapContext;


public class ImportData extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account account = getRequestedAccount(zsc);

        for (Element elem : request.listElements()) {
            DataSource ds;

            String name, id = elem.getAttribute(MailConstants.A_ID, null);
            if (id != null) {
                ds = prov.get(account, Key.DataSourceBy.id, id);
                if (ds == null) {
                    throw ServiceException.INVALID_REQUEST("Could not find Data Source with id " + id, null);
                }
            } else if ((name = elem.getAttribute(MailConstants.A_NAME, null)) != null) {
                ds = prov.get(account, Key.DataSourceBy.name, name);
                if (ds == null) {
                    throw ServiceException.INVALID_REQUEST("Could not find Data Source with name " + name, null);
                }
            } else {
                throw ServiceException.INVALID_REQUEST("must specify either 'id' or 'name'", null);
            }

            ZmailLog.addDataSourceNameToContext(ds.getName());
            DataSourceManager.asyncImportData(ds);
        }

        Element response = zsc.createElement(MailConstants.IMPORT_DATA_RESPONSE);
        return response;
    }

}
