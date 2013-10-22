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

package generated.zcsclient.adminext;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bulkIMAPDataImportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bulkIMAPDataImportResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runningAccounts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="account" type="{urn:zimbraAdminExt}nameId" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="totalCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="idleCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="runningCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="finishedCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ConnectionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IMAPHost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IMAPPort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indexBatchSize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UseAdminLogin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IMAPAdminLogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IMAPAdminPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bulkIMAPDataImportResponse", propOrder = {
    "runningAccounts",
    "totalCount",
    "idleCount",
    "runningCount",
    "finishedCount",
    "connectionType",
    "imapHost",
    "imapPort",
    "indexBatchSize",
    "useAdminLogin",
    "imapAdminLogin",
    "imapAdminPassword"
})
public class testBulkIMAPDataImportResponse {

    protected testBulkIMAPDataImportResponse.RunningAccounts runningAccounts;
    protected Integer totalCount;
    protected Integer idleCount;
    protected Integer runningCount;
    protected Integer finishedCount;
    @XmlElement(name = "ConnectionType")
    protected String connectionType;
    @XmlElement(name = "IMAPHost")
    protected String imapHost;
    @XmlElement(name = "IMAPPort")
    protected String imapPort;
    protected String indexBatchSize;
    @XmlElement(name = "UseAdminLogin")
    protected Integer useAdminLogin;
    @XmlElement(name = "IMAPAdminLogin")
    protected String imapAdminLogin;
    @XmlElement(name = "IMAPAdminPassword")
    protected String imapAdminPassword;

    /**
     * Gets the value of the runningAccounts property.
     * 
     * @return
     *     possible object is
     *     {@link testBulkIMAPDataImportResponse.RunningAccounts }
     *     
     */
    public testBulkIMAPDataImportResponse.RunningAccounts getRunningAccounts() {
        return runningAccounts;
    }

    /**
     * Sets the value of the runningAccounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link testBulkIMAPDataImportResponse.RunningAccounts }
     *     
     */
    public void setRunningAccounts(testBulkIMAPDataImportResponse.RunningAccounts value) {
        this.runningAccounts = value;
    }

    /**
     * Gets the value of the totalCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the value of the totalCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalCount(Integer value) {
        this.totalCount = value;
    }

    /**
     * Gets the value of the idleCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdleCount() {
        return idleCount;
    }

    /**
     * Sets the value of the idleCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdleCount(Integer value) {
        this.idleCount = value;
    }

    /**
     * Gets the value of the runningCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRunningCount() {
        return runningCount;
    }

    /**
     * Sets the value of the runningCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRunningCount(Integer value) {
        this.runningCount = value;
    }

    /**
     * Gets the value of the finishedCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFinishedCount() {
        return finishedCount;
    }

    /**
     * Sets the value of the finishedCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFinishedCount(Integer value) {
        this.finishedCount = value;
    }

    /**
     * Gets the value of the connectionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * Sets the value of the connectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionType(String value) {
        this.connectionType = value;
    }

    /**
     * Gets the value of the imapHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMAPHost() {
        return imapHost;
    }

    /**
     * Sets the value of the imapHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMAPHost(String value) {
        this.imapHost = value;
    }

    /**
     * Gets the value of the imapPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMAPPort() {
        return imapPort;
    }

    /**
     * Sets the value of the imapPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMAPPort(String value) {
        this.imapPort = value;
    }

    /**
     * Gets the value of the indexBatchSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexBatchSize() {
        return indexBatchSize;
    }

    /**
     * Sets the value of the indexBatchSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexBatchSize(String value) {
        this.indexBatchSize = value;
    }

    /**
     * Gets the value of the useAdminLogin property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUseAdminLogin() {
        return useAdminLogin;
    }

    /**
     * Sets the value of the useAdminLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUseAdminLogin(Integer value) {
        this.useAdminLogin = value;
    }

    /**
     * Gets the value of the imapAdminLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMAPAdminLogin() {
        return imapAdminLogin;
    }

    /**
     * Sets the value of the imapAdminLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMAPAdminLogin(String value) {
        this.imapAdminLogin = value;
    }

    /**
     * Gets the value of the imapAdminPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMAPAdminPassword() {
        return imapAdminPassword;
    }

    /**
     * Sets the value of the imapAdminPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMAPAdminPassword(String value) {
        this.imapAdminPassword = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="account" type="{urn:zimbraAdminExt}nameId" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "account"
    })
    public static class RunningAccounts {

        protected List<testNameId> account;

        /**
         * Gets the value of the account property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the account property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccount().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link testNameId }
         * 
         * 
         */
        public List<testNameId> getAccount() {
            if (account == null) {
                account = new ArrayList<testNameId>();
            }
            return this.account;
        }

    }

}
