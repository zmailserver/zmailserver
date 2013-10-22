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
<%@ tag body-content="empty" %>
<%@ attribute name="high" rtexprvalue="true" required="false" %>
<%@ attribute name="low" rtexprvalue="true" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>

<c:choose>
    <c:when test="${high}">
        <app:img altkey="ALT_HIGH_PRIORITY" src="startup/ImgPriorityHigh_list.png" />
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${low}">
                <app:img altkey="ALT_LOW_PRIORITY" src="startup/ImgPriorityLow_list.png" />
            </c:when>
            <c:otherwise>
				<app:img altkey="ALT_NORMAL_PRIORITY" src="startup/ImgPriorityNormal_list.gif" />
			</c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
