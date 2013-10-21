/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.common.util;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.PropertyConfigurator;

/**
 * Log categories.
 *
 * @author schemers
 */
public final class ZmailLog {

    /**
     * "ip" key for context. IP of request
     */
    private static final String C_IP = "ip";

    /**
     * "oip" key for context. originating IP of request
     */
    private static final String C_OIP = "oip";

    /**
     * "id" key for context. Id of the target account
     */
    public static final String C_ID = "id";

    /**
     * "name" key for context. Id of the target account
     */
    public static final String C_NAME = "name";

    /**
     * "aid" key for context. Id in the auth token. Only present if target id is
     * different then auth token id.
     */
    public static final String C_AID = "aid";

    /**
     * "aname" key for context. name in the auth token. Only present if target id is
     * different then auth token id.
     */
    public static final String C_ANAME = "aname";

    /**
     * "cid" is the connection id of a server that is monotonically increasing - useful
     * for tracking individual connections.
     */
    public static final String C_CONNECTIONID = "cid";

    /**
     * "mid" key for context. Id of requested mailbox. Only present if request is
     * dealing with a mailbox.
     */
    public static final String C_MID = "mid";

    /**
     * "ua" key for context.  The name of the client application.
     */
    private static final String C_USER_AGENT = "ua";

    /**
     * List of IP addresses and user-agents of the proxy chain.
     * was sent.
     */
    private static final String C_VIA = "via";

    /**
     * "msgid" key for context.  The Message-ID header of the message being
     * operated on.
     */
    private static final String C_MSG_ID = "msgid";

    /**
     * "item" key for context.
     */
    private static final String C_ITEM = "item";

    /**
     * "ds" key for context.  The name of the Data Source being operated on.
     */
    private static final String C_DATA_SOURCE_NAME = "ds";

    /**
     * "port" key for context.  The server port to which the client connected.
     */
    private static final String C_PORT = "port";

    /**
     * the "zmail.misc" logger. For all events that don't have a specific-catagory.
     */
    public static final Log misc = LogFactory.getLog("zmail.misc");

    /**
     * the "zmail.net" logger. For logging of network activities
     */
    public static final Log net = LogFactory.getLog("zmail.net");

    /**
     * the "zmail.index" logger. For indexing.
     */
    public static final Log index = LogFactory.getLog("zmail.index");

    /**
     * the "zmail.search" logger. For search.
     */
    public static final Log search = LogFactory.getLog("zmail.search");

    /**
     * the "zmail.searchstat" logger. For search statistics.
     */
    public static final Log searchstat = LogFactory.getLog("zmail.searchstat");

    /**
     * Fhe "zmail.searchstat" logger.  For logging statistics about what kinds of searches are run
     */
    public static final Log searchstats = LogFactory.getLog("zmail.searchstats");

    /**
     * the "zmail.redolog" logger. For redolog-releated events.
     */
    public static final Log redolog = LogFactory.getLog("zmail.redolog");

    /**
     * the "zmail.lmtp" logger. For LMTP-related events.
     */
    public static final Log lmtp = LogFactory.getLog("zmail.lmtp");

    /**
     * the "zmail.smtp" logger. For SMTP-related events.
     */
    public static final Log smtp = LogFactory.getLog("zmail.smtp");

    /**
     * the "zmail.nio" logger. For NIO-related events.
     */
    public static final Log nio = LogFactory.getLog("zmail.nio");

    /**
     * the "zmail.imap.client" logger. For IMAP client related events.
     */
    public static final Log imap_client = LogFactory.getLog("zmail.imap-client");

    /**
     * the "zmail.imap" logger. For IMAP server related events.
     */
    public static final Log imap = LogFactory.getLog("zmail.imap");

    /**
     * the "zmail.pop.client" logger. For POP3 client related events.
     */
    public static final Log pop_client = LogFactory.getLog("zmail.pop-client");

    /**
     * the "zmail.pop" logger. For POP3 server related events.
     */
    public static final Log pop = LogFactory.getLog("zmail.pop");

    /**
     * the "zmail.milter" logger. For MILTER-related events
     */
    public static final Log milter = LogFactory.getLog("zmail.milter");

    /**
     * the "zmail.mailbox" logger. For mailbox-related events.
     */
    public static final Log mailbox = LogFactory.getLog("zmail.mailbox");

