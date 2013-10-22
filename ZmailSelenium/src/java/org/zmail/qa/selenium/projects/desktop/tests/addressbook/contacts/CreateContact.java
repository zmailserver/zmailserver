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
package com.zimbra.qa.selenium.projects.desktop.tests.addressbook.contacts;

import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount.SOAP_DESTINATION_HOST_TYPE;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.*;
import com.zimbra.qa.selenium.projects.desktop.ui.addressbook.FormContactNew;


//TODO: add more in ContactItem.java

public class CreateContact extends AjaxCommonTest  {

	public CreateContact() {
		logger.info("New "+ CreateContact.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		// Make sure we are using an account with conversation view
		super.startingAccountPreferences = null;		
		
	}
	
	private DialogWarning _clickCancel(ContactItem contactItem, FormContactNew formContactNew) throws HarnessException {
        
        // Fill in the form
       formContactNew.zFill(contactItem);
   
       // Click Cancel
       DialogWarning dialogWarning = (DialogWarning) app.zPageAddressbook.zToolbarPressButton(Button.B_CANCEL);
       
       //Verify title Warning and content "Do you want to save changes?"
       String text="Warning";
       ZAssert.assertEquals(dialogWarning.zGetWarningTitle(), text,
             "Verify title is " + text);
       text = "Do you want to save changes?";
       ZAssert.assertEquals(dialogWarning.zGetWarningContent(), text,
             " Verify content is " + text);
   
       return dialogWarning;
   }


	//can be used for other classes such as DeleteContact, MoveContact
	public static ContactItem createBasicContact(AppAjaxClient app, FormContactNew formContactNew)throws HarnessException {
							
		// Create a contact Item
		ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
	
		//verify form contact new page is displayed
		ZAssert.assertTrue(formContactNew.zIsActive(),"Verify new contact form is displayed");
		
        // Fill in the form
	    formContactNew.zFill(contactItem);
	    
		// Save the contact
        formContactNew.zSubmit();
		
        //verify toasted message 'contact created'  
        Toaster toast = app.zPageMain.zGetToaster();
        String toastMsg = toast.zGetToastMessage();
        ZAssert.assertStringContains(toastMsg, "Contact Created", "Verify toast message 'Contact Created'");

        //verify contact "file as" is displayed
		List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts();
		boolean isFileAsEqual=false;
		for (ContactItem ci : contacts) {
			if (ci.fileAs.equals(contactItem.fileAs)) {
	            isFileAsEqual = true;	
				break;
			}
		}
		
        ZAssert.assertTrue(isFileAsEqual, "Verify contact fileAs (" + contactItem.fileAs + ") existed ");

		return contactItem;
	}
	
	@Test(	description = "Create a basic contact item by click New in page Addressbook ",
			groups = { "sanity" })
	public void createContactByClickingNewFromToolBar() throws HarnessException {				
		FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);

		ContactItem contactItem = createBasicContact(app, formContactNew);

      GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
      app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);
		
		ContactItem contact = ContactItem.importFromSOAP(
            app.zGetActiveAccount(),
            contactItem.fileAs);

