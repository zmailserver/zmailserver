/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

package com.zimbra.soap.adminext.message;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminExtConstants;
import com.zimbra.soap.adminext.type.BulkIMAPImportTaskInfo;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminExtConstants.E_GET_BULK_IMAP_IMPORT_TASKLIST_RESPONSE)
public class GetBulkIMAPImportTaskListResponse {

    /**
     * @zm-api-field-description Information on inport tasks
     */
    @XmlElement(name=AdminExtConstants.E_Task /* task */, required=false)
    private List<BulkIMAPImportTaskInfo> tasks = Lists.newArrayList();

    public GetBulkIMAPImportTaskListResponse() {
    }

    public void setTasks(Iterable <BulkIMAPImportTaskInfo> tasks) {
        this.tasks.clear();
        if (tasks != null) {
            Iterables.addAll(this.tasks,tasks);
        }
    }

    public void addTask(BulkIMAPImportTaskInfo task) {
        this.tasks.add(task);
    }

    public List<BulkIMAPImportTaskInfo> getTasks() {
        return tasks;
    }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("tasks", tasks);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
