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
package org.zmail.cs.offline.util.yab;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.zmail.cs.offline.util.Xml;

import java.util.List;

public class Bucket extends Entity {
    private int id;
    private int count;
    private Contact start;
    private Contact end;

    public static final String TAG = "bucket";

    private static final String ID = "id";
    private static final String CONTACT_COUNT = "contact-count";
    
    private Bucket() {}

    public int getId() { return id; }
    public int getContactCount() { return count; }
    public Contact getStartContact() { return start; }
    public Contact getEndContact() { return end; }

    public static Bucket fromXml(Element e) {
        return new Bucket().parseXml(e);
    }

    private Bucket parseXml(Element e) {
        if (!e.getTagName().equals(TAG)) {
            throw new IllegalArgumentException(
                "Not a '" + TAG + "' element: " + e.getTagName());
        }
        id = Xml.getIntAttribute(e, "id");
        count = Xml.getIntAttribute(e, "contact-count");
        List<Element> children = Xml.getChildren(e);
        if (children.size() != 2) {
            throw new IllegalArgumentException("Invalid '" + TAG + "' element");
        }
        start = Contact.fromXml(children.get(0));
        end = Contact.fromXml(children.get(1));
        return this;
    }

    @Override
    public Element toXml(Document doc) {
        Element e = doc.createElement(TAG);
        Xml.appendElement(e, ID, String.valueOf(id));
        Xml.appendElement(e, CONTACT_COUNT, String.valueOf(count));
        e.appendChild(start.toXml(doc));
        e.appendChild(end.toXml(doc));
        return e;
    }
}
