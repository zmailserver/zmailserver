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
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Get the intersection of installed skins on the server and the list specified in the
 * <b>zimbraAvailableSkin</b> on an account (or its CoS).  If none is set in <b>zimbraAvailableSkin</b>, get the entire
 * list of installed skins.  The installed skin list is obtained by a directory scan of the designated location of
 * skins on a server.
 */
@XmlRootElement(name=AccountConstants.E_GET_AVAILABLE_SKINS_REQUEST)
public class GetAvailableSkinsRequest {
}
