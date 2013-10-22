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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dirPathInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dirPathInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="exists" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isDirectory" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="readable" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="writable" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirPathInfo")
public class testDirPathInfo {

    @XmlAttribute(name = "path", required = true)
    protected String path;
    @XmlAttribute(name = "exists", required = true)
    protected boolean exists;
    @XmlAttribute(name = "isDirectory", required = true)
    protected boolean isDirectory;
    @XmlAttribute(name = "readable", required = true)
    protected boolean readable;
    @XmlAttribute(name = "writable", required = true)
    protected boolean writable;

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the exists property.
     * 
     */
    public boolean isExists() {
        return exists;
    }

    /**
     * Sets the value of the exists property.
     * 
     */
    public void setExists(boolean value) {
        this.exists = value;
    }

    /**
     * Gets the value of the isDirectory property.
     * 
     */
    public boolean isIsDirectory() {
        return isDirectory;
    }

    /**
     * Sets the value of the isDirectory property.
     * 
     */
    public void setIsDirectory(boolean value) {
        this.isDirectory = value;
    }

    /**
     * Gets the value of the readable property.
     * 
     */
    public boolean isReadable() {
        return readable;
    }

    /**
     * Sets the value of the readable property.
     * 
     */
    public void setReadable(boolean value) {
        this.readable = value;
    }

    /**
     * Gets the value of the writable property.
     * 
     */
    public boolean isWritable() {
        return writable;
    }

    /**
     * Sets the value of the writable property.
     * 
     */
    public void setWritable(boolean value) {
        this.writable = value;
    }

}
