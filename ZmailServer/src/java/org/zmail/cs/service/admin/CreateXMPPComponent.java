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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.account.Key.ServerBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.XMPPComponent;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;


/**
 * 
 */
public class CreateXMPPComponent extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        // <CreateXMPPComponentRequest>
        //    <xmppComponent name="name">
        //       <domain [by="id, name, virtualHostname, krb5Realm"]>domainId</domain>
        //       <server[by="id, name, serviceHostname"]>serviceId</domain>
        //       <a n="zmailXMPPComponentCategory">category (see XEP-0030)</a>
        //       <a n="zmailXMPPComponentName">long component name</a>
        //       [<a n="zmailXMPPComponentType">type from XEP-0030</a>]
        //    </xmppComponent>
        //
        
        Element cEl = request.getElement(AccountConstants.E_XMPP_COMPONENT);
        Map<String, Object> attrs = AdminService.getAttrs(cEl);
        
        Element domainElt = cEl.getElement(AdminConstants.E_DOMAIN);
        String byStr = domainElt.getAttribute(AdminConstants.A_BY, "id");
        Key.DomainBy domainby = Key.DomainBy.valueOf(byStr);
        Domain domain = Provisioning.getInstance().get(domainby,domainElt.getText());
        
        Element serverElt = cEl.getElement(AdminConstants.E_SERVER);
        String serverByStr = serverElt.getAttribute(AdminConstants.A_BY);
        Server server = prov.get(Key.ServerBy.fromString(serverByStr), serverElt.getText());
        
        String name = cEl.getAttribute(AccountConstants.A_NAME);
        
        if (!name.endsWith(domain.getName())) {
            throw ServiceException.INVALID_REQUEST("Specified component name must be full name, and must be a subdomain of the specified parent", null);
        }
        
        checkRight(zsc, context, null, Admin.R_createXMPPComponent);
        checkSetAttrsOnCreate(zsc, TargetType.xmppcomponent, name, attrs);
        
        XMPPComponent comp = prov.createXMPPComponent(name, domain, server, attrs);
        
        Element response = zsc.createElement(AdminConstants.CREATE_XMPPCOMPONENT_RESPONSE);
        GetXMPPComponent.encodeXMPPComponent(response, comp);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_createXMPPComponent);
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY, 
                Admin.R_modifyXMPPComponent.getName(), "XMPP component"));
    }
}
