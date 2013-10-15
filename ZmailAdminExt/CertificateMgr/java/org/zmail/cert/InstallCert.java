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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.zmail.common.account.Key.ServerBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.CertMgrConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.rmgmt.RemoteManager;
import org.zmail.cs.rmgmt.RemoteResult;
import org.zmail.cs.service.FileUploadServlet;
import org.zmail.cs.service.FileUploadServlet.Upload;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.soap.ZmailSoapContext;


public class InstallCert extends AdminDocumentHandler {
    final static String CERT_TYPE_SELF= "self" ;
    final static String CERT_TYPE_COMM = "comm" ;
    //private final static String ALLSERVER = "allserver" ;
    private final static String ALLSERVER_FLAG = "-allserver" ;
    private Server server = null;
    
    private Provisioning prov = null;
     
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZmailSoapContext lc = getZmailSoapContext(context);
        
        prov = Provisioning.getInstance();
        
        String serverId = request.getAttribute(AdminConstants.A_SERVER) ;
        boolean isTargetAllServer = false ;
        if (serverId != null && serverId.equals(ZmailCertMgrExt.ALL_SERVERS)) {
        	server = prov.getLocalServer() ;
        	isTargetAllServer = true ;
        }else {
        	server = prov.get(ServerBy.id, serverId);
        }
    	
        if (server == null) {
            throw ServiceException.INVALID_REQUEST("Server with id " + serverId + " could not be found", null);
        }
        checkRight(lc, context, server, Admin.R_installCertificate);
        ZmailLog.security.debug("Install the certificateion for server:  " + server.getName()) ;
        //the deployment of certs should happen on the target server
        RemoteManager rmgr = RemoteManager.getRemoteManager(server);
        String cmd = ZmailCertMgrExt.CREATE_CRT_CMD ;
        String deploycrt_cmd = ZmailCertMgrExt.DEPLOY_CERT_CMD ;
        String certType = request.getAttribute(AdminConstants.A_TYPE) ;
        if (certType == null || certType.length() == 0 ) {
            throw ServiceException.INVALID_REQUEST("No valid certificate type is set", null);
        }else if (certType.equals(CERT_TYPE_SELF) || certType.equals(CERT_TYPE_COMM)) {
            //cmd += " " + certType  ;   //createcrt implies self signed
            deploycrt_cmd += " " + certType ;
        }else {
            throw ServiceException.INVALID_REQUEST("Invalid certificate type: " + certType + ". Must be (self|comm).", null);
        }
        
        if (certType.equals("comm")) {
            checkUploadedCommCert(request, lc, isTargetAllServer) ;
        }
        
        //always set the -new flag for the cmd since the ac requests for a new cert always
        cmd += " -new " ;
        
        Element valDayEl = request.getElement(CertMgrConstants.E_VALIDATION_DAYS) ;
        String validation_days = null ;
                
        if ((valDayEl != null) && (!certType.equals("comm"))) {
            validation_days = valDayEl.getText() ;
            if (validation_days != null && validation_days.length() > 0) {
                cmd += " -days " + validation_days ;
            }
        }

        Element subjectEl = request.getElement(CertMgrConstants.E_SUBJECT)  ;
        String subject = GenerateCSR.getSubject(subjectEl) ;

        String subjectAltNames = GenerateCSR.getSubjectAltNames(request) ;

        if (certType.equals("self")) {
            Element keysizeEl = request.getElement (CertMgrConstants.E_KEYSIZE) ;
            String keysize = null ;

            if (keysizeEl != null)  {
                if (certType.equals("self")) {
                    keysize = keysizeEl.getText() ;
                    if (!(keysize.equalsIgnoreCase("1024") || keysize.equalsIgnoreCase("2048"))) {
                        keysize = "2048";
                    }
                }
            } else {
                keysize = "2048";
            }
            cmd += " -keysize " + keysize + " " ;

            if (subject != null && subject.length() > 0) {
                cmd += "-subject \"" + subject +"\"";
            }

            if (subjectAltNames != null && subjectAltNames.length() >0) {
                cmd += " -subjectAltNames \"" + subjectAltNames + "\"" ;
            }
        } else if (certType.equals("comm")) {
            deploycrt_cmd += " " + ZmailCertMgrExt.UPLOADED_CRT_FILE +  " "  + ZmailCertMgrExt.UPLOADED_CRT_CHAIN_FILE ;
        }
        
        if (isTargetAllServer) {
           if (certType.equals("self")) { //self -allserver install - need to pass the subject to the createcrt cmd
                if (subject != null && subject.length() > 0) {
                    ZmailLog.security.debug("Subject for allserver: " + subject);
                    cmd += " -subject " + " \"" + subject +"\"";
                }
            }

            cmd += " " + ALLSERVER_FLAG;
            deploycrt_cmd += " " + ALLSERVER_FLAG;
        }

