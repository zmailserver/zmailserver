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
package com.zimbra.cs.service.mail;

import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.DataSourceBy;
import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.soap.SoapFaultException;
import com.zimbra.common.util.SystemUtil;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.datasource.DataSourceManager;
import com.zimbra.soap.ZimbraSoapContext;

import java.util.HashMap;
import java.util.Map;


public class TestDataSource extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException, SoapFaultException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account account = getRequestedAccount(zsc);
        
        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");

        Map<String, Object> testAttrs = new HashMap<String, Object>();
        String testId = "TestId";
        
        // Parse request
        Element eDataSource = CreateDataSource.getDataSourceElement(request);
        DataSourceType type = DataSourceType.fromString(eDataSource.getName());

        String id = eDataSource.getAttribute(MailConstants.A_ID, null);
        String password = null;
        if (id != null) {
            // Testing existing data source
            DataSource dsOrig = prov.get(account, Key.DataSourceBy.id, id);
            Map<String, Object> origAttrs = dsOrig.getAttrs();
            for (String key : origAttrs.keySet()) {
                if (key.equals(Provisioning.A_zimbraDataSourcePassword)) {
                    password = dsOrig.getDecryptedPassword();
                } else {
                    testAttrs.put(key, dsOrig.getAttr(key));
                }
            }
        }

        // Get values from SOAP request.  If testing an existing data source,
        // values in the request override the persisted values.
        String value = eDataSource.getAttribute(MailConstants.A_DS_HOST, null);
        if (value != null) {
            testAttrs.put(Provisioning.A_zimbraDataSourceHost, value);
        }
        value = eDataSource.getAttribute(MailConstants.A_DS_PORT, null);
        if (value != null) {
            testAttrs.put(Provisioning.A_zimbraDataSourcePort, value);
        }
        value = eDataSource.getAttribute(MailConstants.A_DS_CONNECTION_TYPE, null);
        if (value != null) {
            testAttrs.put(Provisioning.A_zimbraDataSourceConnectionType, value);
        }
        value = eDataSource.getAttribute(MailConstants.A_DS_USERNAME, null);
        if (value != null) {
            testAttrs.put(Provisioning.A_zimbraDataSourceUsername, value);
        }
        value = eDataSource.getAttribute(MailConstants.A_DS_PASSWORD, null);
        if (value != null) {
            password = value;
        }
        value = eDataSource.getAttribute(MailConstants.A_DS_LEAVE_ON_SERVER, null);
        if (value != null) {
            testAttrs.put(Provisioning.A_zimbraDataSourceLeaveOnServer, value.toUpperCase());
        }
        value = eDataSource.getAttribute(MailConstants.A_FOLDER, null);
        if (value != null) {
            testAttrs.put(Provisioning.A_zimbraDataSourceFolderId, value);
        }
        
        if (password != null) {
            // Password has to be encrypted explicitly since this is a temporary object.
            // The current implementation of LdapDataSource doesn't perform encryption until
            // the DataSource is saved.
            testAttrs.put(Provisioning.A_zimbraDataSourcePassword, DataSource.encryptData(testId, password));
        }
        
        // import class
        value = eDataSource.getAttribute(MailConstants.A_DS_IMPORT_CLASS, DataSourceManager.getDefaultImportClass(type));
        if (value != null) {
        	testAttrs.put(Provisioning.A_zimbraDataSourceImportClassName, value);
        }
        
        // Common optional attributes
        ModifyDataSource.processCommonOptionalAttrs(testAttrs, eDataSource);
        
        // Perform test and assemble response
        DataSource ds = new DataSource(account, type, "Test", testId, testAttrs, null);
        Element response = zsc.createElement(MailConstants.TEST_DATA_SOURCE_RESPONSE);
        
        /*
        if (type == DataSource.Type.imap) {
            eDataSource = response.addElement(MailConstants.E_DS_IMAP);
        } else {
            eDataSource = response.addElement(MailConstants.E_DS_POP3);
        }
        */
        eDataSource = response.addElement(type.toString());
        
        String error = null;
        try {
        	DataSourceManager.test(ds);
        } catch (ServiceException x) {
        	Throwable t = SystemUtil.getInnermostException(x);
        	error = t.getMessage();
        	if (error == null)
        		error = "datasource test failed";
        }
        if (error == null) {
            eDataSource.addAttribute(MailConstants.A_DS_SUCCESS, true);
        } else {
            eDataSource.addAttribute(MailConstants.A_DS_SUCCESS, false);
            eDataSource.addAttribute(MailConstants.A_DS_ERROR, error);
        }
        
        return response;
    }
}
