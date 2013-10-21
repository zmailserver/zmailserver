/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.soap.ZmailSoapContext;

/**
 * @since Jun 25, 2004
 * @author dkarp
 */
public class ConvAction extends ItemAction {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException, SoapFaultException {
        ZmailSoapContext lc = getZmailSoapContext(context);

        Element action = request.getElement(MailConstants.E_ACTION);
        String operation = action.getAttribute(MailConstants.A_OPERATION).toLowerCase();

        String successes = handleCommon(context, request, operation, MailItem.Type.CONVERSATION);

        Element response = lc.createElement(MailConstants.CONV_ACTION_RESPONSE);
        Element act = response.addUniqueElement(MailConstants.E_ACTION);
        act.addAttribute(MailConstants.A_ID, successes);
        act.addAttribute(MailConstants.A_OPERATION, operation);
        return response;
    }
}
