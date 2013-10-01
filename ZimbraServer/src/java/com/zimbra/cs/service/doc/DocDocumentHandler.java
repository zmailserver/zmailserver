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
package com.zimbra.cs.service.doc;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.service.mail.MailDocumentHandler;
import com.zimbra.cs.service.util.ItemId;
import com.zimbra.soap.ZimbraSoapContext;

public abstract class DocDocumentHandler extends MailDocumentHandler {
    private static final String[] TARGET_ID_PATH = new String[] { MailConstants.E_WIKIWORD, MailConstants.A_ID };
    private static final String[] TARGET_DOC_ID_PATH = new String[] { MailConstants.E_DOC, MailConstants.A_ID };
    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_WIKIWORD, MailConstants.A_FOLDER };
    private static final String[] TARGET_DOC_FOLDER_PATH = new String[] { MailConstants.E_DOC, MailConstants.A_FOLDER };

    @Override
    protected String[] getProxiedIdPath(Element request)     {
        String[] path = TARGET_ID_PATH;
        String id = getXPath(request, path);
        if (id == null) {
            path = TARGET_DOC_ID_PATH;
            id = getXPath(request, path);
        }
        if (id == null) {
            path = TARGET_FOLDER_PATH;
            id = getXPath(request, path);
        }
        if (id == null) {
            path = TARGET_DOC_FOLDER_PATH;
            id = getXPath(request, path);
        }
        return path; 
    }

    @Override
    protected boolean checkMountpointProxy(Element request)  { return true; }

    protected String getAuthor(ZimbraSoapContext zsc) throws ServiceException {
        return getAuthenticatedAccount(zsc).getName();
    }

    protected ItemId getRequestedFolder(Element request, ZimbraSoapContext zsc) throws ServiceException {
        for (Element elem : request.listElements()) {
            String fid = elem.getAttribute(MailConstants.A_FOLDER, null);
            if (fid != null) {
                return new ItemId(fid, zsc);
            }
        }
        return null;
    }
}