    /**
     * the "zmail.calendar" logger. For calendar-related events.
     */
    public static final Log calendar = LogFactory.getLog("zmail.calendar");

    /**
     * the "zmail.im" logger. For instant messaging-related events.
     */
    public static final Log im = LogFactory.getLog("zmail.im");

    /**
     * the "zmail.im.intercept" logger. The IM packet interceptor (IM protocol logger)
     */
    public static final Log im_intercept= LogFactory.getLog("zmail.im.intercept");

    /**
     * the "zmail.account" logger. For account-related events.
     */
    public static final Log account = LogFactory.getLog("zmail.account");

    /**
     * the "zmail.autoprov" logger. For account auto provision related events.
     */
    public static final Log autoprov = LogFactory.getLog("zmail.autoprov");

    /**
     * the "zmail.gal" logger. For gal-related events.
     */
    public static final Log gal = LogFactory.getLog("zmail.gal");

    /**
     * the "zmail.gal.concurrency" logger. For gal concurrency events.
     */
    public static final Log galconcurrency = LogFactory.getLog("zmail.gal.concurrency");

    /**
     * the "zmail.ldap" logger. For ldap-related events.
     */
    public static final Log ldap = LogFactory.getLog("zmail.ldap");

    /**
     * the "zmail.acl" logger. For acl-related events.
     */
    public static final Log acl = LogFactory.getLog("zmail.acl");

    /**
     * the "zmail.security" logger. For security-related events
     */
    public static final Log security = LogFactory.getLog("zmail.security");

    /**
     * the "zmail.soap" logger. For soap-related events
     */
    public static final Log soap = LogFactory.getLog("zmail.soap");

    /**
     * the "zmail.test" logger. For testing-related events
     */
    public static final Log test = LogFactory.getLog("zmail.test");

    /**
     * the "zmail.sqltrace" logger. For tracing SQL statements sent to the database
     */
    public static final Log sqltrace = LogFactory.getLog("zmail.sqltrace");

    /**
     * the "zmail.dbconn" logger. For tracing database connections
     */
    public static final Log dbconn = LogFactory.getLog("zmail.dbconn");

    /**
     * the "zmail.perf" logger. For logging performance statistics
     */
    public static final Log perf = LogFactory.getLog("zmail.perf");

    /**
     * the "zmail.cache" logger. For tracing object cache activity
     */
    public static final Log cache = LogFactory.getLog("zmail.cache");

    /**
     * the "zmail.filter" logger. For filter-related logs.
     */
    public static final Log filter = LogFactory.getLog("zmail.filter");

    /**
     * the "zmail.session" logger. For session- and notification-related logs.
     */
    public static final Log session = LogFactory.getLog("zmail.session");

    /**
     * the "zmail.backup" logger. For backup/restore-related logs.
     */
    public static final Log backup = LogFactory.getLog("zmail.backup");

    /**
     * the "zmail.system" logger. For startup/shutdown and other related logs.
     */
    public static final Log system = LogFactory.getLog("zmail.system");

    /**
     * the "zmail.sync" logger. For sync client interface logs.
     */
    public static final Log sync = LogFactory.getLog("zmail.sync");

    /**
     * the "zmail.synctrace" logger. For sync client interface logs.
     */
    public static final Log synctrace = LogFactory.getLog("zmail.synctrace");

    /**
     * the "zmail.syncstate" logger. For sync client interface logs.
     */
    public static final Log syncstate = LogFactory.getLog("zmail.syncstate");

    /**
     * the "zmail.wbxml" logger. For wbxml client interface logs.
     */
    public static final Log wbxml = LogFactory.getLog("zmail.wbxml");

    /**
     * the "zmail.xsync" logger. For xsync client interface logs.
     */
    public static final Log xsync = LogFactory.getLog("zmail.xsync");

    /**
     * the "zmail.extensions" logger. For logging extension loading related info.
     */
    public static final Log extensions = LogFactory.getLog("zmail.extensions");

    /**
     * the "zmail.zimlet" logger. For logging zimlet related info.
     */
    public static final Log zimlet = LogFactory.getLog("zmail.zimlet");

    /**
     * the "zmail.doc" logger. For document sharing.
     */
    public static final Log doc = LogFactory.getLog("zmail.doc");

    /**
     * the "zmail.op" logger. Logs server operations
     */
    public static final Log op = LogFactory.getLog("zmail.op");

