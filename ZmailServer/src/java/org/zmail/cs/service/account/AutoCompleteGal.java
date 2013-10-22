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
package com.zimbra.cs.service.account;

import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.gal.GalSearchControl;
import com.zimbra.cs.gal.GalSearchParams;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.type.GalSearchType;

/**
 * @since May 26, 2004
 * @author schemers
 */
public class AutoCompleteGal extends GalDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Account account = getRequestedAccount(getZimbraSoapContext(context));

        if (!canAccessAccount(zsc, account))
            throw ServiceException.PERM_DENIED("can not access account");
        
        String name = request.getAttribute(AccountConstants.E_NAME);
        String typeStr = request.getAttribute(AccountConstants.A_TYPE, "account");
        GalSearchType type = GalSearchType.fromString(typeStr);

        boolean needCanExpand = request.getAttributeBool(AccountConstants.A_NEED_EXP, false);

        String galAcctId = request.getAttribute(AccountConstants.A_GAL_ACCOUNT_ID, null);
        
        GalSearchParams params = new GalSearchParams(account, zsc);
        params.setType(type);
        params.setRequest(request);
        params.setQuery(name);
        params.setLimit(account.getContactAutoCompleteMaxResults());
        params.setNeedCanExpand(needCanExpand);
        params.setResponseName(AccountConstants.AUTO_COMPLETE_GAL_RESPONSE);
        if (galAcctId != null)
            params.setGalSyncAccount(Provisioning.getInstance().getAccountById(galAcctId));
        GalSearchControl gal = new GalSearchControl(params);
        gal.autocomplete();
        return params.getResultCallback().getResponse();
    }

    @Override
    public boolean needsAuth(Map<String, Object> context) {
        return true;
    }
}
