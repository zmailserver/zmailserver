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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for instanceDataAttrs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="instanceDataAttrs">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:zmailMail}commonInstanceDataAttrs">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="dur" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "instanceDataAttrs")
@XmlSeeAlso({
    testInstanceDataInfo.class,
    testCommonCalendaringData.class
})
public class testInstanceDataAttrs
    extends testCommonInstanceDataAttrs
{

    @XmlAttribute(name = "dur")
    protected Long dur;

    /**
     * Gets the value of the dur property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDur() {
        return dur;
    }

    /**
     * Sets the value of the dur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDur(Long value) {
        this.dur = value;
    }

}
