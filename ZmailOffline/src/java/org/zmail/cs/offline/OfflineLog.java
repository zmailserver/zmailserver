/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.offline;

import org.zmail.common.util.LogFactory;

public class OfflineLog {
    /** The "zmail.offline" logger. For offline sync logs. */
    public static final org.zmail.common.util.Log offline = LogFactory.getLog("zmail.offline");

    /** The "zmail.offline.request" logger. For recording SOAP traffic
     *  sent to the remote server. */
    public static final org.zmail.common.util.Log request = LogFactory.getLog("zmail.offline.request");

    /** The "zmail.offline.response" logger. For recording SOAP traffic
     *  received from the remote server. */
    public static final org.zmail.common.util.Log response = LogFactory.getLog("zmail.offline.response");

    /** The "zmail.offline.yab" logger. For recording Yahoo Address Book sync events */
    public static final org.zmail.common.util.Log yab = LogFactory.getLog("zmail.offline.yab");

    /** The "zmail.offline.gab" logger. For recording Google Address Book sync events */
    public static final org.zmail.common.util.Log gab = LogFactory.getLog("zmail.offline.gab");

    /** For logging YMail cascade events*/
    public static final org.zmail.common.util.Log ymail = LogFactory.getLog("zmail.offline.ymail");
}
