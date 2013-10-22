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
package org.zmail.qa.selenium.projects.desktop.ui.preferences.trustedaddresses;

import java.util.Arrays;
import java.util.List;

import org.zmail.qa.selenium.framework.ui.AbsApplication;
import org.zmail.qa.selenium.framework.ui.AbsDisplay;
import org.zmail.qa.selenium.framework.ui.AbsPage;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.HarnessException;

public class DisplayTrustedAddress extends AbsDisplay {

   public static class Locators {
      public static final String zMsgViewDisplayImgLink = "css=a#zv__TV__MSG_displayImages_dispImgs";
      public static final String zMsgViewDomainLink = "css=a#zv__TV__MSG_displayImages_domain";
      public static final String zMsgViewWarningIcon = "css=div#zv__TV__MSG_displayImages.DisplayImages div div.ImgWarning";

      public static final String zConViewDisplayImgLink = "css=a#zv__CLV__MSG_displayImages_dispImgs";
      public static final String zConViewDomainLink = "css=a#zv__CLV__MSG_displayImages_domain";
      public static final String zConViewWarningIcon = "css=div#zv__CLV__MSG_displayImages.DisplayImages div div.ImgWarning";

      public static final String IsConViewActive = "css=[parentid='zv__CLV']";
      public static final String IsMsgViewActive = "css=[parentid='zv__TV']";
   }

   public DisplayTrustedAddress(AbsApplication application) {
      super(application);
      // TODO Auto-generated constructor stub
   }

   @Override
   public String myPageName() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public AbsPage zPressButton(Button button) throws HarnessException {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean zIsActive() throws HarnessException {
      // logger.warn("implement me", new Throwable());
      return (sIsElementPresent(Locators.IsConViewActive) || sIsElementPresent(Locators.IsMsgViewActive));

   }

   public String zDisplayImageLink(String zmailPrefGroupMailBy)
         throws HarnessException {

      String DisplayImgLink = null;
      if (zmailPrefGroupMailBy == "message") {
         DisplayImgLink = sGetEval("selenium.browserbot.getCurrentWindow().document.getElementById('zv__TV__MSG_displayImages').style.display");
         return DisplayImgLink;
      } else if (zmailPrefGroupMailBy == "conversation") {
         DisplayImgLink = sGetEval("selenium.browserbot.getCurrentWindow().document.getElementById('zv__CLV__MSG_displayImages').style.display");
         return DisplayImgLink;
      } else {
         throw new HarnessException("no logic defined  ");
      }
   }

   /**
    * Check warning icon,Display Image link,Domain link
    * 
    * @return
    * @throws HarnessException
    */
   public boolean zHasWDDLinks(String zmailPrefGroupMailBy)
         throws HarnessException {

      if (zmailPrefGroupMailBy == "message") {
         List<String> locators = Arrays.asList(
               Locators.zMsgViewDisplayImgLink,
               Locators.zMsgViewDomainLink, Locators.zMsgViewWarningIcon);

         for (String locator : locators) {
            if (!this.sIsElementPresent(locator))
               return (false);
         }

         return (true);

      } else if (zmailPrefGroupMailBy == "conversation") {
         List<String> locators = Arrays.asList(
               Locators.zConViewDisplayImgLink,
               Locators.zConViewDomainLink, Locators.zConViewWarningIcon);

         for (String locator : locators) {
            if (!this.sIsElementPresent(locator))
               return (false);
         }

         return (true);
      } else {
         throw new HarnessException("no logic defined  ");
      }
   }

}
