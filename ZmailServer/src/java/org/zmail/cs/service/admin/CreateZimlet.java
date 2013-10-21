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

import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

public class CreateZimlet extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
	    
        ZmailSoapContext lc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();
	    
	    String name = request.getAttribute(AdminConstants.E_NAME).toLowerCase();
	    Map<String, Object> attrs = AdminService.getAttrs(request, true);

	    checkRight(lc, context, null, Admin.R_createZimlet);
        checkSetAttrsOnCreate(lc, TargetType.zimlet, name, attrs);
        
	    Zimlet zimlet = prov.createZimlet(name, attrs);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "CreateZimlet","name", name}, attrs));

	    Element response = lc.createElement(AdminConstants.CREATE_ZIMLET_RESPONSE);
	    GetZimlet.encodeZimlet(response, zimlet);

	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_createZimlet);
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY, 
                Admin.R_modifyZimlet.getName(), "zimlet"));
    }
}