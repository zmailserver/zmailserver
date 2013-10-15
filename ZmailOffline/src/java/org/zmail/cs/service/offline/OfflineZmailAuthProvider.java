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

/*
 * Created on Aug 20, 2010
 */
package org.zmail.cs.service.offline;

import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.service.AuthProviderException;
import org.zmail.cs.service.ZmailAuthProvider;

public class OfflineZmailAuthProvider extends ZmailAuthProvider {
    
    public static final String PROVIDER_NAME = "offline";

    public OfflineZmailAuthProvider() {
        super(PROVIDER_NAME);
    }
    
    protected AuthToken genAuthToken(String encodedAuthToken) throws AuthProviderException, AuthTokenException {
        if (StringUtil.isNullOrEmpty(encodedAuthToken))
            throw AuthProviderException.NO_AUTH_DATA();
        AuthToken at = ZmailAuthToken.getAuthToken(encodedAuthToken);
        if (at instanceof ZmailAuthToken) {
           try {
               return (AuthToken)((ZmailAuthToken)at).clone();
           } catch (CloneNotSupportedException e) {
               ZmailLog.system.error("Unable to clone zmail auth token",e);
               return at;
           }
        } else {
            return at;
        }
    }
}
