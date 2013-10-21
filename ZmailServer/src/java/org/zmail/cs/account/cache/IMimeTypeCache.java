/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.account.cache;

import java.util.List;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mime.MimeTypeInfo;

public interface IMimeTypeCache {
    public void flushCache(Provisioning prov) throws ServiceException;
    
    public List<MimeTypeInfo> getAllMimeTypes(Provisioning prov) throws ServiceException;
    
    public List<MimeTypeInfo> getMimeTypes(Provisioning prov, String mimeType) throws ServiceException;
}
