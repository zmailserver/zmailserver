/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.taglib.tag;

import org.zmail.common.auth.ZAuthToken;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapTransport;
import org.zmail.cs.taglib.ZJspSession;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.SoapProtocol;
import org.zmail.common.zclient.ZClientException;
import org.zmail.cs.taglib.tag.TagUtil.JsonDebugListener;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.regex.Pattern;

public class GetItemInfoJSONTag extends ZmailSimpleTag {

    private static final Pattern sSCRIPT = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

    private String mVar;
    private String mId;
    private String mAuthToken;
    

    public void setVar(String var) { this.mVar = var; }
    public void setId(String id) { this.mId = id; }
    public void setAuthtoken(String authToken) { this.mAuthToken = authToken; }

    public void doTag() throws JspException, IOException {
        try {
            JspContext ctxt = getJspContext();
            PageContext pageContext = (PageContext) ctxt;
            String url = ZJspSession.getSoapURL(pageContext);
            String remoteAddr = ZJspSession.getRemoteAddr(pageContext);
            ZAuthToken authToken = new ZAuthToken(mAuthToken);
            Element e = getItemInfoJSON(url, remoteAddr, authToken, mId);
            ctxt.setAttribute(mVar, sSCRIPT.matcher(e.toString()).replaceAll("</scr\"+\"ipt>"),  PageContext.REQUEST_SCOPE);
        } catch (ServiceException e) {
            throw new JspTagException(e.getMessage(), e);
        }
    }

    public static Element getItemInfoJSON(String url, String remoteAddr, ZAuthToken authToken, String mId) throws ServiceException{

        JsonDebugListener debug = new TagUtil.JsonDebugListener();
        SoapTransport transport = TagUtil.newJsonTransport(url, remoteAddr, authToken, debug);

        try {
            Element req = Element.create(SoapProtocol.SoapJS, MailConstants.GET_ITEM_REQUEST);            
            Element item = req.addElement(MailConstants.E_ITEM);
            item.addAttribute(MailConstants.A_ID, mId);
            transport.invokeWithoutSession(req);
            return debug.getEnvelope();
        } catch (IOException e) {
            throw ZClientException.IO_ERROR("invoke "+e.getMessage(), e);
        }
    }    
}
