/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

package org.zmail.clam;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;

public class ClamScannerConfig {

    private boolean mEnabled;
    
    private String mURL;
    
    public ClamScannerConfig() throws ServiceException {
        reload();
    }
    
    public void reload() throws ServiceException {
        Config globalConfig = Provisioning.getInstance().getConfig();
        mEnabled = globalConfig.getBooleanAttr(Provisioning.A_zmailAttachmentsScanEnabled, false);
        
        Server serverConfig = Provisioning.getInstance().getLocalServer();
        mURL = serverConfig.getAttr(Provisioning.A_zmailAttachmentsScanURL);
    }

    public boolean getEnabled() {
        return mEnabled;
    }
    
    public String getURL() {
        return mURL;
    }
}
