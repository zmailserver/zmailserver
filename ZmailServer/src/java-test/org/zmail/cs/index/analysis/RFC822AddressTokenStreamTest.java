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
package org.zmail.cs.index.analysis;

import java.util.Arrays;

import org.apache.lucene.analysis.TokenStream;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Strings;
import org.zmail.cs.index.ZmailAnalyzerTest;

/**
 * Unit test for {@link RFC822AddressTokenStream}.
 *
 * @author ysasaki
 */
public final class RFC822AddressTokenStreamTest {

    @Test
    public void single() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream("user@domain.com");
        Assert.assertEquals(Arrays.asList("user@domain.com", "user", "@domain.com", "domain.com", "domain", "@domain"),
                ZmailAnalyzerTest.toTokens(stream));

        stream = new RFC822AddressTokenStream("\"Tim Brown\" <first.last@sub.domain.com>");
        Assert.assertEquals(Arrays.asList("tim", "brown", "first.last@sub.domain.com", "first.last", "first", "last",
                "@sub.domain.com", "sub.domain.com", "domain", "@domain"), ZmailAnalyzerTest.toTokens(stream));
    }

    @Test
    public void multi() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream(
                "\"User One\" <user.1@zmail.com>, \"User Two\" <user.2@zmail.com>, \"User Three\" <user.3@zmail.com>");
        Assert.assertEquals(Arrays.asList(
                "user", "one", "user.1@zmail.com", "user.1", "user", "1",
                "@zmail.com", "zmail.com", "zmail", "@zmail",
                "user", "two", "user.2@zmail.com", "user.2", "user", "2",
                "@zmail.com", "zmail.com", "zmail", "@zmail",
                "user", "three", "user.3@zmail.com", "user.3", "user", "3",
                "@zmail.com", "zmail.com", "zmail", "@zmail"),
                ZmailAnalyzerTest.toTokens(stream));
    }

    @Test
    public void comment() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream(
                "Pete(A wonderful \\) chap) <pete(his account)@silly.test(his host)>");
        Assert.assertEquals(Arrays.asList("pete", "a", "wonderful", "chap", "pete", "his", "account", "@silly.test",
                "his", "host", "pete@silly.test", "pete", "@silly.test", "silly.test"),
                ZmailAnalyzerTest.toTokens(stream));
    }

    @Test
    public void topPrivateDomain() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream("support@zmail.com");
        Assert.assertEquals(Arrays.asList("support@zmail.com", "support", "@zmail.com", "zmail.com", "zmail",
                "@zmail"), ZmailAnalyzerTest.toTokens(stream));

        stream = new RFC822AddressTokenStream("support@zmail.vmware.co.jp");
        Assert.assertEquals(Arrays.asList("support@zmail.vmware.co.jp", "support", "@zmail.vmware.co.jp",
                "zmail.vmware.co.jp", "vmware", "@vmware"), ZmailAnalyzerTest.toTokens(stream));

        stream = new RFC822AddressTokenStream("test@co.jp");
        Assert.assertEquals(Arrays.asList("test@co.jp", "test", "@co.jp", "co.jp"),
                ZmailAnalyzerTest.toTokens(stream));
    }

    @Test
    public void reset() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream("user@domain.com");
        stream.reset();
        Assert.assertEquals(Arrays.asList("user@domain.com", "user", "@domain.com", "domain.com", "domain", "@domain"),
                ZmailAnalyzerTest.toTokens(stream));
        stream.reset();
        Assert.assertEquals(Arrays.asList("user@domain.com", "user", "@domain.com", "domain.com", "domain", "@domain"),
                ZmailAnalyzerTest.toTokens(stream));
    }

    @Test
    public void limit() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream("<" + Strings.repeat("x.", 600) + "x@zmail.com>");
        Assert.assertEquals(512, ZmailAnalyzerTest.toTokens(stream).size());
    }

    @Test
    public void japanese() throws Exception {
        TokenStream stream = new RFC822AddressTokenStream("=?utf-8?B?5qOu44CA5qyh6YOO?= <jiro.mori@zmail.com>");
        Assert.assertEquals(Arrays.asList("\u68ee", "\u6b21\u90ce", "jiro.mori@zmail.com", "jiro.mori", "jiro", "mori",
                "@zmail.com", "zmail.com", "zmail", "@zmail"),  ZmailAnalyzerTest.toTokens(stream));
    }

}
