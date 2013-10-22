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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;

import java.util.Map;
import java.util.HashMap;

import org.zmail.cs.offline.util.Xml;

/**
 * YAB field type.
 */
public abstract class Field extends Entity {
    private String name;
    private int id = -1;
    private final Map<String, Boolean> flags;

    public static final String FID = "fid";

    public static enum Type {
        NAME, DATE, ADDRESS, SIMPLE
    }
    
    public Field() {
        flags = new HashMap<String, Boolean>();
    }

    public Field(String name) {
        this();
        setName(name);
    }

    public abstract Type getType();
    
    public boolean isName()    { return getType() == Type.NAME; }
    public boolean isDate()    { return getType() == Type.DATE; }
    public boolean isAddress() { return getType() == Type.ADDRESS; }
    public boolean isSimple()  { return getType() == Type.SIMPLE; }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setFlag(String name, boolean value) {
        flags.put(name, value);
    }

    public void setFlag(String name) {
        setFlag(name, true);
    }

    public void setFlags(String... names) {
        for (String name : names) {
            setFlag(name);
        }
    }

    public boolean isFlag(String name) {
        Boolean b = flags.get(name);
        return b != null && b;
    }

    public Map<String, Boolean> getFlags() {
        return flags;
    }
    
    public boolean isHome() {
        return isFlag(Flag.HOME);
    }

    public boolean isWork() {
        return isFlag(Flag.WORK);
    }

    public boolean isPersonal() {
        return isFlag(Flag.PERSONAL);
    }

    public Element toXml(Document doc, String tag) {
        Element e = doc.createElement(tag);
        if (id != -1) {
            e.setAttribute(FID, String.valueOf(id));
        }
        if (flags != null) {
            for (Map.Entry<String, Boolean> flag : flags.entrySet()) {
                e.setAttribute(flag.getKey(), flag.getValue().toString());
            }
        }
        return e;
    }

    @Override
    public Element toXml(Document doc) {
        return toXml(doc, getName());
    }
    
    public static Field fromXml(Element e) {
        Field field = newField(e.getTagName());
        field.parseXml(e);
        return field;
    }

    private static Field newField(String name) {
        if (name.equals(NameField.NAME)) {
            return new NameField();
        } else if (name.equals(AddressField.ADDRESS)) {
            return new AddressField();
        } else if (name.equals(DateField.BIRTHDAY) ||
                   name.equals(DateField.ANNIVERSARY)) {
            return new DateField(name);
        } else if (name.equals(CustomField.CUSTOM)) {
            return new CustomField();
        } else {
            return new SimpleField(name);
        }
    }
    
    protected void parseXml(Element e) {
        name = e.getTagName();
        id = Xml.getIntAttribute(e, FID);
        parseFlags(e);
    }

    private void parseFlags(Element e) {
        NamedNodeMap attrs = e.getAttributes();
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                Attr attr = (Attr) attrs.item(i);
                String s = attr.getValue();
                if ("true".equals(s) || "false".equals(s)) {
                    flags.put(attr.getName(), Boolean.parseBoolean(s));
                }
            }
        }
    }

    protected String getTextValue(Element e) {
        return denormalize(Xml.getTextValue(e));
    }
    
    // "De-normalize" YAB field value (see Yahoo! address book reference guide)
    private String denormalize(String s) {
        if (s == null) return null;
        return s.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&#39;", "'").replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&");
    }
}
