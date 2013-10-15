/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
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
 */


package org.zmail.cert;

import java.io.IOException;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.CertMgrConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.FileUploadServlet.Upload;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class UploadProxyCA extends AdminDocumentHandler {

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext lc = getZmailSoapContext(context);
        Element response = lc.createElement(CertMgrConstants.UPLOAD_PROXYCA_RESPONSE);

        String attachId = null;
        String filename = null;
        Upload up = null ;

        try {
            attachId = request.getAttribute(CertMgrConstants.A_CERT_AID);
            filename = request.getAttribute(CertMgrConstants.A_CERT_NAME);
            ZmailLog.security.debug("Found certificate Filename  = " + filename + "; attid = " + attachId );

            up = FileUploadServlet.fetchUpload(lc.getAuthtokenAccountId(), attachId, lc.getAuthToken());
            if (up == null)
                throw ServiceException.FAILURE("Uploaded file " + filename + " with " + attachId + " was not found.", null);

            byte [] blob = ByteUtil.getContent(up.getInputStream(),-1) ;
            if(blob.length > 0)
                response.addAttribute(CertMgrConstants.A_cert_content, new String(blob));
        }catch (IOException ioe) {
            throw ServiceException.FAILURE("Can not get uploaded certificate content", ioe);
        }finally {
            FileUploadServlet.deleteUpload(up);
        }

        return response;
    }
}
