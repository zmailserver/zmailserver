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
 * <p>Java class for backupQueryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="backupQueryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="backup" type="{urn:zimbraAdmin}backupQueryInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="totalSpace" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="freeSpace" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="more" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "backupQueryResponse", propOrder = {
    "backup"
})
public class testBackupQueryResponse {

    protected List<testBackupQueryInfo> backup;
    @XmlAttribute(name = "totalSpace", required = true)
    protected long totalSpace;
    @XmlAttribute(name = "freeSpace", required = true)
    protected long freeSpace;
    @XmlAttribute(name = "more")
    protected Boolean more;

    /**
     * Gets the value of the backup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the backup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBackup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link testBackupQueryInfo }
     * 
     * 
     */
    public List<testBackupQueryInfo> getBackup() {
        if (backup == null) {
            backup = new ArrayList<testBackupQueryInfo>();
        }
        return this.backup;
    }

    /**
     * Gets the value of the totalSpace property.
     * 
     */
    public long getTotalSpace() {
        return totalSpace;
    }

    /**
     * Sets the value of the totalSpace property.
     * 
     */
    public void setTotalSpace(long value) {
        this.totalSpace = value;
    }

    /**
     * Gets the value of the freeSpace property.
     * 
     */
    public long getFreeSpace() {
        return freeSpace;
    }

    /**
     * Sets the value of the freeSpace property.
     * 
     */
    public void setFreeSpace(long value) {
        this.freeSpace = value;
    }

    /**
     * Gets the value of the more property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMore() {
        return more;
    }

    /**
     * Sets the value of the more property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMore(Boolean value) {
        this.more = value;
    }

}
