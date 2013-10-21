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
package org.zmail.cs.account.ldap.entry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeClass;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Signature;
import org.zmail.cs.ldap.LdapUtil;

/**
 * 
 * @author pshao
 *
 */
public abstract class LdapSignatureBase extends Signature implements LdapEntry {

    protected LdapSignatureBase(Account acct, String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(acct, name, id, attrs, prov);
    }
    
    /*
     * For backward compatibility (in the case when upgrade script is not run) to preserve the 
     * signature on the account, we need to check for presence of A_zmailPrefMailSignature.
     * 
     * We also need to check for presence of A_zmailPrefSignatureName, because in the new scheme
     * a signature can have a name but not a value.
     * 
     * The account signature is considered present if either A_zmailPrefMailSignature or 
     * A_zmailPrefSignatureName on the account is set.  If A_zmailPrefSignatureName is not set,
     * getAccountSignature uses the account's name for the signature name.
     * 
     * Note: we do not set/writeback to LDAP the default signature to the account signature even 
     * if it is the only signature.  The upgrade script should do that.
     * 
     */
    private static boolean hasAccountSignature(Account acct) {
        return (acct.getAttr(Provisioning.A_zmailPrefMailSignature) != null || 
                acct.getAttr(Provisioning.A_zmailSignatureName) != null ||
                acct.getAttr(Provisioning.A_zmailSignatureId) != null);
    }
    
    public static Signature getAccountSignature(Provisioning prov, Account acct) throws ServiceException {
        if (!hasAccountSignature(acct))
            return null;
        
        Map<String, Object> attrs = new HashMap<String, Object>();
        Set<String> signatureAttrs = AttributeManager.getInstance().getAttrsInClass(AttributeClass.signature);
        
        for (String name : signatureAttrs) {
            String value = acct.getAttr(name, null);
            if (value != null) 
                attrs.put(name, value);            
        }
        
        // for backward compatibility, we recognize an existing signature on the account if 
        // it has a A_zmailPrefMailSignature value.  We write back name and id if they are not
        // present.  This write back should happen only once for the account.
        Map<String, Object> putbackAttrs = new HashMap<String, Object>();
        String sigName = acct.getAttr(Provisioning.A_zmailSignatureName);
        if (sigName == null) {
            sigName = acct.getName();
            putbackAttrs.put(Provisioning.A_zmailSignatureName, sigName);
        }
        String sigId = acct.getAttr(Provisioning.A_zmailSignatureId);
        if (sigId == null) {
            sigId = LdapUtil.generateUUID();
            putbackAttrs.put(Provisioning.A_zmailSignatureId, sigId);
        }
        if (putbackAttrs.size() > 0)
            prov.modifyAttrs(acct, putbackAttrs);
                
        return new Signature(acct, sigName, sigId, attrs, prov);        
    }
    
    /*
     * Account entry "holds" a signature "slot".  This is because most of the accounts have only 
     * one signature and we don't want to create an ldap signature entry for that.
     */ 
    public static boolean isAccountSignature(Account acct, String signatureId) {
        String acctSigId = acct.getAttr(Provisioning.A_zmailSignatureId);
        return (signatureId.equals(acctSigId));
    }
    
    public static void modifyAccountSignature(Provisioning prov, Account acct, Map<String, Object>signatureAttrs) throws ServiceException {
        prov.modifyAttrs(acct, signatureAttrs);
    }
    
    public static void createAccountSignature(Provisioning prov, Account acct, Map<String, Object>signatureAttrs, boolean setAsDefault) throws ServiceException {
        if (setAsDefault) {
            String signatureId = (String)signatureAttrs.get(Provisioning.A_zmailSignatureId);
            signatureAttrs.put(Provisioning.A_zmailPrefDefaultSignatureId, signatureId);
        }
        prov.modifyAttrs(acct, signatureAttrs);
    }

    public static void deleteAccountSignature(Provisioning prov, Account acct) throws ServiceException {
        Map<String, Object> attrs = new HashMap<String, Object>();
        Set<String> signatureAttrs = AttributeManager.getInstance().getAttrsInClass(AttributeClass.signature);
        
        for (String name : signatureAttrs) {
            String value = acct.getAttr(name, null);
            if (value != null) attrs.put("-" + name, value);            
        }
        
        prov.modifyAttrs(acct, attrs);
    }
    

}
