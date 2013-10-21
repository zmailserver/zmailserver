/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.doc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimePart;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ZAttrProvisioning;
import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.mime.ContentType;
import org.zmail.common.mime.MimeConstants;
import org.zmail.common.mime.MimeDetect;
import org.zmail.common.service.ServiceException;
import org.zmail.common.service.ServiceException.Argument;
import org.zmail.common.service.ServiceException.InternalArgument;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.Pair;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Document;
import org.zmail.cs.mailbox.Flag;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.Message;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.mime.ParsedDocument;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.FileUploadServlet.Upload;
import org.zmail.cs.service.UserServlet;
import org.zmail.cs.service.mail.UploadScanner;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.ZmailSoapContext;

public class SaveDocument extends DocDocumentHandler {

    private static String[] TARGET_DOC_ID_PATH = new String[] { MailConstants.E_DOC, MailConstants.A_ID };
    private static String[] TARGET_DOC_FOLDER_PATH = new String[] { MailConstants.E_DOC, MailConstants.A_FOLDER };

    @Override protected String[] getProxiedIdPath(Element request) {
        String id = getXPath(request, TARGET_DOC_ID_PATH);
        return id == null ? TARGET_DOC_FOLDER_PATH : TARGET_DOC_ID_PATH;
    }

    private static final String DEFAULT_DOCUMENT_FOLDER = "" + Mailbox.ID_FOLDER_BRIEFCASE;

    @Override public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        OperationContext octxt = getOperationContext(zsc, context);

        Element docElem = request.getElement(MailConstants.E_DOC);

