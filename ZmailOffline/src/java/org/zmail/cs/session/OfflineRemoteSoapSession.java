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
package org.zmail.cs.session;

import org.zmail.common.soap.Element;
import org.zmail.common.soap.ZmailNamespace;
import org.zmail.cs.account.Server;
import org.zmail.soap.ZmailSoapContext;

public class OfflineRemoteSoapSession extends OfflineSoapSession {

    public OfflineRemoteSoapSession(ZmailSoapContext zsc) {
        super(zsc);
    }

    @Override
    protected boolean isMailboxListener() {
        return false;
    }

    @Override
    public String getRemoteSessionId(Server server) {
        return null;
    }

    @Override
    public void putRefresh(Element ctxt, ZmailSoapContext zsc) {
        ctxt.addUniqueElement(ZmailNamespace.E_REFRESH);
        return;
    }

    @Override
    public Element putNotifications(Element ctxt, ZmailSoapContext zsc, int lastSequence) {
        if (ctxt == null) {
            return null;
        }
        QueuedNotifications ntfn;
        synchronized (sentChanges) {
            if (!changes.hasNotifications()) {
                return null;
            }
            ntfn = changes;
            changes = new QueuedNotifications(ntfn.getSequence() + 1);
        }

        putQueuedNotifications(null, ntfn, ctxt, zsc);
        return ctxt;
    }

}
