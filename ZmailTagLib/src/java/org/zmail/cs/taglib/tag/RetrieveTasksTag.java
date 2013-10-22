/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.taglib.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

import org.zmail.client.ZMailbox;
import org.zmail.client.ZFolder;
import org.zmail.client.ZSearchParams;
import org.zmail.cs.taglib.bean.ZFolderBean;
import org.zmail.soap.type.SearchSortBy;

public class RetrieveTasksTag extends ZmailSimpleTag {
    private static final int DEFAULT_TASKS_LIMIT = 50;

    private String mVar;
    private ZFolderBean mTasklist;

    private ZSearchParams params;

    public void setVar(String var) { this.mVar = var; }
    public void setTasklist(ZFolderBean tasklist) { this.mTasklist = tasklist; }


    @Override
    public void doTag() throws JspException, IOException {
            ZMailbox mailbox = getMailbox();

            SearchSortBy mSortBy = SearchSortBy.dateDesc;
            String                 mTypes = ZSearchParams.TYPE_TASK;

            PageContext pageContext = (PageContext) getJspContext();
            SearchContext  sContext = SearchContext.newSearchContext(pageContext);

/*
            sContext.setSfi(mTasklist.getId());
            sContext.setSt(mTypes);
            sContext.setTypes(mTypes);
*/

            ZFolder tasklist = mTasklist.folderObject();
/*
            sContext.setQuery("in:\"" + tasklist.getRootRelativePath() + "\"");

            sContext.setBackTo(I18nUtil.getLocalizedMessage(pageContext, "backToFolder", new Object[] {tasklist.getName()}));
            sContext.setShortBackTo(tasklist.getName());

            sContext.setFolder(new ZFolderBean(tasklist));
            sContext.setTitle(tasklist.getName());
            sContext.setSelectedId(tasklist.getId());
*/

//            params = new ZSearchParams(sContext.getQuery());
            params = new ZSearchParams("in:\"" + tasklist.getRootRelativePath() + "\"");
            params.setOffset(0);
            params.setLimit(DEFAULT_TASKS_LIMIT);
            params.setSortBy(mSortBy);
            params.setTypes(mTypes);

            sContext.setParams(params);
            sContext.doSearch(mailbox, false, false);

//            pageContext.setAttribute(mVar, sContext.getSearchResult().getHits(), PageContext.REQUEST_SCOPE);
            pageContext.setAttribute(mVar, sContext, PageContext.REQUEST_SCOPE);
    }
}
