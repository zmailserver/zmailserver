/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.Map;

import com.google.common.base.Strings;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.httpclient.URLUtil;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class GetAccountInfo extends AccountDocumentHandler  {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Element a = request.getElement(AccountConstants.E_ACCOUNT);
        String key = a.getAttribute(AccountConstants.A_BY);
        String value = a.getText();

        if (Strings.isNullOrEmpty(value)) {
            throw ServiceException.INVALID_REQUEST(
                "no text specified for the " + AccountConstants.E_ACCOUNT + " element", null);
        }
        Provisioning prov = Provisioning.getInstance();
        Account account = prov.get(AccountBy.fromString(key), value, zsc.getAuthToken());

        // prevent directory harvest attack, mask no such account as permission denied
        if (account == null)
            throw ServiceException.PERM_DENIED("can not access account");

        Element response = zsc.createElement(AccountConstants.GET_ACCOUNT_INFO_RESPONSE);
        response.addAttribute(AccountConstants.E_NAME, account.getName(), Element.Disposition.CONTENT);
        response.addKeyValuePair(Provisioning.A_zmailId, account.getId(), AccountConstants.E_ATTR, AccountConstants.A_NAME);
        response.addKeyValuePair(Provisioning.A_zmailMailHost, account.getAttr(Provisioning.A_zmailMailHost), AccountConstants.E_ATTR, AccountConstants.A_NAME);
        response.addKeyValuePair(Provisioning.A_displayName, account.getAttr(Provisioning.A_displayName), AccountConstants.E_ATTR, AccountConstants.A_NAME);
        addUrls(response, account);
        return response;
    }

    static void addUrls(Element response, Account account) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        
        Server server = prov.getServer(account);
        if (server == null) return;
        String hostname = server.getAttr(Provisioning.A_zmailServiceHostname);        
        if (hostname == null) return;
        
        Domain domain = prov.getDomain(account);

        String httpSoap = URLUtil.getSoapPublicURL(server, domain, false);
        String httpsSoap = URLUtil.getSoapPublicURL(server, domain, true);
        
        if (httpSoap != null)
            response.addAttribute(AccountConstants.E_SOAP_URL /* soapURL */, httpSoap, Element.Disposition.CONTENT);

        if (httpsSoap != null && !httpsSoap.equalsIgnoreCase(httpSoap))
            /* Note: addAttribute with Element.Disposition.CONTENT REPLACEs any previous attribute with the same name.
             * i.e. Will NOT end up with both httpSoap and httpsSoap as values for "soapURL"
             */
            response.addAttribute(AccountConstants.E_SOAP_URL /* soapURL */, httpsSoap, Element.Disposition.CONTENT);
        
        String pubUrl = URLUtil.getPublicURLForDomain(server, domain, "", true);
        if (pubUrl != null)
            response.addAttribute(AccountConstants.E_PUBLIC_URL, pubUrl, Element.Disposition.CONTENT);
        
        String changePasswordUrl = null;
        if (domain != null)
            changePasswordUrl = domain.getAttr(Provisioning.A_zmailChangePasswordURL);
        if (changePasswordUrl != null)
            response.addAttribute(AccountConstants.E_CHANGE_PASSWORD_URL, changePasswordUrl, Element.Disposition.CONTENT);
    }
}
