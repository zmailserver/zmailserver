/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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

package sample.oauth.provider;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.extension.ExtensionDispatcherServlet;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.cs.service.AuthProvider;
import org.zmail.soap.SoapServlet;
import sample.oauth.provider.soap.OAuthProviderService;

public class OAuthProvExt implements ZmailExtension {

	static {
    }
    
    public void init() throws ServiceException {    
    	ExtensionDispatcherServlet.register(this, new RequestTokenHandler());
    	ExtensionDispatcherServlet.register(this, new AuthorizationHandler());
    	ExtensionDispatcherServlet.register(this, new AccessTokenHandler());
    	
    	AuthProvider.register(new ZmailAuthProviderForOAuth());
    	AuthProvider.refresh();

        SoapServlet.addService("SoapServlet", new OAuthProviderService());
    }

    public void destroy() {
    	ExtensionDispatcherServlet.unregister(this);
    }
    
    public String getName() {
    	return "oauth";
    }

}
