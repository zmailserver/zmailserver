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
import java.util.TreeSet;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.AttributeInfo;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.FileGenUtil;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.soap.ZmailSoapContext;

public class GetAttributeInfo extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        String[] attrs = null;
        String attrsRequested = request.getAttribute(AdminConstants.A_ATTRS, null);
        if (attrsRequested != null) {
            attrs = attrsRequested.split(",");
        }
        
        String[] entryTypes = null;
        String entryTypesRequested = request.getAttribute(AdminConstants.A_ENTRY_TYPES, null);
        if (entryTypesRequested != null) {
            entryTypes = entryTypesRequested.split(",");
        }
        
        if (attrs != null && entryTypes != null) {
            throw ServiceException.INVALID_REQUEST("only one of " + 
                    AdminConstants.A_ATTRS + " or " + AdminConstants.A_ENTRY_TYPES + 
                    " can be specified", null);
        }
        
        AttributeManager attrMgr = AttributeManager.getInstance();
        
        Element response = zsc.createElement(AdminConstants.GET_ATTRIBUTE_INFO_RESPONSE);
        
        if (attrs != null) {
            for (String attr : attrs) {
                encodeAttr(response, attrMgr, attr.trim());
            }
        } else if (entryTypes != null) {
            for (String entry : entryTypes) {
                AttributeClass attrClass = AttributeClass.fromString(entry.trim());
                TreeSet<String> attrsOnEntry = new TreeSet<String>(attrMgr.getAllAttrsInClass(attrClass));
                for (String attr : attrsOnEntry) {
                    encodeAttr(response, attrMgr, attr);
                }
            }
        } else {
            // AttributeManager.getAllAttrs() only contains attrs with AttributeInfo,
            // not extension attrs
            // attrs = new TreeSet<String>(am.getAllAttrs());
            //
            // attr sets for each AttributeClass contain attrs in the extensions, use them
            TreeSet<String> allAttrs = new TreeSet<String>();
            for (AttributeClass ac : AttributeClass.values()) {
                allAttrs.addAll(attrMgr.getAllAttrsInClass(ac));
            }
            
            for (String attr : allAttrs) {
                encodeAttr(response, attrMgr, attr);
            }
        }
       
        return response;
    }
    
    private void encodeAttr(Element response, AttributeManager attrMgr, String attr) {
        AttributeInfo attrInfo = attrMgr.getAttributeInfo(attr);
        
        if (attrInfo == null) {
            ZmailLog.account.info("no attribte info for " + attr);
            return;
        }
        
        String desc = attrInfo.getDescription();
        String deSpacedDesc = FileGenUtil.wrapComments((desc==null?"":desc), Integer.MAX_VALUE, "");
        
        Element eAttr = response.addElement(AdminConstants.E_A);
        eAttr.addAttribute(AdminConstants.A_N, attr);
        eAttr.addAttribute(AdminConstants.A_DESC, deSpacedDesc);
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        notes.add(AdminRightCheckPoint.Notes.ALLOW_ALL_ADMINS);
    }

}
