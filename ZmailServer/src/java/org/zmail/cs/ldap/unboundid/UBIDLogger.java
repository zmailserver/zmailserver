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
package org.zmail.cs.ldap.unboundid;

import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.ldap.LdapOp;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.ZModificationList;
import org.zmail.cs.ldap.ZMutableEntry;
import org.zmail.cs.ldap.LdapServerConfig.ExternalLdapConfig;
import org.zmail.cs.ldap.ZLdapElement.StringLdapElementDebugListener;

/**
 * @author pshao
 */
public class UBIDLogger {
    
    private static Log debugLogger = ZmailLog.ldap;
    
    static class Timer {
        private long startTime;
        
        private void start() {
            startTime = System.currentTimeMillis();
        }
        
        private long elapsedMillis() {
            return System.currentTimeMillis() - startTime;
        }
        
    }
    
    static Timer beforeTimedOp() {
        if (!debugLogger.isDebugEnabled()) {
            return null;
        }
        
        Timer timer = new Timer();
        timer.start();
        return timer;
    }
    
    static void beforeOp(LdapOp ldapOp, LDAPConnection conn) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug("%s - conn=[%d]",
                ldapOp, conn.getConnectionID());
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, String dn) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d] dn=[%s]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(), 
                dn);
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, ZMutableEntry entry) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        StringLdapElementDebugListener debugListener = new StringLdapElementDebugListener();
        entry.debug(debugListener);
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d] entry=[%s]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(), 
                debugListener.getString());
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, String dn, ZModificationList modList) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        StringLdapElementDebugListener debugListener = new StringLdapElementDebugListener();
        modList.debug(debugListener);
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d] dn=[%s], mod=[%s]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(), 
                dn,
                debugListener.getString());
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID());
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, String baseDN, String filter) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d], base=[%s], filter=[%s]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(), 
                baseDN,
                filter);
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, DN oldDN, DN newDN) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d], oldDN=[%s], newDN=[%s]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(), 
                oldDN.toString(),
                newDN.toString());
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, LDAPConnectionPool connPool) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug("%s - millis=[%d], usage=[%s], conn=[%d], connPool=[%s(%d)]", 
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(), 
                getConnectionPoolLogName(connPool), 
                connPool.hashCode());
    }
    
    static void afterTimedOp(LdapOp ldapOp, Timer timer, LdapUsage usage,
            LDAPConnection conn, LdapServerPool serverPool, String bindDN) {
        if (!debugLogger.isDebugEnabled()) {
            return;
        }
        
        UBIDLogger.debugLogger.debug(
                "%s - millis=[%d], usage=[%s], conn=[%d], url=[%s], connType=[%s], bindDN=[%s]",
                ldapOp, timer.elapsedMillis(), usage.name(),
                conn.getConnectionID(),
                serverPool.getRawUrls(),
                serverPool.getConnectionType().name(),
                bindDN);
    }
   
    
    static String getConnectionPoolLogName(LDAPConnectionPool connPool) {
        String name = connPool.getConnectionPoolName();
        if (LdapConnectionPool.CP_ZIMBRA_REPLICA.equals(name) ||
            LdapConnectionPool.CP_ZIMBRA_MASTER.equals(name)) {
            return name;
        } else {
            // hide the password
            return ExternalLdapConfig.ConnPoolKey.getDisplayName(name);
        }
    }
    
    /*
    todo, xxx
    static void debug(ConnAction connAction, int connId, String connPoolName, int connPoolId, LdapUsage usage) {
        debugLogger.debug("%s - conn=[%d], connPool=[%s(%d)], usage=[%s]", 
                connAction.name(),
                connId,
                connPoolName, 
                connPoolId,
                usage.name());
    }
    */
}
