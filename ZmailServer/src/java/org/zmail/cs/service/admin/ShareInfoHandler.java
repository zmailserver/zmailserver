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

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.DistributionList;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.DistributionListBy;
import org.zmail.soap.ZmailSoapContext;

public abstract class ShareInfoHandler extends AdminDocumentHandler {

    /**
     * must be careful and only return accounts a domain admin can see
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }
    
    /*
     * DL only for now
     */
    protected NamedEntry getPublishableTargetEntry(ZmailSoapContext zsc, Element request, Provisioning prov) throws ServiceException {
        Element eDl = request.getElement(AdminConstants.E_DL);
        
        NamedEntry entry = null;
        
        String key = eDl.getAttribute(AdminConstants.A_BY);
        String value = eDl.getText();
    
        DistributionList dl = prov.get(Key.DistributionListBy.fromString(key), value);
            
        if (dl == null)
            throw AccountServiceException.NO_SUCH_DISTRIBUTION_LIST(value);
            
        entry = dl;
       
        return entry;
    }
    
    protected Account getOwner(ZmailSoapContext zsc, Element eShare, Provisioning prov, boolean required) throws ServiceException {
        Element eOwner = null;
        if (required)
            eOwner = eShare.getElement(AdminConstants.E_OWNER);
        else
            eOwner = eShare.getOptionalElement(AdminConstants.E_OWNER);
        
        if (eOwner == null)
            return null;
        
        String key = eOwner.getAttribute(AdminConstants.A_BY);
        String value = eOwner.getText();

        Account account = prov.get(AccountBy.fromString(key), value, zsc.getAuthToken());

        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(value);
        
        return account;
    }
    

}
