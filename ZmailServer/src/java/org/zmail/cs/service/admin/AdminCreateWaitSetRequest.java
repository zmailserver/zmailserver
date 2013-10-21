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
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.service.mail.CreateWaitSet;
import org.zmail.soap.ZmailSoapContext;

public class AdminCreateWaitSetRequest extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element response = zsc.createElement(AdminConstants.ADMIN_CREATE_WAIT_SET_RESPONSE);
        return CreateWaitSet.staticHandle(this, request, context, response);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("If allAccounts is specified, " + AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
        notes.add("Otherwise, for each requested account, " + AdminRightCheckPoint.Notes.ADMIN_LOGIN_AS);
    }

}
