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
package org.zmail.cs.fb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.AccessManager;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.accesscontrol.Rights.User;
import org.zmail.cs.fb.FreeBusy.FBInstance;
import org.zmail.cs.fb.FreeBusy.Interval;
import org.zmail.cs.fb.FreeBusy.IntervalList;
import org.zmail.cs.mailbox.Appointment;
import org.zmail.cs.mailbox.CalendarItem;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.calendar.IcalXmlStrMap;
import org.zmail.cs.mailbox.calendar.cache.CalendarItemData;
import org.zmail.cs.mailbox.calendar.cache.FullInstanceData;
import org.zmail.cs.mailbox.calendar.cache.InstanceData;
import org.zmail.cs.mailbox.calendar.cache.CalSummaryCache.CalendarDataResult;

public class LocalFreeBusyProvider {

    /**
     *
     * @param mbox
     * @param start
     * @param end
     * @param folder folder to run free/busy search on; FreeBusyQuery.CALENDAR_FOLDER_ALL (-1) for all folders
     * @param exAppt appointment to exclude; calculate free/busy assuming
     *               the specified appointment wasn't there
     * @return
     * @throws ServiceException
     */
    public static FreeBusy getFreeBusyList(
            Account authAcct, boolean asAdmin, Mailbox mbox, String name, long start, long end, int folder, Appointment exAppt)
    throws ServiceException {
        AccessManager accessMgr = AccessManager.getInstance();
        boolean accountAceAllowed = accessMgr.canDo(authAcct, mbox.getAccount(), User.R_viewFreeBusy, asAdmin);
        int numAllowedFolders = 0;

        int exApptId = exAppt == null ? -1 : exAppt.getId();

        IntervalList intervals = new IntervalList(start, end);

        List<CalendarDataResult> calDataResultList;
        if (folder == FreeBusyQuery.CALENDAR_FOLDER_ALL) {
            calDataResultList = mbox.getAllCalendarsSummaryForRange(null, MailItem.Type.APPOINTMENT, start, end);
        } else {
            calDataResultList = new ArrayList<CalendarDataResult>(1);
            calDataResultList.add(mbox.getCalendarSummaryForRange(null, folder, MailItem.Type.APPOINTMENT, start, end));
        }
        for (CalendarDataResult result : calDataResultList) {
            int folderId = result.data.getFolderId();
            Folder f = mbox.getFolderById(null, folderId);
            if ((f.getFlagBitmask() & Flag.BITMASK_EXCLUDE_FREEBUSY) != 0)
                continue;
            // Free/busy must be allowed by folder or at account-level.
            boolean folderFBAllowed = CalendarItem.allowFreeBusyAccess(f, authAcct, asAdmin);
            if (folderFBAllowed)
                ++numAllowedFolders;
            if (!folderFBAllowed && !accountAceAllowed)
                continue;
            for (Iterator<CalendarItemData> iter = result.data.calendarItemIterator(); iter.hasNext(); ) {
                CalendarItemData appt = iter.next();
                int apptId = appt.getCalItemId();
                if (apptId == exApptId)
                    continue;
                FullInstanceData defaultInstance = appt.getDefaultData();
                if (defaultInstance == null)
                    continue;
                boolean isTransparent = false;
                String transp = defaultInstance.getTransparency();
                isTransparent = IcalXmlStrMap.TRANSP_TRANSPARENT.equals(transp);
                long defaultDuration = 0;
                if (defaultInstance.getDuration() != null)
                    defaultDuration = defaultInstance.getDuration().longValue();
                String defaultFreeBusy = defaultInstance.getFreeBusyActual();
                for (Iterator<InstanceData> instIter = appt.instanceIterator(); instIter.hasNext(); ) {
                    InstanceData instance = instIter.next();
                    long instStart = instance.getDtStart() != null ? instance.getDtStart().longValue() : 0;
                    // Skip instances that are outside the time range but were returned due to alarm being in range.
                    if (instStart >= end)
                        continue;
                    long dur = defaultDuration;
                    if (instance.getDuration() != null)
                        dur = instance.getDuration().longValue();
                    if (dur <= 0)  // Only consider instances with non-zero, positive duration.
                        continue;
                    long instEnd = instStart + dur;

                    long recurIdDt = 0;
                    // Skip if instance is TRANSPARENT to free/busy searches.
                    if (instance instanceof FullInstanceData) {
                        FullInstanceData fullInst = (FullInstanceData) instance;
                        String transpInst = fullInst.getTransparency();
                        recurIdDt = fullInst.getRecurrenceId();
                        if (IcalXmlStrMap.TRANSP_TRANSPARENT.equals(transpInst))
                            continue;
                    } else if (isTransparent) {
                        continue;
                    }

                    String freeBusy = instance.getFreeBusyActual();
                    if (freeBusy == null)
                        freeBusy = defaultFreeBusy;
                    if (!IcalXmlStrMap.FBTYPE_FREE.equals(freeBusy)) {
                        FBInstance fbInst = new FBInstance(freeBusy, instStart, instEnd, apptId, recurIdDt);
                        Interval ival = new Interval(instStart, instEnd, freeBusy, fbInst);
                        intervals.addInterval(ival);
                    }
                }
            }
        }
        if (!accountAceAllowed && numAllowedFolders == 0 && !LC.freebusy_disable_nodata_status.booleanValue()) {
            Interval nodata = new Interval(start, end, IcalXmlStrMap.FBTYPE_NODATA);
            intervals.addInterval(nodata);
        }
        return new FreeBusy(name, intervals, start, end);
    }

    public static void main(String[] args) {
        IntervalList l = new IntervalList(0, 100);
        Interval toAdd;

        System.out.println("List: "+ l.toString());

        toAdd = new Interval(50, 60, IcalXmlStrMap.FBTYPE_BUSY);
        l.addInterval(toAdd);
        System.out.println("Added: "+toAdd+l.toString());
        toAdd = new Interval(10, 20, IcalXmlStrMap.FBTYPE_BUSY_TENTATIVE);
        l.addInterval(toAdd);
        System.out.println("Added: "+toAdd+l.toString());
        toAdd = new Interval(20, 30, IcalXmlStrMap.FBTYPE_BUSY_UNAVAILABLE);
        l.addInterval(toAdd);
        System.out.println("Added: "+toAdd+l.toString());
        toAdd = new Interval(15, 35, IcalXmlStrMap.FBTYPE_BUSY);
        l.addInterval(toAdd);
        System.out.println("Added: "+toAdd+l.toString());

        try {
            Mailbox mbox = MailboxManager.getInstance().getMailboxById(1);
            FreeBusy fb = getFreeBusyList(mbox.getAccount(), false, mbox, mbox.getAccount().getName(), 0, Long.MAX_VALUE, FreeBusyQuery.CALENDAR_FOLDER_ALL, null);
            System.out.println(fb.toString());
        } catch (ServiceException e){
            System.out.println("EXCEPTION: "+e);
            e.printStackTrace();
        }

    }
}
