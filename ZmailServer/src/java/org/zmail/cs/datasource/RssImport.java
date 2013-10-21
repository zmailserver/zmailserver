/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.datasource;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import org.zmail.common.httpclient.HttpClientUtil;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.StringUtil;
import org.zmail.common.util.ZmailHttpConnectionManager;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.DataSource;
import org.zmail.cs.account.DataSource.DataImport;
import org.zmail.cs.httpclient.HttpProxyUtil;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailServiceException.NoSuchItemException;

/**
 * Imports data for RSS and remote calendar folders.
 */
public class RssImport implements DataImport {

    private DataSource mDataSource;
    
    public RssImport(DataSource ds) {
        mDataSource = ds;
    }
    
    public void importData(List<Integer> folderIds, boolean fullSync) throws ServiceException {
        Mailbox mbox = DataSourceManager.getInstance().getMailbox(mDataSource);
        int folderId = mDataSource.getFolderId();
        try {
            mbox.getFolderById(null, folderId);
            mbox.synchronizeFolder(null, folderId);
        } catch (NoSuchItemException e) {
            ZmailLog.datasource.info("Folder %d was deleted.  Deleting data source %s.",
                folderId, mDataSource.getName());
            mbox.getAccount().deleteDataSource(mDataSource.getId());
        }
    }

    public void test()
    throws ServiceException {
        Mailbox mbox = DataSourceManager.getInstance().getMailbox(mDataSource);
        int folderId = mDataSource.getFolderId();
        
        Folder folder = mbox.getFolderById(null, folderId);
        String urlString = folder.getUrl();
        if (StringUtil.isNullOrEmpty(urlString)) {
            throw ServiceException.FAILURE("URL not specified for folder " + folder.getPath(), null);
        }
        GetMethod get = new GetMethod(urlString);
        try {
            HttpClient client = ZmailHttpConnectionManager.getExternalHttpConnMgr().newHttpClient();
            HttpProxyUtil.configureProxy(client);
            HttpClientUtil.executeMethod(client, get);
            get.getResponseContentLength();
        } catch (Exception e) {
            throw ServiceException.FAILURE("Data source test failed.", e);
        } finally {
            get.releaseConnection();
        }
    }

}
