/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.qa.selenium.staf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.zmail.qa.selenium.framework.core.ExecuteHarnessMain;
import org.zmail.qa.selenium.framework.util.HarnessException;


public class Driver {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws HarnessException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, HarnessException {

        // Create the execution object
        ExecuteHarnessMain harness = new ExecuteHarnessMain();
        
        harness.jarfilename = "jarfile.jar";
        harness.classfilter = "projects.mobile.tests";
        harness.groups = new ArrayList<String>(Arrays.asList("always", "sanity"));
        
        // Execute!
		String response = harness.execute();
		
		System.out.println(response);
		
	}

}
