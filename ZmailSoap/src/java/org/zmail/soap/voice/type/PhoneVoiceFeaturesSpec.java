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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.VoiceConstants;

@XmlAccessorType(XmlAccessType.NONE)
public class PhoneVoiceFeaturesSpec {

    public interface CallFeatureReq {
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class AnonCallRejectionReq
    implements CallFeatureReq {
        public AnonCallRejectionReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class CallerIdBlockingReq
    implements CallFeatureReq {
        public CallerIdBlockingReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class CallForwardReq
    implements CallFeatureReq {
        public CallForwardReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class CallForwardBusyLineReq
    implements CallFeatureReq {
        public CallForwardBusyLineReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class CallForwardNoAnswerReq
    implements CallFeatureReq {
        public CallForwardNoAnswerReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class CallWaitingReq
    implements CallFeatureReq {
        public CallWaitingReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class SelectiveCallForwardReq
    implements CallFeatureReq {
        public SelectiveCallForwardReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class SelectiveCallAcceptanceReq
    implements CallFeatureReq {
        public SelectiveCallAcceptanceReq() {}
    }

    @XmlAccessorType(XmlAccessType.NONE)
    public static class SelectiveCallRejectionReq
    implements CallFeatureReq {
        public SelectiveCallRejectionReq() {}
    }

    /**
     * @zm-api-field-tag phone
     * @zm-api-field-description Phone
     */
    @XmlAttribute(name=AccountConstants.A_NAME /* name */, required=true)
    private String name;

    /**
     * @zm-api-field-description
     */
    @XmlElements({
        @XmlElement(name=VoiceConstants.E_VOICE_MAIL_PREFS /* voicemailprefs */, type=VoiceMailPrefsReq.class),
        @XmlElement(name=VoiceConstants.E_ANON_CALL_REJECTION /* anoncallrejection */, type=AnonCallRejectionReq.class),
        @XmlElement(name=VoiceConstants.E_CALLER_ID_BLOCKING /* calleridblocking */, type=CallerIdBlockingReq.class),
        @XmlElement(name=VoiceConstants.E_CALL_FORWARD /* callforward */, type=CallForwardReq.class),
        @XmlElement(name=VoiceConstants.E_CALL_FORWARD_BUSY_LINE /* callforwardbusyline */, type=CallForwardBusyLineReq.class),
        @XmlElement(name=VoiceConstants.E_CALL_FORWARD_NO_ANSWER /* callforwardnoanswer */, type=CallForwardNoAnswerReq.class),
        @XmlElement(name=VoiceConstants.E_CALL_WAITING /* callwaiting */, type=CallWaitingReq.class),
        @XmlElement(name=VoiceConstants.E_SELECTIVE_CALL_FORWARD /* selectivecallforward */, type=SelectiveCallForwardReq.class),
        @XmlElement(name=VoiceConstants.E_SELECTIVE_CALL_ACCEPTANCE /* selectivecallacceptance */, type=SelectiveCallAcceptanceReq.class),
        @XmlElement(name=VoiceConstants.E_SELECTIVE_CALL_REJECTION /* selectivecallrejection */, type=SelectiveCallRejectionReq.class)
    })
    private List<CallFeatureReq> callFeatures = Lists.newArrayList();

    public PhoneVoiceFeaturesSpec() {
    }

    public void setName(String name) { this.name = name; }
    public void setCallFeatures(Iterable <CallFeatureReq> callFeatures) {
        this.callFeatures.clear();
        if (callFeatures != null) {
            Iterables.addAll(this.callFeatures, callFeatures);
        }
    }

    public void addCallFeature(CallFeatureReq callFeature) {
        this.callFeatures.add(callFeature);
    }

    public String getName() { return name; }
    public List<CallFeatureReq> getCallFeatures() {
        return callFeatures;
    }

    public Objects.ToStringHelper addToStringInfo(Objects.ToStringHelper helper) {
        return helper
            .add("name", name)
            .add("callFeatures", callFeatures);
    }

    @Override
    public String toString() {
        return addToStringInfo(Objects.toStringHelper(this)).toString();
    }
}
