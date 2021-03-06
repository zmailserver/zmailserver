/*
 * Copyright 2009 Yutaka Obuchi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//The original is modified for handling OAuth token in Zmail

// Original's copyright and license terms
/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

package sample.oauth.provider;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Metadata;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.service.AuthProviderException;
import org.zmail.cs.service.UserServletContext;
import org.zmail.soap.SoapServlet;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;
import sample.oauth.provider.core.SampleZmOAuthProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class ZmailAuthProviderForOAuth extends AuthProvider{

    ZmailAuthProviderForOAuth() {
        super("oauth");
    }
    
    // AP-TODO-6: dup in ZAuthToken, move to common?
    //public static String cookieName(boolean isAdminReq) {
    //    return isAdminReq? ZmailServlet.COOKIE_ZM_ADMIN_AUTH_TOKEN : ZmailServlet.COOKIE_ZM_AUTH_TOKEN;
    //}
    
    protected AuthToken authToken(HttpServletRequest req, boolean isAdminReq) throws AuthProviderException, AuthTokenException {
        
    	ZmailLog.extensions.debug("authToken(HttpServletRequest req, boolean isAdminReq) is requested.");
    	if(isAdminReq){
    		ZmailLog.extensions.debug("isAdminReq"+isAdminReq);
        	return null;
    	}
    	
        String origUrl = req.getHeader("X-Zmail-Orig-Url");
        OAuthMessage oAuthMessage =
                StringUtil.isNullOrEmpty(origUrl) ?
                        OAuthServlet.getMessage(req, null) : OAuthServlet.getMessage(req, origUrl);

        try {
            if(oAuthMessage.getToken() == null) {
                ZmailLog.extensions.debug("no need for further oauth processing");
                throw AuthProviderException.NO_AUTH_DATA();
            }
        } catch (IOException e) {
            ZmailLog.extensions.debug("Error in getting OAuth token from request", e);
            throw AuthProviderException.FAILURE(e.getMessage());
        }

        UserServletContext userServletContext;
        try {
            userServletContext = new UserServletContext(req, null, null);
        } catch (Exception e) {
            ZmailLog.extensions.debug("Error in creating userServletContext object", e);
            throw AuthProviderException.FAILURE("Error in creating userServletContext object");
        }

        Account account = userServletContext.targetAccount;
        if (account == null) {
            throw AuthProviderException.FAILURE("Could not identify account corresponding to the OAuth request");
        }

        boolean acctOnLocalServer;
        try {
            acctOnLocalServer = Provisioning.onLocalServer(account);
        } catch (ServiceException e) {
            ZmailLog.extensions.warn("Error in checking whether account on local server or not", e);
            throw AuthProviderException.FAILURE(e.getMessage());
        }

        if (acctOnLocalServer) {
            OAuthAccessor accessor = getAccessTokenFromMbox(oAuthMessage, account);
            if (accessor == null)
                throw new AuthTokenException("invalid OAuth token");
            try {
                SampleZmOAuthProvider.VALIDATOR.validateMessage(oAuthMessage, accessor);
            } catch (Exception e) {
                ZmailLog.extensions.debug("Exception in validating OAuth token", e);
                throw new AuthTokenException("Exception in validating OAuth token", e);
            }
            return AuthProvider.getAuthToken(account);
        } else {
            throw AuthProviderException.FAILURE("Account not on this server");
        }
    }

    private static OAuthAccessor getAccessTokenFromMbox(OAuthMessage oAuthMessage, Account account)
            throws AuthProviderException, AuthTokenException {
        try {
            Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
            Metadata oAuthConfig = mbox.getConfig(null, "zwc:oauth");
            if (oAuthConfig != null) {
                Metadata authzedConsumers = oAuthConfig.getMap("authorized_consumers", true);
                if (authzedConsumers != null) {
                    String consumerKey;
                    try {
                        consumerKey = oAuthMessage.getConsumerKey();
                    } catch (IOException e) {
                        ZmailLog.extensions.debug("Error in getting consumer key from OAuth message", e);
                        throw new AuthTokenException("Error in getting consumer key from OAuth message", e);
                    }
                    String serializedAccessor = authzedConsumers.get(consumerKey, null);
                    if (serializedAccessor != null) {
                        return new OAuthAccessorSerializer().deserialize(serializedAccessor);
                    }
                }
            }
        } catch (ServiceException e) {
            ZmailLog.extensions.warn("Error in reading mailbox metadata", e);
            throw AuthProviderException.FAILURE(e.getMessage());
        }
        return null;
    }

    protected AuthToken authToken(Element soapCtxt, Map engineCtxt) throws AuthProviderException, AuthTokenException  {
    	HttpServletRequest hsr = (HttpServletRequest)engineCtxt.get(SoapServlet.SERVLET_REQUEST);
    	
    	//String encodedAuthToken = (soapCtxt == null ? null : soapCtxt.getAttribute(HeaderConstants.E_AUTH_TOKEN, null));
        
        // check for auth token in engine context if not in header  
        //if (encodedAuthToken == null)
        //    encodedAuthToken = (String) engineCtxt.get(SoapServlet.ZIMBRA_AUTH_TOKEN);
        
        //return genAuthToken(encodedAuthToken);
    	return authToken(hsr,false);
    }
    
    protected AuthToken authToken(String encoded) throws AuthProviderException, AuthTokenException {
        return genAuthToken(encoded);
    }
    
    private AuthToken genAuthToken(String encodedAuthToken) throws AuthProviderException, AuthTokenException {
        if (StringUtil.isNullOrEmpty(encodedAuthToken))
            throw AuthProviderException.NO_AUTH_DATA();
        
        return ZmailAuthToken.getAuthToken(encodedAuthToken);
    }
    
    //protected AuthToken authToken(Account acct) {
    //    return new ZmailAuthToken(acct);
    //}
    
    //protected AuthToken authToken(Account acct, boolean isAdmin) {
    //    return new ZmailAuthToken(acct, isAdmin);
    //}
    
    //protected AuthToken authToken(Account acct, long expires) {
    //    return new ZmailAuthToken(acct, expires);
    //}
    
    //protected AuthToken authToken(Account acct, long expires, boolean isAdmin, Account adminAcct) {
    //    return new ZmailAuthToken(acct, expires, isAdmin, adminAcct);
    //}
    
}
