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

import java.util.ArrayList;
import java.util.List;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.index.DBQueryOperation;
import com.zimbra.cs.index.QueryOperation;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.service.util.ItemId;

/**
 * Query by conversation ID.
 *
 * @author tim
 * @author ysasaki
 */
public final class ConvQuery extends Query {
    private final ItemId convId;

    private ConvQuery(ItemId convId) throws ServiceException {
        this.convId = convId;

        if (convId.getId() < 0) { // should never happen (make an ItemQuery instead)
            throw ServiceException.FAILURE(
                    "Illegal Negative ConvID: " + convId + ", use ItemQuery for virtual convs", null);
        }
    }

    public static Query create(Mailbox mbox, String target) throws ServiceException {
        ItemId convId = new ItemId(target, mbox.getAccountId());
        if (convId.getId() < 0) {
            // ...convert negative convId to positive ItemId...
            convId = new ItemId(convId.getAccountId(), -1 * convId.getId());
            List<ItemId> iidList = new ArrayList<ItemId>(1);
            iidList.add(convId);
            return new ItemQuery(false, false, iidList);
        } else {
            return new ConvQuery(convId);
        }
    }

    @Override
    public boolean hasTextOperation() {
        return false;
    }

    @Override
    public QueryOperation compile(Mailbox mbox, boolean bool) {
        DBQueryOperation op = new DBQueryOperation();
        op.addConvId(mbox, convId, evalBool(bool));
        return op;
    }

    @Override
    public void dump(StringBuilder out) {
        out.append("CONV:");
        out.append(convId);
    }
    
    @Override
    public void sanitizedDump(StringBuilder out) {
        out.append("CONV:");
        out.append("$NUM");
    }

}
