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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for filterRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="filterRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filterTests" type="{urn:zimbraMail}filterTests"/>
 *         &lt;element name="filterActions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                     &lt;element name="actionKeep" type="{urn:zimbraMail}keepAction"/>
 *                     &lt;element name="actionDiscard" type="{urn:zimbraMail}discardAction"/>
 *                     &lt;element name="actionFileInto" type="{urn:zimbraMail}fileIntoAction"/>
 *                     &lt;element name="actionFlag" type="{urn:zimbraMail}flagAction"/>
 *                     &lt;element name="actionTag" type="{urn:zimbraMail}tagAction"/>
 *                     &lt;element name="actionRedirect" type="{urn:zimbraMail}redirectAction"/>
 *                     &lt;element name="actionReply" type="{urn:zimbraMail}replyAction"/>
 *                     &lt;element name="actionNotify" type="{urn:zimbraMail}notifyAction"/>
 *                     &lt;element name="actionStop" type="{urn:zimbraMail}stopAction"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="active" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "filterRule", propOrder = {
    "filterTests",
    "filterActions"
})
public class testFilterRule {

    @XmlElement(required = true)
    protected testFilterTests filterTests;
    protected testFilterRule.FilterActions filterActions;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "active", required = true)
    protected boolean active;

    /**
     * Gets the value of the filterTests property.
     * 
     * @return
     *     possible object is
     *     {@link testFilterTests }
     *     
     */
    public testFilterTests getFilterTests() {
        return filterTests;
    }

    /**
     * Sets the value of the filterTests property.
     * 
     * @param value
     *     allowed object is
     *     {@link testFilterTests }
     *     
     */
    public void setFilterTests(testFilterTests value) {
        this.filterTests = value;
    }

    /**
     * Gets the value of the filterActions property.
     * 
     * @return
     *     possible object is
     *     {@link testFilterRule.FilterActions }
     *     
     */
    public testFilterRule.FilterActions getFilterActions() {
        return filterActions;
    }

    /**
     * Sets the value of the filterActions property.
     * 
     * @param value
     *     allowed object is
     *     {@link testFilterRule.FilterActions }
     *     
     */
    public void setFilterActions(testFilterRule.FilterActions value) {
        this.filterActions = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
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
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="actionKeep" type="{urn:zimbraMail}keepAction"/>
     *           &lt;element name="actionDiscard" type="{urn:zimbraMail}discardAction"/>
     *           &lt;element name="actionFileInto" type="{urn:zimbraMail}fileIntoAction"/>
     *           &lt;element name="actionFlag" type="{urn:zimbraMail}flagAction"/>
     *           &lt;element name="actionTag" type="{urn:zimbraMail}tagAction"/>
     *           &lt;element name="actionRedirect" type="{urn:zimbraMail}redirectAction"/>
     *           &lt;element name="actionReply" type="{urn:zimbraMail}replyAction"/>
     *           &lt;element name="actionNotify" type="{urn:zimbraMail}notifyAction"/>
     *           &lt;element name="actionStop" type="{urn:zimbraMail}stopAction"/>
     *         &lt;/choice>
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
        "actionKeepOrActionDiscardOrActionFileInto"
    })
    public static class FilterActions {

        @XmlElements({
            @XmlElement(name = "actionStop", type = testStopAction.class),
            @XmlElement(name = "actionNotify", type = testNotifyAction.class),
            @XmlElement(name = "actionReply", type = testReplyAction.class),
            @XmlElement(name = "actionTag", type = testTagAction.class),
            @XmlElement(name = "actionFileInto", type = testFileIntoAction.class),
            @XmlElement(name = "actionDiscard", type = testDiscardAction.class),
            @XmlElement(name = "actionRedirect", type = testRedirectAction.class),
            @XmlElement(name = "actionFlag", type = testFlagAction.class),
            @XmlElement(name = "actionKeep", type = testKeepAction.class)
        })
        protected List<testFilterAction> actionKeepOrActionDiscardOrActionFileInto;

        /**
         * Gets the value of the actionKeepOrActionDiscardOrActionFileInto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the actionKeepOrActionDiscardOrActionFileInto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getActionKeepOrActionDiscardOrActionFileInto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link testStopAction }
         * {@link testNotifyAction }
         * {@link testReplyAction }
         * {@link testTagAction }
         * {@link testFileIntoAction }
         * {@link testDiscardAction }
         * {@link testRedirectAction }
         * {@link testFlagAction }
         * {@link testKeepAction }
         * 
         * 
         */
        public List<testFilterAction> getActionKeepOrActionDiscardOrActionFileInto() {
            if (actionKeepOrActionDiscardOrActionFileInto == null) {
                actionKeepOrActionDiscardOrActionFileInto = new ArrayList<testFilterAction>();
            }
            return this.actionKeepOrActionDiscardOrActionFileInto;
        }

    }

}
