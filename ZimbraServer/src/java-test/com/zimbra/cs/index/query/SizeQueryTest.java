/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.index.query;

import java.text.ParseException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zimbra.cs.account.MockProvisioning;
import com.zimbra.cs.account.Provisioning;

/**
 * Unit test for {@link SizeQuery}.
 *
 * @author ysasaki
 */
public final class SizeQueryTest {

    @BeforeClass
    public static void init() throws Exception {
        MockProvisioning prov = new MockProvisioning();
        prov.createAccount("zero@zimbra.com", "secret", new HashMap<String, Object>());
        Provisioning.setInstance(prov);
    }

    @Test
    public void parseSize() throws Exception {
        SizeQuery query = new SizeQuery(SizeQuery.Type.EQ, "1KB");
        Assert.assertEquals("Q(SIZE:=1024)", query.toString());

        query = new SizeQuery(SizeQuery.Type.EQ, ">1KB");
        Assert.assertEquals("Q(SIZE:>1024)", query.toString());

        query = new SizeQuery(SizeQuery.Type.EQ, "<1KB");
        Assert.assertEquals("Q(SIZE:<1024)", query.toString());

        query = new SizeQuery(SizeQuery.Type.EQ, ">=1KB");
        Assert.assertEquals("Q(SIZE:>1023)", query.toString());

        query = new SizeQuery(SizeQuery.Type.EQ, "<=1KB");
        Assert.assertEquals("Q(SIZE:<1025)", query.toString());

        query = new SizeQuery(SizeQuery.Type.EQ, "1 KB");
        Assert.assertEquals("Q(SIZE:=1024)", query.toString());

        try {
            query = new SizeQuery(SizeQuery.Type.EQ, "x KB");
            Assert.fail();
        } catch (ParseException expected) {
        }
    }

}
