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
package org.zmail.cs.store.triton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.localconfig.LC;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.store.BlobBuilder;
import org.zmail.cs.store.external.ExternalResumableOutputStream;
import org.zmail.cs.store.triton.TritonBlobStoreManager.HashType;

/**
 * Output stream that writes to TDS, calculates digest, and buffers to local BlobBuilder
 *
 */
public class TritonIncomingOutputStream extends ExternalResumableOutputStream {

    protected final String baseUrl;
    private final TritonUploadUrl uploadUrl;
    protected final MessageDigest digest;
    protected final HashType hashType;
    protected final MozyServerToken serverToken;
    protected AtomicLong written;
    protected ByteArrayOutputStream baos;

    public TritonIncomingOutputStream(BlobBuilder blobBuilder, MessageDigest digest, HashType hashType, String baseUrl, TritonUploadUrl uploadUrl, MozyServerToken serverToken, AtomicLong written) {
        super(blobBuilder);
        this.baseUrl = baseUrl;
        this.uploadUrl = uploadUrl;
        this.hashType = hashType;
        this.serverToken = serverToken;
        this.digest = digest;
        this.written = written;
        this.baos = new ByteArrayOutputStream(LC.triton_upload_buffer_size.intValue());
    }

    @Override
    protected void writeToExternal(byte[] b, int off, int len) throws IOException {
        baos.write(b, off, len);
        if (baos.size() >= LC.triton_upload_buffer_size.intValue()) {
            sendHttpData();
        }
    }

    @Override
    public void flush() throws IOException {
        if (baos.size() > 0) {
            sendHttpData();
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        if (baos.size() > 0) {
            sendHttpData();
        }
    }

    private void sendHttpData() throws IOException {
        HttpClient client = ZmailHttpConnectionManager.getInternalHttpConnMgr().newHttpClient();
        PostMethod post;
        boolean started = false;
        if (uploadUrl.isInitialized()) {
            started = true;
            post = new PostMethod(baseUrl + uploadUrl);
        } else {
            post = new PostMethod(baseUrl + "/blob");
        }
        try {
            ZmailLog.store.info("posting to %s",post.getURI());
            HttpClientUtil.addInputStreamToHttpMethod(post, new ByteArrayInputStream(baos.toByteArray()), baos.size(), "application/octet-stream");
            post.addRequestHeader(TritonHeaders.CONTENT_LENGTH, baos.size()+"");
            post.addRequestHeader(TritonHeaders.HASH_TYPE, hashType.toString());
            post.addRequestHeader("Content-Range", "bytes " + written.longValue() + "-" + (written.longValue()+baos.size()-1)+ "/*");
            if (serverToken.getToken() != null) {
                post.addRequestHeader(TritonHeaders.SERVER_TOKEN, serverToken.getToken());
            }
            int statusCode = HttpClientUtil.executeMethod(client, post);
            if (statusCode == HttpStatus.SC_OK) {
                handleResponse(post);
            } else if (!started && statusCode == HttpStatus.SC_SEE_OTHER) {
                started = true;
                uploadUrl.setUploadUrl(post.getResponseHeader(TritonHeaders.LOCATION).getValue());
                handleResponse(post);
            } else {
                throw new IOException("Unable to append, bad response code "+statusCode);
            }
        } finally {
            post.releaseConnection();
        }
        baos = new ByteArrayOutputStream(LC.triton_upload_buffer_size.intValue());
    }

  private void handleResponse(PostMethod post) throws IOException {
      serverToken.setToken(post);
      written.set(written.longValue() + baos.size());
      digest.update(baos.toByteArray());
  }
}
