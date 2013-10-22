/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.offline.ab;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

public class Change {
    public enum Type { ADD, UPDATE, DELETE }

    private final Type type;
    private final int itemId;

    public static Change add(int itemId) {
        return new Change(Type.ADD, itemId);
    }

    public static Change update(int itemId) {
        return new Change(Type.UPDATE, itemId);
    }

    public static Change delete(int itemId) {
        return new Change(Type.DELETE, itemId);
    }
    
    private Change(Type type, int itemId) {
        this.type = type;
        this.itemId = itemId;
    }

    public Type getType() { return type; }
    public int getItemId() { return itemId; }

    public boolean isAdd() { return type == Type.ADD; }
    public boolean isUpdate() { return type == Type.UPDATE; }
    public boolean isDelete() { return type == Type.DELETE; }

    @Override
    public String toString() {
        ToStringHelper helper = Objects.toStringHelper(this);
        return helper.add("operation", this.type.name())
            .add("itemId", this.itemId).toString();
    }
}
