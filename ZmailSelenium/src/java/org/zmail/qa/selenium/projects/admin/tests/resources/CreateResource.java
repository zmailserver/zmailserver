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
package org.zmail.qa.selenium.projects.admin.tests.resources;



import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.projects.admin.core.AdminCommonTest;
import org.zmail.qa.selenium.projects.admin.items.ResourceItem;
import org.zmail.qa.selenium.projects.admin.ui.WizardCreateResource;

public class CreateResource extends AdminCommonTest {
	public CreateResource() {
		logger.info("New " + CreateResource.class.getCanonicalName());

		//Every test starts at Resource page
		super.startingPage=app.zPageManageResources;
	}


	/**
	 * Testcase : Create a basic resource.
	 * Steps :
	 * 1. Create a resource from GUI.
	 * 2. Verify resource is created using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Create a basic resource",
			groups = { "obsolete" })
			public void CreateResource_01() throws HarnessException {

		// Create a new resource in the Admin Console
		ResourceItem resource = new ResourceItem();


		// Click "New"
		WizardCreateResource wizard = 
			(WizardCreateResource)app.zPageManageResources.zToolbarPressButton(Button.B_NEW);

		// Fill out the wizard and click Finish
		wizard.zCompleteWizard(resource);


		// Verify the resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  resource.getEmailAddress() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");

		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the RESOURCE is created successfully");

	}

	/**
	 * Testcase : Create a basic resource.
	 * Steps :
	 * 1. Create a resource from GUI i.e. Manage Accounts --> Resources --> Gear box --> New
	 * 2. Verify resource is created using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Create a basic resource using Manage Accounts --> Resources --> Gear box --> New",
			groups = { "sanity" })
			public void CreateResource_02() throws HarnessException {

		// Create a new resource in the Admin Console
		ResourceItem resource = new ResourceItem();


		// Click "New --> Resources"
		WizardCreateResource wizard = 
			(WizardCreateResource)app.zPageManageResources.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_NEW);

		// Fill out the wizard and click Finish
		wizard.zCompleteWizard(resource);


		// Verify the resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  resource.getEmailAddress() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");

		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the RESOURCE is created successfully");

	}

	/**
	 * Testcase : Create a basic location resource.
	 * Steps :
	 * 1. Create a Location resource from GUI.
	 * 2. Verify resource is created using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Create a basic Location resource",
			groups = { "sanity" })
			public void CreateResource_03() throws HarnessException {

		// Create a new resource in the Admin Console
		ResourceItem resource = new ResourceItem();


		// Click "New"
		WizardCreateResource wizard = 
			(WizardCreateResource)app.zPageManageResources.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_NEW);

		// Fill out the wizard and click Finish
		wizard.setResourceType(WizardCreateResource.Locators.LOCATION);
		wizard.zCompleteWizard(resource);


		// Verify the resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  resource.getEmailAddress() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");

		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the RESOURCE is created successfully");

	}
	
	/**
	 * Testcase : Create a basic equipment resource.
	 * Steps :
	 * 1. Create a Equipment resource from GUI.
	 * 2. Verify resource is created using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Create a basic Equipment resource",
			groups = { "sanity" })
			public void CreateResource_04() throws HarnessException {

		// Create a new resource in the Admin Console
		ResourceItem resource = new ResourceItem();


		// Click "New"
		WizardCreateResource wizard = 
			(WizardCreateResource)app.zPageManageResources.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_NEW);

		// Fill out the wizard and click Finish
		wizard.setResourceType(WizardCreateResource.Locators.EQUIPMENT);
		wizard.zCompleteWizard(resource);


		// Verify the resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  resource.getEmailAddress() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");

		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the RESOURCE is created successfully");

	}



}

