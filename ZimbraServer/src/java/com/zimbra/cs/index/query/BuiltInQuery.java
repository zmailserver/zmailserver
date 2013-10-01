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
package com.zimbra.cs.index.query;

import java.util.EnumSet;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;

import com.google.common.collect.ImmutableMap;
import com.zimbra.common.service.ServiceException;

import com.zimbra.cs.mailbox.Mailbox;

/**
 * Built-in queries.
 * <p>
 * Use {@link #getQuery(String, Mailbox, Analyzer, int)} to create an instance.
 *
 * @author tim
 * @author ysasaki
 */
public abstract class BuiltInQuery {

    private BuiltInQuery() {
    }

    abstract Query create(Mailbox mailbox, Analyzer analyzer) throws ServiceException;

    public static Query getQuery(String name, Mailbox mailbox, Analyzer analyzer) throws ServiceException {
        BuiltInQuery query = BUILTIN_QUERIES.get(name);
        if (query != null) {
            return query.create(mailbox, analyzer);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static final Map<String, BuiltInQuery> BUILTIN_QUERIES = ImmutableMap.<String, BuiltInQuery>builder()
        .put("read", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new ReadQuery(true);
            }
        })
        .put("unread", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new ReadQuery(false);
            }
        })
        .put("flagged", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new FlaggedQuery(true);
            }
        })
        .put("unflagged", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new FlaggedQuery(false);
            }
        })
        .put("draft", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new DraftQuery(true);
            }
        })
        .put("received", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new SentQuery(false);
            }
        })
        .put("replied", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new RepliedQuery(true);
            }
        })
        .put("unreplied", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new RepliedQuery(false);
            }
        })
        .put("forwarded", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new ForwardedQuery(true);
            }
        })
        .put("unforwarded", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer)  {
                return new ForwardedQuery(false);
            }
        })
        .put("invite", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new InviteQuery(true);
            }
        })
        .put("anywhere", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return InQuery.create(InQuery.In.ANY, false);
            }
        })
        .put("local", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return InQuery.create(InQuery.In.LOCAL, false);
            }
        })
        .put("remote", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return InQuery.create(InQuery.In.REMOTE, true);
            }
        })
        .put("solo", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return ConvCountQuery.create("1");
            }
        })
        .put("sent", new BuiltInQuery() { // send by me
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new SentQuery(true);
            }
        })
        .put("tome", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return MeQuery.create(mbox, analyzer, EnumSet.of(AddrQuery.Address.TO));
            }
        })
        .put("fromme", new BuiltInQuery() { // sent by me
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) {
                return new SentQuery(true);
            }
        })
        .put("ccme", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return MeQuery.create(mbox, analyzer, EnumSet.of(AddrQuery.Address.CC));
            }
        })
        .put("tofromme", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return MeQuery.create(mbox, analyzer, EnumSet.of(AddrQuery.Address.TO, AddrQuery.Address.FROM));
            }
        })
        .put("toccme", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return MeQuery.create(mbox, analyzer, EnumSet.of(AddrQuery.Address.TO, AddrQuery.Address.CC));
            }
        })
        .put("fromccme", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return MeQuery.create(mbox, analyzer, EnumSet.of(AddrQuery.Address.FROM, AddrQuery.Address.CC));
            }
        })
        .put("tofromccme", new BuiltInQuery() {
            @Override
            Query create(Mailbox mbox, Analyzer analyzer) throws ServiceException {
                return MeQuery.create(mbox, analyzer,
                        EnumSet.of(AddrQuery.Address.TO, AddrQuery.Address.FROM, AddrQuery.Address.CC));
            }
        })
        .build();
}
