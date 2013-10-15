/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.bp;

import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminExtConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class PurgeIMAPImportTasks extends AdminDocumentHandler {

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
         
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Element response = zsc.createElement(AdminExtConstants.PURGE_BULK_IMAP_IMPORT_TASKS_RESPONSE);
        BulkIMAPImportTaskManager.purgeQueue(zsc.getAuthtokenAccountId());
        return response;
    }
}
