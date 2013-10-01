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
<%@ attribute name="context" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.tag.SearchContext"%>
<%@ attribute name="keys" rtexprvalue="true" required="true" %>
<%@ attribute name="title" rtexprvalue="true" required="false" %>
<%@ attribute name="today" rtexprvalue="true" required="true" type="java.util.Calendar"%>
<%@ attribute name="date" rtexprvalue="true" required="true" type="java.util.Calendar"%>
<%@ attribute name="timezone" rtexprvalue="true" required="true" type="java.util.TimeZone"%>
<%@ attribute name="nextDate" rtexprvalue="true" required="false" type="java.util.Calendar"%>
<%@ attribute name="prevDate" rtexprvalue="true" required="false" type="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>

<zm:getMailbox var="mailbox"/>

<table width="100%" cellspacing="0" class='Tb'>
    <tr>
        <td align="left" class=TbBt id="caltb">
            <table cellpadding="0" cellspacing="0">
                <tr valign="middle">
                    <app:calendarUrl var="refreshUrl" refresh="1"/>
                    <td height="100%">
                        <a href="${fn:escapeXml(refreshUrl)}" <c:if test="${keys}"> id="CAL_REFRESH"</c:if>><app:img altkey="ALT_CAL_REFRESH" src="startup/ImgRefresh.png"/><span style='padding-left:5px'><fmt:message key="refresh"/></span></a>
                    </td>
                    <td>
                        <div class='vertSep'></div>
                    </td>
                    <fmt:formatDate var="dateDf" value="${zm:getCurrentTime(timezone).time}" pattern="yyyyMMdd'T'HHmmss" timeZone="${timezone}"/>
                    <app:calendarUrl var="newApptUrl" date="${dateDf}" action="edit"/>
                    <app:calendarUrl var="dayViewUrl" date="${dateDf}" view="day"/>
                    <app:calendarUrl var="weekViewUrl" date="${dateDf}" view="week"/>
                    <app:calendarUrl var="workWeekViewUrl" date="${dateDf}" view="workWeek"/>
                    <app:calendarUrl var="monthViewUrl" date="${dateDf}" view="month"/>
                    <app:calendarUrl var="listViewUrl" date="${dateDf}" view="list"/>
                    <app:calendarUrl var="scheduleViewUrl" date="${dateDf}" view="schedule"/>
                    <td height="100%">
                        <a id="CAL_NEWAPPT" href="${fn:escapeXml(newApptUrl)}"><app:img altkey="ALT_CAL_NEW_APPT" src="calendar/ImgNewAppointment.png"/><span style='padding-left:5px'><fmt:message key="new"/></span></a>
                    </td>
                    <td height="100%"><div class='vertSep'></div></td>
                    <td height="100%">
                        <a id="CAL_DAY" href="${fn:escapeXml(dayViewUrl)}"><app:img altkey="ALT_CAL_DAY_VIEW" src="calendar/ImgDayView.png"/><span style='padding-left:5px'><fmt:message key="day"/></span></a>
                    </td>
                    <td height="100%">
                        <a id="CAL_WORK" href="${fn:escapeXml(workWeekViewUrl)}"><app:img altkey="ALT_CAL_WORKWEEK_VIEW" src="calendar/ImgWorkWeekView.png"/><span style='padding-left:5px'><fmt:message key="workWeek"/></span></a>
                    </td>
                    <td height="100%">
                        <a id="CAL_WEEK" href="${fn:escapeXml(weekViewUrl)}"><app:img altkey="ALT_CAL_WEEK_VIEW" src="calendar/ImgWeekView.png"/><span style='padding-left:5px'><fmt:message key="week"/></span></a>
                    </td>
                    <td height="100%">
                        <a id="CAL_MONTH" href="${fn:escapeXml(monthViewUrl)}"><app:img altkey="ALT_CAL_MONTH_VIEW" src="calendar/ImgMonthView.png"/><span style='padding-left:5px'><fmt:message key="month"/></span></a>
                    </td>
                    <td height="100%">
                        <a id="CAL_LIST" href="${fn:escapeXml(listViewUrl)}"><app:img altkey="ALT_CAL_LIST_VIEW" src="deprecated/ImgListView.gif"/><span style='padding-left:5px'><fmt:message key="list"/></span></a>
                    </td>
                    <td height="100%">
                        <a id="CAL_SCHED" href="${fn:escapeXml(scheduleViewUrl)}"><app:img altkey="ALT_CAL_SCHEDULE_VIEW" src="calendar/ImgGroupSchedule.png"/><span style='padding-left:5px'><fmt:message key="schedule"/></span></a>
                    </td>
                    <td height="100%"><div class='vertSep'></div></td>
                    <app:calendarUrl var="todayUrl" nodate="true"/>
                    <td height="100%">
                        <a id="CAL_TODAY" href="${fn:escapeXml(todayUrl)}"><app:img altkey="ALT_CAL_TODAY" src="calendar/ImgDate.png"/><span style='padding-left:5px'><fmt:message key="today"/></span></a>
                    </td>
                    <td height="100%"><div class='vertSep'></div></td>
                    <td height="100%" nowrap valign="middle" style="padding: 0 2px 0 2px">
                        <td height="100%" nowrap valign="middle" style="padding: 0 2px 0 2px">
                        <input onclick="zprint();return false;" id="${keys ? 'IOPPRINT' : ''}" name="actionPrint" type="image" src="<app:imgurl value='startup/ImgPrint.png'/>" alt='<fmt:message key="actionPrint" />' title='<fmt:message key="actionPrint" />' />
                    </td>
                    <td height="100%" nowrap valign="middle" style="padding: 0 2px 0 2px">
                        <input onclick="zprint();return false;" id="${keys ? 'SOPPRINT' : ''}" name="actionPrint" type="submit" value='<fmt:message key="actionPrint" />' title='<fmt:message key="actionPrint" />' />
                    </td>
                </tr>
            </table>
        </td>
        <c:if test="${not empty prevDate and not empty nextDate and not empty title}">
        <td align=right>
            <app:calendarUrl var="prevUrl" rawdate="${prevDate}" timezone="${timezone}"/>
            <app:calendarUrl var="nextUrl" rawdate="${nextDate}" timezone="${timezone}"/>
            <table cellspacing=5 cellpadding=0>
                <tr>
                    <td>
            <a <c:if test="${keys}">id="PREV_PAGE"</c:if> href="${fn:escapeXml(prevUrl)}"><app:img altkey="ALT_PAGE_PREVIOUS" src="startup/ImgLeftArrow.png" border="0"/></a>
                    </td>
                    <td class='ZhCalPager'>
            ${fn:escapeXml(title)}
                    </td>
                    <td>
            <a <c:if test="${keys}">id="NEXT_PAGE"</c:if> href="${fn:escapeXml(nextUrl)}"><app:img altkey="ALT_PAGE_NEXT" src="startup/ImgRightArrow.png" border="0"/></a>
                    </td>
                </tr>
            </table>
        </td>
        </c:if>
    </tr>
</table>
