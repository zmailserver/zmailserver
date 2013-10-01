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
package com.zimbra.cs.store.file;

import java.io.File;

import com.zimbra.cs.store.Blob;

class VolumeBlob extends Blob {
    private final short volumeId;

    VolumeBlob(File file, short volumeId) {
        super(file);
        this.volumeId = volumeId;
    }

    short getVolumeId() {
        return volumeId;
    }
}
