/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

import java.io.IOException;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.util.yauth.MetadataTokenStore;
import org.zmail.cs.util.yauth.TokenAuthenticateV1;
import org.zmail.soap.ZmailSoapContext;

/**
 * 
 */
public class GetYahooAuthToken extends MailDocumentHandler {
    
    private static final String APPID = "ZYMSGRINTEGRATION";

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        
        MetadataTokenStore mts = new MetadataTokenStore(mbox);
        
        String userId = request.getAttribute("user");
        String passwd = request.getAttribute("password");
        
        Element response = zsc.createElement(MailConstants.GET_YAHOO_AUTH_TOKEN_RESPONSE);
        
        try {
            String token = TokenAuthenticateV1.getToken(userId, passwd);
            mts.putToken(APPID, userId, token);
            if (token == null) {
                response.addAttribute("failed", true);
            }
        } catch (IOException e) {
            throw ServiceException.FAILURE("IOException", e);
        }
        
        
        return response;
    }
}
