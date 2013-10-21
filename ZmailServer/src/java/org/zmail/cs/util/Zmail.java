/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.util;

import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.Timer;

import org.apache.mina.core.buffer.IoBuffer;

import org.zmail.common.calendar.WellKnownTimeZones;
import org.zmail.common.lmtp.SmtpToLmtp;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.SoapTransport;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.AutoProvisionThread;
import org.zmail.cs.account.ExternalAccountManagerTask;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.RightManager;
import org.zmail.cs.account.ldap.LdapProv;
import org.zmail.cs.db.DbPool;
import org.zmail.cs.db.Versions;
import org.zmail.cs.extension.ExtensionUtil;
import org.zmail.cs.iochannel.MessageChannel;
import org.zmail.cs.mailbox.MailboxIndex;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.PurgeThread;
import org.zmail.cs.mailbox.ScheduledTaskManager;
import org.zmail.cs.mailbox.acl.AclPushTask;
import org.zmail.cs.memcached.MemcachedConnector;
import org.zmail.cs.redolog.RedoLogProvider;
import org.zmail.cs.server.ServerManager;
import org.zmail.cs.servlet.FirstServlet;
import org.zmail.cs.session.SessionCache;
import org.zmail.cs.session.WaitSetMgr;
import org.zmail.cs.stats.ZmailPerf;
import org.zmail.cs.store.StoreManager;
import org.zmail.znative.Util;

/**
 * Class that encapsulates the initialization and shutdown of services needed
 * by any process that adds mail items.  Services under control include redo
 * logging and indexing.
 */
public final class Zmail {
    private static boolean sInited = false;
    private static boolean sIsMailboxd = false;

    /** Sets system properties before the server fully starts up.  Note that
     *  there's a potential race condition if {@link FirstServlet} or another
     *  servlet faults in classes or references properties before they're set
     *  here. */
    private static void setSystemProperties() {
        System.setProperty("mail.mime.decodetext.strict",       "false");
        System.setProperty("mail.mime.encodefilename",          "true");
        System.setProperty("mail.mime.charset",                 "utf-8");
        System.setProperty("mail.mime.base64.ignoreerrors",     "true");
        System.setProperty("mail.mime.ignoremultipartencoding", "false");
        System.setProperty("mail.mime.multipart.allowempty",    "true");
    }

    private static void checkForClass(String clzName, String jarName) {
        try {
            String s = Class.forName(clzName).getName();
            ZmailLog.misc.debug("checked for class " + s + " and found it");
        } catch (ClassNotFoundException cnfe) {
            ZmailLog.misc.error(jarName + " not in your common/lib?", cnfe);
        } catch (UnsatisfiedLinkError ule) {
            ZmailLog.misc.error("error in shared library used by " + jarName + "?", ule);
        }
    }

    private static String getSysProperty(String prop) {
        try {
            return System.getProperty(prop);
        } catch (SecurityException e) {
            return "(accessing " + prop + " is not allowed by security manager)";
        }
    }

    private static void logVersionAndSysInfo() {
        ZmailLog.misc.info("version=" + BuildInfo.VERSION +
                " release=" + BuildInfo.RELEASE +
                " builddate=" + BuildInfo.DATE +
                " buildhost=" + BuildInfo.HOST);

        ZmailLog.misc.info("LANG environment is set to: " + System.getenv("LANG"));

        ZmailLog.misc.info("System property java.home="            + getSysProperty("java.home"));
        ZmailLog.misc.info("System property java.runtime.version=" + getSysProperty("java.runtime.version"));
        ZmailLog.misc.info("System property java.version="         + getSysProperty("java.version"));
        ZmailLog.misc.info("System property java.vm.info="         + getSysProperty("java.vm.info"));
        ZmailLog.misc.info("System property java.vm.name="         + getSysProperty("java.vm.name"));
        ZmailLog.misc.info("System property java.vm.version="      + getSysProperty("java.vm.version"));
        ZmailLog.misc.info("System property os.arch="              + getSysProperty("os.arch"));
        ZmailLog.misc.info("System property os.name="              + getSysProperty("os.name"));
        ZmailLog.misc.info("System property os.version="           + getSysProperty("os.version"));
        ZmailLog.misc.info("System property sun.arch.data.model="  + getSysProperty("sun.arch.data.model"));
        ZmailLog.misc.info("System property sun.cpu.endian="       + getSysProperty("sun.cpu.endian"));
        ZmailLog.misc.info("System property sun.cpu.isalist="      + getSysProperty("sun.cpu.isalist"));
        ZmailLog.misc.info("System property sun.os.patch.level="   + getSysProperty("sun.os.patch.level"));
    }

