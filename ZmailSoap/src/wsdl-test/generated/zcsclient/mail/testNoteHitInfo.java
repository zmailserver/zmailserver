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
 * <p>Java class for noteHitInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="noteHitInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:zmailMail}noteInfo">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="sf" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "noteHitInfo")
public class testNoteHitInfo
    extends testNoteInfo
{

    @XmlAttribute(name = "sf")
    protected String sf;

    /**
     * Gets the value of the sf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSf() {
        return sf;
    }

    /**
     * Sets the value of the sf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSf(String value) {
        this.sf = value;
    }

}
