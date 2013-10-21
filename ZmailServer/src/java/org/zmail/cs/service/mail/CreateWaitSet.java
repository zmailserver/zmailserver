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
package org.zmail.cs.service.mail;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.Pair;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.session.WaitSetAccount;
import org.zmail.cs.session.WaitSetError;
import org.zmail.cs.session.WaitSetMgr;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;

/**
 *
 */
public class CreateWaitSet extends MailDocumentHandler {
    /*
     <!--*************************************
          CreateWaitSet: must be called once to initialize the WaitSet
          and to set its "default interest types"
         ************************************* -->
        <CreateWaitSetRequest defTypes="DEFAULT_INTEREST_TYPES" [all="1"]>
          [ <add>
            [<a id="ACCTID" [token="lastKnownSyncToken"] [types="if_not_default"]/>]+
            </add> ]
        </CreateWaitSetRequest>

        <CreateWaitSetResponse waitSet="setId" defTypes="types" seq="0">
          [ <error ...something.../>]*
        </CreateWaitSetResponse>
     */

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element response = zsc.createElement(MailConstants.CREATE_WAIT_SET_RESPONSE);
        return staticHandle(this, request, context, response);
    }

    static public Element staticHandle(DocumentHandler handler, Element request, Map<String, Object> context, Element response) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        String defInterestStr = request.getAttribute(MailConstants.A_DEFTYPES);
        Set<MailItem.Type> defaultInterests = WaitSetRequest.parseInterestStr(defInterestStr,
                EnumSet.noneOf(MailItem.Type.class));
        boolean adminAllowed = zsc.getAuthToken().isAdmin();

        boolean allAccts = request.getAttributeBool(MailConstants.A_ALL_ACCOUNTS, false);
        if (allAccts) {
            WaitSetMgr.checkRightForAllAccounts(zsc);
        }

        List<WaitSetAccount> add = WaitSetRequest.parseAddUpdateAccounts(zsc, 
            request.getOptionalElement(MailConstants.E_WAITSET_ADD), defaultInterests);

        // workaround for 27480: load the mailboxes NOW, before we grab the waitset lock
        List<Mailbox> referencedMailboxes = new ArrayList<Mailbox>();
        for (WaitSetAccount acct : add) {
            try {
                MailboxManager.FetchMode fetchMode = MailboxManager.FetchMode.AUTOCREATE;
                Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(acct.getAccountId(), fetchMode);
                referencedMailboxes.add(mbox);
            } catch (ServiceException e) {
                ZmailLog.session.debug("Caught exception preloading mailbox for waitset", e);
            }
        }


        Pair<String, List<WaitSetError>> result = WaitSetMgr.create(zsc.getRequestedAccountId(), adminAllowed, defaultInterests, allAccts, add);
        String wsId = result.getFirst();
        List<WaitSetError> errors = result.getSecond();

        response.addAttribute(MailConstants.A_WAITSET_ID, wsId);
        response.addAttribute(MailConstants.A_DEFTYPES, WaitSetRequest.interestToStr(defaultInterests));
        response.addAttribute(MailConstants.A_SEQ, 0);

        WaitSetRequest.encodeErrors(response, errors);

        return response;
    }

}
