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
package org.zmail.cs.db;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;

import org.zmail.common.localconfig.LC;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.db.DbPool.PoolConfig;

/**
 * Default ConnectionFactory implementation
 *
 */
public class ZmailConnectionFactory extends DriverManagerConnectionFactory {

    private static ConnectionFactory sConnFactory = null;
    public static ConnectionFactory getConnectionFactory(PoolConfig pconfig) {
        if (sConnFactory == null) {
            String className = LC.zmail_class_dbconnfactory.value();
            if (className != null && !className.equals("")) {
                try {
                    ZmailLog.dbconn.debug("instantiating DB connection factory class "+className);
                    Class clazz = Class.forName(className);
                    Constructor constructor = clazz.getDeclaredConstructor(String.class, Properties.class);
                    sConnFactory = (ConnectionFactory) constructor.newInstance(pconfig.mConnectionUrl, pconfig.mDatabaseProperties);
                } catch (Exception e) {
                    ZmailLog.system.error("could not instantiate database connection pool '" + className + "'; defaulting to ZmailConnectionFactory", e);
                }
            }
            if (sConnFactory == null)
                sConnFactory = new ZmailConnectionFactory(pconfig.mConnectionUrl, pconfig.mDatabaseProperties);
        }
        return sConnFactory;
    }

    ZmailConnectionFactory(String connectUri, Properties props) {
        super(connectUri, props);
    }

    /**
     * Wraps the JDBC connection from the pool with a <tt>DebugConnection</tt>,
     * which does  <tt>sqltrace</tt> logging.
     */
    @Override
    public Connection createConnection() throws SQLException {
        Connection conn = super.createConnection();
        Db.getInstance().postCreate(conn);
        return new DebugConnection(conn);
    }
}
