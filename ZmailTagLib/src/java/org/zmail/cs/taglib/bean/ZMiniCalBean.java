/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import org.zmail.client.ZAppointmentHit;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class ZMiniCalBean {

    private Map<String,Boolean> mDays;

    public ZMiniCalBean(Set<String> days) {
        mDays = new HashMap<String,Boolean>();
        for (String day : days) {
            mDays.put(day, true);
        }
    }

    public Map<String,Boolean> getDays() {
        return mDays;
    }
}