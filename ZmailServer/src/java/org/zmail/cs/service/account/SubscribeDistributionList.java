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
package org.zmail.cs.service.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.google.common.collect.Lists;
import com.sun.mail.smtp.SMTPMessage;
import org.zmail.common.account.ZAttrProvisioning.DistributionListSubscriptionPolicy;
import org.zmail.common.account.ZAttrProvisioning.DistributionListUnsubscriptionPolicy;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.mime.shim.JavaMailInternetAddress;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.L10nUtil;
import org.zmail.common.util.L10nUtil.MsgKey;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.zmime.ZMimeBodyPart;
import org.zmail.common.zmime.ZMimeMultipart;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.util.AccountUtil;
import org.zmail.cs.util.JMSession;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.account.type.DistributionListSubscribeOp;
import org.zmail.soap.account.type.DistributionListSubscribeStatus;

public class SubscribeDistributionList extends DistributionListDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        Account acct = getRequestedAccount(zsc);
        
        if (!canAccessAccount(zsc, acct)) {
            throw ServiceException.PERM_DENIED("can not access account");
        }

        Element response = zsc.createElement(AccountConstants.SUBSCRIBE_DISTRIBUTION_LIST_RESPONSE);

        Group group = getGroupBasic(request, prov);
        SubscribeDistributionListHandler handler = new SubscribeDistributionListHandler(
                group, request, response, prov, acct);
        handler.handle();

        return response;
    }

    private static class SubscribeDistributionListHandler extends SynchronizedGroupHandler {
        private final Element request;
        private final Element response;
        private final Provisioning prov;
        private final Account acct;

        protected SubscribeDistributionListHandler(Group group,
                Element request, Element response,
                Provisioning prov, Account acct) {
            super(group);
            this.request = request;
            this.response = response;
            this.prov = prov;
            this.acct = acct;
        }

        @Override
        protected void handleRequest() throws ServiceException {
            DistributionListSubscribeOp op =
                DistributionListSubscribeOp.fromString(request.getAttribute(AccountConstants.A_OP));

            String[] members = new String[]{acct.getName()};

            DistributionListSubscribeStatus status = null;
            boolean accepted = false;
            if (op == DistributionListSubscribeOp.subscribe) {
                DistributionListSubscriptionPolicy policy = group.getSubscriptionPolicy();

                if (policy == DistributionListSubscriptionPolicy.ACCEPT) {
                    addGroupMembers(prov, group, members);
                    accepted = true;
                    status = DistributionListSubscribeStatus.subscribed;
                } else if (policy == DistributionListSubscriptionPolicy.REJECT) {
                    throw ServiceException.PERM_DENIED("subscription policy for group " +
                            group.getName() + " is reject");
                } else { // REQUEST APPROAVAL
                    ApprovalRequestSender sender = new ApprovalRequestSender(prov, group, acct, op);
                    sender.composeAndSend();
                    status = DistributionListSubscribeStatus.awaiting_approval;
                }

            } else {
                DistributionListUnsubscriptionPolicy policy = group.getUnsubscriptionPolicy();

                if (policy == DistributionListUnsubscriptionPolicy.ACCEPT) {
                    removeGroupMembers(prov, group, members);
                    accepted = true;
                    status = DistributionListSubscribeStatus.unsubscribed;
                } else if (policy == DistributionListUnsubscriptionPolicy.REJECT) {
                    throw ServiceException.PERM_DENIED("un-subscription policy for group " +
                            group.getName() + " is reject");
                } else { // REQUEST APPROAVAL
                    ApprovalRequestSender sender = new ApprovalRequestSender(prov, group, acct, op);
                    sender.composeAndSend();
                    status = DistributionListSubscribeStatus.awaiting_approval;
                }

            }

            if (accepted) {
                ZmailLog.security.info(ZmailLog.encodeAttrs(
                            new String[] {"cmd", "SubscribeDistributionList","name", group.getName(),
                            "op", op.name(),
                            "member", Arrays.deepToString(members)}));
            }

            response.addAttribute(AccountConstants.A_STATUS, status.name());
        }

    }

    private static class ApprovalRequestSender extends NotificationSender {

        private ApprovalRequestSender(Provisioning prov, Group group,
                Account requestingAcct, DistributionListSubscribeOp op) {
            super(prov, group, requestingAcct, op);
        }

        private void composeAndSend() throws ServiceException {
            // list of owner emails
            List<String> owners = new ArrayList<String>();

            Group.GroupOwner.getOwnerEmails(group, owners);

            if (owners.size() == 0) {
                throw ServiceException.PERM_DENIED(
                        op.name() + "request needs approval but there is no owner for list " + group.getName());
            }

            sendMessage(owners.toArray(new String[owners.size()]));
        }

        private void sendMessage(String[] owners) throws ServiceException {
            try {
                SMTPMessage out = new SMTPMessage(JMSession.getSmtpSession());

                Address fromAddr = AccountUtil.getFriendlyEmailAddress(requestingAcct);

                Address replyToAddr = fromAddr;
                String replyTo = requestingAcct.getAttr(Provisioning.A_zmailPrefReplyToAddress);
                if (replyTo != null) {
                    replyToAddr = new JavaMailInternetAddress(replyTo);
                }

                // From
                out.setFrom(fromAddr);

                // Reply-To
                out.setReplyTo(new Address[]{replyToAddr});

                // To
                List<Address> addrs = Lists.newArrayList();
                for (String ownerEmail : owners) {
                    addrs.add(new JavaMailInternetAddress(ownerEmail));
                }
                out.addRecipients(javax.mail.Message.RecipientType.TO, addrs.toArray(new Address[addrs.size()]));

                // Date
                out.setSentDate(new Date());

                // since we have multiple recipients, just send in the requester's Locale
                Locale locale = getLocale(requestingAcct);

                // Subject
                String subject = L10nUtil.getMessage(MsgKey.dlSubscriptionRequestSubject, locale);
                out.setSubject(subject);

                buildContentAndSend(out, locale, "group subscription request");

            } catch (MessagingException e) {
                ZmailLog.account.warn("send share info notification failed, rcpt='" +
                        Arrays.deepToString(owners) +"'", e);
            }
        }

        @Override
        protected MimeMultipart buildMailContent(Locale locale)
        throws MessagingException {
            String text = textPart(locale);
            String html = htmlPart(locale);
            String xml = xmlPart(locale);

            // Body
            MimeMultipart mmp = new ZMimeMultipart("alternative");

            // TEXT part (add me first!)
            MimeBodyPart textPart = new ZMimeBodyPart();
            textPart.setText(text, MimeConstants.P_CHARSET_UTF8);
            mmp.addBodyPart(textPart);

            // HTML part
            MimeBodyPart htmlPart = new ZMimeBodyPart();
            htmlPart.setDataHandler(new DataHandler(new HtmlPartDataSource(html)));
            mmp.addBodyPart(htmlPart);

            // XML part
            MimeBodyPart xmlPart = new ZMimeBodyPart();
            xmlPart.setDataHandler(new DataHandler(new XmlPartDataSource(xml)));
            mmp.addBodyPart(xmlPart);

            return mmp;
        }

        private String textPart(Locale locale) {
            StringBuilder sb = new StringBuilder();

            MsgKey msgKey = DistributionListSubscribeOp.subscribe == op ? MsgKey.dlSubscribeRequestText :
                MsgKey.dlUnsubscribeRequestText;

            sb.append("\n");
            sb.append(L10nUtil.getMessage(msgKey, locale,
                    requestingAcct.getName(), group.getName()));
            sb.append("\n\n");
            return sb.toString();
        }

        private String htmlPart(Locale locale) {
            StringBuilder sb = new StringBuilder();

            sb.append("<h4>\n");
            sb.append("<p>" + textPart(locale) + "</p>\n");
            sb.append("</h4>\n");
            sb.append("\n");
            return sb.toString();
        }

        private String xmlPart(Locale locale) {
            StringBuilder sb = new StringBuilder();

            final String URI = "urn:zmailDLSubscription";
            final String VERSION = "0.1";

            // make notes xml friendly
            // notes = StringEscapeUtils.escapeXml(notes);

            String groupDisplayName = group.getDisplayName();
            groupDisplayName = groupDisplayName == null ? "" : groupDisplayName;

            String userDisplayName = requestingAcct.getDisplayName();
            userDisplayName = userDisplayName == null ? "" : userDisplayName;

            sb.append(String.format("<%s xmlns=\"%s\" version=\"%s\" action=\"%s\">\n",
                    MailConstants.E_DL_SUBSCRIPTION_NOTIFICATION, URI, VERSION, op.name()));
            sb.append(String.format("<dl id=\"%s\" email=\"%s\" name=\"%s\"/>\n",
                    group.getId(), group.getName(), groupDisplayName));
            sb.append(String.format("<user id=\"%s\" email=\"%s\" name=\"%s\"/>\n",
                    requestingAcct.getId(), requestingAcct.getName(), userDisplayName));
            sb.append(String.format("</%s>\n", MailConstants.E_DL_SUBSCRIPTION_NOTIFICATION));

            return sb.toString();
        }
    }

}
