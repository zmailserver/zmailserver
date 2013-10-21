/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
import org.zmail.soap.mail.type.MailCalDataSource;
import org.zmail.soap.mail.type.MailCaldavDataSource;
import org.zmail.soap.mail.type.MailGalDataSource;
import org.zmail.soap.mail.type.MailImapDataSource;
import org.zmail.soap.mail.type.MailPop3DataSource;
import org.zmail.soap.mail.type.MailRssDataSource;
import org.zmail.soap.mail.type.MailUnknownDataSource;
import org.zmail.soap.mail.type.MailYabDataSource;
import org.zmail.soap.type.DataSource;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_GET_DATA_SOURCES_RESPONSE)
public class GetDataSourcesResponse {

    /**
     * @zm-api-field-description Data source information
     */
    @XmlElements({
        @XmlElement(name=MailConstants.E_DS_IMAP /* imap */, type=MailImapDataSource.class),
        @XmlElement(name=MailConstants.E_DS_POP3 /* pop3 */, type=MailPop3DataSource.class),
        @XmlElement(name=MailConstants.E_DS_CALDAV /* caldav */, type=MailCaldavDataSource.class),
        @XmlElement(name=MailConstants.E_DS_YAB /* yab */, type=MailYabDataSource.class),
        @XmlElement(name=MailConstants.E_DS_RSS /* rss */, type=MailRssDataSource.class),
        @XmlElement(name=MailConstants.E_DS_GAL /* gal */, type=MailGalDataSource.class),
        @XmlElement(name=MailConstants.E_DS_CAL /* cal */, type=MailCalDataSource.class),
        @XmlElement(name=MailConstants.E_DS_UNKNOWN /* unknown */, type=MailUnknownDataSource.class)
    })
    private List<DataSource> dataSources = Lists.newArrayList();

    public GetDataSourcesResponse() {
    }

    public void setDataSources(Iterable <DataSource> dataSources) {
        this.dataSources.clear();
        if (dataSources != null) {
            Iterables.addAll(this.dataSources,dataSources);
        }
    }

    public GetDataSourcesResponse addDataSource(DataSource dataSource) {
        this.dataSources.add(dataSource);
        return this;
    }

    public List<DataSource> getDataSources() {
        return Collections.unmodifiableList(dataSources);
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
