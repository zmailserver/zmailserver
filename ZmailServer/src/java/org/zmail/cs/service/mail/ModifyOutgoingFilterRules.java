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
import org.zmail.cs.account.Account;
import org.zmail.cs.filter.RuleManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.mail.message.ModifyOutgoingFilterRulesRequest;
import org.zmail.soap.mail.message.ModifyOutgoingFilterRulesResponse;

public final class ModifyOutgoingFilterRules extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account)) {
            throw ServiceException.PERM_DENIED("cannot modify options");
        }

        ModifyOutgoingFilterRulesRequest req = JaxbUtil.elementToJaxb(request);
        RuleManager.setOutgoingXMLRules(account, req.getFilterRules());
        return zsc.jaxbToElement(new ModifyOutgoingFilterRulesResponse());
    }

}
