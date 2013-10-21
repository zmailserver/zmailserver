/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.XMPPComponent;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * 
 */
public class GetAllXMPPComponents extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        List<XMPPComponent> components = prov.getAllXMPPComponents();
        
        AdminAccessControl aac = AdminAccessControl.getAdminAccessControl(zsc);
        
        Element response = zsc.createElement(AdminConstants.GET_ALL_XMPPCOMPONENTS_REQUEST);
        
        for (XMPPComponent comp : components) {
            if (aac.hasRightsToList(comp, Admin.R_listXMPPComponent, null))
                GetXMPPComponent.encodeXMPPComponent(response, comp, null, aac.getAttrRightChecker(comp));
        }
        
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_listXMPPComponent);
        relatedRights.add(Admin.R_getXMPPComponent);
        
        notes.add(AdminRightCheckPoint.Notes.LIST_ENTRY);
    }
}
