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
package com.zimbra.qa.selenium.projects.desktop.tests.preferences.mail.signatures;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.ui.AbsDialog;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraSeleniumProperties;
import com.zimbra.qa.selenium.projects.desktop.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.desktop.ui.preferences.TreePreferences.TreeItem;
import com.zimbra.qa.selenium.projects.desktop.ui.preferences.signature.FormSignatureNew;
import com.zimbra.qa.selenium.projects.desktop.ui.preferences.signature.PageSignature;
import com.zimbra.qa.selenium.projects.desktop.ui.preferences.signature.FormSignatureNew.Field;

public class CancelTextSignature extends AjaxCommonTest {

   public CancelTextSignature() throws HarnessException {
      super.startingPage = app.zPagePreferences;
      super.startingAccountPreferences = null;
   }

   @Test(description = "Cancel text signature", groups = { "functional" })
   public void CancelTextSignature_01() throws HarnessException {

      String sigName = "signame" + ZimbraSeleniumProperties.getUniqueString();
      String sigBody = "sigbody" + ZimbraSeleniumProperties.getUniqueString();

      // click on signature from left pane
      app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,TreeItem.MailSignatures);

      // Click on New signature button
      FormSignatureNew signew = (FormSignatureNew) app.zPageSignature.zToolbarPressButton(Button.B_NEW);

      // Fill Signature Name and body
      signew.zFillField(Field.SignatureName, sigName);
      signew.zFillField(Field.SignatureBody, sigBody);
      
      //Verify Warning Dialog gets pop up after click on Cancel button
      AbsDialog warning = (AbsDialog) signew.zToolbarPressButton(Button.B_CANCEL);
      ZAssert.assertNotNull(warning, "Verify the dialog is returned");

      //click on No button
      warning.zClickButton(Button.B_NO);

      //Verify canceled signature name from SignatureListView
      app.zPagePreferences.zNavigateTo();
      app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK,
            TreeItem.MailSignatures);

      PageSignature pagesig = new PageSignature(app);
      String SignatureListViewName = pagesig.zGetSignatureNameFromListView();

      // Verify signature name doesn't exist in SignatureListView
      ZAssert.assertStringDoesNotContain(
            SignatureListViewName,
            sigName,
            "Verify after  Cancelled, signature" +
            " does not present in SignatureList view");
   }

}
