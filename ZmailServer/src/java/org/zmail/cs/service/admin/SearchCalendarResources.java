/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.common.account.Key.DomainBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.EntrySearchFilter;
import org.zmail.cs.account.NamedEntry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.SearchDirectoryOptions;
import org.zmail.cs.account.SearchDirectoryOptions.SortOpt;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.account.ldap.LdapEntrySearchFilter;
import org.zmail.cs.gal.GalExtraSearchFilter;
import org.zmail.cs.ldap.ZLdapFilterFactory.FilterId;
import org.zmail.cs.session.AdminSession;
import org.zmail.cs.session.Session;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author jhahm
 */
public class SearchCalendarResources extends AdminDocumentHandler {

    public static final String BY_NAME = "name";
    public static final String BY_ID = "id";

    /**
     * must be careful and only allow access if domain admin
     */
    public boolean domainAuthSufficient(Map context) {
        return true;
    }

    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Provisioning prov = Provisioning.getInstance();

        int limit = (int) request.getAttributeLong(AdminConstants.A_LIMIT,
                                                   Integer.MAX_VALUE);
        if (limit == 0) limit = Integer.MAX_VALUE;
        int offset = (int) request.getAttributeLong(AdminConstants.A_OFFSET, 0);
        String domain = request.getAttribute(AdminConstants.A_DOMAIN, null);
        boolean applyCos = request.getAttributeBool(AdminConstants.A_APPLY_COS, true);
        String sortBy = request.getAttribute(AdminConstants.A_SORT_BY, null);
        boolean sortAscending = request.getAttributeBool(AdminConstants.A_SORT_ASCENDING, true);
        String attrsStr = request.getAttribute(AdminConstants.A_ATTRS, null);
        String[] attrs = attrsStr == null ? null : attrsStr.split(",");

        EntrySearchFilter filter = GalExtraSearchFilter.parseSearchFilter(request);

        // if we are a domain admin only, restrict to domain
        //
        // Note: isDomainAdminOnly *always* returns false for pure ACL based AccessManager 
        if (isDomainAdminOnly(zsc)) {
            if (domain == null) {
                domain = getAuthTokenAccountDomain(zsc).getName();
            } else {
                checkDomainRight(zsc, domain, AdminRight.PR_ALWAYS_ALLOW);
            }
        }

        Domain d = null;
        if (domain != null) {
            d = prov.get(Key.DomainBy.name, domain);
            if (d == null)
                throw AccountServiceException.NO_SUCH_DOMAIN(domain);
        }

        AdminAccessControl aac = AdminAccessControl.getAdminAccessControl(zsc);
        AdminAccessControl.SearchDirectoryRightChecker rightChecker = 
            new AdminAccessControl.SearchDirectoryRightChecker(aac, prov, null);
        
        // filter is not RFC 2254 escaped 
        // query is RFC 2254 escaped
        String query = LdapEntrySearchFilter.toLdapCalendarResourcesFilter(filter);
        
        SearchDirectoryOptions options = new SearchDirectoryOptions();
        options.setDomain(d);
        options.setTypes(SearchDirectoryOptions.ObjectType.resources);
        options.setFilterString(FilterId.ADMIN_SEARCH, query);
        options.setReturnAttrs(attrs);
        options.setSortOpt(sortAscending ? SortOpt.SORT_ASCENDING : SortOpt.SORT_DESCENDING);
        options.setSortAttr(sortBy);
        options.setConvertIDNToAscii(true);
        
        List resources;
        AdminSession session = (AdminSession) getSession(zsc, Session.Type.ADMIN);
        if (session != null) {
            resources = session.searchDirectory(options, offset, rightChecker);
        } else {
            resources = prov.searchDirectory(options);
            resources = rightChecker.getAllowed(resources);
        }

        Element response = zsc.createElement(AdminConstants.SEARCH_CALENDAR_RESOURCES_RESPONSE);
        int i, limitMax = offset+limit;
        for (i=offset; i < limitMax && i < resources.size(); i++) {
            NamedEntry entry = (NamedEntry) resources.get(i);
            ToXML.encodeCalendarResource(response, (CalendarResource)entry, applyCos, null, aac.getAttrRightChecker((CalendarResource)entry));
        }

        response.addAttribute(AdminConstants.A_MORE, i < resources.size());
        response.addAttribute(AdminConstants.A_SEARCH_TOTAL, resources.size());
        return response;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getCalendarResource);
        relatedRights.add(Admin.R_listCalendarResource);
        
        notes.add(AdminRightCheckPoint.Notes.LIST_ENTRY);
    }
}
