/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

/*
 * Created on May 26, 2004
 */
package org.zmail.cs.service.account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Strings;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.Element.KeyValuePair;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeInfo;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.Provisioning;
import org.zmail.soap.ZmailSoapContext;

/**
 * @author schemers
 */
public class ModifyPrefs extends AccountDocumentHandler {

    public static final String PREF_PREFIX = "zmailPref";

    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account account = getRequestedAccount(zsc);

        if (!canModifyOptions(zsc, account))
            throw ServiceException.PERM_DENIED("can not modify options");

        HashMap<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Set<String>> name2uniqueAttrValues = new HashMap<String, Set<String>>();
        for (KeyValuePair kvp : request.listKeyValuePairs(AccountConstants.E_PREF, AccountConstants.A_NAME)) {
            String name = kvp.getKey(), value = kvp.getValue();
            char ch = name.length() > 0 ? name.charAt(0) : 0;
            int offset = ch == '+' || ch == '-' ? 1 : 0;
            if (!name.startsWith(PREF_PREFIX, offset))
                throw ServiceException.INVALID_REQUEST("pref name must start with " + PREF_PREFIX, null);

            AttributeInfo attrInfo = AttributeManager.getInstance().getAttributeInfo(name.substring(offset));
            if (attrInfo == null) {
                throw ServiceException.INVALID_REQUEST("no such attribute: " + name, null);
            }
            if (attrInfo.isCaseInsensitive()) {
                String valueLowerCase = Strings.nullToEmpty(value).toLowerCase();
                if (name2uniqueAttrValues.get(name) == null) {
                    Set<String> set = new HashSet<String>();
                    set.add(valueLowerCase);
                    name2uniqueAttrValues.put(name, set);
                    StringUtil.addToMultiMap(prefs, name, value);
                } else {
                    Set<String> set = name2uniqueAttrValues.get(name);
                    if (set.add(valueLowerCase)) {
                        StringUtil.addToMultiMap(prefs, name, value);
                    }
                }
            } else {
                StringUtil.addToMultiMap(prefs, name, value);
            }
        }

        if (prefs.containsKey(Provisioning.A_zmailPrefMailForwardingAddress)) {
            if (!account.getBooleanAttr(Provisioning.A_zmailFeatureMailForwardingEnabled, false))
                throw ServiceException.PERM_DENIED("forwarding not enabled");
        }

        // call modifyAttrs and pass true to checkImmutable
        Provisioning.getInstance().modifyAttrs(account, prefs, true, zsc.getAuthToken());

        Element response = zsc.createElement(AccountConstants.MODIFY_PREFS_RESPONSE);
        return response;
    }
}
