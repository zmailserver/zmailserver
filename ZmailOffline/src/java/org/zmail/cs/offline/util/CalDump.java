/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.offline.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.cs.mailbox.MailServiceException;

public class CalDump {
	
	private static final String remoteUri = "http://localhost:7070";
	private static final String localUri = "http://localhost:7633";
	
	private static final String user = "user1@jjmac.local";
	private static final String pass = "test123";

	private static void printXml(Element e) {
		System.out.println(e.prettyPrint());
	}
	
	private static void saveXml(Element e, int id, String suffix) throws Exception {
		FileWriter fw = new FileWriter("" + id + suffix + ".xml");
		fw.write(e.prettyPrint());
		fw.close();
	}
	
	private static void saveMime(byte[] content, int id, String suffix) throws Exception {
		FileOutputStream fos = new FileOutputStream("" +id + suffix + ".msg");
		fos.write(content);
		fos.close();
	}
	
    private static String getAuthToken(String uri) throws ServiceException {
        Element request = new Element.XMLElement(AccountConstants.AUTH_REQUEST);
        request.addElement(AccountConstants.E_ACCOUNT).addAttribute(AccountConstants.A_BY, "name").setText(user);
        request.addElement(AccountConstants.E_PASSWORD).setText(pass);

        Element response = sendRequest(request, uri, false);
        return response.getAttribute(AccountConstants.E_AUTH_TOKEN);
    }
	
    private static Element sendRequest(Element request, String uri, boolean authRequired) throws ServiceException {
        SoapHttpTransport transport = new SoapHttpTransport(uri + "/service/soap");
        try {
            transport.setUserAgent("CalDump", "0");
            transport.setRetryCount(1);
            transport.setTimeout(6000);
            if (authRequired) {
            	transport.setAuthToken(getAuthToken(uri));
            }
            transport.setRequestProtocol(SoapProtocol.Soap12);

            printXml(request);
            Element response = transport.invokeWithoutSession(request.detach());
            printXml(response);
            
            return response;
        } catch (IOException e) {
            throw ServiceException.PROXY_ERROR(e, uri);
        } finally {
            transport.shutdown();
        }
    }
	
    private static byte[] getRemoteResourceInternal(String authToken, String hostname, String url) throws ServiceException {
    	
    	System.out.println(hostname + url);
    	
        // create an HTTP client with the same cookies
        HttpState state = new HttpState();
        state.addCookie(new org.apache.commons.httpclient.Cookie(hostname, "ZM_AUTH_TOKEN", authToken, "/", null, false));
        HttpClient client = new HttpClient();
        client.setState(state);
        GetMethod get = new GetMethod(url);
        try {
            int statusCode = HttpClientUtil.executeMethod(client, get);
            if (statusCode == HttpStatus.SC_NOT_FOUND)
                throw MailServiceException.NO_SUCH_ITEM(-1);
            else if (statusCode != HttpStatus.SC_OK)
                throw ServiceException.RESOURCE_UNREACHABLE(get.getStatusText(), null);

            Header[] headers = get.getResponseHeaders();
            return get.getResponseBody();
        } catch (HttpException e) {
            throw ServiceException.RESOURCE_UNREACHABLE("HttpException while fetching " + url, e);
        } catch (IOException e) {
            throw ServiceException.RESOURCE_UNREACHABLE("IOException while fetching " + url, e);
        }
    }

	
	public static void main(String[] args) throws Exception {
		
		int id = Integer.parseInt(args[0]);
		
        Element request = new Element.XMLElement(MailConstants.GET_APPOINTMENT_REQUEST);
        request.addAttribute(MailConstants.A_ID, Integer.toString(id));
        request.addAttribute(MailConstants.A_CAL_INCLUDE_CONTENT, 1);
        request.addAttribute(MailConstants.A_SYNC, "1");
        Element response = sendRequest(request, remoteUri, true);
        saveXml(response, id, "dog");
        response = sendRequest(request, localUri, true);
        saveXml(response, id, "loc");
        
        final String contentUrlPrefix = "/service/content/get?id=";
        
    	byte[] mimeContent = getRemoteResourceInternal(getAuthToken(remoteUri), new URI(remoteUri).getHost(), remoteUri + contentUrlPrefix + id);
    	saveMime(mimeContent, id, "dog");
    	mimeContent = getRemoteResourceInternal(getAuthToken(localUri), new URI(localUri).getHost(), localUri + contentUrlPrefix + id);
    	saveMime(mimeContent, id, "loc");
	}

}
