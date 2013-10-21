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

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author jhahm
 */
public class DeleteCalendarResource extends AdminDocumentHandler {

    private static final String[] TARGET_RESOURCE_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedResourcePath()  { return TARGET_RESOURCE_PATH; }

    /**
     * must be careful and only allow deletes domain admin has access to
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    /**
     * Deletes a calendar resource account and its mailbox.
     */
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getAttribute(AdminConstants.E_ID);

        // Confirm that the account exists and that the mailbox is located
        // on the current host
        CalendarResource resource = prov.get(Key.CalendarResourceBy.id, id);
        if (resource == null)
            throw AccountServiceException.NO_SUCH_CALENDAR_RESOURCE(id);
        
        checkCalendarResourceRight(zsc, resource, Admin.R_deleteCalendarResource);        

        if (!Provisioning.onLocalServer(resource)) {
            // Request must be sent to the host that the mailbox is on, so that
            // the mailbox can be deleted
            throw ServiceException.WRONG_HOST(resource.getAttr(Provisioning.A_zmailMailHost), null);
        }
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(resource);

        prov.deleteCalendarResource(id);
        mbox.deleteMailbox();

        ZmailLog.security.info(ZmailLog.encodeAttrs(new String[] {"cmd", "DeleteCalendarResource", "name", resource.getName(), "id", resource.getId()}));

        Element response = zsc.createElement(AdminConstants.DELETE_CALENDAR_RESOURCE_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_deleteCalendarResource);
    }
}
