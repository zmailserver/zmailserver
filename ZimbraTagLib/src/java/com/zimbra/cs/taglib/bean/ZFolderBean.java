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
package com.zimbra.cs.taglib.bean;

import com.zimbra.common.soap.VoiceConstants;
import com.zimbra.common.util.StringUtil;
import com.zimbra.common.service.ServiceException;
import com.zimbra.client.ZFolder;
import com.zimbra.client.ZFolder.Color;
import com.zimbra.client.ZFolder.View;
import com.zimbra.client.ZGrant;
import com.zimbra.client.ZMailbox;
import com.zimbra.client.ZMountpoint;
import com.zimbra.client.ZSearchFolder;

import java.util.List;

public class  ZFolderBean {

    private ZFolder mFolder;
    private Boolean hasPublicShare;
    private Boolean hasPrivateShare;

    public ZFolderBean(ZFolder folder) {
        mFolder = folder;
    }

    public ZFolder folderObject() { return mFolder; }

    public ZFolderBean getParent() { return mFolder.getParent() == null ? null : new ZFolderBean(mFolder.getParent()); }

    public String getId() { return mFolder.getId(); }

    /** Returns the folder's name.  Note that this is the folder's
     *  name (e.g. <code>"foo"</code>), not its absolute pathname
     *  (e.g. <code>"/baz/bar/foo"</code>).
     * 
     * @see #getPath()
     * @return name
     * 
     */
    public String getName() { return mFolder.getName(); }

    /** Returns the folder's absolute path.  Paths are UNIX-style with 
     *  <code>'/'</code> as the path delimiter.  Paths are relative to
     *  the user root folder,
     *  which has the path <code>"/"</code>.  So the Inbox's path is
     *  <code>"/Inbox"</code>, etc.
     * @return path
     */
    public String getPath() { return mFolder.getPath(); }


	public String getPathURLEncoded() { return mFolder.getPathURLEncoded(); }


    /** Returns the folder's  path relative to the root
     * @return path
     */
    public String getRootRelativePath() { return mFolder.getRootRelativePath(); }

    public String getRootRelativePathURLEncoded() { return mFolder.getRootRelativePathURLEncoded(); }
    
    /**
     * 
     * @return parent id of folder, or null if root folder.
     */
    public String getParentId() { return mFolder.getParentId(); }

    /**
     * @return number of unread items in folder
     */
    public int getUnreadCount() { 
        return getIsDrafts() ? getMessageCount() : mFolder.getUnreadCount();
    }
    
    public boolean getHasUnread() { return getUnreadCount() > 0; }

    /**
     * @return number of unread items in folder
     */
    public int getMessageCount() { return mFolder.getMessageCount(); }

    /**
     * @return size of folder in bytes
     */
    public long getSize() { return mFolder.getSize(); }
    
    /** @return Returns the "hint" as to which view to use to display the folder's
     *  contents.
     */
    public String getDefaultView() { return mFolder.getDefaultView().name(); } 
    
    /**
     *  checked in UI (#), exclude free/(b)usy info, IMAP subscribed (*)
     * @return the flags
     */
    public String getFlags() { return mFolder.getFlags(); }

    public boolean getHasFlags() { return mFolder.hasFlags(); }
    
    public boolean getIsCheckedInUI() { return mFolder.isCheckedInUI(); }

    public boolean getIsExcludedFromFreeBusy() { return mFolder.isExcludedFromFreeBusy(); }

    public boolean getIsIMAPSubscribed() { return mFolder.isIMAPSubscribed(); }

    /**
     * range 0-127; defaults to 0 if not present; client can display only 0-7
     * 
     * @return color
     */
    public String getColor() {
        Color color = mFolder.getColor();
        if (color == Color.DEFAULTCOLOR) {
            color = (getIsContactView() || getIsTaskView()) ? Color.GRAY :  Color.ORANGE;
        }
        return color.getName();
    }

    public String getRgb() {
        return mFolder.getRgb();
    }
    /**
     * remote URL (RSS, iCal, etc) this folder syncs to
     * 
     * @return remote url
     */
    public String getRemoteURL() { return StringUtil.isNullOrEmpty(mFolder.getRemoteURL()) ? null : mFolder.getRemoteURL(); }
    