        RemoteResult rr ;
        if (certType.equals("self")) {
            ZmailLog.security.debug("***** Executing the cmd = " + cmd) ;
            rr = rmgr.execute(cmd);
            //ZmailLog.security.info("***** Exit Status Code = " + rr.getMExitStatus()) ;
            try {
                OutputParser.parseOuput(rr.getMStdout()) ;
            }catch (IOException ioe) {
                throw ServiceException.FAILURE("exception occurred handling command", ioe);
            }
        }
        
        //need to deploy the crt now
        ZmailLog.security.debug("***** Executing the cmd = " + deploycrt_cmd) ;
        rr = rmgr.execute(deploycrt_cmd);
        try {
            OutputParser.parseOuput(rr.getMStdout()) ;
        }catch (IOException ioe) {
            throw ServiceException.FAILURE("exception occurred handling command", ioe);
        }
        
        Element response = lc.createElement(CertMgrConstants.INSTALL_CERT_RESPONSE);
        response.addAttribute(AdminConstants.A_SERVER, server.getName());
        return response;    
    }

    
    private boolean checkUploadedCommCert (Element request, ZmailSoapContext lc, boolean isAllServer) throws ServiceException {
        Upload up = null ;
        InputStream is = null ;
        //the verification commands are all executed on the local server
        RemoteManager rmgr = RemoteManager.getRemoteManager(prov.getLocalServer());
        
        try {
            //read the cert file
            ByteArrayOutputStream completeCertChain = new ByteArrayOutputStream(8192);
            Element certEl = request.getPathElement(
                    new String [] {CertMgrConstants.E_comm_cert, CertMgrConstants.E_cert});
            String attachId = certEl.getAttribute(AdminConstants.A_ATTACHMENT_ID) ;
            String filename = certEl.getAttribute(CertMgrConstants.A_FILENAME) ;
            ZmailLog.security.debug("Certificate Filename  = " + filename + "; attid = " + attachId );
            
            up = FileUploadServlet.fetchUpload(lc.getAuthtokenAccountId(), attachId, lc.getAuthToken());
            if (up == null)
                throw ServiceException.FAILURE("Uploaded file " + filename + " with " + attachId + " was not found.", null);
          
            is = up.getInputStream() ;
            byte [] cert = ByteUtil.getContent(is, 1024) ;
            ZmailLog.security.debug ("Put the uploaded commercial crt  to " + ZmailCertMgrExt.UPLOADED_CRT_FILE) ;
            ByteUtil.putContent(ZmailCertMgrExt.UPLOADED_CRT_FILE, cert) ;
            is.close();
            completeCertChain.write(cert);
            completeCertChain.write('\n') ;
                    
            //read the CA
            ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);


            Element rootCAEl = request.getPathElement(
                    new String [] {CertMgrConstants.E_comm_cert,
                            CertMgrConstants.E_rootCA});
            attachId = rootCAEl.getAttribute(AdminConstants.A_ATTACHMENT_ID) ;
            filename = rootCAEl.getAttribute(CertMgrConstants.A_FILENAME) ;
            
            ZmailLog.security.debug("Certificate Filename  = " + filename + "; attid = " + attachId );
            
            up = FileUploadServlet.fetchUpload(lc.getAuthtokenAccountId(), attachId, lc.getAuthToken());
            if (up == null)
                throw ServiceException.FAILURE("Uploaded file " + filename + " with " + attachId + " was not found.", null);
            is = up.getInputStream();
            byte [] rootCA = ByteUtil.getContent(is, 1024) ;
            is.close();
            
            //read interemediateCA
            byte [] intermediateCA ;
            List<Element> intermediateCAElList = request.getPathElementList(
                    new String [] {CertMgrConstants.E_comm_cert,
                            CertMgrConstants.E_intermediateCA});
            if (intermediateCAElList != null && intermediateCAElList.size() > 0) {
                for (int i=0; i < intermediateCAElList.size(); i ++ ) {
                    Element intemediateCAEl = intermediateCAElList.get(i);
                    attachId = intemediateCAEl.getAttribute(AdminConstants.A_ATTACHMENT_ID) ;
                    filename = intemediateCAEl.getAttribute(CertMgrConstants.A_FILENAME) ;
                    
                    if (attachId != null && filename != null) {
                        ZmailLog.security.debug("Certificate Filename  = " + filename + "; attid = " + attachId );
                        
                        up = FileUploadServlet.fetchUpload(lc.getAuthtokenAccountId(), attachId, lc.getAuthToken());
                        if (up == null)
                            throw ServiceException.FAILURE("Uploaded file " + filename + " with " + attachId + " was not found.", null);
                        is = up.getInputStream();
                        intermediateCA = ByteUtil.getContent(is, 1024);
                        is.close();
                        
                        baos.write(intermediateCA);
                        baos.write('\n');

                        completeCertChain.write(intermediateCA);
                        completeCertChain.write('\n');
                    }
                }
            }
            
            baos.write(rootCA);
            baos.write('\n');


            byte [] chain = baos.toByteArray() ;
            baos.close();

            completeCertChain.write(rootCA);
            completeCertChain.write('\n');
            completeCertChain.close();
            
            ZmailLog.security.debug ("Put the uploaded crt chain  to " + ZmailCertMgrExt.UPLOADED_CRT_CHAIN_FILE) ;
            ByteUtil.putContent(ZmailCertMgrExt.UPLOADED_CRT_CHAIN_FILE, chain) ;

            String privateKey = null;
            if (isAllServer) {
                ZmailLog.security.debug ("Retrieving zmailSSLPrivateKey from Global Config.");
                privateKey = prov.getConfig().getAttr(ZmailCertMgrExt.A_zmailSSLPrivateKey);
                //Note: We do this because zmcertmgr don't save the private key to global config
                //since -allserver is not supported by createcsr
                // and deploycrt has to take the hard path of cert and CA chain
                if (privateKey == null || privateKey.length() <= 0) {
                    //permission is denied for the  COMM_CRT_KEY_FILE which is readable to root only
                    //ZmailLog.security.debug ("Retrieving commercial private key from " + ZmailCertMgrExt.COMM_CRT_KEY_FILE);
                    //privateKey = new String (ByteUtil.getContent(new File(ZmailCertMgrExt.COMM_CRT_KEY_FILE))) ;

                    //retrieve the key from the local server  since the key is always saved in the local server when createcsr is called
                    ZmailLog.security.debug ("Retrieving zmailSSLPrivateKey from server: " + server.getName());
                    privateKey = server.getAttr(ZmailCertMgrExt.A_zmailSSLPrivateKey) ;
                }
            } else {
                ZmailLog.security.debug ("Retrieving zmailSSLPrivateKey from server: " + server.getName());
                privateKey = server.getAttr(ZmailCertMgrExt.A_zmailSSLPrivateKey) ;
            }

            if (privateKey != null && privateKey.length() > 0) {
                ZmailLog.security.debug ("Saving zmailSSLPrivateKey to  " + ZmailCertMgrExt.SAVED_COMM_KEY_FROM_LDAP) ;
            }   else {
                 throw ServiceException.FAILURE("zmailSSLPrivateKey is not present.", new Exception());
            }
            ByteUtil.putContent(ZmailCertMgrExt.SAVED_COMM_KEY_FROM_LDAP, privateKey.getBytes());
            
            try {
                //run zmcertmgr verifycrt to validate the cert and key
                String cmd = ZmailCertMgrExt.VERIFY_CRTKEY_CMD + " comm "
                            //+ " " + ZmailCertMgrExt.COMM_CRT_KEY_FILE
                            + " " + ZmailCertMgrExt.SAVED_COMM_KEY_FROM_LDAP
                            + " " + ZmailCertMgrExt.UPLOADED_CRT_FILE ;
              
                String verifychaincmd = ZmailCertMgrExt.VERIFY_CRTCHAIN_CMD
                            + " " + ZmailCertMgrExt.UPLOADED_CRT_CHAIN_FILE
                            + " " + ZmailCertMgrExt.UPLOADED_CRT_FILE ;
                
          
                ZmailLog.security.debug("*****  Executing the cmd: " + cmd);
                RemoteResult rr = rmgr.execute(cmd) ;
          
                OutputParser.parseOuput(rr.getMStdout()) ;
                
                //run zmcertmgr verifycrtchain to validate the certificate chain
                ZmailLog.security.debug("*****  Executing the cmd: " + verifychaincmd);
                rr = rmgr.execute(verifychaincmd) ;
                OutputParser.parseOuput(rr.getMStdout()) ;

                //Certs are validated and Save the uploaded certificate to the LDAP
                String [] zmailSSLCertificate =  {
                        ZmailCertMgrExt.A_zmailSSLCertificate, completeCertChain.toString()};

                ZmailLog.security.debug("Save complete cert chain to " +  ZmailCertMgrExt.A_zmailSSLCertificate +
                    completeCertChain.toString()) ;

                if (isAllServer) {
                    prov.modifyAttrs(prov.getConfig(),
                        StringUtil.keyValueArrayToMultiMap(zmailSSLCertificate, 0), true);
                }   else {
                    prov.modifyAttrs(server,
                        StringUtil.keyValueArrayToMultiMap(zmailSSLCertificate, 0), true);
                }

                
            }catch (IOException ioe) {
                throw ServiceException.FAILURE("IOException occurred while running cert verification command", ioe);
            }
        } catch (IOException ioe) {
            throw ServiceException.FAILURE("IOException while handling uploaded certificate", ioe);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ZmailLog.security.warn("exception closing uploaded certificate:", ioe);
                }
            }

            //delete the key file      
            File comm_priv = new File (ZmailCertMgrExt.SAVED_COMM_KEY_FROM_LDAP)  ;
            if (!comm_priv.delete()) {
                ZmailLog.security.error ("File " + ZmailCertMgrExt.SAVED_COMM_KEY_FROM_LDAP + " was not deleted");
            }
        }


        return true ;
    }
    
    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
    	relatedRights.add(Admin.R_installCertificate);
    }
}
