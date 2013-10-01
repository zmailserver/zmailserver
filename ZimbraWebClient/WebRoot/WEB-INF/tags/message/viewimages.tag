<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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
<%@ attribute name="message" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.bean.ZMessageBean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>

<c:forEach var="part" items="${message.attachments}">
    <c:if test="${part.isImage}">
       <hr/>
       <c:set var="previewUrl" value="/service/home/~/?id=${message.id}&amp;part=${part.partName}&amp;auth=co&amp;max_width=800"/>
       <c:set var="url" value="/service/home/~/?id=${message.id}&amp;part=${part.partName}&amp;auth=co"/>
       <span class="ShowAllImageName"><a href="${url}">${fn:escapeXml(part.displayName)}</a></span>
       <br/><br/><img class="ShowAllImageItem" src="${previewUrl}" alt="${fn:escapeXml(part.displayName)}" border="0"/><br/><br/>
    </c:if>
</c:forEach>