    /**
     * for remote folders, the access rights the authenticated user has on the folder.
     * 
     * @return effective perms
     */
    public String getEffectivePerm() { return mFolder.getEffectivePerms(); }
    
    /**
     * @return return grants or empty list if no grants
     */
    public List<ZGrant> getGrants() { return mFolder.getGrants(); }

    /**
     * @return sub folders, or empty list if no sub folders
     */
    public List<ZFolder> getSubFolders() { return mFolder.getSubFolders(); }

    public int getSubFolderCount() { return mFolder.getSubFolders().size(); }

    public boolean getHasChildren() { return !mFolder.getSubFolders().isEmpty(); }
    
    public boolean getIsSearchFolder() { return mFolder instanceof ZSearchFolder; }
    
    public String getQuery() { return getIsSearchFolder() ? ((ZSearchFolder) mFolder).getQuery() : ""; }
    
    //public ZFolder getSubFolderByPath(String path);
    
    public boolean getIsInbox() { return mFolder.getId().equals(ZFolder.ID_INBOX); }
    public boolean getIsChats() { return mFolder.getId().equals(ZFolder.ID_CHATS); }
    public boolean getIsTrash() { return mFolder.getId().equals(ZFolder.ID_TRASH); }
    public boolean getIsSpam() { return mFolder.getId().equals(ZFolder.ID_SPAM); }
    public boolean getIsSent() { return mFolder.getId().equals(ZFolder.ID_SENT); }    
    public boolean getIsDrafts() { return mFolder.getId().equals(ZFolder.ID_DRAFTS); }
    public boolean getIsContacts() { return mFolder.getId().equals(ZFolder.ID_CONTACTS); }
    public boolean getIsCalendar() { return mFolder.getId().equals(ZFolder.ID_CALENDAR); }    
    public boolean getIsNotebook() { return mFolder.getId().equals(ZFolder.ID_NOTEBOOK); }    
    public boolean getIsDocument() { return mFolder.getId().equals(ZFolder.ID_BRIEFCASE); }
    public boolean getIsAutoContacts() { return mFolder.getId().equals(ZFolder.ID_AUTO_CONTACTS); }

    public boolean getIsVoiceMailInbox() { return getIsVoiceView() && VoiceConstants.FNAME_VOICEMAILINBOX.equals(mFolder.getName()); } 
    public boolean getIsMissedCalls() { return getIsVoiceView() && VoiceConstants.FNAME_MISSEDCALLS.equals(mFolder.getName()); }
    public boolean getIsAnsweredCalls() { return getIsVoiceView() && VoiceConstants.FNAME_ANSWEREDCALLS.equals(mFolder.getName()); }
    public boolean getIsPlacedCalls() { return getIsVoiceView() && VoiceConstants.FNAME_PLACEDCALLS.equals(mFolder.getName()); }
    public boolean getIsVoiceMailTrash() { return getIsVoiceView() && VoiceConstants.FNAME_TRASH.equals(mFolder.getName()); }

    public boolean getIsMailView() {
        ZFolder.View view = mFolder.getDefaultView();
        return view == null || view == ZFolder.View.message || view == ZFolder.View.conversation;
    }
    
    public boolean getIsNullView() { return mFolder.getDefaultView() == null; }
    public boolean getIsUnknownView() { return mFolder.getDefaultView() == ZFolder.View.unknown; }
    public boolean getIsMessageView() { return mFolder.getDefaultView() == ZFolder.View.message; }
    public boolean getIsContactView() { return mFolder.getDefaultView() == ZFolder.View.contact; }    
    public boolean getIsConversationView() { return mFolder.getDefaultView() == ZFolder.View.conversation; }        
    public boolean getIsAppointmentView() { return mFolder.getDefaultView() == ZFolder.View.appointment; }
    public boolean getIsWikiView() { return mFolder.getDefaultView() == ZFolder.View.wiki; }
    public boolean getIsTaskView() { return mFolder.getDefaultView() == ZFolder.View.task; }
    public boolean getIsVoiceView() { return mFolder.getDefaultView() == ZFolder.View.voice; }
    public boolean getIsDocumentView() { return mFolder.getDefaultView() == ZFolder.View.document; }
    
    public boolean getIsSystemFolder() { return mFolder.isSystemFolder(); }
    
    public boolean getIsMountPoint() { return mFolder instanceof ZMountpoint; }

