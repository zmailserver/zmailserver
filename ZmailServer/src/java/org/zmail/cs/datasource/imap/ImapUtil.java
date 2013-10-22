/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.datasource.imap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zimbra.cs.mailclient.imap.ImapConnection;
import com.zimbra.cs.mailclient.imap.ListData;

public final class ImapUtil {
    private static final String INBOX = "INBOX";
    private static final int INBOX_LEN = INBOX.length();

    // Used for sorting ListData lexicographically in reverse order. This
    // ensures that inferior mailboxes will be processed before their
    // parents which avoids problems when deleting folders. Also, ignore
    // case when comparing mailbox names so we can remove duplicates (Zimbra
    // folder names are case insensitive).
    private static final Comparator<ListData> COMPARATOR =
        new Comparator<ListData>() {
            public int compare(ListData ld1, ListData ld2) {
                return ld2.getMailbox().compareToIgnoreCase(ld1.getMailbox());
            }
        };

    public static List<ListData> listFolders(ImapConnection ic, String name)
        throws IOException {
        return sortFolders(ic.list("", name));
    }

    public static List<ListData> sortFolders(List<ListData> folders) {
        // Keep INBOX and inferiors separate so we can return them first
        ListData inbox = null;
        ListData defaultInbox = null;
        List<ListData> inboxInferiors = new ArrayList<ListData>();
        List<ListData> otherFolders = new ArrayList<ListData>();
        for (ListData ld : folders) {
            if (INBOX.equalsIgnoreCase(ld.getMailbox())) { //rfc3501(5.1), INBOX is case-insensitive
                if (inbox == null) {
                 // Ignore duplicate INBOX (fixes bug 26483)
                    inbox = ld;
                }
            } else if (isInboxInferior(ld)) {
                inboxInferiors.add(ld);
                if (defaultInbox == null) {
                    defaultInbox = new ListData(INBOX, ld.getDelimiter());
                }
            } else {
                otherFolders.add(ld);
            }
        }
        List<ListData> sorted = new ArrayList<ListData>(folders.size());
        if (inbox == null) {
            // If INBOX missing from LIST response, then see if we can
            // determine a reasonable default (bug 30844).
            inbox = defaultInbox;
        }
        if (inbox != null) {
            sorted.add(inbox);
        }
        Collections.sort(inboxInferiors, COMPARATOR);
        sorted.addAll(inboxInferiors);
        Collections.sort(otherFolders, COMPARATOR);
        sorted.addAll(otherFolders);
        return sorted;
    }

    /*
     * Returns true if specified ListData refers to am inferior of INBOX
     * (i.e. "INBOX/Foo").
     */
    private static boolean isInboxInferior(ListData ld) {
        String name = ld.getMailbox();
        return name.length() > INBOX_LEN &&
            INBOX.equalsIgnoreCase(name.substring(0, INBOX_LEN)) &&
            name.charAt(INBOX_LEN) == ld.getDelimiter();
    }

    public static boolean isYahoo(ImapConnection ic) {
        return ic.hasCapability("AUTH=XYMCOOKIEB64");
    }
}
