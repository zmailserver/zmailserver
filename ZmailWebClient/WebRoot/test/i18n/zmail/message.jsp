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
<%@ page import="java.io.*" %>
<%@ taglib prefix="fmt" uri="org.zmail.i18n" %>

<%!
	static final String BASENAME = "/messages/test/i18n/message";
	static final String BASENAME2 = BASENAME+"2";
	static final String PROPERTIES =
		"foo = [Test:foo]\n"+
		"bar = [Test:bar] Hello, {0}!\n"+
		"test.foo = [Test:test.foo]\n"+
		"test.bar = [Test:test.bar] Goodbye, {0}!\n"
	;
	static final String PROPERTIES2 =
		"baz = Mumble\n"
	;

	static final String[] BASENAMES = {
		BASENAME,
		BASENAME+"_ja",
		BASENAME2
	};
	static final String[] MESSAGES = {
		PROPERTIES,
		PROPERTIES.replaceAll("Test", "Test_ja"),
		PROPERTIES2
	};
%>

<%
	for (int i = 0; i < BASENAMES.length; i++) {
		String basename = BASENAMES[i];
		File file = new File(getServletContext().getRealPath("/WEB-INF/classes"+basename+".properties"));
		if (file.exists()) break;

		File dir = file.getParentFile();
		dir.mkdirs();
		OutputStream fout = new FileOutputStream(file);
		fout.write(MESSAGES[i].getBytes());
		fout.close();
	}
%>

<h3>fmt:setBundle (Locale: "${pageContext.request.locale}", Basename: <%=BASENAME%>)</h3>
<fmt:setLocale value="${pageContext.request.locale}" />
<fmt:setBundle basename="<%=BASENAME%>" />
<li>foo = <fmt:message key="foo" /></li>
<li>bar = <fmt:message key="bar"><fmt:param value="Baz (attr)" /></fmt:message></li>
<li>bar = <fmt:message key="bar"><fmt:param>Baz (body)</fmt:param></fmt:message></li>

<h3>fmt:setBundle (Locale: "${pageContext.request.locale}", Basename: <%=BASENAME2%>, Var: "msg2")</h3>
<fmt:setBundle var="msg2" basename="<%=BASENAME2%>" />
<li>foo = <fmt:message bundle='${msg2}' key="foo" /></li>
<li>baz = <fmt:message bundle='${msg2}' key="baz" /></li>

<h3>fmt:bundle (Locale: "ja", Basename: <%=BASENAME%>, Prefix: "test.")</h3>
<fmt:setLocale value="ja" />
<fmt:bundle basename='<%=BASENAME%>' prefix='test.'>
	<li>foo = <fmt:message key="foo" /></li>
	<li>bar = <fmt:message key="bar"><fmt:param value="Baz (attr)" /></fmt:message></li>
	<li>bar = <fmt:message key="bar"><fmt:param>Baz (body)</fmt:param></fmt:message></li>
</fmt:bundle>

<h3>Custom Features</h3>
<fmt:setLocale value="${pageContext.request.locale}" />
<li>Inline pattern:
	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:message pattern="Welcome, {0}. It is now {1,time,short} on {1,date,long}.">
		<fmt:param value="Andy" />
		<fmt:param value="${now}" />
	</fmt:message>
</li>
<li>Force fmt:bundle to reload:
	<fmt:bundle basename="<%=BASENAME%>" force='true' />
	(check log or file stat)
</li>
<li>Force fmt:setBundle to reload:
	<fmt:setBundle basename="<%=BASENAME%>" force='true' />
	(check log or file stat)
</li>