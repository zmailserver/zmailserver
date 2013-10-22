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
 * <p>Java class for xmppComponentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xmppComponentInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:zimbraAdmin}adminAttrsImpl">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="x-domainName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="x-serverName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmppComponentInfo")
public class testXmppComponentInfo
    extends testAdminAttrsImpl
{

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "x-domainName")
    protected String xDomainName;
    @XmlAttribute(name = "x-serverName")
    protected String xServerName;

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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the xDomainName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXDomainName() {
        return xDomainName;
    }

    /**
     * Sets the value of the xDomainName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXDomainName(String value) {
        this.xDomainName = value;
    }

    /**
     * Gets the value of the xServerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXServerName() {
        return xServerName;
    }

    /**
     * Sets the value of the xServerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXServerName(String value) {
        this.xServerName = value;
    }

}
