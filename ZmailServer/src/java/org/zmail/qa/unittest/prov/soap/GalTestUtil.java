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
package org.zmail.qa.unittest.prov.soap;

import org.junit.Assert;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapTransport;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.qa.unittest.TestUtil;

public class GalTestUtil {
    static final String GAL_SYNC_ACCOUNT_NAME = "galsync";

    public static enum GSAType {
        zmail,
        external,
        both
    }

    public static void disableGalSyncAccount(Provisioning prov, String domainName) 
    throws Exception {
        Domain domain = prov.get(Key.DomainBy.name, domainName);
        
        String[] galSyncAcctIds = domain.getGalAccountId();
        for (String galSyncAcctId : galSyncAcctIds) {
            prov.deleteAccount(galSyncAcctId);
        }
        
        domain.unsetGalAccountId();
    }

    
    public static void enableGalSyncAccount(Provisioning prov, String domainName) 
    throws Exception {
        enableGalSyncAccount(prov, domainName, (String)null);
    }
    
    public static void enableGalSyncAccount(Provisioning prov, String domainName, 
            String galSyncAcctLocalpart) 
    throws Exception {
        enableGalSyncAccount(prov, domainName, galSyncAcctLocalpart, GSAType.zmail);
    }
    
    public static void enableGalSyncAccount(Provisioning prov, String domainName, GSAType type) 
    throws Exception {
        enableGalSyncAccount(prov, domainName, (String)null, type);
    }

    public static void enableGalSyncAccount(Provisioning prov, String domainName, 
            String galSyncAcctLocalpart, GSAType type) 
    throws Exception {
        Domain domain = prov.get(Key.DomainBy.name, domainName);
        String[] galSyncAcctIds = domain.getGalAccountId();
        if (galSyncAcctIds.length > 0) {
            // already enabled
            return;
        } else {
            String localpart = galSyncAcctLocalpart == null ? 
                    GAL_SYNC_ACCOUNT_NAME :
                    galSyncAcctLocalpart;
            GalTestUtil.createAndSyncGalSyncAccount(
                    TestUtil.getAddress(localpart, domainName), 
                    domainName, type);
        }
    
    }

    static void createAndSyncGalSyncAccount(String galSyncAcctName, String domainName, GSAType type) 
    throws Exception {
        String dataSourceName;
        String dataSourceType;
        String folderName;
        if (type == GSAType.zmail || type == GSAType.both) {
            dataSourceName = "zmail";
            dataSourceType = "zmail";
            folderName = "zmail-gal-contacts";
        } else {
            dataSourceName = "external";
            dataSourceType = "ldap";
            folderName = "external-gal-contacts";
        }
        
        
        SoapTransport transport = TestUtil.getAdminSoapTransport();
        
        //
        // create gal sync account and data sources, then force sync
        //
        String gsaZmailId = GalTestUtil.createGalSyncAccountOrDataSource(
                transport, galSyncAcctName, domainName, 
                dataSourceName, dataSourceType, folderName);
        
        GalTestUtil.syncGASDataSource(transport, gsaZmailId, dataSourceName);
        
        if (type == GSAType.both) {
            dataSourceName = "external";
            dataSourceType = "ldap";
            folderName = "external-gal-contacts";
            GalTestUtil.createGalSyncAccountOrDataSource(
                    transport, galSyncAcctName, domainName, 
                    dataSourceName, dataSourceType, folderName);
            GalTestUtil.syncGASDataSource(transport, gsaZmailId, dataSourceName);
        }
        
        //
        // index the gal sync account (otherwise the first search will fail)
        //
        Element eReIndex = Element.create(transport.getRequestProtocol(), 
                AdminConstants.REINDEX_REQUEST);
        eReIndex.addAttribute(AdminConstants.A_ACTION, "start");
        Element eMbox = eReIndex.addElement(AdminConstants.E_MAILBOX);
        eMbox.addAttribute(AdminConstants.A_ID, gsaZmailId);
        transport.invoke(eReIndex);
        
        // wait for the reindex to finish
        Thread.sleep(2000);
    }

    static String createGalSyncAccountOrDataSource(SoapTransport transport,
            String galSyncAcctName, String domainName, 
            String dataSourceName, String dataSourceType, String folderName) 
    throws Exception {
        
        Element eCreateReq = Element.create(transport.getRequestProtocol(), 
                AdminConstants.CREATE_GAL_SYNC_ACCOUNT_REQUEST);
        
        eCreateReq.addAttribute(AdminConstants.E_NAME, dataSourceName);
        eCreateReq.addAttribute(AdminConstants.E_DOMAIN, domainName);
        eCreateReq.addAttribute(AdminConstants.A_TYPE, dataSourceType);
        eCreateReq.addAttribute(AdminConstants.E_FOLDER, folderName);
        
        Element eAccount = eCreateReq.addElement(AdminConstants.E_ACCOUNT);
        eAccount.addAttribute(AdminConstants.A_BY, AccountBy.name.name());
        eAccount.setText(galSyncAcctName);
        
        Element response = transport.invoke(eCreateReq);
        
        eAccount = response.getElement(AdminConstants.E_ACCOUNT);
        String name = eAccount.getAttribute(AccountConstants.A_NAME);
        String id = eAccount.getAttribute(AccountConstants.A_ID);
        Assert.assertEquals(galSyncAcctName, name);
        
        return id;
    }

    static void syncGASDataSource(SoapTransport transport, String gsaZmailId, 
            String dataSourceName) 
    throws Exception {
        Element eSyncReq = Element.create(transport.getRequestProtocol(), 
                AdminConstants.SYNC_GAL_ACCOUNT_REQUEST);
        
        Element eAccount = eSyncReq.addElement(AdminConstants.E_ACCOUNT);
        eAccount.addAttribute(AccountConstants.A_ID, gsaZmailId);
        
        Element eDataSource = eAccount.addElement(AdminConstants.E_DATASOURCE);
        eDataSource.addAttribute(AdminConstants.A_RESET, "TRUE");
        eDataSource.addAttribute(AdminConstants.A_BY, AccountBy.name.name());
        eDataSource.setText(dataSourceName);
        
        transport.invoke(eSyncReq);
    }


}
