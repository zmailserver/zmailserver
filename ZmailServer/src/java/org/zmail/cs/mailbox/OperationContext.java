/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.mailbox;

import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.AuthToken;
import com.zimbra.cs.account.GuestAccount;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.cs.redolog.op.RedoableOp;
import com.zimbra.cs.service.AuthProvider;
import com.zimbra.cs.session.Session;
import com.zimbra.cs.util.AccountUtil;

public class OperationContext {
    public static final boolean CHECK_CREATED = false, CHECK_MODIFIED = true;

    private Account    authuser;
    private boolean    isAdmin;
    private Session    session;
    private RedoableOp player;
    private String     requestIP;
    private String     userAgent;
    private AuthToken  authToken;
    private Map<String, OperationContextData> contextData;
    
    boolean changetype = CHECK_CREATED;
    int     change = -1;

    public OperationContext(RedoableOp redoPlayer) {
        player = redoPlayer;
    }

    public OperationContext(Account acct) {
        this(acct, false);
    }

    public OperationContext(Mailbox mbox) throws ServiceException {
        this(mbox.getAccount());
    }

    public OperationContext(Account acct, boolean admin) {
        authuser = acct;  isAdmin = admin;
    }

    public OperationContext(String accountId) throws ServiceException {
        authuser = Provisioning.getInstance().get(AccountBy.id, accountId);
        if (authuser == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(accountId);
    }

    public OperationContext(AuthToken auth) throws ServiceException {
        authToken = auth;
        String accountId = auth.getAccountId();
        isAdmin = AuthToken.isAnyAdmin(auth);
        authuser = Provisioning.getInstance().get(AccountBy.id, accountId, authToken);
        if (authuser == null || !auth.isZimbraUser()) {
            if (auth.getDigest() != null || auth.getAccessKey() != null) {
                authuser = new GuestAccount(auth);
            } else {
                authuser = GuestAccount.ANONYMOUS_ACCT;
            }
        }
        if (authuser == null) {
            throw AccountServiceException.NO_SUCH_ACCOUNT(accountId);
        }
    }
    public OperationContext(OperationContext octxt) {
        player     = octxt.player;      session = octxt.session;
        authuser   = octxt.authuser;    isAdmin = octxt.isAdmin;
        changetype = octxt.changetype;  change  = octxt.change;
        authToken  = octxt.authToken;
    }

    public OperationContext setChangeConstraint(boolean checkModified, int changeId) {
        changetype = checkModified;  change = changeId;  return this;
    }

    public OperationContext unsetChangeConstraint() {
        changetype = CHECK_CREATED;  change = -1;  return this;
    }

    public OperationContext setSession(Session s) {
        session = s;  return this;
    }

    Session getSession() {
        return session;
    }

    public RedoableOp getPlayer() {
        return player;
    }

    public long getTimestamp() {
        return (player == null ? System.currentTimeMillis() : player.getTimestamp());
    }

    int getChangeId() {
        return (player == null ? -1 : player.getChangeId());
    }

    public boolean isRedo() {
        return player != null;
    }

    public boolean needRedo() {
        return player == null || !player.getUnloggedReplay();
    }

    public Account getAuthenticatedUser() {
        return authuser;
    }
    
    public AuthToken getAuthToken() throws ServiceException {
        return getAuthToken(true);
    }
    
    public AuthToken getAuthToken(boolean constructIfNotPresent) throws ServiceException {
        if (authToken != null) {
            return authToken;
        } else if (constructIfNotPresent) {
            if (getAuthenticatedUser() != null) {
                return AuthProvider.getAuthToken(getAuthenticatedUser(), isUsingAdminPrivileges());
            }
        }
        return null;
    }
    
    public boolean isUsingAdminPrivileges() {
        return isAdmin;
    }

    public boolean isDelegatedRequest(Mailbox mbox) {
        return authuser != null && !authuser.getId().equalsIgnoreCase(mbox.getAccountId());
    }

    /**
     * @see com.zimbra.cs.service.mail.CalendarRequest#isOnBehalfOfRequest(com.zimbra.soap.ZimbraSoapContext)
     */
    public boolean isOnBehalfOfRequest(Mailbox mbox) {
        if (!isDelegatedRequest(mbox)) {
            return false;
        }
        return authuser != null && !AccountUtil.isZDesktopLocalAccount(authuser.getId());
    }
    
    public OperationContext setRequestIP(String addr) {
        requestIP = addr;  return this;
    }
    
    public String getRequestIP() {
        return requestIP;
    }
    
    public OperationContext setUserAgent(String ua) {
        userAgent = ua;  return this;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setCtxtData(String key, OperationContextData data) {
        if (contextData == null) {
            contextData = new HashMap<String, OperationContextData>();
        }
        contextData.put(key, data);
    }
    
    public OperationContextData getCtxtData(String key) {
        if (contextData == null) {
            return null;
        } else {
            return contextData.get(key);
        }
    }
}