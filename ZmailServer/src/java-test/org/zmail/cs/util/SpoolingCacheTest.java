/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package com.zimbra.cs.util;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zimbra.common.localconfig.LC;
import com.zimbra.cs.util.SpoolingCache;

public class SpoolingCacheTest {

    @BeforeClass
    public static void init() throws Exception {
        new File("build/test").mkdirs();
        LC.zimbra_tmp_directory.setDefault("build/test");
    }

    @AfterClass
    public static void destroy() throws Exception {
        new File("build/test").delete();
    }

    private static final String[] STRINGS = new String[] { "foo", "bar", "baz" };

    private void test(SpoolingCache<String> scache, boolean shouldSpool) throws IOException {
        for (String v : STRINGS) {
            scache.add(v);
        }

        Assert.assertEquals("spooled", shouldSpool, scache.isSpooled());
        Assert.assertEquals("entry count matches", STRINGS.length, scache.size());
        int i = 0;
        for (String v : scache) {
            Assert.assertEquals("entry matched: #" + i, STRINGS[i++], v);
        }
        Assert.assertEquals("correct number of items iterated", STRINGS.length, i);

    }

    @Test
    public void memory() throws Exception {
        SpoolingCache<String> scache = new SpoolingCache<String>(STRINGS.length + 3);
        test(scache, false);
        scache.cleanup();
    }

    @Test
    public void disk() throws Exception {
        SpoolingCache<String> scache = new SpoolingCache<String>(0);
        test(scache, true);
        scache.cleanup();
    }

    @Test
    public void both() throws Exception {
        SpoolingCache<String> scache = new SpoolingCache<String>(1);
        test(scache, true);
        scache.cleanup();
    }

}
