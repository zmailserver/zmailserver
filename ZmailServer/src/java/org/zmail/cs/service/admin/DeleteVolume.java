/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.volume.VolumeManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.DeleteVolumeRequest;
import org.zmail.soap.admin.message.DeleteVolumeResponse;

public final class DeleteVolume extends AdminDocumentHandler {

    @Override
    public Element handle(Element req, Map<String, Object> ctx) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(ctx);
        return zsc.jaxbToElement(handle((DeleteVolumeRequest) JaxbUtil.elementToJaxb(req), ctx));
    }

    private DeleteVolumeResponse handle(DeleteVolumeRequest req, Map<String, Object> ctx) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(ctx);
        checkRight(zsc, ctx, Provisioning.getInstance().getLocalServer(), Admin.R_manageVolume);

        VolumeManager mgr = VolumeManager.getInstance();
        mgr.getVolume(req.getId()); // make sure the volume exists before doing anything heavyweight...
        mgr.delete(req.getId());
        return new DeleteVolumeResponse();
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageVolume);
    }

}
