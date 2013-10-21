/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

package generated.zcsclient.voice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for callLogItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="callLogItem">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:zmailVoice}voiceCallItem">
 *       &lt;sequence>
 *         &lt;element name="cp" type="{urn:zmailVoice}callLogCallParty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "callLogItem", propOrder = {
    "cp"
})
public class testCallLogItem
    extends testVoiceCallItem
{

    protected List<testCallLogCallParty> cp;

    /**
     * Gets the value of the cp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testCallLogCallParty }
     * 
     * 
     */
    public List<testCallLogCallParty> getCp() {
        if (cp == null) {
            cp = new ArrayList<testCallLogCallParty>();
        }
        return this.cp;
    }

}
