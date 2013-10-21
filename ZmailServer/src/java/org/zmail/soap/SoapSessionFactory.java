/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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
package org.zmail.soap;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.extension.ExtensionUtil;
import org.zmail.cs.session.RemoteSoapSession;
import org.zmail.cs.session.SoapSession;

public class SoapSessionFactory {

    private static SoapSessionFactory sSessionFactory = null;

    public synchronized static SoapSessionFactory getInstance() {
        if (sSessionFactory == null) {
            String className = LC.zmail_class_soapsessionfactory.value();
            if (className != null && !className.equals("")) {
                try {
                    try {
                        sSessionFactory = (SoapSessionFactory) Class.forName(className).newInstance();
                    } catch (ClassNotFoundException cnfe) {
                        // ignore and look in extensions
                        sSessionFactory = (SoapSessionFactory) ExtensionUtil.findClass(className).newInstance();
                    }
                } catch (Exception e) {
                    ZmailLog.account.error("could not instantiate SoapSessionFactory class '" + className + "'; defaulting to SoapSessionFactory", e);
                }
            }
            if (sSessionFactory == null) {
                sSessionFactory = new SoapSessionFactory();
            }
        }
        return sSessionFactory;
    }

    public SoapSession getSoapSession(ZmailSoapContext zsc) throws ServiceException {
        if (zsc.isAuthUserOnLocalhost()) {
            return new SoapSession(zsc);
        } else {
            return new RemoteSoapSession(zsc);
        }
    }
}
