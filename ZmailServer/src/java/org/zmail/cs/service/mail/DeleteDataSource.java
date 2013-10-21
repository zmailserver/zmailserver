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
import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.db.DbDataSource;
import org.zmail.cs.db.DbImapFolder;
import org.zmail.cs.db.DbPop3Message;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;


public class DeleteDataSource extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException, SoapFaultException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");

        Mailbox mbox = getRequestedMailbox(zsc);

        for (Element eDsrc : request.listElements()) {
            DataSource dsrc = null;
            String name, id = eDsrc.getAttribute(MailConstants.A_ID, null);
            
            if (id != null)
                dsrc = prov.get(account, Key.DataSourceBy.id, id);
            else if ((name = eDsrc.getAttribute(MailConstants.A_NAME, null)) != null)
                dsrc = prov.get(account, Key.DataSourceBy.name, name);
            else
                throw ServiceException.INVALID_REQUEST("must specify either 'id' or 'name'", null);

            // note that we're not checking the element name against the actual data source's type
            if (dsrc == null)
                continue;
            String dataSourceId = dsrc.getId();
            DataSourceType dstype = dsrc.getType();

            prov.deleteDataSource(account, dataSourceId);
            if (dstype == DataSourceType.pop3)
                DbPop3Message.deleteUids(mbox, dataSourceId);
            else if (dstype == DataSourceType.imap)
                DbImapFolder.deleteImapData(mbox, dataSourceId);
            DbDataSource.deleteAllMappings(dsrc);
            DataSourceManager.cancelSchedule(account, dataSourceId);
        }
        
        Element response = zsc.createElement(MailConstants.DELETE_DATA_SOURCE_RESPONSE);
        return response;
    }
}
