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


/**
 * <p>Java class for mpInviteInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mpInviteInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tz" type="{urn:zmailMail}calTZInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="replies" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="reply" type="{urn:zmailMail}calendarReply" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="comp" type="{urn:zmailMail}inviteComponent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mpInviteInfo", propOrder = {
    "tz",
    "replies",
    "comp"
})
public class testMpInviteInfo {

    protected List<testCalTZInfo> tz;
    protected testMpInviteInfo.Replies replies;
    protected List<testInviteComponent> comp;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the tz property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tz property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTz().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testCalTZInfo }
     * 
     * 
     */
    public List<testCalTZInfo> getTz() {
        if (tz == null) {
            tz = new ArrayList<testCalTZInfo>();
        }
        return this.tz;
    }

    /**
     * Gets the value of the replies property.
     * 
     * @return
     *     possible object is
     *     {@link testMpInviteInfo.Replies }
     *     
     */
    public testMpInviteInfo.Replies getReplies() {
        return replies;
    }

    /**
     * Sets the value of the replies property.
     * 
     * @param value
     *     allowed object is
     *     {@link testMpInviteInfo.Replies }
     *     
     */
    public void setReplies(testMpInviteInfo.Replies value) {
        this.replies = value;
    }

    /**
     * Gets the value of the comp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testInviteComponent }
     * 
     * 
     */
    public List<testInviteComponent> getComp() {
        if (comp == null) {
            comp = new ArrayList<testInviteComponent>();
        }
        return this.comp;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
     *         &lt;element name="reply" type="{urn:zmailMail}calendarReply" maxOccurs="unbounded" minOccurs="0"/>
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
        "reply"
    })
    public static class Replies {

        protected List<testCalendarReply> reply;

        /**
         * Gets the value of the reply property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reply property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReply().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link testCalendarReply }
         * 
         * 
         */
        public List<testCalendarReply> getReply() {
            if (reply == null) {
                reply = new ArrayList<testCalendarReply>();
            }
            return this.reply;
        }

    }

}
