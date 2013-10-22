/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2009, 2012 VMware, Inc.
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
package org.zmail.common;

public class user_info
{
    public String zmailYahooID;
    public String username;
    public String displayname;
    public String password;
    public String ZmailAcctStatus;
    public String description;
    public String zmailCOSId;
    public String zmailMailCanonicalAddress;
    public String zmailDomainAdmin;
    public String zmailPrefMailForwardingAddress;
    //owner_yid,reg,product_type,domain,max_accounts,active_accounts, (<---PA_FIELDS)
    //emailaddr:yid:reg:fullname:farm:sledid:silo:privileges          (<---Account fields)
    public static final int OWNERYID=0;
    public static final int PA_REG=OWNERYID+1;
    public static final int PRODUCT_TYPE=PA_REG+1;
    public static final int DOAMIN = PRODUCT_TYPE+1;        
    public static final int MAX_ACCOUNTS= DOAMIN+1;        
    public static final int ACTIVE_ACCOUNTS= MAX_ACCOUNTS+1;   
    
    public static final int PA_FIELDS=ACTIVE_ACCOUNTS+1;
    
    public static final int EMAIL_ADDR=0;
    public static final int USER_YID =EMAIL_ADDR+1;
    public static final int USER_REG= USER_YID+1;
    public static final int USER_FULLNAME=USER_REG+1;    
    public static final int FARM=USER_FULLNAME+1;
    public static final int SLEDID=FARM+1;
    public static final int SILO=SLEDID+1;
    public static final int PRIVILEGES=SILO+1;
}
    	
