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

public class GetAllEffectiveRights extends RightDocumentHandler {
    
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Pair<Boolean, Boolean> expandAttrs = parseExpandAttrs(request);
        boolean expandSetAttrs = expandAttrs.getFirst();
        boolean expandGetAttrs = expandAttrs.getSecond();
            
        Element eGrantee = request.getOptionalElement(AdminConstants.E_GRANTEE);
        String granteeType;
        Key.GranteeBy granteeBy;
        String grantee;
        if (eGrantee != null) {
            granteeType = eGrantee.getAttribute(AdminConstants.A_TYPE, GranteeType.GT_USER.getCode());
            granteeBy = Key.GranteeBy.fromString(eGrantee.getAttribute(AdminConstants.A_BY));
            grantee = eGrantee.getText();
        } else {
            granteeType = GranteeType.GT_USER.getCode();
            granteeBy = Key.GranteeBy.id;
            grantee = zsc.getRequestedAccountId();  
        }
        
        GranteeType gt = GranteeType.fromCode(granteeType);
        if (!grantee.equals(zsc.getAuthtokenAccountId())) {
            checkCheckRightRight(zsc, gt, granteeBy, grantee);
        }
        
        RightCommand.AllEffectiveRights aer = RightCommand.getAllEffectiveRights(
                Provisioning.getInstance(),
                granteeType, granteeBy, grantee, 
                expandSetAttrs, expandGetAttrs);
        
        Element resp = zsc.createElement(AdminConstants.GET_ALL_EFFECTIVE_RIGHTS_RESPONSE);
        aer.toXML(resp);
        return resp;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_checkRightUsr);
        relatedRights.add(Admin.R_checkRightGrp);
        
        notes.add("If grantee to check for is an account, needs the " + Admin.R_checkRightUsr.getName() + " right");
        notes.add("If grantee to check for is a group, needs the " + Admin.R_checkRightGrp.getName() + " right");
    }
}
