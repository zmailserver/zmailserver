/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.security.sasl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.security.sasl.SaslServer;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ZmailAuthToken;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.service.AuthProvider;

public class ZmailAuthenticator extends Authenticator {
    public static final String MECHANISM = "X-ZIMBRA";

    public ZmailAuthenticator(AuthenticatorUser user) {
        super(MECHANISM, user);
    }

    // X-ZIMBRA is supported in all protocols (IMAP, POP, etc.)
    @Override protected boolean isSupported()  { return true; }

    @Override public boolean initialize()  { return true; }
    @Override public void dispose()        { }

    @Override public boolean isEncryptionEnabled()  { return false; }

    @Override public InputStream unwrap(InputStream is)  { return null; }
    @Override public OutputStream wrap(OutputStream os)  { return null; }

    @Override public SaslServer getSaslServer()  { return null; }

    @Override public void handle(byte[] data) throws IOException {
        if (isComplete())
            throw new IllegalStateException("Authentication already completed");

        String message = new String(data, "utf-8");

        int nul1 = message.indexOf('\0'), nul2 = message.indexOf('\0', nul1 + 1);
        if (nul1 == -1 || nul2 == -1) {
            sendBadRequest();
            return;
        }
        String authorizeId = message.substring(0, nul1);
        String authenticateId = message.substring(nul1 + 1, nul2);
        String authtoken = message.substring(nul2 + 1);
        authenticate(authorizeId, authenticateId, authtoken);
    }

    @Override public Account authenticate(String username, String authenticateId, String authtoken,
                                          AuthContext.Protocol protocol, String origRemoteIp, String remoteIp, String userAgent)
    throws ServiceException {
        if (authenticateId == null || authenticateId.equals(""))
            return null;

        // validate the auth token
        Provisioning prov = Provisioning.getInstance();
        AuthToken at;
        try {
            at = ZmailAuthToken.getAuthToken(authtoken);
        } catch (AuthTokenException e) {
            return null;
        }

        try {
            AuthProvider.validateAuthToken(prov, at, false);
        } catch (ServiceException e) {
            return null;
        }

        // make sure that the authentication account is valid
        Account authAccount = prov.get(Key.AccountBy.name, authenticateId, at);
        if (authAccount == null)
            return null;

        // make sure the auth token belongs to authenticatedId
        if (!at.getAccountId().equalsIgnoreCase(authAccount.getId()))
            return null;

        // if necessary, check that the authenticated user can authorize as the target user
        Account targetAcct = authorize(authAccount, username, AuthToken.isAnyAdmin(at));
        if (targetAcct != null)
            prov.accountAuthed(authAccount);
        return targetAcct;
    }
}
