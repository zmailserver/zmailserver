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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Right;
import org.zmail.cs.account.accesscontrol.RightClass;
import org.zmail.cs.account.accesscontrol.RightCommand;
import org.zmail.soap.ZmailSoapContext;

public class GetAllRights extends RightDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);
        
        String targetType = request.getAttribute(AdminConstants.A_TARGET_TYPE, null);
        boolean expandAllAtrts = request.getAttributeBool(AdminConstants.A_EXPAND_ALL_ATTRS, false);
        String rightClass = request.getAttribute(AdminConstants.A_RIGHT_CLASS, null);
        
        List<Right> rights = RightCommand.getAllRights(targetType, rightClass);
        
        Element response = zsc.createElement(AdminConstants.GET_ALL_RIGHTS_RESPONSE);
        for (Right right : rights)
            RightCommand.rightToXML(response, right, expandAllAtrts, account.getLocale());
        
        return response;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }
}
