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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDistributionListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDistributionListResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:zmailAdmin}dl" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="more" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="total" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDistributionListResponse", propOrder = {
    "dl"
})
public class testGetDistributionListResponse {

    protected testDistributionListInfo dl;
    @XmlAttribute(name = "more")
    protected Boolean more;
    @XmlAttribute(name = "total")
    protected Integer total;

    /**
     * Gets the value of the dl property.
     * 
     * @return
     *     possible object is
     *     {@link testDistributionListInfo }
     *     
     */
    public testDistributionListInfo getDl() {
        return dl;
    }

    /**
     * Sets the value of the dl property.
     * 
     * @param value
     *     allowed object is
     *     {@link testDistributionListInfo }
     *     
     */
    public void setDl(testDistributionListInfo value) {
        this.dl = value;
    }

    /**
     * Gets the value of the more property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMore() {
        return more;
    }

    /**
     * Sets the value of the more property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMore(Boolean value) {
        this.more = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotal(Integer value) {
        this.total = value;
    }

}
