/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package com.zimbra.cs.account.ldap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zimbra.common.localconfig.LC;
import com.zimbra.common.account.Key.DomainBy;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Constants;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.Group;
import com.zimbra.cs.account.NamedEntry;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Server;
import com.zimbra.cs.account.ShareLocator;
import com.zimbra.cs.account.UCService;
import com.zimbra.cs.account.XMPPComponent;
import com.zimbra.cs.account.cache.AccountCache;
import com.zimbra.cs.account.cache.DomainCache;
import com.zimbra.cs.account.cache.IAccountCache;
import com.zimbra.cs.account.cache.IDomainCache;
import com.zimbra.cs.account.cache.IMimeTypeCache;
import com.zimbra.cs.account.cache.INamedEntryCache;
import com.zimbra.cs.account.cache.NamedEntryCache;
import com.zimbra.cs.account.cache.DomainCache.GetFromDomainCacheOption;
import com.zimbra.cs.account.ldap.entry.LdapCos;
import com.zimbra.cs.account.ldap.entry.LdapZimlet;
import com.zimbra.cs.mime.MimeTypeInfo;

/**
 * @author pshao
 */
abstract class LdapCache {
    abstract IAccountCache accountCache();
    abstract INamedEntryCache<LdapCos> cosCache();
    abstract INamedEntryCache<ShareLocator> shareLocatorCache();
    abstract IDomainCache domainCache();
    abstract IMimeTypeCache mimeTypeCache();
    abstract INamedEntryCache<Server> serverCache();
    abstract INamedEntryCache<UCService> ucServiceCache();
    abstract INamedEntryCache<LdapZimlet> zimletCache();
    abstract INamedEntryCache<Group> groupCache();
    abstract INamedEntryCache<XMPPComponent> xmppComponentCache();
    
    /**
     * 
     * LRUMapCache
     *
     */
    static class LRUMapCache extends LdapCache {

