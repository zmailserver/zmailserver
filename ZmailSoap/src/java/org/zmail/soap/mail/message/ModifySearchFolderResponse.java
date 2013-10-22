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

package com.zimbra.soap.mail.message;

import com.google.common.base.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.mail.type.SearchFolder;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_MODIFY_SEARCH_FOLDER_RESPONSE)
public class ModifySearchFolderResponse {

    /**
     * @zm-api-field-description Information about search folder, if and only if Search folder was modified.
     */
    @XmlElement(name=MailConstants.E_SEARCH, required=true)
    private final SearchFolder searchFolder;

    /**
     * no-argument constructor wanted by JAXB
     */
    @SuppressWarnings("unused")
    private ModifySearchFolderResponse() {
        this((SearchFolder) null);
    }

    public ModifySearchFolderResponse(SearchFolder searchFolder) {
        this.searchFolder = searchFolder;
    }

    public SearchFolder getSearchFolder() { return searchFolder; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("searchFolder", searchFolder)
            .toString();
    }
}
