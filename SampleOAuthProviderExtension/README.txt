How to deploy:
--------------

- create "oauth" directory under /opt/zmail/lib/ext; copy "oauth-1.4.jar" and "sampleoauthprov.jar" under it

- copy "authorize.jsp" file to /opt/zmail/jetty/webapps/zmail/public directory

- run "zmlocalconfig -e zmail_auth_provider=zmail,oauth"

- configure Zmail memcached client:
      zmprov mcf zmailMemcachedClientServerList <memcached_server_host>:11211

- zmmailboxdctl restart


For Cosumer Apps:
-----------------

- to register a consumer app run "zmprov mcf +zmailOAuthConsumerCredentials <consumer_key>:<consumer_secret>:<consumer_description>"

- make your consumer app access
  <zmail_base_url>/service/extension/oauth/req_token, for request token,
  <zmail_base_url>/service/extension/oauth/authorization, for authorization
  <zmail_base_url>/service/extension/oauth/access_token, for access token.
