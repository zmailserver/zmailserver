/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.qa.unittest;

import org.junit.*;
import static org.junit.Assert.*;

import com.zimbra.cs.account.AttributeManager;


public class TestBuildInfo  {

    @Test
    public void testInVersion() throws Exception {
        AttributeManager am = AttributeManager.getInstance();

        assertTrue(am.inVersion("zimbraId", "0"));
        assertTrue(am.inVersion("zimbraId", "5.0.10"));

        assertFalse(am.inVersion("zimbraZimletDomainAvailableZimlets", "5.0.9"));
        assertTrue(am.inVersion("zimbraZimletDomainAvailableZimlets", "5.0.10"));
        assertTrue(am.inVersion("zimbraZimletDomainAvailableZimlets", "5.0.11"));
        assertTrue(am.inVersion("zimbraZimletDomainAvailableZimlets", "5.5"));
        assertTrue(am.inVersion("zimbraZimletDomainAvailableZimlets", "6"));
    }

}
