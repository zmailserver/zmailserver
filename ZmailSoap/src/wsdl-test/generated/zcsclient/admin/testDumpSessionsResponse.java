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
 * <p>Java class for dumpSessionsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dumpSessionsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="soap" type="{urn:zmailAdmin}infoForSessionType" minOccurs="0"/>
 *         &lt;element name="imap" type="{urn:zmailAdmin}infoForSessionType" minOccurs="0"/>
 *         &lt;element name="admin" type="{urn:zmailAdmin}infoForSessionType" minOccurs="0"/>
 *         &lt;element name="wiki" type="{urn:zmailAdmin}infoForSessionType" minOccurs="0"/>
 *         &lt;element name="synclistener" type="{urn:zmailAdmin}infoForSessionType" minOccurs="0"/>
 *         &lt;element name="waitset" type="{urn:zmailAdmin}infoForSessionType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="activeSessions" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dumpSessionsResponse", propOrder = {
    "soap",
    "imap",
    "admin",
    "wiki",
    "synclistener",
    "waitset"
})
public class testDumpSessionsResponse {

    protected testInfoForSessionType soap;
    protected testInfoForSessionType imap;
    protected testInfoForSessionType admin;
    protected testInfoForSessionType wiki;
    protected testInfoForSessionType synclistener;
    protected testInfoForSessionType waitset;
    @XmlAttribute(name = "activeSessions", required = true)
    protected int activeSessions;

    /**
     * Gets the value of the soap property.
     * 
     * @return
     *     possible object is
     *     {@link testInfoForSessionType }
     *     
     */
    public testInfoForSessionType getSoap() {
        return soap;
    }

    /**
     * Sets the value of the soap property.
     * 
     * @param value
     *     allowed object is
     *     {@link testInfoForSessionType }
     *     
     */
    public void setSoap(testInfoForSessionType value) {
        this.soap = value;
    }

    /**
     * Gets the value of the imap property.
     * 
     * @return
     *     possible object is
     *     {@link testInfoForSessionType }
     *     
     */
    public testInfoForSessionType getImap() {
        return imap;
    }

    /**
     * Sets the value of the imap property.
     * 
     * @param value
     *     allowed object is
     *     {@link testInfoForSessionType }
     *     
     */
    public void setImap(testInfoForSessionType value) {
        this.imap = value;
    }

    /**
     * Gets the value of the admin property.
     * 
     * @return
     *     possible object is
     *     {@link testInfoForSessionType }
     *     
     */
    public testInfoForSessionType getAdmin() {
        return admin;
    }

    /**
     * Sets the value of the admin property.
     * 
     * @param value
     *     allowed object is
     *     {@link testInfoForSessionType }
     *     
     */
    public void setAdmin(testInfoForSessionType value) {
        this.admin = value;
    }

    /**
     * Gets the value of the wiki property.
     * 
     * @return
     *     possible object is
     *     {@link testInfoForSessionType }
     *     
     */
    public testInfoForSessionType getWiki() {
        return wiki;
    }

    /**
     * Sets the value of the wiki property.
     * 
     * @param value
     *     allowed object is
     *     {@link testInfoForSessionType }
     *     
     */
    public void setWiki(testInfoForSessionType value) {
        this.wiki = value;
    }

    /**
     * Gets the value of the synclistener property.
     * 
     * @return
     *     possible object is
     *     {@link testInfoForSessionType }
     *     
     */
    public testInfoForSessionType getSynclistener() {
        return synclistener;
    }

    /**
     * Sets the value of the synclistener property.
     * 
     * @param value
     *     allowed object is
     *     {@link testInfoForSessionType }
     *     
     */
    public void setSynclistener(testInfoForSessionType value) {
        this.synclistener = value;
    }

    /**
     * Gets the value of the waitset property.
     * 
     * @return
     *     possible object is
     *     {@link testInfoForSessionType }
     *     
     */
    public testInfoForSessionType getWaitset() {
        return waitset;
    }

    /**
     * Sets the value of the waitset property.
     * 
     * @param value
     *     allowed object is
     *     {@link testInfoForSessionType }
     *     
     */
    public void setWaitset(testInfoForSessionType value) {
        this.waitset = value;
    }

    /**
     * Gets the value of the activeSessions property.
     * 
     */
    public int getActiveSessions() {
        return activeSessions;
    }

    /**
     * Sets the value of the activeSessions property.
     * 
     */
    public void setActiveSessions(int value) {
        this.activeSessions = value;
    }

}
