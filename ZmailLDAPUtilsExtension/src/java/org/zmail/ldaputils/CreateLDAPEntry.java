/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.ldaputils;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.LDAPUtilsConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.cs.service.admin.AdminService;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author Greg Solovyev
 */
public class CreateLDAPEntry extends AdminDocumentHandler {

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {

        ZmailSoapContext lc = getZmailSoapContext(context);

        String dn = request.getAttribute(LDAPUtilsConstants.E_DN);
        Map<String, Object> attrs = AdminService.getAttrs(request, true);

        NamedEntry ne = LDAPUtilsHelper.getInstance().createLDAPEntry(dn,  attrs);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "CreateLDAPEntry","dn", dn}, attrs));

        Element response = lc.createElement(LDAPUtilsConstants.CREATE_LDAP_ENTRY_RESPONSE);
        ZmailLDAPUtilsService.encodeLDAPEntry(response,ne);

        return response;
    }

}
