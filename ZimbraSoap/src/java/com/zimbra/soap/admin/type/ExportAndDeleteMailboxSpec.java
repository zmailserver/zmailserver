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

package com.zimbra.soap.admin.type;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.zimbra.common.soap.AdminConstants;

@XmlAccessorType(XmlAccessType.NONE)
public class ExportAndDeleteMailboxSpec {

    /**
     * @zm-api-field-tag id
     * @zm-api-field-description ID
     */
    @XmlAttribute(name=AdminConstants.A_ID, required=true)
    private final int id;

    /**
     * @zm-api-field-description Items
     */
    @XmlElement(name=AdminConstants.E_ITEM, required=false)
    private List<ExportAndDeleteItemSpec> items = Lists.newArrayList();

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private ExportAndDeleteMailboxSpec() {
        this(-1);
    }

    public ExportAndDeleteMailboxSpec(int id) {
        this.id = id;
    }

    public void setItems(Iterable <ExportAndDeleteItemSpec> items) {
        this.items.clear();
        if (items != null) {
            Iterables.addAll(this.items,items);
        }
    }

    public ExportAndDeleteMailboxSpec addItem(ExportAndDeleteItemSpec item) {
        this.items.add(item);
        return this;
    }

    public int getId() { return id; }
    public List<ExportAndDeleteItemSpec> getItems() {
        return Collections.unmodifiableList(items);
    }
}
