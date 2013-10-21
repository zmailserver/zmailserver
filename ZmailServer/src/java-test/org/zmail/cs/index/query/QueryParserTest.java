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
package org.zmail.cs.index.query;

import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.lucene.document.DateTools;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.index.ZmailAnalyzer;
import org.zmail.cs.index.query.parser.QueryParser;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxTestUtil;

/**
 * Unit test for {@link QueryParser}.
 *
 * @author ysasaki
 */
public final class QueryParserTest {
    private static QueryParser parser;

    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();
        prov.createAccount("test@zmail.com", "secret", new HashMap<String, Object>());

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        parser = new QueryParser(mbox, ZmailAnalyzer.getInstance());
    }

    @Test
    public void defaultClause() throws Exception {
        String src = "zmail";
        Assert.assertEquals("Q(l.content:zmail)", Query.toString(parser.parse(src)));
    }

    @Test
    public void modifier() throws Exception {
        String src = "+content:zmail";
        Assert.assertEquals("+Q(l.content:zmail)", Query.toString(parser.parse(src)));

        src = "-content:zmail";
        Assert.assertEquals("-Q(l.content:zmail)", Query.toString(parser.parse(src)));

        src = "not content:zmail";
        Assert.assertEquals("-Q(l.content:zmail)", Query.toString(parser.parse(src)));

        src = "from:(+@zmail.com)";
        Assert.assertEquals("(+Q(from:@zmail.com))", Query.toString(parser.parse(src)));

        src = "from:(-@zmail.com)";
        Assert.assertEquals("(-Q(from:@zmail.com))", Query.toString(parser.parse(src)));
    }

    @Test
    public void sortBy() throws Exception {
        String src = "foo sort:dateAsc and bar";
        Assert.assertEquals("Q(l.content:foo) && Q(l.content:bar)", Query.toString(parser.parse(src)));
        Assert.assertEquals("dateAsc", parser.getSortBy());
    }

    @Test
    public void text() throws Exception {
        String src = "x or y";
        Assert.assertEquals("Q(l.content:x) || Q(l.content:y)", Query.toString(parser.parse(src)));

        src = "(x or y)";
        Assert.assertEquals("(Q(l.content:x) || Q(l.content:y))", Query.toString(parser.parse(src)));

        src = "(x or y) and in:inbox";
        Assert.assertEquals("(Q(l.content:x) || Q(l.content:y)) && Q(IN:Inbox)", Query.toString(parser.parse(src)));

        src = "\"This is a \\\"phrase\\\" query\"";
        Assert.assertEquals("Q(l.content:phrase,query)", Query.toString(parser.parse(src)));
    }

    @Test
    public void folder() throws ServiceException {
        String src = "in:inbox";
        Assert.assertEquals("Q(IN:Inbox)", Query.toString(parser.parse(src)));

        src = "in:(trash -junk)";
        Assert.assertEquals("(Q(IN:Trash) && -Q(IN:Junk))", Query.toString(parser.parse(src)));
    }

    @Test
    public void date() throws Exception {
        String src = "date:-4d";
        Assert.assertEquals("Q(DATE:DATE," + getDate(-4) + "-" + getDate(-3) + ")", Query.toString(parser.parse(src)));

        src = "date:\"-4d\"";
        Assert.assertEquals("Q(DATE:DATE," + getDate(-4) + "-" + getDate(-3) + ")", Query.toString(parser.parse(src)));

        src = "(a or b) and before:1/1/2009 and -subject:\"quoted string\"";
        Assert.assertEquals("(Q(l.content:) || Q(l.content:b)) && Q(DATE:BEFORE,196912312359-200901010000) && " +
                "-Q(subject:quoted,string)", Query.toString(parser.parse(src)));

        src = "date:(01/01/2001 02/02/2002)";
        Assert.assertEquals("(Q(DATE:DATE,200101010000-200101020000) && Q(DATE:DATE,200202020000-200202030000))",
                Query.toString(parser.parse(src)));

        src = "date:-1d date:(01/01/2001 02/02/2002)";
        Assert.assertEquals("Q(DATE:DATE," + getDate(-1) + "-" + getDate(0) +
                ") && (Q(DATE:DATE,200101010000-200101020000) && Q(DATE:DATE,200202020000-200202030000))",
                Query.toString(parser.parse(src)));

        src = "date:(-1d or -2d)";
        Assert.assertEquals("(Q(DATE:DATE," + getDate(-1) + "-" + getDate(0) + ") || Q(DATE:DATE," +
                getDate(-2) + "-" + getDate(-1) +"))", Query.toString(parser.parse(src)));

        src = "date:\"+1d\"";
        Assert.assertEquals("Q(DATE:DATE," + getDate(1) + "-" + getDate(2) + ")", Query.toString(parser.parse(src)));

        src = "date:+2w";
        Assert.assertEquals("Q(DATE:DATE," + getWeek(2) + "-" + getWeek(3) + ")", Query.toString(parser.parse(src)));

        src = "not date:(1/1/2004 or 2/1/2004)";
        Assert.assertEquals("-(Q(DATE:DATE,200401010000-200401020000) || Q(DATE:DATE,200402010000-200402020000))",
                Query.toString(parser.parse(src)));
    }

    private String getDate(int day) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        return DateTools.dateToString(cal.getTime(), DateTools.Resolution.MINUTE);
    }

    private String getWeek(int week) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.add(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        return DateTools.dateToString(cal.getTime(), DateTools.Resolution.MINUTE);
    }

    /**
     * Validate that date queries parse to the proper ranges. The only caveat here is that a query like
     * {@code date:>foo} turns into the range {@code (foo+1, true, -1, false)} instead of the more obvious one
     * {@code (foo, false, -1, false)} -- this is a quirk of the query parsing code. Both are correct.
     */
    @Test
    public void dateRange() throws Exception {
        final long JAN1 = 1167609600000L;
        final long JAN2 = 1167696000000L;

        String src = "date:01/01/2007";
        DateQuery dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN1, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(JAN2, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:<01/01/2007";
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(-1L, dq.getLowestTime());
        Assert.assertEquals(false, dq.isLowestInclusive());
        Assert.assertEquals(JAN1, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "before:01/01/2007";
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(-1L, dq.getLowestTime());
        Assert.assertEquals(false, dq.isLowestInclusive());
        Assert.assertEquals(JAN1, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:<=01/01/2007";
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(-1L, dq.getLowestTime());
        Assert.assertEquals(false, dq.isLowestInclusive());
        Assert.assertEquals(JAN2, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:>01/01/2007";
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN2, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(-1L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "after:01/01/2007";
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN2, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(-1L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:>=01/01/2007";
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN1, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(-1L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN1, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(JAN1 + 1000L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:<" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(-1L, dq.getLowestTime());
        Assert.assertEquals(false, dq.isLowestInclusive());
        Assert.assertEquals(JAN1, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "before:" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(-1L, dq.getLowestTime());
        Assert.assertEquals(false, dq.isLowestInclusive());
        Assert.assertEquals(JAN1, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:<=" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(-1L, dq.getLowestTime());
        Assert.assertEquals(false, dq.isLowestInclusive());
        Assert.assertEquals(JAN1 + 1000, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:>" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN1 + 1000L, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(-1L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "after:" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN1 + 1000L, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(-1L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());

        src = "date:>=" + JAN1;
        dq = (DateQuery) parser.parse(src).get(0);
        Assert.assertEquals(JAN1, dq.getLowestTime());
        Assert.assertEquals(true, dq.isLowestInclusive());
        Assert.assertEquals(-1L, dq.getHighestTime());
        Assert.assertEquals(false, dq.isHighestInclusive());
    }

    @Test
    public void braced() throws Exception {
        String src = "item:{1,2,3}";
        Assert.assertEquals("Q(ITEMID," + MockProvisioning.DEFAULT_ACCOUNT_ID + ":1," +
                MockProvisioning.DEFAULT_ACCOUNT_ID + ":2," + MockProvisioning.DEFAULT_ACCOUNT_ID + ":3)",
                Query.toString(parser.parse(src)));

        src = "item:({1,2,3} or {4,5,6})";
        Assert.assertEquals("(Q(ITEMID," + MockProvisioning.DEFAULT_ACCOUNT_ID + ":1," +
                MockProvisioning.DEFAULT_ACCOUNT_ID + ":2," + MockProvisioning.DEFAULT_ACCOUNT_ID + ":3) || Q(ITEMID," +
                MockProvisioning.DEFAULT_ACCOUNT_ID + ":4," + MockProvisioning.DEFAULT_ACCOUNT_ID + ":5," +
                MockProvisioning.DEFAULT_ACCOUNT_ID + ":6))", Query.toString(parser.parse(src)));
    }

    @Test
    public void builtIn() throws Exception {
        String src = "is:unread is:remote";
        Assert.assertEquals("Q(TAG:\\Unread,UNREAD) && Q(UNDER:REMOTE)", Query.toString(parser.parse(src)));
    }

    @Test
    public void address() throws Exception {
        String src = "from:foo@bar.com";
        Assert.assertEquals("Q(from:foo@bar.com)", Query.toString(parser.parse(src)));

        src = "from:\"foo bar\"";
        Assert.assertEquals("Q(from:foo,bar)", Query.toString(parser.parse(src)));

        src = "to:foo@bar.com";
        Assert.assertEquals("Q(to:foo@bar.com)", Query.toString(parser.parse(src)));

        src = "to:\"foo bar\"";
        Assert.assertEquals("Q(to:foo,bar)", Query.toString(parser.parse(src)));

        src = "cc:foo@bar.com";
        Assert.assertEquals("Q(cc:foo@bar.com)", Query.toString(parser.parse(src)));

        src = "cc:\"foo bar\"";
        Assert.assertEquals("Q(cc:foo,bar)", Query.toString(parser.parse(src)));
    }

    @Test
    public void subject() throws Exception {
        String src = "subject:\"foo\"";
        Assert.assertEquals("Q(subject:foo)", Query.toString(parser.parse(src)));

        src = "subject:\"foo bar\" and content:\"baz gub\"";
        Assert.assertEquals("Q(subject:foo,bar) && Q(l.content:baz,gub)", Query.toString(parser.parse(src)));

        src = "subject:this_is_my_subject subject:\"this is_my_subject\"";
        Assert.assertEquals("Q(subject:my,subject) && Q(subject:my,subject)", Query.toString(parser.parse(src)));
    }

    @Test
    public void has() throws Exception {
        String src = "has:attachment has:phone has:url";
        Assert.assertEquals("Q(attachment:any) && Q(has:phone) && Q(has:url)", Query.toString(parser.parse(src)));
    }

    @Test
    public void filename() throws Exception {
        String src = "filename:foo filename:(\"foo\" \"foo bar\" gub)";
        Assert.assertEquals("Q(filename:foo) && (Q(filename:foo) && Q(filename:foo,bar) && Q(filename:gub))",
                Query.toString(parser.parse(src)));
    }

    @Test
    public void type() throws Exception {
        String src = "type:attachment";
        Assert.assertEquals("Q(type:attachment)", Query.toString(parser.parse(src)));

        src = "type:text";
        Assert.assertEquals("Q(type:text)", Query.toString(parser.parse(src)));

        src = "type:application";
        Assert.assertEquals("Q(type:application)", Query.toString(parser.parse(src)));

        src = "type:word type:msword";
        Assert.assertEquals("Q(type:application/msword) && Q(type:application/msword)",
                Query.toString(parser.parse(src)));

        src = "type:excel type:xls";
        Assert.assertEquals("Q(type:application/vnd.ms-excel) && Q(type:application/vnd.ms-excel)",
                Query.toString(parser.parse(src)));

        src = "type:ppt";
        Assert.assertEquals("Q(type:application/vnd.ms-powerpoint)", Query.toString(parser.parse(src)));

        src = "type:pdf";
        Assert.assertEquals("Q(type:application/pdf)", Query.toString(parser.parse(src)));

        src = "type:ms-tnef";
        Assert.assertEquals("Q(type:application/ms-tnef)", Query.toString(parser.parse(src)));

        src = "type:image type:jpeg type:gif type:bmp";
        Assert.assertEquals("Q(type:image) && Q(type:image/jpeg) && Q(type:image/gif) && Q(type:image/bmp)",
                Query.toString(parser.parse(src)));

        src = "type:none type:any";
        Assert.assertEquals("Q(type:none) && Q(type:any)", Query.toString(parser.parse(src)));
    }

    @Test
    public void tag() throws Exception {
        String src = "is:(read unread)";
        Assert.assertEquals("(Q(TAG:\\Unread,READ) && Q(TAG:\\Unread,UNREAD))", Query.toString(parser.parse(src)));

        src = "is:(flagged unflagged)";
        Assert.assertEquals("(Q(TAG:\\Flagged,FLAGGED) && Q(TAG:\\Flagged,UNFLAGGED))",
                Query.toString(parser.parse(src)));

        src = "is:(\"sent\" received)";
        Assert.assertEquals("(Q(TAG:\\Sent,SENT) && Q(TAG:\\Sent,RECEIVED))", Query.toString(parser.parse(src)));

        src = "is:(replied unreplied)";
        Assert.assertEquals("(Q(TAG:\\Answered,REPLIED) && Q(TAG:\\Answered,UNREPLIED))",
                Query.toString(parser.parse(src)));

        src = "is:(forwarded unforwarded)";
        Assert.assertEquals("(Q(TAG:\\Forwarded,FORWARDED) && Q(TAG:\\Forwarded,UNFORWARDED))",
                Query.toString(parser.parse(src)));
    }

    @Test
    public void priority() throws Exception {
        String src = "priority:high";
        Assert.assertEquals("Q(PRIORITY:HIGH)", Query.toString(parser.parse(src)));

        src = "priority:low";
        Assert.assertEquals("Q(PRIORITY:LOW)", Query.toString(parser.parse(src)));

        src = "priority:medium";
        try {
            parser.parse(src);
            Assert.fail();
        } catch (ServiceException e) {
            Assert.assertSame(MailServiceException.QUERY_PARSE_ERROR, e.getCode());
        }
    }

    @Test
    public void size() throws Exception {
        String src = "size:(1 20 300 1k 10k 100kb 34mb)";
        Assert.assertEquals("(Q(SIZE:=1) && Q(SIZE:=20) && Q(SIZE:=300) && Q(SIZE:=1024) && Q(SIZE:=10240) && Q(SIZE:=102400) && Q(SIZE:=35651584))",
                Query.toString(parser.parse(src)));

        src = "size:(<1k >10k)";
        Assert.assertEquals("(Q(SIZE:<1024) && Q(SIZE:>10240))", Query.toString(parser.parse(src)));

        src = "larger:(1 20 300 100kb 34mb)";
        Assert.assertEquals("(Q(SIZE:>1) && Q(SIZE:>20) && Q(SIZE:>300) && Q(SIZE:>102400) && Q(SIZE:>35651584))",
                Query.toString(parser.parse(src)));

        src = "smaller:(1 20 300 100kb 34mb)";
        Assert.assertEquals("(Q(SIZE:<1) && Q(SIZE:<20) && Q(SIZE:<300) && Q(SIZE:<102400) && Q(SIZE:<35651584))",
                Query.toString(parser.parse(src)));
    }

    @Test
    public void metadata() throws Exception {
        String src = "author:foo author:(\"foo\" \"foo bar\" gub)";
        Assert.assertEquals("Q(AUTHOR:foo) && (Q(AUTHOR:foo) && Q(AUTHOR:foo,bar) && Q(AUTHOR:gub))",
                Query.toString(parser.parse(src)));

        src = "title:foo title:(\"foo\" \"foo bar\" gub)";
        Assert.assertEquals("Q(TITLE:foo) && (Q(TITLE:foo) && Q(TITLE:foo,bar) && Q(TITLE:gub))",
                Query.toString(parser.parse(src)));

        src = "keywords:foo keywords:(\"foo\" \"foo bar\" gub)";
        Assert.assertEquals("Q(KEYWORDS:foo) && (Q(KEYWORDS:foo) && Q(KEYWORDS:foo,bar) && Q(KEYWORDS:gub))",
                Query.toString(parser.parse(src)));

        src = "company:foo company:(\"foo\" \"foo bar\" gub)";
        Assert.assertEquals("Q(COMPANY:foo) && (Q(COMPANY:foo) && Q(COMPANY:foo,bar) && Q(COMPANY:gub))",
                Query.toString(parser.parse(src)));
    }

    @Test
    public void field() throws Exception {
        String src = "#company:\"zmail:vmware\"";
        List<Query> result = parser.parse(src);
        Assert.assertEquals("Q(l.field:company:zmail:vmware)", Query.toString(result));

        TextQuery query = (TextQuery) result.get(0);
        Assert.assertEquals("#company:\"zmail:vmware\"",
                query.toQueryString(query.getField(), "company:zmail:vmware"));
        Assert.assertEquals("#company:\"zmail@vmware\"",
                query.toQueryString(query.getField(), "company:zmail@vmware"));
        Assert.assertEquals("#company:\"zmail\\\"vmware\"",
                query.toQueryString(query.getField(), "company:zmail\"vmware"));
    }

    @Test
    public void textLexicalState() throws Exception {
        String src = "from:and or from:or or not from:not";
        Assert.assertEquals("Q(from:and) || Q(from:or) || -Q(from:not)", Query.toString(parser.parse(src)));
    }

    @Test
    public void contact() throws Exception {
        QueryParser parser = new QueryParser(null, ZmailAnalyzer.getInstance());
        String src = "contact:\"Conf -\"";
        Assert.assertEquals("Q(CONTACT:conf,-)", Query.toString(parser.parse(src)));

        src = "contact:\"Conf - Prom\"";
        Assert.assertEquals("Q(CONTACT:conf,-,prom)", Query.toString(parser.parse(src)));

        src = "contact:\"Conf - Promontory E\"";
        Assert.assertEquals("Q(CONTACT:conf,-,promontory,e)", Query.toString(parser.parse(src)));

        src = "contact:\"Conf - Promontory E*****\"";
        Assert.assertEquals("Q(CONTACT:conf,-,promontory,e)", Query.toString(parser.parse(src)));

        src = "contact:\"Conf - Prom* E*\"";
        Assert.assertEquals("Q(CONTACT:conf,-,prom,e)", Query.toString(parser.parse(src)));
    }

    @Test
    public void contactContent() throws Exception {
        QueryParser parser = new QueryParser(null, ZmailAnalyzer.getInstance());
        parser.setTypes(EnumSet.of(MailItem.Type.CONTACT));

        String src = "zmail";
        Assert.assertEquals("(Q(CONTACT:zmail) || Q(l.content:zmail))", Query.toString(parser.parse(src)));

        src = "in"; // stop word
        Assert.assertEquals("(Q(CONTACT:in) || Q(l.content:))", Query.toString(parser.parse(src)));
    }

    @Test
    public void quoted() throws Exception {
        QueryParser parser = new QueryParser(null, ZmailAnalyzer.getInstance());
        parser.setTypes(EnumSet.of(MailItem.Type.CONTACT));

        Assert.assertEquals("(Q(CONTACT:zmail,quoted,test) || Q(l.content:zmail,quoted,test))",
                Query.toString(parser.parse("\"Zmail \\\"quoted\\\" test\"")));
    }

    @Test
    public void quick() throws Exception {
        QueryParser parser = new QueryParser(null, ZmailAnalyzer.getInstance());
        parser.setQuick(true);

        Assert.assertEquals("Q(l.content:all,hands,meeting[*])", Query.toString(parser.parse("all hands meeting")));
        Assert.assertEquals("Q(l.content:all,hands,meeting[*])", Query.toString(parser.parse("all hands meeting*")));
    }

}
