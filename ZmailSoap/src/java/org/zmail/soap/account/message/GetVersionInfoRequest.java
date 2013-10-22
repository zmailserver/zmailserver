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

package com.zimbra.soap.account.message;

import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AccountConstants;

/**
 * @zm-api-command-auth-required false - if version information shouldn't be exposed a fault will be thrown
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Get Version information
 * <br>
 * Note: This request will return a SOAP fault if the <b>zimbraSoapExposeVersion</b> server/globalconfig attribute is
 * set to FALSE.
 */
@XmlRootElement(name=AccountConstants.E_GET_VERSION_INFO_REQUEST)
public class GetVersionInfoRequest {
}