      ZAssert.assertEquals(contactItem.firstName, contact.firstName,
            "Verifying first name");
      ZAssert.assertEquals(contactItem.lastName, contact.lastName,
            "Verifying last name");
      ZAssert.assertEquals(contactItem.email, contact.email,
            "Verifying email address");
	}
	
	@Test(	description = "Create a basic contact item by use PullDown Menu->Contacts",
			groups = { "functional" })
	public void CreateContactFromPulldownMenu() throws HarnessException {				
		FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CONTACT);
		
		ContactItem contactItem = createBasicContact(app, formContactNew);

		GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
		app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);

		ContactItem contact = ContactItem.importFromSOAP(
            app.zGetActiveAccount(),
            contactItem.fileAs);

      ZAssert.assertEquals(contactItem.firstName, contact.firstName,
            "Verifying first name");
      ZAssert.assertEquals(contactItem.lastName, contact.lastName,
            "Verifying last name");
      ZAssert.assertEquals(contactItem.email, contact.email,
            "Verifying email address");
	}

	  @Test(   description = "Cancel creating a contact item - Click Yes",
	         groups = { "functional" })
	   public void CancelCreateContactClickYes() throws HarnessException {           
	      FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);
	      
	      // Create a contact Item
			ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
	   
	      DialogWarning dialogWarning = _clickCancel(contactItem,formContactNew);
	            
	       // Click Yes in popup dialog 
	       dialogWarning.zClickButton(Button.B_YES);
	             
	       //verify toasted message 'contact created'  
	        Toaster toast = app.zPageMain.zGetToaster();
	        String toastMsg = toast.zGetToastMessage();
	        ZAssert.assertStringContains(toastMsg, "Contact Created", "Verify toast message 'Contact Created'");
	  
	       // Verify contact  created
	       List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts();
	      boolean isFileAsEqual=false;
	      for (ContactItem ci : contacts) {
	         if (ci.fileAs.equals(contactItem.fileAs)) {
	               isFileAsEqual = true;   
	            break;
	         }
	      }

	      ZAssert.assertTrue(isFileAsEqual, "Verify contact fileAs (" + contactItem.fileAs + ") not existed ");

	   }

	   @Test(   description = "Cancel creating a contact item - Click No",
	         groups = { "functional" })
	   public void CancelCreateContactClickNo() throws HarnessException {            
	      FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);
	      
	      // Create a contact Item
			ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
	   
	      DialogWarning dialogWarning = _clickCancel(contactItem,formContactNew);
	       
	      //Click No in popup dialog 
	      dialogWarning.zClickButton(Button.B_NO);

	      // Verify contact not created
	      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts();
	      boolean isFileAsEqual=false;
	      for (ContactItem ci : contacts) {
	         if (ci.fileAs.equals(contactItem.fileAs)) {
	               isFileAsEqual = true;   
	            break;
	         }
	      }

	      ZAssert.assertFalse(isFileAsEqual, "Verify contact fileAs (" + contactItem.fileAs + ") existed ");

	   }

	   @Test(   description = "Cancel creating a contact item - Click Cancel",
	         groups = { "functional" })
	   public void CancelCreateContactClickCancel() throws HarnessException {     
	      FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);
	      
	      // Create a contact Item
			ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
	   
	      DialogWarning dialogWarning = _clickCancel(contactItem,formContactNew);
	    
	       // Click Cancel in popup dialog 
	       dialogWarning.zClickButton(Button.B_CANCEL);
	             
	       // Verify page not redirect
	       // or form contact new page is displayed
	      ZAssert.assertTrue(formContactNew.zIsActive(),"Verify new contact form is displayed");
	      
	      //Verify firstname , lastname  not changed
	        ZAssert.assertEquals(app.zPageAddressbook.sGetValue(FormContactNew.Locators.zFirstEditField),contactItem.firstName, "Verify contact firstname (" + contactItem.firstName + ") not changed ");
	        ZAssert.assertEquals(app.zPageAddressbook.sGetValue(FormContactNew.Locators.zLastEditField),contactItem.lastName, "Verify contact lastname (" + contactItem.lastName + ") not changed ");


	   }

	   @Test(   description = "create a contact item with full attribute",
	         groups = { "smoke" })
	   public void CreateContactWithAllAttributes() throws HarnessException {     
	      FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);
	      
	      // Create a contact Item
			ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
	   
	      // or form contact new page is displayed
	      ZAssert.assertTrue(formContactNew.zIsActive(),"Verify new contact form is displayed");
	      
	      // show all hidden field for names:
	      formContactNew.zDisplayHiddenName();
	      
	      // fill items
	      formContactNew.zFill(contactItem);
	      
	      // Save the contact
	      formContactNew.zSubmit();
	      
	      //verify toasted message 'contact created'  
	      ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(), "Contact Created", "Verify toast message 'Contact Created'");

	      //verify contact "file as" is displayed
	      List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts();
	      boolean isFileAsEqual=false;
	      for (ContactItem ci : contacts) {
	         if (ci.fileAs.equals(contactItem.fileAs)) {
	               isFileAsEqual = true;   
	            break;
	         }
	      }

         ZAssert.assertTrue(isFileAsEqual, "Verify contact fileAs (" + contactItem.fileAs + ") existed ");

         GeneralUtility.syncDesktopToZcsWithSoap(app.zGetActiveAccount());
         app.zPageAddressbook.zWaitForDesktopLoadingSpinner(5000);

         ContactItem contact = ContactItem.importFromSOAP(
               app.zGetActiveAccount(),
               contactItem.fileAs);