    public boolean getIsInTrash() {
        if (getId().equals(ZFolder.ID_TRASH))
            return true;

        ZFolder parent = mFolder.getParent();
        while (parent != null) {
            if (parent.getId().equals(ZFolder.ID_TRASH))
                return true;
            parent = parent.getParent();
        }
        return false;
    }

    /**
     * @return owner display name if mountpoint, otherwise null
     */
    public String getOwnerDisplayName() {
        return mFolder instanceof ZMountpoint ?
                ((ZMountpoint) mFolder ).getOwnerDisplayName() : null;
    }

    /**
     * @return owner id if mountpoint, otherwise null
     */
    public String getOwnerId() {
        return mFolder instanceof ZMountpoint ?
                ((ZMountpoint) mFolder ).getOwnerId() : null;
    }

    /**
     * @return remote id if mountpoint, otherwise null
     */
    public String getRemoteId() {
        return mFolder instanceof ZMountpoint ?
                ((ZMountpoint) mFolder ).getRemoteId() : null;
    }

    /**
     * @return canonical remote id if mountpoint, otherwise null
     */
    public String getCanonicalRemoteId() {
        return mFolder instanceof ZMountpoint ?
                ((ZMountpoint) mFolder ).getCanonicalRemoteId() : null;
    }

    public boolean getIsFeed() { return !StringUtil.isNullOrEmpty(mFolder.getRemoteURL()); }

    public String getCanonicalId() { return (mFolder instanceof ZMountpoint) ? ((ZMountpoint)mFolder).getCanonicalRemoteId() : mFolder.getId(); }

    private int mDepth = -1;
    
    public synchronized int getDepth() {
        if (mDepth != -1) return mDepth;
        int depth=0;
        String path = getPath();
        for (int i=1; i < path.length(); i++) {
            if (path.charAt(i) == ZMailbox.PATH_SEPARATOR_CHAR) depth++; 
        }
        return depth;
    }

    /**
     * @return true if the mount point has write permission
     */
    public boolean getIsMountPointWritable() {
        if (getIsMountPoint()) {
            String perm = getEffectivePerm();
            if (perm != null) {
                String positivePerm = perm.replace("/-./g", "") ;
                if(positivePerm != null)
                    return (positivePerm.indexOf(ZFolder.PERM_WRITE) != -1);
            }
        }
        return false;
    }

    public boolean getIsMessageMoveTarget() {
        return getIsConversationMoveTarget();
    }

    public boolean getIsConversationMoveTarget() {
        boolean isSpamEnabled = true;
        try {
            isSpamEnabled = folderObject().getMailbox().getFeatures().getSpam();
        } catch (ServiceException e) {}
        return (getIsMessageView() || getIsConversationView() || getIsNullView() || getIsUnknownView()) &&
                !(getIsDrafts() || getIsMountPoint() || getIsSearchFolder() || !StringUtil.isNullOrEmpty(getRemoteURL())) &&
                !getId().equals(ZFolder.ID_CHATS) &&
				((!getId().equals(ZFolder.ID_SPAM)) || isSpamEnabled);
    }

    public boolean getIsMessageFolderMarkReadTarget() {
        return (getIsMessageView() || getIsConversationView() || getIsNullView()) &&
                !(getIsDrafts() || getIsMountPoint() || getIsSearchFolder());
    }

    public boolean getIsMessageFolderRenameTarget() {
        return (getIsMessageView() || getIsConversationView() || getIsNullView()) &&
                !(getIsSystemFolder() || getIsSearchFolder());
    }

    public boolean getIsMessageFolderMoveSource() {
        return (getIsMessageView() || getIsConversationView() || getIsNullView()) &&
                !getIsSystemFolder();
    }

    public boolean getIsMessageFolderDeleteTarget() {
        return (getIsMessageView() || getIsConversationView() || getIsNullView()) &&
                !(getIsSystemFolder() || getIsSearchFolder());
    }

    public boolean getIsContactMoveTarget() {
        return (getIsContactView() || getIsTrash()) && (getRemoteURL() == null) && !(getIsDrafts() || getIsSearchFolder()) && (!getIsMountPoint() || getIsMountPointWritable());

    }

    public boolean getIsAppointmentMoveTarget() {
        return getIsAppointmentView() && (getRemoteURL() == null) && (!getIsMountPoint() || getIsMountPointWritable());
    }

