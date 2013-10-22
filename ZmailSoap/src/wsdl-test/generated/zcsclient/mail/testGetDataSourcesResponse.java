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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getDataSourcesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDataSourcesResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="imap" type="{urn:zimbraMail}mailImapDataSource"/>
 *           &lt;element name="pop3" type="{urn:zimbraMail}mailPop3DataSource"/>
 *           &lt;element name="caldav" type="{urn:zimbraMail}mailCaldavDataSource"/>
 *           &lt;element name="yab" type="{urn:zimbraMail}mailYabDataSource"/>
 *           &lt;element name="rss" type="{urn:zimbraMail}mailRssDataSource"/>
 *           &lt;element name="gal" type="{urn:zimbraMail}mailGalDataSource"/>
 *           &lt;element name="cal" type="{urn:zimbraMail}mailCalDataSource"/>
 *           &lt;element name="unknown" type="{urn:zimbraMail}mailUnknownDataSource"/>
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
@XmlType(name = "getDataSourcesResponse", propOrder = {
    "imapOrPop3OrCaldav"
})
public class testGetDataSourcesResponse {

    @XmlElements({
        @XmlElement(name = "caldav", type = testMailCaldavDataSource.class),
        @XmlElement(name = "imap", type = testMailImapDataSource.class),
        @XmlElement(name = "yab", type = testMailYabDataSource.class),
        @XmlElement(name = "rss", type = testMailRssDataSource.class),
        @XmlElement(name = "pop3", type = testMailPop3DataSource.class),
        @XmlElement(name = "cal", type = testMailCalDataSource.class),
        @XmlElement(name = "unknown", type = testMailUnknownDataSource.class),
        @XmlElement(name = "gal", type = testMailGalDataSource.class)
    })
    protected List<testMailDataSource> imapOrPop3OrCaldav;

    /**
     * Gets the value of the imapOrPop3OrCaldav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imapOrPop3OrCaldav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImapOrPop3OrCaldav().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testMailCaldavDataSource }
     * {@link testMailImapDataSource }
     * {@link testMailYabDataSource }
     * {@link testMailRssDataSource }
     * {@link testMailPop3DataSource }
     * {@link testMailCalDataSource }
     * {@link testMailUnknownDataSource }
     * {@link testMailGalDataSource }
     * 
     * 
     */
    public List<testMailDataSource> getImapOrPop3OrCaldav() {
        if (imapOrPop3OrCaldav == null) {
            imapOrPop3OrCaldav = new ArrayList<testMailDataSource>();
        }
        return this.imapOrPop3OrCaldav;
    }

}
