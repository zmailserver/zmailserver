/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package org.zmail.cs.gal;

import org.zmail.common.localconfig.LC;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.extension.ExtensionUtil;
import org.zmail.cs.gal.GalGroup.GroupInfo;

public class GalGroupInfoProvider {

    private static GalGroupInfoProvider instance;
    
    public synchronized static GalGroupInfoProvider getInstance() {
        if (instance == null) {
            instance = makeInstance();
        }
        return instance;
    }

    private static GalGroupInfoProvider makeInstance() {
        GalGroupInfoProvider provider = null;
        String className = LC.zmail_class_galgroupinfoprovider.value();
        if (className != null && !className.equals("")) {
            try {
                try {
                    provider = (GalGroupInfoProvider) Class.forName(className).newInstance();
                } catch (ClassNotFoundException cnfe) {
                    // ignore and look in extensions
                    provider = (GalGroupInfoProvider) ExtensionUtil.findClass(className).newInstance();
                }
            } catch (Exception e) {
                ZmailLog.account.error("could not instantiate GalGroupInfoProvider interface of class '" + className + "'; defaulting to GalGroupInfoProvider", e);
            }
        }
        if (provider == null)
            provider = new GalGroupInfoProvider();
        return provider;
    }
    
    public GroupInfo getGroupInfo(String addr, boolean needCanExpand, Account requestedAcct, Account authedAcct) {
        return GalGroup.getGroupInfo(addr, true, requestedAcct, authedAcct);
    }

}
