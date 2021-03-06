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
 * @zm-api-command-description Create a system retention policy.
 * <br />
 * The system retention policy SOAP APIs allow the administrator to edit named system retention policies that users
 * can apply to folders and tags.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_CREATE_SYSTEM_RETENTION_POLICY_REQUEST)
public class CreateSystemRetentionPolicyRequest {
    
    /**
     * @zm-api-field-description COS
     */
    @XmlElement(name=AdminConstants.E_COS)
    private CosSelector cos;

    public void setCos(CosSelector cos) {
        this.cos = cos;
    }

    public CosSelector getCos() { return cos; }
    

    public static class PolicyHolder {
        /**
         * @zm-api-field-description Policy
         */
        @XmlElement(name=AdminConstants.E_POLICY, namespace=MailConstants.NAMESPACE_STR)
        Policy policy;

        public PolicyHolder() {
        }

        public PolicyHolder(Policy p) {
            policy = p;
        }
    }

    /**
     * @zm-api-field-description Keep policy details
     */
    @XmlElement(name=AdminConstants.E_KEEP)
    private PolicyHolder keep;

    /**
     * @zm-api-field-description Purge policy details
     */
    @XmlElement(name=AdminConstants.E_PURGE)
    private PolicyHolder purge;

    public CreateSystemRetentionPolicyRequest() {
    }

    public static CreateSystemRetentionPolicyRequest newKeepRequest(Policy policy) {
        CreateSystemRetentionPolicyRequest req = new CreateSystemRetentionPolicyRequest();
        req.keep = new PolicyHolder(policy);
        return req;
    }

    public static CreateSystemRetentionPolicyRequest newPurgeRequest(Policy policy) {
        CreateSystemRetentionPolicyRequest req = new CreateSystemRetentionPolicyRequest();
        req.purge = new PolicyHolder(policy);
        return req;
    }

    public Policy getKeepPolicy() {
        if (keep != null) {
            return keep.policy;
        }
        return null;
    }

    public Policy getPurgePolicy() {
        if (purge != null) {
            return purge.policy;
        }
        return null;
    }
}
