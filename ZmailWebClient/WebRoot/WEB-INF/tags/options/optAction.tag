<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
<%@ attribute name="selected" rtexprvalue="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="org.zmail.i18n" %>
<%@ taglib prefix="zm" uri="org.zmail.zm" %>
<%@ taglib prefix="app" uri="org.zmail.htmlclient" %>

<app:handleError>
<zm:getMailbox var="mailbox"/>
<zm:modifyPrefs var="updated">

    <c:choose>
        <%-- GENERAL --%>
        <c:when test="${selected eq 'general'}">
            <zm:pref name="zmailPrefIncludeSpamInSearch" value="${param.zmailPrefIncludeSpamInSearch eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefIncludeTrashInSearch" value="${param.zmailPrefIncludeTrashInSearch eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefShowSearchString" value="${param.zmailPrefShowSearchString eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefClientType" value="${param.zmailPrefClientType}"/>
            <c:if test="${mailbox.features.skinChange}">
                <zm:pref name="zmailPrefSkin" value="${param.zmailPrefSkin}"/>
            </c:if>
            <zm:pref name="zmailPrefTimeZoneId" value="${param.zmailPrefTimeZoneId}"/>
            <zm:pref name="zmailPrefDefaultPrintFontSize" value="${param.zmailPrefDefaultPrintFontSize}"/>
        </c:when>
        <%-- MAIL --%>
        <c:when test="${selected eq 'mail'}">
            <c:if test="${mailbox.features.conversations and not empty param.zmailPrefGroupMailBy}">
                <zm:pref name="zmailPrefGroupMailBy" value="${param.zmailPrefGroupMailBy}"/>
            </c:if>
            <zm:pref name="zmailPrefMailItemsPerPage" value="${param.zmailPrefMailItemsPerPage}"/>
            <zm:pref name="zmailPrefShowFragments" value="${param.zmailPrefShowFragments eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefReadingPaneLocation" value="${param.zmailPrefReadingPaneLocation}"/>
            <zm:pref name="zmailPrefReadingPaneEnabled" value="${param.zmailPrefReadingPaneEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <c:if test="${mailbox.features.initialSearchPreference}">
                <zm:pref name="zmailPrefMailInitialSearch" value="${param.zmailPrefMailInitialSearch}"/>
            </c:if>

            <c:if test="${mailbox.features.outOfOfficeReply}">
                <zm:pref name="zmailPrefOutOfOfficeReplyEnabled" value="${param.zmailPrefOutOfOfficeReplyEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
                <zm:pref name="zmailPrefOutOfOfficeReply" value="${param.zmailPrefOutOfOfficeReply}"/>
	            <fmt:message key="CAL_APPT_EDIT_DATE_FORMAT" var="editDateFmt"/>
                <c:choose>
		            <c:when test="${param.zmailPrefOutOfOfficeReplyEnabled eq 'TRUE'}">
			            <c:set var="fromDate" value="${param.zmailPrefOutOfOfficeFromDate}" />
			            <c:set var="untilDate" value="${param.zmailPrefOutOfOfficeUntilDate}" />
			            <c:if test="${not empty fromDate}">
				            <c:catch var="parseError">
					            <fmt:parseDate pattern="${editDateFmt}" value="${fromDate}" var="parsedDate"  />
					            <fmt:formatDate value="${parsedDate}" pattern="yyyyMMddHHmmss'Z'" var="fmtDate" />
				            </c:catch>
				            <c:if test="${not empty parseError}">
					            <c:set var="fmtDate" value=""/>
				            </c:if>
				            <zm:pref name="zmailPrefOutOfOfficeFromDate" value="${fmtDate}"/>
			            </c:if>
			            <c:if test="${not empty untilDate}">
				            <c:catch var="parseError">
					            <fmt:parseDate pattern="${editDateFmt}" value="${untilDate}" var="parsedDate"  />
					            <fmt:formatDate value="${parsedDate}" pattern="yyyyMMddHHmmss'Z'" var="fmtDate" />
				            </c:catch>
				            <c:if test="${not empty parseError}">
					            <c:set var="fmtDate" value=""/>
				            </c:if>
				            <zm:pref name="zmailPrefOutOfOfficeUntilDate" value="${fmtDate}"/>
			            </c:if>
		            </c:when>
		            <c:otherwise>
			            <zm:pref name="zmailPrefOutOfOfficeFromDate" value=""/>
			            <zm:pref name="zmailPrefOutOfOfficeUntilDate" value=""/>
		            </c:otherwise>
	            </c:choose>
            </c:if>

            <c:if test="${mailbox.features.newMailNotification}">
                <zm:pref name="zmailPrefNewMailNotificationEnabled" value="${param.zmailPrefNewMailNotificationEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
                <zm:pref name="zmailPrefNewMailNotificationAddress" value="${param.zmailPrefNewMailNotificationAddress}"/>
            </c:if>

            <c:if test="${mailbox.features.mailForwarding}">
                <zm:pref name="zmailPrefMailForwardingAddress" value="${param.FORWARDCHECKED eq 'TRUE' ? param.zmailPrefMailForwardingAddress : ''}"/>
                <zm:pref name="zmailPrefMailLocalDeliveryDisabled" value="${param.zmailPrefMailLocalDeliveryDisabled eq 'TRUE' and param.FORWARDCHECKED eq 'TRUE' and not empty param.zmailPrefMailForwardingAddress ? 'TRUE' : 'FALSE'}"/>
            </c:if>

            <zm:pref name="zmailPrefMessageViewHtmlPreferred" value="${param.zmailPrefMessageViewHtmlPreferred eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefDedupeMessagesSentToSelf" value="${param.zmailPrefDedupeMessagesSentToSelf}"/>
			<c:if test="${mailbox.features.pop3Enabled}">
				<zm:pref name="zmailPrefPop3DownloadSince" value="${param.zmailPrefPop3DownloadSince}" />
			</c:if>
            <%-- for velodrome --%>
            <zm:pref name="zmailPrefInboxUnreadLifetime" value="${param.zmailPrefInboxUnreadLifetime}"/>
            <zm:pref name="zmailPrefInboxReadLifetime" value="${param.zmailPrefInboxReadLifetime}"/>
            <zm:pref name="zmailPrefSentLifetime" value="${param.zmailPrefSentLifetime}"/>
            <zm:pref name="zmailPrefJunkLifetime" value="${param.zmailPrefSpamLifetime}"/>
            <zm:pref name="zmailPrefTrashLifetime" value="${param.zmailPrefTrashLifetime}"/>
        </c:when>
        <%-- COMPOSING --%>
        <c:when test="${selected eq 'composing'}">
            <zm:pref name="zmailPrefHtmlEditorDefaultFontFamily" value="${param.zmailPrefHtmlEditorDefaultFontFamily}"/>
            <zm:pref name="zmailPrefHtmlEditorDefaultFontSize" value="${param.zmailPrefHtmlEditorDefaultFontSize}"/>
            <zm:pref name="zmailPrefHtmlEditorDefaultFontColor" value="${param.zmailPrefHtmlEditorDefaultFontColor}"/>
            <zm:pref name="zmailPrefComposeFormat" value="${param.zmailPrefComposeFormat}"/>
            <zm:pref name="zmailPrefForwardReplyInOriginalFormat" value="${param.zmailPrefForwardReplyInOriginalFormat eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefReplyIncludeOriginalText" value="${param.zmailPrefReplyIncludeOriginalText}"/>
            <zm:pref name="zmailPrefForwardIncludeOriginalText" value="${param.zmailPrefForwardIncludeOriginalText}"/>
            <zm:pref name="zmailPrefForwardReplyPrefixChar" value="${param.zmailPrefForwardReplyPrefixChar}"/>
            <zm:pref name="zmailPrefSaveToSent" value="${param.zmailPrefSaveToSent eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
        </c:when>
        <%-- SIGNATURES --%>
        <c:when test="${selected eq 'signatures'}">
            <zm:pref name="zmailPrefMailSignatureStyle" value="${param.zmailPrefMailSignatureStyle}"/>
            <zm:pref name="zmailPrefMailSignatureEnabled" value="${param.zmailPrefMailSignatureEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
        </c:when>
        <%-- ACCOUNTS --%>
        <c:when test="${selected eq 'accounts'}">
            <zm:pref name="zmailPrefIdentityName" value="${param.zmailPrefIdentityName}"/>
            <zm:pref name="zmailPrefFromDisplay" value="${param.zmailPrefFromDisplay}"/>
            <zm:pref name="zmailPrefFromAddress" value="${param.zmailPrefFromAddress}"/>
            <zm:pref name="zmailPrefReplyToDisplay" value="${param.zmailPrefReplyToDisplay}"/>
            <zm:pref name="zmailPrefReplyToAddress" value="${param.zmailPrefReplyToAddress}"/>
            <zm:pref name="zmailPrefReplyToEnabled" value="${param.zmailPrefReplyToEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefDefaultSignatureId" value="${param.zmailPrefDefaultSignatureId}"/>            
        </c:when>
        <%-- ADDRESS BOOK --%>
        <c:when test="${selected eq 'addressbook'}">
            <zm:pref name="zmailPrefAutoAddAddressEnabled" value="${param.zmailPrefAutoAddAddressEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefContactsPerPage" value="${param.zmailPrefContactsPerPage}"/>
        </c:when>
        <%-- CALENDAR --%>
        <c:when test="${selected eq 'calendar'}">
            <zm:pref name="zmailPrefUseTimeZoneListInCalendar" value="${param.zmailPrefUseTimeZoneListInCalendar eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefCalendarInitialView" value="${param.zmailPrefCalendarInitialView}"/>
            <zm:pref name="zmailPrefCalendarFirstdayOfWeek" value="${param.zmailPrefCalendarFirstdayOfWeek}"/>
            <zm:pref name="zmailPrefCalendarDayHourStart" value="${param.zmailPrefCalendarDayHourStart}"/>
            <zm:pref name="zmailPrefCalendarDayHourEnd" value="${param.zmailPrefCalendarDayHourEnd}"/>
            <zm:pref name="zmailPrefAppleIcalDelegationEnabled" value="${param.zmailPrefAppleIcalDelegationEnabled eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <zm:pref name="zmailPrefCalendarShowDeclinedMeetings" value="${param.zmailPrefCalendarShowDeclinedMeetings eq 'TRUE' ? 'TRUE' : 'FALSE'}"/>
            <c:set var="selectedDays" scope="request">${param.sun eq 'TRUE' ? 'Y' : 'N'},${param.mon eq 'TRUE' ? 'Y' : 'N'},${param.tue eq 'TRUE' ? 'Y' : 'N'},${param.wed eq 'TRUE' ? 'Y' : 'N'},${param.thu eq 'TRUE' ? 'Y' : 'N'},${param.fri eq 'TRUE' ? 'Y' : 'N'},${param.sat eq 'TRUE' ? 'Y' : 'N'}</c:set>
            <c:set var="workWeekPref" value="${mailbox.prefs.calendarWorkingHours}"/>
            <%--
                Override the days in the calendar working hours preference to the work days that has been selected by the user.
                Keep the working hours intact.
            --%>
            <c:set var="adjustedWorkWeekPref" value="${zm:generateWorkWeek(workWeekPref, selectedDays)}"/>
            <zm:pref name="zmailPrefCalendarWorkingHours" value="${adjustedWorkWeekPref}"/>
        </c:when>
    </c:choose>