//         ZAssert.assertEquals(contactItem.firstName, contact.firstName,
//               "Verifying first name");
//         ZAssert.assertEquals(contactItem.lastName, contact.lastName,
//               "Verifying last name");
//         ZAssert.assertEquals(contactItem.middleName, contact.middleName,
//               "Verifying middle name");
//         ZAssert.assertEquals(contactItem.homePostalCode, contact.homePostalCode,
//               "Verifying home postal code");
//         ZAssert.assertEquals(contactItem.email, contact.email,
//               "Verifying email address");
//         ZAssert.assertEquals(contactItem.birthday, contact.birthday,
//               "Verifying birthday");
//         ZAssert.assertEquals(contactItem.nickname, contact.nickname,
//               "Verifying nickname");
//         ZAssert.assertEquals(contactItem.department, contact.department,
//               "Verifying department");
//         ZAssert.assertEquals(contactItem.homeCountry, contact.homeCountry,
//               "Verifying home country");
//         ZAssert.assertEquals(contactItem.homeStreet, contact.homeStreet,
//               "Verifying home street");
//         ZAssert.assertEquals(contactItem.homeCity, contact.homeCity,
//               "Verifying home city");
//         ZAssert.assertEquals(contactItem.company, contact.company,
//               "Verifying company");
//         ZAssert.assertEquals(contactItem.homeState, contact.homeState,
//               "Verifying home state");
//         ZAssert.assertEquals(contactItem.notes, contact.notes,
//               "Verifying notes");
//         ZAssert.assertEquals(contactItem.jobTitle, contact.jobTitle,
//               "Verifying job title");
//         ZAssert.assertEquals(contactItem.homeURL, contact.homeURL,
//               "Verifying home URL");
//         ZAssert.assertEquals(contactItem.maidenName, contact.maidenName,
//               "Verifying maiden name");
//         ZAssert.assertEquals(contactItem.namePrefix, contact.namePrefix,
//               "Verifying prefix name");
//         ZAssert.assertEquals(contactItem.mobilePhone, contact.mobilePhone,
//               "Verifying mobile phone");
//         ZAssert.assertEquals(contactItem.imAddress1, contact.imAddress1.split("//")[1],
//               "Verifying IM Address");
//         ZAssert.assertEquals(contactItem.nameSuffix, contact.nameSuffix,
//               "Verifying suffix name");
	   }

	   @Test(description = "Creat a contact on Local Folders by clicking new from toolbar",
	         groups = {"smoke"})
	   public void CreateLocalContactByClickingNewFormToolBar() throws HarnessException {
	      FolderItem localAddressBook = FolderItem.importFromSOAP(
	            app.zGetActiveAccount(),
	            SystemFolder.Contacts,
	            SOAP_DESTINATION_HOST_TYPE.CLIENT,
	            ZimbraAccount.clientAccountName);
	      app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, localAddressBook);
	      FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);
	      ContactItem contactItem = createBasicContact(app, formContactNew);

	      ContactItem contact = ContactItem.importFromSOAP(
               app.zGetActiveAccount(),
               contactItem.fileAs,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               ZimbraAccount.clientAccountName);

         ZAssert.assertEquals(contactItem.firstName, contact.firstName,
               "Verifying first name");
         ZAssert.assertEquals(contactItem.lastName, contact.lastName,
               "Verifying last name");
         ZAssert.assertEquals(contactItem.email, contact.email,
               "Verifying email address");
	   }

	   @Test(description = "Creat a contact on Local Folders by clicking new from pull down menu",
            groups = {"functional"})
      public void CreateLocalContactThruPullDownMenu() throws HarnessException {
         FolderItem localAddressBook = FolderItem.importFromSOAP(
               app.zGetActiveAccount(),
               SystemFolder.Contacts,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               ZimbraAccount.clientAccountName);
         app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, localAddressBook);
         FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressPulldown(Button.B_NEW, Button.O_NEW_CONTACT);
         ContactItem contactItem = createBasicContact(app, formContactNew);

         ContactItem contact = ContactItem.importFromSOAP(
               app.zGetActiveAccount(),
               contactItem.fileAs,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               ZimbraAccount.clientAccountName);

         ZAssert.assertEquals(contactItem.firstName, contact.firstName,
               "Verifying first name");
         ZAssert.assertEquals(contactItem.lastName, contact.lastName,
               "Verifying last name");
         ZAssert.assertEquals(contactItem.email, contact.email,
               "Verifying email address");
      }

	   @Test(description = "Creat a contact on Local Folders with full attribute",
            groups = {"functional"})
      public void CreateLocalContactWithAllAttributes() throws HarnessException {
         FolderItem localAddressBook = FolderItem.importFromSOAP(
               app.zGetActiveAccount(),
               SystemFolder.Contacts,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               ZimbraAccount.clientAccountName);
         app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, localAddressBook);
         FormContactNew formContactNew = (FormContactNew)app.zPageAddressbook.zToolbarPressButton(Button.B_NEW);

         // Create a contact Item
 		ContactItem contactItem = ContactItem.createContactItem(app.zGetActiveAccount());
      
         // or form contact new page is displayed
         ZAssert.assertTrue(formContactNew.zIsActive(),"Verify new contact form is displayed");
         
         // show all hidden field for names:
         formContactNew.zDisplayHiddenName();
         
         // fill items
         formContactNew.zFill(contactItem);
         
         // Save the contact
         formContactNew.zSubmit();
         
         //verify toasted message 'contact created'  
         ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(), "Contact Created", "Verify toast message 'Contact Created'");

         //verify contact "file as" is displayed
         List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts();
         boolean isFileAsEqual=false;
         for (ContactItem ci : contacts) {
            if (ci.fileAs.equals(contactItem.fileAs)) {
                  isFileAsEqual = true;   
               break;
            }
         }

         ZAssert.assertTrue(isFileAsEqual, "Verify contact fileAs (" + contactItem.fileAs + ") existed ");
         ContactItem contact = ContactItem.importFromSOAP(
               app.zGetActiveAccount(),
               contactItem.fileAs,
               SOAP_DESTINATION_HOST_TYPE.CLIENT,
               ZimbraAccount.clientAccountName);

