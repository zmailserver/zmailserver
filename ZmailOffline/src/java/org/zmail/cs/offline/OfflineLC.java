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
package com.zimbra.cs.offline;

import com.zimbra.common.localconfig.KnownKey;
import com.zimbra.common.util.Constants;

public class OfflineLC {
    public static final KnownKey zdesktop_app_id;
    public static final KnownKey zdesktop_name;
    public static final KnownKey zdesktop_version;
    public static final KnownKey zdesktop_relabel;
    public static final KnownKey zdesktop_buildid;
    public static final KnownKey zdesktop_skins;
    public static final KnownKey zdesktop_redolog_enabled;
    
    public static final KnownKey zdesktop_sync_timer_frequency;
    public static final KnownKey zdesktop_dirsync_min_delay;
    public static final KnownKey zdesktop_dirsync_fail_delay;
    public static final KnownKey zdesktop_account_poll_interval;
    public static final KnownKey zdesktop_reauth_delay;
    public static final KnownKey zdesktop_retry_delay_min;
    public static final KnownKey zdesktop_retry_delay_max;    
    public static final KnownKey zdesktop_client_poll_interval;
    public static final KnownKey zdesktop_retry_limit;
    public static final KnownKey zdesktop_gal_sync_timer_frequency;
    public static final KnownKey zdesktop_gal_sync_interval_secs;
    public static final KnownKey zdesktop_gal_sync_retry_interval_secs;
    public static final KnownKey zdesktop_gal_sync_request_timeout;
    public static final KnownKey zdesktop_gal_sync_trace_enabled;
    public static final KnownKey zdesktop_gal_sync_group_size;
    public static final KnownKey zdesktop_gal_sync_group_interval;
    public static final KnownKey zdesktop_gal_refresh_interval_days;
    
    public static final KnownKey zdesktop_sync_batch_size;
    public static final KnownKey zdesktop_sync_zip_level;
    
    public static final KnownKey zdesktop_sync_messages;
    public static final KnownKey zdesktop_sync_contacts;
    public static final KnownKey zdesktop_sync_appointments;
    public static final KnownKey zdesktop_sync_tasks;
    public static final KnownKey zdesktop_sync_chats;
    public static final KnownKey zdesktop_sync_documents;
    public static final KnownKey zdesktop_sync_gal;
    public static final KnownKey zdesktop_sync_mountpoints;
    
    public static final KnownKey zdesktop_sync_skip_idlist;
    
    public static final KnownKey zdesktop_request_timeout;
    public static final KnownKey zdesktop_authreq_timeout;
    public static final KnownKey http_so_timeout;
    public static final KnownKey http_connection_timeout;
    public static final KnownKey dns_cache_ttl;
    
    public static final KnownKey auth_token_lifetime;

    public static final KnownKey zdesktop_upload_size_limit;
    
    public static final KnownKey zdesktop_yauth_appid;
    public static final KnownKey zdesktop_yab_baseuri;
    public static final KnownKey zdesktop_ymail_baseuri;
    public static final KnownKey zdesktop_imap_fullsync_interval;
    public static final KnownKey zdesktop_contacts_fullsync_interval;
    public static final KnownKey zdesktop_calendar_fullsync_interval;
    public static final KnownKey zdesktop_ybizmail_smtp_host;
    public static final KnownKey zdesktop_ybizmail_smtp_port;
    public static final KnownKey zdesktop_ybizmail_smtp_ssl;
    public static final KnownKey zdesktop_yoauth_consumer_key;
    public static final KnownKey zdesktop_yoauth_consumer_secret;
    public static final KnownKey zdesktop_yoauth_get_req_token_url;
    public static final KnownKey zdesktop_yoauth_get_token_url;
    public static final KnownKey zdesktop_yoauth_get_contacts_url;
    public static final KnownKey zdesktop_yoauth_put_contacts_url;
    public static final KnownKey zdesktop_yoauth_token_expire_period;
    
    public static final KnownKey zdesktop_support_email;
    
    public static final KnownKey zdesktop_min_zcs_version_sync_tgz;
    public static final KnownKey zdesktop_min_zcs_version_cal_no_mime;
    
    public static final KnownKey zdesktop_caldav_enabled;

