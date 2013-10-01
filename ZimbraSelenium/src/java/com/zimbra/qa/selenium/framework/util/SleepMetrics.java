/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SleepMetrics {
	private static Logger logger = LogManager.getLogger(SleepMetrics.class);

	/**
	 * A list of class + methods being tracked
	 */
	private static List<SleepEntry> metrics = new ArrayList<SleepEntry>();
	
	/**
	 * Record to the logger the current sleep data report.
	 */
	public static void report() {
		
		logger.info("SleepMetrics:");
		
		Collections.sort(metrics);
		
		for (SleepEntry entry : metrics) {
			logger.info(entry.toString());
		}
		
	}
	
	/**
	 * Record the amount of 'sleep' time
	 * @param stack Current stack trace (i.e. (new Throwable()).getStackTrace())
	 * @param delay The desired Sleep(msec) delay
	 * @param start The start time of Sleep()
	 * @param finish The finish time of Sleep() (i.e in case interrupted)
	 */
	public static void RecordSleep(StackTraceElement[] stack, long delay, Date start, Date finish) {
		
		for ( StackTraceElement e : stack ) {
			
			// Skip any non-zimbra classes
			if ( !e.getClassName().startsWith("com.zimbra.qa.selenium") ) {
				continue;
			}
			
			// Skip any 'Abstract' methods - walk up the chain to find the real class
			if ( e.getClassName().contains(".Abs") ) {
				continue;
			}
			
			// Skip the SleepUtil class, since all sleep goes through that
			if ( e.getClassName().startsWith("com.zimbra.qa.selenium.framework.util.SleepUtil") ) {
				continue;
			}

			RecordSleep(e, delay, finish.getTime() - start.getTime());
			return;
		}
	}
	

	/**
	 * Record the amount of 'sleep' time
	 * @param e The stack element that requested the sleep (i.e. the class + method)
	 * @param delay The desired Sleep(msec) delay
	 * @param actual The actual time of Sleep() (i.e in case interrupted)
	 */
	public static void RecordSleep(StackTraceElement e, long delay, long actual) {
		SleepEntry entry = getEntry( e.getClassName(), e.getMethodName() );
		if ( entry == null ) {
			entry = new SleepEntry(e.getClassName(), e.getMethodName());
			entry.addSleep(actual);
			metrics.add(entry);
		} else {
			entry.addSleep(actual);
		}
		
	}
	
	/**
	 * Record the amount of 'processing' time
	 * @param stack Current stack trace (i.e. (new Throwable()).getStackTrace())
	 * @param start The start time of processing
	 * @param finish The finish time of processing
	 */
	public static void RecordProcessing(StackTraceElement[] stack, Date start, Date finish) {
		
		for ( StackTraceElement e : stack ) {
			
			// Skip any non-zimbra classes
			if ( !e.getClassName().startsWith("com.zimbra.qa.selenium") ) {
				continue;
			}
			
			// Skip any 'Abstract' methods - walk up the chain to find the real class
			if ( e.getClassName().contains(".Abs") ) {
				continue;
			}

			RecordProcessing(e, finish.getTime() - start.getTime());
			return;
		}
	}
	
	/**
	 * Record the amount of 'processing' time
	 * @param e The stack element that processed (i.e. the class + method)
	 * @param actual The total time (msec) that was spent processing
	 */
	public static void RecordProcessing(StackTraceElement e, long actual) {
		SleepEntry entry = getEntry( e.getClassName(), e.getMethodName() );
		if ( entry == null ) {
			entry = new SleepEntry(e.getClassName(), e.getMethodName());
			entry.addExecution(actual);
			metrics.add(entry);
		} else {
			entry.addExecution(actual);
		}
	}
	
	/**
	 * Search the list of entries for this class + method
	 * @param clazz
	 * @param method
	 * @return The existing entry if present, or null if not-present
	 */
	private static SleepEntry getEntry(String clazz, String method) {
		for (SleepEntry e : metrics ) {
			if ( e.className.equals(clazz) && e.methodName.equals(method) ) {
				return (e);
			}
		}
		return (null);
	}
	
	
	private static class SleepEntry implements Comparable<SleepEntry> {
		private static Logger logger = LogManager.getLogger(SleepMetrics.class);

		public String className;
		public String methodName;
		public long totalSleep;
		public long totalExecution;
		
		public SleepEntry(String clazz, String method) {
			className = clazz;
			methodName = method;
			totalSleep = 0;
			totalExecution = 0;
			
			logger.info("Entry: "+ className +"."+ methodName +"() - new entry");
		}
		
		public void addSleep(long delay) {
			totalSleep += delay;
			logger.info("Entry: "+ className +"."+ methodName +"() - Sleep: "+ delay +"/"+ totalSleep);
		}
		
		public void addExecution(long delay) {
			totalExecution += delay;
			logger.info("Entry: "+ className +"."+ methodName +"() -  Exec: "+ delay +"/"+ totalExecution);
		}
		
		public String toString() {
			
			// "Entry: com.zimbra.qa.selenium.class.method() - 1234 msec"
			
			StringBuilder sb = new StringBuilder("Report: ");
			sb.append(className).append('.').append(methodName).append("() -");
			sb.append(" Sleep: ").append(totalSleep).append(" msec");
			sb.append(" Exec: ").append(totalExecution).append(" msec");
			return (sb.toString());
		}

		@Override
		public int compareTo(SleepEntry that) {
			final int BEFORE = -1;
			final int EQUAL = 0;
			final int AFTER = 1;
			
			if (that == null) {
				return (BEFORE);
			}
			
			if ( this == that ) {
				return (EQUAL);
			}
			
			if ( this.totalSleep > that.totalSleep ) {
				return (BEFORE);
			}
			
			if ( this.totalSleep < that.totalSleep ) {
				return (AFTER);
			}
			
			if ( this.totalExecution > that.totalExecution ) {
				return (BEFORE);
			}
			
			if ( this.totalExecution < that.totalExecution ) {
				return (AFTER);
			}
			
			return (EQUAL);
		}
		
	}
}
