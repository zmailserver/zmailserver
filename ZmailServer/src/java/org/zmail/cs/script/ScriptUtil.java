/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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

package org.zmail.cs.script;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.SoapTransport;
import org.zmail.common.util.CliUtil;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.soap.SoapProvisioning;
import org.zmail.cs.util.BuildInfo;

public class ScriptUtil {

    /**
     * Initializes provisioning with the default admin settings.
     */
    public static void initProvisioning()
    throws ServiceException {
        initProvisioning(null);
    }
    
    /**
     * Initializes the default {@link Provisioning} configuration,
     * based on values that were passed to the methods in this class.
     * By default, connects to <tt>https://localhost:7071/service/admin/soap</tt>
     * with <tt>zmail_ldap_user</tt> and <tt>zmail_ldap_password</tt>.
     * 
     * @param options provisioning options, or <tt>null</tt> for default options
     */
    public static void initProvisioning(ProvisioningOptions options)
    throws ServiceException {
        if (options == null) {
            options = new ProvisioningOptions();
        }
        CliUtil.toolSetup();
        SoapProvisioning sp = new SoapProvisioning();

        String userAgent = options.getUserAgent();
        String userAgentVersion = options.getUserAgentVersion();
        if (userAgent == null) {
            userAgent = "Zmail Scripting";
            userAgentVersion = BuildInfo.VERSION;
        }
        SoapTransport.setDefaultUserAgent(userAgent, userAgentVersion);
        
        String uri = options.getSoapURI();
        if (uri == null) {
            uri = LC.zmail_admin_service_scheme.value() + "localhost:7071" + AdminConstants.ADMIN_SERVICE_URI;
        }
        sp.soapSetURI(uri);
        
        String user = options.getUsername();
        if (user == null) {
            user = LC.zmail_ldap_user.value();
        }
        
        String password = options.getPassword();
        if (password == null) {
            password = LC.zmail_ldap_password.value();
        }
        sp.soapAdminAuthenticate(user, password);
        
        Provisioning.setInstance(sp);
    }
}
