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

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

public class DeleteZimlet extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();
	    
        Element z = request.getElement(AdminConstants.E_ZIMLET);
	    String name = z.getAttribute(AdminConstants.A_NAME);

	    Zimlet zimlet = prov.getZimlet(name);
        if (zimlet == null)
            throw AccountServiceException.NO_SUCH_ZIMLET(name);
        
        checkRight(zsc, context, zimlet, Admin.R_deleteZimlet);
        
        String id = zimlet.getId();
        prov.deleteZimlet(name);

        ZmailLog.security.info(ZmailLog.encodeAttrs(new String[] {"cmd", "DeleteZimlet","name", name, "id", id }));

	    Element response = zsc.createElement(AdminConstants.DELETE_ZIMLET_RESPONSE);
	    return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteZimlet);
    }
}