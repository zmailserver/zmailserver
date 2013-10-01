/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
 * Split by comma, space, CR, LF, dot.
 *
 * @author tim
 * @author ysasaki
 */
public final class FilenameTokenizer extends CharTokenizer {

    public FilenameTokenizer(Reader reader) {
        super(LuceneIndex.VERSION, reader);
    }

    @Override
    protected boolean isTokenChar(char c) {
        switch (c) {
            case ',':
            case ' ':
            case '\r':
            case '\n':
            case '.':
                return false;
            default:
                return true;
        }
    }

    @Override
    protected char normalize(char c) {
        return (char) NormalizeTokenFilter.normalize(c);
    }

}
