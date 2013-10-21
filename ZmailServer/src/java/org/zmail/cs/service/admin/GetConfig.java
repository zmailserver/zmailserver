/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

/*
 * Created on Jun 17, 2004
 */
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class GetConfig extends AdminDocumentHandler {
    
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        Element a = request.getElement(AdminConstants.E_A);
        String name = a.getAttribute(AdminConstants.A_N);

        Config config = prov.getConfig();
        
        AdminAccessControl aac = checkRight(zsc, context, config, AdminRight.PR_ALWAYS_ALLOW);
        
        Element response = zsc.createElement(AdminConstants.GET_CONFIG_RESPONSE);
        
        Set<String> reqAttrs = new HashSet<String>();
        reqAttrs.add(name);
        GetAllConfig.encodeConfig(response, config, reqAttrs, aac.getAttrRightChecker(config));

        return response;
	}
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("Need get attr right for the specified attribute.");
    }
}
