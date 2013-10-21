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
package org.zmail.cs.account.accesscontrol;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Entry;

public abstract class CheckRightFallback {
    protected Right mRight;

    void setRight(Right right) {
        mRight = right;
    }
    
    public Boolean checkRight(Account authedAcct, Entry target, boolean asAdmin) {
        try {
            return doCheckRight(authedAcct, target, asAdmin);
        } catch (ServiceException e) {
            ZmailLog.acl.warn("caught exception in checkRight fallback" +
                    ", checkRight fallback for right [" + mRight.getName() +"] skipped", e);
            return null;
        }
    }
    
    protected abstract Boolean doCheckRight(Account grantee, Entry target, boolean asAdmin) throws ServiceException;
    
}
