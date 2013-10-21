/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.account;

import java.util.HashMap;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Zimlet;
import org.zmail.cs.zimlet.ZimletPresence;
import org.zmail.cs.zimlet.ZimletPresence.Presence;
import org.zmail.cs.zimlet.ZimletUtil;
import org.zmail.soap.ZmailSoapContext;

public class ModifyZimletPrefs extends AccountDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");

        Map<String, Object> attrs = new HashMap<String, Object>();
        
        ZimletPresence availZimlets = ZimletUtil.getAvailableZimlets(account);
        
        String addEnabled = "+" + Provisioning.A_zmailPrefZimlets;
        String delEnabled = "-" + Provisioning.A_zmailPrefZimlets;
        String addDisabled = "+" + Provisioning.A_zmailPrefDisabledZimlets;
        String delDisabled = "-" + Provisioning.A_zmailPrefDisabledZimlets;

        for (Element eZimlet : request.listElements(AccountConstants.E_ZIMLET)) {
            String zimletName = eZimlet.getAttribute(AccountConstants.A_NAME);
            String presense = eZimlet.getAttribute(AccountConstants.A_ZIMLET_PRESENCE);
            Presence userPrefPresence = Presence.fromString(presense);
            
            // user cannot make a zimlet mandatory
            if (userPrefPresence == Presence.mandatory)
                throw ServiceException.INVALID_REQUEST("invalid zimlet presence: " + presense, null);
            
            Presence defaultPresence = availZimlets.getPresence(zimletName);
            
            /*
             * if zimlet is not available to the user or is mandatory,
             * we don't want it to appear in either enabled/disabled prefs,
             * regardless what the user wants.
             * 
             * if default presence is the same as what user wants. it does not 
             * need to appear in the user pref.
             */
            if (defaultPresence == null ||               // zimlet not available to the user
                defaultPresence == Presence.mandatory || // zimlet is mandatory
                defaultPresence == userPrefPresence) {   // default == what user wants
                StringUtil.addToMultiMap(attrs, delEnabled, zimletName);
                StringUtil.addToMultiMap(attrs, delDisabled, zimletName);
            } else {
                /*
                 * default != what user wants
                 */
                if (userPrefPresence == Presence.enabled) {
                    // user wants the zimlet enabled
                    StringUtil.addToMultiMap(attrs, addEnabled, zimletName);
                    StringUtil.addToMultiMap(attrs, delDisabled, zimletName);
                } else {
                    // user wants the zimlet disabled
                    StringUtil.addToMultiMap(attrs, delEnabled, zimletName);
                    StringUtil.addToMultiMap(attrs, addDisabled, zimletName);
                }    
            }
        }
        
        Provisioning prov = Provisioning.getInstance();
        prov.modifyAttrs(account, attrs);

        Element response = zsc.createElement(AccountConstants.MODIFY_ZIMLET_PREFS_RESPONSE);
        doResponse(prov, response, account);
        return response;
    }

    private void doResponse(Provisioning prov, Element response, Account acct) throws ServiceException {
        ZimletPresence userZimlets = ZimletUtil.getUserZimlets(acct);
        for (String zimletName : userZimlets.getZimletNames()) {
            Zimlet zimlet = prov.getZimlet(zimletName);
            if (zimlet != null && zimlet.isEnabled() && !zimlet.isExtension()) {
                Element eZimlet = response.addElement(AccountConstants.E_ZIMLET);
                eZimlet.addAttribute(AccountConstants.A_NAME, zimletName);
                eZimlet.addAttribute(AccountConstants.A_ZIMLET_PRESENCE, userZimlets.getPresence(zimletName).toString());
            }
        }
    }
}
