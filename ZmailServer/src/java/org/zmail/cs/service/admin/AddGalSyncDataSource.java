/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.AddGalSyncDataSourceRequest;
import org.zmail.soap.admin.type.GalMode;
import org.zmail.soap.type.AccountBy;
import org.zmail.soap.type.AccountSelector;

public class AddGalSyncDataSource extends AdminDocumentHandler {

    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        AddGalSyncDataSourceRequest dsRequest = JaxbUtil.elementToJaxb(request);

        String name = dsRequest.getName();
        String domainStr = dsRequest.getDomain();
        GalMode type = dsRequest.getType();

        AccountSelector acctSelector = dsRequest.getAccount();
        AccountBy acctBy = acctSelector.getBy();
        String acctValue = acctSelector.getKey();
        String folder = dsRequest.getFolder();

        Domain domain = prov.getDomainByName(domainStr);

        if (domain == null)
            throw AccountServiceException.NO_SUCH_DOMAIN(domainStr);

        Account account = null;
        try {
            account = prov.get(acctBy.toKeyDomainBy(), acctValue, zsc.getAuthToken());
        } catch (ServiceException se) {
            ZmailLog.gal.warn("error checking GalSyncAccount", se);
        }

        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(acctValue);

        if (!Provisioning.onLocalServer(account)) {
            String host = account.getMailHost();
            Server server = prov.getServerByName(host);
            return proxyRequest(request, context, server);
        }

        CreateGalSyncAccount.addDataSource(request, zsc, account, domain, folder, name, type);
        
        Element response = zsc.createElement(AdminConstants.ADD_GAL_SYNC_DATASOURCE_RESPONSE);
        ToXML.encodeAccount(response, account, false, emptySet, null);
        return response;
    }

    private static final Set<String> emptySet = Collections.emptySet();
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        // XXX revisit
        relatedRights.add(Admin.R_createAccount);
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY,
                Admin.R_modifyAccount.getName(), "account"));
    }
}