/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
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
 */
package com.zimbra.cert;

import java.util.List;
import java.util.Map;

import com.zimbra.common.account.Key.ServerBy;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.CertMgrConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.cs.rmgmt.RemoteManager;
import com.zimbra.cs.rmgmt.RemoteResult;
import com.zimbra.cs.service.admin.AdminDocumentHandler;
import com.zimbra.soap.ZimbraSoapContext;


public class GenerateCSR extends AdminDocumentHandler {
    private final static String CSR_TYPE_SELF = "self" ;
    private final static String CSR_TYPE_COMM = "comm" ;
    private final static String [] SUBJECT_ATTRS = {
        CertMgrConstants.E_subjectAttr_C, CertMgrConstants.E_subjectAttr_ST,
        CertMgrConstants.E_subjectAttr_L, CertMgrConstants.E_subjectAttr_O,
        CertMgrConstants.E_subjectAttr_OU, CertMgrConstants.E_subjectAttr_CN };
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext lc = getZimbraSoapContext(context);
        

        
        Provisioning prov = Provisioning.getInstance();
        
        Server server = null;
        String serverId = request.getAttribute(AdminConstants.A_SERVER) ;

        if (serverId != null && serverId.equals(ZimbraCertMgrExt.ALL_SERVERS)) {
        	server = prov.getLocalServer() ;
        }else {
        	server = prov.get(ServerBy.id, serverId);
        }
    	
        if (server == null) {
            throw ServiceException.INVALID_REQUEST("Server with id " + serverId + " could not be found", null);
        }
        checkRight(lc, context, server, Admin.R_generateCSR);
        ZimbraLog.security.debug("Generate the CSR info from server:  " + server.getName()) ;
        
        String cmd = ZimbraCertMgrExt.CREATE_CSR_CMD  ;
        String newCSR = request.getAttribute(CertMgrConstants.A_new);
        String type = request.getAttribute(AdminConstants.A_TYPE);
        String keysize = request.getAttribute (CertMgrConstants.E_KEYSIZE) ; 
        if (keysize == null || (!(keysize.equalsIgnoreCase("1024") || keysize.equalsIgnoreCase("2048")))) {
            keysize = "2048";
        }
        if (newCSR.equalsIgnoreCase("1")) {
            if (type == null || type.length() == 0 ) {
                throw ServiceException.INVALID_REQUEST("No valid CSR type is set.", null);
            }else if (type.equals(CSR_TYPE_SELF) || type.equals(CSR_TYPE_COMM)) {
                cmd += " " + type + " " ;
            }else{
                throw ServiceException.INVALID_REQUEST("Invalid CSR type: " + type +". Must be (self|comm).", null);    
            }
            
            cmd +=  " -new -keysize " + keysize + " " ;
            String subject = getSubject (request);
            
            if (subject != null && subject.length() > 0) {
                cmd += "-subject \"" + subject +"\"";
            }
            
            String subjectAltNames = getSubjectAltNames(request) ;
            if (subjectAltNames != null && subjectAltNames.length() >0) {
                cmd += " -subjectAltNames \"" + subjectAltNames + "\"" ;
            }
            RemoteManager rmgr = RemoteManager.getRemoteManager(server);
            ZimbraLog.security.debug("***** Executing the cmd = " + cmd) ;
            RemoteResult rr = rmgr.execute(cmd);
            OutputParser.logOutput(rr.getMStdout()) ;
        }else{
            ZimbraLog.security.info("No new CSR need to be created.");
        }
        
        Element response = lc.createElement(CertMgrConstants.GEN_CSR_RESPONSE);
        response.addAttribute(AdminConstants.A_SERVER, server.getName());
        return response;  
    }

    public static String  getSubject (Element req) {
        Element e = null ;
        String value = null ;
        String subject = "" ;
        for (int i=0; i < SUBJECT_ATTRS.length ; i ++) {
            try {
                e = req.getElement(SUBJECT_ATTRS[i]);
            }catch (ServiceException se) {
                e = null ; //the current attribute doesn't exist
            }
            if (e != null) {
                value = e.getText();
                if (value != null && value.length() > 0) {
                    subject += "/" + SUBJECT_ATTRS[i] + "=" + value.replace("/", "\\/") ;
                }
            }
        }
        
        return subject ;
    }
    
    public static String  getSubjectAltNames (Element request) {
        String subjectAltNames = "" ;
      
        for (Element a : request.listElements(CertMgrConstants.E_SUBJECT_ALT_NAME)) {
            String value = a.getText();
            if (value != null && value.length() > 0) {
                if (subjectAltNames.length() > 0) {
                    subjectAltNames += "," ;
                }
                subjectAltNames += value ;
            }
        }
   
        return subjectAltNames ;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
    	 relatedRights.add(Admin.R_generateCSR);
    }
}
