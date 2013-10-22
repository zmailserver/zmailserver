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
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.soap.mail.type.DispositionAndText;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=MailConstants.E_DIFF_DOCUMENT_RESPONSE)
public class DiffDocumentResponse {

    /**
     * @zm-api-field-description Difference information in chunks
     */
    @XmlElement(name=MailConstants.E_CHUNK /* chunk */, required=false)
    private List<DispositionAndText> chunks = Lists.newArrayList();

    public DiffDocumentResponse() {
    }

    public void setChunks(Iterable <DispositionAndText> chunks) {
        this.chunks.clear();
        if (chunks != null) {
            Iterables.addAll(this.chunks,chunks);
        }
    }

    public DiffDocumentResponse addChunk(DispositionAndText chunk) {
        this.chunks.add(chunk);
        return this;
    }

    public List<DispositionAndText> getChunks() {
        return Collections.unmodifiableList(chunks);
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("chunks", chunks);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this))
                .toString();
    }
}
