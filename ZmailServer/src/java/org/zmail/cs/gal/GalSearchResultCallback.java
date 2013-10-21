/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.cs.gal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import org.zmail.common.mailbox.ContactConstants;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.GalContact;
import org.zmail.cs.account.Group;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Provisioning.SearchGalResult;
import org.zmail.cs.account.gal.GalOp;
import org.zmail.cs.mailbox.Contact;
import org.zmail.cs.service.mail.ToXML;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.service.util.ItemIdFormatter;
import org.zmail.soap.account.type.MemberOfSelector;

public class GalSearchResultCallback implements GalContact.Visitor {
    private Element mResponse;
	protected ItemIdFormatter mFormatter;
	private boolean mIdOnly;
	private GalOp mOp;
	private Account mAuthAcct;
	private boolean mNeedCanExpand;
	private boolean mNeedIsOwner;
    private MemberOfSelector mNeedIsMember;
	private boolean mNeedSMIMECerts;
	
	private Set<String> mOwnerOfGroupsIds = null;
	private Set<String> mMemberOfGroupsIds = null;
    
    public GalSearchResultCallback(GalSearchParams params) {
    	reset(params);
    	mOp = params.getOp();
    }
    
    public void reset(GalSearchParams params) {
    	if (params.getSoapContext() != null) {
    		mResponse = params.getSoapContext().createElement(params.getResponseName());
        	mFormatter = new ItemIdFormatter(params.getSoapContext());
    	} else {
    	    mResponse = Element.XMLElement.mFactory.createElement(params.getResponseName());
    	    mFormatter = new ItemIdFormatter();
    	}
    	params.setGalResult(SearchGalResult.newSearchGalResult(this));
    	mIdOnly = params.isIdOnly();
    	
    	try {
    	    mAuthAcct = params.getAuthAccount();
        } catch (ServiceException e) {
            ZmailLog.gal.warn("unable to get authed account", e);
        }
        mNeedCanExpand = params.getNeedCanExpand();
        mNeedIsOwner = params.getNeedIsOwner();
        mNeedIsMember = params.getNeedIsMember();
        mNeedSMIMECerts = params.getNeedSMIMECerts();
    }
    
    public void visit(GalContact c) throws ServiceException {
    	handleContact(c);
    }
    
    public Element getResponse() {
    	return mResponse;
    }
    
    public boolean passThruProxiedGalAcctResponse() {
        return false;
    }
    
    public void handleProxiedResponse(Element resp) {
        assert(false);
    }
    
    public Element handleContact(Contact c) throws ServiceException {
        Element eContact;
    	if (mIdOnly) {
    	    eContact = mResponse.addElement(MailConstants.E_CONTACT).addAttribute(MailConstants.A_ID, mFormatter.formatItemId(c));
    	} else if (mOp == GalOp.sync) {
    	    eContact = ToXML.encodeContact(mResponse, mFormatter, null, c, true, c.getAllFields().keySet());
    	} else if (mNeedSMIMECerts) {
    		// this is the case only when proxying SearcgGalRequest for the call from 
    	    // GetSMIMEPublicCerts (in ZmailNetwork)
    	    Set<String> fieldSet = new HashSet<String>(c.getFields().keySet());
    		fieldSet.addAll(Contact.getSMIMECertFields());
    	    eContact = ToXML.encodeContact(mResponse, mFormatter, null, c, true, fieldSet);
    	} else {
    	    Set<String> fieldSet = new HashSet<String>(c.getFields().keySet());
            fieldSet.removeAll(Contact.getSMIMECertFields());
    	    eContact = ToXML.encodeContact(mResponse, mFormatter, null, c, true, fieldSet);
    	}
    	
    	eContact.addAttribute(AccountConstants.A_REF, c.get(ContactConstants.A_dn));
    	
    	if (c.isGroup()) {
    	    String zmailId = c.get(ContactConstants.A_zmailId);
    	        
        	if (mNeedCanExpand) {
        	    boolean canExpand = GalSearchControl.canExpandGalGroup(
        	            c.get(ContactConstants.A_email), zmailId, mAuthAcct);
                eContact.addAttribute(AccountConstants.A_EXP, canExpand);
        	}
        	
        	if (mNeedIsOwner) {
        	    boolean isOwner = isOwner(zmailId);
        	    eContact.addAttribute(AccountConstants.A_IS_OWNER, isOwner);
        	}
        	
        	if (MemberOfSelector.none != mNeedIsMember) {
        	    boolean isMember = isMember(zmailId);
                eContact.addAttribute(AccountConstants.A_IS_MEMBER, isMember);
        	}
    	}
    	
    	return eContact;
    }
    
