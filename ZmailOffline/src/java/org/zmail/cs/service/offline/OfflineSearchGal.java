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
package org.zmail.cs.service.offline;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.account.offline.OfflineGal;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.ZcsMailbox;
import org.zmail.cs.mailbox.OfflineServiceException;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class OfflineSearchGal extends DocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext ctxt = getZmailSoapContext(context);
        Account account = getRequestedAccount(getZmailSoapContext(context));
        if (!(account instanceof OfflineAccount))
            throw OfflineServiceException.MISCONFIGURED("incorrect account class: " + account.getClass().getSimpleName());
        
        if (!account.getBooleanAttr(Provisioning.A_zmailFeatureGalEnabled , false))
            throw ServiceException.PERM_DENIED("GAL disabled");
        
        Mailbox mbox = getRequestedMailbox(ctxt);
        if (!(mbox instanceof ZcsMailbox))
            return getResponseElement(ctxt);
        
        Element response;
        if (account.getBooleanAttr(Provisioning.A_zmailFeatureGalSyncEnabled , false)) {
            response = ctxt.createElement(AccountConstants.SEARCH_GAL_RESPONSE);
            
            String name = request.getAttribute(AccountConstants.E_NAME);
            while (name.endsWith("*"))
                name = name.substring(0, name.length() - 1);            

            String type = request.getAttribute(AccountConstants.A_TYPE, "all");
            String sortByStr = request.getAttribute(AccountConstants.A_SORT_BY, null);
            int offset = (int) request.getAttributeLong(MailConstants.A_QUERY_OFFSET, 0);
            int limit = (int) request.getAttributeLong(MailConstants.A_QUERY_LIMIT, 0);
            Element cursor = request.getOptionalElement(MailConstants.E_CURSOR);
            SortBy sortBy = sortByStr == null ? null : SortBy.of(sortByStr);
            (new OfflineGal((OfflineAccount)account)).search(response, name, type, sortBy, offset, limit, cursor);                  
        } else { // proxy mode
            response = ((ZcsMailbox)mbox).proxyRequest(request, ctxt.getResponseProtocol(), true, "search GAL");
            if (response == null) {
                response = ctxt.createElement(AccountConstants.SEARCH_GAL_RESPONSE);
                response.addAttribute(AccountConstants.A_MORE, false);
            }
        }        
        return response;        
    }   
}