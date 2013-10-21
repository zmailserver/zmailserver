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
package org.zmail.cs.account.ldap.upgrade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;

public class BUG_42828 extends UpgradeOp {

    @Override
    void doUpgrade() throws ServiceException {
        upgrade_zmailGalLdapAttrMap();
        upgrade_zmailContactHiddenAttributes();
    }
    
    void upgrade_zmailGalLdapAttrMap() throws ServiceException {
        
        String attrName = Provisioning.A_zmailGalLdapAttrMap;
        
        Config config = prov.getConfig();
        
        printer.println();
        printer.println("Checking " + config.getLabel() + " for " + attrName);
        
        String oldCalResType = "zmailCalResType=zmailCalResType";
        String newCalResType = "zmailCalResType,msExchResourceSearchProperties=zmailCalResType";
        
        String oldCalResLocationDisplayName = "zmailCalResLocationDisplayName=zmailCalResLocationDisplayName";
        String newCalResLocationDisplayName = "zmailCalResLocationDisplayName,displayName=zmailCalResLocationDisplayName";
        
        String oldMailForwardingAddress = "zmailMailForwardingAddress=zmailMailForwardingAddress";
        String newMailForwardingAddress = "zmailMailForwardingAddress=member";
        
        String zmailCalResBuilding = "zmailCalResBuilding=zmailCalResBuilding";
        String zmailCalResCapacity = "zmailCalResCapacity,msExchResourceCapacity=zmailCalResCapacity";
        String zmailCalResFloor = "zmailCalResFloor=zmailCalResFloor";
        String zmailCalResSite = "zmailCalResSite=zmailCalResSite";
        String zmailCalResContactEmail = "zmailCalResContactEmail=zmailCalResContactEmail";
        String zmailAccountCalendarUserType = "msExchResourceSearchProperties=zmailAccountCalendarUserType";
        
        String[] curValues = config.getMultiAttr(attrName);
        
        Map<String, Object> attrs = new HashMap<String, Object>(); 
        for (String curValue : curValues) {
            replaceIfNeeded(attrs, attrName, curValue, oldCalResType, newCalResType);
            replaceIfNeeded(attrs, attrName, curValue, oldCalResLocationDisplayName, newCalResLocationDisplayName);
            replaceIfNeeded(attrs, attrName, curValue, oldMailForwardingAddress, newMailForwardingAddress);
        }

        addValue(attrs, attrName, zmailCalResBuilding);
        addValue(attrs, attrName, zmailCalResCapacity);
        addValue(attrs, attrName, zmailCalResFloor);
        addValue(attrs, attrName, zmailCalResSite);
        addValue(attrs, attrName, zmailCalResContactEmail);
        addValue(attrs, attrName, zmailAccountCalendarUserType);
        
        if (attrs.size() > 0) {
            printer.println("Modifying " + attrName + " on " + config.getLabel());
            prov.modifyAttrs(config, attrs);
        }
    }

    private void upgrade_zmailContactHiddenAttributes(Entry entry, String entryName) throws ServiceException {
        
        String attrName = Provisioning.A_zmailContactHiddenAttributes;
        
        printer.println();
        printer.println("Checking " + entryName + " for " + attrName);
        
        String curValue = entry.getAttr(attrName);
        
        // remove zmailCalResType,zmailCalResLocationDisplayName,zmailCalResCapacity,zmailCalResContactEmail 
        // add member
        String newHiddenAttrs = "";
        if (curValue != null) {
            String[] curHiddenAttrs = curValue.split(",");
            boolean seenMember = false;
            boolean first = true;
            for (String hiddenAttr : curHiddenAttrs) {
                if (!Provisioning.A_zmailCalResType.equalsIgnoreCase(hiddenAttr) &&
                    !Provisioning.A_zmailCalResLocationDisplayName.equalsIgnoreCase(hiddenAttr) &&
                    !Provisioning.A_zmailCalResCapacity.equalsIgnoreCase(hiddenAttr) &&
                    !Provisioning.A_zmailCalResContactEmail.equalsIgnoreCase(hiddenAttr) &&
                    !Provisioning.A_zmailAccountCalendarUserType.equalsIgnoreCase(hiddenAttr)) {
                    if (!first)
                        newHiddenAttrs += ",";
                    else
                        first = false;
                    newHiddenAttrs += hiddenAttr;
                }
                
                if (ContactConstants.A_member.equalsIgnoreCase(hiddenAttr))
                    seenMember = true;
                        
            }
            
            // add member if not seen
            if (!seenMember) {
                if (!first)
                    newHiddenAttrs += ",";
                newHiddenAttrs += ContactConstants.A_member;
            }
        }
        if (newHiddenAttrs.length() == 0)
            newHiddenAttrs = "dn,zmailAccountCalendarUserType,vcardUID,vcardURL,vcardXProps" + ContactConstants.A_member;
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zmailContactHiddenAttributes, newHiddenAttrs);
        
        if (attrs.size() > 0) {
            printer.println("Modifying " + attrName + " on " + entryName + " from " + curValue + " to " + newHiddenAttrs);
            prov.modifyAttrs(entry, attrs);
        }
    }
    
    private void upgrade_zmailContactHiddenAttributes() throws ServiceException {
        Config config = prov.getConfig();
        upgrade_zmailContactHiddenAttributes(config, config.getLabel());
        
        List<Server> servers = prov.getAllServers();
        
        for (Server server : servers) {
            upgrade_zmailContactHiddenAttributes(server, "server " + server.getLabel());
        }
    }
    
    private void replaceIfNeeded(Map<String, Object> attrs, String attrName, String curValue, String oldValue, String newValue) {
        if (curValue.equalsIgnoreCase(oldValue)) {
            printer.println("    removing value: " + oldValue);
            printer.println("    adding value: " + newValue);
            
            StringUtil.addToMultiMap(attrs, "-" + attrName, oldValue);
            StringUtil.addToMultiMap(attrs, "+" + attrName, newValue);
        }
    }
    
    private void addValue(Map<String, Object> attrs, String attrName, String value) {
        printer.println("    adding value: " + value);
        StringUtil.addToMultiMap(attrs, "+" + attrName, value);
    }

}
