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
package org.zmail.qa.unittest.prov.ldap;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import static org.junit.Assert.*;

import org.zmail.common.localconfig.DebugConfig;
import org.zmail.common.localconfig.KnownKey;
import org.zmail.common.util.CliUtil;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.accesscontrol.RightManager;
import org.zmail.cs.ldap.unboundid.InMemoryLdapServer;
import org.zmail.qa.unittest.prov.ProvTest;

public class LdapTest extends ProvTest {
    private static final String LDAP_TEST_BASE_DOMAIN = "ldaptest";

    // variable guarding initTest() enter only once per JVM
    // if test is triggered from ant test-ldap(-inmem), number of JVM's
    // to fork is controlled by forkmode attr in the <junit> ant element
    private static boolean perJVMInited = false;

    // - handy to set it to "true"/"false" when invoking a single test from inside Eclipse
    // - make sure it is always set to null in p4.
    private static String useInMemoryLdapServerOverride = "true"; // null;  // "true";

    // ensure assertion is enabled
    static {
        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!

        if (!assertsEnabled) {
            throw new RuntimeException("Asserts must be enabled!!!");
        }
    }

    @BeforeClass  // invoked once per class loaded
    public static void beforeClass() throws Exception {
        initPerJVM();
    }

    static String baseDomainName() {
        StackTraceElement [] s = new RuntimeException().getStackTrace();
        return s[1].getClassName().toLowerCase() + "." +
                LDAP_TEST_BASE_DOMAIN + "." + InMemoryLdapServer.UNITTEST_BASE_DOMAIN_SEGMENT;
    }

    public static String genTestId() {
        Date date = new Date();
        SimpleDateFormat fmt =  new SimpleDateFormat("yyyyMMdd-HHmmss");
        return fmt.format(date);
    }

    // invoked once per JVM
    private static synchronized void initPerJVM() throws Exception {
        if (perJVMInited) {
            return;
        }
        perJVMInited = true;

        CliUtil.toolSetup(Log.Level.error.name());
        ZmailLog.test.setLevel(Log.Level.info);
        // ZmailLog.acl.setLevel(Log.Level.debug);
        // ZmailLog.autoprov.setLevel(Log.Level.debug);
        // ZmailLog.account.setLevel(Log.Level.debug);
        // ZmailLog.ldap.setLevel(Log.Level.debug);
        // ZmailLog.soap.setLevel(Log.Level.trace);

        if (useInMemoryLdapServerOverride != null) {
            boolean useInMemoryLdapServer =
                    Boolean.parseBoolean(useInMemoryLdapServerOverride);

            KnownKey key = new KnownKey("debug_use_in_memory_ldap_server",
                    useInMemoryLdapServerOverride);
            if (DebugConfig.useInMemoryLdapServer != useInMemoryLdapServer) {
                System.out.println("useInMemoryLdapServerOverride is " + useInMemoryLdapServerOverride +
                        " but LC key debug_use_in_memory_ldap_server is " + key.value() +
                        ".  Remove the value from LC key.");
                fail();
            }
        }
        ZmailLog.test.info("useInMemoryLdapServer = " + InMemoryLdapServer.isOn());

        RightManager.getInstance(true);

        Cleanup.deleteAll();
    }

}
