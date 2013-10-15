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
package org.zmail.qa.selenium.projects.admin.tests.cos;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.projects.admin.core.AdminCommonTest;
import org.zmail.qa.selenium.projects.admin.items.CosItem;
import org.zmail.qa.selenium.projects.admin.ui.WizardCreateCos;


public class CreateCos extends AdminCommonTest {
	
	public CreateCos() {
		logger.info("New "+ CreateCos.class.getCanonicalName());

		// All tests start at the "Cos" page
		super.startingPage = app.zPageManageCOS;
	}
	
	/**
	 * Testcase : Create a basic COS
	 * Steps :
	 * 1. Create a COS from GUI.
	 * 2. Verify cos is created using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Create a basic COS",
			groups = { "sanity" })
			public void CreateCos_01() throws HarnessException {

		// Create a new cos in the Admin Console
		CosItem cos = new CosItem();

		// Click "New"
		WizardCreateCos cosDialog = (WizardCreateCos) app.zPageManageCOS.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_NEW);
	
	
		// Fill out the necessary input fields and submit
		cosDialog.zCompleteWizard(cos);
		
		// Verify the cos exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
		"<GetCosRequest xmlns='urn:zmailAdmin'>" +
		                     "<cos by='name'>"+cos.getName()+"</cos>"+
		                   "</GetCosRequest>");
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCosResponse/admin:cos", 1);
		ZAssert.assertNotNull(response, "Verify the cos is created successfully");
	}

}
