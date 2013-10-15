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
package org.zmail.qa.selenium.framework.items;

import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.*;

import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.ui.Button;
import org.zmail.qa.selenium.framework.util.*;
import org.zmail.qa.selenium.projects.ajax.ui.AppAjaxClient;

/**
 * The <code>DistributionListItem</code> defines a Zmail dl item
 */
public class DistributionListItem extends ContactItem implements IItem {
	protected static Logger logger = LogManager.getLogger(IItem.class);
	public static final String IMAGE_CLASS   = "ImgDistributionList";

	/**
	 * The name of the list
	 */
	private String email = null;
	
	private ZmailAccount owner=null;
	
	/**
	 * The list of emails belong to this list
	 */
	private ArrayList<String> emailList = null;

	/**
	 * The name of the list
	 */
	public String name = null;
	
	/**
	 * The owner(s) of the list
	 */
	public List<String> owners = new ArrayList<String>();
	
	/**
	 * The list's members email addresses
	 */
	public List<String> members = new ArrayList<String>();
	
	/**
	 * The list's attributes
	 */
	public Map<String, String> attributes = new HashMap<String, String>();
	
	
	public DistributionListItem() {	
	}
	
	/**
	 * Create a new dlist item 
	 */
	public DistributionListItem(String displayName) {
		fileAs = displayName;
		type = "dlist";
		emailList = new ArrayList<String>();
	}

	/**
	 * Create a new dlist item+ 
	 */
	public DistributionListItem(String email,String displayName) {
		this.email=email;
		fileAs = displayName;
		type = "dlist";
		emailList = new ArrayList<String>();
	}
	
	public static DistributionListItem createDistributionListItem(ZmailAccount owner) throws HarnessException {
		
		// Set the name
		String unique = ZmailSeleniumProperties.getUniqueString(); // group name is max 20 chars
		String listname = "dlist"+ unique.substring(unique.length() - 10) + "@" + ZmailSeleniumProperties.getStringProperty("testdomain", "testdomain.com");

		// Create the new list
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<CreateDistributionListRequest xmlns='urn:zmailAdmin'>" +
						"<name>" + listname +"</name>" +
						"<a n='zmailMailStatus'>enabled</a>" +
						"<a n='displayName'>" +  listname +"</a>" +
				"</CreateDistributionListRequest>");
		String id = ZmailAdminAccount.GlobalAdmin().soapSelectValue("//admin:CreateDistributionListResponse/admin:dl", "id");

