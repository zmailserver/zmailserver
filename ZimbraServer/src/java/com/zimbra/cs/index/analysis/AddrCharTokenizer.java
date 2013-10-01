/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012, 2013 VMware, Inc.
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
package com.zimbra.cs.index.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.CharTokenizer;

import com.zimbra.cs.index.LuceneIndex;

/**
 * Tokenizer for email addresses.
 *
 * @author tim
 * @author ysasaki
 */
public final class AddrCharTokenizer extends CharTokenizer {

    public AddrCharTokenizer(Reader reader) {
        super(LuceneIndex.VERSION, reader);
    }

    @Override
    protected boolean isTokenChar(int ch) {
        if (Character.isWhitespace(ch)) {
            return false;
        }
        switch (ch) {
            case '\u3000': // fullwidth space
            case '<':
            case '>':
            case '\"':
            case ',':
            case '\'':
            case '(':
            case ')':
            case '[':
            case ']':
                return false;
        }
        return true;
    }

    @Override
    protected int normalize(int c) {
        return (char) NormalizeTokenFilter.normalize(c);
    }

}
