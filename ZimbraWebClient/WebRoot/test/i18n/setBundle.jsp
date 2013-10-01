<!--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
-->
<%@ taglib prefix="jfmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="zfmt" uri="com.zimbra.i18n" %>

<% int times = 1000; %>

<h2>Loading ZhMsg Comparison</h2>
<% long before, after; %>

<h3>&lt;fmt:setBundle> (JSTL, <%=times%> times)</h3>
<% before = System.currentTimeMillis(); %>
<% for (int i = 0; i < times; i++) { %>
	<jfmt:setBundle basename="/messages/ZhMsg" />
<% } %>
<% after = System.currentTimeMillis(); %>
<li>Time: <%=after-before%> ms</li>

<h3>&lt;fmt:setBundle> (Zimbra, <%=times%> times)</h3>
<% before = System.currentTimeMillis(); %>
<% for (int i = 0; i < times; i++) { %>
	<zfmt:setBundle basename="/messages/ZhMsg" />
<% } %>
<% after = System.currentTimeMillis(); %>
<li>Time: <%=after-before%> ms</li>

<h2>Loading ZmMsg Comparison</h2>

<h3>&lt;fmt:setBundle> (JSTL, <%=times%> times)</h3>
<% before = System.currentTimeMillis(); %>
<% for (int i = 0; i < times; i++) { %>
	<jfmt:setBundle basename="/messages/ZmMsg" />
<% } %>
<% after = System.currentTimeMillis(); %>
<li>Time: <%=after-before%> ms</li>

<h3>&lt;fmt:setBundle> (Zimbra, <%=times%> times)</h3>
<% before = System.currentTimeMillis(); %>
<% for (int i = 0; i < times; i++) { %>
	<zfmt:setBundle basename="/messages/ZmMsg" />
<% } %>
<% after = System.currentTimeMillis(); %>
<li>Time: <%=after-before%> ms</li>
