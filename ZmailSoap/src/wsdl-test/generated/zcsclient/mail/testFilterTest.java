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
 * <p>Java class for filterTest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="filterTest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="negative" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "filterTest")
@XmlSeeAlso({
    testImportanceTest.class,
    testLinkedInTest.class,
    testCurrentTimeTest.class,
    testHeaderTest.class,
    testBodyTest.class,
    testSizeTest.class,
    testListTest.class,
    testFlaggedTest.class,
    testFacebookTest.class,
    testCurrentDayOfWeekTest.class,
    testTrueTest.class,
    testAttachmentTest.class,
    testContactRankingTest.class,
    testHeaderExistsTest.class,
    testAddressTest.class,
    testDateTest.class,
    testTwitterTest.class,
    testBulkTest.class,
    testMimeHeaderTest.class,
    testConversationTest.class,
    testAddressBookTest.class,
    testSocialcastTest.class,
    testInviteTest.class,
    testMeTest.class
})
public class testFilterTest {

    @XmlAttribute(name = "index", required = true)
    protected int index;
    @XmlAttribute(name = "negative")
    protected Boolean negative;

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the negative property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNegative() {
        return negative;
    }

    /**
     * Sets the value of the negative property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegative(Boolean value) {
        this.negative = value;
    }

}
