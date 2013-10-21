/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.account;

import java.util.Map;

/**
 * @author jhahm
 */
public class CalendarResource extends ZAttrCalendarResource {

    public CalendarResource(String name, String id, Map<String, Object> attrs, Map<String, Object> defaults, Provisioning prov) {
        super(name, id, attrs, defaults, prov);
    }
    
    @Override
    public EntryType getEntryType() {
        return EntryType.CALRESOURCE;
    }

    public String getResourceType() {
        return getAttr(Provisioning.A_zmailCalResType, "Location");
    }

    public boolean autoAcceptDecline() {
        return getBooleanAttr(
                Provisioning.A_zmailCalResAutoAcceptDecline, true);
    }

    public boolean autoDeclineIfBusy() {
        return getBooleanAttr(
                Provisioning.A_zmailCalResAutoDeclineIfBusy, true);
    }

    public boolean autoDeclineRecurring() {
        return getBooleanAttr(
                Provisioning.A_zmailCalResAutoDeclineRecurring, false);
    }

    public int getMaxNumConflictsAllowed() {
        return getIntAttr(Provisioning.A_zmailCalResMaxNumConflictsAllowed, 0);
    }

    public int getMaxPercentConflictsAllowed() {
        return getIntAttr(Provisioning.A_zmailCalResMaxPercentConflictsAllowed, 0);
    }

    public String getLocationDisplayName() {
        return getAttr(Provisioning.A_zmailCalResLocationDisplayName);
    }

    public String getSite() {
        return getAttr(Provisioning.A_zmailCalResSite);
    }

    public String getBuilding() {
        return getAttr(Provisioning.A_zmailCalResBuilding);
    }

    public String getFloor() {
        return getAttr(Provisioning.A_zmailCalResFloor);
    }

    public String getRoom() {
        return getAttr(Provisioning.A_zmailCalResRoom);
    }

    public int getCapacity() {
        return getIntAttr(Provisioning.A_zmailCalResCapacity, 0);
    }

    public String getContactName() {
        return getAttr(Provisioning.A_zmailCalResContactName);
    }

    public String getContactEmail(){
        return getAttr(Provisioning.A_zmailCalResContactEmail);
    }

    public String getContactPhone(){
        return getAttr(Provisioning.A_zmailCalResContactPhone);
    }    
    
}
