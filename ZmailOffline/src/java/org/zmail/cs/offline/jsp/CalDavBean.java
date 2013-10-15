/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.offline.jsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.account.Key.AccountBy;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.soap.admin.type.DataSourceType;
import org.zmail.cs.offline.common.OfflineConstants;
import org.zmail.client.ZMailbox;
import org.zmail.client.ZFolder;
import org.zmail.soap.type.DataSource.ConnectionType;


public class CalDavBean extends FormBean {
	
	public CalDavBean() {}
	
	protected String accountId;
	protected String name;
	protected String principalPath;
	protected String principalUrl;
	protected String defaultCalDavUrl;
	
	protected String displayName = "";
	protected String email = "";
	protected String password = "";
	
	protected String mailUsername;
	protected String mailPassword;

	protected String host = "";
	protected String port = "";
	protected boolean isSsl;
	protected boolean useLoginFromEmail = true;
	
	protected long syncFreqSecs = OfflineConstants.DEFAULT_SYNC_FREQ / 1000;
	
	protected boolean isDebugTraceEnabled;
	protected boolean isLoaded;
	protected DataSourceType type;
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		this.name = require(n);
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getPrincipalPath() {
		return principalPath;
	}
	
	public void setPrincipalPath(String principalPath) {
		this.principalPath = principalPath;
	}
	
	public String getPrincipalUrl() {
		if (principalUrl == null || principalUrl.length() == 0)
			return defaultCalDavUrl;
		return principalUrl;
	}
	
	public void setPrincipalUrl(String principalUrl) {
		this.principalUrl = principalUrl;
	}
	
	public String getDefaultCalDavUrl() {
		return defaultCalDavUrl;
	}
	
	public void setDefaultCalDavUrl(String caldavUrl) {
		this.defaultCalDavUrl = caldavUrl;
	}
	
	public String getPassword() {
		return "";
	}
	
	public void setPassword(String password) {
		this.password = require(password);
	}
	
	public String getEmail() {
		if (useLoginFromEmail)
			return mailUsername;
		return email;
	}
	
	public void setEmail(String email) {
		this.email = require(email);
	}

	public String getMailUsername() {
		return mailUsername;
	}
	
	public void setMailUsername(String email) {
		mailUsername = email;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = require(host);
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = require(port);
	}
	
	public boolean isSsl() {
		return isSsl;
	}
	
	public void setSsl(boolean isSsl) {
		this.isSsl = isSsl;
	}
	
	public boolean isUseLoginFromEmail() {
		return useLoginFromEmail;
	}
	
	public void setUseLoginFromEmail(boolean val) {
		useLoginFromEmail = val;
	}
	
	public boolean isDebugTraceEnabled() {
		return isDebugTraceEnabled;
	}
	
	public void setDebugTraceEnabled(boolean isDebugTraceEnabled) {
		this.isDebugTraceEnabled = isDebugTraceEnabled;
	}
	
	public long getSyncFreqSecs() {
		return syncFreqSecs;
	}
	
