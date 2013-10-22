/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.offline.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.XmlParseException;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.cs.offline.OfflineLog;

public class AutoUpdateTest {

//    static final String BASE_UPDATE_URL = "http://localhost/update-new.php"; //for local testing
    static final String BASE_UPDATE_URL = "https://www.zmail.com/aus/zdesktop2/update.php"; //real update site; only updated once build is RTM

    static HttpClient httpClient = ZmailHttpConnectionManager.getExternalHttpConnMgr().newHttpClient();

    static final String MEDIA_MAC = "_macos_intel.dmg";
    static final String MEDIA_WIN = "_win32.msi";
    static final String MEDIA_LINUX = "_linux_i686.tgz";
    

    //these values change with every build
    
    static class UpdateInfo {
        int expectedBuild;
        String expectedFileVersion;
        String expectedAttrVersion;
        String expectedType;
        String expectedFilePrefix;
        
        public int getExpectedBuild() {
            return expectedBuild;
        }

        public String getExpectedFileVersion() {
            return expectedFileVersion;
        }

        public String getExpectedAttrVersion() {
            return expectedAttrVersion;
        }

        public String getExpectedType() {
            return expectedType;
        }

        public String getExpectedFilePrefix() {
            return expectedFilePrefix;
        }

        Map<String, PlatformInfo> platforms = new HashMap<String, PlatformInfo>();
        
        UpdateInfo(int expectedBuild, String expectedFileVersion, String expectedAttrVersion, String expectedType) {
            this.expectedBuild = expectedBuild;
            this.expectedFileVersion = expectedFileVersion;
            this.expectedAttrVersion = expectedAttrVersion + " build " + expectedBuild;
            this.expectedType = expectedType;
            this.expectedFilePrefix = "zdesktop_"+expectedFileVersion+"_b"+expectedBuild;
        }
        
        void addPlatform(PlatformInfo info) {
            platforms.put(info.getPlatform(), info);
        }
        
        PlatformInfo getPlatform(String platform) {
            return platforms.get(platform);
        }
    }

    static class PlatformInfo {
        String platform;
        String hash;
        int size;
        
        public String getPlatform() {
            return platform;
        }

        public String getHash() {
            return hash;
        }

        public int getSize() {
            return size;
        }

        PlatformInfo(String platform, String hash, int size) {
            this.platform = platform;
            this.hash = hash;
            this.size = size;
        }

        public String getFileSuffix() {
            if (platform.equals("macos")) {
                return MEDIA_MAC;
            } else if (platform.equals("win32")) {
                return MEDIA_WIN;
            } else if (platform.equals("linux")) {
                return MEDIA_LINUX;
            }  
            Assert.fail("Unexpected platform type");
            return null;
        }
    }
    
    private static Map<String, UpdateInfo> updateInfo = new HashMap<String, UpdateInfo>();
    private static String CHN_RELEASE = "release";
    private static String CHN_BETA = "beta";
   
    @BeforeClass
    public static void setUp()
    {
        UpdateInfo gaUpdateInfo = new UpdateInfo(11299, "7_1_4_ga", "7.1.4", "minor");
        gaUpdateInfo.addPlatform(new PlatformInfo("macos", "8530e2c9a003a4dd66efac2bd217f12f", 76599623));
        gaUpdateInfo.addPlatform(new PlatformInfo("win32", "7eed5033f5088f6b0d7eff6f1723c5ae", 96885760));
        gaUpdateInfo.addPlatform(new PlatformInfo("linux", "872ae3039ee376a96e0e763f5a876b46", 148836285));
        updateInfo.put(CHN_RELEASE, gaUpdateInfo);
    }
    
    static final String PARAM_CHN = "chn";
    static final String PARAM_VER = "ver";
    static final String PARAM_BID = "bid";
    static final String PARAM_BOS = "bos";
    
    static final String E_UPDATES = "updates";
    static final String E_UPDATE  = "update";
    static final String E_PATCH   = "patch";
    static final String A_URL     = "URL";
    static final String A_TYPE    = "type";
    static final String A_VERSION = "version";
    static final String A_HASH    = "hashValue";
    static final String A_SIZE    = "size";
    
