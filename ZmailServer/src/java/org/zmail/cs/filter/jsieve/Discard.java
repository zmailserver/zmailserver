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
package org.zmail.cs.filter.jsieve;

import org.zmail.cs.filter.ZmailMailAdapter;
import org.apache.jsieve.Arguments;
import org.apache.jsieve.Block;
import org.apache.jsieve.SieveContext;
import org.apache.jsieve.exception.SieveException;
import org.apache.jsieve.mail.MailAdapter;

/**
 */
public class Discard extends org.apache.jsieve.commands.Discard {

    @Override
    protected Object executeBasic(MailAdapter mail, Arguments arguments, Block block, SieveContext context) throws SieveException {
        if (!(mail instanceof ZmailMailAdapter))
            return null;
        ((ZmailMailAdapter) mail).setDiscardActionPresent();
        return super.executeBasic(mail, arguments, block, context);
    }
}
