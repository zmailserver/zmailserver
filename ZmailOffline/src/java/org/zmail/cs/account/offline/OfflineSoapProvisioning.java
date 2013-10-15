/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.account.offline;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.cs.account.soap.SoapProvisioning;
import org.zmail.cs.offline.common.OfflineConstants;

public class OfflineSoapProvisioning extends SoapProvisioning {

    public void resetGal(String accountId) throws ServiceException {
        XMLElement req = new XMLElement(OfflineConstants.RESET_GAL_ACCOUNT_REQUEST);
        req.addElement(AdminConstants.E_ID).setText(accountId);
        invoke(req);
    }
}
