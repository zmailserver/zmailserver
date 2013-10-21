/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Created on Jun 17, 2004
 */
package org.zmail.cs.service.admin;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.AccessManager.AttrRightChecker;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author schemers
 */
public class GetDomain extends AdminDocumentHandler {

    /**
     * must be careful and only return accounts a domain admin can see
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        boolean applyConfig = request.getAttributeBool(AdminConstants.A_APPLY_CONFIG, true);
        Set<String> reqAttrs = getReqAttrs(request, AttributeClass.domain);
        
        Element d = request.getElement(AdminConstants.E_DOMAIN);
        String key = d.getAttribute(AdminConstants.A_BY);
        String value = d.getText();

        Domain domain = prov.get(Key.DomainBy.fromString(key), value);

        if (domain == null)
            throw AccountServiceException.NO_SUCH_DOMAIN(value);

        AdminAccessControl aac = checkDomainRight(zsc, domain, AdminRight.PR_ALWAYS_ALLOW);

        Element response = zsc.createElement(AdminConstants.GET_DOMAIN_RESPONSE);
        encodeDomain(response, domain, applyConfig, reqAttrs, aac.getAttrRightChecker(domain));

        return response;
    }

    public static void encodeDomain(Element e, Domain d) throws ServiceException {
        encodeDomain(e, d, true);
    }
    
    public static void encodeDomain(Element e, Domain d, boolean applyConfig) throws ServiceException {
        encodeDomain(e, d, applyConfig, null, null);
    }
    
    public static void encodeDomain(Element e, Domain d, boolean applyConfig, Set<String> reqAttrs, 
            AttrRightChecker attrRightChecker) throws ServiceException {
        Element domain = e.addElement(AdminConstants.E_DOMAIN);
        domain.addAttribute(AdminConstants.A_NAME,d.getUnicodeName());
        domain.addAttribute(AdminConstants.A_ID,d.getId());
        Map attrs = d.getUnicodeAttrs(applyConfig);
        
        ToXML.encodeAttrs(domain, attrs, reqAttrs, attrRightChecker);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getDomain);
        notes.add(String.format(AdminRightCheckPoint.Notes.GET_ENTRY, Admin.R_getDomain.getName()));
    }
}
