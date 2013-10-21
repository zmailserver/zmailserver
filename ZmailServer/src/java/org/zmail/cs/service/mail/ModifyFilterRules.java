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

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.filter.RuleManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.mail.message.ModifyFilterRulesRequest;
import org.zmail.soap.mail.message.ModifyFilterRulesResponse;

public final class ModifyFilterRules extends MailDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account)) {
            throw ServiceException.PERM_DENIED("cannot modify options");
        }

        ModifyFilterRulesRequest req = JaxbUtil.elementToJaxb(request);
        RuleManager.setIncomingXMLRules(account, req.getFilterRules());
        return zsc.jaxbToElement(new ModifyFilterRulesResponse());
    }

}
