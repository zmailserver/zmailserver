/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package org.zmail.cs.index.analysis;

import java.io.StringReader;
import java.util.Collections;

import org.apache.lucene.analysis.TokenFilter;
import org.junit.Assert;
import org.junit.Test;

import org.zmail.cs.index.ZmailAnalyzerTest;

/**
 * Unit test for {@link ContactTokenFilter}.
 *
 * @author ysasaki
 */
public class ContactTokenFilterTest {

    @Test
    public void contactDataFilter() throws Exception {
        AddrCharTokenizer tokenizer = new AddrCharTokenizer(new StringReader("all-snv"));
        TokenFilter filter = new ContactTokenFilter(tokenizer);
        Assert.assertEquals(Collections.singletonList("all-snv"),
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader("."));
        Assert.assertEquals(Collections.EMPTY_LIST,
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader(".. ."));
        Assert.assertEquals(Collections.singletonList(".."),
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader(".abc"));
        Assert.assertEquals(Collections.singletonList(".abc"),
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader("a"));
        Assert.assertEquals(Collections.singletonList("a"),
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader("test.com"));
        Assert.assertEquals(Collections.singletonList("test.com"),
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader("user1@zim"));
        Assert.assertEquals(Collections.singletonList("user1@zim"),
                ZmailAnalyzerTest.toTokens(filter));

        tokenizer.reset(new StringReader("user1@zmail.com"));
        Assert.assertEquals(Collections.singletonList("user1@zmail.com"),
                ZmailAnalyzerTest.toTokens(filter));
    }

}