    public static final KnownKey zdesktop_gab_base_url;
    
    public static final KnownKey zdesktop_log_context_filter;
    public static final KnownKey zdesktop_installation_key;

    public static final KnownKey zdesktop_batched_indexing_size;
    public static final KnownKey zdesktop_mail_disk_streaming_threshold;
    public static final KnownKey zdesktop_mail_file_descriptor_cache_size;
    public static final KnownKey zdesktop_message_cache_size;
    public static final KnownKey zdesktop_volume_compression_threshold;

    public static final KnownKey zdesktop_mobileme_domain =
        new KnownKey("zdesktop_mobileme_domain", "me.com");
    public static final KnownKey zdesktop_mobileme_imap_host =
        new KnownKey("zdesktop_mobileme_imap_host", "mail.me.com");
    public static final KnownKey zdesktop_mobileme_imap_port =
        new KnownKey("zdesktop_mobileme_imap_port", "993");
    public static final KnownKey zdesktop_mobileme_imap_connection_type =
        new KnownKey("zdesktop_mobileme_imap_connection_type", "ssl");
    public static final KnownKey zdesktop_mobileme_smtp_host =
        new KnownKey("zdesktop_mobileme_smtp_host", "smtp.me.com");
    public static final KnownKey zdesktop_mobileme_smtp_port =
        new KnownKey("zdesktop_mobileme_smtp_port", "587");
    public static final KnownKey zdesktop_mobileme_smtp_ssl =
        new KnownKey("zdesktop_mobileme_smtp_ssl", "false");
    public static final KnownKey zdesktop_mobileme_smtp_auth =
        new KnownKey("zdesktop_mobileme_smtp_auth", "true");

    public static final KnownKey zdesktop_backup_dir;
    public static final KnownKey zdesktop_sync_io_exception_limit;
    public static final KnownKey zdesktop_sync_item_io_exception_limit;
    public static final KnownKey zdesktop_sync_io_exception_rate;
    public static final KnownKey zdesktop_heapdump_ftp;
    public static final KnownKey zdesktop_heapdump_dir;
    public static final KnownKey zdesktop_heapdump_enabled;
    public static final KnownKey zdesktop_heapdump_ftp_user;
    public static final KnownKey zdesktop_heapdump_ftp_psw;
    public static final KnownKey zdesktop_mailbox_cache;
    public static final KnownKey zdesktop_archive_dir;

    static void init() {
        // This method is there to guarantee static initializer of this
        // class is run.
    }

