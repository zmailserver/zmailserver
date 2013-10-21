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
import java.util.Set;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.XMPPComponentBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.XMPPComponent;
import org.zmail.cs.account.AccessManager.AttrRightChecker;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * <GetXMPPComponentRequest>
 *    <xmppcomponent by="by">identifier</xmppcomponent>
 * </GetXMPPComponentRequest>   
 */
public class GetXMPPComponent extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        Set<String> reqAttrs = getReqAttrs(request, AttributeClass.xmppComponent);
        Element id = request.getElement(AdminConstants.E_XMPP_COMPONENT);
        String byStr = id.getAttribute(AdminConstants.A_BY);
        String name = id.getText();
        
        if (name == null || name.equals(""))
            throw ServiceException.INVALID_REQUEST("must specify a value for a xmppcomponent", null);
        
        Key.XMPPComponentBy by = Key.XMPPComponentBy.valueOf(byStr);
        
        XMPPComponent comp = prov.get(by, name);
        if (comp == null)
            throw AccountServiceException.NO_SUCH_XMPP_COMPONENT(name);
        
        AdminAccessControl aac = checkRight(zsc, context, comp, AdminRight.PR_ALWAYS_ALLOW);
        
        Element response = zsc.createElement(AdminConstants.GET_XMPPCOMPONENT_RESPONSE);
        encodeXMPPComponent(response, comp, reqAttrs, aac.getAttrRightChecker(comp));
        return response;
    }
    
    public static Element encodeXMPPComponent(Element parent, XMPPComponent comp) {
        return encodeXMPPComponent(parent, comp, null, null);
    }
    
    public static Element encodeXMPPComponent(Element parent, XMPPComponent comp,
            Set<String> reqAttrs, AttrRightChecker attrRightChecker) {
        Element e = parent.addElement(AccountConstants.E_XMPP_COMPONENT);
        e.addAttribute(AccountConstants.A_NAME, comp.getName());
        e.addAttribute(AccountConstants.A_ID, comp.getId());
        
        try { // for testing only
            e.addAttribute("x-domainName", comp.getDomain().getName());
        } catch (ServiceException ex) {}

        try { // for testing only
            e.addAttribute("x-serverName", comp.getServer().getName());
        } catch (ServiceException ex) {}
        
        ToXML.encodeAttrs(e, comp.getUnicodeAttrs(), reqAttrs, attrRightChecker);
        return e;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getXMPPComponent);
        notes.add(String.format(AdminRightCheckPoint.Notes.GET_ENTRY, Admin.R_getXMPPComponent.getName()));
    }

}