    public void handleContact(GalContact c) throws ServiceException {
		Element eGalContact = ToXML.encodeGalContact(mResponse, c);
		eGalContact.addAttribute(AccountConstants.A_REF, c.getId());
		
		if (c.isGroup()) {
		    String zmailId = c.getSingleAttr(ContactConstants.A_zmailId);
		        
    		if (mNeedCanExpand) {
    		    boolean canExpand = GalSearchControl.canExpandGalGroup(
    		            c.getSingleAttr(ContactConstants.A_email), zmailId, mAuthAcct);
    		    eGalContact.addAttribute(AccountConstants.A_EXP, canExpand);
    		}
    		
            if (mNeedIsOwner) {
                boolean isOwner = isOwner(zmailId);
                eGalContact.addAttribute(AccountConstants.A_IS_OWNER, isOwner);
            }
            
            if (MemberOfSelector.none != mNeedIsMember) {
                boolean isMember = isMember(zmailId);
                eGalContact.addAttribute(AccountConstants.A_IS_MEMBER, isMember);
            }
		}
    }
    
    public void handleElement(Element e) throws ServiceException {
    	mResponse.addElement(e.detach());
    }
    
    public void handleDeleted(ItemId id) {
    	mResponse.addElement(MailConstants.E_DELETED).addAttribute(MailConstants.A_ID, id.toString());
    }
    
    protected HashMap<String,Object> parseContactElement(Element e) {
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	Iterator<Element> iter = e.elementIterator(MailConstants.E_ATTRIBUTE);
    	while (iter.hasNext()) {
    		Element elem = iter.next();
    		String key = elem.getAttribute(MailConstants.A_ATTRIBUTE_NAME, null);
    		String value = elem.getText();
    		if (key == null)
    			continue;
    		Object obj = map.get(key);
    		if (obj != null) {
    			if (obj instanceof String) {
    				String[] str = new String[2];
    				str[0] = (String)obj;
    				str[1] = value;
    				map.put(key, str);
    			} else if (obj instanceof String[]) {
    				ArrayList<String> arr = new ArrayList<String>();
    				arr.addAll(Arrays.asList((String[])obj));
    				arr.add(value);
    				map.put(key, arr.toArray(new String[0]));
    			}
    		} else {
    			map.put(key, value);
    		}
    	}
    	return map;
    }
    public void setNewToken(String newToken) {
    	setNewToken(new GalSyncToken(newToken));
    }
    public void setNewToken(GalSyncToken newToken) {
    	String oldToken = mResponse.getAttribute(MailConstants.A_TOKEN, null);
    	if (oldToken != null)
    		newToken.merge(new GalSyncToken(oldToken));
    	mResponse.addAttribute(MailConstants.A_TOKEN, newToken.toString());
    }
    public void setSortBy(String sortBy) {
        mResponse.addAttribute(MailConstants.A_SORTBY, sortBy);
    }
    public void setQueryOffset(int offset) {
        mResponse.addAttribute(MailConstants.A_QUERY_OFFSET, offset);
    }
    public void setHasMoreResult(boolean more) {
        mResponse.addAttribute(MailConstants.A_QUERY_MORE, more);
    }
    
    public void setThrottled(boolean throttled) {
        mResponse.addAttribute(MailConstants.A_GALSYNC_THROTTLED, throttled);
    }
    
    public void setFullSyncRecommended(boolean value) {
        mResponse.addAttribute(MailConstants.A_GALSYNC_FULLSYNC_RECOMMENDED, value);
    }
    
    public void setGalDefinitionLastModified(String timestamp) {
        mResponse.addAttribute(MailConstants.A_GAL_DEFINITION_LAST_MODIFIED, timestamp);
    }
    
    private boolean isOwner(String groupZmailId) throws ServiceException {
        if (mAuthAcct == null || groupZmailId == null) {
            return false;
        }
        
        if (mOwnerOfGroupsIds == null) {
            mOwnerOfGroupsIds = Group.GroupOwner.getOwnedGroupsIds(mAuthAcct);
        }
        
        return mOwnerOfGroupsIds.contains(groupZmailId);
    }
    
    private boolean isMember(String groupZmailId) throws ServiceException {
        if (mAuthAcct == null || groupZmailId == null) {
            return false;
        }
        
        if (mMemberOfGroupsIds == null) {
            mMemberOfGroupsIds = Provisioning.getInstance().getGroups(mAuthAcct);
        }
        
        return mMemberOfGroupsIds.contains(groupZmailId);
    }
    
    public static abstract class PassThruGalSearchResultCallback extends GalSearchResultCallback {
        protected Element mProxiedResponse;
        protected boolean mPagingSupported; // default to false
        
        public PassThruGalSearchResultCallback(GalSearchParams params) {
            super(params);
        }
    
        @Override
        public boolean passThruProxiedGalAcctResponse() {
            return true;
        }
        
        @Override
        public void handleProxiedResponse(Element resp) {
            mProxiedResponse = resp;
            mProxiedResponse.detach();
        }
        
        @Override
        public Element getResponse() {
            if (mProxiedResponse != null)
                return mProxiedResponse;
            else {
                Element resp = super.getResponse();
                resp.addAttribute(AccountConstants.A_PAGINATION_SUPPORTED, mPagingSupported);
                return super.getResponse();
            }
        }
        
        @Override
        public void handleElement(Element e) throws ServiceException {
            // should never be called
            throw ServiceException.FAILURE("internal error, method should not be called", null);
        }
        
        @Override
        public void setQueryOffset(int offset) {
            super.setQueryOffset(offset);
            mPagingSupported = true;
        }

    }
}
