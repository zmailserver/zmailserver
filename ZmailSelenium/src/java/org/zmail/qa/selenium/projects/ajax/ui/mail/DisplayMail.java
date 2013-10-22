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
package org.zmail.qa.selenium.projects.ajax.ui.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.zmail.qa.selenium.framework.items.AttachmentItem;
import org.zmail.qa.selenium.framework.ui.*;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.framework.util.staf.Stafpostqueue;
import org.zmail.qa.selenium.projects.ajax.ui.*;

/**
 * The <code>DisplayMail<code> object defines a read-only view of a message
 * in the Zmail Ajax client.
 * <p>
 * This class can be used to extract data from the message, such as To,
 * From, Subject, Received Date, message body.  Additionally, it can
 * be used to click on certain links in the message body, such as 
 * "view entire message" and "highlight objects".
 * <p>
 * Hover over objects, such as email or URL hover over, are encapsulated.
 * <p>
 * 
 * @author zmail
 * @see http://wiki.zmail.com/wiki/Testing:_Selenium:_ZmailSelenium_Overview#Mail_Page
 */
public class DisplayMail extends AbsDisplay {

	/**
	 * Defines Selenium locators for various objects in {@link DisplayMail}
	 */
	public static class Locators {
				
		
		public static final String MessageViewPreviewAtBottomCSS		= "css=div[id='zv__TV-main__MSG']";
		public static final String MessageViewPreviewAtRightCSS			= "css=div[id='zv__TV-main__MSG']";
		// 4/26/2012, the message ID is now used in the locator
		// public static final String MessageViewOpenMessageCSS			= "css=div[id='zv__MSG-1__MSG']";
		public static final String MessageViewOpenMessageCSS			= "css=div[id^='zv__MSG-'][id$='__MSG']";
		
		public static final String ConversationViewPreviewAtBottomCSS	= "css=div[id='zv__CLV-main__CV']";
		public static final String ConversationViewPreviewAtRightCSS	= "css=div[id='zv__CLV-main__CV']";
		public static final String ConversationViewOpenMessageCSS		= "css=TODO#TODO";
		//public static final String ConversationViewPreviewAtBottomCSS	= "css=div[id='zv__CLV__MSG']";
		//public static final String ConversationViewPreviewAtRightCSS	= "css=div[id='zv__CLV__MSG']";
	
		// Accept, Decline & Tentative button, menus and dropdown locators
		public static final String AcceptButton = "css=td[id$='__Inv__REPLY_ACCEPT_title']";
		public static final String AcceptDropdown = "css=td[id$='__Inv__REPLY_ACCEPT_dropdown']>div";
		public static final String AcceptNotifyOrganizerMenu = "id=REPLY_ACCEPT_NOTIFY_title";
		public static final String AcceptEditReplyMenu = "id=INVITE_REPLY_ACCEPT_title";
		public static final String AcceptDontNotifyOrganizerMenu = "id=REPLY_ACCEPT_IGNORE_title";

		public static final String TentativeButton = "css=td[id$='__Inv__REPLY_TENTATIVE_title']";
		public static final String TentativeDropdown = "css=td[id$='__Inv__REPLY_TENTATIVE_dropdown']>div";
		public static final String TentativeNotifyOrganizerMenu = "id=REPLY_TENTATIVE_NOTIFY_title";
		public static final String TentativeEditReplyMenu = "id=INVITE_REPLY_TENTATIVE_title";
		public static final String TentativeDontNotifyOrganizerMenu = "id=REPLY_TENTATIVE_IGNORE_title";
		
		public static final String DeclineButton = "css=td[id$='__Inv__REPLY_DECLINE_title']";
		public static final String DeclineDropdown = "css=td[id$='__Inv__REPLY_DECLINE_dropdown']>div";
		public static final String DeclineNotifyOrganizerMenu = "id=REPLY_DECLINE_NOTIFY_title";
		public static final String DeclineEditReplyMenu = "id=INVITE_REPLY_DECLINE_title";
		public static final String DeclineDontNotifyOrganizerMenu = "id=REPLY_DECLINE_IGNORE_title";
		
		public static final String ProposeNewTimeButton = "css=td[id$='__Inv__PROPOSE_NEW_TIME_title']";
	}

