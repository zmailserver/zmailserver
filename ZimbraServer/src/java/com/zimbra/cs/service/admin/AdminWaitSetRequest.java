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
package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.Constants;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.service.mail.WaitSetRequest;
import com.zimbra.soap.ZimbraSoapContext;

public class AdminWaitSetRequest extends AdminDocumentHandler {
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Element response = zsc.createElement(AdminConstants.ADMIN_WAIT_SET_RESPONSE);
        return WaitSetRequest.staticHandle(request, context, response, true);
    }
    
    @Override
    public void preProxy(Element request, Map<String, Object> context) throws ServiceException {
        setProxyTimeout(WaitSetRequest.getTimeoutMillis(request, true) + 10 * Constants.MILLIS_PER_SECOND);
        super.preProxy(request, context);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("If the waitset is on all accounts, " + AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
        notes.add("Otherwise, must be the owner of the specified waitset");
    }
}