        Doc doc = null;
        Element response = null;
        boolean success = false;
        Mailbox mbox = null;
        int folderId = 0;
        try {
            String explicitName = docElem.getAttribute(MailConstants.A_NAME, null);
            String explicitCtype = docElem.getAttribute(MailConstants.A_CONTENT_TYPE, null);

            //bug 37180, extract the filename from the path (for IE). IE sends the full path.
            if (explicitName != null) {
                try {
                    explicitName = explicitName.replaceAll("\\\\", "/");
                    explicitName = explicitName.substring(explicitName.lastIndexOf("/") + 1);
                } catch (Exception e) {
                    //Do nothing
                }
            }

            String description = docElem.getAttribute(MailConstants.A_DESC, null);
            ItemId fid = new ItemId(docElem.getAttribute(MailConstants.A_FOLDER, DEFAULT_DOCUMENT_FOLDER), zsc);
            folderId = fid.getId();

            String id = docElem.getAttribute(MailConstants.A_ID, null);
            int itemId = id == null ? 0 : new ItemId(id, zsc).getId();
            int ver = (int) docElem.getAttributeLong(MailConstants.A_VERSION, 0);

            mbox = getRequestedMailbox(zsc);
            Element attElem = docElem.getOptionalElement(MailConstants.E_UPLOAD);
            Element msgElem = docElem.getOptionalElement(MailConstants.E_MSG);
            Element docRevElem = docElem.getOptionalElement(MailConstants.E_DOC);
            if (attElem != null) {
                String aid = attElem.getAttribute(MailConstants.A_ID, null);
                Upload up = FileUploadServlet.fetchUpload(zsc.getAuthtokenAccountId(), aid, zsc.getAuthToken());
                // scan upload for viruses
                StringBuffer info = new StringBuffer();
                UploadScanner.Result result = UploadScanner.accept(up, info);
                if (result == UploadScanner.REJECT)
                    throw MailServiceException.UPLOAD_REJECTED(up.getName(), info.toString());
                if (result == UploadScanner.ERROR)
                    throw MailServiceException.SCAN_ERROR(up.getName());

                doc = new Doc(up, explicitName, explicitCtype, description);
            } else if (msgElem != null) {
                String part = msgElem.getAttribute(MailConstants.A_PART);
                ItemId iid = new ItemId(msgElem.getAttribute(MailConstants.A_ID), zsc);
                doc = fetchMimePart(octxt, zsc.getAuthToken(), iid, part, explicitName, explicitCtype, description);
            } else if (docRevElem != null) {
                ItemId iid = new ItemId(docRevElem.getAttribute(MailConstants.A_ID), zsc);
                int revSource = (int) docRevElem.getAttributeLong(MailConstants.A_VERSION, 0);
                Account sourceAccount = Provisioning.getInstance().getAccountById(iid.getAccountId());
                if (sourceAccount.getId().equals(zsc.getRequestedAccountId())) {
                    Document docRev;
                    if (revSource == 0) {
                        docRev = mbox.getDocumentById(octxt, iid.getId());
                    } else {
                        docRev = (Document) mbox.getItemRevision(octxt, iid.getId(), MailItem.Type.DOCUMENT, revSource);
                    }
                    doc = new Doc(docRev);
                } else {
                    doc = new Doc(zsc.getAuthToken(), sourceAccount, iid.getId(), revSource);
                }
                // preserve the old name when adding a new revision with
                // the content from another document
                if (ver != 0) {
                    doc.name = null;
                }
            } else {
                String inlineContent = docElem.getAttribute(MailConstants.E_CONTENT);
                doc = new Doc(inlineContent, explicitName, explicitCtype, description);
            }
            
            // set content-type based on file extension.
            if (doc.name != null) {
                String guess = MimeDetect.getMimeDetect().detect(doc.name);
                if (guess != null)
                    doc.contentType = guess;
            }

            Document docItem = null;
            InputStream is = null;
            try {
                is = doc.getInputStream();
            } catch (IOException e) {
                throw ServiceException.FAILURE("can't save document", e);
            }
            if (itemId == 0) {
                // create a new page
                if (doc.name == null || doc.name.trim().equals("")) {
                    throw ServiceException.INVALID_REQUEST("missing required attribute: " + MailConstants.A_NAME, null);
                } else if (doc.contentType == null || doc.contentType.trim().equals("")) {
                    throw ServiceException.INVALID_REQUEST("missing required attribute: " + MailConstants.A_CONTENT_TYPE, null);
                }
                boolean descEnabled = docElem.getAttributeBool(MailConstants.A_DESC_ENABLED, true);
                try {
                    ParsedDocument pd = new ParsedDocument(is, doc.name, doc.contentType, System.currentTimeMillis(),
                        getAuthor(zsc), doc.description, descEnabled);
                    String flags = docElem.getAttribute(MailConstants.A_FLAGS, null);
                    docItem = mbox.createDocument(octxt, folderId, pd, MailItem.Type.DOCUMENT, Flag.toBitmask(flags));
                } catch (IOException e) {
                    throw ServiceException.FAILURE("unable to create document", e);
                }
            } else {
                // add a new revision
                docItem = mbox.getDocumentById(octxt, itemId);
                if (docItem.getVersion() != ver) {
                    throw MailServiceException.MODIFY_CONFLICT(new Argument(MailConstants.A_NAME, doc.name, Argument.Type.STR), new Argument(MailConstants.A_ID, itemId,
                            Argument.Type.IID), new Argument(MailConstants.A_VERSION, docItem.getVersion(), Argument.Type.NUM));
                }
                String name = docItem.getName();
                if (doc.name != null) {
                    name = doc.name;
                }
                boolean descEnabled = docElem.getAttributeBool(MailConstants.A_DESC_ENABLED, docItem.isDescriptionEnabled());
                docItem = mbox.addDocumentRevision(octxt, itemId, getAuthor(zsc), name, doc.description, descEnabled, is);
            }

            response = zsc.createElement(MailConstants.SAVE_DOCUMENT_RESPONSE);
            Element m = response.addElement(MailConstants.E_DOC);
            m.addAttribute(MailConstants.A_ID, new ItemIdFormatter(zsc).formatItemId(docItem));
            m.addAttribute(MailConstants.A_VERSION, docItem.getVersion());
            m.addAttribute(MailConstants.A_NAME, docItem.getName());
            success = true;
        } catch (ServiceException e) {
            if (e.getCode().equals(MailServiceException.ALREADY_EXISTS)) {
                MailItem item = null;
                if (mbox != null && folderId != 0) {
                    item = mbox.getItemByPath(octxt, doc.name, folderId);
                }
                if (item != null && item instanceof Document) {
                    // name clash with another Document
                    throw MailServiceException.ALREADY_EXISTS("name " + doc.name + " in folder " + folderId, doc.name, item.getId(), ((Document) item).getVersion());
                } else if (item != null) {
                    // name clash with a folder
                    throw MailServiceException.ALREADY_EXISTS("name " + doc.name + " in folder " + folderId, doc.name, item.getId());
                }
            }
            throw e;
        } finally {
            if (success && doc != null) {
                doc.cleanup();
            }
        }
        return response;
    }

    private Doc fetchMimePart(OperationContext octxt, AuthToken authtoken, ItemId itemId, String partId, String name, String ct, String description) throws ServiceException {
        String accountId = itemId.getAccountId();
        Account acct = Provisioning.getInstance().get(AccountBy.id, accountId);
        if (Provisioning.onLocalServer(acct)) {
            Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(acct);
            Message msg = mbox.getMessageById(octxt, itemId.getId());
            try {
                return new Doc(Mime.getMimePart(msg.getMimeMessage(), partId), name, ct);
            } catch (MessagingException e) {
                throw ServiceException.RESOURCE_UNREACHABLE("can't fetch mime part msgId=" + itemId + ", partId=" + partId, e);
            } catch (IOException e) {
                throw ServiceException.RESOURCE_UNREACHABLE("can't fetch mime part msgId=" + itemId + ", partId=" + partId, e);
            }
        }

        String url = UserServlet.getRestUrl(acct) + "?auth=co&id=" + itemId + "&part=" + partId;
        HttpClient client = ZmailHttpConnectionManager.getInternalHttpConnMgr().newHttpClient();
        GetMethod get = new GetMethod(url);
        authtoken.encode(client, get, false, acct.getAttr(ZAttrProvisioning.A_zmailMailHost));
        try {
            int statusCode = HttpClientUtil.executeMethod(client, get);
            if (statusCode != HttpStatus.SC_OK) {
                throw ServiceException.RESOURCE_UNREACHABLE("can't fetch remote mime part", null, new InternalArgument(ServiceException.URL, url, Argument.Type.STR));
            }

            Header ctHeader = get.getResponseHeader("Content-Type");
            ContentType contentType = new ContentType(ctHeader.getValue());

            return new Doc(get.getResponseBodyAsStream(), contentType, name, ct, description);
        } catch (HttpException e) {
            throw ServiceException.PROXY_ERROR(e, url);
        } catch (IOException e) {
            throw ServiceException.RESOURCE_UNREACHABLE("can't fetch remote mime part", e, new InternalArgument(ServiceException.URL, url, Argument.Type.STR));
        }
    }

    private static class Doc {
        String name;
        String contentType;
        String description;
        private Upload up;
        private MimePart mp;
        private String sp;
        private InputStream in;

        Doc(MimePart mpart, String filename, String ctype) {
            mp = mpart;
            name = Mime.getFilename(mpart);
            contentType = Mime.getContentType(mpart);
            overrideProperties(filename, ctype);
        }

        Doc(Upload upload, String filename, String ctype, String d) {
            up = upload;
            name = upload.getName();
            contentType = upload.getContentType();
            description = d;
            overrideProperties(filename, ctype);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        }

        Doc(String content, String filename, String ctype, String d) {
            sp = content;
            description = d;
            overrideProperties(filename, ctype);
            if (contentType != null) {
                contentType = new ContentType(contentType).setParameter("charset", "utf-8").toString();
            }
        }

        Doc(InputStream in, ContentType ct, String filename, String ctype, String d) {
            this.in = in;
            description = d;
            name = ct == null ? null : ct.getParameter("name");
            if (name == null) {
                name = "New Document";
            }
            contentType = ct == null ? null : ct.getContentType();
            if (contentType == null) {
                contentType = MimeConstants.CT_APPLICATION_OCTET_STREAM;
            }
            overrideProperties(filename, ctype);
        }

        Doc (Document d) throws ServiceException {
            in = d.getContentStream();
            contentType = d.getContentType();
            name = d.getName();
            description = d.getDescription();
        }
        Doc(AuthToken auth, Account acct, int id, int ver) throws ServiceException {
            String url = UserServlet.getRestUrl(acct) + "?fmt=native&disp=attachment&id=" + id;
            if (ver > 0) {
                url += "&ver=" + ver;
            }
            Pair<Header[], byte[]> resource = UserServlet.getRemoteResource(auth.toZAuthToken(), url);
            int status = 0;
            for (Header h : resource.getFirst()) {
                if (h.getName().equalsIgnoreCase("X-Zmail-Http-Status")) {
                    status = Integer.parseInt(h.getValue());
                } else if (h.getName().equalsIgnoreCase("X-Zmail-ItemName")) {
                    name = h.getValue();
                } else if (h.getName().equalsIgnoreCase("Content-Type")) {
                    contentType = h.getValue();
                }
            }
            if (status != 200) {
                throw ServiceException.RESOURCE_UNREACHABLE("http error " + status, null);
            }
            in = new ByteArrayInputStream(resource.getSecond());
        }

        private void overrideProperties(String filename, String ctype) {
            if (filename != null && !filename.trim().equals("")) {
                name = filename;
            }
            if (ctype != null && !ctype.trim().equals("")) {
                contentType = ctype;
            }
        }

        public InputStream getInputStream() throws IOException {
            try {
                if (up != null) {
                    return up.getInputStream();
                } else if (mp != null) {
                    return mp.getInputStream();
                } else if (sp != null) {
                    return new ByteArrayInputStream(sp.getBytes("utf-8"));
                } else if (in != null) {
                    return in;
                } else {
                    throw new IOException("no contents");
                }
            } catch (MessagingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public void cleanup() {
            if (up != null) {
                FileUploadServlet.deleteUpload(up);
            }
            ByteUtil.closeStream(in);
        }
    }

    @Override public boolean isReadOnly() {
        return false;
    }
}
