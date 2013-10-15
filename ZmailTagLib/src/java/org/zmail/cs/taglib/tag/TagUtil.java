/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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

package org.zmail.cs.taglib.tag;

import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.soap.SoapTransport;
import org.zmail.common.soap.SoapTransport.DebugListener;

public class TagUtil {

    public static class JsonDebugListener implements DebugListener {
        Element env;
        public void sendSoapMessage(Element envelope) {}
        public void receiveSoapMessage(Element envelope) {env = envelope; }
        public Element getEnvelope(){ return env; }
    }
    
    public static SoapTransport newJsonTransport(String url, String remoteAddr, ZAuthToken authToken, DebugListener debug) {
        SoapTransport transport = new SoapHttpTransport(url);
        transport.setClientIp(remoteAddr);
        transport.setAuthToken(authToken);
        transport.setRequestProtocol(SoapProtocol.SoapJS);
        transport.setResponseProtocol(SoapProtocol.SoapJS);
        transport.setDebugListener(debug);
        return transport;
    }
}
