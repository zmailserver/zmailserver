<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
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
--%>
<%@ tag body-content="empty" %>
<%@ attribute name="mailbox" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.bean.ZMailboxBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>

<zm:getUserAgent var="ua" session="true"/>
<c:set var="suffix" value="${ua.isOsWindows ? '.win' : ua.isOsMac ? '.mac' : ua.isOsLinux ? '.linux' :  ''}"/>
<fmt:bundle basename="/keys/ZhKeys">
    <fmt:message var="sections" key="sections"/>
    <table width="100%" class="ZPropertySheet" cellspacing="6">
        <tbody>
            <app:optShortcutSection section="global" suffix="${suffix}" mailbox="${mailbox}"/>
            <c:if test="${zm:isMailEnabled(mailbox)}">
            <app:optShortcutSection section="mail" suffix="${suffix}" mailbox="${mailbox}"/>
            <app:optShortcutSection section="compose" suffix="${suffix}" mailbox="${mailbox}"/>
            <c:if test="${mailbox.features.conversations}">
                <app:optShortcutSection section="conversation" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
            </c:if>
            <c:if test="${mailbox.features.voice}">
                <app:optShortcutSection section="voicemail" suffix="${suffix}" mailbox="${mailbox}"/>
                <app:optShortcutSection section="call" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
            <c:if test="${mailbox.features.contacts}">
                <app:optShortcutSection section="contacts" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
            <c:if test="${mailbox.features.calendar}">
                <app:optShortcutSection section="calendar" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
            <app:optShortcutSection section="list" suffix="${suffix}" mailbox="${mailbox}"/>
            <app:optShortcutSection section="mfolders" suffix="${suffix}" mailbox="${mailbox}"/>
            <c:if test="${mailbox.features.tagging}">
                <app:optShortcutSection section="mtags" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
            <c:if test="${mailbox.features.contacts}">
                <app:optShortcutSection section="maddrbooks" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
            <c:if test="${mailbox.features.calendar}">
                <app:optShortcutSection section="mcalendars" suffix="${suffix}" mailbox="${mailbox}"/>
            </c:if>
        </tbody>
    </table>
</fmt:bundle>