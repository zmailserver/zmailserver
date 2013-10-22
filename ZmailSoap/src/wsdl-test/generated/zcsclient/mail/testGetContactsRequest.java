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

package generated.zcsclient.mail;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import generated.zcsclient.zm.testAttributeName;
import generated.zcsclient.zm.testId;


/**
 * <p>Java class for getContactsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getContactsRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="a" type="{urn:zmail}attributeName" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ma" type="{urn:zmail}attributeName" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cn" type="{urn:zmail}id" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="sync" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="l" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sortBy" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="derefGroupMember" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="returnHiddenAttrs" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="maxMembers" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getContactsRequest", propOrder = {
    "a",
    "ma",
    "cn"
})
public class testGetContactsRequest {

    protected List<testAttributeName> a;
    protected List<testAttributeName> ma;
    protected List<testId> cn;
    @XmlAttribute(name = "sync")
    protected Boolean sync;
    @XmlAttribute(name = "l")
    protected String l;
    @XmlAttribute(name = "sortBy")
    protected String sortBy;
    @XmlAttribute(name = "derefGroupMember")
    protected Boolean derefGroupMember;
    @XmlAttribute(name = "returnHiddenAttrs")
    protected Boolean returnHiddenAttrs;
    @XmlAttribute(name = "maxMembers")
    protected Long maxMembers;

    /**
     * Gets the value of the a property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the a property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testAttributeName }
     * 
     * 
     */
    public List<testAttributeName> getA() {
        if (a == null) {
            a = new ArrayList<testAttributeName>();
        }
        return this.a;
    }

    /**
     * Gets the value of the ma property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ma property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testAttributeName }
     * 
     * 
     */
    public List<testAttributeName> getMa() {
        if (ma == null) {
            ma = new ArrayList<testAttributeName>();
        }
        return this.ma;
    }

    /**
     * Gets the value of the cn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testId }
     * 
     * 
     */
    public List<testId> getCn() {
        if (cn == null) {
            cn = new ArrayList<testId>();
        }
        return this.cn;
    }

    /**
     * Gets the value of the sync property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSync() {
        return sync;
    }

    /**
     * Sets the value of the sync property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSync(Boolean value) {
        this.sync = value;
    }

    /**
     * Gets the value of the l property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getL() {
        return l;
    }

    /**
     * Sets the value of the l property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setL(String value) {
        this.l = value;
    }

    /**
     * Gets the value of the sortBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * Sets the value of the sortBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortBy(String value) {
        this.sortBy = value;
    }

    /**
     * Gets the value of the derefGroupMember property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDerefGroupMember() {
        return derefGroupMember;
    }

    /**
     * Sets the value of the derefGroupMember property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDerefGroupMember(Boolean value) {
        this.derefGroupMember = value;
    }

    /**
     * Gets the value of the returnHiddenAttrs property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnHiddenAttrs() {
        return returnHiddenAttrs;
    }

    /**
     * Sets the value of the returnHiddenAttrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnHiddenAttrs(Boolean value) {
        this.returnHiddenAttrs = value;
    }

    /**
     * Gets the value of the maxMembers property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMaxMembers() {
        return maxMembers;
    }

    /**
     * Sets the value of the maxMembers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaxMembers(Long value) {
        this.maxMembers = value;
    }

}
