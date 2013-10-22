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
package com.zimbra.examples.extns.samlprovider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;

import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.common.auth.ZAuthToken;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.SystemUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AuthToken;
import com.zimbra.cs.account.AuthTokenException;
import com.zimbra.cs.account.Provisioning;

/**
 * SAML auth token.
 *
 * @author vmahajan
 */
public class SamlAuthToken extends AuthToken {

    private String id;
    private String subjectNameId;
    private Date expires;

    /**
     * Constructs instance from a SAML assertion element.
     *
     * @param samlAssertionElt
     * @throws AuthTokenException
     */
    public SamlAuthToken(Element samlAssertionElt) throws AuthTokenException {
        Element authnStmtElt;
        try {
            id = samlAssertionElt.getAttribute("ID");
            Element subjectElt = samlAssertionElt.getElement("Subject");
            Element nameIdElt = subjectElt.getElement("NameID");
            subjectNameId = nameIdElt.getTextTrim();
            Element conditionsElt = samlAssertionElt.getElement("Conditions");
            String notOnOrAfter = conditionsElt.getAttribute("NotOnOrAfter");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            expires = dateFormat.parse(notOnOrAfter);
            authnStmtElt = samlAssertionElt.getElement("AuthnStatement");
        } catch (Exception e) {
            ZimbraLog.extensions.error(e);
            throw new AuthTokenException("Error in parsing SAML auth token", e);
        }
        if (authnStmtElt == null)
            throw new AuthTokenException("SAML auth token does not contain any authentication statement");
    }

    @Override
    public String toString() {
        return "SAML Auth Token(ID=" + id + ",NameID=" + subjectNameId + ")";
    }

    @Override
    public String getAccountId() {
        Provisioning prov = Provisioning.getInstance();
        Account acct;
        try {
            acct = prov.get(AccountBy.name, subjectNameId);
        } catch (ServiceException e) {
            ZimbraLog.extensions.error(SystemUtil.getStackTrace(e));
            return null;
        }
        if (acct != null)
            return acct.getId();
        return null;
    }

    @Override
    public String getAdminAccountId() {
        return null;
    }

    @Override
    public long getExpires() {
        return expires.getTime();
    }

    @Override
    public boolean isExpired() {
        return ! new Date().before(expires);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isDomainAdmin() {
        return false;
    }

    @Override
    public boolean isDelegatedAdmin() {
        return false;
    }

    @Override
    public boolean isZimbraUser() {
        return true;
    }

    @Override
    public String getExternalUserEmail() {
        return null;
    }

    @Override
    public String getDigest() {
        return null;
    }

    @Override
    public String getCrumb() throws AuthTokenException {
        return null;
    }

    /**
     * Encode original auth info into an outgoing http request.
     *
     * @param client http client
     * @param method http method
     * @param isAdminReq is admin request
     * @param cookieDomain cookie domain
     * @throws com.zimbra.common.service.ServiceException
     *
     */
    @Override
    public void encode(HttpClient client, HttpMethod method, boolean isAdminReq, String cookieDomain) throws ServiceException {
    }

    /**
     * Encode original auth info into an outgoing http request cookie.
     *
     * @param state http state
     * @param isAdminReq is admin request
     * @param cookieDomain cookie domain
     * @throws com.zimbra.common.service.ServiceException
     *
     */
    @Override
    public void encode(HttpState state, boolean isAdminReq, String cookieDomain) throws ServiceException {
    }

    /**
     * Encode original auth info into an HttpServletResponse.
     *
     * @param resp response message
     * @param isAdminReq is admin request
     * @param secureCookie secure cookie
     * @param remember is auth token persisted by client after logout
     * @throws com.zimbra.common.service.ServiceException
     */
    @Override
    public void encode(HttpServletResponse resp, boolean isAdminReq, boolean secureCookie, boolean remember) throws ServiceException {
    }

    @Override
    public void encodeAuthResp(Element parent, boolean isAdmin) throws ServiceException {
    }

    @Override
    public ZAuthToken toZAuthToken() throws ServiceException {
        Map<String,String> attrs = new HashMap<String, String>();
        attrs.put("ID", id);
        return new ZAuthToken("SAML_AUTH_PROVIDER", null, attrs);
    }

    @Override
    public String getEncoded() throws AuthTokenException {
        return null;
    }
}
