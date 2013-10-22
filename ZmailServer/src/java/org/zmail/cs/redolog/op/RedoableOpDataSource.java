/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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

package com.zimbra.cs.redolog.op;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.zimbra.common.mime.MimeConstants;

public class RedoableOpDataSource implements DataSource {

    private RedoableOpData mData;
    
    public RedoableOpDataSource(RedoableOpData data) {
        if (data == null) {
            throw new NullPointerException();
        }
        mData = data;
    }
    
    public String getContentType() {
        return MimeConstants.CT_APPLICATION_OCTET_STREAM;
    }

    public InputStream getInputStream() throws IOException {
        return mData.getInputStream();
    }

    public String getName() {
        return null;
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("not supported");
    }
}
