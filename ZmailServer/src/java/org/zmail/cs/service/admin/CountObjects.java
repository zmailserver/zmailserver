/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.UCService;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.type.CountObjectsType;

public class CountObjects extends AdminDocumentHandler {
    
    public Element handle(Element request, Map<String, Object> context) 
    throws ServiceException {
        
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        CountObjectsType type = CountObjectsType.fromString(
                request.getAttribute(AdminConstants.A_TYPE));
        
        Domain domain = null;
        Element eDomain = request.getOptionalElement(AdminConstants.E_DOMAIN);
        if (eDomain != null) {
            if (!type.allowsDomain()) {
                throw ServiceException.INVALID_REQUEST("domain cannot be specified for type: " + type.name(), null);
            }
            
            String key = eDomain.getAttribute(AdminConstants.A_BY);
            String value = eDomain.getText();
                
            domain = prov.get(Key.DomainBy.fromString(key), value);
            if (domain == null) {
                throw AccountServiceException.NO_SUCH_DOMAIN(value);
            }
        }
        
        UCService ucService = null;
        Element eUCService = request.getOptionalElement(AdminConstants.E_UC_SERVICE);
        if (eUCService != null) {
            if (!type.allowsUCService()) {
                throw ServiceException.INVALID_REQUEST("UCService cannot be specified for type: " + type.name(), null);
            }
            
            String key = eUCService.getAttribute(AdminConstants.A_BY);
            String value = eUCService.getText();
                
            ucService = prov.get(Key.UCServiceBy.fromString(key), value);
            if (ucService == null) {
                throw AccountServiceException.NO_SUCH_UC_SERVICE(value);
            }
        }
        
        long count = prov.countObjects(type, domain, ucService);

        Element response = zsc.createElement(AdminConstants.COUNT_OBJECTS_RESPONSE);
        response.addAttribute(AdminConstants.A_NUM, count);
        return response;
    }
    
}
