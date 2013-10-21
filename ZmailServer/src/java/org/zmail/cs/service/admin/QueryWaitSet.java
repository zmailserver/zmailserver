/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.session.IWaitSet;
import org.zmail.cs.session.WaitSetMgr;
import org.zmail.soap.ZmailSoapContext;

/**
 * This API is used to dump the internal state of a wait set.  This API is intended
 * for debugging use only.
 */
public class QueryWaitSet extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        
        ZmailSoapContext zsc = getZmailSoapContext(context);
        WaitSetMgr.checkRightForAllAccounts(zsc); // must be a global admin
        
        Element response = zsc.createElement(AdminConstants.QUERY_WAIT_SET_RESPONSE);
        
        String waitSetId = request.getAttribute(MailConstants.A_WAITSET_ID, null);
        
        List<IWaitSet> sets;
        
        if (waitSetId != null) {
            sets = new ArrayList<IWaitSet>(1);
            IWaitSet ws = WaitSetMgr.lookup(waitSetId);
            if (ws == null) {
                throw AdminServiceException.NO_SUCH_WAITSET(waitSetId);
            }
            sets.add(ws);
        } else {
            sets = WaitSetMgr.getAll();
        }

        for (IWaitSet set : sets) {
            Element waitSetElt = response.addElement(AdminConstants.E_WAITSET);
            set.handleQuery(waitSetElt);
        }
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
    }
}
