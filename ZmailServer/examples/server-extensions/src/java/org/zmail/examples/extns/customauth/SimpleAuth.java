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
package com.zimbra.examples.extns.customauth;

import com.zimbra.common.soap.Element;
import com.zimbra.common.util.SystemUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.auth.ZimbraCustomAuth;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple authentication mechanism that reads usernames/passwords from /opt/zimbra/conf/users.xml file.
 *
 * @author vmahajan
 */
public class SimpleAuth extends ZimbraCustomAuth {

    private static Map<String, String> userPassMap = new HashMap<String, String>();

    static {
        try {
            Element usersElt = Element.parseXML(new FileInputStream("/opt/zimbra/conf/users.xml"));
            List<Element> userEltList = usersElt.getPathElementList(new String[]{"user"});
            for (Element userElt : userEltList) {
                userPassMap.put(userElt.getAttribute("name"), userElt.getAttribute("password"));
            }
        } catch (Exception e) {
            ZimbraLog.extensions.error(SystemUtil.getStackTrace(e));
        }
    }

    /**
     * Authenticates account.
     *
     * @param acct account
     * @param password password
     * @param context context map
     * @param args arg list
     * @throws Exception
     */
    public void authenticate(Account acct, String password, Map<String, Object> context, List<String> args)
            throws Exception {
        String username = acct.getName();
        if (userPassMap.containsKey(username)) {
            if (!userPassMap.get(username).equals(password))
                throw new Exception("Invalid password");
        } else {
            throw new Exception("Invalid user name \"" + username + "\"");
        }

    }
}
