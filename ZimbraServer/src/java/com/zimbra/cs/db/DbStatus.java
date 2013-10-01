/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zimbra.common.util.Log;
import com.zimbra.common.util.LogFactory;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.db.DbPool.DbConnection;

/**
 * @since 2005. 1. 26.
 */
public class DbStatus {

    private static Log mLog = LogFactory.getLog(DbStatus.class);

    public static boolean healthCheck() {
        boolean result = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DbConnection conn = null;
        try {
            conn = DbPool.getConnection();
            stmt = conn.prepareStatement("SELECT 'foo' AS STATUS");
            rs = stmt.executeQuery();
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            mLog.warn("Database health check error", e);
        } catch (ServiceException e) {
            mLog.warn("Database health check error", e);
        } finally {
            try {
                DbPool.closeResults(rs);
            } catch (ServiceException e) {
                mLog.info("Ignoring error while closing database result set during health check", e);
            }
            try {
                DbPool.closeStatement(stmt);
            } catch (ServiceException e) {
                mLog.info("Ignoring error while closing database statement during health check", e);
            }
            if (conn != null)
                DbPool.quietClose(conn);
        }
        return result;
    }
}
