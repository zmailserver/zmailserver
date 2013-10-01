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
package com.zimbra.cs.mailclient.util;

import java.io.InputStream;
import java.io.IOException;
import java.io.FilterInputStream;
import java.io.EOFException;

public class LimitInputStream extends FilterInputStream {
    private int remaining;

    public LimitInputStream(InputStream is, int count) {
        super(is);
        remaining = count;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                  ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        if (remaining <= 0) {
            return -1;
        }
        if (len > remaining) {
            len = remaining;
        }
        len = in.read(b, off, len);
        if (len < 0) {
            throw new EOFException("Unexpected end of stream");
        }
        remaining -= len;
        return len;
    }

    public int read() throws IOException {
        if (remaining <= 0) {
            return -1;
        }
        int c = in.read();
        if (c < 0) {
            throw new EOFException("Unexpected end of stream");
        }
        --remaining;
        return c;
    }

    public int available() throws IOException {
        return Math.min(remaining, in.available());
    }

    public long skip(long n) throws IOException {
        if (n > remaining) {
            n = remaining;
        }
        if (n <= 0) {
            return 0;
        }
        int skipped = (int) in.skip(n);
        remaining -= skipped;
        return skipped;
    }

    public void close() {
        // Do not close underlying stream
    }
}
