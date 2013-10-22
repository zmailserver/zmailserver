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

package com.zimbra.soap.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public interface InviteInfoInterface {
    public InviteInfoInterface create(String calItemType);
    public String getCalItemType();
    public void setTimezoneInterfaces(Iterable<CalTZInfoInterface> timezones);
    public void addTimezoneInterface(CalTZInfoInterface timezone);
    public void setInviteComponentInterface(
            InviteComponentInterface inviteComponent);
    public void setCalendarReplyInterfaces(
            Iterable<CalendarReplyInterface> calendarReplies);
    public void addCalendarReplyInterface(CalendarReplyInterface calendarReply);
    public List<CalTZInfoInterface> getTimezoneInterfaces();
    public InviteComponentInterface getInviteComponentInterface();
    public List<CalendarReplyInterface> getCalendarReplyInterfaces();
}
