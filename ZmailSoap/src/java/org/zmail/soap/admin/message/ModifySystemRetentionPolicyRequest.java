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

package org.zmail.soap.admin.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.MailConstants;
import org.zmail.soap.admin.type.CosSelector;
import org.zmail.soap.mail.type.Policy;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Modify system retention policy
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_MODIFY_SYSTEM_RETENTION_POLICY_REQUEST)
public class ModifySystemRetentionPolicyRequest {
    
    
    /**
     * @zm-api-field-description COS
     */
    @XmlElement(name=AdminConstants.E_COS)
    private CosSelector cos;

    public void setCos(CosSelector cos) {
        this.cos = cos;
    }

    public CosSelector getCos() { return cos; }

    /**
     * @zm-api-field-description New policy
     */
    @XmlElement(name=AdminConstants.E_POLICY, namespace=MailConstants.NAMESPACE_STR, required=true)
    private Policy policy;

    public ModifySystemRetentionPolicyRequest() {
    }

    public ModifySystemRetentionPolicyRequest(Policy p) {
        policy = p;
    }

    public Policy getPolicy() {
        return policy;
    }
}