	/**
	 * The various displayed fields in a message
	 */
	public static enum Field {
		ReceivedTime,	// Message received time
		ReceivedDate,	// Message received date
		From,
		ResentFrom,
		ReplyTo,
		To,
		Cc,
		OnBehalfOf,
		OnBehalfOfLabel,
		Bcc,			// Does this show in any mail views?  Maybe in Sent?
		Subject,
		Body
	}
	
	public String ContainerLocator = Locators.MessageViewPreviewAtBottomCSS;
	

	/**
	 * Protected constuctor for this object.  Only classes within
	 * this package should create DisplayMail objects.
	 * 
	 * @param application
	 */
	protected DisplayMail(AbsApplication application) {
		super(application);
		
		logger.info("new " + DisplayMail.class.getCanonicalName());
	}
	
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public AbsPage zPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zDisplayPressButton("+ button +")");
		
		tracer.trace("Click "+ button);

		AbsPage page = this;
		String locator = null;
		boolean doPostfixCheck = false;

		if ( button == Button.B_VIEW_ENTIRE_MESSAGE ) {
			
			locator = this.ContainerLocator + " span[id$='_msgTruncation_link']";

			if ( !this.sIsElementPresent(locator) )
				throw new HarnessException("locator is not present for button "+ button +" : "+ locator);
			
			this.sClick(locator); // sClick() is required for this element
			
			this.zWaitForBusyOverlay();

			return (page);

		} else if ( button == Button.B_HIGHLIGHT_OBJECTS ) {

			locator = this.ContainerLocator + " span[id$='_highlightObjects_link']";


			if ( !this.sIsElementPresent(locator) )
				throw new HarnessException("locator is not present for button "+ button +" : "+ locator);
			
			this.sClick(locator); // sClick() is required for this element
			
			this.zWaitForBusyOverlay();

			return (page);

		} else if ( button == Button.B_ACCEPT ) {
			
			locator = Locators.AcceptButton;
			page = null;
			doPostfixCheck = true;
		
		} else if ( button == Button.O_ACCEPT_NOTIFY_ORGANIZER ) {
			
			locator = Locators.AcceptNotifyOrganizerMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_ACCEPT_EDIT_REPLY ) {
			
			locator = Locators.AcceptEditReplyMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_ACCEPT_DONT_NOTIFY_ORGANIZER ) {
			
			locator = Locators.AcceptDontNotifyOrganizerMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.B_TENTATIVE ) {
			
			locator = Locators.TentativeButton;
			page = null;
			doPostfixCheck = true;
		
		} else if ( button == Button.O_TENTATIVE_NOTIFY_ORGANIZER ) {
			
			locator = Locators.TentativeNotifyOrganizerMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_TENTATIVE_EDIT_REPLY ) {
			
			locator = Locators.TentativeEditReplyMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_TENTATIVE_DONT_NOTIFY_ORGANIZER ) {
			
			locator = Locators.TentativeDontNotifyOrganizerMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.B_DECLINE ) {
			
			locator = Locators.DeclineButton;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_DECLINE_NOTIFY_ORGANIZER ) {
			
			locator = Locators.DeclineNotifyOrganizerMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_DECLINE_EDIT_REPLY ) {
			
			locator = Locators.DeclineEditReplyMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.O_DECLINE_DONT_NOTIFY_ORGANIZER ) {
			
			locator = Locators.DeclineDontNotifyOrganizerMenu;
			page = null;
			doPostfixCheck = true;
			
		} else if ( button == Button.B_PROPOSE_NEW_TIME ) {
			
			locator = Locators.ProposeNewTimeButton;
			page = null;

		} else if ( button == Button.B_ACCEPT_SHARE ) {

			locator = this.ContainerLocator + " td[id$='__Shr__SHARE_ACCEPT_title']";
			page = new DialogShareAccept(MyApplication, ((AppAjaxClient) MyApplication).zPageMail);
			doPostfixCheck = true;

		} else if ( button == Button.B_DECLINE_SHARE ) {

			locator = this.ContainerLocator + " td[id$='__Shr__SHARE_DECLINE_title']";
			page = new DialogShareDecline(MyApplication, ((AppAjaxClient) MyApplication).zPageMail);
			doPostfixCheck = true;

		} else  {
			
			throw new HarnessException("no implementation for button: "+ button);

		}
		
