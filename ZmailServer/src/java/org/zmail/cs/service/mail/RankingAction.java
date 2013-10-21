/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.mailbox.ContactRankings;
import org.zmail.soap.ZmailSoapContext;

public class RankingAction extends MailDocumentHandler {
    
    public static final String OP_RESET = "reset";
    public static final String OP_DELETE = "delete";
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);
        if (account == null)
            throw AccountServiceException.NO_SUCH_ACCOUNT("");
        Element action = request.getElement(MailConstants.E_ACTION);
        String operation = action.getAttribute(MailConstants.A_OPERATION).toLowerCase();
        if (operation.equals(OP_RESET)) {
            ContactRankings.reset(account.getId());
        } else if (operation.equals(OP_DELETE)) {
            String email = action.getAttribute(MailConstants.A_EMAIL);
            ContactRankings.remove(account.getId(), email);
        }
        return zsc.createElement(MailConstants.RANKING_ACTION_RESPONSE);
    }
}