    /**
     * the "zmail.dav" logger. Logs dav operations
     */
    public static final Log dav = LogFactory.getLog("zmail.dav");

    /**
     * the "zmail.io" logger.  Logs file IO operations.
     */
    public static final Log io = LogFactory.getLog("zmail.io");

    /**
     * the "zmail.datasource" logger.  Logs data source operations.
     */
    public static final Log datasource = LogFactory.getLog("zmail.datasource");

    /**
     * remote management.
     */
    public static final Log rmgmt = LogFactory.getLog("zmail.rmgmt");

    /**
     * the "zmail.webclient" logger. Logs ZmailWebClient servlet and jsp operations.
     */
    public static final Log webclient = LogFactory.getLog("zmail.webclient");

    /**
     * the "zmail.scheduler" logger.  Logs scheduled task operations.
     */
    public static final Log scheduler = LogFactory.getLog("zmail.scheduler");

    /**
     * the "zmail.store" logger.  Logs filesystem storage operations.
     */
    public static final Log store = LogFactory.getLog("zmail.store");

    /**
     * the "zmail.fb" logger.  Logs free/busy operations.
     */
    public static final Log fb = LogFactory.getLog("zmail.fb");

    /**
     * the "zmail.purge" logger.  Logs mailbox purge operations.
     */
    public static final Log purge = LogFactory.getLog("zmail.purge");

    /**
     * the "zmail.mailop" logger.  Logs changes to items in the mailbox.
     */
    public static final Log mailop = LogFactory.getLog("zmail.mailop");

    /**
     * "zmail.slogger" logger.  Used for "logger service", publishes stats events to syslog
     */
    public static final Log slogger = LogFactory.getLog("zmail.slogger");

    /**
     * the "zmail.mbxmgr" logger is used to track mailbox loading/maintenance mode
     */
    public static final Log mbxmgr = LogFactory.getLog("zmail.mbxmgr");

    /**
     * "zmail.tnef" logger.  Logs TNEF conversion operations.
     */
    public static final Log tnef = LogFactory.getLog("zmail.tnef");


    public static final Log nginxlookup = LogFactory.getLog("zmail.nginxlookup");

    /**
     * the "zmail.contact" logger.  Logs contact operations.
     */
    public static final Log contact = LogFactory.getLog("zmail.contact");

    /**
     * the "zmail.share" logger.  Logs share operations.
     */
    public static final Log share = LogFactory.getLog("zmail.share");

    /**
     * the "zmail.activity" logger. For ACTIVITY-related events
     */
    public static final Log activity = LogFactory.getLog("zmail.activity");


    /**
     * Maps the log category name to its description.
     */
    public static final Map<String, String> CATEGORY_DESCRIPTIONS;

    private ZmailLog() {
    }

    /**
     * Returns a new <tt>Set</tt> that contains the values of
     * {@link #C_NAME} and {@link #C_ANAME} if they are set.
     */
    public static Set<String> getAccountNamesFromContext() {
        Map<String, String> contextMap = sContextMap.get();
        if (contextMap == null) {
            return Collections.emptySet();
        }

        String name = contextMap.get(C_NAME);
        String aname = contextMap.get(C_ANAME);
        if (name == null && aname == null) {
            return Collections.emptySet();
        }

        Set<String> names = new HashSet<String>();
        if (name != null) {
            names.add(name);
        }
        if (aname != null) {
            names.add(aname);
        }
        return names;
    }

    private static final ThreadLocal<Map<String, String>> sContextMap = new ThreadLocal<Map<String, String>>();
    private static final ThreadLocal<String> sContextString = new ThreadLocal<String>();

    private static final Set<String> CONTEXT_KEY_ORDER = new LinkedHashSet<String>();

