/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.qa.unittest.prov.soap;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.calendar.ZCalendar;
import org.zmail.common.soap.SoapTransport;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.index.SearchParams;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.service.mail.ToXML.EmailType;
import org.zmail.soap.mail.message.CreateAppointmentRequest;
import org.zmail.soap.mail.message.CreateAppointmentResponse;
import org.zmail.soap.mail.message.GetMsgRequest;
import org.zmail.soap.mail.message.GetMsgResponse;
import org.zmail.soap.mail.message.SearchConvRequest;
import org.zmail.soap.mail.message.SearchConvResponse;
import org.zmail.soap.mail.message.SearchRequest;
import org.zmail.soap.mail.message.SearchResponse;
import org.zmail.soap.mail.message.SendMsgRequest;
import org.zmail.soap.mail.message.SendMsgResponse;
import org.zmail.soap.mail.type.CalOrganizer;
import org.zmail.soap.mail.type.CalendarAttendee;
import org.zmail.soap.mail.type.CalendarAttendeeWithGroupInfo;
import org.zmail.soap.mail.type.DtTimeInfo;
import org.zmail.soap.mail.type.EmailAddrInfo;
import org.zmail.soap.mail.type.EmailInfo;
import org.zmail.soap.mail.type.InvitationInfo;
import org.zmail.soap.mail.type.InviteComponent;
import org.zmail.soap.mail.type.InviteComponentWithGroupInfo;
import org.zmail.soap.mail.type.InviteWithGroupInfo;
import org.zmail.soap.mail.type.MessageHitInfo;
import org.zmail.soap.mail.type.MimePartInfo;
import org.zmail.soap.mail.type.Msg;
import org.zmail.soap.mail.type.MsgSpec;
import org.zmail.soap.mail.type.MsgToSend;
import org.zmail.soap.mail.type.MsgWithGroupInfo;
import org.zmail.soap.type.SearchHit;

public class TestExpandGroupInfo extends SoapTest {
    private static SoapProvTestUtil provUtil;
    private static Provisioning prov;
    private static Domain domain;
    private static Account acct;
    private static Group group;

    @BeforeClass
    public static void init() throws Exception {
        provUtil = new SoapProvTestUtil();
        prov = provUtil.getProv();
        domain = provUtil.createDomain(baseDomainName());

        acct = provUtil.createAccount("acct", domain);
        group = provUtil.createGroup("group", domain, true);

        prov.addGroupMembers(group, new String[]{acct.getName()});
    }

    @AfterClass
    public static void cleanup() throws Exception {

        // use soap to delete account, the mailbox willalso be deleted
        if (acct != null) { // test null in case init failed
            provUtil.deleteAccount(acct);
        }

        Cleanup.deleteAll(baseDomainName());
    }

    private void sendMsg(Account authAcct, String toAddress, String subject, String content)
    throws Exception {
        SoapTransport transport = authUser(authAcct.getName());

        MsgToSend msg = new MsgToSend();

        EmailAddrInfo toAddr = new EmailAddrInfo(toAddress);
        toAddr.setAddressType(EmailType.TO.toString());
        msg.addEmailAddresse(toAddr);

        msg.setSubject(subject);

        MimePartInfo mp = new MimePartInfo();
        mp.setContentType("text/plain");
        mp.setContent(content);
        msg.setMimePart(mp);

        SendMsgRequest req = new SendMsgRequest();
        req.setMsg(msg);

        SendMsgResponse resp = invokeJaxb(transport, req);
    }

