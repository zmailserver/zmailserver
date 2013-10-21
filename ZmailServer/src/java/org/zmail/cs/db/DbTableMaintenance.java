/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

/**
 * @author bburtin
 */
package org.zmail.cs.db;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;

public class DbTableMaintenance {
    
    public static int runMaintenance()
    throws ServiceException {
        if (!(Db.getInstance() instanceof MySQL)) {
            ZmailLog.mailbox.warn("Table maintenance only supported for MySQL.");
            return 0;
        }
        
        int numTables = 0;
        DbResults results = DbUtil.executeQuery(
            "SELECT table_schema, table_name " +
            "FROM INFORMATION_SCHEMA.TABLES " +
            "WHERE table_schema = 'zmail' " +
            "OR table_schema LIKE '" + DbMailbox.DB_PREFIX_MAILBOX_GROUP + "%'");

        while (results.next()) {
            String dbName = results.getString("TABLE_SCHEMA");
            String tableName = results.getString("TABLE_NAME");
            String sql = String.format("ANALYZE TABLE %s.%s", dbName, tableName); 
            ZmailLog.mailbox.info("Running %s", sql);
            DbUtil.executeUpdate(sql);
            numTables++;
        }
        
        return numTables;
    }
}