</zm:modifyPrefs>

<c:if test="${selected eq 'calendar'}">
    <c:set var="startHour" value="${zm:cookInt(param.zmailPrefCalendarDayHourStart, 0)}"/>
    <c:set var="endHour" value="${zm:cookInt(param.zmailPrefCalendarDayHourEnd, 0)}"/>
    <c:if test="${startHour gt endHour}">
        <c:set var="calendarHoursWarning" value="${true}" scope="request"/>
        <app:status style="Warning"><fmt:message key="calendarHoursInvalid"/></app:status>
    </c:if>
</c:if>
    
<c:if test="${selected eq 'signatures'}">
    <c:forEach var="i" begin="0" end="${param.numSignatures}">
        <c:set var="origSignatureNameKey" value="origSignatureName${i}"/>
        <c:set var="signatureNameKey" value="signatureName${i}"/>
        <c:set var="origSignatureValueKey" value="origSignatureValue${i}"/>
        <c:set var="signatureValueKey" value="signatureValue${i}"/>
        <c:set var="signatureTypeKey" value="signatureType${i}"/>
        <c:if test="${(param[origSignatureNameKey] ne param[signatureNameKey]) or
                (param[origSignatureValueKey] ne param[signtureValueKey])}">
            <c:set var="modSignatureWarning" value="${true}" scope="request"/>
            <c:choose>
                <c:when test="${empty param[signatureNameKey]}">
                    <app:status style="Warning"><fmt:message key="optionsNoSignatureName"/></app:status>
                </c:when>
                <c:when test="${empty param[signatureValueKey]}">
                    <app:status style="Warning"><fmt:message key="optionsNoSignatureValue"/></app:status>
                </c:when>
                <c:otherwise>
                    <c:set var="signatureIdKey" value="signatureId${i}"/>
                    <zm:modifySiganture id="${param[signatureIdKey]}"
                                        name="${param[signatureNameKey]}" value="${param[signatureValueKey]}" type="${param[signatureTypeKey]}"/>
                    <c:set var="signatureUpdated" value="${true}"/>
                    <c:set var="modSignatureWarning" value="${false}" scope="request"/>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
