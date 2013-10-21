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

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.DataSource.DataImport;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.gal.GalGroup;
import org.zmail.cs.gal.GalImport;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.type.DataSourceType;

public final class SyncGalAccount extends AdminDocumentHandler {

    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_ACCOUNT, AdminConstants.A_ID };

    @Override
    protected String[] getProxiedAccountPath() {
        return TARGET_ACCOUNT_PATH;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.TODO); //TODO
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        ZmailLog.addToContext(ZmailLog.C_ANAME, getAuthenticatedAccount(zsc).getName());

        for (Element accountEl : request.listElements(AdminConstants.E_ACCOUNT)) {
            String accountId = accountEl.getAttribute(AdminConstants.A_ID);
            Account account = prov.getAccountById(accountId);
            if (account == null) {
                throw AccountServiceException.NO_SUCH_ACCOUNT(accountId);
            }
            ZmailLog.addToContext(ZmailLog.C_NAME, account.getName());

            for (Element dsEl : accountEl.listElements(AdminConstants.E_DATASOURCE)) {
                String by = dsEl.getAttribute(AdminConstants.A_BY);
                String name = dsEl.getText();
                DataSource ds = by.equals("id") ? account.getDataSourceById(name) : account.getDataSourceByName(name);
                if (ds == null) {
                    throw AccountServiceException.NO_SUCH_DATA_SOURCE(name);
                }
                if (!ds.getType().equals(DataSourceType.gal)) {
                    continue;
                }
                boolean fullSync = dsEl.getAttributeBool(AdminConstants.A_FULLSYNC, false);
                boolean reset = dsEl.getAttributeBool(AdminConstants.A_RESET, false);
                int fid = ds.getFolderId();

                DataImport dataImport = DataSourceManager.getInstance().getDataImport(ds);
                if (dataImport instanceof GalImport) {
                    ((GalImport) dataImport).importGal(fid, (reset ? reset : fullSync), reset);
                }
                //flush domain gal group cache
                Domain domain = prov.getDomain(account);
                GalGroup.flushCache(domain);
            }
        }

        return zsc.createElement(AdminConstants.SYNC_GAL_ACCOUNT_RESPONSE);
    }

}
