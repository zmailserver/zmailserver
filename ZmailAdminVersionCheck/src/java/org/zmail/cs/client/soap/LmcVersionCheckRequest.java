/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.client.soap;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.util.Iterator;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.DomUtil;
import org.zmail.cs.versioncheck.VersionUpdate;
public class LmcVersionCheckRequest extends LmcSoapRequest {
	private String mAction;
    protected Element getRequestXML() {
        Element request = DocumentHelper.createElement(AdminConstants.VC_REQUEST);
        
        if(mAction == null) {
        	this.setAction(AdminConstants.VERSION_CHECK_CHECK);
        }
        addAttrNotNull(request, AdminConstants.E_ACTION, mAction);
        return request;
    }

    protected LmcSoapResponse parseResponseXML(Element responseXML) throws ServiceException {
    	LmcVersionCheckResponse response = new LmcVersionCheckResponse();
        if(mAction == AdminConstants.A_VERSION_CHECK_STATUS) {
	    	try {
	        	Element evc = DomUtil.get(responseXML, AdminConstants.E_VERSION_CHECK);
		        response.setStatus(DomUtil.getAttrBoolean(evc, AdminConstants.A_VERSION_CHECK_STATUS));
		        Element eUpdates = DomUtil.get(evc, AdminConstants.E_UPDATES);
		        for(Iterator<Element> iter = eUpdates.elementIterator();iter.hasNext();) {
		        	Element eUpdate = iter.next();
		        	VersionUpdate upd = new VersionUpdate();
		        	upd.setCritical(DomUtil.getAttrBoolean(eUpdate, AdminConstants.A_CRITICAL));
		        	upd.setType(DomUtil.getAttr(eUpdate, AdminConstants.A_UPDATE_TYPE));
		        	upd.setShortversion(DomUtil.getAttr(eUpdate, AdminConstants.A_SHORT_VERSION));
		        	upd.setRelease(DomUtil.getAttr(eUpdate, AdminConstants.A_RELEASE));
		        	upd.setVersion(DomUtil.getAttr(eUpdate, AdminConstants.A_VERSION));
		        	upd.setDescription(DomUtil.getAttr(eUpdate, AdminConstants.A_DESCRIPTION));
		        	upd.setPlatform(DomUtil.getAttr(eUpdate, AdminConstants.A_PLATFORM));
		        	upd.setBuildtype(DomUtil.getAttr(eUpdate, AdminConstants.A_BUILDTYPE));
		        	upd.setUpdateURL(DomUtil.getAttr(eUpdate, AdminConstants.A_UPDATE_URL));
		        	response.addUpdate(upd);
		        }
	    	} catch (ServiceException ex) {
	    		
	    	}
        }
        return response;    	
    }

	public String getAction() {
		return mAction;
	}

	public void setAction(String action) {
		mAction = action;
	}
}
