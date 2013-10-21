/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.soap;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.ProtocolException;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.util.BufferStream;
import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.RemoteIP;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.util.ZmailServletOutputStream;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.servlet.ZmailServlet;
import org.zmail.cs.stats.ZmailPerf;
import org.zmail.cs.util.Zmail;

/**
 * The soap service servlet
 */
public class SoapServlet extends ZmailServlet {
    private static final long serialVersionUID = 38710345271877593L;

    private static final String PARAM_ENGINE_HANDLER = "engine.handler.";

    /** context name of auth token extracted from cookie */
    public static final String ZIMBRA_AUTH_TOKEN = "zmail.authToken";
    /** context name of servlet context */
    public static final String SERVLET_CONTEXT = "servlet.context";
    /** context name of servlet HTTP request */
    public static final String SERVLET_REQUEST = "servlet.request";
    /** context name of servlet HTTP response */
    public static final String SERVLET_RESPONSE = "servlet.response";
    /** If this is a request sent to the admin port */
    public static final String IS_ADMIN_REQUEST = "zmail.isadminreq";
    /** Flag for requests that want to force invalidation of client cookies */
    public static final String INVALIDATE_COOKIES = "zmail.invalidateCookies";

    // Used by sExtraServices
    private static class ArrayListFactory implements Function<String, List<DocumentService>> {
        @Override
        public List<DocumentService> apply(String from) {
            return new ArrayList<DocumentService>();
        }
    }

    /**
     * Keeps track of extra services added by extensions.
     */
    private static Map<String, List<DocumentService>> sExtraServices =
        new MapMaker().makeComputingMap(new ArrayListFactory());

    private static Log sLog = LogFactory.getLog(SoapServlet.class);
    private SoapEngine mEngine;

    @Override
    public void init() throws ServletException {
        LogFactory.init();

        String name = getServletName();
        ZmailLog.soap.info("Servlet " + name + " starting up");
        super.init();

        mEngine = new SoapEngine();

        int i = 0;
        String cname;
        while ((cname = getInitParameter(PARAM_ENGINE_HANDLER+i)) != null) {
            loadHandler(cname);
            i++;
        }

        // See if any extra services were perviously added by extensions
        synchronized (sExtraServices) {
            List<DocumentService> services = sExtraServices.get(getServletName());
            for (DocumentService service : services) {
                addService(service);
                i++;
            }
        }

        mEngine.getDocumentDispatcher().clearSoapWhiteList();

        if (i == 0)
            throw new ServletException("Must specify at least one handler "+PARAM_ENGINE_HANDLER+i);

        try {
            Zmail.startup();
        } catch (OutOfMemoryError e) {
            Zmail.halt("out of memory", e);
        } catch (Throwable t) {
            ZmailLog.soap.fatal("Unable to start servlet", t);
            throw new UnavailableException(t.getMessage());
        }
    }

    @Override public void destroy() {
        String name = getServletName();
        ZmailLog.soap.info("Servlet " + name + " shutting down");
        try {
            Zmail.shutdown();
        } catch (ServiceException e) {
            // Log as error and ignore.
            ZmailLog.soap.error("ServiceException while shutting down servlet " + name, e);
        } catch (RuntimeException e) {
            ZmailLog.soap.error("Unchecked Exception while shutting down servlet " + name, e);
            throw e;
        }
        // FIXME: we might want to add mEngine.destroy()
        // to allow the mEngine to cleanup?
        mEngine = null;

        super.destroy();
    }

    private void loadHandler(String cname) throws ServletException {
        Class<?> dispatcherClass = null;
        try {
            dispatcherClass = Class.forName(cname);
        } catch (ClassNotFoundException cnfe) {
            throw new ServletException("can't find handler initializer class " + cname, cnfe);
        } catch (OutOfMemoryError e) {
            Zmail.halt("out of memory", e);
        } catch (Throwable t) {
            throw new ServletException("can't find handler initializer class " + cname, t);
        }

        Object dispatcher;

        try {
            dispatcher = dispatcherClass.newInstance();
        } catch (InstantiationException ie) {
            throw new ServletException("can't instantiate class " + cname, ie);
        } catch (IllegalAccessException iae) {
            throw new ServletException("can't instantiate class " + cname, iae);
        }

        if (!(dispatcher instanceof DocumentService)) {
            throw new ServletException("class not an instanceof HandlerInitializer: " + cname);
        }

        DocumentService hi = (DocumentService) dispatcher;
        addService(hi);
    }

