/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

package org.zmail.cs.mailbox;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import org.zmail.common.calendar.ParsedDateTime;
import org.zmail.common.calendar.TimeZoneMap;
import org.zmail.common.calendar.WellKnownTimeZones;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.mime.shim.JavaMailMimeMessage;
import org.zmail.common.service.ServiceException;
import org.zmail.common.zmime.ZSharedFileInputStream;
import org.zmail.cs.account.MockProvisioning;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Mailbox.SetCalendarItemData;
import org.zmail.cs.mailbox.calendar.Invite;
import org.zmail.cs.mime.ParsedContact;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.util.JMSession;

@Ignore("failing in hudson?!?")
public class MetadataTest {

    @BeforeClass
    public static void init() throws Exception {
        MailboxTestUtil.initServer();
        Provisioning prov = Provisioning.getInstance();
        prov.createAccount("test@zmail.com", "secret", new HashMap<String, Object>());
    }

    @Before
    public void setUp() throws Exception {
        MailboxTestUtil.clearData();
    }

    @Test
    public void legacyContact() throws ServiceException {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        Map<String, Object> fields = new HashMap<String, Object>();
        String f1 = "First1";
        String l1 = "Last1";
        fields.put(ContactConstants.A_firstName, f1);
        fields.put(ContactConstants.A_lastName, l1);
        Contact contact = mbox.createContact(null, new ParsedContact(fields), Mailbox.ID_FOLDER_CONTACTS, null);

        Assert.assertEquals(f1, contact.get(ContactConstants.A_firstName));
        Assert.assertEquals(l1, contact.get(ContactConstants.A_lastName));

        Metadata metadata = new Metadata();
        String mailAddr = "test@email.net";
        String f2 = "First2";
        metadata.put(ContactConstants.A_email, mailAddr);
        metadata.put(ContactConstants.A_firstName, f2);

        contact.decodeMetadata(metadata);

        Assert.assertEquals(mailAddr, contact.get(ContactConstants.A_email));
        Assert.assertEquals(f2, contact.get(ContactConstants.A_firstName));
    }

    @Test
    public void legacyCalendarItem() throws ServiceException, MessagingException {
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccountId(MockProvisioning.DEFAULT_ACCOUNT_ID);
        SetCalendarItemData defaultInv = new SetCalendarItemData();
        MimeMessage message = new JavaMailMimeMessage(JMSession.getSession(), new ZSharedFileInputStream("data/TestMailRaw/invite1"));
        defaultInv.message = new ParsedMessage(message, Calendar.getInstance().getTimeInMillis(), false);
        TimeZoneMap tzMap = new TimeZoneMap(WellKnownTimeZones.getTimeZoneById("EST"));
        Invite invite = new Invite("REQUEST", tzMap, false);
        invite.setUid("test-uid");
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(2005, 1, 21);
        invite.setDtStart(ParsedDateTime.fromUTCTime(cal.getTimeInMillis()));
        cal.set(2005, 2, 21);
        invite.setDtEnd(ParsedDateTime.fromUTCTime(cal.getTimeInMillis()));
        defaultInv.invite = invite;
        CalendarItem calItem = mbox.setCalendarItem(null, Mailbox.ID_FOLDER_CALENDAR, 0, null, defaultInv, null, null, CalendarItem.NEXT_ALARM_KEEP_CURRENT);

        calItem.mData.dateChanged = (int) (cal.getTimeInMillis() / 1000L);
        Metadata meta = calItem.encodeMetadata();
        meta.remove(Metadata.FN_TZMAP);

        calItem.decodeMetadata(meta);

        Assert.assertEquals(0, calItem.getStartTime());
        Assert.assertEquals(0, calItem.getEndTime());

        meta.put(Metadata.FN_TZMAP, "foo"); //simulate existence of FN_TZMAP with bad content. In reality the metadata versions 4, 5, 6 had more subtle differences in invite encoding, but this provokes the exception we need

        calItem.decodeMetadata(meta);

        Assert.assertEquals(0, calItem.getStartTime());
        Assert.assertEquals(0, calItem.getEndTime());

        cal.set(2007, 2, 21);

        calItem.mData.dateChanged = (int) (cal.getTimeInMillis() / 1000L);

        boolean caught = false;
        try {
            calItem.decodeMetadata(meta);
        } catch (ServiceException se) {
            if (se.getCode().equalsIgnoreCase(ServiceException.INVALID_REQUEST)) {
                caught = true;
            }
        }
        Assert.assertTrue("new(er) appointment with bad metadata", caught);
    }

    @Test
    public void standardMetadataFormat() throws MailServiceException {
        String encoded = "d3:prt94:d1:X5:false1:fd1:a25:roland.schemers@gmail.com1:d6:Roland1:p15:Roland Schemerse2:noi2e1:vi10ee1:vi10ee";
        Metadata md = new Metadata(encoded);
        Assert.assertNotNull(md);
    }

    @Test
    public void legacyBlobMetadataFormat() throws MailServiceException {
        String encoded = "s=43:Roland Schemers <roland.schemers@gmail.com>;rt=44:forward schemers <roland.schemers@gmail.com>;f=74:http://support.microsoft.com/default.aspx?scid=kb;en-us;177378&Product=iep;";
        Metadata md = new Metadata(encoded);
        Assert.assertNotNull(md);
    }
}
