/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.dav.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.json.JSONException;

import com.google.common.io.Closeables;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.HttpUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.dav.DavContext;
import org.zmail.cs.dav.DavElements;
import org.zmail.cs.dav.DavException;
import org.zmail.cs.dav.DavProtocol;
import org.zmail.cs.dav.property.CardDavProperty;
import org.zmail.cs.dav.property.ResourceProperty;
import org.zmail.cs.index.ContactHit;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.index.ZmailHit;
import org.zmail.cs.index.ZmailQueryResults;
import org.zmail.cs.mailbox.Contact;
import org.zmail.cs.mailbox.ContactGroup;
import org.zmail.cs.mailbox.ContactGroup.Member;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.formatter.VCard;
import org.zmail.cs.service.util.ItemId;

public class AddressObject extends MailItemResource {

    public static final String VCARD_EXTENSION = ".vcf";

    // for compatibility with Apple Address Book app.
    private static final String XABSKIND = "X-ADDRESSBOOKSERVER-KIND";
    private static final String XABSMEMBER = "X-ADDRESSBOOKSERVER-MEMBER";

    public AddressObject(DavContext ctxt, Contact item) throws ServiceException {
        super(ctxt, item);
        setProperty(DavElements.P_GETCONTENTTYPE, DavProtocol.VCARD_CONTENT_TYPE);

        // size is approximate.  it just has to be non-zero as the actual content
        // will be chunked to the client in GET response.
        setProperty(DavElements.P_GETCONTENTLENGTH, Integer.toString(item.getFields().size()));
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public ResourceProperty getProperty(Element prop) {
        if (prop.getQName().equals(DavElements.CardDav.E_ADDRESS_DATA)) {
            return CardDavProperty.getAddressbookData(prop, this);
        }
        return super.getProperty(prop);
    }

    @Override
    public InputStream getContent(DavContext ctxt) throws DavException, IOException {
        try {
            return new ByteArrayInputStream(toVCard(ctxt).getBytes("UTF-8"));
        } catch (ServiceException e) {
            ZmailLog.dav.warn("can't get content for Contact %d", mId, e);
        }
        return null;
    }
        
    private static void constructContactGroupFromAppleXProps(DavContext ctxt, Account ownerAccount, VCard vcard, Contact existingContact, int folderId) {
        Map<String, String> xprops = Contact.decodeXProps(vcard.fields.get(ContactConstants.A_vCardXProps));
        String kind = xprops.get(XABSKIND);
        String memberList = xprops.get(XABSMEMBER);
        if (kind != null && kind.compareTo("group") == 0) {
            ContactGroup contactGroup;
            try {
                if (existingContact == null) // create
                    contactGroup = ContactGroup.init();
                else { // modify
                    contactGroup = ContactGroup.init(existingContact, true);
                    // remove all the contacts of type CONTACT_REF that belong to the collection same as the group
                    ArrayList<Member> membersToRemove = new ArrayList<Member>();
                    for (Member member : contactGroup.getMembers()) {                        
                        if (Member.Type.CONTACT_REF.equals(member.getType())) {
                            ItemId itemId = new ItemId(member.getValue(), existingContact.getAccount().getId());
                            if (itemId.belongsTo(existingContact.getAccount())) {
                                // make sure member belongs to the same collection as the group.
                                Contact c = getContactByUID(ctxt, itemId.toString(), existingContact.getAccount(), folderId);
                                if (c != null)
                                    membersToRemove.add(member);                                    
                            }
                        }                            
                    }
                    for (Member member : membersToRemove)
                        contactGroup.removeMember(member.getType(), member.getValue());
                }
                if (memberList != null) {
                    try {
                        String[] members = Contact.parseMultiValueAttr(memberList);
                        for (String uidStr : members) {
                            if (uidStr.startsWith("urn:uuid:"))
                                uidStr = uidStr.substring(9);                            
                            Contact c = getContactByUID(ctxt, uidStr, ownerAccount, folderId);
                            if (c != null) {
                                // add to the group as a CONTACT_REF
                                ItemId itemId = new ItemId(c);
                                contactGroup.addMember(Member.Type.CONTACT_REF, itemId.toString());
                            }
                        }
                    } catch (JSONException e) {
                        ZmailLog.dav.debug("can't parse xprop %s", memberList, e);
                    }
                }

                vcard.fields.put(ContactConstants.A_type, ContactConstants.TYPE_GROUP);
                vcard.fields.put(ContactConstants.A_groupMember, contactGroup.encode());
                // remove the Apple x-props and preserve the rest.
                xprops.remove(XABSKIND);
                xprops.remove(XABSMEMBER);
                vcard.fields.put(ContactConstants.A_vCardXProps, Contact.encodeXProps(xprops));
            } catch (ServiceException e) {
                ZmailLog.dav.debug("can't parse xprop %s", memberList, e);
            }
        }
    }
    
    private static void populateContactGroupAppleXProps(DavContext ctxt, Contact contact) {
        if (contact.isContactGroup() == false)
            return;
        ContactGroup contactGroup = null;
        try {
            contactGroup = ContactGroup.init(contact.get(ContactConstants.A_groupMember));
        } catch (ServiceException e) {
            ZmailLog.dav.warn("can't get group members for Contact %d", contact.getId(), e);
        }
        // get the x-props first.
        Map<String, String> xprops = contact.getXProps();
        xprops.put(XABSKIND, "group");
        if (contactGroup != null) {            
            ArrayList<String> memberList = new ArrayList<String>(contactGroup.getMembers().size());
            try {
                for (Member member : contactGroup.getMembers()) {
                    if (member.getType().equals(Member.Type.CONTACT_REF)) {
                        ItemId itemId = new ItemId(member.getValue(), contact.getAccount().getId());
                        if (itemId.belongsTo(contact.getAccount())) {
                            // make sure member belongs to the same collection as the group.
                            Contact c = getContactByUID(ctxt, itemId.toString(), contact.getAccount(), contact.getFolderId());                             
                            if (c != null)
                                memberList.add("urn:uuid:" + VCard.getUid(c));   
                        }
                    }
                }            
                xprops.put(XABSMEMBER, Contact.encodeMultiValueAttr(memberList.toArray(new String[0])));
            } catch (JSONException e) {
                ZmailLog.dav.warn("can't create group members xprops for Contact %d", contact.getId(), e);
            } catch (ServiceException e) {
                ZmailLog.dav.warn("can't create group members xprops for Contact %d", contact.getId(), e);
            }
        }
        contact.setXProps(xprops);
    }

    public String toVCard(DavContext ctxt) throws ServiceException, DavException {
        Contact contact = (Contact)getMailItem(ctxt);
        populateContactGroupAppleXProps(ctxt, contact);
        return VCard.formatContact(contact, null, true, false).formatted;
    }
    public String toVCard(DavContext ctxt, java.util.Collection<String> attrs) throws ServiceException, DavException {
        if (attrs == null || attrs.isEmpty())
            return toVCard(ctxt);
        Contact contact = (Contact)getMailItem(ctxt);
        populateContactGroupAppleXProps(ctxt, contact);
        return VCard.formatContact(contact, attrs, true).formatted;
    }

    public static DavResource create(DavContext ctxt, String name, Collection where) throws DavException, IOException {
        FileUploadServlet.Upload upload = ctxt.getUpload();
        String buf = new String(ByteUtil.getContent(upload.getInputStream(), (int)upload.getSize()), MimeConstants.P_CHARSET_UTF8);
        Mailbox mbox = null;
        DavResource res = null;
        try {
            String url = ctxt.getItem();
            if (url.endsWith(AddressObject.VCARD_EXTENSION)) {
                url = url.substring(0, url.length()-4);
                url = HttpUtil.urlUnescape(url);
            }
            mbox = where.getMailbox(ctxt);
            for (VCard vcard : VCard.parseVCard(buf)) {
                if (vcard.fields.isEmpty())
                    continue;
                vcard.fields.put(ContactConstants.A_vCardURL, url);
                String uid = vcard.uid;
                Account ownerAccount = Provisioning.getInstance().getAccountById(where.mOwnerId);
                Contact c = null;
                // check for existing contact
                if (uid != null) {
                    vcard.fields.put(ContactConstants.A_vCardUID, uid);
                    res = UrlNamespace.getResourceAt(ctxt, ctxt.getUser(), ctxt.getPath());
                }
                if (res == null) {
                    String ifnonematch = ctxt.getRequest().getHeader(DavProtocol.HEADER_IF_NONE_MATCH);
                    if (ifnonematch == null)
                        throw new DavException("item does not exists", HttpServletResponse.SC_CONFLICT);
                    // Convert Apple contact group to Zmail contact group.
                    constructContactGroupFromAppleXProps(ctxt, ownerAccount, vcard, null, where.getId());
                    c = mbox.createContact(ctxt.getOperationContext(), vcard.asParsedContact(), where.mId, null);
                    res = new AddressObject(ctxt, c);
                    res.mNewlyCreated = true;
                } else {
                    String etag = ctxt.getRequest().getHeader(DavProtocol.HEADER_IF_MATCH);
                    String itemEtag = res.getEtag();
                    if (etag != null && !etag.equals(itemEtag))
                        throw new DavException("item etag does not match", HttpServletResponse.SC_CONFLICT);
                    MailItemResource mir = (MailItemResource) res;
                    constructContactGroupFromAppleXProps(ctxt, ownerAccount, vcard, (Contact) mir.getMailItem(ctxt), where.getId());
                    vcard.merge((Contact) mir.getMailItem(ctxt));
                    mbox.modifyContact(ctxt.getOperationContext(), mir.getId(), vcard.asParsedContact());
                    res = UrlNamespace.getResourceAt(ctxt, ctxt.getUser(), ctxt.getPath());
                }
            }
        } catch (ServiceException e) {
            throw new DavException.InvalidData(DavElements.CardDav.E_VALID_ADDRESS_DATA, "cannot parse vcard");
        }
        if (res == null)
            throw new DavException.InvalidData(DavElements.CardDav.E_VALID_ADDRESS_DATA, "cannot parse vcard");
        return res;
    }

    public static AddressObject getAddressObjectByUID(DavContext ctxt, String uid, Account account, Folder f) throws ServiceException {
        Contact c = getContactByUID(ctxt, uid, account, (f != null) ? f.getId() : -1);
        if (c == null)
            return null;
        return new AddressObject(ctxt, c);
    }
    
    public static AddressObject getAddressObjectByUID(DavContext ctxt, String uid, Account account) throws ServiceException {
        return getAddressObjectByUID(ctxt, uid, account, null);
    }

    private static Contact getContactByUID(DavContext ctxt, String uid, Account account, int folderId) throws ServiceException {
        Contact item = null;
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account);
        if (uid.endsWith(AddressObject.VCARD_EXTENSION)) {
            uid = uid.substring(0, uid.length() - AddressObject.VCARD_EXTENSION.length());
            // Unescape the name (It was encoded in DavContext intentionally)
            uid = HttpUtil.urlUnescape(uid);
        }
        int id = 0;
        int index = uid.indexOf(':');
        if (index > 0) {
            String accountId = uid.substring(0, index);
            try {
                if (accountId.equals(account.getId()))
                    id = Integer.parseInt(uid.substring(index+1));
            } catch (NumberFormatException e) {
            }
        }
        if (id > 0) {
            item = mbox.getContactById(ctxt.getOperationContext(), Integer.parseInt(uid.substring(index+1)));
        } else {
            ZmailQueryResults zqr = null;
            StringBuilder query = new StringBuilder();
            query.append("#").append(ContactConstants.A_vCardUID).append(":");
            // escape the double quotes in uid and surround with double quotes
            query.append("\"").append(uid.replace("\"", "\\\"")).append("\"");
            query.append(" OR ").append("#").append(ContactConstants.A_vCardURL).append(":");
            query.append("\"").append(uid.replace("\"", "\\\"")).append("\"");
            ZmailLog.dav.debug("query %s", query.toString());
            try {
                zqr = mbox.index.search(ctxt.getOperationContext(), query.toString(),
                        EnumSet.of(MailItem.Type.CONTACT), SortBy.NAME_ASC, 10);
                // There could be multiple contacts with the same UID from different collections.
                while (zqr.hasNext()) {
                    ZmailHit hit = zqr.getNext();
                    if (hit instanceof ContactHit) {
                        item = ((ContactHit)hit).getContact();
                        if (folderId < 0)
                            break;
                        if (item.getFolderId() == folderId)
                            break;
                        item = null;
                    }
                }
            } catch (Exception e) {
                ZmailLog.dav.error("can't search for: uid=%s", uid, e);
            } finally {
                Closeables.closeQuietly(zqr);
            }
        }
        
        if ((item != null) && (folderId >= 0) && (item.getFolderId() != folderId))
            item = null;
        
        return item;
    }
}
