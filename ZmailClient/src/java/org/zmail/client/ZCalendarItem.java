/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import org.zmail.client.event.ZModifyEvent;
import org.zmail.client.event.ZModifyMessageEvent;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;

public class ZCalendarItem implements ZItem, ToZJSONObject {

    public enum Flag {
        flagged('f'),
        attachment('a');

        private char mFlagChar;

        public char getFlagChar() { return mFlagChar; }

        public static String toNameList(String flags) {
            if (flags == null || flags.length() == 0) return "";
            StringBuilder sb = new StringBuilder();
            for (int i=0; i < flags.length(); i++) {
                String v = null;
                for (Flag f : Flag.values()) {
                    if (f.getFlagChar() == flags.charAt(i)) {
                        v = f.name();
                        break;
                    }
                }
                if (sb.length() > 0) sb.append(", ");
                sb.append(v == null ? flags.substring(i, i+1) : v);
            }
            return sb.toString();
        }

        Flag(char flagChar) {
            mFlagChar = flagChar;

        }
    }

    private String mId;
    private String mFlags;
    private String mTags;
    private String mFolderId;
    private long mDate;
    private long mSize;
    private String mUID;
    private List<ZInvite> mInvites;

    public ZCalendarItem(Element e) throws ServiceException {
        mId = e.getAttribute(MailConstants.A_ID);
        mFlags = e.getAttribute(MailConstants.A_FLAGS, null);
        mTags = e.getAttribute(MailConstants.A_TAGS, null);
        mUID = e.getAttribute(MailConstants.A_UID, null);
        mDate = e.getAttributeLong(MailConstants.A_DATE, 0);
        mFolderId = e.getAttribute(MailConstants.A_FOLDER, null);
        mSize = e.getAttributeLong(MailConstants.A_SIZE);
        mInvites = new ArrayList<ZInvite>();
        for (Element inviteEl : e.listElements(MailConstants.E_INVITE)) {
            mInvites.add(new ZInvite(inviteEl));
        }
    }

    @Override
    public void modifyNotification(ZModifyEvent event) throws ServiceException {
    	if (event instanceof ZModifyMessageEvent) {
    		ZModifyMessageEvent mevent = (ZModifyMessageEvent) event;
            if (mevent.getId().equals(mId)) {
                mFlags = mevent.getFlags(mFlags);
                mTags = mevent.getTagIds(mTags);
                mFolderId = mevent.getFolderId(mFolderId);
            }
        }
    }

    /**
     *
     * @return UID of item
     */
    public String getUid() {
        return mUID;
    }

    public long getSize() {
        return mSize;
    }

    public List<ZInvite> getInvites() {
        return mInvites;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getUuid() {
        return null;
    }

    public boolean hasFlags() {
        return mFlags != null && mFlags.length() > 0;
    }

    public boolean hasTags() {
        return mTags != null && mTags.length() > 0;
    }

   @Override
public ZJSONObject toZJSONObject() throws JSONException {
        ZJSONObject zjo = new ZJSONObject();
        zjo.put("id", mId);
        zjo.put("flags", mFlags);
        zjo.put("tags", mTags);
        zjo.put("date", mDate);
        zjo.put("folderId", mFolderId);
        zjo.put("size", mSize);
        zjo.put("uid", mUID);
        zjo.put("invites", mInvites);
        return zjo;
    }

    @Override
    public String toString() {
        return String.format("[ZCalendarItem %s]", mId);
    }

    public String dump() {
        return ZJSONObject.toString(this);
    }

    public String getFlags() {
        return mFlags;
    }

    public String getTagIds() {
        return mTags;
    }

    public String getFolderId() {
        return mFolderId;
    }

    public long getDate() {
        return mDate;
    }

    public boolean hasAttachment() {
        return hasFlags() && mFlags.indexOf(ZMessage.Flag.attachment.getFlagChar()) != -1;
    }

    public boolean isFlagged() {
        return hasFlags() && mFlags.indexOf(ZMessage.Flag.flagged.getFlagChar()) != -1;
    }

}
