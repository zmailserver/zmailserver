/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.GalContact;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.gal.GalOp;
import org.zmail.cs.account.ldap.Check;
import org.zmail.cs.service.mail.ToXML;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class CheckGalConfig extends AdminDocumentHandler {

	public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Element q = request.getOptionalElement(AdminConstants.E_QUERY);
        String query = null;
        long limit = 0;
        if (q != null) {
            query = q.getText();
            limit = q.getAttributeLong(AdminConstants.A_LIMIT, 10);
        }
        
        Element action = request.getOptionalElement(AdminConstants.E_ACTION);
        GalOp galOp = GalOp.search;
        if (action != null)
            galOp = GalOp.fromString(action.getText());
                
	    Map attrs = AdminService.getAttrs(request, true);

        Element response = zsc.createElement(AdminConstants.CHECK_GAL_CONFIG_RESPONSE);
        Provisioning.Result r = Provisioning.getInstance().checkGalConfig(
                attrs, query, (int)limit, galOp);
        
        response.addElement(AdminConstants.E_CODE).addText(r.getCode());
        String message = r.getMessage();
        if (message != null)
            response.addElement(AdminConstants.E_MESSAGE).addText(message);

        if (r instanceof Provisioning.GalResult) {
            List<GalContact> contacts = ((Provisioning.GalResult)r).getContacts();
            if (contacts != null) {
                for (GalContact contact : contacts) {
                    ToXML.encodeGalContact(response, contact);
                }
            }
        }
	    return response;
	}
	
	@Override
	public void docRights(List<AdminRight> relatedRights, List<String> notes) {
	    notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }
}
