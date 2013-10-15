/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.versioncheck;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.qa.unittest.TestVersionCheck;
import org.zmail.qa.unittest.ZmailSuite;
import org.zmail.soap.SoapServlet;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.cs.service.versioncheck.VersionCheckService;

/**
 * @author Greg Solovyev
 */
public class VersionCheckExtension implements ZmailExtension {
    public static final String EXTENSION_NAME_VERSIONCHECK = "versioncheck";
    
    public void init() throws ServiceException {
        SoapServlet.addService("AdminServlet", new VersionCheckService());
        // XXX bburtin: Disabling test to avoid false positives until bug 54812 is fixed.
        try {
            ZmailSuite.addTest(TestVersionCheck.class);
        } catch (NoClassDefFoundError e) {
            // Expected in production, because JUnit is not available. 
            ZmailLog.test.debug("Unable to load ZmailAdminVersionCheck unit tests.", e);
        }
    }

    public void destroy() {

    }
    
    public String getName() {
        return EXTENSION_NAME_VERSIONCHECK;
    }

}
