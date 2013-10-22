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


/**
 * <p>Java class for tzFixupRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tzFixupRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="match" type="{urn:zmailAdmin}tzFixupRuleMatch"/>
 *         &lt;element name="touch" type="{urn:zmailAdmin}simpleElement" minOccurs="0"/>
 *         &lt;element name="replace" type="{urn:zmailAdmin}tzReplaceInfo" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tzFixupRule", propOrder = {

})
public class testTzFixupRule {

    @XmlElement(required = true)
    protected testTzFixupRuleMatch match;
    protected testSimpleElement touch;
    protected testTzReplaceInfo replace;

    /**
     * Gets the value of the match property.
     * 
     * @return
     *     possible object is
     *     {@link testTzFixupRuleMatch }
     *     
     */
    public testTzFixupRuleMatch getMatch() {
        return match;
    }

    /**
     * Sets the value of the match property.
     * 
     * @param value
     *     allowed object is
     *     {@link testTzFixupRuleMatch }
     *     
     */
    public void setMatch(testTzFixupRuleMatch value) {
        this.match = value;
    }

    /**
     * Gets the value of the touch property.
     * 
     * @return
     *     possible object is
     *     {@link testSimpleElement }
     *     
     */
    public testSimpleElement getTouch() {
        return touch;
    }

    /**
     * Sets the value of the touch property.
     * 
     * @param value
     *     allowed object is
     *     {@link testSimpleElement }
     *     
     */
    public void setTouch(testSimpleElement value) {
        this.touch = value;
    }

    /**
     * Gets the value of the replace property.
     * 
     * @return
     *     possible object is
     *     {@link testTzReplaceInfo }
     *     
     */
    public testTzReplaceInfo getReplace() {
        return replace;
    }

    /**
     * Sets the value of the replace property.
     * 
     * @param value
     *     allowed object is
     *     {@link testTzReplaceInfo }
     *     
     */
    public void setReplace(testTzReplaceInfo value) {
        this.replace = value;
    }

}
