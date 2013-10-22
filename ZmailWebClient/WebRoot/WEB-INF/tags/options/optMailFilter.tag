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

<app:handleError>

    <c:set var="rules" value="${empty param.ruleName ? mailbox.filterRulesReload : mailbox.filterRules}"/>
    <c:forEach items="${rules}" var="rule" varStatus="status">
        <%--
            If a filter has only "Discard" action and zimbraFeatureDiscardInFiltersEnabled is set to false,
            do not list the filter at all.
        --%>
        <c:if test="${(empty param.ruleName or (rule.name eq param.ruleName)) and
                                            (mailbox.features.discardFilterEnabled eq true or
                                            (mailbox.features.discardFilterEnabled eq false and not zm:isDiscardActionFilter(rule)))}">
            <c:set var="selectedRule" value="${rule}"/>
        </c:if>
    </c:forEach>

    <c:if test="${not empty param.frompost}">
        <app:constructRule var="postRule"/>
    </c:if>
    
    <c:choose>
        <c:when test="${zm:actionSet(param, 'actionFilterSave') and requestScope.filterSave eq 'success'}">

        </c:when>
        <c:when test="${zm:actionSet(param, 'actionFilterCancel')}">
            
        </c:when>
        <c:when test="${zm:actionSet(param, 'actionNewFilter')}">
            <c:set var="newRule" value="${not empty postRule ? postRule : null}"/>
        </c:when>
        <c:when test="${zm:actionSet(param, 'actionEditFilter')}">
            <c:set var="editRule" value="${not empty postRule ? postRule : selectedRule}"/>
        </c:when>
    </c:choose>
</app:handleError>
<table width="100%">
<tr>
<td>
<table class="ZOptionsSectionTable" width="100%">
    <tr class="ZOptionsHeaderRow">
        <td class="ImgPrefsHeader_L">&nbsp;</td>
        <td class='ZOptionsHeader ImgPrefsHeader' >
            <fmt:message key="mailFilters"/>
        </td>
        <td class="ImgPrefsHeader_R">&nbsp;</td>
    </tr>
</table>
<table width="100%" class="ZOptionsSectionMain" cellspacing="6">
    <tr>
        <c:choose>
            <c:when test="${zm:actionSet(param, 'actionNewFilter') and (not zm:actionSet(param, 'actionFilterCancel') and requestScope.filterSave ne 'success')}">
                <td valign='top'>
                    <app:editRule rule="${newRule}" mailbox="${mailbox}"/>
                </td>
            </c:when>
            <c:when test="${zm:actionSet(param, 'actionEditFilter') and not empty editRule and (not zm:actionSet(param, 'actionFilterCancel') and requestScope.filterSave ne 'success')}">
                <td valign='top'>
                    <app:editRule rule="${editRule}" mailbox="${mailbox}"/>
                </td>
            </c:when>
            <c:otherwise>
                <td width="200" class='List' valign='top'>
                    <table width="100%">
                        <tr>
                            <th width="1%" nowrap>&nbsp;</th>
                            <th width="1%" nowrap><fmt:message key="active"/>
                            <th width="1%" nowrap>&nbsp;</th>
                            <th nowrap><fmt:message key="filterName"/>
                        </tr>
                        <c:forEach items="${rules}" var="rule" varStatus="status">
                            <c:if test="${mailbox.features.discardFilterEnabled eq true or
                                                 (mailbox.features.discardFilterEnabled eq false and not zm:isDiscardActionFilter(rule))}">
                            <tr <c:if test="${selectedRule.name eq rule.name}">class='RowSelected'</c:if> >
                                <td width="1%" nowrap>&nbsp;
                                    <c:if test="${selectedRule.name eq rule.name}">
                                        <input type="hidden" name="ruleName" value="${fn:escapeXml(rule.name)}"/>
                                    </c:if>
                                </td>
                                <td width="1%" nowrap>
                                    <c:if test="${rule.active}">
                                        <app:img altkey="active" src="common/ImgCheck.png"/>
                                    </c:if>
                                </td>
                                <td width="1%" nowrap>&nbsp;</td>
                                <td>
                                    <c:url var="selectRuleUrl" value="/h/options?selected=filter">
                                        <c:param name="ruleName" value="${rule.name}"/>
                                    </c:url>
                                    <a href="${fn:escapeXml(selectRuleUrl)}">
                                            ${fn:escapeXml(rule.name)}
                                    </a>
                                </td>
                            </tr>
                            </c:if>
                        </c:forEach>
                        <c:if test="${empty rules}">
                            <tr>
                                <td colspan="4">
                                    <div class='NoResults'>
                                        <fmt:message key="noFilterRules"/>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </td>
                <td class='ZhDisplayRuleContent' valign='top'>
                    <c:if test="${not empty selectedRule}">
                        <app:displayRule rule="${selectedRule}" mailbox="${mailbox}"/>
                    </c:if>
                </td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>
</td>
</tr>
</table>

