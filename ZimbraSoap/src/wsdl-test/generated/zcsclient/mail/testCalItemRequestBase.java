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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for calItemRequestBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="calItemRequestBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="m" type="{urn:zimbraMail}msg" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="echo" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="html" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="neuter" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="forcesend" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calItemRequestBase", propOrder = {
    "m"
})
@XmlSeeAlso({
    testCreateAppointmentExceptionRequest.class,
    testModifyAppointmentRequest.class,
    testCreateAppointmentRequest.class
})
public abstract class testCalItemRequestBase {

    protected testMsg m;
    @XmlAttribute(name = "echo")
    protected Boolean echo;
    @XmlAttribute(name = "max")
    protected Integer max;
    @XmlAttribute(name = "html")
    protected Boolean html;
    @XmlAttribute(name = "neuter")
    protected Boolean neuter;
    @XmlAttribute(name = "forcesend")
    protected Boolean forcesend;

    /**
     * Gets the value of the m property.
     * 
     * @return
     *     possible object is
     *     {@link testMsg }
     *     
     */
    public testMsg getM() {
        return m;
    }

    /**
     * Sets the value of the m property.
     * 
     * @param value
     *     allowed object is
     *     {@link testMsg }
     *     
     */
    public void setM(testMsg value) {
        this.m = value;
    }

    /**
     * Gets the value of the echo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEcho() {
        return echo;
    }

    /**
     * Sets the value of the echo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEcho(Boolean value) {
        this.echo = value;
    }

    /**
     * Gets the value of the max property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMax(Integer value) {
        this.max = value;
    }

    /**
     * Gets the value of the html property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHtml() {
        return html;
    }

    /**
     * Sets the value of the html property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHtml(Boolean value) {
        this.html = value;
    }

    /**
     * Gets the value of the neuter property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeuter() {
        return neuter;
    }

    /**
     * Sets the value of the neuter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeuter(Boolean value) {
        this.neuter = value;
    }

    /**
     * Gets the value of the forcesend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForcesend() {
        return forcesend;
    }

    /**
     * Sets the value of the forcesend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForcesend(Boolean value) {
        this.forcesend = value;
    }

}
