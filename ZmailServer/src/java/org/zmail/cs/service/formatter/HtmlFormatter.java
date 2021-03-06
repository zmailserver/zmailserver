/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.service.formatter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.httpclient.Header;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Pair;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.account.GuestAccount;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.Mountpoint;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.service.UserServlet;
import org.zmail.cs.service.UserServletContext;
import org.zmail.cs.service.UserServletException;
import org.zmail.cs.service.UserServlet.HttpInputStream;
import org.zmail.cs.service.formatter.FormatterFactory.FormatType;

public class HtmlFormatter extends Formatter {

    private static final String PATH_MAIN_CONTEXT  = "/zmail";
    private static final String PATH_JSP_REST_PAGE = "/h/rest";
    private static final long   AUTH_EXPIRATION = 60L * 60L * 1000L;

    private static final String ATTR_REQUEST_URI         = "zmail_request_uri";

    private static final String ATTR_INTERNAL_DISPATCH   = "zmail_internal_dispatch";
    private static final String ATTR_AUTH_TOKEN          = "zmail_authToken";
    private static final String ATTR_TARGET_ACCOUNT_NAME = "zmail_target_account_name";
    private static final String ATTR_TARGET_ACCOUNT_ID   = "zmail_target_account_id";
    private static final String ATTR_TARGET_ITEM_ID      = "zmail_target_item_id";
    private static final String ATTR_TARGET_ITEM_TYPE    = "zmail_target_item_type";
    private static final String ATTR_TARGET_ITEM_COLOR   = "zmail_target_item_color";
    private static final String ATTR_TARGET_ITEM_VIEW    = "zmail_target_item_view";
    private static final String ATTR_TARGET_ITEM_PATH    = "zmail_target_item_path";
    private static final String ATTR_TARGET_ITEM_NAME    = "zmail_target_item_name";

    private static final String ATTR_TARGET_ACCOUNT_PREF_TIME_ZONE   = "zmail_target_account_prefTimeZoneId";
    private static final String ATTR_TARGET_ACCOUNT_PREF_SKIN   = "zmail_target_account_prefSkin";
    private static final String ATTR_TARGET_ACCOUNT_PREF_LOCALE   = "zmail_target_account_prefLocale";
    private static final String ATTR_TARGET_ACCOUNT_PREF_CALENDAR_FIRST_DAY_OF_WEEK   = "zmail_target_account_prefCalendarFirstDayOfWeek";
    private static final String ATTR_TARGET_ACCOUNT_PREF_CALENDAR_DAY_HOUR_START   = "zmail_target_account_prefCalendarDayHourStart";
    private static final String ATTR_TARGET_ACCOUNT_PREF_CALENDAR_DAY_HOUR_END  = "zmail_target_account_prefCalendarDayHourEnd";

    @Override
    public void formatCallback(UserServletContext context) throws UserServletException,
            ServiceException, IOException, ServletException {
        dispatchJspRest(context.getServlet(), context);
    }

    @Override
    public FormatType getType() {
        return FormatType.HTML;
    }