    private String createAppt(Account authAcct, String toAddress, String subject, String content)
    throws Exception {
        SoapTransport transport = authUser(authAcct.getName());

        Msg msg = new Msg();

        EmailAddrInfo toAddr = new EmailAddrInfo(toAddress);
        toAddr.setAddressType(EmailType.TO.toString());
        msg.addEmailAddresse(toAddr);

        msg.setSubject(subject);

        MimePartInfo mp = new MimePartInfo();
        mp.setContentType("multipart/alternative");
        MimePartInfo mpSub = new MimePartInfo();
        mpSub.setContent(content);
        mp.addMimePart(mpSub);
        msg.setMimePart(mp);

        InvitationInfo invite = new InvitationInfo();
        InviteComponent invComp = new InviteComponent(ZCalendar.ICalTok.REQUEST.name(), 0, false);
        CalOrganizer organizer  = new CalOrganizer();
        organizer.setAddress(authAcct.getName());
        CalendarAttendee attendee = new CalendarAttendee();
        attendee.setAddress(toAddress);
        invComp.setOrganizer(organizer);
        invComp.addAttendee(attendee);
        invComp.setDtStart(new DtTimeInfo("20120101"));
        invComp.setDtEnd(new DtTimeInfo("20120102"));
        invite.setInviteComponent(invComp);
        msg.setInvite(invite);

        CreateAppointmentRequest req = new CreateAppointmentRequest();
        req.setMsg(msg);
        CreateAppointmentResponse resp = invokeJaxb(transport, req);

        /*
        String calItemId = resp.getCalItemId();
        return calItemId;
        */

        String invId = resp.getCalInvId();
        return invId;

        /*
           <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
           <soap:Header>
               <context xmlns="urn:zmail">
               <userAgent xmlns="" name="ZmailWebClient - FF10 (Mac)" version="0.0"/>
               <session xmlns="" id="14"/>
               <account xmlns="" by="name">user1@phoebe.mbp</account>
               <format xmlns="" type="js"/>
               </context>
           </soap:Header>

           <soap:Body>
           <CreateAppointmentRequest xmlns="urn:zmailMail">
               <m xmlns="" l="10">
                   <inv>
                       <comp status="CONF" fb="B" class="PUB" transp="O" draft="0" allDay="0" name="test" loc="">
                           <at role="REQ" ptst="NE" rsvp="1" a="user2@phoebe.mbp"/>
                           <at role="REQ" ptst="NE" rsvp="1" a="user3@phoebe.mbp"/>
                           <s tz="America/Los_Angeles" d="20120227T080000"/>
                           <e tz="America/Los_Angeles" d="20120227T083000"/>
                           <or a="user1@phoebe.mbp" d="Demo User One"/>
                           <alarm action="DISPLAY">
                               <trigger>
                                   <rel m="5" related="START" neg="1"/>
                               </trigger>
                           </alarm>
                       </comp>
                   </inv>

                   <e a="user2@phoebe.mbp" t="t"/>
                   <e a="user3@phoebe.mbp" t="t"/>

                   <su>test</su>

                   <mp ct="multipart/alternative">
                       <mp ct="text/plain">
                           <content>The following is a new meeting request: Subject: test Organizer: "Demo User One" &lt;user1@phoebe.mbp&gt; Time: Monday, February 27, 2012, 8:00:00 AM - 8:30:00 AM GMT -08:00 US/Canada Pacific Invitees: user2@phoebe.mbp; user3@phoebe.mbp *~*~*~*~*~*~*~*~*~* </content></mp><mp ct="text/html"><content>&lt;html&gt;&lt;body&gt;&lt;h3&gt;The following is a new meeting request:&lt;/h3&gt; &lt;p&gt; &lt;table border='0'&gt; &lt;tr&gt;&lt;th align=left&gt;Subject:&lt;/th&gt;&lt;td&gt;test &lt;/td&gt;&lt;/tr&gt; &lt;tr&gt;&lt;th align=left&gt;Organizer:&lt;/th&gt;&lt;td&gt;"Demo User One" &amp;lt;user1@phoebe.mbp&amp;gt; &lt;/td&gt;&lt;/tr&gt; &lt;/table&gt; &lt;p&gt; &lt;table border='0'&gt; &lt;tr&gt;&lt;th align=left&gt;Time:&lt;/th&gt;&lt;td&gt;Monday, February 27, 2012, 8:00:00 AM - 8:30:00 AM GMT -08:00 US/Canada Pacific &lt;/td&gt;&lt;/tr&gt;&lt;/table&gt; &lt;p&gt; &lt;table border='0'&gt; &lt;tr&gt;&lt;th align=left&gt;Invitees:&lt;/th&gt;&lt;td&gt;user2@phoebe.mbp; user3@phoebe.mbp &lt;/td&gt;&lt;/tr&gt; &lt;/table&gt; &lt;div&gt;*~*~*~*~*~*~*~*~*~*&lt;/div&gt;&lt;br&gt;&lt;/body&gt;&lt;/html&gt;</content>
                       </mp>
                   </mp>
               </m>
           </CreateAppointmentRequest>
           </soap:Body></soap:Envelope>

         */
    }

