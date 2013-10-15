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
package org.zmail.cs.service.offline;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.offline.OfflineProvisioning;
import org.zmail.cs.service.mail.SendInviteReply;

/**
 * @author vmahajan
 */
public class OfflineSendInviteReply extends SendInviteReply {

    @Override
    protected boolean deleteInviteOnReply(Account acct) throws ServiceException {
        return OfflineProvisioning.getOfflineInstance().getLocalAccount().isPrefDeleteInviteOnReply();
    }
}
