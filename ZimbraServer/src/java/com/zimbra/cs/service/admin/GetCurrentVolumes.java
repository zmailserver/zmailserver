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

package com.zimbra.cs.service.admin;

import java.util.List;
import java.util.Map;

import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.cs.volume.Volume;
import com.zimbra.cs.volume.VolumeManager;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.soap.JaxbUtil;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.admin.message.GetCurrentVolumesRequest;
import com.zimbra.soap.admin.message.GetCurrentVolumesResponse;

public final class GetCurrentVolumes extends AdminDocumentHandler {

    @Override
    public Element handle(Element req, Map<String, Object> ctx) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(ctx);
        return zsc.jaxbToElement(handle((GetCurrentVolumesRequest) JaxbUtil.elementToJaxb(req), ctx));
    }

    private GetCurrentVolumesResponse handle(@SuppressWarnings("unused") GetCurrentVolumesRequest req,
            Map<String, Object> ctx) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(ctx);
        checkRight(zsc, ctx, Provisioning.getInstance().getLocalServer(), Admin.R_manageVolume);

        GetCurrentVolumesResponse resp = new GetCurrentVolumesResponse();
        VolumeManager mgr = VolumeManager.getInstance();
        Volume msgVol = mgr.getCurrentMessageVolume();
        if (msgVol != null) {
            resp.addVolume(new GetCurrentVolumesResponse.CurrentVolumeInfo(msgVol.getId(), msgVol.getType()));
        }
        Volume secondaryMsgVol = mgr.getCurrentSecondaryMessageVolume();
        if (secondaryMsgVol != null) {
            resp.addVolume(new GetCurrentVolumesResponse.CurrentVolumeInfo(
                    secondaryMsgVol.getId(), secondaryMsgVol.getType()));
        }
        Volume indexVol = mgr.getCurrentIndexVolume();
        if (indexVol != null) {
            resp.addVolume(new GetCurrentVolumesResponse.CurrentVolumeInfo(indexVol.getId(), indexVol.getType()));
        }
        return resp;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_manageVolume);
    }

}