    private static void checkForClasses() {
        checkForClass("javax.activation.DataSource", "activation.jar");
        checkForClass("javax.mail.internet.MimeMessage", "javamail-1.4.3.jar");
        checkForClass("org.zmail.znative.IO", "zmail-native.jar");
    }

    public static void startup() {
        try {
            startup(true);
        } catch (ServiceException se) {
            Zmail.halt("Exception during startup, aborting server, please check your config", se);
        }
    }

    public static void startupCLI() throws ServiceException {
        startup(false);
    }

    /**
     * Initialize the various subsystems at server/CLI startup time.
     * @param forMailboxd true if this is the mailboxd process; false for CLI processes
     * @throws ServiceException
     */
    private static synchronized void startup(boolean forMailboxd) throws ServiceException {
        if (sInited)
            return;

        sIsMailboxd = forMailboxd;
        if (sIsMailboxd) {
            FirstServlet.waitForInitialization();
        }

        setSystemProperties();

        logVersionAndSysInfo();

        SoapTransport.setDefaultUserAgent("ZCS", BuildInfo.VERSION);

        checkForClasses();

        ZmailApplication app = ZmailApplication.getInstance();

        DbPool.startup();

        app.initializeZmailDb(forMailboxd);


        if (!Versions.checkVersions()) {
            Zmail.halt("Data version mismatch.  Reinitialize or upgrade the backend data store.");
        }

        DbPool.loadSettings();

        String tzFilePath = LC.timezone_file.value();
        try {
            File tzFile = new File(tzFilePath);
            WellKnownTimeZones.loadFromFile(tzFile);
        } catch (Throwable t) {
            Zmail.halt("Unable to load timezones from " + tzFilePath, t);
        }

        Provisioning prov = Provisioning.getInstance();
        if (prov instanceof LdapProv) {
            ((LdapProv) prov).waitForLdapServer();
            if (forMailboxd) {
                AttributeManager.loadLdapSchemaExtensionAttrs((LdapProv) prov);
            }
        }

        if( Provisioning.getInstance().getLocalServer().isMailSSLClientCertOCSPEnabled()) {
            // Activate OCSP
            Security.setProperty("ocsp.enable", "true");
            // Activate CRLDP
            System.setProperty("com.sun.security.enableCRLDP", "true");
        }else {
            // Disable OCSP
            Security.setProperty("ocsp.enable", "false");
            // Disable CRLDP
            System.setProperty("com.sun.security.enableCRLDP", "false");
        }

        try {
            RightManager.getInstance();
        } catch (ServiceException e) {
            Util.halt("cannot initialize RightManager", e);
        }

        ZmailHttpConnectionManager.startReaperThread();

        ExtensionUtil.initAll();

        try {
            StoreManager.getInstance().startup();
        } catch (IOException e) {
            throw ServiceException.FAILURE("Unable to initialize StoreManager.", e);
        }

        MailboxManager.getInstance();

        app.startup();

        if (app.supports(MemcachedConnector.class.getName())) {
            MemcachedConnector.startup();
        }
        if (app.supports(EhcacheManager.class.getName())) {
            EhcacheManager.getInstance().startup();
        }

        // ZimletUtil.loadZimlets();

        MailboxIndex.startup();

        RedoLogProvider redoLog = RedoLogProvider.getInstance();
        if (sIsMailboxd) {
            redoLog.startup();
        } else {
            redoLog.initRedoLogManager();
        }

        System.setProperty("ical4j.unfolding.relaxed", "true");

        MailboxManager.getInstance().startup();

        app.initialize(sIsMailboxd);
        if (sIsMailboxd) {
            SessionCache.startup();

            Server server = Provisioning.getInstance().getLocalServer();

            if (!redoLog.isSlave()) {
                boolean useDirectBuffers = server.isMailUseDirectBuffers();
                IoBuffer.setUseDirectBuffer(useDirectBuffers);
                ZmailLog.misc.info("MINA setUseDirectBuffers(" + useDirectBuffers + ")");

                ServerManager.getInstance().startServers();
            }

            if (app.supports(WaitSetMgr.class.getName())) {
                WaitSetMgr.startup();
            }

            if (app.supports(MemoryStats.class.getName())) {
                MemoryStats.startup();
            }

            if (app.supports(ScheduledTaskManager.class.getName())) {
                ScheduledTaskManager.startup();
            }

            if (app.supports(PurgeThread.class.getName())) {
                PurgeThread.startup();
            }

            if (app.supports(AutoProvisionThread.class.getName())) {
                AutoProvisionThread.switchAutoProvThreadIfNecessary();
            }

            if (LC.smtp_to_lmtp_enabled.booleanValue()) {
                int smtpPort = LC.smtp_to_lmtp_port.intValue();
                int lmtpPort = Provisioning.getInstance().getLocalServer().getLmtpBindPort();
                SmtpToLmtp smtpServer = SmtpToLmtp.startup(smtpPort, "localhost", lmtpPort);
                smtpServer.setRecipientValidator(new SmtpRecipientValidator());
            }

            if (app.supports(AclPushTask.class)) {
                long pushInterval = server.getSharingUpdatePublishInterval();
                sTimer.schedule(new AclPushTask(), pushInterval, pushInterval);
            }

            if (app.supports(ExternalAccountManagerTask.class)) {
                long interval = server.getExternalAccountStatusCheckInterval();
                sTimer.schedule(new ExternalAccountManagerTask(), interval, interval);
            }

            if (prov.getLocalServer().isMessageChannelEnabled()) {
                try {
                    MessageChannel.getInstance().startup();
                } catch (IOException e) {
                    ZmailLog.misc.warn("can't start notification channels", e);
                }
            }

            // should be last, so that other subsystems can add dynamic stats counters
            if (app.supports(ZmailPerf.class.getName())) {
                ZmailPerf.initialize();
            }
        }

        ExtensionUtil.postInitAll();

        sInited = true;
    }

