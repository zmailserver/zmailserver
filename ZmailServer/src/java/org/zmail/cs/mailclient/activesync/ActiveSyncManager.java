/* Copyright 2010 Vivek Iyer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zimbra.cs.mailclient.activesync;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.*;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.*;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.zimbra.cs.mailclient.activesync.WBXML;
import com.zimbra.cs.mailclient.activesync.ASTypes;

/**
 * @author Vivek Iyer
 *
 * This class is responsible for implementing the ActiveSync commands that
 * are used to connect to the Exchange server and  query the GAL
 */
/**
 * @author vivek
 *
 */
public class ActiveSyncManager {

    static Logger logger = Logger.getLogger(ActiveSyncManager.class);

    private String mPolicyKey = "0";
    private String mAuthString;
    private String mUri;
    private String mServerName;
    private String mDomain;
    private String mUsername;
    private String mPassword;
    private boolean mUseSSL;
    private boolean mAcceptAllCerts;
    private String mActiveSyncVersion = "";
    private String mDeviceType = "";
    private String mDeviceId = "";
    private float mActiveSyncVersionFloat = 0.0F;

    private WBXML mWbxml = null;
    private ASTypes mAst = null;
    private ASXMLParser mParser = null;

    public WBXML getWbxml() {
        return mWbxml;
    }

    public void setWbxml(WBXML wbxml) {
        mWbxml = wbxml;
    }

    public ASTypes getAst() {
        return mAst;
    }

    public void setAst(ASTypes ast) {
        mAst = ast;
    }

    public String getUri() {
        return mUri;
    }

    //private static final String TAG = "ActiveSyncManager";
    private float getActiveSyncVersionFloat(){
        if(mActiveSyncVersionFloat == 0.0F)
            mActiveSyncVersionFloat = Float.parseFloat(mActiveSyncVersion);
        return mActiveSyncVersionFloat;
    }

    public boolean isUseSSLSet() {
        return mUseSSL;
    }

    public void setUseSSL(boolean mUseSSL) {
        this.mUseSSL = mUseSSL;
    }

    public boolean isAcceptAllCertsSet() {
        return mAcceptAllCerts;
    }

    public void setAcceptAllCerts(boolean mAcceptAllCerts) {
        this.mAcceptAllCerts = mAcceptAllCerts;
    }

    public String getActiveSyncVersion() {
        return mActiveSyncVersion;
    }

    public void setActiveSyncVersion(String version) {
        mActiveSyncVersion = version;
    }

    public String getDomain() {
        return mDomain;
    }

    public void setDomain(String domain) {
        mDomain = domain;
    }

    public String getPolicyKey() {
        return mPolicyKey;
    }

    public void setPolicyKey(String policyKey) {
        this.mPolicyKey = policyKey;
    }

    public String getServerName() {
        return mServerName;
    }

    public void setServerName(String serverName) {
        this.mServerName = serverName;
    }

