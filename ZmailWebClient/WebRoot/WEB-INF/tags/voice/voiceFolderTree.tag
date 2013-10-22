<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
<%@ attribute name="editmode" rtexprvalue="true" required="false" %>
<%@ attribute name="keys" rtexprvalue="true" required="true" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>

<zm:getMailbox var="mailbox"/>

<jsp:useBean id="expanded" scope="session" class="java.util.HashMap"/>
<c:set var="expanded" value="${sessionScope.expanded.contacts ne 'collapse'}"/>

<div class=Tree>
    <table width=100% cellpadding=0 cellspacing=0>
        <c:set var="firstAccount" value="true" scope="page"/>
        <zm:forEachPhoneAccount var="account">
            <c:if test="${account.phoneType eq 'DeskPhone'}">
                <fmt:message var="displayNameType" key="deskphone"/>
                <c:set var="displayName" value="${displayNameType} - ${account.phone.display}" scope="page"/>
            </c:if>
        </zm:forEachPhoneAccount>

        <zm:forEachPhoneAccount var="account">
            <c:set var="query" value="phone:${account.phone.name}"/>
            <c:if test="${account.hasVoiceMail}">
                <tr>
                    <c:url var="toggleUrl" value="/h/search">
                        <c:param name="st" value="voicemail"/>
                        <c:param name="sq" value="${query}"/>
                    </c:url>
                    <th class='Header'>
                        <a href="${toggleUrl}">
                            <c:choose>
                                <c:when test="${not empty displayName}">
                                    ${displayName}
                                </c:when>
                                <c:otherwise>
                                    ${account.phone.display}
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </th>
                </tr>

                <c:set var="expanded"
                       value="${(fn:indexOf(param.sq, query) ne -1) or ((fn:indexOf(param.sq, 'phone:') eq -1) and firstAccount eq 'true')}"/>
                <c:if test="${expanded}">
                    <app:doVoiceFolderTree parentfolder="${account.rootFolder}" skiproot="${true}" skipsystem="true"/>
                </c:if>
                <c:set var="firstAccount" value="false"/>
            </c:if>
        </zm:forEachPhoneAccount>
    </table>

</div>