    static {
        CONTEXT_KEY_ORDER.add(C_NAME);
        CONTEXT_KEY_ORDER.add(C_ANAME);
        CONTEXT_KEY_ORDER.add(C_MID);
        CONTEXT_KEY_ORDER.add(C_IP);

        // Initialize log category descriptions.  Categories that don't have a description
        // won't be listed in zmprov online help.
        Map<String, String> descriptions = new TreeMap<String, String>();
        descriptions.put(misc.getCategory(), "Miscellaneous");
        descriptions.put(index.getCategory(), "Indexing operations");
        descriptions.put(search.getCategory(), "Search operations");
        descriptions.put(redolog.getCategory(), "Redo log operations");
        descriptions.put(lmtp.getCategory(), "LMTP server (incoming mail)");
        descriptions.put(smtp.getCategory(), "SMTP client (outgoing mail)");
        descriptions.put(imap_client.getCategory(), "IMAP client");
        descriptions.put(imap.getCategory(), "IMAP server");
        descriptions.put(milter.getCategory(), "MILTER protocol operations");
        descriptions.put(pop_client.getCategory(), "POP client");
        descriptions.put(pop.getCategory(), "POP server");
        descriptions.put(mailbox.getCategory(), "General mailbox operations");
        descriptions.put(calendar.getCategory(), "Calendar operations");
        descriptions.put(im.getCategory(), "Instant messaging operations");
        descriptions.put(account.getCategory(), "Account operations");
        descriptions.put(autoprov.getCategory(), "Auto provision operations");
        descriptions.put(gal.getCategory(), "GAL operations");
        descriptions.put(ldap.getCategory(), "LDAP operations");
        descriptions.put(acl.getCategory(), "ACL operations");
        descriptions.put(security.getCategory(), "Security events");
        descriptions.put(soap.getCategory(), "SOAP protocol");
        descriptions.put(sqltrace.getCategory(), "SQL tracing");
        descriptions.put(dbconn.getCategory(), "Database connection tracing");
        descriptions.put(cache.getCategory(), "In-memory cache operations");
        descriptions.put(filter.getCategory(), "Mail filtering");
        descriptions.put(session.getCategory(), "User session tracking");
        descriptions.put(backup.getCategory(), "Backup and restore");
        descriptions.put(system.getCategory(), "Startup/shutdown and other system messages");
        descriptions.put(sync.getCategory(), "Sync client operations");
        descriptions.put(extensions.getCategory(), "Server extension loading");
        descriptions.put(zimlet.getCategory(), "Zimlet operations");
        descriptions.put(doc.getCategory(), "Docs operations");
        descriptions.put(mailop.getCategory(), "Changes to mailbox state");
        descriptions.put(dav.getCategory(), "DAV operations");
        descriptions.put(io.getCategory(), "Filesystem operations");
        descriptions.put(store.getCategory(), "Mail store disk operations");
        descriptions.put(purge.getCategory(), "Mailbox purge operations");
        descriptions.put(datasource.getCategory(), "Data Source operations");
        descriptions.put(nginxlookup.getCategory(), "Nginx lookup operations");
        descriptions.put(activity.getCategory(), "Document operations");
        CATEGORY_DESCRIPTIONS = Collections.unmodifiableMap(descriptions);
    }

    static String getContextString() {
        return sContextString.get();
    }

    //this is called from offline and only at LC init so we are taking chances with race
    private static final Set<String> CONTEXT_FILTER = new HashSet<String>();
    public static void addContextFilters(String filters) {
        for (String item : filters.split(","))
            CONTEXT_FILTER.add(item);
    }

    /**
     * Adds a key/value pair to the current thread's logging context.  If
     * <tt>key</tt> is null, does nothing.  If <tt>value</tt> is null,
     * removes the context entry.
     */
    public static void addToContext(String key, String value) {
        if (key == null || CONTEXT_FILTER.contains(key))
            return;

        Map<String, String> contextMap = sContextMap.get();
        boolean contextChanged = false;

        if (StringUtil.isNullOrEmpty(value)) {
            // Remove
            if (contextMap != null) {
                String oldValue = contextMap.remove(key);
                if (oldValue != null) {
                    contextChanged = true;
                }
            }
        } else {
            // Add
            if (contextMap == null) {
                contextMap = new LinkedHashMap<String, String>();
                sContextMap.set(contextMap);
            }
            String oldValue = contextMap.put(key, value);
            if (!StringUtil.equal(oldValue, value)) {
                contextChanged = true;
            }
        }
        if (contextChanged) {
            updateContextString();
        }
    }

    /**
     * Updates the context string with the latest data in {@link #sContextMap}.
     */
    private static void updateContextString() {
        Map<String, String> contextMap = sContextMap.get();
        if (contextMap == null || contextMap.size() == 0) {
            sContextString.set(null);
            return;
        }

        StringBuilder sb = new StringBuilder();

        // Append ordered keys first
        for (String key : CONTEXT_KEY_ORDER) {
            String value = contextMap.get(key);
            if (value != null) {
                encodeArg(sb, key, value);
            }
        }

        // Append the rest
        for (String key : contextMap.keySet()) {
            if (!CONTEXT_KEY_ORDER.contains(key)) {
                String value = contextMap.get(key);
                if (key != null && value != null) {
                    encodeArg(sb, key, value);
                }
            }
        }

        sContextString.set(sb.toString());
    }

