/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.service.versioncheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Config;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.XmlParseException;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.common.account.Key;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.cs.account.accesscontrol.AdminRight;
import org.zmail.cs.account.accesscontrol.Rights.Admin;
import org.zmail.cs.service.AuthProvider;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.cs.util.AccountUtil;
import org.zmail.cs.util.BuildInfo;
import org.zmail.client.ZEmailAddress;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZMailbox.Options;
import org.zmail.client.ZMailbox.ZOutgoingMessage;
import org.zmail.client.ZMailbox.ZOutgoingMessage.MessagePart;
import org.zmail.soap.ZmailSoapContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.zmail.cs.httpclient.URLUtil;
/**
 * @author Greg Solovyev
 */
public class VersionCheck extends AdminDocumentHandler {
    public static String UPDATE_TYPE_MAJOR = "major";
    public static String UPDATE_TYPE_MINOR = "minor";
	
	@Override
	public Element handle(Element request, Map<String, Object> context)	throws ServiceException {
        ZmailSoapContext zc = getZmailSoapContext(context);
    	Provisioning prov = Provisioning.getInstance();        
        Config config = prov.getConfig();
    	checkRight(zc, context, null, Admin.R_checkSoftwareUpdates);      
        String action = request.getAttribute(MailConstants.E_ACTION);
    	Element response = zc.createElement(AdminConstants.VC_RESPONSE);
        if(action.equalsIgnoreCase(AdminConstants.VERSION_CHECK_CHECK)) {
        	//check if we need to proxy to the updater server
        	String updaterServerId = config.getAttr(Provisioning.A_zmailVersionCheckServer);

            if (updaterServerId != null) {
                Server server = prov.get(Key.ServerBy.id, updaterServerId);
                if (server != null && !getLocalHostId().equalsIgnoreCase(server.getId()))
                    return proxyRequest(request, context, server);
            }
            
        	//perform the version check
        	String lastAttempt = checkVersion();
        	String resp = config.getAttr(Provisioning.A_zmailVersionCheckLastResponse);
        	if(resp == null) {
        		throw VersionCheckException.EMPTY_VC_RESPONSE(null);
        	}
        	Element respDoc;
			try {	
				respDoc = Element.parseXML(resp);
			} catch (XmlParseException ex) {
				throw VersionCheckException.INVALID_VC_RESPONSE(resp, ex);
			}
			if(respDoc == null) {
				throw ServiceException.FAILURE("error parsing  zmailVersionCheckLastResponse config attribute. Attribute value is empty",null);
			}
			Map<String, String> attrs = new HashMap<String, String>();
			if(resp !=null && resp.length()>0) {
				attrs.put(Provisioning.A_zmailVersionCheckLastSuccess, lastAttempt);
			}
			prov.modifyAttrs(config, attrs, true);
			
			// check if there are any emails to notify of a new version
			boolean sendNotification = false;

			String emails = config.getAttr(Provisioning.A_zmailVersionCheckNotificationEmail);
			if (emails != null && emails.length() > 0 && config.getBooleanAttr(Provisioning.A_zmailVersionCheckSendNotifications, false)) {
				sendNotification = true;
			}
			if (sendNotification) {
				String fromEmail = config.getAttr(Provisioning.A_zmailVersionCheckNotificationEmailFrom);
				boolean hasUpdates = respDoc.getAttributeBool(AdminConstants.A_VERSION_CHECK_STATUS, false);
				if (hasUpdates) {
					boolean hasCritical = false;
					String msgTemplate = config.getAttr(Provisioning.A_zmailVersionCheckNotificationBody);
					String subjTemplate = config.getAttr(Provisioning.A_zmailVersionCheckNotificationSubject);
					if(msgTemplate!=null && subjTemplate!=null) {
						String msg = "";
						String criticalStr = "";
						String updateTemplate = null;
						String prefix = null;
						Element eUpdates = respDoc.getElement(AdminConstants.E_UPDATES);
						int beginUpdateIndex,endUpdateIndex;
						beginUpdateIndex = msgTemplate.indexOf("${BEGIN_UPDATE}");
						endUpdateIndex = msgTemplate.indexOf("${END_UPDATE}",beginUpdateIndex);
						int beginPrefixIndex = msgTemplate.indexOf("${BEGIN_PREFIX}");
						int endPrefixIndex = msgTemplate.indexOf("${END_PREFIX}");
						if(beginPrefixIndex > -1 && endPrefixIndex > 14) {
							prefix = updateTemplate = msgTemplate.substring(beginPrefixIndex+15, endPrefixIndex);
							if(prefix != null && prefix.length()>0) {
								msg = msg.concat(prefix);
							}
						}
						if(beginUpdateIndex > -1 && endUpdateIndex > -1) {
							updateTemplate = msgTemplate.substring(beginUpdateIndex, endUpdateIndex);
							
							int i=1;
							for (Iterator<Element> iter = eUpdates.elementIterator(AdminConstants.E_UPDATE); iter.hasNext();) {
								Element eUpdate = iter.next();
								boolean isCritical = eUpdate.getAttributeBool(AdminConstants.A_CRITICAL, false);
								if (isCritical)
									hasCritical = true;

								if (isCritical) {
									criticalStr = "critical";
								} else {
									criticalStr = "non-critical";
								}
								msg = msg.concat(updateTemplate.replaceAll("\\$\\{UPDATE_URL\\}", eUpdate.getAttribute(AdminConstants.A_UPDATE_URL))
								.replaceAll("\\$\\{UPDATE_DESCRIPTION\\}", eUpdate.getAttribute(AdminConstants.A_DESCRIPTION))
								.replaceAll("\\$\\{UPDATE_VERSION\\}", eUpdate.getAttribute(AdminConstants.A_VERSION))
								.replaceAll("\\$\\{UPDATE_SHORT_VERSION\\}", eUpdate.getAttribute(AdminConstants.A_SHORT_VERSION))
								.replaceAll("\\$\\{UPDATE_RELEASE\\}", eUpdate.getAttribute(AdminConstants.A_RELEASE))
								.replaceAll("\\$\\{UPDATE_PLATFORM\\}", eUpdate.getAttribute(AdminConstants.A_PLATFORM))
								.replaceAll("\\$\\{UPDATE_BUILD_TYPE\\}", eUpdate.getAttribute(AdminConstants.A_BUILDTYPE))
								.replaceAll("\\$\\{IS_CRITICAL\\}", criticalStr)
								.replaceAll("\\$\\{UPDATE_COUNTER\\}", Integer.toString(i))
								.replaceAll("\\$\\{BEGIN_UPDATE\\}", "")
								.replaceAll("\\$\\{END_UPDATE\\}", "\n")
								);
								i++;
							}
						}
						int beginSigIndex = msgTemplate.indexOf("${BEGIN_SIGNATURE}");
						int endSigIndex = msgTemplate.indexOf("${END_SIGNATURE}");
						if(beginSigIndex > -1 && endSigIndex > 17) {
							prefix = updateTemplate = msgTemplate.substring(beginSigIndex+18, endSigIndex);
							if(prefix != null && prefix.length()>0) {
								msg = msg.concat(prefix);
							}
						}						
						if (hasCritical) {
							criticalStr = "Critical";
						} else {
							criticalStr = "Non-critical";
						}
						msg = msg.replaceAll("\\$\\{NEWLINE\\}", "\n");
						String subj = subjTemplate.replaceAll("\\$\\{IS_CRITICAL\\}", criticalStr).replaceAll("\\$\\{NEW_LINE\\}", "\n");
						try {
							Account targetAccount = Provisioning.getInstance().get(AccountBy.id, zc.getAuthtokenAccountId());
							String accountSOAPURI = AccountUtil.getSoapUri(targetAccount);
							AuthToken targetAuth = AuthProvider.getAuthToken(targetAccount,	System.currentTimeMillis() + 3600 * 1000);
							Options options = new Options();
							options.setAuthToken(targetAuth.getEncoded());
							options.setTargetAccount(targetAccount.getId());
							options.setTargetAccountBy(AccountBy.id);
							if(accountSOAPURI == null) {
								accountSOAPURI =  URLUtil.getSoapURL(prov.getLocalServer(),true);
							}
							options.setUri(accountSOAPURI);
							options.setNoSession(true);
							ZMailbox zmbox = ZMailbox.getMailbox(options);
							ZOutgoingMessage m = new ZOutgoingMessage();
							List<ZEmailAddress> addrs = new ArrayList<ZEmailAddress>();
							addrs.addAll(ZEmailAddress.parseAddresses(emails,ZEmailAddress.EMAIL_TYPE_TO));
							
							if(fromEmail != null && fromEmail.length()>0) {
								addrs.add(new ZEmailAddress(fromEmail,null,null, ZEmailAddress.EMAIL_TYPE_FROM));
							}
							m.setSubject(subj);
						
							m.setAddresses(addrs);
							m.setMessagePart(new MessagePart("text/plain", msg));
							zmbox.sendMessage(m, null, false);
							
						} catch (Exception e) {
							ZmailLog.extensions.error("Version check extension failed to send notifications.",	this, e);
						}
					}
				}
			}
        	
        } else if(action.equalsIgnoreCase(AdminConstants.VERSION_CHECK_STATUS)) {
			try {

	        	String resp = config.getAttr(Provisioning.A_zmailVersionCheckLastResponse);
	        	boolean hasUpdates = false;
	        	if(resp != null) {
		        	Element respDoc = Element.parseXML(resp);

					hasUpdates = respDoc.getAttributeBool(AdminConstants.A_VERSION_CHECK_STATUS, false);
					Element elRespVersionCheck = response.addElement(AdminConstants.E_VERSION_CHECK);
					elRespVersionCheck.addAttribute(AdminConstants.A_VERSION_CHECK_STATUS, hasUpdates);
					if(hasUpdates) {
						Element eUpdates = respDoc.getElement(AdminConstants.E_UPDATES);
						Element elRespUpdates = elRespVersionCheck.addElement(AdminConstants.E_UPDATES);
			            for (Iterator<Element> iter = eUpdates.elementIterator(AdminConstants.E_UPDATE); iter.hasNext(); ) {
			                Element eUpdate = iter.next();
			                String updateType = eUpdate.getAttribute(AdminConstants.A_UPDATE_TYPE);
			                boolean isCritical = eUpdate.getAttributeBool(AdminConstants.A_CRITICAL,false);
			                String detailsUrl = eUpdate.getAttribute(AdminConstants.A_UPDATE_URL);
			                String description = eUpdate.getAttribute(AdminConstants.A_DESCRIPTION);
			                String version = eUpdate.getAttribute(AdminConstants.A_VERSION);
			                String release = eUpdate.getAttribute(AdminConstants.A_RELEASE);
			                String platform = eUpdate.getAttribute(AdminConstants.A_PLATFORM);
			                String buildtype = eUpdate.getAttribute(AdminConstants.A_BUILDTYPE);
			                String shortVersion = eUpdate.getAttribute(AdminConstants.A_SHORT_VERSION);
			                
			                Element elRespUpdate = elRespUpdates.addElement(AdminConstants.E_UPDATE);
			                elRespUpdate.addAttribute(AdminConstants.A_UPDATE_TYPE,updateType);
			                elRespUpdate.addAttribute(AdminConstants.A_CRITICAL,isCritical);
			                elRespUpdate.addAttribute(AdminConstants.A_UPDATE_URL,detailsUrl);
			                elRespUpdate.addAttribute(AdminConstants.A_DESCRIPTION,description);
			                elRespUpdate.addAttribute(AdminConstants.A_SHORT_VERSION,shortVersion);
			                elRespUpdate.addAttribute(AdminConstants.A_RELEASE,release);
			                elRespUpdate.addAttribute(AdminConstants.A_VERSION,version);
			                elRespUpdate.addAttribute(AdminConstants.A_BUILDTYPE,buildtype);
			                elRespUpdate.addAttribute(AdminConstants.A_PLATFORM,platform);
			            }
					}
	        	}
			} catch (XmlParseException e) {
				throw ServiceException.FAILURE("error parsing  zmailVersionCheckLastResponse config attribute", e);
			}
            
        }
    	return response;
	}

	
	public static String checkVersion () throws ServiceException {
		String lastAttempt = DateUtil.toGeneralizedTime(new Date());
		Provisioning prov = Provisioning.getInstance();
		Config config = prov.getConfig();
		String url = config.getAttr(Provisioning.A_zmailVersionCheckURL);
		GetMethod method = new GetMethod(url);
		HttpClient client = new HttpClient( );
		boolean checkSuccess=false;
		String resp = null;
		String query = String.format("%s=%s&%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
				AdminConstants.A_VERSION_INFO_MAJOR,BuildInfo.MAJORVERSION,
				AdminConstants.A_VERSION_INFO_MINOR,BuildInfo.MINORVERSION,
				AdminConstants.A_VERSION_INFO_MICRO,BuildInfo.MICROVERSION,
				AdminConstants.A_VERSION_INFO_PLATFORM,BuildInfo.PLATFORM,
				AdminConstants.A_VERSION_INFO_TYPE,
				(StringUtil.isNullOrEmpty(BuildInfo.TYPE) ? "unknown" : BuildInfo.TYPE),
				AdminConstants.A_VERSION_INFO_BUILDNUM, BuildInfo.BUILDNUM
				);
		
		try {
			ZmailLog.extensions.debug("Sending version check query %s", query);
			method.setQueryString(URIUtil.encodeQuery(query));
			client.executeMethod( method );
			resp = method.getResponseBodyAsString();
			if(!StringUtil.isNullOrEmpty(resp)) {
				checkSuccess = true;
			} else {
				throw VersionCheckException.FAILED_TO_GET_RESPONSE(url,null);
			}

/**
 * <?xml version="1.0"?>
 * <versionCheck status="1 - updates available| 0 - up to date">
 * <updates>
 * <update type="minor" shortversion = "6.0.19" version = "6.0.19_GA_1841.RHEL4.NETWORK" release="20090921024654" critical="0|1" detailsURL="URL" description="text"/>
 * <update type="major" shortversion = "7.0.2" version = "7.0.2_GA_4045.RHEL4.NETWORK" release="20090921024654" critical="0|1" detailsURL="URL" description="text"/>
 * <update type="patch" shortversion = "7.0.2" version = "7.0.2_GA_4045.RHEL4.NETWORK" release="20090921024654" critical="0|1" detailsURL="URL" description="text"/>
 * </updates>
 * </versionCheck>
 **/
		} catch (URIException e) {
			throw ServiceException.FAILURE("Failed to create query string for version check.",e);
		} catch (HttpException e) {
			throw ServiceException.FAILURE("Failed to send HTTP request to version check script.",e);
		} catch (IOException e) {
			throw ServiceException.FAILURE("Failed to send HTTP request to version check script.",e);
		}  finally {
			Map<String, String> attrs = new HashMap<String, String>();
			attrs.put(Provisioning.A_zmailVersionCheckLastAttempt, lastAttempt);
			if(checkSuccess) {
				attrs.put(Provisioning.A_zmailVersionCheckLastResponse, resp);
			}
			prov.modifyAttrs(config, attrs, true);
			
			//send a notification
		}
		return lastAttempt;
	}
	
	@Override
	public void docRights(List<AdminRight> relatedRights, List<String> notes) {
		relatedRights.add(Admin.R_checkSoftwareUpdates);
	}	
}
