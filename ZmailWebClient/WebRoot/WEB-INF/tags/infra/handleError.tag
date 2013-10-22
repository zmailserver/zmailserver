<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
<%@ tag body-content="scriptless" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>

<c:catch var="actionException">
    <jsp:doBody/>
</c:catch>
<c:if test="${!empty actionException}">
    <zm:getException var="error" exception="${actionException}"/>
    <c:set var="lastErrorCode" value="${error.code}" scope="request"/>
    <c:choose>
        <c:when test="${error.code eq 'ztaglib.SERVER_REDIRECT'}">
            <c:redirect url="${not empty requestScope.SERVIER_REDIRECT_URL ? requestScope.SERVIER_REDIRECT_URL : '/'}"/>
        </c:when>
        <c:when test="${error.code eq 'service.AUTH_EXPIRED' or error.code eq 'service.AUTH_REQUIRED'}">
            <c:redirect url="/?loginOp=relogin&client=standard&loginErrorCode=${error.code}"/>
        </c:when>
        <c:when test="${error.code eq 'mail.QUERY_PARSE_ERROR' and empty param.sq}">
            <app:status style="Critical">
                <fmt:message key="emptySearchQuery"/>
            </app:status>
        </c:when>
        <c:when test="${error.code eq 'zclient.UPLOAD_SIZE_LIMIT_EXCEEDED'}">
            <zm:saveDraft var="draftResult" compose="${uploader.compose}" draftid="${uploader.compose.draftId}"/>
            <c:set scope="request" var="draftid" value="${draftResult.id}"/>
            <c:set var="statusClass" scope="request" value="StatusCritical"/>
            <c:set var="uploadError" scope="request" value="${true}"/>
            <fmt:message var="errorMsg" key="${error.code}"/>
            <c:set var="statusMessage" scope="request" value="${errorMsg}"/>
            <jsp:forward page="/h/compose"/>
        </c:when>
        <%-- TODO: handle voice errors in a separate tag like handleVoiceError --%>
        <c:when test="${error.code eq 'voice.UNABLE_TO_AUTH'}">
            <app:status style="Critical">
                <fmt:message key="voiceErrorUnableToAuth"/>
            </app:status>
        </c:when>
        <c:when test="${error.code eq 'voice.UNSUPPPORTED'}">
            <app:status style="Critical">
                <fmt:message key="voiceErrorUnsupported"/>
            </app:status>
        </c:when>
        <c:when test="${(error.code eq 'cisco.CISCO_ERROR') or (error.code eq 'mitel.MITEL_ERROR')}">
            <app:status style="Critical">
                <fmt:message key="voiceErrorGeneric"/>
            </app:status>
        </c:when>
        <c:otherwise>
            <app:status style="Critical">
                <fmt:message key="${error.code}"/>
            </app:status>
            <!-- ${fn:escapeXml(error.id)} -->
        </c:otherwise>
    </c:choose>
</c:if>
