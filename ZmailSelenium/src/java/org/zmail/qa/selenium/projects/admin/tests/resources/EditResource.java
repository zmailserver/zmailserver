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
package org.zmail.qa.selenium.projects.admin.tests.resources;

import org.testng.annotations.Test;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Action;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZAssert;
import org.zmail.qa.selenium.framework.util.ZmailAdminAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;
import org.zmail.qa.selenium.projects.admin.core.AdminCommonTest;
import org.zmail.qa.selenium.projects.admin.items.ResourceItem;
import org.zmail.qa.selenium.projects.admin.ui.FormEditResource;
import org.zmail.qa.selenium.projects.admin.ui.PageMain;

public class EditResource extends AdminCommonTest {
	public EditResource() {
		logger.info("New "+ EditResource.class.getCanonicalName());

		// All tests start at the "Resources" page
		super.startingPage = app.zPageManageResources;

	}

	/**
	 * Testcase : Edit Resource name  -- Manage resource View -- Location
	 * Steps :
	 * 1. Create a resource using SOAP.
	 * 2. Go to Manage resource View.
	 * 3. Select a resource.
	 * 4. Edit a resource using edit button in Gear box menu.
	 * 5. Verify resource is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = " Edit Resource name  -- Manage resource View -- Location",
			groups = { "smoke" })
			public void EditResource_01() throws HarnessException {

		// Create a new Resource in the Admin Console using SOAP
		ResourceItem resource = new ResourceItem();
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCalendarResourceRequest xmlns='urn:zmailAdmin'>"
		 		+ "<name>" + resource.getEmailAddress() + "</name>"
		 		+ "<a n=\"displayName\">" + resource.getName() + "</a>"
		 		+ "<a n=\"zmailCalResType\">" + "Location" + "</a>"
		 		+ "<password>test123</password>"
		 		+ "</CreateCalendarResourceRequest>");

		// Refresh the Resource list
		app.zPageManageResources.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");
		
		// Click on Resource to be Edited.
		app.zPageManageResources.zListItem(Action.A_LEFTCLICK, resource.getEmailAddress());
		
		// Click on Edit button
		FormEditResource form = (FormEditResource) app.zPageManageResources.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_EDIT);
		
		//Click on General Information tab.
		form.zClickTreeItem(FormEditResource.TreeItem.PROPERTIES);

		//Edit the name.
		String editedName = "editedResource_" + ZmailSeleniumProperties.getUniqueString();
		form.setName(editedName);
		
		//Submit the form.
		form.zSubmit();
		
		// Verify the Resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  editedName+"@"+resource.getDomainName() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");
		
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the Resource is edited successfully");
	}
	
	/**
	 * Testcase : Edit Resource name  -- Manage resource View -- Equipment
	 * Steps :
	 * 1. Create a resource using SOAP.
	 * 2. Go to Manage resource View.
	 * 3. Select a resource.
	 * 4. Edit a resource using edit button in Gear box menu.
	 * 5. Verify resource is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = " Edit Resource name  -- Manage resource View -- Location",
			groups = { "functional" })
			public void EditResource_02() throws HarnessException {

		// Create a new Resource in the Admin Console using SOAP
		ResourceItem resource = new ResourceItem();
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCalendarResourceRequest xmlns='urn:zmailAdmin'>"
		 		+ "<name>" + resource.getEmailAddress() + "</name>"
		 		+ "<a n=\"displayName\">" + resource.getName() + "</a>"
		 		+ "<a n=\"zmailCalResType\">" + "Equipment" + "</a>"
		 		+ "<password>test123</password>"
		 		+ "</CreateCalendarResourceRequest>");

		// Refresh the Resource list
		app.zPageManageResources.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");
		
		// Click on Resource to be Edited.
		app.zPageManageResources.zListItem(Action.A_LEFTCLICK, resource.getEmailAddress());
		
		// Click on Edit button
		FormEditResource form = (FormEditResource) app.zPageManageResources.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_EDIT);
		
		//Click on General Information tab.
		form.zClickTreeItem(FormEditResource.TreeItem.PROPERTIES);

		//Edit the name.
		String editedName = "editedResource_" + ZmailSeleniumProperties.getUniqueString();
		form.setName(editedName);
		
		//Submit the form.
		form.zSubmit();
		
		// Verify the Resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  editedName+"@"+resource.getDomainName() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");
		
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the Resource is edited successfully");
	}

	
	/**
	 * Testcase : Edit Resource name -- Manage resource View/Right Click Menu -- Location
	 * Steps :
	 * 1. Create a resource using SOAP.
	 * 2. Go to Manage resource View.
	 * 3. Right Click on a resource.
	 * 4. Edit a resource using edit button in right click menu.
	 * 5. Verify resource is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Edit Resource name -- Manage resource View/Right Click Menu -- Location",
			groups = { "functional" })
			public void EditResource_03() throws HarnessException {

		// Create a new Resource in the Admin Console using SOAP
		ResourceItem resource = new ResourceItem();
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCalendarResourceRequest xmlns='urn:zmailAdmin'>"
		 		+ "<name>" + resource.getEmailAddress() + "</name>"
		 		+ "<a n=\"displayName\">" + resource.getName() + "</a>"
		 		+ "<a n=\"zmailCalResType\">" + "Location" + "</a>"
		 		+ "<password>test123</password>"
		 		+ "</CreateCalendarResourceRequest>");

		// Refresh the Resource list
		app.zPageManageResources.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");
		
		// Right Click on Resource to be Edited.
		app.zPageManageResources.zListItem(Action.A_RIGHTCLICK, resource.getEmailAddress());
		
		// Click on Edit button
		FormEditResource form = (FormEditResource) app.zPageManageResources.zToolbarPressButton(Button.B_TREE_EDIT);
		
		//Click on General Information tab.
		form.zClickTreeItem(FormEditResource.TreeItem.PROPERTIES);

		//Edit the name.
		String editedName = "editedResource_" + ZmailSeleniumProperties.getUniqueString();
		form.setName(editedName);
		
		//Submit the form.
		form.zSubmit();
		
		// Verify the Resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  editedName+"@"+resource.getDomainName() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");
		
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the Resource is edited successfully");
	}
	
	/**
	 * Testcase : Edit Resource name -- Manage resource View/Right Click Menu -- Equipment
	 * Steps :
	 * 1. Create a resource using SOAP.
	 * 2. Go to Manage resource View.
	 * 3. Right Click on a resource.
	 * 4. Edit a resource using edit button in right click menu.
	 * 5. Verify resource is deleted using SOAP.
	 * @throws HarnessException
	 */
	@Test(	description = "Edit Resource name -- Manage resource View/Right Click Menu -- Equipment",
			groups = { "functional" })
			public void EditResource_04() throws HarnessException {

		// Create a new Resource in the Admin Console using SOAP
		ResourceItem resource = new ResourceItem();
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<CreateCalendarResourceRequest xmlns='urn:zmailAdmin'>"
		 		+ "<name>" + resource.getEmailAddress() + "</name>"
		 		+ "<a n=\"displayName\">" + resource.getName() + "</a>"
		 		+ "<a n=\"zmailCalResType\">" + "Equipment" + "</a>"
		 		+ "<password>test123</password>"
		 		+ "</CreateCalendarResourceRequest>");

