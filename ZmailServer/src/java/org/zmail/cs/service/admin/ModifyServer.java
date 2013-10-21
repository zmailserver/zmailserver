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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;

/**
 * @since Jun 17, 2004
 * @author schemers
 */
public final class ModifyServer extends AdminDocumentHandler {

    private static final String[] TARGET_SERVER_PATH = new String[] { AdminConstants.E_ID };

    @Override
    protected String[] getProxiedServerPath() {
        return TARGET_SERVER_PATH;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        String id = request.getAttribute(AdminConstants.E_ID);
        Map<String, Object> attrs = AdminService.getAttrs(request);

        Server server = prov.get(Key.ServerBy.id, id);
        if (server == null) {
            throw AccountServiceException.NO_SUCH_SERVER(id);
        }
        checkRight(zsc, context, server, attrs);

        // pass in true to checkImmutable
        prov.modifyAttrs(server, attrs, true);

        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "ModifyServer","name", server.getName()}, attrs));

        Element response = zsc.createElement(AdminConstants.MODIFY_SERVER_RESPONSE);
        GetServer.encodeServer(response, server);
        return response;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(String.format(AdminRightCheckPoint.Notes.MODIFY_ENTRY,
                Admin.R_modifyServer.getName(), "server"));
    }
}
