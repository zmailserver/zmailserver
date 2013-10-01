/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
package com.zimbra.cs.dav.service.method;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.HttpUtil;
import com.zimbra.cs.dav.DavContext;
import com.zimbra.cs.dav.DavContext.RequestProp;
import com.zimbra.cs.dav.DavElements;
import com.zimbra.cs.dav.DavException;
import com.zimbra.cs.dav.resource.AddressObject;
import com.zimbra.cs.dav.resource.AddressbookCollection;
import com.zimbra.cs.dav.resource.DavResource;
import com.zimbra.cs.dav.resource.UrlNamespace;
import com.zimbra.cs.dav.service.DavResponse;

public class AddressbookMultiget extends Report {
    public void handle(DavContext ctxt) throws ServiceException, DavException {
        Element query = ctxt.getRequestMessage().getRootElement();
        if (!query.getQName().equals(DavElements.CardDav.E_ADDRESSBOOK_MULTIGET))
            throw new DavException("msg "+query.getName()+" is not addressbook-multiget", HttpServletResponse.SC_BAD_REQUEST, null);

        DavResponse resp = ctxt.getDavResponse();
        DavResource reqResource = ctxt.getRequestedResource();
        if (!(reqResource instanceof AddressbookCollection))
            throw new DavException("requested resource is not an addressbook collection", HttpServletResponse.SC_BAD_REQUEST, null);
        RequestProp reqProp = ctxt.getRequestProp();
        for (Object obj : query.elements(DavElements.E_HREF)) {
            if (obj instanceof Element) {
                String href = ((Element)obj).getText();
                URI uri = URI.create(href);
                if (uri.getPath().toLowerCase().endsWith(AddressObject.VCARD_EXTENSION)) {
                    // double encode the last fragment
                    String[] fragments = HttpUtil.getPathFragments(uri);
                    fragments[fragments.length - 1] = HttpUtil.urlEscapeIncludingSlash(fragments[fragments.length - 1]);
                    uri = HttpUtil.getUriFromFragments(fragments, uri.getQuery(), true, false);
                    href = uri.getPath();
                }
                DavResource rs = UrlNamespace.getResourceAtUrl(ctxt, href);
                if (rs != null)
                    resp.addResource(ctxt, rs, reqProp, false);
            }
        }
    }
}
