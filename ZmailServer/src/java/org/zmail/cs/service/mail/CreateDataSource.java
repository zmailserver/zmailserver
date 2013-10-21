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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.datasource.DataSourceManager;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;
import org.zmail.soap.ZmailSoapContext;


public class CreateDataSource extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException, SoapFaultException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");
        
        Mailbox mbox = getRequestedMailbox(zsc);
        
        // Create the data source
        Element eDataSource = getDataSourceElement(request);
        DataSourceType type = DataSourceType.fromString(eDataSource.getName());
        Map<String, Object> dsAttrs = new HashMap<String, Object>();

        // Common attributes
        validateFolderId(account, mbox, eDataSource, type);
        String name = eDataSource.getAttribute(MailConstants.A_NAME);
        dsAttrs.put(Provisioning.A_zmailDataSourceFolderId, eDataSource.getAttribute(MailConstants.A_FOLDER));
        dsAttrs.put(Provisioning.A_zmailDataSourceEnabled,
            LdapUtil.getLdapBooleanString(eDataSource.getAttributeBool(MailConstants.A_DS_IS_ENABLED)));
        dsAttrs.put(Provisioning.A_zmailDataSourceImportOnly,
                LdapUtil.getLdapBooleanString(eDataSource.getAttributeBool(MailConstants.A_DS_IS_IMPORTONLY,false)));
        dsAttrs.put(Provisioning.A_zmailDataSourceHost, eDataSource.getAttribute(MailConstants.A_DS_HOST));
        dsAttrs.put(Provisioning.A_zmailDataSourcePort, eDataSource.getAttribute(MailConstants.A_DS_PORT));
        dsAttrs.put(Provisioning.A_zmailDataSourceConnectionType, eDataSource.getAttribute(MailConstants.A_DS_CONNECTION_TYPE));
        dsAttrs.put(Provisioning.A_zmailDataSourceUsername, eDataSource.getAttribute(MailConstants.A_DS_USERNAME));
        dsAttrs.put(Provisioning.A_zmailDataSourcePassword, eDataSource.getAttribute(MailConstants.A_DS_PASSWORD));
        
        String defaultSignature = eDataSource.getAttribute(MailConstants.A_DS_DEFAULT_SIGNATURE, null);
        if (defaultSignature != null) {
            dsAttrs.put(Provisioning.A_zmailPrefDefaultSignatureId, defaultSignature);
        }
        
        String forwardReplySignature = eDataSource.getAttribute(MailConstants.A_DS_FORWARD_REPLY_SIGNATURE, null);
        if (forwardReplySignature != null) {
            dsAttrs.put(Provisioning.A_zmailPrefForwardReplySignatureId, forwardReplySignature);
        }
        
        // type
        dsAttrs.put(Provisioning.A_zmailDataSourceType, type.toString());
        
        // import class
        String importClass = eDataSource.getAttribute(MailConstants.A_DS_IMPORT_CLASS, DataSourceManager.getDefaultImportClass(type));
        if (importClass != null) {
        	dsAttrs.put(Provisioning.A_zmailDataSourceImportClassName, importClass);
        }
        
        // Common optional attributes
        ModifyDataSource.processCommonOptionalAttrs(dsAttrs, eDataSource);
        
        // POP3-specific attributes
        if (type == DataSourceType.pop3) {
            dsAttrs.put(Provisioning.A_zmailDataSourceLeaveOnServer,
                LdapUtil.getLdapBooleanString(eDataSource.getAttributeBool(MailConstants.A_DS_LEAVE_ON_SERVER, true)));
        }
        
        DataSource ds = prov.createDataSource(account, type, name, dsAttrs);
        ZmailLog.addDataSourceNameToContext(ds.getName());
        
        // Assemble response
        Element response = zsc.createElement(MailConstants.CREATE_DATA_SOURCE_RESPONSE);
        eDataSource = response.addElement(type.toString());
        eDataSource.addAttribute(MailConstants.A_ID, ds.getId());
        
        return response;
    }
    
    /**
     * Gets the data source element from the given request.
     */
    static Element getDataSourceElement(Element request)
    throws ServiceException {
        List<Element> subElements = request.listElements();
        if (subElements.size() != 1) {
            String msg = "Only 1 data source allowed per request.  Found " + subElements.size();
            throw ServiceException.INVALID_REQUEST(msg, null);
        }
        
        return subElements.get(0);
    }

    /**
     * Confirms that the folder attribute specifies a valid folder id and is not
     * within the subtree of another datasource
     */
    static void validateFolderId(Account account, Mailbox mbox, Element eDataSource, DataSourceType dsType)
    throws ServiceException {
        int folderId = (int) eDataSource.getAttributeLong(MailConstants.A_FOLDER);
        String id = eDataSource.getAttribute(MailConstants.A_ID, null);

        try {
            mbox.getFolderById(null, folderId);
        } catch (NoSuchItemException e) {
            throw ServiceException.INVALID_REQUEST("Invalid folder id: " + folderId, null);
        }
        for (DataSource ds : account.getAllDataSources()) {
            if (id != null && ds.getId().equals(id))
                continue;
            try {
                for (Folder fldr : mbox.getFolderById(null, ds.getFolderId()).getSubfolderHierarchy()) {
                    if (fldr.getId() == folderId)
                        if ( (DataSourceType.pop3.equals(dsType)) && (DataSourceType.pop3.equals(ds.getType())) ) {
                            // Allows unified inbox to work for more than one Pop3 datasource
                        } else {
                            throw ServiceException.INVALID_REQUEST("Folder location conflict: " + fldr.getPath(), null);
                        }
                }
            } catch (NoSuchItemException e) {
            }
        }
    }
}
