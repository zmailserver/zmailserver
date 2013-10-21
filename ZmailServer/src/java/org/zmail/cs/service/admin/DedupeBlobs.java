/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.Pair;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.store.StoreManager;
import org.zmail.cs.store.file.BlobDeduper;
import org.zmail.cs.store.file.FileBlobStore;
import org.zmail.cs.volume.Volume;
import org.zmail.cs.volume.VolumeManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.DedupeBlobsRequest;
import org.zmail.soap.admin.message.DedupeBlobsResponse;
import org.zmail.soap.admin.message.DedupeBlobsResponse.DedupStatus;
import org.zmail.soap.admin.type.IntIdAttr;
import org.zmail.soap.admin.type.VolumeIdAndProgress;

public final class DedupeBlobs extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        checkRight(zsc, context, null, AdminRight.PR_SYSTEM_ADMIN_ONLY);
        StoreManager sm = StoreManager.getInstance();
        if (!(sm instanceof FileBlobStore)) {
            throw ServiceException.INVALID_REQUEST(sm.getClass().getName()
                    + " is not supported", null);
        }
        DedupeBlobsRequest req = JaxbUtil.elementToJaxb(request);
        BlobDeduper deduper = BlobDeduper.getInstance();
        DedupeBlobsResponse resp = new DedupeBlobsResponse();
        // Assemble the list of volumes.
        List<IntIdAttr> volumeList = req.getVolumes();
        List<Short> volumeIds = new ArrayList<Short>();
        if ((req.getAction() == DedupeBlobsRequest.DedupAction.start) ||
                (req.getAction() == DedupeBlobsRequest.DedupAction.reset)) {
            if (volumeList.isEmpty()) {
                // Get all message volume id's.
                for (Volume vol : VolumeManager.getInstance().getAllVolumes()) {
                    switch (vol.getType()) {
                    case Volume.TYPE_MESSAGE:
                    case Volume.TYPE_MESSAGE_SECONDARY:
                        volumeIds.add(vol.getId());
                        break;
                    }
                }
            } else {
                // Read volume id's from the request.
                for (IntIdAttr attr : volumeList) {
                    short volumeId = (short) attr.getId();
                    Volume vol = VolumeManager.getInstance().getVolume(volumeId);
                    if (vol.getType() == Volume.TYPE_INDEX) {
                        throw ServiceException.INVALID_REQUEST("Index volume " + volumeId + " is not supported", null);
                    } else {
                        volumeIds.add(volumeId);
                    }
                }
            }
        }
        if (req.getAction() == DedupeBlobsRequest.DedupAction.start) {
                try {
                    deduper.process(volumeIds);
                } catch (IOException e) {
                    throw ServiceException.FAILURE("error while deduping", e);
                }
        } else if (req.getAction() == DedupeBlobsRequest.DedupAction.stop) {
            deduper.stopProcessing();
        } else if (req.getAction() == DedupeBlobsRequest.DedupAction.reset) {
            if (volumeList.isEmpty()) {
                deduper.resetVolumeBlobs(new ArrayList<Short>());
            } else {
                deduper.resetVolumeBlobs(volumeIds);
            }
        }
        // return the stats for all actions.
        boolean isRunning = deduper.isRunning();
        if (isRunning) {
            resp.setStatus(DedupStatus.running);
        } else {
            resp.setStatus(DedupStatus.stopped);
        }
        Map<Short, String> volumeBlobsProgress = deduper.getVolumeBlobsProgress();
        VolumeIdAndProgress[] blobProgress = new VolumeIdAndProgress[volumeBlobsProgress.size()];
        int i=0;
        for (Map.Entry<Short, String> entry : volumeBlobsProgress.entrySet()) {
        	blobProgress[i++] = new VolumeIdAndProgress(String.valueOf(entry.getKey()), entry.getValue());
        }
        resp.setVolumeBlobsProgress(blobProgress);
        Map<Short, String> blobDigestsProgress = deduper.getBlobDigestsProgress();
        VolumeIdAndProgress[] digestProgress = new VolumeIdAndProgress[blobDigestsProgress.size()];
        i=0;
        for (Map.Entry<Short, String> entry : blobDigestsProgress.entrySet()) {
        	digestProgress[i++] = new VolumeIdAndProgress(String.valueOf(entry.getKey()), entry.getValue());
        }
        resp.setBlobDigestsProgress(digestProgress);
        Pair<Integer, Long> pair = deduper.getCountAndSize();
        resp.setTotalCount(pair.getFirst());
        resp.setTotalSize(pair.getSecond());
        return JaxbUtil.jaxbToElement(resp);
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.SYSTEM_ADMINS_ONLY);
    }
}