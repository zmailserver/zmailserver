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

package org.zmail.client;

import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;

import com.google.common.collect.Iterables;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.calendar.TZIDMapper;

public class ZPrefs {

    private Map<String, Collection<String>> mPrefs;

    public ZPrefs(Map<String, Collection<String>> prefs) {
        mPrefs = prefs;
    }
    
    /**
     * @param name name of pref to get
     * @return null if unset, or first value in list
     */
    public String get(String name) {
        Collection<String> values = mPrefs.get(name);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return Iterables.get(values, 0);
    }

    public boolean getBool(String name) {
        return ProvisioningConstants.TRUE.equals(get(name));
    }

    public long getLong(String name) {
        String v = get(name);
        try {
            return v == null ? -1 : Long.parseLong(v);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Map<String, Collection<String>> getPrefs() { return mPrefs; }

    public String getAppleiCalDelegationEnabled() { return get(ZAttrProvisioning.A_zmailPrefAppleIcalDelegationEnabled); }
    
    public String getComposeFormat() { return get(ZAttrProvisioning.A_zmailPrefComposeFormat); }

    public String getHtmlEditorDefaultFontFamily() { return get(ZAttrProvisioning.A_zmailPrefHtmlEditorDefaultFontFamily); }

    public String getHtmlEditorDefaultFontSize() { return get(ZAttrProvisioning.A_zmailPrefHtmlEditorDefaultFontSize); }

    public String getHtmlEditorDefaultFontColor() { return get(ZAttrProvisioning.A_zmailPrefHtmlEditorDefaultFontColor); }

    public String getLocale() { return get(ZAttrProvisioning.A_zmailPrefLocale); }

    public String getYintl() { return get(ZAttrProvisioning.A_zmailPrefLocale); }

    public boolean getUseTimeZoneListInCalendar() { return getBool(ZAttrProvisioning.A_zmailPrefUseTimeZoneListInCalendar); }

    public boolean getReadingPaneEnabled() { return getBool(ZAttrProvisioning.A_zmailPrefReadingPaneEnabled); }

    public String getReadingPaneLocation() { return get(ZAttrProvisioning.A_zmailPrefReadingPaneLocation); }

    public boolean getMailSignatureEnabled() { return getBool(ZAttrProvisioning.A_zmailPrefMailSignatureEnabled); }

    public boolean getIncludeSpamInSearch() { return getBool(ZAttrProvisioning.A_zmailPrefIncludeSpamInSearch); }

    public boolean getIncludeTrashInSearch() { return getBool(ZAttrProvisioning.A_zmailPrefIncludeTrashInSearch); }

    public boolean getShowSearchString() { return getBool(ZAttrProvisioning.A_zmailPrefShowSearchString); }

    public boolean getShowFragments() { return getBool(ZAttrProvisioning.A_zmailPrefShowFragments); }

    public boolean getSaveToSent() { return getBool(ZAttrProvisioning.A_zmailPrefSaveToSent); }

    public boolean getOutOfOfficeReplyEnabled() { return getBool(ZAttrProvisioning.A_zmailPrefOutOfOfficeReplyEnabled); }

    public boolean getNewMailNotificationsEnabled() { return getBool(ZAttrProvisioning.A_zmailPrefNewMailNotificationEnabled); }

    public boolean getMailLocalDeliveryDisabled() { return getBool(ZAttrProvisioning.A_zmailPrefMailLocalDeliveryDisabled); }

    public boolean getMessageViewHtmlPreferred() { return getBool(ZAttrProvisioning.A_zmailPrefMessageViewHtmlPreferred); }

    public boolean getAutoAddAddressEnabled() { return getBool(ZAttrProvisioning.A_zmailPrefAutoAddAddressEnabled); }

    public String getShortcuts() { return get(ZAttrProvisioning.A_zmailPrefShortcuts); }

    public boolean getUseKeyboardShortcuts() { return getBool(ZAttrProvisioning.A_zmailPrefUseKeyboardShortcuts); }

    public boolean getSignatureEnabled() { return getBool(ZAttrProvisioning.A_zmailPrefMailSignatureEnabled); }

    public String getClientType() { return get(ZAttrProvisioning.A_zmailPrefClientType); }
    public boolean getIsAdvancedClient() { return "advanced".equals(getClientType()); }
    public boolean getIsStandardClient() { return "standard".equals(getClientType()); }
    
    public String getSignatureStyle() { return get(ZAttrProvisioning.A_zmailPrefMailSignatureStyle); }
    public boolean getSignatureStyleTop() { return "outlook".equals(getSignatureStyle()); }
    public boolean getSignatureStyleBottom() { return "internet".equals(getSignatureStyle()); }

    public String getGroupMailBy() { return get(ZAttrProvisioning.A_zmailPrefGroupMailBy); }

    public boolean getGroupByConversation() {
        String gb = getGroupMailBy();
        return "conversation".equals(gb);
    }

    public boolean getGroupByMessage() {
        String gb = getGroupMailBy();
        return gb == null || "message".equals(gb);
    }


    public String getSkin() { return get(ZAttrProvisioning.A_zmailPrefSkin); }
    
    public String getDedupeMessagesSentToSelf() { return get(ZAttrProvisioning.A_zmailPrefDedupeMessagesSentToSelf); }

    public String getMailInitialSearch() { return get(ZAttrProvisioning.A_zmailPrefMailInitialSearch); }

    public String getNewMailNotificationAddress() { return get(ZAttrProvisioning.A_zmailPrefNewMailNotificationAddress); }

    public String getMailForwardingAddress() { return get(ZAttrProvisioning.A_zmailPrefMailForwardingAddress); }

    public String getOutOfOfficeReply() { return get(ZAttrProvisioning.A_zmailPrefOutOfOfficeReply); }

	public String getOutOfOfficeFromDate() { return get(ZAttrProvisioning.A_zmailPrefOutOfOfficeFromDate); }

	public String getOutOfOfficeUntilDate() { return get(ZAttrProvisioning.A_zmailPrefOutOfOfficeUntilDate); }

    public String getMailSignature() { return get(ZAttrProvisioning.A_zmailPrefMailSignature); }

    public long getMailItemsPerPage() { return getLong(ZAttrProvisioning.A_zmailPrefMailItemsPerPage); }

    public long getContactsPerPage() { return getLong(ZAttrProvisioning.A_zmailPrefContactsPerPage); }

	public long getVoiceItemsPerPage() { return getLong(ZAttrProvisioning.A_zmailPrefVoiceItemsPerPage); }

    public long getCalendarFirstDayOfWeek() { return getLong(ZAttrProvisioning.A_zmailPrefCalendarFirstDayOfWeek); }

    public String getCalendarWorkingHours() { return get(ZAttrProvisioning.A_zmailPrefCalendarWorkingHours);}

    public String getInboxUnreadLifetime() { return get(ZAttrProvisioning.A_zmailPrefInboxUnreadLifetime); }

    public String getInboxReadLifetime() { return get(ZAttrProvisioning.A_zmailPrefInboxReadLifetime); }

    public String getSentLifetime() { return get(ZAttrProvisioning.A_zmailPrefSentLifetime); }

    public String getJunkLifetime() { return get(ZAttrProvisioning.A_zmailPrefJunkLifetime); }

    public String getTrashLifetime() { return get(ZAttrProvisioning.A_zmailPrefTrashLifetime); }

    public boolean getDisplayExternalImages() { return getBool(ZAttrProvisioning.A_zmailPrefDisplayExternalImages); }

    public long getCalendarDayHourStart() {
        long hour = getLong(ZAttrProvisioning.A_zmailPrefCalendarDayHourStart);
        return hour == -1 ? 8 : hour;
    }

    public long getCalendarDayHourEnd() {
         long hour = getLong(ZAttrProvisioning.A_zmailPrefCalendarDayHourEnd);
        return hour == -1 ? 18 : hour;
    }

    public String getCalendarInitialView() { return get(ZAttrProvisioning.A_zmailPrefCalendarInitialView); }

    public String getTimeZoneId() { return get(ZAttrProvisioning.A_zmailPrefTimeZoneId); }

    public String getTimeZoneCanonicalId() { return TZIDMapper.canonicalize(get(ZAttrProvisioning.A_zmailPrefTimeZoneId)); }

    public String getDefaultPrintFontSize() {return get(ZAttrProvisioning.A_zmailPrefDefaultPrintFontSize);}

    public String getFolderTreeOpen() {return get(ZAttrProvisioning.A_zmailPrefFolderTreeOpen);}

    public String getSearchTreeOpen() {return get(ZAttrProvisioning.A_zmailPrefSearchTreeOpen);}

    public String getTagTreeOpen() {return get(ZAttrProvisioning.A_zmailPrefTagTreeOpen);}

    private TimeZone mCachedTimeZone;
    private String mCachedTimeZoneId;

    public synchronized TimeZone getTimeZone() {
        if (mCachedTimeZone == null || (mCachedTimeZoneId != null && !mCachedTimeZoneId.equals(getTimeZoneId()))) {
            mCachedTimeZoneId = getTimeZoneId();
            mCachedTimeZone  = (mCachedTimeZoneId == null) ? null :
                    TimeZone.getTimeZone(TZIDMapper.canonicalize(mCachedTimeZoneId));
            if (mCachedTimeZone == null)
                mCachedTimeZone = TimeZone.getDefault();
        }
        return mCachedTimeZone;
    }

    public String getReplyIncludeOriginalText() { return get(ZAttrProvisioning.A_zmailPrefReplyIncludeOriginalText); }

    public boolean getReplyIncludeAsAttachment() { return "includeAsAttachment".equals(getReplyIncludeOriginalText()); }
    public boolean getReplyIncludeBody() { return "includeBody".equals(getReplyIncludeOriginalText()) || "includeBodyAndHeaders".equals(getReplyIncludeOriginalText()); }
    public boolean getReplyIncludeBodyWithPrefx() { return "includeBodyWithPrefix".equals(getReplyIncludeOriginalText()) || "includeBodyAndHeadersWithPrefix".equals(getReplyIncludeOriginalText()); }
    public boolean getReplyIncludeNone() { return "includeNone".equals(getReplyIncludeOriginalText()); }
    public boolean getReplyIncludeSmart() { return "includeSmart".equals(getReplyIncludeOriginalText()); }
    
    public String getForwardIncludeOriginalText() { return get(ZAttrProvisioning.A_zmailPrefForwardIncludeOriginalText); }
    public boolean getForwardIncludeAsAttachment() { return "includeAsAttachment".equals(getForwardIncludeOriginalText()); }
    public boolean getForwardIncludeBody() { return "includeBody".equals(getForwardIncludeOriginalText()) || "includeBodyAndHeaders".equals(getForwardIncludeOriginalText()); }
    public boolean getForwardIncludeBodyWithPrefx() { return "includeBodyWithPrefix".equals(getForwardIncludeOriginalText()) || "includeBodyAndHeadersWithPrefix".equals(getForwardIncludeOriginalText()); }
    
    public String getForwardReplyFormat() { return get(ZAttrProvisioning.A_zmailPrefForwardReplyFormat); }
    public boolean getForwardReplyTextFormat() { return "text".equals(getForwardReplyFormat()); }
    public boolean getForwardReplyHtmlFormat() { return "html".equals(getForwardReplyFormat()); }
    public boolean getForwardReplySameFormat() { return "same".equals(getForwardReplyFormat()); }
    public boolean getForwardReplyInOriginalFormat() { return getBool(ZAttrProvisioning.A_zmailPrefForwardReplyInOriginalFormat); }


    public String getForwardReplyPrefixChar() { return get(ZAttrProvisioning.A_zmailPrefForwardReplyPrefixChar); }

    public String getCalendarReminderDuration1() { return get(ZAttrProvisioning.A_zmailPrefCalendarReminderDuration1); }
    public String getCalendarReminderDuration2() { return get(ZAttrProvisioning.A_zmailPrefCalendarReminderDuration2); }
    public String getCalendarReminderEmail() { return get(ZAttrProvisioning.A_zmailPrefCalendarReminderEmail); }
    public boolean getCalendarReminderSendEmail() { return getBool(ZAttrProvisioning.A_zmailPrefCalendarReminderSendEmail); }
    public boolean getCalendarReminderMobile() { return getBool(ZAttrProvisioning.A_zmailPrefCalendarReminderMobile); }
    public boolean getCalendarReminderYMessenger() { return getBool(ZAttrProvisioning.A_zmailPrefCalendarReminderYMessenger); }
    public boolean getCalendarShowDeclinedMeetings() { return getBool(ZAttrProvisioning.A_zmailPrefCalendarShowDeclinedMeetings); }


	public String getPop3DownloadSince() { return get(ZAttrProvisioning.A_zmailPrefPop3DownloadSince); }
}
