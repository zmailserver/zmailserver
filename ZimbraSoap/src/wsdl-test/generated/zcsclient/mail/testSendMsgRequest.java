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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sendMsgRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sendMsgRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="m" type="{urn:zimbraMail}msgToSend" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="needCalendarSentByFixup" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isCalendarForward" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="noSave" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="suid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMsgRequest", propOrder = {
    "m"
})
public class testSendMsgRequest {

    protected testMsgToSend m;
    @XmlAttribute(name = "needCalendarSentByFixup")
    protected Boolean needCalendarSentByFixup;
    @XmlAttribute(name = "isCalendarForward")
    protected Boolean isCalendarForward;
    @XmlAttribute(name = "noSave")
    protected Boolean noSave;
    @XmlAttribute(name = "suid")
    protected String suid;

    /**
     * Gets the value of the m property.
     * 
     * @return
     *     possible object is
     *     {@link testMsgToSend }
     *     
     */
    public testMsgToSend getM() {
        return m;
    }

    /**
     * Sets the value of the m property.
     * 
     * @param value
     *     allowed object is
     *     {@link testMsgToSend }
     *     
     */
    public void setM(testMsgToSend value) {
        this.m = value;
    }

    /**
     * Gets the value of the needCalendarSentByFixup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeedCalendarSentByFixup() {
        return needCalendarSentByFixup;
    }

    /**
     * Sets the value of the needCalendarSentByFixup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedCalendarSentByFixup(Boolean value) {
        this.needCalendarSentByFixup = value;
    }

    /**
     * Gets the value of the isCalendarForward property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCalendarForward() {
        return isCalendarForward;
    }

    /**
     * Sets the value of the isCalendarForward property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCalendarForward(Boolean value) {
        this.isCalendarForward = value;
    }

    /**
     * Gets the value of the noSave property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoSave() {
        return noSave;
    }

    /**
     * Sets the value of the noSave property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoSave(Boolean value) {
        this.noSave = value;
    }

    /**
     * Gets the value of the suid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuid() {
        return suid;
    }

    /**
     * Sets the value of the suid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuid(String value) {
        this.suid = value;
    }

}