        private IAccountCache accountCache =
            new AccountCache(
                    LC.ldap_cache_account_maxsize.intValue(),
                    LC.ldap_cache_account_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        private INamedEntryCache<LdapCos> cosCache =
            new NamedEntryCache<LdapCos>(
                    LC.ldap_cache_cos_maxsize.intValue(),
                    LC.ldap_cache_cos_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        private INamedEntryCache<ShareLocator> shareLocatorCache =
                new NamedEntryCache<ShareLocator>(
                        LC.ldap_cache_share_locator_maxsize.intValue(),
                        LC.ldap_cache_share_locator_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        private IDomainCache domainCache =
            new DomainCache(
                    LC.ldap_cache_domain_maxsize.intValue(),
                    LC.ldap_cache_domain_maxage.intValue() * Constants.MILLIS_PER_MINUTE,
                    LC.ldap_cache_external_domain_maxsize.intValue(),
                    LC.ldap_cache_external_domain_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        private IMimeTypeCache mimeTypeCache = new LdapMimeTypeCache();

        private INamedEntryCache<Server> serverCache =
            new NamedEntryCache<Server>(
                    LC.ldap_cache_server_maxsize.intValue(),
                    LC.ldap_cache_server_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        private INamedEntryCache<UCService> ucServiceCache =
            new NamedEntryCache<UCService>(
                    LC.ldap_cache_ucservice_maxsize.intValue(),
                    LC.ldap_cache_ucservice_maxage.intValue() * Constants.MILLIS_PER_MINUTE);
        
        private INamedEntryCache<LdapZimlet> zimletCache =
            new NamedEntryCache<LdapZimlet>(
                    LC.ldap_cache_zimlet_maxsize.intValue(),
                    LC.ldap_cache_zimlet_maxage.intValue() * Constants.MILLIS_PER_MINUTE);


        private INamedEntryCache<Group> groupCache =
            new NamedEntryCache<Group>(
                    LC.ldap_cache_group_maxsize.intValue(),
                    LC.ldap_cache_group_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        private INamedEntryCache<XMPPComponent> xmppComponentCache =
            new NamedEntryCache<XMPPComponent>(
                    LC.ldap_cache_xmppcomponent_maxsize.intValue(),
                    LC.ldap_cache_xmppcomponent_maxage.intValue() * Constants.MILLIS_PER_MINUTE);

        @Override
        IAccountCache accountCache() {
            return accountCache;
        }

        @Override
        INamedEntryCache<LdapCos> cosCache() {
            return cosCache;
        }

        @Override
        IDomainCache domainCache() {
            return domainCache;
        }

        @Override
        INamedEntryCache<Group> groupCache() {
            return groupCache;
        }

        @Override
        IMimeTypeCache mimeTypeCache() {
            return mimeTypeCache;
        }

        @Override
        INamedEntryCache<Server> serverCache() {
            return serverCache;
        }
        
        @Override
        INamedEntryCache<UCService> ucServiceCache() {
            return ucServiceCache;
        }

        @Override
        INamedEntryCache<ShareLocator> shareLocatorCache() {
            return shareLocatorCache;
        }

        @Override
        INamedEntryCache<XMPPComponent> xmppComponentCache() {
            return xmppComponentCache;
        }

        @Override
        INamedEntryCache<LdapZimlet> zimletCache() {
            return zimletCache;
        }
    }
    
    
    /**
     * 
     * NoopCache
     *
     */
    static class NoopCache extends LdapCache {
        
        private IAccountCache accountCache = new NoopAccountCache();
        private INamedEntryCache<LdapCos> cosCache = new NoopNamedEntryCache<LdapCos>();
        private INamedEntryCache<ShareLocator> shareLocatorCache = new NoopNamedEntryCache<ShareLocator>();
        private IDomainCache domainCache = new NoopDomainCache();
        private IMimeTypeCache mimeTypeCache = new NoopMimeTypeCache();
        private INamedEntryCache<Server> serverCache = new NoopNamedEntryCache<Server>();
        private INamedEntryCache<UCService> ucServiceCache = new NoopNamedEntryCache<UCService>();
        private INamedEntryCache<LdapZimlet> zimletCache = new NoopNamedEntryCache<LdapZimlet>();
        private INamedEntryCache<Group> groupCache = new NoopNamedEntryCache<Group>();
        private INamedEntryCache<XMPPComponent> xmppComponentCache = new NoopNamedEntryCache<XMPPComponent>();
        
        
        static class NoopAccountCache implements IAccountCache {
            @Override
            public void clear() {}
            
            @Override
            public void remove(Account entry) {}
            
            @Override
            public void put(Account entry) {}
            
            @Override
            public void replace(Account entry) {}
            
            @Override
            public Account getById(String key) { return null; }
            
            @Override
            public Account getByName(String key) { return null; }
            
            @Override
            public Account getByForeignPrincipal(String key) { return null; }

            @Override
            public int getSize() { return 0; }
            
            @Override
            public double getHitRate() { return 0; } 
        }
        
        static class NoopDomainCache implements IDomainCache {

            @Override
            public void clear() {}

            @Override
            public Domain getByForeignName(String key, GetFromDomainCacheOption option) { return null; }

            @Override
            public Domain getById(String key, GetFromDomainCacheOption option) { return null; }

            @Override
            public Domain getByKrb5Realm(String key, GetFromDomainCacheOption option) { return null; }

            @Override
            public Domain getByName(String key, GetFromDomainCacheOption option) { return null; }

            @Override
            public Domain getByVirtualHostname(String key, GetFromDomainCacheOption option) { return null; }

            @Override
            public void put(DomainBy domainBy, String key, Domain entry) {}

            @Override
            public void remove(Domain entry) {}

            @Override
            public void removeFromNegativeCache(DomainBy domainBy, String key) {}

            @Override
            public void replace(Domain entry) {}

            @Override
            public double getHitRate() { return 0; }

            @Override
            public int getSize() { return 0; }
            
        }
        
        static class NoopNamedEntryCache<E extends NamedEntry> implements INamedEntryCache<E> {

            @Override
            public void clear() {}

            @Override
            public E getById(String key) { return null; }

            @Override
            public E getByName(String key) { return null; }

            @Override
            public double getHitRate() { return 0; }

            @Override
            public int getSize() { return 0; }

            @Override
            public void put(E entry) {}

            @Override
            public void put(List<E> entries, boolean clear) {}

            @Override
            public void remove(String name, String id) {}

            @Override
            public void remove(E entry) {}

            @Override
            public void replace(E entry) {}
        }
        
        static class NoopMimeTypeCache implements IMimeTypeCache {

            private List<MimeTypeInfo> mimeTypes = 
                Collections.unmodifiableList(new ArrayList<MimeTypeInfo>());
            
            @Override
            public void flushCache(Provisioning prov) throws ServiceException {}

            @Override
            public List<MimeTypeInfo> getAllMimeTypes(Provisioning prov)
                    throws ServiceException {
                return mimeTypes;
            }

            @Override
            public List<MimeTypeInfo> getMimeTypes(Provisioning prov,
                    String mimeType) throws ServiceException {
                return mimeTypes;
            }
            
        }

        @Override
        IAccountCache accountCache() {
            return accountCache;
        }

        @Override
        INamedEntryCache<LdapCos> cosCache() {
            return cosCache;
        }

        @Override
        IDomainCache domainCache() {
            return domainCache;
        }

        @Override
        INamedEntryCache<Group> groupCache() {
            return groupCache;
        }

        @Override
        IMimeTypeCache mimeTypeCache() {
            return mimeTypeCache;
        }

        @Override
        INamedEntryCache<Server> serverCache() {
            return serverCache;
        }
        
        @Override
        INamedEntryCache<UCService> ucServiceCache() {
            return ucServiceCache;
        }

        @Override
        INamedEntryCache<ShareLocator> shareLocatorCache() {
            return shareLocatorCache;
        }

        @Override
        INamedEntryCache<XMPPComponent> xmppComponentCache() {
            return xmppComponentCache;
        }

        @Override
        INamedEntryCache<LdapZimlet> zimletCache() {
            return zimletCache;
        }
    }
}





