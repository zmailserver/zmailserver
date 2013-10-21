Run 'ant package' - This would package the extensions into jar files inside build/jars dir.
Run 'ant javadoc' - This would generate javadoc for examples source code.


httphandler example
-------------------

1. Copy zmail-extns-httphandler.jar to /opt/zmail/lib/ext/httpHandlerExtn dir and restart server.

2. Browse to http://localhost:7070/service/extension/dummyHandler.


soapservice example
-------------------

1. Copy zmail-extns-soapservice.jar to /opt/zmail/lib/ext/soapServiceExtn dir and restart server.

2. Execute:

   $ curl -d "<soap:Envelope xmlns:soap='http://www.w3.org/2003/05/soap-envelope'><soap:Body><p:HelloWorldRequest xmlns:p='urn:zmail:examples'><caller>Vishal</caller></p:HelloWorldRequest></soap:Body></soap:Envelope>" http://localhost:7070/service/soap

   Expected response:

   <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"><soap:Header><context xmlns="urn:zmail"/></soap:Header><soap:Body><HelloWorldResponse xmlns="urn:zmail:examples"><reply>Hello Vishal!</reply></HelloWorldResponse></soap:Body></soap:Envelope>


customauth example
------------------

1. Copy zmail-extns-customauth.jar to /opt/zmail/lib/ext/customAuthExtn dir.

2. Copy conf/customauth/users.xml to /opt/zmail/conf dir.
   Edit usernames and passwords in this file.

3. Execute:

   zmprov modifyDomain <domain_name> zmailAuthMech custom:simple

4. Restart server and try logging-into the web UI. Authentication would now happen against the uses.xml file instead of ldap.


samlprovider example
--------------------

1. Copy zmail-extns-samlprovider.jar to /opt/zmail/lib/ext/samlProviderExtn dir.

2. Copy conf/samlprovider/issued-saml-assertions.xml to /opt/zmail/conf dir.
   Edit the value of <saml:NameID> element in this file.
   This file would be read by a dummy SAML authority (implemented as an extension HTTP handler) hosted at http://localhost:7070/service/extension/samlAuthority.

3. Execute:

   zmlocalconfig -e saml_authority_url=http://localhost:7070/service/extension/samlAuthority
   zmlocalconfig -e zmail_auth_provider=SAML_AUTH_PROVIDER,zmail

4. Restart server.

5. Send a soap request containing auth token as shown below to http://localhost:7070/service/soap and expect a good response:

   <soap:Envelope xmlns:soap='http://www.w3.org/2003/05/soap-envelope'>
       <soap:Header>
           <context xmlns='urn:zmail'>
               <authToken type='SAML_AUTH_PROVIDER'>b07b804c-7c29-ea16-7300-4f3d6f7928ac</authToken>
           </context>
       </soap:Header>
       <soap:Body>
           <NoOpRequest xmlns='urn:zmailMail'/>
       </soap:Body>
   </soap:Envelope>
   
 storemanager example
 --------------------
 
 1. Copy zmail-extns-storemanager.jar to /opt/zmail/lib/ext/storemanager dir
 
 2. Execute:
 
    zmlocalconfig -e zmail_class_store=org.zmail.examples.extns.storemanager.ExampleStoreManager
    
 3. Restart server
 
 4. Perform any write operations such as sending mail, uploading files, etc. Blobs should be written to /tmp/examplestore/blobs
 
 
 
 

