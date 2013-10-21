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
package org.zmail.examples.extns.samlprovider;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.extension.ExtensionDispatcherServlet;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.cs.service.AuthProvider;

/**
 * This extension registers a custom SAML auth provider.
 * 
 * @author vmahajan
 */
public class SamlAuthProviderExtension implements ZmailExtension {

    /**
     * Defines a name for the extension. It must be an identifier.
     *
     * @return extension name
     */
    public String getName() {
        return "samlProviderExtn";
    }

    /**
     * Initializes the extension. Called when the extension is loaded.
     *
     * @throws org.zmail.common.service.ServiceException
     *
     */
    public void init() throws ServiceException {
        ExtensionDispatcherServlet.register(this, new SamlRequestHandler());

        AuthProvider.register(new SamlAuthProvider());
        AuthProvider.refresh();
    }

    /**
     * Terminates the extension. Called when the server is shut down.
     */
    public void destroy() {
        ExtensionDispatcherServlet.unregister(this);
    }
}
