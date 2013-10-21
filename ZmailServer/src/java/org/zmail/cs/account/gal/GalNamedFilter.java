/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.account.gal;

public class GalNamedFilter {
    /*
     * GAL autocomplete
     */
    private static final String ZIMBRA_ACCOUNT_AUTO_COMPLETE = "zmailAccountAutoComplete";
    private static final String ZIMBRA_CALENDAR_RESOURCE_AUTO_COMPLETE = "zmailResourceAutoComplete"; 
    private static final String ZIMBRA_GROUP_AUTO_COMPLETE = "zmailGroupAutoComplete"; 
    
    /*
     * GAL search
     */
    private static final String ZIMBRA_ACCOUNTS = "zmailAccounts";
    private static final String ZIMBRA_CALENDAR_RESOURCES = "zmailResources";
    private static final String ZIMBRA_GROUPS = "zmailGroups";
    
    /*
     * GAL sync
     */
    private static final String ZIMBRA_ACCOUNT_SYNC = "zmailAccountSync";
    private static final String ZIMBRA_CALENDAR_RESOURCE_SYNC = "zmailResourceSync"; 
    private static final String ZIMBRA_GROUP_SYNC = "zmailGroupSync"; 
    
    public static String getZmailCalendarResourceFilter(GalOp galOp) {
        String filter = null;
        
        if (galOp == GalOp.autocomplete)
            filter = GalNamedFilter.ZIMBRA_CALENDAR_RESOURCE_AUTO_COMPLETE;
        else if (galOp == GalOp.search)
            filter = GalNamedFilter.ZIMBRA_CALENDAR_RESOURCES;
        else if (galOp == GalOp.sync)
            filter = GalNamedFilter.ZIMBRA_CALENDAR_RESOURCE_SYNC;
        
        return filter;
    }
    
    public static String getZmailGroupFilter(GalOp galOp) {
        String filter = null;
        
        if (galOp == GalOp.autocomplete)
            filter = GalNamedFilter.ZIMBRA_GROUP_AUTO_COMPLETE;
        else if (galOp == GalOp.search)
            filter = GalNamedFilter.ZIMBRA_GROUPS;
        else if (galOp == GalOp.sync)
            filter = GalNamedFilter.ZIMBRA_GROUP_SYNC;
        
        return filter;
    }
    
    public static String getZmailAcountFilter(GalOp galOp) {
        String filter = null;
        
        if (galOp == GalOp.autocomplete)
            filter = GalNamedFilter.ZIMBRA_ACCOUNT_AUTO_COMPLETE;
        else if (galOp == GalOp.search)
            filter = GalNamedFilter.ZIMBRA_ACCOUNTS;
        else if (galOp == GalOp.sync)
            filter = GalNamedFilter.ZIMBRA_ACCOUNT_SYNC;
        
        return filter;
    }
}
