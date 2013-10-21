/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.service.mail;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.ldap.LdapUtil;
import org.zmail.soap.ZmailSoapContext;

public class GenerateUUID extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element response = zsc.createElement(MailConstants.GENERATE_UUID_RESPONSE);
        response.setText(LdapUtil.generateUUID());
        return response;
    }
}
