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
package org.zmail.cs.zimlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.service.AuthProvider;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.mime.ContentDisposition;
import org.zmail.common.mime.ContentType;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.httpclient.HttpProxyUtil;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.FileUploadServlet.Upload;
import org.zmail.cs.servlet.ZmailServlet;
import org.zmail.common.localconfig.LC;
/**
 * @author jylee
 */
@SuppressWarnings("serial")
public class ProxyServlet extends ZmailServlet {

    private static final String TARGET_PARAM = "target";

    private static final String UPLOAD_PARAM = "upload";
    private static final String FILENAME_PARAM = "filename";
    private static final String FORMAT_PARAM = "fmt";

    private static final String USER_PARAM = "user";
    private static final String PASS_PARAM = "pass";
    private static final String AUTH_PARAM = "auth";
    private static final String AUTH_BASIC = "basic";
   

    private Set<String> getAllowedDomains(AuthToken auth) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        Account acct = prov.get(AccountBy.id, auth.getAccountId(), auth);

        Cos cos = prov.getCOS(acct);

        Set<String> allowedDomains = cos.getMultiAttrSet(Provisioning.A_zmailProxyAllowedDomains);

        ZmailLog.zimlet.debug("get allowedDomains result: "+allowedDomains);
        
        return allowedDomains;
    }
    
    private boolean checkPermissionOnTarget(URL target, AuthToken auth) {
        String host = target.getHost().toLowerCase();
        ZmailLog.zimlet.debug("checking allowedDomains permission on target host: "+host);
        Set<String> domains;
        try {
            domains = getAllowedDomains(auth);
        } catch (ServiceException se) {
            ZmailLog.zimlet.info("error getting allowedDomains: "+se.getMessage());
            return false;
        }
        for (String domain : domains) {
            if (domain.equals("*")) {
                return true;
            }
            if (domain.charAt(0) == '*') {
                domain = domain.substring(1);
            }
            if (host.endsWith(domain)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canProxyHeader(String header) {
        if (header == null) return false;
        header = header.toLowerCase();
        if (header.startsWith("accept") ||
            header.equals("content-length") ||
            header.equals("connection") ||
            header.equals("keep-alive") ||
            header.equals("pragma") ||
            header.equals("host") ||
            //header.equals("user-agent") ||
            header.equals("cache-control") ||
            header.equals("cookie") ||
            header.equals("transfer-encoding")) {
            return false;
        }
        return true;
    }
    
    private byte[] copyPostedData(HttpServletRequest req) throws IOException {
        int size = req.getContentLength();
        if (req.getMethod().equalsIgnoreCase("GET") || size <= 0) {
            return null;
        }
        InputStream is = req.getInputStream();
        ByteArrayOutputStream baos = null;
        try {
            if (size < 0)
                size = 0; 
            baos = new ByteArrayOutputStream(size);
            byte[] buffer = new byte[8192];
            int num;
            while ((num = is.read(buffer)) != -1) {
                baos.write(buffer, 0, num);
            }
            return baos.toByteArray();
        } finally {
            ByteUtil.closeStream(baos);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doProxy(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doProxy(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doProxy(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doProxy(req, resp);
    }

    protected boolean isAdminRequest(HttpServletRequest req) {
        return req.getServerPort() == LC.zmail_admin_service_port.intValue();
    }
    
    private static final String DEFAULT_CTYPE = "text/xml";

    private void doProxy(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ZmailLog.clearContext();
        boolean isAdmin = isAdminRequest(req);
        AuthToken authToken = isAdmin ?
                getAdminAuthTokenFromCookie(req, resp, true) : getAuthTokenFromCookie(req, resp, true);
        if (authToken == null) {
            String zAuthToken = req.getParameter(QP_ZAUTHTOKEN);
            if (zAuthToken != null) {
                try {
                    authToken = AuthProvider.getAuthToken(zAuthToken);
                    if (authToken.isExpired()) {
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "authtoken expired");
                        return;
                    }
                    if (isAdmin && !authToken.isAdmin()) {
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "permission denied");
                        return;
                    }
                } catch (AuthTokenException e) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unable to parse authtoken");
                    return;
                }
            }
        }
        if (authToken == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "no authtoken cookie");
            return;
        }

        // get the posted body before the server read and parse them.
        byte[] body = copyPostedData(req);

        // sanity check
        String target = req.getParameter(TARGET_PARAM);
        if (target == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // check for permission
        URL url = new URL(target);
        if (!isAdmin && !checkPermissionOnTarget(url, authToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // determine whether to return the target inline or store it as an upload
        String uploadParam = req.getParameter(UPLOAD_PARAM);
        boolean asUpload = uploadParam != null && (uploadParam.equals("1") || uploadParam.equalsIgnoreCase("true"));

        HttpMethod method = null;
        try {
            HttpClient client = ZmailHttpConnectionManager.getExternalHttpConnMgr().newHttpClient();
            HttpProxyUtil.configureProxy(client);
            String reqMethod = req.getMethod();
            if (reqMethod.equalsIgnoreCase("GET")) {
                method = new GetMethod(target);
            } else if (reqMethod.equalsIgnoreCase("POST")) {
                PostMethod post = new PostMethod(target);
                if (body != null)
                    post.setRequestEntity(new ByteArrayRequestEntity(body, req.getContentType()));
                method = post;
            } else if (reqMethod.equalsIgnoreCase("PUT")) {
                PutMethod put = new PutMethod(target);
                if (body != null)
                    put.setRequestEntity(new ByteArrayRequestEntity(body, req.getContentType()));
                method = put;
            } else if (reqMethod.equalsIgnoreCase("DELETE")) {
                method = new DeleteMethod(target);
            } else {
                ZmailLog.zimlet.info("unsupported request method: " + reqMethod);
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return;
            }

            // handle basic auth
            String auth, user, pass;
            auth = req.getParameter(AUTH_PARAM);
            user = req.getParameter(USER_PARAM);
            pass = req.getParameter(PASS_PARAM);
            if (auth != null && user != null && pass != null) {
                if (!auth.equals(AUTH_BASIC)) {
                    ZmailLog.zimlet.info("unsupported auth type: " + auth);
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                HttpState state = new HttpState();
                state.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pass));
                client.setState(state);
                method.setDoAuthentication(true);
            }
            
            Enumeration headers = req.getHeaderNames();
            while (headers.hasMoreElements()) {
                String hdr = (String) headers.nextElement();
            	ZmailLog.zimlet.debug("incoming: " + hdr + ": " + req.getHeader(hdr));
                if (canProxyHeader(hdr)) {
                	ZmailLog.zimlet.debug("outgoing: " + hdr + ": " + req.getHeader(hdr));
                	if (hdr.equalsIgnoreCase("x-host"))
                		method.getParams().setVirtualHost(req.getHeader(hdr));
                	else
                		method.addRequestHeader(hdr, req.getHeader(hdr));
                }
            }
            
            try {
                HttpClientUtil.executeMethod(client, method);
            } catch (HttpException ex) {
                ZmailLog.zimlet.info("exception while proxying " + target, ex);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            int status = method.getStatusLine() == null ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : method.getStatusCode();

            // workaround for Alexa Thumbnails paid web service, which doesn't bother to return a content-type line
            Header ctHeader = method.getResponseHeader("Content-Type");
            String contentType = ctHeader == null || ctHeader.getValue() == null ? DEFAULT_CTYPE : ctHeader.getValue();

            InputStream targetResponseBody = method.getResponseBodyAsStream();
            
            if (asUpload) {
                String filename = req.getParameter(FILENAME_PARAM);
                if (filename == null || filename.equals(""))
                    filename = new ContentType(contentType).getParameter("name");
                if ((filename == null || filename.equals("")) && method.getResponseHeader("Content-Disposition") != null)
                    filename = new ContentDisposition(method.getResponseHeader("Content-Disposition").getValue()).getParameter("filename");
                if (filename == null || filename.equals(""))
                    filename = "unknown";

                List<Upload> uploads = null;
                
                if (targetResponseBody != null) {
                    try {
                        Upload up = FileUploadServlet.saveUpload(targetResponseBody, filename, contentType, authToken.getAccountId());
                        uploads = Arrays.asList(up);
                    } catch (ServiceException e) {
                        if (e.getCode().equals(MailServiceException.UPLOAD_REJECTED))
                            status = HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE;
                        else
                            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                    }
                }

                resp.setStatus(status);
                FileUploadServlet.sendResponse(resp, status, req.getParameter(FORMAT_PARAM), null, uploads, null);
            } else {
                resp.setStatus(status);
                resp.setContentType(contentType);
                for (Header h : method.getResponseHeaders())
                    if (canProxyHeader(h.getName()))
                        resp.addHeader(h.getName(), h.getValue());
                if (targetResponseBody != null)
                	ByteUtil.copy(targetResponseBody, true, resp.getOutputStream(), true);
            }
        } finally {
            if (method != null)
                method.releaseConnection();
        }
    }
}
