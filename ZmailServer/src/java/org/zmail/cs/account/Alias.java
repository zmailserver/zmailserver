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

/*
 * Created on Nov 24, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.zmail.cs.account;

import java.util.Map;

import org.zmail.cs.account.accesscontrol.TargetType;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ZmailLog;

/**
 * @author anandp
 *
 */
public class Alias extends MailTarget {
    
    private NamedEntry mTarget     = null;
    private boolean mIsDangling    = false;;

    public Alias(String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(name, id, attrs, null, prov);
    }
    
    @Override
    public EntryType getEntryType() {
        return EntryType.ALIAS;
    }
    
    public NamedEntry getTarget(Provisioning prov) {
        // if we had already searched and know the target is not there, 
        // don't bother to search again
        if (mIsDangling)
            return null;
        
        // if we had already searched and know the target, return it
        if (mTarget != null)
            return mTarget;
        
        try {
            mTarget = prov.getAliasTarget(this, false);
        } catch (ServiceException e) {
            ZmailLog.account.warn("cannot find target " +  getId() + " for alias " + getName(), e);
        }
        
        // set the dangling flag so we don't search again next time when called
        if (mTarget == null)
            mIsDangling = true;
        
        return mTarget;
    }

    public String getTargetName(Provisioning prov) throws ServiceException {
        NamedEntry target = getTarget(prov);
        if (target == null)
            return null;
        return target.getName();
    }
    
    public String getTargetUnicodeName(Provisioning prov) throws ServiceException {
        String targetName = getTargetName(prov);
        if (targetName == null)
            return null;
        return IDNUtil.toUnicodeEmail(targetName);
    }
    
    public TargetType getTargetType(Provisioning prov) throws ServiceException {
        NamedEntry target = getTarget(prov);
        if (target == null)
            return null;
        
        if (target instanceof CalendarResource)
            return TargetType.calresource;
        else if (target instanceof Account)
            return TargetType.account;
        else if (target instanceof DistributionList)
            return TargetType.dl;
        else if (target instanceof DynamicGroup)
            return TargetType.group;
        else
            throw ServiceException.FAILURE("invalid target type for alias " + getName(), null);
    }

}
