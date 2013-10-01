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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recurrenceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recurrenceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="add" type="{urn:zimbraAdmin}addRecurrenceInfo"/>
 *           &lt;element name="exclude" type="{urn:zimbraAdmin}excludeRecurrenceInfo"/>
 *           &lt;element name="except" type="{urn:zimbraAdmin}exceptionRuleInfo"/>
 *           &lt;element name="cancel" type="{urn:zimbraAdmin}cancelRuleInfo"/>
 *           &lt;element name="dates" type="{urn:zimbraAdmin}singleDates"/>
 *           &lt;element name="rule" type="{urn:zimbraAdmin}simpleRepeatingRule"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recurrenceInfo", propOrder = {
    "addOrExcludeOrExcept"
})
@XmlSeeAlso({
    testAddRecurrenceInfo.class,
    testExcludeRecurrenceInfo.class
})
public class testRecurrenceInfo {

    @XmlElements({
        @XmlElement(name = "dates", type = testSingleDates.class),
        @XmlElement(name = "add", type = testAddRecurrenceInfo.class),
        @XmlElement(name = "rule", type = testSimpleRepeatingRule.class),
        @XmlElement(name = "cancel", type = testCancelRuleInfo.class),
        @XmlElement(name = "exclude", type = testExcludeRecurrenceInfo.class),
        @XmlElement(name = "except", type = testExceptionRuleInfo.class)
    })
    protected List<Object> addOrExcludeOrExcept;

    /**
     * Gets the value of the addOrExcludeOrExcept property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addOrExcludeOrExcept property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddOrExcludeOrExcept().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testSingleDates }
     * {@link testAddRecurrenceInfo }
     * {@link testSimpleRepeatingRule }
     * {@link testCancelRuleInfo }
     * {@link testExcludeRecurrenceInfo }
     * {@link testExceptionRuleInfo }
     * 
     * 
     */
    public List<Object> getAddOrExcludeOrExcept() {
        if (addOrExcludeOrExcept == null) {
            addOrExcludeOrExcept = new ArrayList<Object>();
        }
        return this.addOrExcludeOrExcept;
    }

}
