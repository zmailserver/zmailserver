/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.localconfig.LC;
import org.zmail.common.net.SocketFactories;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.util.DateUtil;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.soap.SoapProvisioning;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetDomainInfoTag extends ZmailSimpleTag {
    private static final String CONFIG_ZIMBRA_DOMAININFO_TTL = "zmail.domaininfo.ttl";

    static {
        SocketFactories.registerProtocols();
    }

    private String mVar;
    private Key.DomainBy mBy;
    private String mValue;

    private static final Map<String, CachedDomain> mCache = new HashMap<String, CachedDomain>();

    public void setVar(String var) { this.mVar = var; }
    public void setBy(String by) throws ServiceException { this.mBy = Key.DomainBy.fromString(by); }
    public void setValue(String value) { this.mValue = value; }

    private static final String DEFAULT_TTL_STR = "60m";
    private static final long DEFAULT_TTL = 60*60*1000;
    private static long sCacheTtl = -1;
    
    public void doTag() throws JspException, IOException {
        JspContext ctxt = getJspContext();
        if (sCacheTtl == -1) {
            String ttl = (String) Config.find((PageContext) ctxt, CONFIG_ZIMBRA_DOMAININFO_TTL);
            sCacheTtl = DateUtil.getTimeInterval(ttl != null ? ttl : DEFAULT_TTL_STR, DEFAULT_TTL);
        }

        ctxt.setAttribute(mVar, checkCache(),  PageContext.REQUEST_SCOPE);
    }

    private String getCacheKey() { return mBy +"/" + mValue; }

    private Domain checkCache() {
        CachedDomain cd = mCache.get(getCacheKey());
        if (cd != null) {
            if (cd.expireTime > System.currentTimeMillis())
                return cd.domain;
        }
        Domain d = getInfo();
        synchronized(mCache) {
            mCache.put(getCacheKey(), new CachedDomain(d));
        }
        return d;
    }

    private Domain getInfo() {
        SoapProvisioning sp = new SoapProvisioning();
        String mServer = LC.zmail_zmprov_default_soap_server.value();
        int mPort = LC.zmail_admin_service_port.intValue();
        sp.soapSetURI(LC.zmail_admin_service_scheme.value()+mServer+":"+mPort+ AdminConstants.ADMIN_SERVICE_URI);
        try {
            return sp.getDomainInfo(mBy, mValue);
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class CachedDomain {
        public Domain domain;
        public long expireTime;

        public CachedDomain(Domain d) { domain = d; expireTime = System.currentTimeMillis() + sCacheTtl; }
    }
}
