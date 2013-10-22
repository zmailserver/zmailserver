/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package com.zimbra.cs.zimlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import com.zimbra.common.util.ByteUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.W3cDomUtil;
import com.zimbra.common.soap.XmlParseException;
import com.zimbra.common.soap.ZimletConstants;

/**
 * Parses the Zimlet description files, <zimlet>.xml and config.xml.
 * 
 * @author jylee
 *
 */
public abstract class ZimletMeta {
	protected Element mTopElement;
	
	protected String mName;
	protected Version mVersion;
	protected String mDescription;
	protected boolean mIsExtension;

	protected String mRawXML;
	protected String mGeneratedXML;
	
	protected ZimletMeta() {
		// empty
	}
	
	public ZimletMeta(File f) throws ZimletException {
		this(readFile(f));
	}

	public ZimletMeta(String meta) throws ZimletException {
		initialize();
		if (meta == null) {
			return;
		}
		try {
			mTopElement = Element.parseXML(meta);
			mRawXML = meta;
		} catch (XmlParseException de) {
			throw ZimletException.INVALID_ZIMLET_DESCRIPTION("Cannot parse Zimlet description: "+de.getMessage());
		}

		validate();
	}

	private static String readFile(File f) throws ZimletException {
		try {
			return new String(ByteUtil.getContent(new FileInputStream(f), -1));
		} catch (IOException ie) {
			throw ZimletException.INVALID_ZIMLET_DESCRIPTION("Cannot find Zimlet description file: " + f.getName());
		}
	}
	
	protected void validate() throws ZimletException {
		if (mTopElement == null) {
			throw ZimletException.INVALID_ZIMLET_DESCRIPTION("Null DOM element");
		}
		String name = mTopElement.getName();
		if (!name.equals(ZimletConstants.ZIMLET_TAG_ZIMLET) && !name.equals(ZimletConstants.ZIMLET_TAG_CONFIG)) {
			throw ZimletException.INVALID_ZIMLET_DESCRIPTION("Top level tag not recognized " + name);
		}
		
		mName = mTopElement.getAttribute(ZimletConstants.ZIMLET_ATTR_NAME, "");
		mVersion = new Version(mTopElement.getAttribute(ZimletConstants.ZIMLET_ATTR_VERSION, ""));
		mDescription = mTopElement.getAttribute(ZimletConstants.ZIMLET_ATTR_DESCRIPTION, "");
		try {
			mIsExtension = mTopElement.getAttributeBool(ZimletConstants.ZIMLET_ATTR_EXTENSION, false);
		} catch (Exception se) {
			mIsExtension = false;
		}

		@SuppressWarnings("unchecked")
		Iterator iter = mTopElement.listElements().iterator();
		while (iter.hasNext()) {
			validateElement((Element) iter.next());
		}
	}
	
	public String getName() {
		assert(mTopElement != null);
		return mName;
	}
	
	public Version getVersion() {
		assert(mTopElement != null);
		return mVersion;
	}
	
	public String getDescription() {
		assert(mTopElement != null);
		return mDescription;
	}
	
	public boolean isExtension() {
		assert(mTopElement != null);
		return mIsExtension;
	}

	/*
	 * returns JSON representation of the parsed DOM tree.
	 */
	public String toJSONString() {
		return toString(Element.JSONElement.mFactory);
	}

	/*
	 * returns XML representation of the parsed DOM tree.
	 */
	public String toXMLString() {
		return toString(Element.XMLElement.mFactory);
	}
	
	public String getRawXML() {
		return mRawXML;
	}
	
	/*
	 * returns either XML or JSON representation of parsed and possibly modified DOM tree.
	 */
	public String toString(Element.ElementFactory f) {
		try {
			if (mGeneratedXML == null) {
				mGeneratedXML = mTopElement.toString();
			}
			return W3cDomUtil.parseXML(mGeneratedXML, f).toString();
		} catch (XmlParseException e) {
			ZimbraLog.zimlet.warn("error parsing the Zimlet file "+mName);
		}
		return "";
	}
	
	/*
	 * attaches the DOM tree underneath the Element passed in.
	 */
	public void addToElement(Element elem) throws ZimletException {
		try {
			// TODO: cache parsed structure or result or both.
			Element newElem = W3cDomUtil.parseXML(toXMLString(), elem.getFactory());
			elem.addElement(newElem);
		} catch (XmlParseException de) {
			throw ZimletException.ZIMLET_HANDLER_ERROR("cannot parse the dom tree");
		}
	}
	
	protected abstract void initialize() throws ZimletException;
	protected abstract void validateElement(Element e) throws ZimletException;
}
