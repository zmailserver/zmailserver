/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.ldap.Check;
import org.zmail.common.soap.Element;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.CheckHostnameResolveRequest;
import org.zmail.soap.admin.message.CheckHostnameResolveResponse;

/**
 * @author schemers
 */
public class CheckHostnameResolve extends AdminDocumentHandler {

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);

        CheckHostnameResolveRequest req = JaxbUtil.elementToJaxb(request);
        String host = req.getHostname().toLowerCase();

        Provisioning.Result r = Check.checkHostnameResolve(host);

        return zsc.jaxbToElement(
            CheckHostnameResolveResponse.fromCodeMessage(
                    r.getCode(), r.getMessage()));
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }
}