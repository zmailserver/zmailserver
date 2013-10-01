How to deploy:
--------------

- create "oauth" directory under /opt/zimbra/lib/ext; copy "oauth-1.4.jar" and "sampleoauthprov.jar" under it

- copy "authorize.jsp" file to /opt/zimbra/jetty/webapps/zimbra/public directory

- run "zmlocalconfig -e zimbra_auth_provider=zimbra,oauth"

- configure Zimbra memcached client:
      zmprov mcf zimbraMemcachedClientServerList <memcached_server_host>:11211

- zmmailboxdctl restart


For Cosumer Apps:
-----------------

- to register a consumer app run "zmprov mcf +zimbraOAuthConsumerCredentials <consumer_key>:<consumer_secret>:<consumer_description>"

- make your consumer app access
  <zimbra_base_url>/service/extension/oauth/req_token, for request token,
  <zimbra_base_url>/service/extension/oauth/authorization, for authorization
  <zimbra_base_url>/service/extension/oauth/access_token, for access token.