    /**
     * Adds a <tt>MailItem</tt> id to the current thread's
     * logging context.
     */
    public static void addItemToContext(int itemId) {
        addToContext(C_ITEM, Integer.toString(itemId));
    }

    /**
     * Removes a key/value pair from the current thread's logging context.
     */
    public static void removeFromContext(String key) {
        if (key != null) {
            addToContext(key, null);
        }
    }

    /**
     * Removes a <tt>MailItem</tt> id from the current thread's
     * logging context.
     */
    public static void removeItemFromContext(int itemId) {
        removeFromContext(C_ITEM);
    }

    /**
     * Adds account name to the current thread's logging context.
     */
    public static void addAccountNameToContext(String accountName) {
        ZmailLog.addToContext(C_NAME, accountName);
    }

    /**
     * Removes all account-specific values from the current thread's
     * logging context.
     */
    public static void removeAccountFromContext() {
        removeFromContext(C_ID);
        removeFromContext(C_MID);
        removeFromContext(C_NAME);
        removeFromContext(C_ANAME);
        removeFromContext(C_ITEM);
        removeFromContext(C_MSG_ID);
    }

    /**
     * Adds ip to the current thread's logging context.
     */
    public static void addIpToContext(String ipAddress) {
        ZmailLog.addToContext(C_IP, ipAddress);
    }

    /**
     * Adds oip (originating IP) to the current thread's logging context.
     */
    public static void addOrigIpToContext(String ipAddress) {
        ZmailLog.addToContext(C_OIP, ipAddress);
    }

    /**
     * Adds connection id to the current thread's logging context.
     */
    public static void addConnectionIdToContext(String connectionId) {
        ZmailLog.addToContext(C_CONNECTIONID, connectionId);
    }

    /**
     * Adds mailbox id to the current thread's logging context.
     */
    public static void addMboxToContext(int mboxId) {
        addToContext(C_MID, Integer.toString(mboxId));
    }

    /**
     * Removes mailbox id from the current thread's logging context.
     */
    public static void removeMboxFromContext() {
        removeFromContext(C_MID);
    }

    /**
     * Adds message id to the current thread's logging context.
     */
    public static void addMsgIdToContext(String messageId) {
        addToContext(C_MSG_ID, messageId);
    }

    /**
     * Adds data source name to the current thread's logging context.
     */
    public static void addDataSourceNameToContext(String dataSourceName) {
        addToContext(C_DATA_SOURCE_NAME, dataSourceName);
    }

    /**
     * Removes data source name from the current thread's logging context.
     */
    public static void removeDataSourceNameFromContext() {
        removeFromContext(C_DATA_SOURCE_NAME);
    }

    /**
     * Adds port to the current thread's logging context.
     */
    public static void addPortToContext(int port) {
        ZmailLog.addToContext(C_PORT, Integer.toString(port));
    }

    /**
     * Adds user agent to the current thread's logging context.
     */
    public static void addUserAgentToContext(String ua) {
        ZmailLog.addToContext(C_USER_AGENT, ua);
    }

    /**
     * Adds {@code via} to the current thread's logging context.
     *
     * @param value
     */
    public static void addViaToContext(String value) {
        ZmailLog.addToContext(C_VIA, value);
    }

    /**
     * Clears the current thread's logging context.
     *
     */
    public static void clearContext() {
        Map<String, String> contextMap = sContextMap.get();
        if (contextMap != null) {
            contextMap.clear();
        }
        sContextString.remove();
    }

