/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.offline.jsp;

import java.util.Arrays;

public interface JspConstants {

    public enum JspVerb {
        add, del, exp, imp, mod, rst, idx, rstgal;

        public boolean isAdd() {
            return this == add;
        }

        public boolean isDelete() {
            return this == del;
        }

        public boolean isExport() {
            return this == exp;
        }

        public boolean isImport() {
            return this == imp;
        }

        public boolean isModify() {
            return this == mod;
        }

        public boolean isReset() {
            return this == rst;
        }

        public boolean isReindex() {
            return this == idx;
        }

        public boolean isResetGal() {
            return this == rstgal;
        }

        public static JspVerb fromString(String s) {
            try {
                return s == null ? null : JspVerb.valueOf(s);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("invalid type: " + s + ", valid values: " + Arrays.asList(JspVerb.values()),
                        e);
            }
        }
    }

    public static final String LOCAL_ACCOUNT = "local@host.local";
    public static final String MASKED_PASSWORD = "********";
    public static final String DUMMY_PASSWORD = "topsecret";

    public static final String OFFLINE_REMOTE_HOST = "offlineRemoteHost";
    public static final String OFFLINE_REMOTE_PORT = "offlineRemotePort";
    public static final String OFFLINE_REMOTE_SSL = "offlineRemoteSsl";
}