    public static synchronized void shutdown() throws ServiceException {
        if (!sInited)
            return;

        sInited = false;

        if (sIsMailboxd) {
            PurgeThread.shutdown();
            AutoProvisionThread.shutdown();
        }

        ZmailApplication app = ZmailApplication.getInstance();

        app.shutdown();

        if (sIsMailboxd) {
            if (app.supports(MemoryStats.class.getName())) {
                MemoryStats.shutdown();
            }

            if (app.supports(WaitSetMgr.class.getName())) {
                WaitSetMgr.shutdown();
            }
        }

        RedoLogProvider redoLog = RedoLogProvider.getInstance();
        if (sIsMailboxd) {
            if (!redoLog.isSlave()) {
                ServerManager.getInstance().stopServers();
            }

            SessionCache.shutdown();
        }

        MailboxIndex.shutdown();

        if (sIsMailboxd) {
            redoLog.shutdown();
        }

        if (app.supports(ExtensionUtil.class.getName())) {
            ExtensionUtil.destroyAll();
        }

        if (app.supports(MemcachedConnector.class.getName())) {
            MemcachedConnector.shutdown();
        }
        if (app.supports(EhcacheManager.class.getName())) {
            EhcacheManager.getInstance().shutdown();
        }

        MailboxManager.getInstance().shutdown();

        if (sIsMailboxd) {
            StoreManager.getInstance().shutdown();
        }

        ZmailHttpConnectionManager.shutdownReaperThread();

        sTimer.cancel();

        try {
            DbPool.shutdown();
        } catch (Exception ignored) {
        }
    }

    public static synchronized boolean started() {
        return sInited;
    }

    public static Timer sTimer = new Timer("Timer-Zmail", true);

    /**
     * Logs the given message and shuts down the server.
     *
     * @param message the message to log before shutting down
     */
    public static void halt(String message) {
        try {
            ZmailLog.system.fatal(message);
        } finally {
            Runtime.getRuntime().halt(1);
        }
    }

    /**
     * Logs the given message and shuts down the server.
     *
     * @param message the message to log before shutting down
     * @param t the exception that was thrown
     */
    public static void halt(String message, Throwable t) {
        try {
            ZmailLog.system.fatal(message, t);
        } finally {
            Runtime.getRuntime().halt(1);
        }
    }
}
