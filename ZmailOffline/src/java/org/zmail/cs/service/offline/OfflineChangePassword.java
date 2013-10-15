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

package org.zmail.cs.service.offline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.service.RemoteServiceException;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineDataSource;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.offline.util.OfflineYAuth;
import org.zmail.cs.util.yauth.AuthenticationException;
import org.zmail.cs.util.yauth.ErrorCode;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class OfflineChangePassword extends DocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
                    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        String accountId = request.getAttribute(AccountConstants.E_ID);
        Element password = request.getElement(AccountConstants.E_PASSWORD);
        String newPass = password.getText();
        OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
        
        Account acct = prov.getAccount(accountId);
        if (acct == null) {
            throw ServiceException.INVALID_REQUEST("no account found with ID "+accountId, null);
        }
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        String status = "fail";
        try {
            if (prov.isZcsAccount(acct)) {
                attrs.put(OfflineConstants.A_offlineAccountSetup, ProvisioningConstants.TRUE); //flag so modify validates
                attrs.put(OfflineConstants.A_offlineRemotePassword, newPass);
                prov.modifyAttrs(acct, attrs);
                status = "success";
            } else {
                List<DataSource> dataSources = acct.getAllDataSources();
                if (dataSources != null) {
                    for (DataSource ds : dataSources) {
                        boolean needModify = false;
                        if (ds.getAttr(Provisioning.A_zmailDataSourcePassword) != null) {
                            attrs.put(Provisioning.A_zmailDataSourcePassword, newPass);
                            needModify = true;
                        }
                        if (ds.getAttr(OfflineConstants.A_zmailDataSourceSmtpAuthPassword) != null) {
                            attrs.put(OfflineConstants.A_zmailDataSourceSmtpAuthPassword, newPass);
                            needModify = true;
                        }
                        if (needModify) {
                            String domain = ds.getAttr(Provisioning.A_zmailDataSourceDomain);
                            if ("yahoo.com".equals(domain)) {
                                OfflineYAuth.removeToken(ds);
                            }
                            prov.modifyDataSource(acct, ds.getId(), attrs);
                            prov.testDataSource((OfflineDataSource) ds);
                            status = "success";
                        }
                    }
                }
            }
        } catch (ServiceException se) {
            boolean badPass = false;
            if (AccountServiceException.AUTH_FAILED.equals(se.getCode()) || RemoteServiceException.AUTH_FAILURE.equals(se.getCode())) {
                badPass = true;
            } else if (se instanceof AuthenticationException) {
                AuthenticationException ae = (AuthenticationException) se;
                if (ae.getErrorCode() == ErrorCode.INVALID_PASSWORD) {
                    badPass = true;
                }
            }
            if (!badPass) {
                throw se;
            } else {
                if (acct instanceof OfflineAccount && ((OfflineAccount) acct).isDebugTraceEnabled()) {
                    OfflineLog.offline.debug("unable to save new password due to exception",se);
                }
                //if acct debug is on dump to log before failing
            }
        }
        Element response = getResponseElement(zsc);
        response.addAttribute(AccountConstants.A_STATUS, status);
        return response;
    }
}
