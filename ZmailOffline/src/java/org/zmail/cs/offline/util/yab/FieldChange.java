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

/**
 * Represents a Contact field change.
 */
public class FieldChange extends Entity {
    private final Type type;
    private final Field field;
    private final int fid;

    public static enum Type {
        ADD, UPDATE, REMOVE
    }

    public static FieldChange add(Field field) {
        return new FieldChange(Type.ADD, field, -1);
    }

    public static FieldChange remove(int fid) {
        return new FieldChange(Type.REMOVE, null, fid);
    }

    public static FieldChange update(Field field) {
        return new FieldChange(Type.UPDATE, field, field.getId());
    }
    
    private FieldChange(Type type, Field field, int fid) {
        this.type = type;
        this.field = field;
        this.fid = fid;
    }

    public Type getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    public int getFieldId() {
        return fid;
    }

    @Override
    public Element toXml(Document doc) {
        switch (type) {
        case ADD:
            return field.toXml(doc, "add-" + field.getName());
        case UPDATE:
            return field.toXml(doc, "update-" + field.getName());
        case REMOVE:
            Element e = doc.createElement("remove-field");
            e.setAttribute(Field.FID, String.valueOf(fid));
            return e;
        }
        return null;
    }
}
