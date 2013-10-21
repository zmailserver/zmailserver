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
package org.zmail.cs.account.ldap.entry;

import java.util.List;

import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.ldap.LdapException;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.IAttributes.CheckBinary;
import org.zmail.cs.ldap.ZSearchResultEntry;

/**
 * 
 * @author pshao
 *
 */
public class LdapDataSource extends DataSource implements LdapEntry {

	private String mDn;

	public LdapDataSource(Account acct, String dn, ZAttributes attrs, Provisioning prov) 
	throws LdapException, ServiceException {
		super(acct, getObjectType(attrs),
		        attrs.getAttrString(Provisioning.A_zmailDataSourceName),
		        attrs.getAttrString(Provisioning.A_zmailDataSourceId),                
		        attrs.getAttrs(), 
		        prov);
		mDn = dn;
	}
	
	public String getDN() {
		return mDn;
	}

    public static String getObjectClass(DataSourceType type) {
        switch (type) {
            case pop3:
                return AttributeClass.OC_zmailPop3DataSource;
            case imap:
                return AttributeClass.OC_zmailImapDataSource;
            case rss:
                return AttributeClass.OC_zmailRssDataSource;
            case gal:
                return AttributeClass.OC_zmailGalDataSource;
            default: 
                return null;
        }
    }

    static DataSourceType getObjectType(ZAttributes attrs) throws ServiceException {
        try {
            String dsType = attrs.getAttrString(Provisioning.A_zmailDataSourceType);
            if (dsType != null)
                return DataSourceType.fromString(dsType);
        } catch (LdapException e) {
            ZmailLog.datasource.error("cannot get DataSource type", e);
        }
        
        List<String> attr = attrs.getMultiAttrStringAsList(Provisioning.A_objectClass, CheckBinary.NOCHECK);
        if (attr.contains(AttributeClass.OC_zmailPop3DataSource)) 
            return DataSourceType.pop3;
        else if (attr.contains(AttributeClass.OC_zmailImapDataSource))
            return DataSourceType.imap;
        else if (attr.contains(AttributeClass.OC_zmailRssDataSource))
            return DataSourceType.rss;
        else if (attr.contains(AttributeClass.OC_zmailGalDataSource))
            return DataSourceType.gal;
        else
            throw ServiceException.FAILURE("unable to determine data source type from object class", null);
    }
}
