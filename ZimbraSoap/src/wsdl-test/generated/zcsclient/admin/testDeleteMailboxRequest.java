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
 * <p>Java class for deleteMailboxRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteMailboxRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mbox" type="{urn:zimbraAdmin}mailboxByAccountIdSelector" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteMailboxRequest", propOrder = {
    "mbox"
})
public class testDeleteMailboxRequest {

    protected testMailboxByAccountIdSelector mbox;

    /**
     * Gets the value of the mbox property.
     * 
     * @return
     *     possible object is
     *     {@link testMailboxByAccountIdSelector }
     *     
     */
    public testMailboxByAccountIdSelector getMbox() {
        return mbox;
    }

    /**
     * Sets the value of the mbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link testMailboxByAccountIdSelector }
     *     
     */
    public void setMbox(testMailboxByAccountIdSelector value) {
        this.mbox = value;
    }

}
