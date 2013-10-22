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
 * <p>Java class for hostStats complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="hostStats">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stats" type="{urn:zimbraAdmin}statsInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="hn" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "hostStats", propOrder = {
    "stats"
})
public class testHostStats {

    protected testStatsInfo stats;
    @XmlAttribute(name = "hn", required = true)
    protected String hn;

    /**
     * Gets the value of the stats property.
     * 
     * @return
     *     possible object is
     *     {@link testStatsInfo }
     *     
     */
    public testStatsInfo getStats() {
        return stats;
    }

    /**
     * Sets the value of the stats property.
     * 
     * @param value
     *     allowed object is
     *     {@link testStatsInfo }
     *     
     */
    public void setStats(testStatsInfo value) {
        this.stats = value;
    }

    /**
     * Gets the value of the hn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHn() {
        return hn;
    }

    /**
     * Sets the value of the hn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHn(String value) {
        this.hn = value;
    }

}
