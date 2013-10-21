/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccessManager;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.account.accesscontrol.ACLAccessManager;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.zimlet.ZimletPresence.Presence;
import org.zmail.cs.zimlet.ZimletUtil;
import org.zmail.soap.ZmailSoapContext;

public class GetAdminExtensionZimlets extends AdminDocumentHandler  {

    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
		ZmailSoapContext zsc = getZmailSoapContext(context);
		
        Element response = zsc.createElement(AdminConstants.GET_ADMIN_EXTENSION_ZIMLETS_RESPONSE);
        Element zimlets = response.addUniqueElement(AccountConstants.E_ZIMLETS);
        doExtensionZimlets(zsc, context, zimlets);
        
        return response;
    }

	private void doExtensionZimlets(ZmailSoapContext zsc, Map<String, Object> context, Element response) throws ServiceException {
		Iterator<Zimlet> zimlets = Provisioning.getInstance().listAllZimlets().iterator();
		while (zimlets.hasNext()) {
		    
		    Zimlet z = (Zimlet) zimlets.next();
		    
		    if (!hasRightsToList(zsc, z, Admin.R_listZimlet, Admin.R_getZimlet))
			    continue;
			
			if (z.isExtension()) {
			    boolean include = true;
			    if ("org_zmail_delegatedadmin".equals(z.getName()))
                    include = (AccessManager.getInstance() instanceof ACLAccessManager);
			    if (include)
				    ZimletUtil.listZimlet(response, z, -1, Presence.enabled); // admin zimlets are all enabled
			}
		}
    }
	
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_listZimlet);
        relatedRights.add(Admin.R_getZimlet);
        
        notes.add("Only zimlets on which the authed admin has effective " + 
                  Admin.R_listZimlet.getName() + " and " + Admin.R_getZimlet.getName() + 
                  " rights will appear in the response.");
        
        notes.add("e.g. there are zimlet1, zimlet2, zimlet3, if an admin has effective " + 
                  Admin.R_listZimlet.getName() + " and " + Admin.R_getZimlet.getName() +  
                  " rights on zimlet1, zimlet2, " + 
                  "then only zimlet1, zimlet2 will appear in the GetAdminExtensionZimletsResponse.  " + 
                  "The GetAdminExtensionZimletsRequest itself will not get PERM_DENIED.");
    }
	
}
