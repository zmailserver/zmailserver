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
package org.zmail.cs.account.offline;

import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.AccessManager;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthToken;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.accesscontrol.Right;

public class OfflineAccessManager extends AccessManager {

    @Override
    public boolean isAdequateAdminAccount(Account acct) {
        return true;
    }
    
	@Override
	public boolean canAccessAccount(AuthToken at, Account target,
			boolean asAdmin) throws ServiceException {
		return true;
	}

	@Override
	public boolean canAccessAccount(AuthToken at, Account target)
			throws ServiceException {
		return true;
	}

	@Override
	public boolean canAccessAccount(Account credentials, Account target,
			boolean asAdmin) throws ServiceException {
		return true;
	}

	@Override
	public boolean canAccessAccount(Account credentials, Account target)
			throws ServiceException {
		return true;
	}

	@Override
	public boolean canAccessDomain(AuthToken at, String domainName)
			throws ServiceException {
		return true;
	}

	@Override
	public boolean canAccessDomain(AuthToken at, Domain domain)
			throws ServiceException {
		return true;
	}
	
	@Override
	public  boolean canAccessCos(AuthToken at, Cos cos)
			throws ServiceException {
		return true;
	}
	
	@Override
	public boolean canAccessEmail(AuthToken at, String email)
			throws ServiceException {
		return true;
	}

	@Override
	public boolean canModifyMailQuota(AuthToken at, Account targetAccount,
			long mailQuota) throws ServiceException {
		return true;
	}

	@Override
	public boolean isDomainAdminOnly(AuthToken at) {
		return false;
	}
	
	@Override
	public boolean canDo(AuthToken grantee, Entry target, Right rightNeeded, boolean asAdmin) {
	    return true;
	}
    
	@Override
	public boolean canDo(Account grantee, Entry target, Right rightNeeded, boolean asAdmin) {
	    return true;
	}
	
	@Override
	public boolean canDo(String grantee, Entry target, Right rightNeeded, boolean asAdmin) {
	    return true;
	}
	
	@Override
    public boolean canGetAttrs(Account grantee,   Entry target, Set<String> attrs, boolean asAdmin) throws ServiceException {
        return true;
    }
	
	@Override
    public boolean canGetAttrs(AuthToken grantee, Entry target, Set<String> attrs, boolean asAdmin) throws ServiceException {
	    return true;
    }
	
	@Override
    public boolean canSetAttrs(Account grantee,   Entry target, Set<String> attrs, boolean asAdmin) throws ServiceException {
        return true;
    }
	    
    @Override
    public boolean canSetAttrs(AuthToken grantee, Entry target, Set<String> attrs, boolean asAdmin) throws ServiceException {
        return true;
    }
	
	@Override
    public boolean canSetAttrs(Account grantee,   Entry target, Map<String, Object> attrs, boolean asAdmin) throws ServiceException {
	    return true;
    }
	
	@Override
    public boolean canSetAttrs(AuthToken grantee, Entry target, Map<String, Object> attrs, boolean asAdmin) throws ServiceException {
	    return true;
    }

    @Override
    public boolean canAccessGroup(AuthToken at, Group group)
            throws ServiceException {
        return true;
    }
    
    @Override
    public boolean canAccessGroup(Account credentials, Group group, boolean asAdmin)
            throws ServiceException {
        return true;
    }

    @Override
    public boolean canCreateGroup(AuthToken at, String groupEmail)
            throws ServiceException {
        return true;
    }

    @Override
    public boolean canCreateGroup(Account credentials, String groupEmail)
            throws ServiceException {
        return true;
    }
}
