/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.common.account.Key.CosBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class CopyCos extends AdminDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        
        ZmailSoapContext lc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        String destCosName = request.getElement(AdminConstants.E_NAME).getText().toLowerCase();
        Element srcCosElem = request.getElement(AdminConstants.E_COS);
        String srcCosNameOrId = srcCosElem.getText();
        Key.CosBy srcCosBy = Key.CosBy.fromString(srcCosElem.getAttribute(AdminConstants.A_BY));
        
        Cos srcCos = prov.get(srcCosBy, srcCosNameOrId);
        if (srcCos == null)
            throw AccountServiceException.NO_SUCH_COS(srcCosNameOrId);
        
        checkRight(lc, context, null, Admin.R_createCos);
        checkRight(lc, context, srcCos, Admin.R_getCos);
        
        Cos cos = prov.copyCos(srcCos.getId(), destCosName);
        
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "CopyCos","name", destCosName, "cos", srcCosNameOrId}));         

        Element response = lc.createElement(AdminConstants.COPY_COS_RESPONSE);
        GetCos.encodeCos(response, cos);

        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_createCos);
        relatedRights.add(Admin.R_getCos);
        notes.add("Need the " + Admin.R_getCos.getName() + " right on the source cos.");
    }
}