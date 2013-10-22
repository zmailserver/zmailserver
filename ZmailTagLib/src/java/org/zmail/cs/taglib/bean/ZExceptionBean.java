/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.SoapFaultException;
import org.zmail.common.soap.ZmailNamespace;
import org.zmail.common.util.ExceptionToString;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.AccountServiceException;

public class ZExceptionBean {

    private ServiceException exception;

    public static class Argument {
        private String name, type, val;

        Argument(String name,String type,String val){
            this.type = type;
            this.name = name;
            this.val = val;
        }

        public String getName(){
            return this.name;
        }

        public String getType(){
            return this.type;
        }

        public String getVal(){
            return this.val;
        }
    }

    public ZExceptionBean(Throwable e) {
        if (e instanceof JspException) {
            while ((e instanceof JspException) && (((JspException) e).getRootCause() != null)) {
                e =  ((JspException) e).getRootCause();
            }
        }
        if ((!(e instanceof ServiceException)) && (e.getCause() instanceof ServiceException)) {
            e = e.getCause();
        }
        if( e instanceof SoapFaultException)  {
            if(AccountServiceException.AUTH_FAILED.equals(((SoapFaultException)e).getCode())) {
                ZmailLog.webclient.debug(e.getMessage(), e);
            } else {
                ZmailLog.webclient.error(e.getMessage(), e);
            }
        }
        else{
            ZmailLog.webclient.error(e.getMessage(), e);
        }

        if (e instanceof ServiceException) {
            exception = (ServiceException) e;
        } else {
            exception = ZTagLibException.TAG_EXCEPTION(e.getMessage(), e);
        }
    }

    public Exception getException() {
        return exception;
    }

    public String getCode() {
        return exception.getCode();
    }

    public String getId() {
        return exception.getId();
    }

    public String getStackStrace() {
       return ExceptionToString.ToString(exception);
    }

    public List<Argument> getArguments(){
        List<Argument> args = new ArrayList<Argument>();
        try {
            if (exception instanceof SoapFaultException) {
                SoapFaultException sfe = (SoapFaultException) exception;
                Element d = sfe.getDetail();
                if (d != null) {
                    List<Element> list = d.getPathElementList(
                            new String[] {ZmailNamespace.E_ERROR.getName(), ZmailNamespace.E_ARGUMENT.getName()});
                    for (Element el : list) {
                        args.add(new Argument(el.getAttribute(ZmailNamespace.A_ARG_NAME,""),
                            el.getAttribute(ZmailNamespace.A_ARG_TYPE, ""), el.getText()));
                    }
                }
            }
        } catch (Exception e) {
            //ignore...
        }
        return args;
    }
}