		if ( locator == null )
			throw new HarnessException("no locator defined for button "+ button);
		
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("locator is not present for button "+ button +" : "+ locator);
		
		this.zClick(locator);
		
		this.zWaitForBusyOverlay();

		if ( page != null ) {
			page.zWaitForActive();
		}
		
		if ( doPostfixCheck ) {
			// Make sure the response is delivered before proceeding
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();
		}

		return (page);
	}
	
	public AbsPage zPressButtonPulldown(Button pulldown, Button option) throws HarnessException {
		
		logger.info(myPageName() + " zPressButtonPulldown(" + pulldown + ", " + option + ")");
		
		tracer.trace("Click pulldown " + pulldown + " then " + option);

		if (pulldown == null || option == null) throw new HarnessException("Button/options cannot be null!");
		
		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = this;
		boolean doPostfixCheck = false;

		if ( pulldown == Button.B_ACCEPT ) {
			
			pulldownLocator = Locators.AcceptDropdown;
			
			if (option == Button.O_ACCEPT_NOTIFY_ORGANIZER) {

				optionLocator = Locators.AcceptNotifyOrganizerMenu;
				doPostfixCheck = true;
				page = this;

			} else if (option == Button.O_ACCEPT_EDIT_REPLY) {

				optionLocator = Locators.AcceptEditReplyMenu;
				doPostfixCheck = false;
				page = new FormMailNew(this.MyApplication);
				
			} else if (option == Button.O_ACCEPT_DONT_NOTIFY_ORGANIZER) {

				optionLocator = Locators.AcceptDontNotifyOrganizerMenu;
				doPostfixCheck = false;
				page = this;
				
			} else {
	
				throw new HarnessException("No logic defined for pulldown " + pulldown + " and option " + option);

			}

		} else if ( pulldown == Button.B_TENTATIVE ) {
			
			pulldownLocator = Locators.TentativeDropdown;
			
			if (option == Button.O_TENTATIVE_NOTIFY_ORGANIZER) {

				optionLocator = Locators.TentativeNotifyOrganizerMenu;
				doPostfixCheck = true;
				page = this;

			} else if (option == Button.O_TENTATIVE_EDIT_REPLY) {

				optionLocator = Locators.TentativeEditReplyMenu;
				doPostfixCheck = false;
				page = new FormMailNew(this.MyApplication);
				
			} else if (option == Button.O_TENTATIVE_DONT_NOTIFY_ORGANIZER) {

				optionLocator = Locators.TentativeDontNotifyOrganizerMenu;
				doPostfixCheck = false;
				page = this;
				
			} else {
	
				throw new HarnessException("No logic defined for pulldown " + pulldown + " and option " + option);

			}

		} else if ( pulldown == Button.B_DECLINE ) {
			
			pulldownLocator = Locators.DeclineDropdown;
			
			if (option == Button.O_DECLINE_NOTIFY_ORGANIZER) {

				optionLocator = Locators.DeclineNotifyOrganizerMenu;
				doPostfixCheck = true;
				page = this;

			} else if (option == Button.O_DECLINE_EDIT_REPLY) {

				optionLocator = Locators.DeclineEditReplyMenu;
				doPostfixCheck = false;
				page = new FormMailNew(this.MyApplication);
				
			} else if (option == Button.O_DECLINE_DONT_NOTIFY_ORGANIZER) {

				optionLocator = Locators.DeclineDontNotifyOrganizerMenu;
				doPostfixCheck = false;
				page = this;
				
			} else {
	
				throw new HarnessException("No logic defined for pulldown " + pulldown + " and option " + option);

			}
			
		}

		// Click to dropdown and corresponding option
		
		zClickAt(pulldownLocator, "");
		
		zWaitForBusyOverlay();
		
		zClick(optionLocator);
		
		zWaitForBusyOverlay();

		if (page != null) {
			page.zWaitForActive();
		}

		if ( doPostfixCheck ) {
			// Make sure the response is delivered before proceeding
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();
		}

		return (page);
		
	}
	
	/**
	 * Parse an attachment row from a message
	 * @param locator
	 * @return
	 * @throws HarnessException
	 */
	protected AttachmentItem parseAttachmentRow(String locator) throws HarnessException {
		
		AttachmentItem item = new AttachmentItem();
		
		// Remember the locator to this structure.
		// The locator can be re-used when clicking on the attachment.
		item.setLocator(locator);
		
		// Set the icon class
		//
		// "tr>td>div" ... probably not the most stable locator, however, there
		// is no unique ID on the image element.  If this locator breaks,
		// just file a bug to put an ID on that element, e.g. div[id$='_icon']
		//
		if ( this.sIsElementPresent(locator + " tr>td>div") ) {
			String icon = this.sGetAttribute(locator + " tr>td>div@class");
			item.setAttachmentIcon(AttachmentItem.AttachmentIcon.valueOf(icon));
		}
		
		// Set the file name
		if ( this.sIsElementPresent(locator + " a[id$='_main']") ) {
			item.setAttachmentName(this.sGetText(locator + " a[id$='_main']"));
		}		

		return (item);

	}
	
	/**
	 * Get the list of attachments on the displayed message
	 * @return
	 * @throws HarnessException
	 */
	public List<AttachmentItem> zListGetAttachments() throws HarnessException {
		logger.info(myPageName() + " zListGetAttachments()");
		
		List<AttachmentItem> items = new ArrayList<AttachmentItem>();

		String listLocator = "css=table[id$='zv__TV__TV-main_MSG_attLinks_table']";

		// Make sure the button exists
		if ( !this.sIsElementPresent(listLocator) ) {
			// No attachments!
			return (items);
		}

		// How many items are in the table?
		String tableLocator = listLocator + ">tbody>tr";
		int count = this.sGetCssCount(tableLocator);
		logger.debug(myPageName() + " zListGetMessages: number of messages: "+ count);

		// Get each conversation's data from the table list
		for (int i = 1; i <= count; i++) {

			// Add the new item to the list
			AttachmentItem item = parseAttachmentRow(listLocator + ">tbody>tr:nth-of-type("+ i +") ");
			items.add(item);
			logger.info(item.prettyPrint());
		}


		return (items);
	}

	public AbsPage zListAttachmentItem(Button button, AttachmentItem attachment) throws HarnessException {
		
		if ( button == null )
			throw new HarnessException("button cannot be null");

		if ( attachment == null )
			throw new HarnessException("attachment cannot be null");

		if ( attachment.getLocator() == null ) {
			throw new HarnessException("Unable to locate attachment with filename("+ attachment.getAttachmentName() +")");
		}
		
		logger.info(myPageName() + " zListAttachmentItem("+ button +", "+ attachment.getAttachmentName() +")");

		tracer.trace(button +" on attachment = "+ attachment.getAttachmentName());

		AbsPage page = null;
		String locator = attachment.getLocator();



		if ( attachment.getLocator() == null ) {
			throw new HarnessException("Unable to locate attachment with filename("+ attachment.getAttachmentName() +")");
		}

		if ( button == Button.B_REMOVE ) {

			locator = attachment.getLocator() + "a[id$='_remove']";
			page = new DialogWarning(
					DialogWarning.DialogWarningID.PermanentlyRemoveTheAttachment,
					MyApplication, 
					((AppAjaxClient) MyApplication).zPageMail);

			this.sClick(locator);
			
			this.zWaitForBusyOverlay();

			if ( page != null ) {
				page.zWaitForActive();
			}
			
			return (page);

		} else if ( button == Button.B_ADD_TO_MY_FILES ) {
			
			locator = "implement me!";
			page = null;
			
		} else {
			throw new HarnessException("implement me!  action = "+ button);
		}


		if ( locator == null )
			throw new HarnessException("no locator defined for button "+ button);
		
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("locator is not present for button "+ button +" : "+ locator);
		
		this.zClick(locator);
		
		this.zWaitForBusyOverlay();

		if ( page != null ) {
			page.zWaitForActive();
		}
		
		return (page);

	}

	public AbsPage zListAttachmentItem(Action action, AttachmentItem attachment) throws HarnessException {

		if ( action == null )
			throw new HarnessException("action cannot be null");

		if ( attachment == null )
			throw new HarnessException("attachment cannot be null");

		if ( attachment.getLocator() == null ) {
			throw new HarnessException("Unable to locate attachment with filename("+ attachment.getAttachmentName() +")");
		}

		logger.info(myPageName() + " zListAttachmentItem("+ action +", "+ attachment.getAttachmentName() +")");

		tracer.trace(action +" on attachment = "+ attachment.getAttachmentName());

		AbsPage page = null;
		String locator = attachment.getLocator();



		if ( attachment.getLocator() == null ) {
			throw new HarnessException("Unable to locate attachment with filename("+ attachment.getAttachmentName() +")");
		}

		if ( action == Action.A_LEFTCLICK ) {

			// Since the window opens without a title bar, initialize it first
			page = new SeparateWindowOpenAttachment(this.MyApplication);
			((SeparateWindowOpenAttachment)page).zInitializeWindowNames();

			// Left-Click on the item
			this.sClick(locator + " a[id$='_main']");

			this.zWaitForBusyOverlay();
			
			// Sucks.  Wait for the page to display.
			// Any have ideas for a workaround?
			SleepUtil.sleepVeryLong();
			
			return (page);

		} else if ( action == Action.A_RIGHTCLICK ) {
			
			locator = "implement me!";
			page = null;
			
		} else {
			throw new HarnessException("implement me!  action = "+ action);
		}


		if ( page != null ) {
			//page.zWaitForActive();
		}

		// default return command
		return (page);

	}
	
	/**
	 * Return TRUE/FALSE whether the appointment Accept/Decline/Tentative buttons are present
	 * @return
	 * @throws HarnessException
	 */
	public boolean zHasShareADButtons() throws HarnessException {
		
		// Haven't fully baked this method.  
		// Maybe it works.  
		// Maybe it needs to check "visible" and/or x/y/z coordinates

		List<String> locators = Arrays.asList(
				this.ContainerLocator + " td[id$='__Shr__SHARE_ACCEPT_title']",
				this.ContainerLocator + " td[id$='__Shr__SHARE_DECLINE_title']");

		for (String locator : locators) {
			if ( !this.sIsElementPresent(locator) )
				return (false);
		}
		
		return (true);

	}

	/**
	 * Return TRUE/FALSE whether the Accept/Decline/Tentative buttons are present
	 * @return
	 * @throws HarnessException
	 */
	public boolean zHasADTButtons() throws HarnessException {
	
		// Haven't fully baked this method.  
		// Maybe it works.  
		// Maybe it needs to check "visible" and/or x/y/z coordinates

		List<String> locators = Arrays.asList(
				this.ContainerLocator + " td[id$='__Inv__REPLY_ACCEPT_title']",
				this.ContainerLocator + " td[id$='__Inv__REPLY_TENTATIVE_title']",
				this.ContainerLocator + " td[id$='__Inv__REPLY_DECLINE_title']",
				this.ContainerLocator + " td[id$='__Inv__PROPOSE_NEW_TIME_title']");

		for (String locator : locators) {
			if ( !this.sIsElementPresent(locator) )
				return (false);
		}
		
		return (true);
	}
	
	public HtmlElement zGetMailPropertyAsHtml(Field field) throws HarnessException {

		String source = null;

		if ( field == Field.Body) {

			try {

				this.sSelectFrame("css=iframe[id='zv__TV-main__MSG__body__iframe']");

				source = this.sGetHtmlSource();

				// For some reason, we don't get the <html/> tag.  Add it
				source = "<html>" + source + "</html>";

			} finally {
				// Make sure to go back to the original iframe
				this.sSelectFrame("relative=top");
			}

		} else {
			throw new HarnessException("not implemented for field "+ field);
		}

		// Make sure source was found
		if ( source == null )
			throw new HarnessException("source was null for "+ field);

		logger.info("DisplayMail.zGetMailPropertyAsHtml() = "+ HtmlElement.clean(source).prettyPrint());

		// Clean up the HTML code to be valid
		return (HtmlElement.clean(source));


	}

	
	/**
	 * Get the string value of the specified field
	 * @return the displayed string value
	 * @throws HarnessException
	 */
	public String zGetMailProperty(Field field) throws HarnessException {
		logger.info("DisplayMail.zGetDisplayedValue(" + field + ")");

		String locator = null;
		
		if ( field == Field.Bcc ) {
			
			// Does this show in any mail views?  Maybe in Sent?
			throw new HarnessException("implement me!");
			
		} else if ( field == Field.Body ) {

			/*
			 * To get the body contents, need to switch iframes
			 */
			try {
				
				this.sSelectFrame("//iframe[contains(@id, '_body__iframe')]");
				
				String bodyLocator = "css=body";
				
				// Make sure the body is present
				if ( !this.sIsElementPresent(bodyLocator) )
					throw new HarnessException("Unable to find the message body!");
				
				// Get the body value
				// String body = this.sGetText(bodyLocator).trim();
				String html = this.zGetHtml(bodyLocator);
				
				logger.info("DisplayMail.zGetBody(" + bodyLocator + ") = " + html);
				return(html);

			} finally {
				// Make sure to go back to the original iframe
				this.sSelectFrame("relative=top");
			}

		} else if ( field == Field.Cc ) {
			
			locator = this.ContainerLocator + " tr[id$='_cc'] td[class~='LabelColValue'] span[id$='_org_zmail_email'] span span";
			if ( !sIsElementPresent(locator) ) {
				// no email zimlet case
				locator = this.ContainerLocator + " tr[id$='_cc'] td[class~='LabelColValue']";
			}
			
		} else if ( field == Field.From ) {
			
			locator = this.ContainerLocator + " td[id$='_from'] span[id$='_org_zmail_email'] span span";
			if ( !sIsElementPresent(locator) ) {
				// no email zimlet case
				locator = this.ContainerLocator + " td[id$='_from']";
			}

		} else if ( field == Field.OnBehalfOf ) {
			
			locator = this.ContainerLocator + " td[id$='_obo'] span[id$='_org_zmail_email'] span span";
			if ( !sIsElementPresent(locator) ) {
				// no email zimlet case
				locator = this.ContainerLocator + " td[id$='_obo']";
			}

		} else if ( field == Field.ResentFrom ) {
			
			locator = this.ContainerLocator + " td[id$='_bwo'] span[id$='_org_zmail_email'] span";
			if ( !sIsElementPresent(locator) ) {
				// no email zimlet case
				locator = this.ContainerLocator + " td[id$='_bwo']";
			}

		} else if ( field == Field.OnBehalfOfLabel ) {
			
			locator = this.ContainerLocator + " td[id$='_obo_label']";

		} else if ( field == Field.ReplyTo ) {
			
			locator = this.ContainerLocator + " tr[id$='_reply to'] td.LabelColValue span[id$='_org_zmail_email'] span span";
			if ( !sIsElementPresent(locator) ) {
				// no email zimlet case
				locator = this.ContainerLocator + " tr[id$='_reply to'] td.LabelColValue";
			}

		} else if ( field == Field.ReceivedDate ) {
			
			locator = this.ContainerLocator + " tr[id$='_hdrTableTopRow'] td[class~='DateCol']";

		} else if ( field == Field.ReceivedTime ) {
			
			String timeAndDateLocator = this.ContainerLocator + " td[class~='DateCol']";

			// Make sure the subject is present
			if ( !this.sIsElementPresent(timeAndDateLocator) )
				throw new HarnessException("Unable to find the time and date field!");
			
			// Get the subject value
			String timeAndDate = this.sGetText(timeAndDateLocator).trim();
			String date = this.zGetMailProperty(Field.ReceivedDate);
			
			// Strip the date so that only the time remains
			String time = timeAndDate.replace(date, "").trim();
			
			logger.info("DisplayMail.zGetDisplayedValue(" + field + ") = " + time);
			return(time);

		} else if ( field == Field.Subject ) {
			
			locator = this.ContainerLocator + " tr[id$='_hdrTableTopRow'] td[class~='SubjectCol']";

		} else if ( field == Field.To ) {
			
			locator = this.ContainerLocator + " tr[id$='_to'] td[class='LabelColValue'] span[id$='_org_zmail_email'] span span";
			if ( !sIsElementPresent(locator) ) {
				// no email zimlet case
				locator = this.ContainerLocator + " tr[id$='_to'] td[class='LabelColValue'] ";
			}

		} else {
			
			throw new HarnessException("no logic defined for field "+ field);
			
		}

		// Make sure something was set
		if ( locator == null )
			throw new HarnessException("locator was null for field = "+ field);
		
		// Default behavior, process the locator by clicking on it
		//
		
		// Make sure the subject is present
		if ( !this.sIsElementPresent(locator) )
			throw new HarnessException("Unable to find the field = "+ field +" using locator = "+ locator);
		
		// Get the subject value
		String value = this.sGetText(locator).trim();
		
		logger.info("DisplayMail.zGetDisplayedValue(" + field + ") = " + value);
		return(value);

		
	}
	
	public AbsPage zHoverOver(Field field) throws HarnessException {
		logger.info("zHoverOver("+ field +")");

		if ( field == null ) {
			throw new HarnessException("field cannot be null");
		}
		
		AbsPage page = null;
		String locator = null;
		
		if ( field == Field.From ) {
			
			locator = this.ContainerLocator + " td[id$='_from'] span[id$='_org_zmail_email'] span";

		} else {
			throw new HarnessException("Logic not defined for field="+ field);

		}
		
		if ( locator == null ) {
			throw new HarnessException("locator was null!");
		}
		
		if ( !(this.sIsElementPresent(locator)) ) {
			throw new HarnessException("locator not present: "+ locator);
		}
		
		this.sMouseOver(locator);
		this.zWaitForBusyOverlay();
		
		if ( page != null ) {
			page.zWaitForActive();
		}
		
		return (page);

	}
	

	/**
	 * Wait for Zimlets to be rendered in the message
	 * @throws HarnessException
	 */
	public void zWaitForZimlets() throws HarnessException {
		// TODO: don't sleep.  figure out a way to query the app if zimlets are applied
		logger.info("zWaitForZimlets: sleep a bit to let the zimlets be applied");
		SleepUtil.sleepLong();
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		//logger.warn("implement me", new Throwable());
		zWaitForZimlets();
		
		// Determine which <div/> contains this preview
		// Use this 'top' css for all subsequent parsing
		
		if ( this.zIsVisiblePerPosition(Locators.MessageViewOpenMessageCSS, 0, 0) ) {
			int count = this.sGetCssCount(Locators.MessageViewOpenMessageCSS);
			if ( count > 1 ) {
				throw new HarnessException("Too many message views open: "+ count);
			}
			ContainerLocator = "css=div#" + this.sGetAttribute(Locators.MessageViewOpenMessageCSS + "@id");
		} else if ( this.zIsVisiblePerPosition(PageMail.Locators.IsMsgViewActiveCSS, 0, 0)) {
			if ( this.zIsVisiblePerPosition(Locators.MessageViewPreviewAtBottomCSS, 0, 0) ) {
				ContainerLocator = Locators.MessageViewPreviewAtBottomCSS;
			} else if ( this.zIsVisiblePerPosition(Locators.MessageViewPreviewAtRightCSS, 0, 0) ) {
				ContainerLocator = Locators.MessageViewPreviewAtRightCSS;
			} else {
				throw new HarnessException("Unable to determine the current open view");				
			}
		} else if ( this.zIsVisiblePerPosition(PageMail.Locators.IsConViewActiveCSS, 0, 0) ) {
			if ( this.zIsVisiblePerPosition(Locators.ConversationViewPreviewAtBottomCSS, 0, 0) ) {
				ContainerLocator = Locators.ConversationViewPreviewAtBottomCSS;
			} else if ( this.zIsVisiblePerPosition(Locators.ConversationViewPreviewAtRightCSS, 0, 0) ){
				ContainerLocator = Locators.ConversationViewPreviewAtRightCSS;
			} else {
				throw new HarnessException("Unable to determine the current open view");
			}
		} else {
			throw new HarnessException("Unable to determine the current open view");
		}
		

		return (sIsElementPresent(this.ContainerLocator) );
				
	}

}