    /**
     * Adds a service to the instance of <code>SoapServlet</code> with the given
     * name.  If the servlet has not been loaded, stores the service for later
     * initialization.
     */
    public static void addService(String servletName, DocumentService service) {
        synchronized (sExtraServices) {
            ZmailServlet servlet = ZmailServlet.getServlet(servletName);
            if (servlet != null) {
                ((SoapServlet) servlet).addService(service);
            } else {
                sLog.debug("addService(%s, %s): servlet has not been initialized",
                        servletName, service.getClass().getSimpleName());
                List<DocumentService> services = sExtraServices.get(servletName);
                services.add(service);
            }
        }
    }

    private void addService(DocumentService service) {
        ZmailLog.soap.info("Adding service %s to %s", service.getClass().getSimpleName(), getServletName());
        service.registerHandlers(mEngine.getDocumentDispatcher());
    }

    @Override public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ZmailLog.clearContext();
        long startTime = ZmailPerf.STOPWATCH_SOAP.start();

        try {
            doWork(req, resp);
        } finally {
            ZmailLog.clearContext();
            ZmailPerf.STOPWATCH_SOAP.stop(startTime);
        }
    }

    private void doWork(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int len = req.getContentLength();
        byte[] buffer;
        boolean isResumed = true;

        // resuming from a Jetty Continuation does *not* reset the HttpRequest's input stream -
        // therefore we store the read buffer in the Continuation, and use the stored buffer
        // if we're resuming
        buffer = (byte[])req.getAttribute("org.zmail.request.buffer");
        if (buffer == null) {
            isResumed = false;

            // Look up max request size
            int maxSize = 0;
            try {
                maxSize = Provisioning.getInstance().getLocalServer().getIntAttr(Provisioning.A_zmailSoapRequestMaxSize, 0);
            } catch (ServiceException e) {
                ZmailLog.soap.warn("Unable to look up %s.  Not limiting the request size.", Provisioning.A_zmailSoapRequestMaxSize, e);
            }
            if (maxSize <= 0) {
                maxSize = Integer.MAX_VALUE;
            }

            // Read the request
            boolean success;
            if (len > maxSize) {
                success = false;
            } else {
                BufferStream bs = new BufferStream(len, maxSize, maxSize);
                int in = (int)bs.readFrom(req.getInputStream(), len >= 0 ? len :
                    Integer.MAX_VALUE);

                if (len > 0 && in < len)
                    throw new EOFException("SOAP content truncated " + in + "!=" + len);
                success = in <= maxSize;
                buffer = bs.toByteArray();
            }

            // Handle requests that exceed the size limit
            if (!success) {
                String sizeString = (len < 0 ? "" : " size " + len);
                String msg = String.format("Request%s exceeded limit of %d bytes set for %s.",
                    sizeString, maxSize, Provisioning.A_zmailSoapRequestMaxSize);
                ServiceException e = ServiceException.INVALID_REQUEST(msg, null);
                ZmailLog.soap.warn(null, e);
                Element fault = SoapProtocol.Soap12.soapFault(e);
                Element envelope = SoapProtocol.Soap12.soapEnvelope(fault);
                sendResponse(req, resp, envelope);
                return;
            }

            req.setAttribute("org.zmail.request.buffer", buffer);
        }

        HashMap<String, Object> context = new HashMap<String, Object>();
        context.put(SERVLET_CONTEXT, getServletContext());
        context.put(SERVLET_REQUEST, req);
        context.put(SERVLET_RESPONSE, resp);
        
        try {
            Boolean isAdminReq = isAdminRequest(req);
            context.put(IS_ADMIN_REQUEST, isAdminReq);
        } catch (ServiceException se) {
            ZmailLog.soap.warn("unable to determine isAdminReq", se);
        }

        // setup IPs in the context and add to logging context
        RemoteIP remoteIp = new RemoteIP(req, ZmailServlet.getTrustedIPs());
        context.put(SoapEngine.SOAP_REQUEST_IP, remoteIp.getClientIP());
        context.put(SoapEngine.ORIG_REQUEST_IP, remoteIp.getOrigIP());
        context.put(SoapEngine.REQUEST_IP, remoteIp.getRequestIP());
        remoteIp.addToLoggingContext();

        //checkAuthToken(req.getCookies(), context);

        Element envelope = null;
        try {
            envelope = mEngine.dispatch(req.getRequestURI(), buffer, context);
            if (context.containsKey(INVALIDATE_COOKIES)) {
                ZAuthToken.clearCookies(resp);
            }
        } catch (Throwable e) {
            if (e instanceof OutOfMemoryError) {
                Zmail.halt("handler exception", e);
            }

            if (ZmailLog.soap.isTraceEnabled() && !context.containsKey(SoapEngine.SOAP_REQUEST_LOGGED)) {
                ZmailLog.soap.trace(!isResumed ? "C:\n%s" : "C: (resumed)\n%s", new String(buffer, Charsets.UTF_8));
            }

            // don't interfere with Jetty Continuations -- pass the exception right up
            if (e.getClass().getName().equals("org.eclipse.jetty.continuation.ContinuationThrowable"))
                throw (Error) e;

            ZmailLog.soap.warn("handler exception", e);
            Element fault = SoapProtocol.Soap12.soapFault(ServiceException.FAILURE(e.toString(), e));
            envelope = SoapProtocol.Soap12.soapEnvelope(fault);
        }

        if (ZmailLog.soap.isTraceEnabled()) {
            ZmailLog.soap.trace("S:\n%s", envelope.prettyPrint());
        }
        sendResponse(req, resp, envelope);
    }

    private int soapResponseBufferSize() {
        String val = LC.soap_response_buffer_size.value();
        if (val == null || val.length() == 0)
            return -1; // will be using jetty default
        else
            return LC.soap_response_buffer_size.intValue();
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, Element envelope) throws IOException {
        SoapProtocol soapProto = SoapProtocol.determineProtocol(envelope);
        int statusCode = soapProto.hasFault(envelope) ?
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR : HttpServletResponse.SC_OK;

        boolean chunkingEnabled = LC.soap_response_chunked_transfer_encoding_enabled.booleanValue();

        if (chunkingEnabled) {
            // disable chunking if proto < HTTP 1.1
            String proto = req.getProtocol();
            try {
                HttpVersion httpVer = HttpVersion.parse(proto);
                chunkingEnabled = !httpVer.lessEquals(HttpVersion.HTTP_1_0);
            } catch (ProtocolException e) {
                ZmailLog.soap.warn("cannot parse http version in request: %s, http chunked transfer encoding disabled",
                        proto, e);
                chunkingEnabled = false;
            }
        }

        // use jetty default if the LC key is not set
        int responseBufferSize = soapResponseBufferSize();
        if (responseBufferSize != -1)
            resp.setBufferSize(responseBufferSize);

        resp.setContentType(soapProto.getContentType());
        resp.setStatus(statusCode);
        resp.setHeader("Cache-Control", "no-store, no-cache");

        if (chunkingEnabled) {
            // Let jetty chunk the response if applicable.
            ZmailServletOutputStream out = new ZmailServletOutputStream(resp.getOutputStream());
            envelope.output(out);
            out.flush();
        } else {
            // serialize the envelope to a byte array and send the response with Content-Length header.
            byte[] soapBytes = envelope.toUTF8();
            resp.setContentLength(soapBytes.length);
            resp.getOutputStream().write(soapBytes);
            resp.getOutputStream().flush();
        }
        envelope.destroy();
    }
}
