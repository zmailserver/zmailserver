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
package org.zmail.cs.service.mail;

import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.accesscontrol.Right;
import org.zmail.cs.account.accesscontrol.ACLUtil;
import org.zmail.cs.account.accesscontrol.RightManager;
import org.zmail.cs.account.accesscontrol.ZmailACE;
import org.zmail.soap.ZmailSoapContext;

/*
 * Delete this class in bug 66989
 */

public class GetPermission extends MailDocumentHandler {
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canAccessAccount(zsc, account))
            throw ServiceException.PERM_DENIED("can not access account");

        Set<Right> specificRights = null;
        for (Element eACE : request.listElements(MailConstants.E_ACE)) {
            if (specificRights == null)
                specificRights = new HashSet<Right>();
            specificRights.add(RightManager.getInstance().getUserRight(eACE.getAttribute(MailConstants.A_RIGHT)));
        }
        
        List<ZmailACE> aces = (specificRights==null)?ACLUtil.getAllACEs(account) : ACLUtil.getACEs(account, specificRights);
        Element response = zsc.createElement(MailConstants.GET_PERMISSION_RESPONSE);
        if (aces != null) {
            for (ZmailACE ace : aces)
                ToXML.encodeACE(response, ace);
        }
        return response;
    }
}


