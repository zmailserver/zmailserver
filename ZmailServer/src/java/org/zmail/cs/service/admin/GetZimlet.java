/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.AccessManager.AttrRightChecker;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class GetZimlet extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        Set<String> reqAttrs = getReqAttrs(request, AttributeClass.zimletEntry);
        
        Element z = request.getElement(AdminConstants.E_ZIMLET);
        String n = z.getAttribute(AdminConstants.A_NAME);

        Zimlet zimlet = prov.getZimlet(n);

        if (zimlet == null)
            throw AccountServiceException.NO_SUCH_ZIMLET(n);
        
        AdminAccessControl aac = checkRight(zsc, context, zimlet, AdminRight.PR_ALWAYS_ALLOW);

        Element response = zsc.createElement(AdminConstants.GET_ZIMLET_RESPONSE);
        encodeZimlet(response, zimlet, reqAttrs, aac.getAttrRightChecker(zimlet));
        
        return response;
    }

    static void encodeZimlet(Element response, Zimlet zimlet) throws ServiceException {
        encodeZimlet(response, zimlet, null, null);
    }

    static void encodeZimlet(Element response, Zimlet zimlet, Set<String> reqAttrs, 
            AttrRightChecker attrRightChecker) throws ServiceException {
        Element zim = response.addElement(AdminConstants.E_ZIMLET);
        zim.addAttribute(AdminConstants.A_NAME, zimlet.getName());
        zim.addAttribute(AdminConstants.A_ID, zimlet.getId());
        String keyword = zimlet.getAttr(Provisioning.A_zmailZimletKeyword);
        if (keyword != null)
            zim.addAttribute(AdminConstants.A_HAS_KEYWORD, keyword);
        Map<String,Object> attrs = zimlet.getUnicodeAttrs();
        ToXML.encodeAttrs(zim, attrs, reqAttrs, attrRightChecker);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getZimlet);
        notes.add(String.format(AdminRightCheckPoint.Notes.GET_ENTRY, Admin.R_getZimlet.getName()));
    }
}
