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
package com.zimbra.cs.offline.util.yc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class FieldValue extends Entity {

    private static final String TAG_NAME = "value";
    @Override
    public Element toXml(Document doc) {
        Element e = doc.createElement(TAG_NAME);
        appendValues(e);
        return e;
    }
    
    protected abstract void appendValues(Element parent);

    public abstract Fields.Type getType();
}
