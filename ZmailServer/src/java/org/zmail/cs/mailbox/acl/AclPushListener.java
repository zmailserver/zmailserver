/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.cs.mailbox.acl;

import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailboxListener;
import org.zmail.cs.session.PendingModifications.Change;
import org.zmail.cs.util.Zmail;

import java.util.Collections;
import java.util.Set;

/**
 * Listens to folder changes and schedules immediate run of {@link AclPushTask} in a separate thread if required.
 */
public class AclPushListener extends MailboxListener {

    private static final Set<MailItem.Type> registeredTypes = Collections.singleton(MailItem.Type.FOLDER);

    @Override
    public Set<MailItem.Type> registerForItemTypes() {
        return registeredTypes;
    }

    @Override
    public void notify(ChangeNotification notification) {
        boolean runAclPushTask = false;
        if (notification.mods.created != null) {
            for (MailItem created : notification.mods.created.values()) {
                if (created instanceof Folder) {
                    Folder folder = (Folder) created;
                    if (folder.getACL() != null) {
                        runAclPushTask = true;
                        break;
                    }
                }
            }
        }
        if (!runAclPushTask && notification.mods.modified != null) {
            for (Change change : notification.mods.modified.values()) {
                // we also need to check for folder rename and move
                if (change.what instanceof Folder && (change.why & (Change.ACL | Change.NAME | Change.PARENT)) != 0) {
                    runAclPushTask = true;
                    break;
                }
            }
        }
        if (!runAclPushTask && notification.mods.deleted != null) {
            for (Change change : notification.mods.deleted.values()) {
                if (change.preModifyObj == null) {
                    if (change.what == MailItem.Type.FOLDER) {
                        // not sure if the deleted folder had an ACL
                        runAclPushTask = true;
                        break;
                    }
                } else if (change.preModifyObj instanceof Folder && ((Folder) change.preModifyObj).getACL() != null) {
                    runAclPushTask = true;
                    break;
                }
            }
        }
        if (runAclPushTask) {
            // run in separate thread to avoid ldap communication inside mailbox lock
            Zmail.sTimer.schedule(new AclPushTask(), 0);
        }
    }
}
