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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLoggerStatsRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLoggerStatsRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hostname" type="{urn:zimbraAdmin}hostName" minOccurs="0"/>
 *         &lt;element name="stats" type="{urn:zimbraAdmin}statsSpec" minOccurs="0"/>
 *         &lt;element name="startTime" type="{urn:zimbraAdmin}timeAttr" minOccurs="0"/>
 *         &lt;element name="endTime" type="{urn:zimbraAdmin}timeAttr" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLoggerStatsRequest", propOrder = {
    "hostname",
    "stats",
    "startTime",
    "endTime"
})
public class testGetLoggerStatsRequest {

    protected testHostName hostname;
    protected testStatsSpec stats;
    protected testTimeAttr startTime;
    protected testTimeAttr endTime;

    /**
     * Gets the value of the hostname property.
     * 
     * @return
     *     possible object is
     *     {@link testHostName }
     *     
     */
    public testHostName getHostname() {
        return hostname;
    }

    /**
     * Sets the value of the hostname property.
     * 
     * @param value
     *     allowed object is
     *     {@link testHostName }
     *     
     */
    public void setHostname(testHostName value) {
        this.hostname = value;
    }

    /**
     * Gets the value of the stats property.
     * 
     * @return
     *     possible object is
     *     {@link testStatsSpec }
     *     
     */
    public testStatsSpec getStats() {
        return stats;
    }

    /**
     * Sets the value of the stats property.
     * 
     * @param value
     *     allowed object is
     *     {@link testStatsSpec }
     *     
     */
    public void setStats(testStatsSpec value) {
        this.stats = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link testTimeAttr }
     *     
     */
    public testTimeAttr getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link testTimeAttr }
     *     
     */
    public void setStartTime(testTimeAttr value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link testTimeAttr }
     *     
     */
    public testTimeAttr getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link testTimeAttr }
     *     
     */
    public void setEndTime(testTimeAttr value) {
        this.endTime = value;
    }

}
