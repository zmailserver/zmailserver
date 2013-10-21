/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.util;

import java.util.List;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;

/**
 * Zmail Servers enable/disable settings overridable by LC.
 */

public class ZmailApplication {

    private static ZmailApplication sServices;

    public static ZmailApplication getInstance() {
        if (sServices == null) {
            String className = LC.zmail_class_application.value();
            if (className != null && !className.equals("")) {
                try {
                    sServices = (ZmailApplication)Class.forName(className)
                        .newInstance();
                } catch (Exception e) {
                    ZmailLog.misc.error(
                        "could not instantiate ZmailServices interface of class '"
                            + className + "'; defaulting to ZmailServices", e);
                }
            }
            if (sServices == null)
                sServices = new ZmailApplication();
        }
        return sServices;
    }

    public String getId() {
        return "zmail";
    }

    public String getClientId() {
        return "01234567-89AB-CDEF--FEDC-BA9876543210";
    }

    public boolean supports(String className) {
        return true;
    }

    public boolean supports(Class cls) {
        return supports(cls.getName());
    }

    public void startup() {}

    public void initialize(boolean forMailboxd) {}

    public void initializeZmailDb(boolean forMailboxd) throws ServiceException {}

    private boolean isShutdown;

    public void shutdown() {
        isShutdown = true;
    }

    public boolean isShutdown() {
        return isShutdown;
    }
    
    public void addExtensionName(String name) {
        assert false;
    }
    
    public List<String> getExtensionNames() {
        assert false;
        return null;
    }
}
