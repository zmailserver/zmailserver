/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.offline.ab;

import org.zmail.cs.mailbox.Contact;
import org.zmail.cs.mime.ParsedContact;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.service.ServiceException;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;
import java.util.TreeSet;

public class ContactGroup {
    private final String name;
    private final Set<String> emails;

    public static boolean isContactGroup(Contact contact) {
        return contact.isGroup();
    }

    public ContactGroup(String name) {
        this.name = name;
        emails = new TreeSet<String>();
    }

    public ContactGroup(Contact contact) throws ServiceException {
        if (!isContactGroup(contact)) {
            throw new IllegalArgumentException("Not a contact group: " + contact);
        }
        name = contact.get(ContactConstants.A_nickname);
        emails = new TreeSet<String>();
        String dlist = contact.get(ContactConstants.A_dlist);
        if (dlist != null) {
            emails.addAll(Arrays.asList(dlist.trim().split(",")));
        }
    }

    public String getName() {
        return name;
    }

    public boolean hasEmail(String mapEmail) {
        for(String email : emails) {
            if(email.contains(mapEmail))
                return true;
        }
        return false;
    }

    public boolean isEmpty() throws ServiceException {
        return emails.isEmpty();
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public ParsedContact getParsedContact()
        throws ServiceException {
        Map<String, String> fields = new HashMap<String, String>();
        fields.put(ContactConstants.A_type, ContactConstants.TYPE_GROUP);
        fields.put(ContactConstants.A_nickname, name);
        fields.put(ContactConstants.A_fileAs, ContactConstants.FA_EXPLICIT + ":" + name);
        fields.put(ContactConstants.A_dlist, join(emails, ","));
        return new ParsedContact(fields);
    }

    public Set<String> adjustEmail() {
        Set<String> storeEmails = new TreeSet<String>();
        String tempDlist = join(this.emails, ",");
        storeEmails.addAll(emails);
        this.emails.clear();
        this.emails.addAll(Arrays.asList(tempDlist.trim().split(",")));
        return storeEmails;
    }

    public void resetEmail(Set<String> list) {
        this.emails.clear();
        this.emails.addAll(list);
    }

    private static String join(Collection<?> parts, String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = parts.iterator();
        if (it.hasNext()) {
            sb.append(it.next().toString());
            while (it.hasNext()) {
                sb.append(delimiter).append(it.next().toString());
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == ContactGroup.class) {
            ContactGroup group = (ContactGroup) obj;
            return name.equals(group.name) && emails.equals(group.emails);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("{name=%s,dlist=%s}", name, emails);
    }
}
