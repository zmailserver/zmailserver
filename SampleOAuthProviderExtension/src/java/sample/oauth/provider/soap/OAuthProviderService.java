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
package sample.oauth.provider.soap;

import com.zimbra.soap.DocumentDispatcher;
import com.zimbra.soap.DocumentService;

/**
 */
public class OAuthProviderService implements DocumentService {

    @Override
    public void registerHandlers(DocumentDispatcher dispatcher) {
        dispatcher.registerHandler(GetOAuthConsumers.GET_OAUTH_CONSUMERS_REQUEST, new GetOAuthConsumers());
    }
}
