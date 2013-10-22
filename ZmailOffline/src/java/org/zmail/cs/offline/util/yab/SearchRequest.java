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
package org.zmail.cs.offline.util.yab;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SearchRequest extends Request {
    private static final String ACTION = "searchContacts";
    
    public SearchRequest(Session session) {
        super(session);
    }

    @Override
    protected String getAction() {
        return ACTION;
    }

    @Override
    protected Response parseResponse(Document doc) {
        return SearchResponse.fromXml(doc.getDocumentElement());
    }

    @Override
    public Element toXml(Document doc) {
        return null; // Not an XML entity
    }
}
