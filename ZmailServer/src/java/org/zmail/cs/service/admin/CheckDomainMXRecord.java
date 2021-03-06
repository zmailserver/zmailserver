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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.CheckDomainMXRecordRequest;
import org.zmail.soap.admin.message.CheckDomainMXRecordResponse;

import  javax.naming.*;
import  javax.naming.directory.*;

import  java.util.Hashtable;

public class CheckDomainMXRecord extends AdminDocumentHandler {

    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        CheckDomainMXRecordRequest req = JaxbUtil.elementToJaxb(request);
        DomainBy domainBy = req.getDomain().getBy().toKeyDomainBy();
        String value = req.getDomain().getKey();

        Domain domain = prov.get(domainBy, value);

        checkDomainRight(zsc, domain, Admin.R_checkDomainMXRecord);

        String SMTPHost = domain.getAttr(Provisioning.A_zmailDNSCheckHostname, true);
        String domainName = domain.getName();
        if(SMTPHost == null || SMTPHost.length()<1)
            SMTPHost = domain.getAttr(Provisioning.A_zmailSmtpHostname, false);

        if(SMTPHost == null || SMTPHost.length()<1)
            SMTPHost = prov.getLocalServer().getAttr(Provisioning.A_zmailSmtpHostname);

        if(SMTPHost == null || SMTPHost.length()<1)
            SMTPHost = prov.getConfig().getAttr(Provisioning.A_zmailSmtpHostname);

        if(SMTPHost == null || SMTPHost.length()<1)
            SMTPHost = domain.getName();

        String SMTPHostMatch = String.format("^\\d+\\s%s\\.$", SMTPHost);
        ZmailLog.soap.info("checking domain mx record");
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        String message = String.format("Domain is configured to use SMTP host: %s. None of the MX records match this name.", SMTPHost);
        // Element response = zsc.createElement(AdminConstants.CHECK_DOMAIN_MX_RECORD_RESPONSE);
        CheckDomainMXRecordResponse resp = new CheckDomainMXRecordResponse();
        boolean found = false;
        try {
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(domainName, new String[] {"MX"});
            if(attrs.size()<1) {
                throw ServiceException.FAILURE("NoMXRecordsForDomain", null);
            }
            for (NamingEnumeration<? extends Attribute> ne = attrs.getAll(); ne.hasMore(); ) {
                Attribute attr = (Attribute) ne.next();
                if (attr.size() == 1) {
                    ZmailLog.soap.info("single attribute");
                    Object o = attr.get();
                    if (o instanceof String) {
                        String rec = o.toString();
                        ZmailLog.soap.info("found MX record " + rec);
                        if(rec.matches(SMTPHostMatch)) {
                            found = true;
                            break;
                        }
                        resp.addEntry(rec);
                    } else {
                        String rec = new String((byte[])o);
                        ZmailLog.soap.info("found MX attribute " + attr.getID() + " = "+ rec);
                        if(rec.matches(SMTPHostMatch)) {
                            found = true;
                            break;
                        }
                        resp.addEntry(rec);
                    }

                } else {
                    ZmailLog.soap.info("multivalued attribute");
                    for (int i=0; i < attr.size(); i++) {
                        Object o = attr.get(i);
                        if (o instanceof String) {
                            String rec = o.toString();
                            ZmailLog.soap.info("found MX record " + attr.getID() + "-" + Integer.toString(i) + " = " + rec);
                            if(rec.matches(SMTPHostMatch)) {
                                found = true;
                                break;
                            }
                            resp.addEntry(rec);
                        } else {
                            String rec = new String((byte[])o);
                            ZmailLog.soap.info("found MX attribute " + attr.getID() + "-" + Integer.toString(i) + " = "+ rec);
                            if(rec.matches(SMTPHostMatch)) {
                                found = true;
                                break;
                            }
                            resp.addEntry(rec);
                            //message = String.format("%s %s", message,rec);
                        }
                    }

                }
            }
            if(found)
                resp.setCode("Ok");
            else {
                resp.setCode("Failed");
                resp.setMessage(message);
            }
        } catch (NameNotFoundException e) {
            throw ServiceException.FAILURE("NameNotFoundException", e);
        }
        catch (NamingException e) {
            throw ServiceException.FAILURE("Failed to verify domain's MX record. " + e.getMessage(), e);
        }
        return zsc.jaxbToElement(resp);
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_checkDomainMXRecord);
    }
}
