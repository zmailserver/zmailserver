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
package com.zimbra.cs.index;

import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;

/**
 * Suggested query string.
 *
 * @author ysasaki
 */
public final class SuggestQueryInfo implements QueryInfo {
    private final String suggest;

    public SuggestQueryInfo(String suggest) {
        this.suggest = suggest;
    }

    @Override
    public Element toXml(Element parent) {
        return parent.addElement(MailConstants.E_SUGEST).setText(suggest);
    }

    @Override
    public String toString() {
        return suggest;
    }
}
