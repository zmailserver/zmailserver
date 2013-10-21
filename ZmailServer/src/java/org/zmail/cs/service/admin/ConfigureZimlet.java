/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.service.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.mailbox.MailServiceException;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.FileUploadServlet.Upload;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.zimlet.ZimletException;
import org.zmail.cs.zimlet.ZimletUtil;
import org.zmail.soap.ZmailSoapContext;

public class ConfigureZimlet extends AdminDocumentHandler {

	@Override
	public Element handle(Element request, Map<String, Object> context) throws ServiceException {
	    
	    checkRightTODO();
	    
		ZmailSoapContext zsc = getZmailSoapContext(context);
        Element content = request.getElement(MailConstants.E_CONTENT);
        String attachment = content.getAttribute(MailConstants.A_ATTACHMENT_ID, null);
        Upload up = FileUploadServlet.fetchUpload(zsc.getAuthtokenAccountId(), attachment, zsc.getAuthToken());
        if (up == null)
            throw MailServiceException.NO_SUCH_UPLOAD(attachment);

        Element response = zsc.createElement(AdminConstants.CONFIGURE_ZIMLET_RESPONSE);
		try {
			byte[] blob = ByteUtil.getContent(up.getInputStream(), 0);
			ZimletUtil.installConfig(new String(blob));
		} catch (IOException ioe) {
			throw ServiceException.FAILURE("cannot configure", ioe);
		} catch (ZimletException ze) {
			throw ServiceException.FAILURE("cannot configure", ze);
		} finally {
			FileUploadServlet.deleteUpload(up);
		}
		return response;
	}
	
	@Override
	public void docRights(List<AdminRight> relatedRights, List<String> notes) {
	    notes.add(AdminRightCheckPoint.Notes.TODO);
	    
	    notes.add("Currently the soap gets a uploaded blob containing metadata. " + 
	            "The zimlet name is encoded in in the blob and is decoded in ZimletUtil. " +
	            "We need a way to know the zimlet name (and cos name if any, currently it " +
	            "seems to always only update the default cos) in the SOAP handler in order to " +
	            "check right.");
    }
}
