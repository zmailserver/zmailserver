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

package generated.zcsclient.replication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for replicationStatusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="replicationStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="masterStatus" type="{urn:zimbraRepl}replicationMasterStatus" minOccurs="0"/>
 *         &lt;element name="slaveStatus" type="{urn:zimbraRepl}replicationSlaveStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="replicationEnabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="currentRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="originalRole" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "replicationStatusResponse", propOrder = {
    "masterStatus",
    "slaveStatus"
})
public class testReplicationStatusResponse {

    protected testReplicationMasterStatus masterStatus;
    protected testReplicationSlaveStatus slaveStatus;
    @XmlAttribute(name = "replicationEnabled", required = true)
    protected boolean replicationEnabled;
    @XmlAttribute(name = "currentRole")
    protected String currentRole;
    @XmlAttribute(name = "originalRole")
    protected String originalRole;

    /**
     * Gets the value of the masterStatus property.
     * 
     * @return
     *     possible object is
     *     {@link testReplicationMasterStatus }
     *     
     */
    public testReplicationMasterStatus getMasterStatus() {
        return masterStatus;
    }

    /**
     * Sets the value of the masterStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link testReplicationMasterStatus }
     *     
     */
    public void setMasterStatus(testReplicationMasterStatus value) {
        this.masterStatus = value;
    }

    /**
     * Gets the value of the slaveStatus property.
     * 
     * @return
     *     possible object is
     *     {@link testReplicationSlaveStatus }
     *     
     */
    public testReplicationSlaveStatus getSlaveStatus() {
        return slaveStatus;
    }

    /**
     * Sets the value of the slaveStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link testReplicationSlaveStatus }
     *     
     */
    public void setSlaveStatus(testReplicationSlaveStatus value) {
        this.slaveStatus = value;
    }

    /**
     * Gets the value of the replicationEnabled property.
     * 
     */
    public boolean isReplicationEnabled() {
        return replicationEnabled;
    }

    /**
     * Sets the value of the replicationEnabled property.
     * 
     */
    public void setReplicationEnabled(boolean value) {
        this.replicationEnabled = value;
    }

    /**
     * Gets the value of the currentRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentRole() {
        return currentRole;
    }

    /**
     * Sets the value of the currentRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentRole(String value) {
        this.currentRole = value;
    }

    /**
     * Gets the value of the originalRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalRole() {
        return originalRole;
    }

    /**
     * Sets the value of the originalRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalRole(String value) {
        this.originalRole = value;
    }

}
