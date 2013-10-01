/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.mailbox.acl;

import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.account.Key;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Cos;
import com.zimbra.cs.account.DistributionList;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.ShareInfoData;
import com.zimbra.cs.mailbox.ACL;
import com.zimbra.cs.mailbox.Folder;
import com.zimbra.cs.mailbox.MailItem;

/**
 */
public class AclPushSerializer {

    private AclPushSerializer() {
    }

    public static String serialize(ShareInfoData shareInfoData) {
        return serialize(
                shareInfoData.getItemId(),
                shareInfoData.getItemUuid(),
                shareInfoData.getPath(),
                shareInfoData.getFolderDefaultViewCode(),
                shareInfoData.getType(),
                shareInfoData.getGranteeId(),
                shareInfoData.getGranteeName(),
                shareInfoData.getGranteeTypeCode(),
                shareInfoData.getRightsCode(),
                shareInfoData.getExpiry());
    }

    public static String serialize(MailItem item, ACL.Grant grant) {
        return serialize(
                item.getId(), item.getUuid(),
                (item instanceof Folder) ? ((Folder)item).getPath() : item.getName(),
                (item instanceof Folder) ? ((Folder)item).getDefaultView() : item.getType(),
                item.getType(),
                grant.getGranteeId(),
                grant.getGranteeName(),
                grant.getGranteeType(),
                grant.getGrantedRights(),
                grant.getEffectiveExpiry(item.getACL()));
    }

    public static String serialize(int itemId, String itemUuid, String path, MailItem.Type folderDefaultView, MailItem.Type type,
            String granteeId, String granteeName, byte granteeType, short rights, long expiry) {
        // Mailbox ACLs typically persist grantee id but not grantee name
        if (granteeName == null && granteeId != null) {
            try {
                switch (granteeType) {
                    case ACL.GRANTEE_USER:
                        Account granteeAcct = Provisioning.getInstance().get(Key.AccountBy.id, granteeId);
                        if (granteeAcct != null) {
                            granteeName = granteeAcct.getName();
                        }
                        break;
                    case ACL.GRANTEE_GROUP:
                        DistributionList granteeDL = Provisioning.getInstance().get(Key.DistributionListBy.id, granteeId);
                        if (granteeDL != null) {
                            granteeName = granteeDL.getName();
                        }
                        break;
                    case ACL.GRANTEE_DOMAIN:
                        Domain granteeDomain = Provisioning.getInstance().get(Key.DomainBy.id, granteeId);
                        if (granteeDomain != null) {
                            granteeName = granteeDomain.getName();
                        }
                        break;
                    case ACL.GRANTEE_COS:
                        Cos granteeCos = Provisioning.getInstance().get(Key.CosBy.id, granteeId);
                        if (granteeCos != null) {
                            granteeName = granteeCos.getName();
                        }
                        break;
                    default:
                }
            } catch (ServiceException e) {
                ZimbraLog.misc.info("Error in getting grantee name for grantee id %s", granteeId, e);
            }
        }
        if (granteeType == ACL.GRANTEE_GUEST && granteeId != null) {
            granteeId = granteeId.toLowerCase();
        }
        StringBuilder sb = new StringBuilder().
                append("granteeId:").append(granteeId).
                append(";granteeName:").append(granteeName).
                append(";granteeType:").append(ACL.typeToString(granteeType)).
                append(";folderId:").append(itemId).
                append(";folderUuid:").append(itemUuid).
                append(";folderPath:").append(path).
                append(";folderDefaultView:").append(folderDefaultView).
                append(";rights:").append(ACL.rightsToString(rights)).
                append(";type:").append(type);
        if (expiry != 0) {
            sb.append(";expiry:").append(expiry);
        }
        return sb.toString();
    }

    public static ShareInfoData deserialize(String sharedItemInfo) throws ServiceException {
        String[] parts = sharedItemInfo.split(";");
        Map<String, String> attrs = new HashMap<String, String>();
        for (String part : parts) {
            String x[] = part.split(":", 2);
            attrs.put(x[0], x[1]);
        }
        ShareInfoData obj = new ShareInfoData();

        String granteeId = attrs.get("granteeId");
        obj.setGranteeId("null".equals(granteeId) ? null : granteeId);
        String granteeName = attrs.get("granteeName");
        obj.setGranteeName("null".equals(granteeName) ? null : granteeName);
        obj.setGranteeType(ACL.stringToType(attrs.get("granteeType")));
        obj.setItemId(Integer.valueOf(attrs.get("folderId")));
        String uuid = attrs.get("folderUuid");
        obj.setItemUuid("null".equals(uuid) ? null : uuid);
        obj.setPath(attrs.get("folderPath"));
        obj.setFolderDefaultView(MailItem.Type.of(attrs.get("folderDefaultView")));
        obj.setRights(ACL.stringToRights(attrs.get("rights")));
        String type = attrs.get("type");
        if (type != null) {
            obj.setType(MailItem.Type.of(type));
        } else {
            obj.setType(MailItem.Type.FOLDER);
        }
        String expiry = attrs.get("expiry");
        if (expiry != null) {
            obj.setExpiry(Long.valueOf(expiry));
        }
        return obj;
    }
}
