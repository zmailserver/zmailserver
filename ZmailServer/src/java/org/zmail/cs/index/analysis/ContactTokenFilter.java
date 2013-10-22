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

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Swallow dots, but include dots in a token only when it is not the only char
 * in the token.
 */
public final class ContactTokenFilter extends TokenFilter {
    private CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);

    public ContactTokenFilter(AddrCharTokenizer input) {
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        while (input.incrementToken()) {
            if (termAttr.length() == 1 && termAttr.charAt(0) == '.') {
                continue; // swallow dot
            } else {
                return true;
            }
        }
        return false;
    }

}
