/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
 * Created on Apr 11, 2005
 *
 */
package com.zimbra.cs.filter.jsieve;

import org.apache.jsieve.Arguments;
import org.apache.jsieve.Block;
import org.apache.jsieve.SieveContext;
import org.apache.jsieve.commands.AbstractConditionalCommand;
import org.apache.jsieve.mail.MailAdapter;

public class DisabledIf extends AbstractConditionalCommand {

    @Override
    protected Object executeBasic(MailAdapter mail, Arguments arguments,
                                  Block block, SieveContext context) {
        return null;
    }
    
    @Override
    protected void validateArguments(Arguments arguments, SieveContext context) {
        // No validation
    }
}
