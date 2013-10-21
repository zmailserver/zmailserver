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

package org.zmail.cs.filter.jsieve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.jsieve.SieveContext;
import org.apache.jsieve.exception.SieveException;
import org.apache.jsieve.mail.MailAdapter;
import org.apache.jsieve.tests.Header;

import org.zmail.cs.filter.ZmailMailAdapter;

/**
 * Acts just like the original header test, but tests the headers
 * of all MIME parts instead of just the top-level message.
 */
public class MimeHeaderTest extends Header {

    @SuppressWarnings("unchecked")
    @Override
    protected boolean match(MailAdapter mail, String comparator,
                            String matchType, List headerNames, List keys, SieveContext context)
    throws SieveException {
        if (!(mail instanceof ZmailMailAdapter)) {
            return false;
        }
        ZmailMailAdapter zma = (ZmailMailAdapter) mail;
        // Iterate over the header names looking for a match
        boolean isMatched = false;
        Iterator<String> headerNamesIter = headerNames.iterator();
        while (!isMatched && headerNamesIter.hasNext()) {
            Set<String> values = zma.getMatchingHeaderFromAllParts(headerNamesIter.next());
            isMatched = match(comparator, matchType, new ArrayList<String>(values), keys, context);
        }
        return isMatched;
    }
}
