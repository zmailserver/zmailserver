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

package org.zmail.soap.mail.message;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.zmail.common.soap.MailConstants;
import org.zmail.soap.mail.type.CalDataSourceNameOrId;
import org.zmail.soap.mail.type.CaldavDataSourceNameOrId;
import org.zmail.soap.mail.type.DataSourceNameOrId;
import org.zmail.soap.mail.type.GalDataSourceNameOrId;
import org.zmail.soap.mail.type.ImapDataSourceNameOrId;
import org.zmail.soap.mail.type.Pop3DataSourceNameOrId;
import org.zmail.soap.mail.type.RssDataSourceNameOrId;
import org.zmail.soap.mail.type.UnknownDataSourceNameOrId;
import org.zmail.soap.mail.type.YabDataSourceNameOrId;

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Triggers the specified data sources to kick off their import processes.
 * Data import runs asynchronously, so the response immediately returns.  Status of an import can be queried via
 * the <b>&lt;GetImportStatusRequest></b> message.  If the server receives an <b>&lt;ImportDataRequest></b> while
 * an import is already running for a given data source, the second request is ignored.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_IMPORT_DATA_REQUEST)
public class ImportDataRequest {

    /**
     * @zm-api-field-description
     */
    @XmlElements({
        @XmlElement(name=MailConstants.E_DS_IMAP /* imap */, type=ImapDataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_POP3 /* pop3 */, type=Pop3DataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_CALDAV /* caldav */, type=CaldavDataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_YAB /* yab */, type=YabDataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_RSS /* rss */, type=RssDataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_GAL /* gal */, type=GalDataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_CAL /* cal */, type=CalDataSourceNameOrId.class),
        @XmlElement(name=MailConstants.E_DS_UNKNOWN /* unknown */, type=UnknownDataSourceNameOrId.class)
    })
    private List<DataSourceNameOrId> dataSources = Lists.newArrayList();

    public ImportDataRequest() {
    }

    public void setDataSources(Iterable <DataSourceNameOrId> dataSources) {
        this.dataSources.clear();
        if (dataSources != null) {
            Iterables.addAll(this.dataSources,dataSources);
        }
    }

    public void addDataSource(DataSourceNameOrId dataSource) {
        this.dataSources.add(dataSource);
    }

    public List<DataSourceNameOrId> getDataSources() {
        return dataSources;
    }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("dataSources", dataSources);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
