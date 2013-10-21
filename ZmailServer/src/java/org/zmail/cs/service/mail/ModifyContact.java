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

/*
 * Created on May 26, 2004
 */
package org.zmail.cs.service.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.Pair;
import org.zmail.cs.mailbox.Contact;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.Contact.Attachment;
import org.zmail.cs.mailbox.util.TagUtil;
import org.zmail.cs.mime.ParsedContact;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class ModifyContact extends MailDocumentHandler  {

    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.E_CONTACT, MailConstants.A_ID };
    @Override
    protected String[] getProxiedIdPath(Element request)     { return TARGET_FOLDER_PATH; }
    @Override
    protected boolean checkMountpointProxy(Element request)  { return false; }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        boolean replace = request.getAttributeBool(MailConstants.A_REPLACE, false);
        boolean verbose = request.getAttributeBool(MailConstants.A_VERBOSE, true);

        Element cn = request.getElement(MailConstants.E_CONTACT);
        ItemId iid = new ItemId(cn.getAttribute(MailConstants.A_ID), zsc);
        String tagsAttr = cn.getAttribute(MailConstants.A_TAG_NAMES, null);
        String[] tags = (tagsAttr == null) ? null : TagUtil.decodeTags(tagsAttr);

        Contact contact = mbox.getContactById(octxt, iid.getId());

        ParsedContact pc;
        if (replace) {
            Pair<Map<String,Object>, List<Attachment>> cdata = CreateContact.parseContact(cn, zsc, octxt, contact);
            pc = new ParsedContact(cdata.getFirst(), cdata.getSecond());
        } else {
            pc = CreateContact.parseContactMergeMode(cn, zsc, octxt, contact);
        }

        if (CreateContact.needToMigrateDlist(zsc)) {
            CreateContact.migrateFromDlist(pc);
        }

        mbox.modifyContact(octxt, iid.getId(), pc);
        if (tags != null) {
            mbox.setTags(octxt, iid.getId(), MailItem.Type.CONTACT, MailItem.FLAG_UNCHANGED, tags);
        }

        Contact con = mbox.getContactById(octxt, iid.getId());
        Element response = zsc.createElement(MailConstants.MODIFY_CONTACT_RESPONSE);
        if (con != null) {
            if (verbose)
                ToXML.encodeContact(response, ifmt, octxt, con, true, null);
            else
                response.addElement(MailConstants.E_CONTACT).addAttribute(MailConstants.A_ID, con.getId());
        }
        return response;
    }

    static Map<String, String> parseFields(List<Element> elist) throws ServiceException {
        if (elist == null || elist.isEmpty())
            return null;

        HashMap<String, String> attrs = new HashMap<String, String>();
        for (Element e : elist) {
            String name = e.getAttribute(MailConstants.A_ATTRIBUTE_NAME);
            attrs.put(name, e.getText());
        }
        return attrs;
    }
}
