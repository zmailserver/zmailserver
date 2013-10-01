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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.DataSourceBy;
import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.soap.SoapFaultException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.datasource.DataSourceManager;
import com.zimbra.cs.db.DbDataSource;
import com.zimbra.cs.db.DbPop3Message;
import com.zimbra.cs.ldap.LdapUtil;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.soap.ZimbraSoapContext;


public class ModifyDataSource extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException, SoapFaultException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");

        //boolean wipeOutOldData = false;

        Element eDataSource = CreateDataSource.getDataSourceElement(request);
        DataSourceType type = DataSourceType.fromString(eDataSource.getName());

        String id = eDataSource.getAttribute(MailConstants.A_ID);
        DataSource ds = prov.get(account, Key.DataSourceBy.id, id);
        if (ds == null) {
            throw ServiceException.INVALID_REQUEST("Unable to find data source with id=" + id, null);
        }

        Map<String, Object> dsAttrs = new HashMap<String, Object>();
        String value = eDataSource.getAttribute(MailConstants.A_NAME, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraDataSourceName, value);
        value = eDataSource.getAttribute(MailConstants.A_DS_IS_ENABLED, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraDataSourceEnabled,
                LdapUtil.getLdapBooleanString(eDataSource.getAttributeBool(MailConstants.A_DS_IS_ENABLED)));
        value = eDataSource.getAttribute(MailConstants.A_FOLDER, null);
        if (value != null) {
            Mailbox mbox = getRequestedMailbox(zsc);
            CreateDataSource.validateFolderId(account, mbox, eDataSource, type);
        	dsAttrs.put(Provisioning.A_zimbraDataSourceFolderId, value);
        }

        value = eDataSource.getAttribute(MailConstants.A_DS_HOST, null);
        if (value != null && !value.equals(ds.getHost())) {
            dsAttrs.put(Provisioning.A_zimbraDataSourceHost, value);
        }

        value = eDataSource.getAttribute(MailConstants.A_DS_PORT, null);
        if (value != null)
        	dsAttrs.put(Provisioning.A_zimbraDataSourcePort, value);
        value = eDataSource.getAttribute(MailConstants.A_DS_CONNECTION_TYPE, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraDataSourceConnectionType, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_USERNAME, null);
        if (value != null && !value.equals(ds.getUsername())) {
        	dsAttrs.put(Provisioning.A_zimbraDataSourceUsername, value);
        }

        value = eDataSource.getAttribute(MailConstants.A_DS_PASSWORD, null);
        if (value != null)
        	dsAttrs.put(Provisioning.A_zimbraDataSourcePassword, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_LEAVE_ON_SERVER, null);
        if (value != null) {
            if (type != DataSourceType.pop3) {
                String msg = String.format("%s only allowed for %s data sources",
                    MailConstants.A_DS_LEAVE_ON_SERVER, MailConstants.E_DS_POP3);
                throw ServiceException.INVALID_REQUEST(msg, null);
            }
            boolean newValue = eDataSource.getAttributeBool(MailConstants.A_DS_LEAVE_ON_SERVER);
            if (newValue != ds.leaveOnServer()) {
                dsAttrs.put(Provisioning.A_zimbraDataSourceLeaveOnServer,
                    LdapUtil.getLdapBooleanString(newValue));
                Mailbox mbox = getRequestedMailbox(zsc);
                DbPop3Message.deleteUids(mbox, ds.getId());
                DbDataSource.deleteAllMappings(ds);
            }
        }

        value = eDataSource.getAttribute(MailConstants.A_DS_POLLING_INTERVAL, null);
        if (value != null) {
            dsAttrs.put(Provisioning.A_zimbraDataSourcePollingInterval, value);
        }

        // import class
        String importClass = eDataSource.getAttribute(MailConstants.A_DS_IMPORT_CLASS, DataSourceManager.getDefaultImportClass(type));
        if (importClass != null) {
        	dsAttrs.put(Provisioning.A_zimbraDataSourceImportClassName, importClass);
        }

        processCommonOptionalAttrs(dsAttrs, eDataSource);

        prov.modifyDataSource(account, id, dsAttrs);

        Element response = zsc.createElement(MailConstants.MODIFY_DATA_SOURCE_RESPONSE);

        return response;
    }

    public static void processCommonOptionalAttrs(Map<String, Object> dsAttrs, Element eDataSource) throws ServiceException {
        String value;

        value = eDataSource.getAttribute(MailConstants.A_DS_EMAIL_ADDRESS, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraDataSourceEmailAddress, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_USE_ADDRESS_FOR_FORWARD_REPLY, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraDataSourceUseAddressForForwardReply,
                    LdapUtil.getLdapBooleanString(eDataSource.getAttributeBool(MailConstants.A_DS_USE_ADDRESS_FOR_FORWARD_REPLY, false)));

        value = eDataSource.getAttribute(MailConstants.A_DS_DEFAULT_SIGNATURE, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraPrefDefaultSignatureId, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_FORWARD_REPLY_SIGNATURE, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraPrefForwardReplySignatureId, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_FROM_DISPLAY, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraPrefFromDisplay, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_REPLYTO_ADDRESS, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraPrefReplyToAddress, value);

        value = eDataSource.getAttribute(MailConstants.A_DS_REPLYTO_DISPLAY, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraPrefReplyToDisplay, value);
        value = eDataSource.getAttribute(MailConstants.A_DS_POLLING_INTERVAL, null);
        if (value != null)
            dsAttrs.put(Provisioning.A_zimbraDataSourcePollingInterval, value);

        Iterator<Element> attrs = eDataSource.elementIterator(MailConstants.E_ATTRIBUTE);
        if (attrs != null && attrs.hasNext()) {
        	ArrayList<String> attrList = new ArrayList<String>();
        	while (attrs.hasNext()) {
        		attrList.add(attrs.next().getText());
        	}
        	dsAttrs.put(Provisioning.A_zimbraDataSourceAttribute, attrList.toArray(new String[0]));
        }
    }
}
