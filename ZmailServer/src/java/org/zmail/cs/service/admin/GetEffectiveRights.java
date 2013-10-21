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
import org.zmail.common.util.Pair;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.GranteeType;
import org.zmail.cs.account.accesscontrol.RightCommand;
import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.type.TargetBy;

public class GetEffectiveRights  extends RightDocumentHandler {
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Pair<Boolean, Boolean> expandAttrs = parseExpandAttrs(request);
        boolean expandSetAttrs = expandAttrs.getFirst();
        boolean expandGetAttrs = expandAttrs.getSecond();
        
        Element eTarget = request.getElement(AdminConstants.E_TARGET);
        String targetType = eTarget.getAttribute(AdminConstants.A_TYPE);
        TargetBy targetBy = null;
        String target = null;
        if (TargetType.fromCode(targetType).needsTargetIdentity()) {
            targetBy = TargetBy.fromString(eTarget.getAttribute(AdminConstants.A_BY));
            target = eTarget.getText();
        }
            
        Element eGrantee = request.getOptionalElement(AdminConstants.E_GRANTEE);
        Key.GranteeBy granteeBy;
        String grantee;
        if (eGrantee != null) {
            String granteeType = eGrantee.getAttribute(AdminConstants.A_TYPE, GranteeType.GT_USER.getCode());
            if (GranteeType.fromCode(granteeType) != GranteeType.GT_USER)
                throw ServiceException.INVALID_REQUEST("invalid grantee type " + granteeType, null);
            granteeBy = Key.GranteeBy.fromString(eGrantee.getAttribute(AdminConstants.A_BY));
            grantee = eGrantee.getText();
        } else {
            granteeBy = Key.GranteeBy.id;
            grantee = zsc.getRequestedAccountId();  
        }
        
        if (!grantee.equals(zsc.getAuthtokenAccountId())) {
            checkCheckRightRight(zsc, GranteeType.GT_USER, granteeBy, grantee);
        }
        
        RightCommand.EffectiveRights er = RightCommand.getEffectiveRights(
                Provisioning.getInstance(),
                targetType, targetBy, target,
                granteeBy, grantee,
                expandSetAttrs, expandGetAttrs);
        
        Element resp = zsc.createElement(AdminConstants.GET_EFFECTIVE_RIGHTS_RESPONSE);
        er.toXML_getEffectiveRights(resp);
        return resp;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_checkRightUsr);
    }
}
