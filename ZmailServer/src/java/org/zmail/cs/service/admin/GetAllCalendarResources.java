/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import org.dom4j.QName;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.service.admin.GetAllAccounts.AccountVisitor;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author jhahm
 */
public class GetAllCalendarResources extends GetAllAccounts {

    protected QName getResponseQName() {
        return AdminConstants.GET_ALL_CALENDAR_RESOURCES_RESPONSE;
    }
    
    protected static class CalendarResourceVisitor extends AccountVisitor {
        CalendarResourceVisitor(ZmailSoapContext zsc, AdminDocumentHandler handler, Element parent) 
        throws ServiceException {
            super(zsc, handler, parent);
        }
        
        public void visit(org.zmail.cs.account.NamedEntry entry) throws ServiceException {
            if (mHandler.hasRightsToList(mZsc, entry, Admin.R_listCalendarResource, null))
                ToXML.encodeCalendarResource(mParent, (CalendarResource)entry, true, null, mAAC.getAttrRightChecker(entry));
        }
    }
    
    /*
     * server s is not used, need to use the same signature as GetAllAccounts.doDomain 
     * so the overridden doDomain is called.
     */
    protected void doDomain(ZmailSoapContext zsc, final Element e, Domain d, Server s) throws ServiceException {
        CalendarResourceVisitor visitor = new CalendarResourceVisitor(zsc, this, e);
        Provisioning.getInstance().getAllCalendarResources(d, s, visitor);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_listCalendarResource);
        relatedRights.add(Admin.R_getCalendarResource);
        
        notes.add(AdminRightCheckPoint.Notes.LIST_ENTRY);
    }
}
