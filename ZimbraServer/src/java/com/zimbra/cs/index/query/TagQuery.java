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

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.index.DBQueryOperation;
import com.zimbra.cs.index.QueryOperation;
import com.zimbra.cs.mailbox.MailServiceException;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.Tag;

/**
 * Query by tag.
 *
 * @author tim
 * @author ysasaki
 */
public class TagQuery extends Query {

    private final String name;

    public TagQuery(String name, boolean bool) {
        this.name = name;
        setBool(bool);
    }

    @Override
    public boolean hasTextOperation() {
        return false;
    }

    @Override
    public QueryOperation compile(Mailbox mbox, boolean bool) throws ServiceException {
        DBQueryOperation op = new DBQueryOperation();
        try {
            op.addTag(mbox.getTagByName(null, name), evalBool(bool));
        } catch (MailServiceException mse) {
            if (MailServiceException.NO_SUCH_TAG.equals(mse.getCode())) {
                // Probably clicked on a remote tag for an item in a shared folder which isn't mirrored by a local tag
                // Would be too confusing for UI to then claim that there was no such tag (Bug 77646)
                op.addTag(Tag.createPseudoRemoteTag(mbox, name), evalBool(bool));
            } else {
                throw mse;
            }
        }
        return op;
    }

    @Override
    public void dump(StringBuilder out) {
        out.append("TAG:");
        out.append(name);
    }

    @Override
    public void sanitizedDump(StringBuilder out) {
        out.append("TAG:");
        out.append("$TAG");
    }

}