		// Refresh the Resource list
		app.zPageManageResources.sClickAt(PageMain.Locators.REFRESH_BUTTON, "");
		
		// Right Click on Resource to be Edited.
		app.zPageManageResources.zListItem(Action.A_RIGHTCLICK, resource.getEmailAddress());
		
		// Click on Edit button
		FormEditResource form = (FormEditResource) app.zPageManageResources.zToolbarPressButton(Button.B_TREE_EDIT);
		
		//Click on General Information tab.
		form.zClickTreeItem(FormEditResource.TreeItem.PROPERTIES);

		//Edit the name.
		String editedName = "editedResource_" + ZmailSeleniumProperties.getUniqueString();
		form.setName(editedName);
		
		//Submit the form.
		form.zSubmit();
		
		// Verify the Resource exists in the ZCS
		ZmailAdminAccount.AdminConsoleAdmin().soapSend(
				"<GetCalendarResourceRequest xmlns='urn:zmailAdmin'>"
				+ 		"<calresource by='name'>" +  editedName+"@"+resource.getDomainName() + "</calresource>"  
				+		"</GetCalendarResourceRequest>");
		
		Element response = ZmailAdminAccount.AdminConsoleAdmin().soapSelectNode("//admin:GetCalendarResourceResponse/admin:calresource", 1); 
		ZAssert.assertNotNull(response, "Verify the Resource is edited successfully");
	}


}
