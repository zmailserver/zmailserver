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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for checkBlobConsistencyRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="checkBlobConsistencyRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="volume" type="{urn:zimbraAdmin}intIdAttr" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mbox" type="{urn:zimbraAdmin}intIdAttr" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="checkSize" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkBlobConsistencyRequest", propOrder = {
    "volume",
    "mbox"
})
public class testCheckBlobConsistencyRequest {

    protected List<testIntIdAttr> volume;
    protected List<testIntIdAttr> mbox;
    @XmlAttribute(name = "checkSize")
    protected Boolean checkSize;

    /**
     * Gets the value of the volume property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the volume property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVolume().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testIntIdAttr }
     * 
     * 
     */
    public List<testIntIdAttr> getVolume() {
        if (volume == null) {
            volume = new ArrayList<testIntIdAttr>();
        }
        return this.volume;
    }

    /**
     * Gets the value of the mbox property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mbox property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMbox().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testIntIdAttr }
     * 
     * 
     */
    public List<testIntIdAttr> getMbox() {
        if (mbox == null) {
            mbox = new ArrayList<testIntIdAttr>();
        }
        return this.mbox;
    }

    /**
     * Gets the value of the checkSize property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCheckSize() {
        return checkSize;
    }

    /**
     * Sets the value of the checkSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCheckSize(Boolean value) {
        this.checkSize = value;
    }

}
