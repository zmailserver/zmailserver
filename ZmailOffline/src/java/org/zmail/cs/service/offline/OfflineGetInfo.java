/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.offline;

import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.offline.OfflineAccount;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.cs.service.account.GetInfo;
import org.zmail.cs.session.Session;
import org.zmail.cs.session.SoapSession;
import org.zmail.soap.ZmailSoapContext;

public class OfflineGetInfo extends GetInfo {
    @Override
    protected Element encodeChildAccount(Element parent, Account child,
        boolean isVisible) {
        String accountName = child.getAttr(Provisioning.A_zmailPrefLabel);
        if (child instanceof OfflineAccount && ((OfflineAccount)child).isDisabledDueToError()) {
            //bug 47450
            //eventually want the UI to be able to show a meaningful error
            //currently, the main mailbox UI doesn't load if one or more accounts have corrupt DB files
            isVisible=false; 
        }
        Element elem = super.encodeChildAccount(parent, child, isVisible);
        
        accountName = accountName != null ? accountName : child.getAttr(
            OfflineConstants.A_offlineAccountName);
        if (elem != null && accountName != null) {
            Element attrsElem = elem.addUniqueElement(AccountConstants.E_ATTRS);
            
            if (accountName != null)
                attrsElem.addKeyValuePair(Provisioning.A_zmailPrefLabel,
                    accountName, AccountConstants.E_ATTR, AccountConstants.A_NAME);
        }
        return elem;
    }
    
    @Override
    protected Session getSession(ZmailSoapContext zsc, Session.Type stype) {
        Session s = super.getSession(zsc, stype);
        if (!s.isDelegatedSession())
            ((SoapSession)s).setOfflineSoapSession();
        return s;
    }
}
