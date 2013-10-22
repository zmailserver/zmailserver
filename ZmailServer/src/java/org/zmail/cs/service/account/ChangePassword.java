/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on Sep 3, 2004
 */
package com.zimbra.cs.service.account;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.StringUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException.AuthFailedServiceException;
import com.zimbra.cs.account.AuthToken;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.cs.service.AuthProvider;
import com.zimbra.soap.ZimbraSoapContext;

import java.util.Map;

/**
 * @author dkarp
 */
public class ChangePassword extends AccountDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
	    
	    if (!checkPasswordSecurity(context))
            throw ServiceException.INVALID_REQUEST("clear text password is not allowed", null);
	    
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        String namePassedIn = request.getAttribute(AccountConstants.E_ACCOUNT);
        String name = namePassedIn;
        
        Element virtualHostEl = request.getOptionalElement(AccountConstants.E_VIRTUAL_HOST);
        String virtualHost = virtualHostEl == null ? null : virtualHostEl.getText().toLowerCase();
        
        if (virtualHost != null && name.indexOf('@') == -1) {
            Domain d = prov.get(Key.DomainBy.virtualHostname, virtualHost);
            if (d != null)
                name = name + "@" + d.getName();
        }
        
        Account acct = prov.get(AccountBy.name, name, zsc.getAuthToken());
        if (acct == null)
            throw AuthFailedServiceException.AUTH_FAILED(name, namePassedIn, "account not found");
        
        // proxyIfNecessary is called by the SOAP framework only for 
        // requests that require auth.  ChangePassword does not require 
        // an auth token.  Proxy here if this is not the home server of the account.
        if (!Provisioning.onLocalServer(acct)) {
            try {
                return proxyRequest(request, context, acct.getId());
            } catch (ServiceException e) {
                // if something went wrong proxying the request, just execute it locally
                if (ServiceException.PROXY_ERROR.equals(e.getCode())) {
                    ZimbraLog.account.warn("encountered proxy error", e);
                } else {
                    // but if it's a real error, it's a real error
                    throw e;
                }
            }
        }
        
		String oldPassword = request.getAttribute(AccountConstants.E_OLD_PASSWORD);
		String newPassword = request.getAttribute(AccountConstants.E_PASSWORD);
        if (acct.isIsExternalVirtualAccount() && StringUtil.isNullOrEmpty(oldPassword)
                && !acct.isVirtualAccountInitialPasswordSet() && acct.getId().equals(zsc.getAuthtokenAccountId())) {
            // need a valid auth token in this case
            AuthProvider.validateAuthToken(prov, zsc.getAuthToken(), false);
            prov.setPassword(acct, newPassword, true);
            acct.setVirtualAccountInitialPasswordSet(true);
        } else {
		    prov.changePassword(acct, oldPassword, newPassword);
        }
        AuthToken at = AuthProvider.getAuthToken(acct);

        Element response = zsc.createElement(AccountConstants.CHANGE_PASSWORD_RESPONSE);
        at.encodeAuthResp(response, false);
        response.addAttribute(AccountConstants.E_LIFETIME, at.getExpires() - System.currentTimeMillis(), Element.Disposition.CONTENT);
        return response;
	}

    public boolean needsAuth(Map<String, Object> context) {
        // This command can be sent before authenticating, so this method
        // should return false.  The Account.changePassword() method called
        // from handle() will internally make sure the old password provided
        // matches the current password of the account.
        //
        // The user identity in the auth token, if any, is ignored.
        return false;
    }
}
