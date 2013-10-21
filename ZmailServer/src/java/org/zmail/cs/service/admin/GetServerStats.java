/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.stats.ZmailPerf;
import org.zmail.soap.ZmailSoapContext;

public class GetServerStats extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        
        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_getServerStats);
        
        // Assemble list of requested stat names.
        List<Element> eStats = request.listElements(AdminConstants.E_STAT);
        Set<String> requestedNames = new HashSet<String>();
        for (int i = 0; i < eStats.size(); i++) {
            requestedNames.add(eStats.get(i).getAttribute(AdminConstants.A_NAME));
        }
        
        // Get latest values.
        Map<String, Object> allStats = ZmailPerf.getStats();
        Map<String, Object> returnedStats = new TreeMap<String, Object>();
        boolean returnAllStats = (requestedNames.size() == 0);
        
        for (String name : allStats.keySet()) {
            if (returnAllStats || requestedNames.contains(name)) {
                returnedStats.put(name, allStats.get(name));
                requestedNames.remove(name);
            }
        }
        
        if (requestedNames.size() != 0) {
            StringBuilder buf = new StringBuilder("Invalid stat name");
            if (requestedNames.size() > 1) {
                buf.append("s");
            }
            buf.append(": ").append(StringUtil.join(", ", requestedNames));
            throw ServiceException.FAILURE(buf.toString(), null);
        }
        
        // Send response.
        Element response = zsc.createElement(AdminConstants.GET_SERVER_STATS_RESPONSE);
        for (String name : returnedStats.keySet()) {
            String stringVal = toString(returnedStats.get(name));
            Element eStat = response.addElement(AdminConstants.E_STAT)
                .addAttribute(AdminConstants.A_NAME, name)
                .setText(stringVal);
            
            String description = ZmailPerf.getDescription(name);
            if (description != null) {
                eStat.addAttribute(AdminConstants.A_DESCRIPTION, description);
            }
        }
        
        return response;
    }

    private static String toString(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double || value instanceof Float) {
            return String.format("%.2f", value);
        } else {
            return value.toString();
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getServerStats);
    }
}
