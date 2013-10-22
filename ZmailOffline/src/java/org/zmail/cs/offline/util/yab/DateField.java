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

import java.util.Date;

/**
 * Structure aniversary or birthday YAB field.
 */
public final class DateField extends Field {
    private int day;
    private int month;
    private int year;

    public static final String BIRTHDAY = "birthday";
    public static final String ANNIVERSARY = "anniversary";

    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";

    public static DateField birthday(int day, int month, int year) {
        return new DateField(BIRTHDAY, day, month, year);
    }

    public static DateField birthday(Date date) {
        return new DateField(BIRTHDAY, date);
    }

    public static DateField anniversary(int day, int month, int year) {
        return new DateField(ANNIVERSARY, day, month, year);
    }

    public static DateField anniversary(Date date) {
        return new DateField(ANNIVERSARY, date);
    }

    public DateField(String name) {
        super(name);
    }

    public DateField(String name, int day, int month, int year) {
        super(name);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public DateField(String name, Date date) {
        super(name);
        setDate(date);
    }

    @Override
    public Type getType() {
        return Type.DATE;
    }
    
    public boolean isBirthday() {
        return BIRTHDAY.equals(getName());
    }

    public boolean isAnniversary() {
        return ANNIVERSARY.equals(getName());
    }
    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @SuppressWarnings("deprecation")
    public void setDate(Date date) {
        day = date.getDate();
        month = date.getMonth() + 1;
        year = date.getYear() + 1900;
    }

    @SuppressWarnings("deprecation")
    public Date getDate() {
        return new Date(year - 1900, month - 1, day);
    }
    
    @Override
    public Element toXml(Document doc, String tag) {
        Element e = super.toXml(doc, tag);
        if (day != -1) Xml.appendElement(e, DAY, day);
        if (month != -1) Xml.appendElement(e, MONTH, month);
        if (year != -1) Xml.appendElement(e, YEAR, year);
        return e;
    }

    @Override
    protected void parseXml(Element e) {
        super.parseXml(e);
        for (Element child : Xml.getChildren(e)) {
            String tag = child.getTagName();
            if (tag.equals(DAY)) {
                day = Xml.getIntValue(child);
            } else if (tag.equals(MONTH)) {
                month = Xml.getIntValue(child);
            } else if (tag.equals(YEAR)) {
                year = Xml.getIntValue(child);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d", day, month, year);
    }
}
