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
package org.zmail.cs.db;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.hsqldb.cmdline.SqlFile;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.db.DbPool.PoolConfig;
import org.zmail.cs.mailbox.Mailbox;

/**
 * HSQLDB is for unit test. All data is in memory, not persistent across JVM restarts.
 *
 * @author ysasaki
 */
public final class HSQLDB extends Db {

    /**
     * Populates ZIMBRA and MBOXGROUP1 schema.
     */
    public static void createDatabase() throws Exception {
        createDatabase("", false);
    }

    /**
     * Populates ZIMBRA and MBOXGROUP1 schema.
     * @param zmailServerDir the directory that contains the ZmailServer project
     * @throws Exception
     */
    public static void createDatabase(String zmailServerDir, boolean isOctopus) throws Exception {
        zmailServerDir = Strings.nullToEmpty(zmailServerDir);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DbConnection conn = DbPool.getConnection();
        try {
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = ?");
            stmt.setString(1, "ZIMBRA");
            rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return;  // already exists
            }
            execute(conn, zmailServerDir + "src/db/hsqldb/db.sql");
            execute(conn, zmailServerDir + "src/db/hsqldb/create_database.sql");
            if (isOctopus) {
                execute(conn, "src/db/hsqldb/create_octopus_tables.sql");
            }
        } finally {
            DbPool.closeResults(rs);
            DbPool.quietCloseStatement(stmt);
            DbPool.quietClose(conn);
        }
    }

    /**
     * Deletes all records from all tables.
     */
    public static void clearDatabase() throws Exception {
        clearDatabase("");
    }

    /**
     * Deletes all records from all tables.
     * @param zmailServerDir the directory that contains the ZmailServer project
     * @throws Exception
     */
    public static void clearDatabase(String zmailServerDir) throws Exception {
        zmailServerDir = Strings.nullToEmpty(zmailServerDir);
        DbConnection conn = DbPool.getConnection();
        try {
            execute(conn, zmailServerDir + "src/db/hsqldb/clear.sql");
        } finally {
            DbPool.quietClose(conn);
        }
    }

    private static void execute(DbConnection conn, String file) throws Exception {
        Map<String, String> vars = Collections.singletonMap("DATABASE_NAME", DbMailbox.getDatabaseName(1));
        SqlFile sql = new SqlFile(new File(file));
        sql.addUserVars(vars);
        sql.setConnection(conn.getConnection());
        sql.execute();
        conn.commit();
    }

    @Override
    PoolConfig getPoolConfig() {
        return new Config();
    }

    @Override
    boolean supportsCapability(Capability capability) {
        switch (capability) {
            case MULTITABLE_UPDATE:
            case BITWISE_OPERATIONS:
            case REPLACE_INTO:
            case DISABLE_CONSTRAINT_CHECK:
                return false;
            default:
                return true;
        }
    }

    @Override
    boolean compareError(SQLException e, Error error) {
        switch (error) {
            case DUPLICATE_ROW:
                return e.getErrorCode() == -104;
            default:
                return false;
        }
    }

    /**
     * Always returns true. All schema is pre-populated.
     */
    @Override
    public boolean databaseExists(DbConnection connection, String dbname) {
        return true;
    }

    /**
     * TODO
     */
    @Override
    String forceIndexClause(String index) {
        return "";
    }

    @Override
    String getIFNULLClause(String expr1, String expr2) {
        return "COALESCE(" + expr1 + ", " + expr2 + ")";
    }

    @Override
    public String bitAND(String expr1, String expr2) {
        return "BITAND(" + expr1 + ", " + expr2 + ")";
    }

    @Override
    public void flushToDisk() {
    }

    private static final class Config extends PoolConfig {
        Config() {
            mDriverClassName = "org.hsqldb.jdbcDriver";
            mPoolSize = 10;
            mRootUrl = "jdbc:hsqldb:mem:";
            mConnectionUrl = "jdbc:hsqldb:mem:zmail";
            mSupportsStatsCallback = false;
            mDatabaseProperties = new Properties();
        }
    }

    @Override
    public String concat(String... fieldsToConcat) {
        Joiner joiner = Joiner.on(", ").skipNulls();
        return "CONCAT(" + joiner.join(fieldsToConcat) + ")";
    }

    @Override
    public String sign(String field) {
        return "SIGN(" + field + ")";
    }

    @Override
    public String lpad(String field, int padSize, String padString) {
        return "LPAD(" + field + ", " + padSize + ", '" + padString + "')";
    }

    @Override
    public String limit(int offset, int limit) {
        return "LIMIT " + limit + " OFFSET " + offset;
    }

    public void useMVCC(Mailbox mbox) throws ServiceException, SQLException {
        //tell HSQLDB to use multiversion so our asserts can read while write is open
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DbConnection conn = DbPool.getConnection(mbox);
        try {
            stmt = conn.prepareStatement("SET DATABASE TRANSACTION CONTROL MVCC");
            stmt.executeUpdate();
        } finally {
            DbPool.closeResults(rs);
            DbPool.quietCloseStatement(stmt);
            DbPool.quietClose(conn);
        }
    }

}
