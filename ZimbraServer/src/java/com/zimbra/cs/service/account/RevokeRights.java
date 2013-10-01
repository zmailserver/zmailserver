/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.service.account;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.accesscontrol.ACLUtil;
import com.zimbra.cs.account.accesscontrol.ZimbraACE;
import com.zimbra.soap.ZimbraSoapContext;

public class RevokeRights extends AccountDocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context) 
    throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canAccessAccount(zsc, account)) {
            throw ServiceException.PERM_DENIED("can not access account");
        }
        
        Set<ZimbraACE> aces = new HashSet<ZimbraACE>();
        for (Element eACE : request.listElements(AccountConstants.E_ACE)) {
            ZimbraACE ace = GrantRights.handleACE(eACE, zsc, false);
            aces.add(ace);
        }

        // TODO, change to Provisioning.grantPermission?
        List<ZimbraACE> revoked = ACLUtil.revokeRight(Provisioning.getInstance(), account, aces);
        Element response = zsc.createElement(AccountConstants.REVOKE_RIGHTS_RESPONSE);
        if (aces != null) {
            for (ZimbraACE ace : revoked) {
                ToXML.encodeACE(response, ace);
            }
        }
        return response;
    }
    
}
