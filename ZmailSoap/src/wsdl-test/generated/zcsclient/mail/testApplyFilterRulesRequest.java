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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import generated.zcsclient.zm.testNamedElement;


/**
 * <p>Java class for applyFilterRulesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="applyFilterRulesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filterRules">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="filterRule" type="{urn:zimbra}namedElement" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="m" type="{urn:zimbraMail}idsAttr" minOccurs="0"/>
 *         &lt;element name="query" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applyFilterRulesRequest", propOrder = {
    "filterRules",
    "m",
    "query"
})
public class testApplyFilterRulesRequest {

    @XmlElement(required = true)
    protected testApplyFilterRulesRequest.FilterRules filterRules;
    protected testIdsAttr m;
    protected String query;

    /**
     * Gets the value of the filterRules property.
     * 
     * @return
     *     possible object is
     *     {@link testApplyFilterRulesRequest.FilterRules }
     *     
     */
    public testApplyFilterRulesRequest.FilterRules getFilterRules() {
        return filterRules;
    }

    /**
     * Sets the value of the filterRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link testApplyFilterRulesRequest.FilterRules }
     *     
     */
    public void setFilterRules(testApplyFilterRulesRequest.FilterRules value) {
        this.filterRules = value;
    }

    /**
     * Gets the value of the m property.
     * 
     * @return
     *     possible object is
     *     {@link testIdsAttr }
     *     
     */
    public testIdsAttr getM() {
        return m;
    }

    /**
     * Sets the value of the m property.
     * 
     * @param value
     *     allowed object is
     *     {@link testIdsAttr }
     *     
     */
    public void setM(testIdsAttr value) {
        this.m = value;
    }

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="filterRule" type="{urn:zimbra}namedElement" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "filterRule"
    })
    public static class FilterRules {

        protected List<testNamedElement> filterRule;

        /**
         * Gets the value of the filterRule property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the filterRule property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFilterRule().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link testNamedElement }
         * 
         * 
         */
        public List<testNamedElement> getFilterRule() {
            if (filterRule == null) {
                filterRule = new ArrayList<testNamedElement>();
            }
            return this.filterRule;
        }

    }

}
