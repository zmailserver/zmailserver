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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import generated.zcsclient.zm.testAccountSelector;
import generated.zcsclient.zm.testGranteeChooser;


/**
 * <p>Java class for getShareInfoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getShareInfoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="grantee" type="{urn:zmail}granteeChooser" minOccurs="0"/>
 *         &lt;element name="owner" type="{urn:zmail}accountSelector"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getShareInfoRequest", propOrder = {
    "grantee",
    "owner"
})
public class testGetShareInfoRequest {

    protected testGranteeChooser grantee;
    @XmlElement(required = true)
    protected testAccountSelector owner;

    /**
     * Gets the value of the grantee property.
     * 
     * @return
     *     possible object is
     *     {@link testGranteeChooser }
     *     
     */
    public testGranteeChooser getGrantee() {
        return grantee;
    }

    /**
     * Sets the value of the grantee property.
     * 
     * @param value
     *     allowed object is
     *     {@link testGranteeChooser }
     *     
     */
    public void setGrantee(testGranteeChooser value) {
        this.grantee = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link testAccountSelector }
     *     
     */
    public testAccountSelector getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link testAccountSelector }
     *     
     */
    public void setOwner(testAccountSelector value) {
        this.owner = value;
    }

}
