/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
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

package com.zimbra.cs.taglib.bean;

import com.zimbra.client.ZPhone;
import com.zimbra.client.ZSelectiveCallRejection;

import java.util.ArrayList;
import java.util.List;

public class ZSelectiveCallRejectionBean extends ZCallFeatureBean {

    public ZSelectiveCallRejectionBean(ZSelectiveCallRejection feature) {
        super(feature);
    }

    public List<String> getRejectFrom() {
        List<String> data = getFeature().getRejectFrom();
        List<String> result = new ArrayList<String>(data.size());
        for (String name : data) {
            result.add(ZPhone.getDisplay(name));
        }
        return result;
    }

    public void setRejectFrom(List<String> list) {
        List<String> names = new ArrayList<String>(list.size());
        for (String display : list) {
            names.add(ZPhone.getNonFullName(display));
        }
        getFeature().setRejectFrom(names);
    }

    protected ZSelectiveCallRejection getFeature() {
        return (ZSelectiveCallRejection) super.getFeature(); 
    }
}
