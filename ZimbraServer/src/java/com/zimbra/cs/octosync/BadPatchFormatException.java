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
package com.zimbra.cs.octosync;

@SuppressWarnings("serial")
public class BadPatchFormatException extends PatchException
{
    public BadPatchFormatException()
    {
    }

    public BadPatchFormatException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BadPatchFormatException(String message)
    {
        super(message);
    }

    public BadPatchFormatException(Throwable cause)
    {
        super(cause);
    }
}
