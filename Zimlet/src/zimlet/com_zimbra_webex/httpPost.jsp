<!--
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
-->
<%@ page language="java" import="java.io.*"%>
<%@ page language="java" import="java.net.*"%>
<%!
public class HTTPPost {
    public  String doPost(String inputUrl, String content, String companyId) throws Exception {
	
	URL url = new URL("https://"+companyId+".webex.com/WBXService/XMLService");
	URLConnection connection = url.openConnection();
	connection.setDoOutput(true);

	OutputStreamWriter out = new OutputStreamWriter(
                              connection.getOutputStream());
	out.write("XML=" + content);
	out.close();

	BufferedReader in = new BufferedReader(
				new InputStreamReader(
				connection.getInputStream()));
				
	String result = "";
	String decodedString;
	while ((decodedString = in.readLine()) != null) {
	    result = result.concat(decodedString);
	}
	in.close();
	return result;
    }
}
%>

<%
   String content = "";
   ServletInputStream in = request.getInputStream();
   byte[] line = new byte[128];
   int i = in.readLine(line, 0, 128);
   while (i != -1) {
      content = content.concat(new String(line, 0, i));
      i = in.readLine(line, 0, 128);
   }

	String url = request.getParameter("url");
	//String content = request.getParameter("XML");
	String companyId = request.getParameter("companyId");

	HTTPPost post = new HTTPPost();	
	String result = post.doPost(url, content, companyId);
	out.println(result);


 %>