	public void setSyncFreqSecs(long syncFreqSecs) {
		this.syncFreqSecs = syncFreqSecs;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	public void setLoaded(boolean loaded) {
		isLoaded = loaded;
	}
	
	public String[] getExportList() throws ServiceException {
		return getFolderList(true);
	}

	public String[] getImportList() throws ServiceException {
		return getFolderList(false);
	}

	String[] getFolderList(boolean export) throws ServiceException {
		List<ZFolder> fldrs;
		ArrayList<String> list = new ArrayList<String>();
		ZMailbox.Options options = new ZMailbox.Options();

		options.setAccountBy(AccountBy.id);
		options.setAccount(accountId);
		options.setPassword(password);
		options.setUri(ConfigServlet.LOCALHOST_SOAP_URL);
		ZMailbox mbox = ZMailbox.getMailbox(options);
		fldrs = mbox.getAllFolders();
		for (ZFolder f : fldrs) {
			if (f.getPath().equals("/") || f.getParentId() == null ||
				f.getId().equals(ZFolder.ID_AUTO_CONTACTS))
				continue;
			else if (!export && f.getClass() != ZFolder.class)
				continue;
			list.add(f.getRootRelativePath());
		}
		return list.toArray(new String[0]);
	}

	@Override
	protected void reload() {
		DataSource ds, mailDs;
		isLoaded = false;
		try {
			mailDs = JspProvStub.getInstance().getOfflineDataSource(accountId);
			mailUsername = mailDs.getUsername();
			mailPassword = mailDs.getDecryptedPassword();
			ds = JspProvStub.getInstance().getOfflineCalendarDataSource(accountId);
		} catch (ServiceException x) {
			setError(x.getMessage());
			return;
		}
		
    	type = DataSourceType.caldav;
		if (ds == null)
			return;
		name = ds.getName();
		email = ds.getUsername();
		password = JspConstants.MASKED_PASSWORD;
		displayName = ds.getFromDisplay();
		host = ds.getHost();
		port = ds.getPort().toString();
		isSsl = ds.getConnectionType() == ConnectionType.ssl;
    	String attrs[] = ds.getMultiAttr(Provisioning.A_zmailDataSourceAttribute);
    	for (String a : attrs) {
    		if (a.startsWith("p:")) {
    			principalPath = a.substring(2);
    			break;
    		}
    	}
		isDebugTraceEnabled = ds.isDebugTraceEnabled();
		syncFreqSecs = ds.getTimeIntervalSecs(OfflineConstants.A_zmailDataSourceSyncFreq, OfflineConstants.DEFAULT_SYNC_FREQ / 1000);
		isLoaded = true;
	}

	@Override protected void doRequest() {
		if (verb == null) {
			reload();
			return;
		}
		if (!isAllOK())
			return;

		if (verb.isReset()) {
			// delete calendar ds
			return;
		}

		if (useLoginFromEmail) {
			try {
				DataSource mailDs = JspProvStub.getInstance().getOfflineDataSource(accountId);
				mailUsername = mailDs.getUsername();
				// we can't get the password via SoapProvisioning
				mailPassword = mailDs.getDecryptedPassword();
			} catch (ServiceException x) {
				setError(x.getMessage());
				return;
			}
			email = mailUsername;
			//password = mailPassword;
		}
		
		Map<String, Object> dsAttrs = new HashMap<String, Object>();
		
		if (isEmpty(email))
	    	addInvalid("email");
		if (isEmpty(password))
	    	addInvalid("password");
		if (isEmpty(principalUrl))
	    	addInvalid("url");

		String url = principalUrl;
		if (url.startsWith("http://")) {
			url = url.substring(7);
			port = "80";
			isSsl = false;
		} else if (url.startsWith("https://")) {
			url = url.substring(8);
			port = "443";
			isSsl = true;
		}
		int slash = url.indexOf('/');
		if (slash > 0) {
			principalPath = url.substring(slash);
			url = url.substring(0, slash);
		}
		int colon = url.indexOf(':');
		if (colon > 0) {
			port = url.substring(colon);
			url = url.substring(0, colon);
		}
		host = url;
		
		if (isEmpty(principalPath))
			addInvalid("url");
		
	    if (isAllOK()) {
	        dsAttrs.put(Provisioning.A_zmailDataSourceEnabled, ProvisioningConstants.TRUE);
	        dsAttrs.put(Provisioning.A_zmailDataSourceName, name);
	        dsAttrs.put(Provisioning.A_zmailDataSourceUsername, email);
	        dsAttrs.put(Provisioning.A_zmailPrefFromDisplay, displayName);
	        if (!password.equals(JspConstants.MASKED_PASSWORD)) {
	            dsAttrs.put(Provisioning.A_zmailDataSourcePassword, password);
	        }
	        
	        dsAttrs.put(Provisioning.A_zmailDataSourceHost, host);
	        dsAttrs.put(Provisioning.A_zmailDataSourcePort, port);
	        dsAttrs.put(Provisioning.A_zmailDataSourceAttribute, "p:"+principalPath);
	        dsAttrs.put(Provisioning.A_zmailDataSourceConnectionType, (isSsl ? ConnectionType.ssl : ConnectionType.cleartext).toString());
	        dsAttrs.put(Provisioning.A_zmailDataSourceEnableTrace, isDebugTraceEnabled ? ProvisioningConstants.TRUE : ProvisioningConstants.FALSE);
	        
	        dsAttrs.put(OfflineConstants.A_zmailDataSourceSyncFreq, Long.toString(syncFreqSecs));
            dsAttrs.put(Provisioning.A_zmailDataSourceFolderId, ZFolder.ID_USER_ROOT);

			try {
				JspProvStub stub = JspProvStub.getInstance();
				if (verb.isAdd()) {
					stub.createOfflineCalendarDataSource(accountId, dsAttrs);
				} else if (verb.isModify()) {
					stub.modifyOfflineDataSource(accountId, name, dsAttrs);
				} else {
					setError(getMessage("UnknownAct"));
				}
		    } catch (SoapFaultException x) {
		    	if (x.getCode().equals("account.AUTH_FAILED")) {
		    		setError(getMessage("InvalidUserOrPass"));
		    	} else if (!(verb != null && verb.isDelete() && x.getCode().equals("account.NO_SUCH_ACCOUNT"))) {
		    		setExceptionError(x);
		    	}
	        } catch (Exception t) {
	            setError(t.getMessage());
			}
		}
	}
}
