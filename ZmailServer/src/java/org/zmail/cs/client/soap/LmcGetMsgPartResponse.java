/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

package com.zimbra.cs.client.soap;

import com.zimbra.cs.client.*;

public class LmcGetMsgPartResponse extends LmcSoapResponse {

    private LmcMessage mMsg;

    /**
     * Get the message that includes the MIME part that was requested.
     */
    public LmcMessage getMessage() { return mMsg; }

    public void setMessage(LmcMessage m) { mMsg = m; }
}
