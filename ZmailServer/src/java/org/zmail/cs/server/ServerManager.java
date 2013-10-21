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
package org.zmail.cs.server;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.imap.ImapConfig;
import org.zmail.cs.imap.ImapServer;
import org.zmail.cs.imap.NioImapServer;
import org.zmail.cs.imap.TcpImapServer;
import org.zmail.cs.lmtpserver.LmtpConfig;
import org.zmail.cs.lmtpserver.LmtpServer;
import org.zmail.cs.lmtpserver.TcpLmtpServer;
import org.zmail.cs.milter.MilterConfig;
import org.zmail.cs.milter.MilterServer;
import org.zmail.cs.pop3.NioPop3Server;
import org.zmail.cs.pop3.Pop3Config;
import org.zmail.cs.pop3.Pop3Server;
import org.zmail.cs.pop3.TcpPop3Server;
import org.zmail.cs.util.ZmailApplication;

public final class ServerManager {
    private LmtpServer lmtpServer;
    private Pop3Server pop3Server;
    private Pop3Server pop3SSLServer;
    private ImapServer imapServer;
    private ImapServer imapSSLServer;
    private MilterServer milterServer;

    private static final ServerManager INSTANCE = new ServerManager();

    // For debugging...
    private static final boolean NIO_ENABLED = Boolean.getBoolean("ZmailNioEnabled");

    public static ServerManager getInstance() {
        return INSTANCE;
    }

    public void startServers() throws ServiceException {
        ZmailApplication app = ZmailApplication.getInstance();
        if (app.supports(LmtpServer.class)) {
            startLmtpServer();
        }
        if (app.supports(Pop3Server.class)) {
            if (isEnabled(Provisioning.A_zmailPop3ServerEnabled)) {
                pop3Server = startPop3Server(false);
            }
            if (isEnabled(Provisioning.A_zmailPop3SSLServerEnabled)) {
                pop3SSLServer = startPop3Server(true);
            }
        }
        if (app.supports(ImapServer.class)) {
            if (isEnabled(Provisioning.A_zmailImapServerEnabled)) {
                imapServer = startImapServer(false);
            }
            if (isEnabled(Provisioning.A_zmailImapSSLServerEnabled)) {
                imapSSLServer = startImapServer(true);
            }
        }

        // run milter service in the same process as mailtoxd. should be used only in dev environment
        if (app.supports(MilterServer.class)) {
            if (LC.milter_in_process_mode.booleanValue()) {
                milterServer = startMilterServer();
            }
        }
    }

    private static boolean isEnabled(String key) throws ServiceException {
        return Provisioning.getInstance().getLocalServer().getBooleanAttr(key, false);
    }

    private LmtpServer startLmtpServer() throws ServiceException {
        LmtpConfig config = LmtpConfig.getInstance();
        LmtpServer server = new TcpLmtpServer(config);
        server.start();
        return server;
    }

    private Pop3Server startPop3Server(boolean ssl) throws ServiceException {
        Pop3Config config = new Pop3Config(ssl);
        Pop3Server server = NIO_ENABLED || LC.nio_pop3_enabled.booleanValue() ?
            new NioPop3Server(config) : new TcpPop3Server(config);
        server.start();
        return server;
    }

    private ImapServer startImapServer(boolean ssl) throws ServiceException {
        ImapConfig config = new ImapConfig(ssl);
        ImapServer server = NIO_ENABLED || LC.nio_imap_enabled.booleanValue() ?
            new NioImapServer(config) : new TcpImapServer(config);
        server.start();
        return server;
    }

    private MilterServer startMilterServer() throws ServiceException {
        MilterServer server = new MilterServer(new MilterConfig());
        server.start();
        return server;
    }

    public void stopServers() throws ServiceException {
        if (lmtpServer != null) {
            lmtpServer.stop();
        }
        if (pop3Server != null) {
            pop3Server.stop();
        }
        if (pop3SSLServer != null) {
            pop3SSLServer.stop();
        }
        if (imapServer != null) {
            imapServer.stop();
        }
        if (imapSSLServer != null) {
            imapSSLServer.stop();
        }
        if (milterServer != null) {
            milterServer.stop();
        }
    }

    public LmtpServer getLmtpServer() {
        return lmtpServer;
    }

}
