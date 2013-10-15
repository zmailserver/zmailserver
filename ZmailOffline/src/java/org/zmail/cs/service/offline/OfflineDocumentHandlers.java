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
package org.zmail.cs.service.offline;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import org.zmail.common.account.Key;
import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.BufferStreamRequestEntity;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ZmailAuthTokenEncoded;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.util.ItemId;
import org.zmail.soap.ZmailSoapContext;

public class OfflineDocumentHandlers {
    static Pattern pattern = Pattern.compile("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}:\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}");

    static Account getTargetAccount(ZmailSoapContext zsc)
        throws ServiceException {
        String acctId = zsc.getRequestedAccountId();
        Provisioning prov = Provisioning.getInstance();
        return prov.get(Key.AccountBy.id, acctId);
    }

    static class DiffDocument extends org.zmail.cs.service.doc.DiffDocument {
        @Override
        protected String getAuthor(ZmailSoapContext zsc) throws ServiceException {
            return getTargetAccount(zsc).getName();
        }
    }

    static class ListDocumentRevisions extends
        org.zmail.cs.service.doc.ListDocumentRevisions {
        @Override
        protected String getAuthor(ZmailSoapContext zsc) throws ServiceException {
            return getTargetAccount(zsc).getName();
        }
    }

    static class SaveDocument extends org.zmail.cs.service.doc.SaveDocument {
        @Override
        protected String getAuthor(ZmailSoapContext zsc) throws ServiceException {
            return getTargetAccount(zsc).getName();
        }
    }

    public static String uploadOfflineDocument(String id, String acctId) throws ServiceException {
        HttpClient client = ZmailHttpConnectionManager.getInternalHttpConnMgr().getDefaultHttpClient();
        boolean ka = LC.httpclient_soaphttptransport_keepalive_connections.booleanValue();
        FileUploadServlet.Upload upload = FileUploadServlet.fetchUpload(
            OfflineConstants.LOCAL_ACCOUNT_ID, id, null);
        OfflineProvisioning prov = OfflineProvisioning.getOfflineInstance();
        Account account = prov.get(Key.AccountBy.id, acctId);

        if (prov.isMountpointAccount(account))
            account = prov.get(Key.AccountBy.id, account.getAttr(
                OfflineProvisioning.A_offlineMountpointProxyAccountId));
        String url = account.getAttr(OfflineConstants.A_offlineRemoteServerUri);
        if (url == null)
            throw ServiceException.FAILURE("not a zmail account: " + account.getName(), null);
        url += "/service/upload";
        
        String authToken = prov.getProxyAuthToken(acctId, null);
        BufferStreamRequestEntity bsre = null;
        String newId;
        PostMethod post = new PostMethod(url);
        HttpState state = new HttpState();
        
        try {
            bsre = new BufferStreamRequestEntity(upload.getInputStream(),
                upload.getContentType() + "; name=\"" + upload.getName() + "\"",
                upload.getSize(), 512 * 1024);
            post.setRequestEntity(bsre);
            (new ZmailAuthTokenEncoded(authToken)).encode(state, false, post.getURI().getHost());
            client.setState(state);
            post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(LC.httpclient_soaphttptransport_retry_count.intValue() - 1, true));
            post.getParams().setVersion(HttpVersion.HTTP_1_1);
            post.getParams().setSoTimeout(LC.httpclient_soaphttptransport_so_timeout.intValue());
            post.setRequestHeader("Connection", ka ? "keep-alive" : "close");
            HttpClientUtil.executeMethod(client, post);
            if (post.getStatusCode() != HttpStatus.SC_OK)
                throw ServiceException.FAILURE("http error. failed to upload to " + url, null);
            
            InputStream resp = post.getResponseBodyAsStream();
            String body = new String(ByteUtil.readInput(resp, 0, 2048));
            Matcher matcher = pattern.matcher(body);
            
            if (!matcher.find())
                throw ServiceException.FAILURE("missing upload id. failed to upload to " + url, null);
            newId = body.substring(matcher.start(), matcher.end());
        } catch (IOException e) {
            throw ServiceException.FAILURE("io error reading upload file: " + e.getMessage(), e);            
        } finally {
            post.releaseConnection();
            if (bsre != null)
                bsre.close();
        }
        return newId;
    }

    /**
     * upload attachment to remote server (shared folder's host) and use that id as attachment id
     * @param request ModifyContact or CreateContact request
     * @param iidRequested accountId part is remote account id
     * @param iidResolved id part is local attachment id
     * @throws ServiceException
     */
    static void uploadAttachmentToRemoteServer(Element request, ItemId iidRequested, ItemId iidResolved)
            throws ServiceException {
        Element eUpload = request.getElement(MailConstants.E_CONTACT);
        if (eUpload != null && OfflineProvisioning.getOfflineInstance().isMountpointAccount(iidResolved.getAccountId())) {
            Element attachment = null;
            for (Element element : eUpload.listElements(MailConstants.E_ATTRIBUTE)) {
                if ("image".equals(element.getAttribute(MailConstants.A_ATTRIBUTE_NAME))) {
                    attachment = element;
                    break;
                }
            }
            if (attachment != null) {
                String attachmentId = attachment.getAttribute(MailConstants.A_ATTACHMENT_ID);
                String acctId = iidRequested.getAccountId();
                attachment.addAttribute(MailConstants.A_ATTACHMENT_ID,
                        OfflineDocumentHandlers.uploadOfflineDocument(attachmentId, acctId));
            }
        }
    }
}
