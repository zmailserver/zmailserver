/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package org.zmail.cs.offline.util.yc;

import org.zmail.cs.offline.OfflineLog;
import org.zmail.cs.offline.util.yc.oauth.OAuthPutContactRequest;
import org.zmail.cs.offline.util.yc.oauth.OAuthRequest;
import org.zmail.cs.offline.util.yc.oauth.OAuthToken;

public class PutRequest extends Request {

    private String reqBody;

    public PutRequest(OAuthToken token, String reqBody) {
        super(token);
        this.reqBody = reqBody;
    }

    @Override
    public PutResponse send() throws YContactException {
        OAuthRequest req = new OAuthPutContactRequest(this.getToken(), this.reqBody);
        String resp = req.send();

        OfflineLog.yab.debug(resp);

        return new PutResponse(200, resp);
    }
}
