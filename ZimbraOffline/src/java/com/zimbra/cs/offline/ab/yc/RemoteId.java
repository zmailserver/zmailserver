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
package com.zimbra.cs.offline.ab.yc;

import com.zimbra.cs.offline.ab.SyncException;

public class RemoteId {
    private final Type type;
    private final int value;

    private enum Type {
        CONTACT, CATEGORY
    }

    public static final String CONTACT_PREFIX = "yc:contact:";  //TODO maybe need to change this and wipe out the original yahoo contacts from datasource, like a migration
    public static final String CATEGORY_PREFIX = "yc:category:";

    public static RemoteId contactId(int id) {
        return new RemoteId(Type.CONTACT, id);
    }

    public static RemoteId categoryId(int id) {
        return new RemoteId(Type.CATEGORY, id);
    }

    public static RemoteId parse(String s) throws SyncException {
        try {
            if (s.startsWith(CONTACT_PREFIX)) {
                String id = s.substring(CONTACT_PREFIX.length());
                return contactId(Integer.parseInt(id));
            } else if (s.startsWith(CATEGORY_PREFIX)) {
                String id = s.substring(CATEGORY_PREFIX.length());
                return categoryId(Integer.parseInt(id));
            }
        } catch (NumberFormatException e) {
        }
        throw new SyncException("Invalid ID syntax: " + s);
    }

    private RemoteId(Type type, int value) {
        assert value > 0 : "Invalid id value: " + value;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return value;
    }

    public boolean isContact() {
        return type == Type.CONTACT;
    }

    public boolean isCategory() {
        return type == Type.CATEGORY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RemoteId.class) {
            RemoteId rid = (RemoteId) obj;
            return type == rid.type && value == rid.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return type.hashCode() ^ Integer.valueOf(value).hashCode();
    }

    @Override
    public String toString() {
        switch (type) {
        case CONTACT:
            return CONTACT_PREFIX + String.valueOf(value);
        case CATEGORY:
            return CATEGORY_PREFIX + String.valueOf(value);
        default:
            throw new AssertionError();
        }
    }
}
