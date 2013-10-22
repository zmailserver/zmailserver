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
<%@ attribute name="context" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.tag.SearchContext"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<app:handleError>
    <zm:getMailbox var="mailbox"/>
	<zm:checkVoiceStatus var="voiceStatus"/>
    <app:searchTitle var="title" context="${context}"/>

    <c:set var="useTo" value="${context.folder.isPlacedCalls}"/>
</app:handleError>
<app:view editmode="${voiceStatus eq 'false' ? 'true' : ''}" mailbox="${mailbox}" title="${title}" selected='voice' voice="true" folders="false" tags="false" searches="false" context="${context}" keys="true">
	<c:if test="${voiceStatus}">
		<zm:currentResultUrl var="currentUrl" value="/h/search" context="${context}"/>
		<table width=100% cellpadding="0" cellspacing="0">
			<tr>
				<td class='TbTop'>
					<app:callListViewToolbar context="${context}" keys="true"/>
				</td>
			</tr>
			<tr>
				<td class='List'>
						<table width=100% cellpadding=2 cellspacing=0>
							<tr class='Header'>
								<th width=50% nowrap><fmt:message key="${useTo ? 'to' : 'from'}"/>
								<th width=25% nowrap>
									<zm:newSortUrl var="durSortUrl" value="/h/search" context="${context}" sort="${(context.ss eq 'durDesc' or empty context.ss) ? 'durAsc' : 'durDesc'}"/>
									<a href="${durSortUrl}">
										<fmt:message key="duration"/>
									</a>
								</th>
								<th nowrap>
									<zm:newSortUrl var="dateSortUrl" value="/h/search" context="${context}" sort="${(context.ss eq 'dateDesc' or empty context.ss) ? 'dateAsc' : 'dateDesc'}"/>
									<a href="${dateSortUrl}">
										<fmt:message key="received"/>
									</a>
								</th>
							</tr>

							<c:forEach items="${context.searchResult.hits}" var="hit" varStatus="status">
							<tr>
								<td nowrap>${zm:getDisplayCaller(pageContext, useTo ? hit.callHit.recipient : hit.callHit.caller)}</td>
								<td nowrap>${fn:escapeXml(zm:displayDuration(pageContext, hit.callHit.duration))}</td>
								<td nowrap>${fn:escapeXml(zm:displayVoiceDate(pageContext, hit.callHit.date))}</td>
							</tr>
							</c:forEach>
						</table>
						<c:if test="${context.searchResult.size == 0}">
							<div class='NoResults'><fmt:message key="noResultsFound"/></div>
						</c:if>
				</td>
			</tr>
			<tr>
				<td class='TbBottom'>
					<app:callListViewToolbar context="${context}" keys="false"/>
				</td>
			</tr>
		</table>

		<SCRIPT TYPE="text/javascript">
			<!--
			var zprint = function() { var e = document.getElementById("OPPRINT"); window.open(e.href, e.target); }
			/*var zcallManager = function() { var e = document.getElementById("OPCALLMANAGER"); window.location = e.href; }*/
			//-->
		</SCRIPT>

		<app:keyboard cache="voice.callListView" globals="true" mailbox="${mailbox}" tags="false" folders="false">
			<zm:bindKey message="call.Print" func="zprint"/>
			<%--zm:bindKey message="call.CallManager" func="zcallManager"/--%>
		</app:keyboard>
	</c:if>
</app:view>
