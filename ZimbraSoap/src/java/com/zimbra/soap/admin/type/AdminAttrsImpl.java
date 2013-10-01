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

package com.zimbra.soap.admin.type;

import java.util.List;
import java.util.Collections;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.json.jackson.annotate.ZimbraKeyValuePairs;

/**
 * Note: Any subclasses that have fields for elements MUST either omit {@link XmlType} or specify a propOrder in it
 *       naming all fields related to elements.  This is required to ensure correctly generated WSDL.
 *       Failure to do so will result in an error similar to the following from "ant wsdl-client-support"'s wsimport:
 *           [WARNING] cos-all-limited.1.2: An all model group must appear in a particle with {min occurs} =
 *           {max occurs} = 1, and that particle must be part of a pair which constitutes the {content type} of
 *           a complex type definition.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"attrs"})
public class AdminAttrsImpl implements AdminAttrs {

    /**
     * @zm-api-field-description Attributes
     */
    @ZimbraKeyValuePairs
    @XmlElement(name=AdminConstants.E_A /* a */, required=false)
    private List<Attr> attrs = Lists.newArrayList();

    public AdminAttrsImpl() {
    }

    public AdminAttrsImpl (Iterable<Attr> attrs) {
        this.setAttrs(attrs);
    }

    public AdminAttrsImpl (Map<String, ? extends Object> attrs)
    throws ServiceException {
        this.setAttrs(attrs);
    }

    @Override
    public void setAttrs(Iterable<Attr> attrs) {
        this.attrs.clear();
        if (attrs != null) {
            Iterables.addAll(this.attrs,attrs);
        }
    }

    @Override
    public void setAttrs(Map<String, ? extends Object> attrs)
    throws ServiceException {
        this.setAttrs(Attr.mapToList(attrs));
    }

    @Override
    public void addAttr(Attr attr) {
        this.attrs.add(attr);
    }

    @Override
    public void addAttr(String n, String value) {
        this.attrs.add(Attr.fromNameValue(n, value));
    }

    @Override
    public List<Attr> getAttrs() {
        return Collections.unmodifiableList(attrs);
    }

    public Map<String, Object> getAttrsAsOldMultimap()
    throws ServiceException {
        return Attr.collectionToMap(this.getAttrs());
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("attrs", attrs);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
