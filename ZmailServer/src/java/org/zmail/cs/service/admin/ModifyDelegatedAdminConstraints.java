/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key.CosBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.AttributeConstraint;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.soap.ZmailSoapContext;

public class ModifyDelegatedAdminConstraints extends AdminDocumentHandler {

    private static final String CONSTRAINT_ATTR = Provisioning.A_zmailConstraint;
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        Entry entry = GetDelegatedAdminConstraints.getEntry(request);
        
        AdminAccessControl.SetAttrsRight sar = new AdminAccessControl.SetAttrsRight();
        sar.addAttr(Provisioning.A_zmailConstraint);
        checkRight(zsc, context, entry, sar);
        
        AttributeManager am = AttributeManager.getInstance();
        List<AttributeConstraint> constraints = new ArrayList<AttributeConstraint>();
        for (Element a : request.listElements(AdminConstants.E_A)) {
            String attrName = a.getAttribute(AdminConstants.A_NAME);
            Element eConstraint = a.getElement(AdminConstants.E_CONSTRAINT);
            
            constraints.add(AttributeConstraint.fromXML(am, attrName, eConstraint));
        }
        
        AttributeConstraint.modifyConstraint(entry, constraints);
        
        // log it
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(CONSTRAINT_ATTR, entry.getMultiAttr(CONSTRAINT_ATTR, false));
        ZmailLog.security.info(ZmailLog.encodeAttrs(
                new String[] {"cmd", "ModifyDelegatedAdminConstraints","name", entry.getLabel()}, attrs));
        
        Element response = zsc.createElement(AdminConstants.MODIFY_DELEGATED_ADMIN_CONSTRAINTS_RESPONSE);
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("Need set attr right on attribute " + CONSTRAINT_ATTR);
    }

}
