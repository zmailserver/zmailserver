/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
package com.zimbra.common.zmime;

import java.nio.charset.Charset;

import javax.mail.internet.MimePart;
import javax.mail.internet.SharedInputStream;

public interface ZMimePart extends MimePart {
    void appendHeader(ZInternetHeader header);

    @Override
    String getEncoding();

    void endPart(SharedInputStream sis, long partSize, int lineCount);

    Charset defaultCharset();
}
