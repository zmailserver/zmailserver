/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.offline.util.yab;

import org.zmail.cs.util.yauth.Auth;
import org.zmail.cs.util.yauth.Authenticator;
import org.zmail.cs.offline.OfflineLog;
import org.zmail.common.util.Log;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * Yahoo Address book access.
 */
public class Yab {
    public static final String DTD = "http://l.yimg.com/us.yimg.com/lib/pim/r/abook/xml/2/pheasant.dtd";

    public static final Log LOG = OfflineLog.yab;
    
    public static final String BASE_URI = "http://address.yahooapis.com/v1";

    public static final String XML = "xml";
    public static final String JSON = "json";
    
    public static Session createSession(Authenticator auth) {
        return new Session(auth);
    }

    public static boolean isDebug() {
        return LOG.isDebugEnabled();
    }

    public static void debug(String fmt, Object... args) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format(fmt, args));
        }
    }
}
