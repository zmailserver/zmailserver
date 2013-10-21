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

package org.zmail.soap.mail.type;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.zmail.common.soap.MailConstants;
import org.zmail.soap.type.GranteeType;
import org.zmail.soap.type.ZmBoolean;

/*
 * Delete this class in bug 66989
 */

@XmlAccessorType(XmlAccessType.NONE)
public class AccountACEinfo {

    /**
     * @zm-api-field-tag grantee-zmail-id
     * @zm-api-field-description Zmail ID of the grantee
     */
    @XmlAttribute(name=MailConstants.A_ZIMBRA_ID /* zid */, required=false)
    private String zmailId;

    /**
     * @zm-api-field-tag grantee-type
     * @zm-api-field-description Grantee type
     * <table>
     * <tr> <td> <b>usr</b> </td> <td> Zmail User </td> </tr>
     * <tr> <td> <b>grp</b> </td> <td> Zmail Group (distribution list) </td> </tr>
     * <tr> <td> <b>all</b> </td> <td> all authenticated users </td> </tr>
     * <tr> <td> <b>gst</b> </td> <td> non-Zmail email address and password (not yet supported) </td> </tr>
     * <tr> <td> <b>key</b> </td> <td> external user with accesskey </td> </tr>
     * <tr> <td> <b>pub</b> </td> <td> public authenticated and unauthenticated access </td> </tr>
     * </table>
     */
    @XmlAttribute(name=MailConstants.A_GRANT_TYPE /* gt */, required=true)
    private final GranteeType granteeType;

    /**
     * @zm-api-field-tag right
     * @zm-api-field-description Right - viewFreeBusy | invite
     */
    @XmlAttribute(name=MailConstants.A_RIGHT /* right */, required=true)
    private final String right;

    /**
     * @zm-api-field-tag grantee-name
     * @zm-api-field-description Name or email address of the grantee.
     * Not present if <b>{grantee-type}</b> is "all" or "pub"
     */
    @XmlAttribute(name=MailConstants.A_DISPLAY /* d */, required=false)
    private String displayName;

    /**
     * @zm-api-field-tag access-key
     * @zm-api-field-description Optional argument.  Access key when <b>{grantee-type}</b> is "key"
     */
    @XmlAttribute(name=MailConstants.A_ACCESSKEY /* key */, required=false)
    private String accessKey;

    /**
     * @zm-api-field-tag password
     * @zm-api-field-description Optional argument.  Password when <b>{grantee-type}</b> is "gst" (not yet supported)
     */
    @XmlAttribute(name=MailConstants.A_PASSWORD /* pw */, required=false)
    private String password;

    /**
     * @zm-api-field-tag deny
     * @zm-api-field-description Set if a right is specifically denied.  Default is unset.
     */
    @XmlAttribute(name=MailConstants.A_DENY /* deny */, required=false)
    private ZmBoolean deny;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private AccountACEinfo() {
        this((GranteeType) null, (String) null);
    }

    public AccountACEinfo(GranteeType granteeType, String right) {
        this.granteeType = granteeType;
        this.right = right;
    }

    public void setZmailId(String zmailId) { this.zmailId = zmailId; }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }
    public void setPassword(String password) { this.password = password; }
    public void setDeny(Boolean deny) { this.deny = ZmBoolean.fromBool(deny); }
    public String getZmailId() { return zmailId; }
    public GranteeType getGranteeType() { return granteeType; }
    public String getRight() { return right; }
    public String getDisplayName() { return displayName; }
    public String getAccessKey() { return accessKey; }
    public String getPassword() { return password; }
    public Boolean getDeny() { return ZmBoolean.toBool(deny); }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("zmailId", zmailId)
            .add("granteeType", granteeType)
            .add("right", right)
            .add("displayName", displayName)
            .add("accessKey", accessKey)
            .add("password", password)
            .add("deny", deny);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