//         ZAssert.assertEquals(contactItem.firstName, contact.firstName,
//               "Verifying first name");
//         ZAssert.assertEquals(contactItem.lastName, contact.lastName,
//               "Verifying last name");
//         ZAssert.assertEquals(contactItem.middleName, contact.middleName,
//               "Verifying middle name");
//         ZAssert.assertEquals(contactItem.homePostalCode, contact.homePostalCode,
//               "Verifying home postal code");
//         ZAssert.assertEquals(contactItem.email, contact.email,
//               "Verifying email address");
//         ZAssert.assertEquals(contactItem.birthday, contact.birthday,
//               "Verifying birthday");
//         ZAssert.assertEquals(contactItem.nickname, contact.nickname,
//               "Verifying nickname");
//         ZAssert.assertEquals(contactItem.department, contact.department,
//               "Verifying department");
//         ZAssert.assertEquals(contactItem.homeCountry, contact.homeCountry,
//               "Verifying home country");
//         ZAssert.assertEquals(contactItem.homeStreet, contact.homeStreet,
//               "Verifying home street");
//         ZAssert.assertEquals(contactItem.homeCity, contact.homeCity,
//               "Verifying home city");
//         ZAssert.assertEquals(contactItem.company, contact.company,
//               "Verifying company");
//         ZAssert.assertEquals(contactItem.homeState, contact.homeState,
//               "Verifying home state");
//         ZAssert.assertEquals(contactItem.notes, contact.notes,
//               "Verifying notes");
//         ZAssert.assertEquals(contactItem.jobTitle, contact.jobTitle,
//               "Verifying job title");
//         ZAssert.assertEquals(contactItem.homeURL, contact.homeURL,
//               "Verifying home URL");
//         ZAssert.assertEquals(contactItem.maidenName, contact.maidenName,
//               "Verifying maiden name");
//         ZAssert.assertEquals(contactItem.namePrefix, contact.namePrefix,
//               "Verifying prefix name");
//         ZAssert.assertEquals(contactItem.mobilePhone, contact.mobilePhone,
//               "Verifying mobile phone");
//         ZAssert.assertEquals(contactItem.imAddress1, contact.imAddress1.split("//")[1],
//               "Verifying IM Address");
//         ZAssert.assertEquals(contactItem.nameSuffix, contact.nameSuffix,
//               "Verifying suffix name");
         
      }

}
