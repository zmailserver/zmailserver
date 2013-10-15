/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
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

// Original is from example in OAuth Java library(http://oauth.googlecode.com/svn/code/java/)
// and modified for integratin with Zmail

// Original's copyright and license terms
/*
 * Copyright 2007 AOL, LLC.
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

package sample.oauth.provider;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.extension.ExtensionHttpHandler;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Metadata;
import org.zmail.cs.servlet.ZmailServlet;
import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.server.OAuthServlet;
import sample.oauth.provider.core.SampleZmOAuthProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Access Token request handler for zmail extension
 *
 * @author Yutaka Obuchi
 */
public class AccessTokenHandler extends ExtensionHttpHandler {
    
    
    public void init(ZmailExtension ext) throws ServiceException{
        super.init(ext);    
    }
    
    public String getPath() {
        return super.getPath() + "/access_token";
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	ZmailLog.extensions.debug("Access Token Handler doGet requested!");
        processRequest(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	ZmailLog.extensions.debug("Access Token Handler doPost requested!");
        processRequest(request, response);
    }
        
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try{
            String origUrl = request.getHeader("X-Zmail-Orig-Url");
            OAuthMessage oAuthMessage =
                    StringUtil.isNullOrEmpty(origUrl) ?
                            OAuthServlet.getMessage(request, null) : OAuthServlet.getMessage(request, origUrl);
            
            OAuthAccessor accessor = SampleZmOAuthProvider.getAccessor(oAuthMessage);
            SampleZmOAuthProvider.VALIDATOR.validateAccTokenMessage(oAuthMessage, accessor);
            
            // make sure token is authorized
            if (!Boolean.TRUE.equals(accessor.getProperty("authorized"))) {
                 OAuthProblemException problem = new OAuthProblemException("permission_denied");
                 ZmailLog.extensions.debug("permission_denied");
                 throw problem;
            }

            AuthToken userAuthToken = ZmailAuthToken.getAuthToken((String) accessor.getProperty("ZM_AUTH_TOKEN"));
            String accountId = userAuthToken.getAccountId();
            Account account = Provisioning.getInstance().getAccountById(accountId);
            if (Provisioning.onLocalServer(account)) {
                // generate access token and secret
                SampleZmOAuthProvider.generateAccessToken(accessor);

                persistAccessTokenInMbox(accessor, accountId);

                response.setContentType("text/plain");
                OutputStream out = response.getOutputStream();
                OAuth.formEncode(OAuth.newList("oauth_token", accessor.accessToken,
                                               "oauth_token_secret", accessor.tokenSecret),
                                 out);
                out.close();
            } else {
                ZmailServlet.proxyServletRequest(request, response, accountId);
            }
        } catch (Exception e){
            ZmailLog.extensions.debug("AccessTokenHandler exception", e);            
            SampleZmOAuthProvider.handleException(e, request, response, true);
        }
    }

    private static void persistAccessTokenInMbox(OAuthAccessor accessor, String accountId)
            throws AuthTokenException, ServiceException {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(accountId);
        Metadata oAuthConfig = mbox.getConfig(null, "zwc:oauth");
        if (oAuthConfig == null)
            oAuthConfig = new Metadata();
        Metadata authzedConsumers = oAuthConfig.getMap("authorized_consumers", true);
        if (authzedConsumers == null)
            authzedConsumers = new Metadata();
        authzedConsumers.put(accessor.consumer.consumerKey, new OAuthAccessorSerializer().serialize(accessor));
        oAuthConfig.put("authorized_consumers", authzedConsumers);
        mbox.setConfig(null, "zwc:oauth", oAuthConfig);
    }
}
