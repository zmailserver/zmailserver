/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.milter;

class MilterPacket {
    private final int len;
    private final byte cmd;
    private final byte[] data;

    MilterPacket(int len, byte cmd, byte[] data) {
        this.len = len;
        this.cmd = cmd;
        this.data = data;
    }

    MilterPacket(byte cmd) {
        this.len = 1;
        this.cmd = cmd;
        this.data = null;
    }

    int getLength() {
        return len;
    }

    byte getCommand() {
        return cmd;
    }

    byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(len);
        sb.append(':');
        sb.append((char)cmd);
        sb.append(':');
        if (data != null) {
            for (byte b : data) {
                if (b > 32 &&  b < 127) {
                    sb.append((char)b);
                } else {
                    sb.append("\\");
                    sb.append(b & 0xFF); // make unsigned
                }
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
