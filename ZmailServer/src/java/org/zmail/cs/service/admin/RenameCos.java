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

import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class RenameCos extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext lc = getZmailSoapContext(context);
	    Provisioning prov = Provisioning.getInstance();

	    String id = request.getElement(AdminConstants.E_ID).getText();
        String newName = request.getElement(AdminConstants.E_NEW_NAME).getText();

	    Cos cos = prov.get(Key.CosBy.id, id);
        if (cos == null)
            throw AccountServiceException.NO_SUCH_COS(id);
        
        // check if the admin can rename the cos
        checkRight(lc, context, cos, Admin.R_renameCos);

        String oldName = cos.getName();

        prov.renameCos(id, newName);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "RenameCos","name", oldName, "newName", newName})); 

        // get again with new name...

        cos = prov.get(Key.CosBy.id, id);
        if (cos == null)
            throw ServiceException.FAILURE("unabled to get renamed cos: "+id, null);
	    Element response = lc.createElement(AdminConstants.RENAME_COS_RESPONSE);
	    GetCos.encodeCos(response, cos);
	    return response;
	}

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_renameCos);
    }
}