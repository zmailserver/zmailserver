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
package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Zimlet;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.soap.ZimbraSoapContext;

public class DeleteZimlet extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZimbraSoapContext zsc = getZimbraSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();
	    
        Element z = request.getElement(AdminConstants.E_ZIMLET);
	    String name = z.getAttribute(AdminConstants.A_NAME);

	    Zimlet zimlet = prov.getZimlet(name);
        if (zimlet == null)
            throw AccountServiceException.NO_SUCH_ZIMLET(name);
        
        checkRight(zsc, context, zimlet, Admin.R_deleteZimlet);
        
        String id = zimlet.getId();
        prov.deleteZimlet(name);

        ZimbraLog.security.info(ZimbraLog.encodeAttrs(new String[] {"cmd", "DeleteZimlet","name", name, "id", id }));

	    Element response = zsc.createElement(AdminConstants.DELETE_ZIMLET_RESPONSE);
	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteZimlet);
    }
}