    static {
    	zdesktop_app_id = new KnownKey("zdesktop_app_id");

    	// UserAgent name of the Zimbra Desktop software.
        zdesktop_name = new KnownKey("zdesktop_name");
        zdesktop_name.setDefault("Zimbra Desktop");
        
        // Release label such as R or BETA
        zdesktop_relabel = new KnownKey("zdesktop_relabel");
        zdesktop_relabel.setDefault("ALPHA");
        
        // Version number of the Zimbra Desktop software.
        zdesktop_version = new KnownKey("zdesktop_version");
        zdesktop_version.setDefault("2.0");
        
        // Build number of the Zimbra Desktop software.
        zdesktop_buildid = new KnownKey("zdesktop_buildid");
        zdesktop_buildid.setDefault("1");
        
        // Comma delimited list of installed skins.
        zdesktop_skins = new KnownKey("zdesktop_skins");
        zdesktop_skins.setDefault("carbon");
        
        // Whether to use redolog.  Default false.
        zdesktop_redolog_enabled = new KnownKey("zdesktop_redolog_enabled");
        zdesktop_redolog_enabled.setDefault("false");
        
        // Main sync loop timer firing frequency. Default 5000 (5 seconds)
        zdesktop_sync_timer_frequency = new KnownKey("zdesktop_sync_timer_frequency");
        zdesktop_sync_timer_frequency.setDefault(Long.toString(5 * Constants.MILLIS_PER_SECOND));
        
        // Minimum delay in milliseconds between two directory sync executions. Default 15000 (15 seconds)
        zdesktop_dirsync_min_delay = new KnownKey("zdesktop_dirsync_min_delay");
        zdesktop_dirsync_min_delay.setDefault(Long.toString(15 * Constants.MILLIS_PER_SECOND));
        
        // Minimum delay in milliseconds after a directory sync failure. Default 900000 (15 minutes)
        zdesktop_dirsync_fail_delay = new KnownKey("zdesktop_dirsync_fail_delay");
        zdesktop_dirsync_fail_delay.setDefault(Long.toString(15 * Constants.MILLIS_PER_MINUTE));
        
        // Minimum delay in milliseconds between two directory sync executions for the same account. Default 3600000 (1 hour).
        zdesktop_account_poll_interval = new KnownKey("zdesktop_account_poll_interval");
        zdesktop_account_poll_interval.setDefault(Long.toString(Constants.MILLIS_PER_HOUR));
        
        // How often Ajax client should poll for updates. Default 15 (seconds).
        zdesktop_client_poll_interval = new KnownKey("zdesktop_client_poll_interval");
        zdesktop_client_poll_interval.setDefault("60");
        
	    
        // "Minimum delay in milliseconds to reauth after auth failure. Default 3600000 (1 hour).
        zdesktop_reauth_delay = new KnownKey("zdesktop_reauth_delay");
        zdesktop_reauth_delay.setDefault(Long.toString(Constants.MILLIS_PER_HOUR));

        // Minimum delay in milliseconds to retry after becoming offline or encountering error. Default 60000 (1 minute).
        zdesktop_retry_delay_min = new KnownKey("zdesktop_retry_delay_min");
        zdesktop_retry_delay_min.setDefault(Long.toString(Constants.MILLIS_PER_MINUTE));

        // "Maximum delay in milliseconds to retry after becoming offline or encountering error. Default 600000 (10 minutes).
        zdesktop_retry_delay_max = new KnownKey("zdesktop_retry_delay_max");
        zdesktop_retry_delay_max.setDefault(Long.toString(10 * Constants.MILLIS_PER_MINUTE));

        // Number of times to retry if sync fails. Default 2.
        zdesktop_retry_limit = new KnownKey("zdesktop_retry_limit");
        zdesktop_retry_limit.setDefault("2");

        // How often offline GAL is delta-sync'ed. Default every 12 hours.
        zdesktop_gal_sync_interval_secs = new KnownKey("zdesktop_gal_sync_interval_seconds");
        zdesktop_gal_sync_interval_secs.setDefault("43200");

        // How often offline GAL retry for failed items. Default every 1 hours.
        zdesktop_gal_sync_retry_interval_secs = new KnownKey("zdesktop_gal_sync_retry_interval_seconds");
        zdesktop_gal_sync_retry_interval_secs.setDefault("3600");

        // How often offline GAL timer fires. Default every 5 minutes.
        zdesktop_gal_sync_timer_frequency = new KnownKey("zdesktop_gal_sync_timer_frequency");
        zdesktop_gal_sync_timer_frequency.setDefault("300000");

        // HTTP GAL sync request timeout in milliseconds while waiting for response. A value of zero means no timeout. Default 5 minutes.
        zdesktop_gal_sync_request_timeout = new KnownKey("zdesktop_galsync_request_timeout");
        zdesktop_gal_sync_request_timeout.setDefault("300000");

        // Whether to enable GAL sync trace logging. Default false
        zdesktop_gal_sync_trace_enabled = new KnownKey("zdesktop_gal_sync_trace_enabled");
        zdesktop_gal_sync_trace_enabled.setDefault("false");

        // Number of entries to fetch in each GetContactsRequest in GAL sync
        zdesktop_gal_sync_group_size = new KnownKey("zdesktop_gal_sync_group_size");
        zdesktop_gal_sync_group_size.setDefault("500");

        // Number of milliseconds between each GetContactsRequest in GAL sync
        zdesktop_gal_sync_group_interval = new KnownKey("zdesktop_gal_sync_group_interval");
        zdesktop_gal_sync_group_interval.setDefault("5000");

        // How often offline GAL is full-sync'ed. Default every 30 days.
        zdesktop_gal_refresh_interval_days = new KnownKey("zdesktop_gal_refresh_interval_days");
        zdesktop_gal_refresh_interval_days.setDefault("30");

        // Max number of messages to download in each transaction. Default 25.
        zdesktop_sync_batch_size = new KnownKey("zdesktop_sync_batch_size");
        zdesktop_sync_batch_size.setDefault("25");

        // Zip compression level for batch message sync. Default 0 (NO_COMPRESSION).
        zdesktop_sync_zip_level = new KnownKey("zdesktop_sync_zip_level");
        zdesktop_sync_zip_level.setDefault("0");

        // Whether to sync messages. Default true
        zdesktop_sync_messages = new KnownKey("zdesktop_sync_messages");
        zdesktop_sync_messages.setDefault("true");

        // Whether to sync contacts. Default true
        zdesktop_sync_contacts = new KnownKey("zdesktop_sync_contacts");
        zdesktop_sync_contacts.setDefault("true");

        // Whether to sync appointments. Default true
        zdesktop_sync_appointments = new KnownKey("zdesktop_sync_appointments");
        zdesktop_sync_appointments.setDefault("true");

        // Whether to sync tasks. Default true
        zdesktop_sync_tasks = new KnownKey("zdesktop_sync_tasks");
        zdesktop_sync_tasks.setDefault("true");

        // Whether to sync chats. Default true
        zdesktop_sync_chats = new KnownKey("zdesktop_sync_chats");
        zdesktop_sync_chats.setDefault("true");

        // Whether to sync documents. Default true
        zdesktop_sync_documents = new KnownKey("zdesktop_sync_documents");
        zdesktop_sync_documents.setDefault("true");

        // Whether to sync GAL. Default true
        zdesktop_sync_gal = new KnownKey("zdesktop_sync_gal");
        zdesktop_sync_gal.setDefault("true");

        // Whether to sync mountpoints. Default true
        zdesktop_sync_mountpoints = new KnownKey("zdesktop_sync_mountpoints");
        zdesktop_sync_mountpoints.setDefault("true");

        // Comma delimited list of item IDs to skip during sync.  Default empty.
        zdesktop_sync_skip_idlist = new KnownKey("zdesktop_sync_skip_idlist");
        zdesktop_sync_skip_idlist.setDefault("");

        // Number of seconds before auth token expires. Default 31536000 (1 year).
        auth_token_lifetime = new KnownKey("auth_token_lifetime");
        auth_token_lifetime.setDefault("31536000"); // 365 * 24 * 3600
        
        // Number of seconds a resolved address stays valid
        dns_cache_ttl = new KnownKey("dns_cache_ttl");
        dns_cache_ttl.setDefault("10");

        // HTTP request timeout in milliseconds while waiting for response. A value of zero means no timeout. Default 30000 (30 seconds).
        zdesktop_request_timeout = new KnownKey("zdesktop_request_timeout");
        zdesktop_request_timeout.setDefault("30000");

        //HTTP request timeout in milliseconds while waiting for auth response. A value of zero means no timeout. Default 6000 (6 seconds).
        zdesktop_authreq_timeout = new KnownKey("zdesktop_authreq_timeout");
        zdesktop_authreq_timeout.setDefault("6000");

        // Socket timeout (SO_TIMEOUT) in milliseconds while waiting for data. A value of zero means no timeout. Default 30000 (30 seconds).
        http_so_timeout = new KnownKey("http_so_timeout");
        http_so_timeout.setDefault("30000");

        // Timeout in milliseconds while waiting for connection to establish. A value of zero means no timeout. Default 30000 (30 seconds).
        http_connection_timeout = new KnownKey("http_connection_timeout");
        http_connection_timeout.setDefault("30000");

        // Message size limit for uploading to server in number of bytes
        zdesktop_upload_size_limit = new KnownKey("zdesktop_upload_size_limit");
        zdesktop_upload_size_limit.setDefault("1073741824"); // 1024 * 1024 * 1024

        // appid for yauth with rw access to ab and mail
        zdesktop_yauth_appid = new KnownKey("zdesktop_yauth_appid");
        zdesktop_yauth_appid.setDefault("0YbgbonAkY2iNypMZQOONB8mNDSJkrfBlr3wgxc-");
        
        // base uri for yab
        zdesktop_yab_baseuri = new KnownKey("zdesktop_yab_baseuri");
        zdesktop_yab_baseuri.setDefault("http://address.yahooapis.com/v1");

        // base uri for ymail
        zdesktop_ymail_baseuri = new KnownKey("zdesktop_ymail_baseuri");
        zdesktop_ymail_baseuri.setDefault("http://mail.yahooapis.com/ws/mail/v1.1/soap");
        
        //yahoo oauth consumer key
        zdesktop_yoauth_consumer_key = new KnownKey("zdesktop_yoauth_consumer_key");
//        zdesktop_yoauth_consumer_key.setDefault("dj0yJmk9TWsybDA5aWNsSTVxJmQ9WVdrOWVsbGhjMmhMTmpRbWNHbzlNVEEwTWpJek9EYzJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD1iMA--");
        zdesktop_yoauth_consumer_key.setDefault("dj0yJmk9U1FZQXpQalFNb3BNJmQ9WVdrOWMxRXlZbkpoTlRnbWNHbzlNVFE1TURJME5qWXkmcz1jb25zdW1lcnNlY3JldCZ4PWQ2");

        //yahoo oauth consumer secret
        zdesktop_yoauth_consumer_secret = new KnownKey("zdesktop_yoauth_consumer_secret");
//        zdesktop_yoauth_consumer_secret.setDefault("5c593062703d28de06085806c6016e4edce729b4");
        zdesktop_yoauth_consumer_secret.setDefault("4008a88d1ef86a9f90a058bdc9d16789dfa57c25");
        
        //yahoo oauth get request token url
        zdesktop_yoauth_get_req_token_url = new KnownKey("zdesktop_yoauth_get_req_token_url");
        zdesktop_yoauth_get_req_token_url.setDefault("https://api.login.yahoo.com/oauth/v2/get_request_token");
        
        //yahoo oauth get (access) token url
        zdesktop_yoauth_get_token_url = new KnownKey("zdesktop_yoauth_get_token_url");
        zdesktop_yoauth_get_token_url.setDefault("https://api.login.yahoo.com/oauth/v2/get_token");
        
        //yahoo oauth get contacts url
        zdesktop_yoauth_get_contacts_url = new KnownKey("zdesktop_yoauth_get_contacts_url");
        zdesktop_yoauth_get_contacts_url.setDefault("http://social.yahooapis.com/v1/user/%s/contacts?view=sync&rev=%s");
        
        //yahoo oauth put contact url
        zdesktop_yoauth_put_contacts_url = new KnownKey("zdesktop_yoauth_put_contacts_url");
        zdesktop_yoauth_put_contacts_url.setDefault("http://social.yahooapis.com/v1/user/%s/contacts");
        
        //yahoo oauth token expire period (1 hour)
        zdesktop_yoauth_token_expire_period = new KnownKey("zdesktop_yoauth_token_expire_period");
        zdesktop_yoauth_token_expire_period.setDefault(3600*1000);
        
        //Max number of minutes between full sync of IMAP
        zdesktop_imap_fullsync_interval = new KnownKey("zdesktop_imap_fullsync_interval");
        zdesktop_imap_fullsync_interval.setDefault("60"); // 1 hour

        // Max number of minutes between full sync of contacts
        zdesktop_contacts_fullsync_interval = new KnownKey("zdesktop_ab_fullsync_interval");
        zdesktop_contacts_fullsync_interval.setDefault("15"); // 15 minutes


        // Max number of minutes between full sync of calendar
        zdesktop_calendar_fullsync_interval = new KnownKey("zdesktop_caldav_fullsync_interval");
        zdesktop_calendar_fullsync_interval.setDefault("15"); // 15 minutes
        
        // Yahoo bizmail SMTP host
        zdesktop_ybizmail_smtp_host = new KnownKey("zdesktop_ybizmail_smtp_host");
        zdesktop_ybizmail_smtp_host.setDefault("smtp.bizmail.yahoo.com");
        

        // Yahoo bizmail SMTP port
        zdesktop_ybizmail_smtp_port = new KnownKey("zdesktop_ybizmail_smtp_port");
        zdesktop_ybizmail_smtp_port.setDefault("465");
        
        // Yahoo bizmail SMTP SSL enabled
        zdesktop_ybizmail_smtp_ssl = new KnownKey("zdesktop_ybizmail_smtp_ssl");
        zdesktop_ybizmail_smtp_ssl.setDefault("true");

        // support email address
        zdesktop_support_email = new KnownKey("zdesktop_support_email");
        zdesktop_support_email.setDefault("zdesktop-report@zimbra.com");

        // min zcs server version to support tgz format for sync
        zdesktop_min_zcs_version_sync_tgz = new KnownKey("zdesktop_min_zcs_version_sync_tgz");
        zdesktop_min_zcs_version_sync_tgz.setDefault("5.0.11");

        // min zcs server version to support fetching mime separately
        zdesktop_min_zcs_version_cal_no_mime = new KnownKey("zdesktop_min_zcs_version_cal_no_mime");
        zdesktop_min_zcs_version_cal_no_mime.setDefault("5.0.15");

        // whether to enable caldav sync
        zdesktop_caldav_enabled = new KnownKey("zdesktop_caldav_enabled", "false");

        // Google contacts base url
        zdesktop_gab_base_url = new KnownKey("zdesktop_gab_base_url");
        zdesktop_gab_base_url.setDefault("http://www.google.com/m8/feeds");
        
        // comma delimited list of log context items to filter out
        zdesktop_log_context_filter = new KnownKey("zdesktop_log_context_filter", "name,aname,ip,ua");
        // unique key of the installation instance
        zdesktop_installation_key = new KnownKey("zdesktop_installation_key", null);
        // batch size to use when indexing data
        zdesktop_batched_indexing_size = new KnownKey("zdesktop_batched_indexing_size", "0");
        // number of bytes to hold in memory before streaming to disk
        zdesktop_mail_disk_streaming_threshold = new KnownKey("zdesktop_mail_disk_streaming_threshold", "1048576");
        // maximum number of file descriptors that are opened for accessing message content
        zdesktop_mail_file_descriptor_cache_size = new KnownKey("zdesktop_mail_file_descriptor_cache_size", "5");
        // maximum number of JavaMail MimeMessage objects in the message cache
        zdesktop_message_cache_size = new KnownKey("zdesktop_message_cache_size", "12");
        // size threshold for blob store compression
        zdesktop_volume_compression_threshold = new KnownKey("zdesktop_volume_compression_threshold", "131072");

        zdesktop_backup_dir = new KnownKey("zdesktop_backup_dir", "${zimbra_home}/../zd_backup");
        
        //number of io exceptions in a single sync which triggers abort
        zdesktop_sync_io_exception_limit = new KnownKey("zdesktop_sync_io_exception_limit", "10");
        //number of io exceptions for a given item before it is considered 'bad' and skipped
        zdesktop_sync_item_io_exception_limit = new KnownKey("zdesktop_sync_item_io_exception_limit", "3");
        //percentage of io exceptions/total items synced which triggers abort
        zdesktop_sync_io_exception_rate = new KnownKey("zdesktop_sync_io_exception_rate", "50");

        //by default disabled in localconfig
        zdesktop_heapdump_enabled = new KnownKey("zdesktop_heapdump_enabled", "false");
        zdesktop_heapdump_dir = new KnownKey("zdesktop_heapdump_dir", "${zimbra_home}/heapdump");
        //ftp account used for uploading heap dumps
        zdesktop_heapdump_ftp = new KnownKey("zdesktop_heapdump_ftp", "ftp.zimbra.com");
        zdesktop_heapdump_ftp_user = new KnownKey("zdesktop_heapdump_ftp_user", "zdthrdump");
        zdesktop_heapdump_ftp_psw = new KnownKey("zdesktop_heapdump_ftp_psw", "kvlprG");
        //mailbox cache size for LRU used in OfflineMailboxMananer
        zdesktop_mailbox_cache = new KnownKey("zdesktop_mailbox_cache", "50");
        //archive dir
        zdesktop_archive_dir = new KnownKey("zdesktop_archive_dir", "${zimbra_tmp_directory}/archive");
    }

    public static String getFullVersion() {
        return zdesktop_version.value() + "_" + zdesktop_buildid.value() + "_" + getOSShortName();
    }

    private static String getOSShortName() {
        String os = System.getProperty("os.name");
        int sp = os.indexOf(' ');
        return sp > 0 ? os.substring(0, sp) : os;
    }
}