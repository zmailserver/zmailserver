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
package com.zimbra.soap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.session.SoapSession;

public abstract class SoapContextExtension {
	
	
	private static List<SoapContextExtension> sExtensions = Collections.synchronizedList(new ArrayList<SoapContextExtension>());
	
	public static void register(String name, SoapContextExtension sce) {
		synchronized (sExtensions) {
			ZimbraLog.soap.info("Adding context extension: " + name);
			sExtensions.add(sce);
		}
	}
	
	public static void addExtensionHeaders(Element context, ZimbraSoapContext zsc, SoapSession session) throws ServiceException {
		SoapContextExtension[] exts = null;
		synchronized (sExtensions) {
			exts = new SoapContextExtension[sExtensions.size()];
			sExtensions.toArray(exts); //make a copy so that we keep lock on addExtensionHeader calls
		}
		for (SoapContextExtension sce : exts) {
			sce.addExtensionHeader(context, zsc, session);
		}
	}

	public abstract void addExtensionHeader(Element context, ZimbraSoapContext zsc,  SoapSession session) throws ServiceException;
}
