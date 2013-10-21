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
package org.zmail.qa.unittest;

import org.junit.*;
import static org.junit.Assert.*;

import org.zmail.cs.account.AttributeManager;


public class TestBuildInfo  {

    @Test
    public void testInVersion() throws Exception {
        AttributeManager am = AttributeManager.getInstance();

        assertTrue(am.inVersion("zmailId", "0"));
        assertTrue(am.inVersion("zmailId", "5.0.10"));

        assertFalse(am.inVersion("zmailZimletDomainAvailableZimlets", "5.0.9"));
        assertTrue(am.inVersion("zmailZimletDomainAvailableZimlets", "5.0.10"));
        assertTrue(am.inVersion("zmailZimletDomainAvailableZimlets", "5.0.11"));
        assertTrue(am.inVersion("zmailZimletDomainAvailableZimlets", "5.5"));
        assertTrue(am.inVersion("zmailZimletDomainAvailableZimlets", "6"));
    }

}
