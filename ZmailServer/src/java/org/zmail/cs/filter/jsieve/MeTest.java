/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package org.zmail.cs.filter.jsieve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.jsieve.Argument;
import org.apache.jsieve.Arguments;
import org.apache.jsieve.SieveContext;
import org.apache.jsieve.StringListArgument;
import org.apache.jsieve.TagArgument;
import org.apache.jsieve.exception.SieveException;
import org.apache.jsieve.mail.MailAdapter;
import org.apache.jsieve.tests.AbstractTest;

import org.zmail.common.mime.InternetAddress;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.filter.ZmailMailAdapter;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.util.AccountUtil;

/**
 * SIEVE test that returns true if the specified header contains the recipient's email address including aliases.
 *
 * @author ysasaki
 */
public final class MeTest extends AbstractTest {
    private static final String IN = ":in";
    private String[] headers;

    @Override
    protected void validateArguments(Arguments args, SieveContext ctx) throws SieveException {
        Iterator<Argument> itr = args.getArgumentList().iterator();
        if (itr.hasNext()) {
            Argument arg = itr.next();
            if (arg instanceof TagArgument) {
                TagArgument tag = (TagArgument) arg;
                if (tag.is(IN)) {
                    if (itr.hasNext()) {
                        arg = itr.next();
                        if (arg instanceof StringListArgument) {
                            headers = ((StringListArgument) arg).getList().get(0).split(",");
                        } else {
                            throw ctx.getCoordinate().syntaxException(IN + " is missing an argument");
                        }
                    } else {
                        throw ctx.getCoordinate().syntaxException(IN + " is missing an argument");
                    }
                } else {
                    throw ctx.getCoordinate().syntaxException("Unknown tag: " + tag.getTag());
                }
            } else {
                throw ctx.getCoordinate().syntaxException("Unexpected argument: " + arg.getValue());
            }
        }
    }

    @Override
    protected boolean executeBasic(MailAdapter mail, Arguments args, SieveContext ctx) throws SieveException {
        assert(headers != null);
        if (!(mail instanceof ZmailMailAdapter)) {
            return false;
        }
        Mailbox mbox = ((ZmailMailAdapter) mail).getMailbox();
        List<InternetAddress> addrs = new ArrayList<InternetAddress>();
        for (String header : headers) {
            for (String value : mail.getHeader(header)) {
                List<InternetAddress> inetAddrs = InternetAddress.parseHeader(value);
                if (inetAddrs != null) {
                    addrs.addAll(inetAddrs);
                }
            }
        }
        try {
            Account account = mbox.getAccount();
            Set<String> me = AccountUtil.getEmailAddresses(account);
            me.addAll(AccountUtil.getImapPop3EmailAddresses(account));
            for (InternetAddress addr : addrs) {
                String email = addr.getAddress();
                if (email != null && me.contains(email.toLowerCase())) {
                    return true;
                }
            }
        } catch (ServiceException e) {
            ZmailLog.filter.error("Failed to lookup my addresses", e);
        }
        return false;
    }

}
