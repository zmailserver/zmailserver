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

package org.zmail.soap.account.message;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.AccountConstants;
import org.zmail.soap.account.type.Identity;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Create an Identity
 * <p>
 * Allowed attributes (see objectclass zmailIdentity in zmail.schema):
 * </p>
 * <ul>
 * <li> zmailPrefBccAddress
 * <li> zmailPrefForwardIncludeOriginalText
 * <li> zmailPrefForwardReplyFormat
 * <li> zmailPrefForwardReplyPrefixChar
 * <li> zmailPrefFromAddress
 * <li> zmailPrefFromDisplay
 * <li> zmailPrefMailSignature
 * <li> zmailPrefMailSignatureEnabled
 * <li> zmailPrefMailSignatureStyle
 * <li> zmailPrefReplyIncludeOriginalText
 * <li> zmailPrefReplyToAddress
 * <li> zmailPrefReplyToDisplay
 * <li> zmailPrefReplyToEnabled
 * <li> zmailPrefSaveToSent
 * <li> zmailPrefSentMailFolder
 * <li> zmailPrefUseDefaultIdentitySettings
 * <li> zmailPrefWhenInFolderIds
 * <li> zmailPrefWhenInFoldersEnabled
 * <li> zmailPrefWhenSentToAddresses
 * <li> zmailPrefWhenSentToEnabled
 * </ul>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AccountConstants.E_CREATE_IDENTITY_REQUEST)
public class CreateIdentityRequest {

    /**
     * @zm-api-field-description Details of the new identity to create
     */
    @XmlElement(name=AccountConstants.E_IDENTITY, required=true)
    private final Identity identity;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private CreateIdentityRequest() {
        this((Identity) null);
    }

    public CreateIdentityRequest(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() { return identity; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("identity", identity)
            .toString();
    }
}