    public boolean getIsTaskMoveTarget() {
        return getIsTaskView() && (getRemoteURL() == null) && (!getIsMountPoint() || getIsMountPointWritable());
    }

    public boolean getIsDocumentMoveTarget() {
        //TODO: handle perm check on mountpoint!
        return getIsDocumentView() && !(getIsMountPoint() || getRemoteURL() != null);
    }

    public boolean getIsWikiMoveTarget() {
        //TODO: handle perm check on mountpoint!
        return getIsWikiView() && !(getIsMountPoint() || getRemoteURL() != null);
    }

    public boolean getIsContactCreateTarget() {
      return getIsContactView() && (getRemoteURL() == null) && !(getIsDrafts() || getIsSearchFolder()) && (!getIsMountPoint() || getIsMountPointWritable());
    }

    public String getStyleColor() {
        return getStyleColor(mFolder.getColor(), mFolder.getDefaultView());
    }

    public static String getStyleColor(Color color, View view) {
        String colorName = color.getName();
        if (!StringUtil.equal(colorName, Color.RGBCOLOR) && !StringUtil.equal(colorName, Color.DEFAULTCOLOR.getName())) {
            return colorName.substring(0,1).toUpperCase() + colorName.substring(1);
        } else if (view == View.contact || view == View.task) {
            colorName = Color.GRAY.getName();
        } else {
            colorName = Color.ORANGE.getName();
        }
        return colorName.substring(0,1).toUpperCase() + colorName.substring(1);
    }

    public String getRgbColor() {
        return getRgbColor(mFolder.getColor(), mFolder.getDefaultView());
    }

    public int getRgbColorIndex() {
        if( getRgb()!=null ) {
            /* when rgb is set, a custom color is used. Hence returning 0 */
            return 0;
        }
        else {
            return getRgbColorIndex(mFolder.getColor(), mFolder.getDefaultView());
        }
    }

    /* This function is to get string property names from ZhMsg.properties by colorIndex */
    public String getRgbColorMsg() {
        return ZFolder.RGB_COLORS_MSG[getRgbColorIndex()];
    }

    public static String getRgbColorMsg(Color color, View view) {
            return ZFolder.RGB_COLORS_MSG[getRgbColorIndex(color, view)];
        }

    public static String getRgbColor(Color color, View view) {
        int colorIndex = (int) color.getValue();
        if (color == Color.DEFAULTCOLOR) {
            if (view == View.contact || view == View.task)
                colorIndex = (int) Color.GRAY.getValue();
            else
                colorIndex = (int) Color.ORANGE.getValue();
        }
        return ZFolder.RGB_COLORS[colorIndex];
    }

    public static int getRgbColorIndex (Color color, View view) {
                if (color == Color.DEFAULTCOLOR) {
                    if (view == View.contact || view == View.task)
                        return (int) Color.GRAY.getValue();
                    else
                        return (int) Color.ORANGE.getValue();
                }
        return (int) color.getValue();
    }