</c:if>

<c:if test="${selected eq 'signatures' and not empty param.newSignature}">
    <c:set var="newSignatureWarning" value="${true}" scope="request"/>
    <c:choose>
        <c:when test="${empty param.newSignatureName}">
            <app:status style="Warning"><fmt:message key="optionsNoSignatureName"/></app:status>
        </c:when>
        <c:when test="${empty param.newSignatureValue}">
            <app:status style="Warning"><fmt:message key="optionsNoSignatureValue"/></app:status>
        </c:when>
        <c:otherwise>
            <zm:createSiganture var="sigId" name="${param.newSignatureName}" value="${param.newSignatureValue}" type="${param.newSignatureType}"/>
            <c:set var="updated" value="${true}"/>
            <c:set var="newSignatureWarning" value="${false}" scope="request"/>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${mailbox.features.skinChange and updated}">
    <c:remove var="skin" scope="session"/> <%-- remove old var so that new skin gets applied using skin.tag --%>    
</c:if>
<c:choose>
    <c:when test="${newSignatureWarning or modSignatureWarning or calendarHoursWarning}">
        <%-- do nothing --%>
    </c:when>
    <c:when test="${updated or signatureUpdated}">
        <zm:getMailbox var="mailbox" refreshaccount="${true}"/>
        <app:status><fmt:message key="optionsSaved"/></app:status>
    </c:when>
    <c:otherwise>
        <app:status><fmt:message key="noOptionsChanged"/></app:status>        
    </c:otherwise>
</c:choose>
</app:handleError>