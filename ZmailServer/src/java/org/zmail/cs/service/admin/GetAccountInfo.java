/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.httpclient.URLUtil;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class GetAccountInfo extends AdminDocumentHandler  {


    /**
     * must be careful and only return accounts a domain admin can see
     */
    @Override
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    /* (non-Javadoc)
     * @see org.zmail.soap.DocumentHandler#handle(org.dom4j.Element, java.util.Map)
     */
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
   
        Element a = request.getElement(AdminConstants.E_ACCOUNT);
        String key = a.getAttribute(AdminConstants.A_BY);
        String value = a.getText();

        Provisioning prov = Provisioning.getInstance();
        Account account = prov.get(AccountBy.fromString(key), value, zsc.getAuthToken());

        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT(value);
       
        if (account.isCalendarResource()) {
            // need a CalendarResource instance for RightChecker
            CalendarResource resource = prov.get(Key.CalendarResourceBy.id, account.getId());
            checkCalendarResourceRight(zsc, resource, Admin.R_getCalendarResourceInfo);
        } else
            checkAccountRight(zsc, account, Admin.R_getAccountInfo);
        

        Element response = zsc.createElement(AdminConstants.GET_ACCOUNT_INFO_RESPONSE);
        response.addElement(AdminConstants.E_NAME).setText(account.getName());
        addAttr(response, Provisioning.A_zmailId, account.getId());
        addAttr(response, Provisioning.A_zmailMailHost, account.getAttr(Provisioning.A_zmailMailHost));
 
        doCos(account, response);
        addUrls(response, account);
        
        return response;
    }

    static void addUrls(Element response, Account account) throws ServiceException {

        Server server = Provisioning.getInstance().getServer(account);
        if (server == null) return;
        String hostname = server.getAttr(Provisioning.A_zmailServiceHostname);        
        if (hostname == null) return;
        
        String http = URLUtil.getSoapURL(server, false);
        String https = URLUtil.getSoapURL(server, true);

        if (http != null)
            response.addElement(AdminConstants.E_SOAP_URL).setText(http);
        
        if (https != null && !https.equalsIgnoreCase(http))
            response.addElement(AdminConstants.E_SOAP_URL).setText(https);
        
        String adminUrl = URLUtil.getAdminURL(server);
        if (adminUrl != null)
            response.addElement(AdminConstants.E_ADMIN_SOAP_URL).setText(adminUrl);
        
        String webMailUrl = URLUtil.getPublicURLForDomain(server, Provisioning.getInstance().getDomain(account), "", true);
        if (webMailUrl != null)
            response.addElement(AdminConstants.E_PUBLIC_MAIL_URL).setText(webMailUrl);
        
    }

    private static void addAttr(Element response, String name, String value) {
        if (value != null && !value.equals("")) {
            Element e = response.addElement(AdminConstants.E_A);
            e.addAttribute(AdminConstants.A_N, name);
            e.setText(value);
        }
    }
    
    static void doCos(Account acct, Element response) throws ServiceException {
        Cos cos = Provisioning.getInstance().getCOS(acct);
        if (cos != null) {
            Element eCos = response.addUniqueElement(AdminConstants.E_COS);
            eCos.addAttribute(AdminConstants.A_ID, cos.getId());
            eCos.addAttribute(AdminConstants.A_NAME, cos.getName());
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getAccountInfo);
        relatedRights.add(Admin.R_getCalendarResourceInfo);
    }
}
