/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package org.zmail.examples.extns.storemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.FileUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.extension.ExtensionException;
import org.zmail.cs.extension.ZmailExtension;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.store.external.ExternalStoreManager;

public class ExampleStoreManager extends ExternalStoreManager implements ZmailExtension {

    String directory = "/tmp/examplestore/blobs";

    @Override
    public void startup() throws IOException, ServiceException {
        super.startup();
        ZmailLog.store.info("Using ExampleStoreManager. If you are seeing this in production you have done something WRONG!");
        FileUtil.mkdirs(new File(directory));
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    private String dirName(Mailbox mbox) {
        return directory + "/" + mbox.getAccountId();
    }

    private File getNewFile(Mailbox mbox) throws IOException {
        String baseName = dirName(mbox);
        FileUtil.mkdirs(new File(baseName));
        baseName += "/zmailblob";
        String name = baseName;
        synchronized (this) {
            int count = 1;
            File file = new File(name+".msg");
            while (file.exists()) {
                name = baseName+"_"+count++;
                file = new File(name+".msg");
            }
            if (file.createNewFile()) {
                ZmailLog.store.debug("writing to new file %s",file.getName());
                return file;
            } else {
                throw new IOException("unable to create new file");
            }
        }
    }

    @Override
    public String writeStreamToStore(InputStream in, long actualSize, Mailbox mbox) throws IOException {
        File destFile = getNewFile(mbox);
        FileUtil.copy(in, false, destFile);
        return destFile.getCanonicalPath();
    }

    @Override
    public InputStream readStreamFromStore(String locator, Mailbox mbox) throws IOException {
        return new FileInputStream(locator);
    }

    @Override
    public boolean deleteFromStore(String locator, Mailbox mbox) throws IOException {
        File deleteFile = new File(locator);
        return deleteFile.delete();
    }

    @Override
    public boolean supports(StoreFeature feature) {
        if (feature == StoreFeature.CENTRALIZED) {
            return false;
        } else {
            return super.supports(feature);
        }
    }

    
    //ZmailExtension stub so class can be loaded by ExtensionUtil.
    @Override
    public String getName() {
        return "StoreManagerExtension";
    }

    @Override
    public void init() throws ExtensionException, ServiceException {
    }

    @Override
    public void destroy() {
    }
}
