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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAppointmentResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAppointmentResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="appt" type="{urn:zimbraMail}calendarItemInfo"/>
 *           &lt;element name="task" type="{urn:zimbraMail}taskItemInfo"/>
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
@XmlType(name = "getAppointmentResponse", propOrder = {
    "appt",
    "task"
})
public class testGetAppointmentResponse {

    protected testCalendarItemInfo appt;
    protected testTaskItemInfo task;

    /**
     * Gets the value of the appt property.
     * 
     * @return
     *     possible object is
     *     {@link testCalendarItemInfo }
     *     
     */
    public testCalendarItemInfo getAppt() {
        return appt;
    }

    /**
     * Sets the value of the appt property.
     * 
     * @param value
     *     allowed object is
     *     {@link testCalendarItemInfo }
     *     
     */
    public void setAppt(testCalendarItemInfo value) {
        this.appt = value;
    }

    /**
     * Gets the value of the task property.
     * 
     * @return
     *     possible object is
     *     {@link testTaskItemInfo }
     *     
     */
    public testTaskItemInfo getTask() {
        return task;
    }

    /**
     * Sets the value of the task property.
     * 
     * @param value
     *     allowed object is
     *     {@link testTaskItemInfo }
     *     
     */
    public void setTask(testTaskItemInfo value) {
        this.task = value;
    }

}
