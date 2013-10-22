/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2012 VMware, Inc.
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.mail.type.Folder;

/*
<GetFolderResponse>
  <folder ...>
    <folder .../>
    <folder ...>
      <folder .../>
    </folder>
    <folder .../>
    [<link .../>]
    [<search .../>]
  </folder>
</GetFolderResponse>
 */
@XmlRootElement(name=MailConstants.E_GET_FOLDER_RESPONSE)
@XmlType(propOrder = {MailConstants.E_FOLDER})
public class GetFolderResponse {

    /**
     * @zm-api-field-description Folder information
     */
    @XmlElement(name=MailConstants.E_FOLDER /* folder */, required=true)
    private Folder folder;

    public GetFolderResponse() {
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
