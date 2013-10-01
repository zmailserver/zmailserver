/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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

package com.zimbra.soap.voice.type;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.zimbra.common.soap.VoiceConstants;

@XmlAccessorType(XmlAccessType.NONE)
public class CallLogItem extends VoiceCallItem {

    /**
     * @zm-api-field-description Parties involved in the call or voice mail.
     * Information for both calling and called parties is returned
     */
    @XmlElement(name=VoiceConstants.E_CALLPARTY /* cp */, required=false)
    private List<CallLogCallParty> callParties = Lists.newArrayList();

    public CallLogItem() {
    }

    public void setCallParties(Iterable <CallLogCallParty> callParties) {
        this.callParties.clear();
        if (callParties != null) {
            Iterables.addAll(this.callParties, callParties);
        }
    }

    public void addCallParty(CallLogCallParty callParty) {
        this.callParties.add(callParty);
    }

    public List<CallLogCallParty> getCallParties() {
        return callParties;
    }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        helper = super.addToStringInfo(helper);
        return helper
            .add("callParties", callParties);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
