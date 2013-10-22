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

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public final class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private static final String EOL = "\r\n";

    private static final Pattern STATE_ZIP =
        Pattern.compile("([a-zA-Z ]+) +([0-9][^ ]*)");

    public Address() {}
    
    public String getStreet() {
        return street;
    }

    public boolean hasStreet() {
        return street != null;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public boolean hasCity() {
        return city != null;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public boolean hasState() {
        return state != null;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean hasPostalCode() {
        return postalCode != null;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public boolean hasCountry() {
        return country != null;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isEmpty() {
        return !hasStreet() && !hasCity() && !hasState() &&
               !hasPostalCode() && !hasCountry();
    }

    public static Address fromHomeContactFields(Map<String, String> fields) {
        Address addr = new Address();
        addr.street = fields.get(A_homeStreet);
        addr.city = fields.get(A_homeCity);
        addr.state = fields.get(A_homeState);
        addr.postalCode = fields.get(A_homePostalCode);
        addr.country = fields.get(A_homeCountry);
        return addr;
    }

    public static Address fromWorkContactFields(Map<String, String> fields) {
        Address addr = new Address();
        addr.street = fields.get(A_workStreet);
        addr.city = fields.get(A_workCity);
        addr.state = fields.get(A_workState);
        addr.postalCode = fields.get(A_workPostalCode);
        addr.country = fields.get(A_workCountry);
        return addr;
    }
        
    public static Address fromOtherContactFields(Map<String, String> fields) {
        Address addr = new Address();
        addr.street = fields.get(A_otherStreet);
        addr.city = fields.get(A_otherCity);
        addr.state = fields.get(A_otherState);
        addr.postalCode = fields.get(A_otherPostalCode);
        addr.country = fields.get(A_otherCountry);
        return addr;
    }

    public Map<String, String> toHomeContactFields() {
        Map<String, String> fields = new HashMap<String, String>(5);
        fields.put(A_homeStreet, street);
        fields.put(A_homeCity, city);
        fields.put(A_homeState, state);
        fields.put(A_homePostalCode, postalCode);
        fields.put(A_homeCountry, country);
        return fields;
    }

    public Map<String, String> toWorkContactFields() {
        Map<String, String> fields = new HashMap<String, String>(5);
        fields.put(A_workStreet, street);
        fields.put(A_workCity, city);
        fields.put(A_workState, state);
        fields.put(A_workPostalCode, postalCode);
        fields.put(A_workCountry, country);
        return fields;
    }

    public Map<String, String> toOtherContactFields() {
        Map<String, String> fields = new HashMap<String, String>(5);
        fields.put(A_otherStreet, street);
        fields.put(A_otherCity, city);
        fields.put(A_otherState, state);
        fields.put(A_otherPostalCode, postalCode);
        fields.put(A_otherCountry, country);
        return fields;
    }
    
    public static Address parse(String spec) {
        Address addr = new Address();
        List<String> parts = getParts(spec);
        if (parts.isEmpty()) return null;
        int size = parts.size();
        if (size > 1 && addr.parseStateZip(parts.get(size - 2))) {
            addr.country = parts.get(--size);
            parts = parts.subList(0, --size);
        } else if (addr.parseStateZip(parts.get(size - 1))) {
            parts = parts.subList(0, --size);
        } else {
            addr.state = parts.get(--size);
            parts = parts.subList(0, size);
        }
        if (size > 1) {
            String part = parts.get(size - 1);
            if (!part.matches("[0-9].*")) {
                addr.city = part;
                parts = parts.subList(0, --size);
            }
        }
        if (size > 0) {
            addr.street = join(parts);
        }
        return addr;
    }

    private boolean parseStateZip(String part) {
        Matcher m = STATE_ZIP.matcher(part);
        if (m.matches()) {
            state = m.group(1);
            postalCode = m.group(2);
            return true;
        }
        return false;
    }

    private static String join(List<String> parts) {
        StringBuilder sb = new StringBuilder();
        if (parts.isEmpty()) {
            return null;
        }
        sb.append(parts.get(0));
        for (int i = 1; i < parts.size(); i++) {
            sb.append(", ").append(parts.get(i));
        }
        return sb.toString();
    }

    private static List<String> getParts(String spec) {
        List<String> parts = new ArrayList<String>();
        for (String part : spec.split("[,\\n\\r]+")) {
            part = part.trim();
            if (part.length() > 0) {
                parts.add(part);
            }
        }
        return parts;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (street != null) sb.append(street).append(EOL);
        if (city != null) {
            sb.append(city).append(state != null ? ", " : EOL);
        }
        if (state != null) {
            sb.append(state).append(postalCode != null ? "  " : EOL);
        }
        if (postalCode != null) sb.append(postalCode).append(EOL);
        if (country != null) sb.append(country);
        return sb.toString().trim();
    }
}
