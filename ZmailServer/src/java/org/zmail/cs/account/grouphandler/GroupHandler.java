/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.account.grouphandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.ExternalGroup;
import org.zmail.cs.extension.ExtensionUtil;
import org.zmail.cs.gal.ZmailGalGroupHandler;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.ILdapContext;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.LdapServerConfig.ExternalLdapConfig;

public abstract class GroupHandler {

    public abstract boolean isGroup(IAttributes ldapAttrs);

    public abstract String[] getMembers(ILdapContext ldapContext, String searchBase,
            String entryDN, IAttributes ldapAttrs) throws ServiceException;

    public abstract boolean inDelegatedAdminGroup(ExternalGroup group,
            Account acct, boolean asAdmin) throws ServiceException;

    private static Map<String, HandlerInfo> sHandlers =
        new ConcurrentHashMap<String,HandlerInfo>();

    private static class HandlerInfo {
        Class<? extends GroupHandler> mClass;

        public GroupHandler getInstance() {
            GroupHandler handler;
            try {
                handler = mClass.newInstance();
            } catch (InstantiationException e) {
                handler = newDefaultHandler();
            } catch (IllegalAccessException e) {
                handler = newDefaultHandler();
            }
            return handler;
        }
    }

    private static GroupHandler newDefaultHandler() {
        return new ZmailGalGroupHandler();
    }

    private static HandlerInfo loadHandler(String className) {
        HandlerInfo handlerInfo = new HandlerInfo();

        try {
            handlerInfo.mClass = ExtensionUtil.findClass(className).asSubclass(GroupHandler.class);
        } catch (ClassNotFoundException e) {
            // miss configuration or the extension is disabled
            ZmailLog.gal.warn("GAL group handler %s not found, default to ZmailGalGroupHandler", className);
            // Fall back to ZmailGalGroupHandler
            handlerInfo.mClass = ZmailGalGroupHandler.class;
        }
        return handlerInfo;
    }

    public static GroupHandler getHandler(String className) {
        if (StringUtil.isNullOrEmpty(className)) {
            return newDefaultHandler();
        }

        HandlerInfo handlerInfo = sHandlers.get(className);

        if (handlerInfo == null) {
            handlerInfo = loadHandler(className);
            sHandlers.put(className, handlerInfo);
        }

        return handlerInfo.getInstance();
    }

    /*
     * callsite is responsible for closing the context after done.
     *
     * External group for delegated admin uses the external AD auth
     * settings.  The diff is, when looking for the account anywhere
     * other than authenticating the account, we have to use the
     * admin bindDN/password, because:
     *   - we no longer have the user's external LDAP password
     *   - it makes sense to do this task using the admin's credentials.
     */
    public ZLdapContext getExternalDelegatedAdminGroupsLdapContext(Domain domain, boolean asAdmin)
    throws ServiceException {
        String[] ldapUrl = domain.getAuthLdapURL();
        if (ldapUrl == null || ldapUrl.length == 0) {
            throw ServiceException.INVALID_REQUEST("ubable to search external group, " +
                    "missing " + Provisioning.A_zmailAuthLdapURL, null);
        }

        boolean startTLSEnabled = domain.isAuthLdapStartTlsEnabled();
        String bindDN = domain.getAuthLdapSearchBindDn();
        String bindPassword = domain.getAuthLdapSearchBindPassword();

        ExternalLdapConfig ldapConfig = new ExternalLdapConfig(ldapUrl, startTLSEnabled,
                null, bindDN, bindPassword, null,
                "search external group");

        return LdapClient.getExternalContext(ldapConfig, LdapUsage.EXTERNAL_GROUP);
    }

}
