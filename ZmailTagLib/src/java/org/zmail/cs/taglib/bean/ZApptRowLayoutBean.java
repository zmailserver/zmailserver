/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2009, 2010, 2012 VMware, Inc.
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

import java.util.List;
import java.util.Date;

public class ZApptRowLayoutBean {

    private List<ZApptCellLayoutBean> mCells;
    private int mRowNum;
    private long mTime;

    public ZApptRowLayoutBean(List<ZApptCellLayoutBean> cells, int rowNum, long time) {
        mCells = cells;
        mRowNum = rowNum;
        mTime = time;
    }

    public List<ZApptCellLayoutBean> getCells() {
        return mCells;
    }

    public int getRowNum() {
        return mRowNum;
    }

    public long getTime() {
        return mTime;
    }

    public Date getDate() {
        return new Date(mTime);
    }

    public long getScheduleOverlapCount() {
        int overlap = 0;
        ZApptDayLayoutBean day = null;
        for ( ZApptCellLayoutBean cell : mCells) {
            if (cell.getAppt() != null && cell.getDay() != day) {
                overlap++;
                day = cell.getDay();
            }
        }
        return overlap;
    }
}
