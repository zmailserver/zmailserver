/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.common.soap.SoapHttpTransport;
import org.zmail.common.util.L10nUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.CacheExtension;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.CacheEntry;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.PermissionCache;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.gal.GalGroup;
import org.zmail.cs.httpclient.URLUtil;
import org.zmail.cs.util.SkinUtil;
import org.zmail.soap.SoapServlet;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.type.CacheEntryType;

public class FlushCache extends AdminDocumentHandler {

    public static final String FLUSH_CACHE = "flushCache";

    /**
     * must be careful and only allow deletes domain admin has access to
     */
    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        Server localServer = Provisioning.getInstance().getLocalServer();
        checkRight(zsc, context, localServer, Admin.R_flushCache);

        Element eCache = request.getElement(AdminConstants.E_CACHE);
        String typeStr = eCache.getAttribute(AdminConstants.A_TYPE);
        boolean allServers = eCache.getAttributeBool(AdminConstants.A_ALLSERVERS, false);

        String[] types = typeStr.split(",");

        for (String type : types) {
            CacheEntryType cacheType = null;

            try {
                cacheType = CacheEntryType.fromString(type);
                doFlush(context, cacheType, eCache);
            } catch (ServiceException e) {
                
                if (cacheType == null) {
                    // see if it is a registered extension
                    CacheExtension ce = CacheExtension.getHandler(type);
                    if (ce != null)
                        ce.flushCache();
                    else
                        throw e;
                } else {
                    throw e;
                }
            }
        }

        if (allServers)
            flushCacheOnAllServers(zsc, request);

        Element response = zsc.createElement(AdminConstants.FLUSH_CACHE_RESPONSE);
        return response;
    }

    private void doFlush(Map<String, Object> context, CacheEntryType cacheType, Element eCache) throws ServiceException {

		String mailURL = Provisioning.getInstance().getLocalServer().getMailURL();
		switch (cacheType) {
			case acl:
				PermissionCache.invalidateCache();
				break;
			case all:
				flushLdapCache(cacheType, eCache);
				Provisioning.getInstance().refreshValidators(); // refresh other bits of cached license data
				break;
			case galgroup:
				GalGroup.flushCache(getCacheEntries(eCache));
				break;
			case uistrings:
				FlushCache.sendFlushRequest(context, mailURL, "/res/AjxMsg.js");
				FlushCache.sendFlushRequest(context, "/zmailAdmin", "/res/AjxMsg.js");
				break;
			case skin:
				SkinUtil.flushSkinCache();
				FlushCache.sendFlushRequest(context, mailURL, "/js/skin.js");
				break;
			case locale:
				L10nUtil.flushLocaleCache();
				break;
			case license:
				flushLdapCache(CacheEntryType.config, eCache); // refresh global config for parsed license
				Provisioning.getInstance().refreshValidators(); // refresh other bits of cached license data
				break;
			case zimlet:
				FlushCache.sendFlushRequest(context, "/service", "/zimlet/res/all.js");
				// fall through to also flush ldap entries
			default:
				flushLdapCache(cacheType, eCache);
		}
	}

    private CacheEntry[] getCacheEntries(Element eCache) throws ServiceException {
        List<Element> eEntries = eCache.listElements(AdminConstants.E_ENTRY);
        CacheEntry[] entries = null;
        if (eEntries.size() > 0) {
            entries = new CacheEntry[eEntries.size()];
            int i = 0;
            for (Element eEntry : eEntries) {
                entries[i++] = new CacheEntry(Key.CacheEntryBy.valueOf(eEntry.getAttribute(AdminConstants.A_BY)),
                        eEntry.getText());
            }
        }

        return entries;
    }

    private void flushLdapCache(CacheEntryType cacheType, Element eCache) throws ServiceException {
        CacheEntry[] entries = getCacheEntries(eCache);
        Provisioning.getInstance().flushCache(cacheType, entries);
    }

    static void sendFlushRequest(Map<String,Object> context,
            String appContext, String resourceUri) {
        ServletContext containerContext = (ServletContext)context.get(SoapServlet.SERVLET_CONTEXT);
        if (containerContext == null) {
            if (ZmailLog.misc.isDebugEnabled()) {
                ZmailLog.misc.debug("flushCache: no container context");
            }
            return;
        }
        ServletContext webappContext = containerContext.getContext(appContext);
        RequestDispatcher dispatcher = webappContext.getRequestDispatcher(resourceUri);
        if (dispatcher == null) {
            if (ZmailLog.misc.isDebugEnabled()) {
                ZmailLog.misc.debug("flushCache: no dispatcher for "+resourceUri);
            }
            return;
        }

        try {
            if (ZmailLog.misc.isDebugEnabled()) {
                ZmailLog.misc.debug("flushCache: sending flush request");
            }
            ServletRequest request = (ServletRequest)context.get(SoapServlet.SERVLET_REQUEST);
            request.setAttribute(FLUSH_CACHE, Boolean.TRUE);
            ServletResponse response = (ServletResponse)context.get(SoapServlet.SERVLET_RESPONSE);
            dispatcher.include(request, response);
        }
        catch (Throwable t) {
            // ignore error
            if (ZmailLog.misc.isDebugEnabled()) {
                ZmailLog.misc.debug("flushCache: "+t.getMessage());
            }
        }
    }

    public static void flushChacheOnServer(Server server,ZmailSoapContext zsc,String cacheType) throws ServiceException, IOException {
        String adminUrl = URLUtil.getAdminURL(server, AdminConstants.ADMIN_SERVICE_URI);
        SoapHttpTransport mTransport = new SoapHttpTransport(adminUrl);
        mTransport.setAuthToken(zsc.getRawAuthToken());
        XMLElement req = new XMLElement(AdminConstants.FLUSH_CACHE_REQUEST);
        req.addElement(AdminConstants.E_CACHE).addAttribute(AdminConstants.A_TYPE, cacheType);
        mTransport.invoke(req);
    }

    private void flushCacheOnAllServers(ZmailSoapContext zsc, Element origReq) throws ServiceException {

        Provisioning prov = Provisioning.getInstance();
        String localServerId = prov.getLocalServer().getId();

        for (Server server : prov.getAllServers(Provisioning.SERVICE_MAILBOX)) {

            if (localServerId.equals(server.getId()))
                continue;

            ZmailLog.misc.debug("Flushing cache on server: " + server.getName());

            Element req = origReq.clone();
            Element eCache = req.getElement(AdminConstants.E_CACHE);
            eCache.addAttribute(AdminConstants.A_ALLSERVERS, false);

            String adminUrl = URLUtil.getAdminURL(server, AdminConstants.ADMIN_SERVICE_URI);
            SoapHttpTransport mTransport = new SoapHttpTransport(adminUrl);
            mTransport.setAuthToken(zsc.getRawAuthToken());

            try {
                mTransport.invoke(req);
            } catch (ServiceException e) {
                // log and continue
                ZmailLog.misc.warn("Encountered exception while FlushCache on server: " + server.getName() +
                        ", skip and continue with the next server", e);
            } catch (IOException e) {
                // log and continue
                ZmailLog.misc.warn("Encountered exception while FlushCache on server: " + server.getName() +
                        ", skip and continue with the next server", e);
            }
        }
    }


    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_flushCache);
    }
}
