/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.List;
import java.util.Map;

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author jhahm
 */
public class RenameCalendarResource extends AdminDocumentHandler {

    private static final String[] TARGET_RESOURCE_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedResourcePath()  { return TARGET_RESOURCE_PATH; }

    /**
     * must be careful and only allow renames from/to
     * domains a domain admin can see
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getAttribute(AdminConstants.E_ID);
        String newName = request.getAttribute(AdminConstants.E_NEW_NAME);

        CalendarResource resource = prov.get(Key.CalendarResourceBy.id, id);
        if (resource == null)
            throw AccountServiceException.NO_SUCH_CALENDAR_RESOURCE(id);
        
        String oldName = resource.getName();

        // check if the admin can rename the calendar resource
        checkAccountRight(zsc, resource, Admin.R_renameCalendarResource);

        // check if the admin can "create calendar resource" in the domain (can be same or diff)
        checkDomainRightByEmail(zsc, newName, Admin.R_createCalendarResource);

        prov.renameCalendarResource(id, newName);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "RenameCalendarResource", "name",
                              oldName, "newName", newName}));

        // get again with new name...

        resource = prov.get(Key.CalendarResourceBy.id, id);
        if (resource == null)
            throw ServiceException.FAILURE("unable to get calendar resource after rename: " + id, null);
        Element response = zsc.createElement(AdminConstants.RENAME_CALENDAR_RESOURCE_RESPONSE);
        ToXML.encodeCalendarResource(response, resource, true);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_renameCalendarResource);
        relatedRights.add(Admin.R_createCalendarResource);
    }
}
