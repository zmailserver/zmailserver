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
package org.zmail.cs.account;

import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import org.zmail.common.localconfig.LC;
import org.zmail.common.service.ServiceException;
import org.zmail.common.util.Constants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.util.Zmail;

public class AutoProvisionThread extends Thread implements Provisioning.EagerAutoProvisionScheduler{

    private static volatile AutoProvisionThread autoProvThread = null;
    private static Object THREAD_CONTROL_LOCK = new Object();
    private boolean shutdownRequested = false;

    @VisibleForTesting
    protected AutoProvisionThread() {
        setName("AutoProvision");
    }

    /**
     * Starts up the auto provision thread.
     */
    public synchronized static void startup() {
        synchronized (THREAD_CONTROL_LOCK) {
            if (isRunning()) {
                ZmailLog.autoprov.warn("Cannot start a second auto provision thread while another one is running.");
                return;
            }

            if (getSleepInterval() == 0) {
                ZmailLog.autoprov.info("Not starting auto provision thread because %s is 0.",
                    Provisioning.A_zmailAutoProvPollingInterval);
                return;
            }

            // Log status
            try {
                String displayInterval = Provisioning.getInstance().getLocalServer().getAttr(
                    Provisioning.A_zmailAutoProvPollingInterval, null);
                ZmailLog.autoprov.info("Starting auto provision thread with sleep interval %s.", displayInterval);
            } catch (ServiceException e) {
                ZmailLog.autoprov.warn("Unable to get %s.  Aborting thread startup.",
                    Provisioning.A_zmailAutoProvPollingInterval, e);
                return;
            }

            // Start thread
            autoProvThread = new AutoProvisionThread();
            autoProvThread.start();
        }
    }

    /**
     * Returns <tt>true</tt> if the mailbox auto provision thread is currently running.
     */
    public synchronized static boolean isRunning() {
        synchronized (THREAD_CONTROL_LOCK) {
            if (autoProvThread != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Shuts down the auto provision thread.  Does nothing if it is not running.
     */
    public synchronized static void shutdown() {
        synchronized (THREAD_CONTROL_LOCK) {
            if (autoProvThread != null) {
                ZmailLog.autoprov.info("Shutting down auto provision thread");
                autoProvThread.requestShutdown();
                autoProvThread.interrupt();
                autoProvThread = null;
            } else {
                ZmailLog.autoprov.info("shutdown() called, but auto provision thread is not running.");
            }
        }
    }
    
    public synchronized static void switchAutoProvThreadIfNecessary() throws ServiceException {
        Server localServer = Provisioning.getInstance().getLocalServer();
        
        long interval = localServer.getTimeInterval(Provisioning.A_zmailAutoProvPollingInterval, 0);
        
        Set<String> scheduledDomains = 
            localServer.getMultiAttrSet(Provisioning.A_zmailAutoProvScheduledDomains);
        
        boolean needRunning = interval > 0 && !scheduledDomains.isEmpty();
        
        if (needRunning && !AutoProvisionThread.isRunning()) {
            AutoProvisionThread.startup();
        } else if (!needRunning && AutoProvisionThread.isRunning()) {
            AutoProvisionThread.shutdown();
        }
    }
    
    /**
     * Provision accounts for all domain scheduled for auto provision on this server, 
     * sleep for configured amount of time between iterations.
     * 
     * If an iteration takes longer than the sleep interval, the next iteration will start 
     * immediately.
     */
    @Override public void run() {
        // Sleep before doing work, to give the server time to warm up.  Also limits the amount
        // of random effect when determining the next mailbox id.
        long sleepTime = getInitialDelay();
        ZmailLog.autoprov.info("Auto provision thread sleeping for %dms before doing work.", sleepTime);

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            ZmailLog.autoprov.info("Shutting down auto provision thread.");
            autoProvThread = null;
            return;
        }

        Provisioning prov = Provisioning.getInstance();

        while (true) {
            if (isShutDownRequested()) {
                ZmailLog.autoprov.info("Shutting down auto provision thread.");
                return;
            }
            
            long iterStartedAt = System.currentTimeMillis();
            
            try {
                prov.autoProvAccountEager(this);
            } catch (Throwable t) {
                if (t instanceof OutOfMemoryError) {
                    Zmail.halt("Ran out of memory while auto provision accounts", t);
                } else {
                    ZmailLog.autoprov.warn("Unable to auto provision accounts", t);
                }
            }
            
            long iterEndedAt = System.currentTimeMillis();
            long elapsedMillis = iterEndedAt - iterStartedAt;

            // If this iteration took longer than the sleep interval, 
            // go back to work immediately without sleeping
            long sleepInterval = getSleepInterval();
            if (sleepInterval == 0) {
                // a shutdown request was issued
            } else if (elapsedMillis < sleepInterval) {
                sleep();
            } else {
                ZmailLog.autoprov.debug("Iteration took %d seconds, starting next iteration immediately without sleeping", 
                        elapsedMillis/Constants.MILLIS_PER_SECOND);   
            }
        }
    }

    @VisibleForTesting
    protected long getInitialDelay() {
        return LC.autoprov_initial_sleep_ms.longValue();
    }
    
    /**
     * Sleeps for the time interval specified by {@link Provisioning#A_zmailAutoProvPollingInterval}.
     * If sleep is interrupted, sets {@link #mShutdownRequested} to <tt>true</tt>.
     */
    private void sleep() {
        long interval = getSleepInterval();
        ZmailLog.autoprov.info("Sleeping for %d milliseconds.", interval);

        if (interval > 0) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                ZmailLog.autoprov.info("Auto provision thread was interrupted.");
                shutdownRequested = true;
            }
        } else {
            shutdownRequested = true;
        }
    }
    
    private void requestShutdown() {
        shutdownRequested = true;
    }
    
    @Override
    public boolean isShutDownRequested() {
        return shutdownRequested;
    }
    
    /**
     * Stores the sleep interval, so that the auto provision thread doesn't
     * die if there's a problem talking to LDAP.  See bug 32639.
     */
    private static long sleepInterval = 0;
    
    /**
     * Returns the current value of {@link Provisioning#A_zmailAutoProvPollingInterval},
     * or <tt>0</tt> if it cannot be determined.
     */
    private static long getSleepInterval() {
        try {
            Server server = Provisioning.getInstance().getLocalServer();
            sleepInterval = server.getTimeInterval(Provisioning.A_zmailAutoProvPollingInterval, 0);
        } catch (ServiceException e) {
            ZmailLog.autoprov.warn("Unable to determine value of %s.  Using previous value: %d.",
                Provisioning.A_zmailAutoProvPollingInterval, sleepInterval, e);
        }
        
        return sleepInterval;
    }


}
