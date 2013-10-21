/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.mailbox.Contact;
import org.zmail.cs.mailbox.ContactGroup;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mailbox.Tag;
import org.zmail.cs.mailbox.util.TagUtil;
import org.zmail.cs.mime.ParsedContact;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.FileUploadServlet.Upload;
import org.zmail.cs.service.formatter.ContactCSV;
import org.zmail.cs.service.formatter.ContactCSV.ParseException;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.client.ZMailbox;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.JaxbUtil;
import org.zmail.soap.mail.message.ImportContactsRequest;
import org.zmail.soap.mail.message.ImportContactsResponse;
import org.zmail.soap.mail.type.Content;
import org.zmail.soap.mail.type.ImportContact;

/**
 * @author schemers
 */
public class ImportContacts extends MailDocumentHandler  {

    private static final String[] TARGET_FOLDER_PATH = new String[] { MailConstants.A_FOLDER };

    @Override
    protected String[] getProxiedIdPath(Element request) {
        return TARGET_FOLDER_PATH;
    }

    @Override
    protected boolean checkMountpointProxy(Element request) {
        return true;
    }

    private String DEFAULT_FOLDER_ID = Mailbox.ID_FOLDER_CONTACTS + "";

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Mailbox mbox = getRequestedMailbox(zsc);
        OperationContext octxt = getOperationContext(zsc, context);
        ItemIdFormatter ifmt = new ItemIdFormatter(zsc);

        ImportContactsRequest req = JaxbUtil.elementToJaxb(request);
        String ct = req.getContentType();
        if (!ZMailbox.CONTACT_IMPORT_TYPE_CSV.equals(ct))
            throw ServiceException.INVALID_REQUEST("unsupported content type: " + ct, null);
        String folder = req.getFolderId();
        if (folder == null)
            folder = this.DEFAULT_FOLDER_ID;
        ItemId iidFolder = new ItemId(folder, zsc);

        String format = req.getCsvFormat();
        String locale = req.getCsvLocale();
        Content reqContent = req.getContent();
        List<Map<String, String>> contacts = null;
        List<Upload> uploads = null;
        BufferedReader reader = null;
        String attachment = reqContent.getAttachUploadId();
        try {
            if (attachment == null) {
                // Convert LF to CRLF because the XML parser normalizes element text to LF.
                String text = StringUtil.lfToCrlf(reqContent.getValue());
                reader = new BufferedReader(new StringReader(text));
            } else {
                reader = parseUploadedContent(zsc, attachment, uploads = new ArrayList<Upload>());
            }

            contacts = ContactCSV.getContacts(reader, format, locale);
            reader.close();
        } catch (IOException e) {
            throw MailServiceException.UNABLE_TO_IMPORT_CONTACTS(e.getMessage(), e);
        } catch (ParseException e) {
            throw MailServiceException.UNABLE_TO_IMPORT_CONTACTS(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) { }
            }
            if (attachment != null) {
                FileUploadServlet.deleteUploads(uploads);
            }
        }

        List<ItemId> idsList = ImportCsvContacts(octxt, mbox, iidFolder, contacts);


        ImportContactsResponse resp = new ImportContactsResponse();
        ImportContact impCntct = new ImportContact();

        for (ItemId iid : idsList) {
            impCntct.addCreatedId(iid.toString(ifmt));
        }
        impCntct.setNumImported(contacts.size());
        resp.setContact(impCntct);

        return zsc.jaxbToElement(resp);
    }

    private static BufferedReader parseUploadedContent(ZmailSoapContext lc, String attachId, List<Upload> uploads)
    throws ServiceException {
        Upload up = FileUploadServlet.fetchUpload(lc.getAuthtokenAccountId(), attachId, lc.getAuthToken());
        if (up == null) {
            throw MailServiceException.NO_SUCH_UPLOAD(attachId);
        }

        uploads.add(up);
        try {
            return new BufferedReader(new InputStreamReader(up.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            throw ServiceException.FAILURE(e.getMessage(), e);
        }
    }

    public static List<ItemId> ImportCsvContacts(OperationContext oc, Mailbox mbox,  ItemId iidFolder, List<Map<String, String>> csvContacts)
    throws ServiceException {
        List<ItemId> createdIds = new LinkedList<ItemId>();
        for (Map<String,String> contact : csvContacts) {
            String[] tags = TagUtil.decodeTags(ContactCSV.getTags(contact));
            Contact c = mbox.createContact(oc, new ParsedContact(contact), iidFolder.getId(), tags);
            createdIds.add(new ItemId(c));
            ContactGroup.MigrateContactGroup mcg = new ContactGroup.MigrateContactGroup(mbox);
            mcg.migrate(c);
        }
        return createdIds;
    }
}
