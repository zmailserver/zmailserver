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
package org.zmail.cs.service.admin;

import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.gal.GalSearchControl;
import org.zmail.cs.gal.GalSearchParams;
import org.zmail.cs.gal.GalSearchResultCallback;
import org.zmail.common.soap.Element;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.type.GalSearchType;

/**
 * @since May 26, 2004
 * @author schemers
 */
public class SearchGal extends AdminGalDocumentHandler {
    
    /**
     * must be careful and only return accounts a domain admin can see
     */
    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);

        String domainName = request.getAttribute(AdminConstants.A_DOMAIN);
        Provisioning prov = Provisioning.getInstance();
        Domain domain = prov.get(Key.DomainBy.name, domainName);
        if (domain == null)
            throw AccountServiceException.NO_SUCH_DOMAIN(domainName);

        checkDomainRight(zsc, domain, Admin.R_accessGAL);
        
        String name = request.getAttribute(AdminConstants.E_NAME, "");
        int limit = (int) request.getAttributeLong(AdminConstants.A_LIMIT, 0);
        String typeStr = request.getAttribute(AdminConstants.A_TYPE, GalSearchType.account.name());
        GalSearchType type = GalSearchType.fromString(typeStr);
        
        String galAcctId = request.getAttribute(AccountConstants.A_GAL_ACCOUNT_ID, null);
        
        String token = request.getAttribute(AdminConstants.A_TOKEN, null);

        GalSearchParams params = new GalSearchParams(domain, zsc);
        if (token != null)
            params.setToken(token);
        params.setType(type);
        params.setRequest(request);
        params.setQuery(name);
        params.setLimit(limit);
        params.setResponseName(AdminConstants.SEARCH_GAL_RESPONSE);
        if (galAcctId != null)
            params.setGalSyncAccount(Provisioning.getInstance().getAccountById(galAcctId));
        
        params.setResultCallback(new SearchGal.AdminGalCallback(params));
        GalSearchControl gal = new GalSearchControl(params);
        if (token != null)
            gal.sync();
        else
            gal.search();
        return params.getResultCallback().getResponse();
    }

    static class AdminGalCallback extends GalSearchResultCallback {
        private Element proxiedResponse;
        
        AdminGalCallback(GalSearchParams params) {
            super(params);
        }
        
        @Override
        public boolean passThruProxiedGalAcctResponse() {
            return true;
        }
        
        @Override
        public void handleProxiedResponse(Element resp) {
            proxiedResponse = resp;
            proxiedResponse.detach();
        }
        
        @Override
        public Element getResponse() {
            if (proxiedResponse != null)
                return proxiedResponse;
            else
                return super.getResponse();
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_accessGAL);
    }

}
