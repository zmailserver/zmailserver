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

package com.zimbra.cs.mailbox.acl;

import com.zimbra.common.util.memcached.MemcachedKey;
import com.zimbra.cs.memcached.MemcachedKeyPrefix;

public class EffectiveACLCacheKey implements MemcachedKey {
    private String mAcctId;
    private int mFolderId;
    private String mKeyVal;

    public EffectiveACLCacheKey(String acctId, int folderId) {
        mAcctId = acctId;
        mFolderId = folderId;
        mKeyVal = mAcctId + ":" + folderId;
    }

    public String getAccountId() { return mAcctId; }
    public int getFolderId() { return mFolderId; }

    public boolean equals(Object other) {
        if (other instanceof EffectiveACLCacheKey) {
            EffectiveACLCacheKey otherKey = (EffectiveACLCacheKey) other;
            return mKeyVal.equals(otherKey.mKeyVal);
        }
        return false;
    }

    public int hashCode()    { return mKeyVal.hashCode(); }
    public String toString() { return mKeyVal; }

    // MemcachedKey interface
    public String getKeyPrefix() { return MemcachedKeyPrefix.EFFECTIVE_FOLDER_ACL; }
    public String getKeyValue() { return mKeyVal; }
}
