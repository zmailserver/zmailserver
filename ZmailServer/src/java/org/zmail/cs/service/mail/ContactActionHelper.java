/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.service.mail;

import java.util.List;

import com.zimbra.common.mailbox.Color;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.SoapProtocol;
import com.zimbra.cs.mailbox.Flag;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.OperationContext;
import com.zimbra.cs.mime.ParsedContact;
import com.zimbra.cs.service.util.ItemId;
import com.zimbra.soap.ZimbraSoapContext;

public class ContactActionHelper extends ItemActionHelper {

    public static ContactActionHelper UPDATE(ZimbraSoapContext zsc, OperationContext octxt,
            Mailbox mbox, List<Integer> ids, ItemId iidFolder,
            String flags, String[] tags, Color color, ParsedContact pc)
    throws ServiceException {
        ContactActionHelper ca = new ContactActionHelper(octxt, mbox, zsc.getResponseProtocol(), ids, Op.UPDATE);
        ca.setIidFolder(iidFolder);
        ca.setFlags(flags);
        ca.setTags(tags);
        ca.setColor(color);
        ca.setParsedContact(pc);
        ca.schedule();
        return ca;
    }

    // only when OP=UPDATE
    private ParsedContact mParsedContact;


    public void setParsedContact(ParsedContact pc) {
        assert(mOperation == Op.UPDATE);
        mParsedContact = pc;
    }

    ContactActionHelper(OperationContext octxt, Mailbox mbox, SoapProtocol responseProto, List<Integer> ids, Op op) throws ServiceException {
        super(octxt, mbox, responseProto, ids, op, MailItem.Type.CONTACT, true, null);
    }

    @Override
    protected void schedule() throws ServiceException {
        // iterate over the local items and perform the requested operation
        switch (mOperation) {
        case UPDATE:
            if (!mIidFolder.belongsTo(getMailbox())) {
                throw ServiceException.INVALID_REQUEST("cannot move item between mailboxes", null);
            }

            if (mIidFolder.getId() > 0) {
                getMailbox().move(getOpCtxt(), mIds, type, mIidFolder.getId(), mTargetConstraint);
            }
            if (mTags != null || mFlags != null) {
                getMailbox().setTags(getOpCtxt(), mIds, type, Flag.toBitmask(mFlags), mTags, mTargetConstraint);
            }
            if (mColor != null) {
                getMailbox().setColor(getOpCtxt(), mIds, type, mColor);
            }
            if (mParsedContact != null) {
                for (int id : mIds) {
                    getMailbox().modifyContact(getOpCtxt(), id, mParsedContact);
                }
            }
            break;
        default:
            throw ServiceException.INVALID_REQUEST("unknown operation: " + mOperation, null);
        }

        StringBuilder successes = new StringBuilder();
        for (int id : mIds) {
            successes.append(successes.length() > 0 ? "," : "").append(mIdFormatter.formatItemId(id));
        }
        mResult = successes.toString();
    }

    @Override
    public String toString() {
        StringBuilder toRet = new StringBuilder(super.toString());
        if (mOperation == Op.UPDATE) {
            if (mParsedContact != null) {
                toRet.append(" Fields=").append(mParsedContact.getFields());
            }
        }
        return toRet.toString();
    }
}
