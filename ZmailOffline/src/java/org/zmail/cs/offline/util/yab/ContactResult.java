/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package com.zimbra.cs.offline.util.yab;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class ContactResult extends Result {
    private AddAction addAction;
    private Contact contact;

    public static final String TAG = "contact";

    private ContactResult() {}

    @Override
    public boolean isError() {
        return false;
    }
    
    public boolean isAdded() {
        return addAction == AddAction.ADD;
    }

    public boolean isMerged() {
        return addAction == AddAction.MERGE;
    }

    public Contact getContact() {
        return contact;
    }
    
    public static ContactResult fromXml(Element e) {
        return new ContactResult().parseXml(e);
    }

    private ContactResult parseXml(Element e) {
        assert e.getTagName().equals(TAG);
        addAction = AddAction.fromXml(e);
        if (addAction == null) {
            throw new IllegalArgumentException("Missing add-action element");
        }
        contact = Contact.fromXml(e);
        return this;
    }

    @Override
    public Element toXml(Document doc) {
        Element e = contact.toXml(doc, TAG);
        addAction.setAttribute(e);
        return e;
    }
}
