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

/**
 * @zm-api-command-auth-required true
 * @zm-api-command-admin-auth-required false
 * @zm-api-command-description Tests the connection to the specified data source.  Does not modify the data source or
 * import data.  If the id is specified, uses an existing data source.  Any values specified in the request are used
 * in the test instead of the saved values.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_TEST_DATA_SOURCE_REQUEST)
public class TestDataSourceRequest {

    /**
     * @zm-api-field-description Details of the data source
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
    private DataSource dataSource;

    public TestDataSourceRequest() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public DataSource getDataSource() { return dataSource; }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("dataSource", dataSource);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
