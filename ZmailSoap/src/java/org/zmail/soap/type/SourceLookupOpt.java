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

package org.zmail.soap.type;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlEnum;

import org.zmail.common.service.ServiceException;

@XmlEnum
public enum SourceLookupOpt {
    // case must match protocol
    ANY, ALL;

    public static SourceLookupOpt fromString(String s)
    throws ServiceException {
        try {
            return SourceLookupOpt.valueOf(s);
        } catch (IllegalArgumentException e) {
           throw ServiceException.INVALID_REQUEST(
                   "unknown 'SourceLookupOpt' key: " + s + ", valid values: " +
                   Arrays.asList(SourceLookupOpt.values()), null);
        }
    }
}
