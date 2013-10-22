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
 * <p>Java class for uploadProxyCARequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadProxyCARequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="cert.aid" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cert.filename" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadProxyCARequest")
public class testUploadProxyCARequest {

    @XmlAttribute(name = "cert.aid", required = true)
    protected String certAid;
    @XmlAttribute(name = "cert.filename", required = true)
    protected String certFilename;

    /**
     * Gets the value of the certAid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertAid() {
        return certAid;
    }

    /**
     * Sets the value of the certAid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertAid(String value) {
        this.certAid = value;
    }

    /**
     * Gets the value of the certFilename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertFilename() {
        return certFilename;
    }

    /**
     * Sets the value of the certFilename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertFilename(String value) {
        this.certFilename = value;
    }

}