		// Set the owner
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<DistributionListActionRequest xmlns='urn:zmailAccount'>" +
						"<dl by='id'>"+ id +"</dl>" +
						"<action op='addOwners'>" +
							"<owner by='name' type='usr'>"+ owner.EmailAddress +"</owner>" +
						"</action>" +
				"</DistributionListActionRequest>");

		// Add two accounts
		ZmailAdminAccount.GlobalAdmin().soapSend(
				"<AddDistributionListMemberRequest xmlns='urn:zmailAdmin'>" +
						"<id>"+ id +"</id>" +
						"<dlm>" + ZmailAccount.AccountA().EmailAddress + "</dlm>" +
						"<dlm>" + ZmailAccount.AccountB().EmailAddress + "</dlm>" +
				"</AddDistributionListMemberRequest>");

		return (DistributionListItem.importFromSOAP("id", id));

	}
	
	public ArrayList<String> getEmailList() {
		return emailList;
	}
	
	/**
	 * Get the dlist member emails as a comma separated String
	 * @return
	 */
	public String getDList() {
		StringBuilder sb = null;
		for (String email : emailList) {
			if ( sb==null ) {
				sb = new StringBuilder(email);
			} else {
				sb.append(',').append(email);
			}
		}
		return (sb.toString());
	}
	
	public ArrayList<String> addDListMember(String ... memberList) {	     		
        for (String member:memberList) {
        	emailList.add(member);
        }
		return emailList;
	}

	/**
	 * Remove all instances of an emailaddress from the dlist
	 * @param emailaddress
	 * @return the current dlist members
	 */
	public ArrayList<String> removeDListMember(String member) {
	    emailList.remove(member);
		return (emailList);
	}
	
	
	public String setAttribute(String key, String value) {
		
		attributes.put(key, value);
		
		return (value);
	}
	
	
	

	public static void grantRightRequest(ZmailAccount account)throws HarnessException {

	    String accountName   = account.EmailAddress.split("@")[0];
	    String accountDomain = account.EmailAddress.split("@")[1];

		// grant dlist creation request
		
		ZmailAdminAccount.GlobalAdmin().soapSend(
	     "<GrantRightRequest xmlns='urn:zmailAdmin'>" 
			+	"<target xmlns='' by='name' type='domain'>" + accountDomain + "</target>"
			+	"<grantee xmlns='' by='name' type='usr'>" + account.EmailAddress + "</grantee>" 
			+	"<right xmlns=''>createDistList</right>" 
		+ "</GrantRightRequest>");
		
		
		//TODO: verification
		//Element response = ZmailAdminAccount.GlobalAdmin().soapSelectNode("//admin:GrantRightResponse/?????", 1);
	}
	
	public static DistributionListItem createUsingSOAP(ZmailAccount owner, AppAjaxClient app, ZmailAccount ... memberList) throws HarnessException {
				    
	        // Create a dlist
			DistributionListItem dlist = null; 
		    String name = "dl_";
		   
		    
		    String soapStr = "";
		    
		    for (ZmailAccount member:memberList) {
		    	soapStr += 	"<a n='mail'>" + member.EmailAddress + "</a> ";
		    }
	        owner.soapSend(
	        		"<CreateDistributionListRequest xmlns='urn:zmailAccount'>" +
	                "<name>" + name + memberList[0].EmailAddress + "</name>" +
	                soapStr +
	              	"</CreateDistributionListRequest>"
	                       );

	    	//TODO: check CreateDistribution
	    	dlist = new DistributionListItem(name);
			dlist.owner = owner;
			for (ZmailAccount member:memberList) {			    
				dlist.addDListMember(member.EmailAddress);
			}
			
		 	dlist.setId(owner.soapSelectValue("//acct:CreateDistributionListResponse/acct:dl" , "id"));
			
			   
	    	// Refresh addressbook
	    	((AppAjaxClient)app).zPageMain.zToolbarPressButton(Button.B_REFRESH);
		
        return dlist;
	}
	
	public static DistributionListItem importFromSOAP(Element GetDistributionListResponse) throws HarnessException {
		if ( GetDistributionListResponse == null )
			throw new HarnessException("GetDistributionListResponse cannot be null");
		
		/**
		 *     <GetDistributionListResponse total="2" more="0" xmlns="urn:zmailAdmin">
		 *           <dl id="bdd639dd-81db-44ba-b7ee-06beb9e8b762" dynamic="0" name="dlist9873588964@testdomain.com">
		 *             <a n="uid">dlist9873588964</a>
		 *             <a n="mail">dlist9873588964@testdomain.com</a>
		 *             <a n="zmailMailStatus">enabled</a>
		 *             <a n="cn">dlist9873588964@testdomain.com</a>
		 *             <a n="zmailMailHost">zqa-062.eng.vmware.com</a>
		 *             <a n="zmailId">bdd639dd-81db-44ba-b7ee-06beb9e8b762</a>
		 *             <a n="zmailCreateTimestamp">20121011201749Z</a>
		 *             <a n="objectClass">zmailDistributionList</a>
		 *     		   <a n="objectClass">zmailMailRecipient</a>
 		 *             <a n="displayName">dlist9873588964@testdomain.com</a>
		 *             <a n="zmailACE">672d7f71-3629-4bab-9318-35f35f949e4a usr ownDistList</a>
		 *             <a n="zmailMailAlias">dlist9873588964@testdomain.com</a>
		 *             <owners>
		 *               <owner id="672d7f71-3629-4bab-9318-35f35f949e4a" name="enus13499873466623@testdomain.com" type="usr"/>
		 *             </owners>
 		 *            <dlm>enus13499873592055@testdomain.com</dlm>
		 *             <dlm>enus13499873610526@testdomain.com</dlm>
		 *           </dl>
		 *         </GetDistributionListResponse>
		 */

		DistributionListItem dlist = null;

		try {

			// Make sure we only have the GetMsgResponse part
			Element getDistributionListResponse = ZmailAccount.SoapClient.selectNode(GetDistributionListResponse, "//admin:GetDistributionListResponse");
			if ( getDistributionListResponse == null )
				throw new HarnessException("Element does not contain GetDistributionListResponse: " + GetDistributionListResponse.prettyPrint());

			Element dl = ZmailAccount.SoapClient.selectNode(getDistributionListResponse, "//admin:dl");
			if ( dl == null )
				throw new HarnessException("Element does not contain a dl element: "+ getDistributionListResponse.prettyPrint());

			// Create the object
			dlist = new DistributionListItem();

			// Set the ID
			dlist.setId(dl.getAttribute("id", null));
			dlist.name = dl.getAttribute("name", null);
			
			// Iterate the owners
			Element[] owners = ZmailAccount.SoapClient.selectNodes(dl, "//admin:owner");
			for (Element owner : owners) {
				String id = owner.getAttribute("id", null);
				String name = owner.getAttribute("name", null);
				String type = owner.getAttribute("type", null);
				
				dlist.owners.add(name);
				
			}

			// Iterate the members
			Element[] members = ZmailAccount.SoapClient.selectNodes(dl, "//admin:dlm");
			for (Element dlm : members) {
				String name = dlm.getText();
				
				dlist.members.add(name);

			}

			// Iterate the attributes
			Element[] attributes = ZmailAccount.SoapClient.selectNodes(dl, "//admin:a");
			for (Element a : attributes) {
				String key = a.getAttribute("n", "foo");
				String value = a.getText();
				
				dlist.setAttribute(key, value);
			}

			return (dlist);

		} finally {
			if ( dlist != null )	logger.info(dlist.prettyPrint());
		}

	}

	public static DistributionListItem importFromSOAP(String by, String value) throws HarnessException {

		Element response = ZmailAdminAccount.GlobalAdmin().soapSend(
				"<GetDistributionListRequest xmlns='urn:zmailAdmin'>" +
						"<dl by='"+ by +"'>" + value +"</dl>" +
				"</GetDistributionListRequest>");
		
		return (importFromSOAP(response));
	}

	
	public static String getId(ZmailAccount account) {
		return account.soapSelectValue("//mail:CreateContactResponse/mail:cn", "id");
	}
	
	public static String[] getDList(ZmailAccount account) {
		String[] dlist = null; //account.so .soapSelectNodes("//mail:CreateContactResponse/mail:cn/mail:m");    
		return dlist;
	}
	
	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(DistributionListItem.class.getSimpleName()).append('\n');
		sb.append("Name: ").append(email).append('\n');
		for(String owner : owners) {
			sb.append("Owner: ").append(owner).append('\n');
		}
		for(String member : members) {
			sb.append("Member: ").append(member).append('\n');
		}
		for(Entry<String, String> entry : attributes.entrySet()) {
			sb.append(String.format("Attribute: key(%s) value(%s)\n", entry.getKey(), entry.getValue()));
		}
		return (sb.toString());
	}
	

}


