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
package org.zmail.cert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.CertMgrConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.common.account.Key.ServerBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.rmgmt.RemoteManager;
import org.zmail.cs.rmgmt.RemoteResult;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.soap.ZmailSoapContext;


public class GetCert extends AdminDocumentHandler {
    final static String CERT_TYPE_STAGED= "staged" ;
    final static String CERT_TYPE_ALL = "all" ;
    final static String [] CERT_TYPES = {"ldap", "mailboxd", "mta", "proxy"};
    final static String CERT_STAGED_OPTION_SELF = "self" ;
    final static String CERT_STAGED_OPTION_COMM = "comm" ;
    
    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException{
        ZmailSoapContext lc = getZmailSoapContext(context);

        Provisioning prov = Provisioning.getInstance();
        ArrayList<Server> servers = new ArrayList<Server>();
        String serverId = request.getAttribute(AdminConstants.A_SERVER) ;
        String certType = request.getAttribute(AdminConstants.A_TYPE);
        String option = null;
        if (certType.equals(CERT_TYPE_STAGED)) {
            option = request.getAttribute(CertMgrConstants.A_OPTION);
        }
        if (serverId != null && serverId.equals(ZmailCertMgrExt.ALL_SERVERS)) {
            servers.addAll(prov.getAllServers());
        }else {
           Server server =  prov.get(ServerBy.id, serverId);
           if (server != null) {
               servers.add(server);
           } else {
               throw ServiceException.INVALID_REQUEST("Server with id " + serverId + " could not be found", null);
           }
        }
       
        Element response = lc.createElement(CertMgrConstants.GET_CERT_RESPONSE);
        
        for (Server s: servers) {
            addCertsOnServer(response, s, certType, option, context, lc);
        }
        
        return response;
 
    }
    
    private void addCertsOnServer(Element response, Server server,
            String certType, String option, Map<String, Object> context,
            ZmailSoapContext lc) throws ServiceException {
        
        checkRight(lc, context, server, Admin.R_getCertificateInfo);
        ZmailLog.security.debug("load the cert info from server:  " + server.getName()) ;
        
        String cmd = "";
        try {
            RemoteManager rmgr = RemoteManager.getRemoteManager(server);
            
            if (certType == null || certType.length() == 0 ) {
                throw ServiceException.INVALID_REQUEST("No valid certificate type is set in GetCertRequest", null);
            }else if (certType.equals(CERT_TYPE_STAGED)){ 
               
                if (option == null || option.length() ==0) {
                    throw ServiceException.INVALID_REQUEST("No valid option type is set in GetCertRequest for staged certs", null);
                }else if (option.equals(CERT_STAGED_OPTION_SELF) || option.equals(CERT_STAGED_OPTION_COMM)){
                    cmd = ZmailCertMgrExt.GET_STAGED_CERT_CMD + " " + option;
                    ZmailLog.security.debug("***** Executing the cmd = " + cmd) ;
                    addCertInfo(response, rmgr.execute(cmd), certType, server.getName()) ;
                }else{
                    throw ServiceException.INVALID_REQUEST(
                           "Invalid option is set in GetCertRequest for staged certs: " 
                           + certType + ". Must be (self|comm).", null); 
                }
            }else if (certType.equals(CERT_TYPE_ALL)){
                for (int i=0; i < CERT_TYPES.length; i ++) {
                    cmd = ZmailCertMgrExt.GET_DEPLOYED_CERT_CMD + " " + CERT_TYPES[i] ;
                    ZmailLog.security.debug("***** Executing the cmd = " + cmd) ;
                    addCertInfo(response, rmgr.execute(cmd), CERT_TYPES[i], server.getName()) ;
                }
            }else if (Arrays.asList(CERT_TYPES).contains(certType)){
                    //individual types
                cmd = ZmailCertMgrExt.GET_DEPLOYED_CERT_CMD + " " + certType;
                ZmailLog.security.debug("***** Executing the cmd = " + cmd) ;
                addCertInfo(response, rmgr.execute(cmd), certType, server.getName()) ;
            }else{
                throw ServiceException.INVALID_REQUEST("Invalid certificate type: " + certType + ". Must be (self|comm).", null);
            }
        }catch (IOException ioe) {
            throw ServiceException.FAILURE("exception occurred handling command", ioe);
        }
    }
    
    public void addCertInfo(Element parent, RemoteResult rr, String certType, String serverName) throws ServiceException, IOException{
        try {
            byte[] stdOut = rr.getMStdout() ;
            HashMap <String, String> output = OutputParser.parseOuput(stdOut) ;
            Element el = parent.addElement(CertMgrConstants.E_cert);
            el.addAttribute(AdminConstants.A_TYPE, certType);
            el.addAttribute(AdminConstants.A_SERVER, serverName);
            for (String k: output.keySet()) {
                ZmailLog.security.debug("Adding element " + k + " = " + output.get(k)) ;
                Element certEl = el.addElement(k);
                certEl.setText(output.get(k));
            }
        }catch(ServiceException e) {
            ZmailLog.security.warn ("Failed to retrieve the certificate information for " + certType + ".");
            ZmailLog.security.error(e) ;
        }
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_getCertificateInfo);
    }
}
