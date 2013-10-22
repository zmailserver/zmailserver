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
package com.zimbra.cs.mailclient.imap;

import com.zimbra.cs.mailclient.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * IMAP message BODY response:
 *
 * body-response    = "BODY" section ["<" number ">"] SP nstring
 * section          = "[" [section-spec] "]"
 * section-spec     = section-msgtext / (section-part ["." section-text])
 * section-msgtext  = "HEADER" / "HEADER.FIELDS" [".NOT"] SP header-list /
 *                    "TEXT"
 * section-text     = section-msgtext / "MIME"
 * section-part     = nz-number *("." nz-number)
 * header-list      = "(" header-fld-name *(SP header-fld-name) ")"
 * header-fld-name  = astring
 */
public final class Body {
    private String part;
    private CAtom section;
    private String[] fieldNames;
    private long origin = -1;
    private Object data;

    public static Body read(ImapInputStream is) throws IOException {
        Body b = new Body();
        b.readBody(is);
        return b;
    }

    private void readBody(ImapInputStream is) throws IOException {
        is.skipSpaces();
        is.skipChar('[');
        is.skipSpaces();
        if (is.peekChar() != ']') {
            readSection(is);
        }
        is.skipSpaces();
        is.skipChar(']');
        is.skipSpaces();
        if (is.match('<')) {
            origin = is.readNZNumber();
            is.skipSpaces();
            is.skipChar('>');
        }
        data = is.readFetchData();
    }

    private void readSection(ImapInputStream is) throws IOException {
        if (is.isNumber()) {
            part = readPart(is);
        }
        is.skipSpaces();
        if (is.peek() == ']') return; // No section text
        Atom sec = is.readAtom();
        section = sec.getCAtom();
        switch (section) {
        case HEADER: case TEXT:
            break;
        case HEADER_FIELDS: case HEADER_FIELDS_NOT:
            is.skipChar(' ');
            is.skipSpaces();
            fieldNames = readFieldNames(is);
            break;
        case MIME:
            if (part == null) {
                throw new ParseException(
                    "BODY[MIME] response missing section part");
            }
            break;
        default:
            throw new ParseException("Invalid BODY section type: " + sec);
        }
    }

    private static String readPart(ImapInputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            sb.append(is.readNZNumber());
            if (is.peek() == ']') break;
            is.skipChar('.');
            if (!is.isNumber()) break;
            sb.append('.');
        }
        return sb.toString();
    }

    private static String[] readFieldNames(ImapInputStream is)
        throws IOException {
        ArrayList<String> names = new ArrayList<String>();
        is.skipChar('(');
        is.skipSpaces();
        while (!is.match(')')) {
            names.add(is.readAString());
            is.skipSpaces();
        }
        return names.toArray(new String[names.size()]);
    }

    public String getPart() { return part; }
    public CAtom getSection() { return section; }
    public String[] getFieldNames() { return fieldNames; }
    public long getOrigin() { return origin; }
    public Object getData() { return data; }

    public ImapData getImapData() {
        if (data != null && !(data instanceof ImapData)) {
            throw new UnsupportedOperationException();
        }
        return (ImapData) data;
    }
    
    public void dispose() {
        if (data instanceof ImapData) {
            ((ImapData) data).dispose();
        }
    }
}
