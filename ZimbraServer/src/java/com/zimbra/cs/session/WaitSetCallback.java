/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package com.zimbra.cs.session;

import java.util.List;

/**
 * User-supplied callback which is set by doWait() and which is called when one 
 * or more of the waiting sessions has new data.
 */
public interface WaitSetCallback {
    void dataReady(IWaitSet ws, String seqNo, boolean cancelled, List<WaitSetError> errors, String[] signalledAccounts);
}