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
package org.zmail.cs.offline.ab;

import static org.zmail.common.mailbox.ContactConstants.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class Name {
    private String first;
    private String middle;
    private String last;
    private String prefix;
    private String suffix;

    private static final String PREFIX = "[A-Za-z]{2,}\\.";

    public Name() {}

    public String getFirst() {
        return first;
    }

    public boolean hasFirst() {
        return first != null;
    }
    
    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public boolean hasMiddle() {
        return middle != null;
    }
    
    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public boolean hasLast() {
        return last != null;
    }
    
    public void setLast(String last) {
        this.last = last;
    }
        
    public String getPrefix() {
        return prefix;
    }

    public boolean hasPrefix() {
        return prefix != null;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean hasSuffix() {
        return suffix != null;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isEmpty() {
        return !hasFirst() && !hasMiddle() && !hasLast() && !hasPrefix() &&
               !hasSuffix();
    }
    
    public static Name fromContactFields(Map<String, String> fields) {
        Name name = new Name();
        name.first = fields.get(A_firstName);
        name.middle = fields.get(A_middleName);
        name.last = fields.get(A_lastName);
        name.prefix = fields.get(A_namePrefix);
        name.suffix = fields.get(A_nameSuffix);
        return name;
    }

    public Map<String, String> toContactFields() {
        Map<String, String> fields = new HashMap<String, String>();
        fields.put(A_firstName, first);
        fields.put(A_middleName, middle);
        fields.put(A_lastName, last);
        fields.put(A_namePrefix, prefix);
        fields.put(A_nameSuffix, suffix);
        return fields;
    }
    
    public static Name parse(String spec) {
        Name name = new Name();
        List<String> parts = getParts(spec);
        int size = parts.size();
        if (size == 0) {
            return null;
        } else if (size == 1) {
            name.first = parts.get(0);
        } else {
            String part = parts.get(0);
            if (part.matches(PREFIX)) {
                name.prefix = part;
                parts = parts.subList(1, size--);
            }
            name.last = parts.get(--size);
            if (size > 0) {
                name.first = parts.get(0);
                if (size > 1) {
                    name.middle = join(parts.subList(1, size));
                }
            }
        }
        return name;
    }

    private static List<String> getParts(String spec) {
        List<String> parts = new ArrayList<String>();
        for (String part : spec.split(" ")) {
            part = part.trim();
            if (part.length() > 0) {
                parts.add(part);
            }
        }
        return parts;
    }

    private static String join(List<String> parts) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = parts.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(' ').append(it.next());
            }
        }
        return sb.toString();
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (prefix != null) sb.append(prefix).append(' ');
        if (first != null) sb.append(first).append(' ');
        if (middle != null) sb.append(middle).append(' ');
        if (last != null) sb.append(last).append(' ');
        return sb.toString().trim();
    }
}
