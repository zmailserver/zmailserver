/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.dav.service.method;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.dav.DavContext;
import com.zimbra.cs.dav.DavException;
import com.zimbra.cs.dav.resource.Collection;
import com.zimbra.cs.dav.resource.Notebook;

public class Copy extends Move {
    public static final String COPY  = "COPY";
    public String getName() {
        return COPY;
    }

    public void handle(DavContext ctxt) throws DavException, IOException, ServiceException {
        String newName = null;        
        if (mir instanceof Collection || mir instanceof Notebook)
            newName = ctxt.getNewName();  
        if (ctxt.isOverwriteSet()) {
            mir.moveORcopyWithOverwrite(ctxt, col, newName, false);
        } else {
            mir.copy(ctxt, col, newName);
        }
        ctxt.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
