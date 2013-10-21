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
import org.zmail.cs.volume.Volume;
import org.zmail.cs.volume.VolumeManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.ModifyVolumeRequest;
import org.zmail.soap.admin.message.ModifyVolumeResponse;
import org.zmail.soap.admin.type.VolumeInfo;

public final class ModifyVolume extends AdminDocumentHandler {

    @Override
    public Element handle(Element req, Map<String, Object> ctx) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(ctx);
        return zsc.jaxbToElement(handle((ModifyVolumeRequest) JaxbUtil.elementToJaxb(req), ctx));
    }

    private ModifyVolumeResponse handle(ModifyVolumeRequest req, Map<String, Object> ctx) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(ctx);
        checkRight(zsc, ctx, Provisioning.getInstance().getLocalServer(), Admin.R_manageVolume);

        VolumeManager mgr = VolumeManager.getInstance();
        Volume.Builder builder = Volume.builder(mgr.getVolume(req.getId()));
        VolumeInfo vol = req.getVolume();
        if (vol == null) {
            throw ServiceException.INVALID_REQUEST("must specify a volume Element", null);
        }
        if (vol.getType() > 0) {
            builder.setType(vol.getType());
        }
        if (vol.getName() != null) {
            builder.setName(vol.getName());
        }
        if (vol.getRootPath() != null) {
            builder.setPath(vol.getRootPath(), true);
        }
        if (vol.getCompressBlobs() != null) {
            builder.setCompressBlobs(vol.getCompressBlobs());
        }
        if (vol.getCompressionThreshold() > 0) {
            builder.setCompressionThreshold(vol.getCompressionThreshold());
        }
        mgr.update(builder.build());
        return new ModifyVolumeResponse();

    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageVolume);
    }

}
