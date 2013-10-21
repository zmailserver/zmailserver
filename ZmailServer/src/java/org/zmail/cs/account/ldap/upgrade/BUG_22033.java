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
package org.zmail.cs.account.ldap.upgrade;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.ldap.LdapDIT;
import org.zmail.cs.ldap.IAttributes;
import org.zmail.cs.ldap.LdapClient;
import org.zmail.cs.ldap.LdapServerType;
import org.zmail.cs.ldap.LdapUsage;
import org.zmail.cs.ldap.SearchLdapOptions;
import org.zmail.cs.ldap.ZAttributes;
import org.zmail.cs.ldap.ZLdapContext;
import org.zmail.cs.ldap.ZMutableEntry;
import org.zmail.cs.ldap.ZSearchScope;

public class BUG_22033 extends UpgradeOp {

    enum Type {
        account,
        alias,
        calendarresource,
        config,
        cos,
        datasource,
        distributionlist,
        domain,
        identity,
        server,
        signature,
        xmppcomponent,
        zimlet;
        
        public static Type fromString(String s) throws ServiceException {
            try {
                return Type.valueOf(s);
            } catch (IllegalArgumentException e) {
                throw ServiceException.INVALID_REQUEST("unknown type: " + s, e);
            }
        }
    }
    
    private Type mType;
    
    @Override
    boolean parseCommandLine(CommandLine cl) {
        String[] args = cl.getArgs();
        if (args.length == 1) {
            try {
                mType = Type.fromString(args[0]);
            } catch (ServiceException e) {
                LdapUpgrade.usage(null, this, "invalid type: " + args[0]);
                return false;
            }
        } else if (args.length != 0) {
            LdapUpgrade.usage(null, this, "invalid arg");
            return false;
        }
        return true;
    }
    
    @Override
    void usage(HelpFormatter helpFormatter) {
        printer.println();
        printer.println("args for bug " + bug + ":");
        printer.println("    [type]");
        printer.println();
        printer.println("type can be: (if omitted, it means all objects)");
        for (Type t : Type.values()) {
            printer.println("    " + t.name());
        }
        printer.println();
    }
    
    private static class Bug22033Visitor extends SearchLdapOptions.SearchLdapVisitor {
        
        private UpgradeOp upgradeOp;
        private ZLdapContext modZlc;
        private int numModified = 0;
        
        private Bug22033Visitor(UpgradeOp upgradeOp, ZLdapContext modZlc) {
            super(false);
            this.upgradeOp = upgradeOp;
            this.modZlc = modZlc;
        }
        
        @Override
        public void visit(String dn, IAttributes ldapAttrs) {
            try {
                doVisit(dn, (ZAttributes) ldapAttrs);
            } catch (ServiceException e) {
                upgradeOp.printer.println("entry skipped, encountered error while processing entry at:" + dn);
                upgradeOp.printer.printStackTrace(e);
            }
        }
        
        private void doVisit(String dn, ZAttributes ldapAttrs) throws ServiceException {
            
            String createTime = ldapAttrs.getAttrString("createTimestamp");
            
            ZMutableEntry entry = LdapClient.createMutableEntry();
            entry.setAttr(Provisioning.A_zmailCreateTimestamp, createTime);
            upgradeOp.replaceAttrs(modZlc, dn, entry);
            
            numModified++;
        }
        
        private int getNumModified() {
            return numModified;
        }
    }
    
    @Override
    void doUpgrade() throws ServiceException {
        LdapDIT dit = prov.getDIT();
        String base;
        String query;
        String returnAttrs[] = new String[] {Provisioning.A_objectClass,
                                             Provisioning.A_zmailCreateTimestamp,
                                             "createTimestamp"};
        
        if (mType == null) {
            printer.println("Checking all objects\n");
            
            base = dit.zmailBaseDN();
            query = "(|" +
                     "(objectclass=zmailAccount)" +
                     "(objectclass=zmailAlias)" +
                     "(objectclass=zmailCalendarResource)" +
                     "(objectclass=zmailGlobalConfig)" +
                     "(objectclass=zmailCOS)" +
                     "(objectclass=zmailDataSource)" +
                     "(objectclass=zmailDistributionList)" +
                     "(objectclass=zmailDomain)" +
                     "(objectclass=zmailIdentity)" +
                     "(objectclass=zmailServer)" +
                     "(objectclass=zmailSignature)" +
                     "(objectclass=zmailXMPPComponent)" +
                     "(objectclass=zmailZimletEntry)" +
                     ")";
        } else {
            printer.println("Checking " + mType.name() + " objects...\n");
            
            switch (mType) {
            case account:
                base = dit.mailBranchBaseDN();
                query = "(&(objectclass=zmailAccount)(!(objectclass=zmailCalendarResource)))";
                break;
            case alias:
                base = dit.mailBranchBaseDN();
                query = "(objectclass=zmailAlias)";
                break;
            case calendarresource:
                base = dit.mailBranchBaseDN();
                query = "(objectclass=zmailCalendarResource)";
                break;
            case config:
                base = dit.configDN();
                query = "(objectclass=zmailGlobalConfig)";
                break;
            case cos:
                base = dit.cosBaseDN();
                query = "(objectclass=zmailCOS)";
                break;
            case datasource:
                base = dit.mailBranchBaseDN();
                query = "(objectclass=zmailDataSource)";
                break;
            case distributionlist:
                base = dit.mailBranchBaseDN();
                query = "(objectclass=zmailDistributionList)";
                break;
            case domain:
                base = dit.domainBaseDN();
                query = "(objectclass=zmailDomain)";
                break;
            case identity:
                base = dit.mailBranchBaseDN();
                query = "(objectclass=zmailIdentity)";
                break;
            case server:
                base = dit.serverBaseDN();
                query = "(objectclass=zmailServer)";
                break;
            case signature:
                base = dit.mailBranchBaseDN();
                query = "(objectclass=zmailSignature)";
                break;
            case xmppcomponent:
                base = dit.xmppcomponentBaseDN();
                query = "(objectclass=zmailXMPPComponent)";
                break;
            case zimlet:
                base = dit.zimletBaseDN();
                query = "(objectclass=zmailZimletEntry)";
                break;
            default:
                throw ServiceException.FAILURE("", null);    
            }
        }
        
        query = "(&" + "(!(zmailCreateTimestamp=*))" + query + ")";
        
        ZLdapContext zlc = null; 
        ZLdapContext modZlc = null;
        Bug22033Visitor visitor = null;
        
        try {
            zlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
            modZlc = LdapClient.getContext(LdapServerType.MASTER, LdapUsage.UPGRADE);
            
            visitor = new Bug22033Visitor(this, modZlc);
            
            SearchLdapOptions searchOpts = new SearchLdapOptions(base, getFilter(query), 
                    returnAttrs, SearchLdapOptions.SIZE_UNLIMITED, null, 
                    ZSearchScope.SEARCH_SCOPE_SUBTREE, visitor);

            zlc.searchPaged(searchOpts);

        } catch (ServiceException e) {
            throw ServiceException.FAILURE("unable to list all objects", e);
        } finally {
            LdapClient.closeContext(zlc);
            LdapClient.closeContext(modZlc);
            
            if (visitor != null) {
                printer.println("\nModified " + visitor.getNumModified() + " objects");
            }
        }
    }

}
