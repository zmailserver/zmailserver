/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

import java.util.Collection;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.soap.admin.type.AdminAttrsImpl;
import org.zmail.soap.admin.type.Attr;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required true
 * @zm-api-command-description Create a domain
 * <br />
 * Notes:
 * <br />
 * Extra attrs: <b>description</b>, <b>zmailNotes</b>
 */
@XmlRootElement(name=AdminConstants.E_CREATE_DOMAIN_REQUEST)
public class CreateDomainRequest extends AdminAttrsImpl {

    /**
     * @zm-api-field-tag new-domain-name
     * @zm-api-field-description Name of new domain
     */
    @XmlAttribute(name=AdminConstants.E_NAME, required=true)
    private String name;

    public CreateDomainRequest() {
        this(null, (Collection<Attr>) null);
    }

    public CreateDomainRequest(String name) {
        this(name, (Collection<Attr>) null);

    }
    public CreateDomainRequest(String name, Collection<Attr> attrs) {
        super(attrs);
        this.name = name;
    }
    public CreateDomainRequest(String name, Map<String, ? extends Object> attrs)
    throws ServiceException {
        super(attrs);
        this.name = name;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }
}
