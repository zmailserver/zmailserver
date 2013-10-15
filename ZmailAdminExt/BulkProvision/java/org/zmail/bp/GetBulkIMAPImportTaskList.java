/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package org.zmail.bp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import org.zmail.bp.BulkIMAPImportTaskManager.taskKeys;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.AdminExtConstants;
import org.zmail.common.soap.Element;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.AttributeManager.IDNType;
import org.zmail.cs.account.accesscontrol.AccessControlUtil;
import org.zmail.cs.service.admin.AdminDocumentHandler;
import org.zmail.cs.service.admin.ToXML;
import org.zmail.soap.DocumentHandler;
import org.zmail.soap.ZmailSoapContext;

public class GetBulkIMAPImportTaskList extends AdminDocumentHandler  {
    @Override
    public Element handle(Element request, Map<String, Object> context)
            throws ServiceException {
        ZmailSoapContext zsc = getZmailSoapContext(context);
        Account authedAcct = DocumentHandler.getAuthenticatedAccount(zsc);
        Element response = zsc.createElement(AdminExtConstants.GET_BULK_IMAP_IMPORT_TASKLIST_RESPONSE);
        HashMap<String, Queue<HashMap<taskKeys, String>>> importQueues = BulkIMAPImportTaskManager.getImportQueues();
        if(AccessControlUtil.isGlobalAdmin(authedAcct, true)) {
            synchronized(importQueues) {
               Iterator<String> keyIter = importQueues.keySet().iterator();
               while(keyIter.hasNext()) {
                   encodeTask(response,keyIter.next());
               }
            }
        } else {
            String adminID = zsc.getAuthtokenAccountId();
            if(importQueues.containsKey(adminID)) {
                encodeTask(response,adminID);
            }
        }
        return response;
    }
    
    private void encodeTask (Element response, String adminID) throws ServiceException {
        Account acct = Provisioning.getInstance().getAccountById(adminID);
        Queue<HashMap<taskKeys, String>> fq =  BulkIMAPImportTaskManager.getFinishedQueue(adminID);
        Queue<HashMap<taskKeys, String>> eq =  BulkIMAPImportTaskManager.getFailedQueue(adminID);
        int numFinished = 0;
        if(fq!=null) {
            synchronized(fq) {
                numFinished = fq.size();
            }
        }
        int numFailed = 0;
        if(eq!=null) {
            synchronized(eq) {
                numFailed = eq.size();
            }
        }        
        int numTotal = 0;
        Queue<HashMap<taskKeys, String>> rq =  BulkIMAPImportTaskManager.getRunningQueue(adminID);
        if(rq!=null) {
            synchronized(rq) {
                numTotal = rq.size();
            }
        } 
        Element elTask = response.addElement(AdminExtConstants.E_Task);
        ToXML.encodeAttr(elTask,AdminExtConstants.A_owner,acct.getName(),AdminConstants.E_A,AdminConstants.A_N,IDNType.none, true);
        ToXML.encodeAttr(elTask,AdminExtConstants.A_totalTasks,Integer.toString(numTotal),AdminConstants.E_A,AdminConstants.A_N,IDNType.none, true);
        ToXML.encodeAttr(elTask,AdminExtConstants.A_finishedTasks,Integer.toString(numFinished),AdminConstants.E_A,AdminConstants.A_N,IDNType.none, true);
        ToXML.encodeAttr(elTask,AdminExtConstants.A_failedTasks,Integer.toString(numFailed),AdminConstants.E_A,AdminConstants.A_N,IDNType.none, true);
    }
}
