/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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

import java.util.Map;

import org.dom4j.DocumentException;

import org.zmail.common.localconfig.ConfigException;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.soap.ZmailSoapContext;
import org.zmail.soap.admin.message.ReloadLocalConfigResponse;

/**
 * Reload the local config file on the fly.
 * <p>
 * After successfully reloading a new local config file, subsequent
 * {@link LC#get(String)} calls should receive new value. However, if you store/
 * cache those values (e.g. keep them as class member or instance member), new
 * values are of course not reflected.
 *
 * @author ysasaki
 */
public final class ReloadLocalConfig extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        try {
            LC.reload();
        } catch (DocumentException e) {
            ZmailLog.misc.error("Failed to reload LocalConfig", e);
            throw AdminServiceException.FAILURE("Failed to reload LocalConfig", e);
        } catch (ConfigException e) {
            ZmailLog.misc.error("Failed to reload LocalConfig", e);
            throw AdminServiceException.FAILURE("Failed to reload LocalConfig", e);
        }
        ZmailLog.misc.info("LocalConfig reloaded");

        ZmailSoapContext zsc = getZmailSoapContext(context);
        return zsc.jaxbToElement(new ReloadLocalConfigResponse());
    }

}
