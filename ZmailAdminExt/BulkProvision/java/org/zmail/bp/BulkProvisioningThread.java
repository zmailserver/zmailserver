/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.bp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
/**
 * @author Greg Solovyev
 */
public class BulkProvisioningThread extends Thread {
	private static Map<String,BulkProvisioningThread> mThreadCache = new HashMap<String,BulkProvisioningThread>();
	private static int MAX_PROVISIONING_THREADS = 4;
	private static int MAX_PROVISIONING_FAILURES = 500;
	public static int iSTATUS_NOT_RUNNING = -1;
	public static int iSTATUS_IDLE = 0;
	public static int iSTATUS_STARTED = 1;
	public static int iSTATUS_STARTING = 2;
	public static int iSTATUS_CREATING_ACCOUNTS = 3;
	public static int iSTATUS_FINISHED = 4;
	public static int iSTATUS_ABORT = 5;
	public static int iSTATUS_ABORTED = 6;
	public static int iSTATUS_ERROR = 100;
	
	private List<Map<String, Object>> sourceAccounts;
	private HashMap<String, Exception> failedAccounts;
	private HashMap<String, String> completedAccounts;
	private List<String> skippedAccounts;
	private int mStatus = 0;
	private int mProvisionedCounter = 0;
	private int mFailCounter = 0;
	private int mSkippedCounter = 0;
	private boolean mWithErrors = false;
	
	public int getFailCounter() {
		return mFailCounter;
	}
	
	public synchronized void start() {
		mStatus = iSTATUS_STARTING;
		super.start();
	}

	public int getProvisionedCounter() {
		return mProvisionedCounter;
	}
	
	public int getSkippedCounter() {
		return mSkippedCounter;
	}
	
	public boolean getWithErrors() {
		return mWithErrors;
	}
	
	public HashMap<String,String> getCompletedAccounts() {
		return completedAccounts;
	}
	
	public HashMap<String,Exception> getfailedAccounts() {
		return failedAccounts;
	}
	
	public int getTotalCount() {
		if(sourceAccounts == null) {
			return 0;
		}
		return sourceAccounts.size();
	}
	
	public int getStatus() {
		return mStatus;
	}

	public List<Map<String, Object>> getSourceAccounts() {
		return sourceAccounts;
	}

	public void abort() {
		mStatus = iSTATUS_ABORT;
	}
	
	@Override
	public void run() {
		mStatus = iSTATUS_STARTED;
		mFailCounter = 0;
		if(sourceAccounts == null) {
			mStatus = iSTATUS_ERROR;
			ZmailLog.extensions.error("sourceAccounts map is empty", BulkProvisionException.BP_IMPORT_THREAD_NOT_INITIALIZED());
			return;
		}
		Provisioning prov = Provisioning.getInstance();
		mProvisionedCounter = 0;
		for (Map<String, Object> entry : sourceAccounts) {
			String accName = "";
			if(mStatus == iSTATUS_ABORT) {
				ZmailLog.extensions.warn("Warning! Aborting bulk provisioning import thread.");
				mStatus = iSTATUS_ABORTED;
				return;
			}
			try {
				accName = String.valueOf(entry.get(Provisioning.A_mail));
				if(accName != null) {
	    			/**
	    			 * Check if user exists
	    			 */
	            	Account acct = prov.getAccountByName(accName);
	            	if(acct!=null) {
	            		/**
	            		 * Skip existing user
	            		 */
	            		skippedAccounts.add(accName);
	            		mSkippedCounter++;
	            		continue;
	            	}					
					String accPwd = String.valueOf(entry.get(Provisioning.A_userPassword));
					entry.remove(Provisioning.A_mail);
					entry.remove(Provisioning.A_userPassword);
					prov.createAccount(accName, accPwd, entry);
					completedAccounts.put(accName, accPwd);
					mProvisionedCounter++;
				}
				
			} catch (Exception e) {
				mFailCounter++;
				mWithErrors = true;
				failedAccounts.put(accName, e);
				ZmailLog.extensions.error("Failed to import account", e);
				if(mFailCounter >= MAX_PROVISIONING_FAILURES) {
					ZmailLog.extensions.error("Failed to import account",BulkProvisionException.BP_IMPORT_TOO_MANY_FAILURES(mFailCounter));
					mStatus = iSTATUS_ERROR;
					return;
				} else {
					continue;
				}
			}
        }
		mStatus = iSTATUS_FINISHED;
		return;
	}

	public void setSourceAccounts(List<Map<String, Object>> sourceAccounts) {
		this.sourceAccounts = sourceAccounts;
	}

	public static BulkProvisioningThread getThreadInstance(String threadId, boolean createNew) throws BulkProvisionException {
		synchronized(mThreadCache) {
			if(mThreadCache.get(threadId) != null) {
				return mThreadCache.get(threadId);
			} else if(createNew) {
				if(mThreadCache.size()>=MAX_PROVISIONING_THREADS) {
					throw BulkProvisionException.BP_TOO_MANY_THREADS (MAX_PROVISIONING_THREADS);
				}
				BulkProvisioningThread thread =  new BulkProvisioningThread(threadId);
				mThreadCache.put(threadId, thread);
				return thread;
			} else {
				return null;
			}
		}
	}
	
	public static void deleteThreadInstance(String threadId) {
		synchronized(mThreadCache) {
			if(mThreadCache.get(threadId) != null) {
				if(mThreadCache.get(threadId).isAlive()) {
					mThreadCache.get(threadId).interrupt();
				}
				mThreadCache.remove(threadId);
			}
		}
	}
	
	private BulkProvisioningThread(String threadId) {
		sourceAccounts = null;
		failedAccounts = new HashMap<String, Exception>();
		completedAccounts = new HashMap<String, String>();
		skippedAccounts = new ArrayList<String>();
		mStatus = iSTATUS_IDLE;
	}
}
