/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package com.zimbra.cs.security.sasl;

import com.zimbra.cs.server.NioUtil;

import javax.security.sasl.SaslException;

import org.apache.mina.core.buffer.IoBuffer;

import java.nio.ByteBuffer;

public class SaslOutputBuffer {
    private final int mMaxSize;
    private ByteBuffer mBuffer;

    private static final int MINSIZE = 512;

    public SaslOutputBuffer(int maxSize) {
        this(Math.min(MINSIZE, maxSize), maxSize);
    }

    public SaslOutputBuffer(int minSize, int maxSize) {
        if (minSize > maxSize) {
            throw new IllegalArgumentException("minSize > maxSize");
        }
        mBuffer = ByteBuffer.allocate(minSize);
        mMaxSize = maxSize;
    }

    public void put(IoBuffer buf) {
        put(buf.buf());
    }

    public void put(ByteBuffer bb) {
        if (isFull()) return;
        if (bb.remaining() > mBuffer.remaining()) {
            int minSize = Math.min(bb.remaining(), mMaxSize);
            mBuffer = NioUtil.expand(mBuffer, minSize, mMaxSize);
        }
        int len = Math.min(mBuffer.remaining(), bb.remaining());
        int pos = mBuffer.position();
        bb.get(mBuffer.array(), pos, len);
        mBuffer.position(pos + len);
    }

    public void put(byte b) {
        if (isFull()) return;
        if (!mBuffer.hasRemaining()) {
            mBuffer = NioUtil.expand(mBuffer, 1, mMaxSize);
        }
        mBuffer.put(b);
    }

    public int size() {
        return mBuffer.position();
    }

    public boolean isFull() {
        return mBuffer.position() >= mMaxSize;
    }

    public byte[] wrap(SaslSecurityLayer security) throws SaslException {
        return security.wrap(mBuffer.array(), 0, mBuffer.position());
    }

    public void clear() {
        mBuffer.clear();
    }
}
