/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2012 VMware, Inc.
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
 * Created on Jun 1, 2004
 *
 */
package org.zmail.ldaputils;

import org.zmail.common.service.ServiceException;


/**
 * @author schemers
 * 
 */
@SuppressWarnings("serial")
public class ZmailLDAPUtilsServiceException extends ServiceException {

    public static final String DN_EXISTS  = "zimblraldaputils.DN_EXISTS";
    
    private ZmailLDAPUtilsServiceException(String message, String code, boolean isReceiversFault, Throwable cause) {
        super(message, code, isReceiversFault, cause);
    }
    
    public static ZmailLDAPUtilsServiceException DN_EXISTS(String dn) {
    	return new ZmailLDAPUtilsServiceException("dn already exists: "+dn, DN_EXISTS, SENDERS_FAULT, null);
    }
}
