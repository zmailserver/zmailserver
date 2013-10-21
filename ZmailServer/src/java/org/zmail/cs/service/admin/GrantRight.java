/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.common.account.Key.GranteeBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.GranteeType;
import org.zmail.cs.account.accesscontrol.RightCommand;
import org.zmail.cs.account.accesscontrol.RightModifier;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.type.TargetBy;

public class GrantRight extends RightDocumentHandler {

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Element eTarget = request.getElement(AdminConstants.E_TARGET);
        String targetType = eTarget.getAttribute(AdminConstants.A_TYPE);
        TargetBy targetBy = null;
        String target = null;
        if (TargetType.fromCode(targetType).needsTargetIdentity()) {
            targetBy = TargetBy.fromString(eTarget.getAttribute(AdminConstants.A_BY));
            target = eTarget.getText();
        }
            
        Element eGrantee = request.getElement(AdminConstants.E_GRANTEE);
        String granteeType = eGrantee.getAttribute(AdminConstants.A_TYPE);
        Key.GranteeBy granteeBy = null;
        String grantee = null;
        String secret = null;
        if (GranteeType.fromCode(granteeType).needsGranteeIdentity()) {
            granteeBy = Key.GranteeBy.fromString(eGrantee.getAttribute(AdminConstants.A_BY));
            grantee = eGrantee.getText();
            secret = eGrantee.getAttribute(AdminConstants.A_SECRET, null);
        }
        
        Element eRight = request.getElement(AdminConstants.E_RIGHT);
        String right = eRight.getText();
        
        RightModifier rightModifier = getRightModifier(eRight);
        
        // right checking is done in RightCommand
        
        RightCommand.grantRight(Provisioning.getInstance(),
                                getAuthenticatedAccount(zsc),
                                targetType, targetBy, target,
                                granteeType, granteeBy, grantee, secret,
                                right, rightModifier);
        
        Element response = zsc.createElement(AdminConstants.GRANT_RIGHT_RESPONSE);
        return response;
    }

    static RightModifier getRightModifier(Element eRight) throws ServiceException {
        boolean deny = eRight.getAttributeBool(AdminConstants.A_DENY, false);
        boolean canDelegate = eRight.getAttributeBool(AdminConstants.A_CAN_DELEGATE, false);
        boolean disinheritSubGroups = eRight.getAttributeBool(AdminConstants.A_DISINHERIT_SUB_GROUPS, false);
        boolean subDomain = eRight.getAttributeBool(AdminConstants.A_SUB_DOMAIN, false);
        
        int numModifiers = 0;
        if (deny) {
            numModifiers++;
        }
        if (canDelegate) {
            numModifiers++;
        }
        if (disinheritSubGroups) {
            numModifiers++;
        }
        if (subDomain) {
            numModifiers++;
        }
        
        if (numModifiers > 1) {
            throw ServiceException.INVALID_REQUEST("can only have one modifier", null);
        }
        
        RightModifier rightModifier = null;
        if (deny) {
            rightModifier = RightModifier.RM_DENY;
        } else if (canDelegate) {
            rightModifier = RightModifier.RM_CAN_DELEGATE;
        } else if (disinheritSubGroups) {
            rightModifier = RightModifier.RM_DISINHERIT_SUB_GROUPS;
        } else if (subDomain) {
            rightModifier = RightModifier.RM_SUBDOMAIN;
        }
        return rightModifier;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add("Grantor must have the same or more rights on the same target or " + 
                "on a larger target set.");
    }

}
