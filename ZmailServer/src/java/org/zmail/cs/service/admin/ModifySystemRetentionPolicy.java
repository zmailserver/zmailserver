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

package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.SystemUtil;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.mailbox.RetentionPolicyManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.DeleteSystemRetentionPolicyRequest;
import org.zmail.soap.admin.message.ModifySystemRetentionPolicyRequest;
import org.zmail.soap.admin.message.ModifySystemRetentionPolicyResponse;
import org.zmail.soap.admin.type.CosSelector;
import org.zmail.soap.mail.type.Policy;

public class ModifySystemRetentionPolicy extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        ModifySystemRetentionPolicyRequest req = JaxbUtil.elementToJaxb(request);
        Provisioning prov = Provisioning.getInstance();
        // assume default retention policy to be set in globalConfig (for backward compatibility)
        Entry entry = prov.getConfig();
        // check if cos is specified
        CosSelector cosSelector = req.getCos();
        if (cosSelector != null) {
            entry = prov.get(Key.CosBy.fromString(cosSelector.getBy().name()), cosSelector.getKey());
            if (entry == null)
                throw AccountServiceException.NO_SUCH_COS(cosSelector.getKey());
        }
        // check right
        CreateSystemRetentionPolicy.checkSetRight(entry, zsc, context, this);
        

        Policy p = req.getPolicy();
        if (p == null) {
            throw ServiceException.INVALID_REQUEST("policy not specified", null);
        }
        if (p.getId() == null) {
            throw ServiceException.INVALID_REQUEST("id not specified for policy", null);
        }
        
        RetentionPolicyManager mgr = RetentionPolicyManager.getInstance();
        String id = p.getId();
        Policy current = mgr.getPolicyById(entry, id);
        if (current == null) {
            throw ServiceException.INVALID_REQUEST("Could not find system retention policy with id " + id, null);
        }
        String name = SystemUtil.coalesce(p.getName(), current.getName());
        String lifetime = SystemUtil.coalesce(p.getLifetime(), current.getLifetime());
        Policy latest = mgr.modifySystemPolicy(entry, id, name, lifetime);
        ModifySystemRetentionPolicyResponse res = new ModifySystemRetentionPolicyResponse(latest);
        return JaxbUtil.jaxbToElement(res, zsc.getResponseProtocol().getFactory());
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("Need set attr right on attribute " + CreateSystemRetentionPolicy.SYSTEM_RETENTION_POLICY_ATTR);
    }

}
