/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.service.offline;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.cs.offline.OfflineSyncManager;
import com.zimbra.cs.session.SoapSession;
import com.zimbra.soap.SoapContextExtension;
import com.zimbra.soap.ZimbraSoapContext;

public class OfflineContextExtension extends SoapContextExtension {

	public static final String ZDSYNC = "zdsync";
	
	@Override
	public void addExtensionHeader(Element context, ZimbraSoapContext zsc, SoapSession session) throws ServiceException {
        OfflineSyncManager.getInstance().encode(context);
	}
}
