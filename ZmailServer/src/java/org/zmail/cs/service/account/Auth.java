/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Created on May 26, 2004
 */
package org.zmail.cs.service.account;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning.AutoProvAuthMech;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailCookie;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException.AuthFailedServiceException;
import org.zmail.cs.account.AttributeFlag;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.krb5.Krb5Principal;
import org.zmail.cs.account.names.NameUtil.EmailAddress;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.auth.AuthContext;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.session.Session;
import org.zmail.cs.util.AccountUtil;
import org.zmail.cs.util.SkinUtil;
import org.zmail.soap.SoapEngine;
import org.zmail.soap.SoapServlet;
import org.zmail.soap.ZmailSoapContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author schemers
 */
public class Auth extends AccountDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        // Look up the specified account.  It is optional in the <authToken> case.
        String acctValuePassedIn = null, acctValue = null, acctByStr = null;
        AccountBy acctBy = null;
        Account acct = null;
        Element acctEl = request.getOptionalElement(AccountConstants.E_ACCOUNT);
        if (acctEl != null) {
            acctValuePassedIn = acctEl.getText();
            acctValue = acctValuePassedIn;
            acctByStr = acctEl.getAttribute(AccountConstants.A_BY, AccountBy.name.name());
            acctBy = AccountBy.fromString(acctByStr);
            if (acctBy == AccountBy.name) {
                Element virtualHostEl = request.getOptionalElement(AccountConstants.E_VIRTUAL_HOST);
                String virtualHost = virtualHostEl == null ? null : virtualHostEl.getText().toLowerCase();
                if (virtualHost != null && acctValue.indexOf('@') == -1) {
                    Domain d = prov.get(Key.DomainBy.virtualHostname, virtualHost);
                    if (d != null)
                        acctValue = acctValue + "@" + d.getName();
                }
            }
            acct = prov.get(acctBy, acctValue);
        }

        Element authTokenEl = request.getOptionalElement(AccountConstants.E_AUTH_TOKEN);
        if (authTokenEl != null) {
            boolean verifyAccount = authTokenEl.getAttributeBool(AccountConstants.A_VERIFY_ACCOUNT, false);
            if (verifyAccount && acctEl == null) {
                throw ServiceException.INVALID_REQUEST("missing required element: " + AccountConstants.E_ACCOUNT, null);
            }
            try {
                AuthToken at = AuthProvider.getAuthToken(authTokenEl, acct);
                
                addAccountToLogContextByAuthToken(prov, at);
                
                // this could've been done in the very beginning of the method,
                // we do it here instead - after the account is added to log context 
                // so the account will show in log context
                if (!checkPasswordSecurity(context))
                    throw ServiceException.INVALID_REQUEST("clear text password is not allowed", null);
                
                Account authTokenAcct = AuthProvider.validateAuthToken(prov, at, false);
                if (verifyAccount) {
                    // Verify the named account matches the account in the auth token.  Client can easily decode
                    // the auth token and do this check, but doing it on the server is nice because then the client
                    // can treat the auth token as an opaque string.
                    if (acct == null || !acct.getId().equalsIgnoreCase(authTokenAcct.getId())) {
                        throw new AuthTokenException("auth token doesn't match the named account");
                    }
                }

                return doResponse(request, at, zsc, context, authTokenAcct);
            } catch (AuthTokenException e) {
                throw ServiceException.AUTH_REQUIRED();
            }
        } else {
            if (!checkPasswordSecurity(context)) {
                throw ServiceException.INVALID_REQUEST("clear text password is not allowed", null);
            }

            Element preAuthEl = request.getOptionalElement(AccountConstants.E_PREAUTH);
            String password = request.getAttribute(AccountConstants.E_PASSWORD, null);

            long expires = 0;
            
            Map<String, Object> authCtxt = new HashMap<String, Object>();
            authCtxt.put(AuthContext.AC_ORIGINATING_CLIENT_IP, context.get(SoapEngine.ORIG_REQUEST_IP));
            authCtxt.put(AuthContext.AC_REMOTE_IP, context.get(SoapEngine.SOAP_REQUEST_IP));
            authCtxt.put(AuthContext.AC_ACCOUNT_NAME_PASSEDIN, acctValuePassedIn);
            authCtxt.put(AuthContext.AC_USER_AGENT, zsc.getUserAgent());
            
            boolean acctAutoProvisioned = false;
            if (acct == null) {
                //
                // try auto provision the account
                //
                if (acctBy == AccountBy.name || acctBy == AccountBy.krb5Principal) {
                    try {
                        if (acctBy == AccountBy.name) {
                            EmailAddress email = new EmailAddress(acctValue, false);
                            String domainName = email.getDomain();
                            Domain domain = domainName == null ? null : prov.get(Key.DomainBy.name, domainName);
                            if (password != null) {
                                acct = prov.autoProvAccountLazy(domain, acctValuePassedIn, password, null);
                            } else if (preAuthEl != null) {
                                long timestamp = preAuthEl.getAttributeLong(AccountConstants.A_TIMESTAMP);
                                expires = preAuthEl.getAttributeLong(AccountConstants.A_EXPIRES, 0);
                                String preAuth = preAuthEl.getTextTrim();
                                prov.preAuthAccount(domain, acctValue, acctByStr, timestamp, expires, preAuth, authCtxt);
                                
                                acct = prov.autoProvAccountLazy(domain, acctValuePassedIn, null, AutoProvAuthMech.PREAUTH);
                            }
                        } else if (acctBy == AccountBy.krb5Principal) {
                            if (password != null) {
                                Domain domain = Krb5Principal.getDomainByKrb5Principal(acctValuePassedIn);
                                if (domain != null) {
                                    acct = prov.autoProvAccountLazy(domain, acctValuePassedIn, password, null);
                                }
                            }
                        }
                        
                        if (acct != null) {
                            acctAutoProvisioned = true;
                        }
                    } catch (AuthFailedServiceException e) {
                        ZmailLog.account.debug("auth failed, unable to auto provisioing acct " + acctValue, e);
                    } catch (ServiceException e) {
                        ZmailLog.account.info("unable to auto provisioing acct " + acctValue, e);
                    }
                }
            }
            
            if (acct == null) {
                throw AuthFailedServiceException.AUTH_FAILED(acctValue, acctValuePassedIn, "account not found");
            }
            
            AccountUtil.addAccountToLogContext(prov, acct.getId(), ZmailLog.C_NAME, ZmailLog.C_ID, null);

            // if account was auto provisioned, we had already authenticated the principal 
            if (!acctAutoProvisioned) {
                if (password != null) {
                    prov.authAccount(acct, password, AuthContext.Protocol.soap, authCtxt);
                } else if (preAuthEl != null) {
                    long timestamp = preAuthEl.getAttributeLong(AccountConstants.A_TIMESTAMP);
                    expires = preAuthEl.getAttributeLong(AccountConstants.A_EXPIRES, 0);
                    String preAuth = preAuthEl.getTextTrim();
                    prov.preAuthAccount(acct, acctValue, acctByStr, timestamp, expires, preAuth, authCtxt);
                } else {
                    throw ServiceException.INVALID_REQUEST("must specify "+AccountConstants.E_PASSWORD, null);
                }
            }
            
            AuthToken at = expires ==  0 ? AuthProvider.getAuthToken(acct) : AuthProvider.getAuthToken(acct, expires);
            return doResponse(request, at, zsc, context, acct);
        }
    }

    private Element doResponse(Element request, AuthToken at, ZmailSoapContext zsc, 
            Map<String, Object> context, Account acct)
    throws ServiceException {
        Element response = zsc.createElement(AccountConstants.AUTH_RESPONSE);
        at.encodeAuthResp(response, false);
        
        /* 
         * bug 67078
         * also return auth token cookie in http header
         */
        HttpServletRequest httpReq = (HttpServletRequest)context.get(SoapServlet.SERVLET_REQUEST);
        HttpServletResponse httpResp = (HttpServletResponse)context.get(SoapServlet.SERVLET_RESPONSE);
        boolean rememberMe = request.getAttributeBool(AccountConstants.A_PERSIST_AUTH_TOKEN_COOKIE, false);
        at.encode(httpResp, false, ZmailCookie.secureCookie(httpReq), rememberMe);
        
        response.addAttribute(AccountConstants.E_LIFETIME, at.getExpires() - System.currentTimeMillis(), Element.Disposition.CONTENT);
        boolean isCorrectHost = Provisioning.onLocalServer(acct);
        if (isCorrectHost) {
            Session session = updateAuthenticatedAccount(zsc, at, context, true);
            if (session != null)
                ZmailSoapContext.encodeSession(response, session.getSessionId(), session.getSessionType());
        }
        
        Server localhost = Provisioning.getInstance().getLocalServer();
        String referMode = localhost.getAttr(Provisioning.A_zmailMailReferMode, "wronghost");
        // if (!isCorrectHost || LC.zmail_auth_always_send_refer.booleanValue()) {
        if (Provisioning.MAIL_REFER_MODE_ALWAYS.equals(referMode) ||
            (Provisioning.MAIL_REFER_MODE_WRONGHOST.equals(referMode) && !isCorrectHost)) {
            response.addAttribute(AccountConstants.E_REFERRAL, acct.getAttr(Provisioning.A_zmailMailHost), Element.Disposition.CONTENT);
        }

		Element prefsRequest = request.getOptionalElement(AccountConstants.E_PREFS);
		if (prefsRequest != null) {
			Element prefsResponse = response.addUniqueElement(AccountConstants.E_PREFS);
			GetPrefs.handle(prefsRequest, prefsResponse, acct);
		}

        Element attrsRequest = request.getOptionalElement(AccountConstants.E_ATTRS);
        if (attrsRequest != null) {
            Element attrsResponse = response.addUniqueElement(AccountConstants.E_ATTRS);
            Set<String> attrList = AttributeManager.getInstance().getAttrsWithFlag(AttributeFlag.accountInfo);
            for (Iterator it = attrsRequest.elementIterator(AccountConstants.E_ATTR); it.hasNext(); ) {
                Element e = (Element) it.next();
                String name = e.getAttribute(AccountConstants.A_NAME);
                if (name != null && attrList.contains(name)) {
                    Object v = acct.getUnicodeMultiAttr(name);
                    if (v != null) {
                        ToXML.encodeAttr(attrsResponse, name, v);
                    }
                }
            }
        }

		Element requestedSkinEl = request.getOptionalElement(AccountConstants.E_REQUESTED_SKIN);
		String requestedSkin = requestedSkinEl != null ? requestedSkinEl.getText() : null;  
		String skin = SkinUtil.chooseSkin(acct, requestedSkin);
		ZmailLog.webclient.debug("chooseSkin() returned "+skin );
		if (skin != null) {
			response.addElement(AccountConstants.E_SKIN).setText(skin);
		}

		return response;
    }

    public boolean needsAuth(Map<String, Object> context) {
		return false;
	}
    
    // for auth by auth token
    public static void addAccountToLogContextByAuthToken(Provisioning prov, AuthToken at) {
        String id = at.getAccountId();
        if (id != null)
            AccountUtil.addAccountToLogContext(prov, id, ZmailLog.C_NAME, ZmailLog.C_ID, null);
        String aid = at.getAdminAccountId();
        if (aid != null && !aid.equals(id))
            AccountUtil.addAccountToLogContext(prov, aid, ZmailLog.C_ANAME, ZmailLog.C_AID, null);
    }
}
