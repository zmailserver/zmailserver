/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.soap.ZmailSoapContext;


public class GetSpellDictionaries
extends MailDocumentHandler {

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zc = getZmailSoapContext(context);
        Server server = Provisioning.getInstance().getLocalServer();
        Element response = zc.createElement(MailConstants.GET_SPELL_DICTIONARIES_RESPONSE);

        for (String dictionary : server.getSpellAvailableDictionary()) {
            response.addElement(MailConstants.E_DICTIONARY).setText(dictionary);
        }
        
        return response;
    }
}
