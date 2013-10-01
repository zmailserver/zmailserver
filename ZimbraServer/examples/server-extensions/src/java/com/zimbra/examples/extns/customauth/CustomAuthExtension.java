/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.examples.extns.customauth;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.auth.ZimbraCustomAuth;
import com.zimbra.cs.extension.ZimbraExtension;

/**
 * This extension registers a custom authentication mechanism.
 *
 * @author vmahajan
 */
public class CustomAuthExtension implements ZimbraExtension {
    
    /**
     * Defines a name for the extension. It must be an identifier.
     *
     * @return extension name
     */
    public String getName() {
        return "customAuthExtn";
    }

    /**
     * Initializes the extension. Called when the extension is loaded.
     *
     * @throws com.zimbra.common.service.ServiceException
     *
     */
    public void init() throws ServiceException {
        ZimbraCustomAuth.register("simple", new SimpleAuth());
    }

    /**
     * Terminates the extension. Called when the server is shut down.
     */
    public void destroy() {
    }
}
