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
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.mailbox.RetentionPolicyManager;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.CreateSystemRetentionPolicyRequest;
import org.zmail.soap.admin.message.CreateSystemRetentionPolicyResponse;
import org.zmail.soap.admin.type.CosSelector;
import org.zmail.soap.mail.type.Policy;

public class CreateSystemRetentionPolicy extends AdminDocumentHandler {

    static final String SYSTEM_RETENTION_POLICY_ATTR = Provisioning.A_zmailMailPurgeSystemPolicy;
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        CreateSystemRetentionPolicyRequest req = JaxbUtil.elementToJaxb(request);
        
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
        checkSetRight(entry, zsc, context, this);
        
        Policy keep = req.getKeepPolicy();
        Policy purge = req.getPurgePolicy();
        if (keep == null && purge == null) {
            throw ServiceException.INVALID_REQUEST("No keep or purge policy specified.", null); 
        }
        if (keep != null && purge != null) {
            throw ServiceException.INVALID_REQUEST("Cannot specify both keep and purge policy.", null);
        }
        
        Policy newPolicy;
        if (keep != null) {
            newPolicy = RetentionPolicyManager.getInstance().createSystemKeepPolicy(entry, keep.getName(), keep.getLifetime());
        } else {
            newPolicy = RetentionPolicyManager.getInstance().createSystemPurgePolicy(entry, purge.getName(), purge.getLifetime());
        }
        CreateSystemRetentionPolicyResponse res = new CreateSystemRetentionPolicyResponse(newPolicy);
        return JaxbUtil.jaxbToElement(res, zsc.getResponseProtocol().getFactory());
    }
    
    static void checkSetRight(Entry entry, ZmailSoapContext zsc, Map<String, Object> context,
            AdminDocumentHandler handler) 
    throws ServiceException {
        AdminAccessControl.SetAttrsRight sar = new AdminAccessControl.SetAttrsRight();
        sar.addAttr(CreateSystemRetentionPolicy.SYSTEM_RETENTION_POLICY_ATTR);
        handler.checkRight(zsc, context, entry, sar);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("Need set attr right on attribute " + SYSTEM_RETENTION_POLICY_ATTR);
    }
}
