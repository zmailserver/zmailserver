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
package org.zmail.cs.mailbox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.zmail.common.service.ServiceException;
import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.Log;
import org.zmail.common.util.LogFactory;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.db.DbMailItem;
import org.zmail.cs.mime.ExpandMimeMessage;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.stats.ZmailPerf;
import org.zmail.cs.store.BlobInputStream;
import org.zmail.cs.store.MailboxBlob;
import org.zmail.cs.store.StoreManager;
import org.zmail.cs.util.JMSession;

public class MessageCache {

    private static final Log sLog = LogFactory.getLog(MessageCache.class);

    private static final class CacheNode {
        CacheNode()  { }
        MimeMessage message;
        MimeMessage expanded;
        long size = 0;
    }

    /** Cache mapping message digest to the corresponding message structure. */
    private static Map<String, CacheNode> sCache = new LinkedHashMap<String, CacheNode>(150, (float) 0.75, true);
    /** Maximum number of items in {@link #sCache}. */
    private static int sMaxCacheSize;
    /** Number of bytes of message data stored in the cache.  This value includes only
     * messages that are read into memory, not streamed from disk. */
    private volatile static long sDataSize = 0;

    static {
        try {
            loadSettings();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadSettings() throws ServiceException {
        sMaxCacheSize = Provisioning.getInstance().getLocalServer().getMessageCacheSize();
        ZmailLog.cache.info("setting message cache size to " + sMaxCacheSize);
    }

    /** Returns the number of messages in the cache. */
    public static int getSize() {
        synchronized (sCache) {
            return sCache.size();
        }
    }

    public static boolean contains(String digest) {
        synchronized (sCache) {
            return sCache.containsKey(digest);
        }
    }

    public static long getDataSize() {
        return sDataSize;
    }

    /** Uncaches any data associated with the given item.  This must be done
     *  before you change the item's content; otherwise, the cache will return
     *  stale data. */
    static void purge(MailItem item) {
        String digest = item.getDigest();
        if (digest != null) {
            sLog.debug("Purging item %d from the message cache.", item.getId());
            purge(item.getDigest());
        }
    }

    /** Uncaches any data associated with the given item.  This must be done
     *  before you change the item's content; otherwise, the cache will return
     *  stale data. */
    static void purge(Mailbox mbox, int itemId) {
        String digest = null;
        try {
            digest = DbMailItem.getBlobDigest(mbox, itemId);
        } catch (ServiceException e) {
            sLog.warn("Unable to uncache message for item %d.", itemId, e);
        }
        if (digest != null) {
            sLog.debug("Purging item %d from the message cache.", itemId);
            purge(digest);
        }
    }

    /** Uncaches any data associated with the given item.  This must be done
     *  before you change the item's content; otherwise, the cache will return
     *  stale data. */
    public static void purge(String digest) {
        if (digest != null) {
            synchronized (sCache) {
                CacheNode node = sCache.remove(digest);
                if (node != null) {
                    sLog.debug("Purged digest %s from the message cache.", digest);
                    sDataSize -= node.size;
                }
            }
        }
    }

    /** Returns a JavaMail {@link javax.mail.internet.MimeMessage}
     *  encapsulating the message content.  If possible, TNEF and uuencoded
     *  attachments are expanded and their components are presented as
     *  standard MIME attachments.  If TNEF or uuencode decoding fails, the
     *  MimeMessage wraps the raw message content.
     *
     * @return A MimeMessage wrapping the RFC822 content of the Message.
     * @throws ServiceException when errors occur opening, reading,
     *                          uncompressing, or parsing the message file,
     *                          or when the file does not exist.
     * @see #getRawContent()
     * @see #getItemContent()
     * @see org.zmail.cs.mime.TnefConverter
     * @see org.zmail.cs.mime.UUEncodeConverter */
    static MimeMessage getMimeMessage(MailItem item, boolean expand) throws ServiceException {
        String digest = item.getDigest();
        CacheNode cnode = null;
        boolean cacheHit = true;
        boolean newNode = false;
        InputStream in = null;

        synchronized (sCache) {
            cnode = sCache.get(digest);
            if (cnode == null) {
                newNode = true;
                cnode = new CacheNode();
            }
        }

        try {
            if (cnode.message == null) {
                sLog.debug("Loading MimeMessage for item %d.", item.getId());
                cacheHit = false;
                try {
                    in = fetchFromStore(item);
                    cnode.message = new Mime.FixedMimeMessage(JMSession.getSession(), in);
                    if (item.getSize() < MESSAGE_CACHE_DISK_STREAMING_THRESHOLD) {
                        cnode.size = item.getSize();
                        // Not the best place to increment the data size, but cacheItem()
                        // won't get called if we're expanding a message for an existing
                        // node.
                        sDataSize += cnode.size;
                    }
                } finally {
                    ByteUtil.closeStream(in);
                }
            }

            if (expand && cnode.expanded == null) {
                sLog.debug("Expanding MimeMessage for item %d.", item.getId());
                cacheHit = false;
                try {
                    ExpandMimeMessage expander = new ExpandMimeMessage(cnode.message);
                    expander.expand();
                    cnode.expanded = expander.getExpanded();
                    if (cnode.expanded != cnode.message) {
                        sDataSize += cnode.size;
                        cnode.size *= 2;
                    }
                } catch (Exception e) {
                    // if the conversion bombs for any reason, revert to the original
                    sLog.warn("MIME converter failed for message %d.  Reverting to original.", item.getId(), e);
                    cnode.expanded = cnode.message;
                }
            }

            if (newNode) {
                cacheItem(digest, cnode);
            }
        } catch (IOException e) {
            throw ServiceException.FAILURE("IOException while retrieving content for item " + item.getId(), e);
        } catch (MessagingException e) {
            throw ServiceException.FAILURE("MessagingException while creating MimeMessage for item " + item.getId(), e);
        } finally {
            ByteUtil.closeStream(in);
        }

        if (cacheHit) {
            sLog.debug("Cache hit for item %d: digest=%s, expand=%b.", item.getId(), item.getDigest(), expand);
            ZmailPerf.COUNTER_MBOX_MSG_CACHE.increment(100);
        } else {
            sLog.debug("Cache miss for item %d: digest=%s, expand=%b.", item.getId(), item.getDigest(), expand);
            ZmailPerf.COUNTER_MBOX_MSG_CACHE.increment(0);
        }

        if (expand) {
            return cnode.expanded;
        } else {
            return cnode.message;
        }
    }

    /** For remote stores, we allow JavaMail to stream message content to
     *  a memory buffer when the messages are sufficiently small.  (For
     *  local stores, all cached messages are backed by disk.) */
    private static final int MESSAGE_CACHE_DISK_STREAMING_THRESHOLD = 4096;

    static InputStream fetchFromStore(MailItem item) throws ServiceException, IOException {
        MailboxBlob mblob = item.getBlob();
        if (mblob == null)
            throw ServiceException.FAILURE("missing blob for id: " + item.getId() + ", change: " + item.getModifiedSequence(), null);

        if (item.getSize() < MESSAGE_CACHE_DISK_STREAMING_THRESHOLD)
            return StoreManager.getInstance().getContent(mblob);
        else
            return new BlobInputStream(mblob.getLocalBlob());
    }

    /**
     * Public API that adds an existing <tt>MimeMessage</tt> to the cache.
     * @param digest the message digest
     * @param original the original message
     * @param expanded the expanded message
     */
    public static void cacheMessage(String digest, MimeMessage original, MimeMessage expanded) {
        sLog.debug("Caching existing MimeMessage, digest=%s.", digest);
        CacheNode cnode = new CacheNode();
        cnode.message = original;
        cnode.expanded = expanded;
        cacheItem(digest, cnode);
    }

    private static void cacheItem(String digest, CacheNode cnode) {
        sLog.debug("Caching MimeMessage for digest %s.", digest);
        synchronized (sCache) {
            sCache.put(digest, cnode);
            // Cache data size was incremented in getMimeMessage().

            // trim the cache if needed
            if (sCache.size() > sMaxCacheSize) {
                Iterator<Map.Entry<String, CacheNode>> it = sCache.entrySet().iterator();
                while (sCache.size() > sMaxCacheSize && it.hasNext()) {
                    Map.Entry<String, CacheNode> entry = it.next();
                    sLog.debug("Pruning digest %s from the cache.", entry.getKey());
                    it.remove();
                    sDataSize -= entry.getValue().size;
                }
            }
        }
    }
}
