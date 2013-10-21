/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.mail;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.util.TagUtil;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.mail.type.RetentionPolicy;

/**
 * @since May 26, 2004
 * @author schemers
 */
public class TagAction extends ItemAction  {

    public static final String OP_RETENTION_POLICY = "retentionpolicy";

    private static final Set<String> TAG_ACTIONS = ImmutableSet.of(OP_READ, OP_COLOR, OP_HARD_DELETE, OP_RENAME, OP_UPDATE, OP_RETENTION_POLICY);

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);

        Element action = request.getElement(MailConstants.E_ACTION);
        String opAttr = action.getAttribute(MailConstants.A_OPERATION).toLowerCase();
        String operation = getOperation(opAttr);

        if (operation.equals(OP_TAG) || operation.equals(OP_FLAG)) {
            throw ServiceException.INVALID_REQUEST("cannot tag/flag a tag", null);
        }
        if (!TAG_ACTIONS.contains(operation)) {
            throw ServiceException.INVALID_REQUEST("invalid operation on tag: " + opAttr, null);
        }

        String tn = action.getAttribute(MailConstants.A_TAG_NAMES, null);
        if (tn != null) {
            // switch tag names to tag IDs, because that's what ItemAction expects
            StringBuilder tagids = new StringBuilder();
            for (String name : TagUtil.decodeTags(tn)) {
                tagids.append(tagids.length() == 0 ? "" : ",").append(mbox.getTagByName(octxt, name).getId());
            }
            action.addAttribute(MailConstants.A_ID, tagids.toString());
        }

        String successes;
        if (operation.equals(OP_RETENTION_POLICY)) {
            ItemId iid = new ItemId(action.getAttribute(MailConstants.A_ID), zsc);
            RetentionPolicy rp = new RetentionPolicy(action.getElement(MailConstants.E_RETENTION_POLICY));
            mbox.setRetentionPolicy(octxt, iid.getId(), MailItem.Type.TAG, rp);
            successes = new ItemIdFormatter(zsc).formatItemId(iid);
        } else {
            successes = handleCommon(context, request, opAttr, MailItem.Type.TAG);
        }

        Element response = zsc.createElement(MailConstants.TAG_ACTION_RESPONSE);
        Element result = response.addUniqueElement(MailConstants.E_ACTION);
        result.addAttribute(MailConstants.A_ID, successes);
        result.addAttribute(MailConstants.A_TAG_NAMES, tn);
        result.addAttribute(MailConstants.A_OPERATION, opAttr);
        return response;
    }
}
