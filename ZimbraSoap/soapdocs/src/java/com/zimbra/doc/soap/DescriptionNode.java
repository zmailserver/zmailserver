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

package com.zimbra.doc.soap;

import java.util.List;

public interface DescriptionNode {
    public DescriptionNode getParent();
    public List<DescriptionNode> getChildren();
    public void writeDescription(StringBuilder desc, int depth);
    public String getDescription();
    public String getSummary();
    public String getXPath();
    public String xmlLinkTargetName();
    public String tableLinkTargetName();
}