    String send(NameValuePair[] params) throws HttpException, IOException {
        GetMethod httpMethod = new GetMethod(BASE_UPDATE_URL);
        httpMethod.setQueryString(params);
        int code = HttpClientUtil.executeMethod(httpClient, httpMethod);
        Assert.assertEquals(200, code);
        return httpMethod.getResponseBodyAsString();
    }
    
    void verifyExpectedUpdate(String response, String os, UpdateInfo updateInfo) throws ServiceException {
        String fileSuffix = updateInfo.getPlatform(os).getFileSuffix();
        String hash = updateInfo.getPlatform(os).getHash();
        int size = updateInfo.getPlatform(os).getSize();
        
        Element xml = Element.parseXML(response);
        Assert.assertEquals(E_UPDATES,xml.getName());
        Element update = xml.getElement(E_UPDATE);
        Assert.assertNotNull(update);
        Assert.assertEquals(updateInfo.getExpectedType(),update.getAttribute(A_TYPE));
        Assert.assertEquals(updateInfo.getExpectedAttrVersion(),update.getAttribute(A_VERSION));
        Element patch = update.getElement(E_PATCH);
        String url = patch.getAttribute(A_URL);
        String filename = url.substring(url.lastIndexOf("/")+1);
        Assert.assertEquals(updateInfo.expectedFilePrefix+fileSuffix, filename);
        Assert.assertEquals(hash, patch.getAttribute(A_HASH));
        Assert.assertEquals(size, Integer.parseInt(patch.getAttribute(A_SIZE)));
    }
    
    void verifyNoUpdate(String response) throws XmlParseException {
        Element xml = Element.parseXML(response);
        Assert.assertEquals(E_UPDATES,xml.getName());
        Assert.assertEquals(0, xml.listElements(E_UPDATE).size());
    }
    
    void sendAndVerify(String chn, String ver, int bid, String os)
    throws HttpException, IOException, ServiceException {
        NameValuePair[] nvp = new NameValuePair[4];
        nvp[0] = new NameValuePair(PARAM_CHN, chn);
        nvp[1] = new NameValuePair(PARAM_VER, ver);
        nvp[2] = new NameValuePair(PARAM_BID, bid+"");
        nvp[3] = new NameValuePair(PARAM_BOS, os);
        String response = send(nvp);
        OfflineLog.offline.info("\r\n"+response);
        UpdateInfo update = updateInfo.get(chn);
        if (chn.equalsIgnoreCase(CHN_BETA)) {
            //if GA is newer it should be published
            if (update == null || updateInfo.get(CHN_RELEASE).getExpectedBuild() > update.getExpectedBuild()) {
                update = updateInfo.get(CHN_RELEASE);
            }
        }
            
        
        if (bid < update.getExpectedBuild()) {
            verifyExpectedUpdate(response, os, update);
            OfflineLog.offline.info("Expected update received for %s %s %s build %d", os, ver, chn, bid);
        } else {
            verifyNoUpdate(response);
            OfflineLog.offline.info("No update expected for %s %s %s build %d", os, ver, chn, bid);
        }
    }
    
    String[] platforms = {"macos", "linux", "win32"};
    
    @Test
    public void ga201() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_RELEASE, "2.0.1", 10659, platform);
        }
    }

    @Test
    public void ga701() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_RELEASE, "7.0.1", 10791, platform);
        }
    }
    
    @Test
    public void beta711() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_BETA, "7.1.1", 10867, platform);
        }
    }

    @Test
    public void ga711() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_RELEASE, "7.1.1", 10917, platform);
        }
    }

    @Test
    public void ga712() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_RELEASE, "7.1.2", 10978, platform);
        }
    }

    @Test
    public void beta713() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_BETA, "7.1.3", 11139, platform);
        }
    }

    @Test
    public void beta714() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_BETA, "7.1.4", 11234, platform);
        }
    }

    @Test
    public void alreadyUpdated() throws HttpException, IOException, ServiceException {
        for (String platform: platforms) {
            sendAndVerify(CHN_RELEASE, updateInfo.get(CHN_RELEASE).getExpectedAttrVersion(), updateInfo.get(CHN_RELEASE).getExpectedBuild(), platform);
        }
    }

}