    private void verifyGroupInfo(MessageHitInfo hit,
            Boolean expectedIsGroup, Boolean expectedCanExpand) {
        List<EmailInfo> emails = hit.getEmails();
        for (EmailInfo emailInfo : emails) {
            String addrType = emailInfo.getAddressType();
            if (EmailType.TO.toString().equals(addrType)) {
                String addr = emailInfo.getAddress();
                Boolean isGroup = emailInfo.getGroup();
                Boolean canExpandGroupMembers = emailInfo.getCanExpandGroupMembers();
                assertEquals(expectedIsGroup, isGroup);
                assertEquals(canExpandGroupMembers, expectedCanExpand);
                return;
            }
        }

        fail();  // should never get here
    }

    @Test
    public void searchConversation() throws Exception {
        // send a to acct, recipient is a group
        String SUBJECT = getTestName();
        sendMsg(acct, group.getName(), SUBJECT, "blah");

        SoapTransport transport = authUser(acct.getName());

        SearchRequest searchReq = new SearchRequest();
        searchReq.setSearchTypes(MailItem.Type.CONVERSATION.toString());
        searchReq.setQuery(String.format("in:inbox and subject:%s", SUBJECT));
        SearchResponse searchResp = invokeJaxb(transport, searchReq);
        List<SearchHit> searchHits = searchResp.getSearchHits();
        assertEquals(1, searchHits.size());

        SearchHit searchHit = searchHits.get(0);
        String convId = searchHit.getId();

        SearchConvRequest searchConvReq = new SearchConvRequest(convId);
        searchConvReq.setNeedCanExpand(Boolean.TRUE);
        searchConvReq.setFetch(SearchParams.ExpandResults.ALL.toString());
        SearchConvResponse searchConvResp = invokeJaxb(transport, searchConvReq);
        List<MessageHitInfo> hits = searchConvResp.getMessages();
        assertEquals(2, hits.size());  // 2 - one in inbox, one in sent folder
        verifyGroupInfo(hits.get(0), Boolean.TRUE, Boolean.TRUE);
        verifyGroupInfo(hits.get(1), Boolean.TRUE, Boolean.TRUE);
    }

    @Test
    public void search() throws Exception {
        // send a to acct, recipient is a group
        String SUBJECT = getTestName();
        sendMsg(acct, group.getName(), SUBJECT, "blah");

        SoapTransport transport = authUser(acct.getName());

        SearchRequest searchReq = new SearchRequest();
        searchReq.setSearchTypes(MailItem.Type.MESSAGE.toString());
        searchReq.setQuery(String.format("in:inbox and subject:%s", SUBJECT));
        searchReq.setFetch(SearchParams.ExpandResults.ALL.toString());
        SearchResponse searchResp = invokeJaxb(transport, searchReq);
        List<SearchHit> searchHits = searchResp.getSearchHits();
        assertEquals(1, searchHits.size());

        verifyGroupInfo((MessageHitInfo) searchHits.get(0), null, null);

        /*
         * search again with needExp
         */
        searchReq.setNeedCanExpand(Boolean.TRUE);
        searchResp = invokeJaxb(transport, searchReq);
        searchHits = searchResp.getSearchHits();
        assertEquals(1, searchHits.size());

        verifyGroupInfo((MessageHitInfo) searchHits.get(0), Boolean.TRUE, Boolean.TRUE);
    }

    @Test
    public void getMsg() throws Exception {
        String SUBJECT = getTestName();
        String msgId = createAppt(acct, group.getName(), SUBJECT, "blah");

        SoapTransport transport = authUser(acct.getName());

        MsgSpec msgSpec = new MsgSpec(msgId);
        msgSpec.setNeedCanExpand(Boolean.TRUE);
        GetMsgRequest req = new GetMsgRequest(msgSpec);
        GetMsgResponse resp = invokeJaxb(transport, req);

        MsgWithGroupInfo msg = resp.getMsg();
        InviteWithGroupInfo invite = msg.getInvite();
        List<InviteComponentWithGroupInfo> invComps = invite.getInviteComponents();

        for (InviteComponentWithGroupInfo invComp : invComps) {
            List<CalendarAttendeeWithGroupInfo> attendees = invComp.getAttendees();
            for (CalendarAttendeeWithGroupInfo attendee : attendees) {
                Boolean isGroup = attendee.getGroup();
                Boolean canExpandGroupMembers = attendee.getCanExpandGroupMembers();
                assertTrue(isGroup);
                assertTrue(canExpandGroupMembers);
            }
        }

    }
}