    public void setmUsername(String username) {
        this.mUsername = username;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getDeviceType(){
        return mDeviceType;
    }

    public void setDeviceType(String deviceType){
        mDeviceType = deviceType;
    }

    public String getDeviceId(){
        return mDeviceId;
    }

    public void setDeviceId(String deviceId){
        mDeviceId = deviceId;
    }


    /**
     * Generates the auth string from the username, password and domain
     */
    private void generateAuthString(){
        // For BPOS the DOMAIN is not required, so remove the backslash
        String userpass;
        if(mDomain.equalsIgnoreCase(""))
            userpass = mUsername + ":" + mPassword;
        else
            userpass = mDomain + "\\" + mUsername + ":" + mPassword;
        mAuthString = "Basic " + DatatypeConverter.printBase64Binary(userpass.getBytes());
    }

    private static final SecureRandom random = new SecureRandom();
    private static String randomString(String alphabet, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return sb.toString();
    }
    public static String newDeviceId() { return randomString("0123456789ABCDEF",32); }

    /**
     * Initializes the class by assigning the Exchange URL and the AuthString  
     * @throws URISyntaxException 
     */
    public boolean Initialize() {
        mWbxml = new WBXML();
        mAst = new ASTypes();
        mParser = new ASXMLParser(mAst);

        generateAuthString();

        Random rand = new Random();

        // Generate a random deviceId that is greater than 0
        while (mDeviceId == "") mDeviceId = Integer.toString(rand.nextInt());
        if (mDeviceType.compareToIgnoreCase("") == 0) mDeviceType = "ActiveSyncManager";

        // If we don't have a server name, 
        // there is no way we can proceed
        if (mServerName.compareToIgnoreCase("") == 0) return false;

        // this is where we will send it
        try {
            URI uri = new URI(
                              (mUseSSL) ? "https" : "http", // Scheme
                              mServerName , // Authority
                              "/Microsoft-Server-ActiveSync", // path
                              "User=" // query
                              + mUsername
                              + "&DeviceId=" 
                              + mDeviceId
                              + "&DeviceType=" 
                              + mDeviceType
                              + "&Cmd=",
                              null // fragment
                              );

            mUri = uri.toString();
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }

    public ActiveSyncManager(String serverName, 
                             String domain, 
                             String username,
                             String password,
                             boolean useSSL,
                             boolean acceptAllCerts,
                             String policyKey, 
                             String activeSyncVersion,
                             String deviceType,
                             String deviceId) {
        mServerName = serverName;
        mDomain = domain;
        mUsername = username;
        mPassword = password;
        mPolicyKey = policyKey;
        mActiveSyncVersion = activeSyncVersion;
        mUseSSL = useSSL;
        mAcceptAllCerts = acceptAllCerts;
        mDeviceType = deviceType;
        mDeviceId = deviceId;
    }

    /* Debug code
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String dump(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length * 4 + bytes.length / 16 + 2);
        StringBuffer chs = new StringBuffer(16);
        int v, j;
        for (j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            sb.append(hexArray[v >>> 4]);
            sb.append(hexArray[v & 0x0F]);
            sb.append(' ');
            chs.append((v >= 0x20 && v <= 0x7f)?(char)v:".");
            if (j % 16 == 15) {
                sb.append("  ");
                sb.append(chs);
                sb.append('\n');
                chs = new StringBuffer(16);
            }
        }
        for (int k = 0; k < 16 - (j % 16); k++) sb.append("   ");
        sb.append("  ");
        sb.append(chs);
        sb.append('\n');
        return sb.toString();
    }
    */

    /**
     * @param entity The entity to decode
     * @return The decoded WBXML or text/HTML entity
     * 
     * Decodes the entity that is returned from the Exchange server
     * @throws Exception 
     * @throws  
     */
    public String decodeContent(HttpMethodBase entity) throws Exception {
        String result = "";

        if (entity != null) {
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();

            // Parse all the entities
            Header contentType = entity.getResponseHeader("Content-Type");
            if (contentType == null) return entity.getResponseBodyAsString();//EntityUtils.toString(entity);

            String contentTypeValue = contentType.getValue();

            // WBXML entities
            if (contentTypeValue.compareToIgnoreCase("application/vnd.ms-sync.wbxml") == 0) {

                //InputStream is = entity.getContent();
                //byte[] res = EntityUtils.toByteArray(entity);
                //logger.info("res=\n"+dump(res));
                //InputStream is = new java.io.ByteArrayInputStream(res);
                InputStream is = entity.getResponseBodyAsStream();
                mWbxml.convertWbxmlToXml(is, output);
                result = output.toString();

            }
            // Text / HTML entities
            else if (contentTypeValue.compareToIgnoreCase("text/html") == 0) {
                result = entity.getResponseBodyAsString();//EntityUtils.toString(entity);
            }
        }
        //Log.d(TAG, (result.toString()));
        return result;

    }

    /**
     * @param httpPost The request to POST to the Exchange sever
     * @return The response to the POST message
     * @throws Exception
     * 
     * POSTs a message to the Exchange server. Any WBXML or String entities that are 
     * returned by the server are parsed and returned to the callee
     */
    public int sendPostRequest(PostMethod httpPost) throws Exception {
        // POST the request to the server
        HttpClient client = createHttpClient();
        //HttpContext localContext = new BasicHttpContext();
        //return client.execute(httpPost, localContext);
        return client.executeMethod(httpPost);
    }

    /**
     * @param httpOptions The OPTIONS message to send to the Exchange server
     * @return The headers returned by the Exchange server
     * @throws Exception
     * 
     * Sends an OPTIONS request to the Exchange server
     */
    private int sendOptionsRequest(OptionsMethod httpOptions) throws Exception {
        // Send the OPTIONS message
        HttpClient client = createHttpClient();
        //HttpContext localContext = new BasicHttpContext();
        //return client.execute(httpOptions, localContext);
        return client.executeMethod(httpOptions);
    }

    /**
     * @return The headers returned by the Exchange server
     * @throws Exception
     * 
     * Get the options that are supported by the Exchange server. 
     * This is accomplished by sending an OPTIONS request with the Cmd set to SYNC
     */
    public int getOptions() throws Exception {
        String uri = mUri; 
        return sendOptionsRequest(createOptionsMethod(uri));
    }

    /**
     * @return the HttpClient object
     * 
     * Creates a HttpClient object that is used to POST messages to the Exchange server
     */
    private HttpClient createHttpClient() {

        /*
        HttpParams httpParams = new BasicHttpParams();

        // Turn off stale checking.  Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);

        // Default connection and socket timeout of 120 seconds.  Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(httpParams, 120 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 120 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 131072);
        
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", 80, new PlainSocketFactory()));
        registry.register(new Scheme("https",443,SSLSocketFactory.getSocketFactory()));
        HttpClient httpclient = new DefaultHttpClient(new PoolingClientConnectionManager(registry), httpParams);

        // Set the headers
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Android");

        // Make sure we are not validating any hostnames
        SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        */

        HttpClient httpclient = new HttpClient();
        httpclient.getParams().setParameter("http.useragent", new String("MLState ActiveSync"));
        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout", new Integer(120 * 1000));
        httpclient.getParams().setParameter("http.socket.sendbuffer", new Integer(131072));
        httpclient.getParams().setParameter("http.connection.timeout", new Integer(120 * 1000));
        httpclient.getParams().setParameter("http.connection-manager.timeout", new Long(120 * 1000));
        httpclient.getParams().setParameter("http.connection.stalecheck", new Boolean(false));
        return httpclient;
    }

    public PostMethod createPostMethod(String uri, String requestXML) throws Exception {
        return createPostMethod(uri, requestXML, null, false);
    }

    /**
     * @param uri The URI to send the POST message to
     * @param requestXML The XML to send in the message
     * @param includePolicyKey Should we include the policyKey in the header
     * @return The POST request that can be sent to the server
     * @throws Exception
     * 
     * Creates a POST request that can be sent to the Exchange server. This method
     * sets all the necessary headers in the POST message that are required for
     * the Exchange server to respond appropriately
     */
    public PostMethod createPostMethod(String uri, String requestXML, Hashtable<String,String> headers,
                                       boolean includePolicyKey) throws Exception {

        // Set the common headers
        PostMethod httpPost = new PostMethod(uri);
        httpPost.setRequestHeader("User-Agent", "Android");
        httpPost.setRequestHeader("Accept", "*/*");
        httpPost.setRequestHeader("Content-Type", "application/vnd.ms-sync.wbxml");

        // If we are connecting to Exchange 2010 or above
        // Lets tell the Exchange server that we are a 12.1 client
        // This is so we don't have to support sending of additional
        // information in the provision method
        if(getActiveSyncVersionFloat() >= 14.0)
            httpPost.setRequestHeader("MS-ASProtocolVersion", "14.0");
        // Else set the version to the highest version returned by the
        // Exchange server
        else
            httpPost.setRequestHeader("MS-ASProtocolVersion", getActiveSyncVersion());


        //Log.d(TAG, mActiveSyncVersion);
        httpPost.setRequestHeader("Accept-Language", "en-us");
        httpPost.setRequestHeader("Authorization", mAuthString);

        // Include policy key if required
        if (includePolicyKey)
            httpPost.setRequestHeader("X-MS-PolicyKey", mPolicyKey);

        // Caller-supplied headers
        if (headers != null && !headers.isEmpty()) {
            Set<Map.Entry<String,String>> hdrs = headers.entrySet();
            Iterator<Map.Entry<String,String>> iter = hdrs.iterator();
            while (iter.hasNext()) {
                Map.Entry<String,String> me = iter.next();
                httpPost.setRequestHeader(me.getKey(), me.getValue());
            }
        }

        // Add the XML to the request
        if (requestXML != null) {
            //Log.d(TAG, requestXML);
            // Set the body
            // Convert the XML to WBXML
            ByteArrayInputStream xmlParseInputStream = new ByteArrayInputStream(requestXML.toString().getBytes());
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            mWbxml.convertXmlToWbxml(xmlParseInputStream, output);
            byte[] bytes = output.toByteArray();
            //logger.info("bytes=\n"+dump(bytes));

            ByteArrayRequestEntity myEntity = new ByteArrayRequestEntity(bytes,"application/vnd.ms-sync.wbxml");
            httpPost.setRequestEntity(myEntity);
        }
        return httpPost;
    }

    /**
     * @param uri The URI that the request needs to be sent to
     * @return The OPTIONS request that can be sent to the server
     * 
     * This method creates an OPTIONS request that can be sent to the Exchange server
     * to query for the features that are supported by the server
     */
    private OptionsMethod createOptionsMethod(String uri) {
        OptionsMethod httpOptions = new OptionsMethod(uri);
        httpOptions.setRequestHeader("User-Agent", "Android");
        httpOptions.setRequestHeader("Authorization", mAuthString);

        return httpOptions;
    }

    public ASXMLParser getXMLparser(String xml) throws Exception {
        //logger.info("parseXMLparser: xml="+xml);
        xml = xml.replaceAll("&", "&amp;");
        ByteArrayInputStream xmlParseInputStream = new ByteArrayInputStream(xml.toString().getBytes());
        XMLReader xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(mParser);
        xr.parse(new InputSource(xmlParseInputStream));
        return mParser;
    }

}
