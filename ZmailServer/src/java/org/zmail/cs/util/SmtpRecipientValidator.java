/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.cs.util;

import java.util.Arrays;
import java.util.Collections;

import org.zmail.common.lmtp.SmtpToLmtp;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;

/**
 * Validates recipients and expands distribution lists for the dev
 * SMTP server.
 */
public class SmtpRecipientValidator
implements SmtpToLmtp.RecipientValidator {

    private static final Log log = LogFactory.getLog(SmtpRecipientValidator.class);

    @Override
    public Iterable<String> validate(String recipient) {
        try {
            Provisioning prov = Provisioning.getInstance();
            Account account = prov.get(AccountBy.name, recipient);
            if (account != null) {
                return Arrays.asList(account.getName());
            } else {
                Group group = prov.getGroup(Key.DistributionListBy.name, recipient);
                if (group != null) {
                    return Arrays.asList(prov.getGroupMembers(group));
                }
            }
        } catch (ServiceException e) {
            log.error("Unable to validate recipient %s", recipient, e);
        }
        return Collections.emptyList();
    }
}
