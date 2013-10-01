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
<%@ attribute name="folder" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.bean.ZFolderBean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>

<c:set var="label" value="${zm:getFolderName(pageContext, folder.id)}"/>
<c:set var="truncatedLabel" value="${zm:getTruncatedFolderName(pageContext, folder.id, 17, true)}"/>
<fmt:message var="colorMsg" key="${folder.rgbColorMsg}"/>
<c:set var="color" value="${zm:lightenColor(not empty folder.rgb ? folder.rgb : colorMsg)}"/>
<tr style="background-color:${color}">
    <td nowrap colspan="2" class='Folder<c:if test="${folder.hasUnread}"> Unread</c:if><c:if test="${folder.id eq requestScope.context.selectedId}"> Selected</c:if>'
        style='padding-left: ${folder.depth*8}px'>
        <c:choose>
            <c:when test="${folder.isCheckedInUI}"><app:calendarUrl var="url" uncheck="${folder.id}"/></c:when>
            <c:otherwise><app:calendarUrl var="url" check="${folder.id}"/></c:otherwise>
        </c:choose>
        <%--<span style='width:20px'><c:if test="${folder.hasChildren}"><app:img src="startup/ImgNodeExpanded.gif"/></c:if></span>--%>
        <a href='${fn:escapeXml(url)}'>
            <c:choose>
            <c:when test="${folder.isCheckedInUI}">
                <app:img altkey="checked" src="tasks/ImgTask.png"/>
            </c:when>
                <c:otherwise>
                    <app:img altkey="unchecked" src="startup/ImgCheckboxUnchecked.png"/>
                </c:otherwise>
            </c:choose>
            <app:img src="${folder.image}" alt='${label}'/>
            <c:choose>
                <c:when test="${folder.isMountPoint and folder.effectivePerm == null}">
                    <del>${truncatedLabel}</del>
                </c:when>
                <c:otherwise>${truncatedLabel}</c:otherwise>
            </c:choose>
        </a>

    </td>
    <td width="1%" align="center" style='padding:0;'>
        <c:choose>
            <c:when test="${not empty folder.remoteURL}">
                <app:calendarUrl var="syncUrl" sync="${folder.id}"/>
                <fmt:message key="reloadCalendar" var="reload"/>
                <a href="${fn:escapeXml(syncUrl)}"><app:img src="startup/ImgRefresh.png" title="${reload}"/></a>
            </c:when>
            <c:otherwise>
                &nbsp;
            </c:otherwise>
        </c:choose>
    </td>
</tr>