    public static String getStackTrace(int maxDepth) {
        // Thread.currentThread().getStackTrace() would seem cleaner but bizarrely is slower.
        StackTraceElement [] stElems = new Throwable().getStackTrace();

        if (stElems == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int count = -1;
        for (StackTraceElement stElem : stElems) {
            count++;
            if (count == 0) {
                continue;  // Skip element for this method.
            }
            sb.append(stElem.toString()).append('\n');
            if (count >= maxDepth) {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Setup log4j for our command line tools.
     *
     * If System.getProperty(zmail.log4j.level) is set then log at that level.
     * Else log at the specified defaultLevel.
     */
    public static void toolSetupLog4j(String defaultLevel, String logFile, boolean showThreads) {
        String level = System.getProperty("zmail.log4j.level");
        if (level == null) {
            level = defaultLevel;
        }
        Properties p = new Properties();
        p.put("log4j.rootLogger", level + ",A1");
        if (logFile != null) {
            p.put("log4j.appender.A1", "org.apache.log4j.FileAppender");
            p.put("log4j.appender.A1.File", logFile);
            p.put("log4j.appender.A1.Append", "false");
        } else {
            p.put("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
        }
        p.put("log4j.appender.A1.layout", "org.apache.log4j.PatternLayout");
        if (showThreads) {
            p.put("log4j.appender.A1.layout.ConversionPattern", "[%t] [%x] %p: %m%n");
        } else {
            p.put("log4j.appender.A1.layout.ConversionPattern", "[%x] %p: %m%n");
        }
        PropertyConfigurator.configure(p);
    }

    public static void toolSetupLog4jConsole(String defaultLevel, boolean stderr, boolean showThreads) {
        String level = System.getProperty("zmail.log4j.level");
        if (level == null) {
            level = defaultLevel;
        }
        Properties p = new Properties();
        p.put("log4j.rootLogger", level + ",A1");

        p.put("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
        if (stderr)
            p.put("log4j.appender.A1.target", "System.err");

        p.put("log4j.appender.A1.layout", "org.apache.log4j.PatternLayout");
        if (showThreads) {
            p.put("log4j.appender.A1.layout.ConversionPattern", "[%t] [%x] %p: %m%n");
        } else {
            p.put("log4j.appender.A1.layout.ConversionPattern", "[%x] %p: %m%n");
        }
        PropertyConfigurator.configure(p);
    }

    /**
     * Setup log4j for command line tool using specified log4j.properties file.
     * If file doesn't exist System.getProperty(zmail.home)/conf/log4j.properties
     * file will be used.
     * @param defaultLevel
     * @param propsFile full path to log4j.properties file
     */
    public static void toolSetupLog4j(String defaultLevel, String propsFile) {
        if (propsFile != null && new File(propsFile).exists()) {
            PropertyConfigurator.configure(propsFile);
        } else {
            toolSetupLog4j(defaultLevel, null, false);
        }
    }

    private static void encodeArg(StringBuilder sb, String name, String value) {
        if (value == null) {
            value = "";
        }
        if (value.indexOf(';') != -1) {
            value = value.replaceAll(";", ";;");
        }
        // replace returns ref to original string if char to replace doesn't exist
        value = value.replace('\r', ' ');
        value = value.replace('\n', ' ');
        sb.append(name);
        sb.append("=");
        sb.append(value);
        sb.append(';');
    }

    /**
     * Take an array of Strings [ "name1", "value1", "name2", "value2", ...] and
     * format them for logging purposes.
     *
     * @param strings
     * @return formatted string
     */
    public static String encodeAttrs(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i += 2) {
            if (i > 0) {
                sb.append(' ');
            }
            encodeArg(sb, args[i], args[i + 1]);
        }
        return sb.toString();
    }

    /**
     * Take an array of Strings [ "name1", "value1", "name2", "value2", ...] and
     * format them for logging purposes into: <tt>name1=value1; name2=value;</tt>.
     * Semicolons are escaped with two semicolons (value a;b is encoded as a;;b).
     *
     * @param strings
     * @return formatted string
     */
    public static String encodeAttrs(String[] args, Map<String, ?> extraArgs) {
        StringBuilder sb = new StringBuilder();
        boolean needSpace = false;
        for (int i = 0; i < args.length; i += 2) {
            if (needSpace) {
                sb.append(' ');
            } else {
                needSpace = true;
            }
            encodeArg(sb, args[i], args[i + 1]);
        }
        if (extraArgs != null) {
            for (Map.Entry<String, ?> entry : extraArgs.entrySet()) {
                if (needSpace) {
                    sb.append(' ');
                } else {
                    needSpace = true;
                }
                String name = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    encodeArg(sb, name, "");
                } else if (value instanceof String) {
                    encodeArg(sb, name, (String) value);
                } else if (value instanceof String[]) {
                    for (String arg : (String[]) value) {
                        encodeArg(sb, name, arg);
                    }
                }
            }
        }
        return sb.toString();
    }
}
