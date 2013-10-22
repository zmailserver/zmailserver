/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.taglib.bean;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.VoiceConstants;
import org.zmail.client.ZCallFeatures;

public class ZCallFeaturesBean {

    private ZCallFeatures mFeatures;

    public ZCallFeaturesBean(ZCallFeatures features, boolean modify) {
        mFeatures = features;
    }

    public ZCallFeatures getCallFeatures() {
        return mFeatures;
    }

    public ZVoiceMailPrefsBean getVoiceMailPrefs() {
        return new ZVoiceMailPrefsBean(mFeatures.getVoiceMailPrefs());
    }

    public ZCallForwardingBean getCallForwardingAll() throws ServiceException {
        return new ZCallForwardingBean(mFeatures.getFeature(VoiceConstants.E_CALL_FORWARD));
    }
    
    public ZCallForwardingBean getCallForwardingNoAnswer() throws ServiceException {
	return new ZCallForwardingBean(mFeatures.getFeature(VoiceConstants.E_CALL_FORWARD_NO_ANSWER));
    }

    public ZSelectiveCallForwardingBean getSelectiveCallForwarding() throws ServiceException {
        return new ZSelectiveCallForwardingBean(mFeatures.getSelectiveCallForwarding());
    }
    
    public ZSelectiveCallRejectionBean getSelectiveCallRejection() throws ServiceException {
        return new ZSelectiveCallRejectionBean(mFeatures.getSelectiveCallRejection());
    }
    
    public ZCallFeatureBean getAnonymousCallRejection() throws ServiceException {
	return new ZCallFeatureBean(mFeatures.getFeature(VoiceConstants.E_ANON_CALL_REJECTION));
    }

    public boolean isEmpty() { return mFeatures.isEmpty(); }
}
