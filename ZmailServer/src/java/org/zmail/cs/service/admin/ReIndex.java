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
package org.zmail.cs.service.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Splitter;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AccountServiceException;
import org.zmail.cs.account.CalendarResource;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxIndex;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.soap.ZmailSoapContext;

/**
 * Admin operation handler for {@code reIndexMailbox(rim)}.
 *
 * @author tim
 * @author ysasaki
 */
public final class ReIndex extends AdminDocumentHandler {

    private static final String ACTION_START = "start";
    private static final String ACTION_STATUS = "status";
    private static final String ACTION_CANCEL = "cancel";

    private static final String STATUS_STARTED = "started";
    private static final String STATUS_RUNNING = "running";
    private static final String STATUS_IDLE = "idle";
    private static final String STATUS_CANCELLED = "cancelled";

    private static final String[] TARGET_ACCOUNT_PATH = new String[] {
        AdminConstants.E_MAILBOX, AdminConstants.A_ACCOUNTID
    };

    @Override
    protected String[] getProxiedAccountPath() {
        return TARGET_ACCOUNT_PATH;
    }

    /**
     * must be careful and only allow access to domain if domain admin.
     */
    @Override
    public boolean domainAuthSufficient(Map<String, Object> context) {
        return true;
    }

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {

        ZmailSoapContext zsc = getZmailSoapContext(context);

        String action = request.getAttribute(MailConstants.E_ACTION);

        Element mreq = request.getElement(AdminConstants.E_MAILBOX);
        String accountId = mreq.getAttribute(AdminConstants.A_ACCOUNTID);

        Provisioning prov = Provisioning.getInstance();
        Account account = prov.get(AccountBy.id, accountId, zsc.getAuthToken());
        if (account == null) {
            throw AccountServiceException.NO_SUCH_ACCOUNT(accountId);
        }

        if (account.isCalendarResource()) {
            // need a CalendarResource instance for RightChecker
            CalendarResource resource = prov.get(Key.CalendarResourceBy.id, account.getId());
            checkCalendarResourceRight(zsc, resource, Admin.R_reindexCalendarResourceMailbox);
        } else {
            checkAccountRight(zsc, account, Admin.R_reindexMailbox);
        }

        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(account, false);
        if (mbox == null) {
            throw ServiceException.FAILURE("mailbox not found for account " + accountId, null);
        }

        Element response = zsc.createElement(AdminConstants.REINDEX_RESPONSE);

        if (ACTION_START.equalsIgnoreCase(action)) {
            if (mbox.index.isReIndexInProgress()) {
                response.addAttribute(AdminConstants.A_STATUS, STATUS_RUNNING);
            } else {
                String typesStr = mreq.getAttribute(MailConstants.A_SEARCH_TYPES, null);
                String idsStr = mreq.getAttribute(MailConstants.A_IDS, null);

                if (typesStr != null && idsStr != null) {
                    ServiceException.INVALID_REQUEST("Can't specify both 'types' and 'ids'", null);
                }

                if (typesStr != null) {
                    Set<MailItem.Type> types;
                    try {
                        types = MailItem.Type.setOf(typesStr);
                    } catch (IllegalArgumentException e) {
                        throw MailServiceException.INVALID_TYPE(e.getMessage());
                    }
                    mbox.index.startReIndexByType(types);
                } else if (idsStr != null) {
                    Set<Integer> ids = new HashSet<Integer>();
                    for (String id : Splitter.on(',').trimResults().split(idsStr)) {
                        try {
                            ids.add(Integer.parseInt(id));
                        } catch (NumberFormatException e) {
                            ServiceException.INVALID_REQUEST("invalid item ID: " + id, e);
                        }
                    }
                    mbox.index.startReIndexById(ids);
                } else {
                    mbox.index.startReIndex();
                }

                response.addAttribute(AdminConstants.A_STATUS, STATUS_STARTED);
            }
        } else if (ACTION_STATUS.equalsIgnoreCase(action)) {
            MailboxIndex.ReIndexStatus status = mbox.index.getReIndexStatus();
            if (status != null) {
                addProgressInfo(response, status);
                response.addAttribute(AdminConstants.A_STATUS, STATUS_RUNNING);
            } else {
                response.addAttribute(AdminConstants.A_STATUS, STATUS_IDLE);
            }
        } else if (ACTION_CANCEL.equalsIgnoreCase(action)) {
            MailboxIndex.ReIndexStatus status = mbox.index.cancelReIndex();
            if (status != null) {
                response.addAttribute(AdminConstants.A_STATUS, STATUS_CANCELLED);
                addProgressInfo(response, status);
            } else {
                response.addAttribute(AdminConstants.A_STATUS, STATUS_IDLE);
            }
        } else {
            throw ServiceException.INVALID_REQUEST("Unknown action: " + action, null);
        }

        return response;
    }

    private void addProgressInfo(Element response, MailboxIndex.ReIndexStatus status) {
        Element prog = response.addElement(AdminConstants.E_PROGRESS);
        prog.addAttribute(AdminConstants.A_NUM_SUCCEEDED, status.getProcessed() - status.getFailed());
        prog.addAttribute(AdminConstants.A_NUM_FAILED, status.getFailed());
        prog.addAttribute(AdminConstants.A_NUM_REMAINING, status.getTotal() - status.getProcessed());
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_reindexMailbox);
        relatedRights.add(Admin.R_reindexCalendarResourceMailbox);
    }
}
