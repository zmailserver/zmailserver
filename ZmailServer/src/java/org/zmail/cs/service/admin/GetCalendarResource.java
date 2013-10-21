/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import java.util.Set;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.CalendarResourceBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author jhahm
 */
public class GetCalendarResource extends AdminDocumentHandler {

    private static final String[] TARGET_RESOURCE_PATH = new String[] { AdminConstants.E_CALENDAR_RESOURCE };
    protected String[] getProxiedResourceElementPath()  { return TARGET_RESOURCE_PATH; }

    /**
     * must be careful and only return calendar resources
     * a domain admin can see
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        boolean applyCos = request.getAttributeBool(AdminConstants.A_APPLY_COS, true);
        Set<String> reqAttrs = getReqAttrs(request, AttributeClass.calendarResource);
        
        Element cr = request.getElement(AdminConstants.E_CALENDAR_RESOURCE);
        String key = cr.getAttribute(AdminConstants.A_BY);
        String value = cr.getText();

        CalendarResource resource = prov.get(Key.CalendarResourceBy.fromString(key), value);

        if (resource == null)
            throw AccountServiceException.NO_SUCH_CALENDAR_RESOURCE(value);

        AdminAccessControl aac = checkCalendarResourceRight(zsc, resource, AdminRight.PR_ALWAYS_ALLOW);
        
        Element response = zsc.createElement(AdminConstants.GET_CALENDAR_RESOURCE_RESPONSE);
        ToXML.encodeCalendarResource(response, resource, applyCos, reqAttrs, aac.getAttrRightChecker(resource));

        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getCalendarResource);
        notes.add(String.format(AdminRightCheckPoint.Notes.GET_ENTRY, Admin.R_getCalendarResource.getName()));
    }
}
