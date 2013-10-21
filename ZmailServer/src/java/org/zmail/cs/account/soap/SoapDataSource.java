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

package org.zmail.cs.account.soap;

import java.util.Map;

import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.soap.Element;

class SoapDataSource extends DataSource implements SoapEntry {
        
    SoapDataSource(Account acct, DataSourceType type, String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(acct, type, name, id, attrs, prov);
    }

    SoapDataSource(Account acct, Element e, Provisioning prov) throws ServiceException {
        super(acct,
              DataSourceType.fromString(e.getAttribute(AccountConstants.A_TYPE)),
              e.getAttribute(AccountConstants.A_NAME), e.getAttribute(AccountConstants.A_ID), SoapProvisioning.getAttrs(e), prov);
    }

    public void modifyAttrs(SoapProvisioning prov, Map<String, ? extends Object> attrs, boolean checkImmutable) throws ServiceException {
        // not needed?        
    }

    public void reload(SoapProvisioning prov) throws ServiceException {
        // not needed?
    }

}
