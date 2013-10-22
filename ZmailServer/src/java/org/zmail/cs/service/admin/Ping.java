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
package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.admin.message.PingResponse;
import com.zimbra.cs.account.accesscontrol.AdminRight;

/**
 * @author schemers
 */
public class Ping extends AdminDocumentHandler {

    /* (non-Javadoc)
      * @see com.zimbra.soap.DocumentHandler#handle(org.dom4j.Element, java.util.Map)
      */
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZimbraSoapContext lc = getZimbraSoapContext(context);
        return lc.jaxbToElement(new PingResponse());
    }

    public boolean needsAuth(Map<String, Object> context) {
        // return false because this may be called from Perl which
        // doesn't have auth token
        return false;
    }

    public boolean needsAdminAuth(Map<String, Object> context) {
        // return false because this may be called from Perl which
        // doesn't have auth token
        return false;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }
}
