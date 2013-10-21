/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.dav.resource;

import java.util.Locale;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.L10nUtil;
import org.zmail.common.util.L10nUtil.MsgKey;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.dav.DavContext;
import org.zmail.cs.dav.DavElements;
import org.zmail.cs.dav.DavException;
import org.zmail.cs.dav.property.CalDavProperty;
import org.zmail.cs.dav.property.ResourceProperty;
import org.zmail.cs.mailbox.Mountpoint;

public class RemoteCalendarCollection extends RemoteCollection {

    public RemoteCalendarCollection(DavContext ctxt, Mountpoint mp) throws DavException, ServiceException {
        super(ctxt, mp);
		Account acct = mp.getAccount();

		addResourceType(DavElements.E_CALENDAR);

		Locale lc = acct.getLocale();
		String description = L10nUtil.getMessage(MsgKey.caldavCalendarDescription, lc, acct.getAttr(Provisioning.A_displayName), mp.getName());
		ResourceProperty desc = new ResourceProperty(DavElements.E_CALENDAR_DESCRIPTION);
		desc.setMessageLocale(lc);
		desc.setStringValue(description);
		desc.setVisible(false);
		addProperty(desc);
		addProperty(CalDavProperty.getSupportedCalendarComponentSet(mp.getDefaultView()));
		addProperty(CalDavProperty.getSupportedCalendarData());
		addProperty(CalDavProperty.getSupportedCollationSet());
		
        addProperty(getIcalColorProperty());
		setProperty(DavElements.E_ALTERNATE_URI_SET, null, true);
		setProperty(DavElements.E_GROUP_MEMBER_SET, null, true);
		setProperty(DavElements.E_GROUP_MEMBERSHIP, null, true);
    }
    
    public short getRights() {
    	return mRights;
    }
}
