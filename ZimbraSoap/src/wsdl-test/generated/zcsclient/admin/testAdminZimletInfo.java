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

package generated.zcsclient.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for adminZimletInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="adminZimletInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="zimletContext" type="{urn:zimbraAdmin}adminZimletContext" minOccurs="0"/>
 *         &lt;element name="zimlet" type="{urn:zimbraAdmin}adminZimletDesc" minOccurs="0"/>
 *         &lt;element name="zimletConfig" type="{urn:zimbraAdmin}adminZimletConfigInfo" minOccurs="0"/>
 *         &lt;any processContents='skip' namespace='##other'/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adminZimletInfo", propOrder = {
    "zimletContext",
    "zimlet",
    "zimletConfig",
    "any"
})
public class testAdminZimletInfo {

    protected testAdminZimletContext zimletContext;
    protected testAdminZimletDesc zimlet;
    protected testAdminZimletConfigInfo zimletConfig;
    @XmlAnyElement
    protected Element any;

    /**
     * Gets the value of the zimletContext property.
     * 
     * @return
     *     possible object is
     *     {@link testAdminZimletContext }
     *     
     */
    public testAdminZimletContext getZimletContext() {
        return zimletContext;
    }

    /**
     * Sets the value of the zimletContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link testAdminZimletContext }
     *     
     */
    public void setZimletContext(testAdminZimletContext value) {
        this.zimletContext = value;
    }

    /**
     * Gets the value of the zimlet property.
     * 
     * @return
     *     possible object is
     *     {@link testAdminZimletDesc }
     *     
     */
    public testAdminZimletDesc getZimlet() {
        return zimlet;
    }

    /**
     * Sets the value of the zimlet property.
     * 
     * @param value
     *     allowed object is
     *     {@link testAdminZimletDesc }
     *     
     */
    public void setZimlet(testAdminZimletDesc value) {
        this.zimlet = value;
    }

    /**
     * Gets the value of the zimletConfig property.
     * 
     * @return
     *     possible object is
     *     {@link testAdminZimletConfigInfo }
     *     
     */
    public testAdminZimletConfigInfo getZimletConfig() {
        return zimletConfig;
    }

    /**
     * Sets the value of the zimletConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link testAdminZimletConfigInfo }
     *     
     */
    public void setZimletConfig(testAdminZimletConfigInfo value) {
        this.zimletConfig = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Element }
     *     
     */
    public Element getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link Element }
     *     
     */
    public void setAny(Element value) {
        this.any = value;
    }

}
