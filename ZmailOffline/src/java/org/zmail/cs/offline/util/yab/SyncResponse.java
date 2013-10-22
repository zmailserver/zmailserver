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

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

import com.zimbra.cs.offline.util.Xml;

public class SyncResponse extends Response {
    private long lastModifiedTime = -1;
    private int revision = -1;
    private final List<Category> categories;
    private final List<Result> results;
    private final List<SyncResponseEvent> events;

    private static final String TAG = "sync-response";
    
    private static final String LMT = "lmt";
    private static final String REV = "rev";
    
    public static SyncResponse fromXml(Element e) {
        return new SyncResponse().parseXml(e);
    }

    private SyncResponse() {
        categories = new ArrayList<Category>();
        results = new ArrayList<Result>();
        events = new ArrayList<SyncResponseEvent>();
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public int getRevision() {
        return revision;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Result> getResults() {
        return results;
    }

    public List<SyncResponseEvent> getEvents() {
        return events;
    }

    public List<Contact> getAddedContacts() {
        return getContacts(SyncResponseEvent.Type.ADD_CONTACT);
    }

    public List<Contact> getUpdatedContacts() {
        return getContacts(SyncResponseEvent.Type.UPDATE_CONTACT);
    }

    public List<Integer> getRemovedContacts() {
        List<Integer> cids = new ArrayList<Integer>();
        for (SyncResponseEvent event : events) {
            switch (event.getType()) {
            case REMOVE_CONTACT:
                cids.add(event.getContactId());
            }
        }
        return cids;
    }
    
    private List<Contact> getContacts(SyncResponseEvent.Type type) {
        List<Contact> contacts = new ArrayList<Contact>();
        for (SyncResponseEvent event : events) {
            if (event.getType() == type) {
                contacts.add(event.getContact());
            }
        }
        return contacts;
    }

    private SyncResponse parseXml(Element e) {
        if (!e.getTagName().equals(TAG)) {
            throw new IllegalArgumentException(
                "Not a '" + TAG + "' element: " + e.getTagName());
        }
        lastModifiedTime = Xml.getIntAttribute(e, LMT);
        revision = Xml.getIntAttribute(e, REV);
        for (Element child : Xml.getChildren(e)) {
            String tag = child.getTagName();
            if (tag.equals(Category.TAG)) {
                categories.add(Category.fromXml(child));
            } else if (tag.equals(SuccessResult.TAG)) {
                results.add(SuccessResult.fromXml(child));
            } else if (tag.equals(ErrorResult.TAG)) {
                results.add(ErrorResult.fromXml(child));
            } else {
                events.add(SyncResponseEvent.fromXml(child));
            }
        }
        return this;
    }

    @Override
    public Element toXml(Document doc) {
        Element e = doc.createElement(TAG);
        if (lastModifiedTime != -1) {
            Xml.appendElement(e, LMT, lastModifiedTime);
        }
        if (revision != -1) {
            Xml.appendElement(e, REV, revision);
        }
        for (Category category : categories) {
            e.appendChild(category.toXml(doc));
        }
        for (Result result : results) {
            e.appendChild(result.toXml(doc));
        }
        for (SyncResponseEvent event : events) {
            e.appendChild(event.toXml(doc));
        }
        return e;
    }
}
