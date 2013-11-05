/*

     Copyright 2011-2013 MLstate
 
     This file is part of Opa.
 
     Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 
     The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 
     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

*/
// WARNING: This file has been generated by japigen.opa, DO NOT EDIT.
// Config file: japigen/jas.apigen
// Module name: AS

package org.zmail.cs.mailclient.activesync;

import java.util.*;
import java.lang.String;
import org.apache.commons.httpclient.methods.*;
import org.zmail.cs.mailclient.activesync.ActiveSyncManager;
import org.zmail.cs.mailclient.activesync.ASTypes.*;


public class ASFunctions {

    public ASFunctions() { }

    // External code
    
    ASTypes mAst = null;
    ActiveSyncManager mAsm = null;
    boolean mInitialized = false;
    public ASFunctions(String serverName, String domain, String username, String password, boolean useSSL,
                       boolean acceptAllCerts, String policyKey, String activeSyncVersion, String deviceType, String deviceId) {
        mAsm = new ActiveSyncManager(serverName, domain, username, password, useSSL,
                                     acceptAllCerts, policyKey, activeSyncVersion, deviceType, deviceId);
        boolean initialized = mAsm.Initialize();
        mInitialized = initialized;
        mAst = mAsm.getAst();
    }
    public ActiveSyncManager getAsm() {
        return mAsm;
    }
    public ASTypes getAst() {
        return mAst;
    }
    
    // End external code
    
    
    // Call functions
    
    public AS_FolderSyncB foldersync(
        Hashtable <String, String> headers,
        AS_FolderSyncF foldersyncff
    ) throws Exception {
        String uri = mAsm.getUri() + "FolderSync";
        String xml = mAst.pack_FolderSyncF(foldersyncff.SyncKey);
        PostMethod method = mAsm.createPostMethod(uri, xml, headers, true);
        int statusCode = mAsm.sendPostRequest(method);
        AS_FolderSyncB foldersyncbb = null;
        if (statusCode == 200) {
            xml = mAsm.decodeContent(method);
            if (!xml.equals("")) {
                ASXMLParser parser = mAsm.getXMLparser(xml);
                foldersyncbb = parser.getFolderSyncB();
            }
        }
        return foldersyncbb;
    }
    
    public AS_ProvisionNoDIB provision_no_device_information(
        Hashtable <String, String> headers,
        AS_ProvisionNoDI provisionnodif
    ) throws Exception {
        String uri = mAsm.getUri() + "Provision";
        String xml = mAst.pack_ProvisionNoDI();
        PostMethod method = mAsm.createPostMethod(uri, xml, headers, true);
        int statusCode = mAsm.sendPostRequest(method);
        AS_ProvisionNoDIB provisionnodibb = null;
        if (statusCode == 200) {
            xml = mAsm.decodeContent(method);
            if (!xml.equals("")) {
                ASXMLParser parser = mAsm.getXMLparser(xml);
                provisionnodibb = parser.getProvisionNoDIB();
            }
        }
        return provisionnodibb;
    }
    
    public AS_Status sendmail(
        Hashtable <String, String> headers,
        AS_SendMail sendmailf
    ) throws Exception {
        String uri = mAsm.getUri() + "SendMail";
        String xml = mAst.pack_SendMail(sendmailf.ClientId,sendmailf.AccountId,sendmailf.SaveInSentItems,sendmailf.Mime);
        PostMethod method = mAsm.createPostMethod(uri, xml, headers, true);
        int statusCode = mAsm.sendPostRequest(method);
        AS_Status statusb = null;
        if (statusCode == 200) {
            xml = mAsm.decodeContent(method);
            if (!xml.equals("")) {
                ASXMLParser parser = mAsm.getXMLparser(xml);
                statusb = parser.getStatus();
            }
        }
        return statusb;
    }
    
    public AS_Sync sync(
        Hashtable <String, String> headers,
        AS_Sync syncf
    ) throws Exception {
        String uri = mAsm.getUri() + "Sync";
        String xml = mAst.pack_Sync(syncf.Collections,syncf.Partial,syncf.Wait,syncf.HeartbeatInterval,syncf.WindowSize);
        PostMethod method = mAsm.createPostMethod(uri, xml, headers, true);
        int statusCode = mAsm.sendPostRequest(method);
        AS_Sync syncb = null;
        if (statusCode == 200) {
            xml = mAsm.decodeContent(method);
            if (!xml.equals("")) {
                ASXMLParser parser = mAsm.getXMLparser(xml);
                syncb = parser.getSync();
            }
        }
        return syncb;
    }
    
    public AS_AttachFetchResponse itemoperationsfetch(
        Hashtable <String, String> headers,
        AS_Fetch fetchf
    ) throws Exception {
        String uri = mAsm.getUri() + "ItemOperations";
        String xml = mAst.pack_Fetch(fetchf.Fetch);
        PostMethod method = mAsm.createPostMethod(uri, xml, headers, true);
        int statusCode = mAsm.sendPostRequest(method);
        AS_AttachFetchResponse attachfetchresponseb = null;
        if (statusCode == 200) {
            xml = mAsm.decodeContent(method);
            if (!xml.equals("")) {
                ASXMLParser parser = mAsm.getXMLparser(xml);
                attachfetchresponseb = parser.getAttachFetchResponse();
            }
        }
        return attachfetchresponseb;
    }
    

}

// End of ASFunctions
