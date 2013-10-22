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

package com.zimbra.soap.admin.type;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlEnum;

import com.zimbra.common.service.ServiceException;

@XmlEnum
public enum GalMode {
    // case must match protocol
    both, ldap, zimbra;

    public static GalMode fromString(String s) throws ServiceException {
        try {
            return GalMode.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw ServiceException.INVALID_REQUEST(
                    "invalid value: "+s+", valid values: "
                    + Arrays.asList(values()), null);
        }
    }
    public boolean isBoth() { return this == both;}
    public boolean isLdap() { return this == ldap;}
    public boolean isZimbra() { return this == zimbra;}
}
