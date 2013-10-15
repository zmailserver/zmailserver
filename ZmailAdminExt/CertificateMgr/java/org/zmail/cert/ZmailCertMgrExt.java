/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.StringUtil;
import org.zmail.cs.account.Server;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Entry;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.soap.SoapServlet;


public class ZmailCertMgrExt implements ZmailExtension {
    public static final String EXTENSION_NAME_CERTMGR = "org_zmail_cert_manager";
    
    //Remote commands
    public static final String GET_STAGED_CERT_CMD = "zmcertmgr viewstagedcrt" ;
    public static final String GET_DEPLOYED_CERT_CMD = "zmcertmgr viewdeployedcrt" ;
    public static final String CREATE_CSR_CMD = "zmcertmgr createcsr" ;
    public static final String CREATE_CRT_CMD = "zmcertmgr createcrt"   ;
    public static final String DEPLOY_CERT_CMD = "zmcertmgr deploycrt" ;
    public static final String GET_CSR_CMD = "zmcertmgr viewcsr" ;
    public static final String VERIFY_CRTKEY_CMD = "zmcertmgr verifycrtkey" ;
    public static final String VERIFY_COMM_CRTKEY_CMD = "zmcertmgr verifycrt" ;
    public static final String VERIFY_CRTCHAIN_CMD = "zmcertmgr verifycrtchain" ;
    public static final String UPLOADED_CRT_FILE = LC.mailboxd_directory.value() + "/webapps/zmailAdmin/tmp/current.crt" ;
    public static final String UPLOADED_CRT_CHAIN_FILE = LC.mailboxd_directory.value() + "/webapps/zmailAdmin/tmp/current_chain.crt" ;
    public static final String SAVED_COMM_KEY_FROM_LDAP = LC.mailboxd_directory.value() + "/webapps/zmailAdmin/tmp/current_comm.key" ;
    //public static final String COMM_CRT_KEY_FILE = LC.zmail_home.value() + "/ssl/zmail/commercial/commercial.key" ;
    // put vefified certificate and key to tempoaray dir
    public static final String COMM_CRT_KEY_DIR = LC.mailboxd_directory.value() + "/webapps/zmailAdmin/tmp";
    public static final String COMM_CRT_KEY_FILE_NAME = "commercial.key";
    public static final String COMM_CRT_FILE_NAME = "commercial.crt";
    public static final String COMM_CRT_CA_FILE_NAME = "commercial_ca.crt";


    public static final String ALL_SERVERS = "--- All Servers ---" ;
    public static final String A_zmailSSLPrivateKey = "zmailSSLPrivateKey" ;
    public static final String A_zmailSSLCertificate = "zmailSSLCertificate" ;
    public void destroy() {
    }

    public String getName() {
        return EXTENSION_NAME_CERTMGR ;
    }

    public void init() throws ServiceException {
        SoapServlet.addService("AdminServlet", new ZmailCertMgrService());
    }

}