    static void dispatchJspRest(Servlet servlet, UserServletContext context)
            throws ServiceException, ServletException, IOException {
        AuthToken auth = null;
        long expiration = System.currentTimeMillis() + AUTH_EXPIRATION;
        if (context.basicAuthHappened) {
            Account acc = context.getAuthAccount();
            if (acc instanceof GuestAccount) {
                auth = AuthToken.getAuthToken(acc.getId(), acc.getName(), null, ((GuestAccount)acc).getDigest(), expiration);
            } else {
                auth = AuthProvider.getAuthToken(context.getAuthAccount(), expiration);
            }
        } else if (context.cookieAuthHappened) {
            auth = UserServlet.getAuthTokenFromCookie(context.req, context.resp, true);
        } else {
            auth = AuthToken.getAuthToken(GuestAccount.GUID_PUBLIC, null, null, null, expiration);
        }
        if (auth != null && context.targetAccount != null && context.targetAccount != context.getAuthAccount()) {
            auth.setProxyAuthToken(Provisioning.getInstance().getProxyAuthToken(context.targetAccount.getId(), null));
        }
        String authString = null;
        try {
            if (auth != null)
                authString = auth.getEncoded();
        } catch (AuthTokenException e) {
            throw new ServletException("error generating the authToken", e);
        }

        Account targetAccount = context.targetAccount;
        MailItem targetItem = context.target;
        String uri = (String) context.req.getAttribute("requestedPath");

        if (targetItem instanceof Mountpoint && ((Mountpoint)targetItem).getDefaultView() != MailItem.Type.APPOINTMENT) {
            Mountpoint mp = (Mountpoint) targetItem;
            Provisioning prov = Provisioning.getInstance();
            targetAccount = prov.getAccountById(mp.getOwnerId());
            Pair<Header[], HttpInputStream> remoteItem = UserServlet.getRemoteResourceAsStream((auth == null) ? null : auth.toZAuthToken(), mp.getTarget(), context.extraPath);
            remoteItem.getSecond().close();
            String remoteItemId = null;
            String remoteItemType = null;
            String remoteItemName = null;
            String remoteItemPath = null;
            for (Header h : remoteItem.getFirst())
                if (h.getName().compareToIgnoreCase("X-Zmail-ItemId") == 0)
                    remoteItemId = h.getValue();
                else if (h.getName().compareToIgnoreCase("X-Zmail-ItemType") == 0)
                    remoteItemType = h.getValue();
                else if (h.getName().compareToIgnoreCase("X-Zmail-ItemName") == 0)
                    remoteItemName = h.getValue();
                else if (h.getName().compareToIgnoreCase("X-Zmail-ItemPath") == 0)
                    remoteItemPath = h.getValue();

            context.req.setAttribute(ATTR_TARGET_ITEM_ID, remoteItemId);
            context.req.setAttribute(ATTR_TARGET_ITEM_TYPE, remoteItemType);
            context.req.setAttribute(ATTR_TARGET_ITEM_NAME, remoteItemName);
            context.req.setAttribute(ATTR_TARGET_ITEM_PATH, remoteItemPath);
            context.req.setAttribute(ATTR_TARGET_ITEM_COLOR, mp.getColor());
            context.req.setAttribute(ATTR_TARGET_ITEM_VIEW, mp.getDefaultView().toByte());
            targetItem = null;
        }

        context.req.setAttribute(ATTR_INTERNAL_DISPATCH, "yes");
        context.req.setAttribute(ATTR_REQUEST_URI, uri != null ? uri : context.req.getRequestURI());
        context.req.setAttribute(ATTR_AUTH_TOKEN, authString);
        if (targetAccount != null) {
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_NAME, targetAccount.getName());
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_ID, targetAccount.getId());
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_PREF_TIME_ZONE, targetAccount.getAttr(Provisioning.A_zmailPrefTimeZoneId));
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_PREF_SKIN, targetAccount.getAttr(Provisioning.A_zmailPrefSkin));
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_PREF_LOCALE, targetAccount.getAttr(Provisioning.A_zmailPrefLocale));
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_PREF_CALENDAR_FIRST_DAY_OF_WEEK, targetAccount.getAttr(Provisioning.A_zmailPrefCalendarFirstDayOfWeek));
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_PREF_CALENDAR_DAY_HOUR_START, targetAccount.getAttr(Provisioning.A_zmailPrefCalendarDayHourStart));
            context.req.setAttribute(ATTR_TARGET_ACCOUNT_PREF_CALENDAR_DAY_HOUR_END, targetAccount.getAttr(Provisioning.A_zmailPrefCalendarDayHourEnd));
        }
        if (targetItem != null) {
            context.req.setAttribute(ATTR_TARGET_ITEM_ID, targetItem.getId());
            context.req.setAttribute(ATTR_TARGET_ITEM_TYPE, targetItem.getType().toString());
            context.req.setAttribute(ATTR_TARGET_ITEM_PATH, targetItem.getPath());
            context.req.setAttribute(ATTR_TARGET_ITEM_NAME, targetItem.getName());

            context.req.setAttribute(ATTR_TARGET_ITEM_COLOR, targetItem.getColor());
            if (targetItem instanceof Folder) {
                context.req.setAttribute(ATTR_TARGET_ITEM_VIEW, ((Folder) targetItem).getDefaultView().toString());
            }
        }

        String mailUrl = PATH_MAIN_CONTEXT;
        try {
            mailUrl = Provisioning.getInstance().getLocalServer().getMailURL();
        } catch (Exception e) {
        }
        ServletContext targetContext = servlet.getServletConfig().getServletContext().getContext(mailUrl);
        RequestDispatcher dispatcher = targetContext.getRequestDispatcher(PATH_JSP_REST_PAGE);
        dispatcher.forward(context.req, context.resp);
    }
}