    public String getImage() {
        if (getIsSearchFolder()) {
            return "startup/ImgSearchFolder.png";
        } else if (getIsAppointmentView() || (getParentId().equals(ZFolder.ID_CALENDAR) && getIsUnknownView())) {
            if (getIsMountPoint()) {
                return "calendar/ImgSharedCalendarFolder.png";
            } else {
                return "calendar/ImgCalendarFolder.png";
            }
        } else if (getIsContactView() || (getParentId().equals(ZFolder.ID_CONTACTS) && getIsUnknownView())) {
            if (getIsMountPoint()) {
                return "contacts/ImgSharedContactsFolder.png";
            } else if (getIsAutoContacts()) {
                return "contacts/ImgEmailedContacts.png";
            } else {
                return "contacts/ImgContactsFolder.png";
            }
        } else if (getIsTaskView() || (getParentId().equals(ZFolder.ID_TASKS) && getIsUnknownView())) {
            if (getIsMountPoint()) {
                return "tasks/ImgSharedTaskList.png";
            } else {
                return "startup/ImgTaskList.png";
            }
        } else if (getIsSystemFolder()) {
            if (getIsInbox())
                return "startup/ImgInbox.png";
            else if (getIsTrash())
                return "startup/ImgTrash.png";
            else if (getIsSpam())
                return "startup/ImgSpamFolder.png";
            else if (getIsSent())
                return "startup/ImgSentFolder.png";
            else if (getIsDrafts())
                return "startup/ImgDraftFolder.png";
            else
                return "startup/ImgFolder.png";
        } else if (getIsMailView() && getIsFeed()) {
            return "startup/ImgRSS.png";
        } else if (getIsMountPoint()) {
            return "startup/ImgSharedMailFolder.png";
        } else if (getIsVoiceView()) {
            String name = getName();
            if (VoiceConstants.FNAME_PLACEDCALLS.equals(name)) {
                return "voicemail/ImgPlacedCalls.png";
            } else if (VoiceConstants.FNAME_ANSWEREDCALLS.equals(name)) {
                return "voicemail/ImgAnsweredCalls.png";
            } else if (VoiceConstants.FNAME_MISSEDCALLS.equals(name)) {
                return "voicemail/ImgMissedCalls.png";
            } else if (VoiceConstants.FNAME_VOICEMAILINBOX.equals(name)) {
                return "voicemail/ImgVoicemail.png";
            } else if (VoiceConstants.FNAME_TRASH.equals(name)) {
                return "startup/ImgTrash.png";
            }
            return null;
        } else {
            return "startup/ImgFolder.png";
        }
    }
    
    public String getTypes() { return getIsSearchFolder() ? ((ZSearchFolder) mFolder).getTypes() : null; }

    public String getType(){
        if (getIsSearchFolder()) {
            return "SearchFolder";
        } else if (getIsAppointmentView()) {
            if (getIsMountPoint()) {
                return "SharedCalendarFolder";
            } else {
                return "CalendarFolder";
            }
        } else if (getIsContactView()) {
            if (getIsMountPoint()) {
                return "SharedContactsFolder";
            } else if (getIsAutoContacts()) {
                return "EmailedContacts";
            } else {
                return "ContactsFolder";
            }
        } else if (getIsTaskView()) {
            if (getIsMountPoint()) {
                return "SharedTaskList";
            } else {
                return "TaskList";
            }
        } else if (getIsSystemFolder()) {
            if (getIsInbox())
                return "Inbox";
            else if (getIsTrash())
                return "Trash";
            else if (getIsSpam())
                return "SpamFolder";
            else if (getIsSent())
                return "SentFolder";
            else if (getIsDrafts())
                return "DraftFolder";
            else
                return "Folder";
        } else if (getIsMailView() && getIsFeed()) {
            return "RSS";
        } else if (getIsMountPoint()) {
            return "SharedMailFolder";
        } else if (getIsVoiceView()) {
            String name = getName();
            if (VoiceConstants.FNAME_PLACEDCALLS.equals(name)) {
                return "PlacedCalls";
            } else if (VoiceConstants.FNAME_ANSWEREDCALLS.equals(name)) {
                return "AnsweredCalls";
            } else if (VoiceConstants.FNAME_MISSEDCALLS.equals(name)) {
                return "MissedCalls";
            } else if (VoiceConstants.FNAME_VOICEMAILINBOX.equals(name)) {
                return "Voicemail";
            } else if (VoiceConstants.FNAME_TRASH.equals(name)) {
                return "Trash";
            }
            return null;
        } else {
            return "Folder";
        }
    }
    
    public Boolean getHasPublicShare(){
        if (this.hasPublicShare == null) {
            this.initShareFlags();
        }
        return this.hasPublicShare;
    }

    public Boolean getHasPrivateShare(){
        if (this.hasPrivateShare == null) {
            this.initShareFlags();
        }
        return this.hasPrivateShare;
    }

    private void initShareFlags(){
        List<ZGrant> grants = this.getGrants();
        for (ZGrant grant : grants){
            if (grant.getGranteeType().equals(ZGrant.GranteeType.pub)){
                this.hasPublicShare = true;
            }
            if (grant.getGranteeType().equals(ZGrant.GranteeType.usr)){
                this.hasPrivateShare = true;
            }
            if (this.hasPublicShare != null && this.hasPrivateShare != null){
                break;
            }
        }
        if (this.hasPublicShare == null){
            this.hasPublicShare = false;
        }
        if (this.hasPrivateShare == null){
            this.hasPrivateShare = false;
        }
    }
}
