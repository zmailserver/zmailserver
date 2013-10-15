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
/**
 * 
 */
package org.zmail.qa.selenium.framework.items;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.qa.selenium.framework.util.HarnessException;
import org.zmail.qa.selenium.framework.util.ZmailAccount;
import org.zmail.qa.selenium.framework.util.ZmailSeleniumProperties;


/**
 * @author Matt Rhoades
 *
 */
public class SavedSearchFolderItem extends AItem implements IItem {
	protected static Logger logger = LogManager.getLogger(IItem.class);

	private String name = null;
	private String query = null;
	private String types = null;
	private String parentId = "0";
	
	/**
	 * Create a new SavedSearchFolderItem object
	 */
	public SavedSearchFolderItem() {
		setName(ZmailSeleniumProperties.getUniqueString());
	}

	
	public void createUsingSOAP(ZmailAccount account) throws HarnessException {
		
		// TODO: handle all folder properties, not just name and parent
		
		// TODO: Maybe use JaxbUtil to create it?
		
		account.soapSend(
				"<CreateSearchFolderRequest xmlns='urn:zmailMail'>" +
					"<search name='"+ getName() +"' query='"+ getQuery() +"' types='"+ getTypes() +"' sortBy='dateDesc' l='1'/>" +
				"</CreateSearchFolderRequest>");
		
		Element[] response = account.soapSelectNodes("//mail:CreateSearchFolderResponse");
		if ( response.length != 1 ) {
			throw new HarnessException("Unable to create folder "+ account.soapLastResponse());
		}
		
	}

	/**
	 * Import a SavedSearchFolderItem specified in a <search/> element from GetSearchFolderRequest
	 * <br>
	 * @param response
	 * @return
	 * @throws HarnessException
	 */
	public static SavedSearchFolderItem importFromSOAP(Element search) throws HarnessException {
		if ( search == null )
			throw new HarnessException("Element cannot be null");

		logger.debug("importFromSOAP("+ search.prettyPrint() +")");

		// TODO: can the ZmailSOAP methods be used to convert this response to item?
		
		// Example response:
		//	    <GetSearchFolderResponse xmlns="urn:zmailMail">
		//			<search id="..." name="..." query="..." [types="..."] [sortBy="..."] l="{folder}"/>+
		//		</GetSearchFolderResponse>

		
		SavedSearchFolderItem item = null;
		
		try {
			
			item = new SavedSearchFolderItem();
			item.setId(search.getAttribute("id"));
			item.setParentId(search.getAttribute("l"));
			item.setName(search.getAttribute("name"));
			item.setQuery(search.getAttribute("query"));
			//item.setTypes(search.getAttribute("types"));
			
			return (item);
			
		} catch (NumberFormatException e) {
			throw new HarnessException("Unable to create FolderItem", e);
		} catch (ServiceException e) {
			throw new HarnessException("Unable to create FolderItem", e);
		} finally {
			if ( item != null )	logger.info(item.prettyPrint());
		}
	}


	/**
	 * Import a folder by name
	 * @param account
	 * @param folder
	 * @return
	 * @throws HarnessException
	 */
	public static SavedSearchFolderItem importFromSOAP(ZmailAccount account, String name) throws HarnessException {
		logger.debug("importFromSOAP("+ account.EmailAddress +", "+ name +")");
		
		// Get all the folders
		account.soapSend("<GetSearchFolderRequest xmlns='urn:zmailMail'/>");
		Element search = account.soapSelectNode("//mail:search[@name='"+ name +"']", 1);
		
		return (importFromSOAP(search));
	}

	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(SavedSearchFolderItem.class.getSimpleName()).append('\n');
		sb.append("ID: ").append(getId()).append('\n');
		sb.append("Name: ").append(getName()).append('\n');
		sb.append("Query: ").append(getQuery()).append('\n');
		sb.append("Types: ").append(getTypes()).append('\n');
		sb.append("Parent ID: ").append(getParentId()).append('\n');
		return (sb.toString());
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setQuery(String query) {
		this.query = query;
	}


	public String getQuery() {
		return query;
	}


	public void setTypes(String types) {
		this.types = types;
	}


	public String getTypes() {
		return types;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getParentId() {
		return parentId;
	}


}
