/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package com.zimbra.cs.mailclient.imap;

import java.util.List;

/**
 * A ResponseHandler implementations which accumulates untagged data results
 * matching the specified command code.
 */
public class BasicResponseHandler implements ResponseHandler {
    private final Atom code;
    private final List results;

    public BasicResponseHandler(Atom code, List results) {
        this.code = code;
        this.results = results;
    }

    public BasicResponseHandler(CAtom code, List results) {
        this(code.atom(), results);
    }

    @SuppressWarnings("unchecked")
    public void handleResponse(ImapResponse res) {
        if (res.getCode().equals(code)) {
            results.add(res.getData());
        }
    }
}
