/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.redolog.op;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.zmail.common.calendar.ICalTimeZone;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.MailboxOperation;
import org.zmail.cs.mailbox.Metadata;
import org.zmail.cs.mailbox.calendar.Util;
import org.zmail.cs.mailbox.calendar.tzfixup.TimeZoneFixupRules;
import org.zmail.cs.redolog.RedoLogInput;
import org.zmail.cs.redolog.RedoLogOutput;

public class FixCalendarItemTZ extends RedoableOp {

    private int mId;
    private Map<String, ICalTimeZone> mReplacementMap;

    public FixCalendarItemTZ() {
        super(MailboxOperation.FixCalendarItemTZ);
    }

    public FixCalendarItemTZ(int mailboxId, int itemId) {
        this();
        setMailboxId(mailboxId);
        mId = itemId;
    }

    public void setReplacementMap(Map<String, ICalTimeZone> replacementMap) {
        mReplacementMap = replacementMap;
    }

    @Override
    protected void serializeData(RedoLogOutput out) throws IOException {
        out.writeInt(mId);
        if (mReplacementMap != null) {
            out.writeInt(mReplacementMap.size());
            for (Entry<String, ICalTimeZone> entry : mReplacementMap.entrySet()) {
                String tzid = entry.getKey();
                ICalTimeZone newTZ = entry.getValue();
                String newTZMeta = null;
                if (newTZ != null)
                    newTZMeta = Util.encodeAsMetadata(newTZ).toString();
                out.writeUTF(tzid);
                out.writeUTF(newTZMeta);
            }
        } else {
            out.writeInt(0);  // map size == 0
        }
    }

    @Override
    protected void deserializeData(RedoLogInput in) throws IOException {
        mId = in.readInt();
        int numReplacements = in.readInt();
        if (numReplacements > 0) {
            mReplacementMap = new HashMap<String, ICalTimeZone>(numReplacements);
            for (int i = 0; i < numReplacements; i++) {
                String tzid = in.readUTF();
                String newTZMeta = in.readUTF();
                try {
                    ICalTimeZone newTZ = null;
                    if (newTZMeta != null)
                        newTZ = Util.decodeTimeZoneFromMetadata(new Metadata(newTZMeta));
                    mReplacementMap.put(tzid, newTZ);
                } catch (ServiceException e) {
                    IOException ioe = new IOException("Error deserializing timezone");
                    ioe.initCause(e);
                    throw ioe;
                }
            }
        }
    }

    @Override
    protected String getPrintableData() {
        StringBuilder sb = new StringBuilder("id=");
        sb.append(mId);
        if (mReplacementMap != null) {
            sb.append(", replacementMap=[");
            for (Entry<String, ICalTimeZone> entry : mReplacementMap.entrySet()) {
                String tzid = entry.getKey();
                ICalTimeZone newTZ = entry.getValue();
                sb.append("\n");
                sb.append("oldTZID=\"").append(tzid).append("\"\n==> newTZ: ").append(newTZ.toString()).append(",");
            }
            sb.append("]");
        }
        return sb.toString();
    }

    @Override
    public void redo() throws Exception {
        Mailbox mbox = MailboxManager.getInstance().getMailboxById(getMailboxId());
        TimeZoneFixupRules rules = new TimeZoneFixupRules(mReplacementMap);
        mbox.fixCalendarItemTZ(getOperationContext(), mId, rules);
    }
}
