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

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.cs.mailbox.Contact;
import com.zimbra.client.ZContact;
import com.zimbra.client.ZEmailAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ZContactBean implements Comparable {

    private ZContact mContact;
    private String mFileAs;       
    private boolean mIsGalContact;
    
    public ZContactBean(ZContact contact) {
        mContact = contact;
    }

    public ZContactBean(ZContact contact, boolean isGalContact) {
        mContact = contact;
        mIsGalContact = isGalContact;
    }
    
    public boolean getIsTypeI() { return mContact.isTypeI();}

    public boolean getIsGalContact() { return mIsGalContact; }

    public String getId() { return mContact.getId(); }
    
    public String getTagIds() { return mContact.getTagIds(); }
    
    public String getFlags() { return mContact.getFlags(); }
    
    public boolean getHasFlags() { return mContact.hasFlags(); }
    
    public boolean getHasTags() { return mContact.hasTags(); }
    
    public boolean getIsFlagged() { return mContact.isFlagged(); }
    
    public boolean getHasAttachment() { return mContact.hasAttachment(); }
    
    public String getFolderId() { return mContact.getFolderId(); }

    public String getRevision() { return mContact.getRevision(); }
    
    /**
     * @return time in msecs
     */
    public long getMetaDataChangedDate() { return mContact.getMetaDataChangedDate(); }
    
    public Map<String, String> getAttrs() { return mContact.getAttrs(); }

    // fields

    public String getAssistantPhone() { return mContact.getAttrs().get("assistantPhone"); }

    public String getBirthday() { return mContact.getAttrs().get("birthday"); }

    public String getCallbackPhone() { return mContact.getAttrs().get("callbackPhone"); }

    public String getCarPhone() { return mContact.getAttrs().get("carPhone"); }

    public String getCompany() { return mContact.getAttrs().get("company"); }

    public String getCompanyPhone() { return mContact.getAttrs().get("companyPhone"); }

    public String getDescription() { return mContact.getAttrs().get("description"); }

    public String getDepartment() { return mContact.getAttrs().get("department"); }

    public String getEmail() { return mContact.getAttrs().get("email"); }

    public String getEmail2() { return mContact.getAttrs().get("email2"); }

    public String getEmail3() { return mContact.getAttrs().get("email3"); }

    public String getFileAs() { return mContact.getAttrs().get("fileAs"); }

    public String getFirstName() { return mContact.getAttrs().get("firstName"); }

    public String getFullName() { return mContact.getAttrs().get("fullName"); }

    public String getHomeCity() { return mContact.getAttrs().get("homeCity"); }

    public String getHomeCountry() { return mContact.getAttrs().get("homeCountry"); }

    public String getHomeFax() { return mContact.getAttrs().get("homeFax"); }

    public String getHomePhone() { return mContact.getAttrs().get("homePhone"); }

    public String getHomePhone2() { return mContact.getAttrs().get("homePhone2"); }

    public String getHomePostalCode() { return mContact.getAttrs().get("homePostalCode"); }

    public String getHomeState() { return mContact.getAttrs().get("homeState"); }

    public String getHomeStreet() { return mContact.getAttrs().get("homeStreet"); }

    public String getHomeURL() { return mContact.getAttrs().get("homeURL"); }

    public String getInitials() { return mContact.getAttrs().get("initials"); }

    public String getJobTitle() { return mContact.getAttrs().get("jobTitle"); }

    public String getLastName() { return mContact.getAttrs().get("lastName"); }

    public String getMaidenName() { return mContact.getAttrs().get("maidenName"); }

    public String getMiddleName() { return mContact.getAttrs().get("middleName"); }

    public String getMobilePhone() { return mContact.getAttrs().get("mobilePhone"); }

    public String getNamePrefix() { return mContact.getAttrs().get("namePrefix"); }

    public String getNameSuffix() { return mContact.getAttrs().get("nameSuffix"); }

    public String getNickname() { return mContact.getAttrs().get("nickname"); }

    public String getNotes() { return mContact.getAttrs().get("notes"); }

    public String getOffice() { return mContact.getAttrs().get("office"); }

    public String getOtherCity() { return mContact.getAttrs().get("otherCity"); }

    public String getOtherCountry() { return mContact.getAttrs().get("otherCountry"); }

    public String getOtherFax() { return mContact.getAttrs().get("otherFax"); }

    public String getOtherPhone() { return mContact.getAttrs().get("otherPhone"); }

    public String getOtherPostalCode() { return mContact.getAttrs().get("otherPostalCode"); }

    public String getOtherState() { return mContact.getAttrs().get("otherState"); }

    public String getOtherStreet() { return mContact.getAttrs().get("otherStreet"); }

    public String getOtherURL() { return mContact.getAttrs().get("otherURL"); }

    public String getPager() { return mContact.getAttrs().get("pager"); }

    public String getPhoneticFirstName() { return mContact.getAttrs().get("phoneticFirstName"); };

    public String getPhoneticLastName() { return mContact.getAttrs().get("phoneticLastName"); };

    public String getPhoneticCompany() { return mContact.getAttrs().get("phoneticCompany"); };

    public String getTollFree() { return mContact.getAttrs().get("tollFree"); }

    public String getWorkCity() { return mContact.getAttrs().get("workCity"); }

    public String getWorkCountry() { return mContact.getAttrs().get("workCountry"); }

    public String getWorkFax() { return mContact.getAttrs().get("workFax"); }

    public String getWorkPhone() { return mContact.getAttrs().get("workPhone"); }

    public String getWorkPhone2() { return mContact.getAttrs().get("workPhone2"); }

    public String getWorkPostalCode() { return mContact.getAttrs().get("workPostalCode"); }

    public String getWorkState() { return mContact.getAttrs().get("workState"); }

    public String getWorkStreet() { return mContact.getAttrs().get("workStreet"); }

    public String getWorkURL() { return mContact.getAttrs().get("workURL"); }

    public boolean getIsGroup() { return mContact.getIsGroup(); }

    public String getImagePart() { return (mContact.getAttachmentPartInfo("image") != null ? mContact.getAttachmentPartName("image") : null);}

    /* Comcast specific */
    public String getHomeAddress() { return mContact.getAttrs().get("homeAddress"); }

    public String getOtherDepartment() { return mContact.getAttrs().get("otherDepartment"); }

    public String getOtherOffice() { return mContact.getAttrs().get("otherOffice"); }

    public String getOtherProfession() { return mContact.getAttrs().get("otherProfession"); }
    
    public String getOtherAddress() { return mContact.getAttrs().get("otherAddress"); }

    public String getOtherMgrName() { return mContact.getAttrs().get("otherMgrName"); }

    public String getOtherAsstName() { return mContact.getAttrs().get("otherAsstName"); }

    public String getOtherAnniversary() { return mContact.getAttrs().get("anniversary"); }

    public String getOtherCustom1() { return mContact.getAttrs().get("custom1"); }

    public String getOtherCustom2() { return mContact.getAttrs().get("otherCustom2"); }

    public String getOtherCustom3() { return mContact.getAttrs().get("otherCustom3"); }

    public String getOtherCustom4() { return mContact.getAttrs().get("otherCustom4"); }

    public String getWorkAddress() { return mContact.getAttrs().get("workAddress"); }

    public String getWorkAltPhone() { return mContact.getAttrs().get("workAltPhone"); }
    
    public String getWorkMobile() { return mContact.getAttrs().get("workMobile"); }

    public String getIMAddress1() { return mContact.getAttrs().get("imAddress1"); }

    public String getIMAddress2() { return mContact.getAttrs().get("imAddress2"); }

    public String getIMAddress3() { return mContact.getAttrs().get("imAddress3"); }

    public String getWorkEmail2() { return mContact.getAttrs().get("workEmail2"); }

    public String getWorkEmail3() { return mContact.getAttrs().get("workEmail3"); }

    public String getWorkIM1() { return mContact.getAttrs().get("workIM1"); }
    
    public String getWorkIM2() { return mContact.getAttrs().get("workIM2"); }

    public String getWorkEmail1() { return mContact.getAttrs().get("workEmail1"); }

    /* end of comcast specific */
    private static final Pattern sCOMMA = Pattern.compile(",");
    
    public String[] getGroupMembers() throws ServiceException {
        Map<String, ZContact> members = mContact.getMembers();
        if (members != null) {
            String memberIds[] = members.keySet().toArray(new String[0]);
            return memberIds;
        }
        return null;
    }

    public static ZContactBean getGroupMemberById(ZContactBean contact, String id) throws ServiceException {
        Map<String, ZContact> members = contact.mContact.getMembers();
        if (members != null) {
            ZContact memberContact = members.get(id);    
            if (memberContact != null)
                return new ZContactBean(memberContact, memberContact.isGalContact());
        }
        return null;
    }

    public String getGroupMembersPerLine() throws ServiceException {
        StringBuilder sb = new StringBuilder();
        for (String addr : getGroupMembers()) {
            sb.append(addr).append("\n");
        }
        return sb.toString();
    }
        
    public String getDisplayFileAs() {
        if (mFileAs == null) {
            try {
                mFileAs = Contact.getFileAsString(mContact.getAttrs());
            } catch (ServiceException e) {
                mFileAs = "";
            }
        }
        return mFileAs;
    }
    
    private static final Pattern sCOMMA_OR_SP = Pattern.compile("[, ]");
    
    public static boolean anySet(ZContactBean cbean, String s) {
        if (s == null || s.length() == 0) return false;
        String[] fields = sCOMMA_OR_SP.split(s);
        Map<String, String> attrs = cbean.getAttrs();         
        for (String field: fields) {
            if (attrs.get(field) != null) return true;
        }
        return false;
    }
        /**
     * @return first email from email/2/3 that is set, or an empty string
     */
    public String getDisplayEmail() {
        if (getEmail() != null && getEmail().length() > 0)
            return getEmail();
        else if (getEmail2() != null && getEmail2().length() > 0)
            return getEmail2();
        else if (getEmail3() != null && getEmail3().length() > 0)
            return getEmail3();
        else
            return "";
    }

    /**
       *
       * @return the "full" email address suitable for inserting into a To/Cc/Bcc header
       */
    public String getFullAddress() {
        return new ZEmailAddress(getDisplayEmail(), null, getDisplayFileAs(), ZEmailAddress.EMAIL_TYPE_TO).getFullAddress();
    }

    /**
     *
     * @return the gal "fileAs" str
     *
     */
    public String getGalFileAsStr() {
        String fname = getFullName();
        if (fname == null || fname.length() == 0) {
            String f = getFirstName();
            String l = getLastName();
            StringBuilder sb = new StringBuilder();
            if (f != null) sb.append(f);
            if (l != null) {
                if (sb.length() > 0)
                    sb.append(' ');
                sb.append(l);
            }
            fname = sb.toString();
        }
        return fname;
    }

    /**
       *
       * @return the "full" email address suitable for inserting into a To/Cc/Bcc header
       */
    public String getGalFullAddress() {
        return new ZEmailAddress(getDisplayEmail(), null, getGalFileAsStr(), ZEmailAddress.EMAIL_TYPE_TO).getFullAddress();
    }

    public String getImage() {
        if (getIsGroup())
            return "contacts/ImgGroup.png";
        else if (getIsGalContact())
            return "startup/ImgGALContact.png";
        else
            return "contacts/ImgContact.png";
    }

    public String getImageAltKey() {
        if (getIsGroup())
            return "ALT_CONTACT_GROUP";
        else if (getIsGalContact())
            return "ALT_CONTACT_GAL_CONTACT";
        else
            return "ALT_CONTACT_CONTACT";
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof ZContactBean))
            return 0;
        ZContactBean other = (ZContactBean) obj;
        String name = getIsGalContact() ? getGalFullAddress() : getFullAddress();
        String oname = other.getIsGalContact() ? other.getGalFullAddress() : other.getFullAddress();
        return name.compareToIgnoreCase(oname);
    }
}
