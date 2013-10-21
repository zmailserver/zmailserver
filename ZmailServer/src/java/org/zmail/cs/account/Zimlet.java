/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account;

import java.util.Map;
import java.util.Set;

public class Zimlet extends NamedEntry {
	public Zimlet(String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(name, id, attrs, null, prov);
    }

    @Override
    public EntryType getEntryType() {
        return EntryType.ZIMLET;
    }
    
    public boolean isEnabled() {
        return getBooleanAttr(Provisioning.A_zmailZimletEnabled, false);
    }
    
    public String getPriority() {
        return getAttr(Provisioning.A_zmailZimletPriority);
    }
    
    public boolean isExtension() {
        return getBooleanAttr(Provisioning.A_zmailZimletIsExtension, false);
    }

    public String getType() {
        return getAttr(Provisioning.A_cn);
    }
    
    public String getDescription() {
        return getAttr(Provisioning.A_zmailZimletDescription);
    }
    
    public boolean isIndexingEnabled() {
        return getBooleanAttr(Provisioning.A_zmailZimletIndexingEnabled, false);
    }
    
    public String getHandlerClassName() {
        return getAttr(Provisioning.A_zmailZimletHandlerClass);
    }
    
    public String getHandlerConfig() {
        return getAttr(Provisioning.A_zmailZimletHandlerConfig);
    }

    public String getServerIndexRegex() {
        return getAttr(Provisioning.A_zmailZimletServerIndexRegex);
    }
    
	
	public boolean checkTarget(String target) {
		Set<String> lTiers = getMultiAttrSet(Provisioning.A_zmailZimletTarget); 
		return ((lTiers == null) ? false : lTiers.contains(target));
	}

}
