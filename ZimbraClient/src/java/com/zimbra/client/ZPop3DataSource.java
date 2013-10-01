/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package com.zimbra.client;

import org.json.JSONException;

import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.util.SystemUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.soap.account.type.AccountPop3DataSource;
import com.zimbra.soap.type.DataSource.ConnectionType;
import com.zimbra.soap.type.DataSources;
import com.zimbra.soap.type.Pop3DataSource;

public class ZPop3DataSource implements ZDataSource, ToZJSONObject {

    private Pop3DataSource data;

    public ZPop3DataSource(Pop3DataSource data) {
        this.data = DataSources.newPop3DataSource(data);
    }

    public ZPop3DataSource(String name, boolean enabled, String host, int port,
                           String username, String password, String folderid,
                           ConnectionType connectionType, boolean leaveOnServer) {
        data = new AccountPop3DataSource();
        data.setName(name);
        data.setEnabled(enabled);
        data.setHost(host);
        data.setPort(port);
        data.setUsername(username);
        data.setPassword(password);
        data.setFolderId(folderid);
        data.setConnectionType(connectionType);
        setLeaveOnServer(leaveOnServer);
    }

    public Element toElement(Element parent) {
        Element src = parent.addElement(MailConstants.E_DS_POP3);
        src.addAttribute(MailConstants.A_ID, data.getId());
        src.addAttribute(MailConstants.A_NAME, data.getName());
        src.addAttribute(MailConstants.A_DS_IS_ENABLED, data.isEnabled());
        src.addAttribute(MailConstants.A_DS_HOST, data.getHost());
        src.addAttribute(MailConstants.A_DS_PORT, data.getPort());
        src.addAttribute(MailConstants.A_DS_USERNAME, data.getUsername());
        src.addAttribute(MailConstants.A_DS_PASSWORD, data.getPassword());
        src.addAttribute(MailConstants.A_FOLDER, data.getFolderId());
        src.addAttribute(MailConstants.A_DS_CONNECTION_TYPE, data.getConnectionType().name());
        src.addAttribute(MailConstants.A_DS_LEAVE_ON_SERVER, data.isLeaveOnServer());
        ZimbraLog.test.info("XXX bburtin: " + src.prettyPrint());
        return src;
    }

    public Element toIdElement(Element parent) {
        Element src = parent.addElement(MailConstants.E_DS_POP3);
        src.addAttribute(MailConstants.A_ID, getId());
        return src;
    }

    public DataSourceType getType() { return DataSourceType.pop3; }

    public String getId() { return data.getId(); }

    public String getName() { return data.getName(); }
    public void setName(String name) { data.setName(name); }

    public boolean isEnabled() { return SystemUtil.coalesce(data.isEnabled(), Boolean.FALSE); }
    public void setEnabled(boolean enabled) { data.setEnabled(enabled); }

    public String getHost() { return data.getHost(); }
    public void setHost(String host) { data.setHost(host); }

    public int getPort() { return SystemUtil.coalesce(data.getPort(), -1); }
    public void setPort(int port) { data.setPort(port); } 

    public String getUsername() { return data.getUsername(); }
    public void setUsername(String username) { data.setUsername(username); }

    public String getFolderId() { return data.getFolderId(); }
    public void setFolderId(String folderid) { data.setFolderId(folderid); }

    public ConnectionType getConnectionType() {
        ConnectionType ct = data.getConnectionType();
        return (ct == null ? ConnectionType.cleartext : ct);
    }
    
    public void setConnectionType(ConnectionType connectionType) {
        data.setConnectionType(connectionType);
    }
    
    public boolean leaveOnServer() {
        Boolean val = data.isLeaveOnServer();
        return (val == null ? true : val);
    }
    
    public void setLeaveOnServer(boolean leaveOnServer) { data.setLeaveOnServer(leaveOnServer); }
    
    public ZJSONObject toZJSONObject() throws JSONException {
        ZJSONObject zjo = new ZJSONObject();
        zjo.put("id", data.getId());
        zjo.put("name", data.getName());
        zjo.put("enabled", data.isEnabled());
        zjo.put("host", data.getHost());
        zjo.put("port", data.getPort());
        zjo.put("username", data.getUsername());
        zjo.put("folderId", data.getFolderId());
        zjo.put("connectionType", data.getConnectionType().toString());
        zjo.put("leaveOnServer", data.isLeaveOnServer());
        return zjo;
    }

    public String toString() {
        return String.format("[ZPop3DataSource %s]", getName());
    }

    public String dump() {
        return ZJSONObject.toString(this);
    }
